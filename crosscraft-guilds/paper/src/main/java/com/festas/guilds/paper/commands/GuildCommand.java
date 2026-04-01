package com.festas.guilds.paper.commands;

import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Haupt-Router für den /guild Befehl.
 */
public class GuildCommand implements CommandExecutor, TabCompleter {

    private static final MiniMessage MM = MiniMessage.miniMessage();

    private final GuildsPaperPlugin plugin;
    private final GuildCreateCommand createCommand;
    private final GuildInviteCommand inviteCommand;
    private final GuildInfoCommand infoCommand;
    private final GuildChatCommand chatCommand;
    private final GuildBankCommand bankCommand;
    private final GuildLeaveCommand leaveCommand;
    private final GuildDisbandCommand disbandCommand;
    private final GuildListCommand listCommand;
    private final GuildKickCommand kickCommand;
    private final GuildPromoteCommand promoteCommand;

    public GuildCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
        this.createCommand = new GuildCreateCommand(plugin);
        this.inviteCommand = new GuildInviteCommand(plugin);
        this.infoCommand = new GuildInfoCommand(plugin);
        this.chatCommand = new GuildChatCommand(plugin);
        this.bankCommand = new GuildBankCommand(plugin);
        this.leaveCommand = new GuildLeaveCommand(plugin);
        this.disbandCommand = new GuildDisbandCommand(plugin);
        this.listCommand = new GuildListCommand(plugin);
        this.kickCommand = new GuildKickCommand(plugin);
        this.promoteCommand = new GuildPromoteCommand(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                              @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MM.deserialize("<red>Dieser Befehl ist nur für Spieler verfügbar."));
            return true;
        }

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (sub) {
            case "create", "erstellen" -> createCommand.execute(player, subArgs);
            case "invite", "einladen" -> inviteCommand.execute(player, subArgs);
            case "info" -> infoCommand.execute(player, subArgs);
            case "chat", "c" -> chatCommand.execute(player, subArgs);
            case "bank" -> bankCommand.execute(player, subArgs);
            case "leave", "verlassen" -> leaveCommand.execute(player, subArgs);
            case "disband", "auflösen" -> disbandCommand.execute(player, subArgs);
            case "list", "liste" -> listCommand.execute(player, subArgs);
            case "kick", "rauswerfen" -> kickCommand.execute(player, subArgs);
            case "promote", "befördern" -> promoteCommand.executePromote(player, subArgs);
            case "demote", "degradieren" -> promoteCommand.executeDemote(player, subArgs);
            case "menu", "menü" -> openMenu(player);
            default -> sendHelp(player);
        }
        return true;
    }

    private void openMenu(Player player) {
        new com.festas.guilds.paper.gui.GuildMainGUI(plugin, player).open();
    }

    private void sendHelp(Player player) {
        player.sendMessage(MM.deserialize("""
                <gradient:#FFD700:#FFA500>⚔ CrossCraft Gilden</gradient>
                <gray>» <gold>/guild create <name> [tag]</gold> - Neue Gilde gründen
                » <gold>/guild invite <spieler></gold> - Spieler einladen
                » <gold>/guild info [gilde]</gold> - Gildeninfo anzeigen
                » <gold>/guild chat <nachricht></gold> - Gildenchat
                » <gold>/guild bank [deposit|withdraw]</gold> - Gildenbank
                » <gold>/guild list</gold> - Alle Gilden auflisten
                » <gold>/guild leave</gold> - Gilde verlassen
                » <gold>/guild kick <spieler></gold> - Spieler rauswerfen
                » <gold>/guild promote/demote <spieler></gold> - Rang ändern
                » <gold>/guild disband</gold> - Gilde auflösen
                » <gold>/guild menu</gold> - Gilden-Menü öffnen
                """));
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                       @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("create", "invite", "info", "chat", "bank", "leave",
                    "disband", "list", "kick", "promote", "demote", "menu");
        }
        return List.of();
    }
}
