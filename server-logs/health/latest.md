# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-10-21 02:07:14 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 37 % belegt |
| RAM | 81 % belegt |
| Paket-Updates offen | 14 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: +160MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 RAM-Auslastung 81 % (Schwelle 80 %).
- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (15798) – Brute-Force? fail2ban prüfen.
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
| Load (1/5/15) | 0.31, 0.44, 0.33 |
| Uptime | up 8 weeks, 3 days, 13 hours, 56 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  105G  184G  37% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/be941dded444bd4725031331a62c51031e6db968ff912aba8bf37de8f96fad7c
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/771e0c9552f3dbab7f47f8346da6b40a01dd19b17396f27cfd440dc97b3cf08b
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/8bc34fa36ea89c3c48b356664294fa89e9dff02d6da84231354276e6e1ee783b
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/72f6651613d125edf07388188758b77fa1ae186b843ffdf5a90682048de9f632
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/ffdd263113168ed9d048ae1613082cb9e9438ae403d62fdeb25b5fa5908e4238
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/e3bf83983d0b2ee9d62edf4dc5aadc532d8929b49a9625a7d962f1c5e599687b
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/6c629bd54987eba590a9f271a1af17cd5d1131542953ab836e729d18609e0832
overlay        overlay  301G  105G  184G  37% /var/lib/docker/rootfs/overlayfs/32410cc28e9b6405985891c6b7d0aef0153c7db7d7f19c522fce555a756c0afe
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
Containers      9         9         213kB     0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **0B (0%)**.

### Container-Status

```
NAMES                                  STATUS                SIZE
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 2 minutes          4.1kB (virtual 599MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 7 minutes          4.1kB (virtual 599MB)
80c1457a-55b2-4671-82a8-60063041558b   Up 24 hours           4.1kB (virtual 599MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 24 hours           4.1kB (virtual 599MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 7 days             4.1kB (virtual 599MB)
cosmic-survivor                        Up 7 days (healthy)   81.9kB (virtual 53.3MB)
minecraft-web                          Up 7 days (healthy)   81.9kB (virtual 68.5MB)
fire-simulator                         Up 7 days             4.1kB (virtual 233MB)
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
Mem:            15Gi        12Gi       208Mi        53Mi       3.1Gi       2.9Gi
Swap:          2.0Gi       170Mi       1.8Gi
```

**RAM-Auslastung:** 81 % belegt.
**Swap:** 8 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
3804994 3804970 pteroda+ 3934048 24.6 32.6 java
3565959 3565934 pteroda+ 2632232 16.4 2.7 java
3567314 3567289 pteroda+ 2572296 16.0 2.0 java
3806326 3806301 pteroda+ 1877532 11.7 41.0 java
2888348       1 mysql    549984  3.4 0.2 mariadbd
2104484 2104371 pteroda+ 393456  2.4 1.2 java
2884232       1 root     244460  1.5 0.0 systemd-journal
2103589 2103486 fire     76124  0.4  0.0 next-server (v
2888366       1 root     76016  0.4  0.0 fail2ban-server
2103128       1 root     74108  0.4  0.4 dockerd
2101928       1 root     42488  0.2  0.3 containerd
3270146 2888765 www-data 40452  0.2  0.0 php-fpm8.3
3221657 2888765 www-data 39504  0.2  0.0 php-fpm8.3
3317990 2888765 www-data 39368  0.2  0.0 php-fpm8.3
2103854       1 root     35148  0.2  2.1 wings
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
3806326 pteroda+ 41.0 11.7 java
3804994 pteroda+ 32.6 24.6 java
3565959 pteroda+  2.7 16.4 java
3807383 root      2.7  0.0 sshd
2103854 root      2.1  0.2 wings
3567314 pteroda+  2.0 16.0 java
3807162 root      1.7  0.0 bash
2104484 pteroda+  1.2  2.4 java
3806958 root      0.9  0.0 systemd
2103128 root      0.4  0.4 dockerd
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

Fehlgeschlagene Passwort-Logins: **15798**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **14** (davon sicherheitsrelevant: **5**).

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
Inst docker-ce-cli [5:29.9.0-1~ubuntu.24.04~noble] (5:29.9.2-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst containerd.io [2.4.1-2~ubuntu.24.04~noble] (2.4.2-3~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce [5:29.9.0-1~ubuntu.24.04~noble] (5:29.9.2-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst open-vm-tools [2:13.0.10-0ubuntu0.24.04.1] (2:13.0.10-0ubuntu0.24.04.2 Ubuntu:24.04/noble-updates [amd64])
Inst libapparmor1 [4.0.1really4.0.1-0ubuntu0.24.04.8] (4.0.1really4.0.1-0ubuntu0.24.04.9 Ubuntu:24.04/noble-updates [amd64])
Inst tzdata [2026c-0ubuntu0.24.04.1] (2026e-0ubuntu0.24.04.1 Ubuntu:24.04/noble-updates, Ubuntu:24.04/noble-security [all])
Inst apparmor [4.0.1really4.0.1-0ubuntu0.24.04.8] (4.0.1really4.0.1-0ubuntu0.24.04.9 Ubuntu:24.04/noble-updates [amd64])
Inst docker-buildx-plugin [0.38.0-1~ubuntu.24.04~noble] (0.38.1-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce-rootless-extras [5:29.9.0-1~ubuntu.24.04~noble] (5:29.9.2-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst git-man [1:2.43.0-1ubuntu7.3] (1:2.43.0-1ubuntu7.4 Ubuntu:24.04/noble-security [all])
Inst git [1:2.43.0-1ubuntu7.3] (1:2.43.0-1ubuntu7.4 Ubuntu:24.04/noble-security [amd64])
Inst libxtst6 [2:1.2.3-1.1build1] (2:1.2.3-1.1ubuntu0.1 Ubuntu:24.04/noble-security [amd64])
Inst python3-jwt [2.7.0-1ubuntu0.3] (2.7.0-1ubuntu0.4 Ubuntu:24.04/noble-security [all])
Inst snapd [2.76.3+ubuntu24.04] (2.77.1+ubuntu24.04 Ubuntu:24.04/noble-updates [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **148** Fehler, **31956** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
    116 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     18 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      7 sshd[#]: error: beginning MaxStartups throttling
      4 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
      2 sshd[#]: error: Protocol major versions differ: # vs. #
      1 sshd[#]: error: send_error: write: Connection reset by peer
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Jan 16 15:43:58 2027 GMT (**87 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-10-18+00:00:01.1286664400 /var/backups/dpkg.arch.0

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


**Freigegebener Speicher in diesem Lauf:** 332MB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-10-21 02:07:14 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
