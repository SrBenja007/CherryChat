package me.yushust.cherrychat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.yushust.cherrychat.storaging.Identifiable;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor @Getter @Setter
public class User implements Identifiable {

    @Id
    private UUID id;
    private String name;
    private List<String> ignoredPlayers = new ArrayList<>();

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {}

}
