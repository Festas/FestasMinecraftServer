# Plan Player Analytics – Einrichtung & Nginx-Konfiguration

Dieses Dokument beschreibt, wie der **Plan**-Webserver für das Netzwerk konfiguriert ist
und wie der Nginx-Reverse-Proxy auf dem Host eingerichtet werden muss, damit das Dashboard
sicher über HTTPS erreichbar ist:

- `https://mc-stats.festas-builds.com` → Plan-Webserver (intern Port 8804)

---

## Warum Reverse Proxy?

Plan kann zwar selbst HTTPS (via JKS-Zertifikat) betreiben, der einfachere Weg ist
**Nginx als SSL-Terminierer** vorschalten:

- Nginx terminiert HTTPS (Let's Encrypt)
- Plan lauscht im Container auf `0.0.0.0:8804`; der Port wird nur an den Host-Loopback
  (`127.0.0.1:8804`) veröffentlicht und ist dadurch nicht direkt von außen erreichbar
- Plan kennt die öffentliche HTTPS-Adresse über `External_Webserver_address`
- `Use_X-Forwarded-For_Header: true` gibt die echte Client-IP an Plan weiter

---

## Ports

| Dienst | Port  | Erreichbarkeit |
|--------|-------|----------------|
| Plan   | 8804  | Bind `0.0.0.0` im Container, nur an Host-Loopback `127.0.0.1:8804` veröffentlicht |

> **Docker/Pterodactyl-Hinweis:** `Webserver.Internal_IP` in der Plan-Config ist die
> **Bind-Adresse innerhalb des Containers** und muss `0.0.0.0` sein, damit die
> Host-nginx den Dienst über das Port-Mapping erreicht. `127.0.0.1` bindet nur an das
> Container-Loopback – der veröffentlichte Port hat dann keinen Listener und nginx liefert
> **502 Bad Gateway**. Details: `docs/PLAN-reverse-proxy.md`.

---

## Relevante Dateien in diesem Repo

| Bereich | Datei |
|--------|-------|
| Plan Proxy-Config | `proxy/plugins/plan/config.yml` |
| Plan Infra-Doku | `docs/infrastructure/PLAN.md` |
| Plugin-Übersicht | `docs/PLUGINS.md` |

---

## Nginx-Konfiguration (Host, Festas/Link-in-Bio)

Die Nginx-Konfiguration gehört in das `Festas/Link-in-Bio`-Repo als eigene Site-Datei.

### Host-basiertes Routing

```nginx
# HTTP → HTTPS Redirect
server {
    listen 80;
    server_name mc-stats.festas-builds.com;
    return 301 https://$host$request_uri;
}

# Plan: https://mc-stats.festas-builds.com
server {
    listen 443 ssl;
    server_name mc-stats.festas-builds.com;

    # SSL (certbot trägt diese Zeilen automatisch ein)
    ssl_certificate     /etc/letsencrypt/live/mc-stats.festas-builds.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/mc-stats.festas-builds.com/privkey.pem;
    include             /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam         /etc/letsencrypt/ssl-dhparams.pem;

    location / {
        # 127.0.0.1:8804 ist der Host-Loopback, auf den der Container-Port 8804
        # veröffentlicht wird (Docker/Pterodactyl). Plan selbst bindet im Container an 0.0.0.0.
        proxy_pass         http://127.0.0.1:8804/;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        # Fest auf "https" setzen (nicht $scheme): die interne Upstream-Verbindung ist HTTP,
        # $scheme wäre daher "http" und Plan würde Auth/Cookies falsch behandeln (weiße Seite).
        proxy_set_header   X-Forwarded-Proto https;
        proxy_read_timeout 120s;
    }
}
```

> **Wichtig:** Erst nachdem Nginx vor Plan geschaltet ist, darf
> `Use_X-Forwarded-For_Header: true` in der Plan-Config aktiv sein – sonst ist
> IP-Spoofing möglich, weil Plan dem Header blind vertraut.
> Die Config in diesem Repo hat diesen Wert bereits auf `true` gesetzt.
> Stelle sicher, dass Port 8804 **nicht direkt** von außen erreichbar ist (siehe Firewall).

---

## Checkliste – Was du noch tun musst

Nach dem Pushen dieser Config-Änderungen:

1. **Plan-Config deployen** — `proxy/plugins/plan/config.yml` auf den Server kopieren und
   Velocity (Proxy) neu starten, damit `Internal_IP: 0.0.0.0` und
   `Use_X-Forwarded-For_Header: true` wirksam werden.
2. **Nginx-Konfiguration einrichten** — Nginx-Site-Datei wie oben in `Festas/Link-in-Bio`
   anlegen und `sudo nginx -t && sudo nginx -s reload` ausführen.
3. **SSL-Zertifikat ausstellen** — z. B.:
   ```bash
   sudo certbot --nginx -d mc-stats.festas-builds.com
   ```
4. **DNS-Eintrag prüfen** — A/CNAME für `mc-stats.festas-builds.com` muss auf die
   Server-IP zeigen.
5. **Firewall** — Port 8804 darf **nicht** direkt von außen erreichbar sein:
   ```bash
   sudo ufw deny 8804
   ```
6. **Plan neu starten** — Velocity neu starten, damit Plan im Container auf `0.0.0.0:8804`
   bindet und die Host-nginx den veröffentlichten Port erreichen kann.

---

## Verifikation

1. `proxy/plugins/plan/config.yml` enthält `Internal_IP: 0.0.0.0` und
   `Use_X-Forwarded-For_Header: true`.
2. `curl -I https://mc-stats.festas-builds.com` liefert HTTP 200 (oder Plan-Login-Redirect).
3. `curl -I http://<SERVER-IP>:8804` von außen schlägt fehl (Verbindung abgelehnt).
4. Plan-Dashboard öffnet sich im Browser ohne weiße Seite.

---

## Troubleshooting

| Problem | Ursache | Lösung |
|---------|---------|--------|
| Weiße Seite im Browser | Plan läuft nur HTTP, Browser erwartet HTTPS | Nginx-Reverse-Proxy + Certbot einrichten (diese Doku); `X-Forwarded-Proto https` setzen |
| `502 Bad Gateway` von Nginx | Plan bindet im Container auf `127.0.0.1` statt `0.0.0.0`, oder Plan läuft nicht / falscher Port | `Internal_IP: 0.0.0.0` in der Plan-Config setzen, Velocity neu starten, Port 8804 prüfen |
| Login schlägt fehl / kein Cookie | HTTPS fehlt (Plan benötigt HTTPS für Auth) | Certbot-Zertifikat ausstellen; `X-Forwarded-Proto https` im nginx setzen |
| Falsche IPs im Plan-Dashboard | `Use_X-Forwarded-For_Header: false` | Auf `true` setzen ✅ (bereits in dieser Config) |
