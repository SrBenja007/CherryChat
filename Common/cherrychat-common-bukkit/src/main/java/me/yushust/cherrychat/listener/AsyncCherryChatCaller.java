package me.yushust.cherrychat.listener;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncCherryChatCaller implements Listener {

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

}
