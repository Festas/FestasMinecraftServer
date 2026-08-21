#!/usr/bin/env python3
"""Erzeugt aus report.json eine kompakte Markdown-Zusammenfassung.

Wird im Workflow für die GitHub-Step-Summary genutzt:
    python3 report_summary.py report-artifacts/report.json >> "$GITHUB_STEP_SUMMARY"
"""
from __future__ import annotations

import json
import sys

STATUS_EMOJI = {"OK": "🟢", "WARN": "🟡", "CRIT": "🔴"}
LEVEL_EMOJI = {"CRIT": "🔴", "WARN": "🟡", "INFO": "🔵"}


def render(data: dict) -> str:
    out: list[str] = []
    status = data.get("status", "?")
    emoji = STATUS_EMOJI.get(status, "⚪")
    out.append("## 🩺 Server-Wartung – Ergebnis")
    out.append("")
    out.append(f"**Status:** {emoji} {status}  ")
    dry = " · Dry-Run" if data.get("dry_run") else ""
    out.append(f"**Modus:** `{data.get('mode', '?')}`{dry}  ")
    out.append(
        f"**Festplatte /:** {data.get('disk_root_used_pct', '?')} % · "
        f"**RAM:** {data.get('mem_used_pct', '?')} % · "
        f"**Updates offen:** {data.get('apt_upgradable', '?')} "
        f"({data.get('apt_security', '?')} sicherheitsrelevant) · "
        f"**Reboot nötig:** {'ja' if data.get('reboot_required') else 'nein'}"
    )
    issues = data.get("issues", [])
    if issues:
        out.append("")
        out.append("**Wichtigste Befunde:**")
        out.append("")
        for it in issues[:15]:
            em = LEVEL_EMOJI.get(it.get("level"), "•")
            out.append(f"- {em} {it.get('text')}")
    recs = data.get("recommendations", [])
    if recs:
        out.append("")
        out.append("**Empfehlungen:**")
        out.append("")
        for r in recs[:15]:
            out.append(f"- {r}")
    out.append("")
    return "\n".join(out)


def main(argv: list[str]) -> int:
    path = argv[1] if len(argv) > 1 else "report.json"
    try:
        with open(path, encoding="utf-8") as fh:
            data = json.load(fh)
    except (OSError, ValueError) as exc:
        print(f"## 🩺 Server-Wartung\n\n_Bericht nicht lesbar: {exc}_")
        return 0
    print(render(data))
    return 0


if __name__ == "__main__":
    raise SystemExit(main(sys.argv))
