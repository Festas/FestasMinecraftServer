# Plan Player Analytics – Einrichtung & Nginx-Konfiguration

Dieses Dokument beschreibt, wie der **Plan**-Webserver für das Netzwerk konfiguriert ist
und wie der Nginx-Reverse-Proxy auf dem Host eingerichtet werden muss, damit das Dashboard
sicher über HTTPS erreichbar ist:

- `https://mc-stats.festas-builds.com` → Plan-Webserver (intern Port 8804)

---

## Warum Reverse Proxy?

Plan kann zwar selbst HTTPS (via JKS-Zertifikat) betreiben, der einfachere Weg ist
**Nginx als SSL-Terminierer** vorschalten:

- Nginx terminiert HTTPS (Let's Encrypt) und leitet intern per `http://` an Plan weiter
- Plan lauscht im Container auf `0.0.0.0:8804`; der Port wird nur an den Host-Loopback
  (`127.0.0.1:8804`) veröffentlicht und ist dadurch nicht direkt von außen erreichbar
- **`SSL_certificate.KeyStore_path: proxy`** versetzt Plan in den Reverse-Proxy-HTTPS-Modus:
  Plan bleibt intern HTTP, baut aber alle Links und API-Requests als `https://`. Ohne diese
  Einstellung wirbt Plan mit `http://` und der Browser blockiert alle `/v1/`-Aufrufe auf der
  `https://`-Seite als **Mixed Content** → nur die (grüne) Sidebar lädt, der Inhalt bleibt leer
- `Webserver.Alternative_IP.Address: mc-stats.festas-builds.com` gibt Plan die öffentliche Adresse
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

## Kritische Einstellung – `KeyStore_path: proxy` (behebt die leere/weiße Seite)

```yaml
Webserver:
    Security:
        SSL_certificate:
            KeyStore_path: proxy   # Reverse-Proxy-HTTPS-Modus
```

**Warum?** Plan baut die Basis-Adresse für sein React-Dashboard aus `Protokoll + Alternative_IP`.
Ohne internes Zertifikat ist das Protokoll `http`, also wirbt Plan mit
`http://mc-stats.festas-builds.com` – das steht dann auch im JS-Bundle (`PLAN_BASE_ADDRESS`)
und in der Datenbank (`plan_servers.web_address`; die Sub-Server loggen genau diese Adresse).
Die Seite selbst wird aber über nginx als **HTTPS** ausgeliefert. Der Browser blockiert
deshalb jeden `/v1/`-API-Aufruf nach `http://…` als **Mixed Content**:

```
Mixed Content: The page at 'https://mc-stats.festas-builds.com/…' was loaded over HTTPS,
but requested an insecure XMLHttpRequest endpoint 'http://mc-stats.festas-builds.com/v1/locale'.
This request has been blocked …
```

Ergebnis: Das Layout (grüne Sidebar) lädt aus dem HTTPS-Bundle, aber **kein Inhalt** erscheint,
weil alle Datenabfragen scheitern.

Mit `KeyStore_path: proxy` läuft Plan **weiterhin intern über HTTP** (nginx bleibt
`proxy_pass http://127.0.0.1:8804`), baut aber alle Links und Requests als `https://`. Damit
laufen die `/v1/`-Aufrufe über HTTPS und das Dashboard lädt vollständig.

> **Login/Authentifizierung:** Im Proxy-Modus gilt die Verbindung als HTTPS, dadurch wird die
> Anmeldung **aktiv** (`Disable_authentication: false`). Registriere einmalig einen Web-User
> (Velocity: `/planproxy register`, danach im Spiel/Konsole verknüpfen). Wer das Dashboard
> **ohne Login** öffentlich betreiben will, setzt `Disable_authentication: true` – auch dann
> verhindert der Proxy-Modus das Mixed-Content-Problem.

Quelle: [Plan-Wiki „If behind a Proxy"](https://github.com/plan-player-analytics/Plan/wiki/SSL-Certificate-%28HTTPS%29-Set-Up#if-behind-a-proxy).

---

## Relevante Dateien in diesem Repo

| Bereich | Datei |
|--------|-------|
| Plan Proxy-Config | `proxy/plugins/plan/config.yml` |
| Plan Infra-Doku | `docs/infrastructure/PLAN.md` |
| Plugin-Übersicht | `docs/PLUGINS.md` |
| Plan-DB → `players.json` Exporter | `tools/plan-players-export/` |
| DB → `leaderboard.json` Exporter | `tools/leaderboard-export/` |

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
        # X-Forwarded-Proto liest Plan NICHT aus – die HTTPS-Erkennung steuert allein
        # KeyStore_path: proxy in der Plan-Config. Der Header ist harmlos und kann bleiben.
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
   Velocity (Proxy) neu starten, damit `KeyStore_path: proxy`, `Internal_IP: 0.0.0.0` und
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
7. **Web-User registrieren** — Da der Proxy-Modus die Anmeldung aktiviert:
   `/planproxy register` ausführen und den User verknüpfen (oder für ein offenes Dashboard
   `Disable_authentication: true` setzen).

---

## Verifikation

1. `proxy/plugins/plan/config.yml` enthält `KeyStore_path: proxy`, `Internal_IP: 0.0.0.0` und
   `Use_X-Forwarded-For_Header: true`.
2. Der Proxy-Log zeigt nach dem Neustart `Webserver running on PORT 8804 (https://mc-stats.festas-builds.com)`
   (also `https://`) und **nicht** mehr `User Authorization Disabled! (Not secure over HTTP)`.
3. Die Sub-Server (Lobby/Survival/RPG) loggen `Proxy Webserver address is 'https://mc-stats.festas-builds.com'`.
4. `curl -I https://mc-stats.festas-builds.com` liefert HTTP 200 (oder Plan-Login-Redirect).
5. `curl -I http://<SERVER-IP>:8804` von außen schlägt fehl (Verbindung abgelehnt).
6. Plan-Dashboard öffnet sich im Browser vollständig – Browser-Konsole (F12) zeigt **keine**
   `Mixed Content`-Fehler mehr.

---

## Troubleshooting

| Problem | Ursache | Lösung |
|---------|---------|--------|
| Leere/weiße Seite, nur (grüne) Sidebar sichtbar | Plan wirbt mit `http://`, Browser blockiert `/v1/`-API-Aufrufe auf der `https://`-Seite als **Mixed Content** | `KeyStore_path: proxy` in der Plan-Config setzen, Velocity neu starten (Reverse-Proxy-HTTPS-Modus) |
| Weiße Seite, Plan gar nicht über HTTPS erreichbar | Kein Reverse-Proxy / kein Zertifikat | Nginx-Reverse-Proxy + Certbot einrichten (diese Doku) |
| `502 Bad Gateway` von Nginx | Plan bindet im Container auf `127.0.0.1` statt `0.0.0.0`, oder Plan läuft nicht / falscher Port | `Internal_IP: 0.0.0.0` in der Plan-Config setzen, Velocity neu starten, Port 8804 prüfen |
| Login schlägt fehl / kein Cookie | Plan sieht sich nicht als HTTPS | `KeyStore_path: proxy` setzen (aktiviert HTTPS-Modus ohne internes Zertifikat) |
| Falsche IPs im Plan-Dashboard | `Use_X-Forwarded-For_Header: false` | Auf `true` setzen ✅ (bereits in dieser Config) |

---

## Plan-DB als `players.json`-Quelle der Website (nur Counts)

Die Website (`https://mc.festas-builds.com`, Abschnitt „Wer ist online?") lädt
`/api/players.json` und rendert je Server eine Karte. Diese Datei wird vom Exporter
[`tools/plan-players-export/`](../../tools/plan-players-export/README.md) aus der
**Plan-Datenbank** befüllt – als aufwandsarme Variante **ohne** zusätzliches Plugin.

**Kernpunkte:**

- **Quelle:** neueste `plan_tps`-Zeile je Game-Server (`players_online`), Proxy via
  `is_proxy = 0` ausgeschlossen. Live-Flag = Zeile jünger als 2 Minuten – identisch
  zu Plans eigener „aktuell online"-Logik.
- **Warum nicht Sessions?** Plan hält aktive Sessions nur im RAM
  (`SessionCache.ACTIVE_SESSIONS`); in `plan_sessions` landen nur **beendete**
  Sessions. Aus der DB gibt es daher **keine Live-Namen und keine Live-Welten**.
- **Mapping:** DB → Website-Key über die stabile Server-**UUID** aus
  `*/plugins/Plan/ServerInfoFile.yml` (Lobby `82755ba5-…`, Survival `679bd851-…`,
  Mining `851efb50-…` aus dem `rpg/`-Ordner; Skyblock hat noch keinen Plan-Ordner
  → `online:false, count:0`).
- **Sicherheit:** dedizierter **Read-only-User** (`SELECT` auf `s4_plan`), Secret
  `PLAN_RO_DB_ENV` (siehe `SECRETS.md`), `useSSL: true`. Nicht Plans RW-User
  wiederverwenden (Least Privilege, vgl. `DATENBANKEN.md`).
- **Betrieb:** systemd-Timer (45 s) schreibt atomar nach
  `/home/deploy/minecraft-website/data/players.json`; Rollout über
  `.github/workflows/deploy-plan-players-export.yml`.

**Grenzen:** `showNames:false`, keine Live-Welten, keine echte Uptime, Latenz
≤ ~2 min („Maximum je Minute"). Für Namen/Welten/Echtzeit ist ein
**Velocity-Proxy-Writer** die überlegene Quelle – dank identischem JSON-Vertrag
ohne Frontend-Änderung nachrüst- oder kombinierbar.

---

## Plan- + LuckPerms-DB als `leaderboard.json`-Quelle (Bestenliste)

Die Website (Abschnitt „Bestenliste") lädt `/api/leaderboard.json` und zeigt die
**Top-N Spieler nach Spielzeit** mit ihrem **höchsten LuckPerms-Rang**. Die Datei
wird vom Exporter
[`tools/leaderboard-export/`](../../tools/leaderboard-export/README.md) aus **zwei**
Datenbanken befüllt – wieder **ohne** zusätzliches Plugin, nach demselben Muster wie
`plan-players-export`.

**Kernpunkte:**

- **Spielzeit:** Summe der **beendeten** Sessions je Spieler aus `s4_plan`
  (`SUM(plan_sessions.session_end − session_start)`, gejoint auf `plan_users` für den
  Namen). Netzwerkweit über alle Server.
- **Höchster Rang:** aus `s4_perms` – aus den Gruppen des Spielers
  (`luckperms_user_permissions`, `permission = 'group.<name>'`) die Gruppe mit dem
  höchsten **`weight`** (Gewichte/Anzeigenamen aus `luckperms_group_permissions`);
  `luckperms_players.primary_group` als Fallback.
- **Join-Schlüssel:** die Spieler-**UUID** (in beiden DBs vorhanden), robust gegen
  Bindestrich-/Groß-/Kleinschreibung normalisiert.
- **Sicherheit:** derselbe Read-only-User wie oben (`PLAN_RO_DB_ENV`, braucht
  **zusätzlich** `SELECT` auf `plan_sessions` + `plan_users`) **plus** ein
  **eigener** Read-only-User für `s4_perms` (`SELECT` auf `luckperms_*`), Secret
  `LUCKPERMS_RO_DB_ENV` (siehe `SECRETS.md`). Beide fail-closed: DB-Fehler ⇒ **kein**
  Schreiben.
- **Betrieb:** systemd-Timer (alle 5 min) schreibt atomar nach
  `/home/deploy/minecraft-website/data/leaderboard.json`; Rollout über
  `.github/workflows/deploy-leaderboard-export.yml`. Nginx liefert die Datei
  same-origin unter `/api/leaderboard.json` (Fallback: leeres `{"players":[]}`).

**Grenzen:** Wie beim Player-Counter zählt die **laufende** Session erst nach ihrem
Ende (Plan hält aktive Sessions nur im RAM). Namen werden hier **bewusst** gezeigt
(Kern des Features); optionale Ausschlussliste + `min_weight`-Filter in
`config.json`. Skyblock nutzt lokales SQLite und ist in `s4_plan` **nicht** enthalten.
