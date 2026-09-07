# ForceResourcepacks

**Resourcepack-Erzwingung · nur Proxy · netzwerkweit · lokal (keine DB)**

## Zweck
ForceResourcepacks schickt/erzwingt beim Join am Proxy ein Resourcepack (z. B. das Oraxen-Pack), damit
alle Spieler netzwerkweit dieselben Custom-Texturen sehen.

## Wo (Server & Config-Pfade)
Nur am Proxy: `proxy/plugins/forceresourcepacks/`
- `config.yml` – Pack-URL/Hash, Erzwingungsverhalten, Prompt
- `key.yml` – Signierung/Schlüssel · `languages/` – Nachrichten (`joined.yml` = Laufzeit)

## Storage & Secrets
Lokal, keine DB. `key.yml` ist ein Plugin-Schlüssel (kein injizierbares Deploy-Secret); nicht in Logs/PRs leaken.

## Wichtige Einstellungen / typische Aufgaben
- **Pack-URL + SHA-1-Hash** → `config.yml` (Hash nach jeder Pack-Änderung aktualisieren, sonst laden
  Clients das alte Pack).
- **Erzwingen ja/nein**, Kick-bei-Ablehnung, Prompt-Text → `config.yml` / `languages/`.

## Cross-Server / Gotchas
- Pack-Quelle ist i. d. R. das **Oraxen**-Pack – bei Oraxen-Änderungen Hash hier nachziehen.
- Läuft am Proxy vor der Backend-Verbindung – Backends müssen kein eigenes Force-Pack erzwingen.
- Teil des Proxy-Bündels (gemeinsamer Custom Agent „proxy-network").

## Custom Agent
[`.github/agents/proxy-network.agent.md`](../../.github/agents/proxy-network.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [Oraxen.md](Oraxen.md) · [MiniMOTD.md](MiniMOTD.md) · [SkinsRestorer.md](SkinsRestorer.md)
