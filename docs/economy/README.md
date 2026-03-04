# Economy-System - Übersicht

CoinsEngine Multi-Währungs-System für das MMO-Netzwerk (RPG + Skyblock).

---

## Währungen

Das Netzwerk nutzt 5 Währungen, die serverseitig synchronisiert werden:

| Währung | Symbol | Beschreibung | Handelbar |
|---------|--------|--------------|-----------|
| **Geld** | `$` | Hauptwährung, Vault-Integration | ✅ Ja |
| **Münzen** | `⛂` | Sekundärwährung, Tausch mit Geld möglich | ✅ Ja |
| **Tokens** | `✦` | Premium-/Event-Währung | ❌ Nein |
| **Quest-Punkte** | `✎` | Quest-Belohnungswährung | ❌ Nein |
| **Dungeon-Marken** | `⚔` | Dungeon-Belohnungswährung | ❌ Nein |

Detaillierte Informationen: [Währungen-System](WAEHRUNGEN.md)

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
├── engine.yml          # DB-Verbindung & Plugin-Einstellungen (nicht in Git!)
├── config.yml          # Allgemeine Plugin-Konfiguration
├── commands.yml        # Befehlsberechtigungen
└── currencies/
    ├── money.yml       # Geld (Hauptwährung)
    ├── coins.yml       # Münzen (Sekundärwährung)
    ├── tokens.yml      # Tokens (Premium/Events)
    ├── quest_points.yml # Quest-Punkte
    └── dungeon_marks.yml # Dungeon-Marken
```

> **Hinweis:** `engine.yml` enthält Datenbankzugangsdaten und ist über `.gitignore` ausgeschlossen.
> Nach dem Klonen des Repos muss `engine.yml` manuell mit den echten Zugangsdaten befüllt werden.

---

## Siehe auch

- [Währungen-System](WAEHRUNGEN.md)
- [Shop-System](SHOPS.md)

---

**Letzte Aktualisierung:** 2026-03-04
