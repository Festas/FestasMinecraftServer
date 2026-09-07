# ShopGUIPlus

**Admin-Shop (GUI) · nur survival · lokal SQLite**

## Zweck
ShopGUIPlus ist der GUI-basierte Admin-Shop mit festen Kauf-/Verkaufspreisen (Server verkauft/kauft) –
der Preisanker der Survival-Economy.

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/ShopGUIPlus/`
- `config.yml` – globale Optionen, Economy-Provider, Storage
- Shop-Kategorien/Preise in den `shops/`-YMLs
- `pricemodifiers.yml` – rang-/permissionabhängige Preis-Modifikatoren · `lang.yml` – Nachrichten

## Storage & Secrets
Lokal **SQLite**; Economy über **CMI/Vault**. DB-Passwörter in `config.yml` sind **dormante
Vendor-Defaults** – nicht ändern, externer DB-Store ist aus.

## Wichtige Einstellungen / typische Aufgaben
- **Preise/Items** je Kategorie → `shops/`-YMLs (Kauf-/Verkaufspreis).
- **Rang-/Permission-Rabatte** (z. B. VIP kauft/verkauft zu anderem Preis) → `pricemodifiers.yml`;
  Nachrichten → `lang.yml`.
- **Verkaufspreise** wirken als Senke/Quelle der Economy – mit NextGens-Drops, Jobs-Einkommen und
  Rankup-Kosten abstimmen.
- Ein Skript (`survival/plugins/Skript/scripts/shopguiplus.sk`) hängt an ShopGUIPlus – bei Struktur-
  änderungen dort mitprüfen.
- Reload: `/shop reload`.

## Cross-Server / Gotchas
- **Survival-only** – skyblock nutzt DeluxeBazaar, rpg/mining hat eigene Prison-Ökonomie.
- Preisänderungen haben Netzwerkwirkung auf das Survival-Balancing – dokumentiert und bewusst ändern.

## Custom Agent
[`.github/agents/survival-shops.agent.md`](../../.github/agents/survival-shops.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [ChestShop.md](ChestShop.md) · [NextGens.md](NextGens.md) · [Jobs.md](Jobs.md) · [CMI.md](CMI.md)
