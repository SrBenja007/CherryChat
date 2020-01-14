package me.yushust.cherrychat.formatting;

import org.bukkit.entity.Player;

public interface Formatter {

    static String getDefaultFormat() {
        return "<%displayname%> %message%";
    }

    String format(Player player, String message);

    String setPlaceholders(Player player, String text);

}
