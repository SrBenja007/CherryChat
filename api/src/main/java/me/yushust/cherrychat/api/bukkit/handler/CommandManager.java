package me.yushust.cherrychat.api.bukkit.handler;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.function.BiConsumer;

public interface CommandManager {

    Plugin getPlugin();

    void registerCommands(BiConsumer<CommandSender, String[]> command, String... names);

    CommandMap getCommandMap();

}
