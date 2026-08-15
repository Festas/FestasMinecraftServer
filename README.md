# MinecraftMMO

Konfigurationen für mein Minecraft Server-Netzwerk (Paper + Velocity)

> **⚠️ Projekt-Status (Umstellung auf 26.2):** Das Netzwerk läuft jetzt auf **Minecraft 26.2** und wird gerade komplett überarbeitet. Der Fokus liegt **aktuell nur auf Lobby und Survival** — die Plugins dieser beiden Server wurden frisch aufgeräumt und neu übertragen. Die MMO-Server **Skyblock** und **RPG** werden **zeitnah eingestellt** und durch **zwei neue Server** ersetzt (Auswahl noch offen). Ihre Dokumentation wird bis dahin nur noch als **Archiv** geführt.

## Über das Projekt

Dieses Repository enthält alle Konfigurationen für ein Minecraft Paper Server Netzwerk (Version 26.2) mit Velocity Proxy.

**Server-IP:** `mc.festas-builds.com`

### Aktiver Fokus
- **Lobby**: Haupt-Hub für Spieler-Routing und Server-Navigation
- **Survival**: Survival-Server mit Tycoon-Gamemode (eigene Economy)

### Wird eingestellt (Archiv, in Ablösung)
- **Skyblock**: MMO Skyblock mit RPG-Elementen
- **RPG**: Vollständiger MMO-RPG Server

### Geplant
- **2 neue Server** als Ersatz für Skyblock & RPG (noch nicht festgelegt)

Die (auslaufenden) **Skyblock** und **RPG** Server kombinierten die besten Elemente von:
- **Hypixel Skyblock**: Progression-System, Custom Items, Stats und Skills
- **Wynncraft RPG**: Quests, Klassen-System (6 Klassen), Story-Elemente

Die zugehörige Doku bleibt vorerst als Referenz erhalten, wird aber nicht weiter gepflegt.

## Repository-Struktur

```
MinecraftMMO/
├── proxy/              # Velocity Proxy Konfigurationen
│   └── plugins/        # Proxy-Plugins (TAB, MiniMOTD, LibertyBans, Geyser, etc.)
├── lobby/              # Lobby Server Konfigurationen  (AKTIV)
│   └── plugins/        # Lobby-Plugins (CMI, DeluxeMenus, Skript, Oraxen, etc.)
├── survival/           # Survival Server Konfigurationen  (AKTIV)
│   └── plugins/        # Survival-Plugins (NextGens, Jobs, Rankup, PlotSquared, etc.)
├── skyblock/           # Skyblock Server Konfigurationen  (ARCHIV — wird eingestellt)
│   └── plugins/        # MMO-Plugins (MMOCore, MMOItems, MythicMobs, etc.)
├── rpg/                # RPG Server Konfigurationen  (ARCHIV — wird eingestellt)
│   └── plugins/        # RPG-Plugins (MythicMobs Premium, MythicDungeons, etc.)
├── crosscraft-guilds/  # CrossCraft Guilds Plugin (Gradle, Java 21)
│   └── ...             # Paper + Velocity Module
└── docs/               # Dokumentation
```

## Klassen-System (Archiv — Skyblock & RPG)

> Das Klassen-System gehört zu den auslaufenden MMO-Servern Skyblock & RPG und wird mit deren Abschaltung ersetzt.

Das (auslaufende) MMO bot ein umfassendes Klassen-System mit **6 Klassen** (MMOCore):

1. **Krieger** - Tank/Melee DPS
2. **Magier** - Ranged Magic DPS
3. **Assassine** - Schneller Melee DPS
4. **Bogenschütze** - Ranged Physical DPS
5. **Schamane** - Support/Healer
6. **Beschwörer** - Summoner/Pet-Class

## Verwendete Plugins

### Aktive Server (Lobby & Survival)

- **CMI** (+CMILib): Core-Management, Chat, Teleport, Kits, Economy-Backend
- **NextGens**: Generator-System (Tycoon-Kern auf Survival)
- **Jobs** & **Rankup**: Economy-Jobs und Rang-Progression (Survival)
- **PlotSquared**: Plot-Claiming (Survival)
- **ShopGUIPlus** & **GlobalMarketPlus**: Shop & Marktplatz (Survival)
- **DeluxeMenus** & **Skript**: GUIs und Custom-Logik (Lobby & Survival)
- **Oraxen**: Custom Items und Texturen
- **LuckPerms**, **PlaceholderAPI**, **Vault**, **ProtocolLib**: Basis-Infrastruktur
- **BlueMap**: 3D-Web-Karte (Survival)

