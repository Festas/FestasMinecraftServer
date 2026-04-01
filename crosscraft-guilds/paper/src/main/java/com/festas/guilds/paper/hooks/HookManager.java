package com.festas.guilds.paper.hooks;

import com.festas.guilds.paper.GuildsPaperPlugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle optionalen Plugin-Hooks.
 * Hooks werden nur aktiviert wenn das jeweilige Plugin verfügbar ist.
 */
public class HookManager {

    private final GuildsPaperPlugin plugin;
    private final List<Object> activeHooks = new ArrayList<>();

    private MMOCoreHook mmoCoreHook;
    private MythicMobsHook mythicMobsHook;
    private MythicDungeonsHook mythicDungeonsHook;
    private CoinsEngineHook coinsEngineHook;
    private PlaceholderAPIHook placeholderAPIHook;

    public HookManager(GuildsPaperPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerHooks() {
        PluginManager pm = plugin.getServer().getPluginManager();

        if (pm.isPluginEnabled("MMOCore")) {
            mmoCoreHook = new MMOCoreHook(plugin);
            mmoCoreHook.register();
            activeHooks.add(mmoCoreHook);
            plugin.getLogger().info("[Hooks] MMOCore Hook aktiviert");
        }

        if (pm.isPluginEnabled("MythicMobs")) {
            mythicMobsHook = new MythicMobsHook(plugin);
            mythicMobsHook.register();
            activeHooks.add(mythicMobsHook);
            plugin.getLogger().info("[Hooks] MythicMobs Hook aktiviert");
        }

        if (pm.isPluginEnabled("MythicDungeons")) {
            mythicDungeonsHook = new MythicDungeonsHook(plugin);
            mythicDungeonsHook.register();
            activeHooks.add(mythicDungeonsHook);
            plugin.getLogger().info("[Hooks] MythicDungeons Hook aktiviert");
        }

        if (pm.isPluginEnabled("CoinsEngine")) {
            coinsEngineHook = new CoinsEngineHook(plugin);
            coinsEngineHook.register();
            activeHooks.add(coinsEngineHook);
            plugin.getLogger().info("[Hooks] CoinsEngine Hook aktiviert");
        }

        if (pm.isPluginEnabled("PlaceholderAPI")) {
            placeholderAPIHook = new PlaceholderAPIHook(plugin);
            placeholderAPIHook.register();
            activeHooks.add(placeholderAPIHook);
            plugin.getLogger().info("[Hooks] PlaceholderAPI Hook aktiviert");
        }

        plugin.getLogger().info("[Hooks] " + activeHooks.size() + " Hook(s) registriert");
    }

    public void unregisterHooks() {
        activeHooks.clear();
    }

    public boolean hasMMOCore() { return mmoCoreHook != null; }
    public boolean hasMythicMobs() { return mythicMobsHook != null; }
    public boolean hasMythicDungeons() { return mythicDungeonsHook != null; }
    public boolean hasCoinsEngine() { return coinsEngineHook != null; }
    public boolean hasPlaceholderAPI() { return placeholderAPIHook != null; }

    public MMOCoreHook getMMOCoreHook() { return mmoCoreHook; }
    public MythicMobsHook getMythicMobsHook() { return mythicMobsHook; }
    public MythicDungeonsHook getMythicDungeonsHook() { return mythicDungeonsHook; }
    public CoinsEngineHook getCoinsEngineHook() { return coinsEngineHook; }
    public PlaceholderAPIHook getPlaceholderAPIHook() { return placeholderAPIHook; }
}
