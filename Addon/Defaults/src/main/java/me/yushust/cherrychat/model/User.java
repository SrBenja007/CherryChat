package me.yushust.cherrychat.model;

import me.yushust.cherrychat.api.bukkit.storage.Identifiable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

@Entity
public class User implements Identifiable, ConfigurationSerializable {

    @Id
    private UUID id;
    private String name;
    private List<String> ignoredPlayers = new ArrayList<>();
    private int warnings;
    private boolean muted;

    public User(UUID id, String name, List<String> ignoredPlayers, int warnings, boolean muted) {
        this.id = id;
        this.name = name;
        this.ignoredPlayers = ignoredPlayers;
        this.warnings = warnings;
        this.muted = muted;
    }

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

    public UUID getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public int getWarnings() { return this.warnings; }

    public void setWarnings(int warnings) { this.warnings = warnings; }

    public void setMuted(boolean muted) { this.muted = muted; }

    public boolean isMuted() { return this.muted; }

    public List<String> getIgnoredPlayers() {
        return this.ignoredPlayers;
    }

    public void setIgnoredPlayers(List<String> ignoredPlayers) {
        this.ignoredPlayers = ignoredPlayers;
    }

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
