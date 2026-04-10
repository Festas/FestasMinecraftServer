# Item-Templates - MinecraftMMO

Vorlagen und Beispiele für die Erstellung neuer Items im MMO-Netzwerk.

---

## Übersicht

Alle Items werden in MMOItems YAML-Dateien definiert unter `rpg/plugins/MMOItems/item/<typ>.yml`. Diese Seite enthält Templates für jeden Item-Typ, die als Ausgangsbasis für neue Items dienen.

---

## Waffen-Template

### Schwert

```yaml
KLASSE_SCHWERTNAME_TIER:
  base:
    material: IRON_SWORD          # Vanilla-Material als Basis
    name: '&f&lSchwertname'       # Farbcode + Name (Tier-Farbe + Fett)
    lore:
    - '&7Beschreibungszeile 1'
    - '&7Beschreibungszeile 2'
    - ''
    - '&8Seltenheit: &fGewöhnlich'
    attack-damage: 6              # Basis-Schaden
    attack-speed: 1.4             # Angriffsgeschwindigkeit
    required-level: 1             # Mindest-Level
    required-class:
    - 'KRIEGER'                   # Klassen-Beschränkung
    tier: COMMON                  # Item-Tier
```

### Bogen

```yaml
BOGENSCHUETZE_BOGENNAME_TIER:
  base:
    material: BOW
    name: '&aBogenname'
    lore:
    - '&7Beschreibungszeile'
    attack-damage: 8
    required-level: 5
    required-class:
    - 'BOGENSCHUETZE'
    tier: UNCOMMON
    arrow-velocity: 2.5           # Pfeil-Geschwindigkeit
```

### Stab / Zauberstab

```yaml
MAGIER_STABNAME_TIER:
  base:
    material: STICK
    custom-model-data: 1001       # Oraxen Custom-Model
    name: '&9Stabname'
    lore:
    - '&7Magischer Stab'
    attack-damage: 3
    magic-damage: 12              # Magischer Schaden
    max-mana: 50                  # Bonus-Mana
    required-level: 10
    required-class:
    - 'MAGIER'
    - 'BESCHWOERER'
    tier: RARE
```

---

## Rüstungs-Template

```yaml
KLASSE_RUESTUNGSTEIL_TIER:
  base:
    material: IRON_CHESTPLATE     # HELMET, CHESTPLATE, LEGGINGS, BOOTS
    name: '&5Rüstungsname'
    lore:
    - '&7Beschreibung'
    armor: 8                      # Rüstungswert
    armor-toughness: 2            # Rüstungszähigkeit
    max-health: 20                # Bonus-HP
    required-level: 25
    required-class:
    - 'KRIEGER'
    tier: EPIC
```

---

## Accessoire-Template

```yaml
RING_NAME_TIER:
  base:
    material: GOLD_NUGGET
    custom-model-data: 2001
    name: '&6Ring der Stärke'
    lore:
    - '&7Erhöht die Stärke des Trägers.'
    attack-damage: 3              # Bonus-Stats
    max-health: 10
    movement-speed: 0.01
    required-level: 30
    tier: LEGENDARY
    # Accessoires haben keinen required-class
```

---

## Material-Template (Crafting-Zutat)

```yaml
GEMEINSAM_MATERIALNAME:
  base:
    material: IRON_INGOT
    name: '&fStahlbarren'
    lore:
    - '&7Crafting-Material'
    - '&8Wird in der Waffenschmiede verwendet.'
```

---

## Verbrauchsgüter-Template

```yaml
HEILTRANK_NAME:
  base:
    material: POTION
    name: '&aHeilender Trank'
    lore:
    - '&7Stellt Lebenspunkte wieder her.'
    restore-health: 50            # HP-Wiederherstellung
    effects:
    - 'REGENERATION:3:60'         # Effekt:Stufe:Dauer(Ticks)
    consume:
      sound: ENTITY_GENERIC_DRINK
      particle: HEART
```

---

## Edelstein-Template (Socketing)

```yaml
GEM_FEUER_RARE:
  base:
    material: REDSTONE
    custom-model-data: 3001
    name: '&9Feuer-Edelstein'
    lore:
    - '&7Fügt Feuer-Schaden hinzu.'
    gem-stone-color: RED
    attack-damage: 5              # Bonus beim Sockeln
    fire-damage: 3                # Feuer-Schaden
    tier: RARE
```

---

## Naming Conventions

| Kategorie | Format | Beispiel |
|-----------|--------|----------|
| Waffen | `KLASSE_NAME_TIER` | `KRIEGER_SCHWERT_T1` |
| Rüstungen | `KLASSE_TEIL_TIER` | `MAGIER_HELM_T3` |
| Accessoires | `TYP_NAME_TIER` | `RING_KRAFT_EPIC` |
| Materialien | `GEMEINSAM_NAME` | `GEMEINSAM_STAHLBARREN` |
| Verbrauchsgüter | `NAME_STUFE` | `HEILTRANK_GROSS` |
| Edelsteine | `GEM_ELEMENT_TIER` | `GEM_FEUER_RARE` |

---

## Siehe auch

- [Item-System Übersicht](README.md)
- [Item-Pipeline](PIPELINE.md)
- [Tier-System](TIERS.md)
- [Crafting-System](CRAFTING.md)

---

**Letzte Aktualisierung:** 2026-04-10
