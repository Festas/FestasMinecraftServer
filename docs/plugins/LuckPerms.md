# LuckPerms

**Permissions & Ränge · lobby / survival / skyblock / rpg(mining) · MariaDB (netzwerkweit geteilt)**

## Zweck
LuckPerms verwaltet alle Permissions, Gruppen, Tracks und Meta-Werte (Prefix/Suffix, Weight) –
**netzwerkweit** über eine geteilte Datenbank. Live-Änderungen werden per Redis-Messaging an alle
Server gepusht.

## Wo (Server & Config-Pfade)
Gleiche Struktur auf jedem Backend: `<server>/plugins/LuckPerms/`
- `config.yml` – Storage, Messaging, Kontext-Optionen (die zentrale Datei)
- `contexts.json` – server-/weltbezogene Kontexte
- `translations/` – Sprachdateien

Die **Rang-/Gruppendaten selbst liegen in der DB** (`s4_perms`), nicht als Datei im Repo. Der
Web-Editor (`/lp editor`) ist der bevorzugte Weg, Gruppen/Nodes zu ändern.

## Storage & Secrets
- `storage-method: MariaDB`, Datenbank **`s4_perms`**, geteilt von Proxy + allen Backends.
- Platzhalter in `config.yml`: `__LUCKPERMS_DB_ADDRESS__`, `__LUCKPERMS_DB_DATABASE__`,
  `__LUCKPERMS_DB_USER__`, `__LUCKPERMS_DB_PASSWORD__` (injiziert aus `LUCKPERMS_DB_ENV`).
- Redis-Messaging-Passwort: `__REDIS_PASSWORD__` (aus `REDIS_PASSWORD`).
- Read-only-Website-Zugriff (Rang für Bestenliste) läuft über einen **separaten** SELECT-User
  (`LUCKPERMS_RO_DB_ENV`), nicht den RW-User.

## Wichtige Einstellungen / typische Aufgaben
- **Pool-Größe** ist bewusst fest (10 Verbindungen), da alle Server gleichzeitig zugreifen.
- **Messaging-Service** (`redis`) muss aktiv sein, damit Rang-Änderungen sofort netzwerkweit gelten.
- Rang setzen: `/lp user <player> group set <group>` bzw. Track: `/lp user <player> promote <track>`.
- Kontext-basierte Rechte (pro Server/Welt) → `contexts.json` + `config.yml`.

## Cross-Server / Gotchas
- **Eine geteilte DB** → Änderungen gelten überall. `config.yml` je Server nur bei server-spezifischen
  Kontexten unterschiedlich; DB-Zugang identisch (Platzhalter).
- Fällt die DB-Auth aus, brechen Permissions/Plan gleichzeitig (gleiche MariaDB-Instanz) – siehe
  Logs in `server-logs/`.
- CMI-Zeitrang, Rankup und Autorank promoten Spieler über **LuckPerms-Gruppen** – Gruppennamen
  konsistent halten.

## Custom Agent
[`.github/agents/luckperms.agent.md`](../../.github/agents/luckperms.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [CMI.md](CMI.md) · [Rankup.md](Rankup.md) ·
[`docs/infrastructure/DATENBANKEN.md`](../infrastructure/DATENBANKEN.md)
