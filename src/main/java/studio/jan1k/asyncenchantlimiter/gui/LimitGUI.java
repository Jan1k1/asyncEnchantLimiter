
package studio.jan1k.asyncenchantlimiter.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LimitGUI implements InventoryHolder, Listener {

    private final AsyncEnchantLimiter plugin;
    private final Inventory inventory;
    private final int page;
    private final List<Enchantment> enchantments;

    public LimitGUI(AsyncEnchantLimiter plugin, int page) {
        this.plugin = plugin;
        this.page = page;
        this.inventory = Bukkit.createInventory(this, 54, Component.text("Enchantment Limits - Page " + (page + 1)));
        this.enchantments = new ArrayList<>();

        for (Enchantment enchant : Enchantment.values()) {
            enchantments.add(enchant);
        }
        enchantments.sort(Comparator.comparing(e -> e.getKey().getKey()));

        loadItems();
    }

    private void loadItems() {
        ConfigManager config = plugin.getConfigManager();
        int slotsPerPage = 45;
        int start = page * slotsPerPage;
        int end = Math.min(start + slotsPerPage, enchantments.size());

        for (int i = start; i < end; i++) {
            Enchantment enchant = enchantments.get(i);
            int currentLimit = config.getLimit(enchant.getKey().getKey());

            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(enchant.getKey().getKey(), NamedTextColor.GOLD));

            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Current Limit: ", NamedTextColor.GRAY)
                    .append(Component.text(currentLimit, NamedTextColor.YELLOW)));
            lore.add(Component.empty());
            lore.add(Component.text("Left-Click: +1", NamedTextColor.GREEN));
            lore.add(Component.text("Right-Click: -1", NamedTextColor.RED));
            lore.add(Component.text("Shift-Left: Set to 0 (Disabled)", NamedTextColor.DARK_RED));
            lore.add(Component.text("Shift-Right: Set to MAX (Uncapped)", NamedTextColor.AQUA));

            meta.lore(lore);
            item.setItemMeta(meta);

            // Store index for click handling
            NamespacedKey key = new NamespacedKey(plugin, "enchant_index");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);
            item.setItemMeta(meta);

            inventory.setItem(i - start, item);
        }

        // Navigation Buttons
        if (page > 0) {
            ItemStack prev = new ItemStack(Material.ARROW);
            ItemMeta meta = prev.getItemMeta();
            meta.displayName(Component.text("Previous Page", NamedTextColor.YELLOW));
            prev.setItemMeta(meta);
            inventory.setItem(45, prev);
        }

        if (end < enchantments.size()) {
            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta meta = next.getItemMeta();
            meta.displayName(Component.text("Next Page", NamedTextColor.YELLOW));
            next.setItemMeta(meta);
            inventory.setItem(53, next);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (!(event.getInventory().getHolder() instanceof LimitGUI))
            return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR)
            return;

        LimitGUI gui = (LimitGUI) event.getInventory().getHolder();

        if (clicked.getType() == Material.ARROW) {
            if (event.getSlot() == 45) {
                new LimitGUI(plugin, gui.page - 1).open(player);
            } else if (event.getSlot() == 53) {
                new LimitGUI(plugin, gui.page + 1).open(player);
            }
            return;
        }

        ItemMeta meta = clicked.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "enchant_index");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER))
            return;

        int index = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        Enchantment enchant = gui.enchantments.get(index);
        String enchantKey = enchant.getKey().getKey();
        ConfigManager config = plugin.getConfigManager();
        int currentLimit = config.getLimit(enchantKey);

        if (event.isShiftClick()) {
            if (event.isLeftClick()) {
                currentLimit = 0;
            } else {
                currentLimit = Integer.MAX_VALUE;
            }
        } else {
            if (event.isLeftClick()) {
                if (currentLimit < Integer.MAX_VALUE)
                    currentLimit++;
            } else if (event.isRightClick()) {
                if (currentLimit > 0)
                    currentLimit--;
            }
        }

        config.saveLimit(enchantKey, currentLimit);

        // Refresh items on this page
        gui.loadItems();
        player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1f, 1f);
    }
}
