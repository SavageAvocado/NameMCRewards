package net.savagedev.namemcrewards.common.config;

import net.savagedev.namemcrewards.common.storage.implementation.file.loader.YamlLoader;
import net.savagedev.namemcrewards.common.utils.io.FileUtils;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Configuration {
    private final ConfigurationLoader<?> loader;

    private ConfigurationNode root;

    public Configuration(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.copy(FileUtils.getStream(path).orElseThrow(NullPointerException::new), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.loader = new YamlLoader().load(path);
        this.reload();
    }

    public void reload() {
        try {
            this.root = this.loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.loader.save(this.root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        try {
            this.getPath(path).set(value);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String path) {
        return this.getPath(path).getBoolean();
    }

    public double getDouble(String path) {
        return this.getPath(path).getDouble();
    }

    public float getFloat(String path) {
        return this.getPath(path).getFloat();
    }

    public int getInteger(String path) {
        return this.getPath(path).getInt();
    }

    public List<String> getStringList(String path) {
        try {
            return this.getPath(path).getList(String.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public String getString(String path) {
        return this.getPath(path).getString();
    }

    private ConfigurationNode getPath(String path) {
        return this.root.node((Object[]) path.split("\\."));
    }
}
