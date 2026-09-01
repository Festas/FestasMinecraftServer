# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-09-01 07:42:43 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 43 % belegt |
| RAM | 73 % belegt |
| Paket-Updates offen | 10 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `analyze` |
| Trend | Seit letztem Lauf: -2.7GB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Viele fehlgeschlagene Logins (15246) – Brute-Force? fail2ban prüfen.

**Empfehlungen (Optimierungspotenzial):**

- Alte Kernel/Pakete entfernen (`apt-get -y autoremove --purge`).
- Fehlgeschlagene Dienste untersuchen (`systemctl --failed`).
- Paket-Updates einspielen (Modus `maintain`/`full`).


## 🖥️ System-Übersicht

| Feld | Wert |
|---|---|
| Host | `festas-builds` |
| OS | Ubuntu 24.04.4 LTS |
| Kernel | Linux 6.8.0-138-generic |
| Virtualisierung | kvm |
| CPU-Kerne | 8 |
| Load (1/5/15) | 0.27, 0.30, 0.27 |
| Uptime | up 1 week, 2 days, 19 hours, 31 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  123G  166G  43% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/c467aa55259504d6c883d3bf1c360f1cfd0d2d9f867b82c794260095c4bbb3a3
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/d9084c6407adad383d78a7b653f55c35c90d7c9632d613bb3c7aae3d3232bfac
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/2107c31dc55250ba0acc7e22dcdb47739734fc0f74dfaf955da41f67514c1997
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/eb80f665fe88f409986c85e083ea0a71be0e5fa58697b19a8e5592e577947517
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/3c72a36f6b362def8632775a63607efda0c8c165c3844a15fba957cbbd7c46c2
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/16f2943a83fef0bec056424c440574df4e1d8add9395dd4a8cc11ffe07c02806
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/344fa784c24d7d104cfa69f6325b7c804b3c6df6dbbcaf07f4f499cfaddfea8b
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
Images          37        4         20.05GB   18.38GB (91%)
Containers      8         8         151.6kB   0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **18.38GB (91%)**.

### Container-Status

```
NAMES                                  STATUS                SIZE
80c1457a-55b2-4671-82a8-60063041558b   Up 5 hours            4.1kB (virtual 600MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 6 hours            4.1kB (virtual 600MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 6 hours            4.1kB (virtual 600MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 6 hours            4.1kB (virtual 600MB)
minecraft-web                          Up 5 days (healthy)   81.9kB (virtual 68.3MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 5 days             4.1kB (virtual 600MB)
festas-redis                           Up 8 days (healthy)   24.6kB (virtual 41.1MB)
fire-simulator                         Up 9 days             24.6kB (virtual 233MB)
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
Mem:            15Gi        11Gi       414Mi        34Mi       4.1Gi       4.1Gi
Swap:          2.0Gi        62Mi       1.9Gi
```

**RAM-Auslastung:** 73 % belegt.
**Swap:** 3 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
1751599 1751574 pteroda+ 3462616 21.6 6.3 java
1755088 1755062 pteroda+ 2544248 15.9 4.1 java
1753895 1753871 pteroda+ 2498936 15.6 5.1 java
1752771 1752746 pteroda+ 1568532  9.8 3.8 java
 713879  713855 pteroda+ 494020  3.0 3.5 java
