package me.yushust.cherrychat.modules;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.handler.CachedCooldownHandler;
import me.yushust.cherrychat.api.bukkit.handler.CooldownHandler;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

@RequiredArgsConstructor
public class CooldownModule implements ChatPluginModule {

    private final CherryChatPlugin plugin;
    private CooldownHandler<UUID> cooldownHandler = new CachedCooldownHandler<>();

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        Player player = event.getPlayer();

        String cooldownBypassPermission = plugin.getConfig().getString("cooldown.bypass-permission", "none");
        if(cooldownBypassPermission.equals("none") || player.hasPermission(cooldownBypassPermission)) {
            return;
        }

        if(cooldownHandler.isInCooldown(player.getUniqueId())) {
            long cooldown = cooldownHandler.getCooldown(player.getUniqueId());
            double cooldownInSeconds = cooldown / 1000D;
            DecimalFormat format = new DecimalFormat("0.00");
            player.sendMessage(plugin.getLanguage().getString("you-are-in-cooldown")
                    .replace("%cooldown%", format.format(cooldownInSeconds)));
            event.setCancelled(true);
            return;
        }

        cooldownHandler.addToCooldown(player.getUniqueId(), plugin.getConfig().getInt("cooldown.time-seconds") * 1000L);
    }
}
