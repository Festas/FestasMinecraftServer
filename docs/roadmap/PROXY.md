# Proxy / Netzwerk — Verbesserungs-Geländer

Verbesserungsplan für den **Velocity-Proxy** und die netzwerkweiten Proxy-Plugins. Der Proxy ist die
**zentrale Präsentations- und Routing-Schicht**: MOTD/Icon, Tablist/Scoreboard, Skins, Moderation,
Resourcepack-Erzwingung und Analytics laufen hier **netzwerkweit**.

> **Zuständige Agents (proxy-zentral):** [`tab`](../../.github/agents/tab.agent.md),
> [`libertybans`](../../.github/agents/libertybans.agent.md),
> [`proxy-network`](../../.github/agents/proxy-network.agent.md) (MiniMOTD + SkinsRestorer +
> ForceResourcepacks), [`plan`](../../.github/agents/plan.agent.md). **TAB ist proxy-zentral –
> nicht auf Backends suchen.**

---

## 1. Proxy-Konzept & Routing-Mapping

- **Modern Forwarding** (`player-info-forwarding-mode = "modern"`) mit **lokaler**
  `forwarding.secret`-Datei (gitignored, nie synchronisiert). Der geteilte Wert landet über
  `__VELOCITY_FORWARDING_SECRET__` in den **Backends** (`<server>/config/paper-global.yml`).
- **Default/Try:** `try = ["lobby"]` – neue Spieler landen in der Lobby.
- **Forced-Host:** `"mc.festas-builds.com" = ["lobby"]`.
- Quelle: [`../../proxy/velocity.toml`](../../proxy/velocity.toml).

