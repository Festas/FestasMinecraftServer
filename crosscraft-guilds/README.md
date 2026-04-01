# CrossCraft Guilds

Ein vollständiges Cross-Server Gilden-System für MinecraftMMO, gebaut als Gradle Multi-Module Java Projekt.

## Module

| Modul | Beschreibung |
|-------|--------------|
| `api` | Öffentliche Interfaces und Enums (Guild, GuildMember, GuildRank, GuildBank, GuildAPI) |
| `common` | Gemeinsame Logik: Datenbankzugriff (HikariCP), Redis (Jedis), Konfiguration, Datenmodelle |
| `paper` | Paper/Spigot Plugin: Befehle, GUIs, Hooks, Listener |
| `velocity` | Velocity Proxy Plugin: Cross-Server Chat, Plugin-Messaging |

## Voraussetzungen

- Java 21+
- MySQL 8.0+ (oder MariaDB 10.6+)
- Redis 6.0+
- Paper 1.21.4+
- Velocity 3.4.0+ (für Cross-Server-Features)

## Installation

### 1. Kompilieren

```bash
./gradlew build
```

Die fertigen JARs liegen in:
- `paper/build/libs/paper-1.0.0.jar` → Paper-Server `plugins/`
- `velocity/build/libs/velocity-1.0.0.jar` → Velocity `plugins/`

### 2. Datenbank einrichten

```sql
CREATE DATABASE minecraft_guilds;
CREATE USER 'guilds'@'localhost' IDENTIFIED BY 'sicheres_passwort';
GRANT ALL ON minecraft_guilds.* TO 'guilds'@'localhost';
```

Das Schema wird beim ersten Start automatisch erstellt.

### 3. Konfiguration

Passe `plugins/CrossCraftGuilds/config.yml` an:

```yaml
database:
  host: "localhost"
  database: "minecraft_guilds"
  username: "guilds"
  password: "sicheres_passwort"

redis:
  host: "localhost"
  password: ""
```

## Features

### Gilden-System
- Gilden erstellen, auflösen, verlassen
- Mitglieder einladen, rauswerfen
- Rang-System: Rekrut → Mitglied → Offizier → Anführer
- Gildenbank mit Multi-Währungs-Unterstützung
- Level-System mit XP-Progression (bis Level 50)

### Cross-Server
- Redis Pub/Sub für Echtzeit-Events
- Gildenchat über alle Server
- Plugin-Messaging über Velocity

### GUIs
- Haupt-Menü (`/guild menu`)
- Mitgliederliste mit Spieler-Köpfen
- Gildenbank
- Paginierende Gildenliste

### Befehle

| Befehl | Beschreibung |
|--------|--------------|
| `/guild create <name> [tag]` | Neue Gilde gründen |
| `/guild invite <spieler>` | Spieler einladen |
| `/guild info [gilde]` | Gildeninfo anzeigen |
| `/guild chat <nachricht>` | Gildenchat |
| `/guild bank [deposit\|withdraw]` | Gildenbank |
| `/guild list` | Alle Gilden auflisten |
| `/guild leave` | Gilde verlassen |
| `/guild kick <spieler>` | Spieler rauswerfen |
| `/guild promote <spieler>` | Spieler befördern |
| `/guild demote <spieler>` | Spieler degradieren |
| `/guild disband` | Gilde auflösen (mit Bestätigung) |
| `/guild menu` | GUI öffnen |
| `/gc <nachricht>` | Gildenchat-Kurzbefehl |

### PlaceholderAPI

| Platzhalter | Beschreibung |
|-------------|--------------|
| `%guild_name%` | Gildenname |
| `%guild_tag%` | Gildentag |
| `%guild_level%` | Gildenlevel |
| `%guild_rank%` | Rang des Spielers |
| `%guild_members%` | Aktuelle Mitgliederzahl |
| `%guild_max_members%` | Maximale Mitgliederzahl |
| `%guild_xp%` | Aktuelle Gilden-XP |
| `%guild_leader%` | Name des Anführers |
| `%guild_progress%` | XP-Fortschritt in % |

### Optionale Hooks (Soft-Dependencies)

| Plugin | Integration |
|--------|-------------|
| MMOCore | Klassensynergie-Boni |
| MythicMobs | XP beim Boss-Kill |
| MythicDungeons | XP beim Dungeon-Abschluss |
| CoinsEngine | Gildenbank-Währungsintegration |
| PlaceholderAPI | Platzhalter-Support |

## Architektur

```
                    ┌─────────────────┐
                    │   GuildAPI      │  (Singleton)
                    │   (Interface)   │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
     ┌────────┴───────┐  ┌───┴────┐  ┌─────┴──────┐
     │  GuildRepository│  │ Redis  │  │ GuildLevel │
     │  (Async CRUD)   │  │Broker  │  │ Manager    │
     └────────┬────────┘  └───┬────┘  └────────────┘
              │               │
     ┌────────┴────────┐  ┌───┴────────────┐
     │  DatabaseManager│  │ RedisManager   │
     │  (HikariCP)     │  │ (Jedis Pool)   │
     └────────┬────────┘  └───┬────────────┘
              │               │
         MySQL/MariaDB      Redis
```

## Entwicklung

### API verwenden

```java
// GuildAPI ist ein Singleton
GuildAPI api = GuildAPI.getInstance();

// Gilde erstellen
api.createGuild(player.getUniqueId(), "MeineGilde", "MG")
    .thenAccept(opt -> opt.ifPresent(guild -> 
        player.sendMessage("Gilde erstellt: " + guild.getName())));

// Spieler-Gilde abfragen
api.getPlayerGuild(player.getUniqueId())
    .thenAccept(opt -> {
        if (opt.isPresent()) {
            Guild guild = opt.get();
            int level = guild.getLevel();
            // ...
        }
    });

// XP vergeben
api.awardXP(guild.getId(), player.getUniqueId(), 100L);
```

## Lizenz

MIT License - siehe LICENSE für Details.
