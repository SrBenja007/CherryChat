package me.yushust.cherrychat;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.yushust.cherrychat.command.ChatCommand;
import me.yushust.cherrychat.command.IgnoreCommand;
import me.yushust.cherrychat.command.MessagingCommands;
import me.yushust.cherrychat.formatting.CherryChatFormatter;
import me.yushust.cherrychat.formatting.DefaultFormatter;
import me.yushust.cherrychat.formatting.Formatter;
import me.yushust.cherrychat.listener.*;
import me.yushust.cherrychat.manager.CommandLoggingFilter;
import me.yushust.cherrychat.manager.CommandManager;
import me.yushust.cherrychat.manager.SimpleCommandManager;
import me.yushust.cherrychat.model.User;
import me.yushust.cherrychat.modules.ChatModulesContainer;
import me.yushust.cherrychat.modules.module.*;
import me.yushust.cherrychat.storaging.DataHandler;
import me.yushust.cherrychat.storaging.MongoConnectionManager;
import me.yushust.cherrychat.storaging.MongoDataHandler;
import me.yushust.cherrychat.storaging.provider.UserProvider;
import me.yushust.cherrychat.task.AnnouncerTask;
import me.yushust.cherrychat.util.Announcement;
import me.yushust.cherrychat.util.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
public final class ChatPlugin extends JavaPlugin {

    private Configuration config;
    private Configuration language;

    private boolean placeholderApiEnabled;
    private boolean vaultApiEnabled;

    @Setter
    private Set<UUID> playersMoved;
    private Set<ChatCommand> chatCommands;

    private CommandManager commandManager;
    private ChatModulesContainer moduleContainer;

    private Formatter formatter;
    private Formatter defaultFormatter;

    private DataHandler<User> userDataHandler;

    @Override
    public void onEnable() {

        Filter loggingFilter = new CommandLoggingFilter();

        org.apache.logging.log4j.core.Logger mainLogger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        mainLogger.addFilter(loggingFilter);

        CommandSender console = Bukkit.getConsoleSender();
        console.sendMessage("§e>>>");
        console.sendMessage("§e>>> §aEnabled §bCherryChat§a!");
        console.sendMessage("§e>>> §a   Version §7>> " + getDescription().getVersion());
        console.sendMessage("§e>>> §a   Author  §7>> " + Joiner.on(", ").join(getDescription().getAuthors()));
        console.sendMessage("§e>>> §a     §b> CherryChat " + Bukkit.getBukkitVersion() + " <");
        console.sendMessage("§e>>>");

        this.config = new Configuration(this, "config");
        this.language = new Configuration(this, "language");

        this.setupStoragingMethods();

        this.commandManager = new SimpleCommandManager(this);
        this.registerCommands();

        this.placeholderApiEnabled = getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        this.vaultApiEnabled = getServer().getPluginManager().getPlugin("Vault") != null;

        this.moduleContainer = new ChatModulesContainer();
        this.formatter = new CherryChatFormatter(this);
        this.defaultFormatter = new DefaultFormatter(this);

        this.startTasks();
        this.setupAnnouncements();

        this.installModules();

        this.registerChatCommands();
        this.playersMoved = new HashSet<>();

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new MoveListener(this), this);
        getServer().getPluginManager().registerEvents(new AsyncChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
    }

    private void setupStoragingMethods() {
        StorageType storageType = StorageType.valueOf(config.getString("storaging.type", "YAML").toUpperCase());

        if(storageType.isRequiresAuth()) {
            String host = config.getString("storaging.host", "127.0.0.1");
            int port = config.getInt("storaging.port", storageType.getDefaultPort());
            String username = config.getString("storaging.username", "");
            String password = config.getString("storaging.password", "");
            String database = config.getString("storaging.database", "");

            if(storageType == StorageType.MONGODB) {
                MongoConnectionManager connectionManager = new MongoConnectionManager(
                        host,
                        port,
                        username,
                        password,
                        database
                );

                connectionManager.connect();

                this.userDataHandler = new MongoDataHandler<>(User.class, new UserProvider(), connectionManager.getDatastore());
                Bukkit.getConsoleSender().sendMessage("§e>>>");
                Bukkit.getConsoleSender().sendMessage("§e>>> §aSuccessfully connected to the Mongo Database!");
                Bukkit.getConsoleSender().sendMessage("§e>>>");
            }
            // return;
        }

        // if(storageType == StorageType.YAML) {}

    }

    private void registerChatCommands() {
        if(chatCommands == null) chatCommands = new HashSet<>();
        Map<String, String[]> aliases = Bukkit.getCommandAliases();
        for(String path : config.getConfigurationSection("chat-commands").getKeys(false)) {
            ConfigurationSection chatCommandSection = config.getConfigurationSection("chat-commands." + path);
            ChatCommand chatCommand = ChatCommand.build(chatCommandSection);
            chatCommands.add(chatCommand);
            for(String alias : aliases.getOrDefault(path, new String[0])) {
                ChatCommand aliasedChatCommand = new ChatCommand(alias, chatCommand.getSlicedArguments());
                chatCommands.add(aliasedChatCommand);
            }
        }
    }

    private void setupAnnouncements() {
        Announcement.getAllFrom(this.config.getConfigurationSection("announcements")).forEach(Announcement::schedule);
    }

    private void startTasks() {
        getServer().getScheduler().runTaskTimerAsynchronously(this, new AnnouncerTask(), 0, 20);
    }

    private void installModules() {
        moduleContainer.installModule(new AntiBotSpamModule(this));
        moduleContainer.installModule(new CooldownModule(this));
        moduleContainer.installModule(new BlacklistModule(this));
        moduleContainer.installModule(new CapsFilterModule(this));
        moduleContainer.installModule(new FloodFilterModule(this));
        moduleContainer.installModule(new MentionsModule(this));
        moduleContainer.installModule(new DotModule());
        moduleContainer.installModule(new PerWorldChatModule(this));
        moduleContainer.installModule(new IgnoredPlayersModule(this));
    }

    private void registerCommands() {
        MessagingCommands messagingCommands = new MessagingCommands(this);

        this.commandManager.registerCommand(messagingCommands.getMessageCommand(), "message", "msg", "tell", "whisper");
        this.commandManager.registerCommand(messagingCommands.getReplyCommand(), "reply", "r", "re");
        this.commandManager.registerCommand(new IgnoreCommand(this), "ignore", "ig");
    }

    @Override
    public void onDisable() {
        CommandSender console = Bukkit.getConsoleSender();
        console.sendMessage("§e>>>");
        console.sendMessage("§e>>> §aDisabled §bCherryChat§a!");
        console.sendMessage("§e>>> §a   Version §7>> " + getDescription().getVersion());
        console.sendMessage("§e>>> §a   Author  §7>> " + Joiner.on(", ").join(getDescription().getAuthors()));
        console.sendMessage("§e>>> §a     §b> CherryChat " + Bukkit.getBukkitVersion() + " <");
        console.sendMessage("§e>>> §a~ Good bye! ~");
        console.sendMessage("§e>>>");
    }

    @AllArgsConstructor @Getter
    public enum StorageType {
        MONGODB(true, 27017),
        YAML(false, -1);

        private boolean requiresAuth;
        private int defaultPort;
    }

}
