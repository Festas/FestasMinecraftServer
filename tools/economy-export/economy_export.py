#!/usr/bin/env python3
"""Export a per-server "richest players" leaderboard into economy.json.

This mirrors the :mod:`leaderboard-export` approach: a small, read-only exporter
reads the shared MariaDB and renders the static JSON contract the website
frontend (``website/js/main.js``) consumes at ``/api/economy.json``. No extra
Minecraft plugin and no open database port are required.

The important twist versus the playtime board is the data source. CMI is the
economy provider on every backend, but by default it stores balances **locally
per server** (``plugins/CMI/Settings/DataBaseInfo.yml`` -> ``storage.method:
sqlite``). To export the richest players we therefore switch CMI to the shared
MariaDB, giving **each gameplay server its own database / table** (CMI warns
that servers must never share a table). This exporter then reads one CMI users
table per server for the top-N balances and joins the network-wide LuckPerms
database (``s4_perms``) for the rank badge, exactly like the playtime board.

Deliberate limits (see README.md):

* Only servers with a *real* economy are exported: Survival, Mining, Skyblock.
  The Lobby is intentionally excluded (no relevant balances).
* A player must have a strictly positive balance to appear (zero / debt skipped).
* Balances reflect CMI's last database flush (``AutoSaveInterval``), so the board
  is "recent" rather than real-time – matching the 5-minute export cadence.

The script is intentionally dependency-light (only PyMySQL) and writes the output
file atomically (temp file + ``os.replace``) so nginx never serves a half-written
file. It fails closed on any database error so a transient outage never clobbers
the last-good file. It is meant to be run once per invocation from a systemd timer.
"""

from __future__ import annotations

import argparse
import json
import os
import re
import sys
import tempfile
import time
from typing import Any, Dict, Iterable, List, Optional, Set, Tuple

try:
    import pymysql
    import pymysql.cursors
except ImportError:  # pragma: no cover - handled at runtime with a clear message
    pymysql = None


# --- Defaults (can be overridden via config.json, environment or CLI) ---------

DEFAULT_CONFIG_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "config.json")
DEFAULT_OUTPUT = "/home/deploy/minecraft-website/data/economy.json"
DEFAULT_DB_HOST = "172.25.0.1"
DEFAULT_DB_PORT = 3306
DEFAULT_LUCKPERMS_DB_NAME = "s4_perms"
DEFAULT_CONNECT_TIMEOUT_S = 5
# The balance query is a simple ORDER BY ... LIMIT over one CMI users table, but
# give reads their own, more generous budget so a large user table on a busy host
# is not killed before the query's own timeout applies.
DEFAULT_READ_TIMEOUT_S = 30
DEFAULT_TOP_N = 10
DEFAULT_QUERY_LIMIT = 200

# CMI's MySQL schema (DataBaseInfo.yml -> mysql.tablePrefix, default "CMI_").
# The users table stores the balance as a DOUBLE. Column/table names are
# configurable per server because CMI's exact layout can differ between versions;
# these are the defaults for a stock CMI install.
DEFAULT_CMI_TABLE = "CMI_users"
DEFAULT_CMI_UUID_COLUMN = "player_uuid"
DEFAULT_CMI_NAME_COLUMN = "username"
DEFAULT_CMI_BALANCE_COLUMN = "Balance"

# Only these identifier characters are allowed for table/column names so a config
# value can never be spliced into SQL as anything other than an identifier.
_IDENT_RE = re.compile(r"^[A-Za-z0-9_]+$")

GENERATOR = "economy-export"

# LuckPerms group metadata is tiny (one row per group per meta key), so it is fetched
# whole. ``value = 1`` keeps only granted nodes (a negated node would be 0).
GROUP_WEIGHTS_QUERY = """
SELECT name, permission
FROM luckperms_group_permissions
WHERE permission LIKE 'weight.%%' AND value = 1
"""

GROUP_DISPLAYS_QUERY = """
SELECT name, permission
FROM luckperms_group_permissions
WHERE permission LIKE 'displayname.%%' AND value = 1
"""


def log(message: str) -> None:
    """Write a timestamped log line to stderr (captured by the systemd journal)."""
    stamp = time.strftime("%Y-%m-%dT%H:%M:%S%z")
    print(f"{stamp} {GENERATOR}: {message}", file=sys.stderr, flush=True)