### Auslaufende MMO-Server (Skyblock & RPG, Archiv)

- **MythicMobs** (Premium auf RPG): Custom Mobs, Items und Skills
- **MMOCore & MMOItems**: Klassen- und Custom-Item-System
- **BetonQuest**: Advanced Quest-System
- **MythicDungeons**: Instanzierte Dungeons
- **Citizens**: NPC-System
- **HuskSync**: Daten-Synchronisation zwischen RPG & Skyblock
- **CoinsEngine**: Multi-Währungs-System

Vollständige Plugin-Liste siehe [docs/PLUGINS.md](docs/PLUGINS.md)

## Dokumentation

Umfassende Dokumentation findest du im [`/docs`](docs/) Verzeichnis:

### Allgemeine Dokumentation
- **[PLANNING.md](docs/PLANNING.md)** - Fragenkatalog für Planung und Entwicklung
- **[ARCHITECTURE.md](docs/ARCHITECTURE.md)** - Netzwerk-Architektur und technische Details
- **[PLUGINS.md](docs/PLUGINS.md)** - Vollständige Plugin-Referenz pro Server
- **[WORKFLOWS.md](docs/WORKFLOWS.md)** - Workflow-Templates für häufige Aufgaben
- **[CHECKLISTS.md](docs/CHECKLISTS.md)** - Checklisten für Content-Erstellung und Testing

### Gameplay-Systeme (Archiv — Skyblock & RPG)
> Diese Systeme gehören zu den auslaufenden MMO-Servern und werden nicht weiter gepflegt.
- **[Klassen-System](docs/classes/README.md)** - Detaillierte Guides für alle 6 Klassen
  - [Krieger](docs/classes/KRIEGER.md), [Magier](docs/classes/MAGIER.md), [Assassine](docs/classes/ASSASSINE.md)
  - [Bogenschütze](docs/classes/BOGENSCHUETZE.md), [Schamane](docs/classes/SCHAMANE.md), [Beschwörer](docs/classes/BESCHWOERER.md)
- **[Item-System](docs/items/README.md)** - Item-Pipeline, Tiers, Templates und Crafting
- **[Economy-System](docs/economy/README.md)** - Währungen und Shops

### Server-spezifische Dokumentation
- **[Survival-Server](docs/survival/README.md)** — Tycoon, Plots, Progression *(aktiv)*
- **[RPG-Server](docs/rpg/README.md)** - Zonen, Quests, Dungeons, Mobs, NPCs *(Archiv, wird eingestellt)*
- **[Skyblock-Server](docs/skyblock/README.md)** - Islands, Minions, Progression *(Archiv, wird eingestellt)*

### Technische Infrastruktur
- **[Infrastruktur](docs/infrastructure/README.md)** - Datenbanken, Synchronisation, Backups
- **[OPERATIONS.md](docs/OPERATIONS.md)** - Betriebshandbuch (Start/Stopp, Wartung, Monitoring)
- **[DISASTER_RECOVERY.md](docs/DISASTER_RECOVERY.md)** - Notfall-Wiederherstellung

### Zusätzliche Referenzen
- **[QUICKREF.md](QUICKREF.md)** - Schnellreferenz für häufige Befehle und Konzepte

## Verwendung

Jeder Server-Ordner (`lobby/`, `survival/`, `proxy/` — aktiv; `skyblock/`, `rpg/` — Archiv) enthält seine eigenen Plugin-Konfigurationen unter `plugins/`.

Die Konfigurationsdateien können direkt in die entsprechenden Plugin-Ordner auf dem Server kopiert werden:
```bash
# Beispiel für Survival (aktiv)
plugins/NextGens/ <- survival/plugins/NextGens/
```

## Beitragen

Dies ist ein persönliches Projekt für meinen Minecraft Server.

## Technische Details

- **Minecraft Version:** 26.2
- **Server Software:** Paper
- **Proxy:** Velocity
- **Bedrock-Support:** Geyser-Velocity + Floodgate
- **Datenbanken:** MariaDB (172.25.0.1:3306), Redis (172.18.0.1:6379)
- **Server-IP:** mc.festas-builds.com
- **Aktueller Fokus:** Lobby & Survival — Skyblock & RPG werden zeitnah durch 2 neue Server ersetzt
