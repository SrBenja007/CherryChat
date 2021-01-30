package me.yushust.cherrychat.api.bukkit;

import me.yushust.cherrychat.api.bukkit.format.PlaceholderReplacer;
import me.yushust.cherrychat.api.bukkit.util.Configuration;

public interface ChatPlugin {

    ChatPluginModuleManager getModuleManager();

    StorageMethod getStorageMethod();

    default void addModuleManager(ChatPluginModuleManager manager) {
        getModuleManager().install(manager);
    }

    boolean isVaultApiEnabled();

    boolean isPlaceholderApiEnabled();

    CommandManager getCommandManager();

    Configuration getConfig();

    Configuration getLanguage();

    PlaceholderReplacer getFormatter();

    PlaceholderReplacer getDefaultFormatter();

}
