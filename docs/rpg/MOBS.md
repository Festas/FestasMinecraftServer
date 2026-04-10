# Mob-System - RPG-Server

MythicMobs Premium Struktur, Templates und Mob-Erstellung.

---

## Mob-Kategorien

### 1. Common Mobs (Normal)
- Basis-Gegner in Zonen
- Standard-Stats
- Einfache AI
- Common-Uncommon Drops

### 2. Elite Mobs
- Stärker als normale Mobs
- +50-100% Stats
- Besondere Fähigkeiten
- Rare Drops

### 3. Bosse (Boss Mobs)
- Dungeon-Ender
- +200-500% Stats
- Komplexe Mechaniken
- Epic-Legendary Drops

### 4. Welt-Bosse (World Bosses)
- Open-World Spawns
- +500-1000% Stats
- Gruppen erforderlich
- Best

ige Loot

---

## Mob-Template

### Mob: [NAME]

#### Basis-Informationen
- **Mob-ID:** <!-- mythicmobs ID -->
- **Display-Name:** <!-- Anzeige-Name -->
- **Typ:** <!-- Common / Elite / Boss / World-Boss -->
- **Level:** <!-- X -->
- **Basis-Entity:** <!-- ZOMBIE / SKELETON / etc. -->

#### Stats

| Stat | Wert | Skalierung |
|------|------|-----------|
| HP | <!-- XX --> | <!-- +X pro Level --> |
| Schaden | <!-- XX --> | <!-- +X pro Level --> |
| Rüstung | <!-- XX --> | <!-- +X pro Level --> |
| Bewegungsgeschwindigkeit | <!-- 0.2 --> | <!-- Standard: 0.2 --> |

#### Fähigkeiten (Skills)

**Passive:**
- <!-- Passive 1 -->

**Active:**
1. <!-- Skill 1 --> - Cooldown: <!-- X -->s
2. <!-- Skill 2 --> - Cooldown: <!-- X -->s

#### Drops

**Garantiert:**
- XP: <!-- XX -->
- Coins: <!-- XX -->

**Item-Drops:**

| Item | Rarity | Drop-Chance |
|------|--------|-------------|
| <!-- Item 1 --> | <!-- Common --> | <!-- 50% --> |
| <!-- Item 2 --> | <!-- Rare --> | <!-- 5% --> |

#### Spawn-Informationen

**Spawn-Locations:**
- Zone: <!-- Zone-Name -->
- Koordinaten: <!-- X, Z -->
- Radius: <!-- XX Blöcke -->

**Spawn-Rate:**
- Häufigkeit: <!-- Häufig / Mittel / Selten / Sehr Selten -->
- Respawn-Timer: <!-- Sekunden -->
- Max pro Area: <!-- Anzahl -->

#### MythicMobs YAML

```yaml
MOB_ID:
  Type: ZOMBIE
  Display: '&c[Elite] Mob Name'
  Health: 200
  Damage: 25
  
  Skills:
  - skill{s=MobSkill} @target ~onTimer:100
  - skill{s=AreaDamage} @PlayersInRadius{r=5} ~onAttack 0.3
  
  Drops:
  - gold_nugget 1-3 0.5
  - mmoitem{type=SWORD;id=ELITE_SWORD} 1 0.05
  
  Options:
    MovementSpeed: 0.25
    FollowRange: 32
    AlwaysShowName: true
    PreventOtherDrops: true
```

---

## Stats-Skalierung

### Nach Level

**Common Mobs:**
- HP: Base × (1 + Level × 0.1)
- Schaden: Base × (1 + Level × 0.08)

**Elite Mobs:**
- HP: Base × (1 + Level × 0.15)
- Schaden: Base × (1 + Level × 0.12)

**Bosse:**
- HP: Base × (1 + Level × 0.25)
- Schaden: Base × (1 + Level × 0.15)

**World-Bosse:**
- HP: Base × (1 + Level × 0.35)
- Schaden: Base × (1 + Level × 0.20)

---

## Siehe auch

- [RPG-Server Übersicht](README.md)
- [Dungeon-System](DUNGEONS.md)
- [Workflows für Mobs](../WORKFLOWS.md#1-neuen-boss-erstellen-mythicmobs)

---

**Letzte Aktualisierung:** 2026-04-10
