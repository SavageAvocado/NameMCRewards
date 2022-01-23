package net.savagedev.namemcrewards.common.storage;

import net.savagedev.namemcrewards.common.storage.implementation.StorageImplementation;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Storage {
    private final StorageImplementation implementation;

    public Storage(final StorageImplementation implementation) {
        this.implementation = implementation;
    }

    public void init() {
        this.implementation.init();
    }

    public void shutdown() {
        this.implementation.shutdown();
    }

    public CompletableFuture<Void> removeLike(UUID uuid) {
        return CompletableFuture.runAsync(() -> this.implementation.removeLike(uuid));
    }

    public CompletableFuture<Void> addLike(UUID uuid) {
        return CompletableFuture.runAsync(() -> this.implementation.addLike(uuid));
    }

    public CompletableFuture<Set<UUID>> getLikes() {
        return CompletableFuture.supplyAsync(this.implementation::getLikes);
    }
}
