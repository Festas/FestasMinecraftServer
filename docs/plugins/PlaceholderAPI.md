# PlaceholderAPI (PAPI)

**Platzhalter-Brücke · lobby / survival / skyblock / rpg(mining) · lokal (keine DB)**

## Zweck
PlaceholderAPI stellt Platzhalter (`%plugin_wert%`) bereit, die andere Plugins (TAB, DeluxeMenus,
Skript, CMI-Chat, Scoreboards) konsumieren. Es ist die **Daten-Brücke** zwischen Plugins.

## Wo (Server & Config-Pfade)
Jedes Backend: `<server>/plugins/PlaceholderAPI/config.yml` (+ heruntergeladene Expansions unter
`expansions/`, nicht im Repo verwaltet).

## Storage & Secrets
Lokal, keine Datenbank, keine Secrets. Expansions werden zur Laufzeit via `/papi ecloud` bezogen.

## Wichtige Einstellungen / typische Aufgaben
- **Benötigte Expansions** sicherstellen (z. B. `vault`, `player`, `cmi`, `luckperms`, `superior`,
  `rankup`, server-spezifische). Der **Proxy-TAB** konsumiert Backend-Platzhalter über eine Bridge –
  fehlt eine Expansion, bleibt der Platzhalter im TAB leer.
- Platzhalter testen: `/papi parse me %platzhalter%`.
- Nach Expansion-Update: `/papi reload`.

## Cross-Server / Gotchas
- TAB/Scoreboard laufen **nur am Proxy** (`proxy/plugins/tab/`), ziehen aber Werte über die
  PlaceholderAPI-Bridge aus den Backends – Platzhalter müssen **auf dem jeweiligen Backend** existieren.
- Typische im Netzwerk genutzte Platzhalter: `%vault_eco_balance_formatted%`, `%rankup_next_rank%`,
  `%superior_island_level%`, `%cmi_user_…%`.

## Custom Agent
[`.github/agents/placeholderapi.agent.md`](../../.github/agents/placeholderapi.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [TAB.md](TAB.md) · [DeluxeMenus.md](DeluxeMenus.md) · [Skript.md](Skript.md)
