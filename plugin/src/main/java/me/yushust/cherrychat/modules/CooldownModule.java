package me.yushust.cherrychat.modules;

import lombok.Getter;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import me.yushust.cherrychat.api.bukkit.intercept.MessageInterceptor;
import me.yushust.cherrychat.api.bukkit.util.Configuration;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

@Getter
public class CooldownModule implements MessageInterceptor {

    private CherryChatPlugin plugin;
    private String moduleName = "cool-down";
    private String cooldownBypassPermission;
    private int cooldownSeconds;
    private CooldownHandler<UUID> cooldownHandler = new CachedCooldownHandler<>();
    private String cooldownMessage;

    public CooldownModule(CherryChatPlugin plugin) {
        Configuration config = plugin.getConfig();
        this.plugin = plugin;
        this.cooldownBypassPermission = config.getString("cooldown.bypass-permission", "none");
        this.cooldownSeconds = config.getInt("cooldown.time-seconds");
        this.cooldownMessage = plugin.getLanguage().getString("you-are-in-cooldown");
    }

    @Override
    public void onChat(AsyncUserChatEvent event) {
        if(event.isCancelled()) return;

        Player player = event.getPlayer();

        if(cooldownBypassPermission.equals("none") || player.hasPermission(cooldownBypassPermission)) {
            return;
        }

        if(cooldownHandler.isInCooldown(player.getUniqueId())) {
            long cooldown = cooldownHandler.getCooldown(player.getUniqueId());
            double cooldownInSeconds = cooldown / 1000D;
            DecimalFormat format = new DecimalFormat("0.00");
            player.sendMessage(cooldownMessage
                    .replace("%cooldown%", format.format(cooldownInSeconds)));
            event.setCancelled(true);
            return;
        }

        cooldownHandler.addToCooldown(player.getUniqueId(), cooldownSeconds * 1000L);
    }
}
