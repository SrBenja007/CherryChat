package me.yushust.cherrychat.listener;

import me.yushust.cherrychat.ChatPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

    private ChatPlugin plugin = ChatPlugin.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player sender = event.getPlayer();

        String format = plugin.getFormatter().format(sender, event.getMessage());
        event.setFormat(format);

        plugin.getModuleContainer().acceptAll(event);
    }

}
