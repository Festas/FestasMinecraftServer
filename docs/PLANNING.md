# Planungs-Fragenkatalog für MinecraftMMO

Dieser Fragenkatalog dient als Leitfaden für die weitere Entwicklung und Dokumentation des MinecraftMMO Server-Netzwerks. Die Fragen helfen dabei, strukturiert über Features, Balance und Implementierung nachzudenken.

> **ℹ️ Stand 26.2:** Der Fokus liegt aktuell auf **Lobby** und **Survival**. Die MMO-Server **Skyblock** und **RPG** werden zeitnah eingestellt und durch **zwei neue Server** ersetzt: **Minigames** (Casual) und **Factions** (Social/PvP). Die Weichenstellung inkl. Bewertung und Plugin-Shortlist steht in [NEW_SERVERS.md](NEW_SERVERS.md); die Planungskapitel für die Nachfolger sind Abschnitte 4 (Minigames) und 5 (Factions). Fragen zu Skyblock/RPG sind nur noch für den Rückbau relevant.

---

## 1. Netzwerk-Vision & Lore

### Vision & Ziele
- [ ] Was ist die übergeordnete Vision für das gesamte Netzwerk?
- [ ] Wie unterscheiden sich die aktiven und geplanten Server (Lobby, Survival, Minigames, Factions) voneinander?
- [ ] Welche einzigartigen Verkaufsargumente (USPs) hat das Netzwerk?
- [ ] Welche Zielgruppe soll angesprochen werden? (Casual, Hardcore, PvP, PvE)
- [ ] Wie lange soll die durchschnittliche Spielzeit bis Endgame sein?

### Lore & Weltaufbau
- [ ] Gibt es eine übergreifende Story, die alle Server verbindet?
- [ ] Wie heißt die Welt? Gibt es Namen für Kontinente/Regionen?
- [ ] Welche Fraktionen, Völker oder Organisationen existieren?
- [ ] Gibt es einen Antagonisten oder eine zentrale Bedrohung?
- [ ] Wie passt Skyblock in die Lore? (Schwebende Inseln, Dimensionen?)
- [ ] Welche historischen Events haben die Welt geformt?

### Server-Verbindung
- [ ] Sind die Server lore-technisch miteinander verbunden?
- [ ] Können Spieler zwischen Servern wechseln mit ihrem Charakter?
- [ ] Gibt es geteilte Währungen oder Items zwischen Servern?
- [ ] Wie funktioniert der Übergang vom Survival zu MMO-Servern?

---

## 2. Klassen-System (MMOCore)

### Aktuelle 6 Klassen
1. **Krieger** (Warrior)
2. **Magier** (Mage)
3. **Assassine** (Assassin)
4. **Bogenschütze** (Archer)
5. **Schamane** (Shaman)
6. **Beschwörer** (Summoner)

### Klassen-Design
- [ ] Was ist die Kernidentität jeder Klasse?
- [ ] Welche Haupt-Stats hat jede Klasse? (Stärke, Int, Geschick, etc.)
- [ ] Welche primären/sekundären Waffen kann jede Klasse nutzen?
- [ ] Welche Rüstungstypen sind pro Klasse erlaubt? (Leder, Ketten, Platten)
- [ ] Gibt es Klassen-exklusive Items oder Skills?

### Krieger (Warrior)
- [ ] Tank oder DPS Fokus? Oder beides möglich?
- [ ] Welche Waffen? (Schwert, Axt, Streitkolben?)
- [ ] Besondere Mechanik? (Wut-System, Rüstungs-Stacking?)
- [ ] Ultimate Ability?

### Magier (Mage)
- [ ] Elemente? (Feuer, Eis, Blitz, Arkane Magie?)
- [ ] Mana-Regeneration-System?
- [ ] Glaskanone oder mit Defensiv-Optionen?
- [ ] Beschwörungen oder nur direkte Zauber?

### Assassine (Assassin)
- [ ] Stealth-Mechanik vorhanden?
- [ ] Kritische Treffer Fokus?
- [ ] Beweglichkeits-Skills? (Dash, Teleport?)
- [ ] Gift/DoT Mechaniken?

### Bogenschütze (Archer)
- [ ] Nur Bögen oder auch Armbrüste?
- [ ] Spezial-Pfeile? (Explosive, Gift, Feuer?)
- [ ] Pet/Begleiter-System?
- [ ] Fallen-Mechaniken?

### Schamane (Shaman)
- [ ] Heiler, Support oder Hybrid?
- [ ] Totem-System?
- [ ] Elementare Thematik? (Natur, Geister?)
- [ ] Gruppen-Buffs oder einzelne Heals?

### Beschwörer (Summoner)
- [ ] Welche Kreaturen können beschworen werden?
- [ ] Permanente oder temporäre Beschwörungen?
- [ ] Maximale Anzahl aktiver Beschwörungen?
- [ ] Beschwörungs-Upgrade-System?

### Klassen-Balance
- [ ] Wie wird Balance zwischen Klassen gewährleistet?
- [ ] Gibt es Klassen-Konter? (Rock-Paper-Scissors System?)
- [ ] PvP vs PvE Balance unterschiedlich?
- [ ] Wie oft werden Klassen neu balanciert?

