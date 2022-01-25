package net.savagedev.namemcrewards.common.listeners;

import java.util.UUID;

public abstract class AbstractConnectionListener {
    /**
     * Checks if the player liked/disliked the server while they were offline, to reward them accordingly.
     */
    protected void handleOfflineRating(UUID uuid, String username) {
    }
}
