# Autorank

**Spielzeit-Tracking & Zeit-Ränge · nur survival · lokal (Flatfile)**

## Zweck
Autorank verfolgt die gespielte Zeit und vergibt zeitbasierte Aufstiege über Pfade („Paths"). Läuft
parallel zur CMI-`AutoRankUp`-Leiter und promotet über LuckPerms.

## Wo (Server & Config-Pfade)
Nur **survival**: `survival/plugins/Autorank/`
- `Settings.yml` – globale Optionen
- `Paths.yml` – **Aufstiegs-Pfade** (Anforderungen: Zeit, Ergebnisse: Rang/Command)
- `DefaultBehavior.yml`, `config.yml`; `data/`, `uuids/`, `logging/`, `backups/` = **Laufzeitdaten**

## Storage & Secrets
Lokal (Flatfile, `data/`). Keine DB, keine Secrets. `data/`/`uuids/` sind Spielerstände (nicht deployen).

## Wichtige Einstellungen / typische Aufgaben
- **Zeit-Schwellen & Belohnungen/Rangwechsel** → `Paths.yml` (Results promoten LuckPerms-Gruppe oder führen
  Command aus).
- Playtime-Quelle konsistent mit CMI (beide zählen Zeit) – Doppelvergabe von Rängen vermeiden.

## Cross-Server / Gotchas
- **Survival-only** und **aktiv** (Daten aktuell gepflegt). Zusammenspiel: **Autorank/CMI = Zeit-Rang**,
  **Rankup = Kauf-Rang** – alle promoten dieselbe LuckPerms-Hierarchie; Gruppennamen konsistent halten.
- Bei Rang-Namensänderungen: `Paths.yml`, CMI `Ranks.yml`, `rankups.yml` und LuckPerms gemeinsam anpassen.

## Custom Agent
[`.github/agents/progression.agent.md`](../../.github/agents/progression.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [Rankup.md](Rankup.md) · [CMI.md](CMI.md) · [LuckPerms.md](LuckPerms.md) · [`docs/survival/ZEITRANG_CMI.md`](../survival/ZEITRANG_CMI.md)
