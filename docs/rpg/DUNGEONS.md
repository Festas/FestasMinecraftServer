# Dungeon-System - RPG-Server

MythicDungeons Struktur, Templates und Dungeon-Erstellung.

---

## MythicDungeons Übersicht

**MythicDungeons** erstellt instanzierte Dungeons mit:
- Separate Instanzen für jede Gruppe
- Respawn-Timers und Loot-Locks
- Schwierigkeitsgrade
- Boss-Mechaniken
- Exklusive Belohnungen

---

## Dungeon-Typen

### 1. Mini-Dungeons (Level 10-30)
- **Spieler:** 1-3
- **Dauer:** 10-20 Minuten
- **Bosse:** 1
- **Schwierigkeit:** Einfach-Mittel

### 2. Standard-Dungeons (Level 30-60)
- **Spieler:** 3-5
- **Dauer:** 30-45 Minuten
- **Bosse:** 2-3
- **Schwierigkeit:** Mittel

### 3. Raid-Dungeons (Level 60-100)
- **Spieler:** 5-10
- **Dauer:** 60-90 Minuten
- **Bosse:** 3-5
- **Schwierigkeit:** Schwer-Extrem

---

## Dungeon-Template

### Dungeon: [NAME]

#### Basis-Informationen
- **Dungeon-ID:** <!-- z.B. bandit_cave_01 -->
- **Dungeon-Name:** <!-- Display-Name -->
- **Level-Range:** <!-- z.B. 10-15 -->
- **Spieler:** <!-- Min-Max -->
- **Geschätzte Dauer:** <!-- Minuten -->
- **Schwierigkeitsgrade:** <!-- Normal / Heroic / Mythic -->
- **Eingangs-Location:** <!-- Koordinaten in Welt -->

#### Thema & Story

**Thema:** <!-- Kurze Beschreibung -->

**Story:**
<!-- TODO: Hintergrund des Dungeons -->

**Lore:**
<!-- TODO: Warum existiert dieser Dungeon? -->

#### Dungeon-Layout

**Struktur:**
```
Eingang → Raum 1 (Trash) → Boss 1 →
Raum 2 (Trash) → Boss 2 →
Finale Arena → End-Boss → Ausgang
```

**Räume:**
1. <!-- Raum 1 Beschreibung -->
2. <!-- Raum 2 Beschreibung -->
3. <!-- Boss-Arena Beschreibung -->

**Größe:** <!-- Blöcke (X x Y x Z) -->

#### Mobs & Encounters

**Trash-Mobs:**

| Mob-Name | Level | HP | Schaden | Anzahl | Special Abilities |
|----------|-------|-----|---------|--------|-------------------|
| <!-- Mob 1 --> | <!-- X --> | <!-- HP --> | <!-- DMG --> | <!-- #-# --> | <!-- Abilities --> |

**Bosse:**

##### Boss 1: [NAME]
- **Level:** <!-- X -->
- **HP:** <!-- XX -->
- **Schaden:** <!-- XX -->
- **Location:** <!-- Raum/Arena -->
- **Mechaniken:**
  - <!-- Mechanik 1 -->
  - <!-- Mechanik 2 -->
  - <!-- Mechanik 3 -->
- **Enrage-Timer:** <!-- Minuten -->
- **Loot:** → Siehe Loot-Table

##### Boss 2: [NAME]
<!-- Gleiche Struktur -->

#### Loot-Tables

**Trash-Mob-Drops:**
- <!-- Item 1 --> - Drop-Chance: <!-- X% -->

**Boss-Drops:**

##### Boss 1 Loot
| Item | Rarity | Drop-Chance |
|------|--------|-------------|
| <!-- Item 1 --> | <!-- Rare --> | <!-- 10% --> |
| <!-- Item 2 --> | <!-- Uncommon --> | <!-- 30% --> |

##### End-Boss Loot
| Item | Rarity | Drop-Chance | Schwierigkeitsgrad |
|------|--------|-------------|--------------------|
| <!-- Item 1 --> | <!-- Epic --> | <!-- 5% --> | <!-- Normal+ --> |
| <!-- Item 2 --> | <!-- Legendary --> | <!-- 1% --> | <!-- Heroic+ --> |

**Garantierte Belohnungen:**
- <!-- XP: XX -->
- <!-- Coins: XX -->
- <!-- Item: XX (1x garantiert) -->

#### Schwierigkeitsgrade

##### Normal
- **Level:** <!-- XX -->
- **Mob-HP-Multiplikator:** 100%
- **Mob-Schaden-Multiplikator:** 100%
- **Loot-Bonus:** Keine
- **Lockout:** Einmal pro Tag

##### Heroic
- **Level:** <!-- XX +5 -->
- **Mob-HP-Multiplikator:** 150%
- **Mob-Schaden-Multiplikator:** 130%
- **Loot-Bonus:** +25% besserer Loot
- **Lockout:** Einmal pro Tag

##### Mythic (Optional)
- **Level:** <!-- XX +10 -->
- **Mob-HP-Multiplikator:** 200%
- **Mob-Schaden-Multiplikator:** 160%
- **Loot-Bonus:** +50% besserer Loot, exclusive Items
- **Lockout:** Einmal pro Woche

#### Voraussetzungen

**Zugang:**
- Level: <!-- Min Level -->
- Quest: <!-- Quest-ID falls benötigt -->
- Schlüssel/Item: <!-- Falls benötigt -->

**Empfohlene Gruppen-Zusammensetzung:**
- 1x Tank (Krieger)
- 1x Healer (Schamane)
- 3x DPS (beliebig)

