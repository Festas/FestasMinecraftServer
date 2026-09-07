# ChestShop

**Spieler-Shops (Schilder/Kisten) · nur survival · lokal**

## Zweck
ChestShop erlaubt Spielern, eigene Verkaufs-/Kaufshops über Schild + Kiste zu betreiben (Spieler-zu-
Spieler-Handel) – Ergänzung zum Admin-Shop (ShopGUIPlus) und Markt (GlobalMarketPlus).

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/ChestShop/`
- `config.yml` – Optionen (Steuern, Schild-Format, Schutz)
- `items.yml` – Item-Aliase (Kurznamen auf Schildern) · Sprach-/Nachrichtendateien im Plugin-Ordner

## Storage & Secrets
Lokal; Economy über **CMI/Vault**. Keine geteilte DB, keine Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Transaktionssteuer/Gebühren, Schild-Format, Shop-Schutz** → `config.yml`.
- Zusammenspiel mit WorldGuard-Regionen (wo dürfen Shops stehen) beachten.

## Cross-Server / Gotchas
- **Survival-only**. Preisbildung ist spielerbestimmt – ergänzt, aber ersetzt nicht den Admin-Shop-Anker.
- Gehört mit ShopGUIPlus zum Custom Agent „survival-shops".

## Custom Agent
[`.github/agents/survival-shops.agent.md`](../../.github/agents/survival-shops.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [ShopGUIPlus.md](ShopGUIPlus.md) · [GlobalMarketPlus.md](GlobalMarketPlus.md) · [WorldGuard.md](WorldGuard.md)
