package me.yushust.cherrychat.modules;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PerWorldChatModule implements ChatPluginModule {

    private final CherryChatPlugin plugin;


    private List<String> getSharedWorlds(String world) {
        List<String> sharedWorlds = plugin.getConfig().getStringList("per-world-chat.share." + world, new ArrayList<>());
        sharedWorlds.add(world);
        return sharedWorlds;
    }

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        boolean perWorldChatEnabled = plugin.getConfig().getBoolean("per-world-chat.enabled");

        if(!perWorldChatEnabled) return;

        Player player = event.getPlayer();
        event.getRecipients().clear();
        for(String worldName : getSharedWorlds(player.getWorld().getName())) {
            World world = Bukkit.getWorld(worldName);
            event.getRecipients().addAll(world.getPlayers());
        }
    }
}
