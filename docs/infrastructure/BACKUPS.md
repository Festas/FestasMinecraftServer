# Backups - Infrastruktur

Backup-Strategie für das MinecraftMMO-Netzwerk. Alle Backups sollten zusätzlich **extern (off-site)** gespeichert werden, um im Falle eines Hardware-Ausfalls oder einer Kompromittierung des Hauptsystems eine Wiederherstellung zu gewährleisten.

---

## Übersicht

| Backup-Typ    | Methode              | Zeitplan                             | Speicherort          |
|---------------|----------------------|--------------------------------------|----------------------|
| Welten        | Datei-Kopie (rsync)  | Täglich um 3:00 Uhr                 | Lokal + Off-Site     |
| Datenbanken   | MariaDB-Dump / Redis | Stündlich (inkrementell), täglich (voll) | Lokal + Off-Site |
| Konfigurationen | Git                | Bei jeder Änderung (Push)            | Dieses Repository    |

---

## 1. Welt-Backups

### Zeitplan

Alle Spielwelten werden **täglich um 3:00 Uhr** gesichert, wenn die Serverlast am niedrigsten ist.

| Server   | Welten                                  | Frequenz    |
|----------|----------------------------------------|-------------|
| Lobby    | `world`                                | Wöchentlich (statische Map) |
| Survival | `world`, `world_nether`, `world_the_end` | Täglich     |
| Skyblock | `world`, Island-Daten                  | Täglich     |
| RPG      | `world`, Dungeon-Instanzen             | Täglich     |

### Verfahren

1. Server wird in den Speichermodus versetzt (`/save-off`)
2. Welt wird auf die Festplatte geschrieben (`/save-all`)
3. Dateien werden mit `rsync` in das Backup-Verzeichnis kopiert
4. Speichermodus wird wiederhergestellt (`/save-on`)
5. Backup wird komprimiert (`.tar.gz`)
6. Komprimiertes Backup wird an den Off-Site-Speicher übertragen

### Wiederherstellung

```bash
# 1. Server stoppen
# 2. Aktuellen Welt-Ordner sichern (umbenennen)
mv /path/to/server/world /path/to/server/world_broken

# 3. Backup entpacken
tar -xzf /path/to/backup/world_YYYY-MM-DD.tar.gz -C /path/to/server/

# 4. Berechtigungen prüfen
chown -R minecraft:minecraft /path/to/server/world

# 5. Server starten
```

> **Hinweis:** Bei der Wiederherstellung gehen alle Änderungen seit dem letzten Backup verloren. Spieler sollten informiert werden.

---

## 2. Datenbank-Backups

### MariaDB (172.25.0.1:3306)

**Inkrementelle Backups:** Stündlich mit `mariadb-backup --incremental`

**Vollständige Backups:** Täglich um 3:00 Uhr mit `mariadb-dump`

```bash
# Vollständiges Backup aller s4_*-Datenbanken
mariadb-dump --all-databases \
  --single-transaction \
  --routines \
  --triggers \
  --host=172.25.0.1 \
  --user=CHANGE_ME \
  --password=CHANGE_ME \
  | gzip > /path/to/backup/mariadb_full_$(date +%Y-%m-%d).sql.gz
```

### Redis (172.18.0.1:6379)

**Tägliche Snapshots:** Redis RDB-Snapshot (`BGSAVE`)

```bash
# RDB-Snapshot erstellen
redis-cli -h 172.18.0.1 BGSAVE

# Snapshot-Datei sichern
cp /var/lib/redis/dump.rdb /path/to/backup/redis_$(date +%Y-%m-%d).rdb
```

### Wiederherstellung – MariaDB

```bash
# 1. Alle betroffenen Server stoppen

# 2. Vollständiges Backup wiederherstellen
gunzip < /path/to/backup/mariadb_full_YYYY-MM-DD.sql.gz \
  | mariadb --host=172.25.0.1 --user=CHANGE_ME --password=CHANGE_ME

# 3. Inkrementelle Backups in chronologischer Reihenfolge einspielen
mariadb-backup --prepare --target-dir=/path/to/full-backup \
  --incremental-dir=/path/to/incremental-backup-N

# 4. Server starten
```

### Wiederherstellung – Redis

```bash
# 1. Redis stoppen
# 2. RDB-Datei ersetzen
cp /path/to/backup/redis_YYYY-MM-DD.rdb /var/lib/redis/dump.rdb
chown redis:redis /var/lib/redis/dump.rdb

# 3. Redis starten
```

---

## 3. Konfigurations-Backups

Alle Plugin-Konfigurationen werden über **dieses Git-Repository** versioniert. Jede Änderung wird per Commit nachverfolgt.

### Abgedeckte Verzeichnisse

- `proxy/` – Velocity-Proxy-Konfigurationen
- `lobby/` – Lobby-Server-Plugins
- `survival/` – Survival-Server-Plugins
- `skyblock/` – Skyblock-Server-Plugins
- `rpg/` – RPG-Server-Plugins

### Wiederherstellung

```bash
# Letzten funktionierenden Stand wiederherstellen
git log --oneline -20
git checkout <COMMIT_HASH> -- <PFAD>

# Oder kompletten Rollback auf einen bestimmten Stand
git revert <COMMIT_HASH>
```

> **Vorteil:** Vollständige Änderungshistorie und Rollback-Möglichkeit auf jeden beliebigen Stand.

---

## Aufbewahrungsfristen (Retention Policy)

| Typ               | Aufbewahrung |
|--------------------|-------------|
| Tägliche Backups   | 7 Tage      |
| Wöchentliche Backups | 30 Tage   |
| Monatliche Backups | 90 Tage     |

### Rotation

- Nach **7 Tagen** werden tägliche Backups gelöscht (außer das wöchentliche Backup)
- Nach **30 Tagen** werden wöchentliche Backups gelöscht (außer das monatliche Backup)
- Nach **90 Tagen** werden monatliche Backups gelöscht

Die Rotation sollte automatisiert über ein Cron-basiertes Skript oder ein Backup-Management-Tool erfolgen.

---

## Off-Site-Speicherung

> **Wichtig:** Backups sollten immer an einem externen Standort (off-site) gespeichert werden, getrennt vom Produktivsystem. Ein lokales Backup schützt nicht vor Hardware-Ausfällen, Ransomware oder physischen Schäden.

Empfohlene Optionen:
- Cloud-Speicher (S3-kompatibel, z. B. AWS S3, Backblaze B2, Hetzner Storage Box)
- Separater physischer Server an einem anderen Standort
- Verschlüsselte Übertragung (GPG oder serverseitiger Verschlüsselung)

---

## Siehe auch

- [Infrastruktur Übersicht](README.md)
- [Datenbanken](DATENBANKEN.md)
- [Synchronisierung](SYNCHRONISIERUNG.md)
- [Architektur-Dokumentation](../ARCHITECTURE.md)

---

**Letzte Aktualisierung:** 2026-04-10
