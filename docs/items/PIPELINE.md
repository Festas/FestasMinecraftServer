# Item-Pipeline - MinecraftMMO

Übersicht über den vollständigen Item-Erstellungsprozess von der Textur bis zum fertigen Drop.

---

## Pipeline-Übersicht

```
1. Oraxen (Textur/Model)
       │
       ▼
2. MMOItems (Stats, Abilities, Tier)
       │
       ▼
3. Crafting-Stationen (Rezepte)
       │
       ▼
4. MythicMobs / drops.yml (Loot-Tables)
       │
       ▼
5. Spieler erhält Item (identifiziert oder unidentifiziert)
```

---

## Schritt 1: Textur erstellen (Oraxen)

**Pfad:** `rpg/plugins/Oraxen/`

Falls das Item ein Custom-Model oder eine Custom-Textur benötigt:

1. Model in Blockbench erstellen (`.json`)
2. Textur als `.png` exportieren
3. In Oraxen konfigurieren (`items/`, `glyphs/`)
4. CustomModelData-ID vergeben

> **Hinweis:** Dieser Schritt ist optional. Viele Items verwenden Standard-Minecraft-Materialien.

---

## Schritt 2: Item in MMOItems definieren

**Pfad:** `rpg/plugins/MMOItems/item/<typ>.yml`

### Item-Typen

| Datei | Typ | Beispiel |
|-------|-----|----------|
| `sword.yml` | Schwerter | `KRIEGER_SCHWERT_T1` |
| `axe.yml` | Äxte | `KRIEGER_AXT_T2` |
| `dagger.yml` | Dolche | `ASSASSINE_DOLCH_T1` |
| `bow.yml` | Bögen | `BOGENSCHUETZE_BOGEN_T3` |
| `wand.yml` | Zauberstäbe | `MAGIER_STAB_T1` |
| `staff.yml` | Stäbe | `SCHAMANE_STAB_T2` |
| `armor.yml` | Rüstungen | `KRIEGER_HELM_T4` |
| `accessory.yml` | Accessoires | `RING_KRAFT_EPIC` |
| `consumable.yml` | Verbrauchsgüter | `HEILTRANK_GROSS` |
| `material.yml` | Materialien | `GEMEINSAM_STAHLBARREN` |
| `gem_stone.yml` | Edelsteine | `GEM_FEUER_RARE` |

### Beispiel Item-Definition

```yaml
KRIEGER_SCHWERT_T1:
  base:
    material: IRON_SWORD
    name: '&fRostige Klinge'
    lore:
    - '&7Eine einfache Klinge für'
    - '&7angehende Krieger.'
    attack-damage: 6
    attack-speed: 1.4
    required-level: 1
    required-class:
    - 'KRIEGER'
    tier: COMMON
```

---

## Schritt 3: Crafting-Station einrichten

**Pfad:** `rpg/plugins/MMOItems/crafting-stations/`

### Verfügbare Stationen

| Station | Datei | Zweck |
|---------|-------|-------|
| Waffenschmiede | `waffenschmiede.yml` | Physische Waffen & Rüstungen |
| Alchemisten-Kessel | `alchemisten-kessel.yml` | Tränke & Alchemie |
| Arkaner Zirkel | `arkaner-zirkel.yml` | Magische Items |
| Juwelenschleifer | `juwelenschleifer.yml` | Schmuck & Edelsteine |
| Schamanen-Hütte | `schamanen-huette.yml` | Schamanen-Items |
| Bogner-Werkstatt | `bogner-werkstatt.yml` | Bögen & Fernkampfwaffen |

### Beispiel Rezept

```yaml
krieger-schwert-t2:
  output: 'mmoitem{type=SWORD,id=KRIEGER_SCHWERT_T2}'
  crafting-time: 8
  conditions:
  - 'level{level=12}'
  ingredients:
  - 'mmoitem{type=MATERIAL,id=GEMEINSAM_STAHLBARREN,amount=4}'
  - 'vanilla{type=STICK,amount=2}'
```

---

## Schritt 4: Drop-Tables konfigurieren

**Pfad:** `rpg/plugins/MMOItems/drops.yml`

### Drop-Format

```yaml
# Format: [Chance]%, [Min-Max Anzahl], [Unidentifizierungs-Chance]%
MATERIAL:
  RARE_DIAMOND: 100,2-3,0
```

Items können aus folgenden Quellen droppen:
- **Mobs** (MythicMobs Loot-Tables)
- **Blöcke** (Vanilla oder Custom)
- **Dungeons** (Boss-Loot)
- **Quests** (Belohnungen)
- **Crafting** (Crafting-Stationen)

---

## Schritt 5: Identifizierung

MMOItems unterstützt ein **Unidentifizierungs-System**:
- Items können unidentifiziert droppen (zufällige Stats)
- Spieler können Items bei NPCs oder über Scrolls identifizieren
- Identifizierte Items erhalten ihre finalen Stats basierend auf dem Tier

---

## Best Practices

1. **Naming Convention:** `KLASSE_ITEMNAME_TIER` (z.B. `KRIEGER_SCHWERT_T3`)
2. **Materialien zuerst:** Erstelle Crafting-Materialien vor fertigen Items
3. **Balance-Reihenfolge:** Common → Uncommon → Rare → Epic → Legendary → Mythic
4. **Cross-Reference:** Stelle sicher, dass referenzierte Materialien in `material.yml` existieren
5. **Testen:** Immer `/mmoitems give <type> <id>` zum Testen verwenden

---

## Siehe auch

- [Item-System Übersicht](README.md)
- [Tier-System](TIERS.md)
- [Item-Templates](TEMPLATES.md)
- [Crafting-System](CRAFTING.md)

---

**Letzte Aktualisierung:** 2026-04-10
