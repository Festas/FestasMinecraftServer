# SkinsRestorer

**Skins (Offline/Proxy) · nur Proxy · netzwerkweit · Filestorage aktiv (DB dormant)**

## Zweck
SkinsRestorer stellt Spieler-Skins netzwerkweit bereit bzw. erlaubt Custom-Skins – wichtig hinter dem
Proxy mit `online-mode=false` auf den Backends.

## Wo (Server & Config-Pfade)
Nur am Proxy: `proxy/plugins/skinsrestorer/`
- `config.yml` – Storage & Optionen
- `locales/` – Nachrichten · `recommendations.json` – vorgeschlagene Skins

## Storage & Secrets
- **Aktiv: Filestorage** (lokal). `config.yml` enthält **dormante** Platzhalter
  `__SKINSRESTORER_DB_USER__` / `__SKINSRESTORER_DB_PASSWORD__` für optionalen MySQL-Betrieb –
  aktuell nicht genutzt, als Template erhalten.

## Wichtige Einstellungen / typische Aufgaben
- **Custom-/Default-Skins** und Verhalten → `config.yml`.
- Skin setzen: `/skin set <name>` (am Proxy).
- Nachrichten → `locales/`.

## Cross-Server / Gotchas
- Funktioniert zusammen mit Velocity **Modern Forwarding** (`online-mode=false` auf Backends,
  `prevent-proxy-connections=true`). Skin-Auflösung läuft **am Proxy**, nicht pro Backend.
- Teil des Proxy-Bündels (gemeinsamer Custom Agent „proxy-network").

## Custom Agent
[`.github/agents/proxy-network.agent.md`](../../.github/agents/proxy-network.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [MiniMOTD.md](MiniMOTD.md) · [ForceResourcepacks.md](ForceResourcepacks.md) · `proxy/velocity.toml`