| Server | Velocity-Name | Repo-Ordner | Port | Öffentlicher Name | Status |
|--------|---------------|-------------|------|-------------------|:------:|
| Lobby | `lobby` | `lobby/` | 25566 | Lobby | 🟢 |
| Mining | `rpg` | `rpg/` | 25567 | **mining** (Logs: „prison") | 🟡 |
| Survival | `survival` | `survival/` | 25568 | Survival | 🟢 |
| Skyblock | `skyblock` | `skyblock/` | 25569 | Skyblock | 🟡 |

> **Konsistenz-Regel:** Servernamen in **allen** Configs (TAB-Bridge, Plan `ServerInfoFile`,
> DeluxeMenus-Server-Selector) müssen **exakt** zu `velocity.toml` passen (`lobby/rpg/survival/skyblock`).
> Öffentlich heißt `rpg` = „mining".

---

## 2. Ist-Stand der Proxy-Plugins

Ordner: [`../../proxy/plugins/`](../../proxy/plugins/). Alle unten gelisteten sind vorhanden.

| Plugin | Ordner | Rolle | Ist-Stand | Agent | Doku |
|--------|--------|-------|-----------|-------|------|
| **TAB** (+ VelocityScoreboardAPI) | `tab/` | Tablist, Scoreboard, Header/Footer, Nametags, BossBars | custom konfiguriert | `tab` | [`../plugins/TAB.md`](../plugins/TAB.md) |
| **LibertyBans** | `libertybans/` | Netzwerkweite Moderation (Bans/Mutes/Kicks/Warns) | custom; HSQLDB aktiv (MySQL dormant) | `libertybans` | [`../plugins/LibertyBans.md`](../plugins/LibertyBans.md) |
| **MiniMOTD** | `minimotd-velocity/` | Serverlisten-MOTD + Icon | custom | `proxy-network` | [`../plugins/MiniMOTD.md`](../plugins/MiniMOTD.md) |
| **SkinsRestorer** | `skinsrestorer/` | Skins netzwerkweit | custom; File-Storage (MySQL dormant) | `proxy-network` | [`../plugins/SkinsRestorer.md`](../plugins/SkinsRestorer.md) |
| **ForceResourcepacks** | `forceresourcepacks/` | Pack-Erzwingung (folgt Oraxen-Pack) | custom | `proxy-network` | [`../plugins/ForceResourcepacks.md`](../plugins/ForceResourcepacks.md) |
| **Plan** | `plan/` | Analytics (geteilte MySQL `s4_plan`) | **Standard** (noch nicht getunt) | `plan` | [`../plugins/Plan.md`](../plugins/Plan.md) · [`../infrastructure/PLAN.md`](../infrastructure/PLAN.md) |
| **VelocityScoreboardAPI** | `velocity-scoreboard-api/` | API-Backend für TAB-Scoreboards | **Standard** (Library) | `tab` | [`../plugins/libraries.md`](../plugins/libraries.md) |

---

## 3. Verbesserungs-Checkliste

Spaltenschema: **ID | Prio | Aufgabe | Agent | Pfad | Validierung**

| ID | Prio | Aufgabe | Agent | Pfad | Validierung |
|----|:----:|---------|-------|------|-------------|
| P-1 | P1 | **Plan vom Standard auf das Netzwerk tunen** (Server-Identität, geteilte `s4_plan`, skyblock bleibt SQLite, Export-Pfad fürs Frontend). | `plan` | [`../../proxy/plugins/plan/config.yml`](../../proxy/plugins/plan/) | Dashboard erreichbar; Server erscheinen mit korrekten Namen; `__PLAN_DB_*__` wörtlich erhalten. |
| P-2 | **P1** | **Resourcepack-SHA-1-Disziplin:** ForceResourcepacks-Hash/URL **folgt** dem Oraxen-Pack; nach jeder Pack-Änderung Hash bumpen. | `proxy-network` (Hash) ↔ `oraxen` (Pack) | [`../../proxy/plugins/forceresourcepacks/config.yml`](../../proxy/plugins/forceresourcepacks/) | Client lädt **neues** Pack (Hash gebumpt); vgl. [`../plugins/CROSS-PLUGIN.md §4.7`](../plugins/CROSS-PLUGIN.md#47-neues-custom-item--resourcepack-asset) + [`../infrastructure/RESOURCE_PACKS.md`](../infrastructure/RESOURCE_PACKS.md). |
| P-3 | P2 | **MOTD/Icon-Pflege** zentral über MiniMOTD (Branding „Festas Builds"); klären, dass Backend-`server.properties`-MOTDs (lobby/skyblock/rpg = Default) **irrelevant** fürs Netz-MOTD sind. | `proxy-network` | [`../../proxy/plugins/minimotd-velocity/`](../../proxy/plugins/minimotd-velocity/) | Serverliste zeigt gepflegtes MOTD/Icon; Default „A Minecraft Server" erscheint Spielern **nicht** (nur Direktverbindung ans Backend). |
| P-4 | P1 | **LibertyBans-Scope prüfen:** netzwerkweite Wirkung, Nachrichten/Layouts, Storage-Entscheidung (HSQLDB vs. MySQL dormant). | `libertybans` | [`../../proxy/plugins/libertybans/`](../../proxy/plugins/libertybans/) | Test-Ban greift auf allen Backends; `__LIBERTYBANS_DB_*__` bleibt dormant/wörtlich, solange HSQLDB aktiv. |
| P-5 | P1 | **TAB-Platzhalter-Abdeckung:** jeder im TAB genutzte `%…%` hat seine **PAPI-Expansion auf dem Backend**; keine Doppel-Tablist (CMI-Tablist nur lobby+skyblock). | `tab` (+ `placeholderapi` je Backend) | [`../../proxy/plugins/tab/`](../../proxy/plugins/tab/) | `/papi parse me %…%` auf dem Backend liefert Wert; keine leeren Platzhalter/doppelte Tablist. |
| P-6 | P2 | **`docs/proxy/README.md` anlegen** (Proxy-Überblick; `docs/proxy/` fehlt). | Doku-Task (Owner) | neu `docs/proxy/README.md` | Datei verweist auf dieses Geländer + `velocity.toml`; siehe [`GESAMTSTRUKTUR.md §E (G-E4)`](GESAMTSTRUKTUR.md#e-neue-docs-anlegen). |

---

## 4. Guardrails (Proxy-spezifisch)

- **TAB/Scoreboard nur hier** (`proxy/plugins/tab/`) – nie auf Backends duplizieren.
- Proxy-`forwarding.secret` ist **lokal/gitignored**; der geteilte Wert geht per
  `__VELOCITY_FORWARDING_SECRET__` in die **Backends** (`sync-server-configs.yml`).
- Dormante Tokens am Proxy (`__LIBERTYBANS_DB_*__`, `__SKINSRESTORER_DB_*__`, `__TAB_DB_*__`) bleiben
  **wörtlich** und werden **nicht** injiziert (Default-Storage aktiv).
- YAML 2-Space, keine Tabs; `velocity.toml` ist TOML – Tabelle/Keys exakt erhalten.

---

**Siehe auch:** [`README.md`](README.md) (Hub) · [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md) ·
[`KI-AGENTS.md`](KI-AGENTS.md) · [`../plugins/CROSS-PLUGIN.md`](../plugins/CROSS-PLUGIN.md) ·
[`../../.github/agents/README.md`](../../.github/agents/README.md)

**Letzte Aktualisierung:** 2026-11-02
