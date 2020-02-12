package me.yushust.cherrychat.manager;

import me.yushust.cherrychat.api.bukkit.event.AsyncCherryChatEvent;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModule;
import me.yushust.cherrychat.api.bukkit.module.ChatPluginModuleManager;

import java.util.*;

public class SimpleChatPluginModuleManager implements ChatPluginModuleManager {

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

    @Override
    public Set<ChatPluginModule> getRegistrations() {
        return new HashSet<>(modules);
    }

    @Override
    public void install(ChatPluginModule module) {
        modules.add(module);
    }

    @Override
    public void handleChat(AsyncCherryChatEvent event) {
        for(ChatPluginModule module : modules) {
            module.onChat(event);
        }
    }

    @Override
    public void install(ChatPluginModuleManager modules) {
        this.modules.addAll(modules.getRegistrations());
    }

}
