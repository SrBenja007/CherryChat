package me.yushust.cherrychat;

import lombok.Getter;
import lombok.Setter;
import me.yushust.cherrychat.formatting.CherryChatFormatter;
import me.yushust.cherrychat.formatting.Formatter;
import me.yushust.cherrychat.listener.AsyncChatListener;
import me.yushust.cherrychat.manager.CommandManager;
import me.yushust.cherrychat.manager.SimpleCommandManager;
import me.yushust.cherrychat.modules.ChatModulesContainer;
import me.yushust.cherrychat.modules.module.*;
import me.yushust.cherrychat.task.AnnouncerTask;
import me.yushust.cherrychat.util.Announcement;
import me.yushust.cherrychat.util.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
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

    private CommandManager commandManager;
    private ChatModulesContainer moduleContainer;

    private Formatter formatter;

    @Override
    public void onEnable() {
        this.config = new Configuration(this, "config");
        this.language = new Configuration(this, "language");

        this.commandManager = new SimpleCommandManager(this);
        this.registerCommands();

        this.placeholderApiEnabled = getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        this.vaultApiEnabled = getServer().getPluginManager().getPlugin("Vault") != null;

        this.moduleContainer = new ChatModulesContainer();
        this.formatter = new CherryChatFormatter(this);

        this.startTasks();
        this.setupAnnouncements();

        this.installModules();

        this.playersMoved = new HashSet<>();

        getServer().getPluginManager().registerEvents(new AsyncChatListener(this), this);
    }

    private void setupAnnouncements() {
        Announcement.getAllFrom(this.config.getConfigurationSection("announcements")).forEach(Announcement::schedule);
    }

    private void startTasks() {
        getServer().getScheduler().runTaskTimerAsynchronously(this, new AnnouncerTask(), 0, 20);
    }

    private void installModules() {
        moduleContainer.installModule(new CooldownModule(this));
        moduleContainer.installModule(new BlacklistModule(this));
        moduleContainer.installModule(new AntiBotSpamModule(this));
        moduleContainer.installModule(new FloodFilterModule(this));
    }

    private void registerCommands() {

    }

}
