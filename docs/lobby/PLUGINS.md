# Lobby-Server — Optimaler Plugin-Stack

> **✅ Aktiv (Stand 26.2).** Fokussierter Plugin-Stack für den **aktiv gepflegten** Lobby-/Hub-Server.
> Diese Datei bündelt den **Ist-Bestand** (ordnergenau aus [`lobby/plugins/`](../../lobby/plugins/)) und ergänzt
> **konkrete Retention-Empfehlungen**, da der Hub aktuell **rein funktional** ist (Navigation, Schutz) und kaum
> Engagement-Features bietet. Übergeordnete Referenz: [../PLUGINS.md](../PLUGINS.md#lobby-server-plugins).

Die Spalte **Status** ordnet jedes Plugin ein:

- ✅ **Kern** — für den Hub-Betrieb erforderlich, bereits in [`lobby/plugins/`](../../lobby/plugins/).
- 🟢 **Vorhanden** — bereits installiert (Komfort/Backend).
- ➕ **Empfohlen (neu)** — sinnvolle Ergänzung für Retention, **noch nicht** installiert (siehe [Empfohlene Extra-Plugins](#a-empfohlene-extra-plugins-neu)).

> ⚠️ **26.2-Blocker-Check:** Für **jedes neue** Plugin (➕) muss vor dem Ausrollen ein aktueller **26.2**-Build
> auf der Bezugsquelle (SpigotMC/Polymart/Modrinth/Hangar/GitHub) bestätigt werden. Die vorhandenen Plugins
> laufen bereits auf 26.2.

---

## 1. Management & Core *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **CMI** (+ **CMILib**) *(paid)* | Kern-Management (Spawn, Warps, Teleport, Chat, Hologramme, Void-/Fall-Schutz) | ✅ Kern |
| **LuckPerms** | Permissions/Ränge (Kontext-basiert) | ✅ Kern |
| **Vault** | Economy-/Permissions-API-Bridge (Backend) | 🟢 Vorhanden |

---

## 2. Display, Navigation & UI *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **DeluxeMenus** | **Zentrale Navigation**: `server_selector` (Survival, Skyblock, Mining via `[connect] rpg`), Guide, Regeln | ✅ Kern |
| **Skript** | Custom-Logik: Navigator-Kompass (öffnet `server_selector`), Doppelsprung, Hub-/Inventar-Schutz | ✅ Kern |
| **Oraxen** | Custom Items/Texturen (Bedrock-tauglich prüfen) | 🟢 Vorhanden |

---

## 3. Utility & Bibliotheken *(vorhanden)*

| Plugin | Zweck | Status |
|--------|-------|:------:|
| **PlaceholderAPI** | Platzhalter für Nachrichten/Displays | ✅ Kern |
| **ProtocolLib** | Packet-Basis (Backend für Custom-Features) | ✅ Kern |
| **CommandAPI** | Command-Bibliothek (Backend) | 🟢 Vorhanden |
| **FastAsyncWorldEdit (FAWE)** | Async World-Editing für Lobby-Bau/-Pflege | 🟢 Vorhanden |
| **WorldGuard** | Regionen-Schutz (kein Griefing im Hub) | ✅ Kern |
| **PartyAndFriendsGUI** | Party-/Freundeslisten-GUI (Backend zum Velocity-PAF) | 🟢 Vorhanden |
| **bStats** / **faststats** / **spark** | Metriken, Performance-Profiling | 🟢 Vorhanden |

> **Hinweis:** FancyNpcs und ein separates Hologramm-Plugin (DecentHolograms) sind **nicht mehr** Teil der Lobby —
> Navigation läuft über den DeluxeMenus-`server_selector` + Skript-Kompass, Hologramme über CMI.

---

## A. Empfohlene Extra-Plugins *(neu)*

> Der Hub ist aktuell rein funktional. Die folgenden Ergänzungen erhöhen **Verweildauer & Wiederkehr** und
> schaffen mögliche **Store-Ware** (Cosmetics). Alle Vorschläge sind **26.2-Blocker-pflichtig** (Build bestätigen).

### A1. Hub-Cosmetics / Gadgets *(Retention)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **UltraCosmetics** | Trails, Gadgets, Morphs, Pets, Effekte im Hub | Frei | ➕ Empfohlen |
| **Hub-Cosmetics** *(Alternative)* | Kommerzielle Cosmetics-Suite mit tieferer Anpassung | Kauf | ➕ Optional |

> **Begründung:** Cosmetics binden Spieler an den Hub und lassen sich als **Store-/Battle-Pass-Ware** monetarisieren.
> **Bedrock-Kompatibilität** (Geyser/Floodgate) vor dem Ausrollen testen.

### A2. Parkour / Mini-Activity *(Retention)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **Parkour** | Parkour-Kurse im Hub mit Checkpoints/Belohnungen | Frei | ➕ Empfohlen |

> **Begründung:** Kleine Aktivität während Warte-/Übergangszeiten → höhere Verweildauer im Hub, niedriger
> Pflegeaufwand.

### A3. Netzwerkweite Ergänzungen *(auch im Hub sinnvoll)*

| Plugin | Zweck | Kosten | Status |
|--------|-------|:------:|:------:|
| **DiscordSRV** | Chat-Bridge/Community-Anbindung (netzwerkweit, siehe Survival-Stack) | Frei | ➕ Optional |
| **VotingPlugin** | Vote-Anzeige/-Belohnungen im Hub (netzwerkweit) | Frei | ➕ Optional |

> **Hinweis:** DiscordSRV und Voting sind primär im
> [Survival-Stack → Abschnitt A6/A7](../survival/PLUGINS.md#a6-discord-anbindung-netzwerkweit) beschrieben;
> im Hub können sie **Anzeige/Einstieg** (z. B. Vote-Menü) bereitstellen.

---

## Offene Punkte

- **A1 Hub-Cosmetics (UltraCosmetics)** einführen — Retention + mögliche Store-Ware; Bedrock-Test.
- **A2 Parkour** als Hub-Aktivität ergänzen.
- **A3** Hub-Anbindung von **DiscordSRV/Voting** klären (Anzeige/Einstieg vs. Backend auf Survival).
- **26.2-Builds** aller ➕-Kandidaten bestätigen.

---

## Siehe auch

- [Netzwerk-Plugin-Referenz](../PLUGINS.md)
- [Survival-Plugin-Stack](../survival/PLUGINS.md)
- [Skyblock-Plugin-Stack](../skyblock/PLUGINS.md) · [Mining-Plugin-Stack](../mining/PLUGINS.md)

---

**Letzte Aktualisierung:** 2026-08-17

**Status:** ✅ Aktiv (26.2) — Ist-Bestand + empfohlene Retention-Plugins
