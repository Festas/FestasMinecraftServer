# Zeitbasierte Rang-Leiter (CMI) — Ablösung von Autorank

Dokumentiert die **zeitbasierte, serverübergreifende Rang-Progression** (`autorank`-Track),
die jetzt von der **CMI-Rang-Engine** getrieben wird. Das Plugin **Autorank** ist damit **obsolet**.

> **Nicht betroffen:** Der geldbasierte **`tycoon`**-Track (`tycoon_erde → … → tycoon_bedrock`,
> getrieben vom **Rankup**-Plugin) bleibt vollständig unverändert. Beide Leitern laufen parallel.

---

## 1. Konzept

Das Problem zerfällt in zwei Ebenen:

1. **Rang-Anwendung (Prefix/Permissions netzwerkweit):** *Bereits gelöst.* LuckPerms nutzt die
   gemeinsame `s4_perms`-Datenbank + Live-Messaging über **alle** Server (Proxy, Lobby, Survival,
   Skyblock, RPG/Mining). Sobald **ein** Server die Gruppe setzt (`lp … promote autorank`), gilt
   der Rang **sofort netzwerkweit**. Hierfür ist nichts Neues nötig.
2. **Spielzeit-Messung:** In **Phase 1** wird die **Survival-lokale** Spielzeit (`StatsRequirements: PlayTime`)
   als Aufstiegs-Bedingung genutzt. Genau **ein** Server betreibt die Rang-Engine.

**Autorität = Survival.** Nur dort ist `Modules.yml → ranks: true`. Auf allen anderen Servern bleibt
das CMI-Rangmodul **aus**, sonst würden mehrere CMI-Instanzen gleichzeitig denselben LuckPerms-Track
promoten (Race Conditions auf `s4_perms`).

---

## 2. Die Leiter (14 Stufen)

| # | Rang | Spielzeit | Sekunden | Geld | Celebration-Tier |
|--:|------|----------:|---------:|-----:|:----------------:|
| 1 | Lauch | 1 h | 3.600 | 1.000 | normal |
| 2 | Knecht | 5 h | 18.000 | 5.000 | normal |
| 3 | Chiller | 12 h | 43.200 | 10.000 | normal |
| 4 | Ticker | 24 h | 86.400 | 25.000 | normal |
| 5 | Hustler | 48 h | 172.800 | 50.000 | mid |
| 6 | Macher | 100 h | 360.000 | 100.000 | mid |
| 7 | Türsteher | 150 h | 540.000 | 150.000 | mid |
| 8 | Bratan | 250 h | 900.000 | 250.000 | mid |
| 9 | Bre | 400 h | 1.440.000 | 400.000 | mid |
| 10 | Ehrenmann | 600 h | 2.160.000 | 750.000 | high |
| 11 | Löwe | 800 h | 2.880.000 | 1.000.000 | high |
| 12 | Maschine | 1.000 h | 3.600.000 | 2.000.000 | high |
| 13 | BABA | 1.250 h | 4.500.000 | 5.000.000 | god |
| 14 | MainCharacter | 1.500 h | 5.400.000 | 10.000.000 | god |

Basis-Stufe ist `default` (NPC) ohne Bedingung. Jede Stufe führt beim Erreichen aus:

- `asConsole! lp user [playerName] promote autorank` — schiebt die LuckPerms-Gruppe eine Stufe weiter
- `asConsole! cmi money give [playerName] <Betrag>` — Geldbelohnung
- `asConsole! tycoon_celebrate_rankup [playerName] <Rang> <Tier>` — Effekte/Sounds (Skript)
- `asConsole! tycoon_msg [playerName] <Nachricht>` — persönliche Chat-Nachricht

Quelle der Schwellen/Belohnungen: der frühere Prototyp
`survival/plugins/CMILib/FileBackups/2025-12-23 20-50-33 Ranks.yml`.

---

## 3. Wie CMI die aktuelle Stufe erkennt

Die LuckPerms-Gruppen des `autorank`-Tracks erben aufeinander
(`lauch ← knecht ← … ← maincharacter`). Damit CMI die **aktuelle** Sprosse eines Spielers kennt,
trägt jede Track-Gruppe einen Erkennungs-Node **`cmi.rank.<gruppe>`**. Weil ein Spieler durch die
Vererbung mehrere dieser Nodes hält, ist in `config.yml` **`Ranks.PermissionCheck: true`** gesetzt —
CMI wählt dann die Stufe mit dem **höchsten Gewicht** (= die tatsächliche aktuelle Stufe).

