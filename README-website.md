# Website-Komponente

Diese Repository-Instanz enthält neben den Server-Konfigurationen auch die öffentliche Website für `mc.festas-builds.com`.

Die Website ist bewusst klein, statisch und schnell: HTML, CSS und JavaScript ohne großes Frontend-Framework. Sie wird als Docker-Container über nginx ausgeliefert und per GitHub Actions deployed.

## Inhalt der Website

```text
website/
├── index.html               # Landingpage
├── css/                     # Stylesheets
├── js/                     # Frontend-Logik und Konfigurations-Template
├── images/                 # Favicons und Assets
├── api/                    # Spieler-Statusdaten / Snapshots
├── wiki/                   # Wiki-Inhalte / Infos
├── Dockerfile              # Container-Definition
├── nginx.conf              # Webserver-Konfiguration
├── DEPLOYMENT.md           # Deploy- und Betriebsdoku
├── SERVERSTATUS-ONLINE-SPIELER-GUIDE.md
├── README.md               # Website-Detaildoku
└── ...
```

## Lokales Testen

```bash
cd website
python3 -m http.server 8000
```

Danach die Seite unter `http://localhost:8000` öffnen.

## Docker-Builder

```bash
docker build -t festas-website .
docker run -p 8080:80 festas-website
```

## Deployment

Die Veröffentlichung erfolgt über GitHub Actions. Änderungen am Website-Code werden nach dem Merge in den relevanten Branch über den Workflow in die Zielumgebung deployed.

## Wichtige Hinweise

- Die Website ist nicht das gesamte Repository; sie ist nur ein Teil des Gesamtsystems.
- `mc.festas-builds.com` dient als öffentliche Adresse; der eigentliche Server-Stack lebt im restlichen Repo.
- Wann immer der Website-Status, Player-Export oder die sichtbaren Servernamen geändert werden, auch die betroffenen Root-Dokumente und Deploy-Workflows prüfen.
- Die Lizenz für die Website liegt in `LICENSE-website`.
