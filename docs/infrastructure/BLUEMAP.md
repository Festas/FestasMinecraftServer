# BlueMap – Einrichtung & Nginx-Konfiguration

Dieses Dokument beschreibt, wie BlueMap für den **Survival**- und **Mining (RPG-Slot)**-Server
konfiguriert ist und wie der Nginx-Reverse-Proxy auf dem Host eingerichtet werden muss,
damit `https://mc-maps.festas-builds.com` funktioniert.

---

## Ports

| Server   | BlueMap-Port |
|----------|-------------|
| Survival | 8100        |
| Mining   | 8101        |

Beide Server laufen mit dem integrierten BlueMap-Webserver. Die Karten sind unter diesen
Ports lokal auf dem Host erreichbar, dürfen aber **nicht direkt nach außen freigegeben werden** —
der Zugriff läuft ausschließlich über den Nginx-Reverse-Proxy.

---

## Nginx-Konfiguration (Host, Festas/Link-in-Bio)

Die Nginx-Konfiguration gehört in das `Festas/Link-in-Bio`-Repo unter
`nginx/sites-available/mc-maps.festas-builds.com.conf`.

Beide BlueMap-Instanzen werden unter **einer URL** zusammengeführt. Die Mining-Karte
wird unter dem Pfad `/mining/` und die Survival-Karte unter `/survival/` (oder direkt `/`)
bereitgestellt. Eine einfachere Variante ist, beide auf separaten Subdomains zu hosten.

### Variante A: Einzelne Domain mit Pfad-Prefix

```nginx
server {
    listen 80;
    server_name mc-maps.festas-builds.com;
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl;
    server_name mc-maps.festas-builds.com;

    # SSL – von Certbot/Let's Encrypt verwaltet
    ssl_certificate     /etc/letsencrypt/live/mc-maps.festas-builds.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/mc-maps.festas-builds.com/privkey.pem;
    include             /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam         /etc/letsencrypt/ssl-dhparams.pem;

    # Survival-Karte (Standard, Port 8100)
    location / {
        proxy_pass         http://127.0.0.1:8100/;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
        # SSE (Live-Updates) – lange Verbindungen erlauben
        proxy_read_timeout 3600s;
        proxy_buffering    off;
    }

    # Mining-Karte (Port 8101) unter /mining/
    location /mining/ {
        proxy_pass         http://127.0.0.1:8101/;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
        proxy_read_timeout 3600s;
        proxy_buffering    off;
    }
}
```

### Variante B: Separate Subdomains (empfohlen, einfacher)

```nginx
# Survival: https://mc-maps.festas-builds.com
server {
    listen 443 ssl;
    server_name mc-maps.festas-builds.com;
    # SSL …
    location / {
        proxy_pass         http://127.0.0.1:8100/;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
        proxy_read_timeout 3600s;
        proxy_buffering    off;
    }
}

# Mining: https://mc-maps-mining.festas-builds.com  (oder mining.mc-maps.…)
server {
    listen 443 ssl;
    server_name mc-maps-mining.festas-builds.com;
    # SSL …
    location / {
        proxy_pass         http://127.0.0.1:8101/;
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
2. **Nginx-Konfiguration einrichten** — eine der obigen Varianten in
   `Festas/Link-in-Bio` anlegen und `sudo nginx -t && sudo nginx -s reload` ausführen.
3. **DNS-Eintrag** — falls du Variante B oder eine neue Subdomain nimmst,
   einen A/CNAME-Eintrag für die neue Domain setzen.
4. **SSL-Zertifikat** — `sudo certbot --nginx -d mc-maps.festas-builds.com`
   (und ggf. für die zweite Domain).
5. **Firewall** — Ports 8100 und 8101 dürfen **nicht** direkt von außen erreichbar sein
   (`ufw deny 8100` / `ufw deny 8101` oder einfach nicht öffnen).
6. **Website-Config aktualisieren** — in `website/js/config.template.js` kann bei Bedarf
   die `bluemapURL` angepasst werden, falls du eine andere Subdomain wählst.

---

## Troubleshooting

| Problem | Ursache | Lösung |
|---------|---------|--------|
| BlueMap startet nicht / keine Texturen | `accept-download: false` | In `core.conf` auf `true` setzen ✅ (bereits gefixt) |
| Einer der Server zeigt keine Karte | Port-Konflikt (beide auf 8100) | RPG jetzt auf 8101 ✅ (bereits gefixt) |
| Live-Player verschwinden sofort | SSE durch Proxy unterbrochen | `proxy_buffering off` + `proxy_read_timeout 3600s` setzen |
| Karte lädt, aber keine Tiles | BlueMap hat noch nicht gerendert | `/bluemap render` im Server ausführen |
