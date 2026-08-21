# GitHub Actions Secrets

Dieses Dokument listet alle GitHub Actions Secrets auf, die für die Workflows dieses Repositories benötigt werden, und beschreibt das erwartete Format für jedes Secret.

---

## SSH-Zugang

### `SSH_HOST`
Die IP-Adresse oder der Hostname des Deployment-Servers.

**Format:** Plain Text  
**Beispiel:** `192.168.1.100` oder `mein-server.example.com`

---

### `SSH_USER`
Der SSH-Benutzername für den Login auf dem Deployment-Server.

**Format:** Plain Text  
**Beispiel:** `deploy`

---

### `SSH_PRIVATE_KEY`
Der private SSH-Schlüssel (RSA oder Ed25519) für die passwortlose Authentifizierung am Deployment-Server.  
Der zugehörige öffentliche Schlüssel muss in `~/.ssh/authorized_keys` auf dem Server eingetragen sein.

**Format:** PEM-Schlüsseldatei, mehrzeiliger Text  
**Beispiel:**
```
-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAA...
-----END OPENSSH PRIVATE KEY-----
```

---

## GitHub / Registry

### `PERSONAL_TOKEN`
Ein GitHub Personal Access Token (classic oder fine-grained) mit Schreibrecht auf das Repository.  
Wird verwendet, um nach dem Sync automatisch Commits zu pushen.

**Format:** Plain Text  
**Berechtigungen (classic):** `repo` (vollständig)  
**Beispiel:** `******`

---

### `GHCR_TOKEN`
Ein GitHub Personal Access Token mit Zugriffsrecht auf die GitHub Container Registry (GHCR).  
Wird zum Bauen und Pushen des Website-Docker-Images verwendet.

**Format:** Plain Text  
**Berechtigungen:** `write:packages`, `read:packages`, `delete:packages`  
**Beispiel:** `******`

---

## Netzwerk (Velocity Modern Forwarding)

### `VELOCITY_FORWARDING_SECRET`
Das Velocity **Modern-Forwarding-Secret**. Es muss auf dem Proxy
(`proxy/forwarding.secret`, **nicht** im Repo) und auf allen Backends identisch
sein. In den committeten Backend-Configs
(`*/config/paper-global.yml`, Schlüssel `proxies.velocity.secret`) steht nur der
Platzhalter `__VELOCITY_FORWARDING_SECRET__`; der echte Wert wird beim Deploy
durch `sync-server-configs.yml` injiziert. Fehlt das Secret, **bricht der Deploy
bewusst ab** (fail-closed), damit die Backends nicht mit leerem Secret laufen
(sonst können sich keine Spieler mehr verbinden).

> **Wichtig:** Dieser Wert wurde zuvor im Klartext im Repo committet und muss
> daher als kompromittiert gelten. Er ist **zu rotieren** (neuen Wert auf Proxy
> + in diesem Secret setzen) und die Git-Historie sollte bereinigt werden.

**Format:** Plain Text (empfohlen: 32+ zufällige Zeichen)  
**Beispiel:** `Xf9c2...` (langer Zufallsstring)

> **Hinweis zu `management-server-secret`:** Der Paper-Management-Server ist
> netzwerkweit deaktiviert (`management-server-enabled=false`). Der Wert in den
> committeten `*/server.properties` ist daher **absichtlich leer** und wird beim
> Pull automatisch geleert – es ist **kein** GitHub-Secret nötig. Wird das
> Feature künftig aktiviert, sollte es analog zum Velocity-Secret externalisiert
> werden.

---

## Server-Pfade

Alle Server-Pfad-Secrets enthalten den **absoluten Pfad zum `plugins`-Ordner** auf dem Deployment-Server.

### `SERVER_PATH_LOBBY`
**Format:** Absoluter Pfad (Plain Text)  
**Beispiel:** `/opt/minecraft/lobby/plugins`

