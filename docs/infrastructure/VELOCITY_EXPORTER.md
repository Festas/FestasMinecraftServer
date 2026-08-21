# Velocity Proxy Exporter – Metrik-Vertrag & Prometheus-Anbindung

Dieses Dokument definiert den **MVP-Vertrag** für einen separaten Metrics-Exporter am
**Velocity-Proxy**. Der Exporter ergänzt **Plan**: Plan bleibt für Web-Analytics und
Historie zuständig, der Exporter liefert **technisches Live-Monitoring** im
**Prometheus-Textformat**.

---

## Zielbild

- **Komponente:** separater Exporter für den Velocity-Proxy
- **Zweck:** Betrieb, Erreichbarkeit und Spielerfluss des Proxys überwachen
- **Scope im MVP:** Proxy-Core, Backend-Health, Logins, Server-Wechsel, grobe
  Java/Bedrock-Verteilung
- **Nicht im MVP:** plugin-spezifische Tiefenmetriken, personenbezogene Daten,
  direkte Auswertung historischer Plan-Daten, Datenbank-Polling

---

## Endpoint & Betriebsmodell

Der Exporter stellt einen internen Prometheus-Endpoint bereit:

- **Bind-Adresse:** `127.0.0.1`
- **Port:** `9108`
- **Pfad:** `/metrics`
- **Format:** `text/plain; version=0.0.4`

Beispiel:

```text
http://127.0.0.1:9108/metrics
```

> **Wichtig:** Der Endpoint ist nur für internes Scraping gedacht und darf nicht
> direkt nach außen veröffentlicht werden.

---

## Datenquellen

| Metrik-Gruppe | Primärquelle | Hinweise |
|---------------|--------------|----------|
| Proxy-Status | Velocity Runtime/API | Uptime, Online-Status, Build-Info |
| Spieler | Velocity Runtime/API | keine Spieler-spezifischen Labels |
| Backend-Server | Velocity Runtime/API | registriert, erreichbar, Ping |
| Routing / Transfers | Velocity Events/API | Switches, Fallbacks, Lobby-Kicks |
| Login / Verbindungen | Velocity Events/API | Attempts, Failures, Ergebniszähler |
| Bedrock/Java | Geyser/Floodgate | nur aggregierte Protokoll-Sicht |

**Nicht als Primärquelle im MVP:**

- Plan
- MariaDB
- LibertyBans

---

## Metrik-Namensschema

- Präfix: `festas_velocity_`
- Counter enden immer auf `_total`
- Laufzeiten in `_seconds`
- Latenzen in `_ms`

### Erlaubte Labels

- `server="lobby|survival|skyblock|prison"`
- `result="success|cancelled|timeout|backend_unavailable|error"`
- `protocol="java|bedrock"`

### Explizit verboten

- `player`
- `username`
- `uuid`
- `ip`
- freie Fehlertexte oder hochkardinale Reasons

---

## MVP-Metriken

### Proxy-Status

| Metrik | Typ | Labels | Beschreibung |
|--------|-----|--------|--------------|
| `festas_velocity_up` | gauge | – | 1 wenn der Exporter erfolgreich Proxy-Daten liefern kann |
| `festas_velocity_build_info` | gauge | `version` | Konstante Info-Metrik mit Wert 1 |
| `festas_velocity_uptime_seconds` | gauge | – | Laufzeit des Velocity-Prozys |

### Spieler

| Metrik | Typ | Labels | Beschreibung |
|--------|-----|--------|--------------|
| `festas_velocity_players_online` | gauge | – | Aktuell verbundene Spieler am Proxy |
| `festas_velocity_players_by_backend` | gauge | `server` | Spieler pro Backend |
| `festas_velocity_player_connections_total` | counter | – | Erfolgreiche Verbindungen zum Proxy |
| `festas_velocity_player_disconnects_total` | counter | – | Getrennte Verbindungen |

### Backend-Server

| Metrik | Typ | Labels | Beschreibung |
|--------|-----|--------|--------------|
| `festas_velocity_backend_registered` | gauge | `server` | 1 wenn Backend in Velocity registriert ist |
| `festas_velocity_backend_up` | gauge | `server` | 1 wenn Backend erreichbar ist |
| `festas_velocity_backend_ping_ms` | gauge | `server` | Gemessene Ping-Latenz des Backends |

### Routing / Transfers

| Metrik | Typ | Labels | Beschreibung |
|--------|-----|--------|--------------|
| `festas_velocity_server_switch_total` | counter | `server` | Erfolgreiche Wechsel auf ein Ziel-Backend |
| `festas_velocity_fallback_total` | counter | `server` | Fallbacks auf ein Ausweich-Backend |
| `festas_velocity_kicked_to_lobby_total` | counter | – | Kicks/Redirects zurück auf die Lobby |

### Login / Verbindungen

