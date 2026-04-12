# Tycoon Gamemode — Vollständige Referenz

Der Tycoon-Gamemode ist das Herzstück des Survival-Servers. Spieler platzieren Generatoren auf ihrem Plot, sammeln produzierte Items und verkaufen diese, um im Rang aufzusteigen.

---

## Spielablauf

```
Spieler betritt Server
        │
        ▼
Tutorial (automatisch bei First-Join)
        │
        ▼
/tycoon start → Plot wird zugewiesen (kostenlos)
        │
        ▼
Rang: Erde → Tier-1-Generator + Sell Wand + Collector
        │
        ▼
Generatoren produzieren Items → Sell Wand verkauft aus Containern
        │
        ▼
Geld verdienen → /rankup → Plot-Reset → Neuer Rang + Neuer Generator
        │
        ▼
25 Ränge durchlaufen (Erde → Bedrock)
        │
        ▼
/prestige → Reset auf Erde mit permanentem Sell-Bonus
        │
        ▼
10 Prestige-Stufen (Bronze → Legende, +10% bis +200%)
```

---

## Ränge & Kosten

| # | Rang | Kosten | Generator |
|---|------|--------|-----------|
| 1 | Erde | — (Start) | tier1_1 |
| 2 | Stein | $5.000 | tier2_1 |
| 3 | Kohle | $25.000 | tier3_1 |
| 4 | Eisen | $75.000 | tier4_1 |
| 5 | Kupfer | $200.000 | tier5_1 |
| 6 | Gold | $500.000 | tier6_1 |
| 7 | Redstone | $1.200.000 | tier7_1 |
| 8 | Lapis | $3.000.000 | tier8_1 |
| 9 | Smaragd | $7.500.000 | tier9_1 |
| 10 | Diamant | $15.000.000 | tier10_1 |
| 11 | Obsidian | $35.000.000 | tier11_1 |
| 12 | Netherite | $75.000.000 | tier12_1 |
| 13 | Tuff | $150.000.000 | tier13_1 |
| 14 | Calcit | $300.000.000 | tier14_1 |
| 15 | Diorit | $600.000.000 | tier15_1 |
| 16 | Andesit | $1.000.000.000 | tier16_1 |
| 17 | Granit | $2.000.000.000 | tier17_1 |
| 18 | Basalt | $4.000.000.000 | tier18_1 |
| 19 | Schwarzstein | $8.000.000.000 | tier19_1 |
| 20 | Purpur | $15.000.000.000 | tier20_1 |
| 21 | Endstein | $25.000.000.000 | tier21_1 |
| 22 | Prismarin | $40.000.000.000 | tier22_1 |
| 23 | Seelenerde | $65.000.000.000 | tier23_1 |
| 24 | Magma | $100.000.000.000 | tier24_1 |
| 25 | Bedrock | $175.000.000.000 | tier25_1 |

---

## Was passiert beim Rankup?

Bei jedem Rangaufstieg wird ein **vollständiger Reset** durchgeführt:

1. Spieler wird eingefroren (Slowness + Jump Boost + Blindness)
2. Inventar wird geleert
3. Kontostand wird auf $0 gesetzt
4. Spieler wird zum Plot-Home teleportiert
5. Alle Generatoren werden entfernt (`gens removegenerators`)
6. Plot wird geleert (`plot clear`)
7. Tycoon-Schematic wird eingefügt (`plot schematic paste tycoon`)
8. Neuer Rang wird gesetzt (LuckPerms)
9. Neues Kit wird gegeben (Generator + Sell Wand + Collector)

> **Hinweis:** Der Rankup-Reset betrifft nur den Haupt-Plot. Siehe [PLOTS.md](PLOTS.md) für Details zum Multi-Plot-System.

---

## Item-Preise (Sell Wand)

Jeder Rang schaltet neue Items frei. Die Sell Wand verkauft Items aus Containern zum festgelegten Preis.

| Tier | Item 1 | Preis | Item 2 | Preis |
|------|--------|-------|--------|-------|
| 1 | Dirt | $5 | Coarse Dirt | $10 |
| 2 | Stone | $10 | Stone Bricks | $20 |
| 3 | Coal | $20 | Coal Block | $180 |
| 4 | Iron Ingot | $50 | Iron Block | $450 |
| 5 | Copper Ingot | $80 | Copper Block | $720 |
| 6 | Gold Ingot | $120 | Gold Block | $1.080 |
| 7 | Redstone | $200 | Redstone Block | $1.800 |
| 8 | Lapis Lazuli | $450 | Lapis Block | $4.050 |
| 9 | Emerald | $800 | Emerald Block | $7.200 |
| 10 | Diamond | $1.500 | Diamond Block | $13.500 |
| 11 | Obsidian | $2.500 | Crying Obsidian | $5.000 |
| 12 | Netherite Scrap | $5.000 | Netherite Ingot | $20.000 |
| 13 | Tuff | $8.000 | Polished Tuff | $16.000 |
| 14 | Calcite | $12.000 | Amethyst Shard | $20.000 |
| 15 | Diorite | $18.000 | Polished Diorite | $36.000 |
| 16 | Andesite | $25.000 | Polished Andesite | $50.000 |
| 17 | Granite | $40.000 | Polished Granite | $80.000 |
| 18 | Basalt | $60.000 | Polished Basalt | $120.000 |
| 19 | Blackstone | $100.000 | Polished Blackstone | $200.000 |
| 20 | Purpur Block | $200.000 | Purpur Pillar | $400.000 |
| 21 | End Stone | $400.000 | End Stone Bricks | $800.000 |
| 22 | Prismarine Shard | $800.000 | Prismarine Crystals | $1.600.000 |
| 23 | Soul Soil | $1.500.000 | Soul Lantern | $3.000.000 |
| 24 | Magma Cream | $3.000.000 | Magma Block | $6.000.000 |
| 25 | Bedrock | $6.000.000 | Beacon | $12.000.000 |

