# Quick Reference Guide

Schnelle Übersicht über die wichtigsten Befehle und Konzepte.

> **ℹ️ Stand 26.2:** Server läuft auf **Minecraft/Paper 26.2**. Aktiver Fokus: **Lobby** und **Survival**. Dazu kommen ein **überarbeiteter Skyblock** (ohne Gilden, Freunde-Koop) und ein neuer **Mining**-Server. Die MMO-Abschnitte (MythicMobs, Citizens, Quests, Klassen, Item-Tiers, Dungeons) beziehen sich auf den auslaufenden **RPG**-Server — sie gelten als **Archiv**.

## Verzeichnisstruktur

```
MinecraftMMO/
├── proxy/              # Velocity Proxy Konfigurationen
│   └── plugins/        # Proxy-Plugins (TAB, MiniMOTD, LibertyBans, etc.)
├── lobby/              # Lobby Server Konfigurationen        (AKTIV)
│   └── plugins/        # Lobby-Plugins (CMI, DeluxeMenus, Skript, Oraxen, etc.)
├── survival/           # Survival Server Konfigurationen     (AKTIV)
│   └── plugins/        # Survival-Plugins (NextGens, Jobs, Rankup, PlotSquared, etc.)
├── skyblock/           # Skyblock Server Konfigurationen      (UMBAU — ohne Gilden, Freunde-Koop)
│   └── plugins/        # Skyblock-Plugins (SuperiorSkyblock2, JetsMinions, etc.)
├── rpg/                # Mining Server Konfigurationen       (RECYCELT aus RPG — Aufbau)
│   └── plugins/        # Mining-/Zonen-Kern, WorldGuard, Shop/Auto-Sell, etc. (+ Alt-RPG-Archiv)
└── docs/               # Dokumentation
```

## Wichtige Dateien

- **README.md** - Projekt-Übersicht
- **CONTRIBUTING.md** - Richtlinien für Beiträge
- **.gitignore** - Git-Ausschlüsse

## Schnellstart

> **⚠️ Archiv:** Die folgenden Schnellstarts (MMOItems, MythicMobs, BetonQuest) betreffen die auslaufenden Server **Skyblock**/**RPG**. Für die aktiven Server bearbeite die Configs unter `lobby/plugins/` bzw. `survival/plugins/` (z. B. `survival/plugins/NextGens/`, `survival/plugins/ShopGUIPlus/`, `lobby/plugins/DeluxeMenus/`).

### 1. Neues Item erstellen *(Archiv — MMO)*

```bash
cd rpg/plugins/MMOItems/
# Bearbeite oder erstelle eine Item-Konfigurationsdatei
```

### 2. Neuen Mob erstellen

```bash
cd rpg/plugins/MythicMobs/Mobs/
# Bearbeite oder erstelle eine Mob-Konfigurationsdatei
```

### 3. Neue Quest erstellen

```bash
cd rpg/plugins/BetonQuest/
# Bearbeite oder erstelle eine Quest-Konfigurationsdatei
```

## In-Game Befehle

> **⚠️ Archiv:** Die folgenden Befehlsgruppen (MythicMobs, Citizens, Quests, Skills/Klassen) sowie die weiter unten stehenden Referenzen (Item-Tiers, Klassen, Mob-Level, Dungeons) gehören zum MMO-Content der auslaufenden Server **Skyblock**/**RPG**.

### MythicMobs

```
/mm items get ITEM_NAME [Anzahl]      # Item erhalten
/mm mobs spawn MOB_NAME [Anzahl]       # Mob spawnen
/mm reload                             # Plugin neu laden
```

### Citizens (NPCs)

```
/npc create NAME                       # NPC erstellen
/npc skin [NAME]                       # Skin setzen
/citizens reload                       # Plugin neu laden
```

### Quests

```
/quests give [Spieler] QUEST_NAME      # Quest geben
/quests reload                         # Plugin neu laden
```

### Skills/Classes

```
/class choose [KLASSE]                 # Klasse wählen
/skills [SKILL]                        # Skill verwenden
/skills reload                         # Plugin neu laden
```

## Tier-System

| Tier | Farbe | Code | Werte-Multiplikator |
|------|-------|------|---------------------|
| Common | Weiß | `&f` | 1.0x |
| Uncommon | Grün | `&a` | 1.25x |
| Rare | Blau | `&9` | 1.5x |
| Epic | Lila | `&5` | 2.0x |
| Legendary | Gold | `&6` | 3.0x |
| Mythic | Pink | `&d` | 5.0x |

## Klassen-Übersicht