#### Design-Notizen

**Besonderheiten:**
<!-- TODO: Was macht diesen Dungeon einzigartig? -->

**Boss-Mechaniken-Details:**
<!-- TODO: Detaillierte Erklärungen komplexer Mechaniken -->

**Bekannte Probleme:**
- [ ] <!-- Problem 1 -->

**TODO:**
- [ ] <!-- Offener Punkt 1 -->

---

## Beispiel-Dungeon: Banditenhöhle

### Dungeon: Banditenhöhle

#### Basis-Informationen
- **Dungeon-ID:** bandit_cave_01
- **Dungeon-Name:** "Banditenhöhle"
- **Level-Range:** 10-15
- **Spieler:** 1-3 (Solo möglich)
- **Geschätzte Dauer:** 15 Minuten
- **Schwierigkeitsgrade:** Normal, Heroic
- **Eingangs-Location:** Starter-Tal (X:300, Z:250)

#### Thema & Story

**Thema:** Versteck von Banditen in einer Höhle

**Story:**
Die Banditen haben sich in einer verlassenen Höhle verschanzt. Der Spieler muss eindringen und den Anführer besiegen.

**Lore:**
Diese Höhle war einst eine Mine, wurde aber aufgegeben als die Banditen sie übernahmen.

#### Dungeon-Layout

**Struktur:**
```
Eingang → Haupthalle (Trash) → Schlafquartiere (Trash) →
Schatzkammer → Boss-Arena → Ausgang
```

**Räume:**
1. Haupthalle - Große Höhle mit Lagerfeuern
2. Schlafquartiere - Enge Gänge mit Schlafplätzen
3. Boss-Arena - Große Arena mit erhöhter Plattform

**Größe:** 100 x 50 x 100 Blöcke

#### Mobs & Encounters

**Trash-Mobs:**

| Mob-Name | Level | HP | Schaden | Anzahl | Special Abilities |
|----------|-------|-----|---------|--------|-------------------|
| Bandit | 10 | 100 | 15 | 15-20 | Keine |
| Banditen-Bogenschütze | 11 | 80 | 12 | 5-8 | Fernkampf |
| Banditen-Wache | 12 | 150 | 20 | 3-5 | Schild (Block) |

**Bosse:**

##### Boss 1: Banditen-Anführer "Rotbart"
- **Level:** 15
- **HP:** 1000
- **Schaden:** 35
- **Location:** Boss-Arena
- **Mechaniken:**
  - Ruft 3 Banditen bei 50% HP
  - "Wilder Ansturm" - Charge auf zufälligen Spieler
  - "Flammen-Wurf" - Wirft brennende Fackeln (AoE)
- **Enrage-Timer:** 10 Minuten
- **Loot:** → Siehe Loot-Table

#### Loot-Tables

**Trash-Mob-Drops:**
- Banditen-Schwert (Common) - 5%
- Leder-Rüstungsteile (Common) - 10%
- Coins - 100%

**Boss-Drops:**

##### Rotbart Loot (Normal)
| Item | Rarity | Drop-Chance |
|------|--------|-------------|
| Rotbarts Klinge | Rare | 15% |
| Banditen-Anführer-Rüstung | Rare | 10% |
| Goldene Halskette | Uncommon | 30% |

##### Rotbart Loot (Heroic)
| Item | Rarity | Drop-Chance |
|------|--------|-------------|
| Rotbarts Legendäre Klinge | Epic | 8% |
| Verstärkte Banditen-Rüstung | Rare | 20% |
| Rotbarts Ring | Rare | 15% |

**Garantierte Belohnungen:**
- XP: 800
- Coins: 150
- Heiltrank (3x garantiert)

#### Schwierigkeitsgrade

##### Normal
- **Level:** 10-15
- **Mob-HP-Multiplikator:** 100%
- **Mob-Schaden-Multiplikator:** 100%
- **Loot-Bonus:** Keine
- **Lockout:** Einmal pro Tag

##### Heroic
- **Level:** 15-20
- **Mob-HP-Multiplikator:** 150%
- **Mob-Schaden-Multiplikator:** 130%
- **Loot-Bonus:** +25% Drop-Chance, bessere Items
- **Lockout:** Einmal pro Tag

#### Voraussetzungen

**Zugang:**
- Level: 10+
- Quest: "Die Banditen-Bedrohung" abgeschlossen
- Schlüssel/Item: Keine

**Empfohlene Gruppen-Zusammensetzung:**
- Solo: Jede Klasse (mit Heilung/Tränken)
- Duo: Tank + DPS oder Healer + DPS
- Trio: Tank + Healer + DPS

---

## Design-Richtlinien

### Dungeon-Länge
- Mini: 10-20 min
- Standard: 30-45 min
- Raid: 60-90 min

### Boss-Mechaniken
- Jeder Boss sollte 2-3 einzigartige Mechaniken haben
- Mechaniken sollten progressiv schwerer werden
- Enrage-Timer verhindert zu lange Kämpfe

### Loot-Balance
- Trash-Mobs: Meist Common, wenig Uncommon
- Bosse: Rare-Epic Items
- End-Boss: Epic-Legendary möglich (niedrige Drop-Chance)

---

## Siehe auch

- [RPG-Server Übersicht](README.md)
- [Mob-System](MOBS.md)
- [Item-Tiers](../items/TIERS.md)

---

**Letzte Aktualisierung:** 2026-04-10

**Status:** 🗄️ Archiv — RPG-Spielmodus eingestellt (nur noch Referenz)
