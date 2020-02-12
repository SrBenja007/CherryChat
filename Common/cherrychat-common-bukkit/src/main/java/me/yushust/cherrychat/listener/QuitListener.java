package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.util.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class QuitListener implements Listener {

    private final CherryChatPlugin plugin;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.getPlayersMoved().remove(player.getUniqueId());

        Configuration config = plugin.getConfig();

        plugin.getUserDataHandler().refresh(player.getUniqueId());

        String message = event.getQuitMessage();
        for(String type : config.getConfigurationSection("join-quit-messages.quit").getKeys(false)) {
            String permission = config.getString("join-quit-messages.quit." + type + ".permission", "none");
            if(permission.isEmpty() || permission.equals("none") || player.hasPermission(permission)) {
                message = config.getString("join-quit-messages.quit." + type + ".message", "none");
            }
        }
        if(message.equalsIgnoreCase("none")) {
            message = null;
        } else {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        event.setQuitMessage(plugin.getDefaultFormatter().setPlaceholders(player, message));
    }

}
