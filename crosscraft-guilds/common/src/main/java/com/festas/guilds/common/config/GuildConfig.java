package com.festas.guilds.common.config;

import java.io.*;
import java.util.Properties;

/**
 * Lädt und speichert die Gilden-Konfiguration für das Common-Modul.
 * Im Paper-Modul wird diese durch eine Bukkit-spezifische Implementierung erweitert.
 */
public class GuildConfig {

    private final Properties props = new Properties();

    // Datenbankstandards
    private String databaseHost = "localhost";
    private int databasePort = 3306;
    private String databaseName = "minecraft_guilds";
    private String databaseUsername = "root";
    private String databasePassword = "";
    private int databasePoolSize = 10;

    // Redis-Standards
    private String redisHost = "localhost";
    private int redisPort = 6379;
    private String redisPassword = "";
    private String redisChannel = "crosscraft-guilds";

    // Gilden-Standards
    private int minNameLength = 3;
    private int maxNameLength = 32;
    private int minTagLength = 2;
    private int maxTagLength = 8;
    private double creationCost = 10_000.0;
    private String creationCurrency = "money";
    private int maxMembersBase = 10;
    private int maxMembersPerLevel = 2;
    private int inviteExpireMinutes = 60;
    private int disbandConfirmationSeconds = 30;
    private int maxLevel = 50;

    public GuildConfig() {}

    /** Lädt die Konfiguration aus einer Properties-Datei. */
    public void load(File file) throws IOException {
        if (!file.exists()) {
            saveDefaults(file);
            return;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
        }
        applyProperties();
    }

    private void applyProperties() {
        databaseHost = props.getProperty("database.host", databaseHost);
        databasePort = Integer.parseInt(props.getProperty("database.port", String.valueOf(databasePort)));
        databaseName = props.getProperty("database.name", databaseName);
        databaseUsername = props.getProperty("database.username", databaseUsername);
        databasePassword = props.getProperty("database.password", databasePassword);
        databasePoolSize = Integer.parseInt(props.getProperty("database.pool-size", String.valueOf(databasePoolSize)));

        redisHost = props.getProperty("redis.host", redisHost);
        redisPort = Integer.parseInt(props.getProperty("redis.port", String.valueOf(redisPort)));
        redisPassword = props.getProperty("redis.password", redisPassword);
        redisChannel = props.getProperty("redis.channel", redisChannel);

        minNameLength = Integer.parseInt(props.getProperty("guild.min-name-length", String.valueOf(minNameLength)));
        maxNameLength = Integer.parseInt(props.getProperty("guild.max-name-length", String.valueOf(maxNameLength)));
        minTagLength = Integer.parseInt(props.getProperty("guild.min-tag-length", String.valueOf(minTagLength)));
        maxTagLength = Integer.parseInt(props.getProperty("guild.max-tag-length", String.valueOf(maxTagLength)));
        creationCost = Double.parseDouble(props.getProperty("guild.creation-cost", String.valueOf(creationCost)));
        creationCurrency = props.getProperty("guild.creation-currency", creationCurrency);
        maxMembersBase = Integer.parseInt(props.getProperty("guild.max-members-base", String.valueOf(maxMembersBase)));
        maxMembersPerLevel = Integer.parseInt(props.getProperty("guild.max-members-per-level", String.valueOf(maxMembersPerLevel)));
        inviteExpireMinutes = Integer.parseInt(props.getProperty("guild.invite-expire-minutes", String.valueOf(inviteExpireMinutes)));
        disbandConfirmationSeconds = Integer.parseInt(props.getProperty("guild.disband-confirmation-seconds", String.valueOf(disbandConfirmationSeconds)));
        maxLevel = Integer.parseInt(props.getProperty("levels.max-level", String.valueOf(maxLevel)));
    }

    private void saveDefaults(File file) throws IOException {
        file.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            props.setProperty("database.host", databaseHost);
            props.setProperty("database.port", String.valueOf(databasePort));
            props.setProperty("database.name", databaseName);
            props.setProperty("database.username", databaseUsername);
            props.setProperty("database.password", databasePassword);
            props.setProperty("database.pool-size", String.valueOf(databasePoolSize));
            props.setProperty("redis.host", redisHost);
            props.setProperty("redis.port", String.valueOf(redisPort));
            props.setProperty("redis.password", redisPassword);
            props.setProperty("redis.channel", redisChannel);
            props.store(fos, "CrossCraft Guilds Konfiguration");
        }
    }

    // Getter
    public String getDatabaseHost() { return databaseHost; }
    public int getDatabasePort() { return databasePort; }
    public String getDatabaseName() { return databaseName; }
    public String getDatabaseUsername() { return databaseUsername; }
    public String getDatabasePassword() { return databasePassword; }
    public int getDatabasePoolSize() { return databasePoolSize; }

    public String getRedisHost() { return redisHost; }
    public int getRedisPort() { return redisPort; }
    public String getRedisPassword() { return redisPassword; }
    public String getRedisChannel() { return redisChannel; }

    public int getMinNameLength() { return minNameLength; }
    public int getMaxNameLength() { return maxNameLength; }
    public int getMinTagLength() { return minTagLength; }
    public int getMaxTagLength() { return maxTagLength; }
    public double getCreationCost() { return creationCost; }
    public String getCreationCurrency() { return creationCurrency; }
    public int getMaxMembersBase() { return maxMembersBase; }
    public int getMaxMembersPerLevel() { return maxMembersPerLevel; }
    public int getInviteExpireMinutes() { return inviteExpireMinutes; }
    public int getDisbandConfirmationSeconds() { return disbandConfirmationSeconds; }
    public int getMaxLevel() { return maxLevel; }

    // Setter für programmatisches Setzen (z.B. aus Bukkit config.yml)
    public void setDatabaseHost(String v) { this.databaseHost = v; }
    public void setDatabasePort(int v) { this.databasePort = v; }
    public void setDatabaseName(String v) { this.databaseName = v; }
    public void setDatabaseUsername(String v) { this.databaseUsername = v; }
    public void setDatabasePassword(String v) { this.databasePassword = v; }
    public void setDatabasePoolSize(int v) { this.databasePoolSize = v; }
    public void setRedisHost(String v) { this.redisHost = v; }
    public void setRedisPort(int v) { this.redisPort = v; }
    public void setRedisPassword(String v) { this.redisPassword = v; }
    public void setRedisChannel(String v) { this.redisChannel = v; }
    public void setMinNameLength(int v) { this.minNameLength = v; }
    public void setMaxNameLength(int v) { this.maxNameLength = v; }
    public void setMinTagLength(int v) { this.minTagLength = v; }
    public void setMaxTagLength(int v) { this.maxTagLength = v; }
    public void setCreationCost(double v) { this.creationCost = v; }
    public void setCreationCurrency(String v) { this.creationCurrency = v; }
    public void setMaxMembersBase(int v) { this.maxMembersBase = v; }
    public void setMaxMembersPerLevel(int v) { this.maxMembersPerLevel = v; }
    public void setInviteExpireMinutes(int v) { this.inviteExpireMinutes = v; }
    public void setDisbandConfirmationSeconds(int v) { this.disbandConfirmationSeconds = v; }
    public void setMaxLevel(int v) { this.maxLevel = v; }
}
