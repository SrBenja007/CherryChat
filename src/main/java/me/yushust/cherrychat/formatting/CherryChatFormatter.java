package me.yushust.cherrychat.formatting;

import me.yushust.cherrychat.ChatPlugin;
import org.bukkit.entity.Player;

public class CherryChatFormatter extends AbstractFormatter {

    public CherryChatFormatter(ChatPlugin plugin) {
        super(plugin);
    }

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
