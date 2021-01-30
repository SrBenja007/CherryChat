package me.yushust.cherrychat.api.bukkit.delay;

/**
 * Class responsible of handle the
 * delay for specific objects
 * @param <T> The guarded object
 */
public interface DelayHolder<T> {

  /**
   * Sets the delay remaining time for the specified
   * {@code object} to the specified {@code remainingMillis}
   */
  void setRemainingTime(T object, long remainingMillis);

  /** @return The remaining delay time in millis */
  long getRemainingTime(T object);

  /**
   * Sets the delay if the {@code object} has no a delay
   * @return The present delay, zero if absent.
   */
  long setIfExpired(T object, long remaining);

}
