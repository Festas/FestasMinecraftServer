# server-maintenance

Umfassender **Wartungs-, Analyse- und Health-Workflow** für den Linux-Host, auf
dem die Minecraft-Server (Docker + Pterodactyl/Wings) laufen.

Der Workflow läuft **wöchentlich** (Mittwoch, tief in der Nacht) und ist jederzeit
**manuell auslösbar**. Er

- analysiert **alle Bereiche** des Servers (nicht nur Speicher) und macht breite
  Health-Checks,
- schreibt einen **Kurzbericht** (Exec-Summary + Details) nach
  [`server-logs/health/`](../../server-logs/health/) ins Repo,
- gibt **konkrete Lösch- und Optimierungsvorschläge**,
- installiert optional **sinnvolle Updates** und räumt sicher auf,
- und kann – nur wenn ausdrücklich freigegeben – die Minecraft-Server
  **vorwarnen, sauber herunterfahren, den Host neustarten** und die Server
  danach **wieder starten**.

---

## Bestandteile

| Datei | Läuft auf | Zweck |
|---|---|---|
| `festas-maintenance.sh` | **Server** (per SSH hochgeladen) | Sämtliche OS-Analyse, Health-Checks und Wartung; erzeugt `report.md` + `report.json`. |
| `ptero_control.py` | **GitHub-Runner** | Steuert die Minecraft-Server über die Pterodactyl-**Client-API** (vorwarnen, sauber stoppen, wiederherstellen). Nur Python-Standardbibliothek. |
| `report_summary.py` | **GitHub-Runner** | Rendert aus `report.json` die GitHub-Job-Summary. |
| `.github/workflows/server-maintenance.yml` | **GitHub-Runner** | Orchestriert alles: hochladen → ausführen → Bericht committen → ggf. Reboot-Sequenz. |

---

## Was analysiert wird

Der Bericht (`report.md`) enthält u. a.:

- **System:** OS, Kernel, Virtualisierung, Uptime, Load.
- **Speicherplatz:** `df`, Inodes, größte Verzeichnisse/Dateien (zeitlich
  begrenzt gescannt), bekannte „Speicherfresser“ (APT-Cache, Journald, Docker,
  alte Kernel, Backups, Crash-Reports …).
- **Docker:** `docker system df`, Container-Status, ungesunde/flappende Container.
- **Pterodactyl/Wings:** Dienststatus, Größe der Server-Volumes.
- **Minecraft-Server:** Welt-, Log- und Plugin-Größen je Server (aus den
  `SERVER_PATH_*`-Secrets abgeleitet).
- **Arbeitsspeicher & Prozesse:** `free`, Top-Prozesse nach RAM/CPU, OOM-Events.
- **Dienste & Health:** fehlgeschlagene systemd-Units, Kern-Dienste, Zeit-Sync.
- **Netzwerk:** offene Ports, aktive Verbindungen, DNS/öffentliche IP.
- **Sicherheit:** Firewall, fehlgeschlagene Logins, letzte Anmeldungen.
- **Updates:** aktualisierbare Pakete, Sicherheitsupdates, Reboot-Bedarf.
- **Logs:** häufigste Fehler/Warnungen (7 Tage), Kernel-I/O-Fehler.
- **Datenträger-Gesundheit:** SMART-Status, Temperatursensoren (falls verfügbar).
- **TLS-Zertifikate:** Restlaufzeit der konfigurierten Domains.
- **Backups:** Heuristik zum jüngsten Backup.
- **Aufräum-Kandidaten:** priorisierte Tabelle mit geschätztem Platzgewinn.

Jeder Lauf verdichtet die Kennzahlen in **`report.json`** (maschinenlesbar) und
schreibt einen **Trend** (Vergleich zum letzten Lauf) fort.

---

## Sicherheitsmodell (was der Workflow **nie** anfasst)

- **`analyze`** verändert **nichts**.
- **`maintain`/`full`** installieren Updates und räumen auf – aber ausschließlich
  gefahrlose Dinge:
  - APT-Updates (`all`, nur `security`, oder `none`), `autoremove --purge`,
    Paketcache leeren.
  - Journald auf Zeit/Größe eindampfen.
  - `docker system prune -f` **ohne** `-a` und **ohne** `--volumes` → genutzte
    Images, Container-Daten und **Volumes bleiben erhalten** (Pterodactyl-Server
    werden nicht angetastet).
  - `systemd-tmpfiles --clean` nach System-Policy.
