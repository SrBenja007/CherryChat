package me.yushust.cherrychat.modules;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.util.Texts;

@RequiredArgsConstructor
public class FloodFilterModule implements ChatPluginModule {

    private final CherryChatPlugin plugin;

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        String message = event.getMessage();
        int minChars = plugin.getConfig().getInt("min-chars-considered-flood");
        String floodFilteredMessage = Texts.decreaseAlphaNumerics(message, minChars);

        event.setMessage(floodFilteredMessage);
    }
}
