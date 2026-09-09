# Plan (Player Analytics)

**Analytics/Statistiken · proxy / lobby / survival / skyblock / rpg(mining) · MySQL geteilt**

## Zweck
Plan sammelt Spielerstatistiken (Sessions, Playtime, Server-Wechsel) und stellt das Web-Dashboard
`mc-stats.festas-builds.com` bereit. Der Website-Export (`tools/plan-players-export/`) liest daraus die
Online-Zahlen für die öffentliche Seite.

## Wo (Server & Config-Pfade)
- Proxy: `proxy/plugins/plan/config.yml` (Webserver-Host, Port **8804**)
- Backends: `<server>/plugins/Plan/config.yml` (+ `ServerInfoFile.yml`)
  - Aktiv mit geteilter MySQL: **lobby, survival, skyblock, rpg(mining)**
  - Aktuelle Skyblock-UUID: **`d8a82d02-e78b-4723-9245-9a902d6cf0ac`**

## Storage & Secrets
- Geteilte MySQL-Datenbank **`s4_plan`** für Proxy/lobby/survival/skyblock/rpg.
- Platzhalter: `__PLAN_DB_HOST__`, `__PLAN_DB_PORT__`, `__PLAN_DB_USER__`, `__PLAN_DB_PASSWORD__`,
  `__PLAN_DB_DATABASE__` (injiziert aus **`PLAN_DB_ENV`**).
- ⚠️ **Injektion erfolgt per Python-Parsing** (Werte verbatim), **nie** shell-sourcen – Passwörter mit
  `$`, Backticks, `\`, Anführungszeichen oder Leerzeichen würden sonst zerstört und Plan schaltet sich mit
  „Access denied … Player Analytics Disabled" ab.
- skyblock nutzt ebenfalls **`PLAN_DB_ENV`** für Plan; **separat davon** bleiben
  **SuperiorSkyblock2** (lokal SQLite) und **SlimeWorldManager** (Datei-Storage).

## Wichtige Einstellungen / typische Aufgaben
- **Webserver** am Proxy: `Disable_Webserver: false`, Port `8804` → nginx-Upstream
  `127.0.0.1:8804` (`nginx/sites-available/mc-stats.festas-builds.com.conf`). Bindet Plan nicht (DB-Auth-Fehler),
  liefert nginx **502**.
- **Server-Name/UUID** → `ServerInfoFile.yml` (nicht duplizieren – sonst doppelte Server im Dashboard).
- Nach DB-Änderungen: Plan-Reload bzw. Server-Neustart.

## Cross-Server / Gotchas
- Auth-Fehler auf der geteilten MySQL betrifft **alle** MySQL-Plan-Server + LuckPerms gleichzeitig.
- skyblock hängt am selben Plan-MySQL-Verbund; fällt `PLAN_DB_ENV`/MariaDB aus, fehlt er wie die anderen
  MySQL-Server im Dashboard/Export.
- Öffentliche Spielerzahlen der Website kommen aus `tools/plan-players-export/config.json` (`max` pro Server),
  **nicht** aus `server.properties`.

## Custom Agent
[`.github/agents/plan.agent.md`](../../.github/agents/plan.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [`docs/infrastructure/DATENBANKEN.md`](../infrastructure/DATENBANKEN.md) ·
`nginx/sites-available/mc-stats.festas-builds.com.conf` · `tools/plan-players-export/`
