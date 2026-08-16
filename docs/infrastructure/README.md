# Infrastruktur - Übersicht

Technische Infrastruktur für das MinecraftMMO-Netzwerk.

> **ℹ️ Stand 26.2:** Datenbanken und Backups gelten weiterhin für die aktiven Server (**Lobby**, **Survival**) und den Proxy. Die in [SYNCHRONISIERUNG.md](SYNCHRONISIERUNG.md) beschriebene **volle Gameplay-Synchronisation** (Klassen, Inventar, Quests) betraf den **alten MMO-Verbund Skyblock ↔ RPG** und **entfällt** — diese Doku ist insoweit **Archiv**. In der Neuausrichtung ist **Skyblock server-isoliert** (überarbeitet, bleibt bestehen) und der **RPG-Slot wird zum Mining-Server recycelt**; **HuskSync** synchronisiert dann nur noch **Cosmetics/Ränge** netzwerkweit.

---

## Komponenten

### Datenbanken *(aktiv)*
- MySQL/MariaDB - Hauptdatenbank
- Redis - Cache-System

### Synchronisation *(Archiv — alte volle Sync Skyblock ↔ RPG)*
- HuskSync - volle Gameplay-Sync des alten MMO-Verbunds (entfällt; künftig nur Cosmetics/Ränge netzwerkweit)

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
