# XPrisonArmors

**Custom-Rüstungen (rpg/mining) · nur rpg · lokal**

## Zweck
XPrisonArmors bietet spezielle Rüstungssets mit Boni/Effekten für den Prison-/Mining-Server (Progression
über Ausrüstung).

## Wo (Server & Config-Pfade)
Nur **rpg**: `rpg/plugins/XPrisonArmors/`
- `config.yml` – globale Optionen
- `armors.yml` – **Rüstungssets** (Boni, Anforderungen, Rezepte)
- `messages.yml`

## Storage & Secrets
Lokal, keine DB, keine Secrets.

## Wichtige Einstellungen / typische Aufgaben
- **Sets/Boni/Anforderungen** → `armors.yml`.
- Boni mit X-Prison-Progression (Enchants, Multiplikatoren) balancen, damit sie sich nicht stapeln/brechen.

## Cross-Server / Gotchas
- **rpg-only** (= „mining"). Teil der X-Prison-Suite → gemeinsamer Custom Agent „prison".

## Custom Agent
[`.github/agents/prison.agent.md`](../../.github/agents/prison.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [X-Prison.md](X-Prison.md) · [XRobots.md](XRobots.md)
