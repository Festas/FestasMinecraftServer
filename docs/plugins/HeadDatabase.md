# HeadDatabase

**Dekorative Köpfe · nur survival · lokal**

## Zweck
HeadDatabase liefert einen großen Katalog dekorativer Spielerköpfe (für Menüs, Deko, Shops). Wird u. a.
von DeluxeMenus-Icons und Kosmetik genutzt.

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/HeadDatabase/`
- `config.yml` – Optionen (Kategorien, Economy-Preise)
- `texts/` – Texte/Kategorien

## Storage & Secrets
Lokal; Kopf-Datenbank wird zur Laufzeit bezogen. Economy über CMI/Vault. Keine Deploy-Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Preise/Kategorien/Economy** → `config.yml`.
- Kopf-IDs, die in DeluxeMenus/Skript referenziert werden, konsistent halten.

## Cross-Server / Gotchas
- **Survival-only** – andere Server nutzen für Icons meist Oraxen-Glyphs/Items.

## Custom Agent
Kein dediziertes Agent-Profil (Utility). Änderungen laufen i. d. R. über den `deluxemenus`- bzw.
`survival-shops`-Agent, da Köpfe dort konsumiert werden.

## Referenzen
[README (Wissensbasis)](README.md) · [DeluxeMenus.md](DeluxeMenus.md) · [Oraxen.md](Oraxen.md)
