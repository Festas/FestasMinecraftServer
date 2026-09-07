# Plugin-Wissensbasis (Data Base für AI-Agents)

Diese Wissensbasis ist die **verbindliche Referenz** für alle Plugins des Festas-Builds-Netzwerks.
Sie ist so aufgebaut, dass **AI-Agents (und Menschen) sie automatisch finden und als Datenbasis
nutzen** können, bevor sie eine Plugin-Konfiguration ändern.

- **Für Agents:** `.github/copilot-instructions.md` verweist repo-weit hierher; die pfadgebundene
  Regel `.github/instructions/plugins.instructions.md` blendet beim Bearbeiten von `**/plugins/**`
  automatisch das passende Plugin-Dokument ein.
- **Spezialisierte Agents:** Für die wichtigsten Plugins/Subsysteme gibt es Custom Agents unter
  [`.github/agents/`](../../.github/agents/README.md), die ein Plugin **gezielt auf allen Servern**
  bearbeiten.
- **Feature über mehrere Plugins?** Für Ketten, die **mehrere Plugins/Agents gleichzeitig** berühren
  (neuer Rang, Item, Generator, Menü, Welt, Server), ist [`CROSS-PLUGIN.md`](CROSS-PLUGIN.md) die
  zentrale Orchestrierungs-/Abhängigkeits-Referenz (Integrations-Backbone, Feature-Playbooks,
  Validierungs-Checkliste).

> **Wichtig – Ist-Stand statt Alt-Doku:** Diese Dateien beschreiben den **tatsächlichen Ordner-Inhalt**
> (`<server>/plugins/`). Die älteren Übersichten [`docs/PLUGINS.md`](../PLUGINS.md) und die
> `docs/<server>/PLUGINS.md` enthalten teils Planungs-/Archivstände (z. B. ein früherer MMO-Skyblock
> oder RPG-Stack), die **nicht mehr** im Repo liegen. Bei Widersprüchen gilt **diese** Wissensbasis
> plus der reale Ordner-Inhalt.

---

## So nutzt ein Agent diese Wissensbasis

1. **Plugin identifizieren** (aus dem Task oder dem Pfad, den du änderst).
2. Das passende **`docs/plugins/<Plugin>.md`** lesen (Zweck, Server, Config-Pfade, Storage, Secrets,
   typische Aufgaben, Guardrails).
3. Prüfen, ob es einen **Custom Agent** für das Plugin/Subsystem gibt und ggf. diesen verwenden.
4. **Bei Features über mehrere Plugins:** [`CROSS-PLUGIN.md`](CROSS-PLUGIN.md) konsultieren – dort stehen
   das Integrations-Backbone (Ränge/Economy/Platzhalter/Items/Routing), Schritt-für-Schritt-Playbooks und
   welche Agents in welcher Reihenfolge zu beauftragen sind.
5. Änderungen **auf allen betroffenen Servern konsistent** vornehmen (siehe Server-Matrix unten).
6. **Secrets-/Deploy-Regeln** einhalten (siehe unten) und vor dem Commit validieren.

---

## Netzwerk-Topologie & Server-Mapping

| Ordner      | Rolle                       | Öffentlicher Name | Velocity-Server | Status            |
|-------------|-----------------------------|-------------------|-----------------|-------------------|
| `proxy/`    | Velocity-Proxy              | –                 | –               | aktiv             |
| `lobby/`    | Hub / Lobby (Paper)         | `lobby`           | `lobby`         | aktiv             |
| `survival/` | Survival / Tycoon (Paper)   | `survival`        | `survival`      | aktiv             |
| `skyblock/` | Skyblock (Paper)            | `skyblock`        | `skyblock`      | Umbau             |
| `rpg/`      | Prison / Mining (Paper)     | **`mining`**      | `rpg`           | Aufbau (Prison)   |

> **Merke:** Der Ordner **`rpg/` ist das Mining-/Prison-Backend**. „rpg" ist nur der Velocity-/Ordnername;
> im Frontend, in der Website und in den Exportern heißt der Server **`mining`**.

