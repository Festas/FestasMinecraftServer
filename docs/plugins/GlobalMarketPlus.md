# GlobalMarketPlus

**Spieler-Markt/Auktionshaus · survival / skyblock / rpg(mining) · lokal SQLite (pro Server)**

## Zweck
GlobalMarketPlus ist der Spieler-zu-Spieler-Markt (Angebote einstellen/kaufen, Auktion). Vorhanden auf
den drei Gameplay-Servern.

## Wo (Server & Config-Pfade)
`<server>/plugins/GlobalMarketPlus/Config.yml` (+ Nachrichten/Menüs im Plugin-Ordner) auf **survival,
skyblock, rpg(mining)**.

## Storage & Secrets
- **Default: SQLite-Storage** (lokal). `Split-Storage.Enabled: false` → kein MySQL, **kein**
  serverübergreifender Markt (jeder Server hat seinen eigenen Marktbestand).
- DB-Passwörter in der Config sind **dormante Vendor-Defaults** – MySQL/Split-Storage bewusst aus.

## Wichtige Einstellungen / typische Aufgaben
- **Gebühren/Limits/Laufzeiten** von Angeboten → `Config.yml`.
- Wenn ein serverübergreifender Markt gewünscht ist: `Split-Storage`/MySQL + Sync-Optionen aktivieren und
  Secret-Injektion einrichten (aktuell nicht der Fall) – dann die in der Config genannten Sync-Settings auf
  allen beteiligten Servern **identisch** halten.

## Cross-Server / Gotchas
- Aktuell **pro Server getrennt** (SQLite). Nicht mit dem globalen Namen verwechseln – „Global" meint das
  Markt-Feature, nicht serverübergreifende Synchronisation.
- Preis-/Gebühren-Balancing pro Server-Economy (`S1_CMI`/`S3_CMI`/`S5_CMI`) betrachten.

## Custom Agent
[`.github/agents/globalmarketplus.agent.md`](../../.github/agents/globalmarketplus.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [CMI.md](CMI.md) · [ShopGUIPlus.md](ShopGUIPlus.md) · [DeluxeBazaar.md](DeluxeBazaar.md)
