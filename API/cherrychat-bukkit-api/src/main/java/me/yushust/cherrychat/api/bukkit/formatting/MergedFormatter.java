package me.yushust.cherrychat.api.bukkit.formatting;

import java.util.Set;

public interface MergedFormatter extends Formatter {

    Set<Formatter> getFormatters();

    void addFormatter(Formatter formatter);

    @Override
    default MergedFormatter merge(Formatter formatter) {
        addFormatter(formatter);
        return this;
    }

}
