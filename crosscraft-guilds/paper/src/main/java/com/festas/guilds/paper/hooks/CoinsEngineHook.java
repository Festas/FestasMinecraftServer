package com.festas.guilds.paper.hooks;

import com.festas.guilds.api.Guild;
import com.festas.guilds.paper.GuildsPaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * CoinsEngine-Integration: Verbindet die Gildenbank mit CoinsEngine-Währungen.
 */
public class CoinsEngineHook implements Listener {

    private final GuildsPaperPlugin plugin;

    public CoinsEngineHook(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Gibt das CoinsEngine-Guthaben eines Spielers für eine bestimmte Währung zurück.
     *
     * @param player   der Spieler
     * @param currency Währungsname (muss in CoinsEngine definiert sein)
     * @return Guthaben oder 0.0 wenn nicht verfügbar
     */
    public double getPlayerBalance(Player player, String currency) {
        // Echte Implementierung:
        // su.nightexpress.coinsengine.api.CoinsEngineAPI.getCurrencyManager().getCurrency(currency)
        //     .map(c -> CoinsEngineAPI.getBalance(player, c))
        //     .orElse(0.0)
        return 0.0;
    }

    /**
     * Zieht Geld vom Spieler ab (für Bank-Einzahlungen).
     *
     * @return true wenn erfolgreich
     */
    public boolean withdrawFromPlayer(Player player, String currency, double amount) {
        // Echte Implementierung würde CoinsEngine API aufrufen
        // um den Betrag vom Spieler abzuziehen
        return false;
    }

    /**
     * Gibt Geld an den Spieler (für Bank-Auszahlungen).
     *
     * @return true wenn erfolgreich
     */
    public boolean depositToPlayer(Player player, String currency, double amount) {
        // Echte Implementierung würde CoinsEngine API aufrufen
        return false;
    }

    /**
     * Verarbeitet eine Gildenbank-Einzahlung mit CoinsEngine.
     */
    public boolean processBankDeposit(Player player, Guild guild, String currency, double amount) {
        double playerBalance = getPlayerBalance(player, currency);
        if (playerBalance < amount) return false;

        boolean withdrawn = withdrawFromPlayer(player, currency, amount);
        if (!withdrawn) return false;

        return guild.getBank().deposit(currency, amount);
    }

    /**
     * Verarbeitet eine Gildenbank-Auszahlung mit CoinsEngine.
     */
    public boolean processBankWithdraw(Player player, Guild guild, String currency, double amount) {
        if (!guild.getBank().hasBalance(currency, amount)) return false;

        boolean withdrawn = guild.getBank().withdraw(currency, amount);
        if (!withdrawn) return false;

        depositToPlayer(player, currency, amount);
        return true;
    }
}
