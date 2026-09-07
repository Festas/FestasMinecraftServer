# WorldGuard (+ WorldEdit)

**Regionsschutz · lobby / survival / rpg(mining) · lokal (pro Welt)**

## Zweck
WorldGuard schützt Regionen und steuert Flags (PvP, Bau, Mob-Spawn, Greeting, …). WorldEdit ist die
zugrunde liegende Bearbeitungs-/Selektions-Engine (als Abhängigkeit gebündelt).

## Wo (Server & Config-Pfade)
Vorhanden auf **lobby, survival, rpg(mining)** (nicht auf skyblock – dort übernimmt SuperiorSkyblock2
den Inselschutz). `<server>/plugins/WorldGuard/`
- `config.yml` – globale Optionen
- `worlds/<welt>/regions.yml` – **Regionsdefinitionen je Welt** (Laufzeitdaten, i. d. R. nicht deployt)

## Storage & Secrets
Lokal, keine DB, keine Secrets. Regionsdaten liegen pro Welt unter `worlds/`.

## Wichtige Einstellungen / typische Aufgaben
- **Globale Flags/Defaults** → `config.yml` (z. B. Bau/PvP-Standards).
- **Region/Flag** in-game: `/rg flag <region> <flag> <wert>` (Regionsdaten werden serverseitig gespeichert).
- Lobby: Schutz gegen Bau/Interaktion im Hub; Survival/RPG: Spawn-Schutz, PvP-Zonen.

## Cross-Server / Gotchas
- **Skyblock nutzt WorldGuard nicht** – Inselrechte laufen über SuperiorSkyblock2-Rollen.
- Regionen sind welt-/servergebunden; nicht netzwerkweit. `worlds/`-Regionsdaten sind meist Serverstand
  und nicht im Repo gepflegt – Änderungen bevorzugt über `config.yml` bzw. dokumentierte Befehle.

## Custom Agent
[`.github/agents/worldguard.agent.md`](../../.github/agents/worldguard.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [Multiverse.md](Multiverse.md) · [SuperiorSkyblock2.md](SuperiorSkyblock2.md)
