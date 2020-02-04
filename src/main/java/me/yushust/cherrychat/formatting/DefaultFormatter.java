package me.yushust.cherrychat.formatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.cherrychat.ChatPlugin;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class DefaultFormatter implements Formatter {

    protected final ChatPlugin plugin;

    @Override
    public String format(Player player, String message) {
        return format(player, Formatter.getDefaultFormat(), message);
    }

    protected final String format(Player player, String format, String message) {
        return setPlaceholders(player, format.replace("%message%", message));
    }

    @Override
    public String setPlaceholders(Player player, String text) {
        if(text == null) return null;

        if(plugin.isPlaceholderApiEnabled()) {
            text = PlaceholderAPI.setPlaceholders(player, text);
        }
        if(plugin.isVaultApiEnabled()) {
            VaultFormatter vaultFormatter = new VaultFormatter(plugin);
            text = vaultFormatter.setPlaceholders(player, text);
        }
        return text
                .replace("%name%", player.getName())
                .replace("%displayname%", player.getDisplayName());
    }

    @AllArgsConstructor @Getter
    protected static class Message {
        private Player sender;
        private String message;
        private String format;
    }
}
