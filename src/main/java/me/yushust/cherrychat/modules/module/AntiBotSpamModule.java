package me.yushust.cherrychat.modules.module;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class AntiBotSpamModule extends AbstractChatPluginModule {

    private final ChatPlugin plugin;

    @Override
    public Consumer<AsyncPlayerChatEvent> getChatConsumer() {
        return event -> {
            Player player = event.getPlayer();
            if(!plugin.getPlayersMoved().contains(player.getUniqueId())) {
                player.sendMessage(
                        plugin.getLanguage().getString("please-move")
                );
                event.setCancelled(true);
            }
        };
    }

}
