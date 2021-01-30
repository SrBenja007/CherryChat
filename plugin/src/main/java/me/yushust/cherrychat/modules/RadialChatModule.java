package me.yushust.cherrychat.modules;

import lombok.Getter;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.api.bukkit.util.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Getter
public class RadialChatModule implements ChatPluginModule {

    private boolean enabled;
    private boolean different;
    private int radiusX;
    private int radiusY;
    private int radiusZ;
    private CherryChatPlugin plugin;
    private String moduleName = "radial-chat";

    public RadialChatModule(CherryChatPlugin plugin) {
        Configuration config = plugin.getConfig();
        this.plugin = plugin;
        this.enabled = config.getBoolean("radial-chat.enabled");
        this.different = config.contains("radial-chat.radius.x");
        if(different) {
            this.radiusX = config.getInt("radial-chat.radius.x", 100);
            this.radiusY = config.getInt("radial-chat.radius.y", 100);
            this.radiusZ = config.getInt("radial-chat.radius.z", 100);
        } else {
            this.radiusX = config.getInt("radial-chat.radius", 100);
        }
    }

    @Override
    public void onChat(AsyncCherryChatEvent event) {

        if(!enabled) return;
        if(event.isCancelled()) return;

        Player player = event.getPlayer();

        event.getRecipients().clear();

        for(Entity entity : player.getWorld().getNearbyEntities(
                player.getLocation(),
                radiusX,
                different ? radiusY : radiusX,
                different ? radiusZ : radiusX
        )) {
            if(!(entity instanceof Player)) continue;
            event.getRecipients().add((Player) entity);
        }

    }

}
