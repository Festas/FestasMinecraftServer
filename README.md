# MinecraftMMO

Konfigurationen von all meinen MMO Plugins auf meinem RPG Minecraft Server

> **⚠️ Projekt-Status:** In Entwicklung - Das Grundgerüst steht, aber vieles ist noch Work in Progress!

## Über das Projekt

Dieses Repository enthält alle Konfigurationen für ein Minecraft Paper Server Netzwerk (Version 1.21.10) mit Velocity Proxy.

**Server-IP:** `mc.festas-builds.com`

Das Netzwerk besteht aus 5 Komponenten:
- **Proxy** (Velocity): Netzwerk-Proxy für Server-Routing, MOTD, TAB, Skins
- **Lobby**: Haupt-Lobby für Spieler-Routing
- **Survival**: Standard Survival Server (getrennt von MMO-Servern)
- **Skyblock**: MMO Skyblock mit RPG-Elementen
- **RPG**: Vollständiger MMO-RPG Server

Die **Skyblock** und **RPG** Server kombinieren die besten Elemente von:
- **Hypixel Skyblock**: Progression-System, Custom Items, Stats und Skills
- **Wynncraft RPG**: Quests, Klassen-System (6 Klassen), Story-Elemente

Mit vielen eigenen Verbesserungen und zusätzlichen Features!

## Repository-Struktur

```
MinecraftMMO/
├── .github/workflows/      # CI/CD Workflows
├── docs/                   # Umfangreiche Dokumentation
│   ├── classes/           # Klassen-System Guides
│   ├── economy/           # Wirtschafts-Dokumentation
│   ├── infrastructure/    # Infrastruktur-Dokumentation
│   ├── items/             # Item-System Dokumentation
│   ├── rpg/               # RPG-Server Dokumentation
│   └── skyblock/          # Skyblock-Server Dokumentation
├── proxy/plugins/          # Velocity Proxy Konfigurationen
│   ├── Geyser-Velocity/   # Bedrock-Crossplay
│   ├── libertybans/       # Ban-System
│   ├── minimotd-velocity/ # Server-MOTD
│   ├── skinsrestorer/     # Skin-System
│   └── tab/               # TAB-Liste & Nametags
├── lobby/plugins/          # Lobby-Server Konfigurationen
├── survival/plugins/       # Survival-Server Konfigurationen
├── skyblock/plugins/       # Skyblock-Server Plugin-Konfigurationen
├── rpg/plugins/            # RPG-Server Plugin-Konfigurationen
│   ├── BetonQuest/        # Quest-System
│   ├── BlueMap/           # Live-Weltkarte
│   ├── CMI/               # Server-Management
│   ├── MMOCore/           # Klassen & Gamelogic
│   ├── MMOItems/          # Custom Items (694 Items!)
│   ├── MythicMobs/        # Custom Mobs & Skills
│   ├── RoseLoot/          # Loot-Tabellen
│   └── ...                # Weitere Plugins
├── CONTRIBUTING.md         # Beitragsrichtlinien
├── IMPLEMENTATION_SUMMARY.md
├── QUICKREF.md             # Schnellreferenz
├── README.md
└── USAGE.md                # Nutzungsanleitung
```

## Klassen-System

Das Netzwerk bietet ein umfassendes Klassen-System mit **6 Klassen** (MMOCore):

1. **Krieger** - Tank/Melee DPS
2. **Magier** - Ranged Magic DPS
3. **Assassine** - Schneller Melee DPS
4. **Bogenschütze** - Ranged Physical DPS
5. **Schamane** - Support/Healer
6. **Beschwörer** - Summoner/Pet-Class

## Verwendete Plugins

Die wichtigsten MMO-Plugins, die in diesem Projekt konfiguriert werden:

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

### Gameplay-Systeme
- **[Klassen-System](docs/classes/README.md)** - Detaillierte Guides für alle 6 Klassen
  - [Krieger](docs/classes/KRIEGER.md), [Magier](docs/classes/MAGIER.md), [Assassine](docs/classes/ASSASSINE.md)
  - [Bogenschütze](docs/classes/BOGENSCHUETZE.md), [Schamane](docs/classes/SCHAMANE.md), [Beschwörer](docs/classes/BESCHWOERER.md)
- **[Item-System](docs/items/README.md)** - Item-Pipeline, Tiers, Templates und Crafting
- **[Economy-System](docs/economy/README.md)** - Währungen und Shops

### Server-spezifische Dokumentation
- **[RPG-Server](docs/rpg/README.md)** - Zonen, Quests, Dungeons, Mobs, NPCs
- **[Skyblock-Server](docs/skyblock/README.md)** - Islands, Minions, Progression

### Technische Infrastruktur
- **[Infrastruktur](docs/infrastructure/README.md)** - Datenbanken, Synchronisation, Backups

### Zusätzliche Referenzen
- **[QUICKREF.md](QUICKREF.md)** - Schnellreferenz für häufige Befehle und Konzepte
- **[USAGE.md](USAGE.md)** - Ausführliche Nutzungsanleitung

## Verwendung

Jeder Server-Ordner enthält ein `plugins/` Verzeichnis mit den jeweiligen Plugin-Konfigurationen.

Die Konfigurationsdateien können direkt in die entsprechenden Plugin-Ordner auf dem Server kopiert werden:
```bash
# Beispiel: RPG-Server MythicMobs Konfigurationen deployen
cp -r rpg/plugins/MythicMobs/ /path/to/rpg-server/plugins/MythicMobs/

# Beispiel: Proxy TAB-Konfiguration deployen
cp -r proxy/plugins/tab/ /path/to/velocity/plugins/tab/
```

## Beitragen

Dies ist ein persönliches Projekt für meinen Minecraft Server.

## Technische Details

- **Minecraft Version:** 1.21.10
- **Server Software:** Paper
- **Proxy:** Velocity
- **Datenbanken:** MySQL/MariaDB, Redis
- **Server-IP:** mc.festas-builds.com