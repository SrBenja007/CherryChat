package me.yushust.cherrychat.modules.module;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class MentionsModule extends AbstractChatPluginModule {

    private final ChatPlugin plugin;

    @Override
    public Consumer<AsyncCherryChatEvent> getChatConsumer() {
        return event -> {

            if(event.isCancelled()) return;

            String message = event.getMessage();

            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.getUniqueId().equals(event.getPlayer().getUniqueId())) continue;
                String mentionFormat = plugin.getConfig().getString("chat-format.mention").replace("%name%", player.getName());
                message = message.replace(player.getName(), mentionFormat);
            }

            event.setMessage(message);

        };
    }

}
