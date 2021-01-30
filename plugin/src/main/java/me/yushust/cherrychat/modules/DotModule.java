package me.yushust.cherrychat.modules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import me.yushust.cherrychat.api.bukkit.intercept.MessageInterceptor;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor @Getter
public class DotModule implements MessageInterceptor {

    private final Plugin plugin;
    private String moduleName = "dot-module";

    @Override
    public void onChat(AsyncUserChatEvent event) {
        if(event.isCancelled()) return;
        String message = event.getMessage();
        if(message.length() >= 5) message += ".";
        event.setMessage(message);
    }

}
