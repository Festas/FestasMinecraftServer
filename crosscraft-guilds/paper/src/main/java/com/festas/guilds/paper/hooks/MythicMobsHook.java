package com.festas.guilds.paper.hooks;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * MythicMobs-Integration: Gilden erhalten XP beim Töten von MythicMobs Bossen.
 */
public class MythicMobsHook implements Listener {

    private final GuildsPaperPlugin plugin;

    // XP-Belohnungen nach MythicMob-Typ
    private static final long DEFAULT_MOB_XP = 50L;
    private static final long BOSS_XP = 500L;

    public MythicMobsHook(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Gibt die XP-Belohnung für einen bestimmten MythicMob-Typ zurück.
     * Bosse (Mobs mit Level >= 50) geben mehr XP.
     */
    public long getXPReward(String mobType, int level) {
        // Echte Implementierung würde MythicMobs API aufrufen
        // io.lumine.mythic.bukkit.MythicBukkit.inst().getMobManager().getMythicMob(mobType)
        if (level >= 50) {
            return BOSS_XP;
        }
        return DEFAULT_MOB_XP + (level * 5L);
    }

    /**
     * Vergibt XP an die Gilde des Spielers nach einem Boss-Kill.
     */
    public void awardBossKillXP(Player player, String mobType, int level) {
        long xp = getXPReward(mobType, level);
        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                .thenAcceptAsync(opt -> opt.ifPresent(guild ->
                        GuildAPI.getInstance().awardXP(guild.getId(), player.getUniqueId(), xp)
                                .thenAcceptAsync(leveled -> {
                                    if (leveled) {
                                        plugin.getServer().getScheduler().runTask(plugin, () ->
                                                player.sendMessage(
                                                        net.kyori.adventure.text.minimessage.MiniMessage.miniMessage()
                                                                .deserialize("<gold>⚔ Deine Gilde ist aufgestiegen!")));
                                    }
                                })
                ));
    }
}
