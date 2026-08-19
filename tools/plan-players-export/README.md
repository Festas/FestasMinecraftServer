# plan-players-export

Exporter that fills the website's `players.json` with **live player counts per
server**, read straight from the **Plan** (Player Analytics) database.

This implements **Option A** of the "live players per server" plan: it polls the
newest `plan_tps` row per game server and renders the exact JSON contract that
`website/js/main.js` already consumes at `/api/players.json`. No new Minecraft
plugin is required and the database access is strictly read-only.

---

## Why the Plan database (and its limits)

Plan does **not** store "currently online" as a session — active sessions live
only in RAM (`SessionCache.ACTIVE_SESSIONS`); `plan_sessions` only receives
**finished** sessions. So the database exposes **no live player names and no live
world breakdown**.

The reliable live number lives in **`plan_tps`**: one row per server per minute,
column `players_online`. Plan's own dashboard uses exactly "the newest `plan_tps`
row, if it is < 2 minutes old" (`PlayersOnlineCurrent.java`). `players_online` is
the **maximum** within that minute window.

**Consequences / limits (by design):**

| Aspect | Result |
|--------|--------|
| Counts per server | ✅ yes |
| Live names / worlds | ❌ no (`showNames: false`, no `worlds[]`) |
| Real server uptime | ❌ not in the DB (cards show `Uptime: —`) |
| Latency | ≤ ~2 min, "max per minute" |

If you later want names/worlds or true real-time data, a **Velocity proxy writer**
is the superior source. Because the JSON contract is identical, you can switch to
or combine it **without touching the frontend**.

---

## What it produces

A snapshot matching the documented contract
(`website/SERVERSTATUS-ONLINE-SPIELER-GUIDE.md`):

```json
{
  "online": 3,
  "max": 120,
  "updated": 1723980000,
  "showNames": false,
  "generator": "plan-players-export",
  "servers": [
    { "name": "lobby",    "online": true,  "count": 3, "updated": 1723979970, "players": [], "max": 40 },
    { "name": "survival", "online": false, "count": 0, "updated": 1723980000, "players": [], "max": 40 },
    { "name": "mining",   "online": false, "count": 0, "updated": 1723980000, "players": [], "max": 20 },
    { "name": "skyblock", "online": false, "count": 0, "updated": 1723980000, "players": [], "max": 20 }
  ]
}
```

- `servers[].online` = `is_live` (TPS row younger than the live threshold) — set
  explicitly as a boolean so the frontend respects it.
- `servers[].count` = `players_online` (0 when not live).
- `servers[].updated` = TPS timestamp in **seconds**; top-level `updated` = the
  script run time ("now"), which keeps the frontend's 90 s staleness check green
  even though individual TPS rows can be up to ~2 min old.
- Servers without a Plan installation yet (Mining, Skyblock) are always emitted as
  `online:false, count:0` so the four cards stay stable.

---

## The query

```sql
SELECT
    s.uuid           AS server_uuid,
    s.name           AS server_name,
    t.players_online AS players_online,
    t.date           AS tps_epoch_ms          -- Unix ms (UTC)
FROM plan_servers s
JOIN plan_tps t
  ON t.server_id = s.id
 AND t.date = (SELECT MAX(t2.date) FROM plan_tps t2 WHERE t2.server_id = s.id)
WHERE s.is_proxy = 0          -- game servers only, exclude the Velocity proxy
  AND s.is_installed = 1
ORDER BY s.name;
```

- `plan_tps.date` and `UNIX_TIMESTAMP()` are both UTC epoch based; only the unit
  differs (ms vs s). The script compares in ms and divides by 1000 for the JSON.
- `is_proxy = 0` is **mandatory**, otherwise the proxy row (= network total) is
  counted twice.
- The script **deduplicates by server UUID** (keeping the newest row) to guarantee
  exactly one row per server.
- Mapping DB → website key is done via the **stable server UUID**, not the name
  (the Plan config template ships `ServerName: Plan` everywhere, so names are
  unreliable). UUIDs come from each server's `*/plugins/Plan/ServerInfoFile.yml`.

---

## Configuration

Non-secret settings live in [`config.json`](config.json) (committed):

- `servers[]` — ordered website keys (`lobby | survival | mining | skyblock`),
  their Plan `uuid` (or `null` if Plan is not installed there yet) and a static
  `max` (Plan has no slot limit; set `max` to `0`/omit to hide `count/max`).
- `output` — target `players.json` path.
- `live_threshold_seconds` — default `120`.
- `database` — non-secret host/port/database/ssl/timeout defaults.

**Collect UUIDs:** current values in the repo —

