package me.yushust.cherrychat.command;

import me.yushust.cherrychat.CherryChatStoraging;
import me.yushust.cherrychat.api.bukkit.ChatPlugin;
import me.yushust.cherrychat.api.bukkit.command.CherryCommand;
import me.yushust.cherrychat.api.bukkit.storage.DataHandler;
import me.yushust.cherrychat.model.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class IgnoreCommand implements CherryCommand {

    private final ChatPlugin plugin;
    private final DataHandler<User> dataHandler;

    public IgnoreCommand(CherryChatStoraging plugin) {
        this.plugin = plugin.getChatPlugin();
        this.dataHandler = plugin.getUserDataHandler();
    }

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
