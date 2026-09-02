# Quick Reference

Kurzreferenz für die wichtigsten Ordner, Workflows und Befehle in diesem Repository.

## Repository-Struktur

```text
.
├── .github/workflows/     # Deployment, Sync, Wartung
├── proxy/                 # Velocity + Proxy-Plugins
├── lobby/                 # Lobby Server
├── survival/              # Survival Server
├── skyblock/              # Skyblock-Entwicklung
├── rpg/                   # Mining-/Prison-Backend
├── website/               # Website für mc.festas-builds.com
├── nginx/                 # Nginx-Konfigurationen
├── infra/                 # Infrastruktur-Assets
├── tools/                 # Exporter / Wartungs-Helfer
├── docs/                  # technische und operative Doku
├── server-logs/           # aktuelle Logs + Historie
├── README.md              # Projekt-Übersicht
├── QUICKREF.md            # diese Kurzreferenz
├── CONTRIBUTING.md        # Beitragsrichtlinien
├── SECRETS.md             # Secrets und Deployment-Handling
├── README-website.md      # Website-Guide
└── docker-compose.web.yml # Website-Container
```

## Wichtige Bestandteile

- `proxy/velocity.toml`: Proxy-Server-Map und Forwarding-Konfiguration
- `lobby/`: Lobby-Plugins und Hub-Skripte
- `survival/`: Survival-Plugins, Shop, Jobs, Rankup, Plots
- `skyblock/`: Insel-Umsetzung mit Freunde-Koop / SuperiorSkyblock2
- `rpg/`: Mining-/Prison-Backend, als `mining` im Frontend sichtbar
- `website/`: statische Landingpage mit Serverstatus und Online-Spieler
- `docs/`: Architektur, Betrieb und Referenzmaterial

## Häufige Arbeitsbefehle

```bash
# Status prüfen
git status

# Änderungen stagen
git add .

# Commit
git commit -m "Update: Security/Config docs"

# Branch / PR
git checkout -b feature/kurze-beschreibung
```

## Konfigurations-Workflow

Die Server-Root-Configs (`server.properties`, `paper-global.yml`, `velocity.toml`, etc.) werden über GitHub Actions synchronisiert. Das Repo ist dafür die Quelle des Truth, der Server ist die Zielinstanz.

Wichtige Richtlinien:

- Keine echten Secrets im Repository committen
- YAML nur mit sauberer Einrückung und korrekter Syntax
- Änderungen an Plugins und Server-Config mit den passenden Docs abgleichen
- Wenn Web-/Deploy-Artefakte betroffen sind, die Website- und Workflow-Doku aktualisieren

## Gängige Server-Pfade

```bash
# Proxy
proxy/plugins/

# Lobby
lobby/plugins/

# Survival
survival/plugins/

# Skyblock
skyblock/plugins/

# Mining / rpg
rpg/plugins/
```

## In-Game / Betrieb

```text
/lp user <player> group set <group>
/cmi reload
/rg reload
/is admin reload
```

## Zuletzt prüfen vor Commit

1. YAML-Syntax inspizieren
2. Referenzen auf alte Servernamen oder veraltete Terminologie bereinigen
3. Secrets / Passwörter nicht mit aufnehmen
4. README, QUICKREF und verwandte Doku mit aktueller Repo-Architektur abgleichen

## Verweise

- `README.md`
- `CONTRIBUTING.md`
- `SECRETS.md`
- `docs/ARCHITECTURE.md`
- `docs/OPERATIONS.md`
