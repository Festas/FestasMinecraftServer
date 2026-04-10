# Betriebshandbuch – Minecraft MMO Netzwerk

Dieses Dokument beschreibt die operativen Abläufe für das Minecraft-MMO-Netzwerk mit fünf Servern: **Proxy (Velocity)**, **Lobby**, **Survival**, **Skyblock** und **RPG**. Alle Server laufen in Docker-Containern.

---

## Server-Startsequenz

Die Server müssen in der folgenden Reihenfolge gestartet werden, um Abhängigkeiten korrekt aufzulösen:

1. **MariaDB & Redis** – Datenbank und Cache müssen zuerst verfügbar sein.
2. **Velocity Proxy** – Der Proxy muss laufen, bevor Spieler sich verbinden können.
3. **Lobby** – Der Lobby-Server ist der Standard-Spawn für alle Spieler.
4. **Spielserver** – RPG, Skyblock und Survival können parallel gestartet werden.

```bash
# Beispiel mit Docker Compose
docker compose up -d mariadb redis

# Bereitschaft prüfen, bevor weitere Dienste gestartet werden
docker compose exec mariadb mysqladmin ping --wait=30
docker compose exec redis redis-cli ping

docker compose up -d velocity
docker compose up -d lobby
docker compose up -d rpg skyblock survival
```

> **Hinweis:** Zwischen den Schritten sollte geprüft werden, ob der jeweilige Dienst vollständig hochgefahren ist, bevor der nächste gestartet wird.

---

## Server-Stopp-Sequenz

Das Herunterfahren erfolgt in **umgekehrter Reihenfolge**. Spieler müssen vorher benachrichtigt werden.

1. **Spieler benachrichtigen** – Mindestens 5 Minuten vorher eine Warnung an alle Spieler senden.
2. **Spielserver stoppen** – RPG, Skyblock und Survival herunterfahren.
3. **Lobby stoppen** – Lobby-Server herunterfahren.
4. **Velocity Proxy stoppen** – Proxy herunterfahren, damit keine neuen Verbindungen möglich sind.
5. **MariaDB & Redis stoppen** – Datenbank und Cache zuletzt stoppen.

```bash
# Warnung an Spieler senden (über Velocity-Konsole)
# /alert Der Server wird in 5 Minuten heruntergefahren!

docker compose stop rpg skyblock survival
docker compose stop lobby
docker compose stop velocity
docker compose stop mariadb redis
```

---

## Tägliche Wartung

Folgende Punkte sollten täglich überprüft werden:

| Prüfpunkt         | Beschreibung                                                  |
| ------------------ | ------------------------------------------------------------- |
| **TPS**            | Ticks pro Sekunde auf allen Spielservern prüfen (Ziel: 20.0) |
| **Arbeitsspeicher** | RAM-Auslastung aller Container überwachen                    |
| **Speicherplatz**  | Festplattenbelegung prüfen (Welten, Logs, Backups)           |
| **Spieler-Reports** | Offene Reports und Beschwerden bearbeiten                    |
| **Logs**           | Fehler- und Warnmeldungen in Server-Logs überprüfen          |
| **Backups**        | Sicherstellen, dass tägliche Backups erfolgreich waren        |

---

## Notfall-Befehle

### Einzelnen Server stoppen

```bash
docker compose stop <servername>
# Beispiel:
docker compose stop rpg
```

### Proxy neu starten

```bash
docker compose restart velocity
```

> **Achtung:** Ein Proxy-Neustart trennt alle verbundenen Spieler. Vorher Warnung senden!

### Notfall-Bann

```bash
# Über die Velocity- oder Server-Konsole:
docker exec -it velocity rcon-cli ban <Spielername> <Grund>
```

### Alle Server sofort stoppen

```bash
docker compose down
```

---

## Monitoring-Empfehlungen

### TPS (Ticks pro Sekunde)

| Stufe       | Schwellenwert | Aktion                                      |
| ----------- | ------------- | ------------------------------------------- |
| **Normal**  | ≥ 18 TPS     | Keine Aktion erforderlich                   |
| **Warnung** | < 18 TPS     | Ursache untersuchen (Entities, Redstone, Plugins) |
| **Kritisch**| < 15 TPS     | Sofortige Maßnahmen einleiten               |

### RAM (Arbeitsspeicher)

| Stufe       | Schwellenwert | Aktion                                      |
| ----------- | ------------- | ------------------------------------------- |
| **Normal**  | ≤ 80%        | Keine Aktion erforderlich                   |
| **Warnung** | > 80%         | Speicherverbrauch analysieren, ggf. GC erzwingen |
| **Kritisch**| > 90%         | Server-Neustart planen, Speicherlecks prüfen |

### Disk (Festplatte)

| Stufe       | Schwellenwert | Aktion                                      |
| ----------- | ------------- | ------------------------------------------- |
| **Normal**  | ≤ 80%        | Keine Aktion erforderlich                   |
| **Warnung** | > 80%         | Alte Logs und Backups aufräumen              |

### Spielerzahl

- Spielerzahlen regelmäßig erfassen und für **Kapazitätsplanung** nutzen.
- Peak-Zeiten identifizieren, um Ressourcen entsprechend zu skalieren.
- Bei konstant hoher Auslastung zusätzliche Server-Instanzen in Betracht ziehen.

---

## Alerting

Für die Benachrichtigung bei kritischen Ereignissen wird eine **Discord-Webhook-Integration** empfohlen.

- Alerts für TPS-Drops, hohe RAM-Auslastung und Festplattenprobleme einrichten.
- Webhook-URL im Monitoring-Tool hinterlegen.
- Verschiedene Kanäle für Warnungen und kritische Alerts verwenden.

Die Webhook-URL wird im jeweiligen Monitoring-Tool konfiguriert (z. B. Grafana, Prometheus Alertmanager oder ein eigenes Skript). Dort die URL unter den Benachrichtigungseinstellungen hinterlegen.

```
Beispiel-Webhook-Payload:
{
  "content": "⚠️ **WARNUNG**: TPS auf RPG-Server bei 16.5 – Untersuchung empfohlen!"
}
```

---

## Weiterführende Dokumentation

- [Architektur-Übersicht](ARCHITECTURE.md)
- [Backup-Strategie](BACKUPS.md)
- [Notfall-Wiederherstellung](DISASTER_RECOVERY.md)
