# Guide: Serverstatus & Online-Spieler implementieren

Dieser Guide beschreibt die komplette Umsetzung für:

- **Serverstatus** (online/offline + Spielerzahl gesamt) über mehrere öffentliche Quellen
- **Live-Online-Spieler** je Unterserver / Welt über `/api/players.json`

## 1) Frontend-Konfiguration

Datei: `website/js/config.template.js`

Wichtige Felder:

- `serverAddress` (z. B. `mc.festas-builds.com`)
- `publicStatusSources` (Standard: `mcsrvstat.us` + `mcstatus.io`)
- `statusAPI` bleibt als Legacy-Fallback unterstützt
- `playersAPI` (Standard: `/api/players.json`)

Das Frontend baut daraus:

- Status-Endpunkte: aus `publicStatusSources[]`
- Spielerliste-Endpunkt: `${playersAPI}`

## 2) Live-Spielerliste bereitstellen

Datei: `website/api/players.json` (lokales Beispiel)

Produktiv wird diese Datei per Bind-Mount geliefert:

- Host: `./data/players.json`
- Container: `/usr/share/nginx/html/api/players.json`
- Konfiguriert in `docker-compose.web.yml`

Minimales JSON-Format:

```json
{
  "online": 0,
  "max": 0,
  "updated": 0,
  "showNames": true,
  "servers": [
    {
      "name": "lobby",
      "online": false,
      "count": 0,
      "max": 40,
      "uptimeSeconds": 0,
      "updated": 0,
      "players": [],
      "worlds": [
        {
          "name": "spawn",
          "online": false,
          "count": 0,
          "max": 40,
          "updated": 0,
          "players": []
        }
      ]
    }
  ]
}
```

Feldregeln:

- `updated`: Unix-Zeitstempel (Sekunden)
- `servers[].online`: bevorzugt explizit setzen; fehlt der Wert, nutzt das Frontend eine defensive Heuristik (`count > 0`, Spielernamen oder `uptimeSeconds`)
- `servers[].max`: optional, für Anzeige `count/max`
- `servers[].uptimeSeconds`: optional, wird als lesbare Uptime (`1d 2h 3m`) angezeigt
- `servers[].updated`: optionaler Zeitstempel pro Unterserver
- `servers[].worlds`: optionale Welt-Aufschlüsselung innerhalb eines Servers
- `servers[].worlds[].count|max|online|players|updated`: gleiches Schema auf Welt-Ebene
- `showNames: false` blendet Spielernamen aus und zeigt nur Zahlen
- `online` kann gesetzt werden; wenn nicht vorhanden, summiert das Frontend die Server-Counts

## 3) Nginx-Fallback für Ausfälle

Datei: `website/nginx.conf`

`/api/players.json` ist auf `no-store` gesetzt. Wenn die Datei fehlt, liefert nginx ein gültiges Fallback-JSON (HTTP 200), damit das Frontend nicht an einer HTML-404 Seite scheitert.

## 4) Frontend-Verhalten

Datei: `website/js/main.js`

- `initServerStatus()`:
  - nutzt mehrere öffentliche Statusquellen (`mcsrvstat.us`, `mcstatus.io`)
  - zeigt `Online`, `Offline` oder `Status unbekannt`
  - nutzt bei Ausfall der Status-API einen Fallback über `players.json` (`Eingeschränkt`)
  - zeigt `x/y Spieler online` (bei bekanntem Max) oder `x Spieler online`
- `initPlayerList()`:
  - lädt `playersAPI` alle 30s
  - rendert je Unterserver Karten inkl. Onlinebadge, `count` (optional `/max`), Uptime und optionaler Spielernamenliste
  - rendert vorhandene `worlds[]` unterhalb des Servers als Server-/Welt-Ansicht
  - zeigt bei Ausfall eine klare Nichtverfügbarkeits-Meldung
  - markiert Daten als potenziell veraltet, wenn `updated`/`servers[].updated` zu alt ist

## 5) Backend/Proxy-Writer (Empfehlung)

