# NPC-System - RPG-Server

Citizens + BetonQuest Integration für NPCs.

---

## NPC-Typen

### 1. Quest-Geber
- Vergeben Quests
- BetonQuest-Integration
- Dialoge

### 2. Händler
- Verkaufen Items
- Shop-GUIs
- Spezielle Waren

### 3. Trainer
- Skill-Training
- Klassen-Wechsel
- Info-NPCs

### 4. Lore/Story-NPCs
- Welt-Building
- Hintergrund-Info
- Atmosphäre

---

## NPC-Template

### NPC: [NAME]

#### Basis-Informationen
- **NPC-ID:** <!-- citizens ID -->
- **Name:** <!-- Display-Name -->
- **Typ:** <!-- Quest-Geber / Händler / Trainer / Lore -->
- **Location:** <!-- Zone, Koordinaten -->
- **Skin:** <!-- Minecraft-Skin oder Player-Name -->

#### Funktion

**Hauptfunktion:**
<!-- TODO: Was macht dieser NPC? -->

**Interaktion:**
- <!-- Rechtsklick: Quest-Dialog -->
- <!-- Linksklick: Shop öffnen -->

#### Dialoge

**Begrüßung:**
```
NPC: "<!-- Begrüßungs-Text -->"
```

**Hauptdialog:**
```
NPC: "<!-- Haupt-Dialog -->"
Optionen:
  [1] "<!-- Option 1 -->"
  [2] "<!-- Option 2 -->"
```

#### Quests (falls Quest-Geber)

**Verfügbare Quests:**
- <!-- Quest 1 → Link zu QUESTS.md -->
- <!-- Quest 2 → Link zu QUESTS.md -->

#### Shop (falls Händler)

**Verkaufte Items:**

| Item | Preis | Währung |
|------|-------|---------|
| <!-- Item 1 --> | <!-- 100 --> | <!-- Coins --> |
| <!-- Item 2 --> | <!-- 50 --> | <!-- Tokens --> |

#### Citizens YAML

```yaml
# NPC erstellen
/npc create [Name]
/npc skin [player_name or URL]
/npc lookclose

# BetonQuest Integration
/npc assignment --set quest_npc
/npc text --set "Hello, traveler!"

# Shop (z.B. mit DeluxeMenus)
/npc command add openmenu [menu_name]
```

---

## Dialog-Struktur

### BetonQuest Conversations

```yaml
conversations:
  npc_name:
    quester: "NPC Display Name"
    first: "greeting"
    
    NPC_options:
      greeting:
        text: "Hello %player%!"
        pointer: "options"
      
      options:
        text: "What can I do for you?"
        pointer: "quest_available,shop,goodbye"
    
    player_options:
      quest_available:
        text: "Do you have any quests?"
        conditions: "!quest_active"
        pointer: "quest_offer"
      
      shop:
        text: "Show me your wares."
        events: "open_shop"
      
      goodbye:
        text: "Goodbye!"
        events: "close_conversation"
```

---

## NPC-Placement Best Practices

### Positionierung
- Gut sichtbare Locations
- Nahe Waypoints
- Logische Gruppierung (alle Händler zusammen)

### Hologramme
- Nutze DecentHolograms über NPCs
- Zeige Funktion an (z.B. "Quest-Geber")
- Quest-Marker für verfügbare Quests

### Varianz
- Unterschiedliche Skins
- Verschiedene Dialoge
- Persönlichkeiten

---

## Siehe auch

- [RPG-Server Übersicht](README.md)
- [Quest-System](QUESTS.md)
- [Workflows für NPCs](../WORKFLOWS.md#6-neuen-npc-erstellen-citizens)

---

**Letzte Aktualisierung:** 2026-04-10
