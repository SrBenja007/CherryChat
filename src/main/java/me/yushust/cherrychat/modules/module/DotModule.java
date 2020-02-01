package me.yushust.cherrychat.modules.module;

import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;

import java.util.function.Consumer;

/**
 * Este módulo solo pone
 * un punto al final de un
 * mensaje con 5 caracteres
 * o más xDD
 */
public class DotModule extends AbstractChatPluginModule {

    @Override
    public Consumer<AsyncCherryChatEvent> getChatConsumer() {
        return event -> {
            if(event.isCancelled()) return;
            String message = event.getMessage();
            if(message.length() >= 5)
                message = message + ".";
            event.setMessage(message);
        };
    }

}
