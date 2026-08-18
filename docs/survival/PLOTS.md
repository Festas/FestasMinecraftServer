# Plot-System — Tycoon Server

Detaillierte Dokumentation des PlotSquared-basierten Plot-Systems für den Tycoon-Gamemode, inklusive Multi-Plot-Unterstützung, Merging und Reset-Verhalten.

---

## Übersicht

Jeder Spieler erhält beim Start (`/tycoon start`) einen kostenlosen Plot in der `tycoon`-Welt. Zusätzliche Plots können ab bestimmten Rängen gekauft werden. Bei einem Rangaufstieg wird **nur der Haupt-Plot** zurückgesetzt — zusätzliche Plots bleiben erhalten.

---

## Plot-Welten

| Welt | Größe | Biom | Gamemode | Kosten | Schematic |
|------|-------|------|----------|--------|-----------|
| `tycoon` | 32×32 | Basalt Deltas | Survival | Erster kostenlos, weitere $1M | `tycoon.schem` |
| ~~`town`~~ | — | — | — | **Lands** (Chunk-Claiming) — kein PlotSquared mehr | — |
| `freebuild` | 64×64 | Lush Caves | Creative | Kostenlos | Keine |

> **Hinweis:** Die `town`-Welt wird nicht mehr von PlotSquared verwaltet. Land-Claiming in der
> Survival-Welt (`world`, `world_nether`, `world_the_end`, `town`) läuft jetzt über das Plugin
> **Lands** (Chunk-basiert, Griefing-Schutz, Nationen). Konfiguration: [`survival/plugins/Lands/`](../../survival/plugins/Lands/).

---

## Plot-Limits pro Rang

Plots werden über die PlotSquared-Permission `plots.plot.<anzahl>` gesteuert. Höhere Ränge erhalten schrittweise mehr Plots.

| Rang-Bereich | Ränge | Max. Plots | Permission |
|-------------|-------|------------|------------|
| Tier 1–5 | Erde → Kupfer | 1 | `plots.plot.1` |
| Tier 6–10 | Gold → Diamant | 2 | `plots.plot.2` |
| Tier 11–15 | Obsidian → Diorit | 3 | `plots.plot.3` |
| Tier 16–20 | Andesit → Purpur | 4 | `plots.plot.4` |
| Tier 21–25 | Endstein → Bedrock | 5 | `plots.plot.5` |
| Prestige 1–4 | Bronze → Platin | 6 | `plots.plot.6` |
| Prestige 5–7 | Smaragd → Rubin | 7 | `plots.plot.7` |
| Prestige 8–10 | Amethyst → Legende | 8 | `plots.plot.8` |

> **Hinweis:** Diese Werte sind per LuckPerms-Gruppenberechtigungen konfiguriert. Änderungen in `tycoon_setup.sk` vornehmen.

---

## Plot-Merging

Spieler können benachbarte Plots zusammenführen, um eine größere Baufläche zu erhalten.

| Eigenschaft | Wert |
|-------------|------|
| **Merge-Kosten** | $5.000.000 pro Merge |
| **Auto-Merge** | Aktiviert (automatisch bei benachbarten Plots desselben Spielers) |
| **Max. Merge-Größe** | 4 Plots (konfigurierbar via `max-auto-area`) |
| **Befehl** | `/plot merge` |

### Merge-Verhalten

- Nur eigene, benachbarte Plots können gemergt werden
- Die Straße zwischen Plots wird entfernt
- Gemergte Plots teilen Flags und Permissions
- Der Merge bleibt auch nach Rankup bestehen (bei zusätzlichen Plots)

---

## Reset-Verhalten bei Rankup

### Was wird zurückgesetzt?

| Element | Reset? | Details |
|---------|--------|---------|
| Haupt-Plot (Slot 1) | ✅ Ja | Clear + Schematic paste |
| Zusätzliche Plots | ❌ Nein | Bleiben unverändert |
| Generatoren | ✅ Ja | Alle Generatoren des Spielers werden entfernt |
| Inventar | ✅ Ja | Vollständig geleert |
| Kontostand | ✅ Ja | Auf $0 gesetzt |
| Rang | ✅ Ja | Auf neuen Rang gesetzt |
| Prestige-Level | ❌ Nein | Bleibt erhalten |
| Achievements | ❌ Nein | Bleiben erhalten |
| Jobs | ❌ Nein | Bleiben erhalten |
| Daily-Streak | ❌ Nein | Bleibt erhalten |

