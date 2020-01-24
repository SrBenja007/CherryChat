package me.yushust.cherrychat.modules.module;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import me.yushust.cherrychat.util.Texts;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class FloodFilterModule extends AbstractChatPluginModule  {

    private final ChatPlugin plugin;

    @Override
    public Consumer<AsyncPlayerChatEvent> getChatConsumer() {
        return event -> {

            String message = event.getMessage();
            int minChars = plugin.getConfig().getInt("min-chars-considered-flood");
            String floodFilteredMessage = Texts.decreaseAlphaNumerics(message, minChars);

            event.setMessage(floodFilteredMessage);

        };
    }

}
