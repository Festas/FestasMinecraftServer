# Betriebshandbuch – Minecraft MMO Netzwerk

Dieses Dokument beschreibt die operativen Abläufe für das Minecraft-MMO-Netzwerk. Alle Server laufen in Docker-Containern.

> **Stand 26.2:** Das Netzwerk läuft auf **Minecraft 26.2**. Aktiv betrieben werden **Proxy (Velocity)**, **Lobby** und **Survival**. Im Aufbau: **Skyblock** (Umbau) und **Mining** (recycelter `rpg/`-Slot).

---

## Server-Startsequenz

Die Server müssen in der folgenden Reihenfolge gestartet werden, um Abhängigkeiten korrekt aufzulösen:

1. **MariaDB & Redis** – Datenbank und Cache müssen zuerst verfügbar sein.
2. **Velocity Proxy** – Der Proxy muss laufen, bevor Spieler sich verbinden können.
3. **Lobby** – Der Lobby-Server ist der Standard-Spawn für alle Spieler.
4. **Spielserver** – RPG, Skyblock und Survival können parallel gestartet werden.

```bash
# Beispiel mit Docker Compose
docker compose up -d mariadb redis

# Bereitschaft prüfen, bevor weitere Dienste gestartet werden
docker compose exec mariadb mysqladmin ping --wait=30
docker compose exec redis redis-cli ping

docker compose up -d velocity
docker compose up -d lobby
docker compose up -d skyblock survival
```

---

## Server-Stopp-Sequenz

Das Herunterfahren erfolgt in **umgekehrter Reihenfolge**. Spieler müssen vorher benachrichtigt werden.

1. **Spieler benachrichtigen** – Mindestens 5 Minuten vorher eine Warnung an alle Spieler senden.
2. **Spielserver stoppen** – RPG, Skyblock und Survival herunterfahren.
3. **Lobby stoppen** – Lobby-Server herunterfahren.
4. **Velocity Proxy stoppen** – Proxy herunterfahren, damit keine neuen Verbindungen möglich sind.
5. **MariaDB & Redis stoppen** – Datenbank und Cache zuletzt stoppen.

```bash
# Warnung an Spieler senden (über Velocity-Konsole)
# /alert Der Server wird in 5 Minuten heruntergefahren!

docker compose stop skyblock survival
docker compose stop lobby
docker compose stop velocity
docker compose stop mariadb redis
```

---

## Zeitzone & geplante Neustarts

Alle Server **und der Proxy** sollen mit **deutscher Zeit** laufen – inklusive
automatischer Umstellung zwischen **Winterzeit (CET, UTC+1)** und **Sommerzeit
(CEST, UTC+2)**. Dazu wird für **jeden** Container die Zeitzone `Europe/Berlin`
gesetzt. Die IANA-Zeitzonendatenbank übernimmt die Sommer-/Winterzeit-Umstellung
dann automatisch. Ein **fester** Offset (`UTC+1`/`UTC+2` bzw. `GMT+1`) darf
**nicht** verwendet werden, da er die Umstellung nicht mitmacht.

### Zeitzone setzen

Auf **allen** Servern – Proxy, Lobby, Survival, Skyblock und Mining
(`rpg`-Slot) – die Umgebungsvariable setzen:

```env
TZ=Europe/Berlin
```

- **Pterodactyl:** Server → **Startup** → Variable `TZ` auf `Europe/Berlin`
  setzen (bzw. in der Egg-Konfiguration ergänzen) und den Server einmal **neu
  starten**, damit die JVM die Zeitzone übernimmt.
- **Docker Compose:** im jeweiligen Service `environment: ["TZ=Europe/Berlin"]`
  ergänzen.
- **JVM-Fallback:** Falls sich `TZ` nicht setzen lässt, wirkt das Startflag
  `-Duser.timezone=Europe/Berlin`.

> Java/Paper, CMI und die Log-Zeitstempel folgen der Container-Zeitzone. Plan
> ist bereits auf `TimeZone: 'server'` konfiguriert und übernimmt sie damit
> automatisch.

### Prüfen

```bash
# Container-Uhr prüfen – erwartet CET (Winter) bzw. CEST (Sommer)
docker exec <container> date
```

Im Spiel muss `/cmi time` (bzw. `/time`) die aktuelle **deutsche** Uhrzeit
anzeigen.

### Neustart-Zeiten (deutsche Ortszeit)

Die täglichen Neustarts laufen über die **CMI-Scheduler**
(`plugins/CMI/Settings/Schedules.yml`) und richten sich nach der oben gesetzten
Zeitzone. Die Zeiten sind **gestaffelt**, damit nie alle Server gleichzeitig
offline sind:

