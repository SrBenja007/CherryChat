package me.yushust.cherrychat.util;

import org.bukkit.Achievement;

public class Achievements {

    private Achievements() throws IllegalAccessException {
        throw new IllegalAccessException("This class couldn't be instantiated!");
    }

    public static String setAchievement(Configuration config, String message, Achievement achievement) {
        return message.replace("%achievement%", getAchievement(config, achievement));
    }

    public static String getAchievement(Configuration config, Achievement achievement) {
        String name = achievement.name().toLowerCase().replace("_", "-");
        return config.getString(name, Texts.asTitle(name.replace("-", " ")));
    }

}
