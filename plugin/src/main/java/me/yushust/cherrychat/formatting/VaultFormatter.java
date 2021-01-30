package me.yushust.cherrychat.formatting;

import me.yushust.cherrychat.CherryChatPlugin;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultFormatter extends DefaultFormatter {

    private Chat chatManager;

    public VaultFormatter(CherryChatPlugin plugin) {
        super(plugin);

        RegisteredServiceProvider<Chat> chatService = Bukkit.getServicesManager().getRegistration(Chat.class);
        if(chatService == null) {
            throw new RuntimeException(
                "[CherryChat] No chat format provider is placed on the server, try installing: LuckPerms, PermissionsEx, DroxPerms"
            );
        } else {
            Chat chat = chatService.getProvider();
            if(chat == null) {
                throw new RuntimeException(
                    "[CherryChat] No chat format provider is placed on the server, try installing: LuckPerms, PermissionsEx, DroxPerms"
                );
            }
            this.chatManager = chat;
        }
    }

    @Override
    public String setPlaceholders(Player player, String text) {
        return text
                .replace("%prefix%", colorize(chatManager.getPlayerPrefix(player)))
                .replace("%suffix%", colorize(chatManager.getPlayerSuffix(player)))
                .replace("%group%", chatManager.getPlayerGroups(player)[0])
                .replace("%name%", player.getName())
                .replace("%displayname%", colorize(player.getDisplayName()));
    }

}
