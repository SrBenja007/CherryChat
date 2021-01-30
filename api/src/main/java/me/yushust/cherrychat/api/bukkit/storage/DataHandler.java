package me.yushust.cherrychat.api.bukkit.storage;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.UUID;

public interface DataHandler<T extends Identifiable> {

    ListenableFuture<T> find(UUID id);

    ListenableFuture<T> refresh(UUID id);

    ListenableFuture<Void> save(T data);

    ListenableFuture<Void> remove(UUID id);

    T findSync(UUID id);

    T refreshSync(UUID id);

    void saveSync(T data);

    void removeSync(UUID id);

}
