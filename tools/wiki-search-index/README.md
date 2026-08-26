# wiki-search-index

Generates the **static client-side search index** for the wiki
(`website/wiki/search-index.json`).

The wiki is a set of hand-maintained static HTML pages served by nginx with **no
backend**. To offer search without a server, this tool renders a small JSON index
that `website/js/main.js` (`initWikiSearch`) loads in the browser and queries
entirely client-side — matching the site's static Docker hosting.

## What it does

For every `*.html` page under `website/wiki/` it captures:

| Field | Source |
|-------|--------|
| `title` | `<title>` (with the `… | Festas Builds` suffix stripped), falling back to the first heading |
| `headings` | the page's `<h1>`/`<h2>`/`<h3>` text (excluding a heading identical to the title) |
| `excerpt` | the first intro paragraph (`.wiki-intro` or an unclassed `<p>`) inside `main.wiki-content` |
| `keywords` | a lower-cased blob of title + headings + excerpt used for matching |
| `url` | page path relative to the wiki root (e.g. `spielmodi/survival.html`) |

Navigation chrome (`<nav>`, `<aside>`, `<script>`, `<style>`) is ignored so the
index only reflects real page content.

## Usage

```bash
python3 tools/wiki-search-index/build_index.py
```

Run it **after editing wiki pages** (or adding/removing a page) and commit the
regenerated `website/wiki/search-index.json`. The script uses only the Python
standard library — no dependencies to install.

## Safety

`url`, `title`, `headings` and `excerpt` are rendered in the browser with
`textContent` only (never `innerHTML`), so indexed page text can never inject
markup into the search dropdown.
