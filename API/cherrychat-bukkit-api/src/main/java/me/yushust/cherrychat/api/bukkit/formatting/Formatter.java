package me.yushust.cherrychat.api.bukkit.formatting;

import org.bukkit.entity.Player;

public interface Formatter {

    String format(Player player, String format);

    String setPlaceholders(Player player, String text);

    Formatter merge(Formatter otherFormatter);

}
