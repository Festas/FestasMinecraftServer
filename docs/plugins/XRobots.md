# XRobots

**Robots/Drohnen (rpg/mining) · nur rpg · lokal (H2/Datei)**

## Zweck
XRobots stellt kaufbare Robots/Drohnen bereit, die für den Spieler Aufgaben übernehmen (z. B. Abbau/
Sammeln) – Passiv-Progression im Prison-/Mining-Server.

## Wo (Server & Config-Pfade)
Nur **rpg**: `rpg/plugins/XRobots/`
- `config.yml` – globale Optionen/Storage
- `robots.yml` – **Robot-Typen** (Fähigkeiten, Upgrades, Kosten)
- `guis.yml`, `messages.yml`

## Storage & Secrets
Lokal (H2/Datei). Keine geteilte DB, keine Deploy-Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Robot-Typen/Upgrades/Kosten** → `robots.yml`.
- **GUIs** → `guis.yml`.
- Erträge mit X-Prison-Currencies und AutoMiner/AutoSell abstimmen (Passiv-Einkommen nicht überdrehen).

## Cross-Server / Gotchas
- **rpg-only** (= „mining"). Teil der X-Prison-Suite → gemeinsamer Custom Agent „prison".

## Custom Agent
[`.github/agents/prison.agent.md`](../../.github/agents/prison.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [X-Prison.md](X-Prison.md) · [XPrivateMines.md](XPrivateMines.md)
