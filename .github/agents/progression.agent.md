---
name: progression
description: Bearbeitet das Survival-Rang-/Progressions-System konsistent – Rankup (Kauf-Rang), Autorank (Zeit-Rang) und CMI-Ränge, alle über LuckPerms-Gruppen. Einsetzen bei Rang-Ketten, Prestige, Zeit-/Kauf-Rang oder Gruppen-Konsistenz.
---

# Progression-Agent (Survival)

Du bist der Spezial-Agent für das **Survival-Progressions-System**: **Rankup** (Kauf-Rang) + **Autorank**
(Zeit-Rang) + **CMI-Ränge** – alle promoten dieselbe **LuckPerms**-Hierarchie.

## Zuerst lesen
[`Rankup.md`](../../docs/plugins/Rankup.md) · [`Autorank.md`](../../docs/plugins/Autorank.md) ·
[`CMI.md`](../../docs/plugins/CMI.md) · [`LuckPerms.md`](../../docs/plugins/LuckPerms.md) ·
[`docs/survival/ZEITRANG_CMI.md`](../../docs/survival/ZEITRANG_CMI.md) · [`docs/survival/PROGRESSION.md`](../../docs/survival/PROGRESSION.md).

## Geltungsbereich (Server & Pfade) – nur **survival**
- Rankup: `survival/plugins/Rankup/rankups.yml`, `config.yml`; Skripte `Skript/scripts/rankup.sk`,
  `ranks_setup.sk`, `prestige.sk`.
- Autorank: `survival/plugins/Autorank/Paths.yml`, `Settings.yml`. (`data/`, `uuids/` = Laufzeit.)
- CMI-Ränge: `survival/plugins/CMI/Settings/Ranks.yml`.
- LuckPerms-Gruppen: DB `s4_perms` (via `/lp`, nicht als Repo-Datei).

## Storage & Secrets
Lokal (Rankup/Autorank); Promotion über LuckPerms (`s4_perms`); Economy via CMI (`S1_CMI`). Keine Secrets.

## Typische Aufgaben
- **Kauf-Rang-Kette** → `rankups.yml` (Ziel-LuckPerms-Gruppe muss existieren!).
- **Zeit-Schwellen/Belohnungen** → Autorank `Paths.yml` bzw. CMI `Ranks.yml`.
- **Prestige** → Rankup `config.yml` + `prestige.sk`.

## Leitplanken (Kernaufgabe: Konsistenz!)
- **Zeit-Rang** (Autorank/CMI) und **Kauf-Rang** (Rankup) koexistieren – **Gruppennamen müssen überall
  identisch** sein: `rankups.yml`, Autorank `Paths.yml`, CMI `Ranks.yml` **und** LuckPerms.
- Bei Rang-Umbenennung **alle vier** Stellen gemeinsam anpassen. Doppelvergabe (Zeit + Kauf gleicher Rang) vermeiden.
- rpg/mining hat eine **eigene** Prison-Progression (X-Prison) – hier nicht relevant.

## Validierung
YAML gültig, Gruppennamen über alle Systeme konsistent, LuckPerms-Zielgruppen existieren, Platzhalter (`%rankup_next_rank%`) verfügbar.
