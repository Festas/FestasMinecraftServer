package com.festas.guilds.common.model;

import com.festas.guilds.api.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Berechnet XP-Anforderungen und verwaltet Level-Aufstiege für Gilden.
 */
public class GuildLevelManager {

    private static final Logger log = LoggerFactory.getLogger(GuildLevelManager.class);

    private final int maxLevel;

    public GuildLevelManager(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * Berechnet das benötigte XP für einen bestimmten Level.
     * Formel: 1000 * level^1.5
     */
    public long xpRequiredForLevel(int level) {
        if (level <= 1) return 0;
        return (long) (1000.0 * Math.pow(level, 1.5));
    }

    /**
     * Berechnet das XP das insgesamt für Level `level` benötigt wird (kumulativ).
     */
    public long totalXPForLevel(int level) {
        long total = 0;
        for (int i = 2; i <= level; i++) {
            total += xpRequiredForLevel(i);
        }
        return total;
    }

    /**
     * Ermittelt den Level basierend auf der Gesamt-XP.
     */
    public int calculateLevel(long totalXP) {
        int level = 1;
        while (level < maxLevel && totalXP >= totalXPForLevel(level + 1)) {
            level++;
        }
        return level;
    }

    /**
     * Gibt zurück wie viel XP noch zum nächsten Level fehlen.
     */
    public long xpToNextLevel(Guild guild) {
        if (guild.getLevel() >= maxLevel) return 0;
        return totalXPForLevel(guild.getLevel() + 1) - guild.getXP();
    }

    /**
     * Verarbeitet einen XP-Zuwachs und gibt zurück ob ein Level-Aufstieg stattgefunden hat.
     *
     * @return true wenn ein Level-Aufstieg eingetreten ist
     */
    public boolean processXP(Guild guild, long xpGained) {
        guild.addXP(xpGained);
        int newLevel = calculateLevel(guild.getXP());
        if (newLevel > guild.getLevel() && newLevel <= maxLevel) {
            log.info("Gilde {} steigt auf Level {} auf!", guild.getName(), newLevel);
            guild.setLevel(newLevel);
            applyLevelRewards(guild, newLevel);
            return true;
        }
        return false;
    }

    /**
     * Wendet Belohnungen für das neue Level an.
     */
    private void applyLevelRewards(Guild guild, int level) {
        // Mitglieder-Kapazität automatisch erhöhen basierend auf Level
        int bonusMembers = switch (level) {
            case 5 -> 0;
            case 10 -> 0;
            case 15 -> 0;
            case 20 -> 10;
            case 25 -> 0;
            case 30 -> 5;
            case 40 -> 10;
            case 50 -> 15;
            default -> 0;
        };

        if (bonusMembers > 0) {
            guild.setMaxMembers(guild.getMaxMembers() + bonusMembers);
            log.info("Gilde {} erhält {} zusätzliche Mitgliederplätze (Level {})",
                    guild.getName(), bonusMembers, level);
        }
    }

    /**
     * Gibt das XP-Fortschritt in Prozent zurück (0-100).
     */
    public double progressPercent(Guild guild) {
        if (guild.getLevel() >= maxLevel) return 100.0;
        long currentLevelXP = totalXPForLevel(guild.getLevel());
        long nextLevelXP = totalXPForLevel(guild.getLevel() + 1);
        long progress = guild.getXP() - currentLevelXP;
        long needed = nextLevelXP - currentLevelXP;
        if (needed <= 0) return 100.0;
        return Math.min(100.0, (double) progress / needed * 100.0);
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
