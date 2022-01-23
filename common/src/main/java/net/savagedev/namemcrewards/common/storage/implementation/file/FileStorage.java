package net.savagedev.namemcrewards.common.storage.implementation.file;

import net.savagedev.namemcrewards.common.storage.implementation.file.loader.ConfigLoader;
import net.savagedev.namemcrewards.common.storage.implementation.StorageImplementation;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class FileStorage implements StorageImplementation {
    private final Lock lock = new ReentrantLock();

    private final ConfigurationLoader<?> loader;

    private ConfigurationNode root;

    public FileStorage(ConfigLoader loader, Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.loader = loader.load(path);
    }

    @Override
    public void init() {
        this.load();
    }

    @Override
    public void shutdown() {
        this.save();
    }

    @Override
    public void removeLike(UUID uuid) {
        this.lock.lock();
        try {
            List<String> likes = this.getLikesAsStringList();
            likes.remove(uuid.toString());
            this.root.node("likes").set(likes);
            this.loader.save(this.root);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void addLike(UUID uuid) {
        this.lock.lock();
        try {
            List<String> likes = this.getLikesAsStringList();
            likes.add(uuid.toString());
            this.root.node("likes").set(likes);
            this.loader.save(this.root);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public Set<UUID> getLikes() {
        this.lock.lock();
        try {
            return this.getLikesAsStringList().stream().map(UUID::fromString).collect(Collectors.toSet());
        } finally {
            this.lock.unlock();
        }
    }

    private List<String> getLikesAsStringList() {
        try {
            return this.root.node("likes").getList(String.class, new ArrayList<>());
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void load() {
        this.lock.lock();
        try {
            this.root = this.loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    private void save() {
        this.lock.lock();
        try {
            this.loader.save(this.root);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }
}
