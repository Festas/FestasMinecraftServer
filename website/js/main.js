// festas_builds – Main JavaScript

document.addEventListener('DOMContentLoaded', function () {
    initVersionDisplay();
    initThemeToggle();
    initCopyButton();
    initServerStatus();
    initPlayerList();
    initNavigationAndScroll();
    initSmoothScroll();
    initClassTabs();
    initScrollReveal();
});

/* ── Version Display ────────────────────────── */
function initVersionDisplay() {
    if (typeof window.MC_CONFIG === 'undefined') return;

    const versionEl = document.getElementById('minecraftVersion');
    const softwareEl = document.getElementById('serverSoftware');

    if (versionEl && window.MC_CONFIG.minecraftVersion) {
        versionEl.textContent = window.MC_CONFIG.minecraftVersion;
    }
    if (softwareEl && window.MC_CONFIG.serverSoftware) {
        softwareEl.textContent = window.MC_CONFIG.serverSoftware;
    }
}

/* ── Theme Toggle ───────────────────────────── */
class ThemeManager {
    constructor() {
        this.theme = this._getInitialTheme();
        this._apply(this.theme);
        this._watchSystem();
    }

    _getInitialTheme() {
        const saved = localStorage.getItem('theme');
        if (saved) return saved;
        return window.matchMedia('(prefers-color-scheme: light)').matches ? 'light' : 'dark';
    }

    _apply(theme) {
        document.documentElement.setAttribute('data-theme', theme);
    }

    toggle() {
        this.theme = this.theme === 'dark' ? 'light' : 'dark';
        this._apply(this.theme);
        localStorage.setItem('theme', this.theme);
    }

    _watchSystem() {
        window.matchMedia('(prefers-color-scheme: light)').addEventListener('change', (e) => {
            if (!localStorage.getItem('theme')) {
                this.theme = e.matches ? 'light' : 'dark';
                this._apply(this.theme);
            }
        });
    }
}

function initThemeToggle() {
    const manager = new ThemeManager();
    const btn = document.getElementById('themeToggle');
    if (!btn) return;

    btn.addEventListener('click', () => {
        manager.toggle();
        btn.style.transform = 'rotate(360deg)';
        setTimeout(() => { btn.style.transform = ''; }, 300);
    });
}

/* ── Copy Server IP ─────────────────────────── */
function initCopyButton() {
    const btn = document.getElementById('copyBtn');
    const ipEl = document.getElementById('serverIp');
    if (!btn || !ipEl) return;

    btn.addEventListener('click', async () => {
        const text = ipEl.textContent.trim();
        try {
            await navigator.clipboard.writeText(text);
        } catch {
            _fallbackCopy(text);
        }
        _showCopyFeedback(btn);
    });
}

function _fallbackCopy(text) {
    const ta = document.createElement('textarea');
    ta.value = text;
    ta.style.cssText = 'position:fixed;left:-9999px;top:-9999px';
    document.body.appendChild(ta);
    ta.focus();
    ta.select();
    try { document.execCommand('copy'); } catch { /* ignore */ }
    document.body.removeChild(ta);
}

function _showCopyFeedback(btn) {
    const original = btn.innerHTML;
    btn.textContent = '✓ Kopiert!';
    btn.style.background = 'var(--accent)';
    btn.style.color = '#fff';
    setTimeout(() => {
        btn.innerHTML = original;
        btn.style.background = '';
        btn.style.color = '';
    }, 2000);
}

/* ── Server Status ──────────────────────────── */
function initServerStatus() {
    const cfg = window.MC_CONFIG || {};
    const ipEl = document.getElementById('serverIp');
    const serverAddress = String(cfg.serverAddress || (ipEl && ipEl.textContent) || 'mc.festas-builds.com').trim();
    const statusBase = String(cfg.statusAPI || 'https://api.mcsrvstat.us/3/').replace(/\/+$/, '/');
    const API = statusBase + encodeURIComponent(serverAddress);

    async function checkStatus() {
        const indicator = document.querySelector('.status-indicator');
        const statusText = document.querySelector('.status-text');
        const playerCount = document.getElementById('playerCount');

        try {
            const res = await fetch(API);
            if (!res.ok) throw new Error('HTTP ' + res.status);
            const data = await res.json();

            if (data.online) {
                if (indicator) { indicator.className = 'status-indicator online'; }
                if (statusText) statusText.textContent = 'Online';
                if (playerCount) {
                    const cur = data.players?.online ?? 0;
                    const max = data.players?.max ?? 0;
                    playerCount.textContent = max > 0
                        ? cur + '/' + max + ' Spieler online'
                        : cur + ' Spieler online';
                }
            } else {
                if (indicator) { indicator.className = 'status-indicator offline'; }
                if (statusText) statusText.textContent = 'Offline';
                if (playerCount) playerCount.textContent = '– Spieler online';
            }
        } catch {
            if (indicator) { indicator.className = 'status-indicator'; }
            if (statusText) statusText.textContent = 'Status unbekannt';
            if (playerCount) playerCount.textContent = '– Spieler online';
        }
    }

    checkStatus();
    setInterval(checkStatus, 60000);
}

