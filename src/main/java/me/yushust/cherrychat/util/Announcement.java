package me.yushust.cherrychat.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.yushust.cherrychat.task.AnnouncerTask;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public class Announcement {

    private List<BaseComponent> components;
    private int period;
    private String permission;

    public void schedule() {
        AnnouncerTask.schedule(this);
    }

    public void announce() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> {
                    if(permission == null) return true;
                    return player.hasPermission(permission);
                }).forEach(player ->
                    player.spigot().sendMessage(components.toArray(new BaseComponent[0]))
                );
    }

    public static List<Announcement> getAllFrom(ConfigurationSection section) {
        List<Announcement> announcementList = new ArrayList<>();
        for(String path : section.getKeys(false)) {
            announcementList.add(Announcement.getFrom(section.getConfigurationSection(path)));
        }
        return announcementList;
    }

    private static Announcement getFrom(ConfigurationSection section) {
        boolean legacy = section.getBoolean("json", false);
        List<String> textLines = section.getStringList("text");
        int periodSeconds = section.getInt("period", 120);
        String permission = section.getString("permission", null);

        List<BaseComponent> components = new ArrayList<>();
        for(String line : textLines) {
            line = ChatColor.translateAlternateColorCodes('&', line);
            if(legacy) {
                components.addAll(Arrays.asList(ComponentSerializer.parse(line)));
            } else {
                components.add(new TextComponent(line + "\n"));
            }
        }

        return new Announcement(components, periodSeconds, permission);
    }

}
