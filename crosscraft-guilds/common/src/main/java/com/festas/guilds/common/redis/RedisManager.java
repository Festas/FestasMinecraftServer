package com.festas.guilds.common.redis;

import com.festas.guilds.common.config.GuildConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Verwaltet den Jedis-Connection-Pool zu Redis.
 */
public class RedisManager {

    private static final Logger log = LoggerFactory.getLogger(RedisManager.class);

    private JedisPool jedisPool;
    private final GuildConfig config;

    public RedisManager(GuildConfig config) {
        this.config = config;
    }

    /** Initialisiert den Jedis-Pool. */
    public void initialize() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(16);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(2);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        String password = config.getRedisPassword();
        if (password == null || password.isBlank()) {
            jedisPool = new JedisPool(poolConfig, config.getRedisHost(), config.getRedisPort(), 2000);
        } else {
            jedisPool = new JedisPool(poolConfig, config.getRedisHost(), config.getRedisPort(), 2000, password);
        }

        // Verbindung testen
        try (Jedis jedis = getJedis()) {
            jedis.ping();
            log.info("Redis verbunden mit {}:{}", config.getRedisHost(), config.getRedisPort());
        } catch (Exception e) {
            log.error("Konnte Redis nicht verbinden: {}", e.getMessage());
            throw new RuntimeException("Redis-Verbindung fehlgeschlagen", e);
        }
    }

    /**
     * Gibt eine Jedis-Instanz aus dem Pool zurück.
     * Muss via try-with-resources geschlossen werden.
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /** Veröffentlicht eine Nachricht auf dem Kanal. */
    public void publish(String channel, String message) {
        try (Jedis jedis = getJedis()) {
            jedis.publish(channel, message);
        } catch (Exception e) {
            log.error("Fehler beim Veröffentlichen auf Kanal {}", channel, e);
        }
    }

    /** Schließt den Pool. */
    public void shutdown() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
            log.info("RedisManager heruntergefahren");
        }
    }

    public boolean isAvailable() {
        return jedisPool != null && !jedisPool.isClosed();
    }

    public JedisPool getPool() {
        return jedisPool;
    }
}
