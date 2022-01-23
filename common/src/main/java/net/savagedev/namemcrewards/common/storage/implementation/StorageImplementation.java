package net.savagedev.namemcrewards.common.storage.implementation;

import java.util.Set;
import java.util.UUID;

public interface StorageImplementation {
    void init();

    void shutdown();

    void removeLike(UUID uuid);

    void addLike(UUID uuid);

    Set<UUID> getLikes();
}
