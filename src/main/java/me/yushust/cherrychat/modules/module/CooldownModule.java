package me.yushust.cherrychat.modules.module;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.manager.CooldownHandler;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class CooldownModule extends AbstractChatPluginModule {

    private final ChatPlugin plugin;
    private CooldownHandler<UUID> cooldownHandler = new CooldownHandler<>();

    @Override
    public Consumer<AsyncCherryChatEvent> getChatConsumer() {
        return event -> {

            if(event.isCancelled()) return;

            Player player = event.getPlayer();

            String cooldownBypassPermission = plugin.getConfig().getString("cooldown.bypass-permission");
            if(player.hasPermission(cooldownBypassPermission)) {
                return;
            }

            if(cooldownHandler.isInCooldown(player.getUniqueId())) {
                player.sendMessage(plugin.getLanguage().getString("you-are-in-cooldown")
                        .replace("%cooldown%", cooldownHandler.getCooldown(player.getUniqueId(), "0.00")));
                event.setCancelled(true);
                return;
            }

            cooldownHandler.addToCooldown(player.getUniqueId(), plugin.getConfig().getInt("cooldown.time-seconds") * 1000L);
        };
    }

}
