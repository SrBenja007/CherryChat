package me.yushust.cherrychat.api.bukkit.module;

public enum ModulePriority {

    HIGHEST(12),
    HIGH(9),
    NORMAL(6),
    LOW(3),
    LOWEST(0);

    private int priority;
    ModulePriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
