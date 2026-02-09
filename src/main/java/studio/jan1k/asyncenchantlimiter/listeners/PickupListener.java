
package studio.jan1k.asyncenchantlimiter.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

public class PickupListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        ItemStack item = event.getItem().getItemStack();
        EnforcementUtil.checkAndFixItem(item);
    }
}
