package me.yushust.cherrychat.api.bukkit.storage;

import me.yushust.cherrychat.api.bukkit.model.Model;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ObjectDatastore<T extends Model> {

  CompletableFuture<T> find(UUID id);

  CompletableFuture<Void> save(T data);

  CompletableFuture<Void> remove(UUID id);

  T findSync(UUID id);

  void saveSync(T data);

  void removeSync(UUID id);

}
