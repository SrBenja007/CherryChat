package me.yushust.cherrychat.api.bukkit.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedCooldownHandler<T> implements CooldownHandler<T> {

    private Map<T, Long> cooldownCache = new ConcurrentHashMap<>();

    @Override
    public void addToCooldown(T value, long millis) {
        if(!cooldownCache.containsKey(value)) {
            cooldownCache.put(value, millis);
        }
    }

    @Override
    public boolean isInCooldown(T value) {
        long currentMillis = System.currentTimeMillis();
        long expired = getCooldown(value);
        if(expired <= currentMillis) {
            cooldownCache.remove(value);
            return false;
        }
        return true;
    }

    @Override
    public long getCooldown(T value) {
        return cooldownCache.getOrDefault(value, -1L);
    }

}
