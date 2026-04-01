package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

/**
 * /guild leave - Gilde verlassen
 */
public class GuildLeaveCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildLeaveCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        GuildAPI api = GuildAPI.getInstance();

        api.getPlayerGuild(player.getUniqueId()).thenAcceptAsync(opt -> {
            if (opt.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                return;
            }
            var guild = opt.get();
            var member = guild.getMember(player.getUniqueId());

            if (member.isPresent() && member.get().getRank() == GuildRank.LEADER) {
                player.sendMessage(MM.deserialize(
                        "<red>Als Anführer kannst du die Gilde nicht verlassen. " +
                        "Nutze <gold>/guild disband</gold> um sie aufzulösen, oder " +
                        "befördere zuerst ein anderes Mitglied zum Anführer."));
                return;
            }

            api.removeMember(guild.getId(), player.getUniqueId()).thenAcceptAsync(success -> {
                if (success) {
                    player.sendMessage(MM.deserialize(
                            "<yellow>Du hast die Gilde <gold>" + guild.getName() + "</gold> verlassen."));
                    // Online-Mitglieder benachrichtigen
                    guild.getMembers().forEach(m -> {
                        var online = plugin.getServer().getPlayer(m.getPlayerUUID());
                        if (online != null) {
                            online.sendMessage(MM.deserialize(
                                    "<yellow>" + player.getName() + " hat die Gilde verlassen."));
                        }
                    });
                } else {
                    player.sendMessage(MM.deserialize("<red>Fehler beim Verlassen der Gilde!"));
                }
            }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }
}
