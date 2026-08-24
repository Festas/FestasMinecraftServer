# BlueMap – Einrichtung & Nginx-Konfiguration

Dieses Dokument beschreibt, wie BlueMap für den **Survival**- und **Mining (RPG-Slot)**-Server
konfiguriert ist und wie der Nginx-Reverse-Proxy auf dem Host eingerichtet werden muss,
damit die Karten über die Ziel-Subdomains erreichbar sind:

- `https://survival.festas-builds.com` → Survival-BlueMap
- `https://mining.festas-builds.com` → Mining-BlueMap

---

## Ports

| Server   | BlueMap-Port |
|----------|-------------|
| Survival | 8102        |
| Mining   | 8103        |

Beide Server laufen mit dem integrierten BlueMap-Webserver. Die Karten sind unter diesen
Ports lokal auf dem Host erreichbar, dürfen aber **nicht direkt nach außen freigegeben werden** —
der Zugriff läuft ausschließlich über den Nginx-Reverse-Proxy.

---

## Relevante Dateien in diesem Repo

| Bereich | Datei |
|--------|-------|
| BlueMap Survival Port | `survival/plugins/BlueMap/webserver.conf` |
| BlueMap Mining Port | `rpg/plugins/BlueMap/webserver.conf` |
| Nginx-Vhost Survival | `nginx/sites-available/survival.festas-builds.com.conf` |
| Nginx-Vhost Mining | `nginx/sites-available/mining.festas-builds.com.conf` |
| Automatisches TLS-Zertifikat (Deploy) | `.github/workflows/deploy-nginx-configs.yml` |
| BlueMap Survival Download-Option | `survival/plugins/BlueMap/core.conf` |
| BlueMap Mining Download-Option | `rpg/plugins/BlueMap/core.conf` |
| BlueMap Infra-Doku | `docs/infrastructure/BLUEMAP.md` |
| Plugin-Übersicht | `docs/PLUGINS.md` |
| Website-Linkziel | `website/js/config.template.js`, `website/index.html`, `website/wiki/index.html` |

---

## Nginx-Konfiguration (Host)

Die fertigen Vhosts liegen in diesem Repo unter `nginx/sites-available/` (wie die
übrigen Host-Vhosts `mc.festas-builds.com.conf` und `mc-stats.festas-builds.com.conf`)
und werden auf dem Host nach `/etc/nginx/sites-available/` übernommen und über
`sites-enabled` aktiviert:

- `nginx/sites-available/survival.festas-builds.com.conf` → Survival-BlueMap (`127.0.0.1:8102`)
- `nginx/sites-available/mining.festas-builds.com.conf` → Mining-BlueMap (`127.0.0.1:8103`)

Ohne diese Vhosts fällt Nginx für die Subdomains auf den Default-Server zurück, dessen
Zertifikat nur für `festas-builds.com` gilt — der Browser meldet dann
`NET::ERR_CERT_COMMON_NAME_INVALID`.

### TLS-Zertifikate (wichtig!)

Jede BlueMap-Subdomain nutzt ihr **eigenes** Let's-Encrypt-Zertifikat, dessen Name exakt
dem Hostnamen entspricht:

- `survival.festas-builds.com` → `/etc/letsencrypt/live/survival.festas-builds.com/`
- `mining.festas-builds.com` → `/etc/letsencrypt/live/mining.festas-builds.com/`

Sie teilen sich **nicht** das gemeinsame `festas-builds.com`-Zertifikat von `mc.`/`mc-stats.`.
Genau das war die Ursache des Fehlers: Dieses Zertifikat enthält die BlueMap-Subdomains
nicht als SAN, weshalb der Browser beim direkten Aufruf `NET::ERR_CERT_COMMON_NAME_INVALID`
meldet und die `<iframe>`-Einbettung auf der Website mit „Verbindung abgelehnt" fehlschlägt.

Diese Zertifikate werden beim Deploy **automatisch** vom Workflow
`deploy-nginx-configs.yml` per HTTP-01-Webroot-Challenge angefordert
(`certbot certonly --webroot -w /var/www/certbot --cert-name <host> -d <host>`), sobald sie
fehlen. Der Schritt ist idempotent (vorhandene Zertifikate werden übersprungen), fasst das
gemeinsame `festas-builds.com`-Zertifikat nie an und ist nicht-fatal — schlägt die
Ausstellung fehl (z. B. weil DNS/Port 80 noch nicht erreichbar sind), wird der jeweilige
HTTPS-Vhost übersprungen und beim nächsten Deploy erneut versucht.

> **Voraussetzung:** DNS für die Subdomain muss auf den Host zeigen und Port 80 muss von
> außen erreichbar sein, damit die ACME-Challenge unter
> `http://<host>/.well-known/acme-challenge/` beantwortet werden kann.

### Host-basiertes Routing (Referenz)

```nginx
# HTTP → HTTPS Redirect (beide Hosts)
server {
    listen 80;
    server_name survival.festas-builds.com mining.festas-builds.com;
    return 301 https://$host$request_uri;
}

# Survival: https://survival.festas-builds.com
server {
    listen 443 ssl;
    server_name survival.festas-builds.com;
    # SSL …
    location / {
        proxy_pass         http://127.0.0.1:8102/;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
        proxy_hide_header  X-Frame-Options;   # erlaubt <iframe>-Einbettung auf der Website
        proxy_read_timeout 3600s;
        proxy_buffering    off;
    }
}

# Mining: https://mining.festas-builds.com
server {
    listen 443 ssl;
    server_name mining.festas-builds.com;
    # SSL …
    location / {
        proxy_pass         http://127.0.0.1:8103/;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
        proxy_hide_header  X-Frame-Options;   # erlaubt <iframe>-Einbettung auf der Website
        proxy_read_timeout 3600s;
        proxy_buffering    off;
    }
}
```

