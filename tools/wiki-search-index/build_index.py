#!/usr/bin/env python3
"""Build the static client-side search index for the wiki.

The wiki (``website/wiki/``) is a set of hand-maintained static HTML pages served
by nginx with no backend. To offer search without a server we render a small
JSON index that ``website/js/main.js`` (``initWikiSearch``) loads in the browser
and queries entirely client-side.

The index is intentionally tiny and deterministic: for every ``*.html`` page under
the wiki root we capture the page title, its headings (``h1``/``h2``/``h3``), a short
excerpt, and a keyword blob built from those. No page content is trusted as HTML on
the client — ``main.js`` renders every field with ``textContent``.

Usage::

    python3 tools/wiki-search-index/build_index.py

Run it after editing wiki pages and commit the regenerated
``website/wiki/search-index.json``.
"""

from __future__ import annotations

import json
import re
from html.parser import HTMLParser
from pathlib import Path

# Repository layout: this file lives at tools/wiki-search-index/build_index.py
REPO_ROOT = Path(__file__).resolve().parents[2]
WIKI_ROOT = REPO_ROOT / "website" / "wiki"
OUTPUT = WIKI_ROOT / "search-index.json"

TITLE_SUFFIX = re.compile(r"\s*[|\u2013-]\s*Festas Builds.*$", re.IGNORECASE)
WHITESPACE = re.compile(r"\s+")

# Skip elements whose text is navigation chrome rather than page content.
SKIP_CONTAINERS = {"script", "style", "aside", "nav"}


def _clean(text: str) -> str:
    return WHITESPACE.sub(" ", text).strip()


class WikiPageParser(HTMLParser):
    """Extract title, headings and the first intro paragraph from a wiki page."""

    def __init__(self) -> None:
        super().__init__(convert_charrefs=True)
        self.title = ""
        self.headings: list[str] = []
        self.excerpt = ""
        self._skip_depth = 0
        self._capture: str | None = None
        self._buffer: list[str] = []
        self._in_content = False

    def handle_starttag(self, tag, attrs):
        attrs_d = dict(attrs)
        if tag == "main" and "wiki-content" in (attrs_d.get("class") or ""):
            self._in_content = True
        if tag in SKIP_CONTAINERS:
            self._skip_depth += 1
            return
        if self._skip_depth:
            return
        if tag == "title":
            self._begin("title")
        elif tag in ("h1", "h2", "h3"):
            self._begin("heading")
        elif tag == "p" and not self.excerpt and self._in_content:
            classes = attrs_d.get("class") or ""
            if "wiki-intro" in classes or classes == "":
                self._begin("excerpt")

    def handle_endtag(self, tag):
        if tag == "main":
            self._in_content = False
        if tag in SKIP_CONTAINERS and self._skip_depth:
            self._skip_depth -= 1
            return
        if self._capture and tag in ("title", "h1", "h2", "h3", "p"):
            self._end()

    def handle_data(self, data):
        if self._capture and not self._skip_depth:
            self._buffer.append(data)

    def _begin(self, kind: str) -> None:
        self._capture = kind
        self._buffer = []

    def _end(self) -> None:
        text = _clean("".join(self._buffer))
        kind, self._capture, self._buffer = self._capture, None, []
        if not text:
            return
        if kind == "title" and not self.title:
            self.title = text
        elif kind == "heading":
            self.headings.append(text)
        elif kind == "excerpt" and not self.excerpt:
            self.excerpt = text


def build_entry(path: Path) -> dict | None:
    parser = WikiPageParser()
    parser.feed(path.read_text(encoding="utf-8"))

    url = path.relative_to(WIKI_ROOT).as_posix()
    title = TITLE_SUFFIX.sub("", parser.title).strip()
    if not title:
        title = parser.headings[0] if parser.headings else url

    # A concise heading list (drop the page's own h1 duplicate of the title).
    headings = [h for h in parser.headings if _clean(h) != _clean(title)]

    keyword_source = " ".join([title, *headings, parser.excerpt])
    keywords = _clean(keyword_source.lower())

    return {
        "title": title,
        "url": url,
        "headings": headings[:8],
        "excerpt": (parser.excerpt or "")[:180],
        "keywords": keywords,
    }


def main() -> int:
    if not WIKI_ROOT.is_dir():
        raise SystemExit(f"wiki root not found: {WIKI_ROOT}")

    pages = sorted(p for p in WIKI_ROOT.rglob("*.html") if p.is_file())
    entries = []
    for page in pages:
        entry = build_entry(page)
        if entry:
            entries.append(entry)

    entries.sort(key=lambda e: e["url"])
    payload = {
        "generator": "wiki-search-index",
        "count": len(entries),
        "pages": entries,
    }

    OUTPUT.write_text(
        json.dumps(payload, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    print(f"wrote {OUTPUT.relative_to(REPO_ROOT)} with {len(entries)} pages")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
