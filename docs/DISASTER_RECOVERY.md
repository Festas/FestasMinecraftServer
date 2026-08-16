# Notfall-Wiederherstellung (Disaster Recovery)

Dieses Dokument beschreibt die Wiederherstellungsverfahren für das Minecraft-MMO-Netzwerk im Katastrophenfall.

> **ℹ️ Stand 26.2:** Priorität bei der Wiederherstellung haben die aktiven Server **Lobby** und **Survival** sowie der **Proxy**. Für die auslaufenden Server **Skyblock** und **RPG** geht es primär um **Datensicherung vor der Abschaltung** (Ersatz durch zwei neue Server).

---

## Übersicht

| Kennzahl                          | Zielwert   |
| --------------------------------- | ---------- |
| **RTO** (Recovery Time Objective) | 1 Stunde   |
| **RPO** (Recovery Point Objective)| 1 Stunde   |

- **RTO:** Maximale Zeit, bis der Betrieb wiederhergestellt sein muss.
- **RPO:** Maximaler Datenverlust, der toleriert werden kann (gemessen in Zeit seit dem letzten Backup).

---

## Szenarien und Maßnahmen

### 1. Server-Crash

Ein einzelner Spielserver oder der Proxy ist abgestürzt.

**Maßnahmen:**

1. Server über Docker neu starten:
   ```bash
   docker compose restart <servername>
   ```
2. Logs auf Fehlerursache analysieren:
   ```bash
   docker compose logs --tail=200 <servername>
   ```
3. Konfigurationsdateien auf fehlerhafte Änderungen prüfen.
4. Bei wiederkehrenden Crashes: Plugin-Konflikte und Speicherauslastung untersuchen.

---

### 2. Datenbank-Ausfall

MariaDB oder Redis sind nicht erreichbar oder beschädigt.

**Maßnahmen:**

1. Datenbank-Container prüfen und ggf. neu starten:
   ```bash
   docker compose restart mariadb redis
   ```
