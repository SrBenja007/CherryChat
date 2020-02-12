package me.yushust.cherrychat.storage.provider;

import me.yushust.cherrychat.model.User;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.function.Function;

public class UserProvider implements Function<UUID, User> {
    @Override
    public User apply(UUID id) {
        return new User(
                id,
                Bukkit.getPlayer(id).getName()
        );
    }
}
