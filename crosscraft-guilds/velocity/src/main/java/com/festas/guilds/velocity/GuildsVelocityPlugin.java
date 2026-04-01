package com.festas.guilds.velocity;

import com.festas.guilds.common.config.GuildConfig;
import com.festas.guilds.common.redis.RedisManager;
import com.festas.guilds.common.redis.RedisMessageBroker;
import com.festas.guilds.velocity.chat.GuildChatHandler;
import com.festas.guilds.velocity.commands.GuildVelocityCommand;
import com.festas.guilds.velocity.messaging.PluginMessageHandler;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Velocity-Plugin für das CrossCraft Gilden-System.
 * Verwaltet Cross-Server-Gildenchat und Plugin-Messaging.
 */
@Plugin(
        id = "crosscraft-guilds",
        name = "CrossCraftGuilds",
        version = "1.0.0",
        description = "Cross-Server Gilden-System - Velocity Proxy Modul",
        authors = {"Festas"}
)
public class GuildsVelocityPlugin {

    public static final MinecraftChannelIdentifier CHANNEL =
            MinecraftChannelIdentifier.create("crosscraft", "guilds");

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    private GuildConfig guildConfig;
    private RedisManager redisManager;
    private RedisMessageBroker messageBroker;
    private GuildChatHandler chatHandler;
    private PluginMessageHandler pluginMessageHandler;

    @Inject
    public GuildsVelocityPlugin(ProxyServer server, Logger logger,
                                  @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        // Konfiguration laden
        guildConfig = new GuildConfig();
        try {
            guildConfig.load(new File(dataDirectory.toFile(), "config.properties"));
        } catch (IOException e) {
            logger.warn("Konfiguration konnte nicht geladen werden, nutze Standards: {}", e.getMessage());
        }

        // Redis initialisieren
        redisManager = new RedisManager(guildConfig);
        try {
            redisManager.initialize();
            messageBroker = new RedisMessageBroker(redisManager, guildConfig.getRedisChannel());

            // Chat-Handler einrichten
            chatHandler = new GuildChatHandler(server, messageBroker);
            chatHandler.register();

            messageBroker.start();
            logger.info("Redis-Verbindung erfolgreich hergestellt");
        } catch (RuntimeException e) {
            logger.warn("Redis nicht verfügbar - Cross-Server-Features deaktiviert: {}", e.getMessage());
        }

        // Plugin-Messaging-Kanal registrieren
        server.getChannelRegistrar().register(CHANNEL);
        pluginMessageHandler = new PluginMessageHandler(server, this);
        server.getEventManager().register(this, pluginMessageHandler);

        // Befehle registrieren
        var meta = server.getCommandManager().metaBuilder("guild")
                .aliases("gilde", "g")
                .plugin(this)
                .build();
        server.getCommandManager().register(meta, new GuildVelocityCommand(server, this));

        logger.info("CrossCraft Guilds Velocity v1.0.0 aktiviert!");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        if (chatHandler != null) chatHandler.unregister();
        if (messageBroker != null) messageBroker.stop();
        if (redisManager != null) redisManager.shutdown();
        logger.info("CrossCraft Guilds Velocity deaktiviert.");
    }

    public ProxyServer getServer() { return server; }
    public Logger getLogger() { return logger; }
    public GuildConfig getGuildConfig() { return guildConfig; }
    public RedisManager getRedisManager() { return redisManager; }
    public RedisMessageBroker getMessageBroker() { return messageBroker; }
    public GuildChatHandler getChatHandlerOrNull() { return chatHandler; }
}
