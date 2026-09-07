# LibertyBans

**Moderation/Bans · nur Proxy · netzwerkweit · HSQLDB (embedded aktiv)**

## Zweck
LibertyBans ist das netzwerkweite Moderationssystem (Bans, Mutes, Kicks, Warns) am Proxy – gilt für
alle Backends gemeinsam.

## Wo (Server & Config-Pfade)
Nur am Proxy: `proxy/plugins/libertybans/`
- `config.yml` – allgemeine Optionen, Scope
- `sql.yml` – Datenbank-Backend
- `scope.yml` – Geltungsbereich (global/servergebunden)
- `import.yml` – Import aus anderen Systemen · `lang/` – Nachrichten

## Storage & Secrets
- **Aktiv: eingebettetes HSQLDB** (lokal). `sql.yml`/`import.yml` enthalten **dormante** Platzhalter
  `__LIBERTYBANS_DB_USER__` / `__LIBERTYBANS_DB_PASSWORD__` für einen optionalen externen DB-Betrieb –
  aktuell nicht genutzt, beim Bearbeiten als Template erhalten.

## Wichtige Einstellungen / typische Aufgaben
- **Ban-Scope** (netzwerkweit vs. serverbezogen) → `scope.yml`.
- **Nachrichten/Format** → `lang/`.
- Auf **DB-Wechsel** nur umstellen, wenn externer Store bewusst eingeführt wird (dann Platzhalter + Secret).
- Befehle: `/ban`, `/mute`, `/tempban …` (am Proxy).

## Cross-Server / Gotchas
- Moderation ist **proxy-zentral** – keine parallele Ban-Lösung auf Backends betreiben.
- Bei Umstieg auf externe DB Secret-Injektion analog zu anderen Proxy-Plugins einrichten.

## Custom Agent
[`.github/agents/libertybans.agent.md`](../../.github/agents/libertybans.agent.md)

## Referenzen
[README (Wissensbasis)](README.md) · [proxy-network → SkinsRestorer/MiniMOTD/ForceResourcepacks](MiniMOTD.md)
