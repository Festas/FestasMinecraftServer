# Mining — Server-Konfigurationen (Gerüst)

> **🟡 Geplant / Aufbauphase (26.2).** Einer der zwei neuen Server (siehe
> [docs/NEW_SERVERS.md](../docs/NEW_SERVERS.md)). Dieses Verzeichnis ist ein **Gerüst** — die
> Plugin-Konfigurationen werden hier iterativ ergänzt.

Casual-Server rund um **Abbau-Zonen**: Mit einer besonderen Spitzhacke werden Blöcke abgebaut; immer
stärkere Spitzhacken bauen **mehr Blöcke auf einmal** ab. Neue **Zonen mit anderen Blöcken** werden nach
und nach freigeschaltet.

## Struktur

```
mining/
└── plugins/     # Plugin-Konfigurationen (analog zu lobby/ und survival/)
```

Die Plugin-Konfigurationen werden — wie bei den aktiven Servern — pro Plugin unter `plugins/<PluginName>/`
abgelegt. Plugin-JARs werden **nicht** eingecheckt (siehe `.gitignore`).

## Konzept & Plugin-Stack

- Konzept: [docs/mining/README.md](../docs/mining/README.md)
- Plugin-Shortlist (26.2): [docs/NEW_SERVERS.md → 3.2](../docs/NEW_SERVERS.md#32-mining-spezifisch)