### `SERVER_PATH_PROXY`
**Format:** Absoluter Pfad (Plain Text)  
**Beispiel:** `/opt/minecraft/proxy/plugins`

### `SERVER_PATH_RPG`
**Format:** Absoluter Pfad (Plain Text)  
**Beispiel:** `/opt/minecraft/rpg/plugins`

### `SERVER_PATH_SKYBLOCK`
**Format:** Absoluter Pfad (Plain Text)  
**Beispiel:** `/opt/minecraft/skyblock/plugins`

### `SERVER_PATH_SURVIVAL`
**Format:** Absoluter Pfad (Plain Text)  
**Beispiel:** `/opt/minecraft/survival/plugins`

### `PLAYERS_JSON_REMOTE_PATH` (optional)
**Format:** Absoluter Pfad (Plain Text)  
**Beispiel:** `/home/deploy/minecraft-website/data/players.json`  
Optionaler Override für `sync-players-json.yml`. Ohne dieses Secret wird der
Standardpfad `/home/deploy/minecraft-website/data/players.json` (die Ausgabe von
`plan-players-export`) verwendet.

---

## Pterodactyl-Panel (Wartungs-Workflow)

Diese Secrets werden vom Workflow `server-maintenance.yml` benötigt, aber **nur**
für den optionalen Reboot-Teil (Spieler vorwarnen → Server sauber stoppen → nach
dem Neustart wieder starten). Fehlen sie, läuft die Analyse/Wartung trotzdem –
es wird dann lediglich **kein Reboot** durchgeführt.

### `PTERODACTYL_URL`
Basis-URL des Pterodactyl-Panels (ohne abschließenden `/`).

**Format:** Plain Text (URL)  
**Beispiel:** `https://panel.festas-builds.com`

### `PTERODACTYL_API_KEY`
Ein **Client-API-Schlüssel** (Account → API Credentials), beginnt mit `ptlc_`.
Er wird nur für die Client-Endpunkte (Power-Signale, Konsolenbefehle) der
**eigenen** Server verwendet – **kein** Application-/Admin-Key nötig.

**Format:** Plain Text  
**Beispiel:** `ptlc_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`

> **Least Privilege:** Der Client-Key sieht nur die Server, auf die das zugehörige
> Konto Zugriff hat. Am besten ein dediziertes Konto mit Zugriff auf genau die
> Minecraft-Server verwenden, die gestoppt/gestartet werden sollen.

---

## Repository-Variablen (optional, `server-maintenance.yml`)

Diese Werte sind **keine Secrets**, sondern GitHub **Variables**
(*Settings → Secrets and variables → Actions → Variables*). Alle sind optional
und haben sinnvolle Defaults. Details siehe
[`tools/server-maintenance/README.md`](tools/server-maintenance/README.md).

| Variable | Default | Zweck |
|---|---|---|
| `MAINTENANCE_ALLOW_REBOOT` | `false` | **Sicherheitsschalter.** Nur bei `true` darf der Workflow den Host neustarten. |
| `MAINTENANCE_SCHEDULED_MODE` | `full` | Modus des wöchentlichen Laufs (`analyze`/`maintain`/`full`). |
| `MAINTENANCE_SCHEDULED_APT` | `all` | Update-Umfang des wöchentlichen Laufs (`all`/`security`/`none`). |
| `MAINTENANCE_SCHEDULED_REBOOT` | `auto` | Reboot-Politik des wöchentlichen Laufs (`never`/`auto`/`force`). |
| `MAINTENANCE_WARN_STEPS` | `10,5,1` | Vorwarn-Minuten vor dem Stopp (kommagetrennt). |
| `MAINTENANCE_CHECK_DOMAINS` | `mc.festas-builds.com` | Domains für die TLS-Zertifikatsprüfung (leer = aus). |

---

## Datenbank & Plugin-Konfigurationen (Multi-Variable Secrets)

