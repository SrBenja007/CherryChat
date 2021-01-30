package me.yushust.cherrychat.api.bukkit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class AsyncCherryChatEvent extends AsyncPlayerChatEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean asCommand;

    public AsyncCherryChatEvent(boolean async, Player who, String message, Set<Player> players, boolean asCommand) {
        super(async, who, message, players);
        this.asCommand = asCommand;
    }

    public boolean isCalledAsCommand() {
        return asCommand;
    }

    public final HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
