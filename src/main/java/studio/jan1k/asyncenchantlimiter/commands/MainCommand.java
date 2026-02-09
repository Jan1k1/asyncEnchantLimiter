
package studio.jan1k.asyncenchantlimiter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;
import studio.jan1k.asyncenchantlimiter.gui.LimitGUI;

public class MainCommand implements CommandExecutor {

    private final AsyncEnchantLimiter plugin;

    public MainCommand(AsyncEnchantLimiter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        ConfigManager config = plugin.getConfigManager();

        if (!sender.hasPermission("enchantlimiter.admin")) {
            String prefix = config.getMessage("prefix");
            String msg = config.getMessage("no-permission");
            sender.sendMessage(prefix + msg);
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("gui") || args[0].equalsIgnoreCase("admin")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command is for players only.");
                return true;
            }
            new LimitGUI(plugin, 0).open(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            config.reload().thenRun(() -> {
                String prefix = config.getMessage("prefix");
                String msg = config.getMessage("reload-success");
                sender.sendMessage(prefix + msg);
            });
            return true;
        }

        String prefix = config.getMessage("prefix");
        sender.sendMessage(prefix + "§7Usage: /el [gui|admin|reload]");
        return true;
    }
}
