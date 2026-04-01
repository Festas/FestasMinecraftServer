package com.festas.guilds.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * Redis Pub/Sub Message Broker für Cross-Server Gildenkommunikation.
 */
public class RedisMessageBroker {

    private static final Logger log = LoggerFactory.getLogger(RedisMessageBroker.class);

    /** Verfügbare Event-Typen die über Redis gesendet werden. */
    public enum GuildEvent {
        GUILD_CREATED,
        GUILD_DISBANDED,
        MEMBER_JOINED,
        MEMBER_LEFT,
        GUILD_CHAT,
        GUILD_XP_GAINED,
        MEMBER_RANK_CHANGED,
        GUILD_LEVEL_UP
    }

    private final RedisManager redisManager;
    private final String channel;
    private final Map<GuildEvent, BiConsumer<GuildEvent, String>> listeners = new ConcurrentHashMap<>();
    private final ExecutorService subscribeThread = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "GuildRedis-Subscribe");
        t.setDaemon(true);
        return t;
    });
    private JedisPubSub pubSub;

    public RedisMessageBroker(RedisManager redisManager, String channel) {
        this.redisManager = redisManager;
        this.channel = channel;
    }

    /** Startet den Subscriber im Hintergrund. */
    public void start() {
        pubSub = new JedisPubSub() {
            @Override
            public void onMessage(String ch, String message) {
                try {
                    // Format: EVENT_TYPE:payload
                    int sep = message.indexOf(':');
                    if (sep < 0) return;
                    String eventName = message.substring(0, sep);
                    String payload = message.substring(sep + 1);
                    GuildEvent event = GuildEvent.valueOf(eventName);
                    BiConsumer<GuildEvent, String> handler = listeners.get(event);
                    if (handler != null) {
                        handler.accept(event, payload);
                    }
                } catch (IllegalArgumentException e) {
                    log.warn("Unbekanntes Guild-Event empfangen: {}", message);
                } catch (Exception e) {
                    log.error("Fehler beim Verarbeiten der Redis-Nachricht", e);
                }
            }
        };

        subscribeThread.execute(() -> {
            try (Jedis jedis = redisManager.getPool().getResource()) {
                log.info("Redis Subscriber gestartet auf Kanal '{}'", channel);
                jedis.subscribe(pubSub, channel);
            } catch (Exception e) {
                log.error("Redis Subscriber abgebrochen", e);
            }
        });
    }

    /**
     * Veröffentlicht ein Gildenevent.
     *
     * @param event   der Event-Typ
     * @param payload Daten als String (z.B. JSON oder kommagetrennte Werte)
     */
    public void publish(GuildEvent event, String payload) {
        String message = event.name() + ":" + payload;
        redisManager.publish(channel, message);
        log.debug("Veröffentlicht: {}", message);
    }

    /** Registriert einen Handler für ein bestimmtes Event. */
    public void on(GuildEvent event, BiConsumer<GuildEvent, String> handler) {
        listeners.put(event, handler);
    }

    /** Entfernt einen Handler. */
    public void off(GuildEvent event) {
        listeners.remove(event);
    }

    /** Stoppt den Subscriber. */
    public void stop() {
        if (pubSub != null && pubSub.isSubscribed()) {
            pubSub.unsubscribe();
        }
        subscribeThread.shutdown();
        log.info("RedisMessageBroker gestoppt");
    }
}