---

## Plugin → Server-Matrix (Ist-Stand)

Legende: ✅ = Ordner vorhanden unter `<server>/plugins/<Plugin>`. Detail-Doku pro Plugin verlinkt.

### Proxy (Velocity)

| Plugin | proxy | Doku |
|--------|:-----:|------|
| ForceResourcepacks | ✅ | [ForceResourcepacks.md](ForceResourcepacks.md) |
| LibertyBans | ✅ | [LibertyBans.md](LibertyBans.md) |
| MiniMOTD | ✅ | [MiniMOTD.md](MiniMOTD.md) |
| Plan (Proxy) | ✅ | [Plan.md](Plan.md) |
| SkinsRestorer | ✅ | [SkinsRestorer.md](SkinsRestorer.md) |
| TAB | ✅ | [TAB.md](TAB.md) |
| VelocityScoreboardAPI | ✅ | [libraries.md](libraries.md) |

### Backends (Paper) – Core & übergreifend

| Plugin | lobby | survival | skyblock | rpg/mining | Doku |
|--------|:-----:|:--------:|:--------:|:----------:|------|
| CMI (+ CMILib) | ✅ | ✅ | ✅ | ✅ | [CMI.md](CMI.md) |
| LuckPerms | ✅ | ✅ | ✅ | ✅ | [LuckPerms.md](LuckPerms.md) |
| PlaceholderAPI | ✅ | ✅ | ✅ | ✅ | [PlaceholderAPI.md](PlaceholderAPI.md) |
| Plan | ✅ | ✅ | ✅ | ✅ | [Plan.md](Plan.md) |
| Vault | ✅ | ✅ | ✅ | ✅ | [libraries.md](libraries.md) |
| Skript | ✅ | ✅ | ✅ | ✅ | [Skript.md](Skript.md) |
| DeluxeMenus | ✅ | ✅ | ✅ | ✅ | [DeluxeMenus.md](DeluxeMenus.md) |
| Oraxen | ✅ | ✅ | ✅ | ✅ | [Oraxen.md](Oraxen.md) |
| ProtocolLib | ✅ | ✅ | ✅ | ✅ | [libraries.md](libraries.md) |

### Backends – Welten, Schutz, Building

| Plugin | lobby | survival | skyblock | rpg/mining | Doku |
|--------|:-----:|:--------:|:--------:|:----------:|------|
| WorldGuard | ✅ | ✅ | – | ✅ | [WorldGuard.md](WorldGuard.md) |
| Multiverse-Core | – | ✅ | ✅ | ✅ | [Multiverse.md](Multiverse.md) |
| Multiverse-Inventories | – | ✅ | ✅ | ✅ | [Multiverse.md](Multiverse.md) |
| VoidGen | – | ✅ | ✅ | – | [libraries.md](libraries.md) |
| Chunky | – | ✅ | – | – | [libraries.md](libraries.md) |
| FastAsyncWorldEdit | ✅ | ✅ | – | ✅ | [libraries.md](libraries.md) |
| AxiomPaper | – | ✅ | – | – | [libraries.md](libraries.md) |
| BlueMap | – | ✅ | – | ✅ | [BlueMap.md](BlueMap.md) |

### Backends – Economy, Shops, Progression

| Plugin | lobby | survival | skyblock | rpg/mining | Doku |
|--------|:-----:|:--------:|:--------:|:----------:|------|
| NextGens | – | ✅ | – | – | [NextGens.md](NextGens.md) |
| Rankup | – | ✅ | – | – | [Rankup.md](Rankup.md) |
| Autorank | – | ✅ | – | – | [Autorank.md](Autorank.md) |
| Jobs | – | ✅ | – | – | [Jobs.md](Jobs.md) |
| ShopGUIPlus | – | ✅ | – | – | [ShopGUIPlus.md](ShopGUIPlus.md) |
| ChestShop | – | ✅ | – | – | [ChestShop.md](ChestShop.md) |
| GlobalMarketPlus | – | ✅ | ✅ | ✅ | [GlobalMarketPlus.md](GlobalMarketPlus.md) |
| DeluxeBazaar | – | – | ✅ | – | [DeluxeBazaar.md](DeluxeBazaar.md) |

