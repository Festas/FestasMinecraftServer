# Website Deployment & Operations

This document explains how the festas_builds website (`mc.festas-builds.com`) is
built, deployed and operated. The site is a static landing page served by nginx
inside a Docker container.

**Setup Difficulty:** ⭐⭐ Medium
**Estimated Setup Time:** 45–90 minutes

---

## 📋 Dependencies

**Required:**
- Docker (for containerization)
- Nginx (web server within the container)
- Nginx (reverse proxy on the host, managed by the `Festas/Link-in-Bio` repo)
- Domain name with DNS access

**Optional:**
- GitHub Actions (for automated deployment)
- Analytics tools (Google Analytics, Plausible, etc.)

**Configuration Files:**
- `docker-compose.web.yml` – Docker Compose configuration
- `website/Dockerfile` – Container build instructions
- `website/nginx.conf` – Nginx web server configuration
- `.github/workflows/deploy-website.yml` – CI/CD pipeline

---

## 🌐 Overview

The website is a modern, responsive landing page that provides:
- Server information and connection details
- Feature highlights
- How-to-join instructions for Java and Bedrock Edition
- Plugin showcase
- Server rules
- Links to BlueMap and contact options

The website is containerized using Docker and served via nginx, integrated with
the existing host nginx reverse proxy infrastructure.

The website is deployed to its own directory (`/home/deploy/minecraft-website`),
independent from any other service on the host.

---

## 🏗️ Architecture

### Components

1. **Static Website** (`website/` directory)
   - HTML, CSS, JavaScript
   - Minecraft-themed design
   - Mobile responsive

2. **Docker Container**
   - Base image: `nginx:alpine`
   - Optimized nginx configuration
   - Health checks included

3. **Reverse Proxy**
   - Managed by nginx (on the host server, `Festas/Link-in-Bio` repository)
   - Handles SSL/TLS termination
   - Compression and caching

4. **CI/CD Pipeline**
   - GitHub Actions workflow
   - Builds the Docker image
   - Pushes to GitHub Container Registry
   - Deploys to the server automatically

5. **Live Player List**
   - The website reads a player snapshot from `/api/players.json`
   - `website/api/players.json` ships a static sample used for local builds
   - In production the file is provided via the read-only `./data` volume mount
     (see `docker-compose.web.yml`), which the migrated server writes to

### Network Setup

```
Internet
   ↓
Nginx (Port 80/443)
   ↓ proxy_pass
minecraft-web container (127.0.0.1:8201 → Port 80)
```

---

## 📁 File Structure

```
website/
├── index.html           # Main landing page
├── impressum.html       # Legal notice
├── datenschutz.html     # Privacy policy
├── css/                 # Stylesheets
├── js/                  # Interactive features + build-time config template
├── images/              # Images and favicons
├── api/                 # Live player-list snapshot (players.json), served at /api/
├── wiki/                # Wiki pages (ranks, classes, game modes, commands, rules)
├── Dockerfile           # Container build instructions
├── nginx.conf           # Nginx configuration
└── .dockerignore        # Files to exclude from build

docker-compose.web.yml   # Docker Compose configuration
.github/workflows/
└── deploy-website.yml   # CI/CD deployment workflow
```

---

## 🚀 Deployment

### Automatic Deployment

The website automatically deploys when changes are pushed to the `main` branch:

1. GitHub Actions builds a Docker image
2. Image is pushed to `ghcr.io/festas/minecraft-server-web:latest`
3. The server pulls the latest image
4. The container is restarted with the new version

**Triggers:**
- Push to `main` branch with changes in `website/**`
- Manual workflow dispatch from the GitHub Actions tab

### Manual Deployment

If you need to deploy manually:

```bash
# SSH to your server
ssh deploy@your-server-ip

# Navigate to the website directory
cd /home/deploy/minecraft-website

# Copy docker-compose.web.yml if not already present
# (from this repository)

# Build and start the container
docker-compose -f docker-compose.web.yml up -d --build
```

---

## ⚙️ Configuration

### Nginx Reverse Proxy Setup

The nginx reverse proxy is centrally managed in the `Festas/Link-in-Bio`
repository. The configuration file for this service is:

```
Festas/Link-in-Bio/nginx/sites-available/mc.festas-builds.com.conf
```

The nginx config proxies `mc.festas-builds.com` to `http://127.0.0.1:8201`, where
the `minecraft-web` container listens. No changes to nginx are needed unless the
port or domain changes.

### DNS Configuration

Ensure your DNS records point to your server:

```
Type: A
Name: mc
Value: <your-server-ip>
TTL: 3600
```

Or use a CNAME if you prefer:

```
Type: CNAME
Name: mc
Value: festas-builds.com
TTL: 3600
```

### Docker Network

The website container binds to `127.0.0.1:8201` on the host. No external Docker
network is required — the host nginx reverse proxy connects directly to this port.

---

## 🛠️ Development

### Local Development

To work on the website locally:

```bash
# Navigate to the website directory
cd website/

# Option 1: Simple HTTP server (Python)
python3 -m http.server 8000

# Option 2: Using Docker
docker build -t minecraft-web-dev .
docker run -p 8080:80 minecraft-web-dev

# Option 3: Using docker-compose
docker-compose -f ../docker-compose.web.yml up
```

