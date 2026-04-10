# Synchronisierung - Infrastruktur

Übersicht über alle Datensynchronisierungs-Mechanismen im MinecraftMMO-Netzwerk. Die Synchronisation stellt sicher, dass Spieler nahtlos zwischen RPG und Skyblock wechseln können und konsistente Daten vorfinden.

---

## Synchronisierungs-Übersicht

| Mechanismus         | Was wird synchronisiert           | Zwischen                  | Methode              |
|---------------------|-----------------------------------|---------------------------|----------------------|
| HuskSync            | Inventar, Health, XP, Effekte    | RPG ↔ Skyblock            | Redis + MariaDB      |
| LuckPerms           | Permissions, Gruppen, Tracks     | Alle 4 Spielserver        | Gemeinsame MariaDB   |
| CoinsEngine         | Währungen (Coins, Tokens)        | RPG ↔ Skyblock            | Gemeinsame MariaDB   |
| GitHub Actions       | Plugin-Konfigurationen           | RPG → Skyblock            | Git Workflow          |

---

## 1. HuskSync – Spielerdaten-Synchronisation

HuskSync synchronisiert Spielerdaten in Echtzeit zwischen dem **RPG-Server** und dem **Skyblock-Server** über Redis (Session-Cache) und MariaDB (`s4_husk`).

### Synchronisierte Daten

- ✅ Haupt-Inventar (36 Slots)
- ✅ Rüstungs-Slots
- ✅ Offhand-Slot
- ✅ Ender-Chest
- ✅ Health / Hunger / Saturation
- ✅ XP-Level und XP-Punkte
- ✅ Potion-Effekte
- ✅ Gamemode
- ✅ Advancement-Progress

### Sync-Ablauf beim Server-Wechsel

```
Spieler auf RPG-Server
        │
        │  /server skyblock
        ▼
1. Spielerdaten → Redis Cache (172.18.0.1)
2. Persistente Daten → MariaDB (s4_husk)
        │
        ▼
3. Server-Wechsel über Velocity Proxy
        │
        ▼
4. Skyblock lädt Daten aus Redis (schnell)
5. Fallback: Daten aus MariaDB (bei Cache-Miss)
6. Spieler spawnt mit synchronisiertem Inventar
```

### Konflikt-Handling

- **Session-Lock:** Ein Spieler kann nur auf einem Server gleichzeitig aktiv sein
- **Timestamp-basiert:** Bei Datenkonflikten gewinnt der neueste Datensatz
- **Connection-Loss:** Daten werden beim Reconnect aus MariaDB geladen

### Konfiguration

```yaml
# Datenbank: s4_husk (MariaDB)
database:
  host: 172.25.0.1
  port: 3306
  database: s4_husk
  username: CHANGE_ME
  password: CHANGE_ME

# Cache: Redis
redis:
  host: 172.18.0.1
  port: 6379
  password: CHANGE_ME
```

---

## 2. GitHub Actions – Plugin-Konfigurations-Sync

Ein automatisierter GitHub-Actions-Workflow synchronisiert Plugin-Konfigurationen vom **RPG-Server** zum **Skyblock-Server**. Der RPG-Server gilt als „Source of Truth" für gemeinsam genutzte Plugins.

### Workflow: `sync-mmo-mythic-to-skyblock.yml`

**Trigger:**
- Automatisch bei Push auf `main` (wenn sich Dateien in den überwachten Ordnern ändern)
- Manuell über `workflow_dispatch`

### Synchronisierte Plugin-Ordner (20 Ordner)

| Kategorie          | Plugins                                                          |
|--------------------|------------------------------------------------------------------|
| MMO Plugins        | `MMOCore`, `MMOItems`                                            |
| Mythic Plugins     | `MythicAchievements`, `MythicDungeons`, `MythicHUD`, `MythicLib`, `MythicMobs`, `MythicRPG` |
| Wirtschaftssystem  | `CoinsEngine`, `DeluxeBazaar`, `GlobalMarketPlus`, `Vault`       |
| Rose Plugins       | `RoseGarden`, `RoseLoot`, `RoseStacker`                          |
| CMI                | `CMI`, `CMILib`                                                  |
| Aurora Plugins     | `Aurora`, `AuroraCollections`                                    |

### Ablauf

