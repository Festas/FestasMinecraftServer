# BlueMap

**Web-Live-Karte · survival / rpg(mining) · lokal (Filestorage)**

## Zweck
BlueMap rendert eine 3D-Web-Live-Karte der Welten und stellt sie über einen Webserver bereit
(hinter nginx). Genutzt für survival und rpg(mining).

## Wo (Server & Config-Pfade)
Auf **survival** und **rpg(mining)**: `<server>/plugins/BlueMap/`
- `core.conf` – Kern-Optionen · `plugin.conf` – Plugin-Verhalten
- `webserver.conf` – **Webserver/Port** · `webapp.conf` – Web-App
- `maps/` – **pro Welt eine Map-Config** · `storages/` – Speicher-Backends

## Storage & Secrets
Lokal (Filestorage in `storages/`). Keine Deploy-Secrets. Auslieferung im Web über nginx (siehe
`docs/infrastructure/BLUEMAP.md`).

## Wichtige Einstellungen / typische Aufgaben
- **Welt zur Karte hinzufügen/ausschließen** → `maps/<welt>.conf`.
- **Port/Bind** → `webserver.conf` (muss zum nginx-Upstream passen).
- Render-Qualität/Sichtbarkeit → `maps/` + `core.conf`.

## Cross-Server / Gotchas
- Vorhanden auf **survival + rpg**; skyblock/lobby ohne BlueMap.
- Webserver-Port mit nginx-Konfiguration abgleichen; große Render-Daten in `web/` sind Serverstand
  (nicht im Repo).

## Custom Agent
[`.github/agents/bluemap.agent.md`](../../.github/agents/bluemap.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [`docs/infrastructure/BLUEMAP.md`](../infrastructure/BLUEMAP.md)
