# Planungs-Fragenkatalog für MinecraftMMO

Dieser Fragenkatalog dient als Leitfaden für die weitere Entwicklung und Dokumentation des MinecraftMMO Server-Netzwerks. Die Fragen helfen dabei, strukturiert über Features, Balance und Implementierung nachzudenken.

> **ℹ️ Stand 26.2:** Der Fokus liegt aktuell auf **Lobby** und **Survival**. Dazu kommen ein **überarbeiteter Skyblock** (ohne Gilden, mit Freunde-Koop) und ein neuer **Mining**-Server (Abbau-Zonen mit aufwertbaren Spitzhacken). Die Weichenstellung inkl. Bewertung und Plugin-Shortlist steht in [NEW_SERVERS.md](NEW_SERVERS.md); die Planungskapitel für die neuen Server sind Abschnitte 2 (Mining) und 3 (Skyblock). Der **RPG**-Server wird zeitnah eingestellt.

---

## 1. Netzwerk-Vision & Lore

### Vision & Ziele
- [ ] Was ist die übergeordnete Vision für das gesamte Netzwerk?
- [ ] Wie unterscheiden sich die aktiven und neuen Server (Lobby, Survival, Skyblock, Mining) voneinander?
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

## 2. Mining-Server Planung (neu, geplant)

> Casual-Server mit **Abbau-Zonen** und aufwertbaren Spitzhacken. Konzept & Plugin-Shortlist:
> [NEW_SERVERS.md](NEW_SERVERS.md) · [prison/README.md](prison/README.md).

### Spitzhacke & Progression
- [ ] Wie viele Spitzhacken-Stufen zum Launch, und welches Abbau-Muster pro Stufe (1×1 → 3×3 → …)?
- [ ] Wie werden Upgrades finanziert (Blöcke verkaufen, Tokens, beides)?
- [ ] Zusätzliche Effekte/Verzauberungen (Auto-Sell, Multiplier, Tempo)?

### Zonen
- [ ] Wie viele Abbau-Zonen zum Launch, mit welchen Block-Sets?
- [ ] Freischalt-Bedingungen pro Zone (Währung, Fortschritt, Rang)?
- [ ] Auto-Regenerations-Tempo der abgebauten Blöcke?

### Framework & Technik (26.2)
- [ ] Welches Mining-/Zonen-Plugin ist 26.2-tauglich? (Blocker!)
- [ ] Bedrock-Kompatibilität aller GUIs (Geyser/Floodgate)?
- [ ] Zonengrenzen/Schutz über WorldGuard?

### Retention & Economy
- [ ] Ränge/Prestige nach den Zonen?
- [ ] Cosmetics (Trails, Effekte) / Battle-Pass?
- [ ] Server-isolierte Währung oder netzwerkweite Cosmetic-Währung?

---

## 3. Skyblock-Server Planung (überarbeitet, geplant)

> Skyblock **ohne Gilden**, aber mit **Freunde-Koop** (Insel-Mitglieder). Konzept & Plugin-Shortlist:
> [NEW_SERVERS.md](NEW_SERVERS.md) · [skyblock/README.md](skyblock/README.md).

### Inseln & Koop
- [ ] Maximale Insel-Mitglieder / Koop-Größe (Freunde einladen)?
- [ ] Insel-Rollen/Rechte innerhalb der Insel (statt Gilden)?
- [ ] Insel-Upgrades, Level und Besucher-System?

### Umfang & Wiederverwendung
- [ ] Bleibt die MMO-Integration (Klassen/MMOItems) erhalten oder schlankerer Koop-Skyblock?
- [ ] Welche vorhandenen Plugins (JetsMinions, Collections, Bazaar) bleiben aktiv?
- [ ] SuperiorSkyblock2 & SlimeWorldManager auf 26.2 verifiziert? (Blocker!)

### Retention & Economy
- [ ] Prestige-/Collection-Systeme, Pets?
- [ ] Server-isolierte Währung (Balance getrennt)?
- [ ] Cosmetics/Battle-Pass?

---

## 4. Economy & Items

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

## 5. Technische Infrastruktur

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

## 6. Content-Planung

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

## 7. Player Experience

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
- [x] Gilden/Clans geplant? → **Nein.** Gilden werden nicht verwendet (CrossCraft-Guilds entfernt); Skyblock nutzt stattdessen **Freunde-Koop** über Insel-Mitglieder.
- [ ] Chat-Kanäle?

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

## 8. Qualitätssicherung

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

## 9. Dokumentation (Meta)

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
