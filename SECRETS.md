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

---

## Datenbank & Plugin-Konfigurationen (Multi-Variable Secrets)

Diese Secrets enthalten **mehrere Schlüssel-Wert-Paare** im Shell-`key=value`-Format (ein Eintrag pro Zeile, wie eine `.env`-Datei). Sie werden beim Deployment in die jeweiligen Plugin-Configs injiziert.

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
Wird in den Servern RPG und Survival verwendet.

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

## Zusammenfassung

| Secret | Typ | Verwendet in |
|---|---|---|
| `SSH_HOST` | Plain Text | Alle Deploy-Workflows |
| `SSH_USER` | Plain Text | Alle Deploy-Workflows |
| `SSH_PRIVATE_KEY` | PEM-Schlüssel (mehrzeilig) | Alle Deploy-Workflows |
| `PERSONAL_TOKEN` | GitHub PAT (Plain Text) | copy-puginsfolder, sync-latest-logs |
| `GHCR_TOKEN` | GitHub PAT (Plain Text) | deploy-website |
| `SERVER_PATH_LOBBY` | Absoluter Pfad (Plain Text) | deploy-lobby, copy-puginsfolder, sync-latest-logs |
| `SERVER_PATH_PROXY` | Absoluter Pfad (Plain Text) | deploy-proxy, copy-puginsfolder, sync-latest-logs |
| `SERVER_PATH_RPG` | Absoluter Pfad (Plain Text) | deploy-rpg, copy-puginsfolder, sync-latest-logs |
| `SERVER_PATH_SKYBLOCK` | Absoluter Pfad (Plain Text) | deploy-skyblock, copy-puginsfolder, sync-latest-logs |
| `SERVER_PATH_SURVIVAL` | Absoluter Pfad (Plain Text) | deploy-survival, copy-puginsfolder, sync-latest-logs |
| `PLAN_DB_ENV` | `.env`-Format (mehrzeilig) | Alle Server-Deploy-Workflows |
| `PLAN_RO_DB_ENV` | `.env`-Format (mehrzeilig) | deploy-plan-players-export |
| `LUCKPERMS_DB_ENV` | `.env`-Format (mehrzeilig) | deploy-rpg, deploy-survival |
| `XPRISON_DASHBOARD_ENV` | `.env`-Format (mehrzeilig) | deploy-rpg |
| `XPRIVATEMINES_DASHBOARD_ENV` | `.env`-Format (mehrzeilig) | deploy-rpg |
