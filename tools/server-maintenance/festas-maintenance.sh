#!/usr/bin/env bash
# =============================================================================
# festas-maintenance.sh
#
# Umfassender Analyse-, Health-Check- und Wartungs-Agent für den Linux-Host,
# der das Festas-Minecraft-Netzwerk (Docker / Pterodactyl) betreibt.
#
# Grundsätze:
#   * Defensiv: einzelne Prüfungen dürfen fehlschlagen, ohne den ganzen Lauf
#     abzubrechen.
#   * Sicher: Der Modus `analyze` verändert NICHTS. Verändernde Aktionen
#     (Updates, Cleanup) laufen nur in `maintain`/`full` und respektieren
#     `--dry-run`. Welten, Spielerdaten, Datenbanken, Backups und Docker-Volumes
#     werden ausschließlich analysiert, niemals gelöscht.
#
# Aufruf (siehe --help):
#   festas-maintenance.sh run [--mode analyze|maintain|full] [--apt all|security|none]
#                             [--dry-run] [--output-dir DIR]
#   festas-maintenance.sh reboot [--delay MINUTES] [--dry-run]
#
# Ausgabe:
#   $OUTPUT_DIR/report.md   – menschenlesbarer Kurzbericht (wird ins Repo committed)
#   $OUTPUT_DIR/report.json – maschinenlesbare Kennzahlen (Trend / Automatik)
#
# Doku: tools/server-maintenance/README.md
# =============================================================================

set -uo pipefail

# ----------------------------------------------------------------------------
# Konfiguration (per Umgebungsvariable überschreibbar)
# ----------------------------------------------------------------------------
OUTPUT_DIR="${OUTPUT_DIR:-/tmp/festas-health}"
STATE_DIR="${STATE_DIR:-/var/lib/festas-maintenance}"
MAX_SCAN_SECONDS="${MAX_SCAN_SECONDS:-180}"
TOP_N="${TOP_N:-20}"
CHECK_DOMAINS="${CHECK_DOMAINS:-mc.festas-builds.com}"
PTERO_VOLUMES="${PTERO_VOLUMES:-/var/lib/pterodactyl/volumes}"
JOURNAL_VACUUM_TIME="${JOURNAL_VACUUM_TIME:-14d}"
JOURNAL_VACUUM_SIZE="${JOURNAL_VACUUM_SIZE:-500M}"
DISK_WARN_PCT="${DISK_WARN_PCT:-80}"
DISK_CRIT_PCT="${DISK_CRIT_PCT:-90}"
MEM_WARN_PCT="${MEM_WARN_PCT:-85}"
MEM_CRIT_PCT="${MEM_CRIT_PCT:-95}"

# Optionale Minecraft-Server-Pfade (aus GitHub-Secrets durchgereicht). Jeder
# Eintrag zeigt auf den plugins-Ordner; der Server-Root ist eine Ebene höher.
SERVER_PATH_LOBBY="${SERVER_PATH_LOBBY:-}"
SERVER_PATH_PROXY="${SERVER_PATH_PROXY:-}"
SERVER_PATH_SURVIVAL="${SERVER_PATH_SURVIVAL:-}"
SERVER_PATH_SKYBLOCK="${SERVER_PATH_SKYBLOCK:-}"
SERVER_PATH_RPG="${SERVER_PATH_RPG:-}"

# ----------------------------------------------------------------------------
# Laufzeit-Status
# ----------------------------------------------------------------------------
MODE="analyze"
APT_MODE="all"
DRY_RUN=0
REPORT_MD=""
REPORT_JSON=""
METRICS=""
ACTIONS_FILE=""
HEALTH_ISSUES=""       # je Zeile: "LEVEL|Text"  (LEVEL = CRIT|WARN|INFO)
RECOMMENDATIONS=""     # je Zeile: "- Text"
NOW_EPOCH="$(date +%s)"
NOW_HUMAN="$(date -u '+%Y-%m-%d %H:%M:%S UTC')"
HOSTNAME_S="$(hostname 2>/dev/null || echo unknown)"

# ----------------------------------------------------------------------------
# Basis-Helfer
# ----------------------------------------------------------------------------
log()  { printf '[festas-maintenance] %s\n' "$*" >&2; }
have() { command -v "$1" >/dev/null 2>&1; }

# priv: führt ein Kommando mit root-Rechten aus, sofern nötig und möglich.
priv() {
  if [ "$(id -u)" -eq 0 ]; then
    "$@"
  elif have sudo && sudo -n true 2>/dev/null; then
    sudo -n "$@"
  else
    return 127
  fi
}
can_priv() { [ "$(id -u)" -eq 0 ] || { have sudo && sudo -n true 2>/dev/null; }; }

# bounded: begrenzt langlaufende Scans (falls timeout verfügbar).
bounded() {
  if have timeout; then timeout "${MAX_SCAN_SECONDS}s" "$@"; else "$@"; fi
}

# human: formatiert eine Byte-Zahl menschenlesbar.
human() {
  local bytes="${1:-0}"
  case "$bytes" in ''|*[!0-9]*) echo "n/a"; return;; esac
  if have numfmt; then
    numfmt --to=iec --suffix=B "$bytes" 2>/dev/null || echo "${bytes}B"
  else
    awk -v b="$bytes" 'BEGIN{split("B KB MB GB TB PB",u," ");i=1;while(b>=1024&&i<6){b/=1024;i++}printf("%.1f%s",b,u[i])}'
  fi
}

# humanize_col1: liest Zeilen "bytes ...rest" und formatiert Spalte 1 iec-lesbar.
humanize_col1() {
  awk '{ s=$1; split("B KB MB GB TB PB",a," "); i=1; while(s>=1024&&i<6){s/=1024;i++}
         printf("%9.1f%s  ", s, a[i]); $1=""; sub(/^ /,""); print }'
}

metric()    { printf '%s\t%s\n' "$1" "$2" >> "$METRICS"; }
getmetric() { awk -F'\t' -v k="$1" '$1==k{v=$2} END{print v}' "$METRICS"; }
issue()     { HEALTH_ISSUES+="${1}|${2}"$'\n'; }
recommend() { RECOMMENDATIONS+="- ${1}"$'\n'; }
action()    { printf -- '- %s\n' "$1" >> "$ACTIONS_FILE"; log "AKTION: $1"; }