| Website key | Plan UUID | Source |
|-------------|-----------|--------|
| `lobby`     | `82755ba5-91eb-4cca-a6a3-be06d891cb3d` | `lobby/plugins/Plan/ServerInfoFile.yml` |
| `survival`  | `679bd851-d131-4425-b88d-e2714a3ef0f2` | `survival/plugins/Plan/ServerInfoFile.yml` |
| `mining`    | _pending_ (no Plan yet) | — |
| `skyblock`  | _pending_ (no Plan yet) | — |

> The RPG archive server has a Plan UUID too, but it is not a website key, so it is
> simply not mapped and therefore excluded from the output.

Secret DB credentials come from the environment (never committed) — see
[`plan-players-export.env.example`](plan-players-export.env.example). Environment
variables and `--config`/`--output` override `config.json`.

---

## Read-only database user (least privilege)

Create a **dedicated read-only** MariaDB user with `SELECT` only on `s4_plan`
(ideally only the two tables the exporter reads). Do **not** reuse Plan's
read/write user (see `docs/infrastructure/DATENBANKEN.md`).

```sql
CREATE USER 'plan_ro'@'%' IDENTIFIED BY 'CHANGE_ME';
GRANT SELECT ON s4_plan.plan_servers TO 'plan_ro'@'%';
GRANT SELECT ON s4_plan.plan_tps     TO 'plan_ro'@'%';
FLUSH PRIVILEGES;
```

The exporter only produces aggregated numbers (no names/IPs), so it is
GDPR-uncritical. MariaDB stays Docker-internal (no external port); keep
`useSSL: true` (the script negotiates TLS, and verifies the certificate when a
`PLAN_RO_DB_SSL_CA` is provided).

---

## Local usage

```bash
python3 -m venv venv
venv/bin/pip install -r requirements.txt

export PLAN_RO_DB_USER=plan_ro
export PLAN_RO_DB_PASSWORD=...           # do not commit

# Print the snapshot without writing the file:
venv/bin/python plan_players_export.py --config config.json --dry-run

# Write players.json (path from config.json, or override with --output):
venv/bin/python plan_players_export.py --config config.json \
    --output /home/deploy/minecraft-website/data/players.json
```

On a database error the script **fails closed**: it logs, exits non-zero and does
**not** overwrite the last-good file. A persistent outage lets the snapshot age
past the 90 s staleness threshold (or the nginx fallback serves an empty snapshot
if the file never existed).

---

## Deployment (host + systemd timer)

The exporter must reach MariaDB (`172.25.0.1:3306`) **and** write into
`/home/deploy/minecraft-website/data/` (the read-only bind mount serving
`/api/players.json`). When the web host is the Docker host (the common case) a
host-side systemd timer is the simplest option.

`.github/workflows/deploy-plan-players-export.yml` automates this: it copies this
folder to the host, creates a virtualenv, installs the DB credentials from the
`PLAN_RO_DB_ENV` secret into a `chmod 600` env file, installs the systemd units and
enables the timer. Canonical install layout:

| Path | Purpose |
|------|---------|
| `/opt/plan-players-export/` | script, `config.json`, `venv/` |
| `/opt/plan-players-export/plan-players-export.env` | DB creds (`chmod 600`) |
| `/etc/systemd/system/plan-players-export.{service,timer}` | scheduler |
| `/home/deploy/minecraft-website/data/players.json` | output |

Manual install (equivalent to the workflow):

```bash
sudo mkdir -p /opt/plan-players-export
sudo cp plan_players_export.py config.json /opt/plan-players-export/
python3 -m venv /opt/plan-players-export/venv
/opt/plan-players-export/venv/bin/pip install -r requirements.txt

sudo install -m 600 plan-players-export.env.example \
    /opt/plan-players-export/plan-players-export.env
sudo "$EDITOR" /opt/plan-players-export/plan-players-export.env   # set user/password

sudo cp plan-players-export.service plan-players-export.timer /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable --now plan-players-export.timer
```

The timer runs every **45 s** (must stay < 90 s). Adjust the service `User=`/
`Group=` to whoever owns the `data/` directory if it is not `deploy`.

---

## Verification

```bash
# 1. Direct SQL sanity check (someone online -> a fresh row < 2 min old):
#    SELECT s.name, t.players_online, t.date FROM plan_servers s JOIN plan_tps t ...

# 2. Local file is valid contract JSON and freshly updated:
cat /home/deploy/minecraft-website/data/players.json | jq .

# 3. Served + rendered on the site:
curl -s https://mc.festas-builds.com/api/players.json | jq .
#    Section "Wer ist online?" updates; stopping the timer falls back to the
#    nginx inline snapshot.

# 4. Cross-check against the Plan dashboard (mc-stats.festas-builds.com);
#    a small difference from "max per minute" is expected.

# systemd:
systemctl status plan-players-export.timer
journalctl -u plan-players-export.service -n 20
```
