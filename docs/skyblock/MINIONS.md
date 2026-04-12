# Minion-System - Skyblock-Server

JetsMinions Struktur und Templates.

---

## Minion-Typen

### Ressourcen-Minions
- **⛏ Miner** - Baut automatisch Blöcke vor sich ab (Erze, Steine)
- **🌾 Farmer** - Erntet und pflanzt Crops automatisch neu (Wheat, Carrots, Potatoes, etc.)
- **🪓 Lumberjack** - Fällt Bäume und pflanzt Setzlinge nach (alle Holzarten)
- **🎣 Fisher** - Fängt automatisch Fische aus nahen Wasserquellen

### Kampf- & Utility-Minions
- **⚔ Slayer** - Bekämpft automatisch feindliche Mobs und sammelt Drops
- **🧲 Collector** - Sammelt alle gedropten Items in seinem Radius ein
- **🍖 Feeder** - Heilt und füttert andere Minions in seiner Reichweite
- **💰 Seller** - Verkauft automatisch Items aus seiner verknüpften Kiste

---

## Minion-Gesundheit

Alle Minions verwenden ein Health-System statt eines Fuel-Systems:

- **Max HP:** 20 Gesundheitspunkte pro Minion
- **Aktionen reduzieren HP:** Nach einer bestimmten Anzahl Aktionen verliert der Minion 1 HP
- **Heilung per Essen:** Rechtsklick mit Nahrung heilt den Minion
- **Heilung per Geld:** 200 Coins für volle Heilung

### Futter-Übersicht

| Nahrung | Heilung |
|---------|---------|
| Brot / Apfel | 2 ❤ |
| Steak / Lachs / Hammel / Schwein / Hähnchen | 4 ❤ |
| Goldener Apfel | 10 ❤ |
| Verzauberter Goldener Apfel | 20 ❤ (volle Heilung) |

---

## Minion-Aktionsgeschwindigkeit

| Minion | Ticks pro Aktion | Sekunden |
|--------|-----------------|----------|
| Miner | 50 | 2,5s |
| Farmer | 60 | 3,0s |
| Fisher | 60 | 3,0s |
| Collector | 100 | 5,0s |
| Slayer | 140 | 7,0s |
| Lumberjack | 200 | 10,0s |
| Feeder | 300 | 15,0s |
| Seller | 300 | 15,0s |

---

## Minion-Placement

**Max Minions pro Spieler (Permission-basiert):**

| Rang | Max Minions | Permission |
|------|-------------|------------|
| Default | 3 | `minions.place.default` |
| VIP | 5 | `minions.place.vip` |
| God | 10 | `minions.place.god` |

**Verknüpfte Kisten:**
- Maximale Distanz: 30 Blöcke
- Link-Timeout: 30 Sekunden
- Feeder kann andere Minions aus verknüpfter Kiste füttern

**Deaktivierte Welten:**
- `world_the_end` (Minions nicht platzierbar)

**Offline-Verhalten:**
- Minions stoppen, wenn der Besitzer offline geht
- Funktionieren nur bei geladenem Chunk

---

## Empfohlene Setups

- **Early Game:** 1x Miner, 1x Farmer, 1x Collector
- **Mid Game:** 2x Miner, 1x Farmer, 1x Slayer, 1x Collector
- **Late Game:** Spezialisierung nach Bedarf + Feeder und Seller

---

## Siehe auch

- [Skyblock Übersicht](README.md)
- [Islands-System](ISLANDS.md)

---

**Letzte Aktualisierung:** 2026-04-12
