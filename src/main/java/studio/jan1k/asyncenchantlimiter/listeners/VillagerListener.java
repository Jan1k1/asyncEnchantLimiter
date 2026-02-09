
package studio.jan1k.asyncenchantlimiter.listeners;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

import java.util.ArrayList;
import java.util.List;

public class VillagerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTrade(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.MERCHANT)
            return;
        if (!(event.getInventory() instanceof MerchantInventory))
            return;
        if (event.getSlotType() != InventoryType.SlotType.RESULT)
            return;

        ItemStack result = event.getCurrentItem();
        if (result == null)
            return;
        if (!AsyncEnchantLimiter.getInstance().getConfigManager().isFixVillagerTrades())
            return;

        EnforcementUtil.checkAndFixItem(result);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAcquireTrade(VillagerAcquireTradeEvent event) {
        if (!AsyncEnchantLimiter.getInstance().getConfigManager().isFixVillagerTrades())
            return;

        MerchantRecipe recipe = event.getRecipe();
        ItemStack result = recipe.getResult();

        if (EnforcementUtil.checkAndFixItem(result)) {
            MerchantRecipe newRecipe = new MerchantRecipe(result, recipe.getUses(), recipe.getMaxUses(),
                    recipe.hasExperienceReward(), recipe.getVillagerExperience(), recipe.getPriceMultiplier());
            newRecipe.setIngredients(recipe.getIngredients());
            event.setRecipe(newRecipe);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteractVillager(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager villager))
            return;
        if (!AsyncEnchantLimiter.getInstance().getConfigManager().isFixVillagerTrades())
            return;
        if (event.getPlayer().isSneaking())
            return;

        List<MerchantRecipe> recipes = new ArrayList<>(villager.getRecipes());
        boolean changed = false;

        for (int i = 0; i < recipes.size(); i++) {
            MerchantRecipe recipe = recipes.get(i);
            ItemStack result = recipe.getResult();

            if (EnforcementUtil.checkAndFixItem(result)) {
                MerchantRecipe newRecipe = new MerchantRecipe(result, recipe.getUses(), recipe.getMaxUses(),
                        recipe.hasExperienceReward(), recipe.getVillagerExperience(), recipe.getPriceMultiplier());
                newRecipe.setIngredients(recipe.getIngredients());
                recipes.set(i, newRecipe);
                changed = true;
            }
        }

        if (changed) {
            villager.setRecipes(recipes);
        }
    }
}
