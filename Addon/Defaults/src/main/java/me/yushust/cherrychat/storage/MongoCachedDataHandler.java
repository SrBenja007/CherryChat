package me.yushust.cherrychat.storage;

import me.yushust.cherrychat.api.bukkit.storage.Identifiable;
import org.mongodb.morphia.Datastore;

import java.util.UUID;
import java.util.function.Function;

public class MongoCachedDataHandler<T extends Identifiable> extends AbstractCachedDataHandler<T> {

    private Class<T> clazz;
    private Datastore datastore;

    public MongoCachedDataHandler(Class<T> clazz, Function<UUID, T> defaultProvider, Datastore datastore) {
        super(defaultProvider);
        this.clazz = clazz;
        this.datastore = datastore;
    }

    @Override
    public T load(UUID id) {
        T getted = datastore.find(clazz).field("_id").equal(id).get();
        if(getted != null)
            cache.put(id, getted);
        return getted;
    }

    @Override
    public void saveSync(T data) {
        datastore.save(data);
    }

    @Override
    public void removeSync(UUID id) {
        datastore.findAndDelete(datastore.find(clazz).field("_id").equal(id));
    }
}