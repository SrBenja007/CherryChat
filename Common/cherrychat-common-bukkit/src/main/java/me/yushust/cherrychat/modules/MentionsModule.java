package me.yushust.cherrychat.modules;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MentionsModule implements ChatPluginModule {

    private final CherryChatPlugin plugin;

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        String message = event.getMessage();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getUniqueId().equals(event.getPlayer().getUniqueId())) continue;
            String mentionFormat = plugin.getConfig().getString("chat-format.mention").replace("%name%", player.getName());
            message = message.replace(player.getName(), mentionFormat);
        }

        event.setMessage(message);
    }
}
