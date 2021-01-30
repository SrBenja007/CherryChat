package me.yushust.cherrychat.modules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor @Getter
public class DotModule implements ChatPluginModule {

    private final Plugin plugin;
    private String moduleName = "dot-module";

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;
        String message = event.getMessage();
        if(message.length() >= 5) message += ".";
        event.setMessage(message);
    }

}
