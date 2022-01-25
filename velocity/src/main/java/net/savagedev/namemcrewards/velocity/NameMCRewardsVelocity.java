package net.savagedev.namemcrewards.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.commands.NameMcCommand;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.namemc.ApiPollTask;
import net.savagedev.namemcrewards.common.namemc.NameMcApi;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.storage.Storage;
import net.savagedev.namemcrewards.common.storage.implementation.file.FileStorage;
import net.savagedev.namemcrewards.common.storage.implementation.file.loader.YamlLoader;
import net.savagedev.namemcrewards.velocity.commands.VelocityCommandExecutor;
import net.savagedev.namemcrewards.velocity.listeners.ConnectionListener;
import net.savagedev.namemcrewards.velocity.listeners.namemc.NameMcListener;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

@Plugin(id = "namemcrewards", name = "NameMC Rewards", version = "${version}", description = "Reward your players for liking your server on NameMC.", authors = {"SavageAvocado"})
public class NameMCRewardsVelocity implements NameMCRewardsPlugin {
    private final ProxyServer server;
    private final Logger logger;

    private ApiPollTask apiPollThread;
    private Configuration config;
    private Storage storage;

    @Inject
    @DataDirectory
    private Path dataPath;

    @Inject
    public NameMCRewardsVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void on(ProxyInitializeEvent ignored) {
        this.onReload();
        this.initCommands();
        this.initListeners();
        this.startPollThread();
    }

    @Override
    public void onReload() {
        this.initConfig();
        if (this.storage != null) {
            this.storage.shutdown();
        }
        this.initStorage();
    }

    @Override
    public Set<Sender<?>> getOnlineSenders() {
        return null;
    }

    @Subscribe
    public void on(ProxyShutdownEvent ignored) {
        this.apiPollThread.shutdown();
        this.storage.shutdown();
    }

    private void initConfig() {
        this.config = new Configuration(this.dataPath.resolve("config.yml"));
    }

    private void initStorage() {
        this.storage = new Storage(new FileStorage(new YamlLoader(), this.dataPath.resolve(Paths.get("storage", "data.yml"))));
        this.storage.init();
    }

    private void startPollThread() {
        this.apiPollThread = new ApiPollTask(this);
        this.apiPollThread.start();
    }

    private void initCommands() {
        this.server.getCommandManager().register(new VelocityCommandExecutor(new NameMcCommand(this)), "namemc", "nmc");
    }

    private void initListeners() {
        this.server.getEventManager().register(this, new ConnectionListener());
        NameMcApi.subscribe(new NameMcListener());
    }

    @Override
    public UUID getUniqueId(String username) {
        return this.server.getPlayer(username)
                .orElseThrow(RuntimeException::new).getUniqueId();
    }

    @Override
    public Configuration getConfiguration() {
        return this.config;
    }

    @Override
    public Storage getStorage() {
        return this.storage;
    }

    @Override
    public Logger getPluginLogger() {
        return this.logger;
    }
}
