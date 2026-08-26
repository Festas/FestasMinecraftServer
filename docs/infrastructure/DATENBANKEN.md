# Datenbanken - Infrastruktur

Übersicht über die Datenbank-Topologie des MinecraftMMO-Netzwerks. Das Netzwerk nutzt **MariaDB** als relationale Hauptdatenbank und **Redis** als In-Memory-Cache, beide als Docker-Container im internen Netzwerk.

---

## Topologie

```
                    ┌──────────────────────────┐
                    │       MariaDB (Docker)    │
                    │   172.25.0.1:3306         │
                    │                           │
                    │  ┌─ s4_perms (LuckPerms)  │
                    │  ├─ s4_plan (Plan)        │
                    │  ├─ s4_husk (HuskSync)    │
                    │  ├─ s4_bazaar (Bazaar)    │
                    │  └─ S{1,3,5}_CMI (CMI Eco)│
                    └────────────┬─────────────┘
                                 │
        ┌────────────┬───────────┼───────────┬────────────┐
        │            │           │           │            │
   ┌────▼───┐  ┌────▼───┐ ┌────▼────┐ ┌────▼───┐  ┌────▼───┐
   │ Proxy  │  │ Lobby  │ │Survival │ │Skyblock│  │ Prison │
   └────────┘  └────────┘ └─────────┘ └────────┘  └────────┘

                    ┌──────────────────────────┐
                    │       Redis (Docker)      │
                    │   172.18.0.1:6380         │
                    │                           │
                    │  HuskSync Session-Cache   │
                    └────────────┬─────────────┘
                                 │
                        ┌────────┴────────┐
                   ┌────▼───┐       ┌────▼───┐
                   │  Lobby │       │ Prison │
                   └────────┘       └────────┘
```

---

## Datenbank-Übersicht

### MariaDB – Datenbanken

