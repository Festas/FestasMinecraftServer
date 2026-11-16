# Lobby — Verbesserungs-Roadmap

**Zweck:** Der Hub, in dem Spieler landen und von dem aus sie per Navigator/Server-Selector in die Gameplay-Server springen — dieses Doc listet die geplanten Verbesserungen.

**Status:** 🟢 **aktiv** (live & gepflegt) · **Velocity-Server:** `lobby` · **Port:** `25566`

> **Lobby-Sonderrolle:** Die Lobby ist der **einzige** Server, der das Inventar leeren und den Gamemode erzwingen **darf**. Auf allen Gameplay-Servern (survival/skyblock/rpg) ist das verboten — dort nur leere Slots füllen.

---

## So nutzt du dieses Doc

Jede Zeile der [Haupt-Tabelle](#baustellen--verbesserungen) ist eine **abhakbare Aufgabe** (☐) mit **ID**, **Prio** (P0 = Hygiene/Blocker, P1 = Kern-Feature, P2 = Politur), **zuständigem Agent** und **Validierung**. Wähle eine Aufgabe, beauftrage den genannten Agent (ein Plugin → direkt; mehrere → Playbook in [`../plugins/CROSS-PLUGIN.md §4`](../plugins/CROSS-PLUGIN.md#4-feature-playbooks)), setze sie minimal/chirurgisch um und gehe danach die [Validierungs-Checkliste §5](../plugins/CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste) durch. Einstieg, Legenden & Guardrails: [Roadmap-Hub `README.md`](README.md).

---

## IST-Stand (kurz)

- **Plugin-Stack (schlank, Hub-tauglich):** CMI (+CMILib), LuckPerms, PlaceholderAPI, Plan, Oraxen, DeluxeMenus, Skript, WorldGuard, FastAsyncWorldEdit, ProtocolLib, Vault (+ bStats/faststats/spark).
- **DeluxeMenus:** `config.yml` + **6 Menüs** unter `lobby/plugins/DeluxeMenus/gui_menus/`: `server_selector.yml`, `network_guide.yml`, `rules.yml`, `help_menu.yml`, `advanced_menu.yml`, `requirements_menu.yml`.
- **Skript (3 eigene + Default-Rest):** `navigator.sk` (Kompass Slot 4 + Guide-Buch Slot 8, **füllt nur leere Slots**), `lobby_features.sk`, `help.sk`; dazu der **ungenutzte, deaktivierte** Default-Ordner `-examples/`.
- **Economy:** Lobby **ohne** CMI-Economy-DB (kein `Sx_CMI`) — bewusst.
- **CMI-Tablist:** aktiv auf **lobby + skyblock** (restliche Tablist läuft über Proxy-TAB).
- **`server.properties`:** `motd=A Minecraft Server` (Default — Netz-MOTD läuft über Proxy-MiniMOTD), `gamemode=survival`, `difficulty=peaceful`, `force-gamemode=false`, `spawn-protection=16`.
- **CMI-Neustart:** täglich **04:00** (Europe/Berlin).

## Stärken

- Oraxen + DeluxeMenus-Setup ist **solide und vollständig** (6 durchdachte Menüs inkl. Server-Selector & Network-Guide).
- Skript-Schicht ist **sauber und minimal**; der Navigator respektiert bereits die Guardrail „nur leere Slots füllen".
- Schlanker, Hub-gerechter Plugin-Stack ohne Economy-Ballast.

## Baustellen & Verbesserungen

| ID | Prio | Aufgabe | Zuständiger Agent | Betroffene Pfade | Validierung |
|----|:----:|---------|-------------------|------------------|-------------|
| ☐ LOB-01 | **P0** | `docs/lobby/README.md` **anlegen** (es existiert nur `PLUGINS.md`): Hub-Konzept, Rolle im Netz, Navigator-Flow, Schutz-Zonen, Neustartfenster. | Doku (Repo-Maintainer; vgl. [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md#e-neue-docs-anlegen)) | `docs/lobby/README.md` | Markdown-Review; Links auflösen; stimmig mit Ist-Stand |
| ☐ LOB-02 | **P1** | Server-Selector/Navigator polieren: Server-Namen in `server_selector.yml` **exakt** an `proxy/velocity.toml` angleichen (**`rpg` = „mining"**), Icons/Requirements prüfen. | `deluxemenus` (+ `skript`) | `lobby/plugins/DeluxeMenus/gui_menus/server_selector.yml`, `lobby/plugins/Skript/scripts/navigator.sk` | `/dm reload`; jeder Klick routet auf den richtigen Server; `/papi parse me %…%` |
| ☐ LOB-03 | **P1** | Hub-Schutz härten: WorldGuard-Spawn-Region (`build deny`, `pvp deny`, `mob-spawning deny`, `keep-inventory`, kein Hunger/Fallschaden). | `worldguard` | `lobby/plugins/WorldGuard/` (Regions-Config, **keine** `world*/`-Daten) | Spawn betreten → kein Bauen/PvP; `/rg info` zeigt Flags |
| ☐ LOB-04 | **P1** | Oraxen-Hub-Items/Glyphs ausbauen (Nav-Icons, Menü-Köpfe, Branding-Font); nach Pack-Änderung **SHA-1** in ForceResourcepacks (Proxy) bumpen. | `oraxen` (+ `proxy-network`) | `lobby/plugins/Oraxen/items/`, `lobby/plugins/Oraxen/glyphs/` | `/oraxen reload`; Client sieht Icons; Pack-Hash aktualisiert |
| ☐ LOB-05 | **P2** | Gamemode/Difficulty-Prüfung: `gamemode=survival` + `difficulty=peaceful` für einen reinen Hub bestätigen oder auf `adventure`/gewünschten Wert setzen (Lobby **darf** Gamemode setzen). Siehe [Offene Entscheidungen](#offene-entscheidungen). | Repo-Maintainer (+ `skript`, falls Join-Handling) | `lobby/server.properties`, `lobby/plugins/Skript/scripts/lobby_features.sk` | Join → gewünschter Gamemode; kein Mob-Schaden am Spawn |
| ☐ LOB-06 | **P2** | MOTD-Konsistenz: Backend-MOTD ist Default — die **Netz-MOTD läuft über Proxy-MiniMOTD**. Nur prüfen, ob Backend-MOTD irrelevant/konsistent ist (niedrige Prio). | `proxy-network` (MiniMOTD) | `proxy/plugins/minimotd-velocity/` *(Info: `lobby/server.properties`)* | Serverliste zeigt „Festas Builds"-MOTD |
| ☐ LOB-07 | **P2** | Hygiene: ungenutzten Default-Ordner `-examples/` (bereits deaktiviert) final einordnen — belassen, säubern oder entfernen. **Nicht raten, Deploy ist additiv** (kein Löschen am Server). | `skript` | `lobby/plugins/Skript/scripts/-examples/` | `/sk reload` ohne Fehler; keine toten Beispiel-Skripte aktiv |

## Offene Entscheidungen

- **Hub-Gamemode/Difficulty:** Soll der Hub auf `adventure` (kein Blockabbau, typisch für Lobbys) statt `survival` laufen, und ist `difficulty=peaceful` gewollt? → Owner-Entscheid, erst dann LOB-05 umsetzen.
- **Branding am Backend:** Reicht die Proxy-MiniMOTD, oder soll die Lobby zusätzlich ein Backend-MOTD/„Festas Builds"-Branding tragen? (Betrifft LOB-06.)
- **`-examples/`:** Als Referenz behalten oder endgültig aus dem Repo nehmen? (Betrifft LOB-07.)

## Doku-Pflege

- **`docs/lobby/README.md`** neu anlegen (LOB-01) — aktuell existiert nur `docs/lobby/PLUGINS.md`.
- Nach Änderungen an Selector/Navigator die Server-Namen-Konsistenz mit [`../SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) (Registry) abgleichen.
- MOTD-/Branding-Aussagen mit [`PROXY.md`](PROXY.md) (MiniMOTD) konsistent halten.

---

**Navigation:** [Roadmap-Hub](README.md) · [Gesamtstruktur](GESAMTSTRUKTUR.md) · [KI-Agents](KI-AGENTS.md) · [Proxy](PROXY.md)
**Plugin-Wissensbasis:** [Index](../plugins/README.md) · [CROSS-PLUGIN](../plugins/CROSS-PLUGIN.md) · [DeluxeMenus](../plugins/DeluxeMenus.md) · [Oraxen](../plugins/Oraxen.md) · [Skript](../plugins/Skript.md) · [WorldGuard](../plugins/WorldGuard.md) · [CMI](../plugins/CMI.md)
**Server-Doku:** [`docs/lobby/PLUGINS.md`](../lobby/PLUGINS.md)

**Letzte Aktualisierung:** 2026-11-02
