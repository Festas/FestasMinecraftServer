# 🩺 Festas Server – Wartungs- & Health-Bericht

_Automatisch erzeugt von `tools/server-maintenance/festas-maintenance.sh`._

**Gesamtstatus:** 🟡 **WARNUNG** · erstellt 2026-11-04 02:03:47 UTC · Host `festas-builds`

| Kennzahl | Wert |
|---|---|
| Festplatte `/` | 37 % belegt |
| RAM | 80 % belegt |
| Paket-Updates offen | 4 |
| Fehlgeschlagene Dienste | 1 |
| Modus dieses Laufs | `full` |
| Trend | Seit letztem Lauf: +752MB auf `/`. |

**Wichtigste Befunde:**

- 🟡 RAM-Auslastung 80 % (Schwelle 80 %).
- 🟡 1 fehlgeschlagene systemd-Unit(s).
- 🟡 Interner Dienst 'Plan Analytics' (Port 8804) ist laut Host-Status öffentlich freigegeben – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Survival' (Port 8102) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Interner Dienst 'BlueMap Mining' (Port 8103) bindet auf allen Interfaces – dokumentiert ist nur Reverse-Proxy/Loopback.
- 🟡 Viele fehlgeschlagene Logins (14878) – Brute-Force? fail2ban prüfen.

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
| Load (1/5/15) | 0.21, 0.13, 0.10 |
| Uptime | up 10 weeks, 3 days, 13 hours, 52 minutes |

## 💾 Speicherplatz


### Dateisysteme (df)

```
Filesystem     Type     Size  Used Avail Use% Mounted on
/dev/sda1      ext4     301G  106G  183G  37% /
/dev/sda15     vfat     253M  146K  252M   1% /boot/efi
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/771e0c9552f3dbab7f47f8346da6b40a01dd19b17396f27cfd440dc97b3cf08b
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/be941dded444bd4725031331a62c51031e6db968ff912aba8bf37de8f96fad7c
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/bfd613e2007d272beb2a8e1fb4a168746000f1cb438506596ae69ec24bb72430
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/8bc34fa36ea89c3c48b356664294fa89e9dff02d6da84231354276e6e1ee783b
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/f0da76853652bd5b991ed20ee29b0859f50036f739ed31391f26e3d96e541861
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/24217c80dc96661f8979be464528fde474961e7e36b7e1626743a97fd4fbd532
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/c5f2b6fbbc043a0ef52883551f7c60ef47b4f9d3ad27392909fa5c06be69bc36
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/35f3d70827852041618e0ab3cd53318020a1428ca1389dfe1db39ab080a86ae7
overlay        overlay  301G  106G  183G  37% /var/lib/docker/rootfs/overlayfs/b01e183fad368c879894e3ca410d8515d8ded4d3d9a7106d5aaff4e90cba5f30
```
**Root (`/`):** 106GB / 301GB belegt (37 %), frei: 183GB.
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
Images          6         6         2.164GB   0B (0%)
Containers      9         9         233.5kB   0B (0%)
Local Volumes   7         0         2.863GB   2.863GB (100%)
Build Cache     9         0         310.6MB   0B
```

Wiedergewinnbar laut Docker: **0B (0%)**.

### Container-Status

```
NAMES                                  STATUS                 SIZE
80c1457a-55b2-4671-82a8-60063041558b   Up 23 hours            4.1kB (virtual 592MB)
0af91553-d5ef-42fc-9ed1-97daaf3c4d70   Up 23 hours            4.1kB (virtual 592MB)
39a0762a-9e53-4b5b-8810-2bf63410800d   Up 23 hours            4.1kB (virtual 592MB)
cfb531d8-3843-4bff-a8d5-b534aa58fc92   Up 23 hours            4.1kB (virtual 592MB)
b50e2f8c-440f-4910-8f00-29577afbc455   Up 2 weeks             4.1kB (virtual 599MB)
cosmic-survivor                        Up 2 weeks (healthy)   81.9kB (virtual 53.3MB)
minecraft-web                          Up 2 weeks (healthy)   81.9kB (virtual 68.5MB)
fire-simulator                         Up 2 weeks             24.6kB (virtual 233MB)
festas-redis                           Up 2 weeks (healthy)   24.6kB (virtual 41.1MB)
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
Mem:            15Gi        12Gi       292Mi        45Mi       3.0Gi       3.0Gi
Swap:          2.0Gi       371Mi       1.6Gi
```

**RAM-Auslastung:** 80 % belegt.
**Swap:** 18 % belegt.

### Top 15 Prozesse nach RAM (RSS)

```
    PID    PPID USER       RSS %MEM %CPU COMMAND
