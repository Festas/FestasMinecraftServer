# Mining-Server — Optimaler Plugin-Stack

> **🟡 Geplant / Aufbau (Stand 26.2).** Empfohlener Plugin-Stack für den neuen **Mining**-Server (Abbau-Zonen
> mit **aufwertbaren Spitzhacken**). Der Server **recycelt den `prison`-Slot** — der Ordner
> [`prison/`](../../prison/) enthält aktuell noch den **Alt-Prison-Stack**, der schrittweise durch die hier gelisteten
> Mining-Plugins ersetzt wird. Übergeordnete Referenz: [../PLUGINS.md](../PLUGINS.md) ·
> Weichenstellung: [../NEW_SERVERS.md](../NEW_SERVERS.md).

Die Spalte **Status** ordnet jedes Plugin für den Umbau ein:

- ✅ **Kern** — für den Abbau-Loop erforderlich.
- ♻️ **Recycelt** — bereits in [`prison/plugins/`](../../prison/plugins/) vorhanden und für Mining weiterverwendbar.
- 🔶 **Auswählen/Prüfen** — Plugin bzw. 26.2-Build muss noch festgelegt/verifiziert werden (**Blocker**).
- 🧹 **Entfernen** — gehört zum Alt-Prison-Spielmodus und wird beim Umbau abgebaut.

