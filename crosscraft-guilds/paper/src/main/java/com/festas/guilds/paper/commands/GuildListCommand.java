package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import com.festas.guilds.paper.gui.GuildListGUI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

/**
 * /guild list - Alle Gilden auflisten
 */
public class GuildListCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildListCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        // Öffnet die GUI-Liste
        GuildAPI.getInstance().getTopGuilds(100).thenAcceptAsync(guilds -> {
            if (guilds.isEmpty()) {
                player.sendMessage(MM.deserialize("<yellow>Es gibt noch keine Gilden!"));
                return;
            }
            new GuildListGUI(plugin, player, guilds).open();
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }
}
