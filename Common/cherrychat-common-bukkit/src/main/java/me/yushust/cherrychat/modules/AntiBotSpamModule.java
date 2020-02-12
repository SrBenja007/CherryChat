package me.yushust.cherrychat.modules;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class AntiBotSpamModule implements ChatPluginModule {

    private final CherryChatPlugin plugin;
    @Override
    public void onChat(AsyncCherryChatEvent event) {
        Player player = event.getPlayer();
        if(!plugin.getPlayersMoved().contains(player.getUniqueId())) {
            player.sendMessage(
                    plugin.getLanguage().getString("please-move")
            );
            event.setCancelled(true);
        }
    }

}
