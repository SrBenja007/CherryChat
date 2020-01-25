package me.yushust.cherrychat.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public final class AsyncCherryChatEvent extends AsyncPlayerChatEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private boolean asCommand;

    public AsyncCherryChatEvent(boolean async, Player who, String message, Set<Player> players, boolean asCommand) {
        super(async, who, message, players);
        this.asCommand = asCommand;
    }

    public final HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
