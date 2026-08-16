# Mining-Server — Konzept (geplant)

> **🟡 Geplant (26.2).** Dieser Server ist einer der **zwei neuen Server** der Netzwerk-Neuausrichtung
> (neben dem überarbeiteten **Skyblock**). Er befindet sich in der **Konzept-/Aufbauphase**.
> Grundlage: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Casual-Server rund um **Abbau-Zonen**: Spieler bauen mit einer **besonderen Spitzhacke** Blöcke ab,
verkaufen sie und schalten damit **stärkere Spitzhacken** und **neue Zonen** frei. Der Kern-Loop ist bewusst
einfach, schnell verständlich und Bedrock-freundlich.

---

## Rolle im Netzwerk

| Eigenschaft | Wert |
|-------------|------|
| **Slot** | Casual |
| **Version** | Paper 26.2 (geplant) |
| **Ordner** | [`mining/`](../../mining/) |
| **Economy** | Server-isoliert; optionale netzwerkweite Cosmetic-Währung |
| **Datenbank** | Eigenes MariaDB-Schema (isoliert) |
| **Bedrock-Support** | Ja (Geyser/Floodgate) — GUIs müssen Bedrock-tauglich sein |
| **Sync (HuskSync)** | Nur Cosmetics/Ränge, **keine** Gameplay-Inventare |

---

## Kern-Idee & USP

- **Besondere Spitzhacke:** Jeder Spieler besitzt eine aufwertbare Spitzhacke als zentrales Werkzeug.
- **Stärker werdende Spitzhacken:** Höhere Stufen bauen **mehr Blöcke auf einmal** ab (z. B. 1×1 → 3×3 →
  größere Muster / ganze Adern) und graben schneller.
- **Freischaltbare Zonen:** Nach und nach werden **neue Abbau-Zonen** mit **anderen/wertvolleren Blöcken**
  freigeschaltet — sichtbare Progression und immer neue Ziele.
- **Verkaufen → Aufwerten → Freischalten:** Abgebaute Blöcke werden verkauft; der Erlös finanziert
  Spitzhacken-Upgrades und Zonen-Freischaltungen.
- **Abgrenzung zum Tycoon:** Fokus auf aktives Abbauen und Spitzhacken-Progression statt auf
  passiven Generatoren/Plots des Survival/Tycoon.

---

## Kern-Systeme (MVP-Vorschlag)

| System | Zweck | MVP? |
|--------|-------|:----:|
| **Abbau-Zonen** | Mehrere Bereiche mit unterschiedlichen Block-Sets | ✅ |
| **Aufwertbare Spitzhacke** | Stufen erhöhen Abbau-Radius/-Menge und Tempo | ✅ |
| **Zonen-Freischaltung** | Neue Zonen gegen Fortschritt/Währung öffnen | ✅ |
| **Verkauf/Economy** | Blöcke zu Geld machen (server-isoliert) | ✅ |
| **Auto-Regeneration der Zonen** | Abgebaute Blöcke füllen sich wieder auf | ✅ |
| **Ränge/Prestige** | Langzeit-Progression nach den Zonen | ⬜ Phase 2 |
| **Cosmetics/Battle-Pass** | Retention, Belohnungen | ⬜ Phase 2 |
| **Verzauberungen/Boosts der Spitzhacke** | Zusätzliche Effekte (Auto-Sell, Multiplier) | ⬜ Phase 2 |

---

## Plugin-Stack (Auszug)

Siehe vollständige Shortlist inkl. 26.2-Verfügbarkeit in
[../NEW_SERVERS.md → Abschnitt 3.2](../NEW_SERVERS.md#32-mining-spezifisch).

- **Mining-/Zonen-Kern** (custom Spitzhacken, Abbau-Regionen, Auto-Regeneration — Blocker zuerst verifizieren)
- **Region-/Schutz-Plugin** (WorldGuard) — bereits im Netzwerk (Zonengrenzen, kein Griefing)
- **WorldEdit/FAWE** (Zonen bauen/zurücksetzen) — bereits im Netzwerk
- **Economy** (Vault-kompatibel) + **Shop/Auto-Sell** (Blöcke verkaufen)
- **Cosmetics/Battle-Pass**-Plugin (Retention)

---

## Offene Punkte

- Konkretes 26.2-taugliches Plugin für **Spitzhacken-Upgrades + Mehrblock-Abbau** festlegen (Blocker).
- Zonen-Design: Anzahl Zonen zum Launch, Block-Sets, Freischalt-Kosten, Balancing der Verkaufspreise.
- Abbau-Muster pro Spitzhacken-Stufe (1×1 → 3×3 → …) und Auto-Regenerations-Tempo festlegen.
- Battle-Pass-Umfang und Cosmetic-Währung (siehe offene Economy-Frage in
  [../NEW_SERVERS.md → Abschnitt 7](../NEW_SERVERS.md#7-verbleibende-offene-fragen)).

---

**Verwandt:** [../NEW_SERVERS.md](../NEW_SERVERS.md) · [../PLANNING.md](../PLANNING.md) ·
[../skyblock/README.md](../skyblock/README.md)

**Letzte Aktualisierung:** 2026-08-16