md()   { printf '%s\n' "$*" >> "$REPORT_MD"; }
blank(){ printf '\n' >> "$REPORT_MD"; }
heading() { blank; md "## $*"; blank; }
sub()     { blank; md "### $*"; blank; }

# codeblock: führt ein Kommando/eine Funktion aus und schreibt die Ausgabe als
# Codeblock in den Bericht (max. 200 Zeilen). Funktionen sind im Subshell-Pipe
# sichtbar, daher können Helfer direkt übergeben werden.
codeblock() {
  local lang="$1"; shift
  {
    printf '```%s\n' "$lang"
    "$@" 2>&1 | sed 's/\r$//' | head -n 200
    printf '```\n'
  } >> "$REPORT_MD"
}

usage() {
  cat <<'EOF'
festas-maintenance.sh – Analyse, Health-Checks und Wartung des Minecraft-Hosts.

BEFEHLE
  run       Analyse (+ optional Wartung) ausführen und Bericht erzeugen.
  reboot    Host neu starten (setzt voraus, dass MC-Server bereits sauber
            gestoppt wurden – siehe ptero_control.py im Workflow).

OPTIONEN für "run"
  --mode <analyze|maintain|full>  Analyse | Analyse+Wartung | wie maintain
                                  (Reboot orchestriert der Workflow). Default: analyze
  --apt  <all|security|none>      Umfang der Paket-Updates. Default: all
  --dry-run                       Nichts verändern, nur berichten was passieren würde.
  --output-dir <DIR>              Zielordner für report.md/report.json.

OPTIONEN für "reboot"
  --delay <MINUTES>               Verzögerung vor dem Reboot. Default: 1
  --dry-run                       Reboot nur simulieren.

Wichtige Umgebungsvariablen: OUTPUT_DIR, STATE_DIR, CHECK_DOMAINS, PTERO_VOLUMES,
SERVER_PATH_LOBBY/PROXY/SURVIVAL/SKYBLOCK/RPG. Siehe README.md.
EOF
}

