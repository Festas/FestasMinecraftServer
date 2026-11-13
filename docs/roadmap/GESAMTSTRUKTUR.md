# Gesamtstruktur verbessern (Doku + Repo)

Konkrete, **abhakbare** Aufgaben zur Konsolidierung der Dokumentation und Repo-Hygiene. Reihenfolge:
**Hygiene zuerst** (P0), dann Struktur (P1), dann Politur (P2).

> **Scope-Hinweis:** Dies sind **Doku-/Struktur-Aufgaben**. Wo eine Plugin-Config berührt würde, ist
> der zuständige **Custom-Agent** genannt; reine Doku-Tasks entscheidet der **Owner** (kein
> Plugin-Agent). Niemals Secrets/`__PLATZHALTER__`, Serverdaten (`world*/`, `playerdata/`, `*.db`)
> oder technische Keys/Pfade (z. B. `s4_perms`, `S3_CMI`, Ordnername `rpg`) „umbenennen".

Spaltenschema: **ID | Prio | Aufgabe | Betroffene Dateien | Vorgehen/Validierung**

---

## A. Doku-Konsolidierung

| ID | Prio | Aufgabe | Betroffene Dateien | Vorgehen/Validierung |
|----|:----:|---------|--------------------|----------------------|
| G-A1 | **P0** | Doppelte „Single Source of Truth" auflösen: **eine** Datei ist kanonisch, die andere wird Verweis/Teil-Doc. | [`../ARCHITECTURE.md`](../ARCHITECTURE.md), [`../SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) | Empfehlung: `SERVER_OVERVIEW.md` bleibt kanonisch (neuester Stand 2026-08-26, korrektes Branding). `ARCHITECTURE.md` auf Netz-/Datenfluss-Diagramme kürzen und oben auf `SERVER_OVERVIEW.md` verweisen. **Validierung:** beide Dateien behaupten danach nicht mehr beide „maßgeblich"; Querlinks aufgelöst. |
| G-A2 | P1 | `docs/PLUGINS.md` entschlacken; **RPG/MMO-Archiv-Abschnitte** nach `docs/archive/` auslagern. | [`../PLUGINS.md`](../PLUGINS.md) → neu `../archive/PLUGINS-rpg-mmo.md` | Archiv-Ordner `docs/archive/` anlegen, Archiv-Abschnitte verschieben, in `PLUGINS.md` kurzen Stub + Link lassen. **Validierung:** keine toten Anker-Links (`grep -rn "PLUGINS.md#" docs/` prüfen); maßgeblich bleibt [`../plugins/README.md`](../plugins/README.md). |
| G-A3 | P1 | **Dedupe Plan-Doku:** Fokus trennen – Plugin-Config vs. Nginx/Webserver. | [`../infrastructure/PLAN.md`](../infrastructure/PLAN.md), [`../plugins/Plan.md`](../plugins/Plan.md), [`../PLAN-reverse-proxy.md`](../PLAN-reverse-proxy.md) | `plugins/Plan.md` = kanonisch für Plugin-Config/Storage; `infrastructure/PLAN.md` = kanonisch für Nginx/Reverse-Proxy. Gegenseitig kurz verlinken, Redundanz entfernen. **Agent:** `plan` (nur falls Config-Pfade berührt). **Validierung:** keine widersprüchlichen DB-Angaben (`s4_plan`, skyblock=SQLite). |
| G-A4 | P1 | **Dedupe BlueMap-Doku** analog. | [`../infrastructure/BLUEMAP.md`](../infrastructure/BLUEMAP.md), [`../plugins/BlueMap.md`](../plugins/BlueMap.md) | `plugins/BlueMap.md` = Plugin/Render-Config; `infrastructure/BLUEMAP.md` = Nginx/Subdomains. Gegenseitig verlinken. **Agent:** `bluemap`. **Validierung:** Subdomain-Angaben (survival/mining) konsistent. |
| G-A5 | P1 | **PLANNING.md vs NEW_SERVERS.md** klären: PLANNING ist veralteter Fragenkatalog (2026-08-15, MMO/CoinsEngine/HuskSync/Factions), NEW_SERVERS ist die aktuelle Weichenstellung. | [`../PLANNING.md`](../PLANNING.md), [`../NEW_SERVERS.md`](../NEW_SERVERS.md) | Deprecation-Banner auf `PLANNING.md` (siehe Entry-Link-Task im Hub-README) + Verweis auf `NEW_SERVERS.md`/`roadmap/README.md`. Inhalt **nicht** löschen. **Validierung:** Banner vorhanden, Links auflösbar. |

