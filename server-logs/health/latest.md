# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-09-09 08:40:23 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 43 % belegt |
| RAM | 78 % belegt |
| Paket-Updates offen | 0 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `analyze` |
| Trend | Seit letztem Lauf: -1.1GB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (16264) – Brute-Force? fail2ban prüfen.

**Empfehlungen (Optimierungspotenzial):**

- Alte Kernel/Pakete entfernen (`apt-get -y autoremove --purge`).
- Fehlgeschlagene Dienste untersuchen (`systemctl --failed`).
- Port 8804 (Plan Analytics) in UFW schließen oder nur nach `127.0.0.1` veröffentlichen.
- Port 8102 (BlueMap Survival) nur nach `127.0.0.1` veröffentlichen; falls bewusst breiter gebunden, Host-Firewall/UFW explizit prüfen.
- Port 8103 (BlueMap Mining) nur nach `127.0.0.1` veröffentlichen; falls bewusst breiter gebunden, Host-Firewall/UFW explizit prüfen.
- Host-Reboot einplanen (Kernel/Bibliotheks-Updates aktivieren).


## 🖥️ System-Übersicht

| Feld | Wert |
|---|---|
| Host | `festas-builds` |
| OS | Ubuntu 24.04.4 LTS |
| Kernel | Linux 6.8.0-138-generic |
| Virtualisierung | kvm |
| CPU-Kerne | 8 |
| Load (1/5/15) | 0.40, 0.26, 0.24 |
| Uptime | up 2 weeks, 3 days, 20 hours, 29 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  123G  166G  43% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/2107c31dc55250ba0acc7e22dcdb47739734fc0f74dfaf955da41f67514c1997
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/c467aa55259504d6c883d3bf1c360f1cfd0d2d9f867b82c794260095c4bbb3a3
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/14ee66121e40a461ec442d68b5bcf0e01d847e3dcf7ee73bf2f6ecf2797866df
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/266800319baee71e42f478934e5e8c33d9f34f2fc419132986b7d3304d73f57a
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/e01780fb1b2e320ae729aabb8954512c9afb0e9cd38f5b3cadf9e69bcfb12fdd
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/44c4e668e093459a88379ca26dfa37cc9f7559f5f2b3a29d3d44298ebe1dbb17
overlay        overlay  301G  123G  166G  43% /var/lib/docker/rootfs/overlayfs/9ae7ddb277731ac60c34521665149014fc34891b25b545bdc97662f050d9ff39
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
Images          37        4         20.2GB    18.54GB (91%)
Containers      8         8         151.6kB   0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **18.54GB (91%)**.

### Container-Status

```
NAMES                                  STATUS                 SIZE
80c1457a-55b2-4671-82a8-60063041558b   Up 6 hours             4.1kB (virtual 598MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 7 hours             4.1kB (virtual 598MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 7 hours             4.1kB (virtual 598MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 7 hours             4.1kB (virtual 598MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 7 hours             4.1kB (virtual 598MB)
minecraft-web                          Up 7 hours (healthy)   81.9kB (virtual 68.3MB)
festas-redis                           Up 7 hours (healthy)   24.6kB (virtual 41.1MB)
fire-simulator                         Up 7 hours             24.6kB (virtual 233MB)
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
Mem:            15Gi        11Gi       169Mi        56Mi       3.5Gi       3.3Gi
Swap:          2.0Gi        15Mi       2.0Gi
```

**RAM-Auslastung:** 78 % belegt.
**Swap:** 0 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
3260630 3260605 pteroda+ 3353740 20.9 6.1 java
3260014 3259989 pteroda+ 2962436 18.5 4.9 java
3262909 3262879 pteroda+ 2483128 15.5 4.0 java
3259518 3259449 pteroda+ 2069544 12.9 3.6 java
3259524 3259451 pteroda+ 387200  2.4 3.9 java
2349003       1 mysql    320400  2.0 0.1 mariadbd
2348827       1 root     155528  0.9 0.0 systemd-journal
3258744       1 root     123316  0.7 0.9 dockerd
3259156 3259076 fire     93252  0.5  0.0 next-server (v
3257925       1 root     75764  0.4  0.7 containerd
2348905       1 root     69368  0.4  0.1 fail2ban-server
2674383 2349514 www-data 47720  0.2  0.0 php-fpm8.3
3098435 2349514 www-data 46752  0.2  0.0 php-fpm8.3
2921159 2349514 www-data 45328  0.2  0.0 php-fpm8.3
3259338       1 root     40832  0.2  3.0 wings
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
3260630 pteroda+  6.1 20.9 java
3260014 pteroda+  4.9 18.5 java
3262909 pteroda+  4.0 15.5 java
3259524 pteroda+  3.9  2.4 java
3259518 pteroda+  3.6 12.9 java
3259338 root      3.0  0.2 wings
3312000 root      1.7  0.0 bash
3311812 root      1.4  0.0 systemd
3258744 root      0.9  0.7 dockerd
3257925 root      0.7  0.4 containerd
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

Fehlgeschlagene Passwort-Logins: **16264**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **0** (davon sicherheitsrelevant: **0**).

⚠️ **Reboot erforderlich** (`reboot-required` vorhanden).
```
linux-image-6.8.0-139-generic
linux-base
```

## 📜 Log-Analyse (7 Tage)

Journald: **103** Fehler, **32308** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
     79 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     12 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      4 sshd[#]: error: Protocol major versions differ: # vs. #
      3 sshd[#]: error: beginning MaxStartups throttling
      2 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
      2 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
      1 sshd[#]: error: send_error: write: Connection reset by peer
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Nov 17 04:51:59 2026 GMT (**68 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-09-07+00:00:01.3960925440 /var/backups/dpkg.arch.0

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

_Modus `analyze`: keine verändernden Aktionen._

---

<sub>Erzeugt am 2026-09-09 08:40:23 UTC · Modus `analyze` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
