# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-09-16 02:05:38 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 44 % belegt |
| RAM | 76 % belegt |
| Paket-Updates offen | 16 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: +2.3GB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (14389) – Brute-Force? fail2ban prüfen.
- 🟡 2 sicherheitsrelevante Updates ausstehend.

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
| OS | Ubuntu 24.04.4 LTS |
| Kernel | Linux 6.8.0-138-generic |
| Virtualisierung | kvm |
| CPU-Kerne | 8 |
| Load (1/5/15) | 1.37, 1.16, 0.85 |
| Uptime | up 3 weeks, 3 days, 13 hours, 54 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  125G  164G  44% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/14ee66121e40a461ec442d68b5bcf0e01d847e3dcf7ee73bf2f6ecf2797866df
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/112483a404e535072efccab33ed27724561c3919260bae2a487186fb2846b602
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/6d62064a8b6eebfa7393bdc4267184a480398a57139fa6ac5586d6d9ebddf555
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/a5d564332d96a2c09ff22a71648247394b656dc41c4cc82acb5b780a67207b98
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/7389f091f1995168d237aaa133b0a581e65c816dc625638b2f7d588df45948a7
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/faa689bfc689edff0daf18c5b0e6b17efbdbcd8d89add23f1fd0ebeb413e61b1
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/adb90d066abcb31c281b55bce57394f6b00d84ec5176ef81a9dcc4a61cc5ecd5
```
**Root (`/`):** 125GB / 301GB belegt (44 %), frei: 164GB.
**Inodes (`/`):** 8 % belegt.

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
Images          38        4         20.29GB   18.54GB (91%)
Containers      8         8         131.1kB   0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     21        0         2.494GB   2.184GB
```

Wiedergewinnbar laut Docker: **18.54GB (91%)**.

### Container-Status

```
NAMES                                  STATUS                SIZE
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 39 seconds         4.1kB (virtual 598MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 5 minutes          4.1kB (virtual 598MB)
80c1457a-55b2-4671-82a8-60063041558b   Up 24 hours           4.1kB (virtual 598MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 24 hours           4.1kB (virtual 598MB)
fire-simulator                         Up 4 days             4.1kB (virtual 233MB)
minecraft-web                          Up 4 days (healthy)   81.9kB (virtual 74.5MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 7 days             4.1kB (virtual 598MB)
festas-redis                           Up 7 days (healthy)   24.6kB (virtual 41.1MB)
```

Container: **8/8** laufend, **0** ungesund.

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
Mem:            15Gi        11Gi       552Mi        42Mi       3.5Gi       3.6Gi
Swap:          2.0Gi       336Mi       1.7Gi
```

**RAM-Auslastung:** 76 % belegt.
**Swap:** 16 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
 377215  377190 pteroda+ 3446836 21.5 78.1 java
 194590  194565 pteroda+ 2655944 16.6 3.6 java
 193404  193379 pteroda+ 2650836 16.5 4.6 java
 378345  378319 pteroda+ 1694196 10.5 177 java
3259524 3259451 pteroda+ 482860  3.0 3.7 java
3866550       1 mysql    265116  1.6 0.2 mariadbd
3258744       1 root     134676  0.8 0.9 dockerd
3866276       1 root     132076  0.8 0.0 systemd-journal
3722811 3722787 fire     58628  0.3  0.0 next-server (v
3866255       1 root     55772  0.3  0.6 containerd
3867035       1 root     52816  0.3  0.0 fail2ban-server
  33881 3866370 www-data 40244  0.2  0.0 php-fpm8.3
3888935 3866370 www-data 40140  0.2  0.0 php-fpm8.3
  33880 3866370 www-data 39004  0.2  0.0 php-fpm8.3
3259338       1 root     31832  0.1  3.0 wings
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
 378345 pteroda+  177 10.5 java
 379365 root      100  0.0 ps
 377215 pteroda+ 78.1 21.5 java
 193404 pteroda+  4.6 16.5 java
3259524 pteroda+  3.7  3.0 java
 194590 pteroda+  3.6 16.6 java
3259338 root      3.0  0.1 wings
 378868 root      1.4  0.0 bash
 378697 root      1.1  0.0 systemd
3258744 root      0.9  0.8 dockerd
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
udp 0.0.0.0:25600
```

**Etablierte Verbindungen:** 87.

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

