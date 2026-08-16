# Mining-Server — Optimaler Plugin-Stack

> **🟡 Geplant / Aufbau (Stand 26.2).** Empfohlener Plugin-Stack für den neuen **Mining**-Server (Abbau-Zonen
> mit **aufwertbaren Spitzhacken**). Der Server **recycelt den `rpg`-Slot** — der Ordner
> [`rpg/`](../../rpg/) enthält aktuell noch den **Alt-RPG-Stack**, der schrittweise durch die hier gelisteten
> Mining-Plugins ersetzt wird. Übergeordnete Referenz: [../PLUGINS.md](../PLUGINS.md) ·
> Weichenstellung: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Die Spalte **Status** ordnet jedes Plugin für den Umbau ein:

- ✅ **Kern** — für den Abbau-Loop erforderlich.
- ♻️ **Recycelt** — bereits in [`rpg/plugins/`](../../rpg/plugins/) vorhanden und für Mining weiterverwendbar.
- 🔶 **Auswählen/Prüfen** — Plugin bzw. 26.2-Build muss noch festgelegt/verifiziert werden (**Blocker**).
- 🧹 **Entfernen** — gehört zum Alt-RPG-Spielmodus und wird beim Umbau abgebaut.

> ⚠️ **26.2-Blocker-Check:** Für **jedes** Plugin muss ein aktueller **26.2**-Build bestätigt sein.
> Der **Mining-Kern** (Spitzhacken-Upgrades + Mehrblock-Abbau + Auto-Regeneration) ist der kritischste Punkt —
> ohne ihn ist der Loop nicht spielbar. Siehe
> [../NEW_SERVERS.md → Abschnitt 3.2](../NEW_SERVERS.md#32-mining-spezifisch).

---

## 1. Mining-Kern *(Blocker — zuerst festlegen)*

Der zentrale Loop: **besondere Spitzhacke → aufwerten → mehr Blöcke pro Schlag → neue Zonen freischalten**.

| Funktion | Zweck | Status |
|----------|-------|:------:|
| **Aufwertbare Spitzhacke** | Zentrales Werkzeug; Stufen erhöhen Abbau-Radius/-Menge und Tempo (1×1 → 3×3 → …) | 🔶 Auswählen |
| **Mehrblock-Abbau** | Ganze Muster/Adern auf einen Schlag (an Spitzhacken-Stufe gekoppelt) | 🔶 Auswählen |
| **Abbau-Zonen** | Mehrere Bereiche mit unterschiedlichen Block-Sets, freischaltbar | 🔶 Auswählen |
| **Auto-Regeneration** | Abgebaute Blöcke füllen sich wieder auf | 🔶 Auswählen |

> **Hinweis:** Diese vier Funktionen können je nach Plugin-Wahl **in einem** etablierten Mining-/Prison-Core
> gebündelt sein oder aus mehreren Plugins kombiniert werden. Erst nach der Auswahl eines **26.2-tauglichen,
> gepflegten** Kern-Plugins werden die weiteren Bausteine final verdrahtet.

---

## 2. Zonen, Welt & Schutz

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **WorldGuard** | Zonengrenzen, Schutz (kein Griefing), Region-Flags | ♻️ Recycelt |
| **FastAsyncWorldEdit (FAWE)** | Zonen bauen/zurücksetzen, Schematics | ♻️ Recycelt |
| **VoidGen** *(optional)* | Leerwelt-Generator für Zonen-Layouts | 🔶 Prüfen |
| **Chunky** *(optional)* | Chunk-Pre-Generierung (Performance) | 🔶 Prüfen |

---

## 3. Economy & Verkauf

> Economy ist **server-isoliert** (eigenes Schema/Währung). Optional eine netzwerkweite **Cosmetic-Währung**
> via CoinsEngine — nur für Cosmetics/Battle-Pass, nicht für Balance.

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Vault** | Economy-API-Bridge (Backend) | ♻️ Recycelt |
| **Shop / Auto-Sell** (Vault-kompatibel) | Blöcke verkaufen → Upgrades/Zonen finanzieren (Economy-Sink & -Quelle) | 🔶 Auswählen |
| **CoinsEngine** *(optional)* | Netzwerkweite Cosmetic-Währung | ♻️ Recycelt |

---

## 4. Retention & Cosmetics *(Phase 2)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Cosmetics / Battle-Pass** | Trails, Effekte, Belohnungen — Langzeit-Bindung | 🔶 Auswählen |
| **Ränge / Prestige** | Progression nach den Zonen (Skript oder Rankup-artig) | 🔶 Prüfen |

---

## 5. Optik, Items & UI

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Oraxen** | Custom Spitzhacken-Texturen/Items (Bedrock-tauglich prüfen) | ♻️ Recycelt |
| **DeluxeMenus** | GUIs: Zonen-Auswahl, Upgrade-Menü, Shop (Bedrock-Formulare testen) | ♻️ Recycelt |
| **Skript** | Custom-Logik: Zonen-Freischaltung, Upgrade-Flow, Events | ♻️ Recycelt |

---

## 6. Management, Core & Bibliotheken

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **CMI** (+ **CMILib**) | Core-Management (Spawn, Teleport, Chat, Hologramme) | ♻️ Recycelt |
| **LuckPerms** | Permissions/Ränge (Kontext `server=rpg` bleibt erhalten) | ♻️ Recycelt |
| **PlaceholderAPI** | Platzhalter (fast alle Plugins) | ♻️ Recycelt |
| **ProtocolLib** | Packet-Basis (Backend) | ♻️ Recycelt |
| **CommandAPI** | Command-Bibliothek (Backend) | ♻️ Recycelt |
| **PartyAndFriendsGUI** | Party-/Freundeslisten-GUI (Backend zum Velocity-PAF) | ♻️ Recycelt |

---

## 7. Betrieb, Bedrock & Performance

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **Floodgate** | Bedrock-Auth (Geyser/Floodgate) — GUIs müssen Bedrock-tauglich sein | ♻️ Recycelt |
| **GrimAC** | Anticheat | ♻️ Recycelt |
| **SimpleAutoRestart** | Geplante Server-Neustarts | ♻️ Recycelt |
| **spark** / **bStats** / **nightcore** | Performance-Profiling, Metriken, Core-Library | ♻️ Recycelt |

---

## 8. Synchronisation *(selektiv)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **HuskSync** | **Nur Cosmetics/Ränge** synchronisieren — **keine** Gameplay-Inventare | ♻️ Recycelt |

> Der Mining-Server hat **server-eigene Inventare**. HuskSync synchronisiert netzwerkweit ausschließlich
> Cosmetics und Ränge. Siehe [../NEW_SERVERS.md → Abschnitt 4](../NEW_SERVERS.md#4-netzwerk-integration-beide-server).

---

## 9. Beim Umbau zu entfernen (Alt-RPG-Spielmodus)

Diese Plugins gehören zum eingestellten RPG-Spielmodus und werden beim Umbau des `rpg/`-Slots **abgebaut**
(vorher Spielerdaten sichern, siehe [../NEW_SERVERS.md → Abschnitt 5](../NEW_SERVERS.md#5-rückbau-des-rpg-spielmodus-slot-recycling)):

| Plugin | Alt-Funktion | Status |
|--------|--------------|:------:|
| **MythicMobs**, **MythicDungeons**, **MythicRPG**, **MythicAchievements**, **MythicHUD** | RPG-Mobs/Dungeons/Scaling | 🧹 Entfernen |
| **MMOCore**, **MMOItems**, **MythicLib** | Klassen/Custom-Items | 🧹 Entfernen |
| **BetonQuest**, **Citizens** | Quests & NPCs | 🧹 Entfernen |
| **ModelEngine**, **LibsDisguises**, **PlayerParticles**, **DecentHolograms** | 3D-Models/Verkleidungen/Partikel/Hologramme (Hologramme via CMI) | 🧹 Entfernen |
| **Aurora**, **AuroraCollections** | RPG-Collections/Achievements | 🧹 Entfernen |
| **DeluxeBazaar**, **GlobalMarketPlus**, **PlayerPoints** | RPG-Handel/Punkte | 🧹 Entfernen |
| **RoseLoot**, **RoseStacker**, **RoseGarden** | Loot/Stacking/Lib (nur behalten, falls vom Mining-Kern gebraucht) | 🔶 Prüfen |
| **ExecutableItems**, **SCore**, **NBTAPI** | Item-Actions/Libs (nur bei Bedarf behalten) | 🔶 Prüfen |
| **BlueMap**, **AxiomPaper** | Web-Karte/Advanced-Building (optional für Zonen-Bau) | 🔶 Prüfen |
| **Plan** | Analytics (optional weiter nutzbar) | 🔶 Prüfen |

---

## Offene Punkte

- **Mining-Kern-Plugin** für 26.2 auswählen (Spitzhacken-Upgrades + Mehrblock-Abbau + Auto-Regeneration) —
  **Blocker Nr. 1**.
- **Shop/Auto-Sell**-Plugin und Verkaufspreis-Balancing festlegen.
- **Zonen-Design:** Anzahl Zonen zum Launch, Block-Sets, Freischalt-Kosten, Abbau-Muster pro Stufe.
- **Cosmetics/Battle-Pass** und optionale netzwerkweite Cosmetic-Währung
  ([../NEW_SERVERS.md#7](../NEW_SERVERS.md#7-verbleibende-offene-fragen)).
- **26.2-Builds** aller ♻️/🔶-Plugins bestätigen.

---

## Siehe auch

- [Mining-Übersicht & Konzept](README.md)
- [Skyblock-Plugin-Stack](../skyblock/PLUGINS.md)
- [Netzwerk-Plugin-Referenz](../PLUGINS.md)
- [Neuausrichtung: Skyblock & Mining](../NEW_SERVERS.md)

---

**Letzte Aktualisierung:** 2026-08-16

**Status:** 🟡 Geplant / Aufbau — recycelt den `rpg`-Slot
