# SlimeWorldManager (SWM)

**Slime-Welt-Format · nur skyblock · Filestorage**

## Zweck
SlimeWorldManager lädt/speichert Welten im kompakten Slime-Format – Grundlage der Skyblock-Inselwelten
(schnelles Laden/Entladen vieler Inseln, geringer Speicher-Overhead).

## Wo (Server & Config-Pfade)
Nur **skyblock**: `skyblock/plugins/SlimeWorldManager/`
- `config.yml` – allgemeine Optionen
- `sources.yml` – **Storage-Quellen** (file/mysql/mongodb/redis)

## Storage & Secrets
**Filestorage aktiv** (`sources.yml`: file). `mysql`/`mongodb`/`redis` sind **deaktiviert** – keine
Deploy-Secrets. Welt-Dateien selbst sind Serverdaten (nicht im Repo).

## Wichtige Einstellungen / typische Aufgaben
- **Storage-Quelle** → `sources.yml` (aktuell `file`; Umstieg auf DB nur bewusst + mit Secret-Injektion).
- Zusammenspiel mit SuperiorSkyblock2 (SSB2 erzeugt/verwaltet die SlimeWorlds) – Quellname konsistent halten.

## Cross-Server / Gotchas
- **Skyblock-only**. Insel-Welten **nicht** in geteilte Multiverse-Inventory-Gruppen aufnehmen.
- Storage-Wechsel betrifft alle Inseln – vorher Backups (siehe `docs/infrastructure/BACKUPS.md`).

## Custom Agent
[`.github/agents/superiorskyblock2.agent.md`](../../.github/agents/superiorskyblock2.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [SuperiorSkyblock2.md](SuperiorSkyblock2.md) · [Multiverse.md](Multiverse.md)
