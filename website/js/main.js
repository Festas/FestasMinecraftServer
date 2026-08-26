// festas_builds – Main JavaScript

document.addEventListener('DOMContentLoaded', function () {
    initVersionDisplay();
    initThemeToggle();
    initCopyButton();
    initServerStatus();
    initPlayerList();
    initLeaderboard();
    initCommandReference();
    initNavigationAndScroll();
    initWikiSidebar();
    initSmoothScroll();
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
function toPositiveNumberOrZero(value) {
    const n = Number(value);
    return Number.isFinite(n) && n > 0 ? n : 0;
}

function inferServerOnline(server) {
    if (!server || typeof server !== 'object') return false;
    if (typeof server.online === 'boolean') return server.online;

    const count = resolvedCount(server);
    if (count > 0) return true;

    if (Array.isArray(server.players) && server.players.length > 0) return true;
    if (normalizeWorlds(server.worlds).some((world) => inferWorldOnline(world))) return true;
    if (toPositiveNumberOrZero(server.uptimeSeconds) > 0) return true;

    return false;
}

function normalizeWorlds(value) {
    return Array.isArray(value) ? value.filter((entry) => entry && typeof entry === 'object') : [];
}

function inferWorldOnline(world) {
    if (!world || typeof world !== 'object') return false;
    if (typeof world.online === 'boolean') return world.online;
    if (toPositiveNumberOrZero(world.count) > 0) return true;
    if (Array.isArray(world.players) && world.players.length > 0) return true;
    return false;
}

function normalizeServers(value) {
    return Array.isArray(value) ? value.filter((entry) => entry && typeof entry === 'object') : [];
}

function resolvedCount(entry) {
    const worlds = normalizeWorlds(entry && entry.worlds);
    const worldsCount = worlds.reduce((sum, world) => sum + toPositiveNumberOrZero(world.count), 0);
    const direct = Number(entry && entry.count);
    if (Number.isFinite(direct) && direct >= 0) {
        return direct === 0 && worldsCount > 0 ? worldsCount : direct;
    }

    return worldsCount;
}

function resolvedMax(entry) {
    const direct = Number(entry && entry.max);
    if (Number.isFinite(direct) && direct > 0) return direct;

    return normalizeWorlds(entry && entry.worlds)
        .reduce((sum, world) => sum + toPositiveNumberOrZero(world.max), 0);
}

function sumServerCounts(servers) {
    return servers.reduce((sum, server) => sum + resolvedCount(server), 0);
}

function sumServerMax(servers) {
    return servers.reduce((sum, server) => sum + resolvedMax(server), 0);
}

function totalOnlineFromSnapshot(snapshot) {
    const servers = normalizeServers(snapshot && snapshot.servers);
    const totalFromServers = sumServerCounts(servers);
    const rootOnline = Number(snapshot && snapshot.online);
    return Number.isFinite(rootOnline) && rootOnline >= 0 ? rootOnline : totalFromServers;
}

function totalMaxFromSnapshot(snapshot) {
    const servers = normalizeServers(snapshot && snapshot.servers);
    const totalFromServers = sumServerMax(servers);
    const rootMax = Number(snapshot && snapshot.max);
    return Number.isFinite(rootMax) && rootMax > 0 ? rootMax : totalFromServers;
}

function formatCountWithOptionalMax(count, max) {
    return max > 0 ? count + '/' + max : String(count);
}

function formatDuration(totalSeconds) {
    const seconds = Math.floor(Number(totalSeconds));
    if (!Number.isFinite(seconds) || seconds < 0) return '—';

    const days = Math.floor(seconds / 86400);
    const hours = Math.floor((seconds % 86400) / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);

    const parts = [];
    if (days > 0) parts.push(days + 'd');
    if (hours > 0 || days > 0) parts.push(hours + 'h');
    parts.push(minutes + 'm');
    return parts.join(' ');
}

function initServerStatus() {
    const cfg = window.MC_CONFIG || {};
    const ipEl = document.getElementById('serverIp');
    const serverAddress = String(cfg.serverAddress || (ipEl && ipEl.textContent) || 'mc.festas-builds.com').trim();
    const playersAPI = String(cfg.playersAPI || '/api/players.json');

    function buildStatusSources() {
        const configured = Array.isArray(cfg.publicStatusSources) ? cfg.publicStatusSources : [];
        const legacy = cfg.statusAPI ? [{ name: 'mcsrvstat.us', type: 'mcsrvstat', url: cfg.statusAPI }] : [];
        const defaults = [
            { name: 'mcsrvstat.us', type: 'mcsrvstat', url: 'https://api.mcsrvstat.us/3/' },
            { name: 'mcstatus.io', type: 'mcstatusio', url: 'https://api.mcstatus.io/v2/status/java/' }
        ];

        const unique = new Map();
        [...configured, ...legacy, ...defaults].forEach((source) => {
            if (!source || typeof source !== 'object') return;
            const type = String(source.type || 'mcsrvstat').trim().toLowerCase();
            const url = String(source.url || '').trim();
            if (!url) return;
            const key = type + '|' + url;
            if (!unique.has(key)) unique.set(key, { name: source.name || type, type, url });
        });

        return Array.from(unique.values()).map((source) => ({
            name: String(source.name || source.type),
            type: source.type,
            requestURL: String(source.url).replace(/\/+$/, '/') + encodeURIComponent(serverAddress)
        }));
    }

    async function fetchJSON(url, timeoutMs) {
        const controller = new AbortController();
        let timeoutId = null;

        try {
            timeoutId = window.setTimeout(() => controller.abort(), timeoutMs);
            const res = await fetch(url, {
                cache: 'no-store',
                signal: controller.signal
            });
            if (!res.ok) throw new Error('HTTP ' + res.status);
            return await res.json();
        } finally {
            if (timeoutId) window.clearTimeout(timeoutId);
        }
    }

    function parsePublicStatus(source, data) {
        if (source.type === 'mcstatusio') {
            const online = typeof data?.online === 'boolean'
                ? data.online
                : String(data?.status || '').toLowerCase() === 'online';
            return {
                online,
                current: toPositiveNumberOrZero(data?.players?.online),
                max: toPositiveNumberOrZero(data?.players?.max)
            };
        }

        return {
            online: Boolean(data && data.online),
            current: toPositiveNumberOrZero(data && data.players && data.players.online),
            max: toPositiveNumberOrZero(data && data.players && data.players.max)
        };
    }

    async function readPlayersFallback() {
        try {
            const data = await fetchJSON(playersAPI, 5000);
            return data && typeof data === 'object' ? data : null;
        } catch {
            return null;
        }
    }

    function setHeroState(state, text, countText) {
        const indicator = document.querySelector('.status-indicator');
        const statusText = document.querySelector('.status-text');
        const playerCount = document.getElementById('playerCount');

        if (indicator) indicator.className = state ? 'status-indicator ' + state : 'status-indicator';
        if (statusText) statusText.textContent = text;
        if (playerCount) playerCount.textContent = countText;
    }

    async function checkStatus() {
        for (const source of buildStatusSources()) {
            try {
                const data = await fetchJSON(source.requestURL, 5000);
                const parsed = parsePublicStatus(source, data);

                if (parsed.online) {
                    setHeroState('online', 'Online', formatCountWithOptionalMax(parsed.current, parsed.max) + ' Spieler online');
                } else {
                    setHeroState('offline', 'Offline', '0 Spieler online');
                }
                return;
            } catch {
                // Try next public source.
            }
        }

        const fallback = await readPlayersFallback();
        if (fallback) {
            const online = totalOnlineFromSnapshot(fallback);
            const max = totalMaxFromSnapshot(fallback);
            const anyOnline = normalizeServers(fallback.servers).some((server) => inferServerOnline(server));
            const state = anyOnline ? 'limited' : 'offline';
            const label = anyOnline ? 'Eingeschränkt' : 'Offline';
            setHeroState(state, label, formatCountWithOptionalMax(online, max) + ' Spieler online');
            return;
        }

        setHeroState('', 'Status unbekannt', '– Spieler online');
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

    function latestUpdatedTimestamp(rootUpdated, servers) {
        let latest = toPositiveNumberOrZero(rootUpdated);
        servers.forEach((server) => {
            latest = Math.max(latest, toPositiveNumberOrZero(server.updated));
            normalizeWorlds(server.worlds).forEach((world) => {
                latest = Math.max(latest, toPositiveNumberOrZero(world.updated));
            });
        });
        return latest;
    }

    function buildPlayersChips(players) {
        const list = document.createElement('ul');
        list.className = 'player-chips';
        players.forEach((name) => {
            const chip = document.createElement('li');
            chip.className = 'player-chip';
            chip.textContent = String(name);
            list.appendChild(chip);
        });
        return list;
    }

    function buildWorldItem(world, showNames) {
        const count = toPositiveNumberOrZero(world.count);
        const max = toPositiveNumberOrZero(world.max);
        const online = inferWorldOnline(world);
        const hasNames = showNames && Array.isArray(world.players) && world.players.length > 0;

        const item = document.createElement('li');
        item.className = 'player-world';
        if (hasNames) item.classList.add('has-players');

        const row = document.createElement('div');
        row.className = 'player-world-row';

        const name = document.createElement('span');
        name.className = 'player-world-name';
        name.textContent = String(world.name || 'Unbekannte Welt');
        row.appendChild(name);

        const badge = document.createElement('span');
        badge.className = 'player-world-count';
        badge.textContent = formatCountWithOptionalMax(count, max);
        row.appendChild(badge);

        const status = document.createElement('span');
        status.className = 'player-world-status ' + (online ? 'online' : 'offline');
        status.textContent = online ? 'Online' : 'Offline';
        row.appendChild(status);

        item.appendChild(row);

        if (hasNames) {
            item.appendChild(buildPlayersChips(world.players));
        }

        return item;
    }

    function buildCard(server, showNames) {
        const info = metaFor(server.name);
        const online = inferServerOnline(server);
        const count = resolvedCount(server);
        const max = resolvedMax(server);
        const uptime = formatDuration(server.uptimeSeconds);
        const worlds = normalizeWorlds(server.worlds);

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

        const status = document.createElement('span');
        status.className = 'player-server-status ' + (online ? 'online' : 'offline');
        status.textContent = online ? 'Online' : 'Offline';
        head.appendChild(status);

        const badge = document.createElement('span');
        badge.className = 'player-server-count';
        badge.textContent = formatCountWithOptionalMax(count, max);
        head.appendChild(badge);

        card.appendChild(head);

        const body = document.createElement('div');
        body.className = 'player-server-body';

        const uptimeLine = document.createElement('p');
        uptimeLine.className = 'player-meta';
        uptimeLine.textContent = 'Uptime: ' + uptime;
        body.appendChild(uptimeLine);

        if (worlds.length) {
            const hasWorldNames = showNames && worlds.some((world) => Array.isArray(world.players) && world.players.length);

            const worldsTitle = document.createElement('p');
            worldsTitle.className = 'player-worlds-label';
            worldsTitle.textContent = 'Welten';
            body.appendChild(worldsTitle);

            const worldsList = document.createElement('ul');
            worldsList.className = 'player-worlds';
            worlds.forEach((world) => worldsList.appendChild(buildWorldItem(world, showNames)));
            body.appendChild(worldsList);

            if (count === 0 && !worlds.some((world) => inferWorldOnline(world))) {
                const empty = document.createElement('p');
                empty.className = 'player-empty';
                empty.textContent = 'Niemand online';
                body.appendChild(empty);
            }

            if (!hasWorldNames && showNames && Array.isArray(server.players) && server.players.length) {
                body.appendChild(buildPlayersChips(server.players));
            }
        } else if (count === 0) {
            const empty = document.createElement('p');
            empty.className = 'player-empty';
            empty.textContent = 'Niemand online';
            body.appendChild(empty);
        } else if (showNames && Array.isArray(server.players) && server.players.length) {
            body.appendChild(buildPlayersChips(server.players));
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
        const snapshot = data && typeof data === 'object' ? data : {};
        const servers = normalizeServers(snapshot.servers);
        const showNames = snapshot.showNames !== false;
        const online = totalOnlineFromSnapshot(snapshot);
        const max = totalMaxFromSnapshot(snapshot);
        const anyOnline = servers.some((server) => inferServerOnline(server));

        setState(anyOnline ? 'online' : 'offline');
        summary.textContent = online === 0
            ? 'Gerade ist niemand online'
            : formatCountWithOptionalMax(online, max) + ' Spieler online';

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
            const ts = latestUpdatedTimestamp(snapshot.updated, servers);
            const ageS = ts > 0 ? Math.floor(Date.now() / 1000) - ts : 0;
            if (ts > 0 && ageS > STALE_AFTER_S) {
                updated.hidden = false;
                updated.textContent = 'Daten möglicherweise veraltet';
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
        const note = document.createElement('p');
        note.className = 'player-empty';
        note.textContent = 'Spielerdaten konnten nicht geladen werden. Bitte später erneut versuchen.';
        grid.appendChild(note);
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

/* ── Player Leaderboard ─────────────────────── */
function initLeaderboard() {
    const list = document.getElementById('leaderboardList');
    const summary = document.getElementById('leaderboardSummary');
    const dot = document.getElementById('leaderboardStatusDot');
    const updated = document.getElementById('leaderboardUpdated');
    if (!list || !summary) return;

    const cfg = window.MC_CONFIG || {};
    const API = cfg.leaderboardAPI || '/api/leaderboard.json';
    const REFRESH_MS = 300000;   // 5 min – matches the leaderboard-export timer cadence
    const STALE_AFTER_S = 1800;  // 30 min – flag clearly outdated data
    const MEDALS = { 1: '🥇', 2: '🥈', 3: '🥉' };

    function clear(el) {
        while (el.firstChild) el.removeChild(el.firstChild);
    }

    function setState(state) {
        if (dot) dot.className = 'status-indicator' + (state ? ' ' + state : '');
    }

    function normalizePlayers(value) {
        return Array.isArray(value) ? value.filter((entry) => entry && typeof entry === 'object') : [];
    }

    function groupLabel(player) {
        const display = typeof player.groupDisplay === 'string' ? player.groupDisplay.trim() : '';
        if (display) return display;
        const group = typeof player.group === 'string' ? player.group.trim() : '';
        return group;
    }

    function buildRow(player, position) {
        const rankNumber = Number(player.rank);
        const rank = Number.isFinite(rankNumber) && rankNumber > 0 ? Math.floor(rankNumber) : position;
        const label = groupLabel(player);

        const item = document.createElement('li');
        item.className = 'leaderboard-row';
        if (rank <= 3) item.classList.add('leaderboard-row-top', 'leaderboard-row-' + rank);

        const rankEl = document.createElement('span');
        rankEl.className = 'leaderboard-rank';
        rankEl.textContent = MEDALS[rank] || ('#' + rank);
        item.appendChild(rankEl);

        const nameEl = document.createElement('span');
        nameEl.className = 'leaderboard-name';
        // textContent keeps user-controlled player names inert (no HTML injection).
        nameEl.textContent = String(player.name || 'Unbekannt');
        item.appendChild(nameEl);

        if (label) {
            const groupEl = document.createElement('span');
            groupEl.className = 'leaderboard-rank-badge';
            // textContent keeps user-controlled rank names inert (no HTML injection).
            groupEl.textContent = label;
            item.appendChild(groupEl);
        }

        const timeEl = document.createElement('span');
        timeEl.className = 'leaderboard-playtime';
        timeEl.textContent = formatDuration(player.playtimeSeconds);
        item.appendChild(timeEl);

        return item;
    }

    function render(data) {
        const snapshot = data && typeof data === 'object' ? data : {};
        const players = normalizePlayers(snapshot.players);

        clear(list);

        if (!players.length) {
            setState('offline');
            summary.textContent = 'Noch keine Spielzeiten erfasst';
            const note = document.createElement('li');
            note.className = 'leaderboard-empty';
            note.textContent = 'Sobald Spielzeiten vorliegen, erscheinen hier die aktivsten Spieler.';
            list.appendChild(note);
            if (updated) updated.hidden = true;
            return;
        }

        setState('online');
        summary.textContent = players.length === 1
            ? 'Top 1 nach Spielzeit'
            : 'Top ' + players.length + ' nach Spielzeit';

        players.forEach((player, index) => list.appendChild(buildRow(player, index + 1)));

        if (updated) {
            const ts = toPositiveNumberOrZero(snapshot.updated);
            const ageS = ts > 0 ? Math.floor(Date.now() / 1000) - ts : 0;
            if (ts > 0 && ageS > STALE_AFTER_S) {
                updated.hidden = false;
                updated.textContent = 'Daten möglicherweise veraltet';
                setState('');
            } else {
                updated.hidden = true;
            }
        }
    }

    function renderUnavailable() {
        clear(list);
        setState('offline');
        summary.textContent = 'Bestenliste momentan nicht verfügbar';
        const note = document.createElement('li');
        note.className = 'leaderboard-empty';
        note.textContent = 'Bestenliste konnte nicht geladen werden. Bitte später erneut versuchen.';
        list.appendChild(note);
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

/* ── Wiki Sidebar (Mobile) ──────────────────── */
function initWikiSidebar() {
    const toggle = document.getElementById('sidebarToggle');
    const sidebar = document.querySelector('.wiki-sidebar');
    if (!toggle || !sidebar) return;

    // Backdrop for closing the sidebar when tapping outside
    let backdrop = document.querySelector('.wiki-sidebar-backdrop');
    if (!backdrop) {
        backdrop = document.createElement('div');
        backdrop.className = 'wiki-sidebar-backdrop';
        document.body.appendChild(backdrop);
    }

    function openSidebar() {
        sidebar.classList.add('open');
        toggle.classList.add('active');
        backdrop.classList.add('active');
        toggle.setAttribute('aria-expanded', 'true');
        toggle.setAttribute('aria-label', 'Menü schließen');
        document.body.style.overflow = 'hidden';
    }

    function closeSidebar() {
        sidebar.classList.remove('open');
        toggle.classList.remove('active');
        backdrop.classList.remove('active');
        toggle.setAttribute('aria-expanded', 'false');
        toggle.setAttribute('aria-label', 'Menü öffnen');
        document.body.style.overflow = '';
    }

    toggle.setAttribute('aria-expanded', 'false');
    toggle.addEventListener('click', () => {
        if (sidebar.classList.contains('open')) {
            closeSidebar();
        } else {
            openSidebar();
        }
    });

    backdrop.addEventListener('click', closeSidebar);

    // Close after navigating via a sidebar link
    sidebar.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', closeSidebar);
    });

    // Close on Escape
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && sidebar.classList.contains('open')) {
            closeSidebar();
        }
    });

    // Reset state when leaving mobile breakpoint
    window.addEventListener('resize', () => {
        if (window.innerWidth > 900 && sidebar.classList.contains('open')) {
            closeSidebar();
        }
    }, { passive: true });
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

/* ── Command Reference (data-driven) ────────── */
function initCommandReference() {
    const results = document.getElementById('cmdResults');
    const searchInput = document.getElementById('cmdSearch');
    const serverFilters = document.getElementById('cmdServerFilters');
    const countEl = document.getElementById('cmdCount');
    const groupSeg = document.querySelector('.cmd-seg');
    if (!results) return;

    // Resolve commands.json relative to the current page (befehle/index.html).
    const DATA_URL = 'commands.json';

    const state = { groupBy: 'category', server: 'all', query: '' };
    let dataset = null;

    function clear(el) {
        while (el.firstChild) el.removeChild(el.firstChild);
    }

    function asArray(value) {
        return Array.isArray(value) ? value : [];
    }

    // Base command without argument placeholders, e.g. "/cmi home [Name]" -> "/cmi home".
    function baseCommand(command) {
        const text = String(command || '');
        const cut = text.indexOf('[');
        return (cut >= 0 ? text.slice(0, cut) : text).trim();
    }

    function labelFor(list, id) {
        const entry = asArray(list).find((item) => item && item.id === id);
        return entry && typeof entry.label === 'string' ? entry.label : String(id || '');
    }

    function iconFor(list, id) {
        const entry = asArray(list).find((item) => item && item.id === id);
        return entry && typeof entry.icon === 'string' ? entry.icon : '';
    }

    function matchesServer(command) {
        if (state.server === 'all') return true;
        const servers = asArray(command.servers);
        // Network-wide commands are available on every gameplay server.
        return servers.indexOf(state.server) !== -1 || servers.indexOf('netzwerk') !== -1;
    }

    function matchesQuery(command) {
        const q = state.query;
        if (!q) return true;
        const haystack = [
            command.command,
            command.description,
            command.rank,
            labelFor(dataset.plugins, command.plugin),
            labelFor(dataset.categories, command.category)
        ].map((v) => String(v || '').toLowerCase()).join(' ');
        return haystack.indexOf(q) !== -1;
    }

    function filtered() {
        return asArray(dataset.commands)
            .filter((c) => c && typeof c === 'object')
            .filter(matchesServer)
            .filter(matchesQuery);
    }

    function buildCommandItem(command) {
        const item = document.createElement('div');
        item.className = 'command-item';

        const main = document.createElement('div');
        main.className = 'command-main';

        const nameRow = document.createElement('div');
        nameRow.className = 'command-name-row';

        const nameEl = document.createElement('span');
        nameEl.className = 'command-name';
        // textContent keeps command strings inert (no HTML injection).
        nameEl.textContent = String(command.command || '');
        nameRow.appendChild(nameEl);

        const rank = typeof command.rank === 'string' ? command.rank.trim() : '';
        if (rank) {
            const rankEl = document.createElement('span');
            rankEl.className = 'command-rank-badge';
            rankEl.textContent = rank;
            nameRow.appendChild(rankEl);
        }

        main.appendChild(nameRow);

        const descEl = document.createElement('p');
        descEl.className = 'command-description';
        descEl.textContent = String(command.description || '');
        main.appendChild(descEl);

        // Secondary meta line: plugin + servers, for cross-reference.
        const metaEl = document.createElement('div');
        metaEl.className = 'command-meta';

        const pluginTag = document.createElement('span');
        pluginTag.className = 'command-tag command-tag--plugin';
        pluginTag.textContent = labelFor(dataset.plugins, command.plugin);
        metaEl.appendChild(pluginTag);

        asArray(command.servers).forEach((sid) => {
            const tag = document.createElement('span');
            tag.className = 'command-tag command-tag--server';
            tag.textContent = labelFor(dataset.servers, sid);
            metaEl.appendChild(tag);
        });

        main.appendChild(metaEl);
        item.appendChild(main);

        const copyBtn = document.createElement('button');
        copyBtn.type = 'button';
        copyBtn.className = 'command-copy';
        copyBtn.setAttribute('aria-label', 'Befehl kopieren');
        copyBtn.title = 'Befehl kopieren';
        copyBtn.textContent = '⧉';
        copyBtn.addEventListener('click', async () => {
            const text = baseCommand(command.command);
            try {
                await navigator.clipboard.writeText(text);
            } catch {
                _fallbackCopy(text);
            }
            const original = copyBtn.textContent;
            copyBtn.textContent = '✓';
            copyBtn.classList.add('is-copied');
            setTimeout(() => {
                copyBtn.textContent = original;
                copyBtn.classList.remove('is-copied');
            }, 1500);
        });
        item.appendChild(copyBtn);

        return item;
    }

    function groupsFor(commands) {
        const key = state.groupBy;
        const order = key === 'plugin' ? asArray(dataset.plugins) : asArray(dataset.categories);
        const buckets = new Map();
        commands.forEach((command) => {
            const id = String(command[key] || 'sonstiges');
            if (!buckets.has(id)) buckets.set(id, []);
            buckets.get(id).push(command);
        });

        // Preserve the declared order of categories/plugins; append any extras.
        const orderedIds = order.map((entry) => entry && entry.id).filter(Boolean);
        const seen = new Set(orderedIds);
        buckets.forEach((_, id) => { if (!seen.has(id)) orderedIds.push(id); });

        return orderedIds
            .filter((id) => buckets.has(id))
            .map((id) => ({ id, commands: buckets.get(id) }));
    }

    function render() {
        if (!dataset) return;
        const commands = filtered();
        clear(results);

        if (countEl) {
            const total = asArray(dataset.commands).length;
            countEl.textContent = commands.length === total
                ? commands.length + ' Befehle'
                : commands.length + ' von ' + total + ' Befehlen';
        }

        if (!commands.length) {
            const empty = document.createElement('p');
            empty.className = 'cmd-empty';
            empty.textContent = 'Keine Befehle gefunden. Passe Suche oder Filter an.';
            results.appendChild(empty);
            return;
        }

        const isPlugin = state.groupBy === 'plugin';
        groupsFor(commands).forEach((group) => {
            const section = document.createElement('section');
            section.className = 'command-group';

            const heading = document.createElement('h2');
            heading.className = 'command-group-title';
            const icon = isPlugin ? '' : iconFor(dataset.categories, group.id);
            if (icon) {
                const iconEl = document.createElement('span');
                iconEl.className = 'command-group-icon';
                iconEl.textContent = icon;
                heading.appendChild(iconEl);
            }
            const titleEl = document.createElement('span');
            titleEl.textContent = isPlugin
                ? labelFor(dataset.plugins, group.id)
                : labelFor(dataset.categories, group.id);
            heading.appendChild(titleEl);

            const countBadge = document.createElement('span');
            countBadge.className = 'command-group-count';
            countBadge.textContent = String(group.commands.length);
            heading.appendChild(countBadge);

            section.appendChild(heading);

            const listEl = document.createElement('div');
            listEl.className = 'command-list';
            group.commands
                .slice()
                .sort((a, b) => String(a.command).localeCompare(String(b.command), 'de'))
                .forEach((command) => listEl.appendChild(buildCommandItem(command)));
            section.appendChild(listEl);

            results.appendChild(section);
        });
    }

    function buildServerFilters() {
        if (!serverFilters) return;
        clear(serverFilters);
        const options = [{ id: 'all', label: 'Alle' }].concat(asArray(dataset.servers));
        options.forEach((opt) => {
            const btn = document.createElement('button');
            btn.type = 'button';
            btn.className = 'cmd-filter-chip' + (opt.id === state.server ? ' active' : '');
            btn.textContent = opt.label;
            btn.setAttribute('aria-pressed', opt.id === state.server ? 'true' : 'false');
            btn.addEventListener('click', () => {
                state.server = opt.id;
                serverFilters.querySelectorAll('.cmd-filter-chip').forEach((chip) => {
                    const active = chip === btn;
                    chip.classList.toggle('active', active);
                    chip.setAttribute('aria-pressed', active ? 'true' : 'false');
                });
                render();
            });
            serverFilters.appendChild(btn);
        });
    }

    if (groupSeg) {
        groupSeg.addEventListener('click', (e) => {
            const btn = e.target.closest('.cmd-seg-btn');
            if (!btn) return;
            const group = btn.getAttribute('data-group');
            if (!group || group === state.groupBy) return;
            state.groupBy = group;
            groupSeg.querySelectorAll('.cmd-seg-btn').forEach((b) => {
                b.classList.toggle('active', b === btn);
            });
            render();
        });
    }

    if (searchInput) {
        searchInput.addEventListener('input', () => {
            state.query = searchInput.value.trim().toLowerCase();
            render();
        });
    }

    function renderUnavailable() {
        clear(results);
        const note = document.createElement('p');
        note.className = 'cmd-empty';
        note.textContent = 'Befehlsliste konnte nicht geladen werden. Bitte später erneut versuchen.';
        results.appendChild(note);
    }

    fetch(DATA_URL, { cache: 'no-store' })
        .then((res) => {
            if (!res.ok) throw new Error('HTTP ' + res.status);
            return res.json();
        })
        .then((data) => {
            dataset = data && typeof data === 'object' ? data : {};
            dataset.categories = asArray(dataset.categories);
            dataset.plugins = asArray(dataset.plugins);
            dataset.servers = asArray(dataset.servers);
            dataset.commands = asArray(dataset.commands);
            buildServerFilters();
            render();
        })
        .catch(renderUnavailable);
}
