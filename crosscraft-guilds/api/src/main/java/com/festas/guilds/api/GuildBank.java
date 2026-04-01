package com.festas.guilds.api;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Represents the bank of a guild, storing currency balances.
 */
public interface GuildBank {

    /**
     * Returns the guild ID this bank belongs to.
     */
    String getGuildId();

    /**
     * Returns the balance for a given currency.
     */
    double getBalance(String currency);

    /**
     * Returns all balances as an unmodifiable map.
     */
    Map<String, Double> getAllBalances();

    /**
     * Deposits an amount into the bank for the given currency.
     *
     * @return true if deposit was successful
     */
    boolean deposit(String currency, double amount);

    /**
     * Withdraws an amount from the bank for the given currency.
     *
     * @return true if withdrawal was successful (sufficient funds)
     */
    boolean withdraw(String currency, double amount);

    /**
     * Checks whether the bank has at least the given amount of currency.
     */
    default boolean hasBalance(String currency, double amount) {
        return getBalance(currency) >= amount;
    }

    /**
     * Returns the total deposited across all currencies (informational).
     */
    default double getTotalValue() {
        return getAllBalances().values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
