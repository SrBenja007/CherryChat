package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.util.Achievements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

@RequiredArgsConstructor
public class AchievementListener implements Listener {

    private final ChatPlugin plugin;

    @EventHandler
    public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {

        if(!plugin.getConfig().getBoolean("custom-achievements-messages.enabled")) {
            return;
        }

        String message = plugin.getConfig().getString("custom-achievements-messages.message", "none");
        if(!message.equalsIgnoreCase("none")) {
            Bukkit.broadcastMessage(
                    ChatColor.translateAlternateColorCodes('&', plugin.getDefaultFormatter().setPlaceholders(
                    event.getPlayer(),
                    Achievements.setAchievement(
                            plugin.getAchievements(),
                            message,
                            event.getAchievement()
                    )
            )));
        }


    }

}
