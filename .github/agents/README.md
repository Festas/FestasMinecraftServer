# Custom AI Agents · FestasMinecraftServer

Dieser Ordner enthält **spezialisierte Copilot-Agents** – pro relevantem Plugin bzw. Subsystem einer.
Jeder Agent ist darauf ausgelegt, „sein" Plugin **gezielt und konsistent auf allen betroffenen Servern**
zu bearbeiten (lobby / survival / skyblock / rpg(=mining) / proxy).

## Format & Auto-Discovery
- Dateien: `<name>.agent.md` mit YAML-Frontmatter (`name`, `description`) + Anleitung im Body.
- Copilot erkennt diese Agents automatisch. Der Agent wird über seine `description` ausgewählt (wann
  einsetzen). Ohne `tools`-Feld hat der Agent Zugriff auf alle Standard-Werkzeuge (Lesen/Bearbeiten/Suchen/Validieren).
- Ergänzend greifen **repo-weite** Regeln aus [`../copilot-instructions.md`](../copilot-instructions.md) und
  **pfad-spezifische** Regeln aus [`../instructions/plugins.instructions.md`](../instructions/plugins.instructions.md).

## Datenbasis
Jeder Agent stützt sich auf die Wissensbasis unter [`../../docs/plugins/`](../../docs/plugins/) (Index +
`docs/plugins/<Plugin>.md`). **Dort zuerst nachschlagen**, bevor Configs geändert werden.

## Verfügbare Agents
| Agent | Deckt ab | Server |
|---|---|---|
| `cmi` | CMI (Economy, Homes, Warps, Ränge, Neustarts, Module) | alle Backends |
| `luckperms` | LuckPerms (Permissions, Gruppen, Tracks) | alle Backends |
| `plan` | Plan (Analytics, Web-Dashboard) | proxy/lobby/survival/rpg + skyblock(SQLite) |
| `placeholderapi` | PlaceholderAPI (Platzhalter/Expansions) | alle Backends |
| `skript` | Skript (Gameplay-Logik, Broadcasts, Navigator) | alle Backends |
| `deluxemenus` | DeluxeMenus (GUI-Menüs) | alle Backends |
| `oraxen` | Oraxen (Custom-Items, Resourcepack) | alle Backends |
| `worldguard` | WorldGuard/WorldEdit (Regionen) | lobby/survival/rpg |
| `multiverse` | Multiverse-Core/Inventories (Welten) | survival/skyblock/rpg |
| `tab` | TAB (Tablist/Scoreboard, proxy-weit) | proxy |
| `libertybans` | LibertyBans (Moderation, proxy-weit) | proxy |
| `proxy-network` | MiniMOTD + SkinsRestorer + ForceResourcepacks | proxy |
| `superiorskyblock2` | SuperiorSkyblock2 + SlimeWorldManager | skyblock |
| `deluxebazaar` | DeluxeBazaar (Skyblock-Shop) | skyblock |
| `prison` | X-Prison + XPrivateMines + XPrisonArmors + XRobots | rpg(=mining) |
| `nextgens` | NextGens (Generatoren) | survival |
| `globalmarketplus` | GlobalMarketPlus (Spieler-Markt) | survival/skyblock/rpg |
| `survival-shops` | ShopGUIPlus + ChestShop | survival |
| `jobs` | Jobs Reborn | survival |
| `land-claims` | Lands + PlotSquared | survival |
| `progression` | Rankup + Autorank + CMI-Ränge (Zeit-/Kauf-Rang) | survival |
| `bluemap` | BlueMap (Web-Live-Karte) | survival/rpg |

Support-/Bibliotheks-Plugins (Vault, ProtocolLib, CommandAPI, FAWE, spark …) haben bewusst **keinen**
eigenen Agent – siehe [`../../docs/plugins/libraries.md`](../../docs/plugins/libraries.md).

## Konventionen für alle Agents
- **Secrets/Platzhalter `__…__` wörtlich erhalten**; niemals echte Credentials committen.
- **Minimal & chirurgisch** ändern; YAML 2-Space, keine Tabs; Struktur/Kommentare bewahren.
- **Server-übergreifende Konsistenz** wahren (gleiche Änderung auf allen betroffenen Servern).
- **`rpg/` = „mining"** (öffentlicher Name); Ordner/Velocity-Name `rpg`.
- Keine Serverdaten (`world*/`, `playerdata/`, `*.db`, `*.log`, `*.jar`) bearbeiten.
