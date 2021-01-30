package me.yushust.cherrychat.api.bukkit.module;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;

import java.util.Set;

public interface ChatPluginModuleManager {

    Set<ChatPluginModule> getRegistrations();

    void install(ChatPluginModule module);

    void handleChat(AsyncCherryChatEvent event);

    void install(ChatPluginModuleManager modules);

}
