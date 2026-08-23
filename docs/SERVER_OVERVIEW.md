# MinecraftMMO Netzwerk — Vollständige Server-Übersicht

> Dieses Dokument enthält alle wichtigen Informationen über das MinecraftMMO Server-Netzwerk.

---

## Inhaltsverzeichnis

1. [Netzwerk-Überblick](#1-netzwerk-überblick)
2. [Server-Details](#2-server-details)
   - [Lobby](#21-lobby)
   - [Survival / Tycoon](#22-survival--tycoon)
   - [Mining — Neu](#23-mining--neu)
   - [Skyblock — Umbau](#24-skyblock--umbau-ohne-gilden-freunde-koop)
3. [Economy-System](#3-economy-system)
4. [Technische Infrastruktur](#4-technische-infrastruktur)
5. [Besonderheiten & USPs](#5-besonderheiten--alleinstellungsmerkmale)
6. [Website](#6-website)

---


## 1. Netzwerk-Überblick

**Server-IP:** `mc.festas-builds.com`  
**Minecraft-Version:** 26.2 (Paper)  
**Proxy:** Velocity  
**Bedrock-Support:** Ja (Geyser + Floodgate — Handy, Konsole, Windows 10 Edition)  
**Sprache:** Deutsch (primär)  
**Status:** Umstellung auf 26.2 — Fokus auf Lobby & Survival

> **⚠️ Umbruch:** Aktiv weiterentwickelt werden **Lobby** und **Survival** (Plugins frisch aufgeräumt). Dazu ein **überarbeiteter Skyblock** (ohne Gilden, mit Freunde-Koop) und ein neuer **Prison**-Server (Abbau-Zonen mit aufwertbaren Spitzhacken) — Weichenstellung, Bewertung und Plugin-Shortlist in [NEW_SERVERS.md](NEW_SERVERS.md). 

### Netzwerk-Architektur

```
Internet
    │
    ▼
Velocity Proxy  (mc.festas-builds.com)
    │
    ├──► Lobby          Haupt-Hub, Server-Navigation           [AKTIV]
    ├──► Survival       Standard Survival + Tycoon-Gamemode     [AKTIV]
    ├──► Skyblock       Koop-Skyblock, ohne Gilden              [NEU → Umbau]
    ├──► Prison         Abbau-Zonen, aufwertbare Spitzhacken    [NEU → Aufbau]

    Skyblock (überarbeitet) & Prison sind die neuen Server;  (Konzept: NEW_SERVERS.md)
```

Der aktive Fokus liegt auf dem **Survival/Tycoon**-Erlebnis und der **Lobby** als Hub. Hinzu kommen ein **überarbeiteter Skyblock** (Koop, ohne Gilden) und ein neuer **Prison**-Server. Der auslaufende **RPG**-Server kombinierte Konzepte aus **Hypixel Skyblock** (Progression, Minions, Collections) und **Wynncraft** (Klassen, Quests, Story) — diese Inhalte werden mit der Abschaltung abgelöst; ausgewählte Bausteine leben in den neuen Servern weiter.

---

## 2. Server-Details

### 2.1 Lobby

**Funktion:** Willkommens-Hub und Server-Navigation  
**Version:** Paper 26.2

#### Features
- **Server-Navigation** über DeluxeMenus-`server_selector` + Navigator-Kompass (Skript)
- **DeluxeMenus-GUIs**: Server-Selector, Regeln, Netzwerk-Guide
- **Doppelsprung** (kosmetisch, kein Gameplay-Element)
- Willkommensnachrichten bei erstem Join und Wiederkehr
- **Void-Fall-Schutz** (automatischer Teleport zum Spawn)
- Inventar-Schutz: Nur der Navigator-Kompass ist erlaubt
- Weltschutz via WorldGuard (kein Bauen, kein PvP, kein Schaden)
- **AFK-System**: Auto-Kick nach 15 Minuten Inaktivität
- **Hologramme** für Spielerzahlen und Server-Info (via CMI)

> **Hinweis:** Bei der 26.2-Aufräumaktion wurden FancyNpcs und DecentHolograms entfernt; die Navigation läuft jetzt rein über DeluxeMenus + Skript, Hologramme über CMI. Der `server_selector` verweist auf **Survival**, **Skyblock** und **Prison** — die Prison-Karte nutzt den **recycelten `prison`-Slot** (`[connect] prison`).

#### Besonderheiten
- Keine Gameplay-Elemente — reine Navigation
- Read-only World

---

### 2.2 Survival / Tycoon

**Funktion:** Klassisches Survival mit integriertem Generator-Tycoon-Gamemode  
**Version:** Paper 26.2  
**Welten:** `tycoon` (Hauptwelt), `town` (Stadtbereich), `freebuild` (Kreativbereich)

#### Tycoon-Gamemode

Der Tycoon-Gamemode ist das Herzstück des Survival-Servers. Spieler errichten Generatoren auf ihrem Plot, sammeln produzierte Ressourcen und verkaufen diese, um in Rängen aufzusteigen.

**Spielablauf:**
1. Join → Tutorial (automatisch für neue Spieler)
2. `/tycoon start` → Plot wird zugewiesen (kostenlos)
3. Generatoren platzieren → Items produzieren
4. Sell Wand nutzen → Items aus Containern verkaufen
5. Geld verdienen → `/rankup` → Plot-Reset → Neuer Rang + neuer Generator
6. 25 Ränge durchlaufen (Erde → Bedrock)
7. `/prestige` → Alles zurücksetzen für permanenten Sell-Bonus

#### 25 Ränge (Erde → Bedrock)

| # | Rang | Kosten |
|---|------|--------|
| 1 | Erde | Gratis (Start) |
| 2 | Stein | $5.000 |
| 3 | Kohle | $25.000 |
| 4 | Eisen | $75.000 |
| 5 | Kupfer | $200.000 |
| 6 | Gold | $500.000 |
| 7 | Redstone | $1.200.000 |
| 8 | Lapis | $3.000.000 |
| 9 | Smaragd | $7.500.000 |
| 10 | Diamant | $15.000.000 |
| 11 | Obsidian | $35.000.000 |
| 12 | Netherite | $75.000.000 |
| 13 | Tuff | $150.000.000 |
| 14 | Calcit | $300.000.000 |
| 15 | Diorit | $600.000.000 |
| 16 | Andesit | $1.000.000.000 |
| 17 | Granit | $2.000.000.000 |
| 18 | Basalt | $4.000.000.000 |
| 19 | Schwarzstein | $8.000.000.000 |
| 20 | Purpur | $15.000.000.000 |
| 21 | Endstein | $25.000.000.000 |
| 22 | Prismarin | $40.000.000.000 |
| 23 | Seelenerde | $65.000.000.000 |
| 24 | Magma | $100.000.000.000 |
| 25 | Bedrock | $175.000.000.000 |

#### Prestige-System (10 Stufen)

Nach Rang 25 kann man prestigen — alles wird zurückgesetzt, aber ein permanenter Sell-Bonus bleibt für immer.

| # | Name | Sell-Bonus |
|---|------|------------|
| 1 | Bronze | +10% |
| 2 | Silber | +20% |
| 3 | Gold | +35% |
| 4 | Platin | +50% |
| 5 | Smaragd | +70% |
| 6 | Saphir | +90% |
| 7 | Rubin | +115% |
| 8 | Amethyst | +140% |
| 9 | Diamant | +170% |
| 10 | Legende | +200% |

#### Kern-Systeme

- **Sell Wand** (Markt-Zepter): Rechtsklick auf Kiste/Fass/Shulker → Alle Items automatisch verkaufen
- **Chunk Collector**: Sammelt dropped Items im 20-Block-Radius alle 3 Sekunden automatisch
- **Dynamische Börse** (`/boerse`): Preise ändern sich basierend auf Angebot und Nachfrage
- **Casino/Glücksspiel** (`/gamble`): Mit täglichem Verlustlimit
- **Boss-Events**: Nitwit-Boss-Encounters mit zufälligen Spawns
- **Tägliche Belohnungen** (`/daily`): Streak-System, bricht ab bei >48h Pause
- **23 Achievements**: Kategorien: Verkäufe, Ränge, Casino, Streaks, Prestige, Bosse, Kontostand
- **Wöchentliche Events**: 5 verschiedene Event-Typen
- **13 Jobs** (Jobs-Plugin): Miner, Farmer, Hunter, Fisher, Builder, u.v.m.
- **Multi-Plot-System**: Progressive Plot-Limits je nach Rang (1→5 Plots + Prestige-Boni)
- **Plot-Merging**: Benachbarte Plots zusammenführen ($5 Mio.)
- **Zeitbasierte Rang-Leiter** (CMI-Rang-Engine): 14 Stufen (Lauch → MainCharacter) nach Spielzeit, netzwerkweit via LuckPerms; ersetzt Autorank ([Details](survival/ZEITRANG_CMI.md))

#### Wichtige Befehle

| Befehl | Beschreibung |
|--------|-------------|
| `/tycoon start` | Tycoon starten (Plot + Erde-Rang) |
| `/rankup` | Rang aufsteigen |
| `/prestige confirm` | Prestige durchführen |
| `/daily` | Tägliche Belohnung abholen |
| `/achievements` | Achievements anzeigen |
| `/boerse` | Dynamische Marktpreise |
| `/shop` | Shop öffnen |
| `/jobs` | Jobs verwalten |
| `/gamble` | Casino öffnen |
| `/tutorial` | Tutorial wiederholen |

---

### 2.3 Mining — *Neu*

> **🟡 Neu / Aufbauphase.** Einer der zwei neuen Server; **recycelt den `prison`-Slot** (Ordner `prison/`,
> Server-Name `prison`). Konzept: [prison/README.md](prison/README.md) · [NEW_SERVERS.md](NEW_SERVERS.md).

**Funktion:** Casual-Server mit **Abbau-Zonen** und aufwertbaren Spitzhacken
**Version:** Paper 26.2 (geplant)
**Slot:** Casual · **Zielgruppe:** Gelegenheitsspieler, Bedrock-freundlich

**Kern-Idee:**
- **Besondere Spitzhacke:** Immer stärkere Stufen bauen **mehr Blöcke auf einmal** ab (1×1 → 3×3 → …) und graben schneller
- **Freischaltbare Zonen:** Neue Abbau-Zonen mit anderen/wertvolleren Blöcken werden nach und nach freigeschaltet
- **Verkaufen → Aufwerten → Freischalten:** Blöcke verkaufen finanziert Spitzhacken-Upgrades und Zonen
- Retention über Ränge/Prestige, Cosmetics und Battle-Pass
- **Phase 2+ kompakt:** Rankups zwischen Minen, Prestige mit permanenten Boni, Black Market als rotierender
  Ressourcen-Sink, stufenweise Enchant-Freischaltung und weitere Battlepass-/Quest-Saisons als Endgame-Loop

**Abgrenzung:** Aktives Abbauen und Spitzhacken-Progression statt passiver Generatoren/Plots des Survival/Tycoon.

---

### 2.4 Skyblock — *Umbau (ohne Gilden, Freunde-Koop)*

> **🟢 Wird überarbeitet und behalten.** Kernänderung: **keine Gilden**, stattdessen **Freunde-Koop** über
> die Insel-Mitglieder von SuperiorSkyblock2. Konzept: [skyblock/README.md](skyblock/README.md) ·
> [NEW_SERVERS.md](NEW_SERVERS.md).

**Funktion:** Koop-Skyblock — Freunde einladen und gemeinsam die Insel bauen  
**Version:** Paper 26.2  
**Kapazität:** 50–100 Spieler  
**Sozialmodell:** Insel-Mitglieder/Koop statt Gilden

#### Island-System (SuperiorSkyblock2)
- **Persönliche oder Koop-Inseln** — Freunde per `/is invite` einladen
- Island-Level-System und Island-Upgrades
- Custom Island-Schematics
- Island-Warps für Besucher
- Top-Islands Leaderboard
- `/is create`, `/is invite`, `/is upgrades`, `/is level`

#### Minion-System (JetsMinions — 8 Typen)

| Minion | Funktion |
|--------|----------|
| **Miner** | Bergbau — produziert Erze |
| **Farmer** | Ernte — baut Pflanzen an und erntet |
| **Fisher** | Angeln — fängt Fische und Items |
| **Lumberjack** | Holzfällen — fällt Bäume |
| **Slayer** | Mob-Kampf — tötet Mobs |
| **Collector** | Sammelt dropped Items im Umkreis |
| **Feeder** | Heilt und füttert andere Minions |
| **Seller** | Verkauft Items automatisch |

Minions nutzen ein Health-System und müssen gepflegt werden. Verknüpfte Kisten für automatische Lagerung.

#### MMO-Integration *(optional — Umfang offen)*
- **6 Klassen** via MMOCore (aus dem RPG-Erbe)
- Custom Items mit Stats via MMOItems
- Custom Mobs via MythicMobs (Community Edition)
- Skills und Fähigkeiten
- Ob dieser MMO-Teil erhalten bleibt oder zugunsten eines schlankeren Koop-Skyblocks entfällt, ist offen
  (siehe [NEW_SERVERS.md → Abschnitt 7](NEW_SERVERS.md#7-verbleibende-offene-fragen))

#### Collection-System (AuroraCollections — 5 Kategorien)

| Kategorie | Beschreibung |
|-----------|-------------|
| **Farming** (16 Collections) | Pflanzen, Ernte-Items |
| **Prison** (8 Collections) | Erze, Steinarten |
| **Combat** (8 Collections) | Mob-Drops |
| **Foraging** (8 Collections) | Holzarten, Blätter |
| **Fishing** (4 Collections) | Fische, Meeresitems |

Jede Sammlung hat Milestones mit exklusiven Belohnungen.

#### Skyblock-Prestige-System
- 10 Prestige-Stufen mit Titeln
- Passive Buffs ab Prestige 2+
- Leaderboard und Server-Broadcasts

#### Pet-System
- 6 Haustier-Typen mit passiven Boni
- Pet-Leveling durch Mob-Kills
- Entitäten folgen dem Spieler

#### Wirtschaft
- **CoinsEngine Multi-Währungs-System**: Geld, Münzen, Tokens, Quest-Punkte, Dungeon-Marken
- **DeluxeBazaar**: Hypixel-Style Bazaar mit Instant-Buy/Sell und Order-System
- **GlobalMarketPlus**: Auktionshaus für Spieler-zu-Spieler-Handel

---

## 3. Economy-System

### Survival-Economy (separat, nicht verbunden mit MMO)

- **Hauptwährung:** Geld ($) via CMI/Vault
- Verdienen durch: Jobs, Shop-Verkäufe, Tycoon-Generatoren, Bosses, Achievements
- **ShopGUIPlus** für Item-Kauf und -Verkauf
- **GlobalMarketPlus** Auktionshaus für Spieler-Handel
- **Dynamische Börse** (`/boerse`): Preise reagieren auf Angebot und Nachfrage

## 4. Technische Infrastruktur

### Proxy (Velocity)
- **IP:** `mc.festas-builds.com`
- **Standard-Server:** Lobby
- **Plugins:** CMIV, ForceResourcepacks, Geyser-Velocity, LibertyBans, MiniMOTD, Plan, SkinsRestorer, TAB, VelocityScoreboardAPI

### Datenbanken
- **MariaDB** (`172.25.0.1:3306`): Spielerdaten, MMOCore, Economy, Quests, Achievements
- **Redis** (`172.18.0.1:6380`): HuskSync Session-Cache, temporäre Inventar-Daten

### Resourcepacks
- Custom Items (Oraxen), Custom Mobs (MythicMobs + ModelEngine), Custom Sounds und Texturen
- Automatischer Download beim Join (erzwungen via ForceResourcepacks)

### Bedrock-Support
- Geyser-Velocity + Floodgate
- Spieler auf Handy, Konsole und Windows 10 Edition können ohne Java-Account beitreten

### Daten-Synchronisation (HuskSync)

Zwischen aktiven Servern werden via HuskSync synchronisiert:
- ✅ **Cosmetics** (Trails, Partikel-Effekte, Skins)
- ✅ **Ränge** (LuckPerms-Gruppen, für einheitliche Anzeige im Netzwerk)
- ❌ Gameplay-Inventare — werden **nicht** synchronisiert (jeder Server hat eigene Inventare)

### Sicherheit
- **LibertyBans** (Velocity): Netzwerk-weite Bans, IP-Bans, Mutes, Temp-Bans
- **LuckPerms**: Fein-granulare Permissions, Rang-Tracks
- **WorldGuard**: TNT, Creeper, Wither, Feuer global blockiert (Survival)
- **GrimAC**: Anti-Cheat (Skyblock, angepasst für Flight)

### Netzwerk-Features (Proxy)
- **TAB**: Custom TAB-Liste und Scoreboard auf allen Servern
- **SkinsRestorer**: Custom Skins, auch im Offline-Mode
- **Plan**: Web-Dashboard mit Spieler-Statistiken und Server-Performance

---

## 5. Besonderheiten & Alleinstellungsmerkmale

### Was uns von anderen Servern unterscheidet

1. **Tycoon-Survival:** Generator-Tycoon-Gamemode mit 25 Rängen und 10 Prestige-Stufen auf einem klassischen Survival-Server
2. **Bedrock-First:** Volle Unterstützung für Handy, Konsole und PC-Edition via Geyser/Floodgate — kein Java-Account nötig
3. **Anti-Inflation-Economy:** Dynamische Börse reagiert auf Angebot und Nachfrage (Survival)
4. **Custom Content:** Custom Items und Texturen via Oraxen
5. **3D-Weltkarten:** BlueMap für Survival und Mining mit Live-Spieler-Markierung
6. **Skyblock mit Freunde-Koop:** Inseln gemeinsam mit Freunden bauen — ohne Gilden-Overhead
7. **Mining-Server:** Klarer Progressions-Loop mit aufwertbaren Spitzhacken und schrittweise freischaltbaren Abbau-Zonen

### Geplante Features (Roadmap)

- [ ] Anti-Cheat (Vulcan Premium) auf Survival
- [ ] Voting-System (NuVotifier + VotingPlugin)
- [ ] Hologramme über CMI (Lobby, Spielerzahlen, News)
- [ ] Crate/Key-System (CrazyCrates)
- [ ] Mining Launch: Black Market, Battle-Pass-Saisons, Endgame-Prestige
- [ ] Skyblock Launch: Plugin-Verifizierung 26.2, MVP-Scope, Collections

---

## 6. Website

Die Website (`mc.festas-builds.com`) ist bereits live. Quellcode und Dokumentation unter `website/` im Repository:

- [`website/README.md`](../website/README.md) — Features, lokale Entwicklung, Anpassungsguide
- [`website/DEPLOYMENT.md`](../website/DEPLOYMENT.md) — Docker-Deployment, CI/CD, Nginx

---

**Letzte Aktualisierung:** 2026-08-19
