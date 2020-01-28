package me.yushust.cherrychat.messaging;

import org.bukkit.entity.Player;

public interface PlayerChannelListener extends ChannelListener {

    int getArgumentCount();

    void onMessage(Player player, String[] arguments);

    @Override
    default void onMessage(String message) {
        this.onMessage(null, new String[]{message});
    }
}
