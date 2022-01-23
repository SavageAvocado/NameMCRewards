package net.savagedev.namemcrewards.common.storage.implementation.sql;

import net.savagedev.namemcrewards.common.storage.implementation.StorageImplementation;

import java.util.Set;
import java.util.UUID;

public class MySQLStorage implements StorageImplementation {
    public MySQLStorage() {
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
