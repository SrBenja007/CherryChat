package me.yushust.cherrychat.api.bukkit.storage;

public enum StorageMethod {

    MONGODB(true, 27017, "mongo"),
    YAML(false, -1, "yml");

    private boolean requiresAuth;
    private int defaultPort;
    private String[] aliases;

    StorageMethod(boolean requiresAuth, int defaultPort, String... aliases) {
        this.requiresAuth = requiresAuth;
        this.defaultPort = defaultPort;
        this.aliases = aliases == null ? new String[0] : aliases;
    }

    public boolean requiresAuth() {
        return requiresAuth;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public String[] getAliases() {
        return aliases;
    }

    public static StorageMethod getByName(String name) {
        for(StorageMethod method : StorageMethod.values()) {
            if(method.name().equalsIgnoreCase(name)) return method;
            for(String alias : method.getAliases()) {
                if(name.equalsIgnoreCase(alias)) return method;
            }
        }
        return StorageMethod.DEFAULT();
    }

    public static StorageMethod DEFAULT() {
        return StorageMethod.YAML;
    }

}
