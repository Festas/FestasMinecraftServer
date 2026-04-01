package com.festas.guilds.common.model;

import com.festas.guilds.api.GuildBank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementierung des GuildBank-Interfaces mit Thread-sicherer Balanceverwaltung.
 */
public class GuildBankImpl implements GuildBank {

    private final String guildId;
    private final Map<String, Double> balances = new ConcurrentHashMap<>();

    public GuildBankImpl(String guildId) {
        this.guildId = guildId;
    }

    @Override
    public String getGuildId() {
        return guildId;
    }

    @Override
    public double getBalance(String currency) {
        return balances.getOrDefault(currency, 0.0);
    }

    @Override
    public Map<String, Double> getAllBalances() {
        return Collections.unmodifiableMap(new HashMap<>(balances));
    }

    @Override
    public synchronized boolean deposit(String currency, double amount) {
        if (amount <= 0) return false;
        balances.merge(currency, amount, Double::sum);
        return true;
    }

    @Override
    public synchronized boolean withdraw(String currency, double amount) {
        if (amount <= 0) return false;
        double current = getBalance(currency);
        if (current < amount) return false;
        balances.put(currency, current - amount);
        return true;
    }

    @Override
    public String toString() {
        return "GuildBank{guildId='" + guildId + "', balances=" + balances + "}";
    }
}
