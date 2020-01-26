package me.yushust.cherrychat.modules.module;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class BlacklistModule extends AbstractChatPluginModule {

    private final ChatPlugin plugin;

    @Override
    public Consumer<AsyncCherryChatEvent> getChatConsumer() {
        return event -> {

            List<String> badWords = plugin.getConfig().getStringList("blacklisted-words");
            String message = event.getMessage();

            for(String badWord : badWords) {
                if(message.contains(badWord)) {
                    message = message.replaceAll(badWord, Strings.repeat("*", badWord.length()));
                }
            }

            event.setMessage(message);
        };
    }

}
