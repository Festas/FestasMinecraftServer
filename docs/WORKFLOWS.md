# Workflow-Templates - MinecraftMMO

Leere Workflow-Templates für die zukünftige Dokumentation von häufigen Aufgaben.

---

## 1. Neuen Boss erstellen (MythicMobs)

### Vorbereitung
- [ ] Boss-Konzept definieren (Name, Typ, Thema)
- [ ] Level und Schwierigkeit festlegen
- [ ] Fähigkeiten/Skills planen
- [ ] Loot-Table planen
- [ ] Spawn-Location(s) festlegen

### Schritte

#### 1.1 Mob-Definition erstellen
```yaml
# Pfad: skyblock/plugins/MythicMobs/Mobs/ oder rpg/plugins/MythicMobs/Mobs/
# Dateiname: BOSS_NAME_LEVEL.yml

BOSS_NAME:
  Type: # z.B. ZOMBIE, SKELETON, WITHER_SKELETON
  Display: '&c&lBoss Name'
  Health: # HP
  Damage: # Base Damage
  
  Skills:
  - # Skills hier definieren
  
  Drops:
  - # Loot hier definieren
  
  Options:
    # Weitere Optionen
```

#### 1.2 Skills definieren
```yaml
# Pfad: skyblock/plugins/MythicMobs/Skills/ oder rpg/plugins/MythicMobs/Skills/
# Dateiname: BOSS_NAME_skills.yml

# Skill-Definitionen hier
```

#### 1.3 Items für Loot erstellen
_Siehe "Neues Item erstellen"_

#### 1.4 Spawn-Mechanik einrichten
- [ ] Spawn-Point festlegen
- [ ] Respawn-Timer konfigurieren
- [ ] Spawn-Conditions definieren (optional)

#### 1.5 Testing
- [ ] Boss spawnen (`/mm mobs spawn BOSS_NAME`)
- [ ] Skills testen
- [ ] Damage/HP balancen
- [ ] Loot überprüfen
- [ ] Mit Gruppe testen

#### 1.6 Dokumentation
- [ ] Boss-Info in Server-Docs hinzufügen
- [ ] Spawn-Location dokumentieren
- [ ] Empfohlenes Level/Gruppe dokumentieren

### Notizen
_Platz für boss-spezifische Notizen_

---

## 2. Neue Quest erstellen (BetonQuest)

### Vorbereitung
- [ ] Quest-Story schreiben
- [ ] Objectives definieren
- [ ] Belohnungen festlegen
- [ ] NPCs für Quest identifizieren
- [ ] Voraussetzungen definieren (Level, vorherige Quests, etc.)

### Schritte

#### 2.1 Quest-Definition erstellen
```yaml
# Pfad: skyblock/plugins/BetonQuest/ oder rpg/plugins/BetonQuest/
# Dateiname: KATEGORIE_NUMMER_NAME.yml

# Quest-Haupt-Definition
```

#### 2.2 Objectives definieren
```yaml
# Objectives (Ziele):
# - kill_mobs
# - collect_items
# - talk_to_npc
# - reach_location
# - etc.
```

#### 2.3 Conditions definieren
```yaml
# Conditions (Bedingungen):
# - level_requirement
# - previous_quest
# - class_requirement
# - etc.
```

#### 2.4 Events definieren
```yaml
# Events (Ereignisse):
# - give_items
# - spawn_mobs
# - teleport
# - etc.
```

#### 2.5 NPC-Integration
- [ ] Quest-NPC erstellen/konfigurieren (Citizens)
- [ ] Dialoge schreiben
- [ ] Quest-Accept/Complete-Messages

#### 2.6 Testing
- [ ] Quest annehmen
- [ ] Alle Objectives durchspielen
- [ ] Quest abschließen
- [ ] Belohnungen überprüfen
- [ ] Follow-up-Quests testen

#### 2.7 Dokumentation
- [ ] Quest-Beschreibung
- [ ] Empfohlenes Level
- [ ] Geschätzte Dauer
- [ ] Belohnungen dokumentieren

### Notizen
_Platz für quest-spezifische Notizen_

---

## 3. Neues Item erstellen (MMOItems + Oraxen)

### Vorbereitung
- [ ] Item-Konzept definieren (Waffe, Rüstung, Tool, etc.)
- [ ] Tier festlegen (Common - Mythic)
- [ ] Stats planen
- [ ] Special Abilities planen (optional)
- [ ] Texture/Model planen (optional)

