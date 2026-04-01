package com.festas.guilds.paper.gui;

import com.festas.guilds.api.Guild;
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
 * Zeigt alle Gilden in einer paginierten Liste.
 */
public class GuildListGUI implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private static final int PAGE_SIZE = 45;

    private final GuildsPaperPlugin plugin;
    private final Player player;
    private final List<Guild> guilds;
    private int page;
    private Inventory inventory;

    public GuildListGUI(GuildsPaperPlugin plugin, Player player, List<Guild> guilds) {
        this.plugin = plugin;
        this.player = player;
        this.guilds = guilds;
        this.page = 0;
    }

    public void open() {
        buildPage();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        player.openInventory(inventory);
    }

    private void buildPage() {
        int totalPages = Math.max(1, (int) Math.ceil((double) guilds.size() / PAGE_SIZE));
        inventory = Bukkit.createInventory(null, 54,
                MM.deserialize("<gold>Gilden <gray>(" + (page + 1) + "/" + totalPages + ")"));

        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, guilds.size());

        for (int i = start; i < end; i++) {
            Guild guild = guilds.get(i);
            ItemStack item = new ItemStack(Material.SHIELD);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.displayName(MM.deserialize("<gold>" + guild.getName() + " <gray>[" + guild.getTag() + "]"));
                meta.lore(List.of(
                        MM.deserialize("<gray>Level: <white>" + guild.getLevel()),
                        MM.deserialize("<gray>XP: <white>" + guild.getXP()),
                        MM.deserialize("<gray>Mitglieder: <white>" + guild.getMemberCount() + "/" + guild.getMaxMembers()),
                        MM.deserialize("<gray>Klicke für mehr Info")
                ));
                item.setItemMeta(meta);
            }
            inventory.setItem(i - start, item);
        }

        // Navigation
        if (page > 0) {
            inventory.setItem(45, createNavItem(Material.ARROW, "<red>← Vorherige Seite"));
        }
        if (end < guilds.size()) {
            inventory.setItem(53, createNavItem(Material.ARROW, "<green>Nächste Seite →"));
        }

        // Filler
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fm = filler.getItemMeta();
        if (fm != null) { fm.displayName(MM.deserialize("<gray>")); filler.setItemMeta(fm); }
        for (int i = 45; i < 54; i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, filler);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) return;
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player clicker) || !clicker.equals(player)) return;

        int slot = event.getSlot();
        if (slot == 45 && page > 0) {
            page--;
            inventory.clear();
            buildPage();
        } else if (slot == 53 && (page + 1) * PAGE_SIZE < guilds.size()) {
            page++;
            inventory.clear();
            buildPage();
        } else if (slot < PAGE_SIZE && slot < guilds.size() - page * PAGE_SIZE) {
            Guild guild = guilds.get(page * PAGE_SIZE + slot);
            player.closeInventory();
            new GuildInfoCommand(plugin).execute(player, new String[]{guild.getName()});
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            HandlerList.unregisterAll(this);
        }
    }

    private ItemStack createNavItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(MM.deserialize(name));
            item.setItemMeta(meta);
        }
        return item;
    }

    // Hilfsklasse für den Info-Aufruf aus der GUI
    private static class GuildInfoCommand {
        private final GuildsPaperPlugin plugin;
        GuildInfoCommand(GuildsPaperPlugin plugin) { this.plugin = plugin; }
        void execute(Player player, String[] args) {
            new com.festas.guilds.paper.commands.GuildInfoCommand(plugin).execute(player, args);
        }
    }
}
