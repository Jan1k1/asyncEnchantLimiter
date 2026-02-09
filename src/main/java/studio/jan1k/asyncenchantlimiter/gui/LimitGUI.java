
package studio.jan1k.asyncenchantlimiter.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;
import studio.jan1k.asyncenchantlimiter.utils.ColorUtil;
import studio.jan1k.asyncenchantlimiter.utils.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LimitGUI implements InventoryHolder, Listener {

    private AsyncEnchantLimiter plugin;
    private Inventory inventory;
    private int page;
    private List<Enchantment> enchantments;

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

    // Required for Listener registration
    public LimitGUI() {
    }

    private void loadItems() {
        ConfigManager config = plugin.getConfigManager();
        int slotsPerPage = 45;
        int start = page * slotsPerPage;
        int end = Math.min(start + slotsPerPage, enchantments.size());

        for (int i = 0; i < slotsPerPage; i++) {
            int index = start + i;
            if (index >= enchantments.size()) {
                inventory.setItem(i, null);
                continue;
            }

            Enchantment enchant = enchantments.get(index);
            String key = enchant.getKey().getKey();
            int currentLimit = config.getLimit(key);
            int vanillaMax = enchant.getMaxLevel();

            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();

            // Format Title: Use Small Caps + Gold
            String title = TextUtils.toSmallCaps(key.replace("_", " "));
            meta.displayName(Component.text(title, NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));

            List<Component> lore = new ArrayList<>();

            // Format Limit Value
            String limitDisplay;
            if (currentLimit == Integer.MAX_VALUE) {
                limitDisplay = "Uncapped";
            } else if (currentLimit == 0) {
                limitDisplay = "Disabled";
            } else {
                limitDisplay = String.valueOf(currentLimit);
            }

            lore.add(Component.text("Current Limit: ", NamedTextColor.GRAY)
                    .append(Component.text(limitDisplay, NamedTextColor.YELLOW))
                    .decoration(TextDecoration.ITALIC, false));

            lore.add(Component.empty());
            lore.add(Component.text("Left-Click: ", NamedTextColor.GREEN).append(Component.text("+1"))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Right-Click: ", NamedTextColor.RED).append(Component.text("-1"))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Shift-Left: ", NamedTextColor.DARK_RED).append(Component.text("Disable (0)"))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Shift-Right: ", NamedTextColor.AQUA)
                    .append(Component.text("Vanilla Max (" + vanillaMax + ")"))
                    .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Drop (Q): ", NamedTextColor.LIGHT_PURPLE).append(Component.text("Uncap"))
                    .decoration(TextDecoration.ITALIC, false));

            meta.lore(lore);

            // Store index
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "index"), PersistentDataType.INTEGER,
                    index);
            item.setItemMeta(meta);
            inventory.setItem(i, item);
        }

        // Navigation
        if (page > 0) {
            ItemStack prev = new ItemStack(Material.ARROW);
            ItemMeta meta = prev.getItemMeta();
            meta.displayName(
                    Component.text("Previous Page", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            prev.setItemMeta(meta);
            inventory.setItem(48, prev);
        }

        if (end < enchantments.size()) {
            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta meta = next.getItemMeta();
            meta.displayName(
                    Component.text("Next Page", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
            next.setItemMeta(meta);
            inventory.setItem(50, next);
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
        if (event.getClickedInventory() == null)
            return;
        if (!(event.getView().getTopInventory().getHolder() instanceof LimitGUI))
            return;

        // Prevent moving items in the GUI
        if (event.getClickedInventory().getHolder() instanceof LimitGUI) {
            event.setCancelled(true);
        } else if (event.isShiftClick()) {
            event.setCancelled(true);
            return;
        } else {
            return;
        }

        LimitGUI gui = (LimitGUI) event.getView().getTopInventory().getHolder();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType() == Material.AIR)
            return;

        // Page Navigation
        if (clicked.getType() == Material.ARROW) {
            if (event.getSlot() == 48) {
                new LimitGUI(gui.plugin, gui.page - 1).open(player);
            } else if (event.getSlot() == 50) {
                new LimitGUI(gui.plugin, gui.page + 1).open(player);
            }
            return;
        }

        ItemMeta meta = clicked.getItemMeta();
        NamespacedKey key = new NamespacedKey(gui.plugin, "index");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER))
            return;

        int index = meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        Enchantment enchant = gui.enchantments.get(index);
        String enchantKey = enchant.getKey().getKey();
        ConfigManager config = gui.plugin.getConfigManager();

        int currentLimit = config.getLimit(enchantKey);
        int vanillaMax = enchant.getMaxLevel();

        ClickType click = event.getClick();

        if (click == ClickType.DROP || click == ClickType.CONTROL_DROP) {
            // Uncap
            currentLimit = Integer.MAX_VALUE;
        } else if (click == ClickType.SHIFT_LEFT) {
            // Disable
            currentLimit = 0;
        } else if (click == ClickType.SHIFT_RIGHT) {
            // Vanilla Max
            currentLimit = vanillaMax;
        } else if (event.isLeftClick()) {
            // +1
            if (currentLimit == Integer.MAX_VALUE) {
                // Already max
            } else {
                currentLimit++;
            }
        } else if (event.isRightClick()) {
            // -1
            if (currentLimit == Integer.MAX_VALUE) {
                currentLimit = vanillaMax; // Snap to vanilla max if coming down from infinity
            } else if (currentLimit > 0) {
                currentLimit--;
            }
        }

        config.saveLimit(enchantKey, currentLimit);
        gui.loadItems(); // Refresh GUI
        player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1f, 1f);
    }
}
