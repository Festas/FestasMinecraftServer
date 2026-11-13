# Verbesserungsplan & Roadmap · Festas Builds (Hub)

**Zentraler Einstieg für Owner *und* KI-Agents.** Dieses Dokument ist das „Geländer", an dem man
sich durch alle geplanten Verbesserungen des Netzwerks heranhangelt: von hier führen Links zu den
Struktur-, Proxy-, Agent- und den vier Server-Roadmaps.

> **Verhältnis zu bestehenden Docs:** Der *tatsächliche Ist-Stand* steht in
> [`../SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) (Single Source of Truth). Dieses Verzeichnis
> beschreibt den *Soll-Weg dorthin* (was noch zu tun ist). Bei Widersprüchen zum Ist-Stand gilt
> `SERVER_OVERVIEW.md`.

---

## Vision & Ist-Stand (1 Absatz)

**Festas Builds** (`mc.festas-builds.com`, Paper/Velocity **26.2**, Java-only, Deutsch) ist ein
Velocity-Netzwerk aus vier Paper-Backends. Aktiver Kern ist **Survival** (Lands-Städte + Freebuild/
PlotSquared); die **Lobby** ist der Hub. **Mining** (Ordner `rpg/`, X-Prison) und **Skyblock**
(SuperiorSkyblock2, Koop ohne Gilden) sind **im Aufbau/Umbau**. Technisch tief konfiguriert sind
X-Prison (mining) und SuperiorSkyblock2 (skyblock); es fehlen aber Glue-Skripte (mining), ein
sauberer MVP-Schnitt (skyblock), eine aufgeräumte Skript-Baustelle (survival) und eine
konsolidierte, namens-konsistente Doku (netzwerkweit). Das Repo ist ein **reines Config-Repo**
(kein Java-Build); Deploys laufen additiv via GitHub Actions und sind **fail-closed** bei
fehlenden Secrets.

---

## So nutzt du diese Roadmap

### Status-Legende (Reifegrad)
| Symbol | Bedeutung |
|:------:|-----------|
| 🟢 | **aktiv** – live & gepflegt |
| 🟡 | **im Aufbau** – konfiguriert, aber noch nicht launch-reif |
| 🔴 | **Baustelle** – widersprüchlich/unfertig, braucht Aufräumen oder Entscheidung |

### Prioritäts-Legende
| Prio | Bedeutung |
|:----:|-----------|
| **P0** | kritisch / Hygiene – zuerst (Secrets, Namens-/Doku-Konsistenz, Deploy) |
| **P1** | wichtig – Kernfunktion/Launch-Blocker (Glue-Logik, MVP, Agent-System) |
| **P2** | nice-to-have – Politur (Branding-Feinschliff, Onboarding/Testing-Docs) |

### Agent-getriebener Workflow (für jede Aufgabe)
1. **Aufgabe wählen** – aus der [Master-Backlog-Tabelle](#querschnitts-themen-master-backlog) oder
   einer Server-Roadmap (`SERVER-*.md`).
2. **Passendes Detail-Doc öffnen** – Struktur ([`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md)), Proxy
   ([`PROXY.md`](PROXY.md)), Agents ([`KI-AGENTS.md`](KI-AGENTS.md)) oder die jeweilige Server-Datei.
