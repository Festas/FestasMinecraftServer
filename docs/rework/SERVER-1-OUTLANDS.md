# Server 1 — OUTLANDS *(Arbeitstitel)*

> **Konzept:** Lifesteal SMP **+** Prison-Rankup verschmolzen zu einer Sandbox.
> **Slot:** ersetzt den bisherigen `skyblock/`-Server.
> **Rolle im Netzwerk:** Geld-Faucet + Ort, an dem Gear riskiert wird (siehe [README.md](README.md)).

---

## 1. Vision — warum das Spaß macht & viral geht

Ein hartes Ödland. **Unten** in geschützten Minen grindest du Erz und rankst auf
(Prison-DNA) — **oben** an der Oberfläche herrscht das Gesetz des Stärkeren: **Lifesteal**.
Wer tötet, gewinnt ein Herz; wer stirbt, verliert eins. Bei 0 Herzen bist du (temporär)
raus. Bau eine Basis, gründe eine Gang, überfalle Rivalen, beherrsche die Map.

**Warum das als Solo-Dev funktioniert:**
- **Lifesteal** ist DAS virale Format (TikTok/YouTube): Clips, Allianzen, Verrat, Drama.
  Der Content entsteht durch **Spieler**, nicht durch dich. Im DE-Raum unterversorgt.
- **Prison** liefert den **PvE-Wirtschaftsmotor** und ein Ziel für Nicht-PvP-Spieler —
  und behebt damit Lifesteals größte Schwäche (nichts zu tun, wenn man nicht kämpft).
- Beides zusammen = ein **komplettes Ökosystem**: Miner finanzieren, Krieger riskieren,
  alle speisen dieselbe Economy. Content-Bau ≈ Configs, **keine** Hand-Quests/-Mobs.

**Die Fusion ist synergetisch, nicht erzwungen:** Prison gibt Lifesteal ein Ziel,
Lifesteal gibt Prison Einsatz und soziale Dramatik.

---

## 2. Grobe Serverstruktur

### 2.1 Welt-Layout (Zwei-Schichten-Design)

| Layer | Zone | PvP / Lifesteal | Zweck |
|-------|------|-----------------|-------|
| **Mine** | Rankup-Minen A→Z | **Aus** (sicher) | Erz farmen → verkaufen → Coins → Rankup. Der verlässliche Geldmotor. |
| **Mine** | PvP-Mine (optional) | **An** | risikoreiches Erz mit höheren Drops — hier darf geklaut werden |
| **Surface** | Wildnis / Ödland | **An** (Lifesteal) | offene Welt: Basen bauen, raiden, Herzen klauen, Bounties |
| **Surface** | Spawn / Safezone | **Aus** | Handel, Shops, Gem-Exchange, Rückkehrpunkt |

Regionen werden über **WorldGuard** abgegrenzt (Flags `pvp`, `lifesteal`-Region via
Skript). *Hinweis: WorldGuard ist aktuell nur auf dem RPG-Server — muss hier ergänzt werden.*

### 2.2 Kern-Mechaniken

**Lifesteal (Option A):**
- Start bei 10 Herzen, Max z. B. 20, Min 0.
- Kill → +1 Herz (du), −1 Herz (Opfer).
- Bei 0 Herzen: **Elimination** → Temp-Ban (z. B. 24 h) *oder* Spectator bis Revive.
- **Herz-Items**: Herzen abheben/craften/handeln → handelbare Ware (Economy-Treiber).
- **Revive-Mechanik**: Revive-Beacon (von Gang gebaut) *oder* Kauf per **Gems** 💎.
- **Bounties**: Kopfgeld auf Spieler aussetzen (zieht Jäger an → mehr Drama).

**Prison / Rankup (Option C):**
- Minen **A→Z**: jede Rank-Stufe kostet **Coins** 🪙 und schaltet reichere Minen frei.
- **Prestige**: Ränge zurücksetzen für permanente Multiplikatoren → **unendliche
  Progression bei null Authoring-Kosten**.
- **Mining-Enchants** über **ExcellentEnchants** (bereits hier installiert): Explosive,
  Fortune, Auto-Sell, Speed.
- **Auto-Sell / Sell-Wands** + Shops über **DeluxeBazaar / GlobalMarketPlus**.

**Gangs & Season:**
- **Gangs = CrossCraft-Guilds** (dein eigenes Plugin): Gang-Bank, Gang-Land, Gang-vs-Gang.
- **Season-Reset** alle 6–8 Wochen: **nur die Surface-Map** wird zurückgesetzt (frischer
  Land-Grab = wiederkehrender Hype & clip-würdige Launch-Tage). **Persistent bleiben:**
  Gems, Stats, Cosmetics, Gilde.

### 2.3 Plugin-Stack

**Behalten / wiederverwenden (schon im Skyblock-Stack vorhanden):**

| Plugin | Neue Rolle |
|--------|-----------|
| CoinsEngine | Coins (lokal) + Gems (geteilt) |
| GrimAC | Anti-Cheat — **Pflicht** für PvP-Integrität |
| ExcellentEnchants | Mining- & Combat-Enchants |
| DeluxeBazaar / DeluxeMenus | Shops & GUIs |
| GlobalMarketPlus | Cross-Server-Markt |
| HuskSync | Gear-Sync mit Server 2 |
| PartyAndFriendsGUI | Parties / Freunde |
| RoseStacker | Mob-/Item-Stacking in Minen (Performance) |
| CMI / CMILib, Vault, LuckPerms, PlaceholderAPI, ProtocolLib, spark | Standard-Stack |
| **Skript** | **Lifesteal-Logik + Prison-Rankup als Custom-Scripts** |
| CrossCraft-Guilds | Gangs |

**Hinzufügen:**

