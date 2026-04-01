package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * /guild invite <spieler>
 */
public class GuildInviteCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildInviteCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /guild invite <spieler>"));
            return;
        }

        GuildAPI api = GuildAPI.getInstance();
        api.getPlayerGuild(player.getUniqueId()).thenAcceptAsync(guildOpt -> {
            if (guildOpt.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                return;
            }
            var guild = guildOpt.get();
            var member = guild.getMember(player.getUniqueId());
            if (member.isEmpty() || !member.get().getRank().canInvite()) {
                player.sendMessage(MM.deserialize("<red>Du hast keine Berechtigung, Spieler einzuladen!"));
                return;
            }
            if (guild.isFull()) {
                player.sendMessage(MM.deserialize("<red>Die Gilde ist voll!"));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(MM.deserialize("<red>Spieler nicht gefunden oder offline!"));
                return;
            }
            if (target.equals(player)) {
                player.sendMessage(MM.deserialize("<red>Du kannst dich nicht selbst einladen!"));
                return;
            }

            api.invitePlayer(guild.getId(), player.getUniqueId(), target.getUniqueId())
                    .thenAcceptAsync(success -> {
                        if (success) {
                            player.sendMessage(MM.deserialize(
                                    "<green>" + target.getName() + " wurde in die Gilde eingeladen!"));
                            target.sendMessage(MM.deserialize(
                                    "<gold>" + guild.getName() + "</gold> hat dich eingeladen! " +
                                    "<click:run_command:/guild accept " + guild.getName() + ">" +
                                    "<green>[Annehmen]</click> " +
                                    "<click:run_command:/guild decline " + guild.getName() + ">" +
                                    "<red>[Ablehnen]</click>"));
                        } else {
                            player.sendMessage(MM.deserialize(
                                    "<red>Einladung fehlgeschlagen. Spieler könnte bereits in einer Gilde sein."));
                        }
                    }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }
}
