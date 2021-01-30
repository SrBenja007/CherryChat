package me.yushust.cherrychat.modules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import me.yushust.cherrychat.api.bukkit.intercept.MessageInterceptor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PerWorldMessageModule implements MessageInterceptor {

    private Map<String, List<String>> sharedWorlds = new HashMap<>();
    private CherryChatPlugin plugin;
    private boolean enabled;
    private String moduleName = "per-world-chat";

    public PerWorldMessageModule(CherryChatPlugin plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("per-world-chat.enabled", false);
    }

    @Override
    public void onChat(AsyncUserChatEvent event) {
        if(!enabled) return;
        if(event.isCancelled()) return;

        Player player = event.getPlayer();

        event.getRecipients().clear();

        for(String worldName : getSharedWorlds(player.getWorld().getName())) {
            World world = Bukkit.getWorld(worldName);
            event.getRecipients().addAll(world.getPlayers());
        }
    }

    private List<String> getSharedWorlds(String world) {
        if(sharedWorlds.containsKey(world)) return sharedWorlds.get(world);
        List<String> sharings = plugin.getConfig().getStringList("per-world-chat.share." + world, new ArrayList<>());
        sharings.add(world);
        this.sharedWorlds.put(world, sharings);
        return sharings;
    }

}
