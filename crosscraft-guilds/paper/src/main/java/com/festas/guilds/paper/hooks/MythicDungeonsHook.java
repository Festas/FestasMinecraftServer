package com.festas.guilds.paper.hooks;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * MythicDungeons-Integration: Gilden erhalten Bonus-XP bei Dungeon-Abschlüssen.
 */
public class MythicDungeonsHook implements Listener {

    private final GuildsPaperPlugin plugin;
    private static final long DUNGEON_COMPLETION_XP = 1000L;

    public MythicDungeonsHook(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Vergib XP an alle Gilden der Spieler, die an einem Dungeon teilgenommen haben.
     *
     * @param participants Spieler die den Dungeon abgeschlossen haben
     * @param dungeonName  Name des Dungeons
     * @param difficulty   Schwierigkeitsgrad (1-5)
     */
    public void onDungeonComplete(Iterable<Player> participants, String dungeonName, int difficulty) {
        long xp = DUNGEON_COMPLETION_XP * difficulty;

        for (Player player : participants) {
            GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                    .thenAcceptAsync(opt -> opt.ifPresent(guild -> {
                        GuildAPI.getInstance().awardXP(guild.getId(), player.getUniqueId(), xp);
                        plugin.getServer().getScheduler().runTask(plugin, () ->
                                player.sendMessage(
                                        net.kyori.adventure.text.minimessage.MiniMessage.miniMessage()
                                                .deserialize("<gold>⚔ Deine Gilde erhält <white>" + xp +
                                                        "</white> XP für den Dungeon-Abschluss!")));
                    }));
        }
    }

    /**
     * Gibt die Basis-XP für einen Dungeon zurück.
     */
    public long getDungeonXP(String dungeonName, int difficulty) {
        return DUNGEON_COMPLETION_XP * difficulty;
    }
}