```
1. Änderung in rpg/plugins/<Plugin>/
        │
        ▼
2. Push auf main-Branch
        │
        ▼
3. GitHub Actions Workflow startet
        │
        ▼
4. Dateien werden von rpg/plugins/ nach skyblock/plugins/ kopiert
   (vollständiger Sync inkl. Löschungen)
        │
        ▼
5. Änderungen werden committed und gepusht
        │
        ▼
6. Deploy via rsync zum Skyblock-Server (SSH)
   (exkl. .jar, .db, .sqlite, .log, playerdata/, data/, cache/)
```

### Ausgeschlossene Dateien beim Deploy

Der rsync-Deploy schließt folgende Dateien/Ordner aus, um serverspezifische Daten nicht zu überschreiben:
- `*.jar`, `*.jar.disabled` – Plugin-Binaries
- `*.db`, `*.sqlite` – Lokale Datenbanken
- `*.log` – Log-Dateien
- `playerdata/`, `data/`, `cache/` – Spieler- und Cache-Daten
- `world/`, `world_nether/`, `world_the_end/` – Welt-Daten

---

## 3. LuckPerms – Permissions-Synchronisation

LuckPerms nutzt eine **gemeinsame MariaDB-Datenbank** (`s4_perms`), sodass Permissions, Gruppen und Tracks netzwerkweit konsistent sind.

### Betroffene Server

| Server   | Zugriff auf `s4_perms` |
|----------|------------------------|
| Proxy    | ✅ Ja                  |
| Lobby    | ✅ Ja                  |
| Survival | ✅ Ja                  |
| Skyblock | ✅ Ja                  |
| RPG      | ✅ Ja                  |

### Funktionsweise

- Alle Server lesen und schreiben Permissions in dieselbe Datenbank
- Änderungen werden über den LuckPerms Messaging-Service in Echtzeit an alle Server propagiert
- Kontext-basierte Permissions ermöglichen serverspezifische Regeln (z. B. `server=rpg`)
- Connection-Pool: **10 Verbindungen** pro Server

### Beispiel: Kontext-basierte Permissions

```
/lp user <Spieler> permission set mythicmobs.admin true server=rpg
/lp user <Spieler> permission set superiorskyblock.admin true server=skyblock
```

---

## 4. CoinsEngine – Wirtschafts-Synchronisation

CoinsEngine synchronisiert das Währungssystem zwischen **RPG** und **Skyblock** über eine gemeinsame MariaDB-Datenbank.

### Synchronisierte Währungen

- **Coins** – Hauptwährung
- **Tokens** – Premium-Währung
- Weitere konfigurierte Währungstypen

### Funktionsweise

Beide Server greifen auf dieselben Tabellen in der MariaDB zu. Ein Spieler, der Coins auf dem RPG-Server verdient, hat diese auch auf dem Skyblock-Server zur Verfügung.

---

## 5. Nicht synchronisierte Daten

### Survival-Server – Vollständig unabhängig

Der **Survival-Server** ist bewusst von der MMO-Synchronisation ausgenommen. Dies ist eine Design-Entscheidung:

- ❌ Kein HuskSync (eigenes Inventar, eigene Progression)
- ❌ Eigene Economy (getrennt von RPG/Skyblock CoinsEngine)
- ❌ Eigene Spielerdaten (Jobs, Claims, Ranks)
- ✅ Nur LuckPerms wird über die gemeinsame Datenbank geteilt

### Serverspezifische Daten (nicht synchronisiert)

| Daten                        | Verbleibt auf       |
|-----------------------------|---------------------|
| Skyblock-Islands            | Skyblock            |
| Dungeon-Instanzen           | RPG                 |
| Welt-Daten (Blöcke, Terrain) | Jeweiliger Server  |
| Server-spezifische Achievements | Jeweiliger Server |
| Bluemap-Renderings          | Jeweiliger Server   |
| Survival Economy (Vault)    | Survival            |
| Jobs-Daten                  | Survival            |
| Claims (PlotSquared/GriefPrevention) | Survival  |

---

## Siehe auch

- [Infrastruktur Übersicht](README.md)
- [Datenbanken](DATENBANKEN.md)
- [Backup-Strategien](BACKUPS.md)
- [Architektur-Dokumentation](../ARCHITECTURE.md)

---

**Letzte Aktualisierung:** 2026-04-10
