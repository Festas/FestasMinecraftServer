# Plugin-Referenz - MinecraftMMO Netzwerk

Vollständige Übersicht aller verwendeten Plugins pro Server.

---

## Velocity Proxy Plugins

### Core Plugins

#### CMIV
- **Funktion:** CMI Integration für Velocity
- **Verwendung:** Erlaubt CMI-Befehle über Server hinweg
- **Abhängigkeiten:** CMI auf Backend-Servern

#### ForceResourcepacks
- **Funktion:** Erzwingt Resourcepacks auf Clients
- **Verwendung:** 
  - Custom Items (Oraxen)
  - Custom Models (ModelEngine)
  - Custom Textures
- **Config:** Resourcepack-URL in `config.yml`

#### Geyser-Velocity
- **Funktion:** Bedrock-Spieler-Support über Java-Server
- **Features:**
  - Erlaubt Bedrock-Edition-Spielern (Handy, Konsole, Windows 10) den Beitritt
  - Floodgate-Authentifizierung (kein Java-Account nötig)
  - Resourcepack-Erzwingung auch für Bedrock
  - MOTD-Konfiguration für Bedrock-Clients
- **Config:** `config.yml` (auth-type: floodgate)
- **Abhängigkeit:** Floodgate (für Account-Verknüpfung)

#### LibertyBans
- **Funktion:** Netzwerk-weites Ban-System
- **Features:**
  - IP-Bans
  - Temporary/Permanent Bans
  - Mutes
  - Warnings
  - History-Tracking
- **Datenbank:** MySQL
- **Commands:**
  - `/ban <player> [reason]`
  - `/tempban <player> <time> [reason]`
  - `/mute <player> [reason]`
  - `/unban <player>`

#### MiniMOTD
- **Funktion:** Custom MOTD (Message of the Day)
- **Features:**
  - Mehrere MOTD-Varianten
  - Platzhalter-Support
  - Hover-Text
  - RGB-Farben
- **Config:** `main.conf`

#### MySQL-And-Configurate
- **Funktion:** MySQL-Datenbank-Integration für Velocity
- **Verwendung:** Zentrale Datenbank-Verbindung für Plugins

#### PAF (PartyAndFriendsGUI)
- **Funktion:** Party & Freundesliste über Server hinweg
- **Features:**
  - Freundschaftsanfragen
  - Party-System (Server-übergreifend)
  - Chat-Nachrichten
  - Online-Status
- **Commands:**
  - `/friend add <player>`
  - `/party invite <player>`
  - `/party chat <message>`

#### Plan
- **Funktion:** Netzwerk-weite Analyse und Statistiken
- **Features:**
  - Spieler-Aktivität
  - Server-Performance
  - Plugin-Performance
  - Web-Dashboard
- **Web-UI:** `http://<server-ip>:8804`
- **Datenbank:** MySQL

#### SkinsRestorer
- **Funktion:** Custom Skins für Spieler (auch Offline-Mode)
- **Features:**
  - Skin-Änderung per Command
  - Skin-URL-Support
  - Offline-Mode-Kompatibilität
- **Commands:**
  - `/skin set <player|url>`
  - `/skin clear`

#### TAB
- **Funktion:** Custom TAB-Liste und Scoreboard
- **Features:**
  - Server-übergreifende TAB-Liste
  - Custom Scoreboard
  - Nametags
  - Header/Footer
  - RGB-Farben
  - PlaceholderAPI-Support
- **Config:** `config.yml`, `scoreboard.yml`

#### VelocityScoreboardAPI
- **Funktion:** API für Scoreboard-Management
- **Verwendung:** Backend für TAB und andere Scoreboard-Plugins

---

## Lobby Server Plugins

### Management & Core

#### CMI (Complete Minecraft Integration)
- **Funktion:** Kern-Management-Plugin
- **Features:**
  - Teleport-Commands
  - World-Management
  - Warps
  - Kits
  - Economy (optional, nicht verwendet)
- **Wichtige Commands:**
  - `/warp <name>`
  - `/spawn`
  - `/fly`

