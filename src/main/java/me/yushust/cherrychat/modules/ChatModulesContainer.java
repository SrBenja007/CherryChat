package me.yushust.cherrychat.modules;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ChatModulesContainer {

    @Setter @Getter
    private Set<Consumer<AsyncPlayerChatEvent>> listeners;

    public void installModule(ChatPluginModule module) {
        if(listeners == null) listeners = new HashSet<>();
        listeners.add(module.getChatConsumer());
    }

    public void acceptAll(AsyncPlayerChatEvent event) {
        listeners.forEach(listener -> listener.accept(event));
    }

}
