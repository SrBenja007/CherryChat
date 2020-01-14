package me.yushust.cherrychat.modules.module;

import com.google.common.base.Strings;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.function.Consumer;

public class BlacklistModule extends AbstractChatPluginModule {

    private ChatPlugin plugin = ChatPlugin.getInstance();

    @Override
    public Consumer<AsyncPlayerChatEvent> getChatConsumer() {
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
