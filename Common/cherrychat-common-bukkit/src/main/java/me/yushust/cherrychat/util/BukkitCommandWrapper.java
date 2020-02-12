package me.yushust.cherrychat.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class BukkitCommandWrapper extends Command {

    private BiConsumer<CommandSender, String[]> command;

    public BukkitCommandWrapper(BiConsumer<CommandSender, String[]> command, String[] aliases) {
        super(aliases[0], "","", Arrays.asList(aliases).subList(0, aliases.length));
        this.command = command;
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        this.command.accept(sender, args);
        return true;
    }
}
