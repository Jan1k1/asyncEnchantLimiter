
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
        if (msg.startsWith("/enchant ") || msg.startsWith("/minecraft:enchant ") || msg.startsWith("/ie enchant")
                || msg.startsWith("/enchant")) {
            Player player = event.getPlayer();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (!player.isOnline())
                    return;
                ItemStack item = player.getInventory().getItemInMainHand();
                if (EnforcementUtil.checkAndFixItem(item)) {
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
