package me.yushust.cherrychat.formatting;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CherryChatFormatter extends AbstractFormatter {

    private final ChatPlugin plugin;

    @Override
    public String format(Message message) {
        return this.setPlaceholders(message.getSender(), plugin.getConfig().getString("chat-format.format"))
                .replace("%message%", message.getMessage());
    }

    @Override
    public String setPlaceholders(Player player, String text) {
        return text
                .replace("%name%", player.getName())
                .replace("%displayname%", player.getDisplayName());
    }
}
