# Resource-Packs (ForceResourcepacks)

Der Proxy erzwingt serverabhängige Ressourcenpakete über das Plugin
**ForceResourcepacks** (`proxy/plugins/forceresourcepacks/config.yml`). Dieses Dokument
beschreibt die **sichere Auslieferung über HTTPS** und die **Integritätsprüfung**.

## Vorher / Nachher

| | Vorher | Nachher |
|---|---|---|
| Transport | `http://128.140.99.121/…` (Klartext-HTTP, rohe IP) | `https://mc.festas-builds.com/packs/…` (TLS, gemeinsames Zertifikat) |
| Integrität | `hash: ""` + Klartext → manipulierbar | `autogeneratehashes` erzwingt SHA-1 beim Client; echter Hash pinnbar |

Der Wechsel von Klartext-HTTP auf HTTPS verhindert, dass das Pack unterwegs
mitgelesen oder manipuliert werden kann. Da für `mc.festas-builds.com` bereits ein
gültiges Let's-Encrypt-Zertifikat existiert (dasselbe wie für die Website), ist **kein
neues Zertifikat** nötig.

## Hosting (Nginx)

Die Pack-Dateien werden statisch über den bestehenden Website-vHost ausgeliefert:
`nginx/sites-available/mc.festas-builds.com.conf`, Block `location ^~ /packs/`.

**Einmalig auf dem Host bereitstellen:**

```bash
sudo mkdir -p /var/www/festas-packs
# Bestehende Pack-Dateien dorthin kopieren/verschieben (bisher am IP-Root ausgeliefert):
sudo cp rpg.zip survival.zip /var/www/festas-packs/
sudo chown -R www-data:www-data /var/www/festas-packs
sudo nginx -t && sudo systemctl reload nginx
```

Danach sind die Packs erreichbar unter:

- `https://mc.festas-builds.com/packs/rpg.zip`
- `https://mc.festas-builds.com/packs/survival.zip`

## Integrität / Hash pinnen

In der Config steht `autogeneratehashes: true`. ForceResourcepacks lädt das Pack beim
ersten Start herunter, berechnet den SHA-1 selbst und **erzwingt** ihn beim Client –
Integrität ist also auch mit leerem `hash` gewährleistet. Nachteil: bei jedem
Proxy-Neustart wird das Pack erneut geladen.

Sobald ein Pack **stabil** ist, den echten Hash **pinnen**:

```bash
sha1sum /var/www/festas-packs/rpg.zip
# Ausgabe (40 Hex-Zeichen) in proxy/plugins/forceresourcepacks/config.yml unter
# dem jeweiligen Pack als hash: "<sha1>" eintragen.
```

> **Wichtig:** Bei jeder Pack-Änderung Hash neu berechnen und aktualisieren. Ein
> falscher/veralteter Hash führt dazu, dass der Client das Pack ablehnt.

## Optionale Umbenennung `rpg.zip` → `mining.zip`

Der `rpg`-Slot ist öffentlich der **Mining**-Server (siehe `mining.festas-builds.com`).
Zur Klarheit kann `rpg.zip` in `mining.zip` umbenannt werden. Dann **beide** Stellen
anpassen: die Datei im Host-Verzeichnis **und** die `url:` in der Config. Die internen
Pack-/Server-Schlüssel (`oraxen_pack_rpg`, `servers.rpg`) müssen **nicht** geändert
werden.

## Hinweis zu Oraxen (separat)

`survival`/`rpg` nutzen zusätzlich Oraxens **self-host** (`Oraxen/settings.yml`,
`self-host.domain: 128.140.99.121:25600`). Das ist ein **eigener** Mechanismus und
nicht Teil dieses ForceResourcepacks-Setups; eine analoge HTTPS-Umstellung dort ist
optional und serverseitig zu konfigurieren.
