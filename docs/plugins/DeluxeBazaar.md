# DeluxeBazaar

**Instant-Shop/Bazaar · nur skyblock · lokal SQLite**

## Zweck
DeluxeBazaar ist der GUI-Instant-Shop des Skyblock-Servers (Sofort-Kauf/-Verkauf mit dynamischen oder
festen Preisen) – das Skyblock-Pendant zum Survival-ShopGUIPlus.

## Wo (Server & Config-Pfade)
Nur **skyblock**: `skyblock/plugins/DeluxeBazaar/`
- `config.yml` – globale Optionen · `database.yml` – Storage-Konfiguration
- `categories.yml`, `items.yml`, `menus.yml`, `messages.yml`

## Storage & Secrets
Lokal (**SQLite**, siehe `database.yml`); Economy über **CMI/Vault** (`S5_CMI`). Keine geteilte DB, keine
Deploy-Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Items/Kategorien/Preise** → `items.yml` / `categories.yml`.
- **Menü-Layout** → `menus.yml`.
- Verkaufspreise als Economy-Senke mit SuperiorSkyblock2-Progression (Insel-Level/Missionen) abstimmen.

## Cross-Server / Gotchas
- **Skyblock-only**. Nicht mit Survival-Shops verwechseln (getrennte Economys `S5_CMI` vs. `S1_CMI`).
- `logs.txt` ist Laufzeitdatei.

## Custom Agent
[`.github/agents/deluxebazaar.agent.md`](../../.github/agents/deluxebazaar.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [SuperiorSkyblock2.md](SuperiorSkyblock2.md) · [CMI.md](CMI.md) · [GlobalMarketPlus.md](GlobalMarketPlus.md)
