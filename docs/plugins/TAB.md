# TAB

**Tablist & Scoreboard · nur Proxy · netzwerkweit · lokal (DB dormant)**

## Zweck
TAB stellt die **netzwerkweite** Tabliste, das Scoreboard, Header/Footer, Namensschilder und
BossBars. Es läuft ausschließlich am Proxy und gilt für alle Backends – die Backends liefern nur
PlaceholderAPI-Werte zu.

## Wo (Server & Config-Pfade)
Nur am Proxy: `proxy/plugins/tab/`
- `config.yml` – zentrale Konfiguration (Tablist, Scoreboard, Header/Footer, Bridge)
- `groups.yml`, `users.yml` – Gruppen-/User-Overrides
- `animations.yml`, `messages.yml` (+ `playerdata.yml`, `skincache.yml` = Laufzeit)

Zusätzlich: `proxy/plugins/velocity-scoreboard-api/` (Scoreboard-API-Abhängigkeit).

## Storage & Secrets
Läuft lokal (Flatfile). `config.yml` enthält **dormante** Platzhalter `__TAB_DB_USER__` /
`__TAB_DB_PASSWORD__` für einen optionalen MySQL-Storage – aktuell **nicht** aktiv, aber beim
Bearbeiten als Template erhalten.

## Wichtige Einstellungen / typische Aufgaben
- **Tablist/Scoreboard-Inhalt** → `config.yml` (nutzt Backend-Platzhalter wie
  `%vault_eco_balance_formatted%`, `%rankup_next_rank%`, `%superior_island_level%` über die **PAPI-Bridge**).
- **Gruppen-Reihenfolge/Sortierung** → `groups.yml`.
- Reload: `/tab reload`.

## Cross-Server / Gotchas
- **Einziger** Ort für Tablist/Scoreboard: Kein `*/plugins/tab` auf den Backends. CMI-Tablist ist nur auf
  lobby+skyblock aktiv (survival+rpg deaktiviert), damit sich TAB und CMI nicht überlagern.
- Fehlt eine PAPI-Expansion auf dem Backend, bleibt der Platzhalter im TAB leer → Expansion dort ergänzen.
- Servernamen in TAB-Bridge müssen zu `proxy/velocity.toml` passen.

## Custom Agent
[`.github/agents/tab.agent.md`](../../.github/agents/tab.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [PlaceholderAPI.md](PlaceholderAPI.md) · `proxy/velocity.toml`
