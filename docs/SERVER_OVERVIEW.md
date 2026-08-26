# Festas Builds — Netzwerk-Übersicht & Architektur

> **Single Source of Truth.** Dieses Dokument beschreibt den *tatsächlichen* Stand des
> Netzwerks (basierend auf den echten Server-Konfigurationen im Repo). Wenn andere Docs
> abweichen, gilt dieses Dokument.
>
> **Letzte Aktualisierung:** 2026-08-26

---

## Inhaltsverzeichnis

1. [Netzwerk-Überblick](#1-netzwerk-überblick)
2. [Server-Registry (kanonisch)](#2-server-registry-kanonisch)
3. [Netzwerk-Architektur](#3-netzwerk-architektur)
4. [Server-Details](#4-server-details)
5. [Economy](#5-economy)
6. [Technische Infrastruktur](#6-technische-infrastruktur)
7. [Alleinstellungsmerkmale](#7-alleinstellungsmerkmale)
8. [Roadmap](#8-roadmap)
9. [Website & Wiki](#9-website--wiki)

---

## 1. Netzwerk-Überblick

| | |
|---|---|
| **Server-IP** | `mc.festas-builds.com` (Port 25565) |
| **Minecraft-Version** | 26.2 (Paper) |
| **Proxy** | Velocity |
| **Plattform** | **Java Edition** (kein Bedrock/Geyser mehr im Stack) |
| **Sprache** | Deutsch |
| **Fokus** | **Survival als Hauptmodus** (Lands-Städte) |

**Aktueller Stand in einem Satz:** Der aktive Kern ist der **Survival**-Server (eigene Städte
per **Lands** claimen und ausbauen, dazu eine Freebuild-Kreativwelt). Die **Lobby** dient als
Hub. Zwei weitere Server sind **im Aufbau**: **Mining** (Prison-/Tycoon-artiges Minigame mit
eigener Währung) und **Skyblock** (Koop-Inseln, ohne Gilden).

> **Historie:** Ein früherer **RPG/MMO**-Server (Klassen, MMOCore/MMOItems/MythicMobs) wurde
> **eingestellt**. Dessen Server-Slot wird technisch für den neuen **Mining**-Server weiterverwendet
> (siehe Registry unten). Ein generatorbasierter **Tycoon**-Modus (NextGens, 25 Ränge) war auf
> Survival angedacht, ist aber **deaktiviert**.

---

## 2. Server-Registry (kanonisch)

Verbindliche Zuordnung von Anzeigename ↔ Technik. **Wichtig:** Der Mining-Server nutzt aus
historischen Gründen weiterhin den Velocity-Backend-Namen und Repo-Ordner `rpg` (Recycling des
alten RPG-Slots). In den Laufzeit-Logs erscheint er als `prison`.

| Anzeigename | Velocity-Backend | Repo-Ordner | Port | Status | Datenspeicher (spielrelevant) |
|-------------|------------------|-------------|------|--------|-------------------------------|
| **Lobby** | `lobby` | `lobby/` | 25566 | 🟢 Aktiv | – (nur Perms/Analytics) |
| **Survival** | `survival` | `survival/` | 25568 | 🟢 Aktiv · **Hauptmodus** | Lands/Jobs lokal + MariaDB (Perms/Analytics) |
| **Mining** | `rpg` | `rpg/` | 25567 | 🟡 Im Aufbau | X‑Prison lokal (H2) + MariaDB (Perms/Analytics) |
| **Skyblock** | `skyblock` | `skyblock/` | 25569 | 🟡 Im Aufbau | SuperiorSkyblock2 **SQLite** (lokal) |

> Quelle: `proxy/velocity.toml` (Backends/Ports) und `*/server.properties`.

---

## 3. Netzwerk-Architektur

```
                         [ Internet ]
                              │
                              ▼
                   ┌────────────────────┐
                   │   Velocity Proxy   │  mc.festas-builds.com
                   │  (Java, Port 25565)│
                   └─────────┬──────────┘
        ┌───────────────┬────┴─────┬───────────────┐
        ▼               ▼          ▼               ▼
   ┌─────────┐    ┌───────────┐ ┌────────┐   ┌───────────┐
   │  Lobby  │    │  Survival │ │ Mining │   │ Skyblock  │
   │  (Hub)  │    │ (Aktiv)   │ │(Aufbau)│   │ (Aufbau)  │
   └─────────┘    └───────────┘ └────────┘   └───────────┘
   Navigation     Lands-Städte   X‑Prison     SuperiorSkyblock2
   DeluxeMenus    Freebuild      Mining-Loop  Koop, ohne Gilden
                  Economy/Ränge  eigene Währung
```

**Netzwerkweit (Proxy):** LibertyBans (Bans/Mutes), TAB (Tablist/Scoreboard), SkinsRestorer,
MiniMOTD, ForceResourcePacks, Plan (Analytics), VelocityScoreboardAPI.

**Cross-Server-Sync:** Ränge & Rechte gelten netzwerkweit über **LuckPerms** (gemeinsame
MariaDB + Redis-Messaging). **Inventare sind pro Server getrennt** (kein globaler Inventar-Sync;
HuskSync ist nicht im Einsatz).

---

## 4. Server-Details

### 4.1 Lobby — Hub

**Funktion:** Willkommens-Hub und Server-Navigation. Keine Gameplay-Elemente, Welt schreibgeschützt.

- Server-Auswahl über **DeluxeMenus** (`server_selector`) + Navigator-Kompass (**Skript**)
- Willkommensnachrichten, Void-Fall-Schutz, Inventar-Schutz (nur Kompass)
- Weltschutz via **WorldGuard** (kein Bauen/PvP/Schaden)
- **Kern-Plugins:** CMI (+CMILib), DeluxeMenus, Skript, Oraxen, LuckPerms, PlaceholderAPI,
  WorldGuard, FastAsyncWorldEdit, Vault, ProtocolLib, CommandAPI

### 4.2 Survival — Hauptmodus (Lands & Städte)

**Funktion:** Klassisches Survival mit **Lands** als Herzstück: Chunks claimen, eigene **Stadt**
gründen, Freunde einladen, gemeinsam bauen — geschützt vor Griefing. Dazu eine **Freebuild**-
Kreativwelt (PlotSquared).

- **Lands:** Chunk-Claims, Rollen (Bürgermeister/Stellvertreter/Einwohner/Besucher), Nationen
- **Freebuild:** Kreativ-Plots (PlotSquared), WorldEdit für höhere Ränge, HeadDatabase
- **Economy:** CMI/Vault-Geld, **Jobs**, **Rankup** (Geld-Ränge), **ShopGUIPlus**,
  **GlobalMarketPlus** (Auktionshaus), **ChestShop** (Spieler-Läden)
- **Ränge:** zeitbasierte Rang-Leiter über die **CMI**-Rang-Engine (netzwerkweit via LuckPerms)
- **Karte:** **BlueMap** (`survival.festas-builds.com`)
- **Kern-Plugins:** CMI, Lands, PlotSquared, Jobs, Rankup, ShopGUIPlus, GlobalMarketPlus,
  ChestShop, Multiverse-Core/-Inventories, Skript, BlueMap, WorldGuard, Oraxen, HeadDatabase,
  LibsDisguises, AxiomPaper/FastAsyncWorldEdit, Chunky, RoseGarden, VoidGen

> **Hinweis:** **NextGens** (Generator-„Tycoon", 25 Ränge Erde→Bedrock) ist installiert, aber
> **deaktiviert** (die `tycoon_*`-Skripte sind abgeschaltet). Der Modus ist derzeit **nicht Teil
> des Live-Erlebnisses** und wird ggf. später auf einem eigenen Server umgesetzt.

### 4.3 Mining — Prison-/Tycoon-Minigame *(im Aufbau)*

**Funktion:** Eigenständiges Minigame mit **eigener Währung**. Abbau-Loop: in freischaltbaren
Zonen abbauen → verkaufen → Spitzhacke aufwerten → neue Zonen/Ränge freischalten.

- **X-Prison** als Kern: Minen/Zonen, Rangaufstieg, **Prestige/Rebirth**, **Enchants** &
  Pickaxe-Level (Spitzhacken-Upgrades), **AutoMiner**, **AutoSell**, Gangs, Battlepass, Quests,
  eigene Währungen (Tokens)
- Ergänzend: **XPrivateMines** (private Minen), **XPrisonArmors**, **XRobots**
- **Karte:** BlueMap (`mining.festas-builds.com`)
- **Kern-Plugins:** X-Prison, XPrivateMines, XPrisonArmors, XRobots, CMI, WorldGuard,
  GlobalMarketPlus, Multiverse-Core/-Inventories, BlueMap, Oraxen, Skript

> **Technik:** Backend/Ordner heißen weiterhin `rpg` (Recycling des alten RPG-Slots),
> Laufzeit-Logs laufen unter `prison`. Spielerseitig ist der Server „Mining".

### 4.4 Skyblock — Koop-Inseln *(im Aufbau)*

**Funktion:** Skyblock mit **Freunde-Koop** statt Gilden — Freunde per `/is invite` einladen und
gemeinsam die Insel bauen.

- **SuperiorSkyblock2:** Insel-Kern inkl. Koop-Mitglieder, Insel-Level & Upgrades, Warps,
  Top-Islands-Leaderboard
- **SlimeWorldManager:** performante, asynchrone Insel-Welten
- **DeluxeBazaar** (Hypixel-artiger Bazaar) & **GlobalMarketPlus** (Auktionshaus)
- **Speicher:** **lokale SQLite** (kein gemeinsamer Skyblock-DB-Server)
- **Kern-Plugins:** SuperiorSkyblock2, SlimeWorldManager, DeluxeBazaar, GlobalMarketPlus, CMI,
  Multiverse-Core/-Inventories, Skript, Oraxen

> **Nicht (mehr) im Einsatz:** Gilden, MMOCore-Klassen, MMOItems, MythicMobs, JetsMinions,
> CoinsEngine, AuroraCollections — der Fokus liegt auf einem schlanken Koop-Skyblock.

---

## 5. Economy

- **Survival:** Haupt-Geld (`$`) über CMI/Vault. Verdienst über Jobs, Shops (ShopGUIPlus),
  Spieler-Handel (GlobalMarketPlus/ChestShop). **Geld-Ränge** über Rankup.
- **Mining:** **komplett getrennte** Währung(en) (X-Prison-Tokens/Coins), nur auf dem Mining-Server.
- **Skyblock:** server-eigene Economy (CMI + DeluxeBazaar/Markt), lokal.

Es gibt **kein** serverübergreifendes Economy-Balancing — jeder Modus hat seinen eigenen
Wirtschaftskreislauf. Netzwerkweit synchron sind nur **Ränge/Rechte** (LuckPerms).

---

## 6. Technische Infrastruktur

### Proxy (Velocity)
Backends: `lobby`, `survival`, `rpg` (Mining), `skyblock` (siehe Registry). Default: Lobby.
Plugins: ForceResourcePacks, LibertyBans, MiniMOTD-Velocity, Plan, SkinsRestorer, TAB,
Velocity-Scoreboard-API.

### Datenbanken
- **MariaDB** — LuckPerms-Rechte (`s4_perms`) und Plan-Analytics (`s4_plan`).
- **Redis** — LuckPerms-Messaging & Cache.
- **Skyblock** — lokale **SQLite** (SuperiorSkyblock2). **Mining/X-Prison** — lokale H2.
- Details: [`infrastructure/DATENBANKEN.md`](infrastructure/DATENBANKEN.md).

### Monitoring & Karten
- **Plan** — Web-Dashboard (Spieler-/Performance-Statistiken).
- **BlueMap** — 3D-Live-Karten für **Survival** und **Mining**.
- **Velocity Exporter** — Prometheus-Metriken (siehe [`infrastructure/VELOCITY_EXPORTER.md`](infrastructure/VELOCITY_EXPORTER.md)).

### Sicherheit
- **LibertyBans** (netzwerkweite Bans/IP-Bans/Mutes), **LuckPerms** (Rechte/Rang-Tracks),
  **WorldGuard** (Weltschutz auf Lobby/Survival/Mining).

### Resourcepack
- Custom Items/Texturen via **Oraxen**, Auslieferung erzwungen über **ForceResourcePacks**
  (siehe [`infrastructure/RESOURCE_PACKS.md`](infrastructure/RESOURCE_PACKS.md)).

---

## 7. Alleinstellungsmerkmale

1. **Survival mit Städten:** Lands-Claims → eigene Stadt mit Freunden, komplett griefsicher.
2. **Klarer Netzwerk-Vertrag:** Ränge/Cosmetics gelten überall, Inventare bleiben pro Server
   getrennt — kein versehentlicher Itemverlust beim Serverwechsel.
3. **Mining als eigenes Tycoon-Minigame** mit separater Währung und Abbau-/Upgrade-Loop.
4. **Koop-Skyblock ohne Gilden-Overhead** — Inseln gemeinsam mit Freunden bauen.
5. **Live-3D-Karten** (BlueMap) direkt im Browser/Wiki.

---

## 8. Roadmap

- [ ] **Mining** finalisieren (Zonen, Enchants/Prestige-Balancing, Battlepass, Launch)
- [ ] **Skyblock** finalisieren (MVP-Scope: Inseln, Koop, Economy, Challenges)
- [ ] Anti-Cheat auf Survival evaluieren
- [ ] Voting-System (NuVotifier + VotingPlugin)
- [ ] Crate/Key-System evaluieren

---

## 9. Website & Wiki

Die Website (`mc.festas-builds.com`) ist live und enthält ein **Wiki**. Quellcode unter
[`website/`](../website/):

- [`website/README.md`](../website/README.md) — Features & lokale Entwicklung
- [`website/DEPLOYMENT.md`](../website/DEPLOYMENT.md) — Docker-Deployment, CI/CD, Nginx
- Wiki: Spielmodi, Ränge, Befehle, Regeln
