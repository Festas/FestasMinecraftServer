# Neuausrichtung: Skyblock (überarbeitet) & Mining

> **Zweck:** Dieses Dokument hält die **Entscheidung** über die zwei neuen Server der Netzwerk-Neuausrichtung
> fest, dokumentiert die **Bewertung der Kandidaten**, den konkreten **Plugin-Stack (26.2)**, die
> **Netzwerk-Integration**, den **Rückbau** des Alt-Servers und die **Roadmap**.
>
> **Status:** Konzept / Weichenstellung. Die Auswahl ist ein begründeter Vorschlag; offene Punkte sind in
> [Abschnitt 7](#7-verbleibende-offene-fragen) markiert.

---

## 0. Ausgangslage & Leitplanken

- **Aktiv gepflegt:** Lobby (Hub) + Survival/Tycoon (eigene Economy, NextGens, PlotSquared, Rankup, Jobs).
- **Neuausrichtung:** Statt eines Social/PvP-Servers und eines Minigames-Servers setzt das Netzwerk auf einen
  **überarbeiteten Skyblock** (ohne Gilden, mit Freunde-Koop) und einen neuen **Mining**-Server (Abbau-Zonen
  mit aufwertbaren Spitzhacken). Die zuvor angedachten Konzepte **Factions** und **Minigames** sowie das
  Eigen-Plugin **CrossCraft-Guilds** entfallen.
- **Wird abgelöst:** Der **RPG-Spielmodus** (MythicMobs Premium, MythicDungeons, BetonQuest, Citizens) — wird
  eingestellt. Der **Server-Slot/Pfad `rpg` bleibt jedoch erhalten** und wird für den neuen **Mining**-Server
  **recycelt** (siehe [Abschnitt 5](#5-rückbau-des-rpg-spielmodus-slot-recycling)).
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
| 5 | **Wiederverwendung** | Nutzt vorhandene Systeme (Economy, HuskSync, Assets) |
| 6 | **Zielgruppe** | Casual/Hardcore, PvE/PvP, Solo/Gruppe |
| 7 | **Retention/Monetarisierung** | Battle-Pass, Cosmetics, Ränge, Wiederspielwert |
| 8 | **Time-to-Launch** | Wie schnell ist ein MVP spielbar? |

### 1.1 Kandidaten-Bewertung

| Kandidat | 1 Content-Aufwand | 2 Wartung | 3 Plugin-Reife 26.2 | 4 Abgrenzung | 5 Wiederverw. | 6 Zielgruppe | 7 Retention | 8 Time-to-Launch |
|----------|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| **A · Mining (Abbau-Zonen)** | ⬤⬤ | ⬤ | ⬤⬤ | ⬤⬤⬤ | ⬤⬤ | Casual, PvE, Solo | ⬤⬤⬤ | ⬤⬤ |
| **B · Skyblock (überarbeitet, Koop)** | ⬤⬤ | ⬤⬤ | ⬤⬤ | ⬤⬤ | ⬤⬤⬤ | Casual/Mittel, PvE, Solo/Freunde | ⬤⬤ | ⬤⬤ |
| **C · Factions/KitPvP** | ⬤⬤ | ⬤⬤⬤ | ⬤⬤ | ⬤⬤⬤ | ⬤⬤ | Hardcore, PvP, Gruppe | ⬤⬤⬤ | ⬤⬤ |
| **D · Minigames** | ⬤⬤ | ⬤ | ⬤⬤ | ⬤⬤⬤ | ⬤⬤ | Casual, PvE+PvP | ⬤⬤⬤ | ⬤⬤⬤ |
| **E · Prison** | ⬤⬤ | ⬤⬤ | ⬤⬤ | ⬤ (nah am Tycoon) | ⬤⬤⬤ | Casual/Grind, PvE+PvP | ⬤⬤ | ⬤⬤ |
| **F · Event-/Seasonal** | ⬤ | ⬤ | ⬤⬤ | ⬤⬤ | ⬤⬤ | Casual, temporär | ⬤ | ⬤⬤⬤ |

**Legende Kriterien 1–3:** höher = mehr Aufwand/Last bzw. bessere Reife. Kriterium 4/5/7/8: höher = besser.

---

## 2. Entscheidung

Die Weichenstellung fällt auf einen **überarbeiteten Skyblock** plus einen neuen **Mining**-Server:

| Slot | Server | Kandidat | Ordner | USP |
|------|--------|----------|--------|-----|
| **Casual (Abbau)** | **Mining** | A | [`rpg/`](../rpg/) *(recycelt)* | Abbau-Zonen mit **aufwertbaren Spitzhacken** (mehr Blöcke pro Schlag) und **schrittweise freischaltbaren Zonen**; einfacher, klarer Progressions-Loop |
| **Casual/Koop** | **Skyblock** | B | [`skyblock/`](../skyblock/) | Klassischer Skyblock **ohne Gilden**, aber mit **Freunde-Koop** (Insel-Mitglieder gemeinsam bauen); nutzt vorhandenen Skyblock-Stack weiter |

### 2.1 Begründung

- **Mining statt Prison (E):** Prison überschneidet sich stark mit dem Tycoon-Grind (Mine → Rankup →
  Prestige). Der Mining-Server grenzt sich über den **Spitzhacken-/Zonen-Loop** ab: Fokus auf aktives
  Abbauen, sichtbare Werkzeug-Progression und Zonen-Freischaltung statt passiver Generatoren/Plots.
- **Skyblock statt Factions (C):** Statt eines wartungs- und moderationsintensiven PvP-Servers wird der
  **bestehende Skyblock** überarbeitet und behalten — **ohne Gilden**, aber mit **Freunde-Koop** über die
  Insel-Mitglieder von SuperiorSkyblock2. Das nutzt den bereits vorhandenen Skyblock-Stack maximal weiter
  (Kriterium 5) und hält die Wartungslast im Solo-Betrieb beherrschbar (Kriterium 2).
- **Verworfen:** C (Factions) — zu hohe Moderations-/Anti-Cheat-Last für Solo-Betrieb; das dafür gedachte
  Eigen-Plugin **CrossCraft-Guilds** wird entfernt. D (Minigames) — als Konzept zurückgestellt. E/F sind als
  **Ergänzungen** denkbar (Prison-Elemente, Seasonal-Events), aber nicht als eigener Server.

> **Alternative bei knapper Zeit:** Sollte der Solo-Betrieb nur **einen** neuen Server tragen, startet zuerst
> **Skyblock** (vorhandener Stack, schnellster Weg zurück zu spielbarem Content), und **Mining** folgt in Phase 2.

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
| spark | Performance-Profiling | ✅ | Frei |

### 3.2 Mining-spezifisch

| Plugin | Zweck | 26.2 | Hinweis |
|--------|-------|:----:|--------|
| Mining-/Zonen-Kern — **A) X-Prison** (Core) *oder* **B) EcoItems + EcoEnchants + AxMines** (modular) | Kern-Loop: Spitzhacke, Mehrblock-Abbau, Zonen | 🔶 prüfen | A = wenigste Plugins/Wartung (empfohlen), B = flexibler; Blocker! |
| **Auto-Regeneration** der Abbau-Zonen (in X-Prison bzw. **AxMines**/JetsPrisonMines enthalten) | Blöcke füllen sich wieder auf | 🔶 prüfen | Bei Weg A im Core enthalten |
| WorldGuard | Zonengrenzen, Schutz (kein Griefing) | ✅ | Bereits im Netzwerk |
| WorldEdit/FAWE | Zonen bauen/zurücksetzen | ✅ | Bereits im Netzwerk |
| **EconomyShopGUI** (Shop/Auto-Sell, Vault-kompatibel) | Blöcke verkaufen → Upgrades finanzieren | 🔶 prüfen | Bei Weg A Core-intern; Economy-Sink & -Quelle |
| **BattlePass** / Skript-Pass + **PlayerParticles** (Cosmetics) | Trails, Effekte, Battle-Pass | 🔶 prüfen | Retention/Monetarisierung |

**Mining-Ausbau (kompakt, Phase 2+):**
- **Rank-/Prestige-/Mine-Progression:** klare Mine-Leiter, Rankups zwischen Zonen und Prestige als Loop-Neustart mit
  permanenten Boni.
- **Black Market:** rotierender Händler als Ressourcen-Sink für Geld, Tokens, seltene Drops und saisonale Angebote.
- **Enchant-Balancing:** frühe Enchants schlicht, starke Flächen-/Endgame-Enchants erst über Mine-Tiers,
  Spitzhacken-Level oder Prestige.
- **Weitere Battlepass-/Quest-Saisons:** wiederkehrende Seasons mit Mining-, Prestige- und Event-Zielen für
  langfristige Retention.

### 3.3 Skyblock-spezifisch

| Plugin | Zweck | 26.2 | Hinweis |
|--------|-------|:----:|--------|
| **SuperiorSkyblock2** | Insel-Kern inkl. **Koop/Insel-Mitglieder** (Freunde einladen) | 🔶 prüfen | Bereits im Repo; Koop-Modell ersetzt Gilden |
| SlimeWorldManager | Insel-Welten-Verwaltung | 🔶 prüfen | Bereits im Repo |
| VoidGen | Void-World-Generator | 🔶 prüfen | Bereits im Repo |
| JetsMinions | Minion-/Automations-System | 🔶 prüfen | Bereits im Repo |
| Aurora/AuroraCollections | Collections/Progression | 🔶 prüfen | Bereits im Repo |
| Economy (CoinsEngine/Vault), DeluxeBazaar, GlobalMarketPlus | Handel/Markt | 🔶 prüfen | Bereits im Repo |

**Aus dem Alt-Bestand wiederverwendbar:** Oraxen-Items, Balancing-Konventionen (siehe
[CONTRIBUTING.md](../CONTRIBUTING.md)), Maps und Lore-Bausteine aus dem RPG-Server, ggf. MythicMobs (falls
Premium-Lizenz weiterverwendbar) für PvE-Events/Bosse.

---

## 4. Netzwerk-Integration (beide Server)

- **Economy-Strategie (Entscheidung):** **Isolierte Währung pro Server** (analog Survival), um Balance getrennt
  zu halten. Optional wird eine **netzwerkweite Premium-/Cosmetic-Währung** via CoinsEngine ergänzt (nur für
  Cosmetics/Battle-Pass, nicht für Gameplay-Balance). → siehe offene Frage in [Abschnitt 7](#7-verbleibende-offene-fragen).
- **Datenhaltung:** Eigenes **MariaDB-Schema pro Server** (isoliert), **Redis** für Sessions/Cross-Server-Messaging.
- **HuskSync:** Synchronisiert werden **Cosmetics und Ränge**, **nicht** die gameplay-relevanten Inventare der
  Server (Mining/Skyblock haben server-eigene Inventare).
- **Velocity-Routing:** Neue/aktualisierte Einträge im Lobby-Selector (**DeluxeMenus**), Anpassung von
  **MOTD (MiniMOTD)** und **TAB**. Server-Namen im Velocity-Config: `rpg` (recycelt — der bestehende
  RPG-Slot wird zum Mining-Server), `skyblock`.
- **Bedrock:** Jede GUI und jedes Custom-Item auf **Geyser/Floodgate**-Kompatibilität testen (Oraxen-Texturen,
  Menü-Formulare).
- **Ränge & Permissions:** Bestehende **LuckPerms**-Gruppen wiederverwenden, server-spezifische Kontexte
  (`server=rpg`, `server=skyblock`) definieren.

---

## 5. Rückbau des RPG-Spielmodus (Slot-Recycling)

> **Wichtig:** Der **Server-Slot/Pfad `rpg` wird nicht abgeschaltet, sondern recycelt** — er wird zum neuen
> **Mining**-Server. Dadurch bleiben Velocity-Routing, MariaDB/Redis, HuskSync, Resource-Pack und der
> LuckPerms-Kontext `server=rpg` erhalten. Rückgebaut wird nur der **RPG-Spielmodus** (Content/Plugins).

1. **Archivierung des RPG-Contents:** Die Alt-Configs/Docs des RPG-Spielmodus bleiben als **Archiv** erhalten
   (bereits so markiert) und werden im `rpg/`-Ordner **schrittweise durch Mining-Configs ersetzt**. Kein
   aktives Pflegen des RPG-Contents mehr; klar gekennzeichnete Archiv-Hinweise in den READMEs.
2. **Daten-Migration/Backup:** Spielerdaten des RPG-Slots **sichern**, bevor der Content umgestellt wird. Klären, ob
   Cosmetics/Ränge in die neuen Server überführt werden (via HuskSync möglich).
3. **Kommunikation:** **Sunset-Ankündigung** des RPG-Spielmodus mit Datum an die Community; optional
   **Entschädigung** (In-Game-Währung/Cosmetic) für aktive Spieler.
4. **Plugin-/Lizenz-Abbau:** **Premium-Lizenzen** (MythicMobs Premium etc.) prüfen — Weiterverwendung für
   PvE-Events statt Wegwerfen.
5. **Asset-Recycling:** Oraxen-Items, Maps und Lore als **Fundus** für die neuen Server.
6. **Guilds-Bereinigung:** Das Eigen-Plugin **CrossCraft-Guilds** wird nicht mehr benötigt und wurde aus dem
   Repository **entfernt**.

---

## 6. Roadmap

1. **Weichenstellung** ✅ (dieses Dokument) — Auswahl Skyblock (überarbeitet) + Mining, USPs festgehalten.
2. **Plugin-Recherche 26.2** — die 🔶-Einträge aus [Abschnitt 3](#3-plugin-shortlist--262-verfügbarkeit)
   verifizieren; Blocker früh erkennen (Mining-Kern und Skyblock-Kern für 26.2 sind die kritischsten).
3. **MVP-Scope definieren** — minimaler spielbarer Umfang je Server (siehe Server-Docs).
4. **Prototyp/Testserver** — recycelter `rpg/`-Slot (wird zum Mining-Server) und vorhandene `skyblock/`-Configs iterieren.
5. **Integration** — Lobby-Selector, HuskSync, Economy, Permissions, Velocity-Routing verdrahten.
6. **Beta & Balance** — Test-Checklisten aus [CHECKLISTS.md](CHECKLISTS.md) nutzen, Feedback-Runde.
7. **Launch + Post-Launch** — Content-Update-Kadenz, saisonale Events sowie neue Battlepass-/Quest-Saisons; für Mining
   zusätzlich Black Market, Enchant-Balance und Endgame-Progression iterieren.

---

## 7. Verbleibende offene Fragen

Diese Punkte sind bewusst **nicht** vorentschieden und brauchen eine finale Bestätigung des Betreibers:

- **Economy:** Vollständig getrennt pro Server, oder eine netzwerkweite Coin-Währung für Cosmetics? (Vorschlag:
  getrennt + optionale netzwerkweite Cosmetic-Währung.)
- **Aufwand:** Realistische laufende Pflegezeit im Solo-Betrieb — trägt das Setup **zwei** neue Server parallel,
  oder gestaffelt (erst Skyblock, dann Mining)?
- **Skyblock-Umfang:** Bleibt die MMO-Integration (Klassen/MMOItems) auf Skyblock erhalten, oder wird der Server
  auf einen schlankeren Koop-Skyblock reduziert?
- **Mining-Design:** Anzahl Zonen zum Launch, Abbau-Muster pro Spitzhacken-Stufe (1×1 → 3×3 → …), Freischalt-
  und Verkaufs-Balancing.
- **Premium-Plugins:** Dürfen bezahlte Plugins (MythicMobs Premium etc.) auf den neuen Servern weiterverwendet
  werden?

---

**Verwandte Dokumente:** [PLANNING.md](PLANNING.md) · [SERVER_OVERVIEW.md](SERVER_OVERVIEW.md) ·
[prison/README.md](prison/README.md) · [skyblock/README.md](skyblock/README.md)

**Letzte Aktualisierung:** 2026-08-19
