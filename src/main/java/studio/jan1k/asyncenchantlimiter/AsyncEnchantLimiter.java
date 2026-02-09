
package studio.jan1k.asyncenchantlimiter;

import org.bukkit.plugin.java.JavaPlugin;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;

public class AsyncEnchantLimiter extends JavaPlugin {

        private static AsyncEnchantLimiter instance;
        private ConfigManager configManager;

        @Override
        public void onEnable() {
                instance = this;

                String art = "\n" +
                                "&#00c6ff    _    ____   __   _   _  ____ \n" +
                                "&#00c6ff   / \\  / ___|  \\ \\ / / | \\ | |/ ___|\n" +
                                "&#00c6ff  / _ \\ \\___ \\   \\ V /  |  \\| | |    \n" +
                                "&#00c6ff / ___ \\ ___) |   | |   | |\\  | |___ \n" +
                                "&#00c6ff/_/   \\_\\____/    |_|   |_| \\_|\\____|\n" +
                                "&#00c6ff                                     \n" +
                                "&#00c6ff _____                 _                  _   _     _           _ _            \n"
                                +
                                "&#00c6ff| ____|               | |                | | | |   (_)         (_) |           \n"
                                +
                                "&#00c6ff| |__   _ __   ___ ___| |__   __ _ _ __  | |_| |    _ _ __ ___  _| |_ ___ _ __ \n"
                                +
                                "&#00c6ff|  __| | '_ \\ / __/ __| '_ \\ / _` | '_ \\ | __| |   | | '_ ` _ \\| | __/ _ \\ '__|\n"
                                +
                                "&#00c6ff| |____| | | | (_| (__| | | | (_| | | | || |_| |___| | | | | | | | ||  __/ |   \n"
                                +
                                "&#00c6ff|______|_| |_|\\___\\___|_| |_|\\__,_|_| |_| \\__|______|_|_| |_| |_|_|\\__\\___|_|   \n";

                getServer().getConsoleSender()
                                .sendMessage(studio.jan1k.asyncenchantlimiter.utils.ColorUtil.colorize(art));

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
