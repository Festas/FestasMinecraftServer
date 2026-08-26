# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-08-26 02:28:48 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 44 % belegt |
| RAM | 79 % belegt |
| Paket-Updates offen | 8 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: +58MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Viele fehlgeschlagene Logins (14391) – Brute-Force? fail2ban prüfen.
- 🟡 3 sicherheitsrelevante Updates ausstehend.

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
| Load (1/5/15) | 0.44, 0.40, 0.41 |
| Uptime | up 3 days, 14 hours, 17 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  125G  164G  44% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/c467aa55259504d6c883d3bf1c360f1cfd0d2d9f867b82c794260095c4bbb3a3
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/53c375919866951d4fd61e5b8b7078809c693c64e1dc8b2f5d7f28ac9d3cba01
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/8215c7ab828eaf228b3eed78515cf64b3503706045331a026f431adbf804e81e
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/b82e77ef7c11ad35072f02ce4fb86f8502284868535178b8ecccd8df88c57807
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/c067430a6bcd7d4a99f1a77fd9358d8af8a6138d6abbbe3ca82f3f891ab28d03
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/7e881219f9332ccae6d23f43cff3965476404df212fdd45c0b93d95a2f08eb20
overlay        overlay  301G  125G  164G  44% /var/lib/docker/rootfs/overlayfs/8b59b7bffca47976729ee4bf8362ca4b7a1aa535a617a485de0b6abdb5a53f6e
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
Images          35        4         20.04GB   18.38GB (91%)
Containers      8         8         131.1kB   0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **18.38GB (91%)**.

### Container-Status

```
NAMES                                  STATUS                  SIZE
80c1457a-55b2-4671-82a8-60063041558b   Up 13 minutes           4.1kB (virtual 600MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 18 minutes           4.1kB (virtual 600MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 23 minutes           4.1kB (virtual 600MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 28 minutes           4.1kB (virtual 600MB)
minecraft-web                          Up 12 hours (healthy)   81.9kB (virtual 68.2MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 15 hours             4.1kB (virtual 600MB)
festas-redis                           Up 45 hours (healthy)   24.6kB (virtual 41.1MB)
fire-simulator                         Up 3 days               4.1kB (virtual 233MB)
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
Mem:            15Gi        12Gi       173Mi        46Mi       3.3Gi       3.1Gi
Swap:          2.0Gi       275Mi       1.7Gi
```

**RAM-Auslastung:** 79 % belegt.
**Swap:** 13 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
 602327  602302 pteroda+ 3876168 24.2 14.2 java
 604593  604566 pteroda+ 2958008 18.5 12.7 java
 605738  605715 pteroda+ 2489520 15.5 14.3 java
 603490  603464 pteroda+ 1987948 12.4 8.5 java
 492708  492672 pteroda+ 372364  2.3 3.4 java
    390       1 root     109268  0.6 0.0 systemd-journal
   1154       1 mysql    95648  0.5  0.2 mariadbd
   1221       1 root     73148  0.4  0.8 dockerd
    926       1 root     51388  0.3  0.1 fail2ban-server
 465795     934 www-data 49076  0.3  0.0 php-fpm8.3
 474624     934 www-data 48908  0.3  0.0 php-fpm8.3
 487933     934 www-data 48608  0.3  0.0 php-fpm8.3
    995       1 root     46100  0.2  0.5 containerd
   1709    1624 fire     36028  0.2  0.0 next-server (v
   1890       1 root     31648  0.1  3.1 wings
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
 605738 pteroda+ 14.3 15.5 java
 602327 pteroda+ 14.2 24.2 java
 604593 pteroda+ 12.7 18.5 java
 603490 pteroda+  8.5 12.4 java
 492708 pteroda+  3.4  2.3 java
   1890 root      3.1  0.1 wings
 607828 root      1.8  0.0 sshd
 607689 root      1.5  0.0 systemd
 607883 root      1.2  0.0 bash
   1221 root      0.8  0.4 dockerd
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

**Etablierte Verbindungen:** 84.

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

Fehlgeschlagene Passwort-Logins: **14391**.

### Letzte Anmeldungen

```
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
root     pts/0        213.244.61.249   Sun Aug 23 16:20 - 16:22  (00:01)
```

## 📦 Paket-Updates

Verfügbare Updates: **8** (davon sicherheitsrelevant: **3**).

### Aktualisierbare Pakete (Auszug)

```
Inst console-setup-linux [1.226ubuntu1] (1.226ubuntu1.1 Ubuntu:24.04/noble-updates [all]) []
Inst console-setup [1.226ubuntu1] (1.226ubuntu1.1 Ubuntu:24.04/noble-updates [all]) []
Inst keyboard-configuration [1.226ubuntu1] (1.226ubuntu1.1 Ubuntu:24.04/noble-updates [all])
Inst open-vm-tools [2:13.0.0-2~ubuntu0.24.04.1] (2:13.0.10-0ubuntu0.24.04.1 Ubuntu:24.04/noble-updates [amd64])
Inst curl [8.5.0-2ubuntu10.12] (8.5.0-2ubuntu10.13 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64]) []
Inst libcurl4t64 [8.5.0-2ubuntu10.12] (8.5.0-2ubuntu10.13 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst libcurl3t64-gnutls [8.5.0-2ubuntu10.12] (8.5.0-2ubuntu10.13 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst snapd [2.76+ubuntu24.04.1] (2.76.3+ubuntu24.04 Ubuntu:24.04/noble-updates [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **6944** Fehler, **262978** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
    328 kernel: Memory cgroup out of memory: Killed process # (next-server (v) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    291 kernel: Memory cgroup out of memory: Killed process # (app_#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    160 kernel: Memory cgroup out of memory: Killed process # (atd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    159 kernel: Memory cgroup out of memory: Killed process # (ntpd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    138 kernel: Memory cgroup out of memory: Killed process # (postgres) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    133 kernel: Memory cgroup out of memory: Killed process # (kthreadd) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    133 kernel: Memory cgroup out of memory: Killed process # (containerd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    120 kernel: Memory cgroup out of memory: Killed process # (blkmapd) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    118 kernel: Memory cgroup out of memory: Killed process # (multipathd#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
    116 kernel: Memory cgroup out of memory: Killed process # (NetworkManager#) total-vm:#kB, anon-rss:#kB, file-rss:#kB, shmem-rss:#kB, UID:# pgtables:#kB oom_score_adj:#
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**83 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-08-24+00:00:01.4285704520 /var/backups/dpkg.arch.0

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


**Freigegebener Speicher in diesem Lauf:** 3.0GB.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-08-26 02:28:48 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
