package me.yushust.cherrychat.module;

import com.google.common.base.Strings;
import me.yushust.cherrychat.CherryChatStoraging;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.api.bukkit.module.ModulePriority;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class BlacklistModule implements ChatPluginModule {

    private final CherryChatStoraging plugin;

    public BlacklistModule(CherryChatStoraging plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        List<String> badWords = plugin.getConfig().getStringList("blacklisted-words.list");
        String message = event.getMessage();
        Player player = event.getPlayer();
        String mode = plugin.getConfig().getString("blacklisted-words.suppressing-mode");

        switch(mode.toLowerCase()) {
            case "censor": {
                for(String badWord : badWords) {
                    if(message.toLowerCase().contains(badWord)) {
                        message = message.toLowerCase().replaceAll(badWord, Strings.repeat("*", badWord.length()));
                    }
                }
                break;
            }
            case "cancel": {
                String cancelMessage = plugin.getConfig().getString("blacklisted-words.configuration.cancel-message");
                player.sendMessage(cancelMessage);
                event.setCancelled(true);
                break;
            }
            case "warning": {
                plugin.getWarningManager().warn(player);
                break;
            }
            case "command": {
                String command = plugin.getConfig().getString("blacklisted-words.configuration.command");
                command = plugin.getChatPlugin().getDefaultFormatter().setPlaceholders(player, command);
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        command
                );
                event.setCancelled(true);
                break;
            }
            default: onChat(event);
        }

        event.setMessage(message);
    }

    @Override
    public ModulePriority getPriority() {
        return ModulePriority.HIGHEST;
    }
}