#### LuckPerms
- **Funktion:** Permissions-Management
- **Features:**
  - Ranks/Groups
  - Permissions
  - Context-basierte Permissions (Server, World)
  - Web-Editor
- **Web-Editor:** `https://luckperms.net/editor`

### Display & UI

#### FancyNpcs
- **Funktion:** Interaktive NPCs für Navigation
- **Verwendung:**
  - Server-Selector NPCs
  - Info-NPCs
  - Tutorial-NPCs
- **Commands:**
  - `/npc create <name>`
  - `/npc action`

#### DecentHolograms
- **Funktion:** Hologramme für Infos/Anzeigen
- **Features:**
  - Multi-Zeilen-Hologramme
  - PlaceholderAPI-Support
  - Animationen
  - Click-Actions
- **Commands:**
  - `/holo create <name>`
  - `/holo line <name> <line> <text>`

#### DeluxeMenus
- **Funktion:** Custom GUI-Menüs
- **Verwendung:**
  - Server-Selector-GUI
  - Info-Menüs
  - Regel-GUI
- **Config:** YAML-basierte Menü-Definitionen

### Items & Resources

#### Oraxen
- **Funktion:** Custom Items und Texturen
- **Features:**
  - Custom Item Models
  - Custom Textures
  - Custom Blocks
  - Resourcepack-Generation
- **Commands:**
  - `/oraxen reload`
  - `/oraxen give <item>`

### Utility

#### PlaceholderAPI
- **Funktion:** Platzhalter für Nachrichten/Displays
- **Expansions:**
  - Player (Name, UUID, etc.)
  - Server (TPS, Online-Count)
  - Custom Platzhalter
- **Commands:**
  - `/papi parse <player> <placeholder>`

#### ProtocolLib
- **Funktion:** Packet-Manipulation für Custom-Features
- **Verwendung:** Backend für andere Plugins (Holograms, NPCs)

---

## Survival Server Plugins

### Economy & Jobs

#### Jobs
- **Funktion:** Job-System für Economy
- **Jobs:**
  - Miner
  - Woodcutter
  - Builder
  - Farmer
  - Hunter
  - Fischer
- **Features:**
  - Level-System pro Job
  - Geld verdienen durch Aktionen
  - Boni bei höherem Level

#### ShopGUIPlus
- **Funktion:** Shop-GUI für Economy
- **Features:**
  - Item-Kauf/Verkauf
  - Kategorien
  - Custom Preise
  - Dynmaic Economy (optional)

#### Vault
- **Funktion:** Economy API
- **Verwendung:** Backend für Economy-Plugins

#### GlobalMarketPlus
- **Funktion:** Globaler Marktplatz
- **Features:**
  - Spieler können Items verkaufen
  - Auktions-System
  - Marktplatz-GUI

#### PlayerPoints
- **Funktion:** Punkt-System (alternative Währung)

### Land & Claims

#### PlotSquared
- **Funktion:** Plot-basiertes Claiming
- **Features:**
  - Automatische Plot-Generierung
  - Plot-Merging
  - Plot-Rechte
  - Schematic-System

#### GriefPrevention
- **Funktion:** Alternative Claims (Freiform)
- **Features:**
  - Claims mit Golden Shovel
  - Subdivisions
  - Trust-System

### Progression

#### Rankup
- **Funktion:** Rang-Progression-System
- **Features:**
  - Ranks basierend auf Geld
  - Automatisches Rankup
  - Permissions pro Rank

### Mapping & Visualization

#### Bluemap
- **Funktion:** 3D-Web-Karte
- **Features:**
  - Live-Rendering
  - Spieler-Positionen
  - Marker
- **Web-UI:** `http://<server-ip>:8100`

### Core & Management

#### CMI
- **Funktion:** Core Management (siehe Lobby)

#### LuckPerms
- **Funktion:** Permissions (siehe Lobby)

#### EssentialsX
- **Funktion:** Basis-Befehle
- **Features:**
  - /home, /tpa, /spawn
  - Kits
  - Economy (optional)

#### PlaceholderAPI
- **Funktion:** Platzhalter (siehe Lobby)

#### ProtocolLib
- **Funktion:** Packet-Manipulation (siehe Lobby)

