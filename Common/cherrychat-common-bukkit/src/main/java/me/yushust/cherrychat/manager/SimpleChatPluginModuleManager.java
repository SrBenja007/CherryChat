package me.yushust.cherrychat.manager;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModuleManager;
import org.bukkit.command.CommandSender;

import java.util.*;

public class SimpleChatPluginModuleManager implements ChatPluginModuleManager {

    private final List<String> acceptedModules;
    private final CommandSender console;
    private boolean integratedModules;

    // negative -> first argument < second argument
    // zero -> first argument == second argument
    // positive -> first argument > second argument
    private final Queue<ChatPluginModule> modules = new PriorityQueue<>(15, (module, otherModule) -> {

        if(module.getPriority().getPriority() > otherModule.getPriority().getPriority()) {
            return 1;
        } else if (module.getPriority().getPriority() < otherModule.getPriority().getPriority()) {
            return -1;
        }

        return 0;
    });

    public SimpleChatPluginModuleManager(List<String> acceptedModules, CommandSender console, boolean integratedModules) {
        this.acceptedModules = acceptedModules;
        this.console = console;
        this.integratedModules = integratedModules;
    }

    @Override
    public Set<ChatPluginModule> getRegistrations() {
        return new HashSet<>(modules);
    }

    @Override
    public void install(ChatPluginModule module) {
        String pluginName = module.getPlugin().getName();
        String moduleName = module.getModuleName();

        if(integratedModules) {
            console.sendMessage("§e>>> §cDenegated access for module " + moduleName + ". Reason: 'integrated modules' is enabled");
            return;
        }

        if(acceptedModules.contains(
                pluginName.toLowerCase() + ":" + moduleName.toLowerCase()
        )) {
            console.sendMessage("§e>>> §aRegistered module " + moduleName + " of plugin " + pluginName);
            modules.add(module);
        } else {
            console.sendMessage("§e>>> §cDenegated access for module " + moduleName + ". Reason: Accepted modules doesn't contains this module.");
        }
    }

    @Override
    public void handleChat(AsyncCherryChatEvent event) {
        for(ChatPluginModule module : modules) {
            if(acceptedModules.contains(
                    module.getPlugin().getName().toLowerCase() + ":" + module.getModuleName()
            )) {
                module.onChat(event);
            }
        }
    }

    @Override
    public void install(ChatPluginModuleManager modules) {
        for(ChatPluginModule module : modules.getRegistrations()) {
            this.install(module);
        }
    }

}
