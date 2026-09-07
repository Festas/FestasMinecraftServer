# Jobs (Jobs Reborn)

**Berufe/Einkommen · nur survival · lokal SQLite**

## Zweck
Jobs Reborn lässt Spieler Berufe wählen und für Tätigkeiten (Abbauen, Farmen, Töten, …) Geld/EXP
verdienen – eine der Einkommensquellen der Survival-Economy.

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/Jobs/`
- `generalConfig.yml` – globale Optionen, Storage
- `jobConfig.yml` – Job-Definitionen (Aktionen → Auszahlung)
- weitere YMLs (Titel, Shop, Restrictions)

## Storage & Secrets
Lokal **SQLite**; Economy über **CMI/Vault** (`S1_CMI`). Keine Deploy-Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Auszahlungen/EXP je Aktion** → `jobConfig.yml` (Balancing der Einkommensquelle).
- **Globale Limits/Boosts** → `generalConfig.yml`.
- Mit ShopGUIPlus-Verkaufspreisen, NextGens-Drops und Rankup-Kosten abstimmen (Geldkreislauf).

## Cross-Server / Gotchas
- **Survival-only**. Direkte Wirkung auf die Survival-Economy – Änderungen bewusst balancen.

## Custom Agent
[`.github/agents/jobs.agent.md`](../../.github/agents/jobs.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [ShopGUIPlus.md](ShopGUIPlus.md) · [NextGens.md](NextGens.md) · [Rankup.md](Rankup.md) · [CMI.md](CMI.md)
