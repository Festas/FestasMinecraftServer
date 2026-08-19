#!/usr/bin/env python3
"""Export live player counts per server from the Plan database into players.json.

This is "Option A" from the players.json plan: the exporter reads the newest
``plan_tps`` row per game server (Plan writes one row per server per minute with
the column ``players_online``) and renders the JSON contract that the website
frontend (``website/js/main.js``) already consumes at ``/api/players.json``.

Deliberate limits (see README.md):

* Only player **counts** per server – Plan keeps active sessions in RAM, so the
  database exposes neither live player names nor a live world breakdown.
* Latency is up to ~2 minutes and ``players_online`` is the *maximum* within the
  minute window, matching Plan's own dashboard logic.
* Servers without a Plan installation yet (Mining, Skyblock) are emitted as
  ``online:false, count:0`` so the frontend cards stay stable.

The script is intentionally dependency-light (only PyMySQL) and writes the output
file atomically (temp file + ``os.replace``) so nginx never serves a half-written
file. It is meant to be run once per invocation from a systemd timer / cron.
"""

from __future__ import annotations

import argparse
import json
import os
import sys
import time
from typing import Any, Dict, List, Optional

try:
    import pymysql
    import pymysql.cursors
except ImportError:  # pragma: no cover - handled at runtime with a clear message
    pymysql = None


# --- Defaults (can be overridden via config.json, environment or CLI) ---------

DEFAULT_CONFIG_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "config.json")
DEFAULT_OUTPUT = "/home/deploy/minecraft-website/data/players.json"
DEFAULT_DB_HOST = "172.25.0.1"
DEFAULT_DB_PORT = 3306
DEFAULT_DB_NAME = "s4_plan"
DEFAULT_LIVE_THRESHOLD_S = 120
DEFAULT_CONNECT_TIMEOUT_S = 5

# Newest TPS row per non-proxy, installed server. ``players_online`` is Plan's own
# "current players" source. The proxy row (is_proxy = 1) is excluded so the network
# total is not double counted. See README.md for the full explanation.
TPS_QUERY = """
SELECT
    s.uuid           AS server_uuid,
    s.name           AS server_name,
    t.players_online AS players_online,
    t.date           AS tps_epoch_ms
FROM plan_servers s
JOIN plan_tps t
  ON t.server_id = s.id
 AND t.date = (
        SELECT MAX(t2.date) FROM plan_tps t2 WHERE t2.server_id = s.id
     )
WHERE s.is_proxy = 0
  AND s.is_installed = 1
ORDER BY s.name
"""


def log(message: str) -> None:
    """Write a timestamped log line to stderr (captured by the systemd journal)."""
    stamp = time.strftime("%Y-%m-%dT%H:%M:%S%z")
    print(f"{stamp} plan-players-export: {message}", file=sys.stderr, flush=True)


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


def normalize_uuid(value: Any) -> str:
    """Return a comparable UUID string (lower-cased, trimmed) or ``""``."""
    if not value:
        return ""
    return str(value).strip().lower()


def coerce_count(value: Any) -> int:
    """Return a non-negative integer count, tolerating None / floats / strings."""
    try:
        number = int(value)
    except (TypeError, ValueError):
        return 0
    return number if number > 0 else 0


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


def resolve_servers(config: Dict[str, Any]) -> List[Dict[str, Any]]:
    """Return the ordered list of website server keys with UUID + max.

    Falls back to the four known website keys when the config omits ``servers``.
    """
    raw_servers = config.get("servers")
    if not isinstance(raw_servers, list) or not raw_servers:
        raw_servers = [
            {"key": "lobby", "uuid": None, "max": 40},
            {"key": "survival", "uuid": None, "max": 40},
            {"key": "mining", "uuid": None, "max": 20},
            {"key": "skyblock", "uuid": None, "max": 20},
        ]

    servers: List[Dict[str, Any]] = []
    seen = set()
    for entry in raw_servers:
        if not isinstance(entry, dict):
            continue
        key = str(entry.get("key", "")).strip()
        if not key or key in seen:
            continue
        seen.add(key)
        try:
            max_players = int(entry.get("max")) if entry.get("max") is not None else 0
        except (TypeError, ValueError):
            max_players = 0
        servers.append(
            {
                "key": key,
                "uuid": normalize_uuid(entry.get("uuid")),
                "max": max_players if max_players > 0 else 0,
            }
        )
    return servers


