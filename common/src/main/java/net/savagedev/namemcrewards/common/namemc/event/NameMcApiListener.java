package net.savagedev.namemcrewards.common.namemc.event;

import java.util.UUID;

public interface NameMcApiListener {
    void onDislike(UUID uuid);

    void onLike(UUID uuid);
}
