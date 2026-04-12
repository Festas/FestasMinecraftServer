# Netzwerk-Architektur - MinecraftMMO

Dokumentation der technischen Architektur des MinecraftMMO Server-Netzwerks.

---

## Netzwerk-Übersicht

```
                                    [Internet]
                                        |
                                        v
                              ┌─────────────────┐
                              │  Velocity Proxy │
                              │ mc.festas-builds│
                              │      .com       │
                              └────────┬────────┘
                                       |
          ┌────────────────────────────┼────────────────────────────┐
          |                            |                            |
    ┌─────▼─────┐              ┌──────▼──────┐            ┌────────▼────────┐
    │   Lobby   │              │   Survival  │            │   MMO-Server    │
    │  Server   │              │   Server    │            │   (RPG/Skyblock)│
    └───────────┘              └─────────────┘            └─────────────────┘
    - Routing                  - Standard SMP            - RPG (Paper 1.21.1)
    - Welcome                  - Jobs, Claims            - Skyblock (Paper 1.21.1)
    - Info NPCs                - Economy                 - MMOCore, MMOItems
                               - Bluemap                 - MythicMobs Premium
```

---

## Server-Details

### 1. Velocity Proxy

**Funktion:** Zentraler Eintrittspunkt und Load-Balancer für alle Backend-Server

**Version:** Velocity (Latest)

**Plugins:**
- **CMIV** - CMI Integration für Velocity
- **ForceResourcepacks** - Erzwingt Resourcepacks auf Clients
- **Geyser-Velocity** - Bedrock-Spieler-Unterstützung (Floodgate Auth)
- **LibertyBans** - Netzwerk-weites Ban-System
- **MiniMOTD** - Custom MOTD (Message of the Day)
- **MySQL-And-Configurate** - Datenbank-Integration
- **PAF** (PartyAndFriends) - Party & Freundesliste über Server hinweg
- **Plan** - Netzwerk-weite Analyse und Statistiken
- **SkinsRestorer** - Custom Skins für Spieler
- **TAB** - Custom TAB-Liste und Scoreboard
- **VelocityScoreboardAPI** - API für Scoreboard-Management

**Konfiguration:**
- Server-IP: `mc.festas-builds.com`
- Default Server: Lobby
- Routing-Logic: Automatisch basierend auf Permissions/Commands

---

### 2. Lobby Server

**Funktion:** Willkommens-Server und Hub für Server-Navigation

**Version:** Paper 1.21.1

**Hauptplugins:**
- **CMI** (Complete Minecraft Integration) - Kern-Management-Plugin (Chat-Formatierung, Events, Void-Schutz)
- **FancyNpcs** - Interaktive NPCs für Server-Navigation (RPG, Survival, Skyblock + Info-NPCs)
- **Oraxen** - Custom Items und Texturen
- **DeluxeMenus** - Custom GUI-Menüs (Server-Selector, Regeln, Netzwerk-Guide)
- **LuckPerms** - Permissions-Management
- **PlaceholderAPI** - Platzhalter für Nachrichten/Displays
- **ProtocolLib** - Packet-Manipulation für Custom-Features
- **WorldGuard** - Weltschutz (Build/PvP/Damage/Hunger blockiert)
- **Skript** - Navigator-Kompass, Doppelsprung, Hub-Schutz

**Besonderheiten:**
- Keine Gameplay-Elemente (kein Survival, kein Combat)
- Read-only World (WorldGuard __global__ Region mit build:deny)
- Server-Selector NPCs (FancyNpcs: RPG, Survival/Tycoon, Skyblock)
- Info-NPCs (Regeln, Guide)
- Willkommens-Nachrichten (Titel + Untertitel + Actionbar auf Join)
- Doppelsprung-System (kosmetisch)
- Inventar-Schutz (nur Navigator-Kompass erlaubt)
- Void-Fall-Schutz (Teleport zum Spawn)

**Datenbank:** Keine eigene (nutzt Velocity-Datenbanken)

