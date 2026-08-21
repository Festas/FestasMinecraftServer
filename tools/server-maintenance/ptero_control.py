#!/usr/bin/env python3
"""Pterodactyl-Client-API-Helfer für den Wartungs-Workflow.

Dieses Skript läuft auf dem GitHub-Runner (nicht auf dem Server) und steuert die
Minecraft-Server über die **Client-API** des Pterodactyl-Panels. Es benutzt
ausschließlich die Python-Standardbibliothek (kein `pip install` nötig).

Zweck im Wartungs-Workflow (`.github/workflows/server-maintenance.yml`):
  1. `announce`        – Spieler auf allen Servern vorwarnen (Broadcast).
  2. `countdown`       – mehrere Vorwarnungen in Intervallen senden.
  3. `graceful-stop`   – Server sauber stoppen (Zustand vorher sichern),
                         auf "offline" warten und bei Zeitüberschreitung killen.
  4. `start`           – zuvor laufende Server wieder starten (nach dem Reboot).
  5. `list` / `state`  – Übersicht bzw. Maschinen-Zustand (read-only).

Konfiguration über Umgebungsvariablen (oder gleichnamige CLI-Optionen):
  PTERODACTYL_URL       Basis-URL des Panels, z. B. https://panel.example.com
  PTERODACTYL_API_KEY   Client-API-Schlüssel (beginnt mit `ptlc_`)

Sicherheits-/Robustheitsdesign:
  * `--dry-run` verändert nichts (nur lesende GET-Aufrufe, Aktionen werden geloggt).
  * Fehler einzelner Server brechen den Gesamtlauf nicht ab (best effort).
  * Transiente Netzwerkfehler werden mehrfach wiederholt.
"""

from __future__ import annotations

import argparse
import json
import os
import sys
import time
import urllib.error
import urllib.request

DEFAULT_TIMEOUT = 30
RETRIES = 3
RETRY_WAIT = 3
POWER_STATES_ONLINE = ("running", "starting")


def log(msg: str) -> None:
    print(f"[ptero] {msg}", flush=True)


def err(msg: str) -> None:
    print(f"[ptero] FEHLER: {msg}", file=sys.stderr, flush=True)


class PteroClient:
    """Minimaler Client für die Pterodactyl-Client-API."""

    def __init__(self, base_url: str, api_key: str, timeout: int = DEFAULT_TIMEOUT,
                 dry_run: bool = False):
        if not base_url:
            raise ValueError("PTERODACTYL_URL fehlt.")
        if not api_key:
            raise ValueError("PTERODACTYL_API_KEY fehlt.")
        self.base = base_url.rstrip("/")
        self.key = api_key
        self.timeout = timeout
        self.dry_run = dry_run

    # -- HTTP ---------------------------------------------------------------
    def _request(self, method: str, path: str, body: dict | None = None) -> tuple[int, dict]:
        url = f"{self.base}{path}"
        data = json.dumps(body).encode("utf-8") if body is not None else None
        headers = {
            "Authorization": "Bearer " + self.key,
            "Accept": "application/json",
            "Content-Type": "application/json",
        }
        last_exc: Exception | None = None
        for attempt in range(1, RETRIES + 1):
            req = urllib.request.Request(url, data=data, headers=headers, method=method)
            try:
                with urllib.request.urlopen(req, timeout=self.timeout) as resp:
                    raw = resp.read().decode("utf-8") if resp.length != 0 else ""
                    payload = json.loads(raw) if raw.strip() else {}
                    return resp.status, payload
            except urllib.error.HTTPError as exc:  # 4xx/5xx
                detail = ""
                try:
                    detail = exc.read().decode("utf-8")[:300]
                except Exception:
                    pass
                # 4xx sind i. d. R. nicht wiederholbar (außer 429).
                if exc.code < 500 and exc.code != 429:
                    return exc.code, {"_error": detail or str(exc)}
                last_exc = exc
            except (urllib.error.URLError, TimeoutError, ConnectionError) as exc:
                last_exc = exc
            if attempt < RETRIES:
                time.sleep(RETRY_WAIT)
        raise RuntimeError(f"{method} {path} fehlgeschlagen: {last_exc}")

    # -- API-Methoden -------------------------------------------------------
    def list_servers(self) -> list[dict]:
        """Alle Server (über alle Seiten) als Liste von Attribut-Dicts."""
        servers: list[dict] = []
        page = 1
        while True:
            status, payload = self._request("GET", f"/api/client?page={page}&per_page=100")
            if status != 200:
                raise RuntimeError(f"Serverliste nicht abrufbar (HTTP {status}).")
            for item in payload.get("data", []):
                attr = item.get("attributes", {})
                if attr:
                    servers.append(attr)
            meta = payload.get("meta", {}).get("pagination", {})
            if not meta or meta.get("current_page", 1) >= meta.get("total_pages", 1):
                break
            page += 1
        return servers

    def power_state(self, identifier: str) -> str:
        status, payload = self._request(
            "GET", f"/api/client/servers/{identifier}/resources")
        if status != 200:
            return "unknown"
        return payload.get("attributes", {}).get("current_state", "unknown")

    def send_power(self, identifier: str, signal: str) -> bool:
        if self.dry_run:
            log(f"DRY-RUN: würde Power-Signal '{signal}' an {identifier} senden.")
            return True
        status, payload = self._request(
            "POST", f"/api/client/servers/{identifier}/power", {"signal": signal})
        if status not in (200, 204):
            err(f"Power '{signal}' an {identifier} fehlgeschlagen (HTTP {status}): "
                f"{payload.get('_error', '')}")
            return False
        return True

    def send_command(self, identifier: str, command: str) -> bool:
        if self.dry_run:
            log(f"DRY-RUN: würde Befehl an {identifier} senden: {command!r}")
            return True
        status, payload = self._request(
            "POST", f"/api/client/servers/{identifier}/command", {"command": command})
        if status not in (200, 204):
            err(f"Befehl an {identifier} fehlgeschlagen (HTTP {status}): "
                f"{payload.get('_error', '')}")
            return False
        return True


