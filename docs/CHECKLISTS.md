# Checklisten-Templates - MinecraftMMO

Wiederverwendbare Checklisten für Config-Erstellung, Balance und Testing der aktiven Server.

---

## 1. Content-Erstellungs-Checkliste

> Spezifische Checklisten (Item, Mob, Quest, Dungeon, Klasse) gehören zum auslaufenden RPG-Server und wurden entfernt. Hier stehen generische Server-Config-Checks.

### Neue Plugin-Config

- [ ] YAML-Syntax geprüft (keine Tabs, korrekte Anführungszeichen)
- [ ] Credentials als `CHANGE_ME`-Platzhalter — nie echte Passwörter committen
- [ ] Auf Test-Instanz geladen, keine Console-Errors
- [ ] Wichtige Features kurz in-game verifiziert
- [ ] Backup vor aktivem Roll-out erstellt

### Neue Skript-Datei (Skript-Plugin)

- [ ] Skript lädt ohne Fehler (`/sk reload <datei>`)
- [ ] Trigger/Events korrekt definiert
- [ ] Kein unendlicher Loop, keine Performance-Killer
- [ ] Edge Cases geprüft (leeres Inventar, offline Spieler, etc.)
- [ ] In-game getestet

### Neue DeluxeMenus-GUI

- [ ] GUI öffnet ohne Errors
- [ ] Alle Button-Actions getestet
- [ ] Platzhalter werden korrekt aufgelöst (PAPI)
- [ ] Rechte (`permission:`) korrekt gesetzt
- [ ] Bedrock-Kompatibilität (Geyser) geprüft

---

## 2. Balance-Checkliste

### Allgemeines Economy-Balancing (Survival / Tycoon)

- [ ] Neue Sell-Preise verglichen mit bestehenden Generator-Tiers
- [ ] Kein zu schnelles Economy-Wachstum (Inflations-Check)
- [ ] Rankup-Kosten angemessen für erwartete Spielzeit

---

## 3. Testing-Checkliste

### Pre-Release Testing

#### Funktionalität
- [ ] Feature funktioniert wie geplant
- [ ] Keine Console-Errors
- [ ] Keine Client-Crashes
- [ ] Keine Lag-Spikes

#### Balance
- [ ] Balance-Checkliste durchgearbeitet (siehe oben)
- [ ] Feedback von Test-Spielern eingeholt
- [ ] Anpassungen vorgenommen

#### Integration
- [ ] Kompatibel mit existierendem Content
- [ ] Keine Konflikte mit anderen Features
- [ ] Passt ins Gesamt-Balancing

#### Edge Cases
- [ ] Extremfälle getestet (sehr niedrig/hoch Level)
- [ ] Multiple Spieler gleichzeitig getestet
- [ ] Server-Restart getestet (Persistence)

---

### Post-Release Monitoring

#### Performance
- [ ] Server-Performance überwacht (TPS)
- [ ] Keine Memory-Leaks
- [ ] Database-Performance OK

#### Player Feedback
- [ ] Feedback gesammelt (Chat, Discord)
- [ ] Bug-Reports gesammelt
- [ ] Balance-Feedback gesammelt

#### Metrics
- [ ] Usage-Statistiken geprüft (Plan)
- [ ] Drop-Rates verifiziert
- [ ] Clear-Rates geprüft (für Dungeons)

#### Adjustments
- [ ] Hot-Fixes deployed (falls nötig)
- [ ] Balance-Tweaks vorgenommen
- [ ] Bugs gefixt

---

## 4. Server-Update-Checkliste

### Pre-Update
- [ ] Update-Notes gelesen
- [ ] Breaking-Changes identifiziert
- [ ] Config-Änderungen notwendig?
- [ ] Plugin-Kompatibilität geprüft
- [ ] Backup erstellt (Welt, DB, Configs)
- [ ] Spieler informiert (Ankündigung)
- [ ] Wartungsfenster geplant

### Update-Prozess
- [ ] Wartungsmodus aktiviert
- [ ] Spieler gekickt
- [ ] Server gestoppt
- [ ] Plugin(s) ersetzt
- [ ] Config(s) aktualisiert
- [ ] Server gestartet
- [ ] Console auf Errors geprüft

### Post-Update Testing
- [ ] Server startet ohne Errors
- [ ] Kritische Features getestet
- [ ] Performance-Check (TPS, RAM)
- [ ] Player-Login getestet
- [ ] Database-Connectivity geprüft

### Release
- [ ] Wartungsmodus deaktiviert
- [ ] Spieler informiert (Update abgeschlossen)
- [ ] Changelog gepostet
- [ ] Monitoring verstärkt (erste Stunden)

### Rollback (falls nötig)
- [ ] Server gestoppt
- [ ] Backup wiederhergestellt
- [ ] Alte Plugin-Version restauriert
- [ ] Server gestartet
- [ ] Spieler informiert
- [ ] Post-Mortem-Analyse

---

## 5. Content-Release-Checkliste

### Vor Release

#### Content-Qualität
- [ ] Alle Content-Pieces fertiggestellt
- [ ] Testing abgeschlossen
- [ ] Balance-Check abgeschlossen
- [ ] Keine bekannten Bugs

#### Dokumentation
- [ ] Interne Docs aktualisiert
- [ ] Changelog geschrieben
- [ ] Player-Guide geschrieben (falls nötig)

#### Marketing/Ankündigung
- [ ] Discord-Ankündigung vorbereitet
- [ ] In-Game-Ankündigung vorbereitet
- [ ] Screenshots/Videos erstellt
- [ ] Release-Datum festgelegt

### Release

#### Deployment
- [ ] Config-Files auf Server kopiert
- [ ] Plugins reloaded
- [ ] Content in-game verifiziert

#### Ankündigung
- [ ] Discord-Post veröffentlicht
- [ ] In-Game-Broadcast gesendet
- [ ] Changelog veröffentlicht

### Post-Release

#### Monitoring
- [ ] Server-Performance überwacht
- [ ] Player-Feedback gesammelt
- [ ] Bug-Reports gesammelt

#### Support
- [ ] Player-Fragen beantwortet
- [ ] Bugs gefixt (falls vorhanden)
- [ ] Hot-Fixes deployed (falls nötig)

#### Analysis
- [ ] Usage-Statistiken analysiert
- [ ] Success-Metrics geprüft
- [ ] Lessons-Learned dokumentiert

---

## 6. Sicherheits-Checkliste

### Neue Configs

- [ ] Keine Passwörter im Klartext
- [ ] Keine API-Keys committed (Git)
- [ ] Database-Credentials sicher gespeichert
- [ ] Permissions korrekt konfiguriert

### Neue Commands

- [ ] Admin-Commands haben Permissions
- [ ] Exploit-Potenzial geprüft
- [ ] Input-Validation vorhanden

### Neue Items/Mobs

- [ ] Keine Item-Duplication möglich
- [ ] Keine Economy-Exploits
- [ ] Keine Performance-Killer (z.B. unendliche Loops)

---

## Template für eigene Checklisten

### Kategorie 1
- [ ] Item 1
- [ ] Item 2
- [ ] Item 3

### Kategorie 2
- [ ] Item 1
- [ ] Item 2

---

**Letzte Aktualisierung:** 2026-04-10

**Hinweis:** Diese Checklisten sind als Richtlinien gedacht - nicht jeder Punkt ist für jeden Content-Typ relevant. Anpassen nach Bedarf!
