# Rankup

**Rang-Aufstieg (Kauf) · nur survival · lokal**

## Zweck
Rankup stellt die kaufbare Rang-Leiter (A→B→…) mit Kosten/Anforderungen bereit und promotet Spieler
über LuckPerms. Ergänzt den zeitbasierten CMI-/Autorank-Aufstieg.

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/Rankup/`
- `config.yml` – globale Optionen (Economy-Anbindung, Prestige)
- `rankups.yml` – **die Rang-Kette** (Kosten, Anforderungen, Ziel-Gruppe/LuckPerms-Node)
- `locale/` – Nachrichten

Zugehörige Skripte: `survival/plugins/Skript/scripts/rankup.sk`, `ranks_setup.sk`, `prestige.sk`.

## Storage & Secrets
Lokal; Economy über **CMI/Vault** (`S1_CMI`), Promotion über **LuckPerms** (`s4_perms`). Keine Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Rang-Kosten/-Reihenfolge** → `rankups.yml` (Ziel-LuckPerms-Gruppe muss existieren!).
- Platzhalter `%rankup_next_rank%` u. a. werden von TAB/Skript genutzt – PAPI-Expansion sicherstellen.
- **Prestige** über `config.yml` + `prestige.sk`.

## Cross-Server / Gotchas
- **Survival-only**. Kauf-Rankup (Rankup) und **Zeit-Rang** (CMI `AutoRankUp` + Autorank) koexistieren –
  Gruppennamen in `rankups.yml`, CMI `Ranks.yml` und LuckPerms konsistent halten.
- rpg/mining hat eine **eigene** Prison-Rank-/Prestige-Logik (X-Prison), nicht dieses Plugin.

## Custom Agent
[`.github/agents/progression.agent.md`](../../.github/agents/progression.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [Autorank.md](Autorank.md) · [CMI.md](CMI.md) · [LuckPerms.md](LuckPerms.md) · [Skript.md](Skript.md)
