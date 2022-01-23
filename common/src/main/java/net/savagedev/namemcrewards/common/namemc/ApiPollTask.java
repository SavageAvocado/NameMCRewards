package net.savagedev.namemcrewards.common.namemc;

import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.namemc.event.NameMcApiListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ApiPollTask implements Runnable {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final NameMCRewardsPlugin context;
    private final String address;

    public ApiPollTask(final NameMCRewardsPlugin context) {
        this.address = context.getConfiguration().getString("address");
        this.context = context;
    }

    @Override
    public void run() {
        this.context.getPluginLogger().log(Level.INFO, "Checking for likes via the NameMC API...");

        final Set<UUID> localLikes = this.context.getStorage().getLikes().join();
        final Set<UUID> apiLikes = NameMCAPI.getLikes(this.address).join();

        final Set<UUID> newLikes = new HashSet<>(apiLikes);
        newLikes.removeAll(localLikes);

        final Set<UUID> removedLikes = new HashSet<>(localLikes);
        removedLikes.removeAll(apiLikes);

        this.context.getPluginLogger().log(Level.INFO, newLikes.size() + " new likes | " + removedLikes.size() + " removed likes.");

        for (UUID uuid : newLikes) {
            this.context.getStorage().addLike(uuid);
            for (NameMcApiListener listener : NameMCAPI.LISTENERS) {
                listener.onLike(uuid);
            }
        }

        for (UUID uuid : removedLikes) {
            this.context.getStorage().removeLike(uuid);
            for (NameMcApiListener listener : NameMCAPI.LISTENERS) {
                listener.onDislike(uuid);
            }
        }
    }

    public void start() {
        this.executor.scheduleAtFixedRate(this, 0L, 150L, TimeUnit.SECONDS);
    }

    public void shutdown() {
        this.executor.shutdown();
    }
}
