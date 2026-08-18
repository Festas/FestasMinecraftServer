# Guide: Serverstatus & Online-Spieler implementieren

Dieser Guide beschreibt die komplette Umsetzung für:

- **Serverstatus** (online/offline + Spielerzahl gesamt) über `mcsrvstat.us`
- **Live-Online-Spieler** je Unterserver über `/api/players.json`

## 1) Frontend-Konfiguration

Datei: `website/js/config.template.js`

Wichtige Felder:

- `serverAddress` (z. B. `mc.festas-builds.com`)
- `statusAPI` (Standard: `https://api.mcsrvstat.us/3/`)
- `playersAPI` (Standard: `/api/players.json`)

Das Frontend baut daraus:

- Status-Endpunkt: `${statusAPI}${encodeURIComponent(serverAddress)}`
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
      "online": true,
      "count": 0,
      "max": 40,
      "uptimeSeconds": 0,
      "updated": 0,
      "players": []
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
- `showNames: false` blendet Spielernamen aus und zeigt nur Zahlen
- `online` kann gesetzt werden; wenn nicht vorhanden, summiert das Frontend die Server-Counts

## 3) Nginx-Fallback für Ausfälle

Datei: `website/nginx.conf`

`/api/players.json` ist auf `no-store` gesetzt. Wenn die Datei fehlt, liefert nginx ein gültiges Fallback-JSON (HTTP 200), damit das Frontend nicht an einer HTML-404 Seite scheitert.

## 4) Frontend-Verhalten

Datei: `website/js/main.js`

- `initServerStatus()`:
  - nutzt `statusAPI` + `serverAddress`
  - zeigt `Online`, `Offline` oder `Status unbekannt`
  - nutzt bei Ausfall der Status-API einen Fallback über `players.json` (`Eingeschränkt`)
  - zeigt `x/y Spieler online` (bei bekanntem Max) oder `x Spieler online`
- `initPlayerList()`:
  - lädt `playersAPI` alle 30s
  - rendert je Unterserver Karten inkl. Onlinebadge, `count` (optional `/max`), Uptime und optionaler Spielernamenliste
  - zeigt bei Ausfall eine klare Nichtverfügbarkeits-Meldung
  - markiert Daten als potenziell veraltet, wenn `updated`/`servers[].updated` zu alt ist

## 5) Backend/Proxy-Writer (Empfehlung)

Der Proxy-Prozess sollte regelmäßig `players.json` atomar schreiben:

1. Daten sammeln (online Spieler je Backend)
2. JSON in temporäre Datei schreiben
3. Temp-Datei per `rename` auf `players.json` ersetzen

Dadurch liest nginx immer konsistente Dateien.

## 6) Deployment

1. Änderungen nach `main` pushen
2. Workflow `.github/workflows/deploy-website.yml` baut/pusht Image
3. Deployment-Job startet Container neu
4. Auf dem Host sicherstellen, dass `data/players.json` vom Proxy aktualisiert wird

## 7) Verifikation

- `curl -s https://mc.festas-builds.com/api/players.json | jq .`
- Website öffnen:
  - Hero: Status sichtbar
  - Abschnitt „Wer ist online?“ aktualisiert sich
- Bei absichtlich fehlender Datei:
  - `/api/players.json` liefert Fallback-JSON
  - Frontend bleibt stabil