**Noch zu installieren:**
- ⚠️ **DecentHolograms** - Plugin-JAR muss manuell installiert werden (Hologramme für Willkommen, Server-Info, Spielerzahlen)
- ⚠️ **Spawn-Punkt** - Muss in-game mit `/cmi setspawn` gesetzt werden
- ⚠️ **NPC-Positionen** - Müssen in-game mit `/npc teleport <name>` positioniert werden
- ⚠️ **NPC-Skins** - Müssen über mineskin.org beschafft und in-game gesetzt werden

---

### 3. Survival / Tycoon Server

**Funktion:** Survival-Server mit integriertem Tycoon-Gamemode (Generator-basierte Economy mit 25-Tier-Progression)

**Version:** Paper 1.21.1

**Hauptplugins:**
- **CMI** - Core Management (Economy, Homes, Teleport, Kits, Chat-Formatierung, AFK-System)
- **EssentialsX** - Basis-Befehle (Konfliktbefehle deaktiviert — CMI hat Priorität)
- **Jobs** - Job-System für Economy (13 Berufe)
- **PlotSquared** - Land-Claiming-System (Tycoon-Plots + Freebuild)
- **ShopGUIPlus** - Shop-GUI für Economy
- **Rankup** - Rang-Progression-System (25 Tycoon-Ränge: Erde → Bedrock)
- **NextGens** - Generator-System (Tycoon-Kern, 25 Tier × 10 Sub-Levels)
- **DecentHolograms** - Info-Hologramme (Spawn, Shop, Casino, Generatoren, Rang-Progression)
- **Autorank** - Spielzeit-basierte Belohnungen (1h, 5h, 24h, 72h, 168h Milestones)
- **Bluemap** - 3D-Web-Karte
- **GlobalMarketPlus** - Globaler Marktplatz
- **LuckPerms** - Permissions
- **Vault** - Economy API
- **Skript** - Custom Tycoon-Logik (Sell Wand, Chunk Collector, Nitwit Boss, Casino, Tutorial, Daily Rewards)
- **WorldGuard** - Regionen-Schutz (gehärtet: TNT/Creeper/Feuer/Wither blockiert)

**Tycoon-Gamemode:**
- 25-Tier Rangaufstieg (Erde → Stein → Kohle → ... → Bedrock)
- Generator-basierte Item-Produktion mit Sell-Wand-System
- Chunk Collector (automatisches Item-Sammeln im 20-Block-Radius)
- Casino/Gambling-System mit täglichem Verlustlimit
- Custom Nitwit-Boss-Encounters (zufällige Spawn-Events)
- Multi-Plot-System: Progressive Plot-Limits pro Rang (1→5 Plots + Prestige-Boni)
- Plot-Merging: Benachbarte Plots zusammenführen ($5M)
- Plot-Reset bei Rangaufstieg (nur Haupt-Plot, zusätzliche Plots bleiben)
- Prestige-System (10 Stufen mit permanentem Sell-Bonus bis +200%)
- Tägliche Login-Belohnungen mit Streak-System (daily_rewards.sk)
- Spielzeit-Belohnungen über Autorank (Meilensteine)
- Tutorial-System für neue Spieler (tycoon_tutorial.sk)

> Siehe [docs/survival/](../docs/survival/) für vollständige Tycoon-Dokumentation.

**Datenbank:**
- **MySQL/MariaDB** (separiert von MMO-Servern)
  - Spielerdaten (Jobs, Claims, Ranks)
  - Economy-Daten (CMI, Logging aktiviert)
  - Shop-Transaktionen

**Besonderheiten:**
- **Strikte Trennung von MMO-Servern** (keine Daten-Synchronisation)
- Eigene Economy (nicht geteilt mit RPG/Skyblock)
- Tycoon-Gamemode als primäres Spielerlebnis neben Standard-Survival
- Geyser/Floodgate für Bedrock-Spieler-Support
- AFK-System aktiviert (Auto-Kick nach 30 Min bei 10+ Spielern online)
- Chat-Formatierung aktiviert (CMI mit Rang-Prefix)

