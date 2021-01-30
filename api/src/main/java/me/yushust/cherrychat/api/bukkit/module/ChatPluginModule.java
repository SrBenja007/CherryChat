package me.yushust.cherrychat.api.bukkit.module;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import org.bukkit.plugin.Plugin;

public interface ChatPluginModule {

    Plugin getPlugin();

    String getModuleName();

    void onChat(AsyncCherryChatEvent event);

    default ModulePriority getPriority() {
        return ModulePriority.NORMAL;
    }

}
