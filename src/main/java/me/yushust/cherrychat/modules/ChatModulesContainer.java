package me.yushust.cherrychat.modules;

import lombok.Getter;
import lombok.Setter;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ChatModulesContainer {

    @Setter @Getter
    private Set<Consumer<AsyncCherryChatEvent>> listeners;

    public void installModule(ChatPluginModule module) {
        if(listeners == null) listeners = new HashSet<>();
        listeners.add(module.getChatConsumer());
    }

    public void acceptAll(AsyncCherryChatEvent event) {
        listeners.forEach(listener -> listener.accept(event));
    }

}
