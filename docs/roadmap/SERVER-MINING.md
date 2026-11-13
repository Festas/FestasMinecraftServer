# Mining — Verbesserungs-Roadmap

> **Namens-Hinweis:** Ordner & Velocity-Name = **`rpg`**, öffentlicher Name = **„mining"**, in Logs manchmal „prison". Alle drei meinen **denselben** Prison-/Mining-Server.

**Zweck:** Casual Prison-/Mining-Server auf Basis von **X-Prison** (Abbau → AutoSell → Rankup → Prestige → Rebirth) — dieses Doc schließt die größte Lücke: die **Glue-Skript-Schicht**.

**Status:** 🟡 **im Aufbau** (Prison-Core tief konfiguriert, Klebeschicht & Launch-Balancing offen) · **Velocity-Server:** `rpg` · **Port:** `25567`

---

## So nutzt du dieses Doc

Jede Zeile der [Haupt-Tabelle](#baustellen--verbesserungen) ist eine **abhakbare Aufgabe** (☐) mit **ID**, **Prio** (P0 = Hygiene/Blocker, P1 = Kern-Feature, P2 = Politur), **zuständigem Agent** und **Validierung**. Beauftrage den genannten Agent (ein Plugin → direkt; mehrere → [`../plugins/CROSS-PLUGIN.md §4`](../plugins/CROSS-PLUGIN.md#4-feature-playbooks)), ändere **minimal & chirurgisch** und validiere via [Checkliste §5](../plugins/CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste). Einstieg & Guardrails: [Roadmap-Hub `README.md`](README.md).

---

## IST-Stand (kurz)

- **Plugin-Stack:** CMI (+CMILib), X-Prison, XPrivateMines, XRobots, XPrisonArmors, DeluxeMenus, Oraxen, GlobalMarketPlus, PlaceholderAPI, Plan, Multiverse-Core/-Inventories, WorldGuard, LibsDisguises, Skript, Vault, ProtocolLib, FastAsyncWorldEdit, NBTAPI, BlueMap (+ faststats/spark).
- **X-Prison (am tiefsten konfiguriert):** `autosell.yml`, `battlepass.yml`, `blocks.yml`/`block-rewards.yml`, `bombs.yml`, `enchants/` (**26 Enchant-JSONs**) + `enchants.yml`, `gangs.yml`, `mines.yml`, `multipliers.yml`, `pickaxe-levels.yml`/`pickaxe-quality.yml`/`pickaxe-skins.yml`, `prestiges.yml`, `quests.yml`, `ranks.yml`, `rebirths.yml`, `currencies.yml`, `autominer.yml`, `dailyrewards.yml`.
  - **Addons:** Pets, BlackMarket (`items/` ~13 Item-Ymls), Events (6 Ymls: enchant-frenzy, gem-rush, payday, pickaxe-frenzy, token-frenzy, xp-boost), AnimatedEnchants (+2/+3) & weitere Enchant-Addons, Dashboard, Momentum, Vanguard, Ascendant, X-Prison Discord Sync (Embeds).
- **XPrivateMines** (`guis.yml`, `mine-tiers.yml`, `mines.yml`, `schematic-settings.yml`, `schematics/`), **XRobots** (`config/guis/messages/robots.yml`), **XPrisonArmors** (`armors.yml`, `config.yml`, `messages.yml`) — voll bestückt.
- **Skript praktisch LEER:** nur generische `navigator.sk`, `help.sk`, `daily_rewards.sk` → **Custom-Glue-Logik/Automationen fehlen**.
- **Oraxen dünn** (kaum Custom-Items).
- **Economy:** CMI-DB **`S3_CMI`** (pro Server) + Prison-eigene Currencies (Tokens/Gems etc. via X-Prison).
- **Secrets:** Dashboard-Addons nutzen Platzhalter — `__XPRISON_DASHBOARD_{USER,PASSWORD,JWT_SECRET}__` und `__XPRIVATEMINES_DASHBOARD_{USER,PASSWORD,JWT_SECRET}__` (injiziert via `deploy-rpg.yml`).
- **`server.properties`:** `motd=A Minecraft Server` (Default — Netz-MOTD via Proxy-MiniMOTD), `gamemode=survival`, `difficulty=normal`.
- **CMI-Neustart:** täglich **04:05** (Europe/Berlin).

## Stärken

- **X-Prison ist der tiefste Content-Block des Netzes** — vollständige Progressions-Kette (Ränge/Mines, Enchants, Pickaxe-Level/Quality, Prestige, Rebirth) plus zahlreiche Addons (BlackMarket, Events, Pets, Dashboard, Discord-Sync).
- XPrivateMines, XRobots, XPrisonArmors ergänzen den Loop vollständig.
- Solide Economy-Trennung (`S3_CMI`) + Prison-Currencies.

## Baustellen & Verbesserungen

| ID | Prio | Aufgabe | Zuständiger Agent | Betroffene Pfade | Validierung |
|----|:----:|---------|-------------------|------------------|-------------|
| ☐ MIN-01 | **P0** | **Glue-Skript-Schicht aufbauen** (größte Lücke): Onboarding neuer Spieler, Broadcast-Rotation in `help.sk`, Reward-/Event-Verzahnung (BlackMarket/Events/Battlepass) und Navigator. **Nav nur leere Slots füllen** (Gameplay-Server, nie Inventar leeren/Gamemode erzwingen). | `skript` | `rpg/plugins/Skript/scripts/{navigator,help,daily_rewards,…}.sk` | `/sk reload`; Onboarding/Broadcasts/Rewards greifen; Nav leert **kein** Inventar |
| ☐ MIN-02 | **P0** | **Dashboard-Secrets verifizieren:** X-Prison- & XPrivateMines-Dashboard nutzen `__…_DASHBOARD_*__` → prüfen, dass `deploy-rpg.yml` injiziert (fail-closed) und Token **wörtlich** bleiben. | `prison` (+ Deploy/Repo-Maintainer) | `rpg/plugins/X-Prison/addons/Dashboard/config.yml`, `rpg/plugins/XPrivateMines/addons/Dashboard/config.yml` | Deploy bricht bei fehlendem Secret ab; Dashboard-Login nach Deploy |
| ☐ MIN-03 | **P1** | **X-Prison-Launch-Balancing:** Zonen/Mines, Ränge, Prestige/Rebirth, Enchants (26 JSONs), Pickaxe-Levels/Quality, AutoSell, Battlepass, BlackMarket/Events auf stimmige Kurve bringen. | `prison` | `rpg/plugins/X-Prison/{mines.yml,ranks.yml,prestiges.yml,rebirths.yml,enchants/,pickaxe-*.yml,autosell.yml,battlepass.yml,addons/BlackMarket,addons/Events}` | `/prison`: Rankup → AutoSell → Prestige → Rebirth-Loop testbar; keine Geld-/Token-Explosion |
| ☐ MIN-04 | **P1** | **XPrivateMines/XRobots/XPrisonArmors-Feinschliff:** Mine-Tiers/Schematics, Robot-Typen, Armor-Set-Boni mit dem X-Prison-Loop abstimmen. | `prison` | `rpg/plugins/{XPrivateMines,XRobots,XPrisonArmors}/` | `/pmine`, `/robots`, Armor-Set-Boni greifen konsistent |
| ☐ MIN-05 | **P1** | **Economy `S3_CMI` + Prison-Currencies konsistent:** CMI-Geld ⇄ Tokens/Gems (Umtausch/Preise), damit keine Parallel-Ökonomie entgleist. | `cmi` + `prison` | `rpg/plugins/CMI/`, `rpg/plugins/X-Prison/currencies.yml` | `/cmi money` (bucht `S3_CMI`); Currency-Umtausch plausibel |
| ☐ MIN-06 | **P1** | **Neue Doku anlegen:** `docs/prison/PROGRESSION.md` — Rang-/Prestige-/Rebirth-/Ascension-Kette, Currencies, Enchant-/Pickaxe-Pfad, Addon-Verzahnung (es existiert nur `README.md`, `PLUGINS.md`, `PRISON_GUIDE.md`). | Doku (Repo-Maintainer; vgl. [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md#e-neue-docs-anlegen)) | `docs/prison/PROGRESSION.md` | Markdown-Review; Links; stimmig mit X-Prison-Config |
| ☐ MIN-07 | **P2** | **Oraxen-Custom-Items ausbauen** (aktuell dünn): Prison-Items/Icons (Pickaxe-Skins, Token-Icons, Menü-Köpfe); nach Pack-Änderung **SHA-1** in ForceResourcepacks (Proxy) bumpen. | `oraxen` (+ `proxy-network`) | `rpg/plugins/Oraxen/items/` | `/oraxen reload`; Client-Icons; Hash aktualisiert |
| ☐ MIN-08 | **P2** | BlueMap für die Mining-Welt tunen (Render-Grenzen, Marker, Performance). | `bluemap` | `rpg/plugins/BlueMap/` | Karte rendert; Web erreichbar |
| ☐ MIN-09 | **P2** | **Naming-Klarheit rpg = „mining":** Server-Name in Server-Selector/TAB/Plan == `proxy/velocity.toml`; MOTD/Branding über Proxy-MiniMOTD. | `proxy-network` (+ `tab`, `plan`) | `proxy/plugins/{minimotd-velocity,tab}/`, Plan-Serveridentität *(Info: `rpg/server.properties`)* | Selector routet „mining" → `rpg`; TAB/Plan zeigen „mining" konsistent |

## Offene Entscheidungen

- **Öffentliches Naming final:** „mining" (Website/Economy/Plan) vs. „prison" (Logs/Genre) vs. `rpg` (Ordner/Velocity) — einheitliche Außen-Sprache festlegen (betrifft MIN-09 & Doku).
- **Currency-Design:** Wie stark koppeln CMI-Geld (`S3_CMI`) und Prison-Tokens/Gems? Umtausch erlaubt oder strikt getrennt? (Betrifft MIN-05.)
- **Launch-Scope:** Welche Addons sind zum Launch aktiv (BlackMarket, Events, Pets, Discord-Sync, Dashboard) und welche kommen später? (Betrifft MIN-03/MIN-07.)

## Doku-Pflege

- **`docs/prison/PROGRESSION.md` neu anlegen** (MIN-06).
- [`docs/prison/README.md`](../prison/README.md), [`PLUGINS.md`](../prison/PLUGINS.md), [`PRISON_GUIDE.md`](../prison/PRISON_GUIDE.md) nach dem Glue-/Balancing-Ausbau aktualisieren und Naming (rpg = „mining") konsistent halten.
- Nach MIN-02 den Secrets-/Deploy-Status in [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md#f-secrets-referenz-verifizieren) abhaken.

---

**Navigation:** [Roadmap-Hub](README.md) · [Gesamtstruktur](GESAMTSTRUKTUR.md) · [KI-Agents](KI-AGENTS.md) · [Proxy](PROXY.md)
**Plugin-Wissensbasis:** [Index](../plugins/README.md) · [CROSS-PLUGIN](../plugins/CROSS-PLUGIN.md) · [X-Prison](../plugins/X-Prison.md) · [XPrivateMines](../plugins/XPrivateMines.md) · [XPrisonArmors](../plugins/XPrisonArmors.md) · [XRobots](../plugins/XRobots.md) · [CMI](../plugins/CMI.md) · [Skript](../plugins/Skript.md) · [Oraxen](../plugins/Oraxen.md) · [BlueMap](../plugins/BlueMap.md) · [GlobalMarketPlus](../plugins/GlobalMarketPlus.md)
**Server-Doku:** [`README`](../prison/README.md) · [`PLUGINS`](../prison/PLUGINS.md) · [`PRISON_GUIDE`](../prison/PRISON_GUIDE.md)

**Letzte Aktualisierung:** 2026-11-02