> ⚠️ **26.2-Blocker-Check:** Für **jedes** Plugin muss ein aktueller **26.2**-Build bestätigt sein.
> Der **Mining-Kern** (Spitzhacken-Upgrades + Mehrblock-Abbau + Auto-Regeneration) ist der kritischste Punkt —
> ohne ihn ist der Loop nicht spielbar. Siehe
> [../NEW_SERVERS.md → Abschnitt 3.2](../NEW_SERVERS.md#32-mining-spezifisch).

---

## 1. Mining-Kern *(Blocker — zuerst festlegen)*

Der zentrale Loop: **besondere Spitzhacke → aufwerten → mehr Blöcke pro Schlag → neue Zonen freischalten**.

Es gibt zwei konkrete, umsetzbare Wege. **Empfehlung für den Solo-Betrieb: Weg A** (ein gebündelter Core →
wenigste Plugins, geringste Wartung). **Weg B** ist der modulare Aufbau aus gepflegten Einzel-Plugins, die
gut zum vorhandenen Stack passen — flexibler, aber mehr Konfigurations- und Wartungsaufwand.

> ⚠️ Für **jeden** hier genannten Kandidaten gilt der **26.2-Blocker-Check** (Build bestätigen) fort. Die
> Vorschläge sind begründete Kandidaten, keine bereits verifizierten Builds.

### 1A. Empfohlen — gebündelter Mining-/Prison-Core *(ein Plugin für den ganzen Loop)*

Deckt Spitzhacken-Progression, Mehrblock-Abbau, Zonen, Auto-Reset, Auto-Sell und Prestige/Ränge **in einem**
Plugin ab. Das minimiert Schnittstellen und Wartung — ideal für den Solo-Betrieb.

| Plugin | Deckt ab | Warum | Status |
|--------|----------|-------|:------:|
| **X-Prison** *(Erstwahl)* | Zonen + Auto-Reset, Mehrblock-Enchants (Explosive/Layer/Nuke), Spitzhacken-Level, Auto-Sell, Prestige/Ränge, Multiplier | Kostenlos & **Open-Source** (auditierbar), aktiv gepflegt, deckt den kompletten Kern-Loop ab | 🔶 26.2 prüfen |
| **EdPrison** / **VortexPrisonCore** *(Alternativen)* | Gleicher Funktionsumfang (All-in-One-Prison-Core) | Ausgereifte Alternativen (Vortex = ohne NMS → zukunftssicherer; Ed = großer Funktionsumfang) | 🔶 26.2 prüfen |

> **Konsequenz bei Weg A:** Shop/Auto-Sell (Abschnitt 3) und Ränge/Prestige (Abschnitt 4) sind bereits im Core
> enthalten — dort dann nur noch Feintuning/Balancing statt eigener Plugins. Der „OP-Prison"-Charakter der
> Mehrblock-Enchants lässt sich über die Config auf den gewünschten Casual-Loop herunterregeln.

### 1B. Alternative — modularer Aufbau *(gepflegte Einzel-Plugins)*

| Funktion | Empfohlenes Plugin | Alternativen | Status |
|----------|--------------------|--------------|:------:|
| **Aufwertbare Spitzhacke** (Item + Stufen) | **EcoItems** (kostenlos, gepflegt) | **MMOItems** (♻️ bereits im Bestand), Oraxen-Item + Skript | 🔶 26.2 prüfen |
| **Mehrblock-Abbau** (3×3 / Adern) | **EcoEnchants** (kostenlos, Area-/Explosive-Enchants) | **AdvancedEnchantments** (premium, Prison-Standard), CrazyEnchantments | 🔶 26.2 prüfen |
| **Abbau-Zonen + Auto-Regeneration** | **AxMines** (kostenlos, Open-Source, GUI, Timer/Prozent-Reset) | **JetsPrisonMines** (gleicher Entwickler wie JetsMinions im Skyblock-Stack), **OreRegrow** | 🔶 26.2 prüfen |

> **Synergie:** **EcoItems + EcoEnchants** stammen aus demselben (eco-)Ökosystem und sind auf Zusammenspiel
> ausgelegt — die Spitzhacke (EcoItems) trägt die Area-Enchants (EcoEnchants), gekoppelt an ihre Stufe.
> **AxMines** übernimmt Zonen inkl. Auto-Reset; **WorldGuard** (Abschnitt 2) sichert die Grenzen.

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
| **EconomyShopGUI** (+ **-Premium** für Auto-Sell) | Blöcke verkaufen → Upgrades/Zonen finanzieren (Economy-Sink & -Quelle); Vault-kompatibel, Bedrock-taugliche GUI | 🔶 26.2 prüfen |
| **ShopGUI+** *(Alternative, premium)* | Ausgereifter Sell-Shop mit Auto-Sell/Sell-Wands | 🔶 Prüfen |
| **CoinsEngine** *(optional)* | Netzwerkweite Cosmetic-Währung | ♻️ Recycelt |

> **Bei Weg 1A (gebündelter Core):** Auto-Sell ist bereits im Prison-Core enthalten — ein separates Shop-Plugin
> entfällt dann. **EconomyShopGUI** ist die Erstwahl für Weg 1B (modular), weil kostenlos, Vault-kompatibel und
> Bedrock-tauglich.

---

## 4. Retention & Cosmetics *(Phase 2)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **BattlePass** (premium) *oder* **Skript**-basierter Pass | Missionen, Tier-Belohnungen, Cosmetic-Unlocks — Langzeit-Bindung | 🔶 26.2 prüfen |
| **PlayerParticles** *(recyceln statt entfernen)* | Trails/Partikel-Cosmetics — kostenlos, bereits im `prison/`-Bestand | ♻️ Recycelt |
| **Ränge / Prestige** | Progression nach den Zonen: **LuckPerms + Skript** (bereits im Stack) — oder direkt aus dem Core (Weg 1A) | 🔶 Prüfen |

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
| **LuckPerms** | Permissions/Ränge (Kontext `server=prison` bleibt erhalten) | ♻️ Recycelt |
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

## 9. Beim Umbau zu entfernen (Alt-Prison-Spielmodus)

Diese Plugins gehören zum eingestellten Prison-Spielmodus und werden beim Umbau des `prison/`-Slots **abgebaut**
(vorher Spielerdaten sichern, siehe [../NEW_SERVERS.md → Abschnitt 5](../NEW_SERVERS.md#5-rückbau-des-prison-spielmodus-slot-recycling)):

| Plugin | Alt-Funktion | Status |
|--------|--------------|:------:|
| **MythicMobs**, **MythicDungeons**, **MythicPrison**, **MythicAchievements**, **MythicHUD** | Prison-Mobs/Dungeons/Scaling | 🧹 Entfernen |
| **MMOCore**, **MMOItems**, **MythicLib** | Klassen/Custom-Items | 🧹 Entfernen |
| **BetonQuest**, **Citizens** | Quests & NPCs | 🧹 Entfernen |
| **ModelEngine**, **LibsDisguises**, **DecentHolograms** | 3D-Models/Verkleidungen/Hologramme (Hologramme via CMI) | 🧹 Entfernen |
| **PlayerParticles** | Partikel/Trails — **behalten** und für Mining-Cosmetics recyceln (siehe Abschnitt 4) | ♻️ Recycelt |
| **Aurora**, **AuroraCollections** | Prison-Collections/Achievements | 🧹 Entfernen |
| **DeluxeBazaar**, **GlobalMarketPlus**, **PlayerPoints** | Prison-Handel/Punkte | 🧹 Entfernen |
| **RoseLoot**, **RoseStacker**, **RoseGarden** | Loot/Stacking/Lib (nur behalten, falls vom Mining-Kern gebraucht) | 🔶 Prüfen |
| **ExecutableItems**, **SCore**, **NBTAPI** | Item-Actions/Libs (nur bei Bedarf behalten) | 🔶 Prüfen |
| **BlueMap**, **AxiomPaper** | Web-Karte/Advanced-Building (optional für Zonen-Bau) | 🔶 Prüfen |
| **Plan** | Analytics (optional weiter nutzbar) | 🔶 Prüfen |

---

## Offene Punkte

- **Mining-Kern:** Entscheidung **Weg 1A (gebündelter Core, empfohlen: X-Prison)** vs. **Weg 1B (modular:
  EcoItems + EcoEnchants + AxMines)** treffen — und den **26.2-Build** des gewählten Kerns bestätigen
  (**Blocker Nr. 1**).
- **Shop/Auto-Sell:** Bei Weg 1B **EconomyShopGUI** (bzw. -Premium) einrichten und Verkaufspreis-Balancing
  festlegen; bei Weg 1A entfällt ein separates Plugin (Core-intern).
- **Zonen-Design:** Anzahl Zonen zum Launch, Block-Sets, Freischalt-Kosten, Abbau-Muster pro Stufe.
- **Cosmetics/Battle-Pass:** **BattlePass** (premium) oder Skript-Pass wählen; **PlayerParticles** für Trails
  recyceln; optionale netzwerkweite Cosmetic-Währung
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

**Status:** 🟡 Geplant / Aufbau — recycelt den `prison`-Slot
