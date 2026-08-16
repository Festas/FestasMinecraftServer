# Skyblock-Server - Übersicht

Dokumentation für den überarbeiteten Skyblock-Server — **ohne Gilden**, mit **Freunde-Koop**.

> **🟢 Umbau (Stand 26.2):** Dieser Server wird **überarbeitet und behalten**. Kernänderung: **keine Gilden**, stattdessen **Freunde-Koop** über die Insel-Mitglieder von SuperiorSkyblock2 (Freunde einladen und gemeinsam die Insel bauen). Ob die MMO-Integration (Klassen/MMOItems) erhalten bleibt, ist offen — siehe [NEW_SERVERS.md](../NEW_SERVERS.md#7-verbleibende-offene-fragen). Details unten, die auf die RPG-Synchronisation verweisen, gelten nur bis zur RPG-Abschaltung.

---

## Server-Informationen

**Server-Typ:** Paper 26.2  
**Hauptfokus:** Koop-Skyblock (Freunde einladen), optional mit MMO-Progression  
**Spieler-Kapazität:** 50-100 Spieler  
**Sozialmodell:** Insel-Mitglieder/Koop **statt Gilden**  
**Basis-Plugin:** SuperiorSkyblock2  
**Sprache:** Deutsch (DE)

---

## Kern-Features

### 1. Island-System (SuperiorSkyblock2)
- Persönliche oder **Koop-Inseln** — Freunde per `/is invite` einladen und gemeinsam bauen (ersetzt Gilden)
- Island-Levels und Upgrades
- Custom Island-Schematics
- Island-Warps und Besucher
- SlimeWorldManager für Island-Welten

### 2. Minion-System (JetsMinions)
- 8 Minion-Typen: Miner, Farmer, Fisher, Lumberjack, Slayer, Collector, Feeder, Seller
- Health-System (Minions müssen gefüttert/geheilt werden)
- Verknüpfte Kisten für automatische Lagerung
- Permission-basierte Placement-Limits

### 3. MMO-Integration
- Klassen-System (6 Klassen via MMOCore)
- Custom Items mit Stats (MMOItems)
- Progression durch Skyblock + MMO
- Skills und Fähigkeiten
- MythicMobs für Custom-Mobs

### 4. Collection-System (AuroraCollections)
- 5 Kategorien: Farming, Mining, Combat, Foraging, Fishing
- Ressourcen sammeln für Belohnungen
- Kategorie-Milestones mit Rewards

### 5. Prestige-System
- 10 Prestige-Stufen mit Titeln
- Passive Buffs ab Prestige 2+
- Leaderboard und Server-Broadcasts

### 6. Pet-System
- 6 Haustier-Typen mit passiven Boni
- Pet-Leveling durch Mob-Kills
- Spawn-Entitäten die dem Spieler folgen

---

## Plugin-Stack

### Core Skyblock
- **SuperiorSkyblock2** - Haupt-Skyblock-Plugin
- **JetsMinions** - Minion-System
- **VoidGen** - Void-World-Generator
- **SlimeWorldManager** - Island-Welten-Verwaltung

### MMO-Integration
- **MMOCore** - Klassen-System
- **MMOItems** - Custom Items
- **MythicMobs** (Community) - Custom Mobs
- **MythicLib** - Library für MMO-Plugins
- **MythicDungeons** - Instanzierte Dungeons
- **MythicAchievements** - Achievement-System
- **MythicHUD** - Custom HUD

### Progression
- **Aurora** - Collections & Achievements
- **AuroraCollections** - Collection-System
- **Skript** - Prestige- und Pet-System (Custom Scripts)

### Economy
- **CoinsEngine** - Multi-Währungs-System
- **DeluxeBazaar** - Bazaar-System
- **GlobalMarketPlus** - Auktionshaus
- **Vault** - Economy-API

### Sonstiges
- **GrimAC** - Anticheat (angepasst für Skyblock-Flight)
- **ExcellentEnchants** - Erweiterte Verzauberungen
- **RoseLoot** - Custom Loot-Tables
- **RoseStacker** - Entity-Stacking
- **Oraxen** - Custom Items/Texturen
- **DeluxeMenus** - GUI-Menüs (Cosmetics)

---

## Unterschiede zum RPG-Server

### Skyblock-spezifisch:
- Island-System (SuperiorSkyblock2)
- Minions (JetsMinions)
- Skyblock-spezifische Collections
- Keine Open-World-Zonen
- Prestige-System
- Pet-System

### Synchronisiert mit RPG:
- ✅ Klassen und Level
- ✅ Skills
- ✅ Inventar (HuskSync)
- ✅ Währungen

### Nicht auf Skyblock (nur RPG):
- ❌ BetonQuest (Quests)
- ❌ Citizens (NPCs)
- ❌ ModelEngine (3D-Modelle)
- ❌ MythicCrucible (Advanced Items)

---

## Datenbank-Konfiguration

- **MySQL/MariaDB:** `172.25.0.1:3306`
  - `s4_skyblock` - SuperiorSkyblock2-Daten
  - `s4_cmi` - CMI-Daten
  - `s4_mmocore` - MMOCore/RPG-Daten (geteilt mit RPG)
  - `s4_superior_islands` - SlimeWorld Island-Daten
- **Redis:** `172.18.0.1:6379` (Cache für HuskSync)

---

## Siehe auch

- [Islands-System](ISLANDS.md)
- [Minions-System](MINIONS.md)
- [Progression-System](PROGRESSION.md)
- [Mining-Server](../mining/README.md)
- [Neue Server: Skyblock & Mining](../NEW_SERVERS.md)

---

**Letzte Aktualisierung:** 2026-08-16

**Status:** 🟢 Umbau — ohne Gilden, mit Freunde-Koop