**Sicherheitshinweise:**
- WorldGuard: TNT, Creeper, Wither, Feuer global blockiert
- Skript-Commands: Spielername-Validierung gegen Command Injection
- Anti-Cheat: **Noch nicht installiert** — Vulcan Premium wird empfohlen (siehe Anti-Cheat Abschnitt unten)

**Noch zu installieren/konfigurieren:**
- ⚠️ **Anti-Cheat** - Vulcan Premium muss manuell installiert werden (PRIORITÄT für Economy-Schutz)
- ⚠️ **Voting-System** - NuVotifier + VotingPlugin für Server-Listen-Integration
- ⚠️ **Hologramm-Positionen** - Müssen in-game mit `/dh hologram move` positioniert werden
- ⚠️ **ChestShop** - Plugin installiert aber nicht konfiguriert (GlobalMarketPlus deckt Spieler-Handel ab)

---

### 4. Skyblock Server (MMO)

**Funktion:** MMO Skyblock mit RPG-Elementen

**Version:** Paper 1.21.1

**Hauptplugins:**
- **SuperiorSkyblock2** - Skyblock Core-System
- **MMOCore** - Klassen und Skills
- **MMOItems** - Custom Items System
- **MythicMobs** - Custom Mobs (Community Edition)
- **JetsMinions** - Minion-System
- **CoinsEngine** - Multi-Währungs-System
- **Aurora** - Quests/Achievements
- **AuroraCollections** - Collection-System
- **HuskSync** - Daten-Synchronisation (mit RPG)
- **LuckPerms** - Permissions
- **PlaceholderAPI** - Platzhalter
- **Oraxen** - Custom Items/Texturen
- **DeluxeBazaar** - Bazaar-System

**Klassen (MMOCore):**
1. Krieger
2. Magier
3. Assassine
4. Bogenschütze
5. Schamane
6. Beschwörer

**Datenbank:**
- **MySQL/MariaDB** (geteilt mit RPG-Server)
  - Spielerprofile (Klassen, Level, Stats)
  - Inventare (synchronisiert via HuskSync)
  - Skyblock-Island-Daten
  - Quest-Progress
- **Redis** (Cache für HuskSync)
  - Session-Daten
  - Temp-Inventare

**Besonderheiten:**
- Daten-Synchronisation mit RPG-Server (HuskSync)
- Eigene Skyblock-Economy (CoinsEngine)
- MMO-Progression parallel zu Skyblock-Progression

---

### 5. RPG Server (MMO)

**Funktion:** Vollständiger MMO-RPG Server mit Open World

**Version:** Paper 1.21.1

**Hauptplugins:**
- **MythicMobs Premium** - Advanced Custom Mobs/Bosse/Items
- **MythicDungeons** - Instanzierte Dungeons
- **MythicCrucible** - Advanced Item Creation
- **MythicRPG** - RPG-Mechaniken
- **MythicAchievements** - Achievement-System
- **MMOCore** - Klassen und Skills
- **MMOItems** - Custom Items System
- **MythicLib** - Library für MMO-Features
- **BetonQuest** - Advanced Quest-System
- **Citizens** - NPCs
- **HuskSync** - Daten-Synchronisation (mit Skyblock)
- **CoinsEngine** - Multi-Währungs-System
- **ModelEngine** - Custom 3D Models
- **LuckPerms** - Permissions
- **PlaceholderAPI** - Platzhalter
- **FastAsyncWorldEdit** - World Editing
- **LibsDisguises** - Mob/NPC Verkleidungen
- **ProtocolLib** - Packet-Manipulation
- **DecentHolograms** - Hologramme
- **Aurora** - Quests/Achievements
- **RoseGarden** - World Management
- **RoseLoot** - Custom Loot-Tables
- **RoseStacker** - Entity-Stacking
- **Plan** - Server-Analytics
- **Bluemap** - 3D-Web-Karte
- **ExecutableItems** - Custom Item Actions
- **PlayerParticles** - Partikel-Effekte
- **PlayerPoints** - Punkt-System
- **DeluxeMenus** - Custom GUIs
- **Oraxen** - Custom Items/Texturen

