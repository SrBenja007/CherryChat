package me.yushust.cherrychat.listener;

import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.modules.AntiBotSpamModule;
import me.yushust.cherrychat.modules.CooldownModule;
import me.yushust.cherrychat.modules.PerWorldChatModule;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class IntegratedAsyncChatListener implements Listener {

    private final CherryChatPlugin plugin;
    private ChatPluginModule antiBotSpamModule;
    private ChatPluginModule coolDownModule;
    private ChatPluginModule perWorldChatModule;

    public IntegratedAsyncChatListener(CherryChatPlugin plugin) {
        this.plugin = plugin;
        this.antiBotSpamModule = new AntiBotSpamModule(plugin);
        this.coolDownModule = new CooldownModule(plugin);
        this.perWorldChatModule = new PerWorldChatModule(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        Player sender = event.getPlayer();

        antiBotSpamModule.onChat(event);
        coolDownModule.onChat(event);
        perWorldChatModule.onChat(event);

        if(event.isCancelled()) return;

        String message = event.getMessage();
        if(sender.hasPermission(plugin.getConfig().getString("chat-format.color-permission")))
            message = ChatColor.translateAlternateColorCodes('&', message);

        if(event.isCalledAsCommand()) {
            event.setMessage(message);
            return;
        }

        String format = plugin.getFormatter().format(sender, message);
        event.setFormat(format);
    }

}
