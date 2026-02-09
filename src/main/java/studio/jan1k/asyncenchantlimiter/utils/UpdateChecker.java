
package studio.jan1k.asyncenchantlimiter.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker implements Listener {

    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL(
                    "https://api.spigotmc.org/legacy/update.php?org.spigotmc.resources=" + resourceId).openStream();
                    Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("enchantlimiter.admin")) {
            getVersion(version -> {
                if (!plugin.getDescription().getVersion().equals(version)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<gray>[<gradient:#00c6ff:#0072ff>EnchantLimiter</gradient>] <green>There is a new update available: <yellow>"
                                    + version));
                }
            });
        }
    }
}
