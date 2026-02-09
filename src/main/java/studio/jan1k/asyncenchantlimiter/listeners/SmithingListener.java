
package studio.jan1k.asyncenchantlimiter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

public class SmithingListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSmith(PrepareSmithingEvent event) {
        ItemStack result = event.getResult();
        if (result == null)
            return;

        ItemStack checked = result.clone();
        if (EnforcementUtil.checkAndFixItem(checked)) {
            event.setResult(checked);
        }
    }
}
