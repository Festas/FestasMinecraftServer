# CMI (+ CMILib)

**Core-Management · lobby / survival / skyblock / rpg(mining) · Economy-Provider · MySQL (pro Server)**

## Zweck
CMI ist das zentrale Management-Plugin auf allen Backends: Economy, Homes, Warps, Teleports, Kits,
Chat-Formatierung, AFK, Hologramme, PlayTime-Rewards, geplante Aufgaben (u. a. **Server-Neustarts**) und
die zeitbasierte Rang-Leiter (`AutoRankUp`). CMI ist der **Economy-Provider** des Netzwerks (via Vault).

## Wo (Server & Config-Pfade)
Gleiche Struktur auf jedem Backend: `<server>/plugins/CMI/`
- `config.yml` – globale CMI-Optionen
- `Settings/Modules.yml` – **Ein/Aus-Schalter aller CMI-Module** (Tablist, Economy, Chat, …)
- `Settings/DataBaseInfo.yml` – DB-Verbindung (Economy) – enthält Platzhalter
- `Settings/Schedules.yml` – geplante Konsolen-Aufgaben (**tägliche Neustarts**)
- `Settings/Ranks.yml` – Rang-Definitionen / `AutoRankUp`-Track (v. a. Survival)
- `Settings/Homes.yml`, `Settings/PlayTimeRewards.yml`, `Settings/Chat.yml`, `Settings/TabList.yml`, `Kits/`, `CustomText/`, `Translations/`

## Storage & Secrets
- **Economy in MySQL, pro Server getrennt:** `S1_CMI` (survival), `S3_CMI` (rpg/mining), `S5_CMI` (skyblock).
  Die **Lobby** hat keine Economy und bleibt auf SQLite.
- `Settings/DataBaseInfo.yml` enthält Platzhalter `__CMI_SURVIVAL_DB_USER__` / `__CMI_MINING_DB_USER__` /
  `__CMI_SKYBLOCK_DB_USER__` (+ `_PASSWORD`), injiziert aus `CMI_*_DB_ENV` in `deploy-<server>.yml`.
  Host/Port/DB-Name stehen (nicht geheim) fest in der Datei.
- ⚠️ **Nie dieselbe DB-Tabelle für zwei Server** – CMI warnt ausdrücklich davor. `security.key`,
  `moneyLog/`, `sellLogs/`, `Saves/` sind Laufzeitdaten (werden nicht deployt).

## Wichtige Einstellungen / typische Aufgaben
- **Neustartzeiten** ändern → `Settings/Schedules.yml` (Berlin, gestaffelt: Survival 03:55, Lobby 04:00,
  RPG/Mining 04:05, Skyblock 04:10).
- **Modul ein-/ausschalten** → `Settings/Modules.yml` (z. B. Tablist: lobby+skyblock aktiv,
  survival+rpg deaktiviert, weil der Proxy-TAB die Tabliste stellt).
- **Ränge / Zeitrang** → `Settings/Ranks.yml` (siehe auch [`docs/survival/ZEITRANG_CMI.md`](../survival/ZEITRANG_CMI.md)).
- **Economy geben (Skript/Konsole):** `cmi money give <player> <amount>`.
- Reload in-game: `/cmi reload`.

## Cross-Server / Gotchas
- Rang gilt netzwerkweit über LuckPerms (`s4_perms`); nur **Survival** betreibt die Zeitrang-Engine.
- Economy-Guthaben sind **nicht** serverübergreifend (bewusst pro Server).
- Änderungen an Modulen/Chat/Tablist mit dem Proxy-TAB abgleichen (Doppelbelegung vermeiden).

## Custom Agent
[`.github/agents/cmi.agent.md`](../../.github/agents/cmi.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [LuckPerms.md](LuckPerms.md) ·
[`docs/infrastructure/DATENBANKEN.md`](../infrastructure/DATENBANKEN.md) · [`docs/survival/ZEITRANG_CMI.md`](../survival/ZEITRANG_CMI.md)
