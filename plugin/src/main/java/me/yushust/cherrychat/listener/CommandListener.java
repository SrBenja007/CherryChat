package me.yushust.cherrychat.listener;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.CherryChatPlugin;
import me.yushust.cherrychat.formatting.ChatCommand;
import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;

@RequiredArgsConstructor
public class CommandListener implements Listener {

    private final CherryChatPlugin plugin;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().isEmpty()) return;

        String name = event.getMessage().split(" ")[0].substring(1);
        for(ChatCommand command : plugin.getChatCommands()) {
            if(name.equalsIgnoreCase(command.getName())) {
                String message = command.getMessage(event.getMessage());

                AsyncUserChatEvent chatEvent = new AsyncUserChatEvent(
                        event.isAsynchronous(),
                        event.getPlayer(),
                        message,
                        new HashSet<>(Bukkit.getOnlinePlayers()),
                        true
                );

                Bukkit.getPluginManager().callEvent(chatEvent);

                if(chatEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
                event.setMessage(command.setMessage(event.getMessage(), chatEvent.getMessage()));
                break;
            }
        }
    }

}
