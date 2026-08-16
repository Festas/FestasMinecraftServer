# Plugin-Referenz - MinecraftMMO Netzwerk

Vollständige Übersicht aller verwendeten Plugins pro Server.

> **⚠️ Stand: Umstellung auf 26.2** — Alle Server laufen auf **Minecraft 26.2**. Der Fokus liegt aktuell auf **Lobby und Survival**; deren Plugin-Listen wurden frisch aufgeräumt und spiegeln den echten Ordnerinhalt wider. **Skyblock** wird überarbeitet und behalten (ohne Gilden, mit Freunde-Koop); zusätzlich kommt ein neuer **Mining**-Server, der den **`rpg`-Slot recycelt**. Der Abschnitt zu **RPG** ist **Archiv** — der RPG-**Spielmodus** wird eingestellt, der Server-Slot `rpg` wird zum Mining-Server.

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

> **Aktiv.** Aufgeräumt und neu übertragen auf 26.2. Diese Liste entspricht dem Inhalt von `lobby/plugins/`.

### Management & Core

#### CMI (+ CMILib)
- **Funktion:** Kern-Management-Plugin (CMILib als Library)
- **Features:**
  - Teleport-Commands, Spawn, Warps
  - Chat-Formatierung
  - Hologramme (ersetzt das frühere DecentHolograms)
  - Void-/Fall-Schutz
- **Wichtige Commands:** `/spawn`, `/setspawn`, `/fly`

#### LuckPerms
- **Funktion:** Permissions-Management
- **Features:** Ranks/Groups, Context-basierte Permissions (Server, World), Web-Editor
- **Web-Editor:** `https://luckperms.net/editor`

#### Vault
- **Funktion:** Economy-/Permissions-API-Bridge (Backend für andere Plugins)

### Display & UI

#### DeluxeMenus
- **Funktion:** Custom GUI-Menüs — **zentrale Navigation der Lobby**
- **Verwendung:**
  - `server_selector` (Server-Auswahl: Survival, Skyblock, Mining — Mining nutzt den recycelten `rpg`-Slot via `[connect] rpg`)
  - Netzwerk-Guide, Regeln, Basics/Advanced-Menüs
- **Config:** `lobby/plugins/DeluxeMenus/gui_menus/`

#### Skript
- **Funktion:** Custom-Logik der Lobby
- **Verwendung:**
  - Navigator-Kompass (öffnet `server_selector`)
  - Doppelsprung (kosmetisch), Hub-/Inventar-Schutz
- **Config:** `lobby/plugins/Skript/scripts/`

### Items & Resources

#### Oraxen
- **Funktion:** Custom Items und Texturen
- **Features:** Custom Item Models, Texturen, Blocks, Resourcepack-Generierung

### Utility & Bibliotheken

#### PlaceholderAPI
- **Funktion:** Platzhalter für Nachrichten/Displays

#### ProtocolLib
- **Funktion:** Packet-Manipulation (Backend für Custom-Features)

#### CommandAPI
- **Funktion:** Command-API (Backend-Bibliothek)

#### FastAsyncWorldEdit (FAWE)
- **Funktion:** Async World-Editing für Lobby-Bau/-Pflege

#### PartyAndFriendsGUI
- **Funktion:** Party- & Freundeslisten-GUI (Backend zum Velocity-PAF)

#### bStats / faststats / spark
- **Funktion:** Statistik- und Performance-Werkzeuge (Metriken, Profiling)

> **Hinweis:** FancyNpcs und ein separates Hologramm-Plugin (DecentHolograms) sind **nicht mehr** Teil der Lobby — Navigation läuft jetzt über den DeluxeMenus-`server_selector` + Skript-Kompass, Hologramme über CMI.

---

## Survival Server Plugins

> **Aktiv.** Aufgeräumt und neu übertragen auf 26.2. Diese Liste entspricht dem Inhalt von `survival/plugins/`.

### Tycoon & Progression

#### NextGens
- **Funktion:** Generator-System — **Kern des Tycoon-Gamemodes**
- **Features:** 25 Generator-Tiers (Erde → Bedrock) mit Sub-Levels, Auto-Produktion

#### Rankup
- **Funktion:** Rang-Progression (25 Tycoon-Ränge, geldbasiert)

#### Skript
- **Funktion:** Custom Tycoon-Logik
- **Verwendung:** Sell Wand, Chunk Collector, Tutorial, Prestige, Casino, Boss-Events, Daily/Weekly Rewards, dynamische Börse
- **Config:** `survival/plugins/Skript/scripts/`

#### Autorank
- **Funktion:** Spielzeit-basierte Belohnungen/Meilensteine

### Economy & Shops

#### Jobs
- **Funktion:** Job-System für zusätzliches Einkommen (mehrere Berufe)

#### ShopGUIPlus
- **Funktion:** Shop-GUI (Kauf/Verkauf, Kategorien, dynamische Preise)

#### GlobalMarketPlus
- **Funktion:** Globaler Marktplatz / Auktionshaus (Spieler-zu-Spieler)

