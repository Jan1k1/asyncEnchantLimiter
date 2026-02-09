
package studio.jan1k.asyncenchantlimiter.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

import java.util.HashMap;
import java.util.Map;

public class AnvilListener implements Listener {

    private final AsyncEnchantLimiter plugin;

    public AnvilListener(AsyncEnchantLimiter plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack result = event.getResult();
        if (result == null)
            return;

        ItemStack checked = result.clone();
        if (EnforcementUtil.checkAndFixItem(checked)) {
            event.setResult(null);

            org.bukkit.entity.HumanEntity viewer = event.getView().getPlayer();
            if (viewer instanceof org.bukkit.entity.Player) {
                ConfigManager config = plugin.getConfigManager();
                if (config.isNotifyPlayer() && !config.isSilentMode()) {
                    String msg = config.getMessage("limit-breached");
                    if (!msg.isEmpty()) {
                        viewer.sendMessage(config.getMessage("prefix") + msg);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEnchant(EnchantItemEvent event) {
        if (event.getEnchanter().hasPermission("enchantlimiter.bypass"))
            return;

        ConfigManager config = plugin.getConfigManager();
        Map<Enchantment, Integer> newEnchants = new HashMap<>(event.getEnchantsToAdd());

        for (Map.Entry<Enchantment, Integer> entry : newEnchants.entrySet()) {
            Enchantment enchant = entry.getKey();
            int level = entry.getValue();
            int limit = config.getLimit(enchant.getKey().getKey());

            if (level > limit) {
                if (limit > 0) {
                    event.getEnchantsToAdd().put(enchant, limit);
                } else {
                    event.getEnchantsToAdd().remove(enchant);
                }
            }
        }
    }
}
