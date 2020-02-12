package me.yushust.cherrychat.storage;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import me.yushust.cherrychat.api.bukkit.storage.DataHandler;
import me.yushust.cherrychat.api.bukkit.storage.Identifiable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.function.Function;

public abstract class AbstractCachedDataHandler<T extends Identifiable> implements DataHandler<T> {

    protected final ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(8));
    protected final Map<UUID, T> cache = new ConcurrentHashMap<>();

    private Function<UUID, T> defaultProvider;

    public AbstractCachedDataHandler(Function<UUID, T> defaultProvider) {
        this.defaultProvider = defaultProvider;
    }

    @Override
    public ListenableFuture<T> find(UUID id) {
        return executor.submit(() -> findSync(id));
    }

    @Override
    public ListenableFuture<T> refresh(UUID id) {
        return executor.submit(() -> refreshSync(id));
    }

    @Override
    public ListenableFuture<Void> save(T data) {
        return executor.submit(() -> {
            cache.put(data.getId(), data);
            saveSync(data);
            return null;
        });
    }

    @Override
    public ListenableFuture<Void> remove(UUID data) {
        return executor.submit(() -> {
            removeSync(data);
            return null;
        });
    }

    @Override
    public T findSync(UUID id) {
        return cache.getOrDefault(id, load(id));
    }

    @Override
    public T refreshSync(UUID id) {
        T loaded = cache.get(id);
        if(loaded == null) {
            loaded = load(id);
            if(loaded == null)
                loaded = defaultProvider.apply(id);
        }
        saveSync(loaded);
        return loaded;
    }

    public abstract T load(UUID id);

}
