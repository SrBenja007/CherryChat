package me.yushust.cherrychat.manager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CooldownHandler<T> {

    private Map<T, Long> cooldown = new HashMap<>();

    public String getCooldown(T value, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        long current = System.currentTimeMillis();
        long expire = cooldown.getOrDefault(value, current) - current;
        double expireSeconds = expire / 1000D;
        return decimalFormat.format(expireSeconds);
    }

    public void addToCooldown(T value, long cooldownMillis) {
        if(!cooldown.containsKey(value)) {
            cooldown.put(value, System.currentTimeMillis() + cooldownMillis);
        }
    }

    public boolean isInCooldown(T value) {
        long currentMillis = System.currentTimeMillis();
        Long expired = cooldown.getOrDefault(value, 0L);
        if(expired <= currentMillis) {
            cooldown.remove(value);
            return false;
        }
        return true;
    }

}
