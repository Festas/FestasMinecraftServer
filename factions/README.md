# Factions — Server-Konfigurationen (Gerüst)

> **🟡 Geplant / Aufbauphase (26.2).** Nachfolger-Server für die auslaufenden MMO-Server (siehe
> [docs/NEW_SERVERS.md](../docs/NEW_SERVERS.md)). Dieses Verzeichnis ist ein **Gerüst** — die
> Plugin-Konfigurationen werden hier iterativ ergänzt.

Social/PvP-Server mit Gilden-/Team-getriebenem Land-Claiming und PvP. Nutzt
[CrossCraft-Guilds](../crosscraft-guilds/README.md) als soziale Klammer.

## Struktur

```
factions/
└── plugins/     # Plugin-Konfigurationen (analog zu lobby/ und survival/)
```

Die Plugin-Konfigurationen werden — wie bei den aktiven Servern — pro Plugin unter `plugins/<PluginName>/`
abgelegt. Plugin-JARs werden **nicht** eingecheckt (siehe `.gitignore`).

## Konzept & Plugin-Stack

- Konzept: [docs/factions/README.md](../docs/factions/README.md)
- Plugin-Shortlist (26.2): [docs/NEW_SERVERS.md → 3.3](../docs/NEW_SERVERS.md#33-factions-spezifisch)
