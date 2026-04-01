package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * /guild disband - Gilde auflösen (mit Bestätigung)
 */
public class GuildDisbandCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;
    // UUID -> Zeitstempel der Bestätigungsanfrage
    private final Map<UUID, Long> pendingConfirmations = new ConcurrentHashMap<>();

    public GuildDisbandCommand(GuildsPaperPlugin plugin) {
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

            if (member.isEmpty() || member.get().getRank() != GuildRank.LEADER) {
                player.sendMessage(MM.deserialize("<red>Nur der Anführer kann die Gilde auflösen!"));
                return;
            }

            int confirmSeconds = plugin.getGuildConfig().getDisbandConfirmationSeconds();
            Long lastRequest = pendingConfirmations.get(player.getUniqueId());

            if (lastRequest != null && System.currentTimeMillis() - lastRequest < confirmSeconds * 1000L) {
                // Bestätigung erhalten - Gilde auflösen
                pendingConfirmations.remove(player.getUniqueId());
                api.disbandGuild(guild.getId()).thenAcceptAsync(success -> {
                    if (success) {
                        player.sendMessage(MM.deserialize(
                                "<red>Die Gilde <gold>" + guild.getName() + "</gold> wurde aufgelöst."));
                    } else {
                        player.sendMessage(MM.deserialize("<red>Fehler beim Auflösen der Gilde!"));
                    }
                }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
            } else {
                // Erste Anfrage - um Bestätigung bitten
                pendingConfirmations.put(player.getUniqueId(), System.currentTimeMillis());
                player.sendMessage(MM.deserialize(
                        "<red>⚠ Bist du sicher, dass du die Gilde <gold>" + guild.getName() +
                        "</gold> auflösen möchtest?\n" +
                        "<red>Schreibe <gold>/guild disband</gold> erneut innerhalb von " +
                        confirmSeconds + " Sekunden zur Bestätigung."));

                // Bestätigung nach Ablauf löschen
                plugin.getServer().getScheduler().runTaskLater(plugin,
                        () -> pendingConfirmations.remove(player.getUniqueId()),
                        confirmSeconds * 20L);
            }
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }
}