---

## B. Namens- & Terminologie-Konsistenz

### B.1 Glossar (als Referenz in die Docs übernehmen)

| Begriff | Bedeutung | Gilt für |
|---------|-----------|----------|
| **`rpg`** | Velocity-Backend-Name **und** Repo-Ordner (`rpg/`) – Recycling des alten RPG-Slots. **Technischer Key, bleibt unverändert.** | `proxy/velocity.toml`, Deploy-Workflows, Pfade |
| **„mining"** | **Öffentlicher** Name des `rpg/`-Servers (Website, Economy-Export, Plan-Frontend, Spieler). | Prosa, Frontend, Exporter |
| **„prison"** | Laufzeit-/Log-Name desselben Servers (X-Prison-Kern). | Logs, `server-logs/` |
| **„Festas Builds"** | Aktuelles Branding des Netzwerks. | gesamte Doku/Prosa |
| **„MinecraftMMO"** | **Alt-Branding** (eingestellter MMO-Modus) – zu ersetzen. | nur Prosa/Überschriften |

### B.2 Branding-Pass „MinecraftMMO" → „Festas Builds"

| ID | Prio | Aufgabe | Betroffene Dateien | Vorgehen/Validierung |
|----|:----:|---------|--------------------|----------------------|
| G-B1 | **P0** | Alt-Branding in **Prosa/Überschriften** ersetzen – **verifizierte** Fundstellen. | `MinecraftMMO`: [`../ARCHITECTURE.md`](../ARCHITECTURE.md) (2, inkl. H1), [`../PLUGINS.md`](../PLUGINS.md) (2, inkl. H1), [`../CHECKLISTS.md`](../CHECKLISTS.md) (1), [`../WORKFLOWS.md`](../WORKFLOWS.md) (1), [`../PLANNING.md`](../PLANNING.md) (2), [`../infrastructure/BACKUPS.md`](../infrastructure/BACKUPS.md) (1), [`../infrastructure/DATENBANKEN.md`](../infrastructure/DATENBANKEN.md) (1), [`../infrastructure/README.md`](../infrastructure/README.md) (1) · **Schreibweise mit Leerzeichen** „Minecraft MMO": [`../OPERATIONS.md`](../OPERATIONS.md) (1, H1-Titel) | Pro Datei prüfen: `grep -nE 'MinecraftMMO\|Minecraft MMO' <datei>`. **Nur Fließtext/Titel** ersetzen – **keine** technischen Keys (DB `s4_*`, Ordner `rpg`) anfassen. **Validierung:** `grep -riE 'minecraftmmo\|minecraft mmo' docs/` liefert **0** Treffer (außer bewusst in `docs/archive/` als Historie). **Hinweis:** `PLANNING.md` erhält ohnehin einen Deprecation-Banner → dort niedrige Prio. |
| G-B2 | P1 | `rpg`/„mining"/„prison" vereinheitlichen: überall dort, wo es **öffentlich/Prosa** ist, „Mining" verwenden; den technischen Key `rpg` erklärend dazusetzen. | netzwerkweit (Prosa in `docs/`) | Muster aus [`../plugins/README.md`](../plugins/README.md) (Server-Mapping-Tabelle) übernehmen. **Validierung:** keine Prosa-Stelle nennt „RPG-Server" als aktiven Spielmodus (der RPG-Modus ist eingestellt). |

