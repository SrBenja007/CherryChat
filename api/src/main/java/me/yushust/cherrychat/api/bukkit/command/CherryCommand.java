package me.yushust.cherrychat.api.bukkit.command;

import org.bukkit.command.CommandSender;

import java.util.function.BiConsumer;

public interface CherryCommand extends BiConsumer<CommandSender, String[]> {

    void execute(CommandSender sender, String[] args);

    @Override
    default void accept(CommandSender commandSender, String[] strings) {
        execute(commandSender, strings);
    }
}
