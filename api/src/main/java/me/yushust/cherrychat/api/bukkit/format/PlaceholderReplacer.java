package me.yushust.cherrychat.api.bukkit.format;

import org.bukkit.entity.Player;

/**
 * Interface responsible of replacing
 * placeholders present in a text
 */
@FunctionalInterface
public interface PlaceholderReplacer {

  /** Sets the placeholders to the specified {@code message} */
  String setPlaceholders(
      Player sender,
      Player receiver,
      String message
  );

}
