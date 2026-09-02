# Beitragsrichtlinien

Diese Richtlinien gelten für die Konfigurations- und Deployment-Repo des Festas Minecraft Networks. Das Ziel ist eine saubere, dokumentierte und sichere Konfigurationsbasis ohne veraltete Projektartefakte.

## Grundprinzipien

- Relevante Änderungen immer mit der aktuellen Server-Architektur abgleichen
- Keine echten Tokens, Passwörter, SSH-Schlüssel oder Secrets in das Repository committen
- Veraltete Bezeichnungen, alte Servernamen oder „Historie-Notizen“ nur dann behalten, wenn sie echte Betriebskontext liefern
- Wenn ein Pfad, ein Server oder ein Workflow geändert wird, auch die passende Doku anpassen

## Repository-Struktur

```text
.
├── .github/workflows/   # Deploys, Syncs, Maintenance
├── proxy/               # Velocity + Proxy-Plugins
├── lobby/               # Lobby
├── survival/            # Survival
├── skyblock/            # Skyblock
├── rpg/                 # Mining-/Prison-Backend
├── website/             # Landingpage
├── docs/                # Betrieb und Architektur
├── tools/               # Exporter und Wartung
├── nginx/               # Nginx-Konfigurationen
├── infra/               # Infrastruktur / Services
├── server-logs/         # Log-Artefakte
└── ...
```

## Konventionen für Configs

- Einrückung: 2 Leerzeichen, keine Tabs
- YAML-Strings mit Sonderzeichen in einfache Anführungszeichen setzen, wenn nötig
- Arrays immer mit `-` definieren
- Placeholder statt echte Werte verwenden, wenn ein Secret beim Deploy injiziert wird
- Dateien mit `__...__`-Platzhaltern nur als Deployment-Templates behandeln

Beispiel:

```yaml
some_key:
  display: '&6Farbiger Text'
  items:
    - 'DIAMOND'
    - 'GOLD_INGOT'
```

## Secrets und Sicherheit

Vor dem Commit prüfen:

- Keine Benutzer- oder Admin-Passwörter
- Keine SSH-Keys oder API-Keys
- Keine echten MySQL-, Redis- oder Velocity-Secrets
- Keine lokal erzeugten Log- oder Dump-Dateien mit committen

Die sichere Vorgehensweise ist: Secrets über GitHub Actions Secrets bzw. injizierte Platzhalter verwalten. Details stehen in `SECRETS.md`.

## Workflow für Änderungen

```bash
git checkout -b feature/kurze-beschreibung
# Änderungen vornehmen
# falls relevant: README / QUICKREF / SECRETS / docs aktualisieren

git add .
git commit -m "Update: Beschreibung der Änderung"
git push origin feature/kurze-beschreibung
```

## Validierung vor dem Merge

Vor dem Commit mindestens prüfen:

1. YAML-Syntax und Dateiformat prüfen
2. Betroffene deploybare Configs mit dem aktuellen Serverbau abgleichen
3. Referenzen auf veraltete Serverbezeichnungen bereinigen
4. Doku aktualisieren, wenn Struktur oder Deployment sich geändert haben

## Doku-Standards

Wenn eine Änderung die Repo-Struktur, Servernamen, Deploy-Prozesse oder Secret-Handling betrifft, müssen auch die betroffenen Root-Dokumente aktualisiert werden:

- `README.md`
- `QUICKREF.md`
- `README-website.md`
- `SECRETS.md`

## Fragen

Wenn die genaue Auswirkung einer Änderung unklar ist:

- aktuelle Datei im passenden `plugins/`-Ordner oder Root-Config vergleichen
- relevante Doku in `docs/` nachsehen
- bei Unsicherheit im Projektkontext Rücksprache mit dem Repository-Owner suchen
