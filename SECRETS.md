# Secrets und Deployment-Referenz

Dieses Dokument beschreibt die Secret- und Deploy-Konventionen für dieses Repository. Das zentrale Prinzip: Keine echten Passwörter, Tokens oder Zugangsdaten ins Repository committen. Alle sensiblen Werte müssen über GitHub Actions Secrets, injizierte Platzhalter oder serverseitige Konfigurationen bereitgestellt werden.

## Grundregeln

- Keine echten Daten in `*.yml`, `*.yaml`, `*.toml`, `*.properties` oder `*.env`-Dateien committen
- Für Platzhalter gelten Muster wie `__SECRET_NAME__` oder `CHANGE_ME`
- Die Deploy-Workflows injizieren die Werte zur Laufzeit in die Server-Konfigurationen
- Wenn ein Secret ändert, auch die betroffenen Deploy- und Doku-Schritte prüfen

## Wichtige Secret-Typen

### SSH / Serverzugang

- `SSH_HOST`: Zielhost des Deployment-Servers
- `SSH_USER`: SSH-Benutzer
- `SSH_PRIVATE_KEY`: privater SSH-Key

### GitHub / Registry

- `PERSONAL_TOKEN`: Token für Automationen und Commit-Push-Schritte
- `GHCR_TOKEN`: Token für Container-Registry-Deployments

### Netzwerk / Proxy

- `VELOCITY_FORWARDING_SECRET`: Secret für Velocity Modern Forwarding

### Serverpfade

- `SERVER_PATH_LOBBY`
- `SERVER_PATH_PROXY`
- `SERVER_PATH_RPG`
- `SERVER_PATH_SKYBLOCK`
- `SERVER_PATH_SURVIVAL`

Diese Werte enthalten den absoluten Pfad zum `plugins`-Ordner auf dem Zielserver.

## Sicherheitsrichtlinien

- `VELOCITY_FORWARDING_SECRET` muss auf Proxy und Backend identisch sein
- Fehlende Secrets sollten Deploy-Schritte bewusst verhindern, statt mit unvollständiger Konfiguration weiterzulaufen
- MySQL-, Redis- und andere Zugangsdaten nur als injizierte Variablen verwenden, nie hardcodiert im Repo
- Plugin-Configs sollten Platzhalter statt echter Anmeldedaten enthalten

## Beispiel-Multi-Secret

Ein Multi-Variable Secret kommt im `.env`-Format:

```env
PLAN_DB_HOST=db.example.com
PLAN_DB_PORT=3306
PLAN_DB_DATABASE=plan
PLAN_DB_USER=planuser
PLAN_DB_PASSWORD=geheimesPasswort
```

Typische Beispiele im Projekt:

- `PLAN_DB_ENV`
- `PLAN_RO_DB_ENV`
- `LUCKPERMS_DB_ENV`
- `LUCKPERMS_RO_DB_ENV`

Diese Variablen werden beim Deployment in die jeweiligen Plugin-Configs injiziert. Das Projekt behandelt Sonderzeichen im Passwort korrekt und vermeidet Shell-Parsing-Probleme.

## Deployment-Checkliste

Vor einem Deployment oder einer Push-Änderung prüfen:

1. alle Secrets sind gesetzt und gültig
2. betroffene Server-Konfigurationen sind ebenfalls auf dem aktuellen Stand
3. Workflows und Serverpfade passen zur aktuellen Infrastruktur
4. Logs und Synchronisierung wurden nach dem Deployment validiert

## Verweise

- `README.md`
- `QUICKREF.md`
- `CONTRIBUTING.md`
- `docs/OPERATIONS.md`
- `.github/workflows/`