# --- Core transformation (pure, unit-testable) --------------------------------

def build_snapshot(
    rows: List[Dict[str, Any]],
    servers: List[Dict[str, Any]],
    now_ms: int,
    live_threshold_ms: int,
    show_names: bool = False,
) -> Dict[str, Any]:
    """Turn ``plan_tps`` query rows into the players.json contract.

    ``rows`` are dict rows with ``server_uuid``/``players_online``/``tps_epoch_ms``.
    Returns the full snapshot dict ready to be serialised.
    """
    # Deduplicate by server UUID, keeping the newest TPS row (guards against the
    # theoretical case of several rows sharing the same MAX(date)).
    newest_by_uuid: Dict[str, Dict[str, int]] = {}
    for row in rows:
        uuid = normalize_uuid(row.get("server_uuid"))
        if not uuid:
            continue
        date_ms = coerce_count(row.get("tps_epoch_ms"))
        previous = newest_by_uuid.get(uuid)
        if previous is None or date_ms > previous["tps_epoch_ms"]:
            newest_by_uuid[uuid] = {
                "players_online": coerce_count(row.get("players_online")),
                "tps_epoch_ms": date_ms,
            }

    now_s = now_ms // 1000
    server_entries: List[Dict[str, Any]] = []
    total_online = 0
    total_max = 0

    for server in servers:
        uuid = server["uuid"]
        max_players = server["max"]
        total_max += max_players

        data = newest_by_uuid.get(uuid) if uuid else None
        if data is not None:
            is_live = data["tps_epoch_ms"] > (now_ms - live_threshold_ms)
            count = data["players_online"] if is_live else 0
            updated = data["tps_epoch_ms"] // 1000
        else:
            # No Plan data (server without Plan yet, or no TPS row at all).
            is_live = False
            count = 0
            updated = now_s

        total_online += count

        entry: Dict[str, Any] = {
            "name": server["key"],
            "online": is_live,
            "count": count,
            "updated": updated,
            "players": [],
        }
        if max_players > 0:
            entry["max"] = max_players
        server_entries.append(entry)

    return {
        "online": total_online,
        "max": total_max,
        "updated": now_s,
        "showNames": bool(show_names),
        "generator": "plan-players-export",
        "servers": server_entries,
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
        "read_timeout": db_settings["connect_timeout"],
        "charset": "utf8mb4",
        "cursorclass": pymysql.cursors.DictCursor,
        "autocommit": True,
    }

    # SSL handling mirrors Plan's own "useSSL: true". When a CA file is supplied we
    # verify the certificate; otherwise PyMySQL negotiates TLS in PREFERRED mode
    # (encrypt if the server offers it) without pinning a self-signed internal CA.
    if db_settings["ssl"]:
        ca = db_settings.get("ssl_ca")
        if ca:
            connect_kwargs["ssl_ca"] = ca
            connect_kwargs["ssl_verify_cert"] = db_settings.get("ssl_verify", True)
            connect_kwargs["ssl_verify_identity"] = db_settings.get("ssl_verify_identity", False)
    else:
        connect_kwargs["ssl_disabled"] = True

    return pymysql.connect(**connect_kwargs)


def fetch_tps_rows(connection) -> List[Dict[str, Any]]:
    """Run the newest-TPS-per-server query and return the rows."""
    with connection.cursor() as cursor:
        cursor.execute(TPS_QUERY)
        return list(cursor.fetchall())


# --- Output -------------------------------------------------------------------

def write_atomic(path: str, snapshot: Dict[str, Any]) -> None:
    """Write the snapshot JSON atomically so nginx never reads a partial file."""
    directory = os.path.dirname(os.path.abspath(path))
    os.makedirs(directory, exist_ok=True)
    tmp_path = f"{path}.tmp"
    payload = json.dumps(snapshot, ensure_ascii=False, separators=(",", ":"))

    with open(tmp_path, "w", encoding="utf-8") as handle:
        handle.write(payload)
        handle.flush()
        os.fsync(handle.fileno())
    # World-readable so the web container (different uid via bind mount) can read it.
    os.chmod(tmp_path, 0o644)
    os.replace(tmp_path, path)