# --- Small typed helpers for reading env / config -----------------------------

def env_str(name: str, default: Optional[str] = None) -> Optional[str]:
    value = os.environ.get(name)
    if value is None:
        return default
    value = value.strip()
    return value if value else default


def env_int(name: str, default: int) -> int:
    raw = env_str(name)
    if raw is None:
        return default
    try:
        return int(raw)
    except ValueError:
        log(f"warning: {name}={raw!r} is not an integer, using {default}")
        return default


def env_bool(name: str, default: bool) -> bool:
    raw = env_str(name)
    if raw is None:
        return default
    return raw.lower() in ("1", "true", "yes", "on")


def as_bool(value: Any, default: bool) -> bool:
    if isinstance(value, bool):
        return value
    if value is None:
        return default
    return str(value).strip().lower() in ("1", "true", "yes", "on")


def canonical_uuid(value: Any) -> str:
    """Return a hex-only, lower-cased UUID key for cross-database matching.

    CMI and LuckPerms both store canonical dashed UUIDs, but stripping the dashes
    yields a representation that matches regardless of dashed/undashed storage.
    """
    if not value:
        return ""
    return re.sub(r"[^0-9a-f]", "", str(value).strip().lower())


def dash_uuid(hex_key: str) -> str:
    """Format a 32-char hex key as a canonical dashed UUID (empty if not 32 hex)."""
    if len(hex_key) != 32:
        return ""
    return f"{hex_key[0:8]}-{hex_key[8:12]}-{hex_key[12:16]}-{hex_key[16:20]}-{hex_key[20:32]}"


def coerce_balance(value: Any) -> float:
    """Return a finite, non-negative balance, tolerating None / Decimal / strings."""
    try:
        number = float(value)
    except (TypeError, ValueError):
        return 0.0
    if number != number or number in (float("inf"), float("-inf")):  # NaN / inf guard
        return 0.0
    return number if number > 0 else 0.0


_COLOR_CODE_RE = re.compile(r"(?:[&\u00a7][0-9A-Fa-fK-Ok-oRrXx])|<[^>]+>")


def strip_colors(text: Any) -> str:
    """Remove legacy (& / §) colour codes and MiniMessage-style <tags> for display."""
    if text is None:
        return ""
    cleaned = _COLOR_CODE_RE.sub("", str(text))
    return re.sub(r"\s+", " ", cleaned).strip()


def prettify_group(name: str) -> str:
    """Human-friendly fallback label for a raw group name (``vip_plus`` → ``Vip Plus``)."""
    return re.sub(r"[_\-]+", " ", str(name)).strip().title()


def valid_identifier(name: Any) -> bool:
    """True when ``name`` is a safe SQL identifier (letters, digits, underscore)."""
    return isinstance(name, str) and bool(_IDENT_RE.match(name))


def env_prefix_for(server_id: str) -> str:
    """Derive the environment-variable prefix for a server (``survival`` → ``CMI_SURVIVAL_DB``)."""
    token = re.sub(r"[^A-Z0-9]", "", str(server_id).upper())
    return f"CMI_{token}_DB"


# --- Configuration ------------------------------------------------------------

def load_config(path: str) -> Dict[str, Any]:
    """Load the JSON config file. A missing file falls back to built-in defaults."""
    if not path or not os.path.isfile(path):
        if path and path != DEFAULT_CONFIG_PATH:
            log(f"warning: config file {path!r} not found, using built-in defaults")
        return {}
    with open(path, "r", encoding="utf-8") as handle:
        data = json.load(handle)
    if not isinstance(data, dict):
        raise ValueError(f"config file {path!r} must contain a JSON object")
    return data


def _str_set(values: Any, transform) -> Set[str]:
    """Build a set from a config list, applying ``transform`` and dropping blanks."""
    result: Set[str] = set()
    if isinstance(values, list):
        for item in values:
            key = transform(item)
            if key:
                result.add(key)
    return result


