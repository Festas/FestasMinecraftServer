# Skript

**Custom-Gameplay-Logik · lobby / survival / skyblock / rpg(mining) · lokale Variablen (`variables.csv`)**

## Zweck
Skript liefert die maßgeschneiderte Gameplay-Logik des Netzwerks: Navigator/Kompass, tägliche Belohnungen,
Broadcasts/Hilfe-Rotation, Prestige, Rankup-Trigger, Gamble, dynamischer Markt, Boss-/Weekly-Events, Tycoon
u. v. m. Die meisten server-spezifischen Features leben hier.

## Wo (Server & Config-Pfade)
Jedes Backend: `<server>/plugins/Skript/`
- `scripts/` – die aktiven Skripte (`.sk`)
- `config.sk`, `features.sk` – Skript-Grundeinstellungen
- `variables.csv` – **persistente Variablen (Laufzeitdaten)**, `backups/` – Auto-Backups

**Konvention:** Dateien/Ordner mit führendem `-` sind **deaktiviert** (z. B. `-examples`,
`-tycoon_logic.sk`). Zum Aktivieren Präfix entfernen (Deploy überschreibt additiv, löscht nicht).

Wichtige Skripte (Auswahl):
- Überall: `help.sk` (Broadcast-Rotation), `navigator.sk` (Kompass/Menü), `daily_rewards.sk`
- Survival zusätzlich: `rankup.sk`, `prestige.sk`, `ranks_setup.sk`, `dynamic_market.sk`, `gamble.sk`,
  `boss_events.sk`, `weekly_events.sk`, `achievements.sk`, `shopguiplus.sk`, `tycoon_*.sk`
- Lobby: `lobby_features.sk`

## Storage & Secrets
Lokal in `variables.csv`; keine DB, keine Secrets. Economy-Aktionen laufen über CMI-Konsolenbefehle.

## Wichtige Einstellungen / typische Aufgaben
- **Broadcasts** anpassen → `help.sk` (`every N minutes: broadcast`, rotierender Index
  `{<server>_bc::N}`). Es gibt **keinen** CMI-Announcer.
- **Economy geben:** `execute console command "cmi money give %player% %amount%"`.
- **Navigation:** auf Gameplay-Servern **nicht** Inventar leeren / Gamemode erzwingen – nur leeren Slot
  füllen bzw. an freien Slot geben. **Nur die Lobby** leert Inventar/setzt Gamemode (Hub-Verhalten).
- Reload eines Skripts: `/sk reload <name>`.

## Cross-Server / Gotchas
- `help.sk`/`daily_rewards.sk`/`navigator.sk` sind pro Server gespiegelt, aber inhaltlich angepasst –
  Änderungen ggf. auf allen Servern konsistent nachziehen.
- `variables.csv` und `backups/` sind Serverdaten und werden **nicht** aus dem Repo deployt.

## Custom Agent
[`.github/agents/skript.agent.md`](../../.github/agents/skript.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [CMI.md](CMI.md) · [Rankup.md](Rankup.md) · [PlaceholderAPI.md](PlaceholderAPI.md)
