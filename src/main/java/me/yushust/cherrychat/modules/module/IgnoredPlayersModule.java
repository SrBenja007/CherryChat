package me.yushust.cherrychat.modules.module;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.model.User;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class IgnoredPlayersModule extends AbstractChatPluginModule {

    private final ChatPlugin plugin;

    @Override
    public Consumer<AsyncCherryChatEvent> getChatConsumer() {
        return event -> {
            if(event.isCancelled()) return;

            Player player = event.getPlayer();
            Set<Player> recipients = new HashSet<>(event.getRecipients());
            for(Player recipient : recipients) {
                User recipientUser = plugin.getUserDataHandler().findSync(recipient.getUniqueId());
                if(recipientUser.getIgnoredPlayers().contains(player.getUniqueId().toString())) {
                    event.getRecipients().remove(recipient);
                }
            }
        };
    }

}
