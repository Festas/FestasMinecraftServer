# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-10-28 02:04:11 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 37 % belegt |
| RAM | 78 % belegt |
| Paket-Updates offen | 33 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: +11MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (17048) – Brute-Force? fail2ban prüfen.
- 🟡 3 sicherheitsrelevante Updates ausstehend.

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
| Load (1/5/15) | 0.39, 0.25, 0.17 |
| Uptime | up 9 weeks, 3 days, 13 hours, 53 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  105G  184G  37% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/771e0c9552f3dbab7f47f8346da6b40a01dd19b17396f27cfd440dc97b3cf08b
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/be941dded444bd4725031331a62c51031e6db968ff912aba8bf37de8f96fad7c
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/8bc34fa36ea89c3c48b356664294fa89e9dff02d6da84231354276e6e1ee783b
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/f0da76853652bd5b991ed20ee29b0859f50036f739ed31391f26e3d96e541861
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/4c37c979fd6e9441511ffc9280d68a3f562d01c23f06477a04511472cc53a4f9
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/6eb909f4ec05d175c83b08d03240f5b9d379ae81aa90dc33d838cb997faadcc3
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/751c470943a02945c62f0682b9e6f846ce64c31be0517d6c11ef683d962c026c
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/266eeda8b7ef9667a6f0554756b3c700a594eb52d40111eb3dc36fdba63d1b7f
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
Images          5         5         1.355GB   0B (0%)
Containers      9         9         233.5kB   0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **0B (0%)**.

### Container-Status

```
NAMES                                  STATUS                SIZE
80c1457a-55b2-4671-82a8-60063041558b   Up 23 hours           4.1kB (virtual 599MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 23 hours           4.1kB (virtual 599MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 23 hours           4.1kB (virtual 599MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 23 hours           4.1kB (virtual 599MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 7 days             4.1kB (virtual 599MB)
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
Mem:            15Gi        11Gi       314Mi        53Mi       3.4Gi       3.3Gi
Swap:          2.0Gi       219Mi       1.8Gi
```

**RAM-Auslastung:** 78 % belegt.
**Swap:** 10 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
1081319 1081293 pteroda+ 3669164 22.9 3.6 java
1085339 1085314 pteroda+ 2630756 16.4 2.1 java
1083971 1083946 pteroda+ 2494564 15.6 2.7 java
1082650 1082626 pteroda+ 1829948 11.4 2.0 java
 395403       1 mysql    518492  3.2 0.2 mariadbd
3812663 3812618 pteroda+ 421624  2.6 1.2 java
4105202       1 root     174500  1.0 0.0 systemd-journal
 395313       1 root     76444  0.4  0.0 fail2ban-server
3811784       1 root     75616  0.4  0.4 dockerd
3812237 3812147 fire     57724  0.3  0.0 next-server (v
 395285  395266 www-data 47044  0.2  0.0 php-fpm8.3
 395287  395266 www-data 45888  0.2  0.0 php-fpm8.3
3810410       1 root     43508  0.2  0.3 containerd
3812501       1 root     36976  0.2  2.1 wings
 395266       1 root     36892  0.2  0.0 php-fpm8.3
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
1081319 pteroda+  3.6 22.9 java
1083971 pteroda+  2.7 15.6 java
1085339 pteroda+  2.1 16.4 java
3812501 root      2.1  0.2 wings
1082650 pteroda+  2.0 11.4 java
3812663 pteroda+  1.2  2.6 java
1311089 root      1.1  0.0 bash
1310901 root      1.0  0.0 systemd
3811784 root      0.4  0.4 dockerd
3810410 root      0.3  0.2 containerd
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

Fehlgeschlagene Passwort-Logins: **17048**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **33** (davon sicherheitsrelevant: **3**).

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
Inst libglib2.0-data [2.80.0-6ubuntu3.9] (2.80.0-6ubuntu3.10 Ubuntu:24.04/noble-updates [all])
Inst libglib2.0-bin [2.80.0-6ubuntu3.9] (2.80.0-6ubuntu3.10 Ubuntu:24.04/noble-updates [amd64]) []
Inst gir1.2-glib-2.0 [2.80.0-6ubuntu3.9] (2.80.0-6ubuntu3.10 Ubuntu:24.04/noble-updates [amd64]) []
Inst libglib2.0-0t64 [2.80.0-6ubuntu3.9] (2.80.0-6ubuntu3.10 Ubuntu:24.04/noble-updates [amd64])
Inst ubuntu-pro-client-l10n [37.2ubuntu~24.04.1] (38ubuntu0~24.04 Ubuntu:24.04/noble-updates [amd64]) []
Inst ubuntu-pro-client [37.2ubuntu~24.04.1] (38ubuntu0~24.04 Ubuntu:24.04/noble-updates [amd64])
Inst docker-compose-plugin [5.6.0-1~ubuntu.24.04~noble] (5.6.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst java-21-amazon-corretto-jdk [1:21.0.12.12-1] (1:21.0.13.0.11-1 . stable:stable [amd64])
Inst libbrotli1 [1.1.0-2build2] (1.1.0-2ubuntu0.1 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64])
Inst nginx [1.24.0-2ubuntu7.18] (1.24.0-2ubuntu7.19 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [amd64]) []
Inst nginx-common [1.24.0-2ubuntu7.18] (1.24.0-2ubuntu7.19 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
Inst php8.1-readline [8.1.34-8+ubuntu24.04.1+deb.sury.org+1] (8.1.34-9+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.1-opcache [8.1.34-8+ubuntu24.04.1+deb.sury.org+1] (8.1.34-9+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.1-phpdbg [8.1.34-8+ubuntu24.04.1+deb.sury.org+1] (8.1.34-9+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.1-cli [8.1.34-8+ubuntu24.04.1+deb.sury.org+1] (8.1.34-9+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.1-common [8.1.34-8+ubuntu24.04.1+deb.sury.org+1] (8.1.34-9+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64])
Inst php8.3-bcmath [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-zip [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-xml [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-sqlite3 [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-readline [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-opcache [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-mysql [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-mbstring [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-intl [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-gd [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-curl [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-fpm [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-cli [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64]) []
Inst php8.3-common [8.3.35-1+ubuntu24.04.1+deb.sury.org+1] (8.3.36-1+ubuntu24.04.1+deb.sury.org+1 PPA for PHP:24.04/noble [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **119** Fehler, **32017** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
     73 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     31 sshd[#]: fatal: userauth_passwd: parse packet: invalid format [preauth]
      6 sshd[#]: error: beginning MaxStartups throttling
      5 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
      2 sshd[#]: error: Bad remote protocol version identification: 'SSH-#.#-'
      1 sshd[#]: fatal: userauth_finish: send failure packet: Broken pipe [preauth]
      1 sshd[#]: error: Protocol major versions differ: # vs. #
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Jan 16 15:43:58 2027 GMT (**80 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-10-25+00:00:01.1124614750 /var/backups/dpkg.arch.0

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


**Freigegebener Speicher in diesem Lauf:** 268MB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-10-28 02:04:11 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
