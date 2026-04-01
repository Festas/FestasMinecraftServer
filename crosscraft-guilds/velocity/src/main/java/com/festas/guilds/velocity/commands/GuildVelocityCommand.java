package com.festas.guilds.velocity.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.festas.guilds.velocity.GuildsVelocityPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;

/**
 * Proxy-Level /guild Befehl.
 * Delegiert die meisten Befehle an den Backend-Server.
 * Verwaltet nur Befehle die auf Proxy-Ebene sinnvoll sind.
 */
public class GuildVelocityCommand implements RawCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();

    private final ProxyServer server;
    private final GuildsVelocityPlugin plugin;

    public GuildVelocityCommand(ProxyServer server, GuildsVelocityPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        var source = invocation.source();
        String arguments = invocation.arguments();

        if (!(source instanceof Player player)) {
            source.sendMessage(MM.deserialize("<red>Dieser Befehl ist nur für Spieler verfügbar."));
            return;
        }

        // Wenn kein Argument - Hilfenachricht anzeigen
        if (arguments.isBlank()) {
            showProxyHelp(player);
            return;
        }

        String[] args = arguments.split(" ");
        String sub = args[0].toLowerCase();

        switch (sub) {
            case "chat", "gc" -> {
                // Direkt an Redis senden
                if (args.length < 2) {
                    player.sendMessage(MM.deserialize("<red>Verwendung: /guild chat <nachricht>"));
                    return;
                }
                String message = arguments.substring(sub.length()).trim();
                var chatHandler = plugin.getChatHandlerOrNull();
                if (chatHandler != null) {
                    // Guild-ID müsste aus Cache kommen - Stub
                    player.sendMessage(MM.deserialize("<yellow>Gildenchat wird weitergeleitet..."));
                } else {
                    player.sendMessage(MM.deserialize("<red>Cross-Server-Chat nicht verfügbar (Redis offline)."));
                }
            }
            default -> {
                // Alle anderen Befehle werden zum Backend delegiert
                // (Der Backend-Server hat den /guild Befehl registriert)
                player.sendMessage(MM.deserialize(
                        "<gray>Verbinde dich mit einem Spielserver um Gilden-Befehle zu nutzen."));
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return List.of("chat", "info", "list");
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return true;
    }

    private void showProxyHelp(Player player) {
        player.sendMessage(MM.deserialize("""
                <gradient:#FFD700:#FFA500>⚔ CrossCraft Gilden</gradient>
                <gray>» Verbinde dich mit einem Spielserver für alle Gilden-Funktionen.
                » <gold>/guild chat <nachricht></gold> - Cross-Server Gildenchat
                » <gold>/guild info</gold> - Gildeninfo anzeigen
                """));
    }
}
