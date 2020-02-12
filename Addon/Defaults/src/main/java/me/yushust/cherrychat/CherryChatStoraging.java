package me.yushust.cherrychat;

import me.yushust.cherrychat.api.bukkit.ChatPlugin;
import me.yushust.cherrychat.api.bukkit.handler.CommandManager;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModuleManager;
import me.yushust.cherrychat.api.bukkit.storage.DataHandler;
import me.yushust.cherrychat.api.bukkit.storage.StorageMethod;
import me.yushust.cherrychat.api.bukkit.util.Configuration;
import me.yushust.cherrychat.command.IgnoreCommand;
import me.yushust.cherrychat.command.MessagingCommands;
import me.yushust.cherrychat.dependency.Dependency;
import me.yushust.cherrychat.listener.RefreshingHandler;
import me.yushust.cherrychat.model.User;
import me.yushust.cherrychat.module.BlacklistModule;
import me.yushust.cherrychat.module.IgnoredPlayersModule;
import me.yushust.cherrychat.punishment.WarningManager;
import me.yushust.cherrychat.storage.MongoCachedDataHandler;
import me.yushust.cherrychat.storage.MongoConnectionManager;
import me.yushust.cherrychat.storage.YamlCachedDataHandler;
import me.yushust.cherrychat.storage.provider.UserProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CherryChatStoraging extends JavaPlugin {

    private DataHandler<User> userDataHandler;
    private File dependencyFolder = new File(getDataFolder(), "lib");
    private StorageMethod storageMethod;
    private ChatPlugin chatPlugin;
    private Configuration config;
    private CommandSender console;
    private WarningManager warningManager;

    private List<Dependency> dependencies = Arrays.asList(
            new Dependency(
                    dependencyFolder,
                    "org.mongodb",
                    "mongo-java-driver",
                    "3.12.0"
            ),
            new Dependency(
                    dependencyFolder,
                    "org.mongodb",
                    "morphia",
                    "1.2"
            )
    );

    @Override
    public void onEnable() {
        dependencies.forEach(Dependency::download);
        this.console = Bukkit.getConsoleSender();
        this.chatPlugin = Bukkit.getServicesManager().getRegistration(ChatPlugin.class).getProvider();

        if(chatPlugin == null) {
            console.sendMessage("§e>>> §cError: CherryChat isn't present!");
            this.setEnabled(false);
        }

        this.config = chatPlugin.getConfig();
        this.storageMethod = chatPlugin.getStorageMethod();

        this.warningManager = new WarningManager(this);

        this.setupStoragingMethods();

        getServer().getPluginManager().registerEvents(new RefreshingHandler(this), this);

        this.installModules();
        this.registerCommands();
    }

    public WarningManager getWarningManager() {
        return warningManager;
    }

    public ChatPlugin getChatPlugin() {
        return chatPlugin;
    }

    public Configuration getConfig() {
        return this.config;
    }

    private void registerCommands() {
        MessagingCommands messagingCommands = new MessagingCommands(this);
        CommandManager commandManager = chatPlugin.getCommandManager();

        commandManager.registerCommands(messagingCommands.messageCommand, "message", "msg", "tell", "whisper");
        commandManager.registerCommands(messagingCommands.replyCommand, "reply", "r", "re");
        commandManager.registerCommands(new IgnoreCommand(this), "ignore", "ig");
    }

    private void installModules() {
        ChatPluginModuleManager moduleManager = chatPlugin.getModuleManager();
        moduleManager.install(new IgnoredPlayersModule(this));
        moduleManager.install(new BlacklistModule(this));
    }

    private void setupStoragingMethods() {
        if (storageMethod.requiresAuth()) {
            String host = config.getString("storaging.host", "127.0.0.1");
            int port = config.getInt("storaging.port", storageMethod.getDefaultPort());
            String username = config.getString("storaging.username", "");
            String password = config.getString("storaging.password", "");
            String database = config.getString("storaging.database", "");

            if (storageMethod == StorageMethod.MONGODB) {
                MongoConnectionManager connectionManager = new MongoConnectionManager(
                        host,
                        port,
                        username,
                        password,
                        database
                );

                connectionManager.connect();

                this.userDataHandler = new MongoCachedDataHandler<>(User.class, new UserProvider(), connectionManager.getDatastore());
                console.sendMessage("§e>>>");
                console.sendMessage("§e>>> §aSuccessfully connected to the Mongo Database!");
                console.sendMessage("§e>>>");
            }
            return;
        }

        if(storageMethod == StorageMethod.YAML) {

            this.userDataHandler = new YamlCachedDataHandler<>(User.class, new UserProvider(), new Configuration(this, "data"));

            console.sendMessage("§e>>>");
            console.sendMessage("§e>>> §aUsing YAML as storage method");
            console.sendMessage("§e>>>");
        }
    }

    public DataHandler<User> getUserDataHandler() {
        return this.userDataHandler;
    }

}
