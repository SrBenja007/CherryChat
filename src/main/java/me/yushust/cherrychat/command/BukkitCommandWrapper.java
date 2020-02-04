package me.yushust.cherrychat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class BukkitCommandWrapper extends Command {

    private ChatPluginCommand command;

    public BukkitCommandWrapper(ChatPluginCommand command, String[] aliases) {
        super(aliases[0], "","", Arrays.asList(aliases).subList(0, aliases.length));
        this.command = command;
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        this.command.execute(sender, args);
        return true;
    }
}
