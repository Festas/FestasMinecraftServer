package com.festas.guilds.paper.listeners;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Zeigt Gildeninfo beim Login an.
 */
public class PlayerJoinListener implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public PlayerJoinListener(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                .thenAcceptAsync(opt -> {
                    if (opt.isPresent()) {
                        var guild = opt.get();
                        player.sendMessage(MM.deserialize(
                                "<gradient:#FFD700:#FFA500>⚔ Willkommen zurück!</gradient> " +
                                "<gray>Du bist Mitglied von <gold>" + guild.getName() +
                                "</gold> [" + guild.getTag() + "] - Level " + guild.getLevel()));

                        // Online-Mitglieder der Gilde benachrichtigen
                        guild.getMembers().forEach(m -> {
                            if (m.getPlayerUUID().equals(player.getUniqueId())) return;
                            var online = plugin.getServer().getPlayer(m.getPlayerUUID());
                            if (online != null) {
                                online.sendMessage(MM.deserialize(
                                        "<gradient:#FFD700:#FFA500>[Gilde]</gradient> " +
                                        "<green>" + player.getName() + " ist online gegangen."));
                            }
                        });
                    }
                }, r -> plugin.getServer().getScheduler().runTaskLater(plugin, r, 20L));
    }
}
