package me.yushust.cherrychat.api.bukkit.model;

import java.util.Set;
import java.util.UUID;

/**
 * Class that holds data of a
 * specific player
 */
public class User
    implements Model {

  /** The player id */
  private final UUID id;

  /** The players that the player has blocked */
  private final Set<UUID> blocks;

  public User(
      UUID id,
      Set<UUID> blocks
  ) {
    this.id = id;
    this.blocks = blocks;
  }

  //#region Getters
  @Override
  public UUID getId() {
    return id;
  }

  public Set<UUID> getBlocks() {
    return blocks;
  }
  //#endregion

}
