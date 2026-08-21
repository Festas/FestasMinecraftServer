# Survival-/Tycoon-Server — Optimaler Plugin-Stack

> **✅ Aktiv (Stand 26.2).** Fokussierter Plugin-Stack für den **aktiv gepflegten** Survival-/Tycoon-Server.
> Diese Datei bündelt den **Ist-Bestand** (ordnergenau aus [`survival/plugins/`](../../survival/plugins/)) und
> ergänzt **konkrete, gut durchdachte Empfehlungen** für fehlende betriebskritische Bausteine sowie mögliche
> Upgrades. Übergeordnete Referenz: [../PLUGINS.md](../PLUGINS.md#survival-server-plugins) · Konzept:
> [README.md](README.md) · [TYCOON.md](TYCOON.md).

Die Spalte **Status** ordnet jedes Plugin ein:

- ✅ **Kern** — für den Tycoon-Loop erforderlich, bereits in [`survival/plugins/`](../../survival/plugins/).
- 🟢 **Vorhanden** — bereits installiert, Komfort/Performance/Content.
- ➕ **Empfohlen (neu)** — sinnvolle Ergänzung, **noch nicht** installiert (siehe [Empfohlene Extra-Plugins](#a-empfohlene-extra-plugins-neu)).
- 🔁 **Upgrade prüfen** — möglicher Ersatz für ein vorhandenes Plugin (siehe [Upgrades](#b-mögliche-upgrades--ersetzungen)).
- 🧹 **Redundanz prüfen** — evtl. entbehrlich (siehe [Aufräum-Kandidaten](#c-redundanzen--aufräum-kandidaten)).

> ⚠️ **26.2-Blocker-Check:** Für **jedes neue** Plugin (➕/🔁) muss vor dem Ausrollen ein aktueller **26.2**-Build
> auf der Bezugsquelle (SpigotMC/Polymart/Modrinth/Hangar/GitHub) bestätigt werden. Die vorhandenen Plugins
> laufen bereits auf 26.2.

---

## 1. Tycoon & Progression *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **NextGens** | Generator-System — **Kern des Tycoon** (25 Tiers × Sub-Levels, Auto-Produktion) | ✅ Kern |
| **Rankup** | 25-stufige, geldbasierte Rang-Progression | ✅ Kern |
| **Skript** | Custom Tycoon-Logik (Sell Wand, Chunk Collector, Prestige, Casino, Boss-/Weekly-Events, Daily Rewards, dynamische Börse) | ✅ Kern |
| **CMI** (Rang-Engine) | Zeitbasierte Rang-Leiter (`autorank`-Track) — treibt LuckPerms per `AutoRankUp`, siehe [ZEITRANG_CMI.md](ZEITRANG_CMI.md) | ✅ Kern |
| ~~**Autorank**~~ | ~~Spielzeit-basierte Belohnungen/Meilensteine~~ — **entfernt**, durch CMI-Rang-Engine ersetzt | ❌ Entfernt |

---

## 2. Economy & Shops *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Vault** | Economy-API-Bridge (angebunden an CMI) | ✅ Kern |
| **ShopGUIPlus** *(paid)* | Server-Shop-GUI (Kauf/Verkauf, Kategorien, dynamische Preise) | 🟢 Vorhanden |
| **GlobalMarketPlus** | Globaler Marktplatz / Auktionshaus (Spieler-zu-Spieler) | 🟢 Vorhanden |
| **ChestShop** | Spieler-Läden per Truhe & Schild | 🔁 Upgrade prüfen |
| **Jobs** | Berufe für zusätzliches Einkommen | 🟢 Vorhanden |

---

## 3. Land, Welten & Building *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Lands** | Chunk-basiertes Land-Claiming in der Survival-Welt — Griefing-Schutz, Claim-Blöcke (Spielzeit + Geld), Rollen-GUI, Nationen, BlueMap-Integration | ✅ Kern |
| **PlotSquared** | Plot-Claiming (`freebuild`-Welt) + ehem. `town`-Welt (jetzt durch Lands ersetzt), Schematics | ✅ Kern |
| **Multiverse-Core** (+ **Multiverse-Inventories**) | Welten `tycoon`/`town`/`freebuild` mit getrennten Inventaren | ✅ Kern |
| **VoidGen** | Void-/Leerwelt-Generator (Plot-/Freebuild-Welten) | 🟢 Vorhanden |
| **Chunky** | Chunk-Pre-Generierung (Performance) | 🟢 Vorhanden |
| **WorldGuard** | Regionen-Schutz (TNT/Creeper/Feuer/Wither begrenzt) | ✅ Kern |
| **FastAsyncWorldEdit (FAWE)** | Async World-Editing (Skripte/Resets) | 🟢 Vorhanden |
| **AxiomPaper** | Advanced Building (Client-Side-Editing, Large-Scale-Edits) | 🟢 Vorhanden |

---

## 4. Optik & Content *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Oraxen** | Custom Items/Texturen (Bedrock-tauglich prüfen) | 🟢 Vorhanden |
| **RoseStacker** (+ **RoseGarden**) | Entity-/Item-Stacking (Performance) | 🟢 Vorhanden |
| **HeadDatabase** *(paid)* | Dekorative Köpfe (Deko/Shops) | 🟢 Vorhanden |
| **LibsDisguises** *(paid)* | Verkleidungen (Mob/Spieler) — Events/Bosse | 🧹 Redundanz prüfen |
| **BlueMap** | 3D-Web-Karte (Live-Rendering, Marker) | 🟢 Vorhanden |

---

## 5. Core, Management & Bibliotheken *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **CMI** (+ **CMILib**) *(paid)* | Core-Management (Economy, Homes, Teleport, Kits, Chat, AFK, Hologramme) | ✅ Kern |
| **LuckPerms** | Permissions, Rang-/Plot-Limits | ✅ Kern |
| **PlaceholderAPI** | Platzhalter (fast alle Plugins) | ✅ Kern |
| **ProtocolLib** | Packet-Basis (Backend) | ✅ Kern |
| **CommandAPI** / **NBTAPI** | Command- bzw. NBT-Bibliotheken (Backend) | ✅ Kern |
| **bStats** / **faststats** / **spark** | Metriken, Performance-Profiling | 🟢 Vorhanden |

---

## A. Empfohlene Extra-Plugins *(neu)*

> **Priorität:** Zuerst die **betriebskritischen** Lücken (A1–A4), dann Schutz/Retention (A5–A7). Alle Vorschläge
> sind **26.2-Blocker-pflichtig** (Build bestätigen). Kostenpflichtige Alternativen sind markiert.

### A1. Anti-Cheat — **größte Lücke** *(hohe Priorität)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **GrimAC** | Anti-Cheat (Fly/Reach/Fast-Break/Movement) — Survival hat aktuell **keinen** Anti-Cheat, obwohl Skyblock/Mining GrimAC bereits nutzen | Frei | ➕ Empfohlen |
| **Vulcan** *(Alternative)* | Etablierter Premium-Anti-Cheat | Kauf | ➕ Optional |

> **Begründung:** Ein Economy-/Grinding-Server ohne Anti-Cheat ist besonders anfällig für Item-Duping via
> Movement-/Reach-Exploits. **GrimAC** ist netzwerkintern bereits bewährt → gleiche Config-Basis, geringe
> zusätzliche Wartung.

### A2. Block-Logging & Rollback *(hohe Priorität)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **CoreProtect** | Block-/Container-Logging, Grief-Rollback, Inspektor-Tool | Frei | ➕ Empfohlen |

> **Begründung:** Bau-/Plot-Server ohne Logging haben **keine** Rollback-Option bei Grief oder Fehlern.
> CoreProtect ist der De-facto-Standard (leichtgewichtig, MySQL/SQLite).

### A3. Backups *(hohe Priorität)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **Backuper** | Automatisierte, geplante Welt-/Plugin-Backups (lokal/Cloud) | Frei | ➕ Empfohlen |

> **Begründung:** Persistente Tycoon-Economy = hoher Datenwert. Ohne automatisierte Backups droht Totalverlust.
> Ergänzt die MySQL-Sicherung um Welt-/Config-Backups.

### A4. Geplante Neustarts *(hohe Priorität)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **SimpleAutoRestart** | Geplante Server-Neustarts mit Vorwarnungen | Frei | ➕ Empfohlen |

> **Begründung:** Im Mining-/Skyblock-Stack bereits vorgesehen; verhindert Memory-Leak-/TPS-Degradation über
> lange Laufzeiten.

### A5. Alt-/Bot-Schutz

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **AntiVPN** / **VpnGuard** | VPN-/Proxy-Erkennung gegen Alt-/Bot-Accounts | Frei/Freemium | ➕ Optional |

> **Begründung:** Schützt die Economy vor Multi-Accounting bei Voting-Rewards und Boni.

### A6. Discord-Anbindung *(netzwerkweit)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **DiscordSRV** | Chat-Bridge, Konsolen-Log, Rollen-Sync mit LuckPerms | Frei | ➕ Empfohlen |

> **Hinweis:** Netzwerkweiter Nutzen; wird pro Backend-Server installiert (oder zentral auf Survival als
> Community-Hub). Großer Retention-/Community-Hebel bei geringem Aufwand.

### A7. Voting & Belohnungen *(netzwerkweit)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **NuVotifier** | Empfang von Server-Listen-Votes (Backend) | Frei | ➕ Empfohlen |
| **VotingPlugin** | Vote-Belohnungen, -Partys, -Ränge | Frei | ➕ Empfohlen |

> **Begründung:** Klassischer, günstiger Traffic-/Retention-Treiber (Votes auf Serverlisten → In-Game-Belohnung).
> Zusammen mit **AntiVPN** (A5) gegen Vote-Farming absichern.

---

## B. Mögliche Upgrades / Ersetzungen

| Aktuell | Vorschlag | Warum wechseln (Trade-off) | Status |
|---------|-----------|----------------------------|:------:|
| **ChestShop** (Schild-basiert, alt) | **QuickShop-Hikari** *(frei)* | Moderne GUI, Bedrock-freundlicher, aktiv gepflegt, bessere Preis-/Log-Features. **Migration** der Spieler-Läden nötig → nur wenn Player-Shops wichtig sind. | 🔁 Upgrade prüfen |
| ~~**Autorank**~~ | **CMI**-eigene Playtime-/AutoRank-Funktion *(umgesetzt)* | ✅ **Erledigt:** CMI treibt die zeitbasierte Leiter (`autorank`-Track) über `AutoRankUp`; Autorank wurde entfernt. Siehe [ZEITRANG_CMI.md](ZEITRANG_CMI.md). | ✅ Erledigt |
| **GlobalMarketPlus** | **AkariAuctionHouse** / **zAuctionHouse** | Nur falls GMP-Pflege/26.2-Support wackelt; sonst behalten. | 🔁 Nur bei Bedarf |
| **BlueMap** | *(behalten)* | Bereits Best-in-Class; kein Wechsel nötig. | 🟢 Behalten |
| **ShopGUIPlus** *(paid)* | *(behalten)* | Guter Standard; nur Balancing pflegen. | 🟢 Behalten |

**Kostenpflichtig, wenn Budget vorhanden (Qualitäts-Upgrades):**

- **TAB** (bereits auf dem Proxy) genügt; für Lobby-/Survival-Optik ggf. **animierte TAB/Scoreboard-Configs**
  statt eines neuen Plugins.
- **ShopGUIPlus** / **HeadDatabase** sind bereits Paid und gut gewählt.
- Für Bedrock-taugliche Menüs generell **Oraxen** (vorhanden) beibehalten; keine teure Alternative nötig.

---

## C. Redundanzen / Aufräum-Kandidaten

| Plugin | Prüfen | Status |
|--------|--------|:------:|
| ~~**Autorank** vs. **CMI**~~ | **Erledigt:** CMI übernimmt Playtime-/AutoRanks; Autorank entfernt | ✅ Erledigt |
| **AxiomPaper** + **FAWE** | Beide behalten (Axiom = Client-Building, FAWE = Skripte/Resets), aber Zweck dokumentieren | 🟢 Bewusst behalten |
| **LibsDisguises** | Nur für Events/Bosse → Nutzen vs. Pflegeaufwand abwägen | 🧹 Redundanz prüfen |

---

## Offene Punkte

- ~~**Town-Welt: PlotSquared → Lands**~~ ✅ **Erledigt:** Lands installiert und konfiguriert (`plugins/Lands/`). Land-Claiming läuft chunk-basiert in `world`/`town`; PlotSquared bleibt für `freebuild` (und ggf. Tycoon) bestehen.
- **A1 Anti-Cheat (GrimAC)** auf Survival ausrollen — höchste Priorität (Duping-/Exploit-Schutz).
- **A2–A4** (CoreProtect, Backuper, SimpleAutoRestart) als betriebskritische Basis nachrüsten.
- **A6/A7** (DiscordSRV, NuVotifier + VotingPlugin) netzwerkweit planen; Vote-Farming via **AntiVPN** absichern.
- **ChestShop → QuickShop-Hikari** entscheiden (Migrationsaufwand vs. Nutzen).
- ~~**Autorank vs. CMI** klären und ggf. eines entfernen.~~ ✅ **Erledigt:** CMI-Rang-Engine treibt die zeitbasierte Leiter, Autorank entfernt — siehe [ZEITRANG_CMI.md](ZEITRANG_CMI.md).
- **26.2-Builds** aller ➕/🔁-Kandidaten bestätigen.

---

## Siehe auch

- [Survival-Übersicht](README.md) · [Tycoon-Referenz](TYCOON.md) · [Plots](PLOTS.md) · [Progression](PROGRESSION.md)
- [Lobby-Plugin-Stack](../lobby/PLUGINS.md)
- [Netzwerk-Plugin-Referenz](../PLUGINS.md)

---

**Letzte Aktualisierung:** 2026-08-18

**Status:** ✅ Aktiv (26.2) — Ist-Bestand + empfohlene Extra-Plugins
