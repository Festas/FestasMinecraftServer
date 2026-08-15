# Klassen-System - MinecraftMMO

Übersicht über das umfassende Klassen-System für die MMO-Server (RPG & Skyblock).

> **⚠️ Archiv (MMO — Skyblock & RPG):** Dieses System gehört zu den auslaufenden MMO-Servern und wird mit deren Abschaltung ersetzt. Aktiver Fokus (26.2): **Lobby** und **Survival**. Diese Doku wird nur noch als Referenz geführt.

---

## Übersicht

Das MinecraftMMO Netzwerk bietet ein tiefgreifendes Klassen-System mit **6 einzigartigen Klassen**, die auf beiden MMO-Servern (RPG und Skyblock) verfügbar sind. Jede Klasse hat ihre eigene Identität, Spielweise und Rolle im Gameplay.

### Die 6 Klassen

1. **[Krieger](KRIEGER.md)** - Tank/Melee DPS, Nahkampf-Spezialist
2. **[Magier](MAGIER.md)** - Ranged Magic DPS, Elementar-Meister
3. **[Assassine](ASSASSINE.md)** - Schneller Melee DPS, Kritischer Schadens-Dealer
4. **[Bogenschütze](BOGENSCHUETZE.md)** - Ranged Physical DPS, Fernkampf-Experte
5. **[Schamane](SCHAMANE.md)** - Support/Healer, Gruppen-Unterstützung
6. **[Beschwörer](BESCHWOERER.md)** - Summoner/Pet-Class, Minion-Meister

---

## Klassen-Archetypen

### Damage Dealer (DPS)
- **Krieger** - Hoher Nahkampf-Schaden, mittlere Verteidigung
- **Magier** - Hoher Fernkampf-Magischer-Schaden, niedrige Verteidigung
- **Assassine** - Sehr hoher Burst-Schaden, niedrige Verteidigung
- **Bogenschütze** - Hoher Fernkampf-Physischer-Schaden, mittlere Verteidigung

### Support
- **Schamane** - Heilung und Buffs für Gruppe, mittlere Verteidigung

### Hybrid
- **Beschwörer** - Mittlerer Schaden durch Beschwörungen, Crowd Control, mittlere Verteidigung

---

## Grundmechaniken

### Klassenwahl
- Jeder Spieler wählt beim ersten Join eine Klasse
- Klassen können gewechselt werden (Cooldown: 7 Tage)
- Beim Klassenwechsel bleiben Level und XP erhalten
- Skills müssen neu geskillt werden

**Befehle:**
```
/class choose <Klassenname>     # Klasse wählen/wechseln
/class info                     # Aktuelle Klassen-Info
/class skills                   # Skill-Tree öffnen
```

### Level-System
- **Max Level:** 100
- **XP-Quellen:** Mobs töten, Quests abschließen, Dungeons, Mining, Farming
- **XP-Kurve:** Exponentiell (späte Level deutlich länger)
- **Level-Milestone:** Alle 10 Level gibt es besondere Belohnungen

### Skill-System
- **Skill-Punkte:** 1 Punkt pro Level (Max 100 Punkte)
- **Skill-Kategorien:**
  - **Passive Skills** - Permanente Stat-Boni
  - **Active Skills** - Aktivierbare Fähigkeiten (Cooldown)
  - **Ultimate Skill** - Mächtige Fähigkeit (langer Cooldown, freischaltbar ab Level 50)

### Stats-System

#### Primär-Stats
Jede Klasse hat unterschiedliche Primär-Stats, die ihre Rolle definieren:

- **Stärke (STR)** - Erhöht physischen Schaden
- **Intelligenz (INT)** - Erhöht magischen Schaden und Mana
- **Geschicklichkeit (DEX)** - Erhöht kritische Trefferchance und Ausweichen
- **Ausdauer (VIT)** - Erhöht maximale Lebenspunkte
- **Weisheit (WIS)** - Erhöht Heilung und Mana-Regeneration

#### Sekundär-Stats
Diese Stats werden von Items, Skills und Buffs beeinflusst:

- **Kritische Trefferchance** - Chance auf kritischen Treffer (2x Schaden)
- **Kritischer Schaden** - Multiplikator für kritische Treffer
- **Angriffsgeschwindigkeit** - Geschwindigkeit der Angriffe
- **Bewegungsgeschwindigkeit** - Laufgeschwindigkeit
- **Cooldown-Reduktion** - Reduziert Skill-Cooldowns
- **Lebensraub** - Heilt basierend auf verursachtem Schaden
- **Mana-Regeneration** - Mana pro Sekunde

---

## Waffen & Rüstungen

### Waffentypen pro Klasse