Die Rangnamen in `Settings/Ranks.yml` sind **identisch** mit den LuckPerms-Gruppennamen
(`default`, `lauch`, `knecht`, …, `maincharacter`), damit `cmi.rank.<name>` eindeutig passt.

---

## 4. Geänderte Dateien (im Repo)

| Datei | Änderung |
|-------|----------|
| `survival/plugins/CMI/Settings/Ranks.yml` | 14-Stufen-Zeitleiter im aktuellen CMI-Schema |
| `survival/plugins/CMI/Settings/Modules.yml` | `ranks: true` (Engine nur auf Survival) |
| `survival/plugins/CMI/config.yml` | `Ranks.PermissionCheck: true` |
| `lobby/plugins/CMI/Settings/Modules.yml` | `ranks: false` (Demo-Ränge entschärft) |
| `survival/plugins/Skript/scripts/rankup.sk` | aus `-rankup.sk` aktiviert (Celebration) |
| `survival/plugins/LuckPerms/meine_raenge.json.gz` | `cmi.rank.<name>`-Nodes an den Track-Gruppen; verwaisten `autorank`-Node auf `default` entfernt |
| `survival/plugins/Autorank/` | **entfernt** |

---

## 5. Manuelle / betriebliche Schritte (außerhalb des Repos)

Die **aktive** LuckPerms-Konfiguration liegt in der MariaDB (`s4_perms`), **nicht** im Repo
(die Datei `meine_raenge.json.gz` ist ein Export/Backup-Snapshot). Der Repo-Export ist bereits
aktualisiert; auf dem **Live-Server** müssen die Erkennungs-Nodes einmalig gesetzt werden:

```
lp group lauch         permission set cmi.rank.lauch true
lp group knecht        permission set cmi.rank.knecht true
lp group chiller       permission set cmi.rank.chiller true
lp group ticker        permission set cmi.rank.ticker true
lp group hustler       permission set cmi.rank.hustler true
lp group macher        permission set cmi.rank.macher true
lp group tuersteher    permission set cmi.rank.tuersteher true
lp group bratan        permission set cmi.rank.bratan true
lp group bre           permission set cmi.rank.bre true
lp group ehrenmann     permission set cmi.rank.ehrenmann true
lp group loewe         permission set cmi.rank.loewe true
lp group maschine      permission set cmi.rank.maschine true
lp group baba          permission set cmi.rank.baba true
lp group maincharacter permission set cmi.rank.maincharacter true

# Verwaisten Autorank-Node aufräumen (optional):
lp group default permission unset autorank
```

Alternativ statt der Einzelbefehle: `/lp import meine_raenge` (Achtung — importiert den kompletten
Export, überschreibt ggf. zwischenzeitliche Live-Änderungen).

### 5.1 Spielzeit-Quelle prüfen
`config.yml → PlayTimeFromStats: true` — CMI liest die Spielzeit aus den Vanilla-Statistiken.
Dafür muss in `survival/spigot.yml` das Speichern der Stats aktiv sein: `stats: disable-saving: false`.

### 5.2 Bestehende Spieler nachbewerten (wichtig!)
CMI-`AutoRankUp` steigt i. d. R. **eine Stufe pro Prüfintervall** (`config.yml → Ranks.AutoRankUp`,
aktuell: allgemein alle 60 s, pro Spieler alle 120 s). Ein Veteran mit z. B. 1.500 h würde sonst
viele Intervalle „hochklettern" und dabei **alle** Geld-/Celebration-Belohnungen nacheinander erhalten.

**Empfohlene Reihenfolge:**
1. Erst die `cmi.rank.<name>`-Nodes setzen (5.).
2. Bestehende Spieler **einmalig** per Bulk auf ihre korrekte Sprosse setzen (an ihrer bekannten
   Spielzeit ausgerichtet), z. B. `lp user <name> parent set <gruppe>` **bevor** `ranks: true`
   greift — so überspringt CMI das Nachzahlen aller Zwischenstufen.
3. Danach zählt nur noch der reguläre Fortschritt.

