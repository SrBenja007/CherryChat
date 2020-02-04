package me.yushust.cherrychat.util;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class Configuration extends YamlConfiguration {

    @Getter
    private final String fileName;
    private final JavaPlugin plugin;
    private final File folder;

    public Configuration(JavaPlugin plugin, String filename, String fileextension, File folder){
        this.folder = folder;
        this.plugin = plugin;
        this.fileName = filename + (filename.endsWith(fileextension) ? "" : fileextension);
        this.createFile();
    }

    public Configuration(final JavaPlugin plugin, final String fileName) {
        this(plugin, fileName, ".yml");
    }

    public Configuration(final JavaPlugin plugin, final String fileName, final String fileExtension) {
        this(plugin, fileName, fileExtension, plugin.getDataFolder());
    }

    @Override
    public String getString(String path){
        try{
            return ChatColor.translateAlternateColorCodes('&', super.getString(path));
        }catch(NullPointerException e){
            return path;
        }
    }

    public ItemStack getItemStack(String path, ItemStack def) {
        if(!this.contains(path)) return def;
        return super.getItemStack(path);
    }

    public List<String> getStringList(String path, List<String> def) {
        if(!super.contains(path)) return def;
        return super.getStringList(path);
    }

    public <T> T get(Class<T> clazz, String path){
        if(!super.contains(path)) return null;
        Object obj = super.get(path);
        return clazz.cast(obj);
    }

    private void createFile() {
        try {
            final File file = new File(folder, this.fileName);
            if (!file.exists()) {
                if (this.plugin.getResource(this.fileName) != null) {
                    this.plugin.saveResource(this.fileName, false);
                } else {
                    this.save(file);
                }
                this.load(file);
            } else {
                this.load(file);
                this.save(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        final File folder = this.plugin.getDataFolder();
        try {
            this.save(new File(folder, this.fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}