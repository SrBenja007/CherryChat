package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class QuitListener implements Listener {

    private final ChatPlugin plugin;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getPlayersMoved().remove(event.getPlayer().getUniqueId());
    }

}
