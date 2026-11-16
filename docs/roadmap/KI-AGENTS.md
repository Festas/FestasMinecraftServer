# KI-Agents nutzen & verbessern

Leitfaden, **wie man die Custom-Agents gut einsetzt** – für den Owner und für den
**Orchestrator-Agent**. Das Netzwerk hat **22 Ein-/Bündel-Plugin-Agents** + **1 Orchestrator**
(insgesamt 23 Agent-Dateien unter [`../../.github/agents/`](../../.github/agents/README.md)).

> **Grundprinzip:** **Ein Plugin → direkt den Plugin-Agent.** **Mehrere Plugins → Orchestrator**
> bzw. ein Playbook aus [`../plugins/CROSS-PLUGIN.md §4`](../plugins/CROSS-PLUGIN.md#4-feature-playbooks).
> Jeder Agent liest **zuerst** sein `docs/plugins/<Plugin>.md`, bevor er Config ändert.

---

## 1. Agent-Nutzungstabelle

Quelle: [`../../.github/agents/README.md`](../../.github/agents/README.md) +
[`../plugins/CROSS-PLUGIN.md §2/§3`](../plugins/CROSS-PLUGIN.md#2-agent--plugin-zuständigkeit).

### Proxy-zentral

| Agent | Wann einsetzen (Trigger) | Bearbeitet (Plugins) | Server | Typische Aufgaben | Gotcha |
|-------|--------------------------|----------------------|--------|-------------------|--------|
| `tab` | Tablist/Scoreboard/Nametag/BossBar | TAB (+ VelocityScoreboardAPI) | proxy | Header/Footer, Gruppen-Sortierung, Platzhalter | Nur Proxy; CMI-Tablist nur lobby+skyblock (keine Doppel-Tablist) |
| `libertybans` | Bans/Mutes/Kicks/Warns, Scope | LibertyBans | proxy | Strafarten, Nachrichten, Scope | Proxy-zentral; HSQLDB aktiv, MySQL dormant |
| `proxy-network` | MOTD/Icon, Skins, Pack-Erzwingung | MiniMOTD, SkinsRestorer, ForceResourcepacks | proxy | MOTD-Branding, Pack-SHA-1 | ForceResourcepacks-Hash **folgt Oraxen-Pack** |

### Alle Backends (shared)

| Agent | Wann einsetzen (Trigger) | Bearbeitet (Plugins) | Server | Typische Aufgaben | Gotcha |
|-------|--------------------------|----------------------|--------|-------------------|--------|
| `cmi` | Economy, Homes, Warps, Kits, Chat, Neustarts, Zeit-Ränge | CMI (+CMILib) | lobby/survival/skyblock/rpg | `money give`, Ranks.yml, Neustart-Zeiten | Economy **pro Server** (`S1/S3/S5`); Lobby ohne Economy |
| `luckperms` | Rechte, Gruppen, Tracks, Meta, Kontexte | LuckPerms | lobby/survival/skyblock/rpg | Gruppe/Prefix/Weight, Track | Geteilte `s4_perms`; Gruppennamen **netzwerkweit identisch** |
| `placeholderapi` | fehlende/leere Platzhalter, Expansions | PlaceholderAPI | lobby/survival/skyblock/rpg | Expansion installieren, `%…%` prüfen | Expansion muss **auf dem Backend** existieren |
| `plan` | Analytics, Web-Dashboard, Plan-DB | Plan | proxy/lobby/survival/rpg + skyblock(SQLite) | Server-Identität, Webserver | Geteilte `s4_plan`; **skyblock = SQLite** |
| `skript` | Custom-Gameplay, Broadcasts, Navigator, Daily-Rewards | Skript | lobby/survival/skyblock/rpg | `help.sk`-Rotation, Trigger, Navigator | Führender `-` = deaktiviert; Navigation nicht-destruktiv |
| `deluxemenus` | klickbare GUI-Menüs, Server-Selector | DeluxeMenus | lobby/survival/skyblock/rpg | Menü + `config.yml`-Registrierung, Requirements | Menü **in `config.yml` registrieren**; `/dm reload` |
| `oraxen` | Custom-Items, Glyphs, Fonts, HUD, Rezepte, Pack | Oraxen | lobby/survival/skyblock/rpg | Item/Textur in `pack/`, Reload | Nach Pack-Änderung **SHA-1** in ForceResourcepacks bumpen |

### Welten / Schutz / Karte

| Agent | Wann einsetzen (Trigger) | Bearbeitet (Plugins) | Server | Typische Aufgaben | Gotcha |
|-------|--------------------------|----------------------|--------|-------------------|--------|
| `worldguard` | Regionen, Flags, Spawn-/Bau-/PvP-Schutz | WorldGuard (+WorldEdit) | lobby/survival/rpg | Regionen/Flags | **Nicht skyblock** (dort SSB2-Rollen) |
| `multiverse` | Welten laden/erstellen, Inventar-Trennung | Multiverse-Core/-Inventories | survival/skyblock/rpg | `/mv import`, `groups.yml` | SlimeWorlds **nicht** in geteilte Inventar-Gruppe |
| `bluemap` | Live-Karte, Webserver/Port, Render | BlueMap | survival/rpg | Welten-Maps, Storage/Port | Nur survival/rpg |

### Survival-Economy & Progression

| Agent | Wann einsetzen (Trigger) | Bearbeitet (Plugins) | Server | Typische Aufgaben | Gotcha |
|-------|--------------------------|----------------------|--------|-------------------|--------|
| `progression` | Kauf-/Zeit-Rang, Prestige, Gruppen-Konsistenz | Rankup, Autorank, CMI-Ränge | survival | Rankup-Kette, Autorank-Pfade | **Keine Doppelvergabe Zeit** (Autorank ⇄ CMI `AutoRankUp`) |
| `survival-shops` | Shop-Preise, Kategorien, Spieler-Läden | ShopGUIPlus, ChestShop | survival | Preise, Kategorien, `pricemodifiers` | Preis-**Konsistenz** mit NextGens/Jobs/Rankup |
| `jobs` | Job-Einkommen, Payout/EXP, Balancing | Jobs Reborn | survival | `jobConfig.yml` Payouts | Nur survival |
| `nextgens` | Generatoren, Drops, Upgrades, Passiv-Einkommen | NextGens | survival | Typen/Drops/Preise | Nur survival (Tycoon-Kontext, derzeit deaktiviert) |
| `land-claims` | Claim-Limits/Level, Plot-Welten | Lands, PlotSquared | survival | Claim-Config, Plot-Welten | Nur survival |
| `globalmarketplus` | Spieler-Markt/Auktion, Gebühren, Limits | GlobalMarketPlus | survival/skyblock/rpg | Gebühren/Laufzeiten | **SQLite pro Server** (kein serverübergreifender Markt) |

### Server-spezifische Kerne

| Agent | Wann einsetzen (Trigger) | Bearbeitet (Plugins) | Server | Typische Aufgaben | Gotcha |
|-------|--------------------------|----------------------|--------|-------------------|--------|
| `superiorskyblock2` | Inseln, Level, Missionen, Rollen, Menüs, Slime-Welten | SuperiorSkyblock2, SlimeWorldManager | skyblock | Missionen, `block-values`, Menüs | **Kein WorldGuard** auf skyblock |
| `deluxebazaar` | Instant-Shop (Items, Kategorien, Preise, Menüs) | DeluxeBazaar | skyblock | `items.yml`/`categories.yml` | Nur skyblock; Economy `S5_CMI` |
| `prison` | Mines, Ränge/Prestige/Rebirth, Enchants, Currencies, AutoSell/AutoMiner, Addons | X-Prison, XPrivateMines, XPrisonArmors, XRobots | rpg (=mining) | `ranks/prestiges/rebirths.yml`, Enchants | **Eigene** Progression (nicht Rankup/Autorank!); `rpg`=„mining" |

### Orchestrierung

| Agent | Wann einsetzen (Trigger) | Bearbeitet | Server | Typische Aufgaben | Gotcha |
|-------|--------------------------|------------|--------|-------------------|--------|
| **Orchestrator** ([`orchestrator.md`](../../.github/agents/orchestrator.md)) | Feature berührt **mehrere** Plugins (Rang, Item, Generator, Menü, Warp, Welt, Server, Währung, Broadcast) | **nichts selbst** – delegiert via `agent`-Tool | alle | zerlegt nach CROSS-PLUGIN-Playbook, beauftragt Ein-Plugin-Agents **nacheinander** | Ändert **keine** Config; sequenziert & prüft nur |

> **Ohne eigenen Agent:** HeadDatabase (über `deluxemenus`/`survival-shops`), LibsDisguises (über
> `progression`/`prison`/`luckperms`) sowie Bibliotheken (Vault, ProtocolLib, FAWE, …) →
> [`../plugins/libraries.md`](../plugins/libraries.md).

---

## 2. Entscheidungshilfe

```
Aufgabe
  ├─ betrifft genau 1 Plugin? ──────────► direkt den passenden Plugin-Agent beauftragen
  └─ betrifft mehrere Plugins? ─────────► Orchestrator  ODER  CROSS-PLUGIN §4-Playbook
                                          (Fundament zuerst, Anzeige zuletzt)
```

**Reihenfolge-Faustregel:** erst **Rechte/Welt/Item** (Fundament), zuletzt **Menü/TAB** (Anzeige).

### Kurz-Rezepte (Agent-Reihenfolge)

Details je Playbook in [`../plugins/CROSS-PLUGIN.md §4`](../plugins/CROSS-PLUGIN.md#4-feature-playbooks).

| Feature | Agent-Reihenfolge (verkürzt) | Playbook |
|---------|------------------------------|:--------:|
| **Kauf-Rang / VIP** (survival) | `luckperms` → `cmi` → `progression` (Rankup) → `survival-shops` → `placeholderapi` → `tab` → `deluxemenus` | §4.1 |
| **Zeit-Rang** (survival) | `progression` (Autorank/CMI) → `luckperms` → `tab` | §4.2 |
| **Prison-Rang / Prestige / Rebirth** (mining) | `prison` → ggf. `luckperms` → `placeholderapi` → `tab` | §4.3 |
| **Verkaufbares Item / Generator** (survival) | `oraxen` → `nextgens` → `survival-shops` → `jobs` → `deluxemenus` → `skript` → `cmi` | §4.4 |
| **GUI-Menü / Navigator** | `deluxemenus` → `placeholderapi` → `oraxen`/HeadDatabase → `luckperms` → `skript` (Lobby: Velocity-Namen) | §4.6 |
| **Custom-Item + Pack** | `oraxen` → `proxy-network` (SHA-1) → Konsumenten (`deluxemenus`/`survival-shops`/`skript`) | §4.7 |
| **Neuer Server / Backend** | `proxy` (velocity.toml) → `proxy-network` → `plan` → `tab` → `luckperms` → `cmi` → Deploy/Secrets | §4.10 |

> **Dediziertes Orchestrator-Rezept:** [`../plugins/ORCHESTRATOR.md`](../plugins/ORCHESTRATOR.md)
> (Bauanleitung + fertige Agent-Vorlage).

---

## 3. Best-Practice-Prompts

Ein guter Auftrag an einen Agent nennt **immer**:

1. **Betroffene Server** – explizit (z. B. „auf survival **und** skyblock", „nur rpg/mining").
2. **Exakte Pfade** – nach Möglichkeit aus dem `docs/plugins/<Plugin>.md` (z. B.
   `survival/plugins/Rankup/rankups.yml`).
3. **Geteilte Namen/Werte** – Gruppenname, Item-ID, Preis, Weltname, Servername **wörtlich**, damit
   der nächste Agent dasselbe durchreicht.
4. **Validierung** – welcher Reload/Check belegt den Erfolg (`/dm reload`, `/papi parse me %…%`,
   `/sk reload <datei>`, Checkliste §5).

**Beispiel (gut):**
> „Lege auf **survival** die LuckPerms-Gruppe `vip` an (Prefix `&a[VIP] `, Weight 50) und verdrahte
> sie in `survival/plugins/Rankup/rankups.yml` als Ziel der Stufe nach `spieler`. Preis `50000`.
> Gruppenname überall **exakt** `vip`. Validierung: `/lp group vip info`, `/rankup`-Testkauf (`S1_CMI`),
> TAB zeigt Prefix. Platzhalter `__…__` und YAML-2-Space erhalten."

**Beispiel (schlecht):** „Mach einen VIP-Rang." (kein Server, kein Pfad, kein Name, keine Validierung)

> **Für den Orchestrator:** pro Plugin **einen** Teilauftrag, **nacheinander**, mit denselben
> geteilten Werten; am Ende die
> [Cross-Plugin-Validierungs-Checkliste §5](../plugins/CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste).

---

## Verbesserungs-Backlog (Agent-System)

Aus dem Audit. **Wichtig:** Diese Punkte werden **in `.github/` separat umgesetzt** (von einem
Folge-Agent für Instructions/Agents) – hier nur als priorisierte Checkliste dokumentiert. Betrifft
`.github/agents/*.agent.md`, `.github/instructions/plugins.instructions.md`,
`.github/copilot-instructions.md`.

| ID | Prio | Aufgabe | Betroffene Dateien | Vorgehen/Validierung |
|----|:----:|---------|--------------------|----------------------|
| A-1 | **P1** | **Guardrails zentralisieren** statt 4-fach duplizieren (sie stehen redundant in copilot-instructions, agents/README, plugins.instructions, CROSS-PLUGIN). Eine kanonische Quelle, Rest **referenziert**. | `.github/copilot-instructions.md`, `.github/agents/README.md`, `.github/instructions/plugins.instructions.md`, `../plugins/CROSS-PLUGIN.md` | Kanonische Guardrail-Liste festlegen (Vorschlag: `plugins/README.md`/`copilot-instructions.md`); andere Stellen auf Kurzfassung + Link reduzieren. **Validierung:** keine widersprüchlichen Formulierungen; ein Ort ist „die Wahrheit". |
| A-2 | **P1** | **Konkreten YAML-Validierungsbefehl ergänzen** (bislang nur „gültiges YAML prüfen", kein Befehl). | Instructions/Agents + [`GESAMTSTRUKTUR.md §E`](GESAMTSTRUKTUR.md#e-neue-docs-anlegen) (`TESTING.md`) | Befehl dokumentieren, z. B. `python -c "import sys,yaml;yaml.safe_load(open(sys.argv[1]))" <datei>` bzw. `yamllint`. **Validierung:** Befehl läuft gegen eine geänderte YAML ohne Fehler. |
| A-3 | **P1** | **Rang-Primärzuständigkeit schärfen** zwischen `luckperms`/`cmi`/`progression` (wer „besitzt" Gruppen vs. Darstellung vs. Promotion). | `.github/agents/{luckperms,cmi,progression}.agent.md` | Klarstellen: LuckPerms = Quelle der Gruppen/Rechte; CMI = Darstellung/Economy; progression = Promotion-Logik. **Validierung:** keine überlappenden „primär"-Ansprüche; deckt sich mit CROSS-PLUGIN §4.1/§4.2. |
| A-4 | **P1** | **`applyTo` erweitern** um `proxy/velocity.toml` (aktuell nur `{proxy,lobby,survival,skyblock,rpg}/plugins/**` → velocity.toml fällt raus). | `.github/instructions/plugins.instructions.md` | `applyTo`-Glob um `proxy/velocity.toml` ergänzen (TOML, nicht YAML – Hinweis aufnehmen). **Validierung:** Bearbeiten von `velocity.toml` triggert die Instruction. |
| A-5 | P2 | **`skript.agent.md` ans Schema angleichen** (weicht leicht vom einheitlichen Agent-Schema ab). | `.github/agents/skript.agent.md` | Mit einem Referenz-Agent (z. B. `cmi.agent.md`) abgleichen: Abschnittsreihenfolge/Headings vereinheitlichen; Inhalt erhalten. **Validierung:** gleiche Struktur wie die übrigen Agents. |
| A-6 | P2 | **Orchestrator-Datei-Namenskonvention prüfen:** `orchestrator.md` folgt nicht dem `*.agent.md`-Muster. | `.github/agents/orchestrator.md` | Entscheiden: in `orchestrator.agent.md` umbenennen **oder** Ausnahme bewusst dokumentieren (Auto-Discovery prüfen!). **Validierung:** Agent wird weiterhin erkannt; Verweise (CROSS-PLUGIN/README) stimmen. |

---

**Siehe auch:** [`README.md`](README.md) (Hub) · [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md) ·
[`PROXY.md`](PROXY.md) · [`../plugins/CROSS-PLUGIN.md`](../plugins/CROSS-PLUGIN.md) ·
[`../plugins/ORCHESTRATOR.md`](../plugins/ORCHESTRATOR.md) ·
[`../../.github/agents/README.md`](../../.github/agents/README.md)

**Letzte Aktualisierung:** 2026-11-02
