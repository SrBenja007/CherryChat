package me.yushust.cherrychat.formatting;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.format.PlaceholderReplacer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

@RequiredArgsConstructor
public class DefaultFormatter implements PlaceholderReplacer {

    protected final CherryChatPlugin plugin;
    private final Set<PlaceholderReplacer> formatters = new LinkedHashSet<>();

    @Override
    public String format(Player player, String message) {
        return format(player, "%name%: %message%", message);
    }

    protected final String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
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

        for(PlaceholderReplacer formatter : formatters) {
            text = formatter.setPlaceholders(player, text);
        }

        return text
                .replace("%name%", player.getName())
                .replace("%displayname%", player.getDisplayName());
    }

    @Override
    public PlaceholderReplacer merge(PlaceholderReplacer otherFormatter) {
        formatters.add(otherFormatter);
        return this;
    }

}
