package me.yushust.cherrychat.storage;

import java.util.UUID;

public interface Provider<T> {

    T provide(UUID id);

}
