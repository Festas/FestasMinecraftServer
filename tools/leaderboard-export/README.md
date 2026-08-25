# leaderboard-export

Exporter that fills the website's `leaderboard.json` with the **Top-N players by
playtime** and **their highest LuckPerms rank**, read straight from the shared
MariaDB.

It follows the exact pattern of [`plan-players-export`](../plan-players-export/README.md):
a small, strictly **read-only** exporter renders a static JSON contract that
`website/js/main.js` consumes at `/api/leaderboard.json`, served **same-origin** by
nginx. No new Minecraft plugin and no open database port are required.

---

## Why these two databases (and their limits)

The leaderboard merges **two** sources on the same MariaDB host (`172.25.0.1`), each
read through its **own** SELECT-only user (least privilege):

| Field | Source DB | How |
|-------|-----------|-----|
| Playtime | `s4_plan` (Plan) | `SUM(plan_sessions.session_end − session_start)` per player, joined to `plan_users` for UUID → name |
| Highest rank | `s4_perms` (LuckPerms) | group with the greatest `weight` among the player's `group.<name>` nodes; `primary_group` as fallback |
| Join key | both | the player **UUID** (present in `plan_users.uuid` and `luckperms_players.uuid`) |

**Consequences / limits (by design):**

- The **running** session is only counted once it *ends* — Plan keeps active
  sessions in RAM (`plan_sessions` only receives finished sessions), exactly like
  the live player counter.
- Playtime is **network-wide** (summed across all Plan servers) and **includes AFK
  time**, matching Plan's own "Playtime" figure.
- Skyblock uses **SQLite** for Plan, so skyblock-only playtime does not appear in
  `s4_plan`. Players are still listed via their network-wide sessions on the other
  servers.
