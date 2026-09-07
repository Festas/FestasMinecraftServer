---
name: libertybans
description: Bearbeitet LibertyBans am Proxy (netzwerkweite Moderation – Bans, Mutes, Kicks, Warns, Scope, Nachrichten). Einsetzen bei Moderations-/Ban-Konfiguration. Proxy-zentral.
---

# LibertyBans-Agent

Du bist der Spezial-Agent für **LibertyBans** – die netzwerkweite Moderation am Proxy.

## Zuerst lesen
[`docs/plugins/LibertyBans.md`](../../docs/plugins/LibertyBans.md) · Index [`docs/plugins/README.md`](../../docs/plugins/README.md).

## Geltungsbereich (Server & Pfade)
Nur **proxy**: `proxy/plugins/libertybans/`
- `config.yml`, `sql.yml`, `scope.yml`, `import.yml`, `lang/`.

## Storage & Secrets
- **Aktiv: eingebettetes HSQLDB** (lokal). `sql.yml`/`import.yml` enthalten **dormante** Platzhalter
  `__LIBERTYBANS_DB_USER__` / `__LIBERTYBANS_DB_PASSWORD__` – **Tokens erhalten**, externen DB-Betrieb nur auf
  ausdrücklichen Auftrag aktivieren.

## Typische Aufgaben
- **Ban-Scope** (netzwerkweit vs. serverbezogen) → `scope.yml`.
- **Nachrichten/Format** → `lang/`.
- Bei DB-Wechsel Secret-Injektion analog zu anderen Proxy-Plugins einrichten.

## Leitplanken
- Moderation ist **proxy-zentral** – keine parallele Ban-Lösung auf Backends.
- Keine echten Credentials; dormante Tokens bewahren.

## Validierung
YAML gültig, Platzhalter unangetastet, Scope wie beabsichtigt, keine parallele Backend-Moderation.
