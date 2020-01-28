package me.yushust.cherrychat.messaging.redis;

import lombok.AllArgsConstructor;
import redis.clients.jedis.JedisPubSub;

@AllArgsConstructor
public class CherryChatRedisPubSub extends JedisPubSub {

    private RedisMessenger messenger;

    @Override
    public void onMessage(String channel, String message) {
        messenger.getListeners(channel).forEach(listener -> listener.onMessage(message));
    }

}
