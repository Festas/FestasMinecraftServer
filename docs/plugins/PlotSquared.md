# PlotSquared

**Plot-Welten (Raster) · nur survival · lokal SQLite**

## Zweck
PlotSquared verwaltet rasterbasierte Plot-Welten (jeder Spieler bekommt gleich große Grundstücke) –
genutzt für Town/Tycoon/Freebuild-Bereiche der Survival.

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/PlotSquared/`
- `config/settings.yml` – globale Optionen
- `config/storage.yml` – **Storage-Backend**
- `config/worlds.yml` – Plot-Welt-Generatoren/Layouts
- `schematics/`, `templates/`, `lang/`; `backups/` = Laufzeit

## Storage & Secrets
Lokal (**SQLite**; `storage.yml` → `mysql.use: false`). DB-Passwörter sind **dormante Vendor-Defaults** –
nicht ändern.

## Wichtige Einstellungen / typische Aufgaben
- **Plot-Welt anlegen/anpassen** → `config/worlds.yml` (Plot-/Straßengröße, Generator).
- **Globale Limits/Flags** → `config/settings.yml`.
- Abgrenzung zu Lands: **PlotSquared = Raster-Plots** (Town/Tycoon/Freebuild); **Lands = frei beanspruchte
  Gebiete** in der Hauptwelt.

## Cross-Server / Gotchas
- **Survival-only**. Plot-Weltnamen müssen zu Multiverse/Skript/DeluxeMenus-Warps passen.
- Bei MySQL-Umstieg (mehrere Server, geteilte Plots) Secret-Injektion einrichten – aktuell nicht der Fall.

## Custom Agent
[`.github/agents/land-claims.agent.md`](../../.github/agents/land-claims.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [Lands.md](Lands.md) · [Multiverse.md](Multiverse.md) · [`docs/survival/PLOTS.md`](../survival/PLOTS.md)
