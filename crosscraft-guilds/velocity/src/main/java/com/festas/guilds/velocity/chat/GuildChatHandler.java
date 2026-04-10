package com.festas.guilds.velocity.chat;

import com.festas.guilds.common.redis.RedisMessageBroker;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Optional;
import java.util.UUID;

/**
 * Verwaltet den Cross-Server-Gildenchat über Redis.
 * Empfängt GUILD_CHAT Events und leitet sie an alle Server weiter,
 * auf denen Gildenmitglieder online sind.
 */
public class GuildChatHandler {

    private static final MiniMessage MM = MiniMessage.miniMessage();

    private final ProxyServer server;
    private final RedisMessageBroker broker;

    public GuildChatHandler(ProxyServer server, RedisMessageBroker broker) {
        this.server = server;
        this.broker = broker;
    }

    /** Registriert den GUILD_CHAT Event-Handler. */
    public void register() {
        broker.on(RedisMessageBroker.GuildEvent.GUILD_CHAT, this::handleGuildChat);
        broker.on(RedisMessageBroker.GuildEvent.GUILD_LEVEL_UP, this::handleGuildLevelUp);
        broker.on(RedisMessageBroker.GuildEvent.MEMBER_JOINED, this::handleMemberJoined);
        broker.on(RedisMessageBroker.GuildEvent.MEMBER_LEFT, this::handleMemberLeft);
    }

    public void unregister() {
        broker.off(RedisMessageBroker.GuildEvent.GUILD_CHAT);
        broker.off(RedisMessageBroker.GuildEvent.GUILD_LEVEL_UP);
        broker.off(RedisMessageBroker.GuildEvent.MEMBER_JOINED);
        broker.off(RedisMessageBroker.GuildEvent.MEMBER_LEFT);
    }

    /**
     * Verarbeitet eine Gildenchat-Nachricht.
     * Format: guildId:playerName:message
     */
    private void handleGuildChat(RedisMessageBroker.GuildEvent event, String payload) {
        String[] parts = payload.split(":", 3);
        if (parts.length < 3) return;

        String guildId = parts[0];
        String playerName = parts[1];
        String message = parts[2];

        String formatted = "<dark_gray>[<gradient:#FFD700:#FFA500>Gilde</gradient>]</dark_gray> " +
                "<white>" + playerName + "</white><gray>: " + message;

        // An alle Proxy-Spieler senden die in der Gilde sind
        // (In einer vollständigen Implementierung würde hier der GuildCache konsultiert)
        broadcastToGuildMembers(guildId, formatted);
    }

    /**
     * Verarbeitet einen Gilden-Level-Aufstieg.
     * Format: guildId:newLevel
     */
    private void handleGuildLevelUp(RedisMessageBroker.GuildEvent event, String payload) {
        String[] parts = payload.split(":", 2);
        if (parts.length < 2) return;

        String guildId = parts[0];
        String levelStr = parts[1];

        String broadcast = "<gradient:#FFD700:#FFA500>⚔ Eine Gilde ist auf Level <white>" +
                levelStr + "</white> aufgestiegen! 🎉";

        // Proxy-weiter Broadcast
        server.getAllPlayers().forEach(p -> p.sendMessage(MM.deserialize(broadcast)));
    }

    /**
     * Verarbeitet ein MEMBER_JOINED Event.
     * Format: guildId:playerUUID
     */
    private void handleMemberJoined(RedisMessageBroker.GuildEvent event, String payload) {
        String[] parts = payload.split(":", 2);
        if (parts.length < 2) return;

        String guildId = parts[0];
        UUID playerUUID = UUID.fromString(parts[1]);
        Optional<Player> player = server.getPlayer(playerUUID);

        String name = player.map(p -> p.getUsername()).orElse("Unbekannt");
        broadcastToGuildMembers(guildId,
                "<green>" + name + " ist der Gilde beigetreten!");
    }

    /**
     * Verarbeitet ein MEMBER_LEFT Event.
     * Format: guildId:playerUUID
     */
    private void handleMemberLeft(RedisMessageBroker.GuildEvent event, String payload) {
        String[] parts = payload.split(":", 2);
        if (parts.length < 2) return;

        String guildId = parts[0];
        UUID playerUUID = UUID.fromString(parts[1]);
        Optional<Player> player = server.getPlayer(playerUUID);

        String name = player.map(p -> p.getUsername()).orElse("Unbekannt");
        broadcastToGuildMembers(guildId,
                "<yellow>" + name + " hat die Gilde verlassen.");
    }

    /**
     * Sendet eine Nachricht an alle online Proxy-Spieler einer Gilde.
     * Hinweis: Erfordert einen Guild-Member-Cache auf Proxy-Ebene für vollständige Implementierung.
     * Aktuell sendet an alle Spieler (wird in Produktion durch Cache-Lookup ersetzt).
     */
    private void broadcastToGuildMembers(String guildId, String miniMessage) {
        // TODO: Hier würde in einer vollständigen Implementierung ein GuildMemberCache
        // konsultiert werden um nur die richtigen Spieler zu erreichen.
        // Für jetzt wird an alle gesendet (Backend-Server filtern selbst)
        server.getAllPlayers().forEach(p ->
                p.sendMessage(MM.deserialize(miniMessage)));
    }

    /**
     * Sendet eine Gildenchat-Nachricht über Redis an alle Server.
     */
    public void sendGuildChat(String guildId, String playerName, String message) {
        if (broker != null) {
            broker.publish(RedisMessageBroker.GuildEvent.GUILD_CHAT,
                    guildId + ":" + playerName + ":" + message);
        }
    }
}
