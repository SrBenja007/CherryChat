package me.yushust.cherrychat.task;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.yushust.cherrychat.util.Announcement;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class AnnouncerTask implements Runnable {

    private static Multimap<Announcement, Long> announcements = HashMultimap.create();

    public static void startScheduling(Plugin plugin, long interval, ConfigurationSection announcementsSection) {

        long currentSecond = System.currentTimeMillis() / 1000;

        for(String name : announcementsSection.getKeys(false)) {

            ConfigurationSection announcementSection = announcementsSection.getConfigurationSection(name);

            announcements.put(
                new Announcement(announcementSection),
                currentSecond
            );
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new AnnouncerTask(), 0L, interval);
    }

    @Override
    public void run() {
        long currentSecond = System.currentTimeMillis() / 1000;
        for(Map.Entry<Announcement, Long> entry : announcements.entries()) {
            Announcement announcement = entry.getKey();
            long whenPlaced = entry.getValue();

            if((currentSecond - whenPlaced) % announcement.getPeriod() == 0) {
                announcement.announce();
            }
        }
    }

}
