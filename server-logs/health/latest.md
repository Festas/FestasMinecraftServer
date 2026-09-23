# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-09-23 02:05:39 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 44 % belegt |
| RAM | 74 % belegt |
| Paket-Updates offen | 12 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: +122MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (14999) – Brute-Force? fail2ban prüfen.
- 🟡 8 sicherheitsrelevante Updates ausstehend.

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
| Load (1/5/15) | 0.74, 0.65, 0.47 |
| Uptime | up 4 weeks, 3 days, 13 hours, 54 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  125G  164G  44% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/112483a404e535072efccab33ed27724561c3919260bae2a487186fb2846b602
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/7db11192f722c4c59ddebfeb2dca290490f3b720bdb96154c21a1e46f94fb616
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/771e0c9552f3dbab7f47f8346da6b40a01dd19b17396f27cfd440dc97b3cf08b
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/01768bf2e7553731e9b6c101908874f451f8cd1a5dbc951fa0f2089dd5501955
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/aaed9dac9f0a64cb22d4cc36319fbfa8a3f722eefe0f73537ad909e2c0ccec02
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/41a22aa8664928fbff723e6b105d804e67fd8984239dc5107c55b15ee8ed5f5f
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/e44a51497fc6ac05d78f9a40c2d2474971e055fc13b6a6306fa8748e5390d486
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
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 41 seconds         4.1kB (virtual 598MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 5 minutes          4.1kB (virtual 598MB)
80c1457a-55b2-4671-82a8-60063041558b   Up 24 hours           4.1kB (virtual 598MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 24 hours           4.1kB (virtual 598MB)
fire-simulator                         Up 5 days             4.1kB (virtual 233MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 7 days             4.1kB (virtual 598MB)
minecraft-web                          Up 7 days (healthy)   81.9kB (virtual 74.5MB)
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
Mem:            15Gi        11Gi       393Mi        41Mi       3.9Gi       3.8Gi
Swap:          2.0Gi       431Mi       1.6Gi
```

**RAM-Auslastung:** 74 % belegt.
**Swap:** 21 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
1690137 1690113 pteroda+ 3225300 20.1 51.9 java
1506494 1506468 pteroda+ 2825524 17.6 4.6 java
1507656 1507631 pteroda+ 2597156 16.2 3.7 java
1691284 1691259 pteroda+ 1648736 10.3 164 java
 383005  382906 pteroda+ 317636  1.9 3.7 java
3866550       1 mysql    314752  1.9 0.2 mariadbd
 381961       1 root     134312  0.8 0.9 dockerd
 647898  647874 fire     56104  0.3  0.0 next-server (v
3866255       1 root     53284  0.3  0.6 containerd
 791947       1 root     41592  0.2  0.0 fail2ban-server
 792116  792112 www-data 39792  0.2  0.0 php-fpm8.3
1070721  792112 www-data 39780  0.2  0.0 php-fpm8.3
1678609  792112 www-data 36856  0.2  0.0 php-fpm8.3
 382553       1 root     33000  0.2  3.0 wings
 792112       1 root     28596  0.1  0.0 php-fpm8.3
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
1691284 pteroda+  164 10.3 java
1690137 pteroda+ 51.9 20.1 java
1506494 pteroda+  4.6 17.6 java
 383005 pteroda+  3.7  1.9 java
1507656 pteroda+  3.7 16.2 java
 382553 root      3.0  0.2 wings
1691800 root      1.3  0.0 bash
1691634 root      1.3  0.0 systemd
 381961 root      0.9  0.8 dockerd
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

**Etablierte Verbindungen:** 85.

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

Fehlgeschlagene Passwort-Logins: **14999**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **12** (davon sicherheitsrelevant: **8**).

⚠️ **Reboot erforderlich** (`reboot-required` vorhanden).
```
linux-image-6.8.0-139-generic
linux-base
libc6
```

### Aktualisierbare Pakete (Auszug)

```
Inst libglib2.0-data [2.80.0-6ubuntu3.8] (2.80.0-6ubuntu3.9 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
Inst libglib2.0-bin [2.80.0-6ubuntu3.8] (2.80.0-6ubuntu3.9 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64]) []
Inst gir1.2-glib-2.0 [2.80.0-6ubuntu3.8] (2.80.0-6ubuntu3.9 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64]) []
Inst libglib2.0-0t64 [2.80.0-6ubuntu3.8] (2.80.0-6ubuntu3.9 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst libexpat1 [2.6.1-2ubuntu0.4] (2.6.1-2ubuntu0.5 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst netplan-generator [1.1.2-8ubuntu1~24.04.2] (1.1.2-8ubuntu1~24.04.3 Ubuntu:24.04/noble-updates [amd64]) []
Inst python3-netplan [1.1.2-8ubuntu1~24.04.2] (1.1.2-8ubuntu1~24.04.3 Ubuntu:24.04/noble-updates [amd64]) []
Inst netplan.io [1.1.2-8ubuntu1~24.04.2] (1.1.2-8ubuntu1~24.04.3 Ubuntu:24.04/noble-updates [amd64]) []
Inst libnetplan1 [1.1.2-8ubuntu1~24.04.2] (1.1.2-8ubuntu1~24.04.3 Ubuntu:24.04/noble-updates [amd64])
Inst libxml2 [2.9.14+dfsg-1.3ubuntu3.8] (2.9.14+dfsg-1.3ubuntu3.9 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst rsyslog [8.2312.0-3ubuntu9.3] (8.2312.0-3ubuntu9.4 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst openjdk-17-jre-headless [17.0.20+8-1~24.04] (17.0.20.1+1-1~24.04 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **110** Fehler, **32601** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
     81 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     18 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      5 sshd[#]: error: Protocol major versions differ: # vs. #
      3 sshd[#]: error: maximum authentication attempts exceeded for root from #.#.#.# port # ssh# [preauth]
      2 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
      1 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**55 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-09-19+00:00:01.1212073450 /var/backups/dpkg.arch.0

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


**Freigegebener Speicher in diesem Lauf:** 2.4GB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-09-23 02:05:39 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
