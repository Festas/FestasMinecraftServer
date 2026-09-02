# Festas Minecraft Server

Dieses Repository enthält das komplette Projekt für das Minecraft-Servernetzwerk von Festas Builds: Proxy, Spielerserver, Website, Infrastruktur, Dokumentation und Deployment-Automationen.

> Status: Minecraft/Paper 26.2. Aktiv: Lobby und Survival. Skyblock wird weiterentwickelt. Der Ordner `rpg/` ist das Mining-/Prison-Backend und wird im Frontend als `mining` geführt.

## Überblick

Das Projekt vereint zwei zentrale Bereiche:

- Server- und Netzwerk-Konfigurationen für das Minecraft-Netzwerk
- die öffentliche Website auf `mc.festas-builds.com`

Damit bildet dieses Repository die komplette Projektbasis für Betrieb, Deployment und Web-Auftritt.

## Server- und Netzwerkstatus

- `mc.festas-builds.com`: öffentliche IP / Hauptdomain
- `lobby`: Hub/Lobby-Server
- `survival`: aktiver Survival-Server
- `skyblock`: in Weiterentwicklung
- `rpg` / `mining`: Mining-/Prison-Backend

## Technische Grundlage

- **Proxy:** Velocity
- **Backends:** Paper
- **Minecraft-Version:** 26.2
- **Datenbanken:** MariaDB/MySQL, Redis, SQLite je nach Server und Plugin
- **Website:** statische HTML/CSS/JS-Seite, ausgeliefert via nginx und Docker

## Repository-Struktur

```text
.
├── .github/workflows/     # Deployments, Syncs, Wartung, CI
├── proxy/                 # Velocity + Proxy-Plugins
├── lobby/                 # Lobby-Serverkonfigurationen
├── survival/              # Survival-Serverkonfigurationen
├── skyblock/              # Skyblock-Konfigurationen
├── rpg/                   # Mining-/Prison-Backend
├── website/               # Öffentliche Landingpage + Web-Assets
├── nginx/                 # Nginx-Konfigurationen
├── infra/                 # Infrastruktur / Services
├── tools/                 # Exporter, Wartungs-Helfer, Hilfstools
├── docs/                  # Architektur, Betrieb, Workflows, Referenzen
├── server-logs/           # aktuelle und historisierte Logs
├── .gitignore             # Ignorierte Dateien / Log-Artefakte
├── CONTRIBUTING.md        # Beitrags- und Workflow-Richtlinien
├── QUICKREF.md            # Kurzreferenz
├── README.md              # Dieses Projekt-README
├── README-website.md      # Legacy-Redirect zur zentralen Doku
├── SECRETS.md             # Secret- und Deployment-Referenz
├── LICENSE-website        # Website-Lizenz
├── docker-compose.web.yml # Website-Container-Definition
└── ...
```

## Website

Die Website liegt unter `website/` und ist die öffentliche Präsentation von Festas Builds. Sie enthält die Landingpage, die Wiki-Abschnitte, Serverstatus-Komponenten und die dafür nötigen Ressourcen.

Wichtige Dateien:

- `website/index.html`: Landingpage
- `website/css/`: Styling
- `website/js/`: Interaktivität und Konfigurationslogik
- `website/api/`: Server-/Spielerstatusdaten
- `website/Dockerfile`: Containerdefinition
- `website/DEPLOYMENT.md`: Deployment- und Betriebsdoku
- `website/README.md`: Website-spezifische Details

## Server- und Plugin-Stack

Das Netzwerk besteht aus einem Velocity-Proxy und mehreren Paper-Backends. Die wichtigsten Teile sind:

- `proxy/`: Routing, Forwarding, Netzwerk-Plugins, TAB-Setup, Plan, etc.
- `lobby/`: Hub- und Navigationsbereich
- `survival/`: aktiver Survival-Server mit Gateway-Funktionen und Spielsystemen
- `skyblock/`: Inselprojekt und weiterentwickeltes Skyblock-Setup
- `rpg/`: Mining-/Prison-Backend, im Frontend als `mining` sichtbar

## Dokumentation

Die aktuelle Projekt-Dokumentation liegt hauptsächlich in `docs/` und wird durch folgende zentrale Referenzen ergänzt:

- `docs/ARCHITECTURE.md`: Netz- und Server-Architektur
- `docs/OPERATIONS.md`: Betrieb, Start/Stopp, Wartung und Monitoring
- `docs/PLUGINS.md`: Plugin-Übersicht je Server
- `docs/WORKFLOWS.md`: häufige Aufgaben und Deployment-Workflows
- `docs/CHECKLISTS.md`: Checklisten und Standards
- `docs/NEW_SERVERS.md`: Skyblock-/Mining-Planung und Entscheidungen

Zusätzliche Referenzen:

- `QUICKREF.md`
- `SECRETS.md`
- `CONTRIBUTING.md`
- `README-website.md` (Redirect / Legacy-Stub)

## Deployment und Betrieb

Deployments laufen primär über GitHub Actions. Dabei werden Konfigurationen und Website-Materialien automatisch synchronisiert und auf den Zielserver deployed.

Zentrale Regeln:

- keine echten Secrets im Repository
- keine echten Passwörter oder Tokens in konfigurierten Dateien
- Doku muss mit Struktur- und Deployment-Änderungen mitlaufen
- Log-Artefakte werden als erzeugte Ausgaben behandelt und nicht als dauerhafte Konfigurationsquelle verwendet

## Beitragen

Beiträge sind willkommen, sofern sie:

- zur aktuellen Server-Architektur passen,
- keine echten Secrets oder Passwörter enthalten,
- die vorhandenen Workflows und Konventionen respektieren,
- die betroffene Doku aktualisieren, wenn sich Struktur oder Deployment ändern.

Bitte vor dem Commit die Konventionen in `CONTRIBUTING.md` und die Secret-Regeln in `SECRETS.md` beachten.

## Lizenz

Für die Website gilt die Lizenz in `LICENSE-website`. Für andere Projektteile gelten die jeweiligen, im Repository enthaltenen Regeln.

## Hinweis zu den README-Dateien

Dieses Repository hatte bisher eine separate Website-README. Die Projekt-Dokumentation ist jetzt in diesem zentralen `README.md` zusammengeführt, damit es für das komplette Projekt nur noch eine Haupt-Doku gibt.