def resolve_options(config: Dict[str, Any]) -> Dict[str, Any]:
    """Resolve the non-database options (top-N, filters, display overrides)."""
    try:
        top_n = int(config.get("top_n", DEFAULT_TOP_N))
    except (TypeError, ValueError):
        top_n = DEFAULT_TOP_N
    if top_n <= 0:
        top_n = DEFAULT_TOP_N

    try:
        query_limit = int(config.get("query_limit", DEFAULT_QUERY_LIMIT))
    except (TypeError, ValueError):
        query_limit = DEFAULT_QUERY_LIMIT
    # Never fetch fewer rows than we intend to show.
    query_limit = max(query_limit, top_n)

    min_balance_raw = config.get("min_balance")
    try:
        min_balance = float(min_balance_raw) if min_balance_raw is not None else None
    except (TypeError, ValueError):
        min_balance = None

    overrides_raw = config.get("group_display_overrides")
    display_overrides: Dict[str, str] = {}
    if isinstance(overrides_raw, dict):
        for key, value in overrides_raw.items():
            group = str(key).strip().lower()
            label = strip_colors(value)
            if group and label:
                display_overrides[group] = label

    return {
        "top_n": top_n,
        "query_limit": query_limit,
        "min_balance": min_balance,
        "exclude_uuids": _str_set(config.get("exclude_uuids"), canonical_uuid),
        "exclude_names": _str_set(config.get("exclude_names"), lambda v: str(v).strip().lower()),
        "display_overrides": display_overrides,
    }


def resolve_servers(config: Dict[str, Any]) -> List[Dict[str, Any]]:
    """Resolve and validate the per-server economy sources from config.

    Each server entry describes one CMI users table (its own database, per CMI's
    "one table per server" rule). Table/column identifiers are validated so a
    misconfigured value can never break out of an identifier position in SQL.
    """
    raw_servers = config.get("servers")
    if not isinstance(raw_servers, list):
        raise ValueError("config 'servers' must be a list of server objects")

    servers: List[Dict[str, Any]] = []
    seen_ids: Set[str] = set()
    for entry in raw_servers:
        if not isinstance(entry, dict):
            raise ValueError("each 'servers' entry must be a JSON object")

        server_id = str(entry.get("id") or "").strip().lower()
        if not server_id:
            raise ValueError("each server needs a non-empty 'id'")
        if not re.match(r"^[a-z0-9_-]+$", server_id):
            raise ValueError(f"server id {server_id!r} may only contain a-z, 0-9, - and _")
        if server_id in seen_ids:
            raise ValueError(f"duplicate server id {server_id!r}")
        seen_ids.add(server_id)

        table = str(entry.get("table") or DEFAULT_CMI_TABLE)
        uuid_column = str(entry.get("uuid_column") or DEFAULT_CMI_UUID_COLUMN)
        name_column = str(entry.get("name_column") or DEFAULT_CMI_NAME_COLUMN)
        balance_column = str(entry.get("balance_column") or DEFAULT_CMI_BALANCE_COLUMN)
        for label, ident in (
            ("table", table),
            ("uuid_column", uuid_column),
            ("name_column", name_column),
            ("balance_column", balance_column),
        ):
            if not valid_identifier(ident):
                raise ValueError(
                    f"server {server_id!r}: {label} {ident!r} is not a valid SQL identifier"
                )

        env_prefix = str(entry.get("env_prefix") or env_prefix_for(server_id))
        if not re.match(r"^[A-Z0-9_]+$", env_prefix):
            raise ValueError(f"server {server_id!r}: env_prefix {env_prefix!r} is invalid")

        servers.append(
            {
                "id": server_id,
                "label": strip_colors(entry.get("label")) or prettify_group(server_id),
                "currency": strip_colors(entry.get("currency")),
                "env_prefix": env_prefix,
                "database": entry.get("database") if isinstance(entry.get("database"), dict) else {},
                "table": table,
                "uuid_column": uuid_column,
                "name_column": name_column,
                "balance_column": balance_column,
            }
        )

    if not servers:
        raise ValueError("config 'servers' must contain at least one server")
    return servers


# --- Core transformation (pure, unit-testable) --------------------------------

