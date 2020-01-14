package me.yushust.cherrychat.modules.module;

import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.manager.CooldownHandler;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class CooldownModule extends AbstractChatPluginModule {

    private ChatPlugin plugin = ChatPlugin.getInstance();
    private CooldownHandler<UUID> cooldownHandler = new CooldownHandler<>();

    @Override
    public Consumer<AsyncPlayerChatEvent> getChatConsumer() {
        return event -> {
            Player player = event.getPlayer();

            if(cooldownHandler.isInCooldown(player.getUniqueId())) {
                player.sendMessage(plugin.getLanguage().getString("you-are-in-cooldown")
                        .replace("%cooldown%", cooldownHandler.getCooldown(player.getUniqueId(), "0.00")));
                event.setCancelled(true);
                return;
            }

            cooldownHandler.addToCooldown(player.getUniqueId(), plugin.getConfig().getInt("cooldown-seconds") * 1000L);
        };
    }

}
