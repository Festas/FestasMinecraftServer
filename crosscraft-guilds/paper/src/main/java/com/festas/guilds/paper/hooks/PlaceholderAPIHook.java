package com.festas.guilds.paper.hooks;

import com.festas.guilds.api.Guild;
import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildMember;
import com.festas.guilds.paper.GuildsPaperPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * PlaceholderAPI-Integration: Registriert %guild_*% Platzhalter.
 *
 * Verfügbare Platzhalter:
 * - %guild_name%       - Gildenname
 * - %guild_tag%        - Gildentag
 * - %guild_level%      - Gildenlevel
 * - %guild_rank%       - Spieler-Rang in der Gilde
 * - %guild_members%    - Anzahl der Mitglieder
 * - %guild_max_members% - Maximale Mitgliederanzahl
 * - %guild_xp%         - Aktuelle Gilden-XP
 * - %guild_leader%     - Name des Anführers
 */
public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final GuildsPaperPlugin plugin;

    public PlaceholderAPIHook(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        super.register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "guild";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Festas";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) return "";

        try {
            Optional<Guild> guildOpt = GuildAPI.getInstance()
                    .getPlayerGuild(player.getUniqueId())
                    .get(2, TimeUnit.SECONDS);

            if (guildOpt.isEmpty()) {
                return switch (params.toLowerCase()) {
                    case "name" -> "Keine Gilde";
                    case "tag" -> "-";
                    case "level" -> "0";
                    case "rank" -> "-";
                    case "members" -> "0";
                    case "max_members" -> "0";
                    case "xp" -> "0";
                    case "leader" -> "-";
                    default -> "";
                };
            }

            Guild guild = guildOpt.get();
            Optional<GuildMember> memberOpt = guild.getMember(player.getUniqueId());

            return switch (params.toLowerCase()) {
                case "name" -> guild.getName();
                case "tag" -> guild.getTag();
                case "level" -> String.valueOf(guild.getLevel());
                case "rank" -> memberOpt.map(m -> m.getRank().getDisplayName()).orElse("-");
                case "members" -> String.valueOf(guild.getMemberCount());
                case "max_members" -> String.valueOf(guild.getMaxMembers());
                case "xp" -> String.valueOf(guild.getXP());
                case "leader" -> guild.getLeader().map(m -> {
                    var op = plugin.getServer().getOfflinePlayer(m.getPlayerUUID());
                    return op.getName() != null ? op.getName() : "Unbekannt";
                }).orElse("-");
                case "progress" -> String.format("%.1f%%",
                        plugin.getLevelManager().progressPercent(guild));
                default -> "";
            };
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return "";
        }
    }
}
