# Lands

**Gebiets-Claiming · nur survival · lokal (Flatfile)**

## Zweck
Lands ermöglicht Spielern das Beanspruchen und Verwalten von Gebieten (Lands/Nationen) inkl. Rollen,
Rechten, Level und Steuern – der Claiming-Layer der Survival-Welt (Overworld/Nether/End/Town).

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/Lands/`
- `config.yml` – globale Optionen · `categories.yml`, `levels.yml`, `player-limits.yml`, `events.yml`
- `Modules/`, `Locale/`; `Data/`, `Logs/` = **Laufzeitdaten**

## Storage & Secrets
Lokal (Flatfile in `Data/`). DB-Optionen sind **dormante Vendor-Defaults** (MySQL aus) – nicht ändern.

## Wichtige Einstellungen / typische Aufgaben
- **Claim-Limits/Level/Kosten** → `levels.yml`, `player-limits.yml`, `config.yml`.
- **Welten**, in denen Claiming gilt → `config.yml`.
- Abgrenzung zu PlotSquared: **Lands = frei beanspruchte Gebiete** in der Hauptwelt; **PlotSquared =
  rasterbasierte Plot-Welten**. Beide koexistieren auf Survival.

## Cross-Server / Gotchas
- **Survival-only**. Nicht mit WorldGuard-Regionen kollidieren lassen (unterschiedliche Schutz-Layer).
- `Data/` ist Serverstand (nicht deployen).

## Custom Agent
[`.github/agents/land-claims.agent.md`](../../.github/agents/land-claims.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [PlotSquared.md](PlotSquared.md) · [WorldGuard.md](WorldGuard.md) · [`docs/survival/PLOTS.md`](../survival/PLOTS.md)