Then visit `http://localhost:8000` (or `8080` for Docker).

### Making Changes

1. **Update Content:**
   - Edit `website/index.html` for content changes
   - Edit `website/css/style.css` for styling
   - Edit `website/js/main.js` for functionality

2. **Test Locally:**
   - Use one of the local development methods above
   - Test on different screen sizes (mobile, tablet, desktop)

3. **Commit and Push:**
   ```bash
   git add website/
   git commit -m "Update website content"
   git push origin main
   ```

4. **Automatic Deployment:**
   - GitHub Actions will automatically build and deploy
   - Check the Actions tab for deployment status

### Customization Guide

Build-time values (Minecraft version and server software) are
injected via `website/js/config.template.js` and the `deploy-website.yml`
build args. Other content lives directly in the HTML/CSS/JS:

- **Server IP** – edit `website/index.html` (`<span id="serverIp">`)
- **Features** – edit the `.features-grid` section in `website/index.html`
- **BlueMap / Stats URLs** – edit `website/js/config.template.js`
- **Colors** – edit the `:root` section in `website/css/style.css`

---

## 🔍 Monitoring

### Check Container Status

```bash
docker ps | grep minecraft-web              # View running container
docker logs -f minecraft-web                # View container logs
docker inspect minecraft-web | grep -A 5 Health
```

### Access Logs

```bash
docker exec minecraft-web cat /var/log/nginx/access.log
docker exec minecraft-web cat /var/log/nginx/error.log
docker logs -f minecraft-web
```

### Health Check

The container includes a health check that runs every 30 seconds:

```bash
docker inspect --format='{{.State.Health.Status}}' minecraft-web
```

Possible statuses:
- `healthy` – Container is working correctly
- `unhealthy` – Container is not responding
- `starting` – Health check hasn't completed yet

---

## 🐛 Troubleshooting

### Website not accessible

1. **Check the container is running:**
   ```bash
   docker ps | grep minecraft-web
   ```

2. **Check nginx is running and configured:**
   ```bash
   sudo systemctl status nginx
   sudo nginx -t
   ```

3. **Verify port binding:**
   ```bash
   curl -I http://127.0.0.1:8201/
   ```

4. **Test the container directly:**
   ```bash
   docker exec minecraft-web wget -O- http://localhost/
   ```

### Container fails to start

1. **Check logs:**
   ```bash
   docker logs minecraft-web
   ```

2. **Rebuild the container:**
   ```bash
   cd /home/deploy/minecraft-website
   docker-compose -f docker-compose.web.yml up -d --build
   ```

3. **Verify the Dockerfile:** ensure all files exist in the `website/` directory.

### Changes not appearing

1. **Clear browser cache:** hard refresh with `Ctrl+F5` (Windows/Linux) or
   `Cmd+Shift+R` (Mac).

2. **Verify deployment:**
   ```bash
   docker inspect ghcr.io/festas/minecraft-server-web:latest | grep Created
   ```

3. **Force rebuild:**
   ```bash
   docker-compose -f docker-compose.web.yml up -d --build --force-recreate
   ```

### SSL/TLS issues

SSL is handled by the host nginx reverse proxy with Certbot/Let's Encrypt.

1. **Check nginx logs:** `sudo tail -f /var/log/nginx/error.log`
2. **Verify DNS:** `nslookup mc.festas-builds.com`
3. **Reload nginx:** `sudo systemctl reload nginx`

---

## 📊 Performance

- **Gzip Compression:** enabled in the nginx config
- **Static Asset Caching:** 1-year immutable cache for hashed CSS/JS and images;
  HTML served with `no-cache`
- **Minimal Container:** based on `nginx:alpine` (~5 MB base image)

Typical resource usage: CPU < 5% idle, memory ~15–30 MB. Resource limits are
configured in `docker-compose.web.yml`.

---

## 🔐 Security

### Security Headers

The nginx configuration includes security headers:
- `X-Frame-Options: SAMEORIGIN`
- `X-Content-Type-Options: nosniff`
- `X-XSS-Protection: 1; mode=block`
- `Referrer-Policy: no-referrer-when-downgrade`

### SSL/TLS

- Handled by the host nginx reverse proxy with Certbot/Let's Encrypt
- Automatic certificate renewal via Certbot
- Configured in `Festas/Link-in-Bio/nginx/sites-available/mc.festas-builds.com.conf`

### Container Security

- Runs as a non-root user (nginx user)
- No unnecessary ports exposed
- Health checks enabled
- Resource limits applied

---

## 📚 Additional Resources

- **Nginx Documentation:** <https://nginx.org/en/docs/>
- **Docker Best Practices:** <https://docs.docker.com/develop/dev-best-practices/>
- **Certbot/Let's Encrypt:** <https://certbot.eff.org/docs/>

---

## 📝 Notes

- The website is static (HTML/CSS/JS only) — no backend required.
- BlueMap and stats links are configured via `website/js/config.template.js`.
- Consider adding analytics (Google Analytics, Plausible, etc.) if desired.
