package me.yushust.cherrychat.modules.module;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import me.yushust.cherrychat.util.Texts;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CapsFilterModule extends AbstractChatPluginModule {

    private final ChatPlugin plugin;

    @Override
    public Consumer<AsyncCherryChatEvent> getChatConsumer() {
            return event -> {

                boolean capitalizeFirstLetter = plugin.getConfig().getBoolean("capitalize-first-letter");

                String message = event.getMessage();
                int minCapitalizedChars = plugin.getConfig().getInt("min-capitalized-chars");

                message = Texts.toLowerCase(
                        message,
                        Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()),
                        minCapitalizedChars
                );

                if(capitalizeFirstLetter) {
                    message = Texts.capitalizeFirst(message);
                }

                event.setMessage(message);

            };
    }

}
