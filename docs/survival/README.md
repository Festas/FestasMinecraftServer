# Survival / Tycoon Server — Übersicht

Dokumentation für den Survival-Server mit integriertem Tycoon-Gamemode.

---

## Inhaltsverzeichnis

- [TYCOON.md](TYCOON.md) — Komplette Tycoon-Gamemode-Referenz (Mechaniken, Systeme, Befehle)
- [PLOTS.md](PLOTS.md) — Plot-System (Multi-Plot, Merging, Reset-Verhalten, Permissions)
- [PROGRESSION.md](PROGRESSION.md) — Rangaufstieg, Prestige, Economy-Fluss

---

## Server-Kurzübersicht

| Eigenschaft | Wert |
|-------------|------|
| **Version** | Paper 1.21.1 |
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
| **Skript** | Custom Tycoon-Logik (Sell Wand, Collector, Tutorial, etc.) |
| **LuckPerms** | Rang-Permissions und Plot-Limits |
| **CMI** | Economy, Teleport, Kits, Chat-Formatierung |
| **Jobs** | 13 Berufe für zusätzliches Einkommen |

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

**Letzte Aktualisierung:** 2026-04-11
