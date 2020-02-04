package me.yushust.cherrychat.tests;

import org.bukkit.Achievement;
import org.junit.Test;

public class AchievementTest {

    @Test
    public void showAllAchievements() {

        for(Achievement achievement : Achievement.values()) {
            System.out.println(achievement.name() + ", ");
        }

    }

}