---

## Skyblock Server Plugins

### Skyblock Core

#### SuperiorSkyblock2
- **Funktion:** Skyblock Core-System
- **Features:**
  - Island-Management
  - Island-Level-System
  - Coop-Islands
  - Island-Upgrades
  - Warp-System
  - Top-Islands
- **Commands:**
  - `/is create`
  - `/is invite <player>`
  - `/is level`
  - `/is upgrades`

#### JetsMinions
- **Funktion:** Minion-System (Hypixel-Style)
- **Features:**
  - 8 Minion-Typen
  - Health-System (Minions müssen gefüttert werden)
  - Verknüpfte Kisten
  - Permission-basierte Limits
- **Minion-Types:**
  - Miner (Bergbau)
  - Farmer (Ernte)
  - Fisher (Fischen)
  - Lumberjack (Holzfällen)
  - Slayer (Mob-Kampf)
  - Collector (Item-Sammlung)
  - Feeder (Minion-Heilung)
  - Seller (Auto-Verkauf)

### MMO Systems

#### MMOCore
- **Funktion:** Klassen und Skills
- **Features:**
  - 6 Klassen (Krieger, Magier, Assassine, Bogenschütze, Schamane, Beschwörer)
  - Skill-Trees
  - Level-System (1-100)
  - Attributes (Strength, Intelligence, Dexterity)
  - Skills (Active/Passive)
- **Commands:**
  - `/class choose <class>`
  - `/class info`
  - `/skills`

#### MMOItems
- **Funktion:** Custom Items System
- **Features:**
  - Custom Stats (Damage, Defense, etc.)
  - Item-Tiers
  - Special Abilities
  - Item-Sets
- **Config:** YAML-basierte Item-Definitionen
- **Commands:**
  - `/mmoitems browse`
  - `/mmoitems give <type> <id> <player>`

#### MythicLib
- **Funktion:** Library für MMOCore/MMOItems
- **Verwendung:** Backend-Library, keine direkten Commands

### Mobs & Combat

#### MythicMobs (Community Edition)
- **Funktion:** Custom Mobs
- **Features:**
  - Custom Stats
  - Custom Skills
  - Custom Drops
  - Spawn-Mechaniken
- **Commands:**
  - `/mm mobs spawn <mob>`
  - `/mm reload`

### Quests & Progression

#### Aurora
- **Funktion:** Collections & Achievements
- **Features:**
  - Collection-System
  - Achievement-System
  - Milestones

#### AuroraCollections
- **Funktion:** Collection-System (Hypixel-Style)
- **Features:**
  - Sammle X von Y
  - Unlock-Belohnungen
  - Collection-Levels
  - 5 Kategorien: Farming, Mining, Combat, Foraging, Fishing

### Economy

#### CoinsEngine
- **Funktion:** Multi-Währungs-System
- **Features:**
  - Mehrere Währungen (Coins, Gems, Tokens)
  - Currency-Commands
  - Economy-API
- **Commands:**
  - `/coins balance`
  - `/coins pay <player> <amount>`

#### DeluxeBazaar
- **Funktion:** Bazaar-System (Hypixel-Style)
- **Features:**
  - Instant-Buy/Sell
  - Order-System
  - Market-Prices

### Synchronization

#### HuskSync
- **Funktion:** Daten-Synchronisation mit RPG-Server
- **Synchronisiert:**
  - Inventar
  - Ender-Chest
  - Health/Hunger
  - XP
  - MMOCore-Daten (Klassen, Skills)
  - Quest-Progress
- **Datenbank:** MySQL + Redis

### Items & Resources

#### Oraxen
- **Funktion:** Custom Items und Texturen (siehe Lobby)

### UI & Menus

#### DeluxeMenus
- **Funktion:** Custom GUI-Menüs (siehe Lobby)

### Management

#### CMI
- **Funktion:** Core Management (siehe Lobby)

#### LuckPerms
- **Funktion:** Permissions (siehe Lobby)

#### PlaceholderAPI
- **Funktion:** Platzhalter (siehe Lobby)