2785314 2785288 pteroda+ 3685296 23.0 3.7 java
2787881 2787857 pteroda+ 2750824 17.2 2.8 java
2789239 2789214 pteroda+ 2528260 15.8 2.1 java
2786614 2786589 pteroda+ 2114724 13.2 2.0 java
3812663 3812618 pteroda+ 440372  2.7 1.3 java
 395403       1 mysql    435716  2.7 0.2 mariadbd
4105202       1 root     166548  1.0 0.0 systemd-journal
3811784       1 root     72052  0.4  0.4 dockerd
 395313       1 root     66704  0.4  0.0 fail2ban-server
3812237 3812147 fire     56008  0.3  0.0 next-server (v
3810410       1 root     51324  0.3  0.3 containerd
1903874 1336020 www-data 43172  0.2  0.0 php-fpm8.3
2318879 1336020 www-data 41720  0.2  0.0 php-fpm8.3
2315163 1336020 www-data 40436  0.2  0.0 php-fpm8.3
3812501       1 root     32072  0.2  2.1 wings
```

### Top 10 Prozesse nach CPU

```
    PID USER     %CPU %MEM COMMAND
2785314 pteroda+  3.7 23.0 java
2787881 pteroda+  2.8 17.2 java
2789239 pteroda+  2.1 15.8 java
3812501 root      2.1  0.2 wings
2786614 pteroda+  2.0 13.2 java
3015448 root      1.8  0.0 bash
3812663 pteroda+  1.3  2.7 java
3015209 root      1.0  0.0 systemd
3811784 root      0.4  0.4 dockerd
3810410 root      0.3  0.3 containerd
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

Fehlgeschlagene Passwort-Logins: **14878**.

### Letzte Anmeldungen

```
root     pts/0        91.192.12.105    Wed Aug 26 06:49 - 06:52  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 17:36 - 17:44  (00:08)
root     pts/0        213.244.61.249   Sun Aug 23 17:19 - 17:21  (00:02)
root     pts/0        213.244.61.249   Sun Aug 23 16:33 - 16:59  (00:25)
root     pts/0        213.244.61.249   Sun Aug 23 16:22 - 16:33  (00:11)
```

## 📦 Paket-Updates

Verfügbare Updates: **4** (davon sicherheitsrelevant: **0**).

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
Inst docker-ce-cli [5:29.9.2-1~ubuntu.24.04~noble] (5:29.9.3-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce [5:29.9.2-1~ubuntu.24.04~noble] (5:29.9.3-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-ce-rootless-extras [5:29.9.2-1~ubuntu.24.04~noble] (5:29.9.3-1~ubuntu.24.04~noble Docker CE:noble [amd64])
Inst docker-compose-plugin [5.6.1-1~ubuntu.24.04~noble] (5.7.0-1~ubuntu.24.04~noble Docker CE:noble [amd64])
```

## 📜 Log-Analyse (7 Tage)

Journald: **123** Fehler, **31781** Warnungen (7 Tage).

### Häufigste Fehlermeldungen

```
     75 sshd[#]: error: kex_exchange_identification: read: Connection reset by peer
     26 sshd[#]: error: beginning MaxStartups throttling
     12 sshd[#]: error: kex_protocol_error: type # seq # [preauth]
      6 sshd[#]: error: Protocol major versions differ: # vs. #
      2 sshd[#]: fatal: userauth_pubkey: parse publickey packet: incomplete message [preauth]
      1 sshd[#]: fatal: userauth_finish: send failure packet: Connection reset by peer [preauth]
      1 sshd[#]: error: send_error: write: Connection reset by peer
```

Kernel-I/O-/Dateisystem-Fehler (7 Tage): **0**.

## 🌡️ Datenträger-Gesundheit & Sensoren

_smartctl (smartmontools) nicht installiert – SMART-Check übersprungen._

## 🔏 TLS-Zertifikate

- `mc.festas-builds.com`: gültig bis Jan 16 15:43:58 2027 GMT (**73 Tage**).

## 🗄️ Backups (Heuristik)

- `/var/backups` (0B); neueste Datei: 2026-10-29+00:00:02.1175235690 /var/backups/alternatives.tar.0

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


**Freigegebener Speicher in diesem Lauf:** 1.1GB.

➡️ Nach den Updates ist ein **Reboot erforderlich**.

**Protokoll:**
- Paket-Updates installiert (apt-get upgrade, all).
- Verwaiste Pakete/Kernel entfernt (autoremove --purge).
- APT-Paketcache geleert (clean).
- Journald eingedampft (time=14d, size=500M).
- Docker aufgeräumt (dangling Images, gestoppte Container, Build-Cache).
- Alte Temp-Dateien nach systemd-Policy bereinigt.

---

<sub>Erzeugt am 2026-11-04 02:03:47 UTC · Modus `full` ·
Details/Anpassung: [tools/server-maintenance/README.md](../../tools/server-maintenance/README.md)</sub>
