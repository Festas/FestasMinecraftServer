# MiniMOTD

**Server-Listen-MOTD & Icon · nur Proxy · netzwerkweit · lokal (keine DB)**

## Zweck
MiniMOTD steuert die MOTD (MiniMessage-Format), die Spielerzahl-Anzeige und das Server-Icon in der
Multiplayer-Serverliste – am Proxy für das gesamte Netzwerk.

## Wo (Server & Config-Pfade)
Nur am Proxy: `proxy/plugins/minimotd-velocity/`
- `main.conf` – MOTD-Texte, Player-Count-Optionen
- `plugin_settings.conf` – Plugin-Optionen
- `icons/` – Server-Icons · `extra-configs/` – zusätzliche MOTD-Profile

## Storage & Secrets
Lokal, keine DB, keine Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **MOTD-Text/Farben** → `main.conf` (MiniMessage-Syntax).
- **Icon** → PNG (64×64) in `icons/` legen und in `main.conf` referenzieren.
- **Fake/aggregierte Spielerzahl** → `main.conf` (Player-Count-Sektion).

## Cross-Server / Gotchas
- Öffentliche Website-Spielerzahlen kommen **separat** aus `tools/plan-players-export/` – MiniMOTD betrifft
  nur die In-Client-Serverliste.
- Teil des Proxy-Bündels mit SkinsRestorer + ForceResourcepacks (gemeinsamer Custom Agent „proxy-network").

## Custom Agent
[`.github/agents/proxy-network.agent.md`](../../.github/agents/proxy-network.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [SkinsRestorer.md](SkinsRestorer.md) · [ForceResourcepacks.md](ForceResourcepacks.md)
