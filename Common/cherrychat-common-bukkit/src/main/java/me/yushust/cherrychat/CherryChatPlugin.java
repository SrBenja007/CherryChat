package me.yushust.cherrychat;

import com.google.common.base.Joiner;
import lombok.Getter;
import lombok.Setter;
import me.yushust.cherrychat.api.bukkit.ChatPlugin;
import me.yushust.cherrychat.api.bukkit.formatting.Formatter;
import me.yushust.cherrychat.api.bukkit.handler.CommandManager;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModuleManager;
import me.yushust.cherrychat.api.bukkit.storage.StorageMethod;
import me.yushust.cherrychat.formatting.ChatCommand;
import me.yushust.cherrychat.formatting.CherryChatFormatter;
import me.yushust.cherrychat.formatting.DefaultFormatter;
import me.yushust.cherrychat.listener.*;
import me.yushust.cherrychat.manager.CommandLoggingFilter;
import me.yushust.cherrychat.manager.SimpleChatPluginModuleManager;
import me.yushust.cherrychat.manager.SimpleCommandManager;
import me.yushust.cherrychat.modules.*;
import me.yushust.cherrychat.task.AnnouncerTask;
import me.yushust.cherrychat.api.bukkit.util.Configuration;
import me.yushust.cherrychat.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Getter
public final class CherryChatPlugin extends JavaPlugin implements ChatPlugin {

    private CommandSender console;

    private Configuration config;
    private Configuration language;
    private Configuration achievements;

    private boolean placeholderApiEnabled;
    private boolean vaultApiEnabled;

    @Setter
    private Set<UUID> playersMoved;
    private Set<ChatCommand> chatCommands;

    private CommandManager commandManager;
    private ChatPluginModuleManager moduleManager;

    private Formatter formatter;
    private Formatter defaultFormatter;

    private StorageMethod storageMethod;

    private static String serverVersion;

    @Override
    public void onEnable() {
        String packageName = getServer().getClass().getPackage().getName();
        serverVersion = packageName.substring(packageName.lastIndexOf('.') + 1);

        Filter loggingFilter = new CommandLoggingFilter();

        org.apache.logging.log4j.core.Logger mainLogger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        mainLogger.addFilter(loggingFilter);

        this.console = Bukkit.getConsoleSender();
        console.sendMessage("§e>>>");
        console.sendMessage("§e>>> §aEnabled §bCherryChat§a!");
        console.sendMessage("§e>>> §a   Version §7>> " + getDescription().getVersion());
        console.sendMessage("§e>>> §a   Author  §7>> " + Joiner.on(", ").join(getDescription().getAuthors()));
        console.sendMessage("§e>>> §a     §b> CherryChat for Server " + serverVersion + " <");
        console.sendMessage("§e>>>");

        this.config = new Configuration(this, "config");
        this.language = new Configuration(this, "language");
        this.achievements = new Configuration(this, "achievements");

        this.storageMethod = StorageMethod.getByName(config.getString("storaging.type", "yaml"));

        if(config.getBoolean("custom-achievements-messages.enabled")){
            console.sendMessage("§e>>>");
            console.sendMessage("§e>>> §aSetting announce-player-achievements from server.properties to false");
            console.sendMessage("§e>>> §7>  §6Please don't change it if you have custom-achievements-messages enabled");
            console.sendMessage("§e>>>");
            try {
                Properties.setAndSaveProperties(Properties.ServerProperty.ANNOUNCE_PLAYER_ACHIEVEMENTS, false);
                console.sendMessage("§e>>> §aSuccess!");
                console.sendMessage("§e>>>");
            } catch (Exception exception) {
                console.sendMessage("§e>>> §cCouldn't set announce-player-achievements to false :(");
                console.sendMessage("§e>>>");
            }
        }

        this.commandManager = new SimpleCommandManager(this);
        this.placeholderApiEnabled = getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        this.vaultApiEnabled = getServer().getPluginManager().getPlugin("Vault") != null;

        this.moduleManager = new SimpleChatPluginModuleManager(
                config.getStringList("modules.accepted-modules"),
                console
        );

        this.formatter = new CherryChatFormatter(this);
        this.defaultFormatter = new DefaultFormatter(this);

        AnnouncerTask.startScheduling(this, 20, config.getConfigurationSection("announcements"));

        this.installModules();
        this.registerChatCommands();

        this.playersMoved = new HashSet<>();

        getServer().getPluginManager().registerEvents(new AchievementListener(this), this);
        getServer().getPluginManager().registerEvents(new MoveListener(this), this);
        getServer().getPluginManager().registerEvents(new AsyncCherryChatCaller(), this);
        getServer().getPluginManager().registerEvents(new AsyncChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        Bukkit.getServicesManager().register(ChatPlugin.class, this, this, ServicePriority.Highest);
    }

    private void registerChatCommands() {
        if(chatCommands == null) chatCommands = new HashSet<>();

        for(String path : config.getConfigurationSection("chat-commands").getKeys(false)) {
            ConfigurationSection chatCommandSection = config.getConfigurationSection("chat-commands." + path);
            ChatCommand chatCommand = ChatCommand.build(chatCommandSection);
            chatCommands.add(chatCommand);
        }
    }

    private void installModules() {
        moduleManager.install(new AntiBotSpamModule(this));
        moduleManager.install(new CooldownModule(this));
        moduleManager.install(new CapsFilterModule(this));
        moduleManager.install(new FloodFilterModule(this));
        moduleManager.install(new MentionsModule(this));
        moduleManager.install(new DotModule(this));
        moduleManager.install(new PerWorldChatModule(this));
    }

    @Override
    public void onDisable() {
        console.sendMessage("§e>>>");
        console.sendMessage("§e>>> §aDisabled §bCherryChat§a!");
        console.sendMessage("§e>>> §a   Version §7>> " + getDescription().getVersion());
        console.sendMessage("§e>>> §a   Author  §7>> " + Joiner.on(", ").join(getDescription().getAuthors()));
        console.sendMessage("§e>>> §a     §b> CherryChat for Server " + serverVersion + " <");
        console.sendMessage("§e>>> §a~ Good bye! ~");
        console.sendMessage("§e>>>");
    }

    public static String getServerVersion() { return serverVersion; }

}
