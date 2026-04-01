package com.festas.guilds.paper.listeners;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Verwaltet den Gildenchat - leitet Nachrichten um wenn Spieler im Gildenchat-Modus sind.
 */
public class PlayerChatListener implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    // Spieler die sich im Gildenchat-Modus befinden
    private final Set<UUID> guildChatMode = ConcurrentHashMap.newKeySet();

    public PlayerChatListener(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(AsyncPlayerChatEvent event) {
        var player = event.getPlayer();

        if (!guildChatMode.contains(player.getUniqueId())) return;

        event.setCancelled(true);
        String message = event.getMessage();

        // Gildenchat-Befehl asynchron aufrufen
        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                .thenAcceptAsync(opt -> {
                    if (opt.isEmpty()) {
                        guildChatMode.remove(player.getUniqueId());
                        player.sendMessage(MM.deserialize("<red>Du bist nicht in einer Gilde. Gildenchat-Modus deaktiviert."));
                        return;
                    }
                    var guild = opt.get();
                    String formatted = "<dark_gray>[<gradient:#FFD700:#FFA500>Gilde</gradient>]</dark_gray> " +
                            "<white>" + player.getName() + "</white><gray>: " + message;

                    guild.getMembers().forEach(m -> {
                        var online = plugin.getServer().getPlayer(m.getPlayerUUID());
                        if (online != null) {
                            online.sendMessage(MM.deserialize(formatted));
                        }
                    });

                    var broker = plugin.getMessageBroker();
                    if (broker != null) {
                        broker.publish(
                                com.festas.guilds.common.redis.RedisMessageBroker.GuildEvent.GUILD_CHAT,
                                guild.getId() + ":" + player.getName() + ":" + message);
                    }
                });
    }

    /** Aktiviert/Deaktiviert den Gildenchat-Modus für einen Spieler. */
    public void toggleGuildChatMode(UUID playerUUID) {
        if (guildChatMode.contains(playerUUID)) {
            guildChatMode.remove(playerUUID);
        } else {
            guildChatMode.add(playerUUID);
        }
    }

    public boolean isInGuildChatMode(UUID playerUUID) {
        return guildChatMode.contains(playerUUID);
    }
}
