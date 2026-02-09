
package studio.jan1k.asyncenchantlimiter.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;

import java.util.Map;

public class EnforcementUtil {

    public static boolean checkAndFixItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return false;

        boolean changed = false;
        ConfigManager config = AsyncEnchantLimiter.getInstance().getConfigManager();
        ItemMeta meta = item.getItemMeta();

        if (meta == null)
            return false;

        if (meta instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) meta;
            for (Map.Entry<Enchantment, Integer> entry : storageMeta.getStoredEnchants().entrySet()) {
                Enchantment enchant = entry.getKey();
                int level = entry.getValue();
                int limit = config.getLimit(enchant.getKey().getKey());

                if (level > limit) {
                    storageMeta.removeStoredEnchant(enchant);
                    if (limit > 0) {
                        storageMeta.addStoredEnchant(enchant, limit, true);
                    }
                    changed = true;
                }
            }
            if (changed)
                item.setItemMeta(storageMeta);
        } else {
            for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                Enchantment enchant = entry.getKey();
                int level = entry.getValue();
                int limit = config.getLimit(enchant.getKey().getKey());

                if (level > limit) {
                    item.removeEnchantment(enchant);
                    if (limit > 0) {
                        item.addEnchantment(enchant, limit);
                    }
                    changed = true;
                }
            }
        }

        return changed;
    }
}
