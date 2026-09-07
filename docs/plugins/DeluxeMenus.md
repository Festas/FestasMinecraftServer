# DeluxeMenus

**GUI-Menüs · lobby / survival / skyblock / rpg(mining) · lokal (keine DB)**

## Zweck
DeluxeMenus baut alle klickbaren GUI-Menüs: Server-Selector (Lobby), Hilfe-/Regel-Menüs, Warps,
Kosmetik, Generatoren, Tycoon-Menüs u. a. Menüs nutzen PlaceholderAPI-Werte und Requirements.

## Wo (Server & Config-Pfade)
Jedes Backend: `<server>/plugins/DeluxeMenus/`
- `config.yml` – registriert die Menüs + globale Optionen
- `gui_menus/` – die einzelnen Menü-Definitionen (`.yml`)

Menüs pro Server (Auswahl):
- Lobby: `server_selector.yml`, `network_guide.yml`, `rules.yml`, `help_menu.yml`, `advanced_menu.yml`
- Survival: `warps.yml`, `generators.yml`, `cosmetics.yml`, `tycoon_main.yml`, `tycoon_warps*.yml`
- Skyblock: `basics_menu.yml`, `help_menu.yml`, `advanced_menu.yml`
- RPG/Mining: `cosmetics.yml`, `help_menu.yml`, `advanced_menu.yml`

## Storage & Secrets
Lokal, keine DB, keine Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Neues Menü** → `.yml` in `gui_menus/` anlegen **und** in `config.yml` registrieren (sonst lädt es nicht).
- **Öffnen** meist via `[openguimenu]`/Command aus Skript, Navigator oder CMI.
- Requirements/Click-Actions nutzen PAPI-Platzhalter → passende Expansion auf dem Backend sicherstellen.
- Reload: `/dm reload`.

## Cross-Server / Gotchas
- Der **Server-Selector** (Lobby) schickt Spieler über Velocity zu den Backends – Servernamen müssen zu
  `proxy/velocity.toml` passen (`lobby/rpg/survival/skyblock`; öffentlicher Name „mining" = Ordner `rpg`).
- Menü-Icons/Heads teils über HeadDatabase/Oraxen – Abhängigkeit beachten.

## Custom Agent
[`.github/agents/deluxemenus.agent.md`](../../.github/agents/deluxemenus.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [PlaceholderAPI.md](PlaceholderAPI.md) · [Oraxen.md](Oraxen.md) · [HeadDatabase.md](HeadDatabase.md)
