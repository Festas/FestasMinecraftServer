# Nachfolge-Server: Ersatz für Skyblock & RPG

> **Zweck:** Dieses Dokument setzt den Planungsprozess aus [PLANNING.md](PLANNING.md) um. Es hält die
> **Entscheidung** fest, welche zwei neuen Server die auslaufenden MMO-Server **Skyblock** und **RPG**
> ersetzen, dokumentiert die **Bewertung der Kandidaten**, den konkreten **Plugin-Stack (26.2)**, die
> **Netzwerk-Integration**, den **Rückbau** der Alt-Server und die **Roadmap**.
>
> **Status:** Konzept / Weichenstellung. Die Auswahl ist ein begründeter Vorschlag; offene Punkte sind in
> [Abschnitt 7](#7-verbleibende-offene-fragen) markiert.

---

## 0. Ausgangslage & Leitplanken

- **Aktiv gepflegt:** Lobby (Hub) + Survival/Tycoon (eigene Economy, NextGens, PlotSquared, Rankup, Jobs).
- **Wird abgelöst:** Skyblock (MMOCore/MMOItems/SuperiorSkyblock2) + RPG (MythicMobs Premium, MythicDungeons,
  BetonQuest, Citizens).
- **Ziel:** Zwei neue Server, die
  1. sich klar vom Tycoon-Survival abgrenzen (kein Kannibalisieren der Spielerbasis),
  2. die vorhandene Infrastruktur wiederverwenden (Velocity, MariaDB, Redis, Geyser/Floodgate, HuskSync,
     CoinsEngine, LuckPerms, Oraxen),
  3. mit vertretbarem Pflegeaufwand betreibbar sind (Solo-Betrieb).
- **Randbedingungen:** Minecraft **26.2** (Plugin-Verfügbarkeit ist ein harter Blocker!), **Bedrock-Support**
  über Geyser/Floodgate nötig, **Solo-Betreiber** → Wartungsaufwand ist ein Hauptkriterium.

---

## 1. Bewertungsraster

Jeder Kandidat wird gegen acht Kriterien bewertet (Skala: ⬤⬤⬤ hoch · ⬤⬤ mittel · ⬤ niedrig — im Sinne
von „viel/wenig", je nach Kriterium ist hoch nicht automatisch gut):

| # | Kriterium | Bedeutung |
|---|-----------|-----------|
| 1 | **Content-Aufwand** | Custom-Building/Config nötig, bevor spielbar |
| 2 | **Wartungslast** | Laufender Betrieb, Balance, Moderation, Anti-Cheat |
| 3 | **Plugin-Reife 26.2** | Existieren aktuelle Builds für 26.2? |
| 4 | **Abgrenzung z. Tycoon** | Wie stark unterscheidet sich der Loop vom Survival/Tycoon? |
| 5 | **Wiederverwendung** | Nutzt vorhandene Systeme (Economy, HuskSync, Guilds, Assets) |
| 6 | **Zielgruppe** | Casual/Hardcore, PvE/PvP, Solo/Gruppe |
| 7 | **Retention/Monetarisierung** | Battle-Pass, Cosmetics, Ränge, Wiederspielwert |
| 8 | **Time-to-Launch** | Wie schnell ist ein MVP spielbar? |

### 1.1 Kandidaten-Bewertung

| Kandidat | 1 Content-Aufwand | 2 Wartung | 3 Plugin-Reife 26.2 | 4 Abgrenzung | 5 Wiederverw. | 6 Zielgruppe | 7 Retention | 8 Time-to-Launch |
|----------|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| **A · Minigames/Party** | ⬤⬤ | ⬤ | ⬤⬤ | ⬤⬤⬤ | ⬤⬤ | Casual, PvE+PvP, Solo/Gruppe | ⬤⬤⬤ | ⬤⬤⬤ |
| **B · Prison** | ⬤⬤ | ⬤⬤ | ⬤⬤ | ⬤ (nah am Tycoon) | ⬤⬤⬤ | Casual/Grind, PvE+PvP | ⬤⬤ | ⬤⬤ |
| **C · Factions/KitPvP** | ⬤⬤ | ⬤⬤⬤ | ⬤⬤ | ⬤⬤⬤ | ⬤⬤⬤ (Guilds!) | Hardcore, PvP, Gruppe | ⬤⬤⬤ | ⬤⬤ |
| **D · Skyblock „light"** | ⬤⬤ | ⬤⬤ | ⬤⬤ | ⬤⬤ | ⬤⬤⬤ | Casual/Mittel, PvE | ⬤⬤ | ⬤⬤ |
| **E · Lobby-Games-Ausbau** | ⬤ | ⬤ | ⬤⬤ | ⬤⬤ | ⬤⬤ | Casual, kein eigener Server | ⬤ | ⬤⬤⬤ |
| **F · Event-/Seasonal** | ⬤ | ⬤ | ⬤⬤ | ⬤⬤ | ⬤⬤ | Casual, temporär | ⬤ | ⬤⬤⬤ |

**Legende Kriterien 1–3:** höher = mehr Aufwand/Last bzw. bessere Reife. Kriterium 4/5/7/8: höher = besser.

---

## 2. Entscheidung

Gemäß der Empfehlung aus [PLANNING.md](PLANNING.md) — **ein Casual-Server + ein Social/PvP-Server** — fällt die
Weichenstellung auf folgende Kombination:

| Slot | Server | Kandidat | Ordner | USP |
|------|--------|----------|--------|-----|
| **Casual** | **Minigames** | A | [`minigames/`](../minigames/) | Schnelle Runden, hoher Wiederspielwert, niedrige Einstiegshürde; nutzt Lobby-Routing optimal aus |
| **Social/PvP** | **Factions** | C | [`factions/`](../factions/) | Gruppen-/Gilden-getriebenes PvP mit **CrossCraft-Guilds** als Klammer; deckt die offene PvP-Endgame-Lücke |

### 2.1 Begründung

- **Minigames statt Prison (B):** Prison überschneidet sich stark mit dem Tycoon-Grind (Mine → Rankup →
  Prestige) — genau das, was Kriterium 4 vermeiden will. Minigames grenzt sich maximal ab, ist am schnellsten
  launchbar (Kriterium 8) und hat den höchsten Wiederspielwert (Kriterium 7).
- **Factions als zweiter Server:** Deckt die in PLANNING.md dokumentierte **PvP-Endgame-Lücke** ab und nutzt das
  bereits im Repo vorhandene **CrossCraft-Guilds**-Plugin als soziale Klammer (Kriterium 5). Höhere
  Moderationslast (Kriterium 2) wird bewusst in Kauf genommen, weil die Retention (Kriterium 7) und die
  Gruppen-Bindung am stärksten sind.
- **Verworfen:** D (Skyblock „light") würde die „Warum abgelöst?"-Frage aufwerfen; E/F sind als **Ergänzungen**
  sinnvoll (Lobby-Ausbau, Seasonal-Events), aber nicht als vollwertiger Server-Ersatz.

> **Alternative bei knapper Zeit:** Sollte der Solo-Betrieb nur **einen** neuen Server tragen, startet zuerst
> **Minigames** (schnellster MVP), und Factions folgt in Phase 2. E (Lobby-Games) dient dann als Übergangs-Fallback.

---

## 3. Plugin-Shortlist & 26.2-Verfügbarkeit

> ⚠️ **Blocker-Check zuerst:** Vor jeder Detailplanung muss für jedes Plugin ein aktueller **26.2-Build**
> bestätigt werden. Die Spalte „26.2" ist eine **Annahme/To-Verify** und muss auf der jeweiligen Bezugsquelle
> (SpigotMC/Polymart/Modrinth/Hangar/GitHub) geprüft werden, bevor der Server aufgesetzt wird.

### 3.1 Geteilte Basis (beide Server)

| Plugin | Zweck | 26.2 | Quelle-Typ |
|--------|-------|:----:|-----------|
| LuckPerms | Permissions/Ränge | ✅ üblich aktuell | Frei |
| PlaceholderAPI | Platzhalter | ✅ | Frei |
| Vault / Vault-kompatible Economy | Economy-Bridge | ✅ | Frei |
| CoinsEngine | Multi-Währung (netzwerkweit optional) | 🔶 prüfen | Frei |
| HuskSync | Cosmetics/Rang-Sync (selektiv) | 🔶 prüfen | Frei |
| Oraxen | Custom Items/Texturen (Bedrock-fähig) | 🔶 prüfen | Kauf |
| ProtocolLib | Protokoll-Basis | ✅ | Frei |
| CMI (+CMILib) | Core-Management (optional) | 🔶 prüfen | Kauf |
| PartyAndFriendsGUI | Party/Friends | 🔶 prüfen | Frei |
| spark | Performance-Profiling | ✅ | Frei |

### 3.2 Minigames-spezifisch

| Plugin | Zweck | 26.2 | Hinweis |
|--------|-------|:----:|--------|
| Minigame-Framework (z. B. **MissionsAddon**-frei / **BedWars**-, **SkyWars**-Plugin) | Spielmodi | 🔶 prüfen | Ein etabliertes, gepflegtes Framework wählen (Blocker!) |
| **Parkour**-Plugin | Parkour-Modus | 🔶 prüfen | Frei verfügbar |
| **MultiArena/Queue**-System | Arena-Rotation, Warteschlange | 🔶 prüfen | Ggf. im Framework enthalten |
| WorldEdit/FAWE | Map-Reset, Building | ✅ | Bereits im Netzwerk |
| Multiverse-Core | Map-/Welt-Verwaltung | 🔶 prüfen | Bereits auf Survival genutzt |
| Cosmetics-Plugin | Trails, Effekte, Battle-Pass | 🔶 prüfen | Retention/Monetarisierung |

### 3.3 Factions-spezifisch

| Plugin | Zweck | 26.2 | Hinweis |
|--------|-------|:----:|--------|
| **CrossCraft-Guilds** (Repo-intern) | Gilden/Teams als Klammer | Java 21 / Paper 1.21.4+ → auf 26.2 anheben | Bereits vorhanden, muss auf 26.2 kompiliert/getestet werden |
| Factions-Kern (Claim/Land) | Land-Claiming, War | 🔶 prüfen | Alt-Factions-Forks auf Aktualität prüfen |
| WorldGuard | Schutzregionen (Spawn, Warzone) | ✅ | Bereits im Netzwerk |
| Combat-Tag/Logger | Anti-Combat-Log | 🔶 prüfen | Wichtig für PvP-Fairness |
| Anti-Cheat (z. B. Vulcan/Grim-Klasse) | Cheat-Schutz | 🔶 prüfen | **Kritisch** für PvP-Server |
| KitPvP/Arena-Plugin | Kits, Warzonen | 🔶 prüfen | Für KitPvP-Anteil |
| Ranks/Kits/Crates | Progression, Belohnungen | 🔶 prüfen | Retention |

**Aus dem Alt-Bestand wiederverwendbar:** Oraxen-Items, Klassen-/Balancing-Konventionen (siehe
[CONTRIBUTING.md](../CONTRIBUTING.md)), Maps und Lore-Bausteine aus RPG/Skyblock, ggf. MythicMobs (falls
Premium-Lizenz weiterverwendbar) für PvE-Events auf dem Factions-Server.

---

## 4. Netzwerk-Integration (beide Server)

- **Economy-Strategie (Entscheidung):** **Isolierte Währung pro Server** (analog Survival), um Balance getrennt
  zu halten. Optional wird eine **netzwerkweite Premium-/Cosmetic-Währung** via CoinsEngine ergänzt (nur für
  Cosmetics/Battle-Pass, nicht für Gameplay-Balance). → siehe offene Frage in [Abschnitt 7](#7-verbleibende-offene-fragen).
- **Datenhaltung:** Eigenes **MariaDB-Schema pro Server** (isoliert), **Redis** für Sessions/Cross-Server-Messaging
  (u. a. CrossCraft-Guilds).
- **HuskSync:** Synchronisiert werden **Cosmetics und Ränge**, **nicht** die gameplay-relevanten Inventare der
  neuen Server (Minigames/Factions haben server-eigene Inventare).
- **Velocity-Routing:** Neue Einträge im Lobby-Selector (**DeluxeMenus**), Anpassung von **MOTD (MiniMOTD)** und
  **TAB**. Server-Namen im Velocity-Config: `minigames`, `factions`.
- **Bedrock:** Jede GUI und jedes Custom-Item auf **Geyser/Floodgate**-Kompatibilität testen (Oraxen-Texturen,
  Menü-Formulare).
- **Ränge & Permissions:** Bestehende **LuckPerms**-Gruppen wiederverwenden, server-spezifische Kontexte
  (`server=minigames`, `server=factions`) definieren.

---

## 5. Rückbau von Skyblock & RPG

1. **Archivierung:** Konfigs/Docs der Alt-Server bleiben als **Archiv** erhalten (bereits so markiert). Kein
   aktives Pflegen mehr; klar gekennzeichnete Archiv-Hinweise in den READMEs.
2. **Daten-Migration/Backup:** Spielerdaten der Alt-Server **sichern**, bevor abgeschaltet wird. Klären, ob
   Cosmetics/Ränge in die neuen Server überführt werden (via HuskSync möglich).
3. **Kommunikation:** **Sunset-Ankündigung** mit Datum an die Community; optional **Entschädigung**
   (In-Game-Währung/Cosmetic) für aktive Spieler.
4. **Plugin-/Lizenz-Abbau:** **Premium-Lizenzen** (MythicMobs Premium etc.) prüfen — Weiterverwendung auf dem
   Factions-Server (PvE-Events) statt Wegwerfen.
5. **Asset-Recycling:** Oraxen-Items, das 6-Klassen-Konzept, Maps und Lore als **Fundus** für die neuen Server
   (v. a. Factions-PvE-Events, Minigames-Maps).

---

## 6. Roadmap

1. **Weichenstellung** ✅ (dieses Dokument) — Auswahl Minigames + Factions, USPs festgehalten.
2. **Plugin-Recherche 26.2** — die 🔶-Einträge aus [Abschnitt 3](#3-plugin-shortlist--262-verfügbarkeit)
   verifizieren; Blocker früh erkennen (Anti-Cheat & Factions-Kern sind die kritischsten).
3. **MVP-Scope definieren** — minimaler spielbarer Umfang je Server (siehe Server-Docs).
4. **Prototyp/Testserver** — Server-Ordner-Gerüste (`minigames/`, `factions/`) mit Configs iterieren.
5. **Integration** — Lobby-Selector, HuskSync, Economy, Permissions, Velocity-Routing verdrahten.
6. **Beta & Balance** — Test-Checklisten aus [CHECKLISTS.md](CHECKLISTS.md) nutzen, Feedback-Runde.
7. **Launch + Post-Launch** — Content-Update-Kadenz, saisonale Events (Kandidat F als Ergänzung).

---

## 7. Verbleibende offene Fragen

Diese Punkte sind bewusst **nicht** vorentschieden und brauchen eine finale Bestätigung des Betreibers:

- **Economy:** Vollständig getrennt pro Server, oder eine netzwerkweite Coin-Währung für Cosmetics? (Vorschlag:
  getrennt + optionale netzwerkweite Cosmetic-Währung.)
- **Aufwand:** Realistische laufende Pflegezeit im Solo-Betrieb — trägt das Setup **zwei** neue Server parallel,
  oder gestaffelt (erst Minigames, dann Factions)?
- **Wiederverwendung:** Sollen Klassen-System/MMO-Items/Maps aus RPG/Skyblock weiterleben, oder kompletter
  Neustart?
- **Bestehende Spieler:** Sollen Skyblock/RPG-Fans gezielt aufgefangen werden (würde Kandidat D „Skyblock light"
  reaktivieren)?
- **Premium-Plugins:** Dürfen bezahlte Plugins (MythicMobs Premium etc.) auf den neuen Servern weiterverwendet
  werden?

---

**Verwandte Dokumente:** [PLANNING.md](PLANNING.md) · [SERVER_OVERVIEW.md](SERVER_OVERVIEW.md) ·
[minigames/README.md](minigames/README.md) · [factions/README.md](factions/README.md)

**Letzte Aktualisierung:** 2026-08-15
