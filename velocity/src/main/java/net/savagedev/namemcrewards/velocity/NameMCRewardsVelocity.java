package net.savagedev.namemcrewards.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.savagedev.namemcrewards.common.commands.NameMcCommand;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.storage.Storage;
import net.savagedev.namemcrewards.velocity.commands.VelocityCommandExecutor;

import java.util.UUID;
import java.util.logging.Logger;

@Plugin(id = "namemcrewards", name = "NameMC Rewards", version = "${version}", description = "Reward your players for liking your server on NameMC.", authors = {"SavageAvocado"})
public class NameMCRewardsVelocity implements NameMCRewardsPlugin {
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public NameMCRewardsVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        this.server.getCommandManager().register(new VelocityCommandExecutor(new NameMcCommand(this)), "namemc", "nmc");
    }

    @Subscribe
    public void on(ProxyInitializeEvent event) {
    }

    @Override
    public void onReload() {
    }

    @Override
    public UUID getUniqueId(String username) {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public Storage getStorage() {
        return null;
    }

    @Override
    public Logger getPluginLogger() {
        return this.logger;
    }
}
