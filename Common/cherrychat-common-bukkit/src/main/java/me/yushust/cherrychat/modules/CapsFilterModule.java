package me.yushust.cherrychat.modules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.util.Texts;

@RequiredArgsConstructor @Getter
public class CapsFilterModule implements ChatPluginModule {

    private final CherryChatPlugin plugin;
    private String moduleName = "caps-filter";

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        boolean capitalizeFirstLetter = plugin.getConfig().getBoolean("capitalize-first-letter");

        String message = event.getMessage();
        int minCapitalizedChars = plugin.getConfig().getInt("min-capitalized-chars");

        message = Texts.toLowerCase(
                message,
                minCapitalizedChars
        );

        if(capitalizeFirstLetter) {
            message = Texts.capitalizeFirst(message);
        }

        event.setMessage(message);
    }
}
