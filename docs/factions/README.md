# Factions-Server — Konzept (geplant)

> **🟡 Geplant (26.2).** Dieser Server ist einer der **zwei Nachfolger** für die auslaufenden MMO-Server
> (Skyblock & RPG). Er befindet sich in der **Konzept-/Aufbauphase**. Grundlage: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Social/PvP-Server mit **Gilden-/Team-getriebenem Land-Claiming und PvP**. Nutzt das bereits im Repo
vorhandene **CrossCraft-Guilds**-Plugin als soziale Klammer und deckt die in
[../PLANNING.md](../PLANNING.md) dokumentierte **PvP-Endgame-Lücke** ab.

---

## Rolle im Netzwerk

| Eigenschaft | Wert |
|-------------|------|
| **Slot** | Social/PvP (Kandidat C) |
| **Version** | Paper 26.2 (geplant) |
| **Ordner** | [`factions/`](../../factions/) |
| **Economy** | Server-isoliert (Balance getrennt vom Netzwerk) |
| **Datenbank** | Eigenes MariaDB-Schema (isoliert) + Redis (Guilds-Sync) |
| **Bedrock-Support** | Ja (Geyser/Floodgate) |
| **Sync (HuskSync)** | Cosmetics/Ränge; **keine** Gameplay-Inventare |

---

## Kern-Idee & USP

- **Gilden als Klammer:** [CrossCraft-Guilds](../../crosscraft-guilds/README.md) bindet Spieler in Teams,
  inkl. Gilden-Bank und Cross-Server-Chat.
- **Land-Claiming & War:** Fraktionen beanspruchen Land, verteidigen und erobern es (Warzone, Raids).
- **PvP-Endgame:** Kits, Crates, Ränge und saisonale Wipes/Ladder halten den Wettbewerb lebendig.
- **Abgrenzung zum Tycoon:** Voller PvP-Fokus mit Gruppenkonflikt statt friedlichem Generator-Grind.

---

## Kern-Systeme (MVP-Vorschlag)

| System | Zweck | MVP? |
|--------|-------|:----:|
| **Gilden/Teams** (CrossCraft-Guilds) | Soziale Struktur, Bank, Chat | ✅ |
| **Land-Claiming** | Territorien beanspruchen/verteidigen | ✅ |
| **Warzone + Spawn-Schutz** (WorldGuard) | Sichere Zonen, Kampfzonen | ✅ |
| **Anti-Cheat** | Faires PvP (kritisch!) | ✅ |
| **Combat-Tag** | Kein Combat-Log | ✅ |
| **KitPvP-Arena** | Schnelles PvP ohne Claim | ⬜ Phase 2 |
| **Crates/Ränge/Battle-Pass** | Retention, Belohnungen | ⬜ Phase 2 |
| **PvE-Welt-Events** (ggf. MythicMobs) | Abwechslung, Alt-Asset-Recycling | ⬜ Phase 2 |

---

## Plugin-Stack (Auszug)

Siehe vollständige Shortlist inkl. 26.2-Verfügbarkeit in
[../NEW_SERVERS.md → Abschnitt 3.3](../NEW_SERVERS.md#33-factions-spezifisch).

- **CrossCraft-Guilds** (Repo-intern; auf 26.2 anheben und testen)
- **Factions-Kern** (Claim/Land/War)
- **WorldGuard** (Schutzregionen) — bereits im Netzwerk
- **Anti-Cheat** (Vulcan-/Grim-Klasse) — **kritischer** Blocker
- **Combat-Tag/Logger**
- **KitPvP-/Arena-Plugin**, **Crates**, **Ranks**

---

## Offene Punkte

- **CrossCraft-Guilds auf 26.2:** aktuell Paper 1.21.4+ / Java 21 — muss für 26.2 kompiliert und getestet werden.
- **Anti-Cheat für 26.2** verifizieren (Blocker für einen PvP-Server).
- **Factions-Kern:** aktives, 26.2-taugliches Claim-Plugin auswählen.
- **Premium-Plugins:** Klären, ob MythicMobs Premium für PvE-Events weiterverwendet werden darf (siehe
  [../NEW_SERVERS.md → Abschnitt 7](../NEW_SERVERS.md#7-verbleibende-offene-fragen)).

---

**Verwandt:** [../NEW_SERVERS.md](../NEW_SERVERS.md) · [../PLANNING.md](../PLANNING.md) ·
[CrossCraft-Guilds](../../crosscraft-guilds/README.md)

**Letzte Aktualisierung:** 2026-08-15
