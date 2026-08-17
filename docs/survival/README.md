# Survival / Tycoon Server — Übersicht

Dokumentation für den Survival-Server mit integriertem Tycoon-Gamemode.

> **✅ Aktiv (26.2).** Dieser Server ist neben der Lobby einer der beiden **aktiv gepflegten** Server. Die Plugins wurden auf 26.2 aufgeräumt und neu übertragen.

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
| **Gamemode** | Survival + Tycoon (Generator-basiert) |
| **Economy** | Vault (CMI) — separat von MMO-Servern |
| **Datenbank** | MySQL/MariaDB (isoliert) |
| **Welten** | `tycoon` (Hauptwelt), `town` (Stadt), `freebuild` (Kreativ) |
| **Bedrock-Support** | Ja (Geyser/Floodgate) |

---

## Kern-Plugins

| Plugin | Funktion |
|--------|----------|
| **NextGens** | Generator-System (25 Tier × Sub-Levels) |
| **PlotSquared** | Plot-Claiming, Merging, Schematics |
| **Rankup** | 25-stufige Rang-Progression |
| **ShopGUIPlus** | Shop-GUI mit dynamischen Preisen |
| **GlobalMarketPlus** / **ChestShop** | Marktplatz & Spieler-Läden |
| **Skript** | Custom Tycoon-Logik (Sell Wand, Collector, Tutorial, etc.) |
| **Multiverse-Core** (+Inventories) | Welten `tycoon`/`town`/`freebuild` mit getrennten Inventaren |
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

| Datei | Funktion |
|-------|----------|
| `tycoon_logic.sk` | Kern-System: Preise, Sell Wand, Start, Rankup-Reset |
| `tycoon_collector.sk` | Chunk Collector (automatisches Item-Sammeln) |
| `tycoon_item.sk` | Tycoon Manager Clock-Item & GUI-Einstieg |
| `tycoon_setup.sk` | LuckPerms-Gruppen und Permissions Setup |
| `tycoon_tutorial.sk` | Onboarding-Tutorial für neue Spieler |
| `prestige.sk` | Prestige/Rebirth-System (10 Stufen) |
| `rankup.sk` | Rankup-Feier-Effekte (Partikel, Sounds) |
| `dynamic_market.sk` | Börse mit dynamischen Preisen |
| `achievements.sk` | 23 Achievements mit Belohnungen |
| `daily_rewards.sk` | Tägliche Login-Belohnungen |
| `gamble.sk` | Casino/Glücksspiel-System |
| `boss_events.sk` | Nitwit-Boss-Encounters |
| `weekly_events.sk` | 5 wöchentliche Event-Typen |
| `economy_monitor.sk` | Admin-Dashboard für Wirtschafts-Überwachung |

---

**Letzte Aktualisierung:** 2026-08-15
