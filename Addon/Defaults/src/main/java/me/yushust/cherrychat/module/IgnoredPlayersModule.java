package me.yushust.cherrychat.module;

import me.yushust.cherrychat.CherryChatStoraging;
import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.api.bukkit.module.ModulePriority;
import me.yushust.cherrychat.model.User;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class IgnoredPlayersModule implements ChatPluginModule {

    private CherryChatStoraging plugin;

    public IgnoredPlayersModule(CherryChatStoraging plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onChat(AsyncCherryChatEvent event) {
        if(event.isCancelled()) return;

        Player player = event.getPlayer();
        Set<Player> recipients = new HashSet<>(event.getRecipients());
        for(Player recipient : recipients) {
            User recipientUser = plugin.getUserDataHandler().findSync(recipient.getUniqueId());
            if(recipientUser.getIgnoredPlayers().contains(player.getUniqueId().toString())) {
                event.getRecipients().remove(recipient);
            }
        }
    }

    @Override
    public ModulePriority getPriority() {
        return ModulePriority.HIGH;
    }
}
