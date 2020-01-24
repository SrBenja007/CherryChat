package me.yushust.cherrychat.modules.module;

import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;

public class CapsFilterModule extends AbstractChatPluginModule {

    @Override
    public Consumer<AsyncPlayerChatEvent> getChatConsumer() {
            return event -> {

            };
    }

}