| Datenbank     | Plugin          | Zweck                                        | Server                              |
|---------------|-----------------|----------------------------------------------|-------------------------------------|
| `s4_perms`    | LuckPerms       | Permissions, Gruppen, Tracks                 | Proxy, Lobby, Survival, Skyblock, Prison |
| `s4_plan`     | Plan             | Spieler-Statistiken, Server-Analytics        | Proxy, Lobby, Survival, RPG         |
| `s4_husk`     | HuskSync        | Ränge und Cosmetics                          | Lobby, Prison                       |
| `s4_bazaar`   | DeluxeBazaar    | Bazaar-Angebote und Transaktionen            | Skyblock, Prison                    |
| `S1_CMI`      | CMI         | Economy-Guthaben (Survival)                  | Survival                            |
| `S3_CMI`      | CMI         | Economy-Guthaben (RPG/Prison = „Mining")     | RPG                                 |
| `S5_CMI`      | CMI         | Economy-Guthaben (Skyblock)                  | Skyblock                            |

> **CMI-Economy pro Server getrennt:** CMI ist der Economy-Anbieter, hält die
> Guthaben aber **pro Server** in einer **eigenen** Datenbank. CMI warnt
> ausdrücklich, dass zwei Server **niemals** dieselbe Tabelle teilen dürfen
> (`DON'T USE SAME DATABASE TABLES FOR MORE THEN ONE SERVER`), daher bekommt jeder
> Gameplay-Server seine eigene Datenbank (`S1_CMI`, `S3_CMI`, `S5_CMI`). Die Lobby
> hat keine Economy und bleibt auf SQLite. Details zur read-only Auswertung für die
> Website siehe den Exporter [`tools/economy-export`](../../tools/economy-export/README.md).

### Redis

| Verwendung                | Zweck                                           | Server         |
|---------------------------|-------------------------------------------------|----------------|
| HuskSync Session-Cache    | Temporäre Daten beim Server-Wechsel             | Alle           |
| LuckPerms-Messaging       | Live-Push von Rang-/Permission-Änderungen (Pub/Sub) | Alle       |

**TTL (Time To Live):**
- Session-Daten: 30 Minuten
- Inventar-Cache: 5 Minuten

**Deployment:** Der Redis-Container wird über den Workflow
[`deploy-redis`](../../.github/workflows/deploy-redis.yml) aus
[`infra/redis/`](../../infra/redis/README.md) gestartet
(`docker compose` + `redis.conf` mit injiziertem `REDIS_PASSWORD`). Der Port wird
nur auf der internen Bridge-Adresse `172.18.0.1:6380` veröffentlicht, nicht
öffentlich. Details siehe [`infra/redis/README.md`](../../infra/redis/README.md).

> **Getrennt vom Panel-Redis:** Auf dem Host läuft zusätzlich eine **native**
> Redis-Instanz (apt + systemd) für das Pterodactyl-Panel auf `127.0.0.1:6379`.
> Der Game-Redis-Container veröffentlicht bewusst auf **Host-Port `6380`** (der
> Container-Port bleibt `6379`), damit sich beide nicht blockieren. Der
> Panel-Redis bleibt unverändert.

### SQLite-Fallback

Die meisten Plugins unterstützen SQLite als lokalen Fallback, falls die MariaDB-Verbindung nicht verfügbar ist. SQLite-Datenbanken werden im jeweiligen Plugin-Ordner unter `data/` oder im Plugin-Root als `.db`-Datei gespeichert. **Wichtig:** Bei SQLite-Betrieb ist keine serverübergreifende Synchronisation möglich.

---

## Verbindungseinstellungen

### MariaDB

```yaml
# Beispiel-Konfiguration (alle Plugins)
host: 172.25.0.1
port: 3306
database: s4_<plugin>
username: CHANGE_ME
password: CHANGE_ME
useSSL: true
```

### GitHub Actions Secret für Plan

Die Plan-Zugangsdaten werden nicht mehr im Repository gespeichert. Stattdessen
werden sie während des Deployments aus dem GitHub-Secret
`PLAN_DB_ENV` in die Plan-Konfigurationen injiziert.

Format des Secrets (`.env`, ein `KEY=VALUE` pro Zeile; für die neue
Plan-Datenbank `s4_plan`):

```env
PLAN_DB_HOST=172.25.0.1
PLAN_DB_PORT=3306
PLAN_DB_DATABASE=s4_plan
PLAN_DB_USER=CHANGE_ME
PLAN_DB_PASSWORD=CHANGE_ME
```

Die Werte werden beim Deployment **wörtlich** injiziert; umschließende `'`/`"`
sind optional und werden entfernt. Sonderzeichen im Passwort (z. B. `$`,
`` ` ``, `\`, `"`) sind erlaubt und müssen **nicht** escaped werden.

> **Read-only-Zugang für die Website:** Der Exporter
> [`tools/plan-players-export`](../../tools/plan-players-export/README.md) liest die
> Live-Spielerzahlen für `players.json` aus `s4_plan`. Dafür wird ein **separater
> Benutzer mit nur `SELECT`** (idealerweise nur auf `plan_servers` und `plan_tps`)
> angelegt und über das Secret `PLAN_RO_DB_ENV` bereitgestellt – **nicht** der
> RW-User oben (Prinzip der minimalen Berechtigung).
>
> **Bestenliste:** Der Exporter
> [`tools/leaderboard-export`](../../tools/leaderboard-export/README.md) füllt
> `leaderboard.json` (Top-N nach Spielzeit + höchster LuckPerms-Rang). Er liest die
> Spielzeiten aus `s4_plan` (`plan_sessions`, `plan_users`) – derselbe
> `PLAN_RO_DB_ENV`-User braucht dafür **zusätzlich** `SELECT` auf diese beiden
> Tabellen – und den Rang aus `s4_perms` über einen **eigenen** Read-only-User
> (`SELECT` auf `luckperms_*`), Secret `LUCKPERMS_RO_DB_ENV`.

### GitHub Actions Secret für CMI (Economy)

Die CMI-Guthaben werden pro Server in einer eigenen MySQL-Datenbank gehalten
(`S1_CMI`, `S3_CMI`, `S5_CMI`). Die Deploy-Workflows (`deploy-survival`,
`deploy-rpg`, `deploy-skyblock`) injizieren dafür pro Server einen Benutzer aus je
einem eigenen Secret in `<server>/plugins/CMI/Settings/DataBaseInfo.yml`:

| Secret                | Ziel-Server | Datenbank |
|-----------------------|-------------|-----------|
| `CMI_SURVIVAL_DB_ENV` | Survival    | `S1_CMI`  |
| `CMI_MINING_DB_ENV`   | RPG         | `S3_CMI`  |
| `CMI_SKYBLOCK_DB_ENV` | Skyblock    | `S5_CMI`  |

Format des Secrets (`.env`, ein `KEY=VALUE` pro Zeile, Beispiel Survival):

```env
CMI_SURVIVAL_DB_HOST=172.25.0.1
CMI_SURVIVAL_DB_PORT=3306
CMI_SURVIVAL_DB_DATABASE=S1_CMI
CMI_SURVIVAL_DB_USER=u1_xxxxxxxxxx
CMI_SURVIVAL_DB_PASSWORD=CHANGE_ME
```

Der Deploy injiziert nur `USER` und `PASSWORD` in die `DataBaseInfo.yml`; Host,
Port und Datenbankname stehen (nicht geheim) fest in der Config. Die Werte werden –
wie bei Plan – in Python geparst und **wörtlich** injiziert; umschließende `'`/`"`
sind optional. Sonderzeichen im Passwort (`$`, `` ` ``, `\`, `"`, `'`, `+`, `=`,
`!`) sind erlaubt und müssen **nicht** escaped werden.

> **Einmalige Umstellung:** CMI legt seine Tabellen (`CMI_users`, …) beim ersten
> Start selbst an und migriert die bestehenden SQLite-Guthaben. Jeder Server muss
> seine **eigene** Datenbank bekommen – niemals eine gemeinsame Tabelle. Setze die
> drei Secrets, bevor dieser Branch nach `main` gemergt wird, sonst schlägt der
> Deploy fehl (fail-closed, keine kaputte Config landet auf dem Server).
>
> **Read-only-Zugang für die Website:** Der Exporter
> [`tools/economy-export`](../../tools/economy-export/README.md) liest die
> reichsten Spieler für `economy.json` aus denselben Datenbanken (`S1_CMI`,
> `S3_CMI`, `S5_CMI`) und verwendet dafür **dasselbe** Secret pro Server
> (`CMI_SURVIVAL_DB_ENV` / `CMI_MINING_DB_ENV` / `CMI_SKYBLOCK_DB_ENV`). Wer strikt
> nach dem Prinzip der minimalen Berechtigung arbeiten möchte, kann den Exporter
> stattdessen auf einen separaten Benutzer mit nur `SELECT` auf `CMI_users`
> zeigen lassen.

### Redis

```yaml
host: 172.18.0.1
port: 6380
password: CHANGE_ME
useSSL: false
```

---

## Connection-Pool-Einstellungen

| Plugin    | Pool-Größe | Max Lifetime | Connection Timeout |
|-----------|------------|--------------|---------------------|
| LuckPerms | 10         | 1800000 ms   | 5000 ms             |
| HuskSync  | Standard   | Standard      | 5000 ms             |
| Plan      | Standard   | Standard      | 5000 ms             |

LuckPerms verwendet eine feste Pool-Größe von **10 Verbindungen**, da es von allen Servern gleichzeitig auf `s4_perms` zugreift. Andere Plugins nutzen die jeweiligen Standard-Werte.

---

## Sicherheit

- **SSL/TLS:** Für MariaDB-Verbindungen ist SSL aktiviert (`useSSL: true`). Zertifikate werden über die Docker-Konfiguration bereitgestellt.
- **Zugangsdaten:** Alle Passwörter und Benutzernamen sind in den Plugin-Konfigurationen als `CHANGE_ME`-Platzhalter hinterlegt und müssen vor dem produktiven Einsatz ersetzt werden.
- **Netzwerk-Isolation:** MariaDB und Redis sind nur im Docker-internen Netzwerk erreichbar (172.25.0.0/16 bzw. 172.18.0.0/16). Kein externer Zugriff möglich.
- **Zugangsbeschränkung:** Jeder Datenbank-Benutzer sollte nur Zugriff auf die benötigten Datenbanken erhalten (Prinzip der minimalen Berechtigung).
- **Keine Credentials im Repository:** Zugangsdaten dürfen niemals in dieses Git-Repository committed werden.

---

## Backup-Strategie

Datenbank-Backups werden automatisiert durchgeführt:
- **Stündlich:** Inkrementelle Backups
- **Täglich:** Vollständige Backups

Detaillierte Informationen zur Backup-Strategie, Aufbewahrungsfristen und Wiederherstellungsverfahren finden sich in der [Backup-Dokumentation](BACKUPS.md).

---

## Siehe auch

- [Infrastruktur Übersicht](README.md)
- [Backup-Strategien](BACKUPS.md)
- [Architektur-Dokumentation](../ARCHITECTURE.md)

---

**Letzte Aktualisierung:** 2026-08-26
