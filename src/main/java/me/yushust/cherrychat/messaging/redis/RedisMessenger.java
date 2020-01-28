package me.yushust.cherrychat.messaging.redis;

import lombok.Getter;
import me.yushust.cherrychat.messaging.ChannelListener;
import me.yushust.cherrychat.messaging.Messenger;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisMessenger implements Messenger {

    private static Map<String, Collection<ChannelListener>> listeners = new ConcurrentHashMap<>();

    @Getter
    private JedisPool dataSource;

    public RedisMessenger(String host, int port, String password){
        this.init(host, port, password);
    }

    public Collection<ChannelListener> getListeners(String channelName){
        return listeners.getOrDefault(channelName, new ArrayList<>());
    }

    @Override
    public void publishMessage(String channel, String message) {
        try(Jedis jedis = dataSource.getResource()) {
            jedis.publish(channel, message);
        }
    }

    @Override
    public void registerListener(String channelName, ChannelListener listener) {
        Collection<ChannelListener> listenerList = listeners.getOrDefault(channelName, new ArrayList<>());
        listenerList.add(listener);
        listeners.put(channelName, listenerList);
    }

    private void init(String host, int port, String password){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(12);
        config.setTestOnBorrow(true);

        if(password == null || password.isEmpty())
            this.dataSource = new JedisPool(config, host, port);
        else
            this.dataSource = new JedisPool(config, host, port, 2000, password);

        try(Jedis jedis = this.getResource()){
            jedis.ping();
        }
    }

    @Override
    public void submitSubscription() {
        JedisPubSub subscriber = new CherryChatRedisPubSub(this);

        try(Jedis jedis = dataSource.getResource()) {
            jedis.subscribe(subscriber, listeners.keySet().toArray(new String[0]));
        }
    }

    public Jedis getResource() { return dataSource.getResource(); }

}
