# server-logs/health

Ausgabeordner des Workflows **[Server-Wartung & Health-Report](../../.github/workflows/server-maintenance.yml)**
(siehe [`tools/server-maintenance/`](../../tools/server-maintenance/README.md)).

Der Workflow committet hierher nach jedem Lauf automatisch:

| Datei | Inhalt |
|---|---|
| `latest.md` | Der aktuelle **Kurzbericht** (Exec-Summary oben, Details darunter): Speicher, Health, Updates, Aufräum- und Optimierungsvorschläge. |
| `latest.json` | Dieselben Kennzahlen **maschinenlesbar** (Status, Metriken, Befunde, Empfehlungen). |
| `history/<zeitstempel>.json` | Ein JSON-Schnappschuss je Lauf für **Trend-Auswertungen** über die Zeit. |

## Status-Ampel

`latest.json` enthält ein Feld `status`:

- `OK` 🟢 – keine akuten Probleme.
- `WARN` 🟡 – Optimierungsbedarf (z. B. hohe Belegung, offene Updates).
- `CRIT` 🔴 – dringender Handlungsbedarf (z. B. Platte fast voll, fehlgeschlagene Dienste).

> **Hinweis:** Diese Dateien werden **automatisch überschrieben/ergänzt**. Nicht
> von Hand bearbeiten – Änderungen gehen beim nächsten Lauf verloren. Der
> `history/`-Ordner wächst mit der Zeit; alte Schnappschüsse können bei Bedarf
> gefahrlos gelöscht werden.
