package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * /guild chat <nachricht> und /gc <nachricht>
 */
public class GuildChatCommand implements CommandExecutor {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildChatCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    /** Wird vom GuildCommand Router aufgerufen. */
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /guild chat <nachricht>"));
            return;
        }
        sendGuildChat(player, String.join(" ", args));
    }

    /** Wird vom /gc CommandExecutor aufgerufen. */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                              @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MM.deserialize("<red>Nur für Spieler verfügbar."));
            return true;
        }
        if (args.length == 0) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /gc <nachricht>"));
            return true;
        }
        sendGuildChat(player, String.join(" ", args));
        return true;
    }

    private void sendGuildChat(Player player, String message) {
        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId()).thenAcceptAsync(opt -> {
            if (opt.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                return;
            }
            var guild = opt.get();
            String formatted = "<dark_gray>[<gradient:#FFD700:#FFA500>Gilde</gradient>]</dark_gray> " +
                    "<white>" + player.getName() + "</white><gray>: " + message;

            // An alle online Gildenmitglieder senden
            guild.getMembers().forEach(m -> {
                var online = plugin.getServer().getPlayer(m.getPlayerUUID());
                if (online != null) {
                    online.sendMessage(MM.deserialize(formatted));
                }
            });

            // Via Redis an andere Server weiterleiten
            var broker = plugin.getMessageBroker();
            if (broker != null) {
                broker.publish(com.festas.guilds.common.redis.RedisMessageBroker.GuildEvent.GUILD_CHAT,
                        guild.getId() + ":" + player.getName() + ":" + message);
            }
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }
}