#### ProtocolLib
- **Funktion:** Packet-Manipulation (siehe Lobby)

---

## RPG Server Plugins

### MythicMobs Ecosystem (Premium)

#### MythicMobs Premium
- **Funktion:** Advanced Custom Mobs/Bosse/Items
- **Premium-Features:**
  - Advanced AI
  - Custom Models (ModelEngine-Integration)
  - Complex Skills
  - Boss-Bars
  - Threat-System
- **Commands:**
  - `/mm mobs spawn <mob>`
  - `/mm items get <item>`
  - `/mm reload`

#### MythicDungeons
- **Funktion:** Instanzierte Dungeons
- **Features:**
  - Dungeon-Instanzen
  - Party-System
  - Difficulty-Levels
  - Custom Loot
- **Commands:**
  - `/md create <dungeon>`
  - `/md join <dungeon>`

#### MythicCrucible
- **Funktion:** Advanced Item Creation
- **Features:**
  - Custom Items mit Stats
  - Item-Tiers
  - Special Effects
  - Item-Upgrades
- **Config:** Crucible-Item-Definitionen

#### MythicRPG
- **Funktion:** RPG-Mechaniken
- **Features:**
  - Level-Skalierung
  - Mob-Scaling
  - Damage-Formeln
  - Level-Zonen

#### MythicAchievements
- **Funktion:** Achievement-System
- **Features:**
  - Custom Achievements
  - Achievement-Trees
  - Rewards

### MMO Systems

#### MMOCore
- **Funktion:** Klassen und Skills (siehe Skyblock)
- **6 Klassen:** Krieger, Magier, Assassine, Bogenschütze, Schamane, Beschwörer

#### MMOItems
- **Funktion:** Custom Items System (siehe Skyblock)

#### MythicLib
- **Funktion:** Library (siehe Skyblock)

### Quests & NPCs

#### BetonQuest
- **Funktion:** Advanced Quest-System
- **Verfügbar auf:** Nur RPG-Server

#### Citizens
- **Funktion:** NPC-System
- **Verfügbar auf:** Nur RPG-Server

### Models & Visuals

#### ModelEngine
- **Funktion:** Custom 3D Models
- **Features:**
  - Custom Mob-Models
  - Custom NPC-Models
  - Animations
  - Resourcepack-Integration
- **Verwendung:**
  - Boss-Models
  - Custom Mounts
  - Custom NPCs

#### LibsDisguises
- **Funktion:** Mob/NPC Verkleidungen
- **Features:**
  - Disguise als Mob
  - Disguise als Spieler
  - Custom Names/Skins
- **Commands:**
  - `/disguise <mob>`
  - `/undisguise`

#### PlayerParticles
- **Funktion:** Partikel-Effekte für Spieler
- **Features:**
  - Trail-Effekte
  - Custom Partikel
  - Cosmetics

### Holograms

#### DecentHolograms
- **Funktion:** Hologramme (siehe Lobby)
- **Verwendung:**
  - Quest-Marker
  - NPC-Namen
  - Info-Hologramme

### Economy

#### CoinsEngine
- **Funktion:** Multi-Währungs-System (siehe Skyblock)

#### PlayerPoints
- **Funktion:** Punkt-System

### Loot & World

#### RoseLoot
- **Funktion:** Custom Loot-Tables
- **Features:**
  - Custom Drops
  - Loot-Tables pro Mob
  - Drop-Chances
  - Conditional Loot

#### RoseGarden
- **Funktion:** World Management
- **Features:**
  - Custom Biomes
  - Custom Structures
  - World-Generation

#### RoseStacker
- **Funktion:** Entity-Stacking
- **Features:**
  - Stack ähnliche Mobs
  - Performance-Optimierung
  - Custom Stack-Limits

### World Editing

#### FastAsyncWorldEdit
- **Funktion:** Async World Editing
- **Features:**
  - Schnelles Editing
  - Async-Operationen
  - Brush-Tools
- **Commands:**
  - `//set <block>`
  - `//copy`
  - `//paste`

#### AxiomPaper
- **Funktion:** Advanced Building-Plugin
- **Features:**
  - Client-Side-Editing
  - Large-Scale-Edits
  - World-Painter-Integration

