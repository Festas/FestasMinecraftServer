# PLAN Plugin – Reverse Proxy Deployment Guide

## Overview

The [PLAN](https://github.com/plan-player-analytics/Plan) plugin runs an embedded
webserver (default port **8804**) inside each Docker container.  
The host **nginx** reverse-proxies HTTPS traffic from
`https://mc-stats.festas-builds.com` to that port.

---

## Config files

| Container | File path inside repo |
|-----------|----------------------|
| proxy     | `proxy/plugins/plan/config.yml` |
| lobby     | `lobby/plugins/Plan/config.yml` |
| rpg       | `rpg/plugins/Plan/config.yml` |
| survival  | `survival/plugins/Plan/config.yml` |

Only the **proxy** container's webserver needs to be reachable from nginx
(the sub-servers register to the proxy and do not expose their own webserver
to the internet).  All four configs are kept consistent to avoid confusion
when switching the active proxy.

---

## Critical setting – `Webserver.Internal_IP`

```yaml
Webserver:
    Internal_IP: 0.0.0.0   # REQUIRED for reverse-proxy access
```

**Why `0.0.0.0`?**  
Docker assigns each container its own IP (e.g. `172.25.0.2`).  
If `Internal_IP` is set to `127.0.0.1` the webserver only listens on the
container's loopback interface – nginx on the **host** cannot reach it via the
container IP and will log a *connection refused* or *connection reset* error.  
`0.0.0.0` makes PLAN listen on **all** interfaces inside the container,
including the Docker bridge IP that nginx uses as its upstream.

**Security:**  
Port 8804 is only accessible from the host network bridge, not from the public
internet, as long as the host firewall rule is in place:

```bash
# UFW – block direct access to port 8804 from outside
sudo ufw deny 8804
sudo ufw status | grep 8804   # should show DENY
```

Docker's own `EXPOSE` / port-mapping does not publish 8804 to the host unless
explicitly mapped in `docker-compose.yml` – verify there is no `8804:8804`
mapping in production.

---

## nginx upstream

nginx (on the host) proxies to the container via its Docker bridge IP:

```nginx
# /etc/nginx/sites-available/mc-stats.festas-builds.com
upstream plan_backend {
    server 172.25.0.2:8804;   # adjust to actual container IP / hostname
}

server {
    listen 443 ssl;
    server_name mc-stats.festas-builds.com;

    location / {
        proxy_pass         http://plan_backend;
        proxy_set_header   Host              $host;
        proxy_set_header   X-Real-IP         $remote_addr;
        proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
    }
}
```

The PLAN config must also have `Use_X-Forwarded-For_Header: true` (already set)
so that PLAN records real client IPs instead of the nginx proxy IP.

---

## Verifying the setup

After restarting the proxy container:

```bash
# From the host:
curl -v http://172.25.0.2:8804/      # should return 302 or 200
curl -vk https://mc-stats.festas-builds.com/   # full HTTPS path

# From inside the container:
docker exec -it <container_id> sh -lc \
  'wget -qO- http://127.0.0.1:8804/ | head -5'
```

---

## Rollback

To revert to localhost-only binding (disables reverse-proxy access):

1. Set `Internal_IP: 127.0.0.1` in `proxy/plugins/plan/config.yml`.
2. Update the nginx upstream to use `127.0.0.1:8804` (requires the port to be
   published to the host via Docker port mapping).
3. Restart the container: `docker restart <container_id>`.

**Note:** This configuration is known to be unreliable in Docker bridge
networking and is **not recommended**.