| Klasse | Hauptstat | Rolle | Waffen |
|--------|-----------|-------|--------|
| Krieger | Stärke | Tank/DPS | Schwert, Axt |
| Magier | Intelligenz | Ranged DPS | Stab, Zauberbuch |
| Assassine | Geschwindigkeit | Melee DPS | Dolch |
| Bogenschütze | Geschick | Ranged DPS | Bogen |
| Schamane | Weisheit | Support/Healer | Stab, Totem |
| Beschwörer | Intelligenz | Summoner | Zauberbuch, Stab |

## Beispiel-Items

### Anfänger-Schwert
```yaml
SWORD_BASIC_COMMON:
  Id: IRON_SWORD
  Display: '&fEinfaches Schwert'
  Damage: 20
  Tier: COMMON
```

### Legendäres Schwert
```yaml
SWORD_FIRE_LEGENDARY:
  Id: DIAMOND_SWORD
  Display: '&6&lLegendäres Feuerschwert'
  Damage: 150
  Tier: LEGENDARY
  Special: Fire Aspect
```

## Mob-Level-System

```
Level 1-25:   Anfänger-Gebiet
Level 26-50:  Fortgeschrittenen-Gebiet
Level 51-75:  Experten-Gebiet
Level 76-100: End-Game-Gebiet
```

## Dungeon-Schwierigkeiten

| Schwierigkeit | Multiplikator | Empfohlene Gruppe |
|---------------|---------------|-------------------|
| Normal | 1.0x | 3-4 Spieler |
| Hard | 1.5x | 4-5 Spieler |
| Master | 2.0x | 5 Spieler |

## Datei-Konventionen

### Items
`KATEGORIE_NAME_TIER.yml`
- `SWORD_FIRE_LEGENDARY.yml`

### Mobs
`MOB_NAME_LEVEL.yml`
- `ZOMBIE_WARRIOR_10.yml`

### Quests
`KATEGORIE_NUMMER_NAME.yml`
- `MAIN_01_THE_BEGINNING.yml`

### NPCs
`FUNKTION_NAME.yml`
- `MERCHANT_WEAPONS.yml`

## Farbcodes Referenz

```
&0 Schwarz      &8 Dunkelgrau
&1 Dunkelblau   &9 Blau
&2 Dunkelgrün   &a Grün
&3 Dunkel-Aqua  &b Aqua
&4 Dunkelrot    &c Rot
&5 Dunkel-Lila  &d Pink
&6 Gold         &e Gelb
&7 Grau         &f Weiß

&l Fett         &o Kursiv
&m Durchgestr.  &n Unterstrichen
&r Reset
```

## Häufige Fehler

### YAML-Syntax
```yaml
# ❌ FALSCH (Tabs verwendet)
Item:
	Display: 'Text'

# ✅ RICHTIG (2 Leerzeichen)
Item:
  Display: 'Text'
```

### Anführungszeichen
```yaml
# ❌ FALSCH (fehlende Anführungszeichen bei &)
Display: &6Text

# ✅ RICHTIG
Display: '&6Text'
```

## Hilfreiche Links

- MythicMobs Wiki: https://git.mythiccraft.io/mythiccraft/MythicMobs/-/wikis/home
- Citizens Wiki: https://wiki.citizensnpcs.co/
- Paper Docs: https://docs.papermc.io/
- Minecraft Farbcodes: https://minecraft.tools/en/color-code.php

## Plugin-Versionen

| Plugin | Min. Version | Empfohlen |
|--------|-------------|-----------|
| MythicMobs | 5.0.0 | 5.7.0+ |
| Citizens | 2.0.30 | 2.0.35+ |
| Paper | 26.2 | Latest |

## Backup-Befehl

```bash
# Komplettes Backup erstellen (aktive Server + Proxy)
tar -czf mmo-backup-$(date +%Y%m%d).tar.gz \
  lobby/ survival/ skyblock/ rpg/ proxy/

# Hinweis: rpg/ ist der recycelte Slot des alten RPG-Servers (wird zum Mining-Server) und enthält
# noch Alt-RPG-Configs; vor dem Content-Umbau ein separates Archiv-Backup des rpg/-Ordners ziehen:
tar -czf mmo-archive-$(date +%Y%m%d).tar.gz \
  rpg/
```

## Repository-Befehle

```bash
# Änderungen anzeigen
git status

# Dateien hinzufügen
git add .

# Commit erstellen
git commit -m "Add: Neue Items"

# Push zu GitHub
git push

# Aktuellen Stand holen
git pull
```

## Support

Bei Problemen:
1. YAML-Syntax prüfen
2. Console-Logs lesen
3. Plugin-Dokumentation konsultieren
4. Beispiel-Dateien vergleichen
