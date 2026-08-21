# Netzwerk-Architektur - MinecraftMMO

Dokumentation der technischen Architektur des MinecraftMMO Server-Netzwerks.

> **Stand: 26.2** — Netzwerk läuft auf **Minecraft/Paper 26.2**. Aktive Server: **Lobby** und **Survival**. Im Aufbau: überarbeiteter **Skyblock** (ohne Gilden, Freunde-Koop) und neuer **Mining**-Server (recycelter `rpg/`-Slot).

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
    │   Lobby   │              │   Survival  │            │  Skyblock/Mining│
    │  Server   │              │   Server    │            │                 │
    │  (AKTIV)  │              │   (AKTIV)   │            │ (NEU / Umbau)   │
    └───────────┘              └─────────────┘            └─────────────────┘
    - Routing                  - Survival/Tycoon         - Skyblock (Koop, ohne Gilden)
    - Welcome                  - Jobs, Plots             - Mining (Abbau-Zonen, rpg/-Slot)
    - Navigation               - Economy, BlueMap       
      (DeluxeMenus) 
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

**Version:** Paper 26.2

**Hauptplugins:**
- **CMI** (+CMILib, Complete Minecraft Integration) - Kern-Management-Plugin (Chat-Formatierung, Events, Void-Schutz, Hologramme)
- **DeluxeMenus** - Custom GUI-Menüs, u. a. der `server_selector` (zentrale Server-Navigation)
- **Skript** - Navigator-Kompass, Doppelsprung, Hub-Schutz
- **Oraxen** - Custom Items und Texturen
- **LuckPerms** - Permissions-Management
- **PlaceholderAPI** - Platzhalter für Nachrichten/Displays
- **ProtocolLib** / **CommandAPI** - Backend-Bibliotheken für Custom-Features
- **WorldGuard** - Weltschutz (Build/PvP/Damage/Hunger blockiert)
- **FastAsyncWorldEdit** - Bau/Pflege der Lobby-Welt
- **Vault**, **bStats**, **faststats**, **spark** - Economy-Bridge, Statistik & Profiling

**Besonderheiten:**
- Keine Gameplay-Elemente (kein Survival, kein Combat)
- Read-only World (WorldGuard __global__ Region mit build:deny)
- Server-Navigation über DeluxeMenus-`server_selector` + Skript-Kompass
- Info-/Regel-Menüs (DeluxeMenus)
- Willkommens-Nachrichten (Titel + Untertitel + Actionbar auf Join)
- Doppelsprung-System (kosmetisch)
- Inventar-Schutz (nur Navigator-Kompass erlaubt)
- Void-Fall-Schutz (Teleport zum Spawn)

**Datenbank:** Keine eigene (nutzt Velocity-Datenbanken)

**Noch einzurichten (in-game):**
- ⚠️ **Spawn-Punkt** - Muss in-game mit `/cmi setspawn` gesetzt werden
- ⚠️ **Hologramme** - Über CMI erstellen (Willkommen, Server-Info, Spielerzahlen)

> **Hinweis:** FancyNpcs und DecentHolograms wurden bei der 26.2-Aufräumaktion entfernt — Navigation läuft jetzt über DeluxeMenus + Skript, Hologramme über CMI.

---

### 3. Survival / Tycoon Server

**Funktion:** Survival-Server mit integriertem Tycoon-Gamemode (Generator-basierte Economy mit 25-Tier-Progression)

**Version:** Paper 26.2