### Mapping

#### Bluemap
- **Funktion:** 3D-Web-Karte (siehe Survival)

### Items & Menus

#### Oraxen
- **Funktion:** Custom Items und Texturen (siehe Lobby)

#### DeluxeMenus
- **Funktion:** Custom GUI-Menüs (siehe Lobby)

#### ExecutableItems
- **Funktion:** Custom Item Actions
- **Features:**
  - Items mit Custom-Commands
  - Conditional Actions
  - Cooldowns

### Synchronization

#### HuskSync
- **Funktion:** Daten-Synchronisation mit Skyblock (siehe Skyblock)

### Management & Core

#### CMI
- **Funktion:** Core Management (siehe Lobby)

#### LuckPerms
- **Funktion:** Permissions (siehe Lobby)

#### PlaceholderAPI
- **Funktion:** Platzhalter (siehe Lobby)

#### ProtocolLib
- **Funktion:** Packet-Manipulation (siehe Lobby)

### Analytics

#### Plan
- **Funktion:** Server-Analytics (siehe Velocity)
- **Web-UI:** `http://<server-ip>:8804`

### Utility Libraries

#### NBTAPI
- **Funktion:** NBT-Data API
- **Verwendung:** Backend für Custom Items/Mobs

#### CommandAPI
- **Funktion:** Command-API
- **Verwendung:** Backend für Custom Commands

#### SCore
- **Funktion:** Core-Library für verschiedene Plugins

#### bStats
- **Funktion:** Plugin-Statistiken (anonym)

#### nightcore
- **Funktion:** Core-Library

---

## Plugin-Abhängigkeiten

### Erforderliche Basisplugins (auf allen Servern)

1. **PlaceholderAPI** - Für fast alle Plugins
2. **ProtocolLib** - Für Holograms, NPCs, Custom Features
3. **LuckPerms** - Permissions-Management

### MMO-Server (Skyblock & RPG)

1. **MMOCore** ← MythicLib
2. **MMOItems** ← MythicLib
3. **MythicMobs** (optional: MMOItems-Integration)
4. **HuskSync** ← MySQL + Redis

### RPG-Server Spezifisch

1. **MythicMobs Premium** → MythicDungeons, MythicCrucible
2. **ModelEngine** → MythicMobs (für Models)
3. **Citizens** → BetonQuest (Quests mit NPCs)
4. **Citizens** ← ProtocolLib

---

## Update-Prioritäten

### Kritisch (sofort updaten)
- LibertyBans (Sicherheit)
- LuckPerms (Sicherheit)
- HuskSync (Data-Sync)

### Hoch (regelmäßig updaten)
- MythicMobs Premium
- MMOCore / MMOItems
- BetonQuest
- SuperiorSkyblock2

### Mittel (bei Bedarf)
- CMI
- DeluxeMenus
- Oraxen

### Niedrig (wenn stabil, nicht updaten)
- ProtocolLib (kann andere Plugins brechen)
- ModelEngine (Custom Models müssen getestet werden)

---

## Plugin-Konfigurationen im Repository

```
MinecraftMMO/
├── proxy/plugins/         # Proxy-Server Plugin-Configs
├── lobby/plugins/         # Lobby-Server Plugin-Configs
├── survival/plugins/      # Survival-Server Plugin-Configs
├── skyblock/plugins/      # Skyblock-Server Plugin-Configs
├── rpg/plugins/           # RPG-Server Plugin-Configs
└── crosscraft-guilds/     # CrossCraft Guilds Plugin (Eigenentwicklung)
```

**Hinweis:** Nicht alle Plugin-Configs sind im Repository - nur Custom-Content und konfigurierte Einstellungen. Sensible Daten (Passwörter, API-Keys) sind über `.gitignore` ausgeschlossen.

---

**Letzte Aktualisierung:** 2026-04-12

**Plugin-Anzahl:**
- Velocity: 11 Plugins (inkl. Geyser)
- Lobby: ~12 Plugins
- Survival: ~15 Plugins
- Skyblock: ~25 Plugins
- RPG: ~40 Plugins
