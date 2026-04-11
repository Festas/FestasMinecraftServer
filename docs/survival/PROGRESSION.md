# Progression — Tycoon Server

Dokumentation des gesamten Progressions-Systems: Ränge, Prestige, Economy-Fluss, und Verbesserungsplan.

---

## Progressions-Übersicht

```
/tycoon start (Erde)
       │
       ▼
  Ränge 1–25 (Erde → Bedrock)
  Jeder Rankup: $-Kosten + vollständiger Reset
       │
       ▼
  Prestige 1–10 (Bronze → Legende)
  Jeder Prestige: Zurück auf Erde + permanenter Sell-Bonus
       │
       ▼
  Endgame: Prestige 10 (Legende) mit +200% Sell-Bonus
```

---

## Economy-Fluss

### Geldquellen (Inflows)

| Quelle | Beschreibung | Einfluss |
|--------|-------------|----------|
| **Sell Wand** | Items aus Containern verkaufen | Primäre Einnahme |
| **ShopGUI+ Sell** | Items aus Inventar verkaufen | Sekundär |
| **Jobs** | 13 Berufe (Mining, Farming, etc.) | Zusätzlich |
| **Tägliche Belohnungen** | Streak-basiert ($500–$1M+) | Klein |
| **Achievements** | 23 einmalige Belohnungen ($500–$500M) | Einmalig |
| **Boss-Events** | Nitwit-Boss Drops | Gelegentlich |
| **Börse** | Dynamische Preise (können höher sein) | Variabel |

### Geldsenken (Outflows)

| Senke | Beschreibung | Betrag |
|-------|-------------|--------|
| **Rankup** | 25 Stufen, Geld wird auf $0 gesetzt | $5K–$175Mrd. |
| **Prestige** | 10 Stufen, vollständiger Reset | $500Mrd.–$500Bio. |
| **Zusätzliche Plots** | Plot claimen | $1M pro Plot |
| **Plot-Merge** | Benachbarte Plots zusammenführen | $5M pro Merge |
| **Generator-Kauf** | Neue Generatoren kaufen | Variabel |
| **Collector-Kauf** | Chunk Collector kaufen | Variabel |
| **Casino/Glücksspiel** | Tägliches Verlustlimit | Variabel |

### Inflations-Kontrolle

| Mechanismus | Details |
|-------------|---------|
| **Rankup-Reset** | Kontostand wird bei jedem Rankup auf $0 gesetzt |
| **Prestige-Reset** | Vollständiger Reset (Rang, Geld, Plot) |
| **Economy Monitor** | Warnung ab $100Mrd. Durchschnitt pro Spieler |
| **Große Transaktionen** | Alert ab $50M (Admin-Benachrichtigung) |
| **Plot-Kosten** | Zusätzliche Plots und Merges als Geldsenke |

---

## Rang-Permissions

Jeder Rang erbt die Permissions aller vorherigen Ränge.

| Permission | Beschreibung | Vergeben ab |
|------------|-------------|------------|
| `tycoon.unlock.<rang>` | Rang-Freischaltung | Jeweiliger Rang |
| `tycoon.rank.<n>` | Rang-Nummer (1–25) | Jeweiliger Rang |
| `shopguiplus.price-modifier.p<n>` | Shop-Preismodifikator | Jeweiliger Rang |
| `plots.plot.<n>` | Plot-Limit | Gestaffelt (siehe PLOTS.md) |
| `plots.delete` | Plot löschen (`false`) | Erde (Basis) |
| `nextgens.shop` | Generator-Shop Zugang | Erde (Basis) |
| `tycoon.started` | Hat gestartet | Nach `/tycoon start` |

---

## Prestige-Integration

Der Prestige-Multiplikator wird automatisch auf alle Sell-Wand-Verkäufe angewandt:

```
Endpreis = Basis-Preis × (1 + Prestige-Bonus / 100)
```

**Beispiel:** Ein Diamond (Basis $1.500) mit Prestige Gold (+35%):
```
$1.500 × 1.35 = $2.025
```

Der Multiplikator wird in `{prestige.level.%uuid%}` gespeichert und in `tycoon_logic.sk` abgerufen.

---

## Verbesserungsplan

### Problem: Nur ein Plot + Reset bei jedem Rankup

Das aktuelle System hat ein Kernproblem: Spieler haben nur **einen Plot**, der bei **jedem der 24 Rankups** vollständig zurückgesetzt wird. Das führt zu:

1. **Frustration:** Spieler verlieren jeglichen Baufortschritt bei jedem Rankup
2. **Fehlender Anreiz für Kreativität:** Warum bauen, wenn alles gelöscht wird?
3. **Einheitliches Spielerlebnis:** Jeder Plot sieht gleich aus (Flat-Schematic)
4. **Kein Langzeit-Investment:** Kein Grund, den Plot zu verschönern

### Lösung: Multi-Plot-System mit selektivem Reset

#### Kernänderungen

1. **Progressive Plot-Limits:** Mehr Plots mit höherem Rang (1→8 Plots)
2. **Selektiver Reset:** Nur der Haupt-Plot wird beim Rankup zurückgesetzt
3. **Zusätzliche Plots bleiben:** Generatoren auf Extra-Plots überleben den Reset
4. **Plot-Merging aktiv:** Spieler können Plots zu größeren Flächen zusammenführen

#### Implementierte Änderungen

- **`tycoon_setup.sk`:** Progressive `plots.plot.<n>` Permissions pro Rang-Gruppe
- **`tycoon_logic.sk`:** Rankup-Reset nur für Haupt-Plot (kein Entfernen aller Generatoren)
- **PlotSquared `worlds.yml`:** Economy aktiviert mit Merge/Claim-Preisen
- **`tycoon_tutorial.sk`:** Tutorial erwähnt Multi-Plot-System
- **`tycoon_main.yml`:** GUI enthält Plot-Management-Eintrag

#### Wirtschaftliche Balance

Die zusätzlichen Plots dienen als **Money Sink**:
- $1M pro zusätzlichem Plot (Claim)
- $5M pro Plot-Merge
- Spieler müssen abwägen: Sofort rankuppen oder erst Plots kaufen

Dies schafft eine strategische Entscheidung und verlangsamt die Inflation.

---

## Offene Punkte

- [ ] Anti-Cheat installieren (Vulcan Premium) — kritisch für Economy-Schutz
- [ ] Voting-System (NuVotifier) für zusätzliche Einnahmen
- [ ] Crate/Key-System für zusätzliche Belohnungen
- [ ] Erweiterte Boss-Events (mehr als nur Nitwit)
- [ ] Saisonale Events mit besonderen Belohnungen

---

**Letzte Aktualisierung:** 2026-04-11
