package me.yushust.cherrychat.task;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.yushust.cherrychat.util.Announcement;

public class AnnouncerTask implements Runnable {

    private static Multimap<Announcement, Long> announcements = HashMultimap.create();

    public static void schedule(Announcement announcement) {
        announcements.put(announcement, System.currentTimeMillis() / 1000);
    }

    @Override
    public void run() {
        long currentSecond = System.currentTimeMillis() / 1000;
        announcements.entries().forEach((entry) -> {
            Announcement announcement = entry.getKey();
            long whenPlaced = entry.getValue();

            if((currentSecond - whenPlaced) % announcement.getPeriod() == 0) {
                announcement.announce();
            }
        });
    }

}
