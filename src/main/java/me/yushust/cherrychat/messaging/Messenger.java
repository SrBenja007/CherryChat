package me.yushust.cherrychat.messaging;

import java.util.Collection;

public interface Messenger {

    void publishMessage(String channel, String message);

    void registerListener(String channel, ChannelListener listener);

    Collection<ChannelListener> getListeners(String channel);

    void submitSubscription();

}