### Backends – Gamemode-Kerne & Content

| Plugin | lobby | survival | skyblock | rpg/mining | Doku |
|--------|:-----:|:--------:|:--------:|:----------:|------|
| SuperiorSkyblock2 | – | – | ✅ | – | [SuperiorSkyblock2.md](SuperiorSkyblock2.md) |
| SlimeWorldManager | – | – | ✅ | – | [SlimeWorldManager.md](SlimeWorldManager.md) |
| X-Prison | – | – | – | ✅ | [X-Prison.md](X-Prison.md) |
| XPrivateMines | – | – | – | ✅ | [XPrivateMines.md](XPrivateMines.md) |
| XPrisonArmors | – | – | – | ✅ | [XPrisonArmors.md](XPrisonArmors.md) |
| XRobots | – | – | – | ✅ | [XRobots.md](XRobots.md) |
| Lands | – | ✅ | – | – | [Lands.md](Lands.md) |
| PlotSquared | – | ✅ | – | – | [PlotSquared.md](PlotSquared.md) |
| HeadDatabase | – | ✅ | – | – | [HeadDatabase.md](HeadDatabase.md) |
| LibsDisguises | – | ✅ | – | ✅ | [LibsDisguises.md](LibsDisguises.md) |

### Bibliotheken & Werkzeuge (kein eigener Agent)

CMILib, ProtocolLib, CommandAPI, NBTAPI, RoseGarden, nightcore, Vault, VoidGen, Chunky,
FastAsyncWorldEdit, AxiomPaper, bStats, faststats, spark, VelocityScoreboardAPI →
gesammelt in **[libraries.md](libraries.md)**.

---

## Storage- & Datenbank-Modell (Kurzüberblick)

Vollständige Infrastruktur: [`docs/infrastructure/DATENBANKEN.md`](../infrastructure/DATENBANKEN.md).

| Plugin | Storage | Geteilt / pro Server | DB / Datei |
|--------|---------|----------------------|------------|
| LuckPerms | MariaDB | **netzwerkweit geteilt** | `s4_perms` |
| Plan | MySQL/MariaDB | **netzwerkweit geteilt** | `s4_plan` |
| CMI (Economy) | MySQL | **pro Server getrennt** | `S1_CMI` (survival), `S3_CMI` (rpg/mining), `S5_CMI` (skyblock); Lobby = SQLite |
| SuperiorSkyblock2 | SQLite | pro Server (lokal) | `plugins/SuperiorSkyblock2/` |
| SlimeWorldManager | Datei (Slime) | pro Server (lokal) | `slime_worlds/` |
| X-Prison / XRobots | H2 (embedded) | pro Server (lokal) | Plugin-Ordner |
| GlobalMarketPlus | SQLite (MySQL optional, **aus**) | pro Server (nicht synchron) | Plugin-Ordner |
| DeluxeBazaar | Datei/SQLite | pro Server (lokal) | Plugin-Ordner |
| Jobs / ShopGUIPlus / NextGens | SQLite (MySQL optional, **aus**) | pro Server (lokal) | Plugin-Ordner |
| Lands / PlotSquared | Flatfile / SQLite | pro Server (lokal) | Plugin-Ordner |
| LibertyBans | HSQLDB (embedded; MySQL optional) | pro Proxy | Plugin-Ordner |
| SkinsRestorer / BlueMap | Datei bzw. konfigurierbar | pro Proxy/Server | Plugin-Ordner |
| Redis | – | geteilt | `172.18.0.1:6380` (LuckPerms-Messaging) |

