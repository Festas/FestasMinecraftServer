# Cross-Plugin-Features & Integrations-Playbooks

**Feature-Orchestrierung über mehrere Plugins/Agents · verbindliche Referenz**

Die Custom Agents in [`.github/agents/`](../../.github/agents/README.md) sind fast alle **auf ein
Plugin bzw. Subsystem** ausgerichtet (z. B. `cmi`, `luckperms`, `oraxen`). Viele **Features** des
Netzwerks berühren aber **mehrere Plugins gleichzeitig** – ein neuer Rang, ein neues verkaufbares
Item oder ein neues Menü ist selten „ein Plugin". Dieses Dokument ist die **zentrale
Orchestrierungs- und Abhängigkeits-Referenz**: Es sagt, **welche Plugins/Agents ein Feature berührt**,
**in welcher Reihenfolge** und **was danach zu prüfen** ist.

> **Verhältnis zu den Einzeldocs:** Jede `docs/plugins/<Plugin>.md` beschreibt **ein** Plugin (Zweck,
> Pfade, Storage, Gotchas). Dieses Dokument verbindet sie zu **Feature-Ketten**. Bei Detailfragen zu
> einem Plugin gilt weiterhin dessen Einzeldoc als maßgebliche Quelle.

---

## So nutzt du dieses Dokument

