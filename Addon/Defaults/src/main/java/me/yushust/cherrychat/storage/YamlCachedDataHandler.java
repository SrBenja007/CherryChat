package me.yushust.cherrychat.storage;

import me.yushust.cherrychat.api.bukkit.storage.Identifiable;
import me.yushust.cherrychat.api.bukkit.util.Configuration;

import java.util.UUID;
import java.util.function.Function;

public class YamlCachedDataHandler<T extends Identifiable> extends AbstractCachedDataHandler<T> {

    private Class<T> type;
    private Configuration data;
    public YamlCachedDataHandler(Class<T> type, Function<UUID, T> defaultProvider, Configuration data) {
        super(defaultProvider);
        this.data = data;
        this.type = type;
    }

    @Override
    public T load(UUID id) {
        T found = data.get(type, type.getSimpleName() + "." + id);
        if(found != null)
            cache.put(id, found);
        return found;
    }

    @Override
    public void saveSync(T data) {
        this.data.set(type.getSimpleName() + "." + data.getId(), data);
        this.data.save();
    }

    @Override
    public void removeSync(UUID id) {
        this.data.set(type.getSimpleName() + "." + id, null);
        this.data.save();
    }
}