1787334       1 mysql    155088  0.9 0.1 mariadbd
1230518       1 root     129824  0.8 0.0 systemd-journal
   1709    1624 fire     71756  0.4  0.0 next-server (v
   1221       1 root     71552  0.4  0.8 dockerd
    995       1 root     54612  0.3  0.6 containerd
1787270       1 root     43732  0.2  0.1 fail2ban-server
1787227       1 root     37528  0.2  0.0 php-fpm8.3
   1890       1 root     37188  0.2  3.1 wings
1230939       1 postgres 31228  0.1  0.0 postgres
    445       1 root     27448  0.1  0.0 multipathd
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
1751599 pteroda+  6.3 21.6 java
1753895 pteroda+  5.1 15.6 java
1755088 pteroda+  4.1 15.9 java
1752771 pteroda+  3.8  9.8 java
 713879 pteroda+  3.5  3.0 java
   1890 root      3.1  0.2 wings
1798958 root      1.4  0.0 bash
1798801 root      1.4  0.0 systemd
   1221 root      0.8  0.4 dockerd
    995 root      0.6  0.3 containerd
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

**Etablierte Verbindungen:** 81.

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

### fail2ban

```
Status
|- Number of jail:	1
`- Jail list:	sshd
```

### Fehlgeschlagene Logins (7 Tage)

Fehlgeschlagene Passwort-Logins: **15246**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **10** (davon sicherheitsrelevant: **0**).

### Aktualisierbare Pakete (Auszug)

```
Inst python3.12-venv [3.12.3-1ubuntu0.15] (3.12.3-1ubuntu0.16 Ubuntu:24.04/noble-updates [amd64]) []
Inst libpython3.12t64 [3.12.3-1ubuntu0.15] (3.12.3-1ubuntu0.16 Ubuntu:24.04/noble-updates [amd64]) []
Inst python3.12 [3.12.3-1ubuntu0.15] (3.12.3-1ubuntu0.16 Ubuntu:24.04/noble-updates [amd64]) []
Inst libpython3.12-stdlib [3.12.3-1ubuntu0.15] (3.12.3-1ubuntu0.16 Ubuntu:24.04/noble-updates [amd64]) []
Inst python3.12-minimal [3.12.3-1ubuntu0.15] (3.12.3-1ubuntu0.16 Ubuntu:24.04/noble-updates [amd64]) []
Inst libpython3.12-minimal [3.12.3-1ubuntu0.15] (3.12.3-1ubuntu0.16 Ubuntu:24.04/noble-updates [amd64])
Inst libproc2-0 [2:4.0.4-4ubuntu3.2] (2:4.0.4-4ubuntu3.3 Ubuntu:24.04/noble-updates [amd64])
Inst procps [2:4.0.4-4ubuntu3.2] (2:4.0.4-4ubuntu3.3 Ubuntu:24.04/noble-updates [amd64])
Inst byobu [6.11-0ubuntu1] (6.11-0ubuntu1.1 Ubuntu:24.04/noble-updates [all])
Inst containerd.io [2.3.3-1~ubuntu.24.04~noble] (2.3.4-1~ubuntu.24.04~noble Docker CE:noble [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **145** Fehler, **32151** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
     67 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     55 systemd[#]: Failed to start economy-export.service - Export the per-server richest-players board (CMI balance + LuckPerms rank) into economy.json.
      6 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
      6 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      5 sshd[#]: error: Protocol major versions differ: # vs. #
      4 sshd[#]: error: beginning MaxStartups throttling
      1 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
      1 sshd[#]: error: maximum authentication attempts exceeded for root from #.#.#.# port # ssh# [preauth]
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**76 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-08-30+00:00:01.5497842790 /var/backups/dpkg.arch.0

> Aufbewahrung/Off-Site siehe [docs/infrastructure/BACKUPS.md](../../docs/infrastructure/BACKUPS.md).

## 🧹 Aufräum-Kandidaten

Diese Posten lassen sich typischerweise gefahrlos freigeben. Im Modus
`maintain`/`full` erledigt der Agent die mit **(auto)** markierten Punkte.

| Kandidat | Umfang | Aktion |
|---|---|---|
| APT-Paketcache | 0B | `apt-get clean` **(auto)** |
| Journald-Logs | aktuell ? | `journalctl --vacuum-time=14d` **(auto)** |
| Docker (dangling/build-cache) | 18.38GB (91%) | `docker system prune -f` **(auto)** |
| Verwaiste Pakete/Kernel | variabel | `apt-get autoremove --purge` **(auto)** |
| Temp-Dateien | `/tmp` (0B) | `systemd-tmpfiles --clean` **(auto)** |

> **Nie automatisch gelöscht:** Welten, Spielerdaten, Datenbanken, Backups
> und Docker-**Volumes**. Diese werden nur analysiert.

## 🔧 Durchgeführte Wartungsaktionen

_Modus `analyze`: keine verändernden Aktionen._

---

<sub>Erzeugt am 2026-09-01 07:42:43 UTC · Modus `analyze` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