def resolve_highest_group(
    key: str,
    user_groups: Dict[str, Set[str]],
    primary_groups: Dict[str, str],
    group_weights: Dict[str, int],
    group_displays: Dict[str, str],
    display_overrides: Dict[str, str],
) -> Tuple[Optional[str], Optional[str], int]:
    """Return ``(group_name, display_name, weight)`` for the player's highest rank.

    The group with the greatest ``weight`` wins; ties break alphabetically for a
    deterministic result. ``primary_group`` is used when no group nodes exist.
    """
    candidates = set(user_groups.get(key) or ())
    if not candidates:
        primary = primary_groups.get(key)
        if primary:
            candidates = {primary}
    if not candidates:
        return None, None, 0

    # Highest weight first, then group name ascending (deterministic tie-break).
    best = min(candidates, key=lambda g: (-group_weights.get(g, 0), g))
    weight = group_weights.get(best, 0)
    display = display_overrides.get(best) or group_displays.get(best) or prettify_group(best)
    return best, display, weight


def build_server_snapshot(
    server: Dict[str, Any],
    balance_rows: List[Dict[str, Any]],
    user_groups: Dict[str, Set[str]],
    primary_groups: Dict[str, str],
    group_weights: Dict[str, int],
    group_displays: Dict[str, str],
    display_overrides: Dict[str, str],
    top_n: int,
    exclude_uuids: Optional[Set[str]] = None,
    exclude_names: Optional[Set[str]] = None,
    min_balance: Optional[float] = None,
) -> Dict[str, Any]:
    """Turn one server's CMI balance rows + LuckPerms ranks into a section (pure)."""
    exclude_uuids = exclude_uuids or set()
    exclude_names = exclude_names or set()

    candidates: List[Dict[str, Any]] = []
    for row in balance_rows:
        key = canonical_uuid(row.get("uuid"))
        if not key or key in exclude_uuids:
            continue
        name = str(row.get("name") or "").strip()
        if not name or name.lower() in exclude_names:
            continue
        balance = coerce_balance(row.get("balance"))
        if balance <= 0:
            continue
        if min_balance is not None and balance < min_balance:
            continue

        group, group_display, weight = resolve_highest_group(
            key, user_groups, primary_groups, group_weights, group_displays, display_overrides
        )

        candidates.append(
            {
                "name": name,
                "balance": round(balance, 2),
                "group": group,
                "groupDisplay": group_display,
                "groupWeight": weight,
            }
        )

    # Defensive re-sort (SQL already orders by balance desc): highest balance first,
    # then name ascending so equal balances are deterministic.
    candidates.sort(key=lambda entry: (-entry["balance"], entry["name"].lower()))

    players: List[Dict[str, Any]] = []
    for index, entry in enumerate(candidates[:top_n]):
        # Ordered so ``rank`` reads first in the serialised object.
        players.append(
            {
                "rank": index + 1,
                "name": entry["name"],
                "balance": entry["balance"],
                "group": entry["group"],
                "groupDisplay": entry["groupDisplay"],
                "groupWeight": entry["groupWeight"],
            }
        )

    return {
        "id": server["id"],
        "label": server["label"],
        "currency": server.get("currency") or "",
        "count": len(players),
        "players": players,
    }


# --- Database access ----------------------------------------------------------

def connect(db_settings: Dict[str, Any]):
    """Open a PyMySQL connection using the resolved database settings."""
    if pymysql is None:
        raise RuntimeError(
            "PyMySQL is not installed. Install it with 'pip install -r requirements.txt'."
        )

    connect_kwargs: Dict[str, Any] = {
        "host": db_settings["host"],
        "port": db_settings["port"],
        "user": db_settings["user"],
        "password": db_settings["password"],
        "database": db_settings["database"],
        "connect_timeout": db_settings["connect_timeout"],
        "read_timeout": db_settings["read_timeout"],
        "charset": "utf8mb4",
        "cursorclass": pymysql.cursors.DictCursor,
        "autocommit": True,
    }

    # SSL handling mirrors Plan's own "useSSL: true". When a CA file is supplied we
    # verify the certificate. Otherwise we rely on PyMySQL's PREFERRED mode
    # (encrypt if the server offers TLS, without pinning a self-signed internal CA).
    # PREFERRED mode requires PyMySQL >= 1.2.0 (see requirements.txt); on older
    # releases a ca-less connection would silently be plaintext.
    if db_settings["ssl"]:
        ca = db_settings.get("ssl_ca")
        if ca:
            connect_kwargs["ssl_ca"] = ca
            connect_kwargs["ssl_verify_cert"] = db_settings.get("ssl_verify", True)
            connect_kwargs["ssl_verify_identity"] = db_settings.get("ssl_verify_identity", False)
    else:
        connect_kwargs["ssl_disabled"] = True

    return pymysql.connect(**connect_kwargs)


