package me.yushust.cherrychat.listener;

import me.yushust.cherrychat.CherryChatStoraging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RefreshingHandler implements Listener {

    private CherryChatStoraging plugin;

    public RefreshingHandler(CherryChatStoraging plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.getUserDataHandler().refresh(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.getUserDataHandler().refresh(player.getUniqueId());
    }

}
