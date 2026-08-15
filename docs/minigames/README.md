# Minigames-Server — Konzept (geplant)

> **🟡 Geplant (26.2).** Dieser Server ist einer der **zwei Nachfolger** für die auslaufenden MMO-Server
> (Skyblock & RPG). Er befindet sich in der **Konzept-/Aufbauphase**. Grundlage: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Casual-Server mit **rotierenden Minispielen** — der „schnelle Spaß"-Slot des Netzwerks. Nutzt das
Lobby-Routing optimal aus, hat eine niedrige Einstiegshürde und hohen Wiederspielwert.

---

## Rolle im Netzwerk

| Eigenschaft | Wert |
|-------------|------|
| **Slot** | Casual (Kandidat A) |
| **Version** | Paper 26.2 (geplant) |
| **Ordner** | [`minigames/`](../../minigames/) |
| **Economy** | Server-isoliert; optionale netzwerkweite Cosmetic-Währung |
| **Datenbank** | Eigenes MariaDB-Schema (isoliert) |
| **Bedrock-Support** | Ja (Geyser/Floodgate) — GUIs müssen Bedrock-tauglich sein |
| **Sync (HuskSync)** | Nur Cosmetics/Ränge, **keine** Gameplay-Inventare |

---

## Kern-Idee & USP

- **Rotierende Modi:** BedWars, SkyWars, Parkour, Spleef, Arcade-Runden.
- **Kurze Sessions:** Runden von wenigen Minuten → ideal für Casual-Spieler und Bedrock-Nutzer.
- **Abgrenzung zum Tycoon:** Kein Grind, keine persistente Economy-Progression im Kern-Loop — bewusst das
  Gegenteil des Survival/Tycoon-Erlebnisses.
- **Retention:** Cosmetics, Trails, Battle-Pass, tägliche Herausforderungen.

---

## Geplante Modi (MVP-Vorschlag)

| Modus | Typ | Gruppengröße | MVP? |
|-------|-----|--------------|:----:|
| **Parkour** | Solo, PvE | 1 | ✅ (schnellster MVP) |
| **Spleef** | PvP-Arena | 2–8 | ✅ |
| **BedWars** oder **SkyWars** | Team-PvP | 2–16 | ✅ (einen der beiden zuerst) |
| **Arcade-Rotation** | Gemischt | variabel | ⬜ Phase 2 |

---

## Plugin-Stack (Auszug)

Siehe vollständige Shortlist inkl. 26.2-Verfügbarkeit in
[../NEW_SERVERS.md → Abschnitt 3.2](../NEW_SERVERS.md#32-minigames-spezifisch).

- **Minigame-Framework** (etabliert, 26.2-tauglich — Blocker zuerst verifizieren)
- **Parkour**-Plugin
- **MultiArena/Queue**-System (Warteschlange, Arena-Rotation)
- **WorldEdit/FAWE** (Map-Reset) — bereits im Netzwerk
- **Multiverse-Core** (Map-Verwaltung)
- **Cosmetics/Battle-Pass**-Plugin

---

## Offene Punkte

- Konkretes Minigame-Framework für 26.2 festlegen (Blocker).
- Map-Beschaffung: eigene Builds vs. lizenzfreie Community-Maps.
- Battle-Pass-Umfang und Cosmetic-Währung (siehe offene Economy-Frage in
  [../NEW_SERVERS.md → Abschnitt 7](../NEW_SERVERS.md#7-verbleibende-offene-fragen)).

---

**Verwandt:** [../NEW_SERVERS.md](../NEW_SERVERS.md) · [../PLANNING.md](../PLANNING.md)

**Letzte Aktualisierung:** 2026-08-15