2. Falls die Datenbank beschädigt ist, letztes Backup einspielen (siehe [Wiederherstellungs-Prozeduren](#datenbank-wiederherstellen)).
3. **Alle Spielserver neu starten**, damit die Verbindungen zur Datenbank neu aufgebaut werden.
4. Datenintegrität prüfen und HuskSync-Status verifizieren.

---

### 3. Datenverlust

Spielerdaten, Weltdaten oder Konfigurationen sind verloren gegangen.

**Maßnahmen:**

1. Letztes verfügbares Backup wiederherstellen (siehe [Wiederherstellungs-Prozeduren](#welt-daten-wiederherstellen)).
2. HuskSync-Synchronisierung prüfen, um sicherzustellen, dass Spielerdaten konsistent sind:
   ```bash
   docker exec -it lobby rcon-cli husksync status
   ```
3. Betroffene Spieler informieren und ggf. Kompensation anbieten.
4. Ursache des Datenverlusts identifizieren und beheben.

---

### 4. Config-Fehler

Fehlerhafte Konfigurationsänderungen verursachen Probleme.

**Maßnahmen:**

1. Letzte funktionierende Version über Git wiederherstellen:
   ```bash
   git log --oneline -10
   git revert <commit-hash>
   ```
2. Neu deployen über GitHub Actions:
   ```bash
   # Push des Reverts löst automatisch den Deploy-Workflow aus
   git push origin main
   ```
3. Betroffene Server neu starten.
4. Änderungen zukünftig in einer Testumgebung validieren.

---

### 5. Kompromittierung

Der Server wurde möglicherweise kompromittiert (unbefugter Zugriff).

**Maßnahmen:**

1. **Server sofort isolieren:**
   ```bash
   docker compose down
   ```
2. **Alle Credentials rotieren:**
   - Datenbank-Passwörter ändern
   - Redis-Passwort ändern
   - API-Schlüssel erneuern
   - RCON-Passwörter ändern
3. **`.gitignore` prüfen** – Sicherstellen, dass keine Secrets im Repository liegen.
4. Zugriffslogs analysieren, um den Angriffsvektor zu identifizieren.
5. System von einem sauberen Backup wiederherstellen.
6. Sicherheitsmaßnahmen verschärfen (Firewall-Regeln, SSH-Konfiguration).

---

## Wiederherstellungs-Prozeduren

### Datenbank wiederherstellen

#### MariaDB

```bash
# Datenbank-Container stoppen
docker compose stop mariadb

# Backup einspielen (Passwort wird interaktiv abgefragt)
docker exec -i mariadb mysql -u root -p < backup/mariadb_backup.sql

# Alternativ: Credentials-Datei verwenden (sicherer, kein Passwort in der Shell-History)
# docker exec -i mariadb mysql --defaults-extra-file=/etc/mysql/credentials.cnf < backup/mariadb_backup.sql

# Container neu starten
docker compose restart mariadb
```

#### Redis

```bash
# Redis-Container stoppen
docker compose stop redis

# Redis-Snapshot wiederherstellen
cp backup/dump.rdb ./redis-data/dump.rdb

# Container neu starten
docker compose start redis
```

---

### Welt-Daten wiederherstellen

```bash
# Betroffenen Server stoppen
docker compose stop <servername>

# Aktuelle (beschädigte) Welt sichern
mv ./server-data/<servername>/world ./server-data/<servername>/world_beschaedigt

# Backup-Welt kopieren
cp -r backup/worlds/<servername>/world ./server-data/<servername>/world

# Server neu starten
docker compose start <servername>
```

> **Hinweis:** Nach der Wiederherstellung von Weltdaten muss die HuskSync-Konsistenz überprüft werden.

---

### Plugin-Konfiguration wiederherstellen

```bash
# Letzten funktionierenden Stand aus Git auschecken
git checkout <commit-hash> -- plugins/

# Über GitHub Actions deployen
git add plugins/
git commit -m "Restore: Plugin-Konfiguration auf funktionierenden Stand zurückgesetzt"
git push origin main

# Betroffene Server neu starten
docker compose restart <servername>
```

---

## Kontaktliste

| Rolle                  | Name             | Kontakt              |
| ---------------------- | ---------------- | -------------------- |
| **Server-Administrator** | _[Name eintragen]_ | _[E-Mail/Discord]_ |
| **Backup-Verantwortlicher** | _[Name eintragen]_ | _[E-Mail/Discord]_ |
| **Entwickler**         | _[Name eintragen]_ | _[E-Mail/Discord]_ |
| **Hosting-Anbieter**   | _[Name eintragen]_ | _[Support-URL]_     |

> **Wichtig:** Diese Liste aktuell halten und sicherstellen, dass alle Kontakte erreichbar sind.

---

## Post-Mortem-Template

Nach jedem schwerwiegenden Vorfall sollte ein Post-Mortem erstellt werden:

```markdown
# Post-Mortem: [Titel des Vorfalls]

## Datum und Uhrzeit
- **Beginn:** [JJJJ-MM-TT HH:MM]
- **Ende:** [JJJJ-MM-TT HH:MM]
- **Dauer:** [X Stunden/Minuten]

## Zusammenfassung
[Kurze Beschreibung des Vorfalls in 2–3 Sätzen.]

## Auswirkungen
- **Betroffene Server:** [z. B. RPG, Lobby]
- **Betroffene Spieler:** [Anzahl]
- **Datenverlust:** [Ja/Nein – Details]

## Ursache
[Detaillierte Beschreibung der Ursache.]

## Zeitlicher Ablauf
| Uhrzeit | Ereignis |
| ------- | -------- |
| HH:MM   | [Beschreibung] |
| HH:MM   | [Beschreibung] |

## Ergriffene Maßnahmen
1. [Maßnahme 1]
2. [Maßnahme 2]

## Präventive Maßnahmen
1. [Maßnahme, um zukünftige Vorfälle zu verhindern]
2. [Maßnahme, um zukünftige Vorfälle zu verhindern]

## Verantwortlich
- **Erstellt von:** [Name]
- **Geprüft von:** [Name]
```

---

## Weiterführende Dokumentation

- [Betriebshandbuch](OPERATIONS.md)
- [Architektur-Übersicht](ARCHITECTURE.md)
- [Backup-Strategie](infrastructure/BACKUPS.md)
