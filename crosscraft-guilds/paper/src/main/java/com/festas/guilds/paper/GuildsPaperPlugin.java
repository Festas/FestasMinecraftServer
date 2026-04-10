package com.festas.guilds.paper;

import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.common.config.GuildConfig;
import com.festas.guilds.common.database.DatabaseManager;
import com.festas.guilds.common.database.GuildRepository;
import com.festas.guilds.common.model.GuildLevelManager;
import com.festas.guilds.common.redis.RedisManager;
import com.festas.guilds.common.redis.RedisMessageBroker;
import com.festas.guilds.paper.commands.*;
import com.festas.guilds.paper.hooks.HookManager;
import com.festas.guilds.paper.listeners.GuildXPListener;
import com.festas.guilds.paper.listeners.PlayerChatListener;
import com.festas.guilds.paper.listeners.PlayerJoinListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Haupt-Plugin-Klasse für das CrossCraft Guilds Paper-Modul.
 */
public class GuildsPaperPlugin extends JavaPlugin {

    private static GuildsPaperPlugin instance;

    private GuildConfig guildConfig;
    private DatabaseManager databaseManager;
    private GuildRepository guildRepository;
    private RedisManager redisManager;
    private RedisMessageBroker messageBroker;
    private GuildLevelManager levelManager;
    private PaperGuildAPIImpl guildAPI;
    private HookManager hookManager;

    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        instance = this;

        // Konfiguration laden
        saveDefaultConfig();
        guildConfig = loadGuildConfig();

        // Datenbank initialisieren
        databaseManager = new DatabaseManager(guildConfig);
        try {
            databaseManager.initialize();
        } catch (RuntimeException e) {
            getLogger().severe("Datenbankverbindung fehlgeschlagen: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        guildRepository = new GuildRepository(databaseManager);
        levelManager = new GuildLevelManager(guildConfig.getMaxLevel());

        // Redis initialisieren
        redisManager = new RedisManager(guildConfig);
        try {
            redisManager.initialize();
            messageBroker = new RedisMessageBroker(redisManager, guildConfig.getRedisChannel());
            messageBroker.start();
        } catch (RuntimeException e) {
            getLogger().warning("Redis nicht verfügbar - Cross-Server-Features deaktiviert: " + e.getMessage());
        }

        // API registrieren
        guildAPI = new PaperGuildAPIImpl(guildRepository, levelManager, guildConfig, messageBroker);
        GuildAPI.register(guildAPI);

        // Hooks initialisieren
        hookManager = new HookManager(this);
        hookManager.registerHooks();

        // Befehle registrieren
        registerCommands();

        // Listener registrieren
        registerListeners();

        getLogger().info("CrossCraft Guilds v" + getPluginMeta().getVersion() + " aktiviert!");
    }

    @Override
    public void onDisable() {
        GuildAPI.unregister();

        if (messageBroker != null) messageBroker.stop();
        if (redisManager != null) redisManager.shutdown();
        if (guildRepository != null) guildRepository.shutdown();
        if (databaseManager != null) databaseManager.shutdown();
        if (hookManager != null) hookManager.unregisterHooks();

        getLogger().info("CrossCraft Guilds deaktiviert.");
    }

    private GuildConfig loadGuildConfig() {
        GuildConfig cfg = new GuildConfig();
        var bukkit = getConfig();

        cfg.setDatabaseHost(bukkit.getString("database.host", "localhost"));
        cfg.setDatabasePort(bukkit.getInt("database.port", 3306));
        cfg.setDatabaseName(bukkit.getString("database.database", "minecraft_guilds"));
        cfg.setDatabaseUsername(bukkit.getString("database.username", "root"));
        cfg.setDatabasePassword(bukkit.getString("database.password", ""));
        cfg.setDatabasePoolSize(bukkit.getInt("database.pool-size", 10));

        cfg.setRedisHost(bukkit.getString("redis.host", "localhost"));
        cfg.setRedisPort(bukkit.getInt("redis.port", 6379));
        cfg.setRedisPassword(bukkit.getString("redis.password", ""));
        cfg.setRedisChannel(bukkit.getString("redis.channel", "crosscraft-guilds"));

        cfg.setMinNameLength(bukkit.getInt("guild.min-name-length", 3));
        cfg.setMaxNameLength(bukkit.getInt("guild.max-name-length", 32));
        cfg.setMinTagLength(bukkit.getInt("guild.min-tag-length", 2));
        cfg.setMaxTagLength(bukkit.getInt("guild.max-tag-length", 8));
        cfg.setCreationCost(bukkit.getDouble("guild.creation-cost", 10000.0));
        cfg.setCreationCurrency(bukkit.getString("guild.creation-currency", "money"));
        cfg.setMaxMembersBase(bukkit.getInt("guild.max-members-base", 10));
        cfg.setMaxMembersPerLevel(bukkit.getInt("guild.max-members-per-level", 2));
        cfg.setInviteExpireMinutes(bukkit.getInt("guild.invite-expire-minutes", 60));
        cfg.setDisbandConfirmationSeconds(bukkit.getInt("guild.disband-confirmation-seconds", 30));
        cfg.setMaxLevel(bukkit.getInt("levels.max-level", 50));

        return cfg;
    }

    private void registerCommands() {
        GuildCommand guildCommand = new GuildCommand(this);

        PluginCommand guild = getCommand("guild");
        if (guild != null) {
            guild.setExecutor(guildCommand);
            guild.setTabCompleter(guildCommand);
        }

        PluginCommand gc = getCommand("gc");
        if (gc != null) {
            GuildChatCommand chatCommand = new GuildChatCommand(this);
            gc.setExecutor(chatCommand);
        }
    }

    private void registerListeners() {
        var pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(this), this);
        pm.registerEvents(new PlayerChatListener(this), this);
        pm.registerEvents(new GuildXPListener(this), this);
    }

    public static GuildsPaperPlugin getInstance() { return instance; }
    public GuildConfig getGuildConfig() { return guildConfig; }
    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public GuildRepository getGuildRepository() { return guildRepository; }
    public RedisManager getRedisManager() { return redisManager; }
    public RedisMessageBroker getMessageBroker() { return messageBroker; }
    public GuildLevelManager getLevelManager() { return levelManager; }
    public PaperGuildAPIImpl getGuildAPI() { return guildAPI; }
    public HookManager getHookManager() { return hookManager; }
}
