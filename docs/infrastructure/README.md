# Infrastruktur - Übersicht

Technische Infrastruktur für das MinecraftMMO-Netzwerk.

> **ℹ️ Stand 26.2:** Datenbanken und Backups gelten weiterhin für die aktiven Server (**Lobby**, **Survival**) und den Proxy. Die **HuskSync-Synchronisation** ([SYNCHRONISIERUNG.md](SYNCHRONISIERUNG.md)) betraf ausschließlich die auslaufenden MMO-Server **Skyblock ↔ RPG** und entfällt mit deren Abschaltung — diese Doku ist insoweit **Archiv**.

---

## Komponenten

### Datenbanken *(aktiv)*
- MySQL/MariaDB - Hauptdatenbank
- Redis - Cache-System

### Synchronisation *(Archiv — nur Skyblock ↔ RPG)*
- HuskSync - Daten-Sync zwischen den auslaufenden MMO-Servern

### Backups *(aktiv)*
- Automatische Backup-Strategien
- Wiederherstellungs-Prozesse

---

## Siehe auch

- [Datenbank-Schema](DATENBANKEN.md)
- [Synchronisation](SYNCHRONISIERUNG.md) *(Archiv)*
- [Backup-Strategien](BACKUPS.md)
- [Architektur-Dokumentation](../ARCHITECTURE.md)

---

**Letzte Aktualisierung:** 2026-08-15
