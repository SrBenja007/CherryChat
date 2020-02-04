package me.yushust.cherrychat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.yushust.cherrychat.storage.Identifiable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

@Entity
@AllArgsConstructor @Getter @Setter
public class User implements Identifiable, ConfigurationSerializable {

    @Id
    private UUID id;
    private String name;
    private List<String> ignoredPlayers = new ArrayList<>();

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    public User(Map<String, Object> args) {
        this.id = UUID.fromString(args.get("id").toString());
        this.name = args.get("name").toString();
        this.ignoredPlayers = (List<String>) args.get("ignored-players");
    }

    public User() {}

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serial = new LinkedHashMap<>();
        serial.put("id", id.toString());
        serial.put("name", name);
        serial.put("ignored-players", ignoredPlayers);
        return serial;
    }

    public static User deserialize(Map<String, Object> args) {
        return new User(args);
    }

    public static User valueOf(Map<String, Object> args) {
        return new User(args);
    }

}