### Progression
- [ ] Max Level pro Klasse?
- [ ] Können Spieler mehrere Klassen haben? Klassenwechsel?
- [ ] Prestige/Reborn System geplant?
- [ ] Skill-Trees: Linear oder verzweigt?
- [ ] Wie viele Skills pro Klasse?

---

## 3. Progression & Tier-System

### Level-System
- [ ] Max Level? (100, 200, unbegrenzt?)
- [ ] XP-Quellen? (Mobs, Quests, Dungeons, Mining, etc.)
- [ ] XP-Kurve: Linear oder exponentiell?
- [ ] Gibt es Level-Bereiche für verschiedene Zonen?

### Tier-System (Items & Mobs)
- [ ] Sind die aktuellen Tiers final? (Common, Uncommon, Rare, Epic, Legendary, Mythic)
- [ ] Soll es noch höhere Tiers geben? (Divine, Celestial?)
- [ ] Wie stark ist der Power-Gap zwischen Tiers?
- [ ] Können Items aufgewertet werden? (Common → Uncommon?)

### Stats-System
- [ ] Welche Stats gibt es? (HP, Mana, Dmg, Def, Crit, Speed, etc.)
- [ ] Gibt es Soft-Caps für Stats?
- [ ] Wie skalieren Stats mit Level?
- [ ] Sekundär-Stats? (Life Steal, Cooldown Reduction, etc.)

### Währungen
- [ ] Welche Währungen existieren?
  - Coins (CoinsEngine)
  - Premium-Währung?
  - Quest-Tokens?
  - Dungeon-Währung?
- [ ] Können Währungen zwischen Servern geteilt werden?
- [ ] Wie werden Währungen verdient?

### Endgame-Content
- [ ] Was ist das Endgame-Ziel?
- [ ] Raid-System geplant?
- [ ] PvP-Endgame? (Arena, Battlegrounds?)
- [ ] Collection-Challenges?
- [ ] Prestige-System?

---

## 4. Minigames-Server Planung (Nachfolger, geplant)

> Casual-Server mit rotierenden Minispielen. Konzept & Plugin-Shortlist:
> [NEW_SERVERS.md](NEW_SERVERS.md) · [minigames/README.md](minigames/README.md).

### Modi & Rotation
- [ ] Welche Modi zum Launch? (BedWars/SkyWars, Parkour, Spleef, Arcade)
- [ ] Fester Modus-Selector oder automatische Rotation?
- [ ] Solo- und Team-Modi?
- [ ] Ranglisten/Leaderboards pro Modus?

### Maps & Arenen
- [ ] Eigene Builds oder lizenzfreie Community-Maps?
- [ ] Wie viele Maps pro Modus zum Launch?
- [ ] MultiArena/Queue-System für parallele Runden?
- [ ] Automatischer Map-Reset nach Runde?

### Framework & Technik (26.2)
- [ ] Welches Minigame-Framework ist 26.2-tauglich? (Blocker!)
- [ ] Bedrock-Kompatibilität aller GUIs (Geyser/Floodgate)?
- [ ] Welt-Verwaltung: Multiverse-Core?

### Retention & Economy
- [ ] Battle-Pass / tägliche Herausforderungen?
- [ ] Cosmetics (Trails, Effekte, Kills-Messages)?
- [ ] Server-isolierte Währung oder netzwerkweite Cosmetic-Währung?

---

## 5. Factions-Server Planung (Nachfolger, geplant)

> Social/PvP-Server mit Gilden-getriebenem Land-Claiming. Konzept & Plugin-Shortlist:
> [NEW_SERVERS.md](NEW_SERVERS.md) · [factions/README.md](factions/README.md).

### Gilden & Teams (CrossCraft-Guilds)
- [ ] CrossCraft-Guilds auf 26.2 kompiliert & getestet?
- [ ] Gilden-Bank, -Ränge und Cross-Server-Chat aktiv?
- [ ] Maximale Gildengröße / Allianzen?

### Land-Claiming & War
- [ ] Welcher Factions-/Claim-Kern ist 26.2-tauglich? (Blocker!)
- [ ] Claim-Limits, Power-System, Raid-Regeln?
- [ ] Warzone & Spawn-Schutz (WorldGuard)?
- [ ] Saisonale Wipes / Ladder?

### PvP-Fairness & Sicherheit
- [ ] Anti-Cheat für 26.2 verifiziert? (kritischer Blocker!)
- [ ] Combat-Tag / Anti-Combat-Log?
- [ ] Moderations-Workflow (Griefing, Reports)?

### Progression & Retention
- [ ] Kits, Crates, Ränge, Battle-Pass?
- [ ] KitPvP-Arena als Zusatz-Modus?
- [ ] PvE-Welt-Events (ggf. MythicMobs aus Alt-Bestand)?
- [ ] Server-isolierte Währung?

---

## 6. Economy & Items

### CoinsEngine
- [ ] Welche Währungen sind aktiv?
- [ ] Exchange-Rates zwischen Währungen?
- [ ] Inflation-Prevention-Mechaniken?

