# MinecraftMMO Netzwerk — Vollständige Server-Übersicht

> Dieses Dokument enthält alle wichtigen Informationen über das MinecraftMMO Server-Netzwerk und dient gleichzeitig als Grundlage für die Erstellung einer Website.

---

## Inhaltsverzeichnis

1. [Netzwerk-Überblick](#1-netzwerk-überblick)
2. [Server-Details](#2-server-details)
   - [Lobby](#21-lobby)
   - [Survival / Tycoon](#22-survival--tycoon)
   - [Skyblock (MMO) — Archiv](#23-skyblock-mmo--archiv-wird-eingestellt)
   - [RPG (MMO) — Archiv](#24-rpg-mmo--archiv-wird-eingestellt)
3. [Klassen-System](#3-klassen-system)
4. [Item-System](#4-item-system)
5. [Economy-System](#5-economy-system)
6. [Technische Infrastruktur](#6-technische-infrastruktur)
7. [Besonderheiten & Alleinstellungsmerkmale](#7-besonderheiten--alleinstellungsmerkmale)
8. [Website-Prompt für AI Agenten](#8-website-prompt-für-ai-agenten)

---

## 1. Netzwerk-Überblick

**Server-IP:** `mc.festas-builds.com`  
**Minecraft-Version:** 26.2 (Paper)  
**Proxy:** Velocity  
**Bedrock-Support:** Ja (Geyser + Floodgate — Handy, Konsole, Windows 10 Edition)  
**Sprache:** Deutsch (primär)  
**Status:** Umstellung auf 26.2 — Fokus auf Lobby & Survival

> **⚠️ Umbruch:** Aktiv weiterentwickelt werden derzeit nur **Lobby** und **Survival** (Plugins frisch aufgeräumt). Die MMO-Server **Skyblock** und **RPG** werden **zeitnah eingestellt** und durch **zwei neue Server** ersetzt (noch nicht festgelegt). Die Skyblock-/RPG-Abschnitte in diesem Dokument gelten als **Archiv/Referenz**.

### Netzwerk-Architektur

```
Internet
    │
    ▼
Velocity Proxy  (mc.festas-builds.com)
    │
    ├──► Lobby          Haupt-Hub, Server-Navigation           [AKTIV]
    ├──► Survival       Standard Survival + Tycoon-Gamemode     [AKTIV]
    ├──► Skyblock       MMO-Skyblock mit RPG-Elementen          [ARCHIV → Abbau]
    └──► RPG            Vollständiger MMO-RPG Open-World-Server  [ARCHIV → Abbau]

    Geplant: 2 neue Server ersetzen Skyblock & RPG (noch offen)
```

Der aktive Fokus liegt auf dem **Survival/Tycoon**-Erlebnis und der **Lobby** als Hub. Die auslaufenden MMO-Server kombinierten Konzepte aus **Hypixel Skyblock** (Progression, Minions, Collections) und **Wynncraft** (Klassen, Quests, Story) — diese Inhalte werden mit der Abschaltung durch zwei neue Server-Konzepte abgelöst.

---

## 2. Server-Details

### 2.1 Lobby

**Funktion:** Willkommens-Hub und Server-Navigation  
**Version:** Paper 26.2

#### Features
- Interaktive **NPC-Server-Selector** (FancyNpcs): Direkter Zugang zu RPG, Survival/Tycoon, Skyblock
- **DeluxeMenus-GUIs**: Server-Selector, Regeln, Netzwerk-Guide
- **Doppelsprung** (kosmetisch, kein Gameplay-Element)
- Willkommensnachrichten bei erstem Join und Wiederkehr
- **Void-Fall-Schutz** (automatischer Teleport zum Spawn)
- Inventar-Schutz: Nur der Navigator-Kompass ist erlaubt
- Weltschutz via WorldGuard (kein Bauen, kein PvP, kein Schaden)
- **AFK-System**: Auto-Kick nach 15 Minuten Inaktivität
- **Hologramme** für Spielerzahlen und Server-Info (DecentHolograms)

#### Besonderheiten
- Keine Gameplay-Elemente — reine Navigation
- Read-only World

---

### 2.2 Survival / Tycoon

**Funktion:** Klassisches Survival mit integriertem Generator-Tycoon-Gamemode  
**Version:** Paper 26.2  
**Welten:** `tycoon` (Hauptwelt), `town` (Stadtbereich), `freebuild` (Kreativbereich)

#### Tycoon-Gamemode

Der Tycoon-Gamemode ist das Herzstück des Survival-Servers. Spieler errichten Generatoren auf ihrem Plot, sammeln produzierte Ressourcen und verkaufen diese, um in Rängen aufzusteigen.

**Spielablauf:**
1. Join → Tutorial (automatisch für neue Spieler)
2. `/tycoon start` → Plot wird zugewiesen (kostenlos)
3. Generatoren platzieren → Items produzieren
4. Sell Wand nutzen → Items aus Containern verkaufen
5. Geld verdienen → `/rankup` → Plot-Reset → Neuer Rang + neuer Generator
6. 25 Ränge durchlaufen (Erde → Bedrock)
7. `/prestige` → Alles zurücksetzen für permanenten Sell-Bonus

#### 25 Ränge (Erde → Bedrock)

| # | Rang | Kosten |
|---|------|--------|
| 1 | Erde | Gratis (Start) |
| 2 | Stein | $5.000 |
| 3 | Kohle | $25.000 |
| 4 | Eisen | $75.000 |
| 5 | Kupfer | $200.000 |
| 6 | Gold | $500.000 |
| 7 | Redstone | $1.200.000 |
| 8 | Lapis | $3.000.000 |
| 9 | Smaragd | $7.500.000 |
| 10 | Diamant | $15.000.000 |
| 11 | Obsidian | $35.000.000 |
| 12 | Netherite | $75.000.000 |
| 13 | Tuff | $150.000.000 |
| 14 | Calcit | $300.000.000 |
| 15 | Diorit | $600.000.000 |
| 16 | Andesit | $1.000.000.000 |
| 17 | Granit | $2.000.000.000 |
| 18 | Basalt | $4.000.000.000 |
| 19 | Schwarzstein | $8.000.000.000 |
| 20 | Purpur | $15.000.000.000 |
| 21 | Endstein | $25.000.000.000 |
| 22 | Prismarin | $40.000.000.000 |
| 23 | Seelenerde | $65.000.000.000 |
| 24 | Magma | $100.000.000.000 |
| 25 | Bedrock | $175.000.000.000 |

#### Prestige-System (10 Stufen)

Nach Rang 25 kann man prestigen — alles wird zurückgesetzt, aber ein permanenter Sell-Bonus bleibt für immer.

| # | Name | Sell-Bonus |
|---|------|------------|
| 1 | Bronze | +10% |
| 2 | Silber | +20% |
| 3 | Gold | +35% |
| 4 | Platin | +50% |
| 5 | Smaragd | +70% |
| 6 | Saphir | +90% |
| 7 | Rubin | +115% |
| 8 | Amethyst | +140% |
| 9 | Diamant | +170% |
| 10 | Legende | +200% |

#### Kern-Systeme

- **Sell Wand** (Markt-Zepter): Rechtsklick auf Kiste/Fass/Shulker → Alle Items automatisch verkaufen
- **Chunk Collector**: Sammelt dropped Items im 20-Block-Radius alle 3 Sekunden automatisch
- **Dynamische Börse** (`/boerse`): Preise ändern sich basierend auf Angebot und Nachfrage
- **Casino/Glücksspiel** (`/gamble`): Mit täglichem Verlustlimit
- **Boss-Events**: Nitwit-Boss-Encounters mit zufälligen Spawns
- **Tägliche Belohnungen** (`/daily`): Streak-System, bricht ab bei >48h Pause
- **23 Achievements**: Kategorien: Verkäufe, Ränge, Casino, Streaks, Prestige, Bosse, Kontostand
- **Wöchentliche Events**: 5 verschiedene Event-Typen
- **13 Jobs** (Jobs-Plugin): Miner, Farmer, Hunter, Fisher, Builder, u.v.m.
- **Multi-Plot-System**: Progressive Plot-Limits je nach Rang (1→5 Plots + Prestige-Boni)
- **Plot-Merging**: Benachbarte Plots zusammenführen ($5 Mio.)
- **Autorank**: Spielzeit-Belohnungen bei 1h, 5h, 24h, 72h, 168h

#### Wichtige Befehle

| Befehl | Beschreibung |
|--------|-------------|
| `/tycoon start` | Tycoon starten (Plot + Erde-Rang) |
| `/rankup` | Rang aufsteigen |
| `/prestige confirm` | Prestige durchführen |
| `/daily` | Tägliche Belohnung abholen |
| `/achievements` | Achievements anzeigen |
| `/boerse` | Dynamische Marktpreise |
| `/shop` | Shop öffnen |
| `/jobs` | Jobs verwalten |
| `/gamble` | Casino öffnen |
| `/tutorial` | Tutorial wiederholen |

---

### 2.3 Skyblock (MMO) — *Archiv, wird eingestellt*

> **⚠️ Dieser Server wird zeitnah abgeschaltet** und durch einen neuen Server ersetzt. Inhalte nur noch als Referenz.

**Funktion:** MMO-Skyblock mit RPG-Elementen und vollständiger Klassen-Integration  
**Version:** Paper 26.2  
**Kapazität:** 50–100 Spieler  
**Synchronisation:** HuskSync ↔ RPG-Server

#### Island-System (SuperiorSkyblock2)
- **Persönliche oder Coop-Islands**
- Island-Level-System und Island-Upgrades
- Custom Island-Schematics
- Island-Warps für Besucher
- Top-Islands Leaderboard
- `/is create`, `/is invite`, `/is upgrades`, `/is level`

#### Minion-System (JetsMinions — 8 Typen)

| Minion | Funktion |
|--------|----------|
| **Miner** | Bergbau — produziert Erze |
| **Farmer** | Ernte — baut Pflanzen an und erntet |
| **Fisher** | Angeln — fängt Fische und Items |
| **Lumberjack** | Holzfällen — fällt Bäume |
| **Slayer** | Mob-Kampf — tötet Mobs |
| **Collector** | Sammelt dropped Items im Umkreis |
| **Feeder** | Heilt und füttert andere Minions |
| **Seller** | Verkauft Items automatisch |

Minions nutzen ein Health-System und müssen gepflegt werden. Verknüpfte Kisten für automatische Lagerung.

#### MMO-Integration
- **6 Klassen** via MMOCore (identisch mit RPG-Server)
- Custom Items mit Stats via MMOItems
- Custom Mobs via MythicMobs (Community Edition)
- Skills und Fähigkeiten
- Daten bleiben beim Wechsel zu RPG erhalten (HuskSync)

#### Collection-System (AuroraCollections — 5 Kategorien)

| Kategorie | Beschreibung |
|-----------|-------------|
| **Farming** (16 Collections) | Pflanzen, Ernte-Items |
| **Mining** (8 Collections) | Erze, Steinarten |
| **Combat** (8 Collections) | Mob-Drops |
| **Foraging** (8 Collections) | Holzarten, Blätter |
| **Fishing** (4 Collections) | Fische, Meeresitems |

Jede Sammlung hat Milestones mit exklusiven Belohnungen.

#### Skyblock-Prestige-System
- 10 Prestige-Stufen mit Titeln
- Passive Buffs ab Prestige 2+
- Leaderboard und Server-Broadcasts

#### Pet-System
- 6 Haustier-Typen mit passiven Boni
- Pet-Leveling durch Mob-Kills
- Entitäten folgen dem Spieler

#### Wirtschaft
- **CoinsEngine Multi-Währungs-System**: Geld, Münzen, Tokens, Quest-Punkte, Dungeon-Marken
- **DeluxeBazaar**: Hypixel-Style Bazaar mit Instant-Buy/Sell und Order-System
- **GlobalMarketPlus**: Auktionshaus für Spieler-zu-Spieler-Handel

---

### 2.4 RPG (MMO) — *Archiv, wird eingestellt*

> **⚠️ Dieser Server wird zeitnah abgeschaltet** und durch einen neuen Server ersetzt. Inhalte nur noch als Referenz.

**Funktion:** Vollständiger MMO-RPG Server mit Open World, Quests, Dungeons, Klassen und Story  
**Version:** Paper 26.2  
**Kapazität:** 50–100 Spieler  
**Synchronisation:** HuskSync ↔ Skyblock-Server

#### Open World
Die Welt ist in **5 Level-Zonen** eingeteilt:

| Zone | Level-Bereich | Beschreibung |
|------|--------------|-------------|
| Hub-Stadt | — | Spawn-Point, Quest-Geber, Händler, Trainer |
| Zone 1 | Level 1–15 | Tutorial, erste Quests, einfache Mobs |
| Zone 2 | Level 15–30 | Story-Quests, mittlere Mobs, frühe Dungeons |
| Zone 3 | Level 30–50 | Elite-Mobs, schwere Dungeons, komplexe Quest-Chains |
| Zone 4 | Level 50–70 | Endgame-Vorbereitung, Welt-Bosse, Raid-Dungeons |
| Zone 5 | Level 70–100 | Härtester Content, epische Bosse, Legendäre Items |
| Nether | Endgame | Besondere Endgame-Zone |
| End | Endgame | Raid-Zone |

#### Quest-System (BetonQuest)
- Hauptquest-Linie mit eigenem Storysystem
- Hunderte Nebenquests
- Tägliche und wöchentliche Quests
- Quest-Chains mit Entscheidungen und Konsequenzen
- Quest-Belohnungen: XP, Geld, Items, Quest-Punkte

#### Dungeon-System (MythicDungeons)
- **10–15 instanzierte Dungeons** (separate Welten pro Gruppe)
- Level-Range: Level 15–100
- 2–4 Schwierigkeitsgrade pro Dungeon
- Boss-Kämpfe mit komplexen Mechaniken
- Exklusive Loot-Belohnungen (Epische/Legendäre Items)
- Party-System für Gruppen

#### Mob-System (MythicMobs Premium)
- Custom Mobs mit einzigartigen Fähigkeiten und Skills
- **Elite-Mobs** mit verstärkten Stats und besonderem Loot
- **Welt-Bosse** mit öffentlichen Boss-Bars und Server-weiten Ankündigungen
- Level-skalierte Gegner je nach Zone
- Umfangreiche custom Loot-Tables (RoseLoot)
- 3D Custom Models via ModelEngine

#### NPC-System (Citizens + BetonQuest)
- Quest-Geber NPCs
- Händler und Trainer
- Story-NPCs mit Dialogen und Entscheidungen
- Dynamische Interaktionen

#### RPG-Besonderheiten
- Nur auf dem RPG-Server verfügbar (nicht auf Skyblock):
  - BetonQuest (Quests)
  - Citizens (NPCs)
  - MythicCrucible (Advanced Item Creation & Crafting)
  - ModelEngine (3D-Modelle für Mobs/NPCs)
  - Instanzierte Dungeons

---

## 3. Klassen-System — *Archiv (MMO)*

> **⚠️ Gehört zu den auslaufenden MMO-Servern Skyblock & RPG.** Wird mit deren Abschaltung ersetzt.

Das Klassen-System war auf beiden MMO-Servern (RPG & Skyblock) identisch verfügbar. Fortschritt wurde via HuskSync synchronisiert.

### Die 6 Klassen

| Klasse | Archetyp | Primärwaffe | Ressource | Rüstung |
|--------|----------|-------------|-----------|---------|
| **Krieger** | Tank / Melee DPS | Schwert, Axt | Wut | Plattenrüstung |
| **Magier** | Ranged Magic DPS | Stab | Mana | Stoffrüstung |
| **Assassine** | Burst Melee DPS | Dolch (Dual Wield) | Energie | Lederrüstung |
| **Bogenschütze** | Ranged Physical DPS | Bogen / Armbrust | Fokus | Leder-/Kettenrüstung |
| **Schamane** | Support / Healer | Stab, Streitkolben | Mana | Kettenrüstung |
| **Beschwörer** | Summoner / Pet-Class | Stab | Mana + Beschwörungs-Slots | Stoffrüstung |

### Klassen-Details

#### Krieger
- **Stärken:** Hohe Verteidigung, starker Nahkampf-Schaden, Gruppenschutz
- **Ressource:** Wut — baut sich durch erlittenen und verursachten Schaden auf
- **Playstil:** Frontlinie im Kampf, zieht Feinde auf sich, nutzt Wut für mächtige Fähigkeiten
- **PvP:** Schlägt Assassinen durch hohe Defense; unterliegt Magiern (Reichweite)

#### Magier
- **Stärken:** Höchster Fernkampf-Zauber-Schaden, AoE-Fähigkeiten
- **Ressource:** Mana — klassisches System, regeneriert über Zeit
- **Playstil:** Aus sicherer Distanz zaubern, hohe Burst-Potenz, niedrige Verteidigung
- **PvP:** Dominiert Krieger durch Reichweite; verwundbar gegenüber Assassinen

#### Assassine
- **Stärken:** Höchster Burst-Schaden, hohe Mobilität, kritische Treffer
- **Ressource:** Energie — schnell regenerierend für häufige Skill-Nutzung
- **Playstil:** Schnelle Treffer-Serie, aus dem Schatten angreifen, hohe Krit-Rate
- **PvP:** Konter zu Magiern (Mobilität); verliert gegen Krieger (Defense)

#### Bogenschütze
- **Stärken:** Konstanter Fernkampf-Schaden, kein Mana-Limit
- **Ressource:** Fokus — aufbauen durch Treffer, nutzen für Spezialschüsse
- **Playstil:** Mittlere Distanz, stetige DPS, gutes Solospiel
- **PvP:** Schlägt Magier durch konstanten Schaden

#### Schamane
- **Stärken:** Heilung für Gruppe, Buffs, vielseitige Unterstützung
- **Ressource:** Mana — fokussiert auf effiziente Heilung
- **Playstil:** Hinter der Frontlinie, Gruppe am Leben halten, Buffs verteilen
- **PvP:** Sehr stark in Gruppenszenarien; solo eher defensiv

#### Beschwörer
- **Stärken:** Crowd Control, vielseitige Beschwörungen, passive Schadensdelegation
- **Ressource:** Mana + Beschwörungs-Slots (limitierte Anzahl aktiver Kreaturen)
- **Playstil:** Armada von Minions steuern, Feinde verlangsamen, indirekter Kampf
- **PvP:** Sehr vielseitig durch Crowd Control

### Skill-System

- **Max Level:** 100
- **Skill-Punkte:** 1 Punkt pro Level (max. 100 Punkte)
- **Skill-Kategorien:**
  - **Passive Skills** — permanente Stat-Boni
  - **Active Skills** — aktivierbare Fähigkeiten mit Cooldown
  - **Ultimate Skill** — mächtige Fähigkeit, freischaltbar ab Level 50
- **Klassenwechsel:** Möglich, Cooldown: 7 Tage; Level & XP bleiben erhalten

### Primär-Stats

| Stat | Abkürzung | Wirkung |
|------|-----------|---------|
| Stärke | STR | Erhöht physischen Schaden |
| Intelligenz | INT | Erhöht magischen Schaden und Mana |
| Geschicklichkeit | DEX | Erhöht krit. Trefferchance und Ausweichen |
| Ausdauer | VIT | Erhöht max. Lebenspunkte |
| Weisheit | WIS | Erhöht Heilung und Mana-Regeneration |

### Level-Progression

| Level-Bereich | Meilenstein |
|---------------|------------|
| 1–10 | Tutorial & Grundlagen, Basis-Skills |
| 11–25 | Frühe Dungeons & Zonen, erste Spezialisierung |
| 26–50 | Mittlere Dungeons & Raids, Ultimate freischaltbar (Lv. 50) |
| 51–75 | Schwere Dungeons & Raids, erweiterte Spezialisierung |
| 76–100 | Endgame Content, alle Skills maximal |

---

## 4. Item-System

### Item-Pipeline

```
Oraxen (Textur/Model) → MMOItems (Stats/Fähigkeiten) → MythicCrucible (Crafting-Rezept)
```

### 7 Item-Tiers

| # | Name | Farbe | Seltenheit |
|---|------|-------|------------|
| 1 | Gewöhnlich (Common) | Weiß | Überall |
| 2 | Ungewöhnlich (Uncommon) | Grün | Häufig |
| 3 | Selten (Rare) | Blau | Gelegentlich |
| 4 | Episch (Epic) | Lila | Selten |
| 5 | Legendär (Legendary) | Gold | Sehr selten |
| 6 | Mythisch (Mythic) | Pink | Extrem selten |
| 7 | Göttlich (Divine) | Hellrot | _(geplant)_ |

### Item-Kategorien

- **Waffen:** Schwerter, Äxte, Bögen/Armbrüste, Stäbe, Dolche, Streitkolben
- **Rüstung:** Helme, Brustplatten, Hosen, Stiefel
- **Schmuck:** Ringe, Amulette
- **Offhand:** Schilde, Orbs, Totems, Köcher
- **Verbrauchsgüter:** Tränke, Essen, Scrolls, Buff-Items

### Sekundär-Stats auf Items

- Kritische Trefferchance & Kritischer Schaden-Multiplikator
- Angriffsgeschwindigkeit & Bewegungsgeschwindigkeit
- Cooldown-Reduktion
- Lebensraub (Leech)
- Mana-Regeneration

---

## 5. Economy-System

### Survival-Economy (separat, nicht verbunden mit MMO)

- **Hauptwährung:** Geld ($) via CMI/Vault
- Verdienen durch: Jobs, Shop-Verkäufe, Tycoon-Generatoren, Bosses, Achievements
- **ShopGUIPlus** für Item-Kauf und -Verkauf
- **GlobalMarketPlus** Auktionshaus für Spieler-Handel
- **Dynamische Börse** (`/boerse`): Preise reagieren auf Angebot und Nachfrage

### MMO-Economy (RPG & Skyblock — synchronisiert)

Das MMO-Netzwerk nutzt **5 verschiedene Währungen**, alle serverübergreifend synchronisiert:

| Währung | Symbol | Verwendung |
|---------|--------|-----------|
| **Geld ($)** | $ | Hauptwährung, allgemeiner Handel |
| **Münzen** | ⛂ | Sekundärwährung, besondere Käufe |
| **Tokens** | ✦ | Premium-Währung, exklusive Inhalte |
| **Quest-Punkte** | ✎ | Quest-Belohnungen, Quest-Shop |
| **Dungeon-Marken** | ⚔ | Dungeon-Belohnungen, Dungeon-Shop |

Alle 5 Währungen sind beim Wechsel zwischen RPG und Skyblock vollständig erhalten.

---

## 6. Technische Infrastruktur

### Proxy (Velocity)
- **IP:** `mc.festas-builds.com`
- **Standard-Server:** Lobby
- **Plugins:** CMIV, ForceResourcepacks, Geyser-Velocity, LibertyBans, MiniMOTD, PAF (PartyAndFriends), Plan, SkinsRestorer, TAB, VelocityScoreboardAPI

### Datenbanken
- **MariaDB** (`172.25.0.1:3306`): Spielerdaten, MMOCore, Economy, Quests, Achievements
- **Redis** (`172.18.0.1:6379`): HuskSync Session-Cache, temporäre Inventar-Daten

### Resourcepacks
- Custom Items (Oraxen), Custom Mobs (MythicMobs + ModelEngine), Custom Sounds und Texturen
- Automatischer Download beim Join (erzwungen via ForceResourcepacks)

### Bedrock-Support
- Geyser-Velocity + Floodgate
- Spieler auf Handy, Konsole und Windows 10 Edition können ohne Java-Account beitreten

### Daten-Synchronisation (HuskSync)
Zwischen RPG ↔ Skyblock werden synchronisiert:
- ✅ Haupt-Inventar, Rüstungs-Slots, Offhand, Ender-Chest
- ✅ Health, Hunger, Sättigung, XP-Level
- ✅ Klassen-Level, Skills und Skill-Trees (MMOCore)
- ✅ Quest-Fortschritt (BetonQuest)
- ✅ Alle 5 Währungen (CoinsEngine)
- ❌ Skyblock-Islands (server-spezifisch)
- ❌ Survival-Daten (komplett getrennt)

### Sicherheit
- **LibertyBans** (Velocity): Netzwerk-weite Bans, IP-Bans, Mutes, Temp-Bans
- **LuckPerms**: Fein-granulare Permissions, Rang-Tracks
- **WorldGuard**: TNT, Creeper, Wither, Feuer global blockiert (Survival)
- **GrimAC**: Anti-Cheat (Skyblock, angepasst für Flight)

### Netzwerk-Features (Proxy)
- **PartyAndFriends (PAF)**: Server-übergreifendes Party-System und Freundesliste
- **TAB**: Custom TAB-Liste und Scoreboard auf allen Servern
- **SkinsRestorer**: Custom Skins, auch im Offline-Mode
- **Plan**: Web-Dashboard mit Spieler-Statistiken und Server-Performance

---

## 7. Besonderheiten & Alleinstellungsmerkmale

### Was uns von anderen Servern unterscheidet

1. **Einzigartiger Crossover:** Hypixel-Skyblock-feeling + Wynncraft-Klassen auf demselben Netzwerk mit gemeinsamer Progression
2. **Echte Daten-Synchronisation:** Inventar, Klasse, Skills und Währungen sind zwischen RPG und Skyblock vollständig synchron — kein "Neustart" beim Server-Wechsel
3. **Bedrock-First:** Volle Unterstützung für Handy, Konsole und PC-Edition ohne Einschränkungen
4. **Tycoon-Survival:** Einzigartiger Generator-Tycoon-Gamemode mit 25 Rängen und 10 Prestige-Stufen auf einem klassischen Survival-Server
5. **6 vollwertige Klassen:** Jede Klasse mit eigener Ressource, eigenem Skill-Tree und eigener Spielweise
6. **Custom Content:** Alle Items, Mobs, Sounds und Texturen sind custom (Oraxen + ModelEngine + MythicMobs Premium)
7. **Anti-Inflation-Economy:** Dynamische Börse reagiert auf Angebot und Nachfrage
8. **Community-Features:** Server-übergreifende Parties, Freundeslisten, globaler Chat

### Geplante Features (Roadmap)
- [ ] Anti-Cheat (Vulcan Premium) auf Survival
- [ ] Voting-System (NuVotifier + VotingPlugin)
- [ ] Crate/Key-System (CrazyCrates)
- [ ] Kosmetik-System mit PlayerPoints
- [ ] DecentHolograms (Lobby, Spielerzahlen, News)
- [ ] CDN für Resourcepacks
- [ ] Parkour-Kurs in der Lobby mit Belohnungen
- [ ] Event-Server für spezielle Events
- [ ] Prestige-System auf RPG-Klassen (geplant nach Balance-Testing)

---

## 8. Website-Prompt für AI Agenten

> Dieser Abschnitt enthält einen fertigen Prompt, den du einem AI Agenten (z.B. GPT-4, Claude, Gemini, Cursor AI o.ä.) übergeben kannst, um eine vollständige Website für das MinecraftMMO Netzwerk zu erstellen oder eine bestehende Website zu überarbeiten.

> **⚠️ Hinweis (26.2-Umstellung):** Der folgende Prompt beschreibt noch das **alte 4-Server-Setup** (inkl. Skyblock & RPG). Da der Fokus jetzt auf **Lobby & Survival** liegt und Skyblock/RPG durch **zwei neue Server** ersetzt werden, sollte der Prompt vor Verwendung entsprechend angepasst werden (Server-Karten, Klassen-/MMO-Sektionen, Version **26.2**).

---

### Vollständiger Prompt

```
Du bist ein erfahrener Webentwickler und UI/UX-Designer, spezialisiert auf Gaming-Websites für Minecraft-Server. Erstelle eine vollständige, moderne Website für das MinecraftMMO Netzwerk.

---

## AUFGABE

Erstelle eine vollständige, responsive Website für einen deutschen Minecraft-Server. Die Website soll bestehende Spieler informieren und neue Spieler anwerben. Nutze moderne Webentwicklungs-Standards.

---

## SERVER-INFORMATIONEN

**Server-Name:** MinecraftMMO
**Server-IP:** mc.festas-builds.com
**Minecraft-Version:** 26.2
**Sprache:** Deutsch (primär)
**Bedrock-Support:** Ja (Handy, Konsole, Windows 10 Edition — keine Java-Account-Pflicht)
**Status:** Early Access / In Entwicklung

---

## SERVER-STRUKTUR (4 Server)

### 1. Lobby
- Willkommens-Hub und Server-Navigation
- Interaktive NPCs für Server-Auswahl
- Doppelsprung-Feature (kosmetisch)
- Willkommensnachrichten für neue Spieler
- Kein Gameplay, reine Navigation

### 2. Survival / Tycoon
Einzigartiger Hybrid aus klassischem Survival und Generator-Tycoon-Gamemode.

**Tycoon-Gamemode:**
- Generatoren auf dem eigenen Plot platzieren → Ressourcen produzieren → Sell Wand nutzen → verkaufen
- 25 Ränge von Erde bis Bedrock (Kosten: $5.000 bis $175 Milliarden)
- 10 Prestige-Stufen (Bronze bis Legende) mit permanentem Sell-Bonus (+10% bis +200%)
- Chunk Collector, Sell Wand, Dynamische Börse, Casino, Boss-Events
- 23 Achievements, tägliche Login-Belohnungen mit Streak-System
- 13 verschiedene Jobs für extra Einkommen
- Multi-Plot-System (bis 5 Plots + Prestige-Boni)
- Wöchentliche Events

### 3. Skyblock (MMO)
Hypixel-inspirierter Skyblock mit vollständiger MMO-Klassen-Integration.

**Features:**
- Persönliche und Coop-Islands (SuperiorSkyblock2)
- 8 Minion-Typen: Miner, Farmer, Fisher, Lumberjack, Slayer, Collector, Feeder, Seller
- 6 MMO-Klassen spielbar (identisch mit RPG, Daten synchronisiert)
- 5 Collection-Kategorien: Farming (16), Mining (8), Combat (8), Foraging (8), Fishing (4)
- Prestige-System (10 Stufen mit Titeln und passiven Buffs)
- Pet-System (6 Typen, leveln durch Mob-Kills)
- Bazaar-System (Instant-Buy/Sell wie Hypixel)
- Multi-Währungs-System (Geld, Münzen, Tokens, Quest-Punkte, Dungeon-Marken)

### 4. RPG (MMO)
Vollständiger Wynncraft-inspirierter MMO-RPG Server mit Open World.

**Features:**
- Open World mit 5 Level-Zonen (Level 1-100) + Nether/End als Endgame
- Quests: Hauptquest-Linie, Nebenquests, tägliche/wöchentliche Quests (BetonQuest)
- 10-15 instanzierte Dungeons (Party-System, mehrere Schwierigkeitsgrade)
- Custom Mobs mit einzigartigen Fähigkeiten, Elite-Mobs, Welt-Bosse
- 3D Custom Models für Mobs und NPCs (ModelEngine)
- NPC-System mit Story-Dialogen (Citizens + BetonQuest)
- 6 MMO-Klassen spielbar (Daten mit Skyblock synchronisiert)

---

## KLASSEN-SYSTEM (Gilt für RPG & Skyblock)

**6 Klassen:**

1. **Krieger** — Tank/Melee DPS
   - Ressource: Wut (durch Schaden aufgebaut)
   - Waffen: Schwert, Axt / Offhand: Schild
   - Rüstung: Plattenrüstung
   - Stärken: Hohe Verteidigung, Frontlinie, Gruppenschutz
   - Schwächen: Geringe Mobilität, keine Reichweite

2. **Magier** — Ranged Magic DPS
   - Ressource: Mana
   - Waffen: Stab / Offhand: Orb oder Totem
   - Rüstung: Stoffrüstung
   - Stärken: Höchster Zauberschaden, AoE
   - Schwächen: Niedrige Verteidigung

3. **Assassine** — Burst Melee DPS
   - Ressource: Energie (schnell regenerierend)
   - Waffen: Dual Dolche
   - Rüstung: Lederrüstung
   - Stärken: Höchster Burst-Schaden, hohe Mobilität, kritische Treffer
   - Schwächen: Niedrige Verteidigung, kein Sustain

4. **Bogenschütze** — Ranged Physical DPS
   - Ressource: Fokus (durch Treffer aufgebaut)
   - Waffen: Bogen / Armbrust
   - Rüstung: Leder-/Kettenrüstung
   - Stärken: Konstanter Fernkampf-Schaden, kein Mana-Limit
   - Schwächen: Geringe Mobilität

5. **Schamane** — Support/Healer
   - Ressource: Mana
   - Waffen: Stab, Streitkolben / Offhand: Totem oder Schild
   - Rüstung: Kettenrüstung
   - Stärken: Heilung und Buffs, unersetzlich in Gruppen
   - Schwächen: Niedrigster Angriffschaden solo

6. **Beschwörer** — Summoner/Pet-Class
   - Ressource: Mana + Beschwörungs-Slots
   - Waffen: Stab / Offhand: Orb oder Totem
   - Rüstung: Stoffrüstung
   - Stärken: Crowd Control, vielseitige Beschwörungen, passiver Schaden
   - Schwächen: Komplex zu spielen, abhängig von Minions

**Klassen-Mechaniken:**
- Level 1-100, 1 Skill-Punkt pro Level
- Passive Skills, Active Skills, Ultimate Skill (ab Level 50)
- Klassenwechsel möglich (7 Tage Cooldown, Level bleibt)
- Stats: Stärke, Intelligenz, Geschicklichkeit, Ausdauer, Weisheit

---

## ITEM-SYSTEM

**7 Item-Tiers:** Gewöhnlich (weiß), Ungewöhnlich (grün), Selten (blau), Episch (lila), Legendär (gold), Mythisch (pink), Göttlich (hellrot, geplant)

**Item-Kategorien:** Waffen (Schwerter, Stäbe, Bögen, Dolche, Äxte, Streitkolben), Rüstung, Schmuck (Ringe, Amulette), Offhand, Verbrauchsgüter

---

## ECONOMY

**Survival (separat):**
- Verdienen durch Jobs, Generatoren, Achievements, Boss-Events, Shop-Verkäufe
- Dynamische Börse, Auktionshaus, Casino

**MMO (RPG & Skyblock, synchronisiert):**
- 5 Währungen: Geld ($), Münzen, Tokens, Quest-Punkte, Dungeon-Marken
- Beim Server-Wechsel bleiben alle Währungen erhalten

---

## NETZWERK-FEATURES

- Bedrock-Support (Handy, Konsole, PC-Edition — kostenlos spielen ohne Java-Account)
- Party & Freundesliste (server-übergreifend)
- Custom Skins auch ohne Premium-Account (SkinsRestorer)
- Server-weite TAB-Liste und Scoreboards
- Resourcepack automatisch beim Join (Custom Items, Mobs, Sounds, Texturen)

---

## DESIGN-ANFORDERUNGEN

**Stil:** Modern, dunkel (Dark Theme), gaming-typisch mit Minecraft-Ästhetik
**Farbpalette:**
- Primär: Dunkelblau oder Dunkelgrau (#0d1117 oder #161b22)
- Akzent 1: Minecraft-Grün (#55FF55 oder ähnlich, für Highlights)
- Akzent 2: Gold/Orange (#FFD700, für seltene Items, CTAs)
- Text: Weiß/Hellgrau

**Typografie:** Moderne, gut lesbare Schriften. Optional: Minecraft-Font für Überschriften (Mojangles oder ähnlich).

**Responsive:** Mobile-first, funktioniert auf Handy, Tablet, Desktop.

**Animationen:** Dezente Einblend-Animationen (keine ablenkenden Endlos-Loops), Hover-Effekte auf Karten und Buttons.

---

## SEITENSTRUKTUR

### Navigation (sticky Header)
- Logo / Server-Name
- Links: Home, Server, Klassen, Items, Community, Wiki
- Server-IP mit Kopier-Button
- "Jetzt spielen" CTA-Button

### Hero-Section
- Großes Banner mit Server-Name und Tagline
- Tagline-Vorschläge: "Erlebe ein einzigartiges MMO-Abenteuer auf Minecraft 26.2" oder "RPG trifft Skyblock — auf einem Server"
- Server-IP prominent mit Kopier-Button
- Minecraft-Version Badge, Bedrock-Badge
- "Jetzt spielen"-Button (klickt und zeigt IP zum Kopieren)
- Hintergrund: Dunkler Minecraft-Screenshot oder Partikel-Animation

### Server-Cards Section
Vier Karten für die 4 Server:
- Lobby (Icon: Kompass)
- Survival/Tycoon (Icon: Generator oder Diamant)
- Skyblock (Icon: Grassblock auf Wolke)
- RPG (Icon: Schwert oder Zauberstab)
Jede Karte: kurze Beschreibung, wichtigste Features als Liste, kleines "Mehr erfahren"-Link

### Features / Highlights Section
Besondere Alleinstellungsmerkmale hervorheben:
- "Bedrock-Support" — spielbar auf Handy, Konsole, PC-Edition
- "6 einzigartige Klassen" — mit Tooltip/Hover für kurze Beschreibung
- "Synchronisierte Progression" — Inventar und Klasse zwischen RPG und Skyblock
- "25 Tycoon-Ränge" — Erde bis Bedrock
- "Custom Content" — eigene Items, Mobs, Sounds

### Klassen-Section
Übersicht der 6 Klassen mit:
- Icon/Bild pro Klasse
- Name, Archetyp (DPS/Healer/Support/etc.)
- Ressource
- Stärken/Schwächen als kurze Liste
- Hover-Effekt oder Flip-Card

### Stats/Counter Section (optional, mit Platzhalter-Zahlen)
- "100+ Spieler online" (Platzhalter)
- "25 Ränge" auf Survival
- "6 Klassen" im MMO
- "10-15 Dungeons" auf RPG
- "8 Minion-Typen" auf Skyblock

### Server-IP / How to Join Section
Schritt-für-Schritt Anleitung:
1. Minecraft starten (Java oder Bedrock)
2. Multiplayer → Server hinzufügen
3. IP eingeben: mc.festas-builds.com
4. Server beitreten und losspielen

Bedrock-Hinweis: Für Handy/Konsole — Port und ggf. Anleitung.

### Footer
- Copyright
- Server-IP
- Links: Discord (Platzhalter), Twitter/X, GitHub
- Impressum-Link (Platzhalter)

---

## TECHNISCHE ANFORDERUNGEN

**Framework/Stack:** Verwende modernes HTML5 + CSS3 + Vanilla JavaScript (oder React/Next.js wenn bevorzugt)
**Kein CSS-Framework-Zwang:** Tailwind CSS ist erlaubt, aber nicht Pflicht.
**Icons:** Heroicons, Feather Icons oder ähnlich (kein Font Awesome wegen Lizenzen).
**Fonts:** Google Fonts (Inter, Outfit oder ähnliches für Texte).
**Performance:** Lazy Loading für Bilder, keine unnötigen Abhängigkeiten.
**SEO:** Korrekte Meta-Tags, Open Graph Tags (og:title, og:description, og:image).
**Accessibility:** Kontraste einhalten (WCAG AA), Alt-Texte für Bilder.

---

## PLATZHALTER & HINWEISE

- Server-Logo: [Logo-Datei wird später bereitgestellt] — verwende einen Platzhalter mit den Initialen "MMO"
- Screenshots: [Werden später bereitgestellt] — verwende dunkle Platzhalter-Boxen mit Beschriftung
- Discord-Link: [Wird noch erstellt]
- Spielerzahlen: [Live-Daten via API, vorerst statische Platzhalter nutzen]
- Alle Texte auf Deutsch verfassen

---

## AUSGABE

Erstelle die komplette Website als einzelne HTML-Datei (index.html) mit eingebettetem CSS und JavaScript, ODER als Datei-Struktur mit separatem CSS und JS. Stelle sicher, dass die Website ohne Backend-Server lokal in einem Browser funktioniert. Alle Sektionen sollen vollständig ausgefüllt sein (keine leeren Platzhalter außer Bilder/Logo).
```

---

> **Hinweis:** Dieser Prompt kann direkt in ein Chat-Interface (ChatGPT, Claude, Gemini, Copilot, etc.) eingefügt werden. Passe ihn bei Bedarf an — z.B. wenn du ein bestimmtes Framework bevorzugst (React, Vue, Astro, etc.) oder das Design ändern möchtest.

---

**Letzte Aktualisierung:** 2026-08-15  
**Version:** 1.1 (26.2-Umstellung: Fokus Lobby & Survival; Skyblock & RPG → Archiv)
