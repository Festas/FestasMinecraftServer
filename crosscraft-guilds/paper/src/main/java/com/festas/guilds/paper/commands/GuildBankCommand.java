package com.festas.guilds.paper.commands;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

/**
 * /guild bank [deposit|withdraw] [währung] [betrag]
 */
public class GuildBankCommand {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;

    public GuildBankCommand(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            // Gildenbank GUI öffnen
            GuildAPI.getInstance().getPlayerGuild(player.getUniqueId()).thenAcceptAsync(opt -> {
                if (opt.isEmpty()) {
                    player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                    return;
                }
                new com.festas.guilds.paper.gui.GuildBankGUI(plugin, player, opt.get()).open();
            }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
            return;
        }

        String action = args[0].toLowerCase();
        if (!action.equals("deposit") && !action.equals("withdraw")) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /guild bank [deposit|withdraw] [währung] [betrag]"));
            return;
        }

        if (args.length < 3) {
            player.sendMessage(MM.deserialize("<red>Verwendung: /guild bank " + action + " <währung> <betrag>"));
            return;
        }

        String currency = args[1];
        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            player.sendMessage(MM.deserialize("<red>Ungültiger Betrag!"));
            return;
        }

        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId()).thenAcceptAsync(opt -> {
            if (opt.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                return;
            }
            var guild = opt.get();
            var member = guild.getMember(player.getUniqueId());
            if (member.isEmpty() || !member.get().getRank().canManageBank()) {
                // Einzahlung ist für alle erlaubt, Auszahlung nur für Offiziere+
                if (action.equals("withdraw")) {
                    player.sendMessage(MM.deserialize("<red>Du hast keine Berechtigung für Auszahlungen!"));
                    return;
                }
            }

            var bank = guild.getBank();
            if (action.equals("deposit")) {
                // TODO: Coins-Integration via CoinsEngineHook
                boolean success = bank.deposit(currency, amount);
                if (success) {
                    plugin.getGuildRepository().logBankTransaction(
                            guild.getId(), player.getUniqueId(), "DEPOSIT", currency, amount);
                    player.sendMessage(MM.deserialize(
                            "<green>Erfolgreich <gold>" + amount + " " + currency +
                            "</gold> in die Gildenkasse eingezahlt!"));
                } else {
                    player.sendMessage(MM.deserialize("<red>Einzahlung fehlgeschlagen!"));
                }
            } else {
                boolean success = bank.withdraw(currency, amount);
                if (success) {
                    plugin.getGuildRepository().logBankTransaction(
                            guild.getId(), player.getUniqueId(), "WITHDRAW", currency, amount);
                    player.sendMessage(MM.deserialize(
                            "<green>Erfolgreich <gold>" + amount + " " + currency +
                            "</gold> aus der Gildenkasse abgehoben!"));
                } else {
                    player.sendMessage(MM.deserialize(
                            "<red>Nicht genug Guthaben in der Gildenkasse! " +
                            "(Aktuell: " + bank.getBalance(currency) + " " + currency + ")"));
                }
            }
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }
}
