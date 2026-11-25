# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-11-25 02:03:51 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 37 % belegt |
| RAM | 9 % belegt |
| Paket-Updates offen | 47 |
| Fehlgeschlagene Dienste | 4 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: +743MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 4 fehlgeschlagene systemd-Unit(s).
- 🟡 Viele fehlgeschlagene Logins (13788) – Brute-Force? fail2ban prüfen.
- 🟡 3 sicherheitsrelevante Updates ausstehend.

**Empfehlungen (Optimierungspotenzial):**

- Alte Kernel/Pakete entfernen (`apt-get -y autoremove --purge`).
- Fehlgeschlagene Dienste untersuchen (`systemctl --failed`).
- Paket-Updates einspielen (Modus `maintain`/`full`).
- Host-Reboot einplanen (Kernel/Bibliotheks-Updates aktivieren).


## 🖥️ System-Übersicht

| Feld | Wert |
|---|---|
| Host | `festas-builds` |
| OS | Ubuntu 24.04.5 LTS |
| Kernel | Linux 6.8.0-138-generic |
| Virtualisierung | kvm |
| CPU-Kerne | 8 |
| Load (1/5/15) | 0.00, 0.00, 0.00 |
| Uptime | up 13 weeks, 3 days, 13 hours, 52 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  106G  183G  37% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/be941dded444bd4725031331a62c51031e6db968ff912aba8bf37de8f96fad7c
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/8bc34fa36ea89c3c48b356664294fa89e9dff02d6da84231354276e6e1ee783b
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/771e0c9552f3dbab7f47f8346da6b40a01dd19b17396f27cfd440dc97b3cf08b
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
```
**Root (`/`):** 106GB / 301GB belegt (37 %), frei: 183GB.
**Inodes (`/`):** 6 % belegt.

### Größte Verzeichnisse unter / (eine Ebene)

```
```

### Größte Verzeichnisse (Top 20)

```
```

### Größte Einzeldateien (Top 20)

```
```

### Bekannte Speicherfresser

| Bereich | Pfad | Größe |
|---|---|---|
| Docker gesamt | `/var/lib/docker` | 0B |
| Pterodactyl-Volumes | `/var/lib/pterodactyl/volumes` | 0B |
| System-Logs | `/var/log` | 0B |
| Journald | `/var/log/journal` | 0B |
| APT-Cache | `/var/cache/apt` | 0B |
| Snap | `/var/lib/snapd` | 0B |
| Tmp | `/tmp` | 0B |
| Home | `/home` | 0B |

**Alte Kernel installiert:** 1 (aktiv: `6.8.0-138-generic`) → `apt-get autoremove` gibt Platz frei.

## 🐳 Docker & Container


### Speicherverbrauch (docker system df)

```
TYPE            TOTAL     ACTIVE    SIZE      RECLAIMABLE
Images          6         6         2.169GB   0B (0%)
Containers      9         4         233.5kB   20.48kB (8%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **0B (0%)**.

### Container-Status

```
NAMES                                  STATUS                SIZE
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Created               4.1kB (virtual 603MB)
80c1457a-55b2-4671-82a8-60063041558b   Created               4.1kB (virtual 603MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Created               4.1kB (virtual 603MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Created               4.1kB (virtual 592MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Created               4.1kB (virtual 592MB)
cosmic-survivor                        Up 7 days (healthy)   81.9kB (virtual 53.3MB)
minecraft-web                          Up 7 days (healthy)   81.9kB (virtual 68.5MB)
fire-simulator                         Up 7 days             24.6kB (virtual 233MB)
festas-redis                           Up 7 days (healthy)   24.6kB (virtual 41.1MB)
```

Container: **4/9** laufend, **0** ungesund.

## 🪶 Pterodactyl / Wings

Wings-Dienst: **aktiv**.

### Server-Volumes (größte 15)

```
```

## ⛏️ Minecraft-Server (Welten & Logs)

| Server | Root | Welten | Logs | Plugins |
|---|---|---|---|---|
| Lobby | `/var/lib/pterodactyl/volumes/39a0762a-9e53-4b5b-8810-2bf63410800d` | 0B | 0B | 0B |
| Proxy | `/var/lib/pterodactyl/volumes/b50e2f8c-440f-4910-8f00-29577afbc455` | 0B | 0B | 0B |
| Survival | `/var/lib/pterodactyl/volumes/cfb531d8-3843-4bff-a8d5-b534aa58fc92` | 0B | 0B | 0B |
| Skyblock | `/var/lib/pterodactyl/volumes/80c1457a-55b2-4671-82a8-60063041558b` | 0B | 0B | 0B |
| Mining(rpg) | `/var/lib/pterodactyl/volumes/0af91553-d5ef-42fc-9ed1-97daaf3c4d70` | 0B | 0B | 0B |

## 🧠 Arbeitsspeicher & Prozesse


### Speicher (free)

```
               total        used        free      shared  buff/cache   available
Mem:            15Gi       1.5Gi       9.1Gi        33Mi       5.0Gi        13Gi
Swap:          2.0Gi        99Mi       1.9Gi
```

**RAM-Auslastung:** 9 % belegt.
**Swap:** 4 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
1525703       1 mysql    505712  3.1 0.0 mariadbd
1525439       1 root     170884  1.0 0.0 systemd-journal
2201889       1 root     122268  0.7 0.0 dockerd
2202342 2202232 fire     104756  0.6 0.0 next-server (v
1525629       1 root     81048  0.5  0.0 fail2ban-server
2201092       1 root     69452  0.4  0.1 containerd
1973499 1525589 www-data 43520  0.2  0.0 php-fpm8.3
1973501 1525589 www-data 42648  0.2  0.0 php-fpm8.3
1973476 1525589 www-data 42280  0.2  0.0 php-fpm8.3
2202606       1 root     37960  0.2  0.0 wings
1525434       1 root     27464  0.1  0.0 multipathd
1525589       1 root     23376  0.1  0.0 php-fpm8.3
1525584       1 postgres 17296  0.1  0.0 postgres
3069575 3069569 www-data 15420  0.0  0.0 nginx
3069580 3069569 www-data 15404  0.0  0.0 nginx
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
3645724 root      2.7  0.0 bash
3645483 root      1.3  0.0 systemd
2202358 pteroda+  0.3  0.0 redis-server
1525483 redis     0.1  0.0 redis-server
2201092 root      0.1  0.4 containerd
1525703 mysql     0.0  3.1 mariadbd
1525629 root      0.0  0.5 fail2ban-server
2201889 root      0.0  0.7 dockerd
1525464 root      0.0  0.0 qemu-ga
3645252 root      0.0  0.0 sshd
```

**OOM-Ereignisse (7 Tage):** 0.

## 🩺 Dienste & Health


### Fehlgeschlagene Units

```
● economy-export.service      loaded failed failed Export the per-server richest-players board (CMI balance + LuckPerms rank) into economy.json
● leaderboard-export.service  loaded failed failed Export the player leaderboard (playtime + LuckPerms rank) into leaderboard.json
● plan-players-export.service loaded failed failed Export Plan player counts into players.json for the website
● pteroq.service              loaded failed failed Pterodactyl Queue Worker
```

### Kern-Dienste

| Dienst | Status |
|---|---|
| docker | active |
| wings | active |
| nginx | active |
| mariadb | active |
| mysql | active |
| redis-server | active |
| redis | active |
| fail2ban | active |
| ssh | active |
| cron | active |
| systemd-timesyncd | active |

**Zeit-Synchronisation (NTP):** yes.

## 🌐 Netzwerk


### Offene Ports (LISTEN)

```
tcp 0.0.0.0:22
tcp 0.0.0.0:3306
tcp 0.0.0.0:443
tcp 0.0.0.0:6379
tcp 0.0.0.0:80
tcp 127.0.0.1:3200
tcp 127.0.0.1:5432
tcp 127.0.0.1:8200
tcp 127.0.0.1:8201
tcp 127.0.0.53%lo:53
tcp 127.0.0.54:53
tcp [::1]:5432
tcp [::1]:6379
tcp 172.18.0.1:6380
tcp *:2022
tcp [::]:22
tcp [::]:443
tcp [::]:80
tcp *:8080
udp 127.0.0.53%lo:53
udp 127.0.0.54:53
udp 128.140.99.121%eth0:68
```

**Etablierte Verbindungen:** 4.

### Konnektivität & DNS

Öffentliche IPv4: `128.140.99.121` · DNS-Auflösung: ja.

## 🔐 Sicherheit


### Firewall

```
Status: active

To                         Action      From
--                         ------      ----
25565/tcp                  ALLOW       Anywhere                   # Velocity Proxy
22/tcp                     ALLOW       Anywhere                  
80/tcp                     ALLOW       Anywhere                  
443/tcp                    ALLOW       Anywhere                  
8080/tcp                   ALLOW       Anywhere                  
8443/tcp                   ALLOW       Anywhere                   # HTTPS
19132/udp                  ALLOW       Anywhere                   # GeyserMC Bedrock
3001                       ALLOW       Anywhere                  
4567/tcp                   ALLOW       Anywhere                  
8100/tcp                   ALLOW       Anywhere                   # Bluemap Webinterface
2022/tcp                   ALLOW       Anywhere                  
25566                      DENY        Anywhere                  
25567                      DENY        Anywhere                  
25568                      DENY        Anywhere                  
8100                       ALLOW       Anywhere                  
8101                       ALLOW       Anywhere                  
3306                       ALLOW       172.25.0.0/16             
25565:25600/tcp            DENY        Anywhere                  
25565:25600/udp            DENY        Anywhere                  
3306/tcp                   ALLOW       172.25.0.0/16             
6379/tcp                   ALLOW       172.25.0.0/16             
25599/tcp                  ALLOW       Anywhere                  
25600/tcp                  ALLOW       Anywhere                  
Nginx Full                 ALLOW       Anywhere                  
27015/udp                  ALLOW       Anywhere                  
27016/udp                  ALLOW       Anywhere                  
25570                      ALLOW       Anywhere                  
8201/tcp                   ALLOW       Anywhere                  
8085/tcp                   ALLOW       Anywhere                  
8804/tcp                   ALLOW       Anywhere                   # Plan Analytics
6380                       DENY        Anywhere                  
25565/tcp (v6)             ALLOW       Anywhere (v6)              # Velocity Proxy
22/tcp (v6)                ALLOW       Anywhere (v6)             
80/tcp (v6)                ALLOW       Anywhere (v6)             
443/tcp (v6)               ALLOW       Anywhere (v6)             
8080/tcp (v6)              ALLOW       Anywhere (v6)             
8443/tcp (v6)              ALLOW       Anywhere (v6)              # HTTPS
19132/udp (v6)             ALLOW       Anywhere (v6)              # GeyserMC Bedrock
3001 (v6)                  ALLOW       Anywhere (v6)             
4567/tcp (v6)              ALLOW       Anywhere (v6)             
8100/tcp (v6)              ALLOW       Anywhere (v6)              # Bluemap Webinterface
2022/tcp (v6)              ALLOW       Anywhere (v6)             
25566 (v6)                 DENY        Anywhere (v6)             
25567 (v6)                 DENY        Anywhere (v6)             
25568 (v6)                 DENY        Anywhere (v6)             
8100 (v6)                  ALLOW       Anywhere (v6)             
8101 (v6)                  ALLOW       Anywhere (v6)             
25565:25600/tcp (v6)       DENY        Anywhere (v6)             
25565:25600/udp (v6)       DENY        Anywhere (v6)             
25599/tcp (v6)             ALLOW       Anywhere (v6)             
25600/tcp (v6)             ALLOW       Anywhere (v6)             
Nginx Full (v6)            ALLOW       Anywhere (v6)             
27015/udp (v6)             ALLOW       Anywhere (v6)             
27016/udp (v6)             ALLOW       Anywhere (v6)             
25570 (v6)                 ALLOW       Anywhere (v6)             
8201/tcp (v6)              ALLOW       Anywhere (v6)             
8085/tcp (v6)              ALLOW       Anywhere (v6)             
8804/tcp (v6)              ALLOW       Anywhere (v6)              # Plan Analytics
6380 (v6)                  DENY        Anywhere (v6)             

```

### Interne Reverse-Proxy-Dienste

Heuristik auf Basis von `docs/infrastructure/PLAN.md` und
`docs/infrastructure/BLUEMAP.md`: diese Ports sind dokumentiert als
**intern-only** und sollen hostseitig nur via Loopback erreichbar sein.
Gewarnt wird bei Bind-Mismatches – also Nicht-Loopback-Binds oder nur `[::1]` trotz nginx-Upstream `127.0.0.1`.
Bestätigte UFW-`ALLOW Anywhere`-Freigaben werden zusätzlich explizit als öffentlich markiert.

| Dienst | Port | Soll | Beobachtung |
|---|---:|---|---|
| Plan Analytics | `8804` | nur via nginx / Host-Loopback | _kein Listener erkannt_ |
| BlueMap Survival | `8102` | nur via nginx / Host-Loopback | _kein Listener erkannt_ |
| BlueMap Mining | `8103` | nur via nginx / Host-Loopback | _kein Listener erkannt_ |

### fail2ban

```
Status
|- Number of jail:	1
`- Jail list:	sshd
```

### Fehlgeschlagene Logins (7 Tage)

Fehlgeschlagene Passwort-Logins: **13788**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **47** (davon sicherheitsrelevant: **3**).

⚠️ **Reboot erforderlich** (`reboot-required` vorhanden).
```
linux-image-6.8.0-139-generic
linux-base
libc6
linux-image-6.8.0-142-generic
linux-base
linux-image-6.8.0-146-generic
linux-base
linux-image-6.8.0-147-generic
linux-base
linux-image-6.8.0-149-generic
linux-base
libc6
```

### Aktualisierbare Pakete (Auszug)

```
Inst libnss-systemd [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) []
Inst systemd-dev [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [all]) []
Inst systemd-timesyncd [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) []
Inst systemd-resolved [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) []
Inst libsystemd-shared [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) [systemd:amd64 ]
Inst libsystemd0 [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) [systemd:amd64 ]
Inst systemd-sysv [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) [systemd:amd64 ]
Inst libpam-systemd [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) [systemd:amd64 ]
Inst systemd [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64])
Inst udev [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64]) []
Inst libudev1 [255.4-1ubuntu8.18] (255.4-1ubuntu8.19 Ubuntu:24.04/noble-updates [amd64])
Inst docker-ce-cli [5:29.10.0-1~ubuntu.24.04~noble] (5:29.10.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce [5:29.10.0-1~ubuntu.24.04~noble] (5:29.10.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst distro-info [1.7build1] (1.7ubuntu0.1 Ubuntu:24.04/noble-updates [amd64])
Inst tzdata [2026e-0ubuntu0.24.04.1] (2026f-0ubuntu0.24.04.1 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
Inst python3-distro-info [1.7build1] (1.7ubuntu0.1 Ubuntu:24.04/noble-updates [all])
Inst docker-buildx-plugin [0.39.0-1~ubuntu.24.04~noble] (0.39.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce-rootless-extras [5:29.10.0-1~ubuntu.24.04~noble] (5:29.10.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst libgl1-mesa-dri [25.2.8-0ubuntu0.24.04.4] (25.2.8-0ubuntu0.24.04.5 Ubuntu:24.04/noble-updates [amd64]) []
Inst libglx-mesa0 [25.2.8-0ubuntu0.24.04.4] (25.2.8-0ubuntu0.24.04.5 Ubuntu:24.04/noble-updates [amd64]) []
Inst libegl-mesa0 [25.2.8-0ubuntu0.24.04.4] (25.2.8-0ubuntu0.24.04.5 Ubuntu:24.04/noble-updates [amd64]) []
Inst libgbm1 [25.2.8-0ubuntu0.24.04.4] (25.2.8-0ubuntu0.24.04.5 Ubuntu:24.04/noble-updates [amd64]) []
Inst mesa-libgallium [25.2.8-0ubuntu0.24.04.4] (25.2.8-0ubuntu0.24.04.5 Ubuntu:24.04/noble-updates [amd64])
Inst libssh-4 [0.10.6-2ubuntu0.5] (0.10.6-2ubuntu0.6 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst linux-libc-dev [6.8.0-149.149] (6.8.0-151.151 Ubuntu:24.04/noble-updates [amd64])
Inst mesa-vulkan-drivers [25.2.8-0ubuntu0.24.04.4] (25.2.8-0ubuntu0.24.04.5 Ubuntu:24.04/noble-updates [amd64])
Inst php8.1-readline [8.1.34-9+ubuntu24.04.1+deb.sury.org+1] (8.1.34-14+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.1-opcache [8.1.34-9+ubuntu24.04.1+deb.sury.org+1] (8.1.34-14+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.1-phpdbg [8.1.34-9+ubuntu24.04.1+deb.sury.org+1] (8.1.34-14+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.1-cli [8.1.34-9+ubuntu24.04.1+deb.sury.org+1] (8.1.34-14+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
```

## 📜 Log-Analyse (7 Tage)

Journald: **16648** Fehler, **64840** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
  12454 systemd[#]: Failed to start plan-players-export.service - Export Plan player counts into players.json for the website.
   2016 systemd[#]: Failed to start leaderboard-export.service - Export the player leaderboard (playtime + LuckPerms rank) into leaderboard.json.
   2016 systemd[#]: Failed to start economy-export.service - Export the per-server richest-players board (CMI balance + LuckPerms rank) into economy.json.
    135 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     12 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      7 sshd[#]: error: Protocol major versions differ: # vs. #
      2 sshd[#]: error: send_error: write: Connection reset by peer
      1 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
      1 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
      1 sshd[#]: fatal: userauth_finish: send failure packet: Broken pipe [preauth]
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Jan 16 15:43:58 2027 GMT (**52 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-11-23+00:00:01.2460931690 /var/backups/dpkg.arch.0

> Aufbewahrung/Off-Site siehe [docs/infrastructure/BACKUPS.md](../../docs/infrastructure/BACKUPS.md).

## 🧹 Aufräum-Kandidaten

Diese Posten lassen sich typischerweise gefahrlos freigeben. Im Modus
`maintain`/`full` erledigt der Agent die mit **(auto)** markierten Punkte.

| Kandidat | Umfang | Aktion |
|---|---|---|
| APT-Paketcache | 0B | `apt-get clean` **(auto)** |
| Journald-Logs | aktuell ? | `journalctl --vacuum-time=14d` **(auto)** |
| Docker (dangling/build-cache) | 0B (0%) | `docker system prune -f` **(auto)** |
| Verwaiste Pakete/Kernel | variabel | `apt-get autoremove --purge` **(auto)** |
| Temp-Dateien | `/tmp` (0B) | `systemd-tmpfiles --clean` **(auto)** |

> **Nie automatisch gelöscht:** Welten, Spielerdaten, Datenbanken, Backups
> und Docker-**Volumes**. Diese werden nur analysiert.

## 🔧 Durchgeführte Wartungsaktionen


**Freigegebener Speicher in diesem Lauf:** 1022MB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-11-25 02:03:51 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
