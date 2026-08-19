# Infrastruktur - Übersicht

Technische Infrastruktur für das MinecraftMMO-Netzwerk.

> **ℹ️ Stand 26.2:** Datenbanken und Backups gelten weiterhin für die aktiven Server (**Lobby**, **Survival**) und den Proxy. Wir gehen komplett den Mining-Server Weg. **HuskSync** synchronisiert dann nur noch **Cosmetics/Ränge** netzwerkweit.

---

## Komponenten

### Datenbanken *(aktiv)*
- MySQL/MariaDB - Hauptdatenbank
- Redis - Cache-System

### Monitoring *(geplant / aktiv vorbereitbar)*
- Velocity Proxy Exporter - internes Live-Monitoring für Proxy, Backends und Logins
- Plan - Web-Analytics und Langzeitstatistiken

### Backups *(aktiv)*
- Automatische Backup-Strategien
- Wiederherstellungs-Prozesse

---

## Siehe auch

- [BlueMap-Setup](BLUEMAP.md)
- [Plan-Setup (mc-stats.festas-builds.com)](PLAN.md)
- [Datenbank-Schema](DATENBANKEN.md)
- [Backup-Strategien](BACKUPS.md)
- [Velocity Exporter](VELOCITY_EXPORTER.md)
- [Architektur-Dokumentation](../ARCHITECTURE.md)

---

**Letzte Aktualisierung:** 2026-08-19
