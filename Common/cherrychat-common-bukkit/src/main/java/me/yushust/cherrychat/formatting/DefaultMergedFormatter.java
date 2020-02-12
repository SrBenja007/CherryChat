package me.yushust.cherrychat.formatting;

import lombok.Getter;
import me.yushust.cherrychat.api.bukkit.formatting.Formatter;
import me.yushust.cherrychat.api.bukkit.formatting.MergedFormatter;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class DefaultMergedFormatter implements MergedFormatter {

    @Getter
    private final Set<Formatter> formatters = new LinkedHashSet<>();

    public DefaultMergedFormatter(Formatter base) {
        Objects.requireNonNull(base);
        formatters.add(base);
    }

    public DefaultMergedFormatter add(Formatter formatter) {
        addFormatter(formatter);
        return this;
    }

    @Override
    public void addFormatter(Formatter formatter) {
        formatters.add(formatter);
    }

    @Override
    public String format(Player player, String format) {
        for(Formatter formatter : formatters) {
            format = formatter.format(player, format);
        }
        return format;
    }

    @Override
    public String setPlaceholders(Player player, String text) {
        for(Formatter formatter : formatters) {
            text = formatter.setPlaceholders(player, text);
        }
        return text;
    }

}
