package me.yushust.cherrychat.command;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public class ChatCommand {

    private String name;
    private int slicedArguments;

    public String setMessage(String format, String message) {
        String[] args = Arrays.copyOfRange(format.split(" "), 0, slicedArguments);
        return Joiner.on(" ").join(args) + " " + message;
    }

    public String getMessage(String format) {
        String[] args = format.split(" ");
        return Joiner.on(" ").join(Arrays.copyOfRange(args, slicedArguments, args.length));
    }

    public static ChatCommand build(ConfigurationSection section) {
        String name = section.getName();
        int slicedArguments = section.getInt("args", 1);
        return new ChatCommand(name, slicedArguments);
    }

}