Wird dieser Schritt ausgelassen, klettern Online-Veteranen nach Aktivierung automatisch hoch und
erhalten die Belohnungen jeder Stufe (ggf. gewünschtes „Retro-Reward", aber hohe Auszahlungssumme +
viele Celebrations).

### 5.3 Autorank-JAR entfernen
Nach erfolgreicher Verifikation von Phase 1 die **Autorank-JAR** vom Survival-Server entfernen
(`plugins/Autorank-*.jar`). Bis dahin für Rollback behalten (siehe 8.).

---

## 6. AFK-Verhalten

Mit `PlayTimeFromStats: true` nutzt die Bedingung die **Vanilla-Spielzeit** — diese zählt auch
AFK-Zeit mit (Vanilla kennt kein AFK). CMIs `Afk.stopPlaytime: true` betrifft nur CMIs **eigene**
Spielzeit-Zählung.

- **AFK soll mitzählen (Standard, einfachste Migration):** nichts ändern.
- **AFK soll NICHT mitzählen (wie früher bei Autorank):** `PlayTimeFromStats: false` setzen; CMI nutzt
  dann seine eigene Spielzeit (respektiert `Afk.stopPlaytime`/`ExcludeAfk`). Trade-off: CMIs eigene
  Historie kann von der Vanilla-Spielzeit abweichen.

---

## 7. Reine Spielzeit-Geld-Meilensteine (früher Autorank `Paths.yml`)

Autorank vergab zusätzlich reine Spielzeit-Prämien (1 h=500, 5 h=5.000, 24 h=25.000, 72 h=100.000,
168 h=500.000, jeweils an `tycoon_erde` gebunden). Diese sind durch die **Geld-pro-Rangaufstieg**-Belohnungen
der neuen Leiter **weitgehend redundant** (die Schwellen 1 h/5 h/24 h fallen mit Lauch/Knecht/Ticker
zusammen). Sie wurden daher **nicht** übernommen, um Doppel-Auszahlungen zu vermeiden.

Falls eigenständige Spielzeit-Meilensteine dennoch gewünscht sind, lassen sie sich 1:1 in
`survival/plugins/CMI/Settings/PlayTimeRewards.yml` nachbilden (`PayFor`-Einträge) und über
`config.yml → PlaytimeRewards.Enabled: true` aktivieren.

---

## 8. Test & Rollback

**Test (Test-Server empfohlen):**
- Aufstieg bei Erreichen der Schwelle; korrekter Wechsel der LuckPerms-`autorank`-Gruppe.
- Prefix/Permissions **auf Lobby/Skyblock/Mining sichtbar** (netzwerkweit über `s4_perms`).
- Celebration-Effekte + Geld + Chat-Nachricht laufen.
- Keine Doppel-Promotes; nur **Survival** hat `ranks: true`.
- Verhalten des `tycoon`-Tracks unverändert.

**Rollback:**
- `Modules.yml → ranks: false` auf Survival deaktiviert die Engine jederzeit.
- Diese Änderung per `git revert` zurücknehmen (stellt u. a. `survival/plugins/Autorank/` wieder her)
  und die Autorank-JAR erneut einspielen.

---

## 9. Ausblick: Phase 2 (optional, echte Netzwerk-Spielzeit)

Nur nötig, wenn Spielzeit auf **Lobby/Skyblock/Mining** wirklich mitzählen soll. Engine/Track/Commands
bleiben identisch — es wird lediglich die Anforderungszeile pro Rang von `StatsRequirements: PlayTime`
auf einen **netzwerkweiten PlaceholderAPI-Wert** umgestellt (`placeholder %…% >= X`). Mögliche Quellen:

- **A) Aggregat über LuckPerms-Meta:** jeder Server schreibt periodisch seine CMI-Playtime in eine
  LP-Meta (`meta.playtime_<server>`); ein Summen-Placeholder speist die Anforderung. Nutzt vorhandene
  Infrastruktur, keine geteilte CMI-DB, etwas Skript-Glue.
- **B) Dedizierter Netzwerk-Playtime-Tracker:** sauberste „echte" Summe in MariaDB, aber ein
  zusätzliches Plugin.
- **C) CMI-Bungee + gemeinsame CMI-MySQL:** von Zrips ausdrücklich abgeraten → **nicht empfohlen**.
