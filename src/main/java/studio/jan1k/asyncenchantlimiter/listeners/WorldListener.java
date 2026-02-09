
package studio.jan1k.asyncenchantlimiter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

import java.util.List;

public class WorldListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (event.getCaught() instanceof org.bukkit.entity.Item) {
            ItemStack item = ((org.bukkit.entity.Item) event.getCaught()).getItemStack();
            EnforcementUtil.checkAndFixItem(item);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMobDrop(EntityDeathEvent event) {
        for (ItemStack item : event.getDrops()) {
            EnforcementUtil.checkAndFixItem(item);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLootGenerate(LootGenerateEvent event) {
        List<ItemStack> loot = event.getLoot();
        for (ItemStack item : loot) {
            EnforcementUtil.checkAndFixItem(item);
        }
    }
}