def fetch_balance_rows(connection, server: Dict[str, Any], query_limit: int) -> List[Dict[str, Any]]:
    """Return one server's top players by CMI balance.

    Table and column names are validated in :func:`resolve_servers` against a
    strict identifier allowlist before reaching this query, so they are safe to
    interpolate as identifiers. Only the LIMIT is a bound parameter.
    """
    query = (
        f"SELECT {server['uuid_column']} AS uuid, "
        f"{server['name_column']} AS name, "
        f"{server['balance_column']} AS balance "
        f"FROM {server['table']} "
        f"WHERE {server['balance_column']} IS NOT NULL "
        f"ORDER BY {server['balance_column']} DESC, {server['name_column']} ASC "
        f"LIMIT %s"
    )
    with connection.cursor() as cursor:
        cursor.execute(query, (query_limit,))
        return list(cursor.fetchall())


def _parse_suffix_int(permission: str, prefix: str) -> Optional[int]:
    """Parse the integer suffix of a LuckPerms meta permission (e.g. ``weight.50``)."""
    if not permission.startswith(prefix):
        return None
    try:
        return int(permission[len(prefix):])
    except ValueError:
        return None


def fetch_group_weights(connection) -> Dict[str, int]:
    """Return ``{group_name: weight}`` from ``weight.<n>`` group nodes."""
    weights: Dict[str, int] = {}
    with connection.cursor() as cursor:
        cursor.execute(GROUP_WEIGHTS_QUERY)
        for row in cursor.fetchall():
            name = str(row.get("name") or "").strip().lower()
            weight = _parse_suffix_int(str(row.get("permission") or ""), "weight.")
            if name and weight is not None:
                # Keep the highest weight if a group somehow has several.
                weights[name] = max(weight, weights.get(name, weight))
    return weights


def fetch_group_displays(connection) -> Dict[str, str]:
    """Return ``{group_name: display_name}`` from ``displayname.<label>`` group nodes."""
    displays: Dict[str, str] = {}
    with connection.cursor() as cursor:
        cursor.execute(GROUP_DISPLAYS_QUERY)
        for row in cursor.fetchall():
            name = str(row.get("name") or "").strip().lower()
            label = strip_colors(str(row.get("permission") or "")[len("displayname."):])
            if name and label:
                displays[name] = label
    return displays


def _uuid_in_values(keys: Iterable[str]) -> List[str]:
    """Expand canonical UUID keys into both dashed and undashed match candidates."""
    values: List[str] = []
    seen: Set[str] = set()
    for key in keys:
        for candidate in (key, dash_uuid(key)):
            if candidate and candidate not in seen:
                seen.add(candidate)
                values.append(candidate)
    return values


def fetch_user_groups(connection, keys: Set[str], now_s: int) -> Dict[str, Set[str]]:
    """Return ``{canonical_uuid: {group_names}}`` for the given players' group nodes."""
    result: Dict[str, Set[str]] = {}
    values = _uuid_in_values(keys)
    if not values:
        return result
    placeholders = ", ".join(["%s"] * len(values))
    query = (
        "SELECT uuid, permission FROM luckperms_user_permissions "
        "WHERE permission LIKE 'group.%%' AND value = 1 "
        "AND (expiry = 0 OR expiry > %s) "
        f"AND uuid IN ({placeholders})"
    )
    with connection.cursor() as cursor:
        cursor.execute(query, [now_s, *values])
        for row in cursor.fetchall():
            key = canonical_uuid(row.get("uuid"))
            group = str(row.get("permission") or "")[len("group."):].strip().lower()
            if key and group:
                result.setdefault(key, set()).add(group)
    return result