# -- Hilfsfunktionen --------------------------------------------------------
def _is_proxy(attr: dict, proxy_match: list[str]) -> bool:
    name = (attr.get("name") or "").lower()
    return any(tok and tok in name for tok in proxy_match)


def _filter_servers(servers: list[dict], only: list[str]) -> list[dict]:
    if not only:
        return servers
    wanted = {o.strip().lower() for o in only if o.strip()}
    out = []
    for s in servers:
        ident = (s.get("identifier") or "").lower()
        name = (s.get("name") or "").lower()
        if ident in wanted or name in wanted:
            out.append(s)
    return out


def _announce_once(client: PteroClient, servers: list[dict], message: str,
                   proxy_match: list[str], proxy_command: str) -> None:
    for s in servers:
        ident = s.get("identifier")
        if not ident:
            continue
        # Nur an laufende Server senden – offline-Server lehnen Befehle ab.
        state = client.power_state(ident) if not client.dry_run else "running"
        if state not in POWER_STATES_ONLINE:
            log(f"überspringe {ident} ({s.get('name')}) – Zustand {state}.")
            continue
        if _is_proxy(s, proxy_match):
            cmd = f"{proxy_command} {message}"
        else:
            cmd = f"say {message}"
        ok = client.send_command(ident, cmd)
        log(f"{'✓' if ok else '✗'} Ansage an {s.get('name')} ({ident}).")


# -- Subcommands ------------------------------------------------------------
def cmd_list(client: PteroClient, args) -> int:
    servers = client.list_servers()
    rows = []
    for s in servers:
        ident = s.get("identifier", "?")
        state = client.power_state(ident)
        rows.append((ident, s.get("name", "?"), state))
    if args.json:
        print(json.dumps(
            [{"identifier": i, "name": n, "state": st} for i, n, st in rows],
            ensure_ascii=False, indent=2))
    else:
        for i, n, st in rows:
            print(f"  {i:12s}  {st:10s}  {n}")
    return 0


def cmd_state(client: PteroClient, args) -> int:
    servers = client.list_servers()
    state = {}
    for s in servers:
        ident = s.get("identifier")
        if not ident:
            continue
        state[ident] = {"name": s.get("name"), "state": client.power_state(ident)}
    payload = {"saved_at": int(time.time()), "servers": state}
    text = json.dumps(payload, ensure_ascii=False, indent=2)
    if args.save:
        with open(args.save, "w", encoding="utf-8") as fh:
            fh.write(text + "\n")
        log(f"Zustand gespeichert: {args.save}")
    print(text)
    return 0


def cmd_announce(client: PteroClient, args) -> int:
    servers = _filter_servers(client.list_servers(), args.only)
    proxy_match = [t.strip().lower() for t in args.proxy_match.split(",")]
    _announce_once(client, servers, args.message, proxy_match, args.proxy_command)
    return 0


