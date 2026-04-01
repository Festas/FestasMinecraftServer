package com.festas.guilds.paper.gui;

import com.festas.guilds.api.Guild;
import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Haupt-GUI der Gilde - zeigt Übersicht und Navigation.
 */
public class GuildMainGUI implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;
    private final Player player;
    private Inventory inventory;

    public GuildMainGUI(GuildsPaperPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        GuildAPI.getInstance().getPlayerGuild(player.getUniqueId()).thenAcceptAsync(opt -> {
            if (opt.isEmpty()) {
                player.sendMessage(MM.deserialize("<red>Du bist in keiner Gilde!"));
                return;
            }
            buildAndOpen(opt.get());
        }, r -> plugin.getServer().getScheduler().runTask(plugin, r));
    }

    private void buildAndOpen(Guild guild) {
        inventory = Bukkit.createInventory(null, 54,
                MM.deserialize("<gradient:#FFD700:#FFA500>⚔ Gilde: " + guild.getName()));

        // Gildeninfo - Mitte oben
        setItem(13, createItem(Material.BEACON,
                "<gold>Gilden-Info",
                List.of(
                    "<gray>Name: <white>" + guild.getName(),
                    "<gray>Tag: <white>[" + guild.getTag() + "]",
                    "<gray>Level: <white>" + guild.getLevel(),
                    "<gray>XP: <white>" + guild.getXP(),
                    "<gray>Mitglieder: <white>" + guild.getMemberCount() + "/" + guild.getMaxMembers()
                )));

        // Mitglieder
        setItem(20, createItem(Material.PLAYER_HEAD,
                "<aqua>Mitglieder",
                List.of("<gray>Klicke um die Mitgliederliste zu öffnen")));

        // Bank
        setItem(22, createItem(Material.GOLD_INGOT,
                "<gold>Gildenbank",
                List.of(
                    "<gray>Guthaben: <gold>" + String.format("%.2f", guild.getBank().getBalance("money")),
                    "<gray>Klicke um die Bank zu öffnen"
                )));

        // Einstellungen
        setItem(24, createItem(Material.WRITABLE_BOOK,
                "<yellow>Einstellungen",
                List.of("<gray>Gildeneinstellungen bearbeiten")));

        // Gilde verlassen
        setItem(49, createItem(Material.OAK_DOOR,
                "<red>Gilde verlassen",
                List.of("<gray>Klicke um die Gilde zu verlassen")));

        // Dekoration
        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE, "<gray>", List.of());
        for (int i = 0; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                setItem(i, border);
            }
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) return;
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player clicker)) return;
        if (!clicker.equals(player)) return;

        int slot = event.getSlot();
        switch (slot) {
            case 20 -> {
                player.closeInventory();
                new GuildMembersGUI(plugin, player).open();
            }
            case 22 -> {
                player.closeInventory();
                GuildAPI.getInstance().getPlayerGuild(player.getUniqueId())
                        .thenAcceptAsync(opt -> opt.ifPresent(g ->
                                new GuildBankGUI(plugin, player, g).open()),
                                r -> plugin.getServer().getScheduler().runTask(plugin, r));
            }
            case 49 -> {
                player.closeInventory();
                plugin.getServer().dispatchCommand(player, "guild leave");
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            HandlerList.unregisterAll(this);
        }
    }

    private void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    private ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(MM.deserialize(name));
            meta.lore(lore.stream().map(MM::deserialize).toList());
            item.setItemMeta(meta);
        }
        return item;
    }
}
