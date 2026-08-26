# economy-export

Exporter that fills the website's `economy.json` with the **richest players per
gameplay server** — Top-N by **CMI balance** plus **their highest LuckPerms rank** —
for **Survival, Mining and Skyblock** (the lobby is excluded, it has no economy).

It follows the exact pattern of [`leaderboard-export`](../leaderboard-export/README.md):
a small, strictly **read-only** exporter renders a static JSON contract that
`website/js/main.js` consumes at `/api/economy.json`, served **same-origin** by nginx.
No new Minecraft plugin and no open database port are required.

---

## The core problem: CMI stores balances locally, per server

Unlike Plan and LuckPerms — which already share the central MariaDB — **CMI is the
economy provider but keeps each server's balances in its own storage**, SQLite by
default (`<server>/plugins/CMI/Settings/DataBaseInfo.yml` → `storage.method: sqlite`).
There is no shared economy database to `SELECT` from, so the `leaderboard-export`
approach does not work out of the box.

**This exporter uses Option A (architecture-consistent):** each gameplay server's CMI
is switched to **MySQL storage writing to its OWN database** on the shared MariaDB, and
this exporter reads one CMI users table **per server**. CMI explicitly warns that two
servers must **never** share the same table (`DON'T USE SAME DATABASE TABLES FOR MORE
THEN ONE SERVER`), so each server gets a distinct database (e.g. `s5_cmi_survival`,
`s5_cmi_mining`, `s5_cmi_skyblock`).

### Enabling CMI MySQL storage per server (already wired in this repo)

Each gameplay server's `<server>/plugins/CMI/Settings/DataBaseInfo.yml` is committed
with `method: MySQL` pointing at its **own** database, and the read/write credentials
are injected at deploy (never committed):

| Server (folder) | Website id | CMI database      | RW secret (deploy)       | Placeholders injected |
|-----------------|------------|-------------------|--------------------------|-----------------------|
| `survival`      | `survival` | `s5_cmi_survival` | `CMI_SURVIVAL_RW_DB_ENV` | `__CMI_SURVIVAL_RW_DB_USER__` / `__CMI_SURVIVAL_RW_DB_PASSWORD__` |
| `rpg`           | `mining`   | `s5_cmi_mining`   | `CMI_MINING_RW_DB_ENV`   | `__CMI_MINING_RW_DB_USER__` / `__CMI_MINING_RW_DB_PASSWORD__` |
| `skyblock`      | `skyblock` | `s5_cmi_skyblock` | `CMI_SKYBLOCK_RW_DB_ENV` | `__CMI_SKYBLOCK_RW_DB_USER__` / `__CMI_SKYBLOCK_RW_DB_PASSWORD__` |

The committed `DataBaseInfo.yml` therefore looks like this (survival shown):

```yaml
storage:
  method: MySQL          # was: sqlite
mysql:
  username: '__CMI_SURVIVAL_RW_DB_USER__'      # injected at deploy (NOT the exporter's RO user)
  password: '__CMI_SURVIVAL_RW_DB_PASSWORD__'  # injected at deploy; never commit real creds
  hostname: 172.25.0.1:3306
  database: s5_cmi_survival                     # a DEDICATED database for THIS server only
  tablePrefix: CMI_                             # → users table becomes CMI_users
  useSSL: true
```

The `deploy-survival` / `deploy-rpg` / `deploy-skyblock` workflows parse the matching
`CMI_*_RW_DB_ENV` secret in Python and substitute the placeholders verbatim (special
characters in the password are safe). The lobby has no economy and stays on SQLite.
Give each server its **own** `database:` (never a shared one). CMI creates its tables
(`CMI_users`, …) on first start and performs the one-time migration of existing
SQLite balances. The users table default is `CMI_users` with columns `player_uuid`
(UUID), `username` (name) and `Balance` (DOUBLE) — all configurable per server in
`config.json` because CMI's exact schema varies by version.

> Keep CMI's own read/write user (`CMI_*_RW_DB_ENV`) separate from this exporter's
> read-only user (`CMI_*_DB_ENV`) — least privilege. This exporter only ever runs
> `SELECT`. See [`docs/infrastructure/DATENBANKEN.md`](../../docs/infrastructure/DATENBANKEN.md).

---

## What it produces

