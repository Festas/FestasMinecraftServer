# Copilot-Instruktionen · FestasMinecraftServer

Dieses Repository verwaltet die **Konfiguration** eines Minecraft-Netzwerks (Velocity-Proxy + mehrere
Paper-Backends) als Git-Wahrheit. Deploys synchronisieren das Repo auf die Server. Es ist **kein**
Java-Build – geändert werden fast ausschließlich **YAML/Config/Skript-Dateien** und Doku.

## Zuerst lesen: Plugin-Wissensbasis
Für **jede** Aufgabe an einem Plugin **zuerst** die Wissensbasis konsultieren:
- **Index:** [`docs/plugins/README.md`](../docs/plugins/README.md) – Matrix (Plugin→Server), Storage-/Secret-/Deploy-Modell, Konventionen.
- **Pro Plugin:** `docs/plugins/<Plugin>.md` – Zweck, exakte Config-Pfade je Server, Storage/Secrets, typische Aufgaben, Gotchas.
- **Feature über mehrere Plugins:** [`docs/plugins/CROSS-PLUGIN.md`](../docs/plugins/CROSS-PLUGIN.md) – Integrations-Backbone (Ränge/Economy/Platzhalter/Items/Routing), Feature-Playbooks (Rang, Item, Generator, Menü, Welt, Server) und welche Agents in welcher Reihenfolge zu beauftragen sind.
- **Custom Agents:** [`.github/agents/`](agents/) – pro Plugin/Subsystem ein Agent, der es **gezielt auf allen Servern** bearbeitet.

Wenn ein passender `docs/plugins/<Plugin>.md` existiert, ist er die maßgebliche Quelle. Weitere Detaildocs
liegen unter `docs/` (u. a. `docs/infrastructure/DATENBANKEN.md`, `docs/prison/`, `docs/skyblock/`, `docs/survival/`).

## Netzwerk-Topologie & Server-Mapping
- Backends als Top-Level-Ordner: `lobby/`, `survival/`, `skyblock/`, `rpg/`; Proxy: `proxy/`.
- ⚠️ **`rpg/` = der Prison-/Mining-Server.** Velocity-/Ordnername ist `rpg`, der **öffentliche Name ist „mining"**.
  „mining" in Website/Economy/Plan bezieht sich immer auf den Ordner `rpg/`.
- Velocity-Ports: lobby 25566, rpg 25567, survival 25568, skyblock 25569. Servernamen in Configs müssen
  zu `proxy/velocity.toml` passen.

## Storage-/DB-Modell (Kurzfassung)
- **Geteilt (netzwerkweit):** LuckPerms → MariaDB `s4_perms`; Plan → MySQL `s4_plan` (Proxy/lobby/survival/rpg;
  **skyblock nutzt SQLite**).
- **CMI-Economy pro Server getrennt:** `S1_CMI` (survival), `S3_CMI` (rpg/mining), `S5_CMI` (skyblock); Lobby ohne Economy (SQLite).
- **Lokal (SQLite/Datei/H2/HSQLDB):** SuperiorSkyblock2, SlimeWorldManager, X-Prison/XRobots, GlobalMarketPlus,
  Jobs, ShopGUIPlus, NextGens, Lands, PlotSquared, DeluxeBazaar, LibertyBans.

## Secrets & Platzhalter (streng beachten)
- Getrackte Configs enthalten **niemals** echte Passwörter. Geheimnisse sind `__PLATZHALTER__`-Tokens, die
  **erst beim Deploy** injiziert werden. Tokens beim Bearbeiten **wörtlich erhalten** – nicht auffüllen, nicht entfernen.
- Injektion erfolgt per **Python-Parsing** (Werte verbatim), **nie** per Shell-Sourcing (zerstört Sonderzeichen).
- Beispiele: `__LUCKPERMS_DB_*__`, `__REDIS_PASSWORD__`, `__PLAN_DB_*__`, `__CMI_{SURVIVAL,MINING,SKYBLOCK}_DB_*__`,
  `__VELOCITY_FORWARDING_SECRET__`, `__XPRISON_DASHBOARD_*__`, `__XPRIVATEMINES_DASHBOARD_*__` (+ dormante:
  `__LIBERTYBANS_DB_*__`, `__SKINSRESTORER_DB_*__`, `__TAB_DB_*__`). Details: `docs/plugins/README.md` + `SECRETS.md`.
- Nie Secrets committen. Vor Commits geänderte Dateien auf Secrets prüfen.

## Deploy-Modell (deploy-*.yml)
- Sync ist **additiv**: `rsync` **ohne** `--delete`, kein `rm`. Es werden nur Dateien **überschrieben/ergänzt**,
  nie server-seitige `.jar`/Plugins/Weltdaten gelöscht.
- Ausgeschlossen u. a.: `*.jar`, `*.db`/`*.sqlite`, `*.log`, Archive, Keys, `playerdata/`, `world*/`, Caches.
- Fail-closed bei fehlenden Secrets. Fehlt ein Secret, bricht der Deploy ab, statt kaputte Configs auszurollen.

## Konventionen & Leitplanken
- **YAML:** 2 Leerzeichen, keine Tabs. Vorhandene Struktur/Kommentare/Formatierung respektieren.
- **TAB/Scoreboard** nur am Proxy (`proxy/plugins/tab/`), netzwerkweit; zieht Backend-Werte über die
  PlaceholderAPI-Bridge. CMI-Tablist nur auf lobby+skyblock aktiv.
- **Economy** immer über CMI (`cmi money give …`) bzw. Vault.
- **Broadcasts** = Skript `every N minutes: broadcast` in `<server>/plugins/Skript/scripts/help.sk` (kein CMI-Announcer).
- **Navigation** auf Gameplay-Servern (survival/skyblock/rpg): **nie** Inventar leeren / Gamemode erzwingen –
  nur leeren Slot füllen. **Nur die Lobby** leert/setzt Gamemode.
- **Skript:** Dateien/Ordner mit führendem `-` sind **deaktiviert**.
- **CMI-Neustarts** (Europe/Berlin, gestaffelt): Survival 03:55, Lobby 04:00, RPG 04:05, Skyblock 04:10 – CI davor terminieren.
- Änderungen **minimal & chirurgisch** halten; server-übergreifende Konsistenz wahren (gleiches Feature auf
  mehreren Servern → überall nachziehen). Keine echten `world*/`/Spieler-Daten anfassen (sind Serverstand).

## Nach Änderungen
- Betroffene Configs auf gültiges YAML prüfen und Platzhalter-Tokens unangetastet lassen.
- Bei server-übergreifenden Plugins prüfen, ob dieselbe Änderung auf allen betroffenen Servern nötig ist
  (siehe „Server"-Spalte im jeweiligen `docs/plugins/<Plugin>.md`).
