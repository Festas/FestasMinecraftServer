# MinecraftMMO

Konfigurationen für mein Minecraft Server-Netzwerk (Paper + Velocity)

> **Projekt-Status:** Netzwerk läuft auf **Minecraft 26.2**. Aktiv gepflegt: **Lobby** und **Survival**. Im Aufbau: **Skyblock** (überarbeitet, ohne Gilden, Freunde-Koop) und **Mining** (Abbau-Zonen mit aufwertbaren Spitzhacken) — Details in [docs/NEW_SERVERS.md](docs/NEW_SERVERS.md). Der RPG-Server wird eingestellt und bis zur Abschaltung als **Archiv** geführt.

## Über das Projekt

Dieses Repository enthält alle Konfigurationen für ein Minecraft Paper Server Netzwerk (Version 26.2) mit Velocity Proxy.

**Server-IP:** `mc.festas-builds.com`

### Aktiver Fokus
- **Lobby**: Haupt-Hub für Spieler-Routing und Server-Navigation
- **Survival**: Survival-Server mit Town und Freebuild (Tycoon-Gamemode aktuell deaktiviert – kommt auf eigenem Server)

### Wird eingestellt (Archiv, in Ablösung)
- **RPG**: Vollständiger MMO-RPG Server

### Neu / in Aufbau
- **Skyblock**: Überarbeiteter Skyblock **ohne Gilden**, aber mit **Freunde-Koop** (Freunde einladen und gemeinsam die Insel bauen)
- **Mining**: Casual-Server mit **Abbau-Zonen** — immer stärkere Spitzhacken bauen mehr Blöcke auf einmal ab, neue Zonen werden nach und nach freigeschaltet

> Auswahl, Bewertung und Plugin-Shortlist siehe [docs/NEW_SERVERS.md](docs/NEW_SERVERS.md).

## Repository-Struktur

```
MinecraftMMO/
├── proxy/              # Velocity Proxy Konfigurationen
│   └── plugins/        # Proxy-Plugins (TAB, MiniMOTD, LibertyBans, Geyser, etc.)
├── lobby/              # Lobby Server Konfigurationen  (AKTIV)
│   └── plugins/        # Lobby-Plugins (CMI, DeluxeMenus, Skript, Oraxen, etc.)
├── survival/           # Survival Server Konfigurationen  (AKTIV)
│   └── plugins/        # Survival-Plugins (NextGens, Jobs, Rankup, PlotSquared, etc.)
├── skyblock/           # Skyblock Server Konfigurationen  (NEU/UMBAU — ohne Gilden, Freunde-Koop)
│   └── plugins/        # Skyblock-Plugins (SuperiorSkyblock2, JetsMinions, etc.)
├── prison/                # Mining Server Konfigurationen  (recycelt aus altem RPG — Aufbau/Gerüst)
│   └── plugins/        # Mining-/Zonen-Kern, WorldGuard, Shop/Auto-Sell, Cosmetics (+ Alt-RPG-Archiv)
└── docs/               # Dokumentation
```

## Verwendete Plugins

### Aktive Server (Lobby & Survival)

- **CMI** (+CMILib): Core-Management, Chat, Teleport, Kits, Economy-Backend
- **NextGens**: Generator-System (Tycoon-Kern, aktuell deaktiviert – kommt auf eigenem Server)
- **Jobs** & **Rankup**: Economy-Jobs und Rang-Progression (Survival)
- **PlotSquared**: Plot-Claiming (Survival)
- **ShopGUIPlus** & **GlobalMarketPlus**: Shop & Marktplatz (Survival)
- **DeluxeMenus** & **Skript**: GUIs und Custom-Logik (Lobby & Survival)
- **Oraxen**: Custom Items und Texturen
- **LuckPerms**, **PlaceholderAPI**, **Vault**, **ProtocolLib**: Basis-Infrastruktur
- **BlueMap**: 3D-Web-Karten (Survival & Mining)

### Skyblock (Umbau) & auslaufender RPG-Server

- **SuperiorSkyblock2**: Insel-Kern inkl. **Koop/Insel-Mitglieder** (Freunde einladen) — ersetzt das frühere Gilden-Konzept
- **JetsMinions**: Minion-/Automations-System (Skyblock)
- **HuskSync**: selektive Synchronisation (Cosmetics/Ränge) zwischen Servern
- **CoinsEngine**: Multi-Währungs-System
- **MythicMobs**, **MMOCore & MMOItems**, **BetonQuest**, **MythicDungeons**, **Citizens** *(RPG-Archiv)*

Vollständige Plugin-Liste siehe [docs/PLUGINS.md](docs/PLUGINS.md)

## Dokumentation

Umfassende Dokumentation findest du im [`/docs`](docs/) Verzeichnis:

### Allgemeine Dokumentation
- **[NEW_SERVERS.md](docs/NEW_SERVERS.md)** - Neue Server (Skyblock überarbeitet & Mining): Auswahl, Kriterien, Plugin-Shortlist, Roadmap
- **[PLANNING.md](docs/PLANNING.md)** - Fragenkatalog für Planung und Entwicklung
- **[ARCHITECTURE.md](docs/ARCHITECTURE.md)** - Netzwerk-Architektur und technische Details
- **[PLUGINS.md](docs/PLUGINS.md)** - Vollständige Plugin-Referenz pro Server
- **[WORKFLOWS.md](docs/WORKFLOWS.md)** - Workflow-Templates für häufige Aufgaben
- **[CHECKLISTS.md](docs/CHECKLISTS.md)** - Checklisten für Content-Erstellung und Testing

### Gameplay-Systeme (Archiv — RPG)
> Diese Systeme gehören zum auslaufenden MMO-Server RPG und werden nicht weiter gepflegt.
- Klassen-System (6 Klassen), Item-System, Economy-System des RPG-Servers

### Server-spezifische Dokumentation
- **[Survival-Server](docs/survival/README.md)** — Survival, Town, Freebuild *(aktiv; Tycoon deaktiviert — kommt auf eigenem Server)*
- **[Skyblock-Server](docs/skyblock/README.md)** — Inseln, Freunde-Koop, Progression *(Umbau — ohne Gilden)*
- **[Prison-Server / Mining](docs/prison/README.md)** — Abbau-Zonen, aufwertbare Spitzhacken *(neu; recycelt den `rpg/`-Slot)*

### Technische Infrastruktur
- **[Infrastruktur](docs/infrastructure/README.md)** - Datenbanken, Synchronisation, Backups
- **[Velocity Exporter](docs/infrastructure/VELOCITY_EXPORTER.md)** - Prometheus-Metriken, Scrape-Setup und Alerts für den Proxy
- **[OPERATIONS.md](docs/OPERATIONS.md)** - Betriebshandbuch (Start/Stopp, Wartung, Monitoring)
- **[DISASTER_RECOVERY.md](docs/DISASTER_RECOVERY.md)** - Notfall-Wiederherstellung

### Zusätzliche Referenzen
- **[QUICKREF.md](QUICKREF.md)** - Schnellreferenz für häufige Befehle und Konzepte

## Verwendung

Jeder Server-Ordner (`lobby/`, `survival/`, `proxy/` — aktiv; `skyblock/` — Umbau; `prison/` — recycelt zum Prison-Server) enthält seine eigenen Plugin-Konfigurationen unter `plugins/`.

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
- **Aktive Server:** Lobby & Survival — Im Aufbau: Skyblock (Umbau) + Mining (neuer `rpg/`-Slot)