Fehlgeschlagene Passwort-Logins: **14389**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **16** (davon sicherheitsrelevant: **2**).

⚠️ **Reboot erforderlich** (`reboot-required` vorhanden).
```
linux-image-6.8.0-139-generic
linux-base
libc6
```

### Aktualisierbare Pakete (Auszug)

```
Inst motd-news-config [13ubuntu10.4] (13ubuntu10.5 Ubuntu:24.04/noble-updates [all])
Inst base-files [13ubuntu10.4] (13ubuntu10.5 Ubuntu:24.04/noble-updates [amd64])
Inst docker-ce-cli [5:29.8.0-1~ubuntu.24.04~noble] (5:29.8.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce [5:29.8.0-1~ubuntu.24.04~noble] (5:29.8.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst libgssapi-krb5-2 [1.20.1-6ubuntu2.8] (1.20.1-6ubuntu2.10 Ubuntu:24.04/noble-updates [amd64]) []
Inst libkrb5-3 [1.20.1-6ubuntu2.8] (1.20.1-6ubuntu2.10 Ubuntu:24.04/noble-updates [amd64]) []
Inst libkrb5support0 [1.20.1-6ubuntu2.8] (1.20.1-6ubuntu2.10 Ubuntu:24.04/noble-updates [amd64]) [libk5crypto3:amd64 ]
Inst libk5crypto3 [1.20.1-6ubuntu2.8] (1.20.1-6ubuntu2.10 Ubuntu:24.04/noble-updates [amd64])
Inst python-apt-common [2.7.7ubuntu5.2] (2.7.7ubuntu5.3 Ubuntu:24.04/noble-updates [all])
Inst python3-apt [2.7.7ubuntu5.2] (2.7.7ubuntu5.3 Ubuntu:24.04/noble-updates [amd64])
Inst ubuntu-release-upgrader-core [1:24.04.28] (1:24.04.29 Ubuntu:24.04/noble-updates [all]) []
Inst python3-distupgrade [1:24.04.28] (1:24.04.29 Ubuntu:24.04/noble-updates [all])
Inst docker-buildx-plugin [0.37.0-1~ubuntu.24.04~noble] (0.37.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce-rootless-extras [5:29.8.0-1~ubuntu.24.04~noble] (5:29.8.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst nginx [1.24.0-2ubuntu7.17] (1.24.0-2ubuntu7.18 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64]) []
Inst nginx-common [1.24.0-2ubuntu7.17] (1.24.0-2ubuntu7.18 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
```

## 📜 Log-Analyse (7 Tage)

Journald: **79** Fehler, **34198** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
     54 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
      7 sshd[#]: error: Protocol major versions differ: # vs. #
      6 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      4 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
      3 sshd[#]: error: beginning MaxStartups throttling
      2 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
      2 sshd[#]: error: send_error: write: Connection reset by peer
      1 sshd[#]: error: maximum authentication attempts exceeded for root from #.#.#.# port # ssh# [preauth]
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**62 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-09-13+00:00:01.5866930380 /var/backups/dpkg.arch.0

> Aufbewahrung/Off-Site siehe [docs/infrastructure/BACKUPS.md](../../docs/infrastructure/BACKUPS.md).

## 🧹 Aufräum-Kandidaten

Diese Posten lassen sich typischerweise gefahrlos freigeben. Im Modus
`maintain`/`full` erledigt der Agent die mit **(auto)** markierten Punkte.

| Kandidat | Umfang | Aktion |
|---|---|---|
| APT-Paketcache | 0B | `apt-get clean` **(auto)** |
| Journald-Logs | aktuell ? | `journalctl --vacuum-time=14d` **(auto)** |
| Docker (dangling/build-cache) | 18.54GB (91%) | `docker system prune -f` **(auto)** |
| Verwaiste Pakete/Kernel | variabel | `apt-get autoremove --purge` **(auto)** |
| Temp-Dateien | `/tmp` (0B) | `systemd-tmpfiles --clean` **(auto)** |

> **Nie automatisch gelöscht:** Welten, Spielerdaten, Datenbanken, Backups
> und Docker-**Volumes**. Diese werden nur analysiert.

## 🔧 Durchgeführte Wartungsaktionen


**Freigegebener Speicher in diesem Lauf:** 2.3GB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-09-16 02:05:38 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
