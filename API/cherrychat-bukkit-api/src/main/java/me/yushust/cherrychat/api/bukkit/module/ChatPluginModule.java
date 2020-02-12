package me.yushust.cherrychat.api.bukkit.module;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;

public interface ChatPluginModule {

    void onChat(AsyncCherryChatEvent event);

    default ModulePriority getPriority() {
        return ModulePriority.NORMAL;
    }

}