**Klassen (MMOCore):** (identisch mit Skyblock)
1. Krieger
2. Magier
3. Assassine
4. Bogenschütze
5. Schamane
6. Beschwörer

**Datenbank:**
- **MySQL/MariaDB** (geteilt mit Skyblock)
  - Spielerprofile (Klassen, Level, Stats)
  - Inventare (synchronisiert via HuskSync)
  - Quest-Progress
  - Achievement-Daten
- **Redis** (Cache für HuskSync)
  - Session-Daten
  - Temp-Inventare

**Besonderheiten:**
- Premium MythicMobs mit erweiterten Features
- Instanzierte Dungeons (MythicDungeons)
- Vollständiges Quest-System (BetonQuest)
- 3D Custom Models (ModelEngine)
- Daten-Synchronisation mit Skyblock (HuskSync)

---

## Datenbank-Struktur

### MySQL/MariaDB (Survival) - Separiert

**Host:** `<survival-db-host>`

**Verwendung:**
- Survival Server Spielerdaten
- Jobs-Plugin-Daten
- Economy (Vault)
- Claims (PlotSquared, GriefPrevention)
- Ranks (Rankup)

**Keine Verbindung zu:** RPG/Skyblock Servern

---

### MySQL/MariaDB (MMO) - Geteilt zwischen RPG & Skyblock

**Host:** `<mmo-db-host>`

**Verwendung:**
- MMOCore Daten (Klassen, Skills, Level)
- MMOItems Daten (Custom Items, Stats)
- BetonQuest Progress
- Citizens NPC-Daten
- CoinsEngine Währungen
- Achievement/Collection-Daten
- MythicMobs Daten (Spawns, Cooldowns)

**Geteilte Tabellen:**
```
mmocore_players        # Spieler-Klassen, Level, Skills
mmoitems_data          # Custom Item Daten
betonquest_*           # Quest-Progress
coinsengine_*          # Währungen (coins, tokens, etc.)
husksync_*             # Synchronisations-Daten
```

---

### Redis Cache (MMO)

**Host:** `<redis-host>`

**Verwendung:**
- HuskSync Session-Cache
- Temporäre Inventar-Daten während Server-Switch
- Player-Session-Daten
- Live-Sync zwischen RPG und Skyblock

**TTL (Time To Live):**
- Session-Daten: 30 Minuten
- Inventar-Cache: 5 Minuten

---

## Synchronisation (HuskSync)

### Synchronisierte Daten zwischen RPG ↔ Skyblock

**Inventare:**
- ✅ Haupt-Inventar
- ✅ Rüstungs-Slots
- ✅ Offhand
- ✅ Ender-Chest
- ❌ Skyblock-Island-Inventare (nicht synchronisiert)

**Spieler-Stats:**
- ✅ Health/Hunger/Saturation
- ✅ XP Level
- ✅ Potion Effects
- ✅ Location (beim Server-Switch)

**MMO-Daten:**
- ✅ Klassen-Level (MMOCore)
- ✅ Skills und Skill-Trees
- ✅ Quest-Progress (BetonQuest)
- ✅ Währungen (CoinsEngine)

**Nicht synchronisiert:**
- ❌ Survival Server Daten (komplett getrennt)
- ❌ Skyblock-Islands (server-spezifisch)
- ❌ RPG-spezifische Dungeon-Progress
- ❌ Server-spezifische Achievements

### Sync-Flow

```
Spieler auf RPG Server
        |
        | /server skyblock
        v
1. Inventar → Redis Cache
2. Stats → Redis Cache
3. MMO-Daten → MySQL
        |
        v
4. Server-Switch (Velocity)
        |
        v
5. Skyblock lädt Daten aus Redis/MySQL
6. Spieler spawnt mit synchronisiertem Inventar
```

