package me.yushust.cherrychat.manager;

import lombok.Getter;
import me.yushust.cherrychat.util.BukkitCommandWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.logging.Level;

@Getter
public class SimpleCommandManager implements CommandManager {

    private Plugin plugin;
    private CommandMap commandMap;

    public SimpleCommandManager(Plugin plugin) {
        this.plugin = plugin;
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            this.commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (Exception exception) {
            Bukkit.getLogger().log(
                    Level.SEVERE,
                    "Error getting Bukkit command map",
                    exception
            );
        }
    }

    @Override
    public void registerCommands(BiConsumer<CommandSender, String[]> command, String... names) {
        commandMap.register(names[0], plugin.getName(), new BukkitCommandWrapper(command, names));
        Bukkit.getConsoleSender().sendMessage("§e>>> §aRegistered command \"" + names[0] + "\"");
    }
}
