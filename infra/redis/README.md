# Redis (Festas Minecraft network)

Central Redis instance for the network. It is used for:

- **LuckPerms messaging** — live push of rank/permission changes to all four
  servers (Lobby, RPG, Survival, Skyblock) so changes apply instantly instead of
  polling the database. Data itself stays in MariaDB (`s4_perms`).
- **HuskSync session cache** — temporary player data during server switches.

See [`docs/infrastructure/DATENBANKEN.md`](../../docs/infrastructure/DATENBANKEN.md).

## Files

| File | Purpose |
|------|---------|
| `docker-compose.redis.yml` | Redis service definition (image, port binding, persistence, healthcheck). |
| `redis.conf` | Redis configuration. Contains the `__REDIS_PASSWORD__` placeholder that is replaced with the `REDIS_PASSWORD` secret at deploy time. |

## Deployment

Redis is deployed by the **[`deploy-redis`](../../.github/workflows/deploy-redis.yml)**
GitHub Actions workflow. It runs automatically when files under `infra/redis/**`
change on `main`, and can also be started manually via *Run workflow*
(`workflow_dispatch`).

The workflow:

1. Injects the `REDIS_PASSWORD` secret into a copy of `redis.conf`.
2. Copies `redis.conf` and `docker-compose.redis.yml` to `~/festas-redis` on the
   server. The directory is locked down to mode `700` so the password in
   `redis.conf` stays private to the deploy user (the file itself is mode `644`
   so the in-container `redis` user can read it).
3. Runs `docker compose up -d` and waits for the container to become healthy
   (the healthcheck authenticates with the configured password).

Because the password lives only in the deployed `redis.conf`, editing
permissions later needs no manual server work — LuckPerms pushes the change over
Redis automatically.

### Manual run on the server

```bash
cd ~/festas-redis
docker compose -f docker-compose.redis.yml up -d
docker compose -f docker-compose.redis.yml ps
```

## Configuration used by the plugins

The LuckPerms configs (`*/plugins/LuckPerms/config.yml`) must point at this
instance to enable live messaging:

```yaml
messaging-service: redis
redis:
  enabled: true
  address: 172.18.0.1:6380
  username: ''
  password: '__REDIS_PASSWORD__'   # injected by the server deploy workflows
```

## Koexistenz mit dem Pterodactyl-Panel-Redis

Auf dem Host läuft zusätzlich eine **native** Redis-Instanz (apt + systemd), die das
**Pterodactyl-Panel** für Cache/Session/Queue nutzt. Sie hört auf
`127.0.0.1:6379`. Beide Instanzen sind bewusst getrennt:

| Instanz | Art | Erreichbar unter | Passwort | Genutzt von |
|---------|-----|------------------|----------|-------------|
| Panel-Redis | nativ (systemd) | `127.0.0.1:6379` | Panel-`.env` | Pterodactyl |
| Game-Redis (dieses Verzeichnis) | Docker `festas-redis` | `172.18.0.1:6380` | `REDIS_PASSWORD`-Secret | LuckPerms, HuskSync |

Weil der Container den Port nur auf **Host-Port `6380`** (der interne Container-Port
bleibt `6379`) und nur auf der Bridge-IP veröffentlicht, kollidiert er nicht mit
der nativen Redis auf `6379`. Der Host-Port ist über die Variable
`REDIS_HOST_PORT` (Default `6380`) bzw. den `host_port`-Input des Workflows
überschreibbar. Der Panel-Redis bleibt dabei **unangetastet**.

## Security notes

- The port is published only on the internal bridge IP
  (`REDIS_BIND_IP`, default `172.18.0.1`) and on host port `REDIS_HOST_PORT`
  (default `6380`), never `0.0.0.0`. Additionally close port `6380` in the host
  firewall so Redis is not reachable from the internet.
- Authentication is mandatory (`requirepass`). The real password is **never**
  committed; only the `__REDIS_PASSWORD__` placeholder is stored in git.
- If the host does not expose `172.18.0.1`, override the bind IP via the
  workflow's `bind_ip` input (or a `REDIS_BIND_IP` entry in `~/festas-redis/.env`).
  Likewise the host port can be changed via the `host_port` input (or a
  `REDIS_HOST_PORT` entry in `~/festas-redis/.env`) if `6380` is already taken.

## Persistence & backups

RDB snapshots are written to `redis-data/dump.rdb` next to the compose file,
matching [`docs/infrastructure/BACKUPS.md`](../../docs/infrastructure/BACKUPS.md)
and [`docs/DISASTER_RECOVERY.md`](../../docs/DISASTER_RECOVERY.md).
