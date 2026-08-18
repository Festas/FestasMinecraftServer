# Survival Server — Übersicht

Dokumentation für den Survival-Server mit Town- und Freebuild-Welten.

> **✅ Aktiv (26.2).** Dieser Server ist neben der Lobby einer der beiden **aktiv gepflegten** Server. Die Plugins wurden auf 26.2 aufgeräumt und neu übertragen.

> **⚠️ Tycoon deaktiviert:** Der Tycoon-Gamemode existiert auf dem Survival-Server aktuell nicht mehr. Der Fokus liegt vollständig auf reinem Survival mit **Town** und **Freebuild**. Tycoon wird in Zukunft auf einem eigenen Server im Netzwerk verfügbar sein.

---

## Inhaltsverzeichnis

- [TYCOON.md](TYCOON.md) — Komplette Tycoon-Gamemode-Referenz (Mechaniken, Systeme, Befehle)
- [PLOTS.md](PLOTS.md) — Plot-System (Multi-Plot, Merging, Reset-Verhalten, Permissions)
- [PROGRESSION.md](PROGRESSION.md) — Rangaufstieg, Prestige, Economy-Fluss
- [PLUGINS.md](PLUGINS.md) — Fokussierter Plugin-Stack: Ist-Bestand + empfohlene Extra-Plugins (Anti-Cheat, Logging, Backups u. a.)

---

## Server-Kurzübersicht

| Eigenschaft | Wert |
|-------------|------|
| **Version** | Paper 26.2 |
| **Gamemode** | Survival (Town + Freebuild; Tycoon deaktiviert — kommt auf eigenem Server) |
| **Economy** | Vault (CMI) — separat von MMO-Servern |
| **Datenbank** | MySQL/MariaDB (isoliert) |
| **Welten** | `world` (Hauptwelt), `town` (Stadt), `freebuild` (Kreativ) |
| **Bedrock-Support** | Ja (Geyser/Floodgate) |

---

## Kern-Plugins

| Plugin | Funktion |
|--------|----------|
| **NextGens** | Generator-System (25 Tier × Sub-Levels) — aktuell deaktiviert (Tycoon kommt auf eigenem Server) |
| **PlotSquared** | Plot-Claiming, Merging, Schematics (`freebuild`-Welt) |
| **Lands** | Chunk-basiertes Land-Claiming in Survival-Welt — Griefing-Schutz, Claim-Blöcke, Nationen, GUI |
| **Rankup** | Rang-Progression |
| **ShopGUIPlus** | Shop-GUI mit dynamischen Preisen |
| **GlobalMarketPlus** / **ChestShop** | Marktplatz & Spieler-Läden |
| **Skript** | Custom-Logik (Sell Wand, Economy, Events etc.) |
| **Multiverse-Core** (+Inventories) | Welten `town`/`freebuild` mit getrennten Inventaren |
| **LuckPerms** | Rang-Permissions und Plot-Limits |
| **CMI** (+CMILib) | Economy, Teleport, Kits, Chat-Formatierung, Hologramme |
| **Jobs** | Berufe für zusätzliches Einkommen |
| **BlueMap** | 3D-Web-Karte |
| **WorldGuard** / **FAWE** / **AxiomPaper** | Regionen-Schutz & Building |

> Vollständige, ordnergenaue Plugin-Liste siehe [../PLUGINS.md](../PLUGINS.md#survival-server-plugins).
> Fokussierter Stack inkl. **empfohlener Extra-Plugins** (Anti-Cheat, Block-Logging, Backups, Auto-Restart):
> [PLUGINS.md](PLUGINS.md).
> Bei der 26.2-Aufräumaktion entfernt: **EssentialsX**, **GriefPrevention**, **PlayerPoints**, **DecentHolograms** (Basis-Befehle & Hologramme laufen jetzt über CMI).

---

## Skript-Dateien

<!-- Tycoon deaktiviert – kommt auf eigenem Server; tycoon_*.sk aktuell nicht aktiv
| Datei | Funktion |
|-------|----------|
| `tycoon_logic.sk` | Kern-System: Preise, Sell Wand, Start, Rankup-Reset |
| `tycoon_collector.sk` | Chunk Collector (automatisches Item-Sammeln) |
| `tycoon_item.sk` | Tycoon Manager Clock-Item & GUI-Einstieg |
| `tycoon_setup.sk` | LuckPerms-Gruppen und Permissions Setup |
| `tycoon_tutorial.sk` | Onboarding-Tutorial für neue Spieler |
-->

| Datei | Funktion |
|-------|----------|
| `prestige.sk` | Prestige/Rebirth-System (10 Stufen) |
| `rankup.sk` | Rankup-Feier-Effekte (Partikel, Sounds) |
| `ranks_setup.sk` | Setzt Tag (LuckPerms-Prefix) & Gewicht für alle Ränge — Befehl `/setupranktags` |
| `dynamic_market.sk` | Börse mit dynamischen Preisen |
| `achievements.sk` | 23 Achievements mit Belohnungen |
| `daily_rewards.sk` | Tägliche Login-Belohnungen |
| `gamble.sk` | Casino/Glücksspiel-System |
| `boss_events.sk` | Nitwit-Boss-Encounters |
| `weekly_events.sk` | 5 wöchentliche Event-Typen |
| `economy_monitor.sk` | Admin-Dashboard für Wirtschafts-Überwachung |

---

**Letzte Aktualisierung:** 2026-08-18
