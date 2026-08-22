# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-08-22 12:39:14 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 50 % belegt |
| RAM | 76 % belegt |
| Paket-Updates offen | 0 |
| Fehlgeschlagene Dienste | 0 |
| Modus dieses Laufs | `analyze` |
| Trend | Seit letztem Lauf: -4.0MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 Viele fehlgeschlagene Logins (13265) – Brute-Force? fail2ban prüfen.

**Empfehlungen (Optimierungspotenzial):**

- Alte Kernel/Pakete entfernen (`apt-get -y autoremove --purge`).


## 🖥️ System-Übersicht

| Feld | Wert |
|---|---|
| Host | `festas-builds` |
| OS | Ubuntu 24.04.4 LTS |
| Kernel | Linux 6.8.0-138-generic |
| Virtualisierung | kvm |
| CPU-Kerne | 8 |
| Load (1/5/15) | 0.45, 0.74, 0.61 |
| Uptime | up 28 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  142G  146G  50% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  142G  146G  50% /var/lib/docker/rootfs/overlayfs/c467aa55259504d6c883d3bf1c360f1cfd0d2d9f867b82c794260095c4bbb3a3
overlay        overlay  301G  142G  146G  50% /var/lib/docker/rootfs/overlayfs/c20be6488472b3c9f2df239fc0a20135de292e55b378c05477eafbf67d6d0cf9
overlay        overlay  301G  142G  146G  50% /var/lib/docker/rootfs/overlayfs/c8c2dbab71cac742e25513e2f0a785ccd764015d2bca7a11c9b52d7d00d035f0
overlay        overlay  301G  142G  146G  50% /var/lib/docker/rootfs/overlayfs/88a638899369dd792330a78913354c9585b5e05c02ef9f866257ae4aa60901fd
overlay        overlay  301G  142G  146G  50% /var/lib/docker/rootfs/overlayfs/91be5754dd452256928cffd9366207873f2212ff6311257d27272bd9d61dbd18
overlay        overlay  301G  142G  146G  50% /var/lib/docker/rootfs/overlayfs/c25d63b080cdbe3a7c4e174929917a168d71a9ee4139dd4ed866c10a09d2f63c
overlay        overlay  301G  142G  146G  50% /var/lib/docker/rootfs/overlayfs/e2ff113418193910ad89b35bad38bae1ff5e7920be6cabddcb0b4b40c50aa966
```
**Root (`/`):** 142GB / 301GB belegt (50 %), frei: 146GB.
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
Images          33        3         19.99GB   18.38GB (91%)
Containers      7         7         106.5kB   0B (0%)
Local Volumes   6         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **18.38GB (91%)**.

### Container-Status

```
NAMES                                  STATUS                    SIZE
80c1457a-55b2-4671-82a8-60063041558b   Up 4 minutes              4.1kB (virtual 600MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 27 minutes             4.1kB (virtual 600MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 27 minutes             4.1kB (virtual 600MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 27 minutes             4.1kB (virtual 600MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 27 minutes             4.1kB (virtual 600MB)
minecraft-web                          Up 27 minutes (healthy)   81.9kB (virtual 68.2MB)
fire-simulator                         Up 27 minutes             4.1kB (virtual 233MB)
```

Container: **7/7** laufend, **0** ungesund.

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
Mem:            15Gi        11Gi       235Mi        57Mi       3.8Gi       3.6Gi
Swap:          2.0Gi       768Ki       2.0Gi
```

**RAM-Auslastung:** 76 % belegt.
**Swap:** 0 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
   3287    3260 pteroda+ 3033432 18.9 14.1 java
   2333    2308 pteroda+ 2937960 18.3 9.5 java
   6539    6514 pteroda+ 2662052 16.6 35.5 java
   2045    1992 pteroda+ 2119532 13.2 7.7 java
   2038    1988 pteroda+ 399484  2.4 4.2 java
   1154       1 mysql    133632  0.8 0.2 mariadbd
   1221       1 root     118688  0.7 1.4 dockerd
   1709    1624 fire     83052  0.5  0.0 next-server (v
    926       1 root     72752  0.4  0.1 fail2ban-server
    995       1 root     72288  0.4  1.0 containerd
   1188       1 www-data 70952  0.4  0.1 php
   1165     934 www-data 54732  0.3  0.0 php-fpm8.3
   1166     934 www-data 53488  0.3  0.0 php-fpm8.3
   5410     934 www-data 53188  0.3  0.0 php-fpm8.3
   1890       1 root     42280  0.2  4.1 wings
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
   6539 pteroda+ 35.5 16.6 java
   3287 pteroda+ 14.1 18.9 java
   2333 pteroda+  9.5 18.3 java
   2045 pteroda+  7.7 13.2 java
   2038 pteroda+  4.2  2.4 java
   1890 root      4.1  0.2 wings
   9631 root      1.8  0.0 bash
   1221 root      1.4  0.7 dockerd
    995 root      1.0  0.4 containerd
   9586 root      0.5  0.0 sshd
```

**OOM-Ereignisse (7 Tage):** 0.

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
tcp 127.0.0.1:3200
tcp 127.0.0.1:5432
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
udp 0.0.0.0:25600
udp 0.0.0.0:8085
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

Fehlgeschlagene Passwort-Logins: **13265**.

### Letzte Anmeldungen

```
root     pts/0        194.182.200.101  Sat Aug 22 12:38   still logged in
root     pts/0        194.182.200.101  Sat Aug 22 12:37 - 12:38  (00:01)
root     pts/0        194.182.200.101  Sat Aug 22 12:30 - 12:33  (00:03)
reboot   system boot  6.8.0-138-generic Sat Aug 22 12:11   still running
root     pts/1        109.42.49.137    Sat Aug 22 12:07 - down   (00:03)
```

## 📦 Paket-Updates

Verfügbare Updates: **0** (davon sicherheitsrelevant: **0**).

## 📜 Log-Analyse (7 Tage)

Journald: **9226** Fehler, **341135** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
    451 kernel: Memory cgroup out of memory: Killed process # (next-server (v) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    401 kernel: Memory cgroup out of memory: Killed process # (app_#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    172 kernel: Memory cgroup out of memory: Killed process # (jbd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    160 kernel: Memory cgroup out of memory: Killed process # (atd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    159 kernel: Memory cgroup out of memory: Killed process # (ntpd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    145 kernel: Memory cgroup out of memory: Killed process # (postgres) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    141 kernel: Memory cgroup out of memory: Killed process # (NetworkManager#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    133 kernel: Memory cgroup out of memory: Killed process # (kthreadd) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    133 kernel: Memory cgroup out of memory: Killed process # (containerd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
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
| Docker (dangling/build-cache) | 18.38GB (91%) | `docker system prune -f` **(auto)** |
| Verwaiste Pakete/Kernel | variabel | `apt-get autoremove --purge` **(auto)** |
| Temp-Dateien | `/tmp` (0B) | `systemd-tmpfiles --clean` **(auto)** |

> **Nie automatisch gelöscht:** Welten, Spielerdaten, Datenbanken, Backups
> und Docker-**Volumes**. Diese werden nur analysiert.

## 🔧 Durchgeführte Wartungsaktionen

_Modus `analyze`: keine verändernden Aktionen._

---

<sub>Erzeugt am 2026-08-22 12:39:14 UTC · Modus `analyze` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
