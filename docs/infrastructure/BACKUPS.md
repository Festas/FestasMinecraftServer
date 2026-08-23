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
| Prison   | `world`, Mine-Bereiche                 | Täglich     |

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
# WICHTIG: Credentials über --defaults-extra-file bereitstellen, NICHT über --password
# Erstelle /etc/mysql/backup.cnf mit [client] user=... password=...
mariadb-dump --all-databases \
  --defaults-extra-file=/etc/mysql/backup.cnf \
  --single-transaction \
  --routines \
  --triggers \
  --host=172.25.0.1 \
  | gzip > /path/to/backup/mariadb_full_$(date +%Y-%m-%d).sql.gz
```

### Redis (172.18.0.1:6380)

**Tägliche Snapshots:** Redis RDB-Snapshot (`BGSAVE`)

```bash
# RDB-Snapshot im Container erzeugen (Passwort aus der gemounteten redis.conf)
docker exec festas-redis sh -c \
  'redis-cli -a "$(sed -n "s/^requirepass //p" /usr/local/etc/redis/redis.conf)" --no-auth-warning BGSAVE'

# Snapshot-Datei sichern (Docker-Volume neben der Compose-Datei)
cp ~/festas-redis/redis-data/dump.rdb /path/to/backup/redis_$(date +%Y-%m-%d).rdb
```

### Wiederherstellung – MariaDB

```bash
# 1. Alle betroffenen Server stoppen

# 2. Vollständiges Backup wiederherstellen
# WICHTIG: Credentials über --defaults-extra-file, NICHT über --password
gunzip < /path/to/backup/mariadb_full_YYYY-MM-DD.sql.gz \
  | mariadb --defaults-extra-file=/etc/mysql/backup.cnf --host=172.25.0.1

# 3. Inkrementelle Backups in chronologischer Reihenfolge einspielen
mariadb-backup --prepare --target-dir=/path/to/full-backup \
  --incremental-dir=/path/to/incremental-backup-N

# 4. Server starten
```

### Wiederherstellung – Redis

```bash
# 1. Redis-Container stoppen
docker compose -f ~/festas-redis/docker-compose.redis.yml down

# 2. RDB-Datei ersetzen (Docker-Volume)
cp /path/to/backup/redis_YYYY-MM-DD.rdb ~/festas-redis/redis-data/dump.rdb

# 3. Redis-Container starten
docker compose -f ~/festas-redis/docker-compose.redis.yml up -d
```

---

## 3. Konfigurations-Backups

Alle Plugin-Konfigurationen werden über **dieses Git-Repository** versioniert. Jede Änderung wird per Commit nachverfolgt.

### Abgedeckte Verzeichnisse

- `proxy/` – Velocity-Proxy-Konfigurationen
- `lobby/` – Lobby-Server-Plugins
- `survival/` – Survival-Server-Plugins
- `skyblock/` – Skyblock-Server-Plugins
- `mining/` – Prison-Server-Plugins

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
- [Architektur-Dokumentation](../ARCHITECTURE.md)

---

**Letzte Aktualisierung:** 2026-04-10
