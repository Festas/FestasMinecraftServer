# Anleitung zur Verwendung

Diese Anleitung erklärt, wie die Konfigurationsdateien in diesem Repository verwendet werden.

## Voraussetzungen

### Benötigte Plugins

Für die MMO-Server (Skyblock & RPG) werden folgende Plugins benötigt:

1. **MythicMobs** (v5.x+)
   - Website: https://www.mythicmobs.net/
   - Für: Custom Items, Mobs, Skills

2. **Citizens** (v2.x+)
   - Website: https://wiki.citizensnpcs.co/
   - Für: NPCs und Dialoge

3. **Quest-Plugin** (z.B. BetonQuest, Quests, oder QuestCreator)
   - Für: Quest-System

4. **Skills/Classes Plugin** (z.B. SkillAPI, Heroes, oder MMOCore)
   - Für: Klassen-System und Skills

5. **Dungeons Plugin** (z.B. MythicDungeons oder DungeonsXL)
   - Für: Instanzierte Dungeons

### Minecraft Server

- Minecraft Version: 1.21.1
- Server-Software: Paper
- Proxy: Velocity

## Installation

### 1. Repository klonen

```bash
git clone https://github.com/Festas/MinecraftMMO.git
```

### 2. Dateien auf Server kopieren

#### MythicMobs Items

```bash
# Skyblock
cp skyblock/plugins/MythicMobs/Items/*.yml /pfad/zum/server/plugins/MythicMobs/Items/

# RPG
cp rpg/plugins/MythicMobs/Items/*.yml /pfad/zum/server/plugins/MythicMobs/Items/
```

#### MythicMobs Mobs

```bash
# Skyblock
cp skyblock/plugins/MythicMobs/Mobs/*.yml /pfad/zum/server/plugins/MythicMobs/Mobs/

# RPG
cp rpg/plugins/MythicMobs/Mobs/*.yml /pfad/zum/server/plugins/MythicMobs/Mobs/
```

#### MythicMobs Skills

```bash
# Skyblock
cp skyblock/plugins/MythicMobs/Skills/*.yml /pfad/zum/server/plugins/MythicMobs/Skills/

# RPG
cp rpg/plugins/MythicMobs/Skills/*.yml /pfad/zum/server/plugins/MythicMobs/Skills/
```

#### Quests

```bash
# Skyblock
cp skyblock/plugins/BetonQuest/*.yml /pfad/zum/server/plugins/BetonQuest/

# RPG
cp rpg/plugins/BetonQuest/*.yml /pfad/zum/server/plugins/BetonQuest/
```

#### NPCs

```bash
# Skyblock
cp skyblock/plugins/Citizens/*.yml /pfad/zum/server/plugins/Citizens/

# RPG
cp rpg/plugins/Citizens/*.yml /pfad/zum/server/plugins/Citizens/
```

### 3. Plugins neu laden

Nach dem Kopieren der Dateien:

```
/mm reload          # MythicMobs neu laden
/citizens reload    # Citizens neu laden
/quests reload      # Quest-Plugin neu laden
/skills reload      # Skills-Plugin neu laden
```

## Anpassungen vornehmen

### Neue Items erstellen

1. Gehe zu `skyblock/plugins/MythicMobs/Items/` oder `rpg/plugins/MythicMobs/Items/`
2. Erstelle eine neue `.yml` Datei (z.B. `my_sword.yml`)
3. Verwende das Format aus `example_sword.yml`
4. Passe Stats, Enchantments und Lore an
5. Kopiere die Datei auf den Server und lade MythicMobs neu

### Neue Mobs erstellen

1. Gehe zu `skyblock/plugins/MythicMobs/Mobs/` oder `rpg/plugins/MythicMobs/Mobs/`
2. Erstelle eine neue `.yml` Datei
3. Verwende das Format aus `example_boss.yml` oder `zombie_warrior.yml`
4. Definiere Health, Damage, Skills und Drops
5. Kopiere die Datei auf den Server und lade MythicMobs neu

### Neue Quests erstellen

1. Gehe zu `skyblock/plugins/BetonQuest/` oder `rpg/plugins/BetonQuest/`
2. Erstelle eine neue `.yml` Datei
3. Verwende das Format aus `example_quest.yml`
4. Definiere Stages, Requirements und Rewards
5. Kopiere die Datei auf den Server und lade das Quest-Plugin neu

### Neue Klassen/Skills erstellen

1. Gehe zu `skyblock/plugins/MMOCore/classes/` oder `rpg/plugins/MMOCore/classes/`
2. Erstelle eine neue `.yml` Datei
3. Verwende das Format aus `warrior.yml` oder `mage.yml`
4. Definiere Stats, Skills und Progression
5. Kopiere die Datei auf den Server und lade das Skills-Plugin neu

## Testing

### Items testen

```
/mm items get ITEM_NAME [Anzahl]
```

### Mobs spawnen

```
/mm mobs spawn MOB_NAME [Anzahl]
```

### Quests testen

```
/quests give [Spieler] QUEST_NAME
```

### Skills testen

```
/skills give [Spieler] SKILL_NAME
```

## Best Practices

1. **Backups**: Erstelle immer Backups, bevor du Änderungen vornimmst
2. **Testing**: Teste neue Items/Mobs auf einem Test-Server
3. **Naming**: Halte dich an die Naming Conventions in den README-Dateien
4. **Documentation**: Dokumentiere neue Items und Mobs in den Lore-Texten
5. **Balance**: Achte auf Balance zwischen verschiedenen Items/Klassen
6. **Git**: Committe Änderungen regelmäßig mit aussagekräftigen Commit-Messages

## Troubleshooting

### Items funktionieren nicht

- Überprüfe die YAML-Syntax (Einrückungen!)
- Stelle sicher, dass die Item-ID korrekt ist
- Lade MythicMobs neu: `/mm reload`
- Prüfe die Console auf Fehler

### Mobs spawnen nicht

- Überprüfe die Mob-Type Syntax
- Stelle sicher, dass alle referenzierten Skills existieren
- Überprüfe die Spawn-Bedingungen
- Prüfe die Console auf Fehler

### Quests starten nicht

- Überprüfe Quest-Requirements (Level, vorherige Quests)
- Stelle sicher, dass NPCs korrekt konfiguriert sind
- Prüfe die Quest-Stages Syntax
- Schaue in die Plugin-Logs

## Weitere Ressourcen

- MythicMobs Wiki: https://git.mythiccraft.io/mythiccraft/MythicMobs/-/wikis/home
- Citizens Wiki: https://wiki.citizensnpcs.co/
- Paper Dokumentation: https://docs.papermc.io/

## Support

Bei Fragen oder Problemen:
1. Überprüfe die Beispiel-Dateien in diesem Repository
2. Schaue in die Plugin-Dokumentationen
3. Prüfe die Server-Logs auf Fehler