- **Niemals** werden Welten, `playerdata`, Datenbanken, Backups oder
  Docker-**Volumes** gelöscht.
- Jeder verändernde Schritt respektiert `--dry-run`.

---

## Reboot-Sequenz (nur wenn freigegeben)

Ein Reboot passiert **nur**, wenn **alle** Bedingungen erfüllt sind:

1. Repository-Variable **`MAINTENANCE_ALLOW_REBOOT = true`** (harter Schalter).
2. Pterodactyl-Secrets (`PTERODACTYL_URL`, `PTERODACTYL_API_KEY`) sind gesetzt –
   sonst kein sauberer Stopp und damit **kein** Reboot.
3. Die **Reboot-Politik** trifft zu:
   - `never` – nie (Default für manuelle Läufe).
   - `auto` – nur wenn nach Updates `/var/run/reboot-required` existiert.
   - `force` – immer.

Ablauf, wenn ein Reboot ansteht:

1. **Vorwarnen:** `ptero_control.py countdown` sendet mehrere Broadcasts
   (Default `10,5,1` Minuten). Backend-Server erhalten `say …`, der Proxy
   `alert …`.
2. **Sauber stoppen:** `graceful-stop` schickt jedem laufenden Server ein
   `stop`-Signal, wartet bis `offline` (Timeout → `kill`) und **sichert vorher**,
   welche Server liefen.
3. **Bericht ist bereits committet** (passiert vor dem Reboot, da die
   SSH-Verbindung beim Neustart abbricht).
4. **Reboot:** `sudo shutdown -r +1` auf dem Host.
5. **Warten**, bis der Host per SSH wieder erreichbar ist.
6. **Wiederherstellen:** die zuvor laufenden Server werden gestartet
   (idempotent – falls der Pterodactyl-Schedule sie schon gestartet hat, schadet
   es nicht). Dieser Schritt ist `continue-on-error`.

> **Kollision mit dem Pterodactyl-Neustart-Schedule vermeiden:** Der Workflow
> stoppt die Server selbst sauber und startet sie wieder. Lege die Cron-Zeit
> (siehe unten) so, dass sie **nicht** mit deinem Pterodactyl-Schedule
> zusammenfällt. Da Wings nach dem Host-Reboot automatisch startet und dein
> Pterodactyl-Schedule die Server ohnehin wieder hochfährt, ist die
> Wiederherstellung doppelt abgesichert.

---

## Zeitplan & Zeitzone

- Cron im Workflow: **`0 2 * * 3`** = Mittwoch **02:00 UTC**.
- GitHub-Cron ist **immer UTC**. 02:00 UTC ≈ **03:00 Uhr** (Winter, CET) bzw.
  **04:00 Uhr** (Sommer, CEST) in Berlin – also mitten in der Nacht.
- Zum Ändern die `cron`-Zeile in
  [`.github/workflows/server-maintenance.yml`](../../.github/workflows/server-maintenance.yml)
  anpassen. Wähle einen Zeitpunkt **ohne** Überschneidung mit deinem
  Pterodactyl-Neustart-Schedule.

---

## Einrichtung

1. **Secrets** setzen (falls noch nicht vorhanden), siehe
   [`SECRETS.md`](../../SECRETS.md):
   - SSH: `SSH_HOST`, `SSH_USER`, `SSH_PRIVATE_KEY` (der Nutzer braucht
     **passwortloses `sudo`**).
   - Commit-Push: `PERSONAL_TOKEN`.
   - Server-Pfade: `SERVER_PATH_LOBBY/PROXY/SURVIVAL/SKYBLOCK/RPG` (optional, für
     die MC-spezifische Analyse).
   - Nur für Reboot: `PTERODACTYL_URL`, `PTERODACTYL_API_KEY`.
2. **Variablen** (optional) unter *Settings → Secrets and variables → Actions →
   Variables* setzen (Defaults siehe Tabelle in [`SECRETS.md`](../../SECRETS.md)):
   - **`MAINTENANCE_ALLOW_REBOOT`** – erst auf `true`, wenn du Reboots wirklich
     erlauben willst.
   - `MAINTENANCE_SCHEDULED_MODE`, `MAINTENANCE_SCHEDULED_APT`,
     `MAINTENANCE_SCHEDULED_REBOOT`, `MAINTENANCE_WARN_STEPS`,
     `MAINTENANCE_CHECK_DOMAINS`.

