# Server 2 — THE ABYSS *(Arbeitstitel)*

> **Konzept:** Rogue-Dungeon / „Endless Descent" — prozedurale Co-op-Runs statt Open-World-RPG.
> **Slot:** ersetzt den bisherigen `rpg/`-Server.
> **Rolle im Netzwerk:** Gear-Faucet + Ort, an dem Geld ausgegeben wird (siehe [README.md](README.md)).

---

## 1. Vision — warum das Spaß macht & viral geht

Ein bodenloser Abgrund unter der Welt. Allein oder in der Gruppe steigst du **Etage für
Etage** hinab — durch **prozedural zusammengesetzte Räume**. Jede Etage ist tödlicher und
reicher. Stirbst du, ist der Run vorbei — aber du behältst **Meta-Fortschritt**, der den
nächsten Abstieg stärker macht. Wöchentliche **Seeds** mit globalem **Leaderboard**.

**Warum das als Solo-Dev funktioniert:**
- **Prozedural statt Open-World:** Du baust **~20–30 Räume einmal** — die Zufalls-
  Kombination erzeugt ein **gefühlt unendliches** Erlebnis. Keine Zonen, keine Story,
  keine hunderte Hand-Quests.
- **Roguelite-Sog** („nur noch ein Run", Hades/Vampire-Survivors-Prinzip) + **Co-op** +
  **Highscore-Wettbewerb** = extrem Twitch-/Clip-tauglich.
- **Deine bereits investierte Arbeit bleibt erhalten:** MythicMobs + MythicDungeons +
  MMOItems werden **weiterverwendet** — nur der Content-Treadmill (Open World, BetonQuest)
  fällt weg.

**Kernwechsel:** von *hand-authored Welt* zu *systemgeneriertem Nachschub*.

---

## 2. Grobe Serverstruktur

### 2.1 Welt-Layout

| Bereich | Zweck |
|---------|-------|
| **Hub** (sichere Stadt) | Loadout-/Klassenwahl, Perk-Shop (Echoes), Gem-Shop (Keys/Modifier), Leaderboard-Boards, Gilden-Dungeon-Stats, Einstieg in den Abstieg |
| **Run-Instanzen** | prozedural aus Raum-Bausteinen zusammengesetzte Dungeon-Etagen (pro Party instanziert) |

### 2.2 Kern-Mechaniken

**Prozeduraler Abstieg (Option B):**
- Räume werden aus einem Pool von **~20–30 handgebauten Bausteinen** zufällig kombiniert:
  Kampfräume, Rätselräume, Schatzräume, Mini-Boss, Boss.
- **Skalierung**: Jede Etage erhöht Mob-Level/HP/Schaden und Loot-Tier.
  „**Wie tief kommst du?**" ist der Highscore-Hook.
- **Tod = Run vorbei** (voller oder teilweiser Loot-Verlust — [offene Entscheidung](README.md#8-offene-entscheidungen)).

**Meta-Progression (Roguelite):**
- Nach dem Tod behältst du **Echoes** 🔮 (lokale Währung).
- Echoes → **permanente Perks** (Start mit X, +Y % Loot, extra Revive, mehr Start-HP).
- Erzeugt den „one more run"-Loop **ohne** neuen Content von dir.

**Wiederkehr-Hooks:**
- **Daily/Weekly Seed**: Alle laufen denselben Zufalls-Seed → **globales Leaderboard** →
  wiederkehrendes Engagement bei **null** neuem Authoring.
- **Co-op-Parties** (PartyAndFriendsGUI) → sozial, gemeinsam tiefer kommen.

**Gear:**
- Loot nutzt **MMOItems** → synct via HuskSync nach Server 1 → dein Dungeon-Schwert ist
  deine Lifesteal-PvP-Waffe. **Das ist die zentrale Cross-Server-Brücke.**

### 2.3 Plugin-Stack

**Behalten / wiederverwenden (bereits im RPG-Stack — deine Investition bleibt!):**

| Plugin | Neue Rolle |
|--------|-----------|
| **MythicMobs** (Premium) | Dungeon-Mobs, Mini-Bosse, Bosse mit Mechaniken |
| **MythicDungeons** | Instanzierung der Runs |
| **MythicLib** | Basis-Library |
| **MMOItems** | Loot / Gear (synct zu Server 1) |
| **MythicHUD** | Run-HUD (Etage, HP, Timer) |
| **ModelEngine / DecentHolograms** | 3D-Bosse / Anzeigen |
| CoinsEngine | Echoes (lokal) + Gems (geteilt) |
| HuskSync | Gear-Sync mit Server 1 |
| PartyAndFriendsGUI | Co-op-Parties |
| GlobalMarketPlus | Cross-Server-Markt |
| GrimAC, LuckPerms, PlaceholderAPI, DeluxeMenus, Vault, ProtocolLib, spark | Standard-Stack |
| CrossCraft-Guilds | Gilden-Dungeon-Leaderboards / Gildenbank |
| **FastAsyncWorldEdit** | **Dev-Tool** zum Bauen der Raum-Bausteine |
| **WorldGuard** | Hub-Schutz |

**Vereinfachen / Entfernen (der Open-World-Content-Treadmill):**

| Plugin | Aktion |
|--------|--------|
| **BetonQuest** | **Entfernen** — keine Open-World-Quests mehr |
| **Citizens** | **Minimieren/Entfernen** — höchstens ein paar Hub-NPCs |
| **MMOCore** (6-Klassen-System) | **Verschlanken** → leichte Loadouts/Perks statt schwerem Klassenbaum (oder stark reduziert behalten) |
| Open-World-Zonen / Welt-Bosse / Schnellreise | **Entfernen** — ersetzt durch Hub + Instanzen |

> **Leitlinie:** Lean bleiben. Der ganze Sinn ist, den Open-World-Content-Treadmill
> **fallenzulassen** und durch prozedurale Runs zu ersetzen.

### 2.4 Andockung an die Shared Economy

- **Echoes** 🔮 bleiben **lokal** (nur Server 2) → Meta-Progression bleibt hier bedeutsam.
- **Gems** 💎 (geteilt) werden hier **ausgegeben**: Dungeon-Keys, Run-Modifier, Revives.
- **Gear** (MMOItems) wandert per HuskSync zu Server 1 → treibt dort das PvP.
- **Netzwerk-Event „Gem Rush"**: zeitweise erhöhte Gem-Drops → zieht S1-Spieler herüber.

### 2.5 Repo-Ordnerstruktur (Zielbild)

```
rpg/                          # (Slot wird umgewidmet zu THE ABYSS)
└── plugins/
    ├── MythicMobs/           # Dungeon-Mobs & Bosse (wiederverwendet)
    ├── MythicDungeons/       # Run-Instanzierung
    ├── MythicLib/ MMOItems/ MythicHUD/
    ├── CoinsEngine/          # Echoes (lokal) + Gems (geteilt, MariaDB)
    ├── HuskSync/ PartyAndFriendsGUI/ GlobalMarketPlus/
    ├── ModelEngine/ DecentHolograms/
    ├── WorldGuard/ FastAsyncWorldEdit/   # Hub-Schutz + Dev-Bau-Tool
    └── GrimAC/ LuckPerms/ PlaceholderAPI/ ...
    # entfernt: BetonQuest, (Citizens minimiert), Open-World-Zonen
```

> Ob der physische Ordner `rpg/` umbenannt wird (z. B. nach `abyss/`), ist eine
> [offene Entscheidung](README.md#8-offene-entscheidungen) — funktional ändert das nichts.

---

## 3. Nächste Schritte (phasenweise)

> **Hinweis:** Laut [Roadmap](README.md#7-roadmap-überblick) wird **Server 1 zuerst** gebaut.
> Server 2 startet, sobald das Shared-Economy-Fundament steht.

### Phase 0 — Setup & Entscheidungen
- [ ] Run-Regeln festlegen: Etagen pro Run, Tod = voller/teilweiser Loot-Verlust?
- [ ] Meta-Progression definieren: welche Perks, Echoes-Kosten?
- [ ] Umfang MMOCore klären: verschlanken oder durch simple Loadouts ersetzen?

### Phase 1 — MVP (ein spielbarer Abstieg)
- [ ] Hub bauen (Einstieg + Basis-Shops).
- [ ] **5–8 Raum-Bausteine** bauen (Kampf/Schatz/Boss) mit FastAsyncWorldEdit.
- [ ] MythicDungeons: Räume zufällig zu einem Run verketten.
- [ ] Etagen-Skalierung (Mob-Level/HP/Loot) via MythicMobs.
- [ ] Intern testen: Abstieg, Skalierung, Tod → Run-Ende.

### Phase 2 — Roguelite-Kern
- [ ] Echoes-Belohnung bei Tod + Perk-Shop (permanente Upgrades).
- [ ] Raum-Pool auf **20–30 Bausteine** erweitern (mehr Varianz).
- [ ] Boss-Encounter mit Mechaniken (MythicMobs-Skills).
- [ ] Loot-Tables (MMOItems), skaliert nach Etage.
- [ ] Co-op/Party-Runs (PartyAndFriendsGUI).

### Phase 3 — Wiederkehr & Bridge
- [ ] **Daily/Weekly Seed** + globales Leaderboard.
- [ ] Gem-Shop (Keys/Modifier) + Gem-Ausgabe verifizieren (gemeinsame MariaDB).
- [ ] HuskSync-Gear-Sync mit Server 1 testen (Dungeon-Loot → PvP-Waffe).
- [ ] Gilden-Dungeon-Leaderboards (CrossCraft-Guilds).

### Phase 4 — Launch & Loop
- [ ] Netzwerk-Event „Gem Rush" einbauen.
- [ ] Balancing: Etagen-Kurve, Loot-Raten, Perk-Kosten.
- [ ] Bedrock-Test (Geyser/Floodgate).
- [ ] Launch + Dokumentation/README aktualisieren.

---

## 4. Balancing-Stellschrauben & Risiken

- **Raum-Varianz**: Zu wenige Bausteine → Runs fühlen sich repetitiv an. Ziel: genug
  Varianz, damit sich zwei Runs selten gleich anfühlen (deshalb 20–30 als Richtwert).
- **Schwierigkeitskurve**: Muss stetig steigen, aber fair bleiben — sonst bricht der
  „one more run"-Sog ab.
- **Gear-Balance vs. Server 1**: Dungeon-Loot wird zur PvP-Waffe → Werte gemeinsam mit
  Server 1 balancen, sonst zerlegt PvE-Gear das PvP (oder umgekehrt).
- **Meta-Progression-Tempo**: Perks dürfen den Reiz nicht zu früh nehmen; langsam,
  spürbar, aber nie „fertig".
- **Instanz-Performance**: Viele parallele Party-Instanzen → mit `spark` überwachen,
  RoseStacker/Cleanup einplanen.
