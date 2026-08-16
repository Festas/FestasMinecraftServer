# Economy-System - Übersicht

CoinsEngine Multi-Währungs-System und Shop-Konfigurationen.

> **⚠️ Archiv (MMO — Skyblock & RPG):** Das CoinsEngine-Multi-Währungssystem gehört zu den auslaufenden MMO-Servern und wird mit deren Abschaltung ersetzt. Die **Survival**-Economy ist davon getrennt und läuft eigenständig über **Vault/CMI** (siehe [../survival/](../survival/)). Diese Doku wird nur noch als Referenz geführt.

---

## Währungen

Das MMO-Netzwerk (RPG & Skyblock) nutzt **5 Währungen**, die über beide Server synchronisiert werden:

1. **Geld ($)** — Hauptwährung (Vault-Economy)
2. **Münzen (⛂)** — Sekundärwährung für besondere Käufe
3. **Tokens (✦)** — Premium-Währung für exklusive Inhalte
4. **Quest-Punkte (✎)** — Quest-Belohnungen
5. **Dungeon-Marken (⚔)** — Dungeon-Belohnungen

> **Hinweis:** Der Survival-Server nutzt ein separates Economy-System und ist nicht mit dem MMO-Economy verbunden.

---

## Cross-Server-Synchronisation

RPG und Skyblock teilen sich **eine gemeinsame MySQL-Datenbank** (`CoinsEngine`).

- Alle Währungsdateien auf beiden Servern sind **identisch** (gleicher `Column_Name`, gleiche `Synchronized`-Einstellung)
- `Synchronized: true` ist auf allen Währungen gesetzt
- `Sync_Interval: 1` sorgt für sekündliche Synchronisation
- Guthaben bleibt beim Wechsel zwischen RPG ↔ Skyblock vollständig erhalten

---

## Konfigurationsstruktur

```
plugins/CoinsEngine/
├── engine.yml          # DB-Verbindung & Plugin-Einstellungen (Vorlage mit Platzhaltern)
├── config.yml          # Allgemeine Plugin-Konfiguration
├── commands.yml        # Befehlsberechtigungen
└── currencies/
    ├── money.yml       # Geld (Hauptwährung)
    ├── coins.yml       # Münzen (Sekundärwährung)
    ├── tokens.yml      # Tokens (Premium/Events)
    ├── quest_points.yml # Quest-Punkte
    └── dungeon_marks.yml # Dungeon-Marken
```

> **Hinweis:** Die `engine.yml`-Dateien im Repo enthalten nur Platzhalter (`CHANGE_ME`).
> Nach dem Klonen müssen die echten Datenbankzugangsdaten eingetragen werden.
> Lokale Änderungen an `engine.yml` werden durch `.gitignore` nicht mehr getrackt.

---

## Siehe auch

- [Währungen-System](WAEHRUNGEN.md) — Details zu allen Währungen, Sync und Befehlen
- **Shop-System** — geplant; noch nicht dokumentiert (Shops laufen aktuell über den Survival-Stack, siehe [../PLUGINS.md](../PLUGINS.md#survival-server-plugins))

---

**Letzte Aktualisierung:** 2026-03-04