/* ── Live Player List ───────────────────────── */
function initPlayerList() {
    const grid = document.getElementById('playersGrid');
    const summary = document.getElementById('playersSummary');
    const dot = document.getElementById('playersStatusDot');
    const updated = document.getElementById('playersUpdated');
    if (!grid || !summary) return;

    const cfg = window.MC_CONFIG || {};
    const API = cfg.playersAPI || '/api/players.json';
    const REFRESH_MS = 30000;
    const STALE_AFTER_S = 90;

    // Known backend servers get a friendly icon + label; unknown names fall back gracefully.
    const SERVER_META = {
        lobby: { icon: '🏛️', label: 'Lobby' },
        survival: { icon: '🏭', label: 'Survival' },
        mining: { icon: '⛏️', label: 'Mining' },
        skyblock: { icon: '🏝️', label: 'Skyblock' }
    };

    function metaFor(name) {
        return SERVER_META[String(name).toLowerCase()] || { icon: '🌐', label: String(name) };
    }

    function clear(el) {
        while (el.firstChild) el.removeChild(el.firstChild);
    }

    function setState(state) {
        if (dot) dot.className = 'status-indicator' + (state ? ' ' + state : '');
    }

    function playersLabel(n) {
        return n === 1 ? '1 Spieler' : n + ' Spieler';
    }

    function buildCard(server, showNames) {
        const info = metaFor(server.name);
        const count = Math.max(0, Number(server.count) || 0);

        const card = document.createElement('article');
        card.className = 'player-server';

        const head = document.createElement('div');
        head.className = 'player-server-head';

        const icon = document.createElement('span');
        icon.className = 'player-server-icon';
        icon.setAttribute('aria-hidden', 'true');
        icon.textContent = info.icon;
        head.appendChild(icon);

        const title = document.createElement('h3');
        title.className = 'player-server-name';
        // textContent keeps user-controlled server/player names inert (no HTML injection).
        title.textContent = info.label;
        head.appendChild(title);

        const badge = document.createElement('span');
        badge.className = 'player-server-count';
        badge.textContent = String(count);
        head.appendChild(badge);

        card.appendChild(head);

        const body = document.createElement('div');
        body.className = 'player-server-body';

        if (count === 0) {
            const empty = document.createElement('p');
            empty.className = 'player-empty';
            empty.textContent = 'Niemand online';
            body.appendChild(empty);
        } else if (showNames && Array.isArray(server.players) && server.players.length) {
            const list = document.createElement('ul');
            list.className = 'player-chips';
            server.players.forEach((name) => {
                const chip = document.createElement('li');
                chip.className = 'player-chip';
                chip.textContent = String(name);
                list.appendChild(chip);
            });
            body.appendChild(list);
        } else {
            const note = document.createElement('p');
            note.className = 'player-empty';
            note.textContent = playersLabel(count) + ' online';
            body.appendChild(note);
        }

        card.appendChild(body);
        return card;
    }

    function render(data) {
        const servers = Array.isArray(data.servers) ? data.servers : [];
        const showNames = data.showNames !== false;
        const totalFromServers = servers.reduce((sum, server) => sum + Math.max(0, Number(server.count) || 0), 0);
        const online = Math.max(0, Number(data.online) || totalFromServers);

        setState('online');
        summary.textContent = online === 0
            ? 'Gerade ist niemand online'
            : playersLabel(online) + ' online';

        clear(grid);
        if (!servers.length) {
            const note = document.createElement('p');
            note.className = 'player-empty';
            note.textContent = 'Keine Serverdaten verfügbar';
            grid.appendChild(note);
        } else {
            servers.forEach((server) => grid.appendChild(buildCard(server, showNames)));
        }

        if (updated) {
            const ts = Number(data.updated) || 0;
            const ageS = ts > 0 ? Math.floor(Date.now() / 1000) - ts : 0;
            if (ts > 0 && ageS > STALE_AFTER_S) {
                updated.hidden = false;
                updated.textContent = 'Daten evtl. veraltet – Proxy nicht erreichbar?';
                setState('');
            } else {
                updated.hidden = true;
            }
        }
    }

    function renderUnavailable() {
        clear(grid);
        setState('offline');
        summary.textContent = 'Spielerliste momentan nicht verfügbar';
        if (updated) updated.hidden = true;
    }

    async function refresh() {
        try {
            const res = await fetch(API, { cache: 'no-store' });
            if (!res.ok) throw new Error('HTTP ' + res.status);
            const data = await res.json();
            render(data);
        } catch {
            renderUnavailable();
        }
    }

    refresh();
    setInterval(refresh, REFRESH_MS);
}

