# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🔴 **KRITISCH** · erstellt 2026-08-21 15:28:25 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 85 % belegt |
| RAM | 62 % belegt |
| Paket-Updates offen | 3 |
| Fehlgeschlagene Dienste | 0 |
| Modus dieses Laufs | `analyze` |

**Wichtigste Befunde:**

- 🔴 In 7 Tagen 15798 OOM-Killer-Ereignisse – RAM/Limits prüfen.
- 🟡 Festplatte zu 85 % voll (Schwelle 80 %).
- 🟡 1 Container im Status 'unhealthy'.
- 🟡 Container in Restart-/Fehler-Zustand – Logs prüfen.
- 🟡 Viele fehlgeschlagene Logins (13040) – Brute-Force? fail2ban prüfen.
- 🟡 3 sicherheitsrelevante Updates ausstehend.

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
| Load (1/5/15) | 0.61, 0.70, 0.90 |
| Uptime | up 2 days, 21 hours, 25 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  243G   46G  85% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/b7256c9d107eb199e052daf051b80b1c615242ebfe359cd266a342eaebbee615
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/b9efc8b20553cc5ac970b3502a24787fd9b87586d9b9cf678947e369aae0bac3
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/bd4c5f900620f5664bd7b7fa9b5d59aeae7d09b82112e1aff4962c07fe111733
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/c467aa55259504d6c883d3bf1c360f1cfd0d2d9f867b82c794260095c4bbb3a3
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/19ac78f012917d7a1d75cdb4f3d8339487926be65dc6ce6111b02e81d438f618
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/c20be6488472b3c9f2df239fc0a20135de292e55b378c05477eafbf67d6d0cf9
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/301f5c87a13fdf8ca1a772867abb6c6b8c79b283fa326022a234a52caeca23bd
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/7481c44a2ab1867b41dd4bf49bca63ce3eff640b513e4ba1a0095ff90008bf17
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/eab1977bb9b370d2c8d6c0a9adc6905a02bba8712437321075ba033ee3c0565e
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/ad5690a368eb982782c00d2e4ed4290b0c35101fa5405de42d66fc558413f74a
overlay        overlay  301G  243G   46G  85% /var/lib/docker/rootfs/overlayfs/4dca094ecd9292003286d1929d6116afee64da0d19210da504a7a07def82b2f9
```
**Root (`/`):** 243GB / 301GB belegt (85 %), frei: 46GB.
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
Images          34        11        20.88GB   12.88GB (61%)
Containers      14        11        76.1GB    8.958MB (0%)
Local Volumes   6         5         2.863GB   0B (0%)
Build Cache     235       0         31.26GB   30.94GB
```

Wiedergewinnbar laut Docker: **12.88GB (61%)**.

### Container-Status

```
NAMES                                  STATUS                    SIZE
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 3 hours                4.1kB (virtual 600MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 3 hours                4.1kB (virtual 600MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 3 hours                4.1kB (virtual 600MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 3 hours                4.1kB (virtual 600MB)
minecraft-web                          Up 4 hours (healthy)      81.9kB (virtual 68.2MB)
8f7bfcb0-17ec-465f-93c2-86a29695bfa6   Exited (130) 6 days ago   4.1kB (virtual 905MB)
fire-simulator                         Up 43 hours               4.1kB (virtual 233MB)
immocalc                               Up 2 minutes              76.1GB (virtual 78.1GB)
immocalc-db                            Up 43 hours (healthy)     20.5kB (virtual 284MB)
cosmic-survivor                        Up 43 hours               4.06MB (virtual 227MB)
80c1457a-55b2-4671-82a8-60063041558b   Exited (0) 4 months ago   4.1kB (virtual 615MB)
minecraft-console                      Up 43 hours (unhealthy)   848kB (virtual 229MB)
minecraft-console-redis                Up 43 hours (healthy)     4.1kB (virtual 43.4MB)
minecraft-server                       Exited (0) 8 months ago   8.95MB (virtual 861MB)
```

Container: **11/14** laufend, **1** ungesund.

**Auffällige Container (Restarting/Exited≠0):**
```
8f7bfcb0-17ec-465f-93c2-86a29695bfa6 Exited (130) 6 days ago
```

## 🪶 Pterodactyl / Wings

Wings-Dienst: **aktiv**.

### Server-Volumes (größte 15)

```
```

## ⛏️ Minecraft-Server (Welten & Logs)

| Server | Root | Welten | Logs | Plugins |
|---|---|---|---|---|
| Lobby | `/var/lib/pterodactyl/volumes/39a0762a-9e53-4b5b-8810-2bf63410800d` | 0B | 324KB | 0B |
| Proxy | `/var/lib/pterodactyl/volumes/b50e2f8c-440f-4910-8f00-29577afbc455` | 0B | 88KB | 0B |
| Survival | `/var/lib/pterodactyl/volumes/cfb531d8-3843-4bff-a8d5-b534aa58fc92` | 0B | 1.4MB | 0B |
| Skyblock | `/var/lib/pterodactyl/volumes/80c1457a-55b2-4671-82a8-60063041558b` | 0B | 34MB | 0B |
| Mining(rpg) | `/var/lib/pterodactyl/volumes/0af91553-d5ef-42fc-9ed1-97daaf3c4d70` | 0B | 608KB | 0B |

## 🧠 Arbeitsspeicher & Prozesse


### Speicher (free)

```
               total        used        free      shared  buff/cache   available
Mem:            15Gi       9.6Gi       1.8Gi        64Mi       4.4Gi       5.7Gi
Swap:          2.0Gi       197Mi       1.8Gi
```

