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
2. **Nginx-Konfiguration aktivieren** — die Vhosts aus `nginx/sites-available/`
   (`survival.festas-builds.com.conf`, `mining.festas-builds.com.conf`) auf dem Host
   nach `/etc/nginx/sites-available/` übernehmen, in `sites-enabled` verlinken und
   `sudo nginx -t && sudo nginx -s reload` ausführen.
3. **DNS-Einträge setzen** — A/CNAME für `survival.festas-builds.com` und
   `mining.festas-builds.com`.
4. **SSL-Zertifikat** — die Vhosts nutzen das gemeinsame `festas-builds.com`-Zertifikat
   (wie `mc.` und `mc-stats.`); es muss die Subdomains abdecken (Wildcard
   `*.festas-builds.com` bzw. passende SANs). Falls nicht, das Zertifikat erweitern, z. B.:
   `sudo certbot --nginx -d survival.festas-builds.com -d mining.festas-builds.com`
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
6. BlueMap im Browser öffnen und prüfen, dass jeweils die erwartete Welt geladen wird.

---

## Troubleshooting

| Problem | Ursache | Lösung |
|---------|---------|--------|
| BlueMap startet nicht / keine Texturen | `accept-download: false` | In `core.conf` auf `true` setzen ✅ (bereits gefixt) |
| Einer der Server zeigt keine Karte | Port-Konflikt / falsches Upstream-Routing | Survival muss auf `8102`, Mining auf `8103` zeigen |
| Live-Player verschwinden sofort | SSE durch Proxy unterbrochen | `proxy_buffering off` + `proxy_read_timeout 3600s` setzen |
| Karte lädt, aber keine Tiles | BlueMap hat noch nicht gerendert | `/bluemap render` im Server ausführen |