---

## C. Freshness-Pass (Status-Banner für Alt-Docs)

Alt-Docs **nicht** löschen, sondern oben einen **Blockquote-Banner** mit Datum + Verweis auf den
aktuellen Stand setzen (analog zum PLANNING-Banner).

| ID | Prio | Aufgabe | Betroffene Dateien (verifizierte Datums) | Vorgehen/Validierung |
|----|:----:|---------|-------------------------------------------|----------------------|
| G-C1 | P1 | 2026-04-Docs mit Freshness-Banner versehen. | [`../CHECKLISTS.md`](../CHECKLISTS.md) (2026-04-10), [`../WORKFLOWS.md`](../WORKFLOWS.md) (2026-04-10), [`../infrastructure/BACKUPS.md`](../infrastructure/BACKUPS.md) (2026-04-10), [`../survival/TYCOON.md`](../survival/TYCOON.md) (2026-04-11), [`../skyblock/ISLANDS.md`](../skyblock/ISLANDS.md) (2026-04-12), [`../skyblock/MINIONS.md`](../skyblock/MINIONS.md) (2026-04-12), [`../skyblock/PROGRESSION.md`](../skyblock/PROGRESSION.md) (2026-04-12) | Banner: „⚠️ Älterer Stand (JJJJ-MM-TT); maßgeblich ist [`SERVER_OVERVIEW.md`](../SERVER_OVERVIEW.md) / die Plugin-Wissensbasis." **Validierung:** jede Datei hat Banner; `grep -L "Älterer Stand"` findet keine der gelisteten. |
| G-C2 | P1 | 2026-08-15-Docs prüfen: `PLANNING.md` (Banner via Entry-Links), ältere Plugin-Übersichten. | [`../PLANNING.md`](../PLANNING.md), [`../PLUGINS.md`](../PLUGINS.md) | Siehe G-A2/G-A5. **Validierung:** Leser landet in ≤1 Klick beim aktuellen Stand. |

---

## D. Repo-Hygiene

| ID | Prio | Aufgabe | Betroffene Dateien | Vorgehen/Validierung |
|----|:----:|---------|--------------------|----------------------|
| G-D1 | P2 | Entscheidung zu ungenutzten **Default-`-examples/`-Skriptordnern** (Skript liefert sie mit; führender `-` = deaktiviert). **Nicht selbst entfernen** – nur Owner-Entscheidung „behalten/entfernen". | `lobby/plugins/Skript/scripts/-examples/`, `skyblock/plugins/Skript/scripts/-examples/`, `survival/plugins/Skript/scripts/-examples/` | Deploy ist additiv & schließt `*.jar` etc. aus; `-examples/` wird mitsynchronisiert, aber von Skript ignoriert (führender `-`). **Agent:** `skript` (falls bereinigt wird). **Validierung:** falls entfernt, keine aktiven Skripte referenzieren `-examples/`. |
| G-D2 | P2 | Endungslose **Dev-Dateien** auf survival klären (werden von Skript ignoriert, nur `.sk` lädt). | `survival/plugins/Skript/scripts/` (`nitwit`, `setup`, `gen_restrict`, `no_block_abbau`) | Siehe Server-Roadmap → [`SERVER-SURVIVAL.md`](SERVER-SURVIVAL.md). **Agent:** `skript`. **Validierung:** `/sk reload scripts` ohne Fehler; keine toten Referenzen. |

---

## E. Neue Docs anlegen

Fehlende Struktur-/Meta-Docs (je 1 Satz Zweck). Anlegen als Doku-Task; betroffene Plugin-Configs
bleiben unberührt.

