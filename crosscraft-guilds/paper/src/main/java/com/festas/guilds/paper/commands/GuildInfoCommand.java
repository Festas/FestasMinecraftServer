package com.festas.guilds.paper.commands;

import com.festas.guilds.api.Guild;
import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildMember;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * /guild info [gilde]
 */
public class GuildInfoCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildInfoCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        GuildAPI api = GuildAPI.getInstance();

        if (args.length >= 1) {
            // Gilde nach Name suchen
            api.getGuildByName(args[0]).thenAcceptAsync(opt -> {
                if (opt.isEmpty()) {
                    player.sendMessage(MM.deserialize("<red>Gilde nicht gefunden!"));
                    return;
                }
                showInfo(player, opt.get());
            }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
        } else {
            // Eigene Gilde
            api.getPlayerGuild(player.getUniqueId()).thenAcceptAsync(opt -> {
                if (opt.isEmpty()) {
                    player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                    return;
                }
                showInfo(player, opt.get());
            }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
        }
    }

    private void showInfo(Player player, Guild guild) {
        long xpToNext = plugin.getLevelManager().xpToNextLevel(guild);
        double progress = plugin.getLevelManager().progressPercent(guild);
        String leader = guild.getLeader()
                .map(m -> {
                    var offPlayer = Bukkit.getOfflinePlayer(m.getPlayerUUID());
                    return offPlayer.getName() != null ? offPlayer.getName() : "Unbekannt";
                }).orElse("Unbekannt");

        player.sendMessage(MM.deserialize("""
                <gradient:#FFD700:#FFA500>══════════════════════════</gradient>
                <gold>Gilde: <white>%s</white> [<aqua>%s</aqua>]
                <gold>Level: <white>%d</white> <gray>(%.1f%%)</gray>
                <gold>XP: <white>%d</white> <gray>(noch %d bis Level %d)</gray>
                <gold>Anführer: <white>%s</white>
                <gold>Mitglieder: <white>%d/%d</white>
                <gold>Beschreibung: <white>%s</white>
                <gradient:#FFD700:#FFA500>══════════════════════════</gradient>
                """.formatted(
                guild.getName(), guild.getTag(),
                guild.getLevel(), progress,
                guild.getXP(), xpToNext, guild.getLevel() + 1,
                leader,
                guild.getMemberCount(), guild.getMaxMembers(),
                guild.getDescription().isBlank() ? "Keine Beschreibung" : guild.getDescription()
        )));
    }
}
