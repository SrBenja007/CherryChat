package me.yushust.cherrychat.modules;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;

public class DotModule implements ChatPluginModule {

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;
        String message = event.getMessage();
        if(message.length() >= 5) message += ".";
        event.setMessage(message);
    }

}