# =============================================================================
# ANALYSE-HELFER (für codeblock)
# =============================================================================
h_du_root()    { bounded priv du -x -B1 --max-depth=1 / 2>/dev/null | sort -rn | head -n 25 | humanize_col1; }
h_du_top()     { bounded priv du -x -B1 /var /home /opt /srv /root 2>/dev/null | sort -rn | head -n "$TOP_N" | humanize_col1; }
h_big_files()  { bounded priv find /var /home /opt /srv /root -xdev -type f -printf '%s\t%p\n' 2>/dev/null | sort -rn | head -n "$TOP_N" | humanize_col1; }
h_ptero_vol()  { bounded priv du -sB1 "$PTERO_VOLUMES"/*/ 2>/dev/null | sort -rn | head -n 15 | humanize_col1; }
h_top_rss()    { ps -eo pid,ppid,user,rss,pmem,pcpu,comm --sort=-rss 2>/dev/null | head -n 16; }
h_top_cpu()    { ps -eo pid,user,pcpu,pmem,comm --sort=-pcpu 2>/dev/null | head -n 11; }
h_listen()     { priv ss -tulnH 2>/dev/null | awk '{print $1, $5}' | sort -u | head -n 40; }
h_iface_err()  { ip -s link 2>/dev/null; }
h_last_login() { last -n 5 -w 2>/dev/null | head -n 5; }
h_sensors()    { sensors 2>/dev/null | grep -iE 'Core|temp|Composite' | head -n 12; }
h_apt_sim()    { apt-get -s upgrade 2>/dev/null | grep '^Inst' | head -n 30; }
h_journal_top(){
  priv journalctl -p err --since '7 days ago' --no-pager 2>/dev/null \
    | sed -E 's/^[A-Za-z]{3} [0-9 :]+ [^ ]+ //; s/[0-9]+/#/g' \
    | sort | uniq -c | sort -rn | head -n 10
}

# =============================================================================
# ANALYSE-ABSCHNITTE
# =============================================================================
section_system() {
  heading "🖥️ System-Übersicht"
  local os kernel uptime virt load cores load1
  os="$( (. /etc/os-release 2>/dev/null && echo "$PRETTY_NAME") || echo unknown)"
  kernel="$(uname -sr 2>/dev/null || echo unknown)"
  uptime="$(uptime -p 2>/dev/null || echo unknown)"
  virt="$( (have systemd-detect-virt && systemd-detect-virt) 2>/dev/null || echo unknown)"
  load="$(awk '{print $1", "$2", "$3}' /proc/loadavg 2>/dev/null || echo unknown)"
  cores="$(nproc 2>/dev/null || echo 1)"
  load1="$(awk '{print $1}' /proc/loadavg 2>/dev/null || echo 0)"
  md "| Feld | Wert |"
  md "|---|---|"
  md "| Host | \`${HOSTNAME_S}\` |"
  md "| OS | ${os} |"
  md "| Kernel | ${kernel} |"
  md "| Virtualisierung | ${virt} |"
  md "| CPU-Kerne | ${cores} |"
  md "| Load (1/5/15) | ${load} |"
  md "| Uptime | ${uptime} |"
  metric os "$os"; metric kernel "$kernel"; metric cpu_cores "$cores"; metric load1 "$load1"
  awk -v l="$load1" -v c="$cores" 'BEGIN{exit !(l > c*1.5)}' \
    && issue WARN "Hohe CPU-Last: Load1 ${load1} bei ${cores} Kernen."
}

section_disk() {
  heading "💾 Speicherplatz"

  sub "Dateisysteme (df)"
  codeblock "" df -hT -x tmpfs -x devtmpfs -x squashfs

  local root_line used_pct avail_h size_h used_h inode_pct
  root_line="$(df -PB1 / 2>/dev/null | awk 'NR==2')"
  size_h="$(human "$(echo "$root_line" | awk '{print $2}')")"
  used_h="$(human "$(echo "$root_line" | awk '{print $3}')")"
  avail_h="$(human "$(echo "$root_line" | awk '{print $4}')")"
  used_pct="$(df -P / 2>/dev/null | awk 'NR==2{gsub("%","",$5);print $5}')"; used_pct="${used_pct:-0}"
  metric disk_root_used_pct "$used_pct"
  metric disk_root_used_bytes "$(echo "$root_line" | awk '{print $3}')"
  metric disk_root_total_bytes "$(echo "$root_line" | awk '{print $2}')"
  md "**Root (\`/\`):** ${used_h} / ${size_h} belegt (${used_pct} %), frei: ${avail_h}."

  if [ "$used_pct" -ge "$DISK_CRIT_PCT" ]; then
    issue CRIT "Festplatte zu ${used_pct} % voll (Schwelle ${DISK_CRIT_PCT} %)."
    recommend "**Dringend** Speicher freigeben – siehe Aufräum-Kandidaten."
  elif [ "$used_pct" -ge "$DISK_WARN_PCT" ]; then
    issue WARN "Festplatte zu ${used_pct} % voll (Schwelle ${DISK_WARN_PCT} %)."
    recommend "Speicher freigeben (Docker-Prune, Journald, alte Backups/Logs)."
  fi

  inode_pct="$(df -Pi / 2>/dev/null | awk 'NR==2{gsub("%","",$5);print $5}')"; inode_pct="${inode_pct:-0}"
  metric inode_root_used_pct "$inode_pct"
  md "**Inodes (\`/\`):** ${inode_pct} % belegt."
  [ "$inode_pct" -ge 85 ] && issue WARN "Inode-Auslastung hoch (${inode_pct} %) – viele kleine Dateien?"

  sub "Größte Verzeichnisse unter / (eine Ebene)"
  codeblock "" h_du_root
  sub "Größte Verzeichnisse (Top ${TOP_N})"
  codeblock "" h_du_top
  sub "Größte Einzeldateien (Top ${TOP_N})"
  codeblock "" h_big_files

  section_disk_wellknown
}

section_disk_wellknown() {
  sub "Bekannte Speicherfresser"
  md "| Bereich | Pfad | Größe |"
  md "|---|---|---|"
  local pairs=(
    "Docker gesamt:/var/lib/docker"
    "Pterodactyl-Volumes:${PTERO_VOLUMES}"
    "System-Logs:/var/log"
    "Journald:/var/log/journal"
    "APT-Cache:/var/cache/apt"
    "Snap:/var/lib/snapd"
    "Tmp:/tmp"
    "Home:/home"
  )
  local entry name path size
  for entry in "${pairs[@]}"; do
    name="${entry%%:*}"; path="${entry#*:}"
    if [ -e "$path" ]; then
      size="$(bounded priv du -sB1 "$path" 2>/dev/null | awk '{print $1}')"
      md "| ${name} | \`${path}\` | $(human "${size:-0}") |"
    else
      md "| ${name} | \`${path}\` | _nicht vorhanden_ |"
    fi
  done

  if have journalctl; then
    local jsize; jsize="$(priv journalctl --disk-usage 2>/dev/null | grep -oE '[0-9.]+[KMGT]?B' | tail -1)"
    [ -n "${jsize:-}" ] && blank && md "Journald meldet aktuell **${jsize}** an Logs."
  fi

  if have dpkg; then
    local cur kernels old
    cur="$(uname -r)"
    kernels="$(dpkg -l 'linux-image-*' 2>/dev/null | awk '/^ii/{print $2}' | grep -E 'linux-image-[0-9]' || true)"
    old="$(printf '%s\n' "$kernels" | grep -v "$cur" | grep -c . || true)"
    if [ "${old:-0}" -gt 0 ]; then
      blank; md "**Alte Kernel installiert:** ${old} (aktiv: \`${cur}\`) → \`apt-get autoremove\` gibt Platz frei."
      recommend "Alte Kernel/Pakete entfernen (\`apt-get -y autoremove --purge\`)."
    fi
  fi
}

section_docker() {
  heading "🐳 Docker & Container"
  if ! have docker; then md "_Docker nicht gefunden – übersprungen._"; return; fi
  if ! priv docker info >/dev/null 2>&1; then
    md "_Docker nicht erreichbar (keine Rechte / Daemon down) – übersprungen._"
    issue WARN "Docker-Daemon nicht abfragbar."
    return
  fi

  sub "Speicherverbrauch (docker system df)"
  codeblock "" priv docker system df
  local reclaim; reclaim="$(priv docker system df --format '{{.Reclaimable}}' 2>/dev/null | head -1)"
  [ -n "${reclaim:-}" ] && blank && md "Wiedergewinnbar laut Docker: **${reclaim}**."

  sub "Container-Status"
  codeblock "" priv docker ps -a --format 'table {{.Names}}\t{{.Status}}\t{{.Size}}'

  local total running unhealthy flapping
  total="$(priv docker ps -aq 2>/dev/null | grep -c . || true)"
  running="$(priv docker ps -q 2>/dev/null | grep -c . || true)"
  unhealthy="$(priv docker ps --filter health=unhealthy -q 2>/dev/null | grep -c . || true)"
  metric docker_containers_total "$total"
  metric docker_containers_running "$running"
  metric docker_unhealthy "$unhealthy"
  blank; md "Container: **${running}/${total}** laufend, **${unhealthy}** ungesund."
  [ "$unhealthy" -gt 0 ] && issue WARN "${unhealthy} Container im Status 'unhealthy'."

  flapping="$(priv docker ps -a --format '{{.Names}} {{.Status}}' 2>/dev/null | grep -iE 'Restarting|Exited \([1-9]' | head -n 10 || true)"
  if [ -n "${flapping:-}" ]; then
    blank; md "**Auffällige Container (Restarting/Exited≠0):**"
    codeblock "" printf '%s\n' "$flapping"
    issue WARN "Container in Restart-/Fehler-Zustand – Logs prüfen."
  fi
}

section_pterodactyl() {
  heading "🪶 Pterodactyl / Wings"
  local wings="inaktiv"
  if have systemctl && priv systemctl is-active wings >/dev/null 2>&1; then wings="aktiv"; fi
  metric wings_active "$([ "$wings" = aktiv ] && echo 1 || echo 0)"
  md "Wings-Dienst: **${wings}**."
  [ "$wings" != aktiv ] && have systemctl && issue WARN "Wings-Dienst nicht aktiv – Pterodactyl-Server evtl. offline."

  if [ -d "$PTERO_VOLUMES" ]; then
    sub "Server-Volumes (größte 15)"
    codeblock "" h_ptero_vol
    local pv; pv="$(bounded priv du -sB1 "$PTERO_VOLUMES" 2>/dev/null | awk '{print $1}')"
    metric ptero_volumes_bytes "${pv:-0}"
  else
    blank; md "_Volumes-Verzeichnis \`${PTERO_VOLUMES}\` nicht gefunden._"
  fi
}

section_minecraft() {
  heading "⛏️ Minecraft-Server (Welten & Logs)"
  local any=0
  local defs=(
    "Lobby:${SERVER_PATH_LOBBY}"
    "Proxy:${SERVER_PATH_PROXY}"
    "Survival:${SERVER_PATH_SURVIVAL}"
    "Skyblock:${SERVER_PATH_SKYBLOCK}"
    "Mining(rpg):${SERVER_PATH_RPG}"
  )
  md "| Server | Root | Welten | Logs | Plugins |"
  md "|---|---|---|---|---|"
  local d name plugins root world_sz log_sz plug_sz
  for d in "${defs[@]}"; do
    name="${d%%:*}"; plugins="${d#*:}"
    [ -z "$plugins" ] && continue
    root="$(dirname "$plugins")"
    if [ ! -d "$root" ]; then md "| ${name} | \`${root}\` | _nicht gefunden_ | | |"; continue; fi
    any=1
    world_sz="$(bounded priv du -scB1 "$root"/world* 2>/dev/null | awk 'END{print $1}')"
    log_sz="$(priv du -sB1 "$root/logs" 2>/dev/null | awk '{print $1}')"
    plug_sz="$(bounded priv du -sB1 "$plugins" 2>/dev/null | awk '{print $1}')"
    md "| ${name} | \`${root}\` | $(human "${world_sz:-0}") | $(human "${log_sz:-0}") | $(human "${plug_sz:-0}") |"
    [ "${world_sz:-0}" -gt $((20*1024*1024*1024)) ] \
      && recommend "${name}: Welt >20 GB – Pre-Gen-Grenzen/World-Border prüfen, alte Chunks trimmen."
    [ "${log_sz:-0}" -gt $((1024*1024*1024)) ] \
      && recommend "${name}: Logs >1 GB – Log-Aufbewahrung reduzieren."
  done
  [ "$any" -eq 0 ] && blank && md "_Keine SERVER_PATH_* Variablen gesetzt – generische Volumes-Analyse siehe oben._"
}

section_memory() {
  heading "🧠 Arbeitsspeicher & Prozesse"
  sub "Speicher (free)"
  codeblock "" free -h

  local mem_total mem_avail mem_used_pct
  mem_total="$(awk '/MemTotal/{print $2}' /proc/meminfo)"
  mem_avail="$(awk '/MemAvailable/{print $2}' /proc/meminfo)"
  if [ -n "${mem_total:-}" ] && [ "${mem_total:-0}" -gt 0 ]; then
    mem_used_pct="$(awk -v t="$mem_total" -v a="$mem_avail" 'BEGIN{printf("%d",(t-a)*100/t)}')"
  else
    mem_used_pct=0
  fi
  metric mem_used_pct "$mem_used_pct"
  blank; md "**RAM-Auslastung:** ${mem_used_pct} % belegt."
  if [ "$mem_used_pct" -ge "$MEM_CRIT_PCT" ]; then
    issue CRIT "RAM-Auslastung ${mem_used_pct} % (Schwelle ${MEM_CRIT_PCT} %)."
  elif [ "$mem_used_pct" -ge "$MEM_WARN_PCT" ]; then
    issue WARN "RAM-Auslastung ${mem_used_pct} % (Schwelle ${MEM_WARN_PCT} %)."
  fi

  local sw_total sw_free sw_pct
  sw_total="$(awk '/SwapTotal/{print $2}' /proc/meminfo)"
  if [ "${sw_total:-0}" -gt 0 ]; then
    sw_free="$(awk '/SwapFree/{print $2}' /proc/meminfo)"
    sw_pct="$(awk -v t="$sw_total" -v f="$sw_free" 'BEGIN{printf("%d",(t-f)*100/t)}')"
    metric swap_used_pct "$sw_pct"
    md "**Swap:** ${sw_pct} % belegt."
    if [ "${sw_pct:-0}" -ge 50 ]; then
      issue WARN "Swap zu ${sw_pct} % belegt – RAM-Druck oder Speicherleck?"
      recommend "Hoher Swap → Top-RAM-Prozesse prüfen; Host-Reboot leert transiente Belegung."
    fi
  else
    md "**Swap:** nicht konfiguriert."
  fi

  sub "Top 15 Prozesse nach RAM (RSS)"
  codeblock "" h_top_rss
  sub "Top 10 Prozesse nach CPU"
  codeblock "" h_top_cpu

  if have journalctl; then
    local oom
    oom="$(priv journalctl -k --since '7 days ago' 2>/dev/null | grep -ciE 'out of memory|oom-kill|killed process' || true)"
    metric oom_events_7d "${oom:-0}"
    blank; md "**OOM-Ereignisse (7 Tage):** ${oom:-0}."
    [ "${oom:-0}" -gt 0 ] && issue CRIT "In 7 Tagen ${oom} OOM-Killer-Ereignisse – RAM/Limits prüfen."
  fi
}

section_services() {
  heading "🩺 Dienste & Health"
  if ! have systemctl; then md "_systemd nicht gefunden – übersprungen._"; return; fi

  sub "Fehlgeschlagene Units"
  local failed
  failed="$(priv systemctl --failed --no-legend 2>/dev/null | awk '{print $1}' | grep -c . || true)"
  metric failed_services "$failed"
  if [ "$failed" -gt 0 ]; then
    codeblock "" priv systemctl --failed --no-legend
    issue WARN "${failed} fehlgeschlagene systemd-Unit(s)."
    recommend "Fehlgeschlagene Dienste untersuchen (\`systemctl --failed\`)."
  else
    md "Keine fehlgeschlagenen Units. ✅"
  fi

  sub "Kern-Dienste"
  md "| Dienst | Status |"
  md "|---|---|"
  local svc st
  for svc in docker wings nginx mariadb mysql redis-server redis fail2ban ssh sshd cron chrony systemd-timesyncd; do
    if priv systemctl cat "${svc}.service" >/dev/null 2>&1; then
      st="$(priv systemctl is-active "${svc}" 2>/dev/null || echo unknown)"
      md "| ${svc} | ${st} |"
    fi
  done

  if have timedatectl; then
    local synced; synced="$(timedatectl show -p NTPSynchronized --value 2>/dev/null || echo unknown)"
    blank; md "**Zeit-Synchronisation (NTP):** ${synced}."
    [ "$synced" = "no" ] && issue WARN "Systemzeit nicht per NTP synchronisiert."
  fi
}

section_network() {
  heading "🌐 Netzwerk"
  sub "Offene Ports (LISTEN)"
  if have ss; then
    codeblock "" h_listen
    local est; est="$(ss -tanH state established 2>/dev/null | grep -c . || true)"
    metric established_conns "$est"
    blank; md "**Etablierte Verbindungen:** ${est}."
  else
    md "_ss nicht verfügbar._"
  fi

  sub "Konnektivität & DNS"
  local ip4="n/a" dnsok="nein"
  if have curl; then ip4="$(curl -s --max-time 8 https://api.ipify.org 2>/dev/null || echo n/a)"; fi
  if have getent && getent hosts github.com >/dev/null 2>&1; then dnsok="ja"; fi
  md "Öffentliche IPv4: \`${ip4:-n/a}\` · DNS-Auflösung: ${dnsok}."
  [ "$dnsok" = "nein" ] && issue WARN "DNS-Auflösung fehlgeschlagen."
}

section_security() {
  heading "🔐 Sicherheit"

  sub "Firewall"
  if have ufw && priv ufw status >/dev/null 2>&1; then
    codeblock "" priv ufw status
  elif have nft; then
    local r; r="$(priv nft list ruleset 2>/dev/null | grep -c . || true)"; md "nftables-Regeln: ${r} Zeilen."
  elif have iptables; then
    local r; r="$(priv iptables -S 2>/dev/null | grep -c . || true)"; md "iptables-Regeln: ${r}."
  else
    md "_Keine bekannte Firewall gefunden._"; issue WARN "Keine Firewall (ufw/nft/iptables) erkennbar."
  fi

  if have fail2ban-client; then
    sub "fail2ban"
    codeblock "" priv fail2ban-client status
  fi

  sub "Fehlgeschlagene Logins (7 Tage)"
  local failed_logins=0
  if have journalctl; then
    failed_logins="$(priv journalctl -u ssh -u sshd --since '7 days ago' 2>/dev/null | grep -c 'Failed password' || true)"
  elif have lastb; then
    failed_logins="$(priv lastb 2>/dev/null | grep -c . || true)"
  fi
  metric failed_logins_7d "${failed_logins:-0}"
  md "Fehlgeschlagene Passwort-Logins: **${failed_logins:-0}**."
  [ "${failed_logins:-0}" -gt 500 ] && issue WARN "Viele fehlgeschlagene Logins (${failed_logins}) – Brute-Force? fail2ban prüfen."

  if have last; then
    sub "Letzte Anmeldungen"
    codeblock "" h_last_login
  fi
}

section_updates() {
  heading "📦 Paket-Updates"
  if ! have apt-get; then md "_apt nicht gefunden – übersprungen._"; return; fi

  local upgradable security reboot_req=0
  priv apt-get update -qq >/dev/null 2>&1 || true
  upgradable="$(apt-get -s upgrade 2>/dev/null | grep -c '^Inst' || true)"
  metric apt_upgradable "$upgradable"

  if [ -x /usr/lib/update-notifier/apt-check ]; then
    security="$(/usr/lib/update-notifier/apt-check 2>&1 | awk -F';' '{print $2}')"
  else
    security="$(apt-get -s upgrade 2>/dev/null | grep '^Inst' | grep -ciE 'security' || true)"
  fi
  metric apt_security "${security:-0}"

  md "Verfügbare Updates: **${upgradable}** (davon sicherheitsrelevant: **${security:-0}**)."
  [ "${upgradable:-0}" -gt 0 ] && recommend "Paket-Updates einspielen (Modus \`maintain\`/\`full\`)."
  [ "${security:-0}" -gt 0 ] && issue WARN "${security} sicherheitsrelevante Updates ausstehend."

  if [ -f /var/run/reboot-required ] || [ -f /run/reboot-required ]; then
    reboot_req=1
    blank; md "⚠️ **Reboot erforderlich** (\`reboot-required\` vorhanden)."
    [ -f /var/run/reboot-required.pkgs ] && codeblock "" cat /var/run/reboot-required.pkgs
    recommend "Host-Reboot einplanen (Kernel/Bibliotheks-Updates aktivieren)."
  fi
  metric reboot_required "$reboot_req"

  if [ "${upgradable:-0}" -gt 0 ]; then
    sub "Aktualisierbare Pakete (Auszug)"
    codeblock "" h_apt_sim
  fi
}

section_logs() {
  heading "📜 Log-Analyse (7 Tage)"
  if have journalctl; then
    local errs warns ioerr
    errs="$(priv journalctl -p err --since '7 days ago' 2>/dev/null | grep -c . || true)"
    warns="$(priv journalctl -p warning --since '7 days ago' 2>/dev/null | grep -c . || true)"
    metric journal_errors_7d "${errs:-0}"
    md "Journald: **${errs}** Fehler, **${warns}** Warnungen (7 Tage)."
    sub "Häufigste Fehlermeldungen"
    codeblock "" h_journal_top
    ioerr="$(priv journalctl -k --since '7 days ago' 2>/dev/null | grep -ciE 'I/O error|ata[0-9]+.*error|EXT4-fs error' || true)"
    blank; md "Kernel-I/O-/Dateisystem-Fehler (7 Tage): **${ioerr:-0}**."
    [ "${ioerr:-0}" -gt 0 ] && issue CRIT "Kernel meldet I/O-/Dateisystem-Fehler – Datenträger (SMART) prüfen!"
  else
    md "_journalctl nicht verfügbar._"
  fi
}

section_smart_temp() {
  heading "🌡️ Datenträger-Gesundheit & Sensoren"
  if have smartctl; then
    sub "SMART-Status"
    local dev h
    for dev in /dev/sd? /dev/nvme?n1; do
      [ -e "$dev" ] || continue
      h="$(priv smartctl -H "$dev" 2>/dev/null | grep -iE 'test result|health' | head -1)"
      md "- \`${dev}\`: ${h:-unbekannt}"
      printf '%s' "$h" | grep -qiE 'FAILED|failing' && issue CRIT "SMART meldet Probleme an ${dev}!"
    done
  else
    md "_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._"
  fi
  if have sensors; then
    sub "Temperaturen"
    codeblock "" h_sensors
  fi
}

section_certs() {
  heading "🔏 TLS-Zertifikate"
  if ! have openssl; then md "_openssl nicht verfügbar – übersprungen._"; return; fi
  local dom enddate end_epoch days
  for dom in $CHECK_DOMAINS; do
    enddate="$(echo | timeout 10 openssl s_client -servername "$dom" -connect "${dom}:443" 2>/dev/null \
      | openssl x509 -noout -enddate 2>/dev/null | cut -d= -f2)"
    if [ -n "${enddate:-}" ]; then
      end_epoch="$(date -d "$enddate" +%s 2>/dev/null || echo 0)"
      days="$(( (end_epoch - NOW_EPOCH) / 86400 ))"
      md "- \`${dom}\`: gültig bis ${enddate} (**${days} Tage**)."
      [ "$days" -lt 14 ] && issue WARN "TLS-Zertifikat für ${dom} läuft in ${days} Tagen ab."
    else
      md "- \`${dom}\`: _nicht erreichbar / kein Zertifikat_."
    fi
  done
}

section_backups() {
  heading "🗄️ Backups (Heuristik)"
  local found=0 dir sz newest
  for dir in /backup /backups /var/backups /opt/backups /home/*/backups /mnt/*/backup*; do
    [ -d "$dir" ] || continue
    found=1
    sz="$(bounded priv du -sh "$dir" 2>/dev/null | awk '{print $1}')"
    newest="$(priv find "$dir" -type f -printf '%T+ %p\n' 2>/dev/null | sort -r | head -1)"
    md "- \`${dir}\` (${sz:-?}); neueste Datei: ${newest:-keine}"
  done
  [ "$found" -eq 0 ] && md "_Kein Standard-Backup-Verzeichnis gefunden – Pfad ggf. in der Konfiguration ergänzen._"
  blank; md "> Aufbewahrung/Off-Site siehe [docs/infrastructure/BACKUPS.md](../../docs/infrastructure/BACKUPS.md)."
}

section_cleanup_candidates() {
  heading "🧹 Aufräum-Kandidaten"
  md "Diese Posten lassen sich typischerweise gefahrlos freigeben. Im Modus"
  md "\`maintain\`/\`full\` erledigt der Agent die mit **(auto)** markierten Punkte."
  blank
  md "| Kandidat | Umfang | Aktion |"
  md "|---|---|---|"

  local apt_cache=0
  [ -d /var/cache/apt/archives ] && apt_cache="$(priv du -sB1 /var/cache/apt/archives 2>/dev/null | awk '{print $1}')"
  md "| APT-Paketcache | $(human "${apt_cache:-0}") | \`apt-get clean\` **(auto)** |"

  if have journalctl; then
    local jusage; jusage="$(priv journalctl --disk-usage 2>/dev/null | grep -oE '[0-9.]+[KMGT]?B' | tail -1)"
    md "| Journald-Logs | aktuell ${jusage:-?} | \`journalctl --vacuum-time=${JOURNAL_VACUUM_TIME}\` **(auto)** |"
  fi
  if have docker && priv docker info >/dev/null 2>&1; then
    local drec; drec="$(priv docker system df --format '{{.Reclaimable}}' 2>/dev/null | head -1)"
    md "| Docker (dangling/build-cache) | ${drec:-?} | \`docker system prune -f\` **(auto)** |"
  fi
  have apt-get && md "| Verwaiste Pakete/Kernel | variabel | \`apt-get autoremove --purge\` **(auto)** |"
  local tmp_sz=""
  [ -d /tmp ] && tmp_sz="$(bounded priv du -sB1 /tmp 2>/dev/null | awk '{print $1}')"
  md "| Temp-Dateien | \`/tmp\` (${tmp_sz:+$(human "$tmp_sz")}) | \`systemd-tmpfiles --clean\` **(auto)** |"
  have coredumpctl && md "| Core-Dumps | \`coredumpctl list\` | manuell prüfen |"

  blank
  md "> **Nie automatisch gelöscht:** Welten, Spielerdaten, Datenbanken, Backups"
  md "> und Docker-**Volumes**. Diese werden nur analysiert."
}

# =============================================================================
# WARTUNG (nur maintain/full, respektiert --dry-run)
# =============================================================================
maintenance_run() {
  heading "🔧 Durchgeführte Wartungsaktionen"

  if [ "$MODE" = "analyze" ]; then md "_Modus \`analyze\`: keine verändernden Aktionen._"; return; fi
  [ "$DRY_RUN" -eq 1 ] && md "_\`--dry-run\`: folgende Aktionen wurden **simuliert** (nichts verändert)._"
  if ! can_priv; then
    md "⚠️ Keine root/sudo-Rechte – Wartung nicht möglich."
    issue WARN "Wartung angefordert, aber keine root/sudo-Rechte."
    return
  fi

  local before after freed
  before="$(df -PB1 / 2>/dev/null | awk 'NR==2{print $3}')"

  # 1) Paket-Updates.
  case "$APT_MODE" in
    none) action "Paket-Updates übersprungen (--apt none)." ;;
    security|all)
      if [ "$DRY_RUN" -eq 1 ]; then action "APT-Updates (${APT_MODE}) würden installiert (Dry-Run)."
      else run_apt_upgrade; fi ;;
  esac

  # 2) APT autoremove/clean.
  if have apt-get; then
    if [ "$DRY_RUN" -eq 1 ]; then
      action "apt-get autoremove --purge + clean würden ausgeführt (Dry-Run)."
    else
      DEBIAN_FRONTEND=noninteractive priv apt-get -y autoremove --purge >/dev/null 2>&1 \
        && action "Verwaiste Pakete/Kernel entfernt (autoremove --purge)." \
        || action "autoremove fehlgeschlagen (siehe Logs)."
      priv apt-get -y autoclean >/dev/null 2>&1 || true
      priv apt-get -y clean >/dev/null 2>&1 && action "APT-Paketcache geleert (clean)."
    fi
  fi

  # 3) Journald eindampfen.
  if have journalctl; then
    if [ "$DRY_RUN" -eq 1 ]; then
      action "journalctl --vacuum-time=${JOURNAL_VACUUM_TIME} würde ausgeführt (Dry-Run)."
    else
      priv journalctl --vacuum-time="$JOURNAL_VACUUM_TIME" >/dev/null 2>&1 || true
      priv journalctl --vacuum-size="$JOURNAL_VACUUM_SIZE" >/dev/null 2>&1 \
        && action "Journald eingedampft (time=${JOURNAL_VACUUM_TIME}, size=${JOURNAL_VACUUM_SIZE})."
    fi
  fi

  # 4) Docker-Prune (OHNE Volumes, OHNE -a → keine genutzten Images/Server-Daten).
  if have docker && priv docker info >/dev/null 2>&1; then
    if [ "$DRY_RUN" -eq 1 ]; then
      action "docker system prune -f (ohne Volumes) würde ausgeführt (Dry-Run)."
    else
      priv docker system prune -f >/dev/null 2>&1 \
        && action "Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache)." \
        || action "docker prune fehlgeschlagen."
    fi
  fi

  # 5) Temp-Dateien nach Policy aufräumen.
  if have systemd-tmpfiles; then
    if [ "$DRY_RUN" -eq 1 ]; then
      action "systemd-tmpfiles --clean würde ausgeführt (Dry-Run)."
    else
      priv systemd-tmpfiles --clean >/dev/null 2>&1 && action "Alte Temp-Dateien nach systemd-Policy bereinigt."
    fi
  fi

  after="$(df -PB1 / 2>/dev/null | awk 'NR==2{print $3}')"
  if [ "$DRY_RUN" -eq 0 ] && [ -n "${before:-}" ] && [ -n "${after:-}" ]; then
    freed=$(( before - after )); [ "$freed" -lt 0 ] && freed=0
    blank; md "**Freigegebener Speicher in diesem Lauf:** $(human "$freed")."
    metric freed_bytes "$freed"
  fi

  # Reboot-Bedarf nach Updates erneut prüfen.
  if [ -f /var/run/reboot-required ] || [ -f /run/reboot-required ]; then
    metric reboot_required 1
    blank; md "➡️ Nach den Updates ist ein **Reboot erforderlich**."
  fi

  if [ -s "$ACTIONS_FILE" ]; then
    blank; md "**Protokoll:**"; cat "$ACTIONS_FILE" >> "$REPORT_MD"
  fi
}

run_apt_upgrade() {
  local aptopts=(-y -o Dpkg::Options::=--force-confdef -o Dpkg::Options::=--force-confold)
  export DEBIAN_FRONTEND=noninteractive
  priv apt-get update -qq >/dev/null 2>&1 || true
  if [ "$APT_MODE" = "security" ]; then
    if have unattended-upgrade; then
      priv unattended-upgrade -v >/dev/null 2>&1 \
        && action "Sicherheitsupdates via unattended-upgrade installiert." \
        || action "unattended-upgrade meldete Fehler (Logs prüfen)."
      return
    fi
    action "Nur-Security angefordert, aber unattended-upgrades fehlt → normales Upgrade als Fallback."
  fi
  if priv apt-get "${aptopts[@]}" upgrade >/dev/null 2>&1; then
    action "Paket-Updates installiert (apt-get upgrade, ${APT_MODE})."
  else
    action "apt-get upgrade meldete Fehler (Logs prüfen)."
    issue WARN "Paket-Update fehlgeschlagen."
  fi
}

# =============================================================================
# STATUS, TREND, ZUSAMMENFASSUNG, JSON
# =============================================================================
compute_status() {
  if printf '%s' "$HEALTH_ISSUES" | grep -q '^CRIT|'; then echo "CRIT"
  elif printf '%s' "$HEALTH_ISSUES" | grep -q '^WARN|'; then echo "WARN"
  else echo "OK"; fi
}

update_history_and_trend() {
  local disk_pct mem_pct disk_bytes hist_file prev_bytes="" trend="" line
  disk_pct="$(getmetric disk_root_used_pct)"
  mem_pct="$(getmetric mem_used_pct)"
  disk_bytes="$(getmetric disk_root_used_bytes)"
  line="{\"ts\":${NOW_EPOCH},\"disk_pct\":${disk_pct:-0},\"disk_bytes\":${disk_bytes:-0},\"mem_pct\":${mem_pct:-0}}"

  if can_priv && priv mkdir -p "$STATE_DIR" 2>/dev/null; then
    hist_file="$STATE_DIR/history.jsonl"
    prev_bytes="$(priv sh -c "tail -1 '$hist_file' 2>/dev/null" | grep -oE '\"disk_bytes\":[0-9]+' | cut -d: -f2)"
    printf '%s\n' "$line" | priv sh -c "cat >> '$hist_file'" 2>/dev/null || true
  else
    hist_file="/tmp/festas-history.jsonl"
    prev_bytes="$(tail -1 "$hist_file" 2>/dev/null | grep -oE '\"disk_bytes\":[0-9]+' | cut -d: -f2)"
    printf '%s\n' "$line" >> "$hist_file" 2>/dev/null || true
  fi

  if [ -n "${prev_bytes:-}" ] && [ -n "${disk_bytes:-}" ]; then
    local delta=$(( disk_bytes - prev_bytes )) sign="+"
    [ "$delta" -lt 0 ] && sign="-"
    trend="Seit letztem Lauf: ${sign}$(human "${delta#-}") auf \`/\`."
  fi
  printf '%s' "$trend"
}

write_summary() {
  local status="$1" trend="$2" badge disk_pct mem_pct upgradable failed summary tmp
  case "$status" in
    OK)   badge="🟢 **OK**";;
    WARN) badge="🟡 **WARNUNG**";;
    CRIT) badge="🔴 **KRITISCH**";;
  esac
  disk_pct="$(getmetric disk_root_used_pct)"
  mem_pct="$(getmetric mem_used_pct)"
  upgradable="$(getmetric apt_upgradable)"
  failed="$(getmetric failed_services)"

  summary="$(mktemp)"
  {
    echo "**Gesamtstatus:** ${badge} · erstellt ${NOW_HUMAN} · Host \`${HOSTNAME_S}\`"
    echo ""
    echo "| Kennzahl | Wert |"
    echo "|---|---|"
    echo "| Festplatte \`/\` | ${disk_pct:-?} % belegt |"
    echo "| RAM | ${mem_pct:-?} % belegt |"
    echo "| Paket-Updates offen | ${upgradable:-?} |"
    echo "| Fehlgeschlagene Dienste | ${failed:-0} |"
    echo "| Modus dieses Laufs | \`${MODE}\`$([ "$DRY_RUN" -eq 1 ] && echo ' (dry-run)') |"
    [ -n "$trend" ] && echo "| Trend | ${trend} |"
    echo ""
    if printf '%s' "$HEALTH_ISSUES" | grep -q '|'; then
      echo "**Wichtigste Befunde:**"
      echo ""
      printf '%s' "$HEALTH_ISSUES" | grep '^CRIT|' | sed 's/^CRIT|//' | while IFS= read -r t; do
        [ -n "$t" ] && echo "- 🔴 $t"; done
      printf '%s' "$HEALTH_ISSUES" | grep '^WARN|' | sed 's/^WARN|//' | while IFS= read -r t; do
        [ -n "$t" ] && echo "- 🟡 $t"; done
      echo ""
    else
      echo "Keine akuten Probleme erkannt. ✅"
      echo ""
    fi
    if printf '%s' "$RECOMMENDATIONS" | grep -q '[^[:space:]]'; then
      echo "**Empfehlungen (Optimierungspotenzial):**"
      echo ""
      printf '%s' "$RECOMMENDATIONS"
      echo ""
    fi
  } > "$summary"

  tmp="$(mktemp)"
  awk -v f="$summary" '
    /<!--SUMMARY-->/ { while ((getline line < f) > 0) print line; next }
    { print }
  ' "$REPORT_MD" > "$tmp" && mv "$tmp" "$REPORT_MD"
  rm -f "$summary"
}

write_json() {
  local status="$1"
  metric status "$status"
  metric mode "$MODE"
  metric dry_run "$DRY_RUN"
  metric generated_at "$NOW_EPOCH"
  metric host "$HOSTNAME_S"
  metric issues_crit "$(printf '%s' "$HEALTH_ISSUES" | grep -c '^CRIT|' || true)"
  metric issues_warn "$(printf '%s' "$HEALTH_ISSUES" | grep -c '^WARN|' || true)"

  if have python3; then
    METRICS_FILE="$METRICS" ISSUES_RAW="$HEALTH_ISSUES" RECS_RAW="$RECOMMENDATIONS" \
    python3 - "$REPORT_JSON" <<'PY'
import json, os, sys
out = sys.argv[1]
data = {}
with open(os.environ["METRICS_FILE"], encoding="utf-8", errors="replace") as fh:
    for raw in fh:
        if "\t" not in raw:
            continue
        k, v = raw.rstrip("\n").split("\t", 1)
        try:
            data[k] = int(v)
        except ValueError:
            try:
                data[k] = float(v)
            except ValueError:
                data[k] = v
issues = []
for line in os.environ.get("ISSUES_RAW", "").splitlines():
    if "|" in line:
        level, _, text = line.partition("|")
        text = text.strip()
        if text:
            issues.append({"level": level.strip(), "text": text})
recs = []
for line in os.environ.get("RECS_RAW", "").splitlines():
    text = line.lstrip("- ").strip()
    if text:
        recs.append(text)
data["issues"] = issues
data["recommendations"] = recs
with open(out, "w", encoding="utf-8") as fh:
    json.dump(data, fh, ensure_ascii=False, indent=2, sort_keys=True)
    fh.write("\n")
PY
  else
    {
      echo "{"
      awk -F'\t' 'NF>=2{gsub(/"/,"\\\"",$2); printf("  \"%s\": \"%s\",\n",$1,$2)}' "$METRICS"
      echo "  \"_note\": \"python3 fehlt – reduzierte JSON-Ausgabe\""
      echo "}"
    } > "$REPORT_JSON"
  fi
}

# =============================================================================
# HAUPTABLAUF
# =============================================================================
do_run() {
  mkdir -p "$OUTPUT_DIR"
  REPORT_MD="$OUTPUT_DIR/report.md"
  REPORT_JSON="$OUTPUT_DIR/report.json"
  METRICS="$(mktemp)"
  ACTIONS_FILE="$(mktemp)"
  : > "$REPORT_MD"

  {
    echo "# 🩺 Festas Server – Wartungs- & Health-Bericht"
    echo ""
    echo "_Automatisch erzeugt von \`tools/server-maintenance/festas-maintenance.sh\`._"
    echo ""
    echo "<!--SUMMARY-->"
  } >> "$REPORT_MD"

  section_system
  section_disk
  section_docker
  section_pterodactyl
  section_minecraft
  section_memory
  section_services
  section_network
  section_security
  section_updates
  section_logs
  section_smart_temp
  section_certs
  section_backups
  section_cleanup_candidates
  maintenance_run

  local trend status
  trend="$(update_history_and_trend)"
  status="$(compute_status)"
  write_summary "$status" "$trend"
  write_json "$status"

  {
    echo ""
    echo "---"
    echo ""
    echo "<sub>Erzeugt am ${NOW_HUMAN} · Modus \`${MODE}\`$([ "$DRY_RUN" -eq 1 ] && echo ' · dry-run') ·"
    echo "Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>"
  } >> "$REPORT_MD"

  log "Bericht geschrieben: $REPORT_MD (Status: $status)"
  rm -f "$METRICS" "$ACTIONS_FILE"
  return 0
}

do_reboot() {
  local delay=1
  while [ $# -gt 0 ]; do
    case "$1" in
      --delay) delay="$2"; shift 2;;
      --dry-run) DRY_RUN=1; shift;;
      *) log "Unbekannte Option für reboot: $1"; shift;;
    esac
  done
  if [ "$DRY_RUN" -eq 1 ]; then log "DRY-RUN: würde 'shutdown -r +${delay}' ausführen."; return 0; fi
  if ! can_priv; then log "FEHLER: Reboot benötigt root/sudo."; return 1; fi
  sync
  log "Host-Reboot in ${delay} Minute(n) …"
  priv shutdown -r "+${delay}" "Festas geplanter Wartungs-Reboot" || priv systemctl reboot
}

main() {
  local cmd="${1:-run}"; shift || true
  case "$cmd" in
    run)
      while [ $# -gt 0 ]; do
        case "$1" in
          --mode) MODE="$2"; shift 2;;
          --apt) APT_MODE="$2"; shift 2;;
          --dry-run) DRY_RUN=1; shift;;
          --output-dir) OUTPUT_DIR="$2"; shift 2;;
          -h|--help) usage; exit 0;;
          *) log "Unbekannte Option: $1"; usage; exit 2;;
        esac
      done
      case "$MODE" in analyze|maintain|full) ;; *) log "Ungültiger Modus: $MODE"; exit 2;; esac
      case "$APT_MODE" in all|security|none) ;; *) log "Ungültiger apt-Modus: $APT_MODE"; exit 2;; esac
      do_run ;;
    reboot) do_reboot "$@";;
    -h|--help|help) usage;;
    *) log "Unbekannter Befehl: $cmd"; usage; exit 2;;
  esac
}

main "$@"
