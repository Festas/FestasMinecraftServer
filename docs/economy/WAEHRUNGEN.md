# Währungen - Economy-System

Vollständige Dokumentation aller Währungen im MMO-Netzwerk.

---

## Übersicht

| Währung | Symbol | Spalte (DB) | Dezimal | Handelbar | Tauschbar |
|---------|--------|-------------|---------|-----------|-----------|
| Geld | `$` | `money` | ✅ Ja | ✅ Ja | ✅ Ja |
| Münzen | `⛂` | `coins` | ❌ Nein | ✅ Ja | ✅ Ja |
| Tokens | `✦` | `tokens` | ❌ Nein | ❌ Nein | ❌ Nein |
| Quest-Punkte | `✎` | `quest_points` | ❌ Nein | ❌ Nein | ❌ Nein |
| Dungeon-Marken | `⚔` | `dungeon_marks` | ❌ Nein | ❌ Nein | ❌ Nein |

---

## Geld ($)

- **Name:** Geld
- **Symbol:** `$`
- **Datenbankfeld:** `money`
- **Beschreibung:** Hauptwährung des Netzwerks. Vault-Integration für kompatible Plugins.
- **Dezimalstellen:** Erlaubt (z.B. 1,50 $)
- **Handelbar:** Ja (`/money pay`)
- **Tauschkurs:** 1 Geld = 10 Münzen
- **Quellen:** Quests, Mobs, Handel, Admin-Vergabe

---

## Münzen (⛂)

- **Name:** Münzen
- **Symbol:** `⛂`
- **Datenbankfeld:** `coins`
- **Beschreibung:** Sekundärwährung. Keine Dezimalstellen.
- **Dezimalstellen:** Nicht erlaubt (nur ganze Zahlen)
- **Handelbar:** Ja (`/coins pay`)
- **Tauschkurs:** 10 Münzen = 1 Geld
- **Quellen:** Tägliche Aufgaben, Minispiele, Tausch mit Geld

---

## Tokens (✦)

- **Name:** Tokens
- **Symbol:** `✦`
- **Datenbankfeld:** `tokens`
- **Beschreibung:** Premium-Währung. Erhältlich durch Events und besondere Aktionen.
- **Dezimalstellen:** Nicht erlaubt
- **Handelbar:** Nein
- **Tauschbar:** Nein
- **Quellen:** Events, Specials, ggf. Shop

---

## Quest-Punkte (✎)

- **Name:** Quest-Punkte
- **Symbol:** `✎`
- **Datenbankfeld:** `quest_points`
- **Beschreibung:** Belohnungswährung für abgeschlossene Quests.
- **Dezimalstellen:** Nicht erlaubt
- **Handelbar:** Nein
- **Tauschbar:** Nein
- **Quellen:** Quest-Abschlüsse (BetonQuest)

---

## Dungeon-Marken (⚔)

- **Name:** Dungeon-Marken
- **Symbol:** `⚔`
- **Datenbankfeld:** `dungeon_marks`
- **Beschreibung:** Belohnungswährung für Dungeon-Erfolge.
- **Dezimalstellen:** Nicht erlaubt
- **Handelbar:** Nein
- **Tauschbar:** Nein
- **Quellen:** Dungeon-Bosse (MythicDungeons)

---

## Economy-Kreislauf

### Quellen (Einnahmen)
- Mob-Drops → Geld / Münzen
- Quest-Abschlüsse → Geld / Quest-Punkte
- Dungeon-Bosse → Dungeon-Marken / Geld
- Events → Tokens
- Tausch: Geld ↔ Münzen

### Senken (Ausgaben)
- NPC-Shops → Geld / Münzen
- Dungeon-Shop → Dungeon-Marken
- Quest-Shop → Quest-Punkte
- Token-Shop → Tokens
- Spieler-zu-Spieler-Handel (Geld & Münzen)

---

## Cross-Server-Synchronisation

Alle Währungen haben `Synchronized: true` und werden über eine gemeinsame MySQL-Datenbank zwischen RPG und Skyblock synchronisiert. Der `Sync_Interval: 1` sorgt für sekündliche Aktualisierung, sodass Guthaben beim Serverwechsel sofort verfügbar ist.

---

## Siehe auch

- [Economy Übersicht](README.md)
- [Shop-System](SHOPS.md)

---

**Letzte Aktualisierung:** 2026-03-04
