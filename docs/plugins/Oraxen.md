# Oraxen

**Custom-Items & Resourcepack · lobby / survival / skyblock / rpg(mining) · lokal (keine DB)**

## Zweck
Oraxen liefert Custom-Items, Glyphs/Emojis, Fonts, HUDs, Rezepte, Sounds und generiert das
Resourcepack, das die Spieler netzwerkweit sehen (custom Texturen/Menü-Icons).

## Wo (Server & Config-Pfade)
Jedes Backend: `<server>/plugins/Oraxen/`
- `settings.yml` – globale Optionen, Pack-Auslieferung
- `mechanics.yml` – aktivierte Mechaniken
- `items/` – Item-Definitionen · `glyphs/` – Glyphs/Emojis · `recipes/` – Rezepte
- `pack/` – Resourcepack-Quellen (Texturen/Modelle) · `font.yml`, `hud.yml`, `sounds.yml`,
  `paintings.yml` (Custom-Gemälde), `text_effects.yml` (Text-/Chat-Effekte)

## Storage & Secrets
Lokal, keine DB, keine Secrets. Die Pack-Auslieferung kann über einen Host/Hash laufen (siehe
`settings.yml`); der Proxy nutzt zusätzlich ForceResourcepacks.

## Wichtige Einstellungen / typische Aufgaben
- **Neues Item** → `.yml` unter `items/` + ggf. Textur/Modell in `pack/`, dann Pack neu generieren
  (`/oraxen reload pack`) und `/oraxen reload items`.
- **Glyph/Emoji** → `glyphs/`; im Chat/GUI über PlaceholderAPI/DeluxeMenus nutzbar.
- Pack-Auslieferung testen; bei Änderungen an `pack/` **Pack-Hash** aktualisieren.

## Cross-Server / Gotchas
- Custom-Items/Glyphs, die in DeluxeMenus oder Skript referenziert werden, müssen auf dem jeweiligen
  Backend **existieren** – Item-IDs netzwerkweit konsistent halten.
- Große Binärdateien in `pack/` – bewusst und sparsam ändern; `*.jar`/Archive werden vom Deploy
  ausgeschlossen, `pack/`-Assets aber synchronisiert.

## Custom Agent
[`.github/agents/oraxen.agent.md`](../../.github/agents/oraxen.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [DeluxeMenus.md](DeluxeMenus.md) · [ForceResourcepacks.md](ForceResourcepacks.md)
