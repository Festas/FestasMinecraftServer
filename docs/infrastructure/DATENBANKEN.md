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
                    │  └─ s4_bazaar (Bazaar)    │
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

Format des Secrets (für die neue Plan-Datenbank `s4_plan`):

```bash
PLAN_DB_HOST='172.25.0.1'
PLAN_DB_PORT='3306'
PLAN_DB_DATABASE='s4_plan'
PLAN_DB_USER='CHANGE_ME'
PLAN_DB_PASSWORD='CHANGE_ME'
```

> **Read-only-Zugang für die Website:** Der Exporter
> [`tools/plan-players-export`](../../tools/plan-players-export/README.md) liest die
> Live-Spielerzahlen für `players.json` aus `s4_plan`. Dafür wird ein **separater
> Benutzer mit nur `SELECT`** (idealerweise nur auf `plan_servers` und `plan_tps`)
> angelegt und über das Secret `PLAN_RO_DB_ENV` bereitgestellt – **nicht** der
> RW-User oben (Prinzip der minimalen Berechtigung).

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

**Letzte Aktualisierung:** 2026-08-18
