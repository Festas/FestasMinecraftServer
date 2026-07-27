# Rework-Konzept: Zwei-Server-Netzwerk mit geteilter Economy

> **Status:** Vorschlag / zur Entscheidung. Ersetzt konzeptionell die bisherigen MMO-Server **Skyblock** und **RPG**.
> **Lobby, Proxy und Survival bleiben unverändert** und dienen weiter als Hub/Routing.

---

## 1. Warum dieser Pivot?

Das bisherige MMO-Konzept (Skyblock-MMO + Open-World-RPG) ist **content-getrieben**: Jede
Stunde Spielspaß muss von Hand gebaut werden (Quests, Mobs, Dungeons, Story, Balancing,
Texturen). Das skaliert linear mit Arbeitszeit — Wynncraft/Hypixel haben dafür ganze Teams.
Solo ist genau das der Grund, warum das Projekt „zu viel Zeit frisst".

**Kernidee des Reworks:** Weg von *hand-authored Content*, hin zu **systemgetriebenem,
emergentem Spaß**. Also Konzepte, bei denen **Spieler + Zufall + Wettbewerb** den Content
erzeugen — nicht der Entwickler. Das ist der Hebel, mit dem ein Einzelner einen viralen
Server bauen kann.

Wir kombinieren die drei favorisierten Optionen auf **zwei Server**:

| Option | Konzept | Landet auf |
|--------|---------|-----------|
| **A** | Lifesteal SMP | Server 1 (Surface-Layer) |
| **C** | Prison / Rankup-Mining | Server 1 (Mine-Layer) |
| **B** | Rogue-Dungeon / Endless Descent | Server 2 |

Optionen **A + C** verschmelzen zu **einem** Sandbox-Server, Option **B** wird der zweite
Server. Beide teilen sich eine **gemeinsame Economy**.

---

## 2. Das Zwei-Server-Konzept auf einen Blick

| | **Server 1 — OUTLANDS** *(Arbeitstitel)* | **Server 2 — THE ABYSS** *(Arbeitstitel)* |
|---|---|---|
| **Slot** | ersetzt `skyblock/` | ersetzt `rpg/` |
| **Genre** | Lifesteal + Prison Sandbox | Rogue-Dungeon (Co-op PvE) |
| **Spielgefühl** | kompetitiv, sozial, PvP-Drama | kooperativ, skill-basiert, „one more run" |
| **Content-Quelle** | Spieler (Gangs, Raids, Politik) | Prozedural (zufällig kombinierte Räume) |
| **Faucet (erzeugt)** | Geld / Rohstoffe | Gear / Loot |
| **Sink (verbraucht)** | Gear-Risiko (PvP-Tod) | Geld (Keys, Consumables) |
| **Wiederkehr-Hook** | Season-Reset alle 6–8 Wochen | tägliche/wöchentliche Seeds + Leaderboard |
| **Detaildoku** | [SERVER-1-OUTLANDS.md](SERVER-1-OUTLANDS.md) | [SERVER-2-ABYSS.md](SERVER-2-ABYSS.md) |

Die beiden Server sind **bewusst komplementär**: Server 1 ist die **Geldquelle + der Ort,
an dem Gear riskiert wird**, Server 2 ist die **Gear-Quelle + der Ort, an dem Geld
ausgegeben wird**. Sie balancieren sich gegenseitig aus.