- A leaderboard naturally shows **names** (unlike the counter's `showNames:false`).
  Optional `exclude_uuids` / `exclude_names` and a `min_weight` filter are available
  (see Configuration).

---

## What it produces

```json
{
  "updated": 1723980000,
  "generator": "leaderboard-export",
  "count": 3,
  "players": [
    { "rank": 1, "name": "Festas",     "playtimeSeconds": 1360800, "group": "owner", "groupDisplay": "Owner",     "groupWeight": 1000 },
    { "rank": 2, "name": "BuilderBen", "playtimeSeconds": 734400,  "group": "mod",   "groupDisplay": "Moderator", "groupWeight": 500 },
    { "rank": 3, "name": "Alex",       "playtimeSeconds": 486000,  "group": "vip",   "groupDisplay": "VIP",       "groupWeight": 100 }
  ]
}
```

- `updated` = script run time in **seconds** (keeps the frontend staleness check green).
- `players[]` is sorted by `playtimeSeconds` **descending**; ties break by name.
- `rank` is the 1-based position; the frontend renders 🥇🥈🥉 for the top 3.
- `group` is the internal LuckPerms group name; `groupDisplay` is the resolved label
  (LuckPerms `displayname.<x>` meta, a `group_display_overrides` entry, or a
  prettified group name); `groupWeight` is the numeric weight. A player without any
  rank has `group: null`.
- The frontend uses `formatDuration()` to turn `playtimeSeconds` into `15d 2h 30m`.

---

## The queries

**Playtime (`s4_plan`):**

```sql
SELECT
    u.uuid                               AS uuid,
    u.name                               AS name,
    SUM(s.session_end - s.session_start) AS playtime_ms   -- Unix ms deltas
FROM plan_users u
JOIN plan_sessions s
  ON s.user_id = u.id
 AND s.session_end >= s.session_start   -- ignore malformed/unfinished rows
GROUP BY u.id, u.uuid, u.name
ORDER BY playtime_ms DESC, u.name ASC
LIMIT :query_limit;                     -- bounded; we only need the top handful
```

**Highest rank (`s4_perms`, LuckPerms table-prefix `luckperms_`):**

```sql
-- group weights + display names (tiny; fetched whole)
SELECT name, permission FROM luckperms_group_permissions WHERE permission LIKE 'weight.%'      AND value = 1;
SELECT name, permission FROM luckperms_group_permissions WHERE permission LIKE 'displayname.%' AND value = 1;

-- the players' current group memberships (bounded by the top-N UUIDs)
SELECT uuid, permission FROM luckperms_user_permissions
WHERE permission LIKE 'group.%' AND value = 1
  AND (expiry = 0 OR expiry > :now_seconds)     -- skip expired temporary groups
  AND uuid IN (:uuids);

-- fallback rank for players with no group node
SELECT uuid, primary_group FROM luckperms_players WHERE uuid IN (:uuids);
```

Merging happens in Python by a **canonical UUID key** (hex-only, lower-cased) so it
matches regardless of dashed/undashed storage. For each player the group with the
highest `weight` wins (ties break alphabetically for determinism).

---

## Configuration

Non-secret settings live in [`config.json`](config.json) (committed):

- `top_n` — how many players to show (default `10`).
- `query_limit` — how many rows to pull from Plan before filtering (default `200`;
  never less than `top_n`). Over-fetching leaves room for `exclude_*`/`min_weight`.
- `min_weight` — optional; hide players whose highest rank weight is below this
  (e.g. only staff). `null` = show everyone.
- `exclude_uuids` / `exclude_names` — opt-out lists (matched case-insensitively).
- `group_display_overrides` — map a raw group name to a custom label, e.g.
  `{ "default": "Spieler" }`.
- `plan_database` / `luckperms_database` — non-secret host/port/database/ssl/timeout
  defaults for the two connections. `connect_timeout_seconds` (default `5`) bounds the
  handshake; `read_timeout_seconds` (default `30`) bounds each query and is larger so
  the playtime aggregation over a big `plan_sessions` history is not killed early.
- `output` — target `leaderboard.json` path.

Secret DB credentials come from the environment (never committed) — see
[`leaderboard-export.env.example`](leaderboard-export.env.example). Environment
variables and `--config`/`--output` override `config.json`.

---

## Read-only database users (least privilege)

Two **dedicated read-only** MariaDB users, one per database. Do **not** reuse the
plugins' read/write users (see `docs/infrastructure/DATENBANKEN.md`).

```sql
-- Playtime source (reuses the existing PLAN_RO_DB_ENV credential). The plan_ro user
-- from plan-players-export only had SELECT on plan_servers + plan_tps; the
-- leaderboard also needs plan_sessions + plan_users:
GRANT SELECT ON s4_plan.plan_sessions TO 'plan_ro'@'%';
GRANT SELECT ON s4_plan.plan_users    TO 'plan_ro'@'%';

-- Rank source (new LUCKPERMS_RO_DB_ENV credential):
CREATE USER 'luckperms_ro'@'%' IDENTIFIED BY 'CHANGE_ME';
GRANT SELECT ON s4_perms.luckperms_players           TO 'luckperms_ro'@'%';
GRANT SELECT ON s4_perms.luckperms_user_permissions  TO 'luckperms_ro'@'%';
GRANT SELECT ON s4_perms.luckperms_group_permissions TO 'luckperms_ro'@'%';
FLUSH PRIVILEGES;
```

MariaDB stays Docker-internal (no external port); keep `useSSL: true` (the script
negotiates TLS, and verifies the certificate when a `*_SSL_CA` is provided).

> **Privacy:** the leaderboard publishes player **names** (the point of the feature).
> Use `exclude_names`/`exclude_uuids` for opt-outs, or `min_weight` to restrict it to
> ranked players only.

---

## Local usage

```bash
python3 -m venv venv
venv/bin/pip install -r requirements.txt

export PLAN_RO_DB_USER=plan_ro          PLAN_RO_DB_PASSWORD=...        # do not commit
export LUCKPERMS_RO_DB_USER=luckperms_ro LUCKPERMS_RO_DB_PASSWORD=...  # do not commit

# Print the snapshot without writing the file:
venv/bin/python leaderboard_export.py --config config.json --dry-run

# Write leaderboard.json (path from config.json, or override with --output):
venv/bin/python leaderboard_export.py --config config.json \
    --output /home/deploy/minecraft-website/data/leaderboard.json

# Unit tests (no database needed):
python3 -m unittest discover -s tools/leaderboard-export
```

On a database error the script **fails closed**: it logs, exits non-zero and does
**not** overwrite the last-good file. A persistent outage lets the snapshot age past
the frontend staleness threshold (or the nginx fallback serves an empty
`{"players":[]}` if the file never existed).

---

## Deployment (host + systemd timer)

The exporter must reach MariaDB (`172.25.0.1:3306`) **and** write into
`/home/deploy/minecraft-website/data/` (the read-only bind mount serving
`/api/leaderboard.json`).

`.github/workflows/deploy-leaderboard-export.yml` automates this: it copies this
folder to the host, creates a virtualenv, installs the DB credentials from the
**`PLAN_RO_DB_ENV` + `LUCKPERMS_RO_DB_ENV`** secrets into a single `chmod 600` env
file, installs the systemd units and enables the timer. Canonical install layout:

| Path | Purpose |
|------|---------|
| `/opt/leaderboard-export/` | script, `config.json`, `venv/` |
| `/opt/leaderboard-export/leaderboard-export.env` | DB creds (`chmod 600`) |
| `/etc/systemd/system/leaderboard-export.{service,timer}` | scheduler |
| `/home/deploy/minecraft-website/data/leaderboard.json` | output |

Manual install (equivalent to the workflow):

```bash
sudo mkdir -p /opt/leaderboard-export
sudo cp leaderboard_export.py config.json /opt/leaderboard-export/
python3 -m venv /opt/leaderboard-export/venv
/opt/leaderboard-export/venv/bin/pip install -r requirements.txt

sudo install -m 600 leaderboard-export.env.example \
    /opt/leaderboard-export/leaderboard-export.env
sudo "$EDITOR" /opt/leaderboard-export/leaderboard-export.env   # set both user/password pairs

sudo cp leaderboard-export.service leaderboard-export.timer /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable --now leaderboard-export.timer
```

The timer runs every **5 min** (a leaderboard changes slowly; no 45 s freshness
needed). Adjust the service `User=`/`Group=` to whoever owns the `data/` directory
if it is not `deploy`.

---

## Verification

```bash
# 1. Local file is valid contract JSON and freshly updated:
cat /home/deploy/minecraft-website/data/leaderboard.json | jq .

# 2. Served + rendered on the site:
curl -s https://mc.festas-builds.com/api/leaderboard.json | jq .
#    Section "Bestenliste" updates; stopping the timer falls back to the
#    nginx inline empty snapshot.

# systemd:
systemctl status leaderboard-export.timer
journalctl -u leaderboard-export.service -n 20
```
