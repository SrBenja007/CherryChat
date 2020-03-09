package me.yushust.cherrychat.modules;

import lombok.Getter;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import org.bukkit.entity.Player;

 @Getter
public class AntiBotSpamModule implements ChatPluginModule {

    private CherryChatPlugin plugin;
    private String moduleName = "anti-bot-spam";
    private String pleaseMoveMessage;

    public AntiBotSpamModule(CherryChatPlugin plugin) {
        this.plugin = plugin;
        this.pleaseMoveMessage = plugin.getLanguage().getString("please-move");
    }

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        Player player = event.getPlayer();

        if(!plugin.getPlayersMoved().contains(player.getUniqueId())) {
            player.sendMessage(pleaseMoveMessage);
            event.setCancelled(true);
        }
    }

}
