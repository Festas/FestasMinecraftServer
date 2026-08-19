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
                    │  ├─ s4_stats (Plan)       │
                    │  ├─ s4_husk (HuskSync)    │
                    │  ├─ s4_party (PAF)        │
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
                    │   172.18.0.1:6379         │
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
| `s4_stats`    | Plan             | Spieler-Statistiken, Server-Analytics        | Proxy, Prison                       |
| `s4_husk`     | HuskSync        | Ränge und Cosmetics                          | Lobby, Prison                       |
| `s4_party`    | PartyAndFriends | Party- und Freundeslisten-Daten              | Proxy                               |
| `s4_bazaar`   | DeluxeBazaar    | Bazaar-Angebote und Transaktionen            | Skyblock, Prison                    |

### Redis

| Verwendung                | Zweck                                           | Server         |
|---------------------------|-------------------------------------------------|----------------|
| HuskSync Session-Cache    | Temporäre Daten beim Server-Wechsel             | Alle           |

**TTL (Time To Live):**
- Session-Daten: 30 Minuten
- Inventar-Cache: 5 Minuten

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
`PLAN_STATS_DB_ENV` in die Plan-Konfigurationen injiziert.

Format des Secrets:

```bash
PLAN_DB_HOST='172.25.0.1'
PLAN_DB_PORT='3306'
PLAN_DB_DATABASE='s4_plan'
PLAN_DB_USER='CHANGE_ME'
PLAN_DB_PASSWORD='CHANGE_ME'
```

### Redis

```yaml
host: 172.18.0.1
port: 6379
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