```json
{
  "updated": 1723980000,
  "generator": "economy-export",
  "servers": [
    {
      "id": "survival",
      "label": "Survival",
      "currency": "€",
      "count": 3,
      "players": [
        { "rank": 1, "name": "Festas",     "balance": 2450300.75, "group": "owner", "groupDisplay": "Owner", "groupWeight": 1000 },
        { "rank": 2, "name": "BuilderBen", "balance": 1320000.0,  "group": "admin", "groupDisplay": "Admin", "groupWeight": 900 },
        { "rank": 3, "name": "FarmerFritz","balance": 845200.5,   "group": "vip",   "groupDisplay": "VIP",   "groupWeight": 100 }
      ]
    },
    { "id": "mining",   "label": "Mining",   "currency": "€", "count": 0, "players": [] },
    { "id": "skyblock", "label": "Skyblock", "currency": "€", "count": 0, "players": [] }
  ]
}
```

- `updated` = script run time in **seconds** (keeps the frontend staleness check green).
- `servers[]` is emitted in `config.json` order; the frontend renders one **tab** per
  server and picks the first one that has players.
- Within each server, `players[]` is sorted by `balance` **descending**; ties break by
  name. `rank` is the 1-based position; the frontend renders 🥇🥈🥉 for the top 3.
- `balance` is a **number** (rounded to 2 decimals). Balances `<= 0`, `NaN`/`inf` are
  skipped. The frontend formats it with `de-DE` grouping and appends `currency`.
- `group` / `groupDisplay` / `groupWeight` come from LuckPerms exactly like
  `leaderboard-export`. A player without any rank has `group: null`.

---

## The queries

**Balance (per server, CMI users table):**

```sql
SELECT player_uuid AS uuid,
       username     AS name,
       Balance      AS balance
FROM CMI_users
WHERE Balance IS NOT NULL
ORDER BY Balance DESC
LIMIT :query_limit;         -- bounded; we only need the top handful
```

The table and column identifiers come from each server's `config.json` entry and are
validated against a strict `^[A-Za-z0-9_]+$` allowlist **before** they are interpolated
(only `LIMIT` is a bound parameter), so a mis-configured name can never inject SQL.

**Highest rank (`s4_perms`, LuckPerms table-prefix `luckperms_`):** identical to
`leaderboard-export` — group weights + display names are fetched whole, the players'
current group memberships are fetched bounded by the top-N UUIDs (expired temporary
groups skipped), with `primary_group` as fallback. Merging happens in Python by a
**canonical UUID key** (hex-only, lower-cased) so it matches regardless of
dashed/undashed storage; the highest-`weight` group wins (ties break alphabetically).

---

## Configuration

Non-secret settings live in [`config.json`](config.json) (committed):

- `top_n` — how many players per server to show (default `10`).
- `query_limit` — how many rows to pull from each CMI table before filtering
  (default `200`; never less than `top_n`).
- `min_balance` — optional; hide players below this balance. `null` = show everyone
  with a positive balance.
- `exclude_uuids` / `exclude_names` — opt-out lists (matched case-insensitively,
  applied to every server).
- `group_display_overrides` — map a raw group name to a custom label, e.g.
  `{ "default": "Spieler" }`.
- `luckperms_database` — non-secret host/port/database/ssl/timeout defaults for the
  shared rank source (`s4_perms`).
- `servers[]` — one entry per gameplay server (lobby excluded), each with:
  - `id` (`survival`/`mining`/`skyblock`), `label`, `currency` (default `€`).
  - `database` — non-secret host/port/database/ssl for **this server's** CMI DB.
  - `table` / `uuid_column` / `name_column` / `balance_column` — CMI schema
    (defaults `CMI_users` / `player_uuid` / `username` / `Balance`).
  - `env_prefix` — optional; overrides the credential env prefix (default derived from
    the id, e.g. `survival` → `CMI_SURVIVAL_DB`).
- `output` — target `economy.json` path.

Secret DB credentials come from the environment (never committed) — see
[`economy-export.env.example`](economy-export.env.example). Environment variables and
`--config`/`--output` override `config.json`. `ECONOMY_OUTPUT` overrides `output`.

Per-server balance credentials use the prefix `CMI_<ID>_DB_*` (e.g.
`CMI_SURVIVAL_DB_USER` / `CMI_SURVIVAL_DB_PASSWORD`); the LuckPerms rank source uses
`LUCKPERMS_RO_DB_*`. Optional per-connection overrides (`_HOST`, `_PORT`, `_DATABASE`,
`_SSL`, `_SSL_CA`, `_SSL_VERIFY`, `_CONNECT_TIMEOUT`, `_READ_TIMEOUT`) are supported.

---

## Read-only database users (least privilege)

One **dedicated read-only** MariaDB user per CMI database plus the shared LuckPerms RO
user. Do **not** reuse CMI's read/write users (see `docs/infrastructure/DATENBANKEN.md`).

