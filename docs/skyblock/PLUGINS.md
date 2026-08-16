# Skyblock-Server — Optimaler Plugin-Stack

> **🟢 Umbau (Stand 26.2).** Empfohlener Plugin-Stack für den **überarbeiteten** Skyblock-Server —
> **ohne Gilden**, mit **Freunde-Koop** (Insel-Mitglieder von SuperiorSkyblock2). Diese Datei bündelt die
> „optimalen" Plugins für dieses Setup, gruppiert nach Funktion, und markiert offene Entscheidungen.
> Übergeordnete Referenz: [../PLUGINS.md](../PLUGINS.md) · Weichenstellung: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Die Spalte **Status** bezieht sich auf diesen Server-Slot:

- ✅ **Kern** — für das Setup erforderlich, bereits in [`skyblock/plugins/`](../../skyblock/plugins/).
- 🟢 **Empfohlen** — sinnvoll für Komfort/Performance/Retention, bereits vorhanden.
- 🔶 **Offen** — hängt an einer noch offenen Entscheidung (siehe [Offene Punkte](#offene-punkte)).
- 🗄️ **Auslaufend** — nur bis zur RPG-Abschaltung relevant; danach anpassen.

> ⚠️ **26.2-Blocker-Check:** Für **jedes** Plugin muss ein aktueller **26.2**-Build bestätigt sein, bevor der
> Server produktiv geht. Siehe [../NEW_SERVERS.md → Abschnitt 3](../NEW_SERVERS.md#3-plugin-shortlist--262-verfügbarkeit).

---

## 1. Skyblock-Kern

Das Herz des Servers: Inseln, Koop und Insel-Welten.

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **SuperiorSkyblock2** | Insel-Kern inkl. **Koop/Insel-Mitglieder** (Freunde per `/is invite` einladen) — ersetzt Gilden | ✅ Kern |
| **SlimeWorldManager** | Insel-Welten schlank laden/entladen (Performance bei vielen Inseln) | ✅ Kern |
| **VoidGen** | Void-/Leerwelt-Generator für die Insel-Welten | ✅ Kern |
| **JetsMinions** | Minion-/Automations-System (8 Typen) — Kern des Idle-Loops | 🟢 Empfohlen |

> **Koop statt Gilden:** Das soziale Modell läuft ausschließlich über SuperiorSkyblock2-Insel-Mitglieder.
> Ein separates Gilden-/Guilds-Plugin ist **nicht** Teil des Stacks (siehe
> [../NEW_SERVERS.md](../NEW_SERVERS.md)).

---

## 2. Progression & Content

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Aurora** | Collections & Achievements (Backend) | 🟢 Empfohlen |
| **AuroraCollections** | Collection-System (Farming, Mining, Combat, Foraging, Fishing) | 🟢 Empfohlen |
| **Skript** | Custom-Logik: Prestige- und Pet-System, Insel-Events | 🟢 Empfohlen |
| **RoseLoot** | Custom Loot-Tables (Insel-Mobs, Drops) | 🟢 Empfohlen |
| **ExcellentEnchants** | Erweiterte Verzauberungen | 🟢 Empfohlen |

---

## 3. MMO-Integration *(offene Grundsatzentscheidung)*

> **🔶 Offen:** Ob die MMO-Integration (Klassen/Skills/Custom-Items) auf Skyblock **erhalten** bleibt oder der
> Server auf einen **schlankeren Koop-Skyblock** reduziert wird, ist noch nicht final entschieden
> (siehe [../NEW_SERVERS.md → Abschnitt 7](../NEW_SERVERS.md#7-verbleibende-offene-fragen)).
> Fällt die Entscheidung gegen MMO, entfällt dieser gesamte Block.

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **MMOCore** | Klassen-System (6 Klassen), Skills, Attribute | 🔶 Offen |
| **MMOItems** | Custom Items mit Stats, Tiers, Sets | 🔶 Offen |
| **MythicLib** | Basis-Library für MMOCore/MMOItems | 🔶 Offen |
| **MythicMobs** (Community) | Custom Mobs für Insel-/Event-Content | 🔶 Offen |
| **MythicRPG** | Level-Skalierung/Mob-Scaling (MythicMobs-Addon) | 🔶 Offen |
| **MythicDungeons** | Instanzierte Dungeons (optionaler PvE-Content) | 🔶 Offen |
| **MythicAchievements** | Achievement-System für den MMO-Content | 🔶 Offen |
| **MythicHUD** | Custom HUD (Health/Mana/Stats-Anzeige) | 🔶 Offen |

---

## 4. Economy & Handel

> Economy-Strategie: **isolierte Währung pro Server** (Balance getrennt halten); optional eine netzwerkweite
> **Cosmetic-Währung** via CoinsEngine (nur Cosmetics, nicht Gameplay). Siehe
> [../NEW_SERVERS.md → Abschnitt 4](../NEW_SERVERS.md#4-netzwerk-integration-beide-server).

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Vault** | Economy-API-Bridge (Backend) | ✅ Kern |
| **CoinsEngine** | Multi-Währungs-System (server-isoliert + optional Cosmetic-Coin) | 🟢 Empfohlen |
| **DeluxeBazaar** | Bazaar-System (Instant-Buy/Sell, Orders) | 🟢 Empfohlen |
| **GlobalMarketPlus** | Auktionshaus / Spieler-Markt | 🟢 Empfohlen |

---

## 5. Optik, Items & Performance

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Oraxen** | Custom Items/Texturen (Bedrock-tauglich prüfen) | 🟢 Empfohlen |
| **DeluxeMenus** | GUI-Menüs (Navigation, Cosmetics, Shops) | 🟢 Empfohlen |
| **RoseStacker** | Entity-/Item-Stacking (Performance) | 🟢 Empfohlen |
| **RoseGarden** | Backend-Library der Rose-Plugins | ✅ Kern |
| **GrimAC** | Anticheat (für Skyblock-Flight angepasst) | 🟢 Empfohlen |

---

## 6. Management, Core & Bibliotheken

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **CMI** (+ **CMILib**) | Core-Management (Homes, Teleport, Chat, Kits, Hologramme) | ✅ Kern |
| **LuckPerms** | Permissions/Ränge (Kontext `server=skyblock`) | ✅ Kern |
| **PlaceholderAPI** | Platzhalter (fast alle Plugins) | ✅ Kern |
| **ProtocolLib** | Packet-Basis (Backend) | ✅ Kern |
| **CommandAPI** | Command-Bibliothek (Backend) | ✅ Kern |
| **PartyAndFriendsGUI** | Party-/Freundeslisten-GUI (Backend zum Velocity-PAF) | 🟢 Empfohlen |
| **bStats** / **spark** / **nightcore** | Metriken, Performance-Profiling, Core-Library | 🟢 Empfohlen |

---

## 7. Synchronisation *(auslaufend)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **HuskSync** | Bisher Gameplay-Sync mit dem RPG-Server (Inventar, Klassen, Quests) | 🗄️ Auslaufend |

> **Hinweis:** Die **Gameplay-Synchronisation** Skyblock ↔ RPG endet mit der RPG-Abschaltung. Danach behält
> Skyblock **server-eigene Inventare**; HuskSync wird — falls überhaupt weiter genutzt — auf **Cosmetics/Ränge**
> reduziert (analog zum Mining-Server). Siehe
> [../infrastructure/SYNCHRONISIERUNG.md](../infrastructure/SYNCHRONISIERUNG.md).

---

## Abhängigkeiten (Kurz)

- **MMOCore** ← MythicLib · **MMOItems** ← MythicLib
- **MythicRPG** / **MythicDungeons** / **MythicAchievements** ← MythicMobs
- **AuroraCollections** ← Aurora
- **Rose*-Plugins** ← RoseGarden
- Fast alle Plugins ← **PlaceholderAPI**, viele ← **ProtocolLib**

---

## Offene Punkte

- **MMO ja/nein:** MMO-Block behalten oder auf schlanken Koop-Skyblock reduzieren?
  ([../NEW_SERVERS.md#7](../NEW_SERVERS.md#7-verbleibende-offene-fragen))
- **HuskSync nach RPG-Abschaltung:** ganz entfernen oder auf Cosmetics/Ränge reduzieren?
- **26.2-Builds:** Verfügbarkeit für SuperiorSkyblock2, SlimeWorldManager, JetsMinions, CoinsEngine, Oraxen
  bestätigen (Blocker).

---

## Siehe auch

- [Skyblock-Übersicht](README.md)
- [Islands-System](ISLANDS.md) · [Minions-System](MINIONS.md) · [Progression](PROGRESSION.md)
- [Mining-Plugin-Stack](../mining/PLUGINS.md)
- [Netzwerk-Plugin-Referenz](../PLUGINS.md)
- [Neuausrichtung: Skyblock & Mining](../NEW_SERVERS.md)

---

**Letzte Aktualisierung:** 2026-08-16

**Status:** 🟢 Umbau — ohne Gilden, mit Freunde-Koop