3. **Zuständigkeit klären** –
   - **Ein Plugin?** → direkt den genannten **Custom-Agent** beauftragen
     (siehe [`KI-AGENTS.md`](KI-AGENTS.md) + [`../../.github/agents/README.md`](../../.github/agents/README.md)).
   - **Mehrere Plugins?** → **Orchestrator** bzw. das passende Playbook in
     [`../plugins/CROSS-PLUGIN.md §4`](../plugins/CROSS-PLUGIN.md#4-feature-playbooks).
4. **Umsetzen** – minimal & chirurgisch; Guardrails beachten (siehe unten).
5. **Validieren** – die
   [Cross-Plugin-Validierungs-Checkliste §5](../plugins/CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste)
   durchgehen und Server-Konsistenz sicherstellen.

### Guardrails (immer gültig – Kurzform)
- Secrets als `__PLATZHALTER__` **wörtlich** erhalten; nie echte Credentials.
- YAML: **2 Leerzeichen**, keine Tabs; Struktur/Kommentare bewahren.
- **Keine Serverdaten** anfassen (`world*/`, `playerdata/`, `data/`, `*.db`, `*.log`, `*.jar`).
- Economy **pro Server getrennt** (`S1`/`S3`/`S5_CMI`); **TAB nur am Proxy**.
- Navigation auf **Gameplay-Servern** (survival/skyblock/mining) **nie** Inventar leeren/Gamemode
  erzwingen – nur leere Slots füllen (das darf **nur die Lobby**).
- Broadcasts via **Skript `help.sk`** (kein CMI-Announcer). **`rpg` = „mining"**.
- CMI-Neustarts (Europe/Berlin): Survival **03:55**, Lobby **04:00**, RPG/Mining **04:05**,
  Skyblock **04:10** – zeitkritische Automationen davor legen.

---

## Querschnitts-Themen (Master-Backlog)

Die netzwerkweiten Verbesserungs-Stränge, nach Priorität. Jede Zeile verweist auf das Detail-Doc mit
konkreten, abhakbaren Aufgaben (ID, zuständiger Agent, Validierung).

| Thema | Prio | Kurzbeschreibung | Detail-Doc |
|-------|:----:|------------------|------------|
| **Doku-Konsolidierung & Namens-Konsistenz** | **P0** | Doppelte „Single Source of Truth" (ARCHITECTURE ↔ SERVER_OVERVIEW) auflösen, Alt-Branding „MinecraftMMO"→„Festas Builds", `rpg`/„mining"/„prison" vereinheitlichen, Dedupe PLAN/BLUEMAP. | [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md) |
| **Secrets/Deploy verifizieren** | **P1** | Prüfen, dass alle `__PLATZHALTER__` (u. a. skyblock CMI `DataBaseInfo.yml`, rpg Dashboard-Addons) in den Deploy-Workflows injiziert werden (fail-closed). | [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md#f-secrets-referenz-verifizieren) |
| **Skript-Baustelle survival aufräumen** | **P1** | Aktive `tycoon_*.sk` ⇄ deaktivierte `-tycoon_*.sk`, `archive/`, endungslose Dev-Dateien (`nitwit`, `setup`, `gen_restrict`, `no_block_abbau`) – Owner-Entscheidung + Aufräumen. | [`SERVER-SURVIVAL.md`](SERVER-SURVIVAL.md) |
| **rpg/mining Glue-Skript-Schicht** | **P1** | X-Prison ist tief konfiguriert, aber die Skript-Klebeschicht fehlt (nur generische `navigator/help/daily_rewards`). Prison-Loop verdrahten. | [`SERVER-MINING.md`](SERVER-MINING.md) |
| **skyblock MVP finalisieren** | **P1** | SSB2-Missionen/Menüs/Block-Values stehen tief; MVP-Scope schließen (MOTD, Skript dünn, CMI-Platzhalter, Economy). | [`SERVER-SKYBLOCK.md`](SERVER-SKYBLOCK.md) |
| **Agent-System & Instructions verbessern** | **P1** | Guardrails zentralisieren statt 4-fach duplizieren, YAML-Validierungsbefehl ergänzen, `skript.agent.md` ans Schema angleichen, `applyTo` um `proxy/velocity.toml` erweitern. | [`KI-AGENTS.md`](KI-AGENTS.md#verbesserungs-backlog-agent-system) |
| **Branding/MOTD-Konsistenz** | **P2** | lobby/skyblock/rpg `server.properties`-MOTD = Default „A Minecraft Server"; lobby `difficulty=peaceful`/`gamemode=survival` auffällig. Netz-MOTD via Proxy-MiniMOTD, Rolle klären. | [`PROXY.md`](PROXY.md) · Server-Roadmaps |
| **Onboarding/Testing-Docs** | **P2** | Fehlende Meta-Docs anlegen: `docs/ONBOARDING.md`, `docs/TESTING.md`, `docs/lobby/README.md`, `docs/proxy/README.md`, `docs/prison/PROGRESSION.md`. | [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md#e-neue-docs-anlegen) |

> **Reihenfolge-Faustregel (netzwerkweit):** erst **Hygiene/Fundament** (P0: Secrets, Namen, Doku),
> dann **Kernfunktion** (P1: Glue/MVP/Agent-System), zuletzt **Politur** (P2).

---

## Index aller Roadmap-Docs

| Doc | Inhalt |
|-----|--------|
| [`README.md`](README.md) *(dieses Doc)* | Hub: Vision, Legenden, Workflow, Master-Backlog, Index |
| [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md) | Doku-Konsolidierung, Namens-Konsistenz, Freshness, Repo-Hygiene, neue Docs, Secrets |
| [`PROXY.md`](PROXY.md) | Proxy/Netzwerk: Routing-Mapping, Proxy-Plugin-Ist-Stand, Verbesserungs-Checkliste |
| [`KI-AGENTS.md`](KI-AGENTS.md) | Nutzung & Verbesserung der 22 Custom-Agents + Orchestrator, Best-Practice-Prompts |
| [`SERVER-LOBBY.md`](SERVER-LOBBY.md) | 🟢 Lobby-Roadmap (Hub) |
| [`SERVER-SURVIVAL.md`](SERVER-SURVIVAL.md) | 🟢 Survival-Roadmap (Hauptmodus, Skript-Baustelle) |
| [`SERVER-SKYBLOCK.md`](SERVER-SKYBLOCK.md) | 🟡 Skyblock-Roadmap (Koop-MVP) |
| [`SERVER-MINING.md`](SERVER-MINING.md) | 🟡 Mining-Roadmap (`rpg/`, Prison-Glue) |

## Verweise auf bestehende Kern-Docs

- [`../SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) — **Ist-Stand / Single Source of Truth** (Topologie, Registry, Economy)
- [`../NEW_SERVERS.md`](../NEW_SERVERS.md) — Weichenstellung & Plugin-Shortlist für Mining/Skyblock
- [`../plugins/README.md`](../plugins/README.md) — Plugin-Wissensbasis (Matrix, Storage/Secrets/Deploy)
- [`../plugins/CROSS-PLUGIN.md`](../plugins/CROSS-PLUGIN.md) — Feature-Playbooks & Validierungs-Checkliste
- [`../../.github/agents/README.md`](../../.github/agents/README.md) — Custom-Agents (Agent↔Plugin↔Server-Tabelle)

---

**Letzte Aktualisierung:** 2026-11-02
