package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * /guild kick <spieler>
 */
public class GuildKickCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildKickCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /guild kick <spieler>"));
            return;
        }

        GuildAPI api = GuildAPI.getInstance();
        api.getPlayerGuild(player.getUniqueId()).thenAcceptAsync(guildOpt -> {
            if (guildOpt.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                return;
            }
            var guild = guildOpt.get();
            var kicker = guild.getMember(player.getUniqueId());
            if (kicker.isEmpty() || !kicker.get().getRank().canKick()) {
                player.sendMessage(MM.deserialize("<red>Du hast keine Berechtigung, Spieler rauszuwerfen!"));
                return;
            }

            // Ziel-Spieler suchen (online oder offline via Name)
            var targetPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetPlayer == null) {
                // Offline-Spieler
                var offTarget = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (offTarget == null) {
                    player.sendMessage(MM.deserialize("<red>Spieler nicht gefunden!"));
                    return;
                }
                var targetMember = guild.getMember(offTarget.getUniqueId());
                if (targetMember.isEmpty()) {
                    player.sendMessage(MM.deserialize("<red>" + args[0] + " ist kein Mitglied deiner Gilde!"));
                    return;
                }
                if (targetMember.get().getRank().getLevel() >= kicker.get().getRank().getLevel()) {
                    player.sendMessage(MM.deserialize("<red>Du kannst diesen Spieler nicht rauswerfen!"));
                    return;
                }
                api.removeMember(guild.getId(), offTarget.getUniqueId()).thenAcceptAsync(
                        success -> notifyKick(player, guild, args[0], success),
                        r -> plugin.getServer().getScheduler().runTask(plugin, r));
            } else {
                var targetMember = guild.getMember(targetPlayer.getUniqueId());
                if (targetMember.isEmpty()) {
                    player.sendMessage(MM.deserialize("<red>" + args[0] + " ist kein Mitglied deiner Gilde!"));
                    return;
                }
                if (targetMember.get().getRank().getLevel() >= kicker.get().getRank().getLevel()) {
                    player.sendMessage(MM.deserialize("<red>Du kannst diesen Spieler nicht rauswerfen!"));
                    return;
                }
                api.removeMember(guild.getId(), targetPlayer.getUniqueId()).thenAcceptAsync(success -> {
                    if (success) {
                        targetPlayer.sendMessage(MM.deserialize(
                                "<red>Du wurdest aus der Gilde <gold>" + guild.getName() + "</gold> entfernt!"));
                    }
                    notifyKick(player, guild, targetPlayer.getName(), success);
                }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
            }
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }

    private void notifyKick(Player player, com.festas.guilds.api.Guild guild, String targetName, boolean success) {
        if (success) {
            player.sendMessage(MM.deserialize("<red>" + targetName + " wurde aus der Gilde entfernt!"));
            guild.getMembers().forEach(m -> {
                var online = plugin.getServer().getPlayer(m.getPlayerUUID());
                if (online != null) {
                    online.sendMessage(MM.deserialize("<red>" + targetName + " wurde aus der Gilde entfernt."));
                }
            });
        } else {
            player.sendMessage(MM.deserialize("<red>Fehler beim Rauswerfen!"));
        }
    }
}
