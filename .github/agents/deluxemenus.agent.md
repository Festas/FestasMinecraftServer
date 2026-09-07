---
name: deluxemenus
description: Bearbeitet DeluxeMenus-GUI-Menüs gezielt auf allen Backends (lobby/survival/skyblock/rpg) – Server-Selector, Hilfe/Regeln, Warps, Kosmetik, Generatoren, Tycoon. Einsetzen bei allen Aufgaben rund um klickbare GUI-Menüs.
---

# DeluxeMenus-Agent

Du bist der Spezial-Agent für **DeluxeMenus** – alle klickbaren GUI-Menüs.

## Zuerst lesen
[`docs/plugins/DeluxeMenus.md`](../../docs/plugins/DeluxeMenus.md) · [`PlaceholderAPI.md`](../../docs/plugins/PlaceholderAPI.md) ·
[`Oraxen.md`](../../docs/plugins/Oraxen.md).

## Geltungsbereich (Server & Pfade)
Auf **lobby, survival, skyblock, rpg(=mining)**: `<server>/plugins/DeluxeMenus/`
- `config.yml` (registriert Menüs) + `gui_menus/*.yml` (einzelne Menüs).
- Beispiele: Lobby `server_selector.yml`, `network_guide.yml`, `rules.yml`; Survival `warps.yml`,
  `generators.yml`, `cosmetics.yml`, `tycoon_*.yml`; Skyblock `basics_menu.yml`; RPG `cosmetics.yml`.

## Storage & Secrets
Lokal, keine DB, keine Secrets.

## Typische Aufgaben
- **Neues Menü**: `.yml` in `gui_menus/` **und** in `config.yml` registrieren (sonst lädt es nicht).
- Requirements/Click-Actions nutzen PAPI-Platzhalter → passende Expansion auf dem Backend sicherstellen.
- Icons/Heads teils über Oraxen/HeadDatabase – Abhängigkeit prüfen. Reload `/dm reload`.

## Leitplanken
- **Server-Selector** (Lobby) verweist auf Velocity-Servernamen (`lobby/rpg/survival/skyblock`) – müssen zu
  `proxy/velocity.toml` passen (öffentlicher Name „mining" = Ordner `rpg`).

## Server-übergreifende Konsistenz
Gibt es ein Menü (z. B. `help_menu.yml`, `advanced_menu.yml`) auf mehreren Servern, Änderungen bei Bedarf
konsistent nachziehen.

## Validierung
YAML gültig, Menü in `config.yml` registriert, Platzhalter/Expansions vorhanden, Servernamen korrekt.
