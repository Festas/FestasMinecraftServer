# Multiverse (Core + Inventories)

**Weltenverwaltung · survival / skyblock / rpg(mining) · lokal (pro Welt)**

## Zweck
Multiverse-Core verwaltet mehrere Welten pro Server (Laden, Spawns, Gamerules, Portale). Mit
Multiverse-Inventories werden **getrennte Inventare/Stats pro Weltgruppe** verwaltet (z. B. Nether/End
oder Minispiel-Welten von der Hauptwelt trennen).

## Wo (Server & Config-Pfade)
Vorhanden auf **survival, skyblock, rpg(mining)** (die Lobby hat nur eine Hub-Welt).
- `<server>/plugins/Multiverse-Core/` – `config.yml`, `worlds.yml`, `anchors.yml`
- `<server>/plugins/Multiverse-Inventories/` – `config.yml`, `groups.yml`, `groups/`, `worlds/`
  (`players/`, `playernames.json` sind Laufzeitdaten)

## Storage & Secrets
Lokal, keine DB, keine Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Welt hinzufügen/importieren:** `/mv import <name> <env>` bzw. `/mv create …` → landet in `worlds.yml`.
- **Inventar-Trennung:** Weltgruppen in `groups.yml` definieren (welche Welten teilen ein Inventar).
- Spawn/Gamerules pro Welt über `worlds.yml` bzw. `/mvm set …`.

## Cross-Server / Gotchas
- **Inventar-Gruppen** und Skyblock/SlimeWorldManager sauber abgrenzen – Insel-Welten nicht versehentlich
  in eine geteilte Inventar-Gruppe legen.
- Weltnamen, die Skript/DeluxeMenus/WorldGuard referenzieren, konsistent halten.
- `players/`-Daten sind Serverstand (nicht im Repo).

## Custom Agent
[`.github/agents/multiverse.agent.md`](../../.github/agents/multiverse.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [WorldGuard.md](WorldGuard.md) · [SlimeWorldManager.md](SlimeWorldManager.md)