1. **Feature statt Plugin denken:** Frage nicht „welches Plugin", sondern „welche Kette". Suche das
   passende **Feature-Playbook** in [Abschnitt 4](#4-feature-playbooks).
2. **Backbone verstehen:** Prüfe, an welche geteilten „Busse" das Feature andockt (Ränge, Economy,
   Platzhalter, Items/Pack, Routing) → [Abschnitt 1](#1-integrations-backbone-die-geteilten-busse).
3. **Agents zuordnen:** Über die [Zuständigkeitstabelle](#2-agent--plugin-zuständigkeit) den/die
   passenden Custom Agent(s) auswählen und **je Plugin einzeln** beauftragen.
4. **Server-Konsistenz wahren:** Erscheint ein beteiligtes Plugin auf mehreren Servern, die Änderung
   überall nachziehen (siehe „Server"-Spalte im jeweiligen Plugin-Doc).
5. **Am Ende validieren:** Die [Cross-Plugin-Validierungs-Checkliste](#5-cross-plugin-validierungs-checkliste)
   durchgehen und typische [Anti-Patterns](#6-anti-patterns--typische-bruchstellen) ausschließen.

> **Für den orchestrierenden (Eltern-)Agent:** Zwei Wege führen zum Ziel. **(A)** Der übergeordnete
> Agent orchestriert selbst: Er identifiziert anhand der Playbooks die beteiligten Plugins und ruft die
> jeweiligen **Ein-Plugin-Agents nacheinander** auf. **(B)** Ein **dedizierter Orchestrator-Agent**
> übernimmt das Dirigieren (delegiert via `agent`-Tool, ändert selbst keine Config) – Bauanleitung +
> fertige Vorlage in [`ORCHESTRATOR.md`](ORCHESTRATOR.md). Die Bündel-Agents (`progression`, `prison`,
> `proxy-network`, `superiorskyblock2`, `multiverse`, `survival-shops`, `land-claims`) decken bereits
> mehrere zusammengehörige Plugins ab – ihre Grenzen stehen in [Abschnitt 2](#2-agent--plugin-zuständigkeit).

---

## 1. Integrations-Backbone (die geteilten „Busse")

Fast jede Cross-Plugin-Kette dockt an einen dieser gemeinsamen Mechanismen an. Wer sie kennt, sieht
sofort, **welche zusätzlichen Plugins** ein Feature mitzieht.

| Bus | Zweck | „Besitzer" (Quelle) | Konsumenten (Beispiele) | Konsistenz-Regel |
|-----|-------|---------------------|--------------------------|------------------|
| **Ränge / Identität** | Gruppen, Prefix/Suffix, Rechte netzwerkweit | **LuckPerms** (`s4_perms`, geteilt) | CMI-Ränge, Rankup, Autorank, TAB, X-Prison-Ränge, DeluxeMenus-Requirements | **Gruppennamen überall identisch** halten |
| **Economy / Geld** | Kontostände, Kauf/Verkauf | **CMI** (Vault-Provider) | ShopGUIPlus, ChestShop, NextGens, Jobs, DeluxeBazaar, GlobalMarketPlus, HeadDatabase, Rankup, Skript | **Pro Server getrennt** (`S1/S3/S5_CMI`) – nie eine gemeinsame Tabelle |
| **Anzeige / Daten** | Platzhalter `%plugin_wert%` | **PlaceholderAPI** (je Backend) | TAB, DeluxeMenus, Skript, CMI-Chat, Scoreboards | Benötigte **Expansion muss auf dem Backend existieren**, sonst leerer Wert |
| **Items / Assets** | Custom-Items, Glyphs, Texturen, Resourcepack | **Oraxen** (je Backend) → **ForceResourcepacks** (Proxy) | DeluxeMenus-Icons, Shops, Skript, Kosmetik | **Item-IDs netzwerkweit konsistent**; nach Pack-Änderung **SHA-1-Hash** in ForceResourcepacks nachziehen |
| **Routing / Server** | Spielerwechsel zwischen Servern | **Velocity** (`proxy/velocity.toml`) | DeluxeMenus-Server-Selector, TAB-Bridge, Plan, Skript | Servernamen **exakt** wie in `velocity.toml` (`lobby/rpg/survival/skyblock`; öffentlich „mining" = Ordner `rpg`) |
| **Live-Sync** | Sofortige netzwerkweite Rang-Änderungen | **Redis** (`__REDIS_PASSWORD__`) | LuckPerms-Messaging | Messaging-Service muss aktiv sein, sonst greifen Rang-Änderungen verzögert |
| **Anzeige-Nametags/Scoreboard** | Tablist, Scoreboard, Nametags | **TAB** (nur Proxy) | zieht Backend-PAPI-Werte über Bridge | **Nur am Proxy**; CMI-Tablist nur auf lobby+skyblock aktiv (Doppelbelegung vermeiden) |

**Merksatz:** Ränge laufen über **LuckPerms**, Geld über **CMI** (pro Server), Anzeige über
**PlaceholderAPI→TAB**, Optik über **Oraxen→ForceResourcepacks**, Wechsel über **Velocity-Namen**.

---

## 2. Agent → Plugin-Zuständigkeit

Welcher Custom Agent welche Plugins bearbeitet (Basis für die Beauftragung). Bündel-Agents decken
mehrere zusammengehörige Plugins ab.

| Agent | Plugin(s) | Server |
|-------|-----------|--------|
| `cmi` | CMI (+ CMILib) | lobby / survival / skyblock / rpg |
| `luckperms` | LuckPerms | lobby / survival / skyblock / rpg |
| `placeholderapi` | PlaceholderAPI | lobby / survival / skyblock / rpg |
| `plan` | Plan | proxy / lobby / survival / rpg (skyblock = SQLite) |
| `skript` | Skript (Gameplay-Logik, Navigator, Broadcasts, Daily-Rewards) | lobby / survival / skyblock / rpg |
| `deluxemenus` | DeluxeMenus (GUI-Menüs, Server-Selector) | lobby / survival / skyblock / rpg |
| `oraxen` | Oraxen (Items, Glyphs, Resourcepack) | lobby / survival / skyblock / rpg |
| `tab` | TAB (Tablist/Scoreboard) | **nur Proxy** |
| `proxy-network` | MiniMOTD, SkinsRestorer, ForceResourcepacks | **nur Proxy** |
| `libertybans` | LibertyBans (Moderation) | **nur Proxy** |
| `progression` | Rankup, Autorank, CMI-Ränge (über LuckPerms) | nur survival |
| `survival-shops` | ShopGUIPlus, ChestShop | nur survival |
| `jobs` | Jobs Reborn | nur survival |
| `nextgens` | NextGens (Generatoren) | nur survival |
| `land-claims` | Lands, PlotSquared | nur survival |
| `worldguard` | WorldGuard (+ WorldEdit) | lobby / survival / rpg (nicht skyblock) |
| `multiverse` | Multiverse-Core, Multiverse-Inventories | survival / skyblock / rpg |
| `globalmarketplus` | GlobalMarketPlus (Spieler-Markt) | survival / skyblock / rpg |
| `superiorskyblock2` | SuperiorSkyblock2, SlimeWorldManager | nur skyblock |
| `deluxebazaar` | DeluxeBazaar (Instant-Shop) | nur skyblock |
| `prison` | X-Prison, XPrivateMines, XPrisonArmors, XRobots | nur rpg (mining) |
| `bluemap` | BlueMap (Live-Karte) | survival / rpg |

**Ohne dedizierten Agent (Utility/Bibliotheken):** HeadDatabase (läuft über `deluxemenus`/`survival-shops`),
LibsDisguises sowie die in [`libraries.md`](libraries.md) gesammelten Bibliotheken (CMILib, ProtocolLib,
Vault, FastAsyncWorldEdit, VoidGen, Chunky, VelocityScoreboardAPI, …).

---

## 3. Cross-Plugin-Abhängigkeitsmatrix

„Berührt / speist" = wenn du das linke Plugin änderst, prüfe die rechten Plugins mit.

| Plugin (Agent) | Hängt ab von / speist | Warum |
|----------------|------------------------|-------|
| CMI (`cmi`) | LuckPerms, TAB, Skript, alle Economy-Plugins | Economy-Provider + Rang-/Chat-/Tablist-Wechselwirkung |
| LuckPerms (`luckperms`) | CMI, Rankup, Autorank, X-Prison, TAB, DeluxeMenus | Gruppen = Ränge/Prefix/Requirements netzwerkweit |
| PlaceholderAPI (`placeholderapi`) | TAB, DeluxeMenus, Skript, CMI-Chat | Datenbrücke – fehlende Expansion ⇒ leere Werte |
| Oraxen (`oraxen`) | ForceResourcepacks, DeluxeMenus, Skript, Shops | Item-IDs/Icons + Pack-Hash |
| ForceResourcepacks (`proxy-network`) | Oraxen | Pack-URL/SHA-1 folgt dem Oraxen-Pack |
| TAB (`tab`) | PlaceholderAPI (Backends), Velocity-Namen, CMI-Tablist | konsumiert Backend-Platzhalter; Doppel-Tablist vermeiden |
| DeluxeMenus (`deluxemenus`) | PlaceholderAPI, Oraxen/HeadDatabase, LuckPerms, Velocity-Namen, Skript | Menüs nutzen Platzhalter, Icons, Rechte, Server-Routing |
| Rankup / Autorank (`progression`) | LuckPerms, CMI, TAB, PlaceholderAPI | promoten LuckPerms-Gruppen; Platzhalter für TAB |
| ShopGUIPlus / ChestShop (`survival-shops`) | CMI, NextGens, Jobs, Oraxen, WorldGuard | Economy-Anker; Balancing + Item-IDs + Shop-Zonen |
| NextGens (`nextgens`) | CMI, ShopGUIPlus, Jobs, DeluxeMenus, Oraxen, Skript | Passiv-Einkommen ins Survival-Balancing |
| Jobs (`jobs`) | CMI, ShopGUIPlus, NextGens, Rankup | Einkommensquelle ins Survival-Balancing |
| SuperiorSkyblock2 (`superiorskyblock2`) | SlimeWorldManager, CMI (`S5`), DeluxeBazaar, PlaceholderAPI, Multiverse-Inv. | Insel-Welten/Level/Progression + Shop |
| DeluxeBazaar (`deluxebazaar`) | CMI (`S5`), SuperiorSkyblock2, Oraxen, GlobalMarketPlus | Skyblock-Economy-Senke |
| X-Prison / XPrivateMines (`prison`) | CMI (`S3`), LuckPerms, TAB, PlaceholderAPI, XPrisonArmors/XRobots | Prison-eigene Progression/Currencies |
| GlobalMarketPlus (`globalmarketplus`) | CMI (jeweils `S1/S3/S5`) | Spieler-Markt pro Server; Gebühren-Balancing |
| Multiverse (`multiverse`) | WorldGuard, Skript, DeluxeMenus, SlimeWorldManager | Weltnamen/Inventar-Gruppen konsistent |
| WorldGuard (`worldguard`) | Multiverse, ChestShop | Regionen je Welt; Shop-Zonen |
| Plan (`plan`) | LuckPerms (gleiche MySQL-Instanz), Velocity-Namen, Website-Exporter | geteilte DB `s4_plan`; Server-Identität |
| Skript (`skript`) | CMI, PlaceholderAPI, DeluxeMenus, Oraxen, LuckPerms | Klebeschicht: Navigator, Trigger, Rewards, Broadcasts |

---

## 4. Feature-Playbooks

Jedes Playbook nennt **betroffene Plugins/Agents**, die **empfohlene Reihenfolge** und die **Prüfpunkte**.
Reihenfolge-Faustregel: **Fundament zuerst** (Rechte/Welt/Item), **Anzeige zuletzt** (Menü/TAB).

### 4.1 Neuer Kauf-Rang / VIP (survival)
- **Plugins/Agents:** `luckperms` → `cmi` → `progression` (Rankup) → `survival-shops` (Rabatte) →
  `placeholderapi` → `tab` → `deluxemenus`.
- **Reihenfolge:**
  1. `luckperms`: Gruppe anlegen (Prefix/Weight/Rechte, ggf. Track-Position).
  2. `cmi`: `Settings/Ranks.yml` – Rang/Prefix, falls über CMI dargestellt.
  3. `progression`: `survival/plugins/Rankup/rankups.yml` – Kette (Kosten/Anforderung → **Ziel-LuckPerms-Gruppe**).
  4. `survival-shops`: ggf. `pricemodifiers.yml` (VIP-Kauf/Verkauf-Rabatte).
  5. `placeholderapi`: Expansion für `%rankup_next_rank%` o. Ä. sicherstellen.
  6. `tab`: `groups.yml` (Sortierung/Prefix), Header/Footer-Platzhalter.
  7. `deluxemenus`: Button/Requirement (`permission:` = neue Gruppe).
- **Prüfen:** Gruppenname **identisch** in Rankup/CMI/LuckPerms/TAB; Kauf funktioniert (`S1_CMI`); TAB-Prefix erscheint.

### 4.2 Neuer Zeit-Rang (survival)
- **Plugins/Agents:** `progression` (Autorank + CMI `AutoRankUp`) → `luckperms` → `tab`.
- **Reihenfolge:** LuckPerms-Gruppe → `survival/plugins/Autorank/Paths.yml` (Zeit-Schwelle → Result promotet Gruppe)
  **und** CMI `Settings/Ranks.yml` (falls CMI-Leiter genutzt) → TAB-Anzeige.
- **Prüfen:** **Keine Doppelvergabe** (Autorank *und* CMI zählen Zeit); Gruppennamen in `Paths.yml`, `Ranks.yml`,
  `rankups.yml`, LuckPerms konsistent (siehe [`docs/survival/ZEITRANG_CMI.md`](../survival/ZEITRANG_CMI.md)).

### 4.3 Neuer Prison-Rang / Prestige / Rebirth (rpg/mining)
- **Plugins/Agents:** `prison` (X-Prison) → ggf. `luckperms` → `placeholderapi` → `tab`.
- **Reihenfolge:** `rpg/plugins/X-Prison/ranks.yml` / `prestiges.yml` / `rebirths.yml` (Kosten/Multiplier in
  `multipliers.yml`) → falls Rang eine LuckPerms-Gruppe für Rechte/Prefix setzt: `luckperms` → PAPI-Expansion →
  TAB-Platzhalter.
- **Prüfen:** Prison hat **eigene** Progression (nicht Rankup/Autorank!); Economy = `S3_CMI` + Prison-Currencies
  (Tokens/Gems); „rpg" = öffentlich „mining".

### 4.4 Neuer Generator / verkaufbares Item (survival-Economy)
- **Plugins/Agents:** `oraxen` (nur bei Custom-Item) → `nextgens` → `survival-shops` → `jobs` (falls Aktion
  entlohnt) → `deluxemenus` → `skript` → `cmi` (Balancing).
- **Reihenfolge:**
  1. `oraxen`: Item + ggf. Textur/Modell in `pack/`, dann Pack/Items reloaden (→ **4.7**).
  2. `nextgens`: `generators.yml`/`generators/` (Drop/Intervall/Upgrade), `worth.yml` (Verkaufswert), `shop.yml`.
  3. `survival-shops`: `shops/*.yml` Kauf-/Verkaufspreis + ggf. `pricemodifiers.yml`.
  4. `jobs`: `jobConfig.yml` (Payout/EXP der zugehörigen Aktion), falls relevant.
  5. `deluxemenus`: `gui_menus/generators.yml` (Eintrag) **in `config.yml` registrieren**.
  6. `skript`: falls `shopguiplus.sk`/Generator-Logik betroffen.
- **Prüfen:** Preise **konsistent** über NextGens `worth.yml` ↔ ShopGUIPlus ↔ Jobs ↔ Rankup-Kosten;
  Inflations-Check (siehe [`docs/CHECKLISTS.md`](../CHECKLISTS.md) §2); Wirkung nur `S1_CMI`.

### 4.5 Neues Skyblock-Shop-Item / Insel-Progression
- **Plugins/Agents:** `oraxen` (bei Custom-Item) → `deluxebazaar` → `superiorskyblock2` → `globalmarketplus`
  (falls handelbar) → `cmi` (`S5`).
- **Reihenfolge:** Oraxen-Item → `skyblock/plugins/DeluxeBazaar/items.yml`+`categories.yml` (Preise) →
  SSB2 `block-values.yml`/Missionen (falls es Insel-Level/Progression beeinflusst) → GlobalMarketPlus-Handelbarkeit.
- **Prüfen:** Economy = `S5_CMI` (getrennt von Survival `S1`); SSB2 nutzt **kein** WorldGuard; Insel-Welten sind
  SlimeWorlds – nicht in geteilte Multiverse-Inventar-Gruppe legen.

### 4.6 Neues GUI-Menü / Navigator-Eintrag
- **Plugins/Agents:** `deluxemenus` → `placeholderapi` → `oraxen`/HeadDatabase (Icons) → `luckperms` (Rechte) →
  `skript` (Öffnen/Navigator) → bei Lobby: Velocity-Namen (Server-Selector).
- **Reihenfolge:** `.yml` in `gui_menus/` anlegen **und in `config.yml` registrieren** → Requirements/Click-Actions
  mit vorhandenen PAPI-Platzhaltern → Icons (Oraxen-Glyph/HeadDatabase-ID muss existieren) → Öffnen via Skript/
  Navigator (**auf Gameplay-Servern nie Inventar leeren/Gamemode erzwingen** – nur leeren Slot füllen).
- **Prüfen:** Menü lädt (`/dm reload`); Platzhalter lösen auf; Rechte greifen; Server-Selector-Namen == `velocity.toml`.

### 4.7 Neues Custom-Item + Resourcepack-Asset
- **Plugins/Agents:** `oraxen` → `proxy-network` (ForceResourcepacks) → Konsumenten (`deluxemenus`, `survival-shops`,
  `skript`).
- **Reihenfolge:**
  1. `oraxen`: `items/` + Assets in `pack/`, dann `/oraxen reload pack` und `/oraxen reload items`.
  2. `proxy-network`: **SHA-1-Hash** (und ggf. URL) in `proxy/plugins/forceresourcepacks/config.yml` aktualisieren.
  3. Konsumenten: Item-ID dort referenzieren, **wo das Item auch existiert** (je Backend).
- **Prüfen:** Client lädt **neues** Pack (Hash gebumpt!); Item-ID auf allen referenzierenden Backends vorhanden;
  große Binärdateien in `pack/` bewusst/sparsam.

### 4.8 Neuer Warp
- **Plugins/Agents:** `cmi` (Warp-Definition) → `deluxemenus` (`warps.yml`-Eintrag) → `luckperms` (Rechte) →
  `oraxen`/HeadDatabase (Icon) → ggf. `worldguard` (Zielregion).
- **Prüfen:** Warp-Ziel-Welt existiert (Multiverse); Menü-Eintrag registriert; Recht gesetzt.

### 4.9 Neue Welt (survival / rpg)
- **Plugins/Agents:** `multiverse` (Core + Inventories) → `worldguard` → `skript`/`deluxemenus` (Referenzen).
- **Reihenfolge:** `/mv import|create` (→ `worlds.yml`) → Inventar-Trennung in `Multiverse-Inventories/groups.yml` →
  WorldGuard-`config.yml`/Regionen → Weltname überall konsistent (Skript/Menüs/Warps).
- **Prüfen:** Weltname identisch in allen Referenzen; Insel-/SlimeWorlds **nicht** in geteilte Inventar-Gruppe;
  `world*/`-Daten sind Serverstand (nicht ins Repo).

### 4.10 Neuer Server / Backend-Onboarding
- **Plugins/Agents:** Velocity/`proxy` → `proxy-network` → `plan` → `tab` → `luckperms` → `cmi` → Deploy/Secrets →
  Website-Exporter.
- **Reihenfolge:**
  1. `proxy/velocity.toml`: Server + Port (bestehend: lobby 25566, rpg 25567, survival 25568, skyblock 25569).
  2. `proxy-network`: MiniMOTD/ForceResourcepacks; `tab`: Bridge-Servername.
  3. `plan`: `ServerInfoFile.yml` + geteilte MySQL (`s4_plan`) **oder** SQLite (wie skyblock).
  4. `luckperms`: geteilte DB `s4_perms` (Platzhalter), Kontexte.
  5. `cmi`: eigene Economy-DB `Sx_CMI` (falls Economy) – **nie** dieselbe Tabelle wie ein anderer Server.
  6. **Deploy/Secrets:** `deploy-<server>.yml` + benötigte Secrets (`LUCKPERMS_DB_ENV`, `PLAN_DB_ENV`,
     `CMI_*_DB_ENV`, `REDIS_PASSWORD`, `VELOCITY_FORWARDING_SECRET`) – Injektion **per Python-Parse** (verbatim).
  7. Website/Exporter (`tools/plan-players-export/`, `tools/economy-export/`) falls öffentlich sichtbar.
- **Prüfen:** Servername **exakt** in velocity.toml/TAB/Plan gleich; Secrets vorhanden (Deploy ist **fail-closed**);
  siehe [`docs/NEW_SERVERS.md`](../NEW_SERVERS.md) für den vollständigen Ablauf.

### 4.11 Neue Währung
- **Prison-Currency (rpg):** `prison` → `rpg/plugins/X-Prison/currencies.yml` (+ `multipliers.yml`, AutoSell) →
  PAPI-Expansion → TAB, falls angezeigt.
- **Markt-Währung:** `globalmarketplus` → `Currency.yml` (pro Server, `S1/S3/S5`).
- **Prüfen:** Klarer Bezug zur richtigen Economy; keine Verwechslung mit CMI-Vault-Geld.

### 4.12 Neuer Broadcast / Daily-Reward
- **Plugins/Agents:** `skript` → `cmi` (Reward-Auszahlung) → `placeholderapi` (dynamische Werte).
- **Reihenfolge:** `help.sk` (Broadcast-Rotation `every N minutes`, rotierender Index `{<server>_bc::N}`) bzw.
  `daily_rewards.sk`; Geld per `execute console command "cmi money give %player% %amount%"`.
- **Prüfen:** **Kein** CMI-Announcer (nur Skript!); pro Server gespiegelt → inhaltlich konsistent nachziehen;
  Skript lädt (`/sk reload help`).

---

## 5. Cross-Plugin-Validierungs-Checkliste

Nach jeder Feature-Kette durchgehen (ergänzt die generischen Listen in [`docs/CHECKLISTS.md`](../CHECKLISTS.md)):

- [ ] **Ränge/Rechte:** LuckPerms-Gruppenname **identisch** in allen beteiligten Configs (Rankup/Autorank/CMI/TAB/X-Prison).
- [ ] **Economy:** Betrag/Preis wirkt auf die **richtige** Server-DB (`S1`/`S3`/`S5_CMI`); Inflations-/Balancing-Check gemacht.
- [ ] **Platzhalter:** Jeder neue `%…%` hat seine **PAPI-Expansion auf dem betroffenen Backend** (`/papi parse me %…%`).
- [ ] **Items/Pack:** Neue Oraxen-Item-IDs existieren auf **allen** referenzierenden Backends; **SHA-1-Hash** in
      ForceResourcepacks nach Pack-Änderung aktualisiert.
- [ ] **Menüs:** Neue DeluxeMenus in `config.yml` **registriert**; Requirements/Rechte gesetzt (`/dm reload`).
- [ ] **Routing:** Servernamen in Server-Selector/TAB/Plan == `proxy/velocity.toml` (rpg = „mining").
- [ ] **Tablist:** Keine Doppelbelegung (CMI-Tablist nur lobby+skyblock; Rest via Proxy-TAB).
- [ ] **Server-Konsistenz:** Multi-Server-Plugin überall gleich nachgezogen (oder bewusst server-spezifisch).
- [ ] **Secrets/Deploy:** Platzhalter-Tokens `__…__` unangetastet; keine echten Credentials committet; YAML gültig
      (2 Spaces, keine Tabs).
- [ ] **Navigation:** Gameplay-Server (survival/skyblock/rpg) leeren **nie** Inventar / erzwingen **nie** Gamemode.
- [ ] **Reload/Neustart:** Betroffene Plugins reloadbar; zeitkritische Automationen **vor** den Neustartfenstern
      (Survival 03:55 · Lobby 04:00 · RPG 04:05 · Skyblock 04:10, Europe/Berlin).

---

## 6. Anti-Patterns & typische Bruchstellen

| Symptom | Ursache | Fix |
|---------|---------|-----|
| Platzhalter im TAB bleibt leer | PAPI-Expansion fehlt auf dem Backend | Expansion dort ergänzen (`/papi ecloud download …`, `/papi reload`) |
| Menü öffnet nicht / fehlt | DeluxeMenu nicht in `config.yml` registriert | Menü registrieren, `/dm reload` |
| Rang wirkt nicht netzwerkweit / falscher Prefix | LuckPerms-Gruppenname weicht zwischen Rankup/Autorank/CMI/TAB ab | Gruppennamen **exakt** angleichen |
| Client sieht altes/falsches Custom-Item | Oraxen-Pack geändert, aber **SHA-1-Hash** in ForceResourcepacks nicht gebumpt | Hash (und ggf. URL) aktualisieren |
| Custom-Item auf einem Server unsichtbar | Oraxen-Item-ID existiert dort nicht | Item auf dem betroffenen Backend anlegen/synchronisieren |
| Server-Selector schickt ins Leere / doppelte Server | Servername ≠ `velocity.toml` | Namen exakt angleichen (rpg = „mining") |
| Doppelte/springende Tablist | CMI-Tablist + Proxy-TAB überlagern sich | CMI-Tablist nur lobby+skyblock lassen |
| Geld „verschwindet" beim Serverwechsel | Economy ist **bewusst pro Server** (`Sx_CMI`) | **Kein Bug** – so gewollt; nicht „reparieren" |
| Plan disabled / `mc-stats` liefert nginx 502 | `PLAN_DB_ENV` shell-gesourct statt Python-geparst → Passwort zerstört, oder Auth-Fehler | Injektion per Python-Parse (verbatim); Credentials prüfen |
| Insel-Inventar „vermischt" sich | SlimeWorld fälschlich in geteilte Multiverse-Inventories-Gruppe | Insel-Welten aus geteilten Inventar-Gruppen nehmen |
| Deploy bricht ab | Fehlendes Secret (fail-closed) **oder** Platzhalter-Token versehentlich entfernt | Secret setzen bzw. `__…__`-Token wiederherstellen |
| Broadcast doppelt/fehlt | Erwartung eines CMI-Announcers | Es gibt keinen – nur `Skript/help.sk`-Rotation pflegen |
| Rang doppelt vergeben (Zeit) | Autorank **und** CMI `AutoRankUp` zählen dieselbe Zeit | Nur eine Quelle promoten lassen, Gruppen konsistent |

---

## 7. Referenzen

- **[`ORCHESTRATOR.md`](ORCHESTRATOR.md)** – Main-/Orchestrator-Agent, der diese Playbooks ausführt
  (Delegation an die Ein-Plugin-/Bündel-Agents) – Bauanleitung + fertige Agent-Vorlage
- [README (Wissensbasis-Index)](README.md) · [`docs/CHECKLISTS.md`](../CHECKLISTS.md) ·
  [`docs/NEW_SERVERS.md`](../NEW_SERVERS.md) · [`docs/ARCHITECTURE.md`](../ARCHITECTURE.md)
- Backbone-Docs: [LuckPerms.md](LuckPerms.md) · [CMI.md](CMI.md) · [PlaceholderAPI.md](PlaceholderAPI.md) ·
  [TAB.md](TAB.md) · [Oraxen.md](Oraxen.md) · [ForceResourcepacks.md](ForceResourcepacks.md)
- Feature-Docs: [Rankup.md](Rankup.md) · [Autorank.md](Autorank.md) · [ShopGUIPlus.md](ShopGUIPlus.md) ·
  [NextGens.md](NextGens.md) · [Jobs.md](Jobs.md) · [DeluxeMenus.md](DeluxeMenus.md) · [Skript.md](Skript.md) ·
  [SuperiorSkyblock2.md](SuperiorSkyblock2.md) · [DeluxeBazaar.md](DeluxeBazaar.md) · [X-Prison.md](X-Prison.md) ·
  [Multiverse.md](Multiverse.md) · [WorldGuard.md](WorldGuard.md) · [Plan.md](Plan.md)
- Custom Agents: [`.github/agents/`](../../.github/agents/README.md) · Secrets: [`SECRETS.md`](../../SECRETS.md)