### Reset-Ablauf (technisch)

```
/tycoon_perform_rankup <spieler> <zielrang> <generator>
    │
    ├─ 1. Spieler einfrieren (Slowness 255 + Jump Boost 200 + Blindness)
    ├─ 2. Inventar leeren + Geld auf 0
    ├─ 3. Teleport zu plot home
    ├─ 4. Generatoren entfernen (gens removegenerators)
    ├─ 5. Plot Clear + Confirm
    ├─ 6. Tycoon-Schematic einfügen
    ├─ 7. Rang setzen (LuckPerms)
    └─ 8. Kit geben (Generator + Sell Wand + Collector + Clock)
```

---

## Plot-Konfiguration (PlotSquared)

### worlds.yml — Tycoon-Welt

```yaml
tycoon:
  plot:
    size: 32              # 32×32 Blöcke pro Plot
    height: 64            # Boden auf Y=64
    biome: basalt_deltas
    filling: deepslate    # Füllung unter dem Boden
    floor: polished_andesite
    auto_merge: true      # Automatisches Merging aktiviert
    bedrock: true         # Bedrock-Schicht unter dem Plot
    flags:
      drop-protection: true  # Items verschwinden nicht
  economy:
    use: true
    prices:
      merge: 5000000      # $5M pro Merge
      sell: 0             # Kein Rückverkauf
      claim: 1000000      # $1M pro zusätzlichem Plot
  natural_mob_spawning: false
  mob_spawner_spawning: false
  schematic:
    on_claim: true        # Schematic wird beim Claimen eingefügt
    file: tycoon
```

### settings.yml — Relevante Einstellungen

```yaml
claim:
  max-auto-area: 4        # Max Plots mit /plot auto <size>
limit:
  global: false           # Plot-Limit gilt pro Welt, nicht global
  max-plots: 127          # Max Permission-Range
backup:
  automatic-backups: true
  backup-limit: 3
  delete-on-unclaim: true
```

---

## Permissions (LuckPerms)

| Permission | Beschreibung |
|------------|-------------|
| `plots.plot.<n>` | Maximale Anzahl Plots in der Tycoon-Welt |
| `plots.delete` | Plot löschen (auf `false` gesetzt für Tycoon) |
| `plots.*` | Temporär gesetzt während Rankup-Reset (für `plot clear`) |
| `tycoon.started` | Spieler hat `/tycoon start` ausgeführt |
| `tycoon.unlock.<rang>` | Spieler hat bestimmten Rang freigeschaltet |
| `tycoon.rank.<n>` | Rang-Nummer (1–25) |

---

## Schematic

Die Tycoon-Schematic (`tycoon.schem`) wird bei jedem neuen Plot und bei jedem Rankup-Reset eingefügt.

- **Speicherort:** `plugins/PlotSquared/schematics/tycoon.schem`
- **Inhalt:** Flache Plattform aus poliertem Andesit mit Bedrock-Schicht
- **Wird eingefügt bei:**
  - `/tycoon start` (erster Plot via `/plot auto`)
  - Rankup (via `/plot schematic paste tycoon`)
  - Neuer zusätzlicher Plot (via PlotSquared `on_claim: true`)

---

## Wichtige Hinweise

1. **Plot-Löschung ist deaktiviert** (`plots.delete: false`) — Spieler können ihre Plots nicht unclaimen
2. **Economy ist aktiv** — Zusätzliche Plots und Merges kosten Geld
3. **Automatische Backups** — PlotSquared erstellt Backups vor destruktiven Befehlen
4. **Drop-Protection** — Items auf Tycoon-Plots verschwinden nicht (Flag `drop-protection: true`)

---

**Letzte Aktualisierung:** 2026-08-18
