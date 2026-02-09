
package studio.jan1k.asyncenchantlimiter.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.utils.EnforcementUtil;

public class CommandListener implements Listener {

    private final AsyncEnchantLimiter plugin;

    public CommandListener(AsyncEnchantLimiter plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();
        // Check for vanilla or plugin enchantment commands
        if (msg.startsWith("/enchant ") || msg.startsWith("/minecraft:enchant ") || msg.startsWith("/ie enchant")
                || msg.startsWith("/enchant")) {
            Player player = event.getPlayer();
            // Wait 1 tick for the command to execute and modify the item
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (!player.isOnline())
                    return;
                ItemStack item = player.getInventory().getItemInMainHand();
                if (EnforcementUtil.checkAndFixItem(item)) {
                    // Notification is handled inside EnforcementUtil -> ConfigManager check within
                    // the Util or external?
                    // Wait, EnforcementUtil checkAndFixItem returns boolean if fixed.
                    // The notification logic is usually inside the listener calling it.
                    // I should add notification logic here.
                    studio.jan1k.asyncenchantlimiter.config.ConfigManager config = plugin.getConfigManager();
                    if (config.isNotifyPlayer() && !config.isSilentMode()) {
                        String message = config.getMessage("limit-breached");
                        if (!message.isEmpty()) {
                            player.sendMessage(config.getMessage("prefix") + message);
                        }
                    }
                }
            }, 1L);
        }
    }
}
