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
- Plan lauscht nur auf `127.0.0.1:8804` (nicht direkt von außen erreichbar)
- Plan kennt die öffentliche HTTPS-Adresse über `External_Webserver_address`
- `Use_X-Forwarded-For_Header: true` gibt die echte Client-IP an Plan weiter

---

## Ports

| Dienst | Port  | Erreichbarkeit |
|--------|-------|----------------|
| Plan   | 8804  | Nur lokal (127.0.0.1) |

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
        proxy_pass         http://127.0.0.1:8804/;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
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
   Velocity (Proxy) neu starten, damit `Internal_IP: 127.0.0.1` und
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
6. **Plan neu starten** — Falls der Proxy noch mit `0.0.0.0` gebunden war, Velocity
   neu starten damit Plan nur noch auf `127.0.0.1:8804` lauscht.

---

## Verifikation

1. `proxy/plugins/plan/config.yml` enthält `Internal_IP: 127.0.0.1` und
   `Use_X-Forwarded-For_Header: true`.
2. `curl -I https://mc-stats.festas-builds.com` liefert HTTP 200 (oder Plan-Login-Redirect).
3. `curl -I http://<SERVER-IP>:8804` von außen schlägt fehl (Verbindung abgelehnt).
4. Plan-Dashboard öffnet sich im Browser ohne weiße Seite.

---

## Troubleshooting

| Problem | Ursache | Lösung |
|---------|---------|--------|
| Weiße Seite im Browser | Plan läuft nur HTTP, Browser erwartet HTTPS | Nginx-Reverse-Proxy + Certbot einrichten (diese Doku) |
| `502 Bad Gateway` von Nginx | Plan läuft nicht oder falscher Port | Velocity neu starten, Port 8804 prüfen |
| Login schlägt fehl / kein Cookie | HTTPS fehlt (Plan benötigt HTTPS für Auth) | Certbot-Zertifikat ausstellen |
| Falsche IPs im Plan-Dashboard | `Use_X-Forwarded-For_Header: false` | Auf `true` setzen ✅ (bereits in dieser Config) |