### Schritte

#### 3.1 MMOItems Definition
```yaml
# Pfad: skyblock/plugins/MMOItems/item/ oder rpg/plugins/MMOItems/item/
# Dateiname: TYPE_NAME_TIER.yml

# Base-Item Definition
# - Material
# - Display Name
# - Lore
# - Stats (Damage, Defense, etc.)
# - Abilities
```

#### 3.2 Oraxen Integration (optional, für Custom Textures)
```yaml
# Pfad: plugins/Oraxen/items/
# Dateiname: custom_items.yml

# Custom Model/Texture Definition
# - CustomModelData
# - Texture-Pack Integration
```

#### 3.3 Stats konfigurieren
- [ ] Base Stats (Damage, Defense, HP, etc.)
- [ ] Sekundär-Stats (Crit, Speed, etc.)
- [ ] Stat-Scaling nach Level

#### 3.4 Abilities hinzufügen (optional)
- [ ] Active Ability definieren
- [ ] Passive Ability definieren
- [ ] Cooldowns festlegen

#### 3.5 Loot-Integration
- [ ] Zu Mob-Loot-Table hinzufügen
- [ ] Zu Dungeon-Loot hinzufügen
- [ ] Zu Quest-Reward hinzufügen

#### 3.6 Testing
- [ ] Item geben (`/mmoitems give TYPE ID`)
- [ ] Stats im Inventar überprüfen
- [ ] Abilities testen
- [ ] Balance-Check

#### 3.7 Dokumentation
- [ ] Item-Beschreibung
- [ ] Wo zu finden (Drop-Location)
- [ ] Klassen-Requirement (falls vorhanden)

### Notizen
_Platz für item-spezifische Notizen_

---

## 4. Neue Klasse erstellen (MMOCore)

### Vorbereitung
- [ ] Klassen-Konzept (Rolle, Thema, Gameplay-Style)
- [ ] Haupt-Stats definieren
- [ ] Waffentypen festlegen
- [ ] Skill-Tree planen
- [ ] Ultimate-Ability planen

### Schritte

#### 4.1 Klassen-Definition
```yaml
# Pfad: plugins/MMOCore/classes/
# Dateiname: CLASSNAME.yml

# Base-Class Definition
# - Display Name
# - Description
# - Max Level
# - Base Stats
# - Allowed Weapons/Armor
```

#### 4.2 Skill-Tree erstellen
- [ ] Passive Skills definieren
- [ ] Active Skills definieren
- [ ] Ultimate definieren
- [ ] Skill-Unlock-Level festlegen

#### 4.3 Stats-Scaling
- [ ] Stats pro Level
- [ ] Stat-Soft-Caps
- [ ] Stat-Priorities

#### 4.4 Ausrüstungs-Restrictions
- [ ] Waffentypen erlauben
- [ ] Rüstungstypen erlauben
- [ ] Klassen-exklusive Items (optional)

#### 4.5 Starter-Ausrüstung
- [ ] Starter-Waffe
- [ ] Starter-Rüstung
- [ ] Starter-Items

#### 4.6 Testing
- [ ] Klasse wählen (`/class choose CLASSNAME`)
- [ ] Skill-Tree testen
- [ ] Balance-Check mit anderen Klassen
- [ ] PvE-Testing
- [ ] PvP-Testing (falls relevant)

#### 4.7 Dokumentation
- [ ] Klassen-Beschreibung
- [ ] Playstyle-Guide
- [ ] Skill-Overview
- [ ] Empfohlene Ausrüstung

### Notizen
_Platz für klassen-spezifische Notizen_

---

## 5. Neuen Dungeon erstellen (MythicDungeons)

### Vorbereitung
- [ ] Dungeon-Thema definieren
- [ ] Schwierigkeitsgrad festlegen
- [ ] Empfohlene Spieler-Anzahl
- [ ] Boss(e) planen
- [ ] Loot-Table planen

### Schritte

#### 5.1 World/Schematic vorbereiten
- [ ] Dungeon-Map bauen
- [ ] Spawn-Points für Mobs
- [ ] Boss-Arena
- [ ] Loot-Chests
- [ ] Schematic speichern

#### 5.2 Dungeon-Definition
```yaml
# Pfad: plugins/MythicDungeons/dungeons/
# Dateiname: DUNGEON_NAME.yml

# Dungeon Configuration
# - Map/Schematic
# - Difficulty
# - Min/Max Players
# - Time Limit
# - Instance Settings
```

