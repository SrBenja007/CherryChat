package me.yushust.cherrychat.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public class Announcement {

    private List<BaseComponent> components = new ArrayList<>();
    private int period;
    private String permission;

    public Announcement(ConfigurationSection section) {
        boolean legacy = section.getBoolean("json", false);
        List<String> textLines = section.getStringList("text");
        this.period = section.getInt("period", 120);
        this.permission = section.getString("permission", null);
        this.setupComponents(textLines, legacy);
    }

    private void setupComponents(List<String> rawLines, boolean legacy) {
        for(int i = 0; i < rawLines.size(); i++) {
            String text = rawLines.get(i);
            String coloredText = ChatColor.translateAlternateColorCodes('&', text);

            if(legacy) {
                components.addAll(Arrays.asList(ComponentSerializer.parse(coloredText)));
                continue;
            }

            components.add(new TextComponent(
                    i == rawLines.size() - 1 ?
                            coloredText :
                            coloredText + "\n"
            ));
        }
    }

    public void announce() {
        boolean usePermission = (permission != null && !permission.equalsIgnoreCase("none"));
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(!usePermission || player.hasPermission(permission)) {
                player.spigot().sendMessage(
                        components.toArray(new BaseComponent[0])
                );
            }
        }
    }

}
