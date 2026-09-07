---
applyTo: "{proxy,lobby,survival,skyblock,rpg}/plugins/**"
---

# Plugin-Konfiguration bearbeiten

Du bearbeitest gerade eine **Plugin-Konfiguration**. Bevor du Änderungen machst:

1. **Wissensbasis lesen:** Öffne `docs/plugins/<Plugin>.md` für dieses Plugin (Index & Matrix:
   [`docs/plugins/README.md`](../../docs/plugins/README.md)). Sie nennt Zweck, **exakte Config-Pfade je
   Server**, Storage/Secrets, typische Aufgaben und Gotchas.
2. **Passenden Custom Agent prüfen:** In [`.github/agents/`](../agents/) gibt es pro Plugin/Subsystem einen
   Agent, der es **gezielt auf allen Servern** bearbeitet. Nutze ihn bzw. seine Vorgehensweise.

## Harte Regeln
- **Secrets/Platzhalter `__…__` wörtlich erhalten** – nie mit echten Werten auffüllen, nie entfernen.
  Geheimnisse werden erst beim Deploy injiziert. Keine echten Credentials committen.
- **YAML:** 2 Leerzeichen, keine Tabs; bestehende Struktur, Reihenfolge und Kommentare bewahren.
- **`rpg/` = „mining"** (Prison-/Mining-Server). Öffentlicher Name „mining", Ordner/Velocity-Name `rpg`.
- **Server-übergreifende Konsistenz:** Ist das Plugin auf mehreren Servern vorhanden (siehe „Server" im
  Plugin-Doc), dieselbe Änderung überall konsistent nachziehen – sofern nicht bewusst server-spezifisch.
- **Keine Serverdaten anfassen:** `world*/`, `playerdata/`, `data/`, `*.db`/`*.sqlite`, `*.log`, Caches und
  `*.jar` sind Serverstand/Deploy-ausgeschlossen – nicht bearbeiten.
- **Deploy ist additiv** (kein Löschen). Dateien werden nur überschrieben/ergänzt.
- **Storage nicht „reparieren":** Dormante Vendor-Default-Passwörter (root/leer) bedeuten, dass ein
  DB-Backend bewusst **aus** ist – nicht aktivieren, wenn es die Aufgabe nicht ausdrücklich verlangt.

## Subsystem-Hinweise
- **TAB/Scoreboard** nur unter `proxy/plugins/tab/` ändern (netzwerkweit); Backend-Werte via PlaceholderAPI.
- **Economy** über CMI (`cmi money give …`); Guthaben sind pro Server getrennt.
- **Broadcasts** in `<server>/plugins/Skript/scripts/help.sk`; **Navigation** auf Gameplay-Servern nie
  Inventar leeren/Gamemode erzwingen (nur Lobby).
- **Skript:** führendes `-` = deaktiviert.

## Nach der Änderung
- YAML-Gültigkeit prüfen, Platzhalter unangetastet, betroffene Server abgeglichen. Änderungen minimal halten.
