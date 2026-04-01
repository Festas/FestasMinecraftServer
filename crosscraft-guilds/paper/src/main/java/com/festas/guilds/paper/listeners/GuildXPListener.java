package com.festas.guilds.paper.listeners;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

/**
 * Verfolgt XP-Events und vergibt Gilden-XP.
 */
public class GuildXPListener implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    // XP-Mengen pro Event
    private static final long XP_PER_MOB_KILL = 10L;
    private static final long XP_PER_LEVEL_UP = 100L;

    public GuildXPListener(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    /** Vergibt Gilden-XP wenn ein Spieler ein Monster tötet. */
    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player player)) return;

        // Basis-XP basierend auf der Vanilla XP-Belohnung skalieren
        long xp = Math.max(XP_PER_MOB_KILL, event.getDroppedExp() / 2L);

        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                .thenCompose(opt -> {
                    if (opt.isEmpty()) return java.util.concurrent.CompletableFuture.completedFuture(false);
                    return GuildAPI.getInstance().awardXP(opt.get().getId(), player.getUniqueId(), xp);
                })
                .thenAcceptAsync(leveledUp -> {
                    if (leveledUp) {
                        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                                .thenAcceptAsync(opt -> opt.ifPresent(guild ->
                                        broadcastLevelUp(guild.getName(), guild.getLevel())),
                                        r -> plugin.getServer().getScheduler().runTask(plugin, r));
                    }
                });
    }

    /** Vergibt Gilden-XP wenn ein Spieler ein Level aufsteigt. */
    @EventHandler
    public void onPlayerLevelUp(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() <= event.getOldLevel()) return;
        Player player = event.getPlayer();

        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                .thenCompose(opt -> {
                    if (opt.isEmpty()) return java.util.concurrent.CompletableFuture.completedFuture(false);
                    return GuildAPI.getInstance().awardXP(opt.get().getId(), player.getUniqueId(), XP_PER_LEVEL_UP);
                })
                .thenAcceptAsync(leveledUp -> {
                    if (leveledUp) {
                        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                                .thenAcceptAsync(opt -> opt.ifPresent(guild ->
                                        broadcastLevelUp(guild.getName(), guild.getLevel())),
                                        r -> plugin.getServer().getScheduler().runTask(plugin, r));
                    }
                });
    }

    private void broadcastLevelUp(String guildName, int newLevel) {
        plugin.getServer().broadcast(MM.deserialize(
                "<gradient:#FFD700:#FFA500>⚔ Die Gilde <white>" + guildName +
                "</white> ist auf Level <white>" + newLevel + "</white> aufgestiegen! 🎉"));
    }
}
