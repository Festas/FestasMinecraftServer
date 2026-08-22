# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🔴 **KRITISCH** · erstellt 2026-08-22 10:04:15 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 87 % belegt |
| RAM | 78 % belegt |
| Paket-Updates offen | 6 |
| Fehlgeschlagene Dienste | 0 |
| Modus dieses Laufs | `analyze` |
| Trend | Seit letztem Lauf: -352MB auf `/`. |

**Wichtigste Befunde:**

- 🔴 In 7 Tagen 21267 OOM-Killer-Ereignisse – RAM/Limits prüfen.
- 🟡 Festplatte zu 87 % voll (Schwelle 80 %).
- 🟡 Container in Restart-/Fehler-Zustand – Logs prüfen.
- 🟡 Viele fehlgeschlagene Logins (13516) – Brute-Force? fail2ban prüfen.
- 🟡 6 sicherheitsrelevante Updates ausstehend.

**Empfehlungen (Optimierungspotenzial):**

- Speicher freigeben (Docker-Prune, Journald, alte Backups/Logs).
- Alte Kernel/Pakete entfernen (`apt-get -y autoremove --purge`).
- Paket-Updates einspielen (Modus `maintain`/`full`).
- Host-Reboot einplanen (Kernel/Bibliotheks-Updates aktivieren).


## 🖥️ System-Übersicht