> **SQLite/H2/Flatfile ⇒ keine serverübergreifende Synchronisation.** Nur LuckPerms und Plan teilen
> aktiv eine zentrale DB. CMI hält Economy **bewusst pro Server** in einer **eigenen** DB
> (niemals dieselbe Tabelle für zwei Server).

---

## Secrets & Platzhalter (Injection beim Deploy)

**Grundregel:** Niemals echte Zugangsdaten committen. Configs enthalten `__TOKEN__`-Platzhalter (oder
dormante Vendor-Defaults für deaktivierte Backends). Die Deploy-Workflows injizieren die echten Werte
zur Laufzeit. Details: [`SECRETS.md`](../../SECRETS.md).

| Platzhalter / Ziel | Quelle (GitHub Secret) | Ziel-Datei(en) | Workflow |
|--------------------|------------------------|----------------|----------|
| `__LUCKPERMS_DB_ADDRESS/DATABASE/USER/PASSWORD__` | `LUCKPERMS_DB_ENV` | `<server>/plugins/LuckPerms/config.yml` | `deploy-<server>.yml` |
| `__PLAN_DB_HOST/PORT/USER/PASSWORD/DATABASE__` | `PLAN_DB_ENV` | `<server>/plugins/Plan/config.yml`, `proxy/plugins/plan/config.yml` | `deploy-<server>.yml`, `deploy-proxy.yml` |
| `__CMI_{SURVIVAL,MINING,SKYBLOCK}_DB_USER/PASSWORD__` | `CMI_*_DB_ENV` | `<server>/plugins/CMI/Settings/DataBaseInfo.yml` | `deploy-survival/rpg/skyblock.yml` |
| `__REDIS_PASSWORD__` | `REDIS_PASSWORD` | `<server>/plugins/LuckPerms/config.yml` (Messaging) | `deploy-<server>.yml`, `deploy-redis.yml` |
| `__VELOCITY_FORWARDING_SECRET__` | `VELOCITY_FORWARDING_SECRET` | `<server>/config/paper-global.yml` (`velocity.secret`) | `sync-server-configs.yml` |
| `__XPRISON_DASHBOARD_USER/PASSWORD/JWT_SECRET__` | `XPRISON_DASHBOARD_ENV` | `rpg/plugins/X-Prison/addons/Dashboard/config.yml` | `deploy-rpg.yml` |
| `__XPRIVATEMINES_DASHBOARD_USER/PASSWORD/JWT_SECRET__` | `XPRIVATEMINES_DASHBOARD_ENV` | `rpg/plugins/XPrivateMines/addons/Dashboard/config.yml` | `deploy-rpg.yml` |
| `__LIBERTYBANS_DB_USER/PASSWORD__` | – (dormant; HSQLDB aktiv) | `proxy/plugins/libertybans/sql.yml`, `import.yml` | **kein Inject** (Token bleibt wörtlich) |
| `__SKINSRESTORER_DB_USER/PASSWORD__` | – (dormant; File-Storage aktiv) | `proxy/plugins/skinsrestorer/config.yml` | **kein Inject** (Token bleibt wörtlich) |
| `__TAB_DB_USER/PASSWORD__` | – (dormant; MySQL aus) | `proxy/plugins/tab/config.yml` | **kein Inject** (Token bleibt wörtlich) |

- Der Velocity-Proxy selbst liest ein lokales `forwarding.secret`-File (**gitignored, wird nie
  synchronisiert**); der geteilte Wert landet über den Platzhalter oben in den **Backends**.
- „dormant" = Platzhalter/Default existiert, das Backend ist aber deaktiviert (z. B. HSQLDB/File aktiv
  statt MySQL). Nicht aktivieren, ohne die DB bereitzustellen.

---

## Deploy-Modell (was ein Agent wissen muss)

Die `deploy-*.yml`-Workflows synchronisieren **repo → Server additiv**:

- **Nur geänderte Dateien** (bei `push`) bzw. voller `rsync` (bei manueller Ausführung) – **kein
  `--delete`**, kein `rm`. Im Repo gelöschte Dateien werden auf dem Server **nicht** entfernt (nur eine
  Warnung).
