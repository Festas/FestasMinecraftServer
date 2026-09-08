# Netzwerk-Architektur - MinecraftMMO

Dokumentation der technischen Architektur des MinecraftMMO Server-Netzwerks.

> **Stand: 26.2** — Netzwerk läuft auf **Minecraft/Paper 26.2**. Aktiv sind **Lobby** als Entry-Hub und **Survival** als aktueller Hauptserver. Im Aufbau: **Skyblock** als Koop-MVP/Umbau und **Mining** als geplanter Grind-Loop im technischen `rpg/`-Slot.

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
    │   Lobby   │              │   Survival  │            │ Skyblock/Mining │
    │  Server   │              │   Server    │            │                 │
    │ (ENTRY)   │              │   (MAIN)    │            │ (UMBAU / PLAN)  │
    └───────────┘              └─────────────┘            └─────────────────┘
    - Routing                  - Town + Freebuild        - Skyblock (Koop-MVP, ohne Gilden)
    - Welcome                  - Jobs, Claims, Shops     - Mining (Grind-Loop, `rpg`-Slot)
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

### 3. Survival Server

**Funktion:** Aktiver Hauptserver mit **Town**- und **Freebuild**-Fokus.
Historische Tycoon-Komponenten liegen teils noch im Repo, sind aktuell aber
nicht das aktive Spielerlebnis.

**Version:** Paper 26.2

**Hauptplugins:**
- **CMI** (+CMILib) - Core Management (Economy, Homes, Teleport, Kits, Chat-Formatierung, AFK-System, Hologramme)
- **NextGens** - Generator-System; aktuell deaktiviert und für einen späteren
  dedizierten Tycoon-Server vorgesehen
- **Jobs** - Job-System für Economy
- **Rankup** - Rang-/Freischalt-Progression für den Survival-Server
- **CMI** (Rang-Engine) - zeitbasierte Rang-Leiter (`autorank`-Track, 14 Stufen) über `AutoRankUp` (ersetzt Autorank; siehe [survival/ZEITRANG_CMI.md](survival/ZEITRANG_CMI.md))
- **Skript** - Custom Survival-/Economy-Logik; einzelne Tycoon-Skripte sind
  historisch vorhanden, aber derzeit nicht das aktive Kern-Gameplay
- **PlotSquared** - Plot-/Bau-System für die `freebuild`-Welt
- **Multiverse-Core** (+Inventories) - Verwaltung der Welten `town` und
  `freebuild` mit getrennten Inventaren
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

**Aktuelle Ausrichtung:**
- **Town** ist die laufende Survival-Welt mit Claims/Economy.
- **Freebuild** bleibt der Bau-/Plot-Bereich mit getrennter Inventargruppe.
- Jobs, Shops, Market, CMI und BlueMap tragen das aktuelle Spielerlebnis.
- Tycoon-/Generator-Mechaniken sind derzeit deaktiviert und für einen späteren
  separaten Server vorgesehen.

> Siehe [docs/survival/README.md](survival/README.md) für den Ist-Stand;
> [docs/survival/TYCOON.md](survival/TYCOON.md) dokumentiert den geparkten
> Tycoon-Bestand.

**Datenbank:**
- **MySQL/MariaDB** (separiert von MMO-Servern)
  - Spielerdaten (Jobs, Claims, Ranks)
  - Economy-Daten (CMI, Logging aktiviert)
  - Shop-Transaktionen

**Besonderheiten:**
- **Strikte Trennung von MMO-Servern** (keine Daten-Synchronisation)
- Town + Freebuild als aktuelles Spielerlebnis; Tycoon-Mechaniken derzeit
  deaktiviert
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

### 4. Skyblock Server — *Koop-MVP im Umbau*

> **🟢 Wird als schlanker Koop-Skyblock überarbeitet.** Keine Gilden; Freunde-Koop läuft über die Insel-Mitglieder von SuperiorSkyblock2. Maßgeblicher Ist-Stand: [skyblock/README.md](skyblock/README.md).

**Funktion:** Skyblock-MVP mit Koop-Inseln, Bazaar/Auktionen und eigener Server-Economy

**Version:** Paper 26.2

**Hauptplugins:**
- **SuperiorSkyblock2** - Insel-/Koop-Kern
- **SlimeWorldManager** - dateibasierte Insel-Welten
- **CMI** (+ **CMILib**, **Vault**) - Server-Economy und Basis-Management
- **DeluxeBazaar** + **GlobalMarketPlus** - Handel/Auktionen
- **LuckPerms**, **PlaceholderAPI**, **DeluxeMenus**, **Oraxen**, **Skript**, **Plan** - Netzwerk-/UI-Basis

**Datenhaltung:**
- **SuperiorSkyblock2:** SQLite
- **SlimeWorldManager:** Datei-Storage (`file`)
- **CMI:** eigene MySQL-Datenbank `S5_CMI`
- **Plan** / **LuckPerms:** zentrale Netzwerk-Dienste

**Besonderheiten:**
- Koop über Insel-Mitglieder statt Gilden
- Gameplay-Daten bleiben lokal; keine aktive Gameplay-Synchronisation mit Mining
- Schlanker als der frühere MMO-Skyblock-Plan; maßgeblich ist der aktuelle Stack unter `skyblock/plugins/`

---

### 5. Mining Server — *geplanter Grind-Loop im `rpg/`-Slot*

> **🟡 Geplant.** Technischer Backend-/Ordnername bleibt `rpg`, spielerseitig heißt der Server **Mining**. Konzept-Stand: [prison/README.md](prison/README.md).

**Funktion:** Casual Grind-Loop rund um Abbau-Zonen, aufwertbare Spitzhacken und neue Zonen-Freischaltungen

**Version:** Paper 26.2 (geplant)

**Kernsysteme:**
- **Abbau-Zonen** mit Auto-Regeneration
- **Aufwertbare Spitzhacke** als Kern-Progression
- **Verkaufen → Aufwerten → Freischalten** als Haupt-Loop
- **Server-isolierte Economy** mit getrennten Backend-Daten
- **X-Prison-Suite** (`X-Prison`, `XPrivateMines`, `XPrisonArmors`, `XRobots`) als aktueller Repo-Bestand für den geplanten Mining-Stack

**Datenhaltung:**
- **X-Prison / XRobots:** lokal (H2/Plugin-Storage)
- **CMI:** eigene MySQL-Datenbank `S3_CMI`
- **Plan** / **LuckPerms:** zentrale Netzwerk-Dienste

**Besonderheiten:**
- Geplant als eigener Grind-/Progressions-Server, nicht als Fortsetzung des alten RPG-Modus
- Technischer Slot/Velocity-Name bleibt `rpg`; öffentliche Kommunikation nutzt **Mining**
