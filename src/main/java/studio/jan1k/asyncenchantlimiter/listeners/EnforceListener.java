
package studio.jan1k.asyncenchantlimiter.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

public class EnforceListener implements Listener {

    private final AsyncEnchantLimiter plugin;

    public EnforceListener(AsyncEnchantLimiter plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHeld(PlayerItemHeldEvent event) {
        if (!plugin.getConfigManager().isEnforceOnHold())
            return;

        Player player = event.getPlayer();
        if (player.hasPermission("enchantlimiter.bypass"))
            return;

        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        handleCheck(player, item);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (!plugin.getConfigManager().isEnforceOnClick())
            return;

        Player player = event.getPlayer();
        if (player.hasPermission("enchantlimiter.bypass"))
            return;

        if (event.getItem() != null) {
            handleCheck(player, event.getItem());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (player.hasPermission("enchantlimiter.bypass"))
            return;

        if (event.getInventory().getHolder() instanceof studio.jan1k.asyncenchantlimiter.gui.LimitGUI)
            return;

        if (event.getCurrentItem() != null) {
            handleCheck(player, event.getCurrentItem());
        }

        if (event.getCursor() != null) {
            handleCheck(player, event.getCursor());
        }

        if (event.getClick().isKeyboardClick() && event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
            int button = event.getHotbarButton();
            if (button >= 0 && button < 9) {
                ItemStack item = player.getInventory().getItem(button);
                handleCheck(player, item);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("enchantlimiter.bypass"))
            return;

        for (ItemStack item : player.getInventory().getArmorContents()) {
            handleCheck(player, item);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDispenseArmor(BlockDispenseArmorEvent event) {
        if (event.getTargetEntity() instanceof Player player) {
            if (player.hasPermission("enchantlimiter.bypass"))
                return;
            EnforcementUtil.checkAndFixItem(event.getItem());
        }
    }

    private void handleCheck(Player player, ItemStack item) {
        if (EnforcementUtil.checkAndFixItem(item)) {
            ConfigManager config = plugin.getConfigManager();
            if (config.isNotifyPlayer() && !config.isSilentMode()) {
                String msg = config.getMessage("limit-breached");
                if (!msg.isEmpty()) {
                    player.sendMessage(config.getMessage("prefix") + msg);
                }
            }
        }
    }
}
