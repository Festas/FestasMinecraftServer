# Beitragsrichtlinien

Vielen Dank für dein Interesse an diesem Projekt!

## Struktur beibehalten

Bitte halte dich an die bestehende Struktur:

```
server/plugins/
├── MythicMobs/
│   ├── Items/
│   ├── Mobs/
│   └── Skills/
├── BetonQuest/
├── MMOCore/classes/
├── MythicDungeons/
└── Citizens/
```

## Naming Conventions

### Items

Format: `KATEGORIE_NAME_TIER`

Beispiele:
- `SWORD_FIRE_LEGENDARY`
- `ARMOR_ICE_CHESTPLATE_EPIC`
- `TOOL_DRILL_MYTHIC`

Tiers: `COMMON`, `UNCOMMON`, `RARE`, `EPIC`, `LEGENDARY`, `MYTHIC`

### Mobs

Format: `MOB_NAME_LEVEL`

Beispiele:
- `ZOMBIE_WARRIOR_10`
- `DRAGON_FIRE_50`
- `BOSS_DEMON_100`

### Quests

Format: `KATEGORIE_NUMMER_NAME`

Beispiele:
- `MAIN_01_THE_BEGINNING`
- `SIDE_15_LOST_TREASURE`
- `DAILY_MINING_QUEST`

### Skills

Beschreibende Namen in PascalCase:

Beispiele:
- `FireballBarrage`
- `HealingAura`
- `SummonMinions`

### NPCs

Format: `FUNKTION_NAME`

Beispiele:
- `VILLAGE_ELDER`
- `MERCHANT_WEAPONS`
- `TRAINER_WARRIOR`

## YAML-Syntax

- Verwende **2 Leerzeichen** für Einrückungen (keine Tabs!)
- Strings mit Sonderzeichen in Anführungszeichen setzen
- Listen mit `-` kennzeichnen
- Achte auf korrekte Einrückung

### Beispiel

```yaml
ITEM_NAME:
  Id: DIAMOND_SWORD
  Display: '&6&lItem Name'
  Lore:
  - '&7Line 1'
  - '&7Line 2'
  Enchantments:
  - SHARPNESS:5
  - UNBREAKABLE:3
```

## Balancing

### Items

- Common: Basis-Werte für das Level
- Uncommon: +25% Werte
- Rare: +50% Werte
- Epic: +100% Werte
- Legendary: +200% Werte
- Mythic: +400% Werte + Spezial-Effekte

### Mobs

Level-basiert:
- Health: Level * 50
- Damage: Level * 2.5
- Armor: Level * 0.5

Boss-Mobs:
- Health: * 100
- Damage: * 5
- Spezial-Skills

## Farbcodes

Verwende Minecraft Farbcodes:

- `&0` - Schwarz
- `&1` - Dunkelblau
- `&2` - Dunkelgrün
- `&3` - Dunkel-Aqua
- `&4` - Dunkelrot
- `&5` - Dunkel-Lila
- `&6` - Gold
- `&7` - Grau
- `&8` - Dunkelgrau
- `&9` - Blau
- `&a` - Grün
- `&b` - Aqua
- `&c` - Rot
- `&d` - Pink
- `&e` - Gelb
- `&f` - Weiß
- `&l` - Fett
- `&m` - Durchgestrichen
- `&n` - Unterstrichen
- `&o` - Kursiv
- `&r` - Reset

### Tier-Farben

- Common: `&f` (Weiß)
- Uncommon: `&a` (Grün)
- Rare: `&9` (Blau)
- Epic: `&5` (Lila)
- Legendary: `&6` (Gold)
- Mythic: `&d` (Pink)

## Dokumentation

Jede neue Datei sollte einen Kommentar am Anfang haben:

```yaml
# Name des Items/Mobs/etc.
# Kurze Beschreibung
# Optional: Besondere Hinweise

ITEM_NAME:
  ...
```

## Testing

Vor dem Commit:

1. YAML-Syntax überprüfen
2. Auf Test-Server testen
3. Balance überprüfen
4. Mit anderen Items/Mobs vergleichen

## Git Workflow

1. Branch erstellen: `git checkout -b feature/neue-items`
2. Änderungen vornehmen
3. Committen: `git commit -m "Add: Neue Feuer-Waffen"`
4. Pushen: `git push origin feature/neue-items`
5. Pull Request erstellen

### Commit Messages

Format: `Typ: Beschreibung`

Typen:
- `Add:` - Neue Dateien/Features
- `Update:` - Änderungen an existierenden Dateien
- `Fix:` - Fehlerbehebungen
- `Remove:` - Dateien entfernt
- `Balance:` - Balance-Änderungen
- `Docs:` - Dokumentation

Beispiele:
- `Add: Legendary Fire Sword`
- `Update: Increase Fire Demon Boss health`
- `Fix: Correct syntax error in warrior.yml`
- `Balance: Reduce Mage damage by 10%`

## Qualitätskriterien

Bevor du einen Pull Request erstellst:

- [ ] YAML-Syntax ist korrekt
- [ ] Naming Conventions eingehalten
- [ ] Auf Test-Server getestet
- [ ] Balance ist angemessen
- [ ] Dokumentation vorhanden
- [ ] Keine Debug-Dateien committed
- [ ] README aktualisiert (falls nötig)

## Fragen?

Bei Fragen oder Unklarheiten:
- Schaue dir die Beispiel-Dateien an
- Lies die Plugin-Dokumentation
- Frage den Repository-Owner