| Feld | Wert |
|---|---|
| Host | `festas-builds` |
| OS | Ubuntu 24.04.4 LTS |
| Kernel | Linux 6.8.0-137-generic |
| Virtualisierung | kvm |
| CPU-Kerne | 8 |
| Load (1/5/15) | 0.65, 0.85, 0.81 |
| Uptime | up 3 days, 16 hours, 1 minute |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  249G   40G  87% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/bd4c5f900620f5664bd7b7fa9b5d59aeae7d09b82112e1aff4962c07fe111733
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/c467aa55259504d6c883d3bf1c360f1cfd0d2d9f867b82c794260095c4bbb3a3
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/19ac78f012917d7a1d75cdb4f3d8339487926be65dc6ce6111b02e81d438f618
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/c20be6488472b3c9f2df239fc0a20135de292e55b378c05477eafbf67d6d0cf9
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/166b674061967e14f98f9a1e37d021b581a75d20121915fa9e90cd7596683d1f
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/33e4cf5f4709a37982f6f18593dc4252d835f13431a120072b533343fcc93505
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/e89ba87a3c7c9758e655f047fb97bd15e296cc274550d2f3b89f957a6b3cd758
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/9d747d61a0d356cc3dca0ce0100b14265b2d1d0212115e2ef12d18daa4780a44
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/17acb68570be1f03967bc1e5cccc9b56ef761add2c8760234754fbfec52a2ab2
overlay        overlay  301G  249G   40G  87% /var/lib/docker/rootfs/overlayfs/4dca094ecd9292003286d1929d6116afee64da0d19210da504a7a07def82b2f9
```
**Root (`/`):** 249GB / 301GB belegt (87 %), frei: 40GB.
**Inodes (`/`):** 15 % belegt.

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

**Alte Kernel installiert:** 1 (aktiv: `6.8.0-137-generic`) → `apt-get autoremove` gibt Platz frei.

## 🐳 Docker & Container


### Speicherverbrauch (docker system df)

```
TYPE            TOTAL     ACTIVE    SIZE      RECLAIMABLE
Images          34        8         20.88GB   14.13GB (67%)
Containers      12        10        82.07GB   8.954MB (0%)
Local Volumes   6         3         2.863GB   7.511MB (0%)
Build Cache     235       0         31.26GB   30.94GB
```

Wiedergewinnbar laut Docker: **14.13GB (67%)**.

### Container-Status

```
NAMES                                  STATUS                    SIZE
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 34 minutes             4.1kB (virtual 600MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 34 minutes             4.1kB (virtual 600MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 34 minutes             4.1kB (virtual 600MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 34 minutes             4.1kB (virtual 600MB)
80c1457a-55b2-4671-82a8-60063041558b   Up 34 minutes             4.1kB (virtual 600MB)
minecraft-web                          Up 23 hours (healthy)     81.9kB (virtual 68.2MB)
8f7bfcb0-17ec-465f-93c2-86a29695bfa6   Exited (130) 7 days ago   4.1kB (virtual 905MB)
fire-simulator                         Up 2 days                 4.1kB (virtual 233MB)
immocalc                               Up 9 minutes              82.1GB (virtual 84.1GB)
immocalc-db                            Up 2 days (healthy)       20.5kB (virtual 284MB)
cosmic-survivor                        Up 2 days                 4.06MB (virtual 227MB)
minecraft-server                       Exited (0) 8 months ago   8.95MB (virtual 861MB)
```

Container: **10/12** laufend, **0** ungesund.

**Auffällige Container (Restarting/Exited≠0):**
```
8f7bfcb0-17ec-465f-93c2-86a29695bfa6 Exited (130) 7 days ago
```

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
Mem:            15Gi        11Gi       315Mi        54Mi       3.4Gi       3.3Gi
Swap:          2.0Gi       404Mi       1.6Gi
```

**RAM-Auslastung:** 78 % belegt.
**Swap:** 19 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
4108046 4108020 pteroda+ 3375080 21.1 12.3 java
4107162 4107070 pteroda+ 3002564 18.7 7.6 java
4107433 4107375 pteroda+ 2848216 17.8 8.6 java
4107171 4107098 pteroda+ 1737216 10.8 6.8 java
4107170 4107111 pteroda+ 385772  2.4 4.1 java
    400       1 root     83624  0.5  0.0 systemd-journal
2226493 2226342 fire     80272  0.5  0.0 next-server (v
2225899       1 root     73088  0.4  1.1 dockerd
4120924 4120899 fire     69060  0.4  0.2 next-server (v
   1195       1 mysql    58640  0.3  0.1 mariadbd
1890539       1 www-data 49896  0.3  0.0 php
2675547 1890542 www-data 48280  0.3  0.0 php-fpm8.3
2226993 2226482 root     45796  0.2  0.0 node
2225033       1 root     45128  0.2  0.8 containerd
    935       1 root     38924  0.2  0.1 fail2ban-server
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
4108046 pteroda+ 12.3 21.1 java
4107433 pteroda+  8.6 17.8 java
4107162 pteroda+  7.6 18.7 java
4107171 pteroda+  6.8 10.8 java
4107170 pteroda+  4.1  2.4 java
2226844 root      3.0  0.2 wings
4126894 root      2.5  0.0 bash
2225899 root      1.1  0.4 dockerd
4126695 root      1.1  0.0 systemd
2225033 root      0.8  0.2 containerd
```

**OOM-Ereignisse (7 Tage):** 21267.

## 🩺 Dienste & Health


### Fehlgeschlagene Units

Keine fehlgeschlagenen Units. ✅

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
tcp 127.0.0.1:3100
tcp 127.0.0.1:3200
tcp 127.0.0.1:5432
tcp 127.0.0.1:8200
tcp 127.0.0.1:8201
tcp 127.0.0.53%lo:53
tcp 127.0.0.54:53
tcp [::1]:5432
tcp [::1]:6379
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

**Etablierte Verbindungen:** 48.

### Konnektivität & DNS

Öffentliche IPv4: `128.140.99.121` · DNS-Auflösung: ja.

## 🔐 Sicherheit


### Firewall

```
Status: active

To                         Action      From
--                         ------      ----
22/tcp                     ALLOW       Anywhere                  
80/tcp                     ALLOW       Anywhere                  
443/tcp                    ALLOW       Anywhere                  
8080/tcp                   ALLOW       Anywhere                  
8443/tcp                   ALLOW       Anywhere                   # HTTPS
25565/tcp                  ALLOW       Anywhere                   # Minecraft Java
25565/udp                  ALLOW       Anywhere                   # Minecraft Java
19132/udp                  ALLOW       Anywhere                  
3001                       ALLOW       Anywhere                  
6379                       ALLOW       Anywhere                  
4567/tcp                   ALLOW       Anywhere                  
8100/tcp                   ALLOW       Anywhere                  
2022/tcp                   ALLOW       Anywhere                  
25565                      ALLOW       Anywhere                  
25566                      DENY        Anywhere                  
25567                      DENY        Anywhere                  
25568                      DENY        Anywhere                  
8100                       ALLOW       Anywhere                  
8101                       ALLOW       Anywhere                  
8804                       DENY        Anywhere                  
3306                       ALLOW       172.25.0.0/16             
25565:25600/tcp            ALLOW       Anywhere                  
25565:25600/udp            ALLOW       Anywhere                  
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
8804/tcp                   DENY        Anywhere                  
22/tcp (v6)                ALLOW       Anywhere (v6)             
80/tcp (v6)                ALLOW       Anywhere (v6)             
443/tcp (v6)               ALLOW       Anywhere (v6)             
8080/tcp (v6)              ALLOW       Anywhere (v6)             
8443/tcp (v6)              ALLOW       Anywhere (v6)              # HTTPS
25565/tcp (v6)             ALLOW       Anywhere (v6)              # Minecraft Java
25565/udp (v6)             ALLOW       Anywhere (v6)              # Minecraft Java
19132/udp (v6)             ALLOW       Anywhere (v6)             
3001 (v6)                  ALLOW       Anywhere (v6)             
6379 (v6)                  ALLOW       Anywhere (v6)             
4567/tcp (v6)              ALLOW       Anywhere (v6)             
8100/tcp (v6)              ALLOW       Anywhere (v6)             
2022/tcp (v6)              ALLOW       Anywhere (v6)             
25565 (v6)                 ALLOW       Anywhere (v6)             
25566 (v6)                 DENY        Anywhere (v6)             
25567 (v6)                 DENY        Anywhere (v6)             
25568 (v6)                 DENY        Anywhere (v6)             
8100 (v6)                  ALLOW       Anywhere (v6)             
8101 (v6)                  ALLOW       Anywhere (v6)             
8804 (v6)                  DENY        Anywhere (v6)             
25565:25600/tcp (v6)       ALLOW       Anywhere (v6)             
25565:25600/udp (v6)       ALLOW       Anywhere (v6)             
25599/tcp (v6)             ALLOW       Anywhere (v6)             
25600/tcp (v6)             ALLOW       Anywhere (v6)             
Nginx Full (v6)            ALLOW       Anywhere (v6)             
27015/udp (v6)             ALLOW       Anywhere (v6)             
27016/udp (v6)             ALLOW       Anywhere (v6)             
25570 (v6)                 ALLOW       Anywhere (v6)             
8201/tcp (v6)              ALLOW       Anywhere (v6)             
8085/tcp (v6)              ALLOW       Anywhere (v6)             
8804/tcp (v6)              DENY        Anywhere (v6)             

```

### fail2ban

```
Status
|- Number of jail:	1
`- Jail list:	sshd
```

### Fehlgeschlagene Logins (7 Tage)

Fehlgeschlagene Passwort-Logins: **13516**.

### Letzte Anmeldungen

```
root     pts/0        194.182.200.101  Sat Aug 22 10:00 - 10:03  (00:03)
root     pts/0        91.192.12.105    Fri Aug 21 12:01 - 12:13  (00:12)
root     pts/0        91.192.12.105    Fri Aug 21 11:43 - 11:44  (00:01)
root     pts/0        91.192.12.105    Fri Aug 21 10:44 - 11:43  (00:58)
root     pts/0        91.192.12.105    Fri Aug 21 06:35 - 07:02  (00:26)
```

## 📦 Paket-Updates

Verfügbare Updates: **6** (davon sicherheitsrelevant: **6**).

⚠️ **Reboot erforderlich** (`reboot-required` vorhanden).
```
linux-image-6.8.0-138-generic
linux-base
```

### Aktualisierbare Pakete (Auszug)

```
Inst vim [2:9.1.0016-1ubuntu7.18] (2:9.1.0016-1ubuntu7.19 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64]) []
Inst vim-common [2:9.1.0016-1ubuntu7.18] (2:9.1.0016-1ubuntu7.19 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all]) [vim-tiny:amd64 ]
Inst vim-tiny [2:9.1.0016-1ubuntu7.18] (2:9.1.0016-1ubuntu7.19 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64]) []
Inst vim-runtime [2:9.1.0016-1ubuntu7.18] (2:9.1.0016-1ubuntu7.19 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
Inst xxd [2:9.1.0016-1ubuntu7.18] (2:9.1.0016-1ubuntu7.19 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst wget [1.21.4-1ubuntu4.4] (1.21.4-1ubuntu4.5 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **9145** Fehler, **338341** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
    445 kernel: Memory cgroup out of memory: Killed process # (next-server (v) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    390 kernel: Memory cgroup out of memory: Killed process # (app_#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    166 kernel: Memory cgroup out of memory: Killed process # (jbd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    160 kernel: Memory cgroup out of memory: Killed process # (atd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    145 kernel: Memory cgroup out of memory: Killed process # (postgres) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    141 kernel: Memory cgroup out of memory: Killed process # (NetworkManager#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    133 kernel: Memory cgroup out of memory: Killed process # (containerd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    130 kernel: Memory cgroup out of memory: Killed process # (kthreadd) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    128 kernel: Memory cgroup out of memory: Killed process # (ntpd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    127 kernel: Memory cgroup out of memory: Killed process # (khelper#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**86 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-08-22+00:00:01.9105850340 /var/backups/dpkg.arch.0

> Aufbewahrung/Off-Site siehe [docs/infrastructure/BACKUPS.md](../../docs/infrastructure/BACKUPS.md).

## 🧹 Aufräum-Kandidaten

Diese Posten lassen sich typischerweise gefahrlos freigeben. Im Modus
`maintain`/`full` erledigt der Agent die mit **(auto)** markierten Punkte.

| Kandidat | Umfang | Aktion |
|---|---|---|
| APT-Paketcache | 0B | `apt-get clean` **(auto)** |
| Journald-Logs | aktuell ? | `journalctl --vacuum-time=14d` **(auto)** |
| Docker (dangling/build-cache) | 14.13GB (67%) | `docker system prune -f` **(auto)** |
| Verwaiste Pakete/Kernel | variabel | `apt-get autoremove --purge` **(auto)** |
| Temp-Dateien | `/tmp` (0B) | `systemd-tmpfiles --clean` **(auto)** |

> **Nie automatisch gelöscht:** Welten, Spielerdaten, Datenbanken, Backups
> und Docker-**Volumes**. Diese werden nur analysiert.

## 🔧 Durchgeführte Wartungsaktionen

_Modus `analyze`: keine verändernden Aktionen._

---

<sub>Erzeugt am 2026-08-22 10:04:15 UTC · Modus `analyze` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
