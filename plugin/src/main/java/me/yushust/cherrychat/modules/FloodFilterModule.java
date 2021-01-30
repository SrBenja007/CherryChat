package me.yushust.cherrychat.modules;

import lombok.Getter;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import me.yushust.cherrychat.api.bukkit.intercept.MessageInterceptor;
import me.yushust.cherrychat.util.Texts;

@Getter
public class FloodFilterModule implements MessageInterceptor {

    private final CherryChatPlugin plugin;
    private String moduleName = "flood-filter";
    private int minChars;

    public FloodFilterModule(CherryChatPlugin plugin) {
        this.plugin = plugin;
        this.minChars = plugin.getConfig().getInt("min-chars-considered-flood", 6);
    }

    @Override
    public void onChat(AsyncUserChatEvent event) {
        if(event.isCancelled()) return;

        String message = event.getMessage();
        String floodFilteredMessage = Texts.decreaseAlphaNumerics(message, minChars);

        event.setMessage(floodFilteredMessage);
    }
}
