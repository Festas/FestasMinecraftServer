# Mining-Server — Konzept & X-Prison-Progression

> **🟢 X-Prison-Kernprogression umgesetzt.** Der Mining-Server (Velocity `rpg`, spielerseitig „Mining",
> Weltname `world`, täglicher Neustart 04:05 Europe/Berlin) nutzt **X-Prison** als gebündelten Mining-Core
> (Weg A). Die **Kernprogression** — Ränge/Zonen, AutoSell, Pickaxe/Enchants, endloses Prestige, Rebirth,
> Ascension und permanente Multiplier — ist konfiguriert (siehe
> [Umgesetzte X-Prison-Progression](#umgesetzte-x-prison-progression-fundament)).
> Angrenzende Systeme (WorldGuard-Regionen, DeluxeMenus-Navigator, CMI-Economy, PlaceholderAPI, TAB, Skript)
> bauen auf diesem Fundament auf. Grundlage: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Casual-Server rund um **Abbau-Zonen**: Spieler bauen mit einer **besonderen Spitzhacke** Blöcke ab,
verkaufen sie (**AutoSell**) und schalten damit **stärkere Spitzhacken**, **Enchants** und **neue Zonen** frei.
Der Kern-Loop ist ein **endloser, milestone-getriebener Loop**:
**Zone abbauen → AutoSell → Geld → Rankup (neue Zone) → … → Prestige (Reset + permanenter Multiplier) →
… → Rebirth → … → Ascension (endlos).**

---

## Rolle im Netzwerk

| Eigenschaft | Wert |
|-------------|------|
| **Slot** | Casual |
| **Server-Name (Velocity/Proxy)** | `rpg` *(technischer Backend-Name; spielerseitig „Mining", in Logs oft „prison")* |
| **Version** | Paper 26.2 (geplant) |
| **Ordner** | [`rpg/`](../../rpg/) *(recycelter RPG-Slot für den Mining-Server)* |
| **Economy** | Server-isoliert; optionale netzwerkweite Cosmetic-Währung |
| **Datenbank** | X-Prison lokal (H2) + zentrale Dienste (LuckPerms/Plan via MariaDB) |
| **Bedrock-Support** | Aktuell Java-only (kein Geyser/Floodgate im Proxy-Stack) |
| **Sync (HuskSync)** | Nicht aktiv im aktuellen Ist-Stand |

---

## Kern-Idee & USP

- **Besondere Spitzhacke:** Jeder Spieler besitzt eine aufwertbare Spitzhacke als zentrales Werkzeug.
- **Stärker werdende Spitzhacken:** Höhere Stufen bauen **mehr Blöcke auf einmal** ab (z. B. 1×1 → 3×3 →
  größere Muster / ganze Adern) und graben schneller.
- **Freischaltbare Zonen:** Nach und nach werden **neue Abbau-Zonen** mit **anderen/wertvolleren Blöcken**
  freigeschaltet — sichtbare Progression und immer neue Ziele.
- **Verkaufen → Aufwerten → Freischalten:** Abgebaute Blöcke werden verkauft; der Erlös finanziert
  Spitzhacken-Upgrades und Zonen-Freischaltungen.
- **Abgrenzung zum Tycoon:** Fokus auf aktives Abbauen und Spitzhacken-Progression statt auf
  passiven Generatoren/Plots des Survival/Tycoon.

---

## Kern-Systeme (MVP-Vorschlag)

| System | Zweck | MVP? |
|--------|-------|:----:|
| **Abbau-Zonen** | Mehrere Bereiche mit unterschiedlichen Block-Sets | ✅ |
| **Aufwertbare Spitzhacke** | Stufen erhöhen Abbau-Radius/-Menge und Tempo | ✅ |
| **Zonen-Freischaltung** | Neue Zonen gegen Fortschritt/Währung öffnen | ✅ |
| **Verkauf/Economy** | Blöcke zu Geld machen (server-isoliert) | ✅ |
| **Auto-Regeneration der Zonen** | Abgebaute Blöcke füllen sich wieder auf | ✅ |
| **Ränge/Prestige/Rebirth** | Endlose Progression (P0–P10 + Ascension, R0–R5) mit permanenten Multipliern | ✅ |
| **Cosmetics/Battle-Pass** | Retention, Belohnungen | ⬜ Phase 2 |
| **Verzauberungen/Boosts der Spitzhacke** | Enchants inkl. AutoSell/Multiplier, stufenweise Freischaltung | ✅ |
| **Black Market** | Rotierende Late-Game-Angebote als Ressourcen-Sink | ⬜ Phase 3 |

---

## Umgesetzte X-Prison-Progression (Fundament)

> Konfiguriert unter `rpg/plugins/X-Prison/`. Minen-Koordinaten/Regionen sind **Server-State**
> (im Spiel via `/mines create` + `/mines panel`); alles andere lebt in getrackter Config.

### Währungen & Rollen

| Währung | Rolle | Hauptquellen | Haupt-Sinks |
|---------|-------|--------------|-------------|
| **money** (Geld) | Kern-Fortschritt | AutoSell, Rankup-Boni | Rankup, Prestige, Private-Mine-Ausbau |
| **tokens** (Token) | Power-Grind | Rankup/Prestige/Rebirth/Pickaxe-Milestones, tokenfinder | Enchants, Pickaxe-Quality, Robots, AutoMiner |
| **gems** (Edelsteine) | Premium | Milestones, gemfinder | BlackMarket, Top-/Flächen-Enchants, Cosmetics |

### Rang- & Zonen-Ladder (`mine_a … mine_j`)

Jeder Rang schaltet die gleichnamige Zone frei (Rang A → `mine_a`, … J → `mine_j`). Die Zonen-IDs sind
**netzwerkweit verbindlich** (WorldGuard-Regionen + Menüs nutzen exakt dieselben Namen). X-Prison gated den
Zutritt nicht selbst — die Freischaltung erfolgt rang-basiert über Navigator/Menü bzw. WG-Region. Der
X-Prison-Rang ist die alleinige Quelle der Progression (nicht Rankup/Autorank/CMI-Ränge).

| Rang | Zone | Rankup-Kosten (money) | Block-Zusammensetzung (im `/mines panel` setzen) |
|:----:|------|----------------------:|--------------------------------------------------|
| A | `mine_a` | 0 | STONE 70 / COBBLESTONE 25 / COAL_ORE 5 |
| B | `mine_b` | 5.000 | STONE 55 / COAL_ORE 30 / COPPER_ORE 15 |
| C | `mine_c` | 15.000 | STONE 45 / COAL_ORE 25 / IRON_ORE 25 / COPPER_ORE 5 |
| D | `mine_d` | 50.000 | STONE 35 / IRON_ORE 30 / GOLD_ORE 25 / COAL_ORE 10 |
| E | `mine_e` | 125.000 | STONE 30 / GOLD_ORE 25 / REDSTONE_ORE 25 / LAPIS_ORE 20 |
| F | `mine_f` | 300.000 | STONE 25 / REDSTONE_ORE 20 / LAPIS_ORE 20 / EMERALD_ORE 20 / GOLD_ORE 15 |
| G | `mine_g` | 750.000 | STONE 20 / EMERALD_ORE 25 / DIAMOND_ORE 30 / GOLD_ORE 25 |
| H | `mine_h` | 1.800.000 | DEEPSLATE 20 / DEEPSLATE_DIAMOND_ORE 30 / DEEPSLATE_EMERALD_ORE 25 / DEEPSLATE_GOLD_ORE 25 |
| I | `mine_i` | 4.500.000 | DEEPSLATE 15 / DEEPSLATE_DIAMOND_ORE 35 / DEEPSLATE_EMERALD_ORE 40 / ANCIENT_DEBRIS 10 |
| J | `mine_j` | 10.000.000 | DEEPSLATE_DIAMOND_ORE 40 / DEEPSLATE_EMERALD_ORE 30 / ANCIENT_DEBRIS 25 / NETHERITE_BLOCK 5 |

Jeder Rankup gibt zusätzlich Token (250 → 25.000) und ab Rang E Gems; Rang J zusätzlich 250.000 money.
AutoSell-Preise (`autosell.yml`) steigen **monoton leicht exponentiell** entlang der Ladder
(z. B. STONE 1 → COAL_ORE 8 → IRON_ORE 20 → GOLD_ORE 70 → EMERALD_ORE 135 → DIAMOND_ORE 180 →
DEEPSLATE_DIAMOND_ORE 260 → ANCIENT_DEBRIS 3.000 → NETHERITE_BLOCK 12.000), sodass höhere Zonen spürbar
lukrativer sind, ohne die vorherigen zu entwerten.

### Pickaxe & Enchants (stufenweise Freischaltung)

- **Pickaxe-Level** (`pickaxe-levels.yml`): Formel `40*(level-1)^2`, max 300, Milestone-Rewards (Token/Gems)
  bei 25/50/75/100/150/200/250/300.
- **Pickaxe-Quality** (`pickaxe-quality.yml`): 10 Tiers (Tokens), permanenter Multiplier `1+0.08*tier`
  (Tier 10 = ×1.8 auf money/tokens/gems) — dauerhafter, prestige-unabhängiger Ertragsbonus.
- **Enchant-Gates** (`pickaxeLevelRequired`): früh (efficiency/fortune/haste/tokenfinder/gemfinder) ab Lvl 1;
  mid: salary/blessing/charity 25, layer/explosive 50, blockbooster 60; late: laserbeam 100, nuke 125.
- **Endlos-Enchants:** efficiency & fortune mit aktiviertem **Enchant-Prestige** (permanenter Multiplier je
  Enchant-Prestige) → endlos investierbar; Kosten skalieren exponentiell (`baseCost*pow(1.05,level)`).
  Flächen-Enchants triggern nur in Regionen, deren Name mit `mine` beginnt (→ `mine_a…mine_j`).

### Endloser Prestige → Rebirth → Ascension-Loop

- **Prestige P0–P10** (`prestiges.yml`, `currency: money`): feste Kosten 25M → 18B; `reset_rank_after_prestige: true`.
  Jeder Prestige gibt Token/Gems **und** schaltet einen permanenten Multiplier `xprison.multiplier.pN` frei.
- **Ascension (unlimited Prestige > P10):** `unlimited_prestiges.enabled: true`, geometrische Kostenformel
  **`cost(n) = 28,8 Mrd × 1.6^(n-11)`** (setzt P10 = 18B glatt fort). Jeder Ascension-Prestige gibt stetig
  Token/Gems; Meilensteine bei **P25 / P50 / P100 / P250 / P500 / P1000** schalten
  `xprison.multiplier.asc1 … asc6` frei.
- **Rebirth R0–R5** (`rebirths.yml`): große Gates (Rang 10 + Prestige 10 + eskalierende money 25B → 500B /
  tokens 1,5M → 25M). Jeder Rebirth vergibt `xprison.rebirthN` **und** `xprison.multiplier.rN` + Token/Gems.
  Über R5 hinaus ist **Ascension** (der endlose unlimited-Prestige-Loop) die dauerhafte Fortsetzung.

### Permanente Multiplier-Leiter (`multipliers.yml`)

Monoton steigende Rank-Multiplier (der **stärkste passende Key gewinnt**, multiplikativ auf den Ertrag),
geprüft als Permission `xprison.multiplier.<key>` (`use-luckperms-groups: false`):

| Stufe | Keys | money | tokens | gems |
|-------|------|:-----:|:------:|:----:|
| Prestige | p1 → p10 | 1.1 → 2.25 | 1.05 → 1.5 | 1.0 → 1.45 |
| Rebirth | r1 → r5 | 3.0 → 12.0 | 2.0 → 6.5 | 1.8 → 5.5 |
| Ascension | asc1 → asc6 | 13.5 → **25.0** | 7.0 → **15.0** | 6.0 → **15.0** |

Deckel (`currency-multipliers`, greifen für Event-/Booster-Multiplier via `/gmulti` `/pmulti`):
money 25× / tokens 15× / gems 15×. So lohnt sich **jeder Reset dauerhaft** und das Endgame bleibt endlos.

### Milestone-Kadenz (Kurzüberblick)

- **Ränge A–J:** Kostenstufe ~×2,2–3 pro Rang (0 → 10M money) — schnelle Früh-Progression, längere Endränge.
- **Pickaxe-Milestones:** Lvl 25 / 50 / 75 / 100 / 150 / 200 / 250 / 300.
- **Prestige P1–P10:** 25M → 18B money; **Ascension** endlos ×1.6 ab 28,8 Mrd.
- **Rebirth R1–R5:** große Sammel-Gates; **Ascension-Meilensteine** P25/50/100/250/500/1000.

> **Server-State (nur im Spiel):** Minen-Koordinaten/Region/Teleport/Reset-Wert je `mine_a…mine_j` via
> `/mines create` + `/mines panel`; Block-Verteilung gemäß Tabelle oben eintragen.
> **Verifizieren:** Dass die definierten P0–P10 bei aktiviertem `unlimited_prestiges` erhalten bleiben und
> die Formel erst *jenseits* P10 greift, im Live-Betrieb einmal prüfen.

---

## Plugin-Stack (Auszug)

Vollständiger, für dieses Setup optimierter Plugin-Stack (inkl. Recycling- und Entfernen-Liste):
[PLUGINS.md](PLUGINS.md). Shortlist inkl. 26.2-Verfügbarkeit in
[../NEW_SERVERS.md → Abschnitt 3.2](../NEW_SERVERS.md#32-mining-spezifisch).

- **Mining-/Zonen-Kern** — zwei Wege: **(A, empfohlen)** ein gebündelter Prison-/Mining-Core (**X-Prison**,
  alt. EdPrison/VortexPrisonCore) deckt Spitzhacke, Mehrblock-Abbau, Zonen-Auto-Reset, Auto-Sell & Prestige in
  einem Plugin ab; **(B)** modular via **EcoItems** (Spitzhacke) + **EcoEnchants** (Area-Abbau) + **AxMines**
  (Zonen/Auto-Regeneration). 26.2-Build zuerst verifizieren (Blocker).
- **Region-/Schutz-Plugin** (WorldGuard) — bereits im Netzwerk (Zonengrenzen, kein Griefing)
- **WorldEdit/FAWE** (Zonen bauen/zurücksetzen) — bereits im Netzwerk
- **Economy** (Vault) + **Shop/Auto-Sell** (**EconomyShopGUI**; bei Weg A Core-intern)
- **Cosmetics/Battle-Pass** (**BattlePass** oder Skript-Pass; **PlayerParticles** für Trails recyceln)

---

## Offene Punkte

- **Entschieden:** Mining-Kern = **X-Prison** (Weg A). Kernprogression konfiguriert (siehe
  [Umgesetzte X-Prison-Progression](#umgesetzte-x-prison-progression-fundament)); 26.2-Build im Deploy bestätigen.
- **Zonen-Design festgelegt:** 10 Zonen `mine_a…mine_j` mit eskalierenden Block-Sets, Rankup-Kosten und
  monoton steigenden AutoSell-Preisen (Details siehe Ladder-Tabelle). Minen-Koordinaten im Spiel setzen.
- Abbau-Muster pro Spitzhacken-Stufe (1×1 → 3×3 → …) und Auto-Regenerations-Tempo festlegen.
- Battle-Pass-Umfang und Cosmetic-Währung (siehe offene Economy-Frage in
  [../NEW_SERVERS.md → Abschnitt 7](../NEW_SERVERS.md#7-verbleibende-offene-fragen)).

---

## Ausbau ab Phase 2

### Rank-/Prestige-/Mine-Progression

- **Klare Mine-Leiter:** Jede Zone soll einen klaren Zweck haben: Einstieg, Midgame, Endgame und Prestige-Vorbereitung.
- **Rankups zwischen Zonen:** Nach mehreren Spitzhacken-Upgrades folgt jeweils ein Rankup, das neue Minen, Shops oder
  Utility-Funktionen freischaltet.
- **Prestige als Loop-Neustart:** Prestige setzt Rang- und Minen-Fortschritt kontrolliert zurück, gibt aber permanente
  Boni wie Sell-Multiplikatoren, kosmetische Titel oder kleine Komfort-Freischaltungen.
- **Endgame-Ziele:** Nach der letzten Mine bleiben Prestige-Stufen, seltene Enchants, saisonale Ziele und Ranglisten als
  langfristige Motivation aktiv.

### Black Market

- **Rotierender Händler:** Der Black Market wechselt regelmäßig sein Angebot und schafft Gründe, auch nach dem täglichen
  Progress wiederzukommen.
- **Ressourcen-Sink:** Angebote sollten gezielt Geld, Tokens oder seltene Drops aus dem Umlauf ziehen, um Inflation zu
  bremsen.
- **Gezielte Belohnungen:** Fokus auf Booster, kosmetische Spitzhacken-Skins, temporäre Mine-Buffs, seltene Enchant-Items
  und saisonale Sammelobjekte statt roher Pay-to-Win-Power.
- **Progressions-Kopplung:** Einzelne Angebote können an Rang, Prestige oder Mine-Tier gebunden werden, damit der Black
  Market die Progression ergänzt statt überspringt.

### Enchant-Balancing

- **Frühe Enchants schlicht halten:** Start-Minen profitieren vor allem von Tempo-, Fortune- oder kleinen Auto-Sell-
  Effekten; große Flächen-Enchants kommen erst später.
- **Stufenweise Freischaltung:** Explosive, Layer-, Nuke- oder ähnliche Mehrblock-Effekte werden an Mine-Tiers,
  Spitzhacken-Level oder Prestige geknüpft.
- **Kostenkurve statt Power-Sprünge:** Starke Enchants sollen teuer skalieren und im Endgame ein Investment-Ziel sein,
  ohne Midgame-Minen sofort zu entwerten.
- **Bedrock-/Performance-Fokus:** Enchant-Kombinationen müssen so begrenzt sein, dass Mine-Resets, Partikel und
  Block-Updates auch bei vielen gleichzeitigen Spielern stabil bleiben.

### Weitere Battlepass-/Quest-Saisons

- **Saison-Modell statt Einmal-Content:** Nach dem Launch sollten neue Battlepass- und Quest-Saisons in festen Zyklen
  nachgeliefert werden.
- **Aufgabenmix:** Saisonziele kombinieren Blöcke abbauen, Mine-Freischaltungen, Prestige-Schritte, Daily-Streaks und
  Community-Events.
- **Belohnungsstruktur:** Freie und Premium-Schienen belohnen vor allem Cosmetics, Titel, Partikel, Booster und Black-
  Market-Währung; Gameplay-Power bleibt kontrolliert.
- **Wiederverwendung mit Variation:** Bestehende Minen, Events und Enchants werden saisonal neu gerahmt, damit mit
  überschaubarem Pflegeaufwand regelmäßig frischer Content entsteht.

---

**Verwandt:** [PLUGINS.md](PLUGINS.md) · [../NEW_SERVERS.md](../NEW_SERVERS.md) · [../PLANNING.md](../PLANNING.md) ·
[../skyblock/README.md](../skyblock/README.md)

**Letzte Aktualisierung:** 2026-09-10
