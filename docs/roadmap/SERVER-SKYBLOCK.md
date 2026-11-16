# Skyblock — Verbesserungs-Roadmap

**Zweck:** Koop-Skyblock (SuperiorSkyblock2, Insel-Mitglieder statt Gilden) — dieses Doc führt den **MVP-Feinschliff** bis zur Launch-Reife.

**Status:** 🟡 **im Umbau** (konfiguriert, noch nicht launch-reif) · **Velocity-Server:** `skyblock` · **Port:** `25569`

> **Kern steht tief** (SSB2 + DeluxeBazaar + GlobalMarketPlus + Oraxen), aber die **Skript-Schicht ist dünn**, die **Economy-Secrets** wollen verifiziert sein und die **Alt-Doku** beschreibt teils ein früheres MMO-/Minion-Skyblock.

---

## So nutzt du dieses Doc

Jede Zeile der [Haupt-Tabelle](#baustellen--verbesserungen) ist eine **abhakbare Aufgabe** (☐) mit **ID**, **Prio** (P0 = Hygiene/Blocker, P1 = Kern-Feature, P2 = Politur), **zuständigem Agent** und **Validierung**. Beauftrage den genannten Agent (ein Plugin → direkt; mehrere → [`../plugins/CROSS-PLUGIN.md §4`](../plugins/CROSS-PLUGIN.md#4-feature-playbooks)), ändere **minimal & chirurgisch** und validiere via [Checkliste §5](../plugins/CROSS-PLUGIN.md#5-cross-plugin-validierungs-checkliste). Einstieg & Guardrails: [Roadmap-Hub `README.md`](README.md).

---

## IST-Stand (kurz)

- **Plugin-Stack:** CMI (+CMILib), SuperiorSkyblock2, SlimeWorldManager, DeluxeBazaar, GlobalMarketPlus, DeluxeMenus, Oraxen, PlaceholderAPI, Plan, Multiverse-Core/-Inventories, Skript, VoidGen, nightcore, Vault, ProtocolLib (+ bStats/faststats/spark).
- **SuperiorSkyblock2 (sehr tief):** `config.yml`, `menus/` (umfangreich), `modules/` = **bank, generators, missions, upgrades**; Missionen unter `modules/missions/categories/` = **explorer, farmer, fisherman, miner, slayer**; `block-values/`, `lang/`, `schematics/`, `heads.yml`, `interactables.yml`, `safe_blocks.yml`.
- **DeluxeBazaar (Instant-Shop, voll):** `categories.yml`, `items.yml`, `menus.yml`, `messages.yml`, `config.yml`, `database.yml`. Dazu **GlobalMarketPlus** (Spieler-Markt) voll.
- **Skript (dünn):** `onboarding.sk`, `skyblock_events.sk`, `daily_rewards.sk`, `navigator.sk`, `help.sk` (+ deaktivierter `-examples/`-Ordner).
- **Economy:** CMI-DB **`S5_CMI`** (pro Server). `CMI/Settings/DataBaseInfo.yml` nutzt die Platzhalter `__CMI_SKYBLOCK_DB_USER__` / `__CMI_SKYBLOCK_DB_PASSWORD__` (DB `s5_CMI`, injiziert beim Deploy). **Plan nutzt hier SQLite** (nicht die geteilte `s4_plan`).
- **CMI-Tablist:** aktiv (lobby + skyblock).
- **`server.properties`:** `motd=A Minecraft Server` (Default — Netz-MOTD via Proxy-MiniMOTD), `gamemode=survival`, `difficulty=normal`.
- **CMI-Neustart:** täglich **04:10** (Europe/Berlin — spätestes Fenster).

## Stärken

- **SuperiorSkyblock2 ist sehr tief konfiguriert** (Missionen in 5 Kategorien, Upgrades, Bank/Generator-Module, Block-Values, mehrsprachige `lang/`, viele `menus/`).
- **Doppelte Economy-Senke** steht: DeluxeBazaar (Instant) + GlobalMarketPlus (Spieler-Markt), beide voll.
- Oraxen voll; saubere Welt-Isolation via SlimeWorldManager + Multiverse-Inventories möglich.

## Baustellen & Verbesserungen

| ID | Prio | Aufgabe | Zuständiger Agent | Betroffene Pfade | Validierung |
|----|:----:|---------|-------------------|------------------|-------------|
| ☐ SKY-01 | **P0** | **CMI-Secrets verifizieren:** `DataBaseInfo.yml` nutzt `__CMI_SKYBLOCK_DB_*__` → prüfen, dass `deploy-skyblock.yml` die Werte injiziert (fail-closed) und die **Token wörtlich** im Repo bleiben. | `cmi` (+ Deploy/Repo-Maintainer) | `skyblock/plugins/CMI/Settings/DataBaseInfo.yml` | Deploy bricht bei fehlendem Secret ab; nach Deploy verbindet CMI-Economy `S5_CMI` |
| ☐ SKY-02 | **P1** | **SSB2-MVP finalisieren:** Insel-Level (`block-values`), Missionen (explorer/farmer/fisherman/miner/slayer), Upgrades und Rollen/Menüs auf Launch-Konsistenz prüfen. | `superiorskyblock2` | `skyblock/plugins/SuperiorSkyblock2/{config.yml,block-values/,modules/missions/,modules/upgrades/,menus/}` | `/is`: Level steigt, Missionen/Upgrades/Rollen greifen |
| ☐ SKY-03 | **P1** | **DeluxeBazaar-Preise & -Kategorien balancieren** (Instant-Shop, Senke gegen `S5_CMI`) und mit GlobalMarketPlus abstimmen (keine Arbitrage). | `deluxebazaar` (+ `globalmarketplus`) | `skyblock/plugins/DeluxeBazaar/{categories.yml,items.yml,menus.yml,messages.yml}` | `/bazaar`: Kauf/Verkauf bucht `S5_CMI`; keine Preis-Lücke zum Spieler-Markt |
| ☐ SKY-04 | **P1** | **Skript-Schicht ausbauen** (dünn): `onboarding.sk`, `skyblock_events.sk`, `daily_rewards.sk` vertiefen; Navigator & Broadcasts (`help.sk`-Rotation) ergänzen. **Nav nur leere Slots füllen** (Gameplay-Server). | `skript` | `skyblock/plugins/Skript/scripts/{onboarding,skyblock_events,daily_rewards,navigator,help}.sk` | `/sk reload`; Onboarding/Rewards greifen; Nav leert **kein** Inventar |
| ☐ SKY-05 | **P1** | **Insel-Welten-Isolation:** SlimeWorld-Inseln **nicht** in eine geteilte Multiverse-Inventories-Gruppe legen (Inventar-Vermischung vermeiden). | `superiorskyblock2` + `multiverse` | `skyblock/plugins/{SuperiorSkyblock2,SlimeWorldManager,Multiverse-Inventories}/` | Inventar trennt sauber pro Insel/Welt |
| ☐ SKY-06 | **P2** | Economy-Tuning `S5_CMI`: Startguthaben, Sell-/Buy-Werte, Balance-Platzhalter für TAB/Menüs. | `cmi` (+ `deluxebazaar`) | `skyblock/plugins/CMI/` | `/cmi money`; Werte plausibel; `/papi parse me %…%` |
| ☐ SKY-07 | **P2** | MOTD-Konsistenz: Backend-MOTD Default → **Netz-MOTD via Proxy-MiniMOTD**; nur prüfen/abgleichen. | `proxy-network` (MiniMOTD) | `proxy/plugins/minimotd-velocity/` *(Info: `skyblock/server.properties`)* | Serverliste zeigt „Festas Builds"-MOTD |
| ☐ SKY-08 | **P2** | GlobalMarketPlus-Gebühren/Limits/Laufzeiten (Spieler-Markt, `S5_CMI`) balancieren. | `globalmarketplus` | `skyblock/plugins/GlobalMarketPlus/config.yml` | `/market`; Gebühr korrekt von `S5_CMI` abgebucht |

## Offene Entscheidungen

- **Koop-Umfang:** Welche SSB2-Rollen/Rechte für Insel-Mitglieder (Koop **ohne Gilden**) sind MVP, welche kommen später? → Scope-Entscheid vor SKY-02.
- **Progression-Modell:** Setzt Skyblock rein auf SSB2-Level/Missionen/Upgrades, oder soll zusätzliche Progression (Skript/Ränge) ergänzt werden? (Betrifft SKY-02/SKY-04.)
- **Alt-Doku-Gültigkeit:** [`docs/skyblock/MINIONS.md`](../skyblock/MINIONS.md) (beschreibt „JetsMinions" — **nicht** im aktuellen Stack) und [`docs/skyblock/PROGRESSION.md`](../skyblock/PROGRESSION.md) (nennt „MMO-Progression") sind vom **2026-04-12** und passen nicht zum schlanken Koop-Stand — streichen, archivieren oder neu schreiben? (Owner-Entscheid.)

## Doku-Pflege

- **Alt-Docs prüfen/aktualisieren** (Stand 2026-04-12 → aktueller Koop-Stand): [`docs/skyblock/ISLANDS.md`](../skyblock/ISLANDS.md), [`docs/skyblock/MINIONS.md`](../skyblock/MINIONS.md) (JetsMinions nicht vorhanden), [`docs/skyblock/PROGRESSION.md`](../skyblock/PROGRESSION.md) (MMO-Bezug veraltet). [`docs/skyblock/README.md`](../skyblock/README.md) ist bereits auf „🟢 Umbau, ohne Gilden, Koop" aktualisiert und dient als Referenz.
- Nach SKY-01 den Secrets-/Deploy-Status in [`GESAMTSTRUKTUR.md`](GESAMTSTRUKTUR.md#f-secrets-referenz-verifizieren) mit abhaken.

---

**Navigation:** [Roadmap-Hub](README.md) · [Gesamtstruktur](GESAMTSTRUKTUR.md) · [KI-Agents](KI-AGENTS.md) · [Proxy](PROXY.md)
**Plugin-Wissensbasis:** [Index](../plugins/README.md) · [CROSS-PLUGIN](../plugins/CROSS-PLUGIN.md) · [SuperiorSkyblock2](../plugins/SuperiorSkyblock2.md) · [SlimeWorldManager](../plugins/SlimeWorldManager.md) · [DeluxeBazaar](../plugins/DeluxeBazaar.md) · [GlobalMarketPlus](../plugins/GlobalMarketPlus.md) · [CMI](../plugins/CMI.md) · [Multiverse](../plugins/Multiverse.md) · [Skript](../plugins/Skript.md) · [Oraxen](../plugins/Oraxen.md)
**Server-Doku:** [`README`](../skyblock/README.md) · [`ISLANDS`](../skyblock/ISLANDS.md) · [`MINIONS`](../skyblock/MINIONS.md) · [`PROGRESSION`](../skyblock/PROGRESSION.md) · [`PLUGINS`](../skyblock/PLUGINS.md)

**Letzte Aktualisierung:** 2026-11-02