def cmd_countdown(client: PteroClient, args) -> int:
    """Mehrere Vorwarnungen senden, z. B. bei 15/10/5/1 Minuten vor dem Stopp."""
    servers = _filter_servers(client.list_servers(), args.only)
    proxy_match = [t.strip().lower() for t in args.proxy_match.split(",")]
    try:
        steps = [int(x) for x in args.steps.split(",") if x.strip()]
    except ValueError:
        err(f"Ungültiger --steps-Wert: {args.steps!r} (erwartet kommagetrennte Minuten, z. B. 10,5,1).")
        return 2
    steps = sorted(set(steps), reverse=True)
    if not steps:
        steps = [0]
    prev = None
    for minutes in steps:
        if prev is not None:
            wait_s = (prev - minutes) * 60
            if wait_s > 0 and not args.no_sleep:
                time.sleep(wait_s)
        if minutes > 0:
            minutes_text = f"{minutes} Minute" if minutes == 1 else f"{minutes} Minuten"
            msg = args.template.format(minutes=minutes, minutes_text=minutes_text)
        else:
            msg = args.final_message
        log(f"Vorwarnung (T-{minutes} min): {msg}")
        _announce_once(client, servers, msg, proxy_match, args.proxy_command)
        prev = minutes
    return 0


def cmd_graceful_stop(client: PteroClient, args) -> int:
    servers = _filter_servers(client.list_servers(), args.only)
    # Zustand vor dem Stopp sichern (für spätere Wiederherstellung).
    snapshot = {}
    running = []
    for s in servers:
        ident = s.get("identifier")
        if not ident:
            continue
        state = client.power_state(ident)
        snapshot[ident] = {"name": s.get("name"), "state": state}
        if state in POWER_STATES_ONLINE:
            running.append(s)
    if args.save_state:
        with open(args.save_state, "w", encoding="utf-8") as fh:
            json.dump({"saved_at": int(time.time()), "servers": snapshot}, fh,
                      ensure_ascii=False, indent=2)
            fh.write("\n")
        log(f"Zustand vor Stopp gesichert: {args.save_state}")

    if not running:
        log("Keine laufenden Server – nichts zu stoppen.")
        return 0

    log(f"Stoppe {len(running)} laufende(n) Server …")
    for s in running:
        client.send_power(s["identifier"], "stop")

    if client.dry_run:
        log("DRY-RUN: warte nicht auf 'offline'.")
        return 0

    deadline = time.time() + args.wait
    pending = {s["identifier"]: s.get("name") for s in running}
    while pending and time.time() < deadline:
        time.sleep(args.poll)
        for ident in list(pending):
            state = client.power_state(ident)
            if state == "offline":
                log(f"✓ {pending[ident]} ({ident}) ist offline.")
                del pending[ident]
    # Zeitüberschreitung → Kill.
    if pending:
        for ident, name in pending.items():
            err(f"{name} ({ident}) nicht rechtzeitig gestoppt – sende 'kill'.")
            client.send_power(ident, "kill")
        # Kurz auf Kill warten.
        kdeadline = time.time() + args.kill_wait
        while pending and time.time() < kdeadline:
            time.sleep(args.poll)
            for ident in list(pending):
                if client.power_state(ident) == "offline":
                    del pending[ident]
    if pending:
        err(f"{len(pending)} Server weiterhin nicht offline: "
            f"{', '.join(pending.values())}")
        return 1
    log("Alle Zielserver sind offline.")
    return 0


def _identifiers_from_state(path: str, only_running: bool = True) -> list[str]:
    with open(path, encoding="utf-8") as fh:
        data = json.load(fh)
    servers = data.get("servers", {})
    idents = []
    for ident, info in servers.items():
        if not only_running or info.get("state") in POWER_STATES_ONLINE:
            idents.append(ident)
    return idents


def cmd_start(client: PteroClient, args) -> int:
    if args.from_state:
        idents = _identifiers_from_state(args.from_state, only_running=not args.all)
    elif args.servers:
        idents = [x.strip() for x in args.servers.split(",") if x.strip()]
    else:
        idents = [s.get("identifier") for s in client.list_servers()
                  if s.get("identifier")]
    if not idents:
        log("Keine Server zum Starten.")
        return 0
    log(f"Starte {len(idents)} Server …")
    for ident in idents:
        client.send_power(ident, "start")
    if args.wait and not client.dry_run:
        deadline = time.time() + args.wait
        pending = set(idents)
        while pending and time.time() < deadline:
            time.sleep(args.poll)
            for ident in list(pending):
                if client.power_state(ident) in POWER_STATES_ONLINE:
                    log(f"✓ {ident} läuft wieder.")
                    pending.discard(ident)
        if pending:
            err(f"{len(pending)} Server noch nicht online: {', '.join(pending)}")
            return 1
    return 0


