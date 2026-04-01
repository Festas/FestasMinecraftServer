package com.festas.guilds.paper.gui;

import com.festas.guilds.api.Guild;
import com.festas.guilds.api.GuildAPI;
import com.festas.guilds.api.GuildMember;
import com.festas.guilds.paper.GuildsPaperPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Zeigt alle Gildenmitglieder mit Rang und Beitrag an.
 */
public class GuildMembersGUI implements Listener {

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private final GuildsPaperPlugin plugin;
    private final Player player;
    private Inventory inventory;

    public GuildMembersGUI(GuildsPaperPlugin plugin, Player player) {
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
        int size = Math.max(54, (int) Math.ceil(guild.getMemberCount() / 9.0) * 9 + 9);
        size = Math.min(size, 54);

        inventory = Bukkit.createInventory(null, size,
                MM.deserialize("<aqua>Mitglieder: " + guild.getName()));

        List<GuildMember> members = new ArrayList<>(guild.getMembers());
        members.sort((a, b) -> Integer.compare(b.getRank().getLevel(), a.getRank().getLevel()));

        int slot = 0;
        for (GuildMember member : members) {
            if (slot >= size - 9) break;
            OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(member.getPlayerUUID());
            String name = offPlayer.getName() != null ? offPlayer.getName() : "Unbekannt";
            boolean online = offPlayer.isOnline();

            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(offPlayer);
                meta.displayName(MM.deserialize(
                        (online ? "<green>" : "<gray>") + name));
                meta.lore(List.of(
                        MM.deserialize("<gray>Rang: <gold>" + member.getRank().getDisplayName()),
                        MM.deserialize("<gray>Beigetragene XP: <aqua>" + member.getContributedXP()),
                        MM.deserialize("<gray>Status: " + (online ? "<green>Online" : "<red>Offline"))
                ));
                skull.setItemMeta(meta);
            }
            inventory.setItem(slot++, skull);
        }

        // Zurück-Button
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        if (backMeta != null) {
            backMeta.displayName(MM.deserialize("<red>Zurück"));
            back.setItemMeta(backMeta);
        }
        inventory.setItem(size - 5, back);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) return;
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player clicker) || !clicker.equals(player)) return;

        // Zurück-Button
        ItemStack clicked = event.getCurrentItem();
        if (clicked != null && clicked.getType() == Material.ARROW) {
            player.closeInventory();
            new GuildMainGUI(plugin, player).open();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            HandlerList.unregisterAll(this);
        }
    }
}