**Prestige-Multiplikator:** Der Sell-Preis wird mit dem Prestige-Bonus multipliziert (z.B. +50% bei Platin).

---

## Prestige-System

Nach Erreichen von Rang 25 (Bedrock) können Spieler prestigen. Prestige setzt alles zurück (Rang, Geld, Plot) und gewährt einen permanenten Sell-Bonus.

| # | Name | Kosten | Sell-Bonus |
|---|------|--------|------------|
| 1 | Bronze | $500 Mrd. | +10% |
| 2 | Silber | $1 Bio. | +20% |
| 3 | Gold | $2,5 Bio. | +35% |
| 4 | Platin | $5 Bio. | +50% |
| 5 | Smaragd | $10 Bio. | +70% |
| 6 | Saphir | $25 Bio. | +90% |
| 7 | Rubin | $50 Bio. | +115% |
| 8 | Amethyst | $100 Bio. | +140% |
| 9 | Diamant | $250 Bio. | +170% |
| 10 | Legende | $500 Bio. | +200% |

---

## Kern-Systeme

### Sell Wand (Markt-Zepter)
- **Item:** Goldene Hacke mit Verzauberung
- **Nutzung:** Rechtsklick auf Kiste/Fass/Shulker → Alle verkaufbaren Items werden verkauft
- **Schutz:** Baurechte-Prüfung (nur auf eigenem Plot)
- **Prestige:** Automatische Multiplikator-Anwendung

### Chunk Collector
- **Item:** Barrel (über Hopper platziert)
- **Funktion:** Sammelt dropped Items im 20-Block-Radius alle 3 Sekunden
- **Optimierung:** Partikel nur für Spieler im 50-Block-Radius

### Börse (Dynamischer Markt)
- **Mechanik:** Preise ändern sich basierend auf Angebot & Nachfrage
- **Integration:** Verkäufe über Sell Wand werden getrackt
- **Befehl:** `/boerse`

### Casino/Glücksspiel
- **Tägliches Verlustlimit:** Ja
- **Befehl:** `/gamble`

### Boss-Events
- **Typ:** Nitwit-Boss-Encounters (zufällige Spawns)
- **Belohnungen:** Geld + Achievements

### Tägliche Belohnungen
- **Streak-System:** Belohnungen skalieren mit Streak-Länge
- **Reset:** Streak bricht ab wenn >48h zwischen Claims
- **Befehl:** `/daily`

### Achievements
- 23 Achievements in Kategorien: Verkäufe, Ränge, Casino, Streaks, Prestige, Bosse, Kontostand
- Jedes Achievement gibt Geld-Belohnungen

---

## Wichtige Befehle

| Befehl | Beschreibung |
|--------|-------------|
| `/tycoon start` | Tycoon starten (Plot + Erde-Rang) |
| `/tycoon` / Rechtsklick Clock | Tycoon Manager GUI öffnen |
| `/rankup` | Rang aufsteigen (mit Reset) |
| `/prestige` | Prestige-Info anzeigen |
| `/prestige confirm` | Prestige durchführen |
| `/prestige info` | Prestige-Status anzeigen |
| `/daily` | Tägliche Belohnung abholen |
| `/achievements` | Achievements anzeigen |
| `/boerse` | Dynamische Marktpreise |
| `/shop` | Shop öffnen |
| `/jobs` | Jobs verwalten |
| `/plot home tycoon` | Zum Plot teleportieren |
| `/plot auto` | Nächsten freien Plot claimen |
| `/plot merge` | Benachbarte Plots zusammenführen |
| `/gm` | Globaler Marktplatz |
| `/tutorial` | Tutorial wiederholen |

---

## GUI-Menüs (DeluxeMenus)

| Menü | Datei | Inhalt |
|------|-------|--------|
| Tycoon Manager | `tycoon_main.yml` | Generatoren, Verkaufen, Rankup, Plot, Warps, Kosmetik, Prestige, Achievements, Börse |
| Warps (Tycoon) | `tycoon_warps.yml` | Warp-Punkte in der Tycoon-Welt |
| Warps (Survival) | `tycoon_warps_survival.yml` | Warp-Punkte in der Survival-Welt |
| Kosmetik | `cosmetics.yml` | Partikel, Chat-Farben, Verkleidungen |

---

## Sicherheit

- **Spielername-Validierung:** Regex `^[a-zA-Z0-9_\.]{1,32}$` gegen Command Injection (Geyser/Floodgate)
- **Baurechte-Prüfung:** Sell Wand prüft `player cannot build at event-block`
- **Plot-Löschung gesperrt:** `plots.delete` ist auf `false` gesetzt
- **Anti-Cheat:** ⚠️ **Noch nicht installiert** — Vulcan Premium empfohlen

---

**Letzte Aktualisierung:** 2026-04-11
