package me.yushust.cherrychat.listener;

import lombok.AllArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.util.SetUtil;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

@AllArgsConstructor
public class MoveListener implements Listener {

    private final ChatPlugin plugin;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        if(plugin.getPlayersMoved().contains(playerId)) return;

        Location oldLocation = event.getFrom();
        Location newLocation = event.getTo();

        if(oldLocation.distance(newLocation) > 0) {
            SetUtil.add(plugin::getPlayersMoved, plugin::setPlayersMoved, playerId);
        }
    }

}
