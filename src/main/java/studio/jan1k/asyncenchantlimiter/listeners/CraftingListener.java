
package studio.jan1k.asyncenchantlimiter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

public class CraftingListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraft(PrepareItemCraftEvent event) {
        ItemStack result = event.getInventory().getResult();
        if (result == null)
            return;

        ItemStack checked = result.clone();
        if (EnforcementUtil.checkAndFixItem(checked)) {
            event.getInventory().setResult(checked);
        }
    }
}