### Items (Oraxen, MMOItems, MythicCrucible)
- [ ] Wie viele Custom Items insgesamt geplant?
- [ ] Item-Sets mit Set-Boni?
- [ ] Upgrading/Reforging-System?
- [ ] Socketing-System? (Gems, Runes?)
- [ ] Item-Durability?

### Waffen
- [ ] Waffentypen pro Klasse?
- [ ] Waffenstufen (Tier 1-6)?
- [ ] Einzigartige/Legendäre Waffen mit Special Effects?

### Rüstungen
- [ ] Rüstungstypen (Stoff, Leder, Kette, Platte)?
- [ ] Rüstungs-Sets?
- [ ] Transmog/Cosmetic-System?

### Crafting
- [ ] Crafting-System vorhanden?
- [ ] Berufe? (Schmied, Alchemist, etc.)
- [ ] Rezepte: Gefunden oder gekauft?

### Loot-System
- [ ] Wie funktioniert Loot-Verteilung in Gruppen?
- [ ] Garantierte Drops vs RNG?
- [ ] Pity-System für seltene Items?

---

## 7. Technische Infrastruktur

### Datenbanken
- [ ] MySQL: Wofür genutzt? (Spielerdaten, Stats?)
- [ ] Redis: Wofür genutzt? (Cache, Sessions?)
- [ ] MariaDB: Wofür genutzt?
- [ ] Warum strikte Trennung Survival ↔ RPG/Skyblock?
- [ ] Backup-Strategie?

### Velocity Proxy
- [ ] Welche Plugins auf Velocity laufen?
- [ ] Wie funktioniert Server-Switching?
- [ ] Load-Balancing geplant?
- [ ] Sicherheits-Plugins? (Anti-Bot, etc.)

### HuskSync
- [ ] Welche Daten werden synchronisiert?
- [ ] Synchronisation zwischen welchen Servern?
- [ ] Konflikt-Handling bei gleichzeitigem Spielen?

### Performance
- [ ] Erwartete Spieleranzahl pro Server?
- [ ] Server-Hardware-Anforderungen?
- [ ] Optimierungs-Prioritäten?
- [ ] Monitoring-Tools? (Plan, etc.)

### Deployment
- [ ] Wie werden Updates ausgerollt?
- [ ] Test-Server vorhanden?
- [ ] Rollback-Strategie?
- [ ] Config-Versionierung (dieses Repo)?

---

## 8. Content-Planung

### Phase 1 (MVP - Minimum Viable Product)
- [ ] Welche Features müssen für Launch fertig sein?
- [ ] Welche Zonen/Dungeons sind Pflicht?
- [ ] Minimale Quest-Anzahl?

### Phase 2 (Post-Launch)
- [ ] Welche Features kommen nach Launch?
- [ ] Content-Update-Frequenz?
- [ ] Saisonale Events?

### Phase 3 (Long-Term)
- [ ] Langzeit-Content-Pläne?
- [ ] Erweiterungen/DLCs?
- [ ] Community-Features?

---

## 9. Player Experience

### Onboarding
- [ ] Wie werden neue Spieler eingeführt?
- [ ] Tutorial-System?
- [ ] Starter-Quests?
- [ ] Starter-Ausrüstung?

### Progression-Pace
- [ ] Wie schnell sollen Spieler leveln?
- [ ] Grinding vs Story-Fokus?
- [ ] Soll Solo-Play möglich sein bis Endgame?

### Social Features
- [ ] Gilden/Clans geplant?
- [ ] Party-System (PAF - PartyAndFriendsGUI)?
- [ ] Chat-Kanäle?
- [ ] Freundesliste?

### PvP
- [ ] Wo ist PvP erlaubt? (Arena, Open World, Battlegrounds?)
- [ ] PvP-Belohnungen?
- [ ] Ranking/Ladder-System?
- [ ] Balance für PvP vs PvE unterschiedlich?

### Events
- [ ] Tägliche/Wöchentliche Events?
- [ ] Boss-Events?
- [ ] Saisonale Events? (Halloween, Weihnachten, etc.)

---

## 10. Qualitätssicherung

### Testing
- [ ] Wie wird Content getestet?
- [ ] Beta-Tester-Programm?
- [ ] Test-Checklisten vorhanden?

### Balance
- [ ] Wie wird Balance gemessen?
- [ ] Feedback-Mechanismen?
- [ ] Balance-Patches: Wie oft?

### Bugs & Issues
- [ ] Issue-Tracking-System?
- [ ] Prioritäten für Bugfixes?
- [ ] Hotfix-Prozess?

---

## 11. Dokumentation (Meta)

### Für Entwickler (Projektinhaber)
- [ ] Welche Docs werden regelmäßig aktualisiert?
- [ ] Changelog führen?
- [ ] Code-Kommentare: Wann und wo?

### Für Spieler (Zukunft?)
- [ ] Wiki geplant?
- [ ] In-Game-Guides?
- [ ] Video-Tutorials?

---

## Notizen & Ideen

_Hier können spontane Ideen, Notizen oder unfertige Gedanken festgehalten werden._

---

**Letzte Aktualisierung:** 2026-08-15