Damit alles live funktioniert, werden zwei Datenquellen benötigt:

1. **Öffentliche Gesamtquelle** für das Netzwerk oben
   - Standard im Frontend: `mcsrvstat.us`
   - zusätzlicher Fallback: `mcstatus.io`
   - beide liefern nur den Gesamtstatus des Netzwerks
2. **Interne Snapshot-Datei** für Server/Welt unten
   - muss vom Proxy oder einem eigenen Exporter geschrieben werden
   - public APIs liefern diese Granularität in der Regel nicht

Der Proxy-/Exporter-Prozess sollte regelmäßig `players.json` atomar schreiben:

1. Daten sammeln (online Spieler je Backend)
2. JSON in temporäre Datei schreiben
3. Temp-Datei per `rename` auf `players.json` ersetzen

Dadurch liest nginx immer konsistente Dateien.

### 5a) Konkrete Quelle A: Plan-DB-Exporter (nur Zahlen, geringster Aufwand)

Im Repo liegt ein fertiger Exporter unter [`tools/plan-players-export/`](../tools/plan-players-export/README.md),
der `players.json` aus der **Plan-Datenbank** (`s4_plan`) füllt – **rein lesend**,
ohne neues Plugin.

- **Datenquelle:** neueste `plan_tps`-Zeile je Game-Server (Spalte `players_online`,
  Proxy via `is_proxy = 0` ausgeschlossen). Genau die Logik, die Plans eigenes
  Dashboard für „aktuell online" nutzt (neueste Zeile, sofern < 2 Minuten alt).
- **Mapping:** DB → Website-Key (`lobby|survival|mining|skyblock`) über die **stabile
  Server-UUID** aus `*/plugins/Plan/ServerInfoFile.yml` (nicht über den Namen).
- **Betrieb:** systemd-Timer auf dem Host, alle **45 s** (< 90 s), schreibt atomar
  nach `data/players.json`. Ausrollen über `.github/workflows/deploy-plan-players-export.yml`.
- **Sicherheit:** dedizierter **Read-only-DB-User** (`SELECT` auf `s4_plan`),
  Zugangsdaten im Secret `PLAN_RO_DB_ENV` (nie committen), `useSSL: true`.

**Grenzen dieser Quelle (bewusst):**

- `showNames: false` – **keine** Live-Spielernamen (Plan hält aktive Sessions nur im RAM).
- **Keine** Live-Welten-Aufschlüsselung (`worlds[]` entfällt).
- **Keine** echte Server-Uptime aus der DB (Karten zeigen `Uptime: —`).
- Latenz **≤ ~2 min**, `players_online` ist das **Maximum je Minutenfenster**.
- Server ohne Plan (aktuell Mining/Skyblock) erscheinen als `online:false, count:0`.

### 5b) Konkrete Quelle B: Velocity-Proxy-Writer (Namen/Welten, Echtzeit)

Wenn Namen, Welten oder echte Echtzeit gewünscht sind, ist ein Proxy-Writer die
überlegene Quelle. Da der JSON-Vertrag identisch bleibt, lässt er sich **ohne
Frontend-Änderung** nachrüsten oder mit Quelle A kombinieren. Plan bleibt dann für
Langzeit-Analytics.

## 6) Deployment

1. Änderungen nach `main` pushen
2. Workflow `.github/workflows/deploy-website.yml` baut/pusht Image
3. Deployment-Job startet Container neu
4. Auf dem Host sicherstellen, dass `data/players.json` aktualisiert wird – entweder
   durch den **Plan-DB-Exporter** (`deploy-plan-players-export.yml`, siehe 5a) oder
   durch einen Proxy/Exporter (siehe 5b)


## 7) Verifikation

- `curl -s https://mc.festas-builds.com/api/players.json | jq .`
- Website öffnen:
  - Hero: Status sichtbar
  - Abschnitt „Wer ist online?“ aktualisiert sich
- Bei absichtlich fehlender Datei:
  - `/api/players.json` liefert Fallback-JSON
  - Frontend bleibt stabil
