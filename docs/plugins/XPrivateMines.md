# XPrivateMines

**Private Minen (rpg/mining) · nur rpg · lokal · Dashboard-Addon = injizierte MySQL**

## Zweck
XPrivateMines gibt Spielern eigene, upgradebare Privatminen (Tiers, Schematics, Rewards) – ergänzt die
öffentlichen X-Prison-Mines.

## Wo (Server & Config-Pfade)
Nur **rpg**: `rpg/plugins/XPrivateMines/`
- `config.yml`, `guis.yml`, `messages.yml`
- `mine-tiers.yml` – **Tier-/Upgrade-Stufen** · `mines.yml` – Mine-Definitionen
- `schematic-settings.yml`, `schematics/` – Mine-Vorlagen
- `addons/Dashboard/` – Web-Dashboard-Addon

## Storage & Secrets
Lokal (Mine-/Tier-Daten in Plugin-Dateien). **Dashboard-Addon** nutzt externe **MySQL** mit Platzhaltern
`__XPRIVATEMINES_DASHBOARD_USER__` / `__XPRIVATEMINES_DASHBOARD_PASSWORD__` /
`__XPRIVATEMINES_DASHBOARD_JWT_SECRET__` in `addons/Dashboard/config.yml` – injiziert aus
**`XPRIVATEMINES_DASHBOARD_ENV`** (`deploy-rpg.yml`). Tokens beim Bearbeiten **erhalten**.

## Wichtige Einstellungen / typische Aufgaben
- **Tiers/Upgrade-Kosten/Rewards** → `mine-tiers.yml`.
- **Mine-Layouts/Schematics** → `schematic-settings.yml` + `schematics/`.
- **GUIs** → `guis.yml`.

## Cross-Server / Gotchas
- **rpg-only** (= „mining"). Balancing mit X-Prison-Currencies (Tokens/Gems) und CMI (`S3_CMI`) abstimmen.
- Schematic-Dateien sind teils binär/groß – gezielt ändern.

## Custom Agent
[`.github/agents/prison.agent.md`](../../.github/agents/prison.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [X-Prison.md](X-Prison.md) · [`docs/prison/PRISON_GUIDE.md`](../prison/PRISON_GUIDE.md)
