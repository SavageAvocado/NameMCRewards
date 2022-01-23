package net.savagedev.namemcrewards.common.storage.implementation.mongodb;

import com.mongodb.client.MongoClient;
import net.savagedev.namemcrewards.common.storage.implementation.StorageImplementation;

import java.util.Set;
import java.util.UUID;

public class MongoDbStorage implements StorageImplementation {
    private MongoClient client;

    public MongoDbStorage() {
    }

    @Override
    public void init() {
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void removeLike(UUID uuid) {
    }

    @Override
    public void addLike(UUID uuid) {
    }

    @Override
    public Set<UUID> getLikes() {
        return null;
    }
}
