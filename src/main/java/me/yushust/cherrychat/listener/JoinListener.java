package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.util.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class JoinListener implements Listener {

    private final ChatPlugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Configuration config = plugin.getConfig();

        plugin.getUserDataHandler().refresh(player.getUniqueId());

        String message = event.getJoinMessage();
        for(String type : config.getConfigurationSection("join-quit-messages.join").getKeys(false)) {
            String permission = config.getString("join-quit-messages.join." + type + ".permission", "none");
            if(permission.isEmpty() || permission.equals("none") || player.hasPermission(permission)) {
                message = config.getString("join-quit-messages.join." + type + ".message", "none");
            }
        }
        if(message.equalsIgnoreCase("none")) {
            message = null;
        } else {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        event.setJoinMessage(plugin.getDefaultFormatter().setPlaceholders(player, message));
    }

}