def fetch_primary_groups(connection, keys: Set[str]) -> Dict[str, str]:
    """Return ``{canonical_uuid: primary_group}`` as a fallback rank source."""
    result: Dict[str, str] = {}
    values = _uuid_in_values(keys)
    if not values:
        return result
    placeholders = ", ".join(["%s"] * len(values))
    query = (
        "SELECT uuid, primary_group FROM luckperms_players "
        f"WHERE uuid IN ({placeholders})"
    )
    with connection.cursor() as cursor:
        cursor.execute(query, values)
        for row in cursor.fetchall():
            key = canonical_uuid(row.get("uuid"))
            group = str(row.get("primary_group") or "").strip().lower()
            if key and group:
                result[key] = group
    return result


# --- Output -------------------------------------------------------------------

def write_atomic(path: str, snapshot: Dict[str, Any]) -> None:
    """Write the snapshot JSON atomically so nginx never reads a partial file."""
    directory = os.path.dirname(os.path.abspath(path))
    os.makedirs(directory, exist_ok=True)
    payload = json.dumps(snapshot, ensure_ascii=False, separators=(",", ":"))

    # Write to a unique temp file in the same directory (so os.replace stays atomic
    # on one filesystem) and only then swap it in. A unique name means two overlapping
    # runs never clobber each other's partial write before the rename.
    fd, tmp_path = tempfile.mkstemp(prefix=".economy.", suffix=".tmp", dir=directory)
    try:
        with os.fdopen(fd, "w", encoding="utf-8") as handle:
            handle.write(payload)
            handle.flush()
            os.fsync(handle.fileno())
        # World-readable so the web container (different uid via bind mount) can read it.
        os.chmod(tmp_path, 0o644)
        os.replace(tmp_path, path)
    except BaseException:
        # Never leave a stray temp file behind if writing/replacing failed.
        try:
            os.unlink(tmp_path)
        except OSError:
            pass
        raise


# --- Settings assembly (env > config > defaults) ------------------------------

def resolve_db_settings(db_config: Any, prefix: str, default_db: str) -> Dict[str, Any]:
    """Resolve one database connection from ``<PREFIX>_*`` env vars and a config block."""
    if not isinstance(db_config, dict):
        db_config = {}

    user = env_str(f"{prefix}_USER")
    password = env_str(f"{prefix}_PASSWORD")
    missing = [
        name
        for name, value in ((f"{prefix}_USER", user), (f"{prefix}_PASSWORD", password))
        if not value
    ]
    if missing:
        raise RuntimeError("Missing required environment variable(s): " + ", ".join(missing))

    default_db = str(db_config.get("database", default_db) or default_db)
    ssl_ca = env_str(f"{prefix}_SSL_CA", db_config.get("ssl_ca"))
    return {
        "host": env_str(f"{prefix}_HOST", str(db_config.get("host", DEFAULT_DB_HOST))),
        "port": env_int(f"{prefix}_PORT", int(db_config.get("port", DEFAULT_DB_PORT))),
        "database": env_str(f"{prefix}_DATABASE", default_db),
        "user": user,
        "password": password,
        "ssl": env_bool(f"{prefix}_SSL", as_bool(db_config.get("ssl"), True)),
        "ssl_ca": ssl_ca,
        "ssl_verify": env_bool(f"{prefix}_SSL_VERIFY", as_bool(db_config.get("ssl_verify"), True)),
        "ssl_verify_identity": env_bool(
            f"{prefix}_SSL_VERIFY_IDENTITY", as_bool(db_config.get("ssl_verify_identity"), False)
        ),
        "connect_timeout": env_int(
            f"{prefix}_CONNECT_TIMEOUT", int(db_config.get("connect_timeout_seconds", DEFAULT_CONNECT_TIMEOUT_S))
        ),
        "read_timeout": env_int(
            f"{prefix}_READ_TIMEOUT", int(db_config.get("read_timeout_seconds", DEFAULT_READ_TIMEOUT_S))
        ),
    }


def parse_args(argv: Optional[List[str]] = None) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Export the per-server richest-players board into economy.json")
    parser.add_argument(
        "--config",
        default=env_str("ECONOMY_CONFIG", DEFAULT_CONFIG_PATH),
        help="Path to config.json (default: alongside this script)",
    )
    parser.add_argument(
        "--output",
        default=None,
        help="Override the output economy.json path",
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Print the snapshot JSON to stdout instead of writing the file",
    )
    return parser.parse_args(argv)


