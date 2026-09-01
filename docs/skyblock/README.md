# Skyblock-Server - Übersicht

Dokumentation für den überarbeiteten Skyblock-Server — **ohne Gilden**, mit **Freunde-Koop**.

> **🟢 Umbau (Stand 26.2):** Aktueller Kern ist ein schlanker Skyblock-Stack mit
> SuperiorSkyblock2 und SlimeWorldManager; Gameplay-Daten bleiben lokal, zentrale Dienste laufen netzwerkweit.

---

## Server-Informationen

**Server-Typ:** Paper 26.2  
**Hauptfokus:** Koop-Skyblock (Freunde einladen)  
**Sozialmodell:** Insel-Mitglieder/Koop **statt Gilden**  
**Basis-Plugin:** SuperiorSkyblock2  
**Sprache:** Deutsch (DE)

---

## Kern-Features (Ist-Stand)

### 1. Insel-System (SuperiorSkyblock2)
- Persönliche oder **Koop-Inseln** via `/is invite`
- Insel-Level, Upgrades, Warps und Besucherfunktionen
- Gilden-System nicht im Einsatz

### 2. Insel-Welten (SlimeWorldManager)
- Dateibasierte Welten für Skyblock-Inseln
- Keine externe Skyblock-Datenbank erforderlich

### 3. Economy & Handel
- **CMI/Vault** als Economy-Basis auf dem Server
- **DeluxeBazaar** und **GlobalMarketPlus** für Handel/Auktionen

### 4. Netzwerk-Integration
- Ränge/Rechte netzwerkweit via **LuckPerms**
- Keine aktive Gameplay-Synchronisation mit dem Mining-Server

---

## Plugin-Stack (Ist-Bestand `skyblock/plugins/`)

- **Core:** SuperiorSkyblock2, SlimeWorldManager, CMI, CMILib, LuckPerms, Vault
- **Handel/UI:** DeluxeBazaar, GlobalMarketPlus, DeluxeMenus, PlaceholderAPI
- **Gameplay/Infra:** Skript, Oraxen, ProtocolLib, Plan, Multiverse-Core, Multiverse-Inventories, VoidGen
- **Tools:** faststats, spark, bStats, nightcore

> Nicht im aktuellen Stack enthalten: JetsMinions, MMOCore, MMOItems, MythicMobs,
> CoinsEngine, HuskSync, Geyser/Floodgate.

---

## Datenbank-Konfiguration

Skyblock-spezifische Kerndaten bleiben lokal:

- **SuperiorSkyblock2:** SQLite
- **SlimeWorldManager:** Datei-Storage (`file`)
- **Plan:** MySQL (`PLAN_DB_ENV`, Webserver auf Skyblock selbst deaktiviert)
- **CMI:** MySQL (`CMI_SKYBLOCK_DB_ENV`, eigene DB `S5_CMI`)

Zentrale, geteilte Netzwerkdienste:

- **LuckPerms:** gemeinsame MySQL/MariaDB + Redis-Messaging

---

## Siehe auch

- [Optimaler Plugin-Stack](PLUGINS.md)
- [Islands-System](ISLANDS.md)
- [Minions-System](MINIONS.md)
- [Progression-System](PROGRESSION.md)
- [Mining-Server](../prison/README.md)
- [Neue Server: Skyblock & Mining](../NEW_SERVERS.md)

---

**Letzte Aktualisierung:** 2026-09-01

**Status:** 🟢 Umbau — ohne Gilden, mit Freunde-Koop
