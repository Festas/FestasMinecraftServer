# Tier-System - MinecraftMMO

Dokumentation des Item-Tier-Systems für das MMO-Netzwerk.

---

## Übersicht

Das Tier-System bestimmt die Seltenheit, Stärke und visuelle Darstellung von Items. Es wird über MMOItems in der Datei `item-tiers.yml` konfiguriert.

**Pfad:** `rpg/plugins/MMOItems/item-tiers.yml`

---

## Die 6 aktiven Tiers (+1 geplant)

| Tier | Deutsch | Farbcode | Farbe | Werte-Multiplikator |
|------|---------|----------|-------|---------------------|
| COMMON | Gewöhnlich | `&f` | Weiß | 1.0x |
| UNCOMMON | Ungewöhnlich | `&a` | Grün | 1.25x |
| RARE | Selten | `&9` | Blau | 1.5x |
| EPIC | Episch | `&5` | Lila | 2.0x |
| LEGENDARY | Legendär | `&6` | Gold | 3.0x |
| MYTHIC | Mythisch | `&d` | Pink | 5.0x |
| DIVINE | Göttlich | `&c` | Hellrot | _(geplant)_ |

> **Hinweis:** Der TRASH-Tier existiert auch als unterste Stufe für wertlose Items.

---

## Drop-Wahrscheinlichkeiten

| Tier | Generierungs-Chance |
|------|---------------------|
| COMMON | ~60% |
| UNCOMMON | ~15% |
| RARE | ~6% |
| EPIC | ~3% |
| LEGENDARY | ~1% |
| MYTHIC | ~0.1% |

> Diese Werte sind die Standard-Generierungschancen. Individuelle Mobs und Dungeons können eigene Wahrscheinlichkeiten haben.

---

## Tier-Eigenschaften

### Capacity-System

Jeder Tier hat eine **Kapazitätsformel**, die bestimmt, wie viele Stats ein Item haben kann:

```
Kapazität = base + (scale × item-level) ± spread
```

Höhere Tiers haben höhere Base-Werte und Scale-Faktoren.

### Dekonstruktion

Items können dekonstruiert werden, um Materialien zurückzugewinnen. Die Menge und Qualität der Materialien hängt vom Tier ab:

| Tier | Dekonstruktions-Ertrag |
|------|------------------------|
| COMMON | 1 Basis-Material |
| UNCOMMON | 1-2 Materialien |
| RARE | 2-3 Materialien |
| EPIC | 3-5 Materialien + Chance auf seltenes Material |
| LEGENDARY | 5-8 Materialien + garantiert seltenes Material |
| MYTHIC | 8-12 Materialien + einzigartige Essenz |

### Item-Glow

Höhere Tiers haben einen visuellen Glow-Effekt im Inventar und am Boden, um ihre Seltenheit zu signalisieren.

---

## Tier-Progression nach Level

| Level-Bereich | Typische Tiers |
|---------------|----------------|
| 1-15 | COMMON, UNCOMMON |
| 16-30 | UNCOMMON, RARE |
| 31-50 | RARE, EPIC |
| 51-75 | EPIC, LEGENDARY |
| 76-100 | LEGENDARY, MYTHIC |

---

## Siehe auch

- [Item-System Übersicht](README.md)
- [Item-Pipeline](PIPELINE.md)
- [Item-Templates](TEMPLATES.md)
- [Crafting-System](CRAFTING.md)

---

**Letzte Aktualisierung:** 2026-04-10