#### ChestShop
- **Funktion:** Spieler-Läden per Truhe & Schild

#### Vault
- **Funktion:** Economy-API (Backend, angebunden an CMI)

### Land, Welten & Building

#### PlotSquared
- **Funktion:** Plot-basiertes Claiming (Tycoon-Plots + Freebuild), Merging, Schematics

#### Multiverse-Core (+ Multiverse-Inventories)
- **Funktion:** Verwaltung mehrerer Welten (`tycoon`, `town`, `freebuild`) mit getrennten Inventaren

#### VoidGen
- **Funktion:** Void-/Leerwelt-Generator (u. a. für Plot-/Freebuild-Welten)

#### Chunky
- **Funktion:** Pre-Generierung von Chunks (Performance)

#### WorldGuard
- **Funktion:** Regionen-Schutz (gehärtet: TNT/Creeper/Feuer/Wither begrenzt)

#### FastAsyncWorldEdit (FAWE)
- **Funktion:** Async World-Editing

#### AxiomPaper
- **Funktion:** Advanced Building (Client-Side-Editing, Large-Scale-Edits)

### Optik & Content

#### Oraxen
- **Funktion:** Custom Items und Texturen

#### RoseStacker
- **Funktion:** Entity-/Item-Stacking (Performance)

#### RoseGarden
- **Funktion:** Bibliothek/Backend für die Rose-Plugins

#### HeadDatabase
- **Funktion:** Datenbank dekorativer Köpfe (Deko/Shops)

#### LibsDisguises
- **Funktion:** Verkleidungen (Mob/Spieler) — u. a. für Events/Bosse

#### BlueMap
- **Funktion:** 3D-Web-Karte (Live-Rendering, Marker)
- **Web-UI:** `http://<server-ip>:8100`

### Core, Management & Bibliotheken

#### CMI (+ CMILib)
- **Funktion:** Core-Management (Economy, Homes, Teleport, Kits, Chat, AFK, Hologramme) — siehe Lobby

#### LuckPerms
- **Funktion:** Permissions und Rang-/Plot-Limits (siehe Lobby)

#### PlaceholderAPI
- **Funktion:** Platzhalter (siehe Lobby)

#### ProtocolLib
- **Funktion:** Packet-Manipulation (siehe Lobby)

#### CommandAPI / NBTAPI
- **Funktion:** Command- bzw. NBT-Bibliotheken (Backend)

#### PartyAndFriendsGUI
- **Funktion:** Party-/Freundeslisten-GUI (Backend zum Velocity-PAF)

#### bStats / faststats / spark
- **Funktion:** Statistik- und Performance-Werkzeuge

> **Hinweis:** **EssentialsX**, **GriefPrevention**, **PlayerPoints** und ein separates DecentHolograms sind **nicht mehr** Teil des Survival-Servers — die Basis-Befehle und Hologramme übernimmt CMI, Claims laufen über PlotSquared/WorldGuard.

---

## Skyblock Server Plugins

> **🟢 Umbau.** Der Skyblock-Server wird überarbeitet und behalten — **ohne Gilden**, mit **Freunde-Koop** (Insel-Mitglieder via SuperiorSkyblock2). Der Plugin-Stack bleibt weitgehend bestehen; ob die MMO-Plugins (MMOCore/MMOItems) erhalten bleiben, ist offen (siehe [NEW_SERVERS.md](NEW_SERVERS.md#7-verbleibende-offene-fragen)).

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

> **⚠️ Archiv.** Der RPG-Server wird zeitnah eingestellt und durch einen neuen Server ersetzt. Diese Liste wird nicht mehr gepflegt.

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

### MMO-Server (Skyblock im Umbau & RPG im Archiv)

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
├── lobby/plugins/         # Lobby-Server Plugin-Configs        (AKTIV)
├── survival/plugins/      # Survival-Server Plugin-Configs     (AKTIV)
├── skyblock/plugins/      # Skyblock-Server Plugin-Configs     (UMBAU — ohne Gilden, Freunde-Koop)
└── rpg/plugins/           # Mining-Server Plugin-Configs       (RECYCELT aus RPG — Aufbau; + Alt-RPG-Archiv)
```

**Hinweis:** Nicht alle Plugin-Configs sind im Repository - nur Custom-Content und konfigurierte Einstellungen. Sensible Daten (Passwörter, API-Keys) sind über `.gitignore` ausgeschlossen.

---

**Letzte Aktualisierung:** 2026-08-15

**Plugin-Anzahl (Ist-Stand der Ordner):**
- Velocity: 10 Plugins (inkl. Geyser + Floodgate)
- Lobby: 16 Plugins *(aktiv, aufgeräumt)*
- Survival: 35 Plugins *(aktiv, aufgeräumt)*
- Skyblock: ~35 Plugins *(Umbau — ohne Gilden, Freunde-Koop)*
- RPG: ~50 Plugins *(Archiv — wird eingestellt)*
- Mining: Gerüst *(neu — Plugin-Stack in Aufbau)*
