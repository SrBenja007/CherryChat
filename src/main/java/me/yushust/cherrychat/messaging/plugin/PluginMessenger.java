package me.yushust.cherrychat.messaging.plugin;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.yushust.cherrychat.messaging.ChannelListener;
import me.yushust.cherrychat.messaging.Messenger;
import me.yushust.cherrychat.messaging.PlayerChannelListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessenger implements PluginMessageListener, Messenger {

    private final Map<String, Collection<PlayerChannelListener>> listeners = new ConcurrentHashMap<>();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] buf) {
        if(!channel.equalsIgnoreCase("BungeeCord"))
            return;

        ByteArrayDataInput input = ByteStreams.newDataInput(buf);
        String subChannel = input.readUTF();

        byte[] messageData = new byte[input.readShort()];
        input.readFully(messageData);

        DataInputStream messageStream = new DataInputStream(new ByteArrayInputStream(messageData));

        Collection<PlayerChannelListener> listenerCollection = listeners.getOrDefault(subChannel, new ArrayList<>());
        listenerCollection.forEach(listener -> {
            String[] args = new String[listener.getArgumentCount()];
            for(int i = 0; i < listener.getArgumentCount(); i++) {
                try {
                    args[i] = messageStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            listener.onMessage(player, args);
        });
    }

    @Override @Deprecated
    public void publishMessage(String channel, String message) {
        throw new RuntimeException(
            "Please use Player#sendPluginMessage instead of PluginMessenger#publishMessage"
        );
    }

    @Override
    public void registerListener(String channel, ChannelListener listener) {
        PlayerChannelListener playerListener = listener instanceof PlayerChannelListener ?
                (PlayerChannelListener) listener :
                new PlayerChannelListener() {
                    @Getter private int argumentCount = 1;

                    public void onMessage(Player player, String[] arguments) {
                        listener.onMessage(arguments[0]);
                    }
                };

        Collection<PlayerChannelListener> listenerCollection = listeners.getOrDefault(channel, new ArrayList<>());
        listenerCollection.add(playerListener);
        listeners.put(channel, listenerCollection);
    }

    @Override
    public Collection<ChannelListener> getListeners(String channel) {
        return new ArrayList<>(listeners.getOrDefault(channel, new ArrayList<>()));
    }

    @Override
    public void submitSubscription() {/* ~Empty~ */}

}
