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
import java.util.Map;

/**
 * Zeigt die Gildenbank mit Guthaben und Transaktions-Optionen.
 */
public class GuildBankGUI implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;
    private final Player player;
    private final Guild guild;
    private Inventory inventory;

    public GuildBankGUI(GuildsPaperPlugin plugin, Player player, Guild guild) {
        this.plugin = plugin;
        this.player = player;
        this.guild = guild;
    }

    public void open() {
        inventory = Bukkit.createInventory(null, 27,
                MM.deserialize("<gold>⚔ Gildenkasse: " + guild.getName()));

        // Guthaben anzeigen
        Map<String, Double> balances = guild.getBank().getAllBalances();
        setItem(13, createItem(Material.GOLD_BLOCK,
                "<gold>Guthaben",
                balances.entrySet().stream()
                        .map(e -> "<gray>" + e.getKey() + ": <gold>" + String.format("%.2f", e.getValue()))
                        .toList()));

        // Einzahlen
        setItem(11, createItem(Material.GREEN_STAINED_GLASS_PANE,
                "<green>Einzahlen",
                List.of("<gray>Klicke zum Einzahlen")));

        // Auszahlen
        setItem(15, createItem(Material.RED_STAINED_GLASS_PANE,
                "<red>Auszahlen",
                List.of("<gray>Klicke zum Auszahlen (Offizier+)")));

        // Zurück
        setItem(22, createItem(Material.ARROW,
                "<red>Zurück",
                List.of()));

        // Rand
        ItemStack border = createItem(Material.GRAY_STAINED_GLASS_PANE, "<gray>", List.of());
        for (int i = 0; i < 27; i++) {
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
        if (!(event.getWhoClicked() instanceof Player clicker) || !clicker.equals(player)) return;

        switch (event.getSlot()) {
            case 11 -> {
                player.closeInventory();
                player.sendMessage(MM.deserialize(
                        "<yellow>Verwendung: <gold>/guild bank deposit <währung> <betrag>"));
            }
            case 15 -> {
                player.closeInventory();
                player.sendMessage(MM.deserialize(
                        "<yellow>Verwendung: <gold>/guild bank withdraw <währung> <betrag>"));
            }
            case 22 -> {
                player.closeInventory();
                new GuildMainGUI(plugin, player).open();
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