- **Immer ausgeschlossen:** `*.jar`, `*.jar.disabled`, `*.db`, `*.sqlite`, `*.log`, Archive, Keys sowie
  `playerdata/`, `data/`, `cache/`, `world*/`. ⇒ **Spielerdaten/Datenbanken werden nie überschrieben.**
- **Fail-closed:** Fehlt ein Secret, bricht der Deploy ab, statt kaputte Configs auszurollen.
- Einen ganzen Plugin-Ordner gezielt neu ausrollen: Workflow manuell mit `replace_folder=<Plugin>`
  starten (Pfad-Traversal wird abgelehnt).

**Konsequenz für Änderungen:** Configs (`*.yml`, `*.conf`, `*.toml`, `*.sk`) editieren ist sicher und
wird deployt. Datenbank-/`data/`-Inhalte gehören **nicht** ins Repo und werden ohnehin nicht synchronisiert.

---

## Konventionen & Guardrails (verbindlich)

- **Keine Secrets** in `*.yml/.yaml/.toml/.conf/.properties/.env`. `__…__`-Dateien sind **Templates** –
  Platzhalter erhalten, nicht durch echte Werte ersetzen.
- **YAML:** 2 Leerzeichen Einrückung, keine Tabs; Strings mit Sonderzeichen in `'…'`; Arrays mit `-`.
- **Konsistenz über Server:** Erscheint ein Plugin auf mehreren Servern, Änderungen sinngemäß **überall**
  angleichen (server-spezifische Werte wie Ports/DB/Namen beachten).
- **Nicht-destruktive Navigation:** Nav-/Hotbar-Items auf Gameplay-Servern (skyblock/mining/survival)
  dürfen **nie** das Inventar leeren oder den Gamemode erzwingen – nur leere Slots füllen. Nur die
  **Lobby** leert das Inventar.
- **Economy:** CMI ist der Economy-Provider (Vault-Bridge). Geld in Skript per
  `execute console command "cmi money give %player% %amount%"`.
- **Ökonomie-DB:** CMI pro Server getrennt (`S1/S3/S5_CMI`) – niemals eine gemeinsame Tabelle.
- **TAB/Scoreboard** läuft **nur auf dem Proxy** (`proxy/plugins/tab/`) und gilt netzwerkweit; es
  konsumiert Backend-PlaceholderAPI-Werte über eine Bridge.
- **Broadcasts** sind Skript-Rotationen (`every N minutes: broadcast` in `help.sk`), kein CMI-Announcer.
- **Server-Neustarts (CMI, Europe/Berlin, gestaffelt):** Survival 03:55, Lobby 04:00, RPG/Mining 04:05,
  Skyblock 04:10 – zeitkritische Automationen davor legen.
- Struktur-/Deploy-/Secret-Änderungen ⇒ betroffene Root-Docs (`README.md`, `QUICKREF.md`, `SECRETS.md`)
  aktualisieren.

---

## Verwandte Referenzen

- **[`CROSS-PLUGIN.md`](CROSS-PLUGIN.md)** – Cross-Plugin-Features, Integrations-Backbone & Feature-Playbooks
  (Orchestrierung über mehrere Plugins/Agents)
- [`docs/ARCHITECTURE.md`](../ARCHITECTURE.md) · [`docs/OPERATIONS.md`](../OPERATIONS.md) ·
  [`docs/WORKFLOWS.md`](../WORKFLOWS.md) · [`docs/PLUGINS.md`](../PLUGINS.md)
- [`docs/infrastructure/`](../infrastructure/README.md) (DB, Plan, BlueMap, Backups, Ressourcen-Packs)
- [`.github/agents/`](../../.github/agents/README.md) – Custom Agents pro Plugin/Subsystem
- [`SECRETS.md`](../../SECRETS.md) · [`CONTRIBUTING.md`](../../CONTRIBUTING.md)
