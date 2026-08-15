# Checklisten-Templates - MinecraftMMO

Wiederverwendbare Checklisten für neue Inhalte, Balance und Testing.

> **ℹ️ Stand 26.2:** Checklisten rund um MMO-Content (**Skyblock**/**RPG**: Klassen, Items, Mobs, Quests, Dungeons) sind **Archiv**, da diese Server eingestellt werden. Aktiv relevant sind die Checklisten für **Lobby** und **Survival**.

---

## 1. Content-Erstellungs-Checkliste

### Neues Item (Waffe/Rüstung)

#### Design & Konzept
- [ ] Item-Name festgelegt
- [ ] Item-Typ definiert (Schwert, Bogen, Rüstung, etc.)
- [ ] Tier bestimmt (Common - Mythic)
- [ ] Thematik/Design-Richtung klar
- [ ] Ziel-Level-Range definiert

#### Stats & Balance
- [ ] Base-Damage/Defense festgelegt
- [ ] Sekundär-Stats definiert (Crit, Speed, etc.)
- [ ] Stat-Werte im Vergleich zu ähnlichen Items balanced
- [ ] Klassen-Requirements geprüft
- [ ] Level-Requirements gesetzt

#### Special Features
- [ ] Active Ability definiert (falls vorhanden)
- [ ] Passive Ability definiert (falls vorhanden)
- [ ] Ability-Cooldowns festgelegt
- [ ] Ability-Mana-Kosten festgelegt
- [ ] Set-Bonus definiert (falls Teil eines Sets)

#### Lore & Flavor
- [ ] Display-Name mit Farben
- [ ] Lore-Text geschrieben
- [ ] Lore passt zum Netzwerk-Thema

#### Technische Umsetzung
- [ ] MMOItems YAML erstellt
- [ ] Oraxen-Integration (falls Custom Texture)
- [ ] CustomModelData konfiguriert
- [ ] Item in-game getestet

#### Verteilung & Drops
- [ ] Drop-Source(n) definiert (Mob, Boss, Dungeon, Quest)
- [ ] Drop-Chance festgelegt
- [ ] Alternative Quellen (Shop, Crafting) definiert

#### Dokumentation
- [ ] Item-Eintrag in internen Docs
- [ ] Screenshot erstellt
- [ ] Fundort dokumentiert

---

### Neuer Mob/Boss

#### Design & Konzept
- [ ] Mob-Name festgelegt
- [ ] Mob-Typ definiert (Zombie, Skeleton, Custom Model)
- [ ] Thematik/Lore definiert
- [ ] Level festgelegt
- [ ] Elite/Boss-Status geklärt

#### Stats
- [ ] Health (HP) festgelegt
- [ ] Base-Damage festgelegt
- [ ] Armor/Defense festgelegt
- [ ] Movement-Speed festgelegt
- [ ] Attack-Speed festgelegt

#### Skills & Abilities
- [ ] Skill-Liste erstellt
- [ ] Skill-Cooldowns definiert
- [ ] Skill-Damage/Effects balanciert
- [ ] Skill-Targeting definiert (Spieler, Random, etc.)
- [ ] Ultimate-Ability (für Bosse) definiert

#### Loot-Table
- [ ] Common-Drops definiert
- [ ] Rare-Drops definiert
- [ ] Guaranteed-Drops definiert
- [ ] Currency-Drops festgelegt
- [ ] XP-Reward festgelegt

#### Spawning
- [ ] Spawn-Location(en) festgelegt
- [ ] Spawn-Conditions definiert
- [ ] Respawn-Timer festgelegt
- [ ] Spawn-Gruppe/Pack-Größe (falls relevant)
- [ ] Despawn-Conditions definiert

#### Testing
- [ ] Mob gespawnt und getestet
- [ ] Skills funktionieren korrekt
- [ ] HP/Damage balanced für Level
- [ ] Loot-Drops verifiziert
- [ ] Mit Gruppe getestet (für Bosse)

#### Dokumentation
- [ ] Mob-Eintrag in internen Docs
- [ ] Spawn-Location dokumentiert
- [ ] Notable-Drops dokumentiert
- [ ] Empfohlenes Level/Gruppe dokumentiert

---

### Neue Quest

#### Story & Design
- [ ] Quest-Story geschrieben
- [ ] Quest-Kategorie definiert (Main, Side, Daily, etc.)
- [ ] Quest-Name festgelegt
- [ ] Empfohlenes Level definiert
- [ ] Geschätzte Completion-Zeit

#### Requirements
- [ ] Level-Requirement definiert
- [ ] Vorherige Quests definiert (falls Quest-Chain)
- [ ] Klassen-Requirement (falls vorhanden)
- [ ] Item-Requirements (falls vorhanden)

#### Objectives
- [ ] Haupt-Objectives definiert
- [ ] Optional-Objectives definiert (falls vorhanden)
- [ ] Objective-Beschreibungen klar formuliert
- [ ] Completion-Criteria eindeutig

#### NPCs
- [ ] Quest-Giver-NPC erstellt/identifiziert
- [ ] Quest-Completion-NPC definiert
- [ ] Zwischen-NPCs (falls vorhanden)
- [ ] Dialoge geschrieben

#### Rewards
- [ ] XP-Reward festgelegt
- [ ] Currency-Reward festgelegt
- [ ] Item-Rewards definiert
- [ ] Reputation/Achievement (falls relevant)

#### Technical
- [ ] BetonQuest YAML erstellt
- [ ] Objectives konfiguriert
- [ ] Conditions konfiguriert
- [ ] Events konfiguriert
- [ ] NPC-Integration abgeschlossen

#### Testing
- [ ] Quest angenommen
- [ ] Alle Objectives durchgespielt
- [ ] Quest abgeschlossen
- [ ] Rewards verifiziert
- [ ] Follow-up-Quest getestet

#### Dokumentation
- [ ] Quest-Eintrag in Docs
- [ ] Quest-Chain dokumentiert (falls relevant)
- [ ] Notable-Rewards dokumentiert

---

### Neuer Dungeon

#### Konzept & Design
- [ ] Dungeon-Name festgelegt
- [ ] Dungeon-Thema definiert
- [ ] Schwierigkeitsgrad definiert (Normal/Hard/Master)
- [ ] Empfohlene Spieler-Anzahl festgelegt
- [ ] Empfohlenes Level festgelegt
- [ ] Geschätzte Clear-Zeit

#### World-Building
- [ ] Dungeon-Map gebaut
- [ ] Spawn-Room erstellt
- [ ] Boss-Arena(s) erstellt
- [ ] Loot-Room erstellt
- [ ] Exit-Portal platziert
- [ ] Schematic gespeichert

#### Mobs & Encounters
- [ ] Trash-Mobs definiert
- [ ] Elite-Mobs definiert
- [ ] Mini-Bosse definiert (falls vorhanden)
- [ ] End-Boss definiert
- [ ] Spawn-Points platziert
- [ ] Pathing getestet

#### Boss-Mechaniken
- [ ] Phase 1 Mechanik definiert
- [ ] Phase 2 Mechanik definiert (falls vorhanden)
- [ ] Enrage-Timer festgelegt
- [ ] Boss-Abilities balanciert
- [ ] Boss-HP für Gruppen-Größe skaliert

#### Loot
- [ ] Trash-Mob-Loot-Table
- [ ] Boss-Loot-Table
- [ ] Chest-Loot definiert
- [ ] Completion-Rewards definiert
- [ ] Rare-Drops definiert

#### Entry & Exit
- [ ] Dungeon-Portal erstellt
- [ ] Entry-Requirements konfiguriert
- [ ] Exit-Teleport funktioniert
- [ ] Instance-System getestet

#### Testing
- [ ] Solo-Clear-Test (falls möglich)
- [ ] Gruppen-Clear-Test
- [ ] Alle Bosse getestet
- [ ] Time-Limit angemessen
- [ ] Loot-Balance geprüft

#### Dokumentation
- [ ] Dungeon-Guide geschrieben
- [ ] Boss-Mechaniken dokumentiert
- [ ] Empfohlene Gruppenzusammensetzung
- [ ] Notable-Loot dokumentiert
- [ ] Map/Layout dokumentiert

---

### Neue Klasse

#### Konzept
- [ ] Klassen-Name festgelegt
- [ ] Klassen-Rolle definiert (Tank, DPS, Support, Hybrid)
- [ ] Thematik/Fantasy klar
- [ ] Playstyle-Beschreibung geschrieben

#### Stats & Equipment
- [ ] Haupt-Stats definiert (Stärke, Int, etc.)
- [ ] Sekundär-Stats definiert
- [ ] Erlaubte Waffentypen festgelegt
- [ ] Erlaubte Rüstungstypen festgelegt
- [ ] Stat-Scaling pro Level definiert

#### Skills
- [ ] Skill-Tree-Struktur geplant
- [ ] Active Skills definiert (min. 4-6)
- [ ] Passive Skills definiert
- [ ] Ultimate-Ability definiert
- [ ] Skill-Unlock-Levels festgelegt
- [ ] Skill-Costs (Mana, Cooldown) balanciert

#### Starter-Setup
- [ ] Starter-Waffe erstellt
- [ ] Starter-Rüstung erstellt
- [ ] Starter-Skills definiert
- [ ] Tutorial-Informationen (optional)

#### Balance
- [ ] PvE-Balance mit anderen Klassen verglichen
- [ ] PvP-Balance getestet (falls relevant)
- [ ] Solo-Viability geprüft
- [ ] Gruppen-Rolle geprüft

#### Testing
- [ ] Klasse wählen getestet
- [ ] Alle Skills getestet
- [ ] Level 1-25 Progression getestet
- [ ] Endgame-Viability getestet
- [ ] Mit verschiedenen Equipment-Setups getestet

#### Dokumentation
- [ ] Klassen-Guide geschrieben
- [ ] Skill-Overview dokumentiert
- [ ] Empfohlene Stat-Priority
- [ ] Empfohlene Equipment-Sets
- [ ] Playstyle-Tipps

---

## 2. Balance-Checkliste

### Item-Balance

#### Stat-Vergleich
- [ ] Stats verglichen mit ähnlichen Items
- [ ] Stats angemessen für Tier
- [ ] Stats angemessen für Level-Requirement
- [ ] Keine Outliers (zu stark/schwach)

#### Power-Level
- [ ] DPS/Protection im erwarteten Range
- [ ] Nicht strictly better als Alternativen
- [ ] Trade-offs vorhanden (hoher Dmg = niedrige Def, etc.)

#### Progression
- [ ] Item passt in Progression-Curve
- [ ] Upgrade-Path vorhanden
- [ ] Nicht zu früh/spät verfügbar

#### Accessibility
- [ ] Nicht zu schwer zu bekommen für Power-Level
- [ ] Nicht zu leicht zu bekommen für Power-Level
- [ ] Drop-Rate angemessen

---

### Mob/Boss-Balance

#### Stats
- [ ] HP angemessen für Level
- [ ] Damage nicht one-shot (außer bei Spezial-Attacks)
- [ ] Armor/Defense im erwarteten Range

#### Difficulty
- [ ] Solo-killable (für normale Mobs)
- [ ] Gruppen-Größe angemessen (für Bosse)
- [ ] Skills telegrafiert (Spieler können reagieren)
- [ ] Nicht zu frustrating

#### Rewards
- [ ] Loot angemessen für Difficulty
- [ ] XP angemessen für Time-Investment
- [ ] Currency-Drops balanced

---

### Quest-Balance

#### Time Investment
- [ ] Completion-Zeit angemessen für Rewards
- [ ] Nicht zu grindy
- [ ] Objectives abwechslungsreich

#### Difficulty
- [ ] Quest schaffbar für empfohlenes Level
- [ ] Nicht zu schwer für Solo-Spieler (außer explizite Gruppen-Quests)
- [ ] Clear Instructions

#### Rewards
- [ ] XP angemessen für Quest-Länge
- [ ] Item-Rewards nützlich für Level
- [ ] Currency-Rewards balanced

---

## 3. Testing-Checkliste

### Pre-Release Testing

#### Funktionalität
- [ ] Feature funktioniert wie geplant
- [ ] Keine Console-Errors
- [ ] Keine Client-Crashes
- [ ] Keine Lag-Spikes

#### Balance
- [ ] Balance-Checkliste durchgearbeitet (siehe oben)
- [ ] Feedback von Test-Spielern eingeholt
- [ ] Anpassungen vorgenommen

#### Integration
- [ ] Kompatibel mit existierendem Content
- [ ] Keine Konflikte mit anderen Features
- [ ] Passt ins Gesamt-Balancing

#### Edge Cases
- [ ] Extremfälle getestet (sehr niedrig/hoch Level)
- [ ] Multiple Spieler gleichzeitig getestet
- [ ] Server-Restart getestet (Persistence)

---

### Post-Release Monitoring

#### Performance
- [ ] Server-Performance überwacht (TPS)
- [ ] Keine Memory-Leaks
- [ ] Database-Performance OK

#### Player Feedback
- [ ] Feedback gesammelt (Chat, Discord)
- [ ] Bug-Reports gesammelt
- [ ] Balance-Feedback gesammelt

#### Metrics
- [ ] Usage-Statistiken geprüft (Plan)
- [ ] Drop-Rates verifiziert
- [ ] Clear-Rates geprüft (für Dungeons)

#### Adjustments
- [ ] Hot-Fixes deployed (falls nötig)
- [ ] Balance-Tweaks vorgenommen
- [ ] Bugs gefixt

---

## 4. Server-Update-Checkliste

### Pre-Update
- [ ] Update-Notes gelesen
- [ ] Breaking-Changes identifiziert
- [ ] Config-Änderungen notwendig?
- [ ] Plugin-Kompatibilität geprüft
- [ ] Backup erstellt (Welt, DB, Configs)
- [ ] Spieler informiert (Ankündigung)
- [ ] Wartungsfenster geplant

### Update-Prozess
- [ ] Wartungsmodus aktiviert
- [ ] Spieler gekickt
- [ ] Server gestoppt
- [ ] Plugin(s) ersetzt
- [ ] Config(s) aktualisiert
- [ ] Server gestartet
- [ ] Console auf Errors geprüft

### Post-Update Testing
- [ ] Server startet ohne Errors
- [ ] Kritische Features getestet
- [ ] Performance-Check (TPS, RAM)
- [ ] Player-Login getestet
- [ ] Database-Connectivity geprüft

### Release
- [ ] Wartungsmodus deaktiviert
- [ ] Spieler informiert (Update abgeschlossen)
- [ ] Changelog gepostet
- [ ] Monitoring verstärkt (erste Stunden)

### Rollback (falls nötig)
- [ ] Server gestoppt
- [ ] Backup wiederhergestellt
- [ ] Alte Plugin-Version restauriert
- [ ] Server gestartet
- [ ] Spieler informiert
- [ ] Post-Mortem-Analyse

---

## 5. Content-Release-Checkliste

### Vor Release

#### Content-Qualität
- [ ] Alle Content-Pieces fertiggestellt
- [ ] Testing abgeschlossen
- [ ] Balance-Check abgeschlossen
- [ ] Keine bekannten Bugs

#### Dokumentation
- [ ] Interne Docs aktualisiert
- [ ] Changelog geschrieben
- [ ] Player-Guide geschrieben (falls nötig)

#### Marketing/Ankündigung
- [ ] Discord-Ankündigung vorbereitet
- [ ] In-Game-Ankündigung vorbereitet
- [ ] Screenshots/Videos erstellt
- [ ] Release-Datum festgelegt

### Release

#### Deployment
- [ ] Config-Files auf Server kopiert
- [ ] Plugins reloaded
- [ ] Content in-game verifiziert

#### Ankündigung
- [ ] Discord-Post veröffentlicht
- [ ] In-Game-Broadcast gesendet
- [ ] Changelog veröffentlicht

### Post-Release

#### Monitoring
- [ ] Server-Performance überwacht
- [ ] Player-Feedback gesammelt
- [ ] Bug-Reports gesammelt

#### Support
- [ ] Player-Fragen beantwortet
- [ ] Bugs gefixt (falls vorhanden)
- [ ] Hot-Fixes deployed (falls nötig)

#### Analysis
- [ ] Usage-Statistiken analysiert
- [ ] Success-Metrics geprüft
- [ ] Lessons-Learned dokumentiert

---

## 6. Sicherheits-Checkliste

### Neue Configs

- [ ] Keine Passwörter im Klartext
- [ ] Keine API-Keys committed (Git)
- [ ] Database-Credentials sicher gespeichert
- [ ] Permissions korrekt konfiguriert

### Neue Commands

- [ ] Admin-Commands haben Permissions
- [ ] Exploit-Potenzial geprüft
- [ ] Input-Validation vorhanden

### Neue Items/Mobs

- [ ] Keine Item-Duplication möglich
- [ ] Keine Economy-Exploits
- [ ] Keine Performance-Killer (z.B. unendliche Loops)

---

## Template für eigene Checklisten

### Kategorie 1
- [ ] Item 1
- [ ] Item 2
- [ ] Item 3

### Kategorie 2
- [ ] Item 1
- [ ] Item 2

---

**Letzte Aktualisierung:** 2026-04-10

**Hinweis:** Diese Checklisten sind als Richtlinien gedacht - nicht jeder Punkt ist für jeden Content-Typ relevant. Anpassen nach Bedarf!
