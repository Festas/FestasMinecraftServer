package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

/**
 * /guild create <name> [tag]
 */
public class GuildCreateCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildCreateCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /guild create <name> [tag]"));
            return;
        }

        String name = args[0];
        String tag = args.length >= 2 ? args[1] : name.substring(0, Math.min(4, name.length())).toUpperCase();

        var cfg = plugin.getGuildConfig();

        if (name.length() < cfg.getMinNameLength() || name.length() > cfg.getMaxNameLength()) {
            player.sendMessage(MM.deserialize("<red>Der Gildenname muss zwischen " +
                    cfg.getMinNameLength() + " und " + cfg.getMaxNameLength() + " Zeichen lang sein."));
            return;
        }

        if (tag.length() < cfg.getMinTagLength() || tag.length() > cfg.getMaxTagLength()) {
            player.sendMessage(MM.deserialize("<red>Der Gildentag muss zwischen " +
                    cfg.getMinTagLength() + " und " + cfg.getMaxTagLength() + " Zeichen lang sein."));
            return;
        }

        player.sendMessage(MM.deserialize("<yellow>Gilde wird gegründet..."));

        GuildAPI.getInstance().createGuild(player.getUniqueId(), name, tag)
                .thenAcceptAsync(opt -> {
                    if (opt.isPresent()) {
                        player.sendMessage(MM.deserialize(
                                "<green>Die Gilde <gold>" + name + "</gold> wurde erfolgreich gegründet!"));
                    } else {
                        player.sendMessage(MM.deserialize(
                                "<red>Gilde konnte nicht gegründet werden. " +
                                "Name/Tag bereits vergeben oder du bist bereits in einer Gilde!"));
                    }
                }, runnable -> plugin.getServer().getScheduler().runTask(plugin, runnable));
    }
}
