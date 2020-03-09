package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class AsyncChatListener implements Listener {

    private final CherryChatPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCherryChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        Player sender = event.getPlayer();

        plugin.getModuleManager().handleChat(event);

        if(event.isCancelled()) return;

        String message = event.getMessage();
        if(sender.hasPermission(plugin.getConfig().getString("chat-format.color-permission")))
            message = ChatColor.translateAlternateColorCodes('&', message);

        if(event.isCalledAsCommand()) {
            event.setMessage(message);
            return;
        }

        String format = plugin.getFormatter().format(sender, message);

        // confusing lines xD
        event.setFormat("%2$s");
        event.setMessage(format);
    }

}
