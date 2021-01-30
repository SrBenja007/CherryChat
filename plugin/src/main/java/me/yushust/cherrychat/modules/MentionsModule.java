package me.yushust.cherrychat.modules;

import lombok.Getter;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import me.yushust.cherrychat.api.bukkit.intercept.MessageInterceptor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class MentionsModule implements MessageInterceptor {

    private CherryChatPlugin plugin;
    private String moduleName = "mentions";
    private String mentionFormat;

    public MentionsModule(CherryChatPlugin plugin) {
        this.plugin = plugin;
        this.mentionFormat = plugin.getConfig().getString("chat-format.mention");
    }

    @Override
    public void onChat(AsyncUserChatEvent event) {
        if(event.isCancelled()) return;

        String message = event.getMessage();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getUniqueId().equals(event.getPlayer().getUniqueId())) continue;
            String mentionFormat = this.mentionFormat.replace("%name%", player.getName());
            message = message.replace(player.getName(), mentionFormat);
        }

        event.setMessage(message);
    }
}
