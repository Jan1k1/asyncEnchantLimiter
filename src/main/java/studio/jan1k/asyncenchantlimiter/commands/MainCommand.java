
package studio.jan1k.asyncenchantlimiter.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.gui.LimitGUI;

public class MainCommand implements CommandExecutor {

    private final AsyncEnchantLimiter plugin;

    public MainCommand(AsyncEnchantLimiter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!sender.hasPermission("enchantlimiter.admin")) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(
                    plugin.getConfigManager().getMessage("prefix") +
                            plugin.getConfigManager().getMessage("no-permission")));
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("gui")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command is for players only.");
                return true;
            }
            new LimitGUI(plugin, 0).open(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getConfigManager().reload().thenRun(() -> {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(
                        plugin.getConfigManager().getMessage("prefix") +
                                plugin.getConfigManager().getMessage("reload-success")));
            });
            return true;
        }

        return true;
    }
}
