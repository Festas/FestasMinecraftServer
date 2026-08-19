# Workflow-Templates - MinecraftMMO

Workflow-Templates für häufige Aufgaben auf den aktiven Servern.

---

## 1. Neues Minion erstellen (JetsMinions)

### Vorbereitung
- [ ] Minion-Typ definieren (Mining, Farming, Combat, etc.)
- [ ] Sammel-Item festlegen
- [ ] Upgrade-Stufen planen
- [ ] Fuel-Typ festlegen (optional)

### Schritte

#### 1.1 Minion-Definition
```yaml
# Pfad: plugins/JetsMinions/minions/
# Dateiname: MINION_TYPE.yml

# Minion Configuration
# - Type (Mining, Farming, etc.)
# - Collected Item
# - Speed per Tier
# - Storage Size
```

#### 1.2 Upgrade-Tiers
- [ ] Tier 1 Stats
- [ ] Tier 2 Stats
- [ ] ...
- [ ] Max Tier Stats

#### 1.3 Crafting-Recipe (optional)
- [ ] Crafting-Zutaten
- [ ] Unlock-Requirements

#### 1.4 Testing
- [ ] Minion platzieren
- [ ] Sammlung testen
- [ ] Upgrades testen
- [ ] Storage testen
- [ ] Fuel-Mechanik testen

#### 1.5 Dokumentation
- [ ] Minion-Beschreibung
- [ ] Unlock-Bedingungen
- [ ] Upgrade-Kosten
- [ ] Optimale Nutzung

### Notizen
_Platz für minion-spezifische Notizen_

---

## 2. Custom Texture/Model hinzufügen (Oraxen/ModelEngine)

### Vorbereitung
- [ ] Model/Texture erstellen (Blockbench, etc.)
- [ ] Geeigneten Slot/Item bestimmen
- [ ] CustomModelData ID vergeben

### Schritte

#### 2.1 Oraxen (für Items/Blöcke)
```yaml
# Pfad: plugins/Oraxen/items/
# Dateiname: custom_items.yml

# Custom Item mit Texture
```

#### 2.2 ModelEngine (für Mobs/NPCs)
```yaml
# Pfad: plugins/ModelEngine/models/
# Dateiname: MODEL_NAME.yml

# Model-Definition
```

#### 2.3 Resourcepack aktualisieren
- [ ] Model/Texture zu Pack hinzufügen
- [ ] Pack neu generieren
- [ ] Pack hochladen
- [ ] Server-Config aktualisieren

#### 2.4 Testing
- [ ] Item/Model in-game testen
- [ ] Animationen testen (falls vorhanden)
- [ ] Clientseitige Überprüfung

#### 2.5 Dokumentation
- [ ] Model-ID dokumentieren
- [ ] CustomModelData notieren

### Notizen
_Platz für model-spezifische Notizen_

---

## 3. Server-Update durchführen

### Vorbereitung
- [ ] Ankündigung an Spieler (Discord, In-Game)
- [ ] Backup erstellen (Welt, Datenbank, Configs)
- [ ] Update-Notes lesen
- [ ] Kompatibilität prüfen

### Schritte

#### 3.1 Pre-Update
- [ ] Wartungsmodus aktivieren
- [ ] Spieler kicken
- [ ] Server stoppen
- [ ] Backup verifizieren

#### 3.2 Update
- [ ] Plugin(s) updaten
- [ ] Config-Änderungen übernehmen
- [ ] Neue Features konfigurieren

#### 3.3 Testing
- [ ] Server starten
- [ ] Console auf Errors prüfen
- [ ] Schnelltest wichtiger Features
- [ ] Performance-Check

#### 3.4 Post-Update
- [ ] Wartungsmodus deaktivieren
- [ ] Spieler informieren
- [ ] Monitoring verstärken
- [ ] Changelog posten

#### 3.5 Rollback (falls Probleme)
- [ ] Server stoppen
- [ ] Backup wiederherstellen
- [ ] Alte Version starten
- [ ] Post-Mortem

### Notizen
_Update-spezifische Notizen_

---

## Template für eigene Workflows

### Vorbereitung
- [ ] ...

### Schritte

#### Schritt 1
...

#### Schritt 2
...

### Testing
- [ ] ...

### Dokumentation
- [ ] ...

### Notizen
_..._

---

**Letzte Aktualisierung:** 2026-04-10

**Hinweis:** Diese Templates sind Leitfäden - nicht jeder Schritt ist für jeden Use-Case relevant. Anpassen nach Bedarf!