#### 5.3 Mob-Spawns konfigurieren
- [ ] Trash-Mobs definieren
- [ ] Elite-Mobs definieren
- [ ] Boss(e) definieren
- [ ] Spawn-Logic

#### 5.4 Boss-Encounters
_Siehe "Neuen Boss erstellen"_

#### 5.5 Loot-Table
- [ ] Trash-Mob-Loot
- [ ] Boss-Loot
- [ ] Chest-Loot
- [ ] Completion-Rewards

#### 5.6 Entrance/Exit
- [ ] Dungeon-Portal erstellen
- [ ] Entry-Requirements (Level, Quest, etc.)
- [ ] Exit-Portal nach Completion

#### 5.7 Testing
- [ ] Solo-Test (wenn möglich)
- [ ] Gruppen-Test
- [ ] Balance-Check
- [ ] Loot-Check
- [ ] Time-Limit anpassen

#### 5.8 Dokumentation
- [ ] Dungeon-Guide
- [ ] Boss-Mechaniken
- [ ] Empfohlene Gruppenzusammensetzung
- [ ] Notable-Loot

### Notizen
_Platz für dungeon-spezifische Notizen_

---

## 6. Neuen NPC erstellen (Citizens)

### Vorbereitung
- [ ] NPC-Funktion definieren (Questgeber, Händler, Trainer, etc.)
- [ ] NPC-Aussehen (Skin, Name)
- [ ] Dialog schreiben (falls Quest-NPC)
- [ ] Shop-Inventory planen (falls Händler)

### Schritte

#### 6.1 NPC spawnen
```
/npc create <NAME>
/npc skin <player_name|URL>
```

#### 6.2 NPC positionieren
- [ ] Location auswählen
- [ ] NPC teleportieren (`/npc tp`)
- [ ] Blickrichtung einstellen

#### 6.3 NPC konfigurieren

**Für Quest-NPCs:**
```
# BetonQuest-Integration
# NPC mit Quest verknüpfen
```

**Für Händler-NPCs:**
```
# Shop-GUI konfigurieren
# Items und Preise definieren
```

**Für Info-NPCs:**
```
# Dialog/Text definieren
# Rechtsklick-Action
```

#### 6.4 Traits hinzufügen (optional)
```
/trait sentinel  # Für Wächter-NPCs
/trait follow    # Folge-NPCs
/trait lookclose # Schaut Spieler an
```

#### 6.5 Hologram hinzufügen (optional)
- [ ] DecentHolograms über NPC spawnen
- [ ] Info-Text schreiben

#### 6.6 Testing
- [ ] NPC-Interaktion testen
- [ ] Dialog durchspielen
- [ ] Shop-Funktionen testen
- [ ] Respawn nach Server-Restart

#### 6.7 Dokumentation
- [ ] NPC-Location dokumentieren
- [ ] NPC-Funktion beschreiben
- [ ] Quest/Shop-Details

### Notizen
_Platz für npc-spezifische Notizen_

---

## 7. Neues Minion erstellen (JetsMinions)

### Vorbereitung
- [ ] Minion-Typ definieren (Mining, Farming, Combat, etc.)
- [ ] Sammel-Item festlegen
- [ ] Upgrade-Stufen planen
- [ ] Fuel-Typ festlegen (optional)

### Schritte

#### 7.1 Minion-Definition
```yaml
# Pfad: plugins/JetsMinions/minions/
# Dateiname: MINION_TYPE.yml

# Minion Configuration
# - Type (Mining, Farming, etc.)
# - Collected Item
# - Speed per Tier
# - Storage Size
```

#### 7.2 Upgrade-Tiers
- [ ] Tier 1 Stats
- [ ] Tier 2 Stats
- [ ] ...
- [ ] Max Tier Stats

#### 7.3 Crafting-Recipe (optional)
- [ ] Crafting-Zutaten
- [ ] Unlock-Requirements

#### 7.4 Testing
- [ ] Minion platzieren
- [ ] Sammlung testen
- [ ] Upgrades testen
- [ ] Storage testen
- [ ] Fuel-Mechanik testen

#### 7.5 Dokumentation
- [ ] Minion-Beschreibung
- [ ] Unlock-Bedingungen
- [ ] Upgrade-Kosten
- [ ] Optimale Nutzung

### Notizen
_Platz für minion-spezifische Notizen_

---

## 8. Custom Texture/Model hinzufügen (Oraxen/ModelEngine)

