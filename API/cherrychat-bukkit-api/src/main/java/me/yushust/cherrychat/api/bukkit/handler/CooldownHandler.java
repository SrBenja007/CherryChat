package me.yushust.cherrychat.api.bukkit.handler;

import java.util.concurrent.TimeUnit;

public interface CooldownHandler<T> {

    void addToCooldown(T value, long millis);

    default void addToCooldown(T value, TimeUnit unit, long cooldown) {
        this.addToCooldown(value, unit.toMillis(cooldown));
    }

    boolean isInCooldown(T value);

    long getCooldown(T value);

}
