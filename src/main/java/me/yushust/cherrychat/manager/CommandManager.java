package me.yushust.cherrychat.manager;

import me.yushust.cherrychat.command.ChatPluginCommand;
import org.bukkit.plugin.Plugin;

public interface CommandManager {

    Plugin getPlugin();

    void registerCommand(ChatPluginCommand command, String... names);

}
