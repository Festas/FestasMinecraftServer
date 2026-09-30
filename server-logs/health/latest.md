# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-09-30 02:12:02 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 43 % belegt |
| RAM | 75 % belegt |
| Paket-Updates offen | 28 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: -2.4GB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (21713) – Brute-Force? fail2ban prüfen.
- 🟡 5 sicherheitsrelevante Updates ausstehend.

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
| Load (1/5/15) | 0.28, 0.29, 0.23 |
| Uptime | up 5 weeks, 3 days, 14 hours, 0 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  123G  166G  43% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/112483a404e535072efccab33ed27724561c3919260bae2a487186fb2846b602
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/7db11192f722c4c59ddebfeb2dca290490f3b720bdb96154c21a1e46f94fb616
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/771e0c9552f3dbab7f47f8346da6b40a01dd19b17396f27cfd440dc97b3cf08b
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/e59ab43912f7cf815f1d8e920da8fefe2f268c9439ff9269d78efa33293f5b06
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/53adfdd9d611cd6b69c527704b0f8f856a847fc6cc3be6ba97156c4c4460dc17
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/ad7e43386e49f88849ca629a41420d6a5bec0c6dddb1ef1a1640b4690444e12a
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/15dd13598595cb0e510078070f576ef84867b8e0783b112fe7de07a6c46b2aa5
```
**Root (`/`):** 123GB / 301GB belegt (43 %), frei: 166GB.
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
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **18.54GB (91%)**.

### Container-Status

```
NAMES                                  STATUS                 SIZE
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 2 minutes           4.1kB (virtual 598MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 7 minutes           4.1kB (virtual 598MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 11 minutes          4.1kB (virtual 598MB)
80c1457a-55b2-4671-82a8-60063041558b   Up 24 hours            4.1kB (virtual 598MB)
fire-simulator                         Up 12 days             4.1kB (virtual 233MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 2 weeks             4.1kB (virtual 598MB)
minecraft-web                          Up 2 weeks (healthy)   81.9kB (virtual 74.5MB)
festas-redis                           Up 2 weeks (healthy)   24.6kB (virtual 41.1MB)
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
Mem:            15Gi        11Gi       220Mi        54Mi       3.9Gi       3.7Gi
Swap:          2.0Gi       409Mi       1.6Gi
```

**RAM-Auslastung:** 75 % belegt.
**Swap:** 19 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
3015843 3015818 pteroda+ 3137472 19.6 20.3 java
3018042 3018017 pteroda+ 2729408 17.0 62.2 java
2835101 2835076 pteroda+ 2619432 16.3 2.0 java
3016959 3016935 pteroda+ 1821444 11.3 15.2 java
3866550       1 mysql    382168  2.3 0.2 mariadbd
 383005  382906 pteroda+ 350304  2.1 3.4 java
 381961       1 root     133492  0.8 0.9 dockerd
2485567       1 root     59672  0.3  0.0 fail2ban-server
3866255       1 root     55276  0.3  0.6 containerd
 647898  647874 fire     54748  0.3  0.0 next-server (v
3866276       1 root     47140  0.2  0.0 systemd-journal
2577472 2485536 www-data 45796  0.2  0.0 php-fpm8.3
2876130 2485536 www-data 45276  0.2  0.0 php-fpm8.3
2948992 2485536 www-data 44852  0.2  0.0 php-fpm8.3
2485536       1 root     37652  0.2  0.0 php-fpm8.3
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
3018042 pteroda+ 62.2 17.0 java
3015843 pteroda+ 20.3 19.6 java
3016959 pteroda+ 15.2 11.3 java
 383005 pteroda+  3.4  2.1 java
 382553 root      2.9  0.2 wings
2835101 pteroda+  2.0 16.3 java
3018915 root      1.4  0.0 bash
 381961 root      0.9  0.8 dockerd
3018739 root      0.9  0.0 systemd
3866255 root      0.6  0.3 containerd
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

**Etablierte Verbindungen:** 83.

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

Fehlgeschlagene Passwort-Logins: **21713**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **28** (davon sicherheitsrelevant: **5**).

⚠️ **Reboot erforderlich** (`reboot-required` vorhanden).
```
linux-image-6.8.0-139-generic
linux-base
libc6
linux-image-6.8.0-142-generic
linux-base
```

### Aktualisierbare Pakete (Auszug)

```
Inst libaudit-common [1:3.1.2-2.1build1.1] (1:3.1.2-2.1ubuntu0.1 Ubuntu:24.04/noble-updates [all])
Inst libaudit1 [1:3.1.2-2.1build1.1] (1:3.1.2-2.1ubuntu0.1 Ubuntu:24.04/noble-updates [amd64])
Inst libapparmor1 [4.0.1really4.0.1-0ubuntu0.24.04.7] (4.0.1really4.0.1-0ubuntu0.24.04.8 Ubuntu:24.04/noble-updates [amd64])
Inst apparmor [4.0.1really4.0.1-0ubuntu0.24.04.7] (4.0.1really4.0.1-0ubuntu0.24.04.8 Ubuntu:24.04/noble-updates [amd64])
Inst dmidecode [3.5-3ubuntu0.1] (3.5-3ubuntu0.2 Ubuntu:24.04/noble-updates [amd64])
Inst containerd.io [2.3.5-1~ubuntu.24.04~noble] (2.3.6-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst dracut-install [060+5-1ubuntu3.3] (060+5-1ubuntu3.4 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst java-21-amazon-corretto-jdk [1:21.0.12.9-1] (1:21.0.12.12-1 . stable:stable [amd64])
Inst libdbi-perl [1.643-4ubuntu0.1] (1.643-4ubuntu0.3 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst libevent-core-2.1-7t64 [2.1.12-stable-9ubuntu2.1] (2.1.12-stable-9ubuntu2.2 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst libpciaccess0 [0.17-3ubuntu0.24.04.2] (0.17-3ubuntu0.24.04.3 Ubuntu:24.04/noble-updates [amd64])
Inst php8.3-bcmath [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-zip [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-xml [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-sqlite3 [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-readline [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-opcache [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-mysql [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-mbstring [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-intl [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-gd [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-curl [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-fpm [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-cli [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-common [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64])
Inst php8.3 [8.3.33-1+ubuntu24.04.1+deb.sury.org+1] (8.3.35-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [all])
Inst python3-jwt [2.7.0-1ubuntu0.1] (2.7.0-1ubuntu0.2 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
Inst python3-requests [2.31.0+dfsg-1ubuntu1.1] (2.31.0+dfsg-1ubuntu1.2 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
```

## 📜 Log-Analyse (7 Tage)

Journald: **140** Fehler, **32195** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
    109 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     18 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      8 sshd[#]: error: Protocol major versions differ: # vs. #
      4 sshd[#]: error: beginning MaxStartups throttling
      1 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**48 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-09-28+00:00:01.2588706940 /var/backups/dpkg.arch.0

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


**Freigegebener Speicher in diesem Lauf:** 262MB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-09-30 02:12:02 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
