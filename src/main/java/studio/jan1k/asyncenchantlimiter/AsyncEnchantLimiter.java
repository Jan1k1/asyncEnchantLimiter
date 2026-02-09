package studio.jan1k.asyncenchantlimiter;

import org.bukkit.plugin.java.JavaPlugin;
import studio.jan1k.asyncenchantlimiter.config.ConfigManager;
import studio.jan1k.asyncenchantlimiter.utils.Logs;

public class AsyncEnchantLimiter extends JavaPlugin {

        private static AsyncEnchantLimiter instance;
        private ConfigManager configManager;

        @Override
        public void onEnable() {
                instance = this;

                suppressLibraryLogs();
                printBanner();

                Logs.info("Initializing AsyncEnchantLimiter v" + getDescription().getVersion());
                Logs.info("Initializing core modules...");

                configManager = new ConfigManager(this);
                configManager.load();

                if (getCommand("enchantlimiter") != null) {
                        getCommand("enchantlimiter")
                                        .setExecutor(new studio.jan1k.asyncenchantlimiter.commands.MainCommand(this));
                }

                registerListeners();

                Logs.success("Plugin enabled successfully.");
        }

        private void suppressLibraryLogs() {
                java.util.logging.Logger.getLogger("com.zaxxer.hikari").setLevel(java.util.logging.Level.OFF);
                System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "warn");
        }

        private void registerListeners() {
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.EnforceListener(this), this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.AnvilListener(this), this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.VillagerListener(), this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.SmithingListener(), this);
                getServer().getPluginManager()
                                .registerEvents(new studio.jan1k.asyncenchantlimiter.listeners.WorldListener(), this);
                getServer().getPluginManager()
                                .registerEvents(new studio.jan1k.asyncenchantlimiter.listeners.PickupListener(), this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.CraftingListener(), this);
                getServer().getPluginManager().registerEvents(
                                new studio.jan1k.asyncenchantlimiter.listeners.CommandListener(this), this);
                getServer().getPluginManager()
                                .registerEvents(new studio.jan1k.asyncenchantlimiter.gui.LimitGUI(this, 0), this);

                // Update checker logic could be here, but using Logs.info
        }

        private void printBanner() {
                String pink = "§x§E§3§5§4§F§F";
                String blue = "§x§5§4§A§5§F§F";
                String reset = "§r";

                Logs.raw(" ");
                Logs.bannerAccent(pink + "  █████╗ ███████╗██╗   ██╗███╗   ██╗ ██████╗");
                Logs.bannerAccent(pink + " ██╔══██╗██╔════╝╚██╗ ██╔╝████╗  ██║██╔════╝");
                Logs.bannerAccent(pink + " ███████║███████╗ ╚████╔╝ ██╔██╗ ██║██║     ");
                Logs.bannerAccent(pink + " ██╔══██║╚════██║  ╚██╔╝  ██║╚██╗██║██║     ");
                Logs.bannerAccent(pink + " ██║  ██║███████║   ██║   ██║ ╚████║╚██████╗");
                Logs.bannerAccent(pink + " ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚═╝  ╚═══╝ ╚═════╝");
                Logs.raw(" ");
                Logs.bannerAccent(blue + " ██╗     ██╗███╗   ███╗██╗████████╗███████╗██████╗ ");
                Logs.bannerAccent(blue + " ██║     ██║████╗ ████║██║╚══██╔══╝██╔════╝██╔══██╗");
                Logs.bannerAccent(blue + " ██║     ██║██╔████╔██║██║   ██║   █████╗  ██████╔╝");
                Logs.bannerAccent(blue + " ██║     ██║██║╚██╔╝██║██║   ██║   ██╔══╝  ██╔══██╗");
                Logs.bannerAccent(blue + " ███████╗██║██║ ╚═╝ ██║██║   ██║   ███████╗██║  ██║");
                Logs.bannerAccent(blue + " ╚══════╝╚═╝╚═╝     ╚═╝╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝" + reset);
                Logs.raw(" ");
                Logs.raw("  §fASYNCLIMITER   Premium Enchantment Limiting System");
                Logs.raw("  §f© 2026 jan1k.studio - All Rights Reserved");
                Logs.raw(" ");
                Logs.raw("  §e⚠ Found a bug or have a suggestion?");
                Logs.raw("  §b→ Join our Discord: §fhttps://discord.gg/38Ebj42e");
                Logs.raw(" ");
        }

        @Override
        public void onDisable() {
                instance = null;
                Logs.info("Plugin disabled successfully.");
        }

        public static AsyncEnchantLimiter getInstance() {
                return instance;
        }

        public ConfigManager getConfigManager() {
                return configManager;
        }
}
