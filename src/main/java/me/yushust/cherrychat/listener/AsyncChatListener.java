package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class AsyncChatListener implements Listener {

    private final ChatPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) {
            return;
        }
        AsyncCherryChatEvent cherryChatEvent = new AsyncCherryChatEvent(
                event.isAsynchronous(),
                event.getPlayer(),
                event.getMessage(),
                event.getRecipients(),
                false
        );
        Bukkit.getPluginManager().callEvent(cherryChatEvent);

        event.setMessage(cherryChatEvent.getMessage());
        event.setFormat(cherryChatEvent.getFormat());
        event.setCancelled(cherryChatEvent.isCancelled());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCherryChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player sender = event.getPlayer();

        plugin.getModuleContainer().acceptAll(event);

        if(event.isCancelled()) {
            return;
        }

        if(event.isAsCommand()) {
            return;
        }

        String format = plugin.getFormatter().format(sender, event.getMessage());
        event.setFormat(format);
    }

}
