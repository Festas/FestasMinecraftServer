# LibsDisguises

**Verkleidungen · survival / rpg(mining) · lokal**

## Zweck
LibsDisguises erlaubt das Verkleiden von Spielern/Entities als andere Mobs/Spieler – für Kosmetik,
Events oder Gameplay-Features.

## Wo (Server & Config-Pfade)
Auf **survival** und **rpg(mining)**: `<server>/plugins/LibsDisguises/`
- `configs/` – Konfiguration (Berechtigungen, Optionen)
- `Skins/`, `SavedSkins/`, `internal/`, `Translations/` = Daten/Übersetzungen

## Storage & Secrets
Lokal, keine DB, keine Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Optionen/Permissions/Limits** → `configs/`.
- Disguise-Rechte über LuckPerms steuern (welche Ränge dürfen welche Disguises).

## Cross-Server / Gotchas
- Vorhanden auf **survival + rpg**, **nicht** auf lobby/skyblock.
- Kein dediziertes Agent-Profil (Utility) – Anpassungen laufen bei Bedarf über den jeweiligen
  Server-Kontext (z. B. `progression`/`prison` je nach Feature).

## Custom Agent
Kein dediziertes Agent-Profil (Utility).

## Referenzen
[README (Wissensbasis)](README.md) · [LuckPerms.md](LuckPerms.md)
