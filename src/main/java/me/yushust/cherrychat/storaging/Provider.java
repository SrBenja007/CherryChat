package me.yushust.cherrychat.storaging;

import java.util.UUID;

public interface Provider<T> {

    T provide(UUID id);

}
