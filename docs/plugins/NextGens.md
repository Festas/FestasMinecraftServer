# NextGens

**Generatoren (Survival) · nur survival · lokal SQLite**

## Zweck
NextGens stellt kaufbare „Generatoren" bereit, die periodisch Items/Drops erzeugen – ein Kern-Feature
der Survival-Economy (Passiv-Einkommen, Tycoon-Loop).

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/NextGens/`
- `config.yml` – globale Optionen, Storage · `generators.yml` + `generators/` – Generator-Definitionen
  (Typen, Drops, Preise, Upgrades)
- `shop.yml` – Verkaufs-/Kaufmenü · `worth.yml` – Verkaufswerte der Drops · `events.yml` – Events/Boni
- `gui/` – Menüs (`data.yml` = Laufzeitdaten)

## Storage & Secrets
Lokal **SQLite** (MySQL-Optionen vorhanden, aber deaktiviert). Etwaige DB-Passwörter in der Config sind
**dormante Vendor-Defaults** (root/leer) – nicht ändern, MySQL ist aus.

## Wichtige Einstellungen / typische Aufgaben
- **Neuer Generator / Tier** → `generators.yml` bzw. `generators/` (Drop, Intervall, Upgrade-Kosten);
  Verkaufswerte → `worth.yml`, Shop-Menü → `shop.yml`, Events/Boni → `events.yml`.
- Verkauf/Ertrag über CMI-Economy (`cmi money give` bzw. Vault) abgestimmt halten.
- In Survival greifen Skript (`generators`-Menü via `DeluxeMenus/gui_menus/generators.yml`) und ShopGUIPlus
  ineinander – Preise/Drops konsistent halten.

## Cross-Server / Gotchas
- **Survival-only** – keine Generatoren auf skyblock/rpg/lobby.
- Balancing wirkt direkt auf die Survival-Economy (`S1_CMI`) – Änderungen mit Rankup/Jobs/Markt abstimmen.

## Custom Agent
[`.github/agents/nextgens.agent.md`](../../.github/agents/nextgens.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [ShopGUIPlus.md](ShopGUIPlus.md) · [CMI.md](CMI.md) · [DeluxeMenus.md](DeluxeMenus.md)