**RAM-Auslastung:** 62 % belegt.
**Swap:** 9 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
2107993 2107964 pteroda+ 3502996 21.9 6.5 java
2107516 2107492 pteroda+ 2576840 16.1 5.2 java
2107225 2107158 pteroda+ 2132608 13.3 3.9 java
2107233 2107164 pteroda+ 410032  2.5 3.5 java
2226493 2226342 fire     90712  0.5  0.0 next-server (v
    400       1 root     88288  0.5  0.0 systemd-journal
2225899       1 root     86664  0.5  1.1 dockerd
2413316 2413291 fire     75824  0.4  0.9 next-server (v
1890539       1 www-data 75488  0.4  0.0 php
2225033       1 root     74924  0.4  0.8 containerd
   1195       1 mysql    69728  0.4  0.1 mariadbd
2226470 2226306 root     59476  0.3  0.0 node
1894013 1890542 www-data 54324  0.3  0.0 php-fpm8.3
1890549 1890542 www-data 54276  0.3  0.0 php-fpm8.3
2226993 2226482 root     53788  0.3  0.0 node
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
2414274 fire      7.9  0.0 sh
2107993 pteroda+  6.5 21.9 java
2107516 pteroda+  5.2 16.1 java
2107225 pteroda+  3.9 13.3 java
2107233 pteroda+  3.5  2.5 java
2226844 root      3.0  0.2 wings
2418364 root      1.4  0.0 systemd
2419968 root      1.2  0.0 bash
2225899 root      1.1  0.5 dockerd
2413316 fire      0.9  0.4 next-server (v
```

**OOM-Ereignisse (7 Tage):** 15798.

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
tcp 0.0.0.0:25599
tcp 0.0.0.0:25600
tcp 0.0.0.0:3002
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
tcp [::]:3002
tcp [::]:443
tcp [::]:80
tcp *:8080
udp 0.0.0.0:19132
udp 0.0.0.0:25565
udp 0.0.0.0:25566
udp 0.0.0.0:25567
udp 0.0.0.0:25568
udp 0.0.0.0:25599
```

**Etablierte Verbindungen:** 70.

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

Fehlgeschlagene Passwort-Logins: **13040**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Fri Aug 21 12:01 - 12:13  (00:12)
root     pts/0        91.192.12.105    Fri Aug 21 11:43 - 11:44  (00:01)
root     pts/0        91.192.12.105    Fri Aug 21 10:44 - 11:43  (00:58)
root     pts/0        91.192.12.105    Fri Aug 21 06:35 - 07:02  (00:26)
root     pts/0        91.192.12.105    Fri Aug 21 06:21 - 06:35  (00:14)
```

## 📦 Paket-Updates

Verfügbare Updates: **3** (davon sicherheitsrelevant: **3**).

⚠️ **Reboot erforderlich** (`reboot-required` vorhanden).
```
linux-image-6.8.0-138-generic
linux-base
```

### Aktualisierbare Pakete (Auszug)

```
Inst libpq5 [16.14-0ubuntu0.24.04.1] (16.15-0ubuntu0.24.04.1 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst postgresql-client-16 [16.14-0ubuntu0.24.04.1] (16.15-0ubuntu0.24.04.1 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst postgresql-16 [16.14-0ubuntu0.24.04.1] (16.15-0ubuntu0.24.04.1 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **7632** Fehler, **287339** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
    370 kernel: Memory cgroup out of memory: Killed process # (next-server (v) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    306 kernel: Memory cgroup out of memory: Killed process # (app_#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    153 kernel: Memory cgroup out of memory: Killed process # (jbd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    140 kernel: Memory cgroup out of memory: Killed process # (NetworkManager#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    137 kernel: Memory cgroup out of memory: Killed process # (kthreadd) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    133 kernel: Memory cgroup out of memory: Killed process # (containerd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    132 kernel: Memory cgroup out of memory: Killed process # (postgres) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    124 kernel: Memory cgroup out of memory: Killed process # (devfreq_wq#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    121 kernel: Memory cgroup out of memory: Killed process # (blkmapd) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    117 kernel: Memory cgroup out of memory: Killed process # (rpc.statd) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**87 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (?); neueste Datei: 2026-08-21+00:00:01.3470531380 /var/backups/dpkg.arch.0

> Aufbewahrung/Off-Site siehe [docs/infrastructure/BACKUPS.md](../../docs/infrastructure/BACKUPS.md).

## 🧹 Aufräum-Kandidaten

Diese Posten lassen sich typischerweise gefahrlos freigeben. Im Modus
`maintain`/`full` erledigt der Agent die mit **(auto)** markierten Punkte.

| Kandidat | Umfang | Aktion |
|---|---|---|
| APT-Paketcache | 60KB | `apt-get clean` **(auto)** |
| Journald-Logs | aktuell ? | `journalctl --vacuum-time=14d` **(auto)** |
| Docker (dangling/build-cache) | 12.88GB (61%) | `docker system prune -f` **(auto)** |
| Verwaiste Pakete/Kernel | variabel | `apt-get autoremove --purge` **(auto)** |
| Temp-Dateien | `/tmp` () | `systemd-tmpfiles --clean` **(auto)** |

> **Nie automatisch gelöscht:** Welten, Spielerdaten, Datenbanken, Backups
> und Docker-**Volumes**. Diese werden nur analysiert.

## 🔧 Durchgeführte Wartungsaktionen

_Modus `analyze`: keine verändernden Aktionen._

---

<sub>Erzeugt am 2026-08-21 15:28:25 UTC · Modus `analyze` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
