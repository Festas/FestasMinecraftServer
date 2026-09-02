# Festas Minecraft Server

Repository für das Minecraft-Servernetzwerk von Festas Builds. Es enthält die Konfigurationen für den Proxy, die Spielerserver, die Website, die Infrastruktur und die Automationen für Deployments und Wartung.

> Status: Minecraft/Paper 26.2. Aktive Server: Lobby und Survival. Skyblock wird weiterentwickelt. Der Backend-Ordner `rpg/` dient als Mining-/Prison-Server und wird im Frontend als `mining` adressiert.

## Überblick

Das Projekt verbindet vier Bereiche:

- `proxy/`: Velocity-Proxy, Forwarding, Plugin-Konfigurationen und Netzwerk-Setup
- `lobby/`: Hub/Lobby-Server
- `survival/`: Survival-Server
- `skyblock/`: Skyblock-Entwicklung und Insel-Setup
- `rpg/`: Mining-/Prison-Backend (`rpg`-Ordner, öffentlich als `mining` geführt)
- `website/`: statische Landingpage auf `mc.festas-builds.com`
- `nginx/`, `infra/`: Nginx- und Infrastruktur-Config
- `tools/`: Exporter, Wartungs-Skripte und Hilfswerkzeuge
- `docs/`: Betrieb, Architektur und Referenzdokumentation
- `.github/workflows/`: Deployment-, Sync- und Wartungs-Workflows

## Server-Status

- `mc.festas-builds.com`: Haupt-IP des Netzes
- `lobby`: Lobby/Hub
- `survival`: aktiver Survival-Server
- `skyblock`: in Arbeit / Weiterentwicklung
- `rpg` / `mining`: Mining-/Prison-Backend, nicht als veralteter MMO-Server zu behandeln

## Repository-Struktur

```text
.
├── .github/workflows/     # CI/CD, Deployments, Syncs, Wartung
├── proxy/                 # Velocity + Proxy-Plugins
├── lobby/                 # Lobby-Serverkonfigurationen
├── survival/              # Survival-Serverkonfigurationen
├── skyblock/              # Skyblock-Konfigurationen
├── rpg/                   # Mining-/Prison-Backend
├── website/               # Landingpage / web frontend
├── nginx/                 # Nginx vhosts und Konfigurationen
├── infra/                 # Infrastruktur-Beispiele / Services
├── tools/                 # Exporter und Wartungs-Hilfen
├── docs/                  # Projekt-Dokumentation
├── server-logs/           # aktuelle und historisierte Server-Logs
├── CONTRIBUTING.md        # Beitrags- und Workflow-Richtlinien
├── QUICKREF.md            # kurze Referenz
├── README.md              # Projekt-Übersicht
├── README-website.md      # Website-Component Guide
├── SECRETS.md             # Secrets- und Deployment-Referenz
├── LICENSE-website        # Website-Lizenz
└── docker-compose.web.yml # Website-Container-Definition
```

## Technische Grundlage

- **Proxy:** Velocity
- **Backends:** Paper
- **Versionsziel:** Minecraft 26.2
- **Datenbanken:** MariaDB / MySQL, Redis, SQLite je nach Server und Plugin
- **Website:** statische HTML/CSS/JS Seite mit nginx + Docker

## Dokumentation

Die aktuelle Projekt-Dokumentation liegt hauptsächlich in `docs/` und wird je nach Thema über die folgenden Referenzen ergänzt:

- `docs/ARCHITECTURE.md`: Netz- und Server-Architektur
- `docs/OPERATIONS.md`: Betrieb, Start/Stopp, Wartung, Monitoring
- `docs/PLUGINS.md`: Plugin-Übersicht je Server
- `docs/WORKFLOWS.md`: häufige Aufgaben und Deployment-Workflows
- `docs/CHECKLISTS.md`: Checklisten und Standards
- `docs/NEW_SERVERS.md`: Skyblock-/Mining-Planung und Entscheidungen

Zusätzliche Schnell-Referenzen:

- `QUICKREF.md`
- `SECRETS.md`
- `README-website.md`

## Beiträge

Beiträge sind willkommen, sofern sie:

- zur aktuellen Server-Architektur passen,
- keine echten Secrets oder Passwörter enthalten,
- die vorhandenen Workflows und Konventionen respektieren,
- die Dokumentation entsprechend aktualisieren, wenn sich Repo-Struktur oder Deployment ändern.

Bitte vor dem Commit die Konventionen in `CONTRIBUTING.md` und die Secrets-Referenz in `SECRETS.md` beachten.

## Deployment und Betrieb

Deployments erfolgen primär über GitHub Actions. Die Repositories sind auf Wiederholbarkeit ausgelegt:

- Server-Configs werden sauber synchronisiert
- Secrets werden nicht im Repository gespeichert
- Log-Dateien werden nur als erzeugte Artefakte verwaltet und nicht als dauerhafte Konfigurationsquelle verwendet

## Lizenz

Für die Website gilt die Lizenz in `LICENSE-website`. Für andere Projektteile gelten die jeweiligen, im Repository enthaltenen Regeln. Wenn eine Datei keine gesonderte Lizenz trägt, ist sie Teil der allgemeinen Projektkonfiguration und nicht als freies, eigenes Produkt zu behandeln.