| Server   | Uhrzeit (Europe/Berlin) | Scheduler        |
| -------- | :---------------------: | ---------------- |
| Survival | 03:55                   | `RestartWarning` |
| Lobby    | 04:00                   | `DailyRestart`   |
| RPG/Mining¹ | 04:05                | `DailyRestart`   |
| Skyblock | 04:10                   | `DailyRestart`   |

Jeder Neustart warnt die Spieler 5 Minuten vorher, sichert die Daten
(`save-all`) und stoppt den Server anschließend (`stop`). Der Pterodactyl-Auto-
Restart bzw. der Pterodactyl-Neustart-Schedule fährt ihn danach wieder hoch.

> ¹ Technischer Slotname bleibt `rpg`; spielerseitig ist dies der Mining-Server.

---

## Tägliche Wartung

Folgende Punkte sollten täglich überprüft werden:

| Prüfpunkt         | Beschreibung                                                  |
| ------------------ | ------------------------------------------------------------- |
| **TPS**            | Ticks pro Sekunde auf allen Spielservern prüfen (Ziel: 20.0) |
| **Arbeitsspeicher** | RAM-Auslastung aller Container überwachen                    |
| **Speicherplatz**  | Festplattenbelegung prüfen (Welten, Logs, Backups)           |
| **Spieler-Reports** | Offene Reports und Beschwerden bearbeiten                    |
| **Logs**           | Fehler- und Warnmeldungen in Server-Logs überprüfen          |
| **Backups**        | Sicherstellen, dass tägliche Backups erfolgreich waren        |

Für das Einsammeln der aktuellen `latest.log`-Dateien gibt es zusätzlich den GitHub-Workflow
`sync-latest-logs.yml`. Er läuft täglich automatisch und kann bei Bedarf manuell für **alle**
oder einen einzelnen Server gestartet werden. Die Logs werden im Repository unter
`server-logs/<server>/latest.log` aktualisiert. **Mining** verwendet den **`rpg`-Server-Slot** im Container-Setup.

### Sofort-Befunde aus aktuellen Logs (P0)

- **Mining (`rpg`)**: `XPrisonArmors` startet auf 26.2 nicht sauber (`NumberFormatException: "build"`).
  - Maßnahme: Addon bis zu einem kompatiblen Build deaktiviert lassen bzw. auf 26.2-kompatible Version aktualisieren.
- **Survival**: `Lands` meldet beim Start wiederholt SQL-Migrationsfehler zu fehlenden War-Tabellen (`lands_wars*`).
  - Maßnahme: beim nächsten Wartungsfenster Lands-Datenbank/Version-Migrationspfad prüfen und mit aktuellem Plugin-Build bereinigen.
- **Survival**: PlaceholderAPI meldet fehlende `floodgate`-Expansion.
  - Maßnahme: Expansion auf dem Zielserver entfernen, wenn kein Bedrock/Floodgate genutzt wird.

Für die Live-Spielerzahlen (`/api/players.json`) gibt es zwei weitere Workflows:

