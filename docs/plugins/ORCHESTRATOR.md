# Orchestrator-/Main-Agent (Feature-Dirigent)

**Ein übergeordneter Agent, der Multi-Plugin-Features erkennt, zerlegt und die vorhandenen
Ein-Plugin-/Bündel-Agents nachrangig beauftragt · Bauanleitung + fertige Vorlage**

Die Custom Agents in [`.github/agents/`](../../.github/agents/README.md) sind fast alle **auf ein
Plugin bzw. Subsystem** ausgerichtet (`cmi`, `luckperms`, `oraxen`, …). Viele **Features** berühren
aber [mehrere Plugins gleichzeitig](CROSS-PLUGIN.md) – ein neuer Rang, ein verkaufbares Item oder ein
Menü ist selten „ein Plugin". Dieses Dokument beschreibt, wie ein **Orchestrator-Agent** (auch „Main-
Agent") solche Ketten **dirigiert**: Er ändert **selbst keine Config**, sondern wählt das passende
[Feature-Playbook](CROSS-PLUGIN.md#4-feature-playbooks), bestimmt die beteiligten Agents und
**beauftragt sie nacheinander**.

> **Verhältnis zu den anderen Docs:** [`CROSS-PLUGIN.md`](CROSS-PLUGIN.md) ist das **Wissen** (welche
> Kette, welche Reihenfolge, was prüfen). Dieses Dokument ist die **Ausführungsschicht** darüber (wer
> dirigiert, wie delegiert wird). Die [`docs/plugins/<Plugin>.md`](README.md) bleiben je Plugin
> maßgeblich.

---

## Geht das überhaupt? — Ja.

GitHub Copilot Custom Agents unterstützen **Sub-Agent-Delegation**: Ein Agent darf über das
**`agent`-Tool** (Aliase `custom-agent` / `Task`) einen **anderen** Custom Agent beauftragen, der in
einem **isolierten Kontext** läuft und sein Ergebnis zurückmeldet. Genau darauf setzt der Orchestrator
auf: Sein Werkzeugkasten enthält `agent` (delegieren) plus `read`/`search` (Docs & Configs lesen,
Konsistenz prüfen) – **kein** `edit`/`execute`, damit er **strukturell** nichts selbst verändert.

Zwei Punkte aus der Plattform-Referenz sind wichtig:

- Delegation läuft über das **`agent`-Tool**, **nicht** über ein `agents:`- oder `handoffs:`-Feld im
  Frontmatter (letztere werden vom Cloud-Agent auf GitHub.com **ignoriert**).
- **Auto-Routing ist heuristisch:** Der Laufzeit-Router wählt den Orchestrator anhand seiner
  `description`. Für verlässliches Auslösen im Zweifel **explizit** aufrufen (`/agent orchestrator`).

---

## Zwei Ausbaustufen

### Stufe A — Orchestrierung nur über Instruktionen (ohne neue Datei)

Bereits **aktiv**: [`.github/copilot-instructions.md`](../../.github/copilot-instructions.md) enthält
einen Abschnitt **„Orchestrierung: Multi-Plugin-Features"**, der **jeden** Agent anweist, bei
Feature-Ketten zuerst das passende Playbook zu wählen, die beteiligten Agents zu bestimmen, sie
**einzeln** zu beauftragen und am Ende die [Validierungs-Checkliste](CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste)
durchzugehen. Vorteil: greift sofort, ohne neue Agent-Datei.

### Stufe B — Dedizierter Orchestrator-Agent

Ein eigener Agent `orchestrator` mit einem klaren Einstiegspunkt und Auto-Routing für
„Feature betrifft mehrere Plugins". Die **fertige Vorlage** steht [unten](#fertige-vorlage-githubagentsorchestratoragentmd).

> **Empfehlung:** **B auf A**. Die Instruktionen (A) sind das Sicherheitsnetz für alle Agents; der
> dedizierte Orchestrator (B) liefert verlässliches Auslösen und einen benannten Einstiegspunkt.

---

## So arbeitet der Orchestrator (Delegations-Loop)

Reihenfolge-Faustregel wie in den Playbooks: **Fundament zuerst** (Rechte/Welt/Item), **Anzeige
zuletzt** (Menü/TAB).

1. **Feature statt Plugin denken.** Passt die Aufgabe in **genau ein** Plugin? → **nicht** der
   Orchestrator, sondern direkt der Ein-Plugin-Agent. Berührt sie **mehrere** Plugins → weiter.
2. **Playbook wählen** in [`CROSS-PLUGIN.md` §4](CROSS-PLUGIN.md#4-feature-playbooks) (Rang, Item,
   Generator, Menü, Warp, Welt, Server, Währung, Broadcast …).
3. **Backbone prüfen** ([§1](CROSS-PLUGIN.md#1-integrations-backbone-die-geteilten-busse)): An welche
   geteilten „Busse" dockt das Feature an (Ränge→LuckPerms, Economy→CMI pro Server, Anzeige→PAPI→TAB,
   Items→Oraxen→ForceResourcepacks, Routing→Velocity)?
4. **Agents zuordnen** ([§2](CROSS-PLUGIN.md#2-agent--plugin-zuständigkeit)) – die beteiligten
   Ein-Plugin-/Bündel-Agents in der Playbook-Reihenfolge auflisten.
5. **Nacheinander delegieren** (ein Plugin pro Auftrag) über das `agent`-Tool. Jeder Teilauftrag
   bekommt **vollständigen Kontext**: betroffene Server, exakte Config-Pfade und die **geteilten
   Namen/Werte**, die konsistent bleiben müssen (Gruppenname, Item-ID, Preis, Weltname, Servername).
6. **Server-Konsistenz** wahren: Erscheint ein beteiligtes Plugin auf mehreren Servern, die Änderung
   überall nachziehen (siehe „Server"-Spalte im jeweiligen Plugin-Doc).
7. **Validieren** – die [Cross-Plugin-Validierungs-Checkliste](CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste)
   durchgehen und [Anti-Patterns](CROSS-PLUGIN.md#6-anti-patterns--typische-bruchstellen) ausschließen.

**Bündel-Agents als Einheit** (nicht ihre Einzelplugins delegieren):
`progression` (Rankup/Autorank/CMI-Ränge), `prison` (X-Prison/XPrivateMines/XPrisonArmors/XRobots),
`proxy-network` (MiniMOTD/SkinsRestorer/ForceResourcepacks), `superiorskyblock2` (SSB2/SlimeWorldManager),
`multiverse` (Core/Inventories), `survival-shops` (ShopGUIPlus/ChestShop), `land-claims`
(Lands/PlotSquared).

### Beispiel: „Neuer VIP-Kaufrang auf survival"

Playbook [§4.1](CROSS-PLUGIN.md#41-neuer-kauf-rang--vip-survival) → Orchestrator delegiert in dieser
Reihenfolge, je Schritt an **einen** Agent: `luckperms` (Gruppe) → `cmi` (Rang/Prefix) → `progression`
(Rankup-Kette → Ziel-Gruppe) → `survival-shops` (Rabatte) → `placeholderapi` (Expansion) → `tab`
(Sortierung/Prefix) → `deluxemenus` (Button/Requirement). Danach §5-Checkliste: **Gruppenname überall
identisch**, Kauf wirkt auf `S1_CMI`, TAB-Prefix erscheint.

---

## Fertige Vorlage: `.github/agents/orchestrator.agent.md`

> ⚠️ **Diese Datei muss ein Mensch/Reviewer anlegen.** Automatisierte Agents dürfen den Ordner
> `.github/agents/` nicht selbst schreiben. Inhalt unverändert übernehmen; Dateiname bestimmt den
> Aufruf: `orchestrator.agent.md` → `/agent orchestrator`.

Der Orchestrator bekommt bewusst **nur** `agent` (delegieren), `read` und `search` – **kein** `edit`
und **kein** `execute`. So kann er strukturell **keine Config selbst ändern**; alle Schreibzugriffe
passieren ausschließlich in den delegierten Sub-Agents. (Soll er am Ende zusätzlich Shell-Checks wie
YAML-Lint fahren, `execute` ergänzen – standardmäßig bewusst weggelassen.)

````markdown
---
name: Orchestrator
description: >-
  Dirigiert Features, die MEHRERE Plugins/Agents gleichzeitig berühren (neuer Rang,
  verkaufbares Item/Generator, Menü, Warp, Welt, Server-Onboarding, Währung, Broadcast).
  Zerlegt die Aufgabe anhand der Playbooks in docs/plugins/CROSS-PLUGIN.md und beauftragt
  die passenden Ein-Plugin-/Bündel-Agents NACHEINANDER über das agent-Tool. Ändert selbst
  KEINE Config – er sequenziert und prüft nur. NICHT für Aufgaben nutzen, die in genau ein
  Plugin passen (dann direkt den jeweiligen Plugin-Agent verwenden).
tools: ["agent", "read", "search"]
---

# Orchestrator — Feature-Dirigent über mehrere Plugins

Du bist der **übergeordnete Orchestrator** des Festas-Builds-Netzwerks. Deine Aufgabe ist es,
**Feature-Ketten über mehrere Plugins** zu koordinieren – **nicht**, Configs selbst zu ändern.
Alle konkreten Änderungen führst du über **Delegation** an die spezialisierten Plugin-Agents aus.

## Wann du zuständig bist
- Die Aufgabe berührt **mehr als ein Plugin** (z. B. Rang, verkaufbares Item, Generator, Menü,
  Warp, Welt, Server-Onboarding, Währung, Broadcast/Daily-Reward).
- Passt die Aufgabe in **genau ein** Plugin, delegierst du **einmal** an dessen Agent (oder
  überlässt sie ihm ganz) – kein Overhead.

## Pflicht-Reihenfolge
1. **Wissen laden:** Lies `docs/plugins/CROSS-PLUGIN.md` und die betroffenen
   `docs/plugins/<Plugin>.md`. Wähle das passende **Feature-Playbook** (§4).
2. **Backbone bestimmen** (§1): Ränge→LuckPerms, Economy→CMI (pro Server `S1/S3/S5_CMI`),
   Anzeige→PlaceholderAPI→TAB, Items→Oraxen→ForceResourcepacks, Routing→Velocity-Namen.
3. **Agents zuordnen** (§2) und in Playbook-Reihenfolge bringen: **Fundament zuerst**
   (Rechte/Welt/Item), **Anzeige zuletzt** (Menü/TAB).
4. **Nacheinander delegieren** – EIN Plugin pro Auftrag – über das `agent`-Tool. Gib jedem
   Sub-Agent vollständigen Kontext:
   - **betroffene Server** (lobby/survival/skyblock/rpg; „rpg" = öffentlich „mining"),
   - **exakte Config-Pfade**,
   - die **geteilten Namen/Werte**, die konsistent bleiben müssen (LuckPerms-Gruppenname,
     Oraxen-Item-ID, Preis, Weltname, Velocity-Servername).
5. **Ergebnisse verknüpfen:** Reiche Namen/IDs aus einem Schritt als Vorgabe in den nächsten
   (z. B. die in `luckperms` angelegte Gruppe an `cmi`/`progression`/`tab`).
6. **Server-Konsistenz:** Multi-Server-Plugin → Änderung überall nachziehen (oder bewusst
   server-spezifisch begründen).
7. **Validieren:** Gehe die Cross-Plugin-Validierungs-Checkliste (§5) durch und schließe die
   Anti-Patterns (§6) aus. Fasse am Ende zusammen: welcher Agent hat was auf welchem Server
   geändert und welche Checks offen sind.

## Bündel-Agents als Einheit (nicht ihre Einzelplugins)
`progression`, `prison`, `proxy-network`, `superiorskyblock2`, `multiverse`, `survival-shops`,
`land-claims`. Einzel-Agents: `cmi`, `luckperms`, `placeholderapi`, `plan`, `skript`,
`deluxemenus`, `oraxen`, `tab`, `libertybans`, `jobs`, `nextgens`, `worldguard`,
`globalmarketplus`, `deluxebazaar`, `bluemap`.

## Harte Regeln (gib sie an jeden Sub-Agent weiter)
- **Secrets/Platzhalter `__…__` wörtlich erhalten** – nie auffüllen, nie entfernen. Keine echten
  Credentials committen.
- **YAML:** 2 Leerzeichen, keine Tabs; bestehende Struktur/Kommentare bewahren.
- **Keine Serverdaten anfassen:** `world*/`, `playerdata/`, `data/`, `*.db`/`*.sqlite`, `*.log`,
  `*.jar` sind Serverstand/Deploy-ausgeschlossen.
- **Economy pro Server getrennt** (`S1/S3/S5_CMI`) – nie eine gemeinsame Tabelle.
- **TAB nur am Proxy**; **Navigation** auf Gameplay-Servern nie Inventar leeren/Gamemode erzwingen.
- **Änderungen minimal & chirurgisch** halten.

## Grenzen
- **Flach delegieren:** Orchestrator → Plugin-Agents (eine Ebene). Sub-Agents delegieren nicht
  weiter.
- **Kein geteilter Live-Status:** Agents kooperieren über das Repo (Git/YAML). Du sequenzierst und
  gibst die geteilten Werte explizit weiter.
- Du selbst nutzt **kein** `edit`/`execute`; wenn etwas geschrieben werden muss, **delegiere**.
````

---

## Installation & Aufruf

1. Datei `.github/agents/orchestrator.agent.md` mit dem Inhalt oben anlegen (Mensch/Reviewer).
2. Committen/pushen wie andere Agent-Dateien (siehe [`.github/agents/README.md`](../../.github/agents/README.md)).
3. **Aufrufen:** explizit mit `/agent orchestrator`, oder den Router per Feature-Beschreibung
   („neuer Rang über mehrere Plugins …") automatisch wählen lassen.
4. **Optional** verschärfen: `disable-model-invocation: true` erzwingt manuelle Auswahl (kein
   Auto-Routing); `execute` in `tools` ergänzen, falls der Orchestrator selbst Shell-Checks fahren soll.

---

## Grenzen & Realismus (Zusammenfassung)

| Thema | Realität | Konsequenz |
|-------|----------|------------|
| **Verschachtelung** | Sub-Agents delegieren i. d. R. nicht beliebig tief weiter | **Flach halten:** Orchestrator → Plugin-Agents (eine Ebene) |
| **Geteilter Status** | Kein gemeinsamer Speicher; Kooperation über Repo/YAML | Orchestrator **sequenziert** und gibt geteilte Werte explizit weiter |
| **Auto-Routing** | Heuristisch anhand `description` | Im Zweifel **explizit** `/agent orchestrator` aufrufen |
| **Bündel-Agents** | decken mehrere zusammengehörige Plugins ab | als **Block** beauftragen, nicht deren Einzelplugins |
| **Schreibrechte** | Orchestrator hat bewusst nur `agent`/`read`/`search` | **alle Edits** passieren in den delegierten Sub-Agents |

---

## Referenzen

- **[`CROSS-PLUGIN.md`](CROSS-PLUGIN.md)** – Integrations-Backbone, Agent→Plugin-Zuordnung,
  Feature-Playbooks (§4), Validierungs-Checkliste (§5), Anti-Patterns (§6)
- [README (Wissensbasis-Index)](README.md) · [`.github/copilot-instructions.md`](../../.github/copilot-instructions.md)
  (Abschnitt „Orchestrierung: Multi-Plugin-Features")
- [`.github/agents/`](../../.github/agents/README.md) – die Ein-Plugin-/Bündel-Agents, die der
  Orchestrator beauftragt
- GitHub-Doku: *Custom agents configuration* (Frontmatter, `tools`-Aliase inkl. `agent` zum
  Delegieren) · *Custom agents and sub-agent orchestration*