def build_parser() -> argparse.ArgumentParser:
    p = argparse.ArgumentParser(
        description="Pterodactyl-Client-API-Helfer für den Wartungs-Workflow.")
    p.add_argument("--url", default=os.environ.get("PTERODACTYL_URL", ""),
                   help="Panel-Basis-URL (Default: $PTERODACTYL_URL).")
    p.add_argument("--key", default=os.environ.get("PTERODACTYL_API_KEY", ""),
                   help="Client-API-Schlüssel (Default: $PTERODACTYL_API_KEY).")
    p.add_argument("--timeout", type=int, default=DEFAULT_TIMEOUT,
                   help=f"HTTP-Timeout in Sekunden (Default: {DEFAULT_TIMEOUT}).")
    p.add_argument("--dry-run", action="store_true",
                   help="Nichts verändern; Aktionen nur protokollieren.")
    sub = p.add_subparsers(dest="command", required=True)

    sp = sub.add_parser("list", help="Server auflisten (mit Zustand).")
    sp.add_argument("--json", action="store_true", help="Als JSON ausgeben.")
    sp.set_defaults(func=cmd_list)

    sp = sub.add_parser("state", help="Zustände als JSON ausgeben/speichern.")
    sp.add_argument("--save", help="Zustand zusätzlich in Datei schreiben.")
    sp.set_defaults(func=cmd_state)

    sp = sub.add_parser("announce", help="Einmalige Ansage an alle Server.")
    sp.add_argument("--message", required=True, help="Nachricht an die Spieler.")
    sp.add_argument("--only", action="append", default=[],
                    help="Nur diese Server (Identifier/Name); mehrfach nutzbar.")
    sp.add_argument("--proxy-match", default="proxy,velocity,bungee",
                    help="Namensfragmente, die einen Proxy kennzeichnen.")
    sp.add_argument("--proxy-command", default="alert",
                    help="Broadcast-Befehl auf dem Proxy (Default: alert).")
    sp.set_defaults(func=cmd_announce)

    sp = sub.add_parser("countdown", help="Mehrere Vorwarnungen in Intervallen.")
    sp.add_argument("--steps", default="15,10,5,1",
                    help="Minuten-Marken, kommagetrennt (Default: 15,10,5,1).")
    sp.add_argument("--template",
                    default="[Wartung] Server-Neustart in {minutes_text}. "
                            "Bitte beende deine Aktivitäten rechtzeitig.",
                    help="Vorlage mit {minutes} (Zahl) und/oder {minutes_text} "
                         "(z. B. '1 Minute' / '10 Minuten').")
    sp.add_argument("--final-message",
                    default="[Wartung] Server wird jetzt heruntergefahren. Bis gleich!",
                    help="Nachricht beim T-0-Schritt.")
    sp.add_argument("--only", action="append", default=[])
    sp.add_argument("--proxy-match", default="proxy,velocity,bungee")
    sp.add_argument("--proxy-command", default="alert")
    sp.add_argument("--no-sleep", action="store_true",
                    help="Nicht zwischen den Schritten warten (für Tests).")
    sp.set_defaults(func=cmd_countdown)

    sp = sub.add_parser("graceful-stop", help="Server sauber stoppen (+ ggf. killen).")
    sp.add_argument("--wait", type=int, default=180,
                    help="Max. Sekunden auf 'offline' warten (Default: 180).")
    sp.add_argument("--kill-wait", type=int, default=30,
                    help="Sekunden nach 'kill' auf 'offline' warten (Default: 30).")
    sp.add_argument("--poll", type=int, default=5,
                    help="Poll-Intervall in Sekunden (Default: 5).")
    sp.add_argument("--save-state", help="Zustand vor dem Stopp hier sichern.")
    sp.add_argument("--only", action="append", default=[])
    sp.set_defaults(func=cmd_graceful_stop)

    sp = sub.add_parser("start", help="Server (wieder) starten.")
    g = sp.add_mutually_exclusive_group()
    g.add_argument("--from-state", help="Zustandsdatei aus 'graceful-stop'.")
    g.add_argument("--servers", help="Kommagetrennte Identifier.")
    sp.add_argument("--all", action="store_true",
                    help="Mit --from-state: alle gesicherten Server, nicht nur zuvor laufende.")
    sp.add_argument("--wait", type=int, default=0,
                    help="Optional auf 'running' warten (Sekunden).")
    sp.add_argument("--poll", type=int, default=5)
    sp.set_defaults(func=cmd_start)

    return p


def main(argv: list[str] | None = None) -> int:
    args = build_parser().parse_args(argv)
    try:
        client = PteroClient(args.url, args.key, timeout=args.timeout,
                             dry_run=args.dry_run)
    except ValueError as exc:
        err(str(exc))
        return 2
    try:
        return args.func(client, args)
    except RuntimeError as exc:
        err(str(exc))
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
