package me.yushust.cherrychat.manager;

import me.yushust.cherrychat.api.bukkit.event.AsyncUserChatEvent;
import me.yushust.cherrychat.api.bukkit.intercept.MessageInterceptor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class SimpleChatPluginModuleManager implements ChatPluginModuleManager {

    private final List<String> acceptedModules;
    private final CommandSender console;


    private final List<MessageInterceptor> moduleList = new ArrayList<>();
    private final Comparator<MessageInterceptor> chatPluginModuleComparator = (module, otherModule) -> {

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
    public Set<MessageInterceptor> getRegistrations() {
        return new HashSet<>(moduleList);
    }

    @Override
    public void install(MessageInterceptor module) {
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
    public void handleChat(AsyncUserChatEvent event) {
        for(MessageInterceptor module : moduleList) {
            module.onChat(event);
        }
    }

    @Override
    public void install(ChatPluginModuleManager modules) {
        for(MessageInterceptor module : modules.getRegistrations()) {
            this.install(module);
        }
    }

}