| Metrik | Typ | Labels | Beschreibung |
|--------|-----|--------|--------------|
| `festas_velocity_login_attempts_total` | counter | – | Eingehende Login-Versuche |
| `festas_velocity_login_failures_total` | counter | – | Fehlgeschlagene Logins |
| `festas_velocity_login_result_total` | counter | `result` | Ergebniszähler für Login-/Connect-Flows |

### Bedrock / Geyser

| Metrik | Typ | Labels | Beschreibung |
|--------|-----|--------|--------------|
| `festas_velocity_players_by_protocol` | gauge | `protocol` | Aktive Spieler nach Java/Bedrock |

---

## Beispiel-Output

```text
# HELP festas_velocity_up 1 if the exporter can read proxy state.
# TYPE festas_velocity_up gauge
festas_velocity_up 1

# HELP festas_velocity_players_by_backend Players currently connected to each backend.
# TYPE festas_velocity_players_by_backend gauge
festas_velocity_players_by_backend{server="lobby"} 8
festas_velocity_players_by_backend{server="survival"} 21

# HELP festas_velocity_backend_ping_ms Ping to registered backends in milliseconds.
# TYPE festas_velocity_backend_ping_ms gauge
festas_velocity_backend_ping_ms{server="lobby"} 11
festas_velocity_backend_ping_ms{server="survival"} 24

# HELP festas_velocity_login_result_total Count of login results by outcome.
# TYPE festas_velocity_login_result_total counter
festas_velocity_login_result_total{result="success"} 1882
festas_velocity_login_result_total{result="backend_unavailable"} 4
```

---

## Prometheus-Integration

Beispiel-Dateien für Prometheus liegen in diesem Repo unter:

- `docs/infrastructure/prometheus/velocity-proxy.yml`
- `docs/infrastructure/prometheus/velocity-proxy-alerts.yml`

Empfohlenes Scrape-Intervall:

- **15s** für den Proxy-Exporter

---

## Grafana-Dashboard (MVP)

Das erste Dashboard sollte mindestens diese Panels enthalten:

1. **Proxy online/offline**
2. **Gesamtspieler online**
3. **Spieler je Backend**
4. **Backend-Erreichbarkeit**
5. **Backend-Ping**
6. **Login-Erfolge vs. Fehler**
7. **Serverwechsel**
8. **Fallbacks**
9. **Kicks zur Lobby**
10. **Java vs. Bedrock**

---

## Alerts (MVP)

Für den Start sind diese Alert-Gruppen vorgesehen:

1. **ProxyDown** – `festas_velocity_up == 0`
2. **BackendDown** – `festas_velocity_backend_up == 0`
3. **BackendPingHigh** – hohe durchschnittliche Backend-Latenz
4. **LoginFailuresHigh** – auffälliger Anstieg fehlgeschlagener Logins
5. **FallbackStorm** – viele Fallbacks oder Lobby-Kicks in kurzer Zeit

Die Prometheus-Regeln dafür liegen in:

- `docs/infrastructure/prometheus/velocity-proxy-alerts.yml`

Benachrichtigungen können wie in `docs/OPERATIONS.md` beschrieben über
**Prometheus Alertmanager** an einen **Discord-Webhook** gehen.

---

## Zusammenspiel mit bestehenden Komponenten

- **Plan** bleibt das Web-Dashboard für Langzeitstatistiken und Spieler-Analytics
  (`proxy/plugins/plan/ServerInfoFile.yml`, Web-UI aktuell auf `localhost:8804`).
- **Der Velocity-Exporter** ist für Live-Scraping, Grafana und Alerting zuständig.
- **Geyser/Floodgate** liefern optional die aggregierte Bedrock-Sicht.

---

## Abnahmekriterien

- Der Proxy-Zustand ist im Monitoring sichtbar.
- Jeder registrierte Backend-Server ist mit `up` und `ping_ms` sichtbar.
- Gesamtspieler und Spieler je Backend sind sichtbar.
- Login-Fehler, Fallbacks und Lobby-Kicks sind als Counter verfügbar.
- Java- und Bedrock-Spieler werden nur aggregiert exportiert.
- Es werden keine personenbezogenen oder geheimen Daten exportiert.

---

## Relevante Dateien

| Bereich | Datei |
|--------|-------|
| Proxy-Plan Web-UI | `proxy/plugins/plan/ServerInfoFile.yml` |
| Geyser-Bedrock-Config | `proxy/plugins/Geyser-Velocity/config.yml` |
| Operations-Handbuch | `docs/OPERATIONS.md` |
| Infrastruktur-Übersicht | `docs/infrastructure/README.md` |
| Prometheus Scrape-Beispiel | `docs/infrastructure/prometheus/velocity-proxy.yml` |
| Prometheus Alerts | `docs/infrastructure/prometheus/velocity-proxy-alerts.yml` |

---

**Letzte Aktualisierung:** 2026-08-19
