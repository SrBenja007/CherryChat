package me.yushust.cherrychat.manager;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModuleManager;
import org.bukkit.command.CommandSender;

import java.util.*;

public class SimpleChatPluginModuleManager implements ChatPluginModuleManager {

    private final List<String> acceptedModules;
    private final CommandSender console;


    private final List<ChatPluginModule> moduleList = new ArrayList<>();
    private final Comparator<ChatPluginModule> chatPluginModuleComparator = (module, otherModule) -> {

        if(module.getPriority().getPriority() > otherModule.getPriority().getPriority()) {
            return 1;
        } else if (module.getPriority().getPriority() < otherModule.getPriority().getPriority()) {
            return -1;
        }
        return 0;

    };

    public SimpleChatPluginModuleManager(List<String> acceptedModules, CommandSender console) {
        this.acceptedModules = acceptedModules;
        this.console = console;
    }

    @Override
    public Set<ChatPluginModule> getRegistrations() {
        return new HashSet<>(moduleList);
    }

    @Override
    public void install(ChatPluginModule module) {
        String pluginName = module.getPlugin().getName();
        String moduleName = module.getModuleName();

        if(acceptedModules.contains(
                pluginName.toLowerCase() + ":" + moduleName.toLowerCase()
        )) {
            moduleList.add(module);
            moduleList.sort(chatPluginModuleComparator);
            console.sendMessage("§e>>> §aRegistered module " + moduleName + " of plugin " + pluginName);
        }
    }

    @Override
    public void handleChat(AsyncCherryChatEvent event) {
        for(ChatPluginModule module : moduleList) {
            module.onChat(event);
        }
    }

    @Override
    public void install(ChatPluginModuleManager modules) {
        for(ChatPluginModule module : modules.getRegistrations()) {
            this.install(module);
        }
    }

}
