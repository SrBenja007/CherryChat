package me.yushust.cherrychat.punishment;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import me.yushust.cherrychat.CherryChatStoraging;
import me.yushust.cherrychat.api.bukkit.util.Configuration;
import me.yushust.cherrychat.model.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class WarningManager {

    private List<WarningGoal> goals = new ArrayList<>();
    private CherryChatStoraging plugin;

    public WarningManager(CherryChatStoraging plugin) {
        this.plugin = plugin;

        Configuration config = plugin.getConfig();

        for(String path : config.getConfigurationSection("warnings.goals").getKeys(false)) {

            int warnings = config.getInt("warnings.goals." + path + ".warnings");
            String command = config.getString("warnings.goals." + path + ".command");

            goals.add(new WarningGoal(warnings, command));
        }
    }

    public void warn(Player player) {
        ListenableFuture<User> futureUser = plugin.getUserDataHandler().find(player.getUniqueId());

        Futures.addCallback(futureUser, new FutureCallback<User>() {
            @Override
            public void onSuccess(@Nullable User user) {
                if(user == null) {
                    onFailure(null);
                    return;
                }

                String warnMessage = plugin.getConfig().getString("blacklisted-words.configuration.warning");
                warnMessage = plugin.getChatPlugin().getDefaultFormatter().setPlaceholders(player, warnMessage);

                int warns = user.getWarnings();
                WarningGoal nextGoal = calculateNext(warns);

                if(nextGoal == null) return;

                int maxWarns = nextGoal.getWarnings();

                warnMessage = warnMessage
                        .replace("%warns%", String.valueOf(warns))
                        .replace("%max_warns%", String.valueOf(maxWarns));

                if(warns == maxWarns) {
                    nextGoal.execute();
                }

                player.sendMessage(warnMessage);
            }

            @Override
            public void onFailure(@Nullable Throwable throwable) {
                Bukkit.getLogger().warning("Failed to get user data of player " + player.getName());
            }
        });

    }

    private WarningGoal calculateNext(int val) {
        if(goals.isEmpty()) return null;

        WarningGoal nextGoal = goals.get(0);
        for(WarningGoal goal : goals) {
            if(val >= goal.getWarnings()) break;
            nextGoal = goal;
        }
        return nextGoal;
    }

}