# --- Settings assembly (CLI > env > config > defaults) ------------------------

def resolve_db_settings(config: Dict[str, Any]) -> Dict[str, Any]:
    db_config = config.get("database") if isinstance(config.get("database"), dict) else {}

    user = env_str("PLAN_RO_DB_USER")
    password = env_str("PLAN_RO_DB_PASSWORD")
    missing = [name for name, value in (("PLAN_RO_DB_USER", user), ("PLAN_RO_DB_PASSWORD", password)) if not value]
    if missing:
        raise RuntimeError("Missing required environment variable(s): " + ", ".join(missing))

    ssl_ca = env_str("PLAN_RO_DB_SSL_CA", db_config.get("ssl_ca"))
    return {
        "host": env_str("PLAN_RO_DB_HOST", str(db_config.get("host", DEFAULT_DB_HOST))),
        "port": env_int("PLAN_RO_DB_PORT", int(db_config.get("port", DEFAULT_DB_PORT))),
        "database": env_str("PLAN_RO_DB_DATABASE", str(db_config.get("database", DEFAULT_DB_NAME))),
        "user": user,
        "password": password,
        "ssl": env_bool("PLAN_RO_DB_SSL", as_bool(db_config.get("ssl"), True)),
        "ssl_ca": ssl_ca,
        "ssl_verify": env_bool("PLAN_RO_DB_SSL_VERIFY", as_bool(db_config.get("ssl_verify"), True)),
        "ssl_verify_identity": env_bool(
            "PLAN_RO_DB_SSL_VERIFY_IDENTITY", as_bool(db_config.get("ssl_verify_identity"), False)
        ),
        "connect_timeout": env_int(
            "PLAN_RO_DB_CONNECT_TIMEOUT", int(db_config.get("connect_timeout_seconds", DEFAULT_CONNECT_TIMEOUT_S))
        ),
    }


def parse_args(argv: Optional[List[str]] = None) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Export Plan player counts into players.json")
    parser.add_argument(
        "--config",
        default=env_str("PLAN_PLAYERS_CONFIG", DEFAULT_CONFIG_PATH),
        help="Path to config.json (default: alongside this script)",
    )
    parser.add_argument(
        "--output",
        default=None,
        help="Override the output players.json path",
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

    servers = resolve_servers(config)
    if not servers:
        log("error: no servers configured")
        return 1

    output = (
        args.output
        or env_str("PLAN_PLAYERS_OUTPUT")
        or str(config.get("output", DEFAULT_OUTPUT))
    )
    show_names = as_bool(config.get("show_names"), False)
    live_threshold_s = int(config.get("live_threshold_seconds", DEFAULT_LIVE_THRESHOLD_S))
    live_threshold_ms = live_threshold_s * 1000

    try:
        db_settings = resolve_db_settings(config)
    except RuntimeError as exc:
        log(f"error: {exc}")
        return 1

    try:
        connection = connect(db_settings)
    except Exception as exc:  # noqa: BLE001 - report any driver/connection failure
        # Fail closed: do not overwrite the last-good file on a transient DB error.
        # A persistent outage lets the snapshot age past the frontend staleness
        # threshold (or the nginx fallback kicks in if the file never existed).
        log(f"error: database connection failed: {exc}")
        return 1

    try:
        rows = fetch_tps_rows(connection)
    except Exception as exc:  # noqa: BLE001
        log(f"error: query failed: {exc}")
        return 1
    finally:
        try:
            connection.close()
        except Exception:  # noqa: BLE001
            pass

    now_ms = int(time.time() * 1000)
    snapshot = build_snapshot(rows, servers, now_ms, live_threshold_ms, show_names)

    if args.dry_run:
        print(json.dumps(snapshot, ensure_ascii=False, indent=2))
        return 0

    try:
        write_atomic(output, snapshot)
    except OSError as exc:
        log(f"error: failed to write {output!r}: {exc}")
        return 1

    log(
        f"wrote {output} (online={snapshot['online']}, "
        f"servers={len(snapshot['servers'])})"
    )
    return 0


if __name__ == "__main__":
    sys.exit(main())