| ID | Prio | Neue Datei | Zweck (1 Satz) |
|----|:----:|------------|----------------|
| G-E1 | P2 | `docs/ONBOARDING.md` | Wie ein neuer Mitwirkender/Agent das Repo versteht: Topologie, Deploy-Modell, Guardrails, „wo finde ich was". |
| G-E2 | P2 | `docs/TESTING.md` | Wie Config-Änderungen validiert werden (YAML-Lint, `/… reload`-Befehle, Cross-Plugin-Checkliste, Smoke-Tests vor Neustartfenstern). |
| G-E3 | P2 | `docs/lobby/README.md` | Lobby-Überblick (bisher nur `docs/lobby/PLUGINS.md`): Rolle als Hub, MOTD/Navigator/Server-Selector, Besonderheiten. |
| G-E4 | P2 | `docs/proxy/README.md` | Proxy-Überblick (`docs/proxy/` fehlt komplett): Routing, Forwarding, Proxy-Plugins – siehe [`PROXY.md`](PROXY.md). |
| G-E5 | P2 | `docs/prison/PROGRESSION.md` | Mining/Prison-Progression (Ränge/Prestige/Rebirth/Enchants) analog zu `docs/survival/PROGRESSION.md`. |

**Validierung (alle):** Datei enthält Zweck, korrekte relative Links, `**Letzte Aktualisierung:**`-Zeile;
im jeweiligen Index (`docs/*/README.md` bzw. Hub) verlinkt.

---

## F. Secrets-Referenz verifizieren

**Referenz der Platzhalter:** [`../../SECRETS.md`](../../SECRETS.md) (Root) + Abschnitt „Secrets & Platzhalter"
in [`../plugins/README.md`](../plugins/README.md). Deploy ist **fail-closed** (fehlt ein Secret → Abbruch).

| ID | Prio | Aufgabe | Betroffene Dateien | Vorgehen/Validierung |
|----|:----:|---------|--------------------|----------------------|
| G-F1 | **P1** | Verifizieren, dass **skyblock CMI**-Platzhalter beim Deploy injiziert werden. | Token `__CMI_SKYBLOCK_DB_USER/PASSWORD__` in `skyblock/plugins/CMI/Settings/DataBaseInfo.yml`; Workflow `.github/workflows/deploy-skyblock.yml` | Prüfen, dass `deploy-skyblock.yml` `CMI_SKYBLOCK_DB_ENV` per **Python-Parse** (verbatim) injiziert. **Validierung:** Token bleibt im Repo wörtlich; Workflow referenziert das Secret; fail-closed greift. |
| G-F2 | **P1** | Verifizieren, dass **rpg Dashboard-Addons**-Platzhalter injiziert werden. | `__XPRISON_DASHBOARD_*__` in `rpg/plugins/X-Prison/addons/Dashboard/config.yml`; `__XPRIVATEMINES_DASHBOARD_*__` in `rpg/plugins/XPrivateMines/addons/Dashboard/config.yml`; Workflow `.github/workflows/deploy-rpg.yml` | Prüfen, dass `deploy-rpg.yml` `XPRISON_DASHBOARD_ENV` + `XPRIVATEMINES_DASHBOARD_ENV` injiziert. **Validierung:** alle 6 Tokens (User/Password/JWT ×2) im Repo wörtlich; Workflow deckt beide Addons ab. |
| G-F3 | P1 | **Vollständigkeits-Scan:** Jeder `__…__`-Token hat eine Injektions-Quelle **oder** ist bewusst „dormant". | repo-weit | `grep -rnoE '__[A-Z0-9_]+__' --include='*.yml' --include='*.yaml' --include='*.toml' --include='*.conf' .` gegen die Tabelle in [`../plugins/README.md`](../plugins/README.md) abgleichen. **Validierung:** keine „verwaisten" Platzhalter ohne Workflow-Zuordnung; dormante Tokens (`__LIBERTYBANS_DB_*__`, `__SKINSRESTORER_DB_*__`, `__TAB_DB_*__`) bleiben wörtlich/uninjiziert. |

---

**Letzte Aktualisierung:** 2026-11-02
