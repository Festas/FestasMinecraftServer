# Island-System - Skyblock-Server

SuperiorSkyblock2 Konfiguration und Island-Templates.

---

## Island-Basics

### Island-Typen
- **Standard Island** - Basis-Template
- **Custom Islands** - Spezielle Designs
- **Nether Island** - Nether-Zugang
- **End Island** - End-Zugang

### Island-Features
- **Island-Level:** Basierend auf platzierten Blöcken
- **Island-Bank:** Gemeinsame Währung
- **Island-Upgrades:** Erweiterungen freischalten
- **Island-Warps:** Besucher einladen

---

## Island-Upgrades

| Upgrade | Level | Kosten | Effekt |
|---------|-------|--------|--------|
| Größe | 1-10 | Steigend | +10x10 Blöcke pro Level |
| Mob-Limit | 1-10 | Steigend | +10 Mobs pro Level |
| Hopper-Limit | 1-10 | Steigend | +5 Hopper pro Level |
| Member-Slots | 1-5 | Steigend | +2 Member pro Level |

---

## Island-Levels

**Berechnung:**
- Jeder Block hat einen Wert
- Rare Blöcke = höherer Wert
- Level = Summe aller Block-Werte

**Milestone-Belohnungen:**

| Island-Level | Belohnung |
|--------------|-----------|
| 100 | Uncommon Kiste |
| 500 | Rare Kiste |
| 1000 | Epic Kiste |
| 5000 | Legendary Kiste |

---

## Prestige-System

Das Prestige-System ist über das Skript `prestige.sk` implementiert und bietet erweiterte Progression nach dem Erreichen des Maximallevels.

**Voraussetzung:** Level 100 (Minecraft XP Level)

**Prestige-Stufen:**

| Prestige | Titel | Farbe | Passive Buffs |
|----------|-------|-------|---------------|
| ✦ 1 | Novize | Gelb | — |
| ✦✦ 2 | Lehrling | Gelb | Speed I |
| ✦✦✦ 3 | Geselle | Gold | Speed I |
| ✦✦✦✦ 4 | Experte | Gold | Speed I, Haste I |
| ✦✦✦✦✦ 5 | Meister | Rot | Speed I, Haste I |
| ✦✦✦✦✦✦ 6 | Großmeister | Rot | Speed I, Haste I, Resistance I |
| ✦✦✦✦✦✦✦ 7 | Champion | Lila | Speed I, Haste I, Resistance I |
| ✦✦✦✦✦✦✦✦ 8 | Legende | Lila | Speed I, Haste I, Resistance I, Strength I |
| ✦✦✦✦✦✦✦✦✦ 9 | Mythisch | Rosa | Speed I, Haste I, Resistance I, Strength I |
| ✦✦✦✦✦✦✦✦✦✦ 10 | Unsterblich | Dunkelrot | Speed I, Haste I, Resistance I, Strength I, Regeneration I |

**Mechanik:**
- XP-Level wird auf 0 zurückgesetzt
- Items und Währung bleiben erhalten
- +5% Stat-Bonus pro Prestige-Stufe
- Prestige-Sterne werden im Chat angezeigt
- Bestenliste via `/prestige top`

**Befehle:**
- `/prestige info` - Status anzeigen
- `/prestige up` - Prestige durchführen
- `/prestige top` - Bestenliste

---

## Siehe auch

- [Skyblock Übersicht](README.md)
- [Minions-System](MINIONS.md)

---

**Letzte Aktualisierung:** 2026-04-12