### Vorbereitung
- [ ] Model/Texture erstellen (Blockbench, etc.)
- [ ] Geeigneten Slot/Item bestimmen
- [ ] CustomModelData ID vergeben

### Schritte

#### 8.1 Oraxen (für Items/Blöcke)
```yaml
# Pfad: plugins/Oraxen/items/
# Dateiname: custom_items.yml

# Custom Item mit Texture
```

#### 8.2 ModelEngine (für Mobs/NPCs)
```yaml
# Pfad: plugins/ModelEngine/models/
# Dateiname: MODEL_NAME.yml

# Model-Definition
```

#### 8.3 Resourcepack aktualisieren
- [ ] Model/Texture zu Pack hinzufügen
- [ ] Pack neu generieren
- [ ] Pack hochladen
- [ ] Server-Config aktualisieren

#### 8.4 Testing
- [ ] Item/Model in-game testen
- [ ] Animationen testen (falls vorhanden)
- [ ] Clientseitige Überprüfung

#### 8.5 Dokumentation
- [ ] Model-ID dokumentieren
- [ ] CustomModelData notieren

### Notizen
_Platz für model-spezifische Notizen_

---

## 9. Server-Update durchführen

### Vorbereitung
- [ ] Ankündigung an Spieler (Discord, In-Game)
- [ ] Backup erstellen (Welt, Datenbank, Configs)
- [ ] Update-Notes lesen
- [ ] Kompatibilität prüfen

### Schritte

#### 9.1 Pre-Update
- [ ] Wartungsmodus aktivieren
- [ ] Spieler kicken
- [ ] Server stoppen
- [ ] Backup verifizieren

#### 9.2 Update
- [ ] Plugin(s) updaten
- [ ] Config-Änderungen übernehmen
- [ ] Neue Features konfigurieren

#### 9.3 Testing
- [ ] Server starten
- [ ] Console auf Errors prüfen
- [ ] Schnelltest wichtiger Features
- [ ] Performance-Check

#### 9.4 Post-Update
- [ ] Wartungsmodus deaktivieren
- [ ] Spieler informieren
- [ ] Monitoring verstärken
- [ ] Changelog posten

#### 9.5 Rollback (falls Probleme)
- [ ] Server stoppen
- [ ] Backup wiederherstellen
- [ ] Alte Version starten
- [ ] Post-Mortem

### Notizen
_Update-spezifische Notizen_

---

## 10. Neue Zone/Gebiet erstellen (RPG-Server)

### Vorbereitung
- [ ] Zonen-Thema definieren
- [ ] Level-Range festlegen
- [ ] Points of Interest planen
- [ ] Mob-Verteilung planen

### Schritte

#### 10.1 World-Building
- [ ] Terrain erstellen
- [ ] Strukturen bauen
- [ ] Deko platzieren
- [ ] Wege/Pfade anlegen

#### 10.2 Mob-Spawns
- [ ] Spawn-Points festlegen
- [ ] Mob-Typen pro Area
- [ ] Spawn-Rates konfigurieren
- [ ] Elite/Boss-Spawns

#### 10.3 NPCs platzieren
- [ ] Questgeber-NPCs
- [ ] Händler-NPCs
- [ ] Lore-NPCs
- [ ] Trainer-NPCs (falls vorhanden)

#### 10.4 Quests erstellen
_Siehe "Neue Quest erstellen"_

#### 10.5 Points of Interest
- [ ] Dungeons
- [ ] Höhlen
- [ ] Dörfer/Städte
- [ ] Landmarks

#### 10.6 Testing
- [ ] Durchqueren der Zone
- [ ] Mob-Spawns testen
- [ ] Quest-Flow testen
- [ ] Level-Balance prüfen

#### 10.7 Dokumentation
- [ ] Zonen-Map
- [ ] Quest-Liste
- [ ] Notable-Loot-Locations
- [ ] Lore/Story-Info

### Notizen
_Zonen-spezifische Notizen_

---

## Template für eigene Workflows

### Vorbereitung
- [ ] ...

### Schritte

#### Schritt 1
...

#### Schritt 2
...

### Testing
- [ ] ...

### Dokumentation
- [ ] ...

### Notizen
_..._

---

**Letzte Aktualisierung:** 2026-04-10

**Hinweis:** Diese Templates sind Leitfäden - nicht jeder Schritt ist für jeden Use-Case relevant. Anpassen nach Bedarf!
