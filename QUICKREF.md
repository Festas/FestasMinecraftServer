# Quick Reference Guide

Schnelle Übersicht über Verzeichnisstruktur, Konventionen und häufige Befehle.

## Verzeichnisstruktur

```
MinecraftMMO/
├── proxy/              # Velocity Proxy Konfigurationen
│   └── plugins/        # Proxy-Plugins (TAB, MiniMOTD, LibertyBans, etc.)
├── lobby/              # Lobby Server Konfigurationen        (AKTIV)
│   └── plugins/        # Lobby-Plugins (CMI, DeluxeMenus, Skript, Oraxen, etc.)
├── survival/           # Survival Server Konfigurationen     (AKTIV)
│   └── plugins/        # Survival-Plugins (NextGens, Jobs, Rankup, PlotSquared, etc.)
├── skyblock/           # Skyblock Server Konfigurationen      (UMBAU — ohne Gilden, Freunde-Koop)
│   └── plugins/        # Skyblock-Plugins (SuperiorSkyblock2, JetsMinions, etc.)
├── prison/             # Mining Server Konfigurationen       (RECYCELT aus RPG — Aufbau)
│   └── plugins/        # Mining-/Zonen-Kern, WorldGuard, Shop/Auto-Sell, etc.
└── docs/               # Dokumentation
```

## Wichtige Dateien

- **README.md** - Projekt-Übersicht
- **CONTRIBUTING.md** - Richtlinien für Beiträge
- **docs/PLUGINS.md** - Vollständige Plugin-Referenz
- **docs/NEW_SERVERS.md** - Konzept Skyblock & Mining

## Configs bearbeiten

```bash
# Survival-Config anpassen
survival/plugins/NextGens/config.yml
survival/plugins/ShopGUIPlus/config.yml

# Lobby-Config anpassen
lobby/plugins/DeluxeMenus/gui_menus/server_selector.yml
lobby/plugins/Skript/scripts/

# Skyblock
skyblock/plugins/SuperiorSkyblock2/config.yml
```

## In-Game Befehle (aktive Server)

### CMI (Lobby & Survival)

```
/cmi setspawn                          # Spawn setzen
/cmi hologram create NAME              # Hologramm erstellen
/cmi reload                            # Plugin neu laden
/cmi kit <name>                        # Kit ausgeben
```

### LuckPerms

```
/lp user <player> group set <group>    # Gruppe setzen
/lp editor                             # Web-Editor öffnen
/lp reload                             # Neu laden
```

### NextGens (Survival)

```
/nextgens reload                       # Config neu laden
/nextgens give <player> <generator>    # Generator geben
```

### PlotSquared (Survival)

```
/plot auto                             # Plot claimen
/plot setowner <player>                # Besitzer setzen
/plot clear                            # Plot leeren
/plot reload                           # Neu laden
```

### Oraxen (Custom Items)

```
/oraxen reload all                     # Alle Configs neu laden
/oraxen give <player> <item>           # Custom-Item geben
```

### SuperiorSkyblock2 (Skyblock)

```
/is admin reload                       # Neu laden
/is admin tp <player>                  # Zu Insel teleportieren
/is admin setbiome <player> <biome>    # Biom setzen
```

### WorldGuard

```
/rg define <region>                    # Region definieren
/rg flag <region> <flag> <value>       # Flag setzen
/rg reload                             # Neu laden
```

## YAML-Syntax Referenz

```yaml
# ✅ Korrekt (2 Leerzeichen, Quotes bei &)
display: '&6&lGoldener Text'
lore:
  - '&7Zeile 1'
  - '&7Zeile 2'

# ❌ Falsch (Tabs, fehlende Quotes)
display:	&6Text
```

## Farbcodes Referenz

```
&0 Schwarz      &8 Dunkelgrau
&1 Dunkelblau   &9 Blau
&2 Dunkelgrün   &a Grün
&3 Dunkel-Aqua  &b Aqua
&4 Dunkelrot    &c Rot
&5 Dunkel-Lila  &d Pink
&6 Gold         &e Gelb
&7 Grau         &f Weiß

&l Fett         &o Kursiv
&m Durchgestr.  &n Unterstrichen
&r Reset
```

## Repository-Befehle

```bash
git status
git add .
git commit -m "Update: Config-Beschreibung"
git push

# Backup erstellen
tar -czf backup-$(date +%Y%m%d).tar.gz lobby/ survival/ skyblock/ prison/ proxy/
```

## Hilfreiche Links

- Paper Docs: https://docs.papermc.io/
- Velocity Docs: https://docs.papermc.io/velocity
- LuckPerms Docs: https://luckperms.net/wiki/Home
- CMI Docs: https://www.zrips.net/cmi/
- Minecraft Farbcodes: https://minecraft.tools/en/color-code.php

## Support

Bei Problemen:
1. YAML-Syntax prüfen
2. Console-Logs lesen (`docker logs <container>`)
3. Plugin-Dokumentation konsultieren
4. Beispiel-Dateien im Repo vergleichen
