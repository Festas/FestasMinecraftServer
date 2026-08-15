# Minigames — Server-Konfigurationen (Gerüst)

> **🟡 Geplant / Aufbauphase (26.2).** Nachfolger-Server für die auslaufenden MMO-Server (siehe
> [docs/NEW_SERVERS.md](../docs/NEW_SERVERS.md)). Dieses Verzeichnis ist ein **Gerüst** — die
> Plugin-Konfigurationen werden hier iterativ ergänzt.

Casual-Server mit rotierenden Minispielen (BedWars/SkyWars, Parkour, Spleef, Arcade).

## Struktur

```
minigames/
└── plugins/     # Plugin-Konfigurationen (analog zu lobby/ und survival/)
```

Die Plugin-Konfigurationen werden — wie bei den aktiven Servern — pro Plugin unter `plugins/<PluginName>/`
abgelegt. Plugin-JARs werden **nicht** eingecheckt (siehe `.gitignore`).

## Konzept & Plugin-Stack

- Konzept: [docs/minigames/README.md](../docs/minigames/README.md)
- Plugin-Shortlist (26.2): [docs/NEW_SERVERS.md → 3.2](../docs/NEW_SERVERS.md#32-minigames-spezifisch)
