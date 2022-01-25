package net.savagedev.namemcrewards.common.namemc;

import net.savagedev.namemcrewards.common.listeners.DefaultNameMcListener;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApiPollTask implements Runnable {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final NameMCRewardsPlugin context;

    public ApiPollTask(final NameMCRewardsPlugin context) {
        this.context = context;

        NameMcApi.subscribe(new DefaultNameMcListener(context));
    }

    @Override
    public void run() {
        NameMcApi.forceCheck(this.context).join();
    }

    public void start() {
        this.executor.scheduleAtFixedRate(this, 0L, 150L, TimeUnit.SECONDS);
    }

    public void shutdown() {
        this.executor.shutdown();
    }
}
