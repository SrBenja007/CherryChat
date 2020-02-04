package me.yushust.cherrychat.storage.provider;

import me.yushust.cherrychat.model.User;
import me.yushust.cherrychat.storage.Provider;
import org.bukkit.Bukkit;

import java.util.UUID;

public class UserProvider implements Provider<User> {

    @Override
    public User provide(UUID id) {
        return new User(
                id,
                Bukkit.getPlayer(id).getName()
        );
    }

}