- `sync-players-json.yml` zieht alle 6 Stunden (oder manuell) die vom
  `plan-players-export` erzeugte `players.json` vom Host und legt sie – ohne
  Spielernamen, nur Zahlen/Flags/Zeitstempel – unter `server-logs/players/players.json`
  im Repository ab. So lässt sich der ausgelieferte Snapshot historisch nachvollziehen
  (z. B. „Server offline obwohl online?").
- `monitor-players-json.yml` prüft alle 30 Minuten (oder manuell) den öffentlichen
  Endpunkt `https://mc.festas-builds.com/api/players.json` gegen den Vertrag der
  Website (gültiges JSON, alle vier Server-Keys vorhanden, plausible Counts,
  Aktualität von `updated`). Ein fehlgeschlagener Lauf meldet früh, dass der Exporter
  oder die Auslieferung gestört ist.

### Server-Configs synchronisieren (`sync-server-configs.yml`)

Für die zentralen Paper-/Velocity-Konfigurationsdateien im **Server-ROOT** (eine Ebene
über `plugins/`) gibt es den Workflow `sync-server-configs.yml`. Er ergänzt die
`deploy-*.yml`-Workflows, die ausschließlich den `plugins/`-Ordner behandeln.

- **`from_server` (manuell):** zieht die aktuellen Configs vom Server ins Repository
  (`<server>/<datei>`) und committet sie. Für einen einzelnen Server oder `ALL`.
- **`to_server` (manuell):** deployed die im Repo liegenden Configs auf den gewählten
  Server (bzw. `ALL`).
- **push (automatisch):** Jede Änderung an einer dieser Dateien auf `main` wird
  automatisch auf den betroffenen Server deployed. Gelöschte Dateien werden aus
  Sicherheitsgründen **nicht** automatisch auf dem Server entfernt.

Berücksichtigte Dateien (Allowlist):

| Servertyp | Dateien |
|---|---|
| Paper (lobby, survival, skyblock, rpg) | `server.properties`, `bukkit.yml`, `spigot.yml`, `commands.yml`, `help.yml`, `permissions.yml`, `wepif.yml`, `eula.txt`, `config/paper-global.yml`, `config/paper-world-defaults.yml`, `banned-ips.json`, `banned-players.json`, `ops.json`, `whitelist.json` |
| Velocity (proxy) | `velocity.toml` |

> **Sicherheit / Datenschutz:**
> - `forwarding.secret` (Velocity) wird **nie** synchronisiert. Beim Ziehen von
>   `velocity.toml` bricht der Workflow ab, falls ein Inline-`forwarding-secret`
>   mit Wert gefunden wird (Empfehlung: `forwarding-secret-file` nutzen).
> - `banned-ips.json`, `banned-players.json`, `ops.json` und `whitelist.json`
>   enthalten **personenbezogene Daten** (IPs, UUIDs, Namen) und sind normalerweise
>   in `.gitignore`. Dieser Workflow versioniert sie bewusst (`git add -f`) – nur
>   verwenden, wenn das Repository dafür geeignet (privat) ist.

### Wöchentliche Host-Wartung & Health-Report (`server-maintenance.yml`)

Umfassender, automatischer Wartungs- und Gesundheits-Workflow für den **Linux-Host**
(nicht nur die Minecraft-Server). Läuft **wöchentlich** (Mittwoch **02:00 UTC**,
~03:00/04:00 Uhr Berlin) und ist jederzeit **manuell** auslösbar. Details und
Einrichtung: [`tools/server-maintenance/README.md`](../tools/server-maintenance/README.md).

- **Analyse & Health-Checks (alle Bereiche):** Speicherplatz (größte
  Verzeichnisse/Dateien, „Speicherfresser", Docker, Volumes, MC-Welten/Logs),
  RAM & Top-Prozesse, Docker-/systemd-/Kern-Dienste, Netzwerk, Sicherheit
  (Firewall, Logins), Paket-Updates, Log-Fehler, SMART/Sensoren, TLS-Zertifikate,
  Backups.
- **Bericht ins Repo:** committet einen Kurzbericht nach
  [`server-logs/health/`](../server-logs/health/) (`latest.md`, `latest.json`,
  `history/<ts>.json`) inkl. **Lösch- und Optimierungsvorschlägen** und einer
  Status-Ampel (`OK`/`WARN`/`CRIT`). Der Festplatten-/RAM-Status nutzt dieselben
  Schwellen wie unter [Monitoring-Empfehlungen](#monitoring-empfehlungen)
  (Warnung > 80 %, kritisch > 90 %).
- **Wartung (optional, Modus `maintain`/`full`):** installiert sinnvolle Updates
  (`all`/nur `security`/`none`), entfernt verwaiste Pakete/alte Kernel, dampft
  Journald ein und räumt Docker gefahrlos auf (**ohne** `-a`/`--volumes` – Welten,
  Volumes, Datenbanken und Backups bleiben unangetastet). `--dry-run` zeigt alles
  nur an.
- **Reboot (nur wenn freigegeben):** Nur wenn Variable
  `MAINTENANCE_ALLOW_REBOOT = true` **und** die Pterodactyl-Secrets gesetzt sind
  **und** die Reboot-Politik greift (`auto` = nur wenn `reboot-required`, `force`,
  `never`), führt der Workflow aus: **Spieler vorwarnen → alle Server sauber
  stoppen → Host neustarten → Rückkehr abwarten → Server wieder starten**. Da
  Wings nach dem Reboot automatisch startet und der Pterodactyl-Schedule die
  Server ohnehin hochfährt, ist die Wiederherstellung doppelt abgesichert.

> **Kollisions-Hinweis:** Die Cron-Zeit so wählen, dass sie **nicht** mit dem
> Pterodactyl-Neustart-Schedule zusammenfällt. Der Workflow warnt und stoppt die
> Server selbst sauber, bevor er den Host neustartet.

---

## Notfall-Befehle

### Einzelnen Server stoppen

```bash
docker compose stop <servername>
# Beispiel:
docker compose stop mining
```

### Proxy neu starten

```bash
docker compose restart velocity
```

> **Achtung:** Ein Proxy-Neustart trennt alle verbundenen Spieler. Vorher Warnung senden!

### Notfall-Bann

```bash
# Über die Velocity- oder Server-Konsole:
docker exec -it velocity rcon-cli ban <Spielername> <Grund>
```

### Alle Server sofort stoppen

```bash
docker compose down
```

---

## Monitoring-Empfehlungen

Für das Netzwerk werden zwei Ebenen unterschieden:

- **Plan** für Web-Analytics und Langzeitstatistiken
- **Velocity Proxy Exporter** für technisches Live-Monitoring über Prometheus

Die konkrete Metrik-Definition und die Prometheus-Beispielkonfiguration liegen unter:

- `docs/infrastructure/VELOCITY_EXPORTER.md`
- `docs/infrastructure/prometheus/velocity-proxy.yml`
- `docs/infrastructure/prometheus/velocity-proxy-alerts.yml`

### TPS (Ticks pro Sekunde)

| Stufe       | Schwellenwert | Aktion                                      |
| ----------- | ------------- | ------------------------------------------- |
| **Normal**  | ≥ 18 TPS     | Keine Aktion erforderlich                   |
| **Warnung** | < 18 TPS     | Ursache untersuchen (Entities, Redstone, Plugins) |
| **Kritisch**| < 15 TPS     | Sofortige Maßnahmen einleiten               |

### RAM (Arbeitsspeicher)

| Stufe       | Schwellenwert | Aktion                                      |
| ----------- | ------------- | ------------------------------------------- |
| **Normal**  | ≤ 80%        | Keine Aktion erforderlich                   |
| **Warnung** | > 80%         | Speicherverbrauch analysieren, ggf. GC erzwingen |
| **Kritisch**| > 90%         | Server-Neustart planen, Speicherlecks prüfen |

### Disk (Festplatte)

| Stufe       | Schwellenwert | Aktion                                      |
| ----------- | ------------- | ------------------------------------------- |
| **Normal**  | ≤ 80%        | Keine Aktion erforderlich                   |
| **Warnung** | > 80%         | Alte Logs und Backups aufräumen              |

### Spielerzahl

- Spielerzahlen regelmäßig erfassen und für **Kapazitätsplanung** nutzen.
- Peak-Zeiten identifizieren, um Ressourcen entsprechend zu skalieren.
- Bei konstant hoher Auslastung zusätzliche Server-Instanzen in Betracht ziehen.

### Proxy-spezifische Live-Metriken

Für den **Velocity Proxy** sollen zusätzlich diese Live-Metriken überwacht werden:

- Proxy online/offline (`festas_velocity_up`)
- Gesamtspieler online (`festas_velocity_players_online`)
- Spieler je Backend (`festas_velocity_players_by_backend`)
- Backend-Erreichbarkeit (`festas_velocity_backend_up`)
- Backend-Latenz (`festas_velocity_backend_ping_ms`)
- Login-Fehler (`festas_velocity_login_failures_total`)
- Fallbacks / Kicks zur Lobby (`festas_velocity_fallback_total`, `festas_velocity_kicked_to_lobby_total`)

---

## Alerting

Für die Benachrichtigung bei kritischen Ereignissen wird eine **Discord-Webhook-Integration** empfohlen.

- Alerts für TPS-Drops, hohe RAM-Auslastung und Festplattenprobleme einrichten.
- Alerts für Proxy-Ausfall, Backend-Ausfall, hohe Backend-Pings und Login-Fehler einrichten.
- Webhook-URL im Monitoring-Tool hinterlegen.
- Verschiedene Kanäle für Warnungen und kritische Alerts verwenden.

Die Webhook-URL wird im jeweiligen Monitoring-Tool konfiguriert (z. B. Grafana, Prometheus Alertmanager oder ein eigenes Skript). Dort die URL unter den Benachrichtigungseinstellungen hinterlegen.

```
Beispiel-Webhook-Payload:
{
  "content": "⚠️ **WARNUNG**: TPS auf Survival-Server bei 16.5 – Untersuchung empfohlen!"
}
```

---

## Weiterführende Dokumentation

- [Architektur-Übersicht](ARCHITECTURE.md)
- [Backup-Strategie](infrastructure/BACKUPS.md)
- [Notfall-Wiederherstellung](DISASTER_RECOVERY.md)
