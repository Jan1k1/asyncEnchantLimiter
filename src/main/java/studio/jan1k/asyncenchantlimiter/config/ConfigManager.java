
package studio.jan1k.asyncenchantlimiter.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import studio.jan1k.asyncenchantlimiter.AsyncEnchantLimiter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class ConfigManager {

    private final AsyncEnchantLimiter plugin;
    private File configFile;
    private FileConfiguration config;
    private File messagesFile;
    private FileConfiguration messagesConfig;

    private final Map<String, Integer> limits = new HashMap<>();
    private final Map<String, String> messageCache = new HashMap<>();

    public ConfigManager(AsyncEnchantLimiter plugin) {
        this.plugin = plugin;
    }

    public void load() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        loadLimits();
        messageCache.clear();
    }

    public CompletableFuture<Void> reload() {
        return CompletableFuture.runAsync(() -> {
            load();
        });
    }

    private void loadLimits() {
        limits.clear();
        ConfigurationSection limitsSection = config.getConfigurationSection("limits");

        if (limitsSection == null)
            return;

        for (String key : limitsSection.getKeys(false)) {
            String simpleKey = key.toLowerCase();
            if (simpleKey.contains(":")) {
                simpleKey = simpleKey.split(":")[1];
            }
            limits.put(simpleKey, limitsSection.getInt(key));
        }
    }

    public int getLimit(String enchantmentKey) {
        String key = enchantmentKey.toLowerCase();
        if (key.contains(":")) {
            key = key.split(":")[1];
        }
        return limits.getOrDefault(key, Integer.MAX_VALUE);
    }

    public String getMessage(String key) {
        if (messageCache.containsKey(key)) {
            return messageCache.get(key);
        }

        String msg = messagesConfig.getString(key, "");
        if (msg.isEmpty())
            return "";

        msg = studio.jan1k.asyncenchantlimiter.utils.TextUtils.toSmallCaps(msg);
        msg = studio.jan1k.asyncenchantlimiter.utils.ColorUtil.colorize(msg);

        messageCache.put(key, msg);
        return msg;
    }

    public boolean isEnforceOnHold() {
        return config.getBoolean("settings.enforce-on-hold", true);
    }

    public boolean isEnforceOnClick() {
        return config.getBoolean("settings.enforce-on-click", true);
    }

    public boolean isEnforceOnEquip() {
        return config.getBoolean("settings.enforce-on-equip", true);
    }

    public boolean isFixVillagerTrades() {
        return config.getBoolean("settings.fix-villager-trades", true);
    }

    public boolean isNotifyPlayer() {
        return config.getBoolean("settings.notify-player", true);
    }

    public boolean isSilentMode() {
        return config.getBoolean("settings.silent-mode", false);
    }

    public void saveLimit(String enchantmentKey, int level) {
        String key = enchantmentKey.toLowerCase();
        if (key.contains(":")) {
            key = key.split(":")[1];
        }

        final String finalKey = key;

        limits.put(finalKey, level);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                config.set("limits." + finalKey, level);
                try {
                    config.save(configFile);
                } catch (IOException e) {
                    plugin.getLogger().log(Level.SEVERE, "Could not save config.yml", e);
                }
            }
        });
    }
}
