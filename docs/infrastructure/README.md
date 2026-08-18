# Infrastruktur - Übersicht

Technische Infrastruktur für das MinecraftMMO-Netzwerk.

> **ℹ️ Stand 26.2:** Datenbanken und Backups gelten weiterhin für die aktiven Server (**Lobby**, **Survival**) und den Proxy. Wir gehen komplett den Mining-Server Weg. **HuskSync** synchronisiert dann nur noch **Cosmetics/Ränge** netzwerkweit.

---

## Komponenten

### Datenbanken *(aktiv)*
- MySQL/MariaDB - Hauptdatenbank
- Redis - Cache-System

### Backups *(aktiv)*
- Automatische Backup-Strategien
- Wiederherstellungs-Prozesse

---

## Siehe auch

- [BlueMap-Setup](BLUEMAP.md)
- [Datenbank-Schema](DATENBANKEN.md)
- [Backup-Strategien](BACKUPS.md)
- [Architektur-Dokumentation](../ARCHITECTURE.md)

---

**Letzte Aktualisierung:** 2026-08-15
