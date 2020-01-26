package me.yushust.cherrychat.modules;

import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractChatPluginModule implements ChatPluginModule {

    protected final Set<Consumer<AsyncCherryChatEvent>> listeners = new HashSet<>();

    @Override
    public void install(ChatPluginModule module) {
        listeners.add(module.getChatConsumer());
    }

    @Override
    public void setupIn(ChatModulesContainer container) {
        listeners.add(this.getChatConsumer());
        Set<Consumer<AsyncCherryChatEvent>> listenerSet = container.getListeners();
        if(listenerSet == null) listenerSet = new HashSet<>();
        listenerSet.addAll(listeners);
    }

}
