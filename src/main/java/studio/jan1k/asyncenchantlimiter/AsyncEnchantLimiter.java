
package studio.jan1k.asyncenchantlimiter;

import org.bukkit.plugin.java.JavaPlugin;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;

public class AsyncEnchantLimiter extends JavaPlugin {

        private static AsyncEnchantLimiter instance;
        private ConfigManager configManager;

        @Override
        public void onEnable() {
                instance = this;

                getLogger().info("\n" +
                                "    _    ____   __   _   _  ____ \n" +
                                "   / \\  / ___|  \\ \\ / / | \\ | |/ ___|\n" +
                                "  / _ \\ \\___ \\   \\ V /  |  \\| | |    \n" +
                                " / ___ \\ ___) |   | |   | |\\  | |___ \n" +
                                "/_/   \\_\\____/    |_|   |_| \\_|\\____|\n" +
                                "                                     \n" +
                                " _____                 _                  _   _     _           _ _            \n" +
                                "| ____|               | |                | | | |   (_)         (_) |           \n" +
                                "| |__   _ __   ___ ___| |__   __ _ _ __  | |_| |    _ _ __ ___  _| |_ ___ _ __ \n" +
                                "|  __| | '_ \\ / __/ __| '_ \\ / _` | '_ \\ | __| |   | | '_ ` _ \\| | __/ _ \\ '__|\n"
                                +
                                "| |____| | | | (_| (__| | | | (_| | | | || |_| |___| | | | | | | | ||  __/ |   \n" +
                                "|______|_| |_|\\___\\___|_| |_|\\__,_|_| |_| \\__|______|_|_| |_| |_|_|\\__\\___|_|   \n");

                configManager = new ConfigManager(this);
                configManager.load();

                if (getCommand("enchantlimiter") != null) {
                        getCommand("enchantlimiter")
                                        .setExecutor(new studio.jan1k.asyncenchantlimiter.commands.MainCommand(this));
                }

                getServer().getPluginManager()
                                .registerEvents(new studio.jan1k.asyncenchantlimiter.listeners.EnforceListener(this),
                                                this);
                getServer().getPluginManager()
                                .registerEvents(new studio.jan1k.asyncenchantlimiter.listeners.AnvilListener(this),
                                                this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.VillagerListener(),
                                this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.SmithingListener(),
                                this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.WorldListener(),
                                this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.PickupListener(),
                                this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.CraftingListener(),
                                this);
                getServer().getPluginManager()
                                .registerEvents(new studio.jan1k.asyncenchantlimiter.gui.LimitGUI(this, 0), this);
                getServer().getPluginManager()
                                .registerEvents(new studio.jan1k.asyncenchantlimiter.utils.UpdateChecker(this, 12345),
                                                this);
        }

        @Override
        public void onDisable() {
                instance = null;
        }

        public static AsyncEnchantLimiter getInstance() {
                return instance;
        }

        public ConfigManager getConfigManager() {
                return configManager;
        }
}
