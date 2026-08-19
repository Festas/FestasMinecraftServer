# Mining-Server — Konzept (geplant)

> **🟡 Geplant (26.2).** Dieser Server ist einer der **zwei neuen Server** der Netzwerk-Neuausrichtung
> (neben dem überarbeiteten **Skyblock**). Er befindet sich in der **Konzept-/Aufbauphase**.
> Grundlage: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Casual-Server rund um **Abbau-Zonen**: Spieler bauen mit einer **besonderen Spitzhacke** Blöcke ab,
verkaufen sie und schalten damit **stärkere Spitzhacken** und **neue Zonen** frei. Der Kern-Loop ist bewusst
einfach, schnell verständlich und Bedrock-freundlich.

---

## Rolle im Netzwerk

| Eigenschaft | Wert |
|-------------|------|
| **Slot** | Casual |
| **Server-Name (Velocity/Proxy)** | `prison` *(recycelt — der alte Prison-Slot wird wiederverwendet)* |
| **Version** | Paper 26.2 (geplant) |
| **Ordner** | [`prison/`](../../prison/) *(recycelt aus dem alten Prison-Server)* |
| **Economy** | Server-isoliert; optionale netzwerkweite Cosmetic-Währung |
| **Datenbank** | Eigenes MariaDB-Schema (isoliert) |
| **Bedrock-Support** | Ja (Geyser/Floodgate) — GUIs müssen Bedrock-tauglich sein |
| **Sync (HuskSync)** | Nur Cosmetics/Ränge, **keine** Gameplay-Inventare |

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
| **Ränge/Prestige** | Langzeit-Progression nach den Zonen | ⬜ Phase 2 |
| **Cosmetics/Battle-Pass** | Retention, Belohnungen | ⬜ Phase 2 |
| **Verzauberungen/Boosts der Spitzhacke** | Zusätzliche Effekte (Auto-Sell, Multiplier) | ⬜ Phase 2 |
| **Black Market** | Rotierende Late-Game-Angebote als Ressourcen-Sink | ⬜ Phase 3 |

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

- Konkretes 26.2-taugliches Plugin für **Spitzhacken-Upgrades + Mehrblock-Abbau** festlegen: Weg A
  (**X-Prison**-Core) oder Weg B (**EcoItems + EcoEnchants + AxMines**) — 26.2-Build bestätigen (Blocker).
- Zonen-Design: Anzahl Zonen zum Launch, Block-Sets, Freischalt-Kosten, Balancing der Verkaufspreise.
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

**Letzte Aktualisierung:** 2026-08-19
