# X-Prison

**Prison-Kern (rpg/mining) · nur rpg · H2 lokal · Dashboard-Addon = injizierte MySQL**

## Zweck
X-Prison ist das Kern-Plugin des Prison-/Mining-Servers (öffentlich „mining", Ordner `rpg`): Mines,
Ränge & Prestige, Tokens/Gems/Currencies, AutoSell/AutoMiner, Block-Rewards, Battlepass, Bombs sowie
zahlreiche **Addons** (Enchants, Pets, BlackMarket, Robot-/Pet-Finder u. v. m.).

## Wo (Server & Config-Pfade)
Nur **rpg**: `rpg/plugins/X-Prison/`
- `config.yml` – zentrale Optionen (u. a. `database_type: H2`)
- **Mines/Blöcke/Rewards**: `mines.yml`, `blocks.yml`, `block-rewards.yml`
- **Progression**: `ranks.yml` (Ränge), `prestiges.yml` (Prestige), `rebirths.yml` (Rebirth),
  `multipliers.yml` (Sell-/Token-Multiplier), `gangs.yml` (Gangs), `quests.yml` (Quests)
- **Pickaxe/Enchants**: `enchants.yml` + `enchants/` (Enchant-Definitionen), `pickaxe-levels.yml`,
  `pickaxe-quality.yml`, `pickaxe-skins.yml`
- **Weitere Feature-YMLs**: `autosell.yml`, `autominer.yml`, `battlepass.yml`, `currencies.yml`,
  `bombs.yml`, `dailyrewards.yml`, `mining-stats.yml`, `logging.yml` (`history.yml` = Laufzeitdaten)
- `addons/` – **jede** Erweiterung mit eigener Config (z. B. `addons/Dashboard/config.yml`,
  `addons/Pets/…`, `addons/AnimatedEnchants*/…`, `addons/BlackMarket/…`)

## Storage & Secrets
- **Kern: H2 lokal** (`database_type: H2`).
- **Dashboard-Addon** nutzt eine externe **MySQL** mit Platzhaltern
  `__XPRISON_DASHBOARD_USER__` / `__XPRISON_DASHBOARD_PASSWORD__` / `__XPRISON_DASHBOARD_JWT_SECRET__` in
  `addons/Dashboard/config.yml` – injiziert aus **`XPRISON_DASHBOARD_ENV`** in `deploy-rpg.yml`.
  Beim Bearbeiten die `__…__`-Tokens **erhalten**.

## Wichtige Einstellungen / typische Aufgaben
- **Mines/Blöcke/Rewards** → `mines.yml`, `blocks.yml`, `block-rewards.yml`.
- **Ränge/Prestige/Rebirth & Kosten** → `ranks.yml`, `prestiges.yml`, `rebirths.yml` (Prison-eigene
  Progression, **nicht** Rankup-Plugin); Sell-/Token-Multiplier → `multipliers.yml`.
- **Pickaxe/Enchants** → `enchants.yml` (+ `enchants/`), `pickaxe-levels.yml`, `pickaxe-quality.yml`,
  `pickaxe-skins.yml`.
- **Gangs/Quests/Battlepass** → `gangs.yml`, `quests.yml`, `battlepass.yml`.
- **Addon aktivieren/anpassen** → jeweilige `addons/<Name>/config.yml`.
- Economy des Prison-Servers = CMI (`S3_CMI`) + Prison-Currencies (Tokens/Gems).

## Cross-Server / Gotchas
- **rpg-only** (= „mining"). Verwechslungsgefahr: `rpg` ist der Velocity-/Ordnername, **öffentlich „mining"**.
- Survival-Rankup/Jobs/NextGens gelten hier **nicht** – Prison hat eigene Ökonomie & Progression.
- Viele Addons = viele Configs; Änderungen gezielt im betroffenen `addons/<Name>/` vornehmen.

## Custom Agent
[`.github/agents/prison.agent.md`](../../.github/agents/prison.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [XPrivateMines.md](XPrivateMines.md) · [XPrisonArmors.md](XPrisonArmors.md) · [XRobots.md](XRobots.md) · [`docs/prison/PRISON_GUIDE.md`](../prison/PRISON_GUIDE.md)