**Hauptplugins:**
- **CMI** (+CMILib) - Core Management (Economy, Homes, Teleport, Kits, Chat-Formatierung, AFK-System, Hologramme)
- **NextGens** - Generator-System (Tycoon-Kern, 25 Tier × Sub-Levels)
- **Jobs** - Job-System für Economy
- **Rankup** - Rang-Progression-System (25 Tycoon-Ränge: Erde → Bedrock)
- **CMI** (Rang-Engine) - zeitbasierte Rang-Leiter (`autorank`-Track, 14 Stufen) über `AutoRankUp` (ersetzt Autorank; siehe [survival/ZEITRANG_CMI.md](survival/ZEITRANG_CMI.md))
- **Skript** - Custom Tycoon-Logik (Sell Wand, Chunk Collector, Nitwit Boss, Casino, Tutorial, Daily/Weekly Rewards, Prestige, dynamische Börse)
- **PlotSquared** - Land-Claiming-System (Tycoon-Plots + Freebuild)
- **Multiverse-Core** (+Inventories) - Verwaltung der Welten `tycoon`/`town`/`freebuild` mit getrennten Inventaren
- **VoidGen** - Void-/Leerwelt-Generator
- **Chunky** - Chunk-Pre-Generierung (Performance)
- **WorldGuard** - Regionen-Schutz (gehärtet: TNT/Creeper/Feuer/Wither begrenzt)
- **FastAsyncWorldEdit** / **AxiomPaper** - World-Editing & Building
- **ShopGUIPlus** - Shop-GUI für Economy
- **GlobalMarketPlus** - Globaler Marktplatz / Auktionshaus
- **ChestShop** - Spieler-Läden per Truhe & Schild
- **Oraxen** - Custom Items und Texturen
- **HeadDatabase** - dekorative Köpfe
- **LibsDisguises** - Verkleidungen (Events/Bosse)
- **RoseStacker** (+RoseGarden) - Entity-/Item-Stacking (Performance)
- **BlueMap** - 3D-Web-Karte
- **LuckPerms** - Permissions
- **Vault** - Economy API (Backend, an CMI angebunden)
- **PlaceholderAPI**, **ProtocolLib**, **CommandAPI**, **NBTAPI** - Backend-Bibliotheken
- **bStats**, **faststats**, **spark** - Statistik & Profiling

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
- Spielzeit-basierte Rang-Leiter über die CMI-Rang-Engine (`autorank`-Track, netzwerkweit via LuckPerms; ersetzt Autorank)
- Tutorial-System für neue Spieler (tycoon_tutorial.sk)

> Siehe [docs/survival/](../docs/survival/) für vollständige Tycoon-Dokumentation.

**Datenbank:**
- **MySQL/MariaDB** (separiert von MMO-Servern)
  - Spielerdaten (Jobs, Claims, Ranks)
  - Economy-Daten (CMI, Logging aktiviert)
  - Shop-Transaktionen

**Besonderheiten:**
- **Strikte Trennung von MMO-Servern** (keine Daten-Synchronisation)
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
- ⚠️ **Hologramm-Positionen** - Über CMI setzen (`/cmi hologram ...`) für Spawn/Shop/Casino/Generatoren

---

### 4. Skyblock Server — *Umbau (ohne Gilden, Freunde-Koop)*

> **🟢 Wird überarbeitet und behalten.** Kernänderung: **keine Gilden**, stattdessen **Freunde-Koop** über die Insel-Mitglieder von SuperiorSkyblock2. Details und Plugin-Shortlist in [NEW_SERVERS.md](NEW_SERVERS.md).

**Funktion:** Koop-Skyblock (Freunde auf Insel einladen), schlankerer Fokus als Alt-MMO

**Version:** Paper 26.2

**Hauptplugins:**
- **SuperiorSkyblock2** - Skyblock Core (inkl. Insel-Mitglieder/Koop)
- **JetsMinions** - Minion-System
- **CoinsEngine** - Multi-Währungs-System
- **Aurora** / **AuroraCollections** - Collections/Achievements
- **LuckPerms**, **PlaceholderAPI**, **Oraxen** - Basis-Infrastruktur
- **DeluxeBazaar** - Bazaar-System
- **MMOCore**, **MMOItems**, **MythicMobs** *(offene Frage: MMO-Integration behalten oder entfernen — siehe [NEW_SERVERS.md](NEW_SERVERS.md#7-verbleibende-offene-fragen))*

**Datenbank:**
- **MariaDB** — Spielerprofile, Skyblock-Island-Daten, Collection-Progress
- **Redis** — HuskSync Session-Cache

**Besonderheiten:**
- Server-eigene Economy (CoinsEngine), kein Cross-Server-Economy-Mix

---