**Empfohlenes Vorgehen zum Scharfschalten:**

1. Erst manuell mit **`mode = analyze`** laufen lassen und den Bericht prüfen.
2. Dann **`mode = maintain`, `dry_run = true`** – zeigt, was Wartung/Cleanup täte.
3. Dann **`mode = maintain`** ohne Dry-Run.
4. Zuletzt Reboots erlauben: `MAINTENANCE_ALLOW_REBOOT = true` und einen Lauf mit
   **`reboot = force`, `dry_run = true`** testen (übt die Warn-/Stopp-Logik ohne
   echten Neustart), bevor du dem wöchentlichen `auto`-Reboot vertraust.

---

## Manuelle Auslösung

Über *Actions → „Server-Wartung & Health-Report“ → Run workflow* mit den Eingaben:

| Eingabe | Werte | Default | Bedeutung |
|---|---|---|---|
| `mode` | `analyze` / `maintain` / `full` | `analyze` | Nur Bericht / + Updates & Cleanup / wie maintain. |
| `apt` | `all` / `security` / `none` | `security` | Umfang der Paket-Updates (nur bei maintain/full). |
| `reboot` | `never` / `auto` / `force` | `never` | Reboot-Politik (zusätzlich zur Freigabe-Variable). |
| `dry_run` | `true` / `false` | `false` | Trockenlauf – nichts verändern. |

Der **wöchentliche** Lauf nutzt statt der Eingaben die `MAINTENANCE_SCHEDULED_*`
Variablen (Defaults: `full` / `all` / `auto`).

---

## Lokale Nutzung / Test

Das Analyse-Skript läuft eigenständig auf dem Server (oder lokal zum Testen –
fehlende Tools werden sauber übersprungen):

```bash
# Nur Analyse, Ausgabe nach /tmp/health:
OUTPUT_DIR=/tmp/health tools/server-maintenance/festas-maintenance.sh run --mode analyze

# Wartung als Trockenlauf (zeigt nur, was passieren würde):
sudo tools/server-maintenance/festas-maintenance.sh run --mode maintain --apt security --dry-run

# Hilfe:
tools/server-maintenance/festas-maintenance.sh --help
```

Den Pterodactyl-Helfer testen:

```bash
export PTERODACTYL_URL="https://panel.example.com"
export PTERODACTYL_API_KEY="ptlc_…"

python3 tools/server-maintenance/ptero_control.py list
python3 tools/server-maintenance/ptero_control.py --dry-run announce --message "Test"
python3 tools/server-maintenance/ptero_control.py --dry-run graceful-stop --save-state /tmp/state.json
```

---

## Umgebungsvariablen des Skripts (Feinjustierung)

`festas-maintenance.sh` liest u. a. diese Variablen (Defaults in Klammern):

| Variable | Default | Zweck |
|---|---|---|
| `OUTPUT_DIR` | `/tmp/festas-health` | Zielordner für `report.md`/`report.json`. |
| `STATE_DIR` | `/var/lib/festas-maintenance` | Ablage für Trend-Historie (Fallback `/tmp`). |
| `CHECK_DOMAINS` | `mc.festas-builds.com` | TLS-Domains (leer = aus). |
| `PTERO_VOLUMES` | `/var/lib/pterodactyl/volumes` | Pterodactyl-Volume-Pfad. |
| `SERVER_PATH_*` | – | Plugins-Pfade der MC-Server (Server-Root = eine Ebene darüber). |
| `MAX_SCAN_SECONDS` | `180` | Zeitbudget je Verzeichnis-Scan. |
| `DISK_WARN_PCT` / `DISK_CRIT_PCT` | `80` / `90` | Schwellen für den Festplatten-Status. |
| `MEM_WARN_PCT` | `80` | Schwelle für den RAM-Status. |

---

## Fehlertoleranz

- Fehlt ein Tool (z. B. `smartctl`, `docker`, `systemctl`), wird der jeweilige
  Abschnitt sauber übersprungen statt abzubrechen.
- Einzelne Server-Fehler in `ptero_control.py` brechen den Gesamtlauf nicht ab.
- Transiente Netzwerkfehler zur Pterodactyl-API werden mehrfach wiederholt.
- Der Workflow schlägt bei `WARN`/`CRIT` **nicht** hart fehl – Befunde stehen im
  committeten Bericht und in der Job-Summary.