> Die Namen **OUTLANDS** und **THE ABYSS** sind Arbeitstitel — siehe [Offene Entscheidungen](#7-offene-entscheidungen).

---

## 3. Shared-Economy-Design (das Herzstück)

**Leitprinzip:** *Eine* geteilte Hartwährung fließt zwischen den Servern, aber jeder
Server behält seine **eigene lokale Grind-Währung**. So bleibt jeder Server-Grind
bedeutsam und kein Server trivialisiert den anderen.

### Währungen

| Währung | Symbol | Geteilt? | Verdient auf | Ausgeben für |
|---------|--------|----------|--------------|--------------|
| **Gems** | 💎 | **JA** (netzwerkweit identisch) | beiden Servern (langsam) | Premium-Sachen auf beiden Servern: Revives, Dungeon-Keys, Cosmetics, Gang-Upgrades |
| **Coins** (S1) | 🪙 | Nein (nur Server 1) | Mining & Verkauf & Bounties | Rankup, Mining-Gear, Prison-Shops |
| **Echoes** (S2) | 🔮 | Nein (nur Server 2) | Dungeon-Runs | Meta-Progression / permanente Perks |

**Warum die Trennung?** Würde *alles* Geld syncen, würden Spieler nur den einfachsten
Server grinden und den anderen dominieren → das killt beide Economies. Durch **eine**
geteilte Premium-Währung (Gems) + **geteiltes Gear** entsteht Cross-Server-Relevanz
**ohne** das Balancing zu zerstören.

### Was synchronisiert wird

| Was | Womit | Effekt |
|-----|-------|--------|
| Inventar + Enderchest + XP/Level | **HuskSync** (bereits installiert) | Gear reist zwischen den Servern |
| **Gems**-Kontostand | **CoinsEngine** mit **gemeinsamer MariaDB** | Balance überall identisch |
| Gilden + Gildenbank | **CrossCraft-Guilds** (eigenes Plugin, bereits cross-server) | Gang/Gilde wirkt auf beiden Servern |
| Marktangebote | **GlobalMarketPlus** (auf beiden installiert) | Item auf S1 listen, auf S2 kaufen |

> **Wichtig:** Lokale Währungen (Coins/Echoes) werden in CoinsEngine als **separate,
> nicht-geteilte** Währungen konfiguriert; nur **Gems** nutzen den gemeinsamen
> DB-Storage. Gear synct über HuskSync, **nicht** die lokalen Währungen.

---

## 4. Der Cross-Server Gameplay-Loop

```
        ┌──────────────────────── SERVER 1: OUTLANDS ───────────────────────┐
        │  (1) Mine sicher in der Mine  →  verdiene Coins  →  Rankup         │
        │  (2) Tausche große Coin-Stacks  →  Gems 💎 (geteilt)               │
        └───────────────┬───────────────────────────────────────────────────┘
                        │  Gems 💎 + gesynctes Gear (HuskSync)
                        ▼
        ┌──────────────────────── SERVER 2: THE ABYSS ──────────────────────┐
        │  (3) Kaufe Dungeon-Keys / Run-Modifier mit Gems                    │
        │  (4) Laufe Rogue-Dungeons  →  starkes Gear + Echoes (Meta-Perks)   │
        └───────────────┬───────────────────────────────────────────────────┘
                        │  Gesynctes Gear zurück (HuskSync)
                        ▼
        ┌──────────────────────── SERVER 1: OUTLANDS ───────────────────────┐
        │  (5) Dominiere mit Dungeon-Gear in der Lifesteal-PvP-Zone          │
        │  (6) Klaue Herzen & Loot  →  finanziert die nächsten Gems          │
        └───────────────────────────────────────────────────────────────────┘
                        │
                        └──────────►  zurück zu (1)  ♻
```

- **Server 1 = Geld-Faucet + Gear-Sink** (PvP-Risiko).
- **Server 2 = Gear-Faucet + Geld-Sink** (Keys/Consumables).
- **Gilden** operieren über beide: Gildenbank in Gems, Gang-Wars auf S1, Dungeon-Leaderboards auf S2.

Das ist ein starker Loop, weil kein Server für sich „fertig" ist — der Fortschritt auf
dem einen macht den anderen besser.

---

## 5. Bridge-Mechaniken (was die Server konkret verbindet)

1. **Gem-Exchange (GUI/NPC)** auf beiden Servern: lokale Währung → Gems zu einem festen
   Kurs (einseitig / mit Spread als Geld-Sink).
2. **Netzwerk-Markt** über **GlobalMarketPlus** (bereits auf beiden!): serverübergreifendes
   Auktionshaus — auf S1 listen, auf S2 kaufen.
3. **Cross-Server Gildenbank** (CrossCraft-Guilds): hält Gems + Items für die ganze Gilde.
4. **Wöchentliche Netzwerk-Events**, z. B. „Gem Rush" (Dungeon-Gems ×2) → treibt
   S1-Spieler auf S2 und umgekehrt.
5. **Geteiltes Gear** (HuskSync): Das Schwert aus dem Dungeon ist die PvP-Waffe im Lifesteal.

---

## 6. Wiederverwendung bestehender Infrastruktur

Fast die komplette technische Basis bleibt erhalten — wir werfen vor allem den
**Content-Treadmill** über Bord, nicht die Infrastruktur.

| Bleibt / wiederverwendet | Zweck |
|--------------------------|-------|
| Velocity-Proxy + Lobby | Routing (unverändert) |
| MariaDB `172.25.0.1:3306` + Redis `172.18.0.1:6379` | geteilter Storage für Gems, Gilden, Sync |
| **HuskSync** | Inventar/Gear-Sync zwischen S1 & S2 |
| **CoinsEngine** | Multi-Währungs-System (Gems geteilt, Coins/Echoes lokal) |
| **GlobalMarketPlus** | Cross-Server-Markt |
| **CrossCraft-Guilds** | Cross-Server-Gilden/Gangs |
| **GrimAC** | Anti-Cheat (Pflicht, v. a. für PvP auf S1) |
| **LuckPerms, PlaceholderAPI, DeluxeMenus, Vault, ProtocolLib, spark** | Standard-Stack |
| **Geyser + Floodgate** | Bedrock-Support (Handy/Konsole) — senkt die Einstiegshürde massiv |

Details, was pro Server **entfernt** wird (der ganze Mythic-/MMO-Stack), stehen in den
jeweiligen Server-Dokumenten.

---

## 7. Roadmap-Überblick

**Empfehlung: „MVP zuerst" — nicht beide Server gleichzeitig bauen.**

1. **Phase 1 — Server 1 (OUTLANDS) zuerst.** Höchstes Viral-/Reichweiten-Verhältnis bei
   geringstem Bauaufwand (reine Configs + Skript, kein Content-Bau). Schnell live testbar,
   zieht früh Spieler.
2. **Phase 2 — Shared-Economy-Fundament** (Gems in gemeinsamer DB, HuskSync-Check,
   GlobalMarket-Bridge, Gildenbank) — sobald Server 1 stabil läuft.
3. **Phase 3 — Server 2 (THE ABYSS).** Nutzt die bereits investierte MythicMobs/
   MythicDungeons-Arbeit weiter, ersetzt aber Open-World durch prozedurale Runs.
4. **Phase 4 — Loop scharf schalten:** Gem-Exchange-Kurse, Netzwerk-Events, Balancing.

Die detaillierten, phasenweisen Checklisten stehen jeweils am Ende der Server-Dokumente.

---

## 8. Offene Entscheidungen

Diese Punkte solltest du festlegen, bevor wir in die Umsetzung gehen:

- [ ] **Server-Namen**: Bleiben „OUTLANDS" / „THE ABYSS" oder eigene Namen?
- [ ] **Season-Länge Server 1**: 6 oder 8 Wochen? Was resettet (nur Surface-Map?), was
      bleibt persistent (Gems, Stats, Gilde, Cosmetics)?
- [ ] **Gem-Kurse**: Wie teuer ist eine Gem in Coins bzw. Echoes? (bestimmt das Tempo des Loops)
- [ ] **Lifesteal-Regeln**: Start-Herzen, Max-Herzen, Strafe bei 0 (Temp-Ban-Dauer vs.
      Spectator), Revive-Kosten (Gems vs. Item).
- [ ] **Dungeon-Tod**: Voller Run-Verlust oder teilweiser Loot-Behalt? Wie viele Etagen pro Run?
- [ ] **Reihenfolge bestätigen**: Wirklich Server 1 zuerst?

Sobald diese Punkte stehen, kann ich pro Server die konkreten Config-Änderungen und die
finale Plugin-Streichliste ausarbeiten.
