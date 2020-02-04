package me.yushust.cherrychat.command;

import lombok.RequiredArgsConstructor;
import me.yushust.cherrychat.ChatPlugin;
import me.yushust.cherrychat.model.User;
import me.yushust.cherrychat.storaging.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class IgnoreCommand implements ChatPluginCommand {

    private final ChatPlugin plugin;

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou need to be a player to execute this command.");
            return;
        }

        if(args.length != 1) {
            sender.sendMessage(plugin.getLanguage().getString("usage").replace("%usage%", "/ignore <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(plugin.getLanguage().getString("offline").replace("%player%", args[0]));
            return;
        }

        Player player = (Player) sender;

        DataHandler<User> dataHandler = plugin.getUserDataHandler();

        User user = dataHandler.findSync(player.getUniqueId());
        List<String> ignoredPlayers = user.getIgnoredPlayers();

        if(ignoredPlayers.contains(target.getUniqueId().toString())) {
            player.sendMessage(plugin.getLanguage().getString("ignoringnt").replace("%player%", target.getDisplayName()));
            ignoredPlayers.remove(target.getUniqueId().toString());
            user.setIgnoredPlayers(ignoredPlayers);
            dataHandler.save(user);
            return;
        }

        ignoredPlayers.add(target.getUniqueId().toString());
        user.setIgnoredPlayers(ignoredPlayers);

        dataHandler.save(user);

        player.sendMessage(plugin.getLanguage().getString("ignoring").replace("%player%", target.getDisplayName()));
    }

}
