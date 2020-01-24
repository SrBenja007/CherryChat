package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class AsyncChatListener implements Listener {

    private final ChatPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player sender = event.getPlayer();

        plugin.getModuleContainer().acceptAll(event);

        if(event.isCancelled()) {
            return;
        }

        String format = plugin.getFormatter().format(sender, event.getMessage());
        event.setFormat(format);
    }

}
