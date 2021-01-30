package me.yushust.cherrychat.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.yushust.cherrychat.CherryChatPlugin;

import java.lang.reflect.Method;

public class Properties {

    private static Object getPropertyManager() throws Exception {
        Class<?> minecraftServer = Class.forName("net.minecraft.server." + CherryChatPlugin.getServerVersion() + ".MinecraftServer");
        Class<?> dedicatedServer = Class.forName("net.minecraft.server." + CherryChatPlugin.getServerVersion() + ".DedicatedServer");

        Object minecraftServerInstance = minecraftServer.getDeclaredMethod("getServer").invoke(null);
        Object dedicatedServerInstance = dedicatedServer.cast(minecraftServerInstance);

        return dedicatedServer.getDeclaredField("propertyManager").get(dedicatedServerInstance);
    }

    public static void setAndSaveProperties(ServerProperty property, Object value) throws Exception {
        Object propertyManager = getPropertyManager();
        setServerProperty(propertyManager, property, value);
        savePropertiesFile(propertyManager);
    }

    public static void savePropertiesFile(Object propertyManager) throws Exception {
        Method savePropertiesMethod = propertyManager.getClass().getDeclaredMethod("savePropertiesFile");
        savePropertiesMethod.invoke(propertyManager);
    }

    public static void setServerProperty(Object propertyManager, ServerProperty property, Object value) throws Exception {
        Method setPropertyMethod = propertyManager.getClass().getDeclaredMethod("setProperty", String.class, Object.class);
        setPropertyMethod.invoke(propertyManager, property.getPropertyName(), value);
    }

    @AllArgsConstructor @Getter
    public enum ServerProperty {

        SPAWN_PROTECTION("spawn-protection"),
        SERVER_NAME("server-name"),
        FORCE_GAMEMODE("force-gamemode"),
        NETHER("allow-nether"),
        DEFAULT_GAMEMODE("gamemode"),
        QUERY("enable-query"),
        PLAYER_IDLE_TIMEOUT("player-idle-timeout"),
        DIFFICULTY("difficulty"),
        SPAWN_MONSTERS("spawn-monsters"),
        OP_PERMISSION_LEVEL("op-permission-level"),
        RESOURCE_PACK_HASH("resource-pack-hash"),
        RESOURCE_PACK("resource-pack"),
        ANNOUNCE_PLAYER_ACHIEVEMENTS("announce-player-achievements"),
        PVP("pvp"),
        SNOOPER("snooper-enabled"),
        LEVEL_NAME("level-name"),
        LEVEL_TYPE("level-type"),
        LEVEL_SEED("level-seed"),
        HARDCORE("hardcore"),
        COMMAND_BLOCKS("enable-command-blocks"),
        MAX_PLAYERS("max-players"),
        PACKET_COMPRESSION_LIMIT("network-compression-threshold"),
        MAX_WORLD_SIZE("max-world-size"),
        IP("server-ip"),
        PORT("server-port"),
        DEBUG_MODE("debug"),
        SPAWN_NPCS("spawn-npcs"),
        SPAWN_ANIMALS("spawn-animals"),
        FLIGHT("allow-flight"),
        VIEW_DISTANCE("view-distance"),
        WHITE_LIST("white-list"),
        GENERATE_STRUCTURES("generate-structures"),
        MAX_BUILD_HEIGHT("max-build-height"),
        MOTD("motd"),
        REMOTE_CONTROL("enable-rcon");

        private String propertyName;

    }

}
