package me.yushust.cherrychat.punishment;

import org.bukkit.Bukkit;

public class WarningGoal {

    private int warnings;
    private String command;

    public WarningGoal(int warnings, String command) {
        this.warnings = warnings;
        this.command = command;
    }

    public int getWarnings() {
        return warnings;
    }

    public String getCommand() {
        return command;
    }

    public void execute() {
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                command
        );
    }

}
