# SuperiorSkyblock2

**Skyblock-Inseln · nur skyblock · lokal SQLite**

## Zweck
SuperiorSkyblock2 (SSB2) ist das Kern-Plugin des Skyblock-Servers: Inseln erstellen/verwalten,
Insel-Level, Missionen, Rollen/Rechte, Warps, Upgrades, Bewertung.

## Wo (Server & Config-Pfade)
Nur **skyblock**: `skyblock/plugins/SuperiorSkyblock2/`
- `config.yml` – zentrale Konfiguration (Storage, Welt, Limits)
- `menus/` – Insel-GUIs · `modules/` – aktivierte Module · `block-values.yml`, `heads.yml`,
  `interactables.yml`, `entity-categories.yml`, `safe_blocks.yml`
- `backup/`, `logs/` = Laufzeitdaten

## Storage & Secrets
Lokal **SQLite** (`config.yml` → `database.type: SQLite`; MySQL-Felder wie `root` sind **dormante
Defaults**). **Kein** Remote-Skyblock-DB / kein `SKYBLOCK_DB_ENV`. Nur LuckPerms nutzt eine geteilte DB.

## Wichtige Einstellungen / typische Aufgaben
- **Insel-Level/Block-Werte** → `block-values.yml`; **Missionen/Upgrades** → `config.yml`/`modules/`.
- **Rechte-Rollen** auf der Insel (Member/Coop/…) statt WorldGuard – Skyblock nutzt **kein** WorldGuard.
- Platzhalter `%superior_island_level%` u. a. für TAB/Scoreboard – PAPI-Expansion `SuperiorSkyblock`
  sicherstellen.

## Cross-Server / Gotchas
- Insel-Welten liegen als **SlimeWorlds** vor (SlimeWorldManager) – nicht versehentlich in eine geteilte
  Multiverse-Inventory-Gruppe legen.
- **Skyblock-only**. Economy via CMI (`S5_CMI`) + DeluxeBazaar.

## Custom Agent
[`.github/agents/superiorskyblock2.agent.md`](../../.github/agents/superiorskyblock2.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [SlimeWorldManager.md](SlimeWorldManager.md) · [DeluxeBazaar.md](DeluxeBazaar.md) · [`docs/skyblock/ISLANDS.md`](../skyblock/ISLANDS.md) · [`docs/skyblock/PROGRESSION.md`](../skyblock/PROGRESSION.md)