> **Wichtig:** `proxy_buffering off` und `proxy_read_timeout 3600s` sind notwendig,
> damit die **Server-Sent Events (SSE)** für Live-Player-Marker funktionieren.

---

## Checkliste – Was du noch tun musst

Nach dem Pushen dieser Config-Änderungen:

1. **RPG/Mining-Server neu starten** — damit `accept-download: true` greift und
   BlueMap die Minecraft-Ressourcen herunterlädt (einmalig, braucht Internetzugang).
2. **Nginx-Konfiguration aktivieren** — passiert automatisch beim Push über den Workflow
   `deploy-nginx-configs.yml` (kopiert die Vhosts nach `/etc/nginx/sites-available/`,
   verlinkt sie in `sites-enabled` und lädt Nginx neu). Manuell alternativ:
   `sudo nginx -t && sudo nginx -s reload`.
3. **DNS-Einträge setzen** — A/CNAME für `survival.festas-builds.com` und
   `mining.festas-builds.com` auf den Host. (Voraussetzung für die automatische
   Zertifikatsausstellung.)
4. **SSL-Zertifikat** — jede Subdomain nutzt ihr **eigenes** Zertifikat
   (`/etc/letsencrypt/live/survival.festas-builds.com/` bzw. `.../mining.festas-builds.com/`),
   **nicht** das gemeinsame `festas-builds.com`-Zertifikat. Der Workflow
   `deploy-nginx-configs.yml` fordert fehlende Zertifikate beim Deploy automatisch per
   Webroot-Challenge an — es ist **kein manueller Certbot-Schritt** mehr nötig.
   Falls die Automatik nicht laufen kann, manuell einmalig ausstellen:
   `sudo certbot certonly --webroot -w /var/www/certbot --cert-name survival.festas-builds.com -d survival.festas-builds.com`
   und analog für `mining.festas-builds.com`.
5. **Firewall** — Ports 8102 und 8103 dürfen **nicht** direkt von außen erreichbar sein
   (`ufw deny 8102` / `ufw deny 8103` oder einfach nicht öffnen).
6. **Website-Links prüfen** — BlueMap-Links sollen auf die neuen Subdomains zeigen.

---

## Verifikation

1. `survival/plugins/BlueMap/webserver.conf` enthält `port: 8102`.
2. `rpg/plugins/BlueMap/webserver.conf` enthält `port: 8103`.
3. Beide `core.conf` enthalten `accept-download: true`.
4. `curl -I https://survival.festas-builds.com` liefert eine Antwort vom Survival-Upstream.
5. `curl -I https://mining.festas-builds.com` liefert eine Antwort vom Mining-Upstream.
6. Zertifikat prüfen — der `subject` muss zum Hostnamen passen:
   `echo | openssl s_client -servername survival.festas-builds.com -connect survival.festas-builds.com:443 2>/dev/null | openssl x509 -noout -subject`
   (analog für `mining.festas-builds.com`).
7. BlueMap im Browser öffnen und prüfen, dass jeweils die erwartete Welt geladen wird.

---

## Troubleshooting

| Problem | Ursache | Lösung |
|---------|---------|--------|
| BlueMap startet nicht / keine Texturen | `accept-download: false` | In `core.conf` auf `true` setzen ✅ (bereits gefixt) |
| Einer der Server zeigt keine Karte | Port-Konflikt / falsches Upstream-Routing | Survival muss auf `8102`, Mining auf `8103` zeigen |
| Live-Player verschwinden sofort | SSE durch Proxy unterbrochen | `proxy_buffering off` + `proxy_read_timeout 3600s` setzen |
| Direkter Aufruf zeigt `NET::ERR_CERT_COMMON_NAME_INVALID` (und `<iframe>` „Verbindung abgelehnt") | Vhost lieferte das gemeinsame `festas-builds.com`-Zertifikat aus, das die Subdomain nicht als SAN enthält | Vhost auf das **dedizierte** Zertifikat `/etc/letsencrypt/live/<host>/` umstellen ✅ (bereits gefixt); die Ausstellung übernimmt `deploy-nginx-configs.yml` automatisch. Prüfen mit dem `openssl s_client … -subject`-Befehl aus der Verifikation |
| Karte lädt direkt, aber **nicht im `<iframe>`** der Website (`… hat die Verbindung abgelehnt`) | BlueMaps integrierter Webserver sendet `X-Frame-Options: SAMEORIGIN` (v.a. ältere Versionen wie 5.14); der Browser verweigert dadurch das Einbetten | Im Nginx-Vhost `proxy_hide_header X-Frame-Options;` setzen ✅ (bereits in beiden Vhosts gefixt). Danach mit `curl -sI https://survival.festas-builds.com \| grep -i x-frame` prüfen, dass der Header weg ist |
| Karte lädt, aber keine Tiles | BlueMap hat noch nicht gerendert | `/bluemap render` im Server ausführen |
