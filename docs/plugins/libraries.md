# Bibliotheken & Support-Plugins

Diese Plugins sind **Abhängigkeiten/Infrastruktur** – meist **nicht** direkt zu bearbeiten. Sie werden
von den Feature-Plugins vorausgesetzt. Kein eigener Custom Agent; bei Bedarf über den Kontext des
abhängigen Plugins anfassen. Alle lokal, keine Deploy-Secrets (sofern nicht anders vermerkt).

| Plugin | Server | Rolle |
|---|---|---|
| **Vault** | lobby, survival, skyblock, rpg | Economy-/Permissions-API-Brücke – CMI ist Provider, LuckPerms liefert Rechte |
| **CMILib** | lobby, survival, skyblock, rpg | Pflicht-Bibliothek von CMI |
| **ProtocolLib** | lobby, survival, skyblock, rpg | Paket-Manipulation (von vielen Plugins benötigt) |
| **CommandAPI** | lobby, survival, rpg | Command-Framework (u. a. für Skript-Erweiterungen) |
| **FastAsyncWorldEdit (FAWE)** | lobby, survival, rpg | WorldEdit-Engine (ersetzt/bündelt WorldEdit); von WorldGuard genutzt |
| **NBTAPI** | survival, rpg | NBT-Zugriff für Items/Entities |
| **NextGens/Rose-Libs → RoseGarden** | survival | Bibliothek für Rose-basierte Plugins |
| **nightcore** | skyblock | Bibliothek für NightExpress-Plugins |
| **VoidGen** | survival, skyblock | Void-Welt-Generator (leere Welten/Inselbasis) |
| **Chunky** | survival | Chunk-Vorgenerierung (Performance/Weltgrenzen) |
| **AxiomPaper** | survival | Serverseite des Axiom-Build-Tools |
| **spark** | alle Backends | Performance-Profiler (`/spark`) |
| **bStats** | lobby, survival, skyblock | anonyme Plugin-Metriken |
| **faststats** | alle Backends | Statistik-Support |
| **velocity-scoreboard-api** | proxy | Scoreboard-API für TAB |

## Typische (seltene) Aufgaben
- **Chunky**: Weltvorgenerierung/Radius steuern (`/chunky …`) – reine Performance-Aufgabe.
- **FAWE**: Limits/Performance in der FAWE-Config anpassen, wenn Bau-/Regen-Operationen es erfordern.
- **spark**: nur zur Diagnose; keine dauerhaften Config-Änderungen nötig.
- **Vault**: nicht konfigurieren – nur sicherstellen, dass CMI (Economy) + LuckPerms (Perms) laufen.

## Gotchas
- Diese Plugins werden **nicht** einzeln „gefeatured"; Änderungen an ihnen betreffen viele abhängige
  Plugins → nur gezielt und bewusst.
- `.hopper` und `.paper-remapped` sind **interne Paper-Artefakte**, keine konfigurierbaren Plugins.
- `*.jar` werden vom Deploy ausgeschlossen – Versions-Updates dieser Libs laufen serverseitig, nicht per Repo.

## Referenzen
[README (Wissensbasis)](README.md) · [CMI.md](CMI.md) · [LuckPerms.md](LuckPerms.md) · [WorldGuard.md](WorldGuard.md)