| Plugin | Zweck |
|--------|-------|
| **WorldGuard** | Regionen/PvP-Flags (Mine vs. Wildnis vs. Safezone) |
| **Lifesteal** (dediziertes Plugin *oder* Skript-Implementierung) | Herz-Mechanik |
| **Prison/Mines** (dediziertes Plugin *oder* Skript + WorldEdit) | Rankup-Minen, Mine-Reset |

> Da **Skript** bereits installiert ist, können Lifesteal und die Rankup-Logik zunächst
> als Skripte umgesetzt werden — spart Plugin-Abhängigkeiten und ist voll anpassbar.

**Entfernen (der komplette MMO-/Skyblock-Treadmill):**

`SuperiorSkyblock2`, `JetsMinions`, `VoidGen`, `SlimeWorldManager`, `MMOCore`,
`MMOItems`, `MythicMobs`, `MythicLib`, `MythicDungeons`, `MythicRPG`, `MythicHUD`,
`MythicAchievements`, `Aurora`, `AuroraCollections`, `Oraxen` (nur behalten, falls für
Cosmetics gewünscht).

### 2.4 Andockung an die Shared Economy

- **Coins** 🪙 bleiben **lokal** (nur Server 1) → der Mining-Grind bleibt hier bedeutsam.
- **Gem-Exchange**: großer Coin-Stack → **Gems** 💎 (geteilt) an einem Exchange-NPC/GUI.
- **Gear** wandert per HuskSync nach Server 2 und zurück (Dungeon-Loot = PvP-Waffe).
- **Gems** finanzieren Revives, Gang-Upgrades, Cosmetics — netzwerkweit gültig.

### 2.5 Repo-Ordnerstruktur (Zielbild)

```
skyblock/                     # (Slot wird umgewidmet zu OUTLANDS)
└── plugins/
    ├── CoinsEngine/          # Coins (lokal) + Gems (geteilt, MariaDB)
    ├── WorldGuard/           # Regionen: Mine / Wildnis / Safezone   (NEU)
    ├── Skript/               # Lifesteal- + Rankup-Scripts
    ├── ExcellentEnchants/    # Mining-/Combat-Enchants
    ├── DeluxeBazaar/ DeluxeMenus/ GlobalMarketPlus/
    ├── HuskSync/ GrimAC/ LuckPerms/ PlaceholderAPI/ ...
    └── (Mines-Plugin)/       # falls dediziert statt Skript          (NEU)
```

> Ob der physische Ordner `skyblock/` umbenannt wird (z. B. nach `outlands/`), ist eine
> [offene Entscheidung](README.md#8-offene-entscheidungen) — funktional ändert das nichts.

---

## 3. Nächste Schritte (phasenweise)

### Phase 0 — Setup & Entscheidungen
- [ ] Namen & Season-Länge festlegen (siehe [README, Offene Entscheidungen](README.md#8-offene-entscheidungen)).
- [ ] Lifesteal-Regeln festlegen: Start-/Max-/Min-Herzen, Strafe bei 0, Revive-Kosten.
- [ ] Entscheiden: Lifesteal & Prison via **Skript** oder dedizierte Plugins?
- [ ] WorldGuard beschaffen und in den Stack aufnehmen.

### Phase 1 — MVP (spielbarer Kern)
- [ ] Welt generieren: Spawn/Safezone + 1 Rankup-Mine + Wildnis-Zone.
- [ ] WorldGuard-Regionen + PvP-Flags setzen (Mine=safe, Wildnis=PvP).
- [ ] Lifesteal-Grundlogik (Herz-Gewinn/-Verlust, 0-Herzen-Strafe).
- [ ] Rankup A→C mit CoinsEngine-Coins + Sell-Shop.
- [ ] Intern testen (Kill-Loop, Rankup-Loop, Sell-Loop).

### Phase 2 — Kern-Features
- [ ] Herz-Items (abheben/handeln), Revive-Mechanik.
- [ ] Volle Rank-Leiter A→Z + Prestige-Multiplikatoren.
- [ ] Mining-Enchants (ExcellentEnchants) + Auto-Sell.
- [ ] Gangs über CrossCraft-Guilds (Bank, Land, Gang-vs-Gang).
- [ ] Bounty-System.

### Phase 3 — Economy-Bridge & Politur
- [ ] Gem-Exchange-NPC (Coins → Gems) einbauen.
- [ ] Gems als geteilte Währung in gemeinsamer MariaDB verifizieren.
- [ ] GlobalMarketPlus als Netzwerk-Markt konfigurieren.
- [ ] GrimAC für PvP feinjustieren (False-Positives minimieren).
- [ ] Season-Reset-Prozess testen (was resettet, was bleibt).

### Phase 4 — Launch
- [ ] Balancing-Pass (Rankup-Preise, Herz-Ökonomie, Drop-Raten).
- [ ] Bedrock-Test (Geyser/Floodgate) — Handy-/Konsolenspieler.
- [ ] Season 1 mit frischer Map + Launch-Event starten.
- [ ] Dokumentation & README aktualisieren.

---

## 4. Balancing-Stellschrauben & Risiken

- **Herz-Inflation**: Wenn Kills zu leicht Herzen geben, eskaliert alles zu schnell →
  Max-Herzen deckeln, Herz-Verlust bei Elimination großzügig.
- **Rankup-Tempo**: Zu billig = Progression vorbei; zu teuer = Frust. Iterativ tunen.
- **Safezone-Missbrauch**: Spawn-Camping verhindern (kein PvP am Safezone-Rand, Combat-Tag).
- **Anti-Cheat ist Pflicht**: Ohne sauberes GrimAC stirbt ein Lifesteal-Server an Cheatern.
- **Season-Reset-Kommunikation**: Klar ankündigen, was den Reset überlebt (sonst Frust).
