package me.yushust.cherrychat.formatting;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.formatting.Formatter;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class DefaultFormatter implements Formatter {

    protected final CherryChatPlugin plugin;

    @Override
    public String format(Player player, String message) {
        return format(player, "%name%: %message%", message);
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

    @Override
    public Formatter merge(Formatter otherFormatter) {
        return new DefaultMergedFormatter(this).add(otherFormatter);
    }

}
