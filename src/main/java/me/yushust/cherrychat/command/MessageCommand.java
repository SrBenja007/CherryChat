package me.yushust.cherrychat.command;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.util.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MessageCommand implements ChatPluginCommand {

    private final ChatPlugin plugin;

    @Override
    public void execute(CommandSender sender, String[] args) {

        Configuration language = plugin.getLanguage();

        if(args.length < 2) {
            sender.sendMessage(language.getString("usage").replace("%usage%", "/message <player> <message>"));
            return;
        }

        String name = sender instanceof Player ? ((Player) sender).getDisplayName() : "Console";
        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            sender.sendMessage(language.getString("offline").replace("%player%", args[0]));
            return;
        }

        if(target.getName().equalsIgnoreCase(sender.getName())) {
            sender.sendMessage(language.getString("what"));
            return;
        }

        

    }
}