| Klasse | Primärwaffe | Sekundärwaffe | Offhand |
|--------|-------------|---------------|---------|
| Krieger | Schwert, Axt | Streitkolben | Schild |
| Magier | Stab | - | Orb, Totem |
| Assassine | Dolch | Schwert | Dolch (Dual Wield) |
| Bogenschütze | Bogen | Armbrust | Köcher (passive) |
| Schamane | Stab | Streitkolben | Totem, Schild |
| Beschwörer | Stab | - | Orb, Totem |

### Rüstungstypen pro Klasse

| Klasse | Erlaubte Rüstung | Verteidigung |
|--------|------------------|--------------|
| Krieger | Plattenrüstung | Hoch |
| Magier | Stoffrüstung | Niedrig |
| Assassine | Lederrüstung | Niedrig-Mittel |
| Bogenschütze | Lederrüstung, Kettenrüstung | Mittel |
| Schamane | Kettenrüstung | Mittel |
| Beschwörer | Stoffrüstung | Niedrig |

---

## Progression-Übersicht

### Level-Bereiche und Ziele

| Level | Ziel | Freischaltungen |
|-------|------|----------------|
| 1-10 | Tutorial & Grundlagen | Basis-Skills |
| 11-25 | Frühe Dungeons & Zonen | Mehr Skills, erste Spezialisierung |
| 26-50 | Mittlere Dungeons & Raids | Ultimate freischaltbar (Level 50) |
| 51-75 | Schwere Dungeons & Raids | Erweiterte Spezialisierung |
| 76-100 | Endgame Content | Alle Skills maximal |

### Skill-Punkt-Verteilung (Empfehlung)

**Ausgewogener Build:**
- 40% Passive Stats
- 40% Active Skills
- 20% Ultimate & Spezial-Skills

**Spezialisierter Build:**
- 30% Passive Stats
- 50% Active Skills (Fokus auf 3-4 Skills)
- 20% Ultimate & Spezial-Skills

---

## Balance-Philosophie

### PvE (Player vs Environment)
- Alle Klassen sind solo spielbar bis Level 50
- Dungeons ab Level 25 empfehlen Gruppen
- Endgame-Raids erfordern ausgewogene Gruppen (Tank, Healer, DPS)

### PvP (Player vs Player)
- Jede Klasse hat Stärken und Schwächen
- Keine Klasse ist in allen Situationen überlegen
- Skill und Ausrüstung sind wichtiger als Klassenwahl
- Rock-Paper-Scissors Balance:
  - Krieger > Assassine (hohe Defense)
  - Assassine > Magier (hohe Mobilität)
  - Magier > Krieger (Reichweite)
  - Bogenschütze > Magier (konstanter Schaden)
  - Schamane = Support-Rolle (Gruppen-PvP stark)
  - Beschwörer = Vielseitig (Crowd Control)

---

## Klassen-spezifische Ressourcen

### Krieger
- **Ressource:** Wut
- **Mechanik:** Baut Wut durch Schaden auf, verbraucht Wut für starke Fähigkeiten

### Magier
- **Ressource:** Mana
- **Mechanik:** Klassisches Mana-System, regeneriert über Zeit

### Assassine
- **Ressource:** Energie
- **Mechanik:** Schnell regenerierende Energie für häufige Skill-Nutzung

### Bogenschütze
- **Ressource:** Fokus
- **Mechanik:** Baut Fokus durch Treffer auf, nutzt Fokus für Spezial-Schüsse

### Schamane
- **Ressource:** Mana
- **Mechanik:** Klassisches Mana-System, fokussiert auf Effizienz

### Beschwörer
- **Ressource:** Mana + Beschwörungs-Slots
- **Mechanik:** Mana für Beschwörungen, limitierte Anzahl aktiver Beschwörungen

---

## Prestige-System

**Status:** In Planung — Wird nach Abschluss des Balance-Testings implementiert.

**Geplante Features:**
- Nach Level 100: Prestige möglich
- Prestige setzt Level zurück, gibt permanente Boni
- Prestige-Stufen: Bis zu 10
- Jede Prestige-Stufe: +5% Stats, exklusive Skins/Titel
- Prestige-spezifische Fähigkeiten und visuelle Effekte
- Prestige-Rangliste im Tab-Menü

---

## Siehe auch

- [RPG-Server Dokumentation](../rpg/README.md)
- [Skyblock-Server Dokumentation](../skyblock/README.md)
- [Item-System](../items/README.md)
- [Workflows für Klassen-Balance](../WORKFLOWS.md#4-neue-klasse-erstellen-mmocore)

---

**Letzte Aktualisierung:** 2026-04-10

**Status:** 🚧 Work in Progress - Detaillierte Klassen-Beschreibungen werden noch ausgefüllt
