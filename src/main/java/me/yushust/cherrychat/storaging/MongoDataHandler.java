package me.yushust.cherrychat.storaging;

import org.mongodb.morphia.Datastore;

import java.util.UUID;

public class MongoDataHandler<T> extends AbstractDataHandler<T> {

    private Class<T> clazz;
    private Datastore datastore;

    public MongoDataHandler(Class<T> clazz, Provider<T> defaultProvider, Datastore datastore) {
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
