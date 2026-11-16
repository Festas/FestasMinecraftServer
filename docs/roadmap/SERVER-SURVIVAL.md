# Survival — Verbesserungs-Roadmap

**Zweck:** Der aktive Hauptmodus (Lands-Städte + Freebuild/PlotSquared) — dieses Doc bündelt Aufräum-, Balancing- und Politur-Aufgaben.

**Status:** 🟢 **aktiv** (Hauptmodus) · ⚠️ **Skript-Ordner = 🔴 Baustelle** · **Velocity-Server:** `survival` · **Port:** `25568`

> **Reichhaltigster Server des Netzes** — und genau deshalb mit dem größten Aufräumbedarf in der Skript-Schicht und einem **offenen Tycoon-Status-Widerspruch** (siehe [Offene Entscheidungen](#offene-entscheidungen)).

---

## So nutzt du dieses Doc

Jede Zeile der [Haupt-Tabelle](#baustellen--verbesserungen) ist eine **abhakbare Aufgabe** (☐) mit **ID**, **Prio** (P0 = Hygiene/Blocker, P1 = Kern-Feature, P2 = Politur), **zuständigem Agent** und **Validierung**. Beauftrage den genannten Agent (ein Plugin → direkt; mehrere → [`../plugins/CROSS-PLUGIN.md §4`](../plugins/CROSS-PLUGIN.md#4-feature-playbooks)), ändere **minimal & chirurgisch** und validiere via [Checkliste §5](../plugins/CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste). Einstieg & Guardrails: [Roadmap-Hub `README.md`](README.md). **Wichtig:** Skript-Dateien **nicht selbst löschen** — die Aufräum-Aufgaben beschreiben *was* zu tun ist; ausführen tut es der `skript`-Agent nach Owner-Entscheid.

---

## IST-Stand (kurz)

- **Voller Plugin-Stack:** Autorank, BlueMap, CMI (+CMILib), ChestShop, Chunky, DeluxeMenus, GlobalMarketPlus, HeadDatabase, Jobs, Lands, LibsDisguises, Multiverse-Core/-Inventories, NextGens, Oraxen, PlaceholderAPI, Plan, PlotSquared, Rankup, ShopGUIPlus, Skript, WorldGuard, FastAsyncWorldEdit, AxiomPaper, VoidGen, RoseGarden, NBTAPI.
- **Economy:** CMI-Provider, DB **`S1_CMI`** (pro Server getrennt). Autorank läuft produktiv (`data/` + Backup-Rotation).
- **DeluxeMenus:** 11 Menüs (u. a. `tycoon_main.yml`, `tycoon_warps.yml`, `tycoon_warps_survival.yml`, `generators.yml`, `progress_menu.yml`, `cosmetics.yml`, `warps.yml`, `warps_survival.yml`).
- **Skript — Baustelle:**
  - **Aktiv:** `tycoon_collector.sk`, `tycoon_logic.sk`, `tycoon_setup.sk`, `tycoon_tutorial.sk` — **parallel zu deaktivierten** `-tycoon_collector.sk`, `-tycoon_item.sk`, `-tycoon_logic.sk`, `-tycoon_setup.sk` (Doppelungen!).
  - **Endungslose Dev-Dateien** (laden nicht als Skript): `nitwit`, `setup`, `gen_restrict`, `no_block_abbau`.
  - **`archive/`:** `-ess_to_cmi.sk`, `sw_fix`, `xp`.
  - **Weitere aktive Skripts:** `dynamic_market.sk`, `economy_monitor.sk`, `prestige.sk`, `rankup.sk`, `ranks_setup.sk`, `shopguiplus.sk`, `boss_events.sk`, `weekly_events.sk`, `daily_rewards.sk`, `gamble.sk`, `achievements.sk`, `navigator.sk`, `help.sk`.
- **`server.properties`:** `motd=✦ Festas' Server ✦ - Survival + Tycoon`, `gamemode=survival`, `difficulty=normal`.
- **NextGens:** installiert, laut [`../SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) jedoch **deaktiviert** (hängt am Tycoon-Status).
- **CMI-Neustart:** täglich **03:55** (Europe/Berlin — frühestes Fenster).

## Stärken

- Mit Abstand **tiefster Content-Stack** des Netzes (Jobs, Shops, Lands, PlotSquared, Generatoren, Custom-Skript-Features wie Prestige, Casino, Boss-/Weekly-Events, dynamische Börse).
- **Oraxen voll**, **GlobalMarketPlus voll**, **Autorank produktiv** (inkl. Backups).
- 11 durchdachte DeluxeMenus für Warps, Fortschritt, Kosmetik und Generatoren.

## Baustellen & Verbesserungen

| ID | Prio | Aufgabe | Zuständiger Agent | Betroffene Pfade | Validierung |
|----|:----:|---------|-------------------|------------------|-------------|
| ☐ SUR-01 | **P0** | Skript-Ordner inventarisieren: aktive vs. `-`deaktivierte Dateien, endungslose Dev-Dateien und `archive/` auflisten und zuordnen — **Grundlage** vor jeder Löschung. | `skript` | `survival/plugins/Skript/scripts/` | Vollständige Liste (aktiv/deaktiviert/Dev/Archiv) dokumentiert |
| ☐ SUR-02 | **P0** | **Doppelte Tycoon-Skripte auflösen:** aktive `tycoon_collector/logic/setup/tutorial.sk` stehen parallel zu `-tycoon_*.sk` → je Datei **genau eine** Variante behalten (**erst nach Tycoon-Entscheid**, siehe Offene Entscheidungen). Nicht raten. | `skript` | `survival/plugins/Skript/scripts/(-)tycoon_*.sk` | `/sk reload` ohne Fehler; **keine doppelten Events/Trigger** |
| ☐ SUR-03 | **P1** | Endungslose Dev-Dateien klären: `nitwit`, `setup`, `gen_restrict`, `no_block_abbau` → zu `.sk` machen (wenn gewollt), mit `-` deaktivieren oder nach `archive/`. | `skript` | `survival/plugins/Skript/scripts/{nitwit,setup,gen_restrict,no_block_abbau}` | Nur gewollte `.sk` laden; `/sk reload` sauber |
| ☐ SUR-04 | **P1** | `archive/` bereinigen/markieren: `-ess_to_cmi.sk`, `sw_fix`, `xp` eindeutig als Archiv kennzeichnen oder entfernen. | `skript` | `survival/plugins/Skript/scripts/archive/` | Lädt nicht; Zweck je Datei dokumentiert |
| ☐ SUR-05 | **P1** | Economy-/Progression-Balancing-Review: Jobs-Auszahlungen ⇄ ShopGUIPlus-Preise ⇄ Rankup-Kosten ⇄ NextGens-Erträge konsistent; **Inflationscheck**; alles gegen **`S1_CMI`**. | `jobs` + `survival-shops` + `progression` + `nextgens` | `survival/plugins/{Jobs,ShopGUIPlus,Rankup,NextGens}/` | Preis-/Ertrags-Matrix stimmig; Testkauf bucht `S1_CMI`; keine Geld-Explosion |
| ☐ SUR-06 | **P1** | Rang-Konsistenz: LuckPerms-Gruppennamen in **Rankup ⇄ Autorank ⇄ CMI-Ränge** exakt identisch; **nur eine** Promotion-Quelle zählt Zeit (Doppel-Promotion vermeiden). | `progression` (+ `luckperms`) | `survival/plugins/{Rankup,Autorank}/`, LuckPerms-Gruppen | `/lp group list`; genau eine Promotion-Quelle; TAB-Prefix korrekt |
| ☐ SUR-07 | **P1** | NextGens-Status **dokumentieren & angleichen** (installiert, laut Overview deaktiviert) — abhängig vom Tycoon-Entscheid; Config und Doku in Einklang bringen. | `nextgens` | `survival/plugins/NextGens/{config.yml,generators.yml}` | `/nextgens reload`; Status eindeutig (an **oder** aus, nicht widersprüchlich) |
| ☐ SUR-08 | **P2** | Lands/PlotSquared-Politur: Claim-Limits/Level je Rang, `freebuild`-Plotwelt-Flags, Merge-/Reset-Verhalten. | `land-claims` | `survival/plugins/{Lands,PlotSquared}/` | `/lands`, `/plot`: Limits & Flags greifen |
| ☐ SUR-09 | **P2** | GlobalMarketPlus-Gebühren/Limits/Laufzeiten balancieren (Spieler-Markt, Senke gegen Inflation, `S1_CMI`). | `globalmarketplus` | `survival/plugins/GlobalMarketPlus/config.yml` | `/market`; Gebühr/Steuer wird korrekt von `S1_CMI` abgebucht |
| ☐ SUR-10 | **P2** | BlueMap-Tuning: Render-Grenzen, Marker/Sets, Performance; nur `survival`-Welten. | `bluemap` | `survival/plugins/BlueMap/` | Karte rendert; Web erreichbar; Marker stimmen |
| ☐ SUR-11 | **P2** | Oraxen-Items/Resourcepack konsistent halten; nach Pack-Änderung **SHA-1** in ForceResourcepacks (Proxy) bumpen. | `oraxen` (+ `proxy-network`) | `survival/plugins/Oraxen/` | `/oraxen reload`; Client-Icons korrekt; Hash aktualisiert |

## Offene Entscheidungen

- **🔴 Tycoon-Status (blockiert SUR-02 & SUR-07):** Die Doku widerspricht sich und passt nicht zum Dateistand:
  - **„deaktiviert":** [`docs/survival/README.md`](../survival/README.md) („Tycoon existiert aktuell nicht mehr … kommt auf eigenem Server", `tycoon_*.sk`-Tabelle auskommentiert) und [`../SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) („NextGens-Tycoon … **deaktiviert**, die `tycoon_*`-Skripte sind abgeschaltet").
  - **„aktiv/Kern":** [`docs/survival/PLUGINS.md`](../survival/PLUGINS.md) („Survival-/**Tycoon**-Server", NextGens = „Kern des Tycoon"), [`docs/survival/PROGRESSION.md`](../survival/PROGRESSION.md), [`docs/survival/PLOTS.md`](../survival/PLOTS.md), [`docs/survival/TYCOON.md`](../survival/TYCOON.md) (Tycoon = „Herzstück").
  - **Dateistand:** `tycoon_*.sk` sind **aktiv** (ohne `-`) **und** zusätzlich als `-tycoon_*.sk` **deaktiviert** vorhanden; MOTD wirbt „Survival + **Tycoon**"; `tycoon_main/warps`-Menüs existieren.
  - **Owner-Entscheid nötig:** Tycoon **live auf Survival**, **ganz abgeschaltet**, oder **eigener Server**? Erst danach Skripte entdoppeln (SUR-02), NextGens-Status fixieren (SUR-07) und MOTD/Doku angleichen. **Keine Annahme treffen.**
- **MOTD-Text:** „Survival + Tycoon" beibehalten oder (bei Tycoon-Abschaltung) anpassen — hängt am Tycoon-Entscheid.

## Doku-Pflege

- **Tycoon-Widerspruch auflösen** (nach Owner-Entscheid): [`docs/survival/README.md`](../survival/README.md), [`PLUGINS.md`](../survival/PLUGINS.md), [`PROGRESSION.md`](../survival/PROGRESSION.md), [`PLOTS.md`](../survival/PLOTS.md), [`TYCOON.md`](../survival/TYCOON.md) **und** [`../SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) konsistent machen.
- **NextGens-Status** in Doku und Config spiegeln (SUR-07).
- Skript-Inventar (SUR-01) als kurze Referenz in `docs/survival/` festhalten, damit die Baustelle nachvollziehbar bleibt.

---

**Navigation:** [Roadmap-Hub](README.md) · [Gesamtstruktur](GESAMTSTRUKTUR.md) · [KI-Agents](KI-AGENTS.md) · [Proxy](PROXY.md)
**Plugin-Wissensbasis:** [Index](../plugins/README.md) · [CROSS-PLUGIN](../plugins/CROSS-PLUGIN.md) · [Skript](../plugins/Skript.md) · [Jobs](../plugins/Jobs.md) · [ShopGUIPlus](../plugins/ShopGUIPlus.md) · [NextGens](../plugins/NextGens.md) · [Rankup](../plugins/Rankup.md) · [Autorank](../plugins/Autorank.md) · [Lands](../plugins/Lands.md) · [PlotSquared](../plugins/PlotSquared.md) · [GlobalMarketPlus](../plugins/GlobalMarketPlus.md) · [BlueMap](../plugins/BlueMap.md)
**Server-Doku:** [`README`](../survival/README.md) · [`TYCOON`](../survival/TYCOON.md) · [`PROGRESSION`](../survival/PROGRESSION.md) · [`PLOTS`](../survival/PLOTS.md) · [`PLUGINS`](../survival/PLUGINS.md)

**Letzte Aktualisierung:** 2026-11-02
