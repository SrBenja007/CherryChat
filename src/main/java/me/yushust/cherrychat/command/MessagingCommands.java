package me.yushust.cherrychat.command;

import com.google.common.base.Joiner;
import lombok.Getter;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.model.User;
import me.yushust.cherrychat.util.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class MessagingCommands {

    private ChatPlugin plugin = null;
    private Map<String, String> responseCache = new ConcurrentHashMap<>();

    public MessagingCommands(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    private ChatPluginCommand messageCommand = (sender, args) -> {
        Configuration language = plugin.getLanguage();

        if(args.length < 2) {
            sender.sendMessage(language.getString("usage").replace("%usage%", "/message <player> <message>"));
            return;
        }

        String name = sender instanceof Player ? ((Player) sender).getDisplayName() : "Console";
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        CommandSender target = targetPlayer == null ?
                (args[0].equalsIgnoreCase("console") ? Bukkit.getConsoleSender() : null) :
                targetPlayer;
        String targetName = target instanceof Player ? target.getName() : "console";

        if(target == null) {
            sender.sendMessage(language.getString("offline").replace("%player%", args[0]));
            return;
        }

        if(targetName.equalsIgnoreCase(sender.getName())) {
            sender.sendMessage(language.getString("what"));
            return;
        }

        if(target instanceof Player && sender instanceof Player) {
            Player playerTarget = (Player) target;
            Player playerSender = (Player) sender;

            User targetUser = plugin.getUserDataHandler().findSync(playerTarget.getUniqueId());
            if(targetUser.getIgnoredPlayers().contains(playerSender.getUniqueId().toString())) {
                playerSender.sendMessage(plugin.getLanguage().getString("ignored"));
                return;
            }

            User senderUser = plugin.getUserDataHandler().findSync(playerSender.getUniqueId());
            if(senderUser.getIgnoredPlayers().contains(playerTarget.getUniqueId().toString())) {
                playerSender.sendMessage(plugin.getLanguage().getString("confused"));
                return;
            }
        }

        String rawMessage = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));
        String message =
                sender.hasPermission(plugin.getConfig().getString("chat-format.color-permission")) ?
                        ChatColor.translateAlternateColorCodes('&', rawMessage) :
                        rawMessage;

        String formatIn = String.format(plugin.getConfig().getString("message.in"), name, message);
        String formatOut = String.format(plugin.getConfig().getString("message.out"), targetName, message);

        sender.sendMessage(formatOut);
        target.sendMessage(formatIn);

        String formatOther = String.format(plugin.getConfig().getString("message.other"), sender.getName(), targetName, message);

        Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> player.hasPermission(plugin.getConfig().getString("message.spy-permission")))
                .filter(player -> !player.getName().equals(target.getName()))
                .filter(player -> !player.getName().equals(sender.getName()))
                .forEach(player ->
                    player.sendMessage(formatOther)
                );
        if(!name.equalsIgnoreCase("console") && !targetName.equalsIgnoreCase("console"))
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[PM] " + formatOther);

        responseCache.put(target.getName(), sender.getName());
        if(sender instanceof Player)
            responseCache.put(sender.getName(),
                    target instanceof Player ? target.getName() : "console"
            );
        else
            responseCache.put("console", target.getName());
    };

    private ChatPluginCommand replyCommand = (sender, args) -> {
        if(args.length < 1) {
            sender.sendMessage(plugin.getLanguage().getString("usage").replace("%usage%", "/reply <message>"));
            return;
        }

        String playerName = responseCache.get(sender instanceof Player ? sender.getName() : "console");
        if(playerName == null) {
            sender.sendMessage(plugin.getLanguage().getString("no-reply"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(playerName);
        String targetName = targetPlayer == null ? "console" : targetPlayer.getName();

        Bukkit.dispatchCommand(sender, "message " + targetName + " " + Joiner.on(" ").join(args));
    };

}