Diese Secrets enthalten **mehrere Schlüssel-Wert-Paare** im Shell-`key=value`-Format (ein Eintrag pro Zeile, wie eine `.env`-Datei). Sie werden beim Deployment in die jeweiligen Plugin-Configs injiziert.

> **Hinweis zu Sonderzeichen:** Injizierte Passwörter landen in YAML-Feldern mit
> **einfachen Anführungszeichen**. Verwende daher **kein** `'` (Apostroph) im
> Passwort, sonst wird die Config ungültig. Andere Sonderzeichen sind in Ordnung.

---

### `PLAN_DB_ENV`
Datenbankverbindung für das [Plan](https://github.com/plan-player-analytics/Plan) Plugin.  
Wird in allen Servern (Lobby, Proxy, RPG, Survival) verwendet.

**Format:** Mehrzeilige `.env`-Datei  
**Pflichtfelder:**
```env
PLAN_DB_HOST=db.example.com
PLAN_DB_PORT=3306
PLAN_DB_DATABASE=plan
PLAN_DB_USER=planuser
PLAN_DB_PASSWORD=geheimesPasswort
```

---

### `PLAN_RO_DB_ENV`
**Read-only** Datenbankverbindung für den Website-Exporter
[`tools/plan-players-export`](tools/plan-players-export/README.md), der aus der
Plan-Datenbank (`s4_plan`) die `players.json` mit Live-Spielerzahlen füllt.  
Wird im Workflow `deploy-plan-players-export` verwendet.

> **Least Privilege:** Ein **dedizierter Benutzer mit nur `SELECT`** auf `s4_plan`
> (idealerweise nur `plan_servers` und `plan_tps`). **Nicht** den Plan-RW-User
> (`PLAN_DB_ENV`) wiederverwenden.

**Format:** Mehrzeilige `.env`-Datei  
**Pflichtfelder:**
```env
PLAN_RO_DB_USER=plan_ro
PLAN_RO_DB_PASSWORD=geheimesPasswort
```
**Optionale Felder** (Defaults kommen aus `tools/plan-players-export/config.json`):
```env
PLAN_RO_DB_HOST=172.25.0.1
PLAN_RO_DB_PORT=3306
PLAN_RO_DB_DATABASE=s4_plan
PLAN_RO_DB_SSL=true
```

---

### `LUCKPERMS_DB_ENV`
Datenbankverbindung für das [LuckPerms](https://luckperms.net/) Plugin.  
Wird **netzwerkweit** in **Lobby, RPG, Survival und Skyblock** verwendet – alle
vier Server teilen sich **dieselbe** LuckPerms-Datenbank, damit Ränge überall
synchron sind (P1-5). Fehlt das Secret, **bricht der Deploy bewusst ab**
(fail-closed).

> **Migration:** Lobby und RPG nutzten zuvor lokales `h2`. Bestehende Rang-Daten
> müssen einmalig aus den `h2`-Dateien in die zentrale MariaDB migriert werden
> (z. B. `/lp export` auf einem Server → `/lp import` gegen die MariaDB), sonst
> starten diese Server nach der Umstellung mit leeren Rängen.

**Format:** Mehrzeilige `.env`-Datei  
**Pflichtfelder:**
```env
LUCKPERMS_DB_ADDRESS=db.example.com
LUCKPERMS_DB_DATABASE=luckperms
LUCKPERMS_DB_USER=luckpermsuser
LUCKPERMS_DB_PASSWORD=geheimesPasswort
```

> **Hinweis:** `LUCKPERMS_DB_ADDRESS` enthält Host und Port in einem Feld, z. B. `db.example.com:3306`.

---

### `REDIS_PASSWORD`
Passwort (`requirepass`) des zentralen **Redis**-Servers. Redis wird für das
**LuckPerms-Messaging** (Live-Push von Rang-Änderungen) auf allen vier Servern
(Lobby, RPG, Survival, Skyblock) sowie – auf dem Host – für HuskSync genutzt.

In den committeten LuckPerms-Configs (`*/plugins/LuckPerms/config.yml`, Schlüssel
`redis.password`) steht nur der Platzhalter `__REDIS_PASSWORD__`; der echte Wert
wird beim Deploy injiziert. Fehlt das Secret, **bricht der Deploy bewusst ab**
(fail-closed). Die Redis-Adresse `172.18.0.1:6379` ist **kein** Secret und steht
im Klartext in den Configs – bei geändertem Docker-Netzwerk dort anpassen.

> **Wichtig (P0-3):** Redis darf **nicht** öffentlich erreichbar sein. `requirepass`
> setzen, an die interne Bridge-Adresse binden und Port `6379` in der Firewall
> schließen – siehe [`docs/infrastructure/HOST_HARDENING.md`](docs/infrastructure/HOST_HARDENING.md).

**Format:** Plain Text (empfohlen: 32+ zufällige Zeichen, **ohne** `'`)  
**Beispiel:** `Xf9c2...` (langer Zufallsstring)

---

### `SKYBLOCK_DB_ENV`
Datenbank-Zugangsdaten für den **Skyblock**-Server: SuperiorSkyblock2
(`s4_skyblock`), das SlimeWorldIslands-Modul (`s4_superior_islands`) und den
SlimeWorldManager (`s4_slimeworldmanager`). Ein DB-Benutzer mit Zugriff auf diese
`s4_*`-Schemata genügt.

In den committeten Configs stehen nur die Platzhalter `__SKYBLOCK_DB_USER__` /
`__SKYBLOCK_DB_PASSWORD__` (vorher der Literal-Platzhalter `CHANGE_ME`, der die
Ursache des Skyblock-Ausfalls war – `Access denied for user 'CHANGE_ME'`). Der
echte Wert wird von `deploy-skyblock.yml` injiziert; fehlt das Secret, **bricht
der Deploy bewusst ab** (fail-closed).

**Format:** Mehrzeilige `.env`-Datei  
**Pflichtfelder:**
```env
SKYBLOCK_DB_USER=s4user
SKYBLOCK_DB_PASSWORD=geheimesPasswort
```

---

### `XPRISON_DASHBOARD_ENV`
Anmeldedaten für das xPrison-Dashboard-Plugin.  
Wird im RPG-Server verwendet.

**Format:** Mehrzeilige `.env`-Datei  
**Pflichtfelder:**
```env
XPRISON_DASHBOARD_USER=adminuser
XPRISON_DASHBOARD_PASSWORD=geheimesPasswort
XPRISON_DASHBOARD_JWT_SECRET=einLangerZufaelligerString
```

---

### `XPRIVATEMINES_DASHBOARD_ENV`
Anmeldedaten für das xPrivateMines-Dashboard-Plugin.  
Wird im RPG-Server verwendet.

**Format:** Mehrzeilige `.env`-Datei  
**Pflichtfelder:**
```env
XPRIVATEMINES_DASHBOARD_USER=adminuser
XPRIVATEMINES_DASHBOARD_PASSWORD=geheimesPasswort
XPRIVATEMINES_DASHBOARD_JWT_SECRET=einLangerZufaelligerString
```

---

## Ruhende / nicht injizierte Platzhalter

Einige Configs enthalten `__…__`-Platzhalter, die **absichtlich nicht** von einem
Deploy-Workflow injiziert werden, weil das zugehörige Backend derzeit **inaktiv** ist
(eingebettete DB oder Feature deaktiviert). Sie ersetzen lediglich zuvor eingecheckte
schwache Default-Passwörter. Wird ein Backend später aktiviert, muss ein
Injection-Schritt (analog zu `LUCKPERMS_DB_ENV`) **und** ein passendes Secret ergänzt
werden – niemals einen echten Wert direkt committen.

| Platzhalter | Datei | Inaktiv, weil |
|---|---|---|
| `__SKINSRESTORER_DB_*__` | `proxy/plugins/skinsrestorer/config.yml` | Storage = FILE |
| `__TAB_DB_*__` | `proxy/plugins/tab/config.yml` | MySQL deaktiviert |
| `__LIBERTYBANS_DB_USER__` / `__LIBERTYBANS_DB_PASSWORD__` | `proxy/plugins/libertybans/sql.yml`, `import.yml` | `rdms-vendor: HSQLDB` (lokal) |
| `__PLOTSQUARED_DB_PASSWORD__` | `survival/plugins/PlotSquared/config/storage.yml` | `mysql.use: false` (SQLite) |
| `__GLOBALMARKETPLUS_DB_PASSWORD__` | `survival/plugins/GlobalMarketPlus/Config.yml`, `rpg/plugins/GlobalMarketPlus/Config.yml` | `MySQL-Storage.Enabled: false` (SQLite) |
| `__XROBOTS_DB_PASSWORD__` | `rpg/plugins/XRobots/config.yml` | `database_type: H2` (lokal) |

---

## Zusammenfassung

| Secret | Typ | Verwendet in |
|---|---|---|
| `SSH_HOST` | Plain Text | Alle Deploy-Workflows, sync-server-configs |
| `SSH_USER` | Plain Text | Alle Deploy-Workflows, sync-server-configs |
| `SSH_PRIVATE_KEY` | PEM-Schlüssel (mehrzeilig) | Alle Deploy-Workflows, sync-server-configs |
| `PERSONAL_TOKEN` | GitHub PAT (Plain Text) | copy-puginsfolder, sync-latest-logs, sync-players-json, sync-server-configs |
| `GHCR_TOKEN` | GitHub PAT (Plain Text) | deploy-website |
| `VELOCITY_FORWARDING_SECRET` | Plain Text | sync-server-configs |
| `SERVER_PATH_LOBBY` | Absoluter Pfad (Plain Text) | deploy-lobby, copy-puginsfolder, sync-latest-logs, sync-server-configs |
| `SERVER_PATH_PROXY` | Absoluter Pfad (Plain Text) | deploy-proxy, copy-puginsfolder, sync-latest-logs, sync-server-configs |
| `SERVER_PATH_RPG` | Absoluter Pfad (Plain Text) | deploy-rpg, copy-puginsfolder, sync-latest-logs, sync-server-configs |
| `SERVER_PATH_SKYBLOCK` | Absoluter Pfad (Plain Text) | deploy-skyblock, copy-puginsfolder, sync-latest-logs, sync-server-configs |
| `SERVER_PATH_SURVIVAL` | Absoluter Pfad (Plain Text) | deploy-survival, copy-puginsfolder, sync-latest-logs, sync-server-configs |
| `PTERODACTYL_URL` | Plain Text (URL) | server-maintenance (nur Reboot-Teil) |
| `PTERODACTYL_API_KEY` | Client-API-Key (`ptlc_…`) | server-maintenance (nur Reboot-Teil) |
| `PLAN_DB_ENV` | `.env`-Format (mehrzeilig) | Alle Server-Deploy-Workflows |
| `PLAN_RO_DB_ENV` | `.env`-Format (mehrzeilig) | deploy-plan-players-export |
| `LUCKPERMS_DB_ENV` | `.env`-Format (mehrzeilig) | deploy-lobby, deploy-rpg, deploy-survival, deploy-skyblock |
| `REDIS_PASSWORD` | Plain Text | deploy-lobby, deploy-rpg, deploy-survival, deploy-skyblock |
| `SKYBLOCK_DB_ENV` | `.env`-Format (mehrzeilig) | deploy-skyblock |
| `XPRISON_DASHBOARD_ENV` | `.env`-Format (mehrzeilig) | deploy-rpg |
| `XPRIVATEMINES_DASHBOARD_ENV` | `.env`-Format (mehrzeilig) | deploy-rpg |
| `PLAYERS_JSON_REMOTE_PATH` (optional) | Absoluter Pfad (Plain Text) | sync-players-json |
