package me.yushust.cherrychat.command;

import org.bukkit.command.CommandSender;

public interface ChatPluginCommand {

    void execute(CommandSender sender, String[] args);

}
