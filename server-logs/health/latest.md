# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-11-11 02:03:32 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 37 % belegt |
| RAM | 79 % belegt |
| Paket-Updates offen | 9 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: -823MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (14301) – Brute-Force? fail2ban prüfen.
- 🟡 6 sicherheitsrelevante Updates ausstehend.

**Empfehlungen (Optimierungspotenzial):**

- Alte Kernel/Pakete entfernen (`apt-get -y autoremove --purge`).
- Fehlgeschlagene Dienste untersuchen (`systemctl --failed`).
- Port 8804 (Plan Analytics) in UFW schließen oder nur nach `127.0.0.1` veröffentlichen.
- Port 8102 (BlueMap Survival) nur nach `127.0.0.1` veröffentlichen; falls bewusst breiter gebunden, Host-Firewall/UFW explizit prüfen.
- Port 8103 (BlueMap Mining) nur nach `127.0.0.1` veröffentlichen; falls bewusst breiter gebunden, Host-Firewall/UFW explizit prüfen.
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
| Load (1/5/15) | 0.34, 0.23, 0.19 |
| Uptime | up 11 weeks, 3 days, 13 hours, 52 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  105G  184G  37% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/771e0c9552f3dbab7f47f8346da6b40a01dd19b17396f27cfd440dc97b3cf08b
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/be941dded444bd4725031331a62c51031e6db968ff912aba8bf37de8f96fad7c
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/8bc34fa36ea89c3c48b356664294fa89e9dff02d6da84231354276e6e1ee783b
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/93fc506032ebde3a51a0bc1e2f82cb79555818acd3cbb0e5c1dea599736fa025
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/af81d7b82cf04824263d2e3db8034a8237ee0c4f2cb860e797a6caca84148eb9
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/e721447e12291327578bab5ab0f9c3c170d56e373d81011917d32d47f1abbcd1
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/ec285eaf35c183aafc3c3f4740b293a783f6dfe02f4fdc3bb7a58aa2b440568e
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/35895932e866179470cd6b762c0162dd32b8979d054a703a713c82133b98b3b9
```
**Root (`/`):** 105GB / 301GB belegt (37 %), frei: 184GB.
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
Images          5         5         1.346GB   0B (0%)
Containers      9         9         233.5kB   0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **0B (0%)**.

### Container-Status

```
NAMES                                  STATUS                SIZE
80c1457a-55b2-4671-82a8-60063041558b   Up 23 hours           4.1kB (virtual 592MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 23 hours           4.1kB (virtual 592MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 23 hours           4.1kB (virtual 592MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 23 hours           4.1kB (virtual 592MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 7 days             4.1kB (virtual 592MB)
cosmic-survivor                        Up 7 days (healthy)   81.9kB (virtual 53.3MB)
minecraft-web                          Up 7 days (healthy)   81.9kB (virtual 68.5MB)
fire-simulator                         Up 7 days             24.6kB (virtual 233MB)
festas-redis                           Up 7 days (healthy)   24.6kB (virtual 41.1MB)
```

Container: **9/9** laufend, **0** ungesund.

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
Mem:            15Gi        12Gi       347Mi        45Mi       3.2Gi       3.1Gi
Swap:          2.0Gi        56Mi       1.9Gi
```

**RAM-Auslastung:** 79 % belegt.
**Swap:** 2 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
 283973  283947 pteroda+ 3341480 20.9 3.6 java
 286575  286550 pteroda+ 2839948 17.7 2.6 java
 287944  287920 pteroda+ 2495000 15.6 2.0 java
 285291  285266 pteroda+ 1987652 12.4 2.0 java
3304351       1 mysql    556568  3.4 0.2 mariadbd
3019394 3019369 pteroda+ 486712  3.0 1.2 java
3304190       1 root     118212  0.7 0.0 systemd-journal
3017934       1 root     80708  0.5  0.4 dockerd
3018391 3018276 fire     70592  0.4  0.0 next-server (v
 395313       1 root     48752  0.3  0.0 fail2ban-server
3810410       1 root     48104  0.3  0.3 containerd
3330601 3304256 www-data 44212  0.2  0.0 php-fpm8.3
3304261 3304256 www-data 43876  0.2  0.0 php-fpm8.3
3304262 3304256 www-data 43284  0.2  0.0 php-fpm8.3
3018656       1 root     36308  0.2  2.1 wings
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
 283973 pteroda+  3.6 20.9 java
 286575 pteroda+  2.6 17.7 java
3018656 root      2.1  0.2 wings
 287944 pteroda+  2.0 15.6 java
 285291 pteroda+  2.0 12.4 java
3019394 pteroda+  1.2  3.0 java
 513325 root      1.1  0.0 bash
 513064 root      1.0  0.0 systemd
3017934 root      0.4  0.5 dockerd
3810410 root      0.3  0.3 containerd
```

**OOM-Ereignisse (7 Tage):** 0.

## 🩺 Dienste & Health


### Fehlgeschlagene Units

```
● pteroq.service loaded failed failed Pterodactyl Queue Worker
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
tcp 0.0.0.0:19132
tcp 0.0.0.0:22
tcp 0.0.0.0:25565
tcp 0.0.0.0:25566
tcp 0.0.0.0:25567
tcp 0.0.0.0:25568
tcp 0.0.0.0:25569
tcp 0.0.0.0:25599
tcp 0.0.0.0:25600
tcp 0.0.0.0:3306
tcp 0.0.0.0:443
tcp 0.0.0.0:6379
tcp 0.0.0.0:80
tcp 0.0.0.0:8085
tcp 0.0.0.0:8100
tcp 0.0.0.0:8101
tcp 0.0.0.0:8102
tcp 0.0.0.0:8103
tcp 0.0.0.0:8804
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
udp 0.0.0.0:19132
udp 0.0.0.0:25565
udp 0.0.0.0:25566
udp 0.0.0.0:25567
udp 0.0.0.0:25568
udp 0.0.0.0:25569
udp 0.0.0.0:25599
```

**Etablierte Verbindungen:** 82.

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
| Plan Analytics | `8804` | nur via nginx / Host-Loopback | ⚠️ öffentlich freigegeben (0.0.0.0:8804, 0.0.0.0:8804; UFW `ALLOW Anywhere`) |
| BlueMap Survival | `8102` | nur via nginx / Host-Loopback | ⚠️ bindet auf allen Interfaces (0.0.0.0:8102, 0.0.0.0:8102); intern-only-Vorgabe verletzt, öffentliche Freigabe per UFW nicht bestätigt |
| BlueMap Mining | `8103` | nur via nginx / Host-Loopback | ⚠️ bindet auf allen Interfaces (0.0.0.0:8103, 0.0.0.0:8103); intern-only-Vorgabe verletzt, öffentliche Freigabe per UFW nicht bestätigt |

### fail2ban

```
Status
|- Number of jail:	1
`- Jail list:	sshd
```

### Fehlgeschlagene Logins (7 Tage)

Fehlgeschlagene Passwort-Logins: **14301**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **9** (davon sicherheitsrelevant: **6**).

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
```

### Aktualisierbare Pakete (Auszug)

```
Inst libperl5.38t64 [5.38.2-3.2ubuntu0.6] (5.38.2-3.2ubuntu0.7 Ubuntu:24.04/noble-security [amd64]) [perl:amd64 ]
Inst perl [5.38.2-3.2ubuntu0.6] (5.38.2-3.2ubuntu0.7 Ubuntu:24.04/noble-security [amd64]) []
Inst perl-base [5.38.2-3.2ubuntu0.6] (5.38.2-3.2ubuntu0.7 Ubuntu:24.04/noble-security [amd64]) []
Inst perl-modules-5.38 [5.38.2-3.2ubuntu0.6] (5.38.2-3.2ubuntu0.7 Ubuntu:24.04/noble-security [all])
Inst sqlite3 [3.45.1-1ubuntu2.8] (3.45.1-1ubuntu2.9 Ubuntu:24.04/noble-updates [amd64]) []
Inst libsqlite3-0 [3.45.1-1ubuntu2.8] (3.45.1-1ubuntu2.9 Ubuntu:24.04/noble-updates [amd64])
Inst linux-libc-dev [6.8.0-147.147] (6.8.0-149.149 Ubuntu:24.04/noble-updates [amd64])
Inst openjdk-17-jre-headless [17.0.20.1+1-1~24.04] (17.0.21.0.3+1-1~24.04.1 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst python3-pyasn1 [0.4.8-4ubuntu0.3] (0.4.8-4ubuntu0.4 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
```

## 📜 Log-Analyse (7 Tage)

Journald: **137** Fehler, **31833** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
     73 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     39 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
     12 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      7 sshd[#]: error: Protocol major versions differ: # vs. #
      2 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
      1 systemd[#]: Failed to start plan-players-export.service - Export Plan player counts into players.json for the website.
      1 sshd[#]: error: userauth_pubkey: parse key: Invalid key length [preauth]
      1 sshd[#]: error: send_error: write: Connection reset by peer
      1 sshd[#]: error: maximum authentication attempts exceeded for root from #.#.#.# port # ssh# [preauth]
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Jan 16 15:43:58 2027 GMT (**66 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-11-09+00:00:01.1467688580 /var/backups/dpkg.arch.0

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


**Freigegebener Speicher in diesem Lauf:** 293MB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-11-11 02:03:32 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