/* ── Navigation & Scroll ────────────────────── */
function initNavigationAndScroll() {
    const header = document.getElementById('siteHeader');
    const navToggle = document.getElementById('navToggle');
    const mainNav = document.getElementById('mainNav');
    const overlay = document.getElementById('mobileOverlay');

    // Sticky scroll class
    if (header) {
        window.addEventListener('scroll', () => {
            header.classList.toggle('scrolled', window.scrollY > 20);
        }, { passive: true });
    }

    // Hamburger toggle with overlay
    if (navToggle && mainNav) {
        function openMenu() {
            mainNav.classList.add('mobile-open');
            navToggle.classList.add('open');
            navToggle.setAttribute('aria-expanded', 'true');
            navToggle.setAttribute('aria-label', 'Menü schließen');
            if (overlay) overlay.classList.add('active');
            document.body.style.overflow = 'hidden';
        }

        function closeMenu() {
            mainNav.classList.remove('mobile-open');
            navToggle.classList.remove('open');
            navToggle.setAttribute('aria-expanded', 'false');
            navToggle.setAttribute('aria-label', 'Menü öffnen');
            if (overlay) overlay.classList.remove('active');
            document.body.style.overflow = '';
        }

        navToggle.addEventListener('click', () => {
            const isOpen = mainNav.classList.contains('mobile-open');
            if (isOpen) {
                closeMenu();
            } else {
                openMenu();
            }
        });

        // Close on overlay click
        if (overlay) {
            overlay.addEventListener('click', closeMenu);
        }

        // Close on nav link click
        mainNav.querySelectorAll('.nav-link').forEach(link => {
            link.addEventListener('click', closeMenu);
        });

        // Close on Escape key
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && mainNav.classList.contains('mobile-open')) {
                closeMenu();
            }
        });
    }

    // Active nav link on scroll
    const sections = document.querySelectorAll('section[id]');
    const navLinks = document.querySelectorAll('.nav-link[href^="#"]');

    if (sections.length && navLinks.length) {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const id = entry.target.id;
                    navLinks.forEach(link => {
                        link.classList.toggle('active', link.getAttribute('href') === '#' + id);
                    });
                }
            });
        }, { rootMargin: '-40% 0px -55% 0px' });

        sections.forEach(s => observer.observe(s));
    }
}

/* ── Smooth Scroll ──────────────────────────── */
function initSmoothScroll() {
    var HEADER_FALLBACK = 60;
    function readHeaderH() {
        return parseInt(getComputedStyle(document.documentElement).getPropertyValue('--header-h')) || HEADER_FALLBACK;
    }
    let headerH = readHeaderH();
    window.addEventListener('resize', () => {
        headerH = readHeaderH();
    }, { passive: true });

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', (e) => {
            const id = anchor.getAttribute('href').slice(1);
            if (!id) return;
            const target = document.getElementById(id);
            if (!target) return;
            e.preventDefault();
            const top = target.getBoundingClientRect().top + window.scrollY - headerH;
            window.scrollTo({ top, behavior: 'smooth' });
        });
    });
}

/* ── Class Tabs ─────────────────────────────── */
function initClassTabs() {
    const tabs = document.querySelectorAll('.class-tab');
    const panels = document.querySelectorAll('.class-panel');

    if (!tabs.length || !panels.length) return;

    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const cls = tab.dataset.class;

            tabs.forEach(t => {
                t.classList.remove('active');
                t.setAttribute('aria-selected', 'false');
            });
            panels.forEach(p => p.classList.remove('active'));

            tab.classList.add('active');
            tab.setAttribute('aria-selected', 'true');

            const panel = document.querySelector(`.class-panel[data-class="${cls}"]`);
            if (panel) panel.classList.add('active');
        });
    });
}

/* ── Scroll Reveal ──────────────────────────── */
function initScrollReveal() {
    const elements = document.querySelectorAll('.reveal');
    if (!elements.length) return;

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1, rootMargin: '0px 0px -40px 0px' });

    elements.forEach(el => observer.observe(el));
}
