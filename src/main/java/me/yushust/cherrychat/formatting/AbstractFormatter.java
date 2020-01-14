package me.yushust.cherrychat.formatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.cherrychat.ChatPlugin;
import org.bukkit.entity.Player;

public abstract class AbstractFormatter implements Formatter {

    protected ChatPlugin plugin = ChatPlugin.getInstance();

    @Override
    public String format(Player player, String message) {

        String format = Formatter.getDefaultFormat();

        if(plugin.isPlaceholderApiEnabled()) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        }

        if(plugin.isVaultApiEnabled()) {
            VaultFormatter vaultFormatter = new VaultFormatter();
            format = vaultFormatter.setPlaceholders(player, format);
        }

        return format(new Message(player, message, format.replace("%message%", message)));
    }

    public abstract String format(Message message);

    @AllArgsConstructor @Getter
    protected static class Message {
        private Player sender;
        private String message;
        private String format;
    }
}