### Konflikt-Handling

- **Gleichzeitiges Spielen:** Nicht möglich (Session-Lock)
- **Daten-Konflikt:** Neueste Timestamp gewinnt
- **Connection-Loss:** Daten werden beim Reconnect aus MySQL geladen

---

## Resourcepacks

**Erzwungen via:** ForceResourcepacks (Velocity)

**Serverübergreifend:**
- ✅ Custom Items (Oraxen)
- ✅ Custom Mobs (MythicMobs + ModelEngine)
- ✅ Custom Sounds
- ✅ Custom Textures

**Resourcepack-URL:**
```
https://<resourcepack-host>/MinecraftMMO-Pack.zip
```

**Auto-Download:** Ja (erzwungen beim Join)

---

## Performance & Skalierung

### Erwartete Spieleranzahl
- **Lobby:** 20-50 Spieler (leichtgewichtig)
- **Survival:** 30-80 Spieler
- **Skyblock:** 50-100 Spieler
- **RPG:** 50-100 Spieler

### Server-Hardware (empfohlen)
```
Velocity Proxy: 2 vCPU, 2 GB RAM
Lobby:          2 vCPU, 4 GB RAM
Survival:       4 vCPU, 8 GB RAM
Skyblock:       4 vCPU, 10 GB RAM
RPG:            6 vCPU, 12 GB RAM
MySQL:          2 vCPU, 4 GB RAM
Redis:          1 vCPU, 2 GB RAM
```

### Optimierungen
- **Paper:** Optimierte Server-Software (Forks: Purpur/Airplane möglich)
- **Entity-Stacking:** RoseStacker (RPG)
- **Async Chunks:** FastAsyncWorldEdit
- **Caching:** Redis für häufige Queries
- **Lazy Loading:** Chunks nur laden wenn nötig

---

## Monitoring & Analytics

### Plan (Netzwerk-weit)
- Spieler-Statistiken
- Server-Performance
- Plugin-Performance
- Join/Leave-Tracking

### Bluemap (Survival, RPG)
- 3D-Web-Karten
- Live-Spieler-Tracking
- Marker für wichtige Orte

### Console Logging
- Log-Level: INFO (Production), DEBUG (Development)
- Log-Rotation: Täglich
- Log-Retention: 30 Tage

---

## Backup-Strategie

### Automatische Backups

**Welten:**
- Lobby: Wöchentlich (statische Map)
- Survival: Täglich (3 Uhr nachts)
- Skyblock: Täglich (3 Uhr nachts)
- RPG: Täglich (3 Uhr nachts)

**Datenbanken:**
- MySQL: Stündlich (Incremental), Täglich (Full)
- Redis: Täglich (RDB Snapshot)

**Configs:**
- Alle Plugin-Configs: Bei jedem Update (via Git)

**Retention:**
- Tägliche Backups: 7 Tage
- Wöchentliche Backups: 4 Wochen
- Monatliche Backups: 12 Monate

---

## Deployment & Updates

### Update-Prozess

1. **Test-Server:** Änderungen testen
2. **Config-Backup:** Aktuelle Configs sichern
3. **Ankündigung:** Spieler informieren (In-Game, Discord)
4. **Wartungsmodus:** Server in Wartung (Velocity)
5. **Update:** Plugins/Configs aktualisieren
6. **Test:** Schneller Funktions-Check
7. **Live:** Server wieder öffnen
8. **Monitoring:** Logs überprüfen

### Config-Versionierung

- **Dieses Repository:** Alle Configs versioniert
- **Branches:**
  - `main` - Production-Configs
  - `dev` - Development/Test-Configs
- **Commits:** Beschreibende Commit-Messages

### Rollback-Strategie

Bei kritischen Fehlern:
1. Server stoppen
2. Letzte funktionierende Configs aus Git wiederherstellen
3. Datenbank-Rollback (falls nötig)
4. Server starten
5. Post-Mortem-Analyse

