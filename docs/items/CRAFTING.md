# Crafting-System - MinecraftMMO

Dokumentation des Crafting-Systems über MMOItems Crafting-Stationen.

---

## Übersicht

Das Crafting-System verwendet **MMOItems Crafting-Stationen**, die von NPCs in der Spielwelt zugänglich sind. Spieler können dort Materialien kombinieren, um mächtigere Items herzustellen.

**Pfad:** `rpg/plugins/MMOItems/crafting-stations/`

---

## Crafting-Stationen

| Station | Datei | Spezialisierung | NPC-Standort |
|---------|-------|-----------------|--------------|
| Waffenschmiede | `waffenschmiede.yml` | Physische Waffen, Rüstungen (T2-T4) | Hub-Stadt |
| Alchemisten-Kessel | `alchemisten-kessel.yml` | Tränke, Gifte, Buffs | Hub-Stadt |
| Arkaner Zirkel | `arkaner-zirkel.yml` | Magische Items, Stäbe, Orbs | Magier-Turm |
| Juwelenschleifer | `juwelenschleifer.yml` | Ringe, Amulette, Edelsteine | Hub-Stadt |
| Schamanen-Hütte | `schamanen-huette.yml` | Totems, Naturstäbe, Heilitems | Schamanen-Dorf |
| Bogner-Werkstatt | `bogner-werkstatt.yml` | Bögen, Armbrüste, Pfeile | Hub-Stadt |

---

## Rezept-Format

### Grundstruktur

```yaml
rezept-name:
  output: 'mmoitem{type=SWORD,id=KRIEGER_SCHWERT_T2}'
  crafting-time: 8                          # Sekunden
  conditions:
  - 'level{level=12}'                       # Level-Voraussetzung
  ingredients:
  - 'mmoitem{type=MATERIAL,id=GEMEINSAM_STAHLBARREN,amount=4}'
  - 'vanilla{type=STICK,amount=2}'
```

### Zutat-Typen

| Typ | Format | Beschreibung |
|-----|--------|-------------|
| MMOItem | `mmoitem{type=TYP,id=ID,amount=N}` | Custom MMOItems Material |
| Vanilla | `vanilla{type=MATERIAL,amount=N}` | Standard Minecraft Material |

### Bedingungen

| Bedingung | Format | Beschreibung |
|-----------|--------|-------------|
| Level | `level{level=N}` | Mindest-Spielerlevel |
| Klasse | `class{class=KLASSE}` | Nur bestimmte Klassen |
| Quest | `quest{quest=QUEST_ID}` | Quest muss abgeschlossen sein |

---

## Crafting-Materialien

Crafting-Materialien werden in `rpg/plugins/MMOItems/item/material.yml` definiert und können von Mobs, aus Dungeons oder aus der Welt gesammelt werden.

### Basis-Materialien

| Material | ID | Quelle |
|----------|----|--------|
| Stahlbarren | `GEMEINSAM_STAHLBARREN` | Mining, Mobs |
| Leder-Schuppen | `GEMEINSAM_LEDERSCHUPPEN` | Mobs |
| Magische Essenz | `GEMEINSAM_MAGIE_ESSENZ` | Mobs, Dungeons |
| Naturkristall | `GEMEINSAM_NATURKRISTALL` | Mining |
| Dunkler Splitter | `GEMEINSAM_DUNKEL_SPLITTER` | Dungeons |

### Seltene Materialien

| Material | ID | Quelle |
|----------|----|--------|
| Drachenschuppe | `SELTEN_DRACHENSCHUPPE` | Bosse |
| Ätherstaub | `SELTEN_AETHERSTAUB` | Dungeons (Hard+) |
| Göttlicher Funken | `SELTEN_GOETTLICHER_FUNKEN` | Raid-Bosse |

---

## Crafting-Progression

### Empfohlene Crafting-Reihenfolge

| Level | Tier | Stationen verfügbar |
|-------|------|---------------------|
| 1-15 | — | Kein Crafting (Items durch Drops/Quests) |
| 15-30 | T2 (UNCOMMON) | Waffenschmiede, Bogner-Werkstatt |
| 30-50 | T3 (RARE) | Alle Stationen |
| 50-75 | T4 (EPIC) | Alle Stationen + seltene Materialien |
| 75-100 | T5 (LEGENDARY) | Spezial-Rezepte + Dungeon-Materialien |

---

## Tipps

1. **Materialien horten:** Viele höherstufige Rezepte benötigen niedrigstufige Materialien
2. **Klassen-Stationen:** Manche Stationen bieten klassen-spezifische Rezepte
3. **Crafting-Zeit:** Höherstufige Items benötigen mehr Crafting-Zeit
4. **Level-Check:** Stelle sicher, dass du das benötigte Level hast, bevor du Materialien sammelst

---

## Siehe auch

- [Item-System Übersicht](README.md)
- [Item-Pipeline](PIPELINE.md)
- [Tier-System](TIERS.md)
- [Item-Templates](TEMPLATES.md)

---

**Letzte Aktualisierung:** 2026-04-10
