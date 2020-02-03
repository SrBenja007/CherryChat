package me.yushust.cherrychat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor @Getter @Setter
public class User {

    @Id
    private UUID id;
    private List<String> ignoredPlayers = new ArrayList<>();

    public User(UUID id) {
        this.id = id;
    }

}