---

## Sicherheit

### LibertyBans (Velocity)
- Netzwerk-weite Bans/Mutes
- IP-Bans
- Temporary Bans
- Reason-Tracking

### Permissions (LuckPerms)
- Fein-granulare Permissions
- Kontext-basiert (Server, World)
- Temporäre Permissions
- Track-System für Ranks

### Anti-Cheat
- **Empfehlung:** Vulcan Anti-Cheat (Premium) oder Spartan Anti-Cheat
- **Aktueller Status:**
  - ❌ **Survival/Tycoon:** Kein Anti-Cheat installiert — **PRIORITÄT!** Besonders kritisch wegen Casino/Gambling und Economy-System
  - RPG/Skyblock: Status prüfen
- **Konfigurationsrichtlinien:**
  - Bewegungs-Checks (Fly, Speed, NoClip) auf allen Servern aktiv
  - Kampf-Checks (KillAura, Reach, AutoClicker) besonders auf RPG/Skyblock
  - Bedrock-Spieler (Geyser/Floodgate) von bestimmten Checks ausnehmen
  - False-Positive-Toleranz initial höher einstellen, nach Testing verschärfen
- **Logging:** Verdächtige Aktionen in Discord-Channel melden
- **Integration:** LibertyBans für automatische Temp-Bans bei wiederholten Verstößen

### DDoS-Protection
- Cloudflare (optional)
- Proxy-Layer (Velocity)

---

## Bekannte Einschränkungen

1. **HuskSync:** Synchronisation zwischen RPG ↔ Skyblock kann bei Lag verzögert sein
2. **Skyblock Islands:** Können nicht auf RPG-Server übertragen werden
3. **Survival Economy:** Komplett getrennt von MMO-Servern (Design-Entscheidung)
4. **Resourcepack:** Muss heruntergeladen werden (kann bei schlechter Verbindung dauern)

---

## Zukünftige Verbesserungen

- [ ] Load-Balancing für RPG/Skyblock (mehrere Instanzen)
- [ ] Separate Build-Server für große Projekte
- [ ] Event-Server (temporär für spezielle Events)
- [ ] CDN für Resourcepacks
- [ ] Backup-Server (Fallback bei Ausfällen)

### Lobby — Nächste Schritte
- [ ] DecentHolograms Plugin-JAR installieren (Welcome, Server-Info, Spielerzahlen)
- [ ] Spawn-Punkt in-game setzen (`/cmi setspawn`)
- [ ] NPC-Skins und -Positionen in-game konfigurieren
- [ ] Scoreboard/Sidebar mit Netzwerk-Info (CMI oder Skript)
- [ ] Kosmetik-System (PlayerParticles + PlayerPoints als Währung)
- [ ] Parkour-Kurs mit Belohnungen
- [ ] Boss-Bar-Announcements für rotierende Ankündigungen
- [ ] Tab-Liste Header/Footer anpassen (via Proxy TAB Plugin)

### Survival — Nächste Schritte
- [x] Multi-Plot-System: Progressive Plot-Limits pro Rang (1→5 Plots, 6→8 mit Prestige)
- [x] Plot-Merging als Economy-Sink ($5M pro Merge)
- [x] Tycoon-Dokumentation erstellt (docs/survival/)
- [ ] Anti-Cheat installieren (Vulcan Premium — PRIORITÄT)
- [ ] Voting-System (NuVotifier + VotingPlugin)
- [ ] Crate/Key-System (ExcellentCrates oder CrazyCrates)
- [ ] ChestShop konfigurieren oder entfernen (GlobalMarketPlus deckt den Bedarf)
- [ ] LibsDisguises in Kosmetik-System integrieren oder entfernen
- [ ] PlayerPoints als sekundäre Währung konfigurieren
- [ ] Erweiterte Boss-Events über Nitwit hinaus

---

**Letzte Aktualisierung:** 2026-04-11

**Version:** 1.1
