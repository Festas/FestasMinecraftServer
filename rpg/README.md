# rpg — Server-Konfigurationen (Mining, recycelt)

> **🟡 Geplant / Aufbauphase (26.2).** Dieser Ordner ist der **recycelte Slot des alten RPG-Servers** und
> wird zum neuen **Mining**-Server (siehe [docs/NEW_SERVERS.md](../docs/NEW_SERVERS.md)). Der **Pfad/Server-Name
> bleibt bewusst `rpg`**, damit der bestehende Server (Velocity-Routing, MariaDB, Redis, HuskSync,
> Resource-Pack, LuckPerms-Kontext) **wiederverwendet** statt neu aufgesetzt wird.
>
> Die noch vorhandenen **Alt-RPG-Plugin-Configs** (MythicMobs, MythicDungeons, BetonQuest, Citizens …) sind
> **Archiv** und werden **schrittweise durch Mining-Configs ersetzt**.

Casual-Server rund um **Abbau-Zonen**: Mit einer besonderen Spitzhacke werden Blöcke abgebaut; immer
stärkere Spitzhacken bauen **mehr Blöcke auf einmal** ab. Neue **Zonen mit anderen Blöcken** werden nach
und nach freigeschaltet.

## Struktur

```
rpg/
└── plugins/     # Plugin-Konfigurationen (analog zu lobby/ und survival/)
```

Die Plugin-Konfigurationen werden — wie bei den aktiven Servern — pro Plugin unter `plugins/<PluginName>/`
abgelegt. Plugin-JARs werden **nicht** eingecheckt (siehe `.gitignore`).

## Konzept & Plugin-Stack

- Konzept: [docs/mining/README.md](../docs/mining/README.md)
- Plugin-Shortlist (26.2): [docs/NEW_SERVERS.md → 3.2](../docs/NEW_SERVERS.md#32-mining-spezifisch)
- Archiv des alten RPG-Spielmodus (Referenz): [docs/rpg/README.md](../docs/rpg/README.md)
