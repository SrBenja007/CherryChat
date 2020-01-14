package me.yushust.cherrychat.modules.module;

import me.yushust.cherrychat.manager.DomainValidator;
import me.yushust.cherrychat.manager.IpAddressValidator;
import me.yushust.cherrychat.manager.StringValidator;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;

public class SpamFilterModule extends AbstractChatPluginModule {

    private StringValidator domainValidator = new DomainValidator();
    private StringValidator ipAddressValidator = new IpAddressValidator();

    @Override
    public Consumer<AsyncPlayerChatEvent> getChatConsumer() {
        return event -> {
            String message = event.getMessage();

            message = domainValidator.replaceAll(message, "*");
            message = ipAddressValidator.replaceAll(message, "*");

            event.setMessage(message);
        };
    }

}
