# Währungen-System - MinecraftMMO

Vollständige Übersicht aller Währungen im MMO-Netzwerk (RPG & Skyblock).

---

## Übersicht

| Währung | Symbol | Verdient durch | Handelbar | Sync |
|---------|--------|----------------|-----------|------|
| Geld ($) | $ | Mobs, Quests, Handel | ✅ Ja | ✅ RPG ↔ Skyblock |
| Münzen (⛂) | ⛂ | Mobs, Quests, Events | ✅ Ja | ✅ RPG ↔ Skyblock |
| Tokens (✦) | ✦ | Premium/Events | ❌ Nein | ✅ RPG ↔ Skyblock |
| Quest-Punkte (✎) | ✎ | Quests | ❌ Nein | ✅ RPG ↔ Skyblock |
| Dungeon-Marken (⚔) | ⚔ | Dungeons | ❌ Nein | ✅ RPG ↔ Skyblock |

---

## Geld ($) — Hauptwährung

Die primäre Währung des Servers. Wird als Vault-Economy verwendet.

- **Verdient durch:** Mobs töten, Items verkaufen, Quests, Jobs
- **Verwendet für:** NPC-Shops, Auktionshaus, Reparaturen, Verzauberungen
- **Spieler-Handel:** Ja (`/pay <spieler> <betrag>`)
- **Dezimal:** Ja (z.B. $12.50)
- **Startwert:** $0

---

## Münzen (⛂) — Sekundärwährung

Allgemeine Spielwährung für besondere Käufe.

- **Verdient durch:** Bosse besiegen, Events, Achievements, seltene Drops
- **Verwendet für:** Spezial-Shops, Kosmetik, Upgrades
- **Spieler-Handel:** Ja (`/coins pay <spieler> <betrag>`)
- **Dezimal:** Nein (nur ganze Zahlen)
- **Startwert:** 0
- **Tausch:** 10 Münzen = 1 Geld (Tauschrate: 0.1)

---

## Tokens (✦) — Premium-Währung

Seltene Währung für exklusive Inhalte.

- **Verdient durch:** Spezial-Events, Achievements, Serverweite Milestones
- **Verwendet für:** Exklusive Kosmetik, spezielle Pets, Titel
- **Spieler-Handel:** Nein
- **Dezimal:** Nein
- **Startwert:** 0

---

## Quest-Punkte (✎) — Quest-Belohnungen

Belohnungs-Währung aus dem Quest-System.

- **Verdient durch:** Haupt-Quests, Neben-Quests, tägliche Aufgaben
- **Verwendet für:** Quest-Shop (spezielle Items, Rezepte, Blueprints)
- **Spieler-Handel:** Nein
- **Dezimal:** Nein
- **Startwert:** 0

---

## Dungeon-Marken (⚔) — Dungeon-Währung

Belohnungs-Währung aus dem Dungeon-System.

- **Verdient durch:** Dungeon-Bosse, Dungeon-Abschluss, Dungeon-Challenges
- **Verwendet für:** Dungeon-Shop (hochwertige Ausrüstung, Dungeon-Keys)
- **Spieler-Handel:** Nein
- **Dezimal:** Nein
- **Startwert:** 0

---

## Cross-Server Synchronisation

Alle Währungen werden zwischen RPG- und Skyblock-Server synchronisiert via:
- **Datenbank:** Gemeinsame MySQL-Datenbank
- **Sync-Interval:** 1 Sekunde (Echtzeit)
- **Mechanismus:** CoinsEngine `Synchronized: true` + gleiche DB

Spieler haben auf beiden Servern denselben Kontostand.

**Wichtig:** Der Survival-Server hat ein separates Economy-System (Vault/EssentialsX) und ist NICHT mit dem MMO-Economy verbunden.

---

## Befehle

| Befehl | Beschreibung |
|--------|-------------|
| `/bal` oder `/balance` | Zeigt dein Geld-Guthaben |
| `/pay <spieler> <betrag>` | Sende Geld an einen Spieler |
| `/coins` | Zeigt dein Münzen-Guthaben |
| `/coins pay <spieler> <betrag>` | Sende Münzen an einen Spieler |
| `/tokens` | Zeigt dein Token-Guthaben |
| `/questpoints` oder `/qp` | Zeigt deine Quest-Punkte |
| `/dungeonmarks` oder `/dm` | Zeigt deine Dungeon-Marken |
| `/wallet` | Übersicht aller Währungen |
| `/baltop` | Geld-Rangliste |

---

**Letzte Aktualisierung:** 2026-03-04