def main(argv: Optional[List[str]] = None) -> int:
    args = parse_args(argv)

    try:
        config = load_config(args.config)
    except (OSError, ValueError, json.JSONDecodeError) as exc:
        log(f"error: failed to load config {args.config!r}: {exc}")
        return 1

    options = resolve_options(config)
    try:
        servers = resolve_servers(config)
    except ValueError as exc:
        log(f"error: invalid server configuration: {exc}")
        return 1

    output = (
        args.output
        or env_str("ECONOMY_OUTPUT")
        or str(config.get("output", DEFAULT_OUTPUT))
    )

    now_s = int(time.time())

    # --- Balances (one CMI users table per server) ----------------------------
    # Fail closed: if any configured server cannot be read we abort the whole run
    # and leave the last-good economy.json in place rather than publish a board
    # that silently dropped a server.
    server_rows: Dict[str, List[Dict[str, Any]]] = {}
    for server in servers:
        try:
            db_settings = resolve_db_settings(server["database"], server["env_prefix"], server["id"])
        except RuntimeError as exc:
            log(f"error: server {server['id']}: {exc}")
            return 1
        try:
            conn = connect(db_settings)
        except Exception as exc:  # noqa: BLE001 - report any driver/connection failure
            log(f"error: server {server['id']} database connection failed: {exc}")
            return 1
        try:
            server_rows[server["id"]] = fetch_balance_rows(conn, server, options["query_limit"])
        except Exception as exc:  # noqa: BLE001
            log(f"error: server {server['id']} balance query failed: {exc}")
            return 1
        finally:
            try:
                conn.close()
            except Exception:  # noqa: BLE001
                pass

    # --- Ranks (s4_perms / LuckPerms) ----------------------------------------
    keys: Set[str] = set()
    for rows in server_rows.values():
        for row in rows:
            keys.add(canonical_uuid(row.get("uuid")))
    keys.discard("")

    user_groups: Dict[str, Set[str]] = {}
    primary_groups: Dict[str, str] = {}
    group_weights: Dict[str, int] = {}
    group_displays: Dict[str, str] = {}
    if keys:
        try:
            luckperms_db = resolve_db_settings(
                config.get("luckperms_database"), "LUCKPERMS_RO_DB", DEFAULT_LUCKPERMS_DB_NAME
            )
        except RuntimeError as exc:
            log(f"error: {exc}")
            return 1
        try:
            perms_conn = connect(luckperms_db)
        except Exception as exc:  # noqa: BLE001
            log(f"error: LuckPerms database connection failed: {exc}")
            return 1
        try:
            group_weights = fetch_group_weights(perms_conn)
            group_displays = fetch_group_displays(perms_conn)
            user_groups = fetch_user_groups(perms_conn, keys, now_s)
            primary_groups = fetch_primary_groups(perms_conn, keys)
        except Exception as exc:  # noqa: BLE001
            log(f"error: LuckPerms query failed: {exc}")
            return 1
        finally:
            try:
                perms_conn.close()
            except Exception:  # noqa: BLE001
                pass

    server_sections: List[Dict[str, Any]] = []
    for server in servers:
        server_sections.append(
            build_server_snapshot(
                server,
                server_rows.get(server["id"], []),
                user_groups,
                primary_groups,
                group_weights,
                group_displays,
                options["display_overrides"],
                options["top_n"],
                exclude_uuids=options["exclude_uuids"],
                exclude_names=options["exclude_names"],
                min_balance=options["min_balance"],
            )
        )

    snapshot = {
        "updated": now_s,
        "generator": GENERATOR,
        "servers": server_sections,
    }

    if args.dry_run:
        print(json.dumps(snapshot, ensure_ascii=False, indent=2))
        return 0

    try:
        write_atomic(output, snapshot)
    except OSError as exc:
        log(f"error: failed to write {output!r}: {exc}")
        return 1

    total = sum(section["count"] for section in server_sections)
    log(f"wrote {output} (servers={len(server_sections)}, players={total})")
    return 0


if __name__ == "__main__":
    sys.exit(main())
