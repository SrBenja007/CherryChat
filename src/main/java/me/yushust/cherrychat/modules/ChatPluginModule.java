package me.yushust.cherrychat.modules;

import me.yushust.cherrychat.event.AsyncCherryChatEvent;

import java.util.function.Consumer;

public interface ChatPluginModule {

    Consumer<AsyncCherryChatEvent> getChatConsumer();

    void install(ChatPluginModule module);

    void setupIn(ChatModulesContainer container);

}
