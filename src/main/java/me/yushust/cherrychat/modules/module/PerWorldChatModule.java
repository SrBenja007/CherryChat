package me.yushust.cherrychat.modules.module;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.modules.AbstractChatPluginModule;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class PerWorldChatModule extends AbstractChatPluginModule {

    private final ChatPlugin plugin;

    @Override
    public Consumer<AsyncCherryChatEvent> getChatConsumer() {
        return event -> {
            if(event.isCancelled()) return;

            boolean perWorldChatEnabled = plugin.getConfig().getBoolean("per-world-chat.enabled");

            if(!perWorldChatEnabled) return;

            Player player = event.getPlayer();
            event.getRecipients().clear();
            for(String worldName : getSharedWorlds(player.getWorld().getName())) {
                World world = Bukkit.getWorld(worldName);
                event.getRecipients().addAll(world.getPlayers());
            }
        };
    }

    private List<String> getSharedWorlds(String world) {
        List<String> sharedWorlds = plugin.getConfig().getStringList("per-world-chat.share." + world, new ArrayList<>());
        sharedWorlds.add(world);
        return sharedWorlds;
    }

}