```sql
-- One SELECT-only user per server's CMI database:
CREATE USER 'cmi_survival_ro'@'%' IDENTIFIED BY 'CHANGE_ME';
GRANT SELECT ON s5_cmi_survival.CMI_users TO 'cmi_survival_ro'@'%';
CREATE USER 'cmi_mining_ro'@'%'   IDENTIFIED BY 'CHANGE_ME';
GRANT SELECT ON s5_cmi_mining.CMI_users   TO 'cmi_mining_ro'@'%';
CREATE USER 'cmi_skyblock_ro'@'%' IDENTIFIED BY 'CHANGE_ME';
GRANT SELECT ON s5_cmi_skyblock.CMI_users TO 'cmi_skyblock_ro'@'%';

-- Rank source (reuses the existing LUCKPERMS_RO_DB_ENV credential from leaderboard-export):
GRANT SELECT ON s4_perms.luckperms_players           TO 'luckperms_ro'@'%';
GRANT SELECT ON s4_perms.luckperms_user_permissions  TO 'luckperms_ro'@'%';
GRANT SELECT ON s4_perms.luckperms_group_permissions TO 'luckperms_ro'@'%';
FLUSH PRIVILEGES;
```

MariaDB stays Docker-internal (no external port); keep `useSSL: true` (the script
negotiates TLS, and verifies the certificate when a `*_SSL_CA` is provided).

> **Privacy:** the board publishes player **names** (the point of the feature). Use
> `exclude_names`/`exclude_uuids` for opt-outs, or `min_balance` to hide small balances.

---

## Local usage

```bash
python3 -m venv venv
venv/bin/pip install -r requirements.txt

export CMI_SURVIVAL_DB_USER=cmi_survival_ro CMI_SURVIVAL_DB_PASSWORD=...  # do not commit
export CMI_MINING_DB_USER=cmi_mining_ro     CMI_MINING_DB_PASSWORD=...    # do not commit
export CMI_SKYBLOCK_DB_USER=cmi_skyblock_ro CMI_SKYBLOCK_DB_PASSWORD=...  # do not commit
export LUCKPERMS_RO_DB_USER=luckperms_ro    LUCKPERMS_RO_DB_PASSWORD=...  # do not commit

# Print the snapshot without writing the file:
venv/bin/python economy_export.py --config config.json --dry-run

# Write economy.json (path from config.json, or override with --output):
venv/bin/python economy_export.py --config config.json \
    --output /home/deploy/minecraft-website/data/economy.json

# Unit tests (no database needed):
python3 -m unittest discover -s tools/economy-export
```

On a database error the script **fails closed**: it logs, exits non-zero and does
**not** overwrite the last-good file, so a single server outage never wipes the board.
A persistent outage lets the snapshot age past the frontend staleness threshold.

---

## Deployment (host + systemd timer)

The exporter must reach MariaDB (`172.25.0.1:3306`) **and** write into
`/home/deploy/minecraft-website/data/` (the read-only bind mount serving
`/api/economy.json`).

`.github/workflows/deploy-economy-export.yml` automates this: it copies this folder to
the host, creates a virtualenv, installs the DB credentials from the
**`CMI_SURVIVAL_DB_ENV` + `CMI_MINING_DB_ENV` + `CMI_SKYBLOCK_DB_ENV` +
`LUCKPERMS_RO_DB_ENV`** secrets into a single `chmod 600` env file, installs the systemd
units and enables the timer. Canonical install layout:

| Path | Purpose |
|------|---------|
| `/opt/economy-export/` | script, `config.json`, `venv/` |
| `/opt/economy-export/economy-export.env` | DB creds (`chmod 600`) |
| `/etc/systemd/system/economy-export.{service,timer}` | scheduler |
| `/home/deploy/minecraft-website/data/economy.json` | output |

Manual install (equivalent to the workflow):

```bash
sudo mkdir -p /opt/economy-export
sudo cp economy_export.py config.json /opt/economy-export/
python3 -m venv /opt/economy-export/venv
/opt/economy-export/venv/bin/pip install -r requirements.txt

sudo install -m 600 economy-export.env.example \
    /opt/economy-export/economy-export.env
sudo "$EDITOR" /opt/economy-export/economy-export.env   # set every user/password pair

sudo cp economy-export.service economy-export.timer /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable --now economy-export.timer
```

The timer runs every **5 min** (balances change slowly; CMI flushes on its own
`AutoSaveInterval`). Adjust the service `User=`/`Group=` to whoever owns the `data/`
directory if it is not `deploy`.

---

## Verification

```bash
# 1. Local file is valid contract JSON and freshly updated:
cat /home/deploy/minecraft-website/data/economy.json | jq .

# 2. Served + rendered on the site:
curl -s https://mc.festas-builds.com/api/economy.json | jq .
#    Section "Reichste Spieler" updates; server tabs switch between boards.

# systemd:
systemctl status economy-export.timer
journalctl -u economy-export.service -n 20
```
