package me.yushust.cherrychat.api.bukkit;

import me.yushust.cherrychat.api.bukkit.formatting.Formatter;
import me.yushust.cherrychat.api.bukkit.handler.CommandManager;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModuleManager;
import me.yushust.cherrychat.api.bukkit.storage.StorageMethod;
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

    Formatter getFormatter();

    Formatter getDefaultFormatter();

}
