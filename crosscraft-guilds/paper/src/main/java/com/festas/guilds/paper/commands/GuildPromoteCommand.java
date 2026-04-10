package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * /guild promote <spieler> und /guild demote <spieler>
 */
public class GuildPromoteCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildPromoteCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void executePromote(Player player, String[] args) {
        execute(player, args, true);
    }

    public void executeDemote(Player player, String[] args) {
        execute(player, args, false);
    }

    private void execute(Player player, String[] args, boolean promote) {
        if (args.length < 1) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /guild " +
                    (promote ? "promote" : "demote") + " <spieler>"));
            return;
        }

        GuildAPI api = GuildAPI.getInstance();
        api.getPlayerGuild(player.getUniqueId()).thenAcceptAsync(guildOpt -> {
            if (guildOpt.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                return;
            }
            var guild = guildOpt.get();
            var executor = guild.getMember(player.getUniqueId());
            if (executor.isEmpty() || !executor.get().getRank().canPromote()) {
                player.sendMessage(MM.deserialize("<red>Du hast keine Berechtigung!"));
                return;
            }

            var targetOffline = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (targetOffline == null) {
                player.sendMessage(MM.deserialize("<red>Spieler nicht gefunden!"));
                return;
            }
            var targetMember = guild.getMember(targetOffline.getUniqueId());
            if (targetMember.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>" + args[0] + " ist kein Mitglied deiner Gilde!"));
                return;
            }

            GuildRank currentRank = targetMember.get().getRank();
            GuildRank newRank;

            if (promote) {
                if (currentRank == GuildRank.OFFICER) {
                    // Beförderung zum Leader erfordert dass der aktuelle Leader degradiert wird
                    if (executor.get().getRank() != GuildRank.LEADER) {
                        player.sendMessage(MM.deserialize("<red>Nur der Anführer kann jemanden befördern!"));
                        return;
                    }
                    newRank = GuildRank.LEADER;
                    // Den aktuellen Leader zu Officer degradieren
                    api.setMemberRank(guild.getId(), player.getUniqueId(), GuildRank.OFFICER);
                } else {
                    newRank = getNextRank(currentRank, true);
                }
            } else {
                if (currentRank == GuildRank.LEADER) {
                    player.sendMessage(MM.deserialize("<red>Der Anführer kann nicht degradiert werden!"));
                    return;
                }
                newRank = getNextRank(currentRank, false);
            }

            if (newRank == currentRank) {
                player.sendMessage(MM.deserialize("<red>Rang kann nicht weiter " +
                        (promote ? "erhöht" : "gesenkt") + " werden!"));
                return;
            }

            GuildRank finalNewRank = newRank;
            String targetName = args[0];
            api.setMemberRank(guild.getId(), targetOffline.getUniqueId(), newRank)
                    .thenAcceptAsync(success -> {
                        if (success) {
                            player.sendMessage(MM.deserialize(
                                    "<green>" + targetName + " wurde zu <gold>" +
                                    finalNewRank.getDisplayName() + "</gold> " +
                                    (promote ? "befördert" : "degradiert") + "!"));
                            var online = plugin.getServer().getPlayer(targetOffline.getUniqueId());
                            if (online != null) {
                                online.sendMessage(MM.deserialize(
                                        "<gold>Dein Rang wurde zu <white>" +
                                        finalNewRank.getDisplayName() + "</white> geändert!"));
                            }
                        } else {
                            player.sendMessage(MM.deserialize("<red>Rang konnte nicht geändert werden!"));
                        }
                    }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }

    private GuildRank getNextRank(GuildRank current, boolean up) {
        return switch (current) {
            case RECRUIT -> up ? GuildRank.MEMBER : GuildRank.RECRUIT;
            case MEMBER -> up ? GuildRank.OFFICER : GuildRank.RECRUIT;
            case OFFICER -> up ? GuildRank.LEADER : GuildRank.MEMBER;
            case LEADER -> up ? GuildRank.LEADER : GuildRank.OFFICER;
        };
    }
}
