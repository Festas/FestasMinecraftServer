# 🌐 festas_builds Website

[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![nginx](https://img.shields.io/badge/Server-nginx-009639?logo=nginx&logoColor=white)](https://nginx.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

> The public website for the **festas_builds** Minecraft community, served at
> [`mc.festas-builds.com`](https://mc.festas-builds.com).

This repository contains **only the website**. It is a static, responsive landing
page (plus a small wiki) built with HTML, CSS and vanilla JavaScript, packaged as
a lightweight `nginx:alpine` Docker image and deployed automatically via GitHub
Actions.

---

## 🗂️ Repository Structure

```
.
├── website/                # The static website (source of the Docker image)
│   ├── index.html          # Landing page
│   ├── impressum.html      # Legal notice
│   ├── datenschutz.html    # Privacy policy
│   ├── css/                # Stylesheets
│   ├── js/                 # Interactive features + build-time config template
│   ├── images/             # Images and favicons
│   ├── api/                # Live player-list snapshot (players.json)
│   ├── wiki/               # Wiki pages (ranks, classes, game modes, commands, rules)
│   ├── Dockerfile          # Container build instructions
│   ├── nginx.conf          # Web server configuration
│   ├── README.md           # Website details & customization
│   └── DEPLOYMENT.md       # Deployment & operations guide
├── docker-compose.web.yml  # Website container definition
└── .github/workflows/      # CI/CD (build, deploy, lint, security)
```

---

## 🚀 Quick Start (Local Development)

```bash
cd website/

# Option 1: Simple static server
python3 -m http.server 8000        # then open http://localhost:8000

# Option 2: Build & run the Docker image
docker build -t minecraft-web .
docker run -p 8080:80 minecraft-web  # then open http://localhost:8080
```

See [`website/README.md`](website/README.md) for customization details.

---

## 📦 Deployment

Deployment is automated with GitHub Actions. Pushing changes under `website/` to
the `main` branch builds a Docker image, pushes it to the GitHub Container
Registry, and deploys it to the server behind the host nginx reverse proxy.

Full instructions (architecture, DNS, monitoring, troubleshooting) are in
[`website/DEPLOYMENT.md`](website/DEPLOYMENT.md).

---

## 📜 License

Released under the MIT License. See [LICENSE](LICENSE) for details.

*Built with ❤️ for the festas_builds community*
