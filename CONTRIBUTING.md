# Beitragsrichtlinien

Kurze Richtlinien für Beiträge zu diesem Minecraft-Netzwerk-Konfigurations-Repository.

---

## Repository-Struktur

```
proxy/plugins/          # Velocity-Plugins (TAB, MiniMOTD, LibertyBans, Geyser, …)
lobby/plugins/          # Lobby-Plugins (CMI, DeluxeMenus, Skript, Oraxen, …)
survival/plugins/       # Survival-Plugins (NextGens, Jobs, Rankup, PlotSquared, …)
skyblock/plugins/       # Skyblock-Plugins (SuperiorSkyblock2, JetsMinions, …) — Umbau
prison/plugins/         # Mining/Prison-Plugins (Zonen-Kern, WorldGuard, …) — Aufbau
docs/                   # Dokumentation
```

Die Plugin-Configs liegen jeweils direkt unter `<server>/plugins/<PluginName>/`.

---

## YAML-Syntax

- **2 Leerzeichen** für Einrückungen — keine Tabs!
- Strings mit Sonderzeichen (insbesondere `&`) in **einfache Anführungszeichen** setzen.
- Listen immer mit `-` kennzeichnen.

```yaml
# ✅ Korrekt
some_key:
  display: '&6Text mit Farbe'
  items:
    - 'DIAMOND'
    - 'GOLD_INGOT'

# ❌ Falsch (Tabs, fehlende Quotes)
some_key:
	display: &6Text mit Farbe
```

---

## Commit Messages

Format: `Typ: Kurzbeschreibung`

| Typ | Verwendung |
|-----|-----------|
| `Add:` | Neue Datei / neues Feature |
| `Update:` | Änderung an bestehender Datei |
| `Fix:` | Fehlerbehebung |
| `Remove:` | Datei/Eintrag entfernt |
| `Docs:` | Nur Doku-Änderungen |
| `Config:` | Plugin-Config-Anpassung |

Beispiele:
```
Add: Survival shop config für Iron-Tier
Update: Lobby DeluxeMenus server_selector — Mining-Eintrag ergänzt
Fix: CMI autorank track Stufenfolge korrigiert
Config: NextGens Generator-Tier 10 Sell-Price angepasst
```

---

## Git-Workflow

```bash
git checkout -b feature/kurze-beschreibung
# … Änderungen vornehmen …
git add .
git commit -m "Add: Neue Survival-Config"
git push origin feature/kurze-beschreibung
# → Pull Request erstellen
```

---

## Testing

Vor dem Commit:
1. YAML-Syntax prüfen (z. B. mit `python3 -c "import yaml; yaml.safe_load(open('datei.yml'))"`)
2. Auf dem Testserver laden und kurz überprüfen
3. Keine Credentials / Passwörter committen (stets `CHANGE_ME`-Platzhalter verwenden)

---

## Fragen?

- Plugin-Docs konsultieren
- Beispiel-Configs im jeweiligen `plugins/`-Ordner vergleichen
- Repository-Owner fragen
