package me.yushust.cherrychat.modules;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;

public interface ChatPluginModule {

    Consumer<AsyncPlayerChatEvent> getChatConsumer();

    void install(ChatPluginModule module);

    void setupIn(ChatModulesContainer container);

}
