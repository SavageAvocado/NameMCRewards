package net.savagedev.namemcrewards.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.savagedev.namemcrewards.bungee.commands.BungeeCommandExecutor;
import net.savagedev.namemcrewards.bungee.commands.sender.BungeeSender;
import net.savagedev.namemcrewards.bungee.listeners.ConnectionListener;
import net.savagedev.namemcrewards.bungee.listeners.namemc.NameMcListener;
import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.commands.NameMcCommand;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.namemc.ApiPollTask;
import net.savagedev.namemcrewards.common.namemc.NameMcApi;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.storage.Storage;
import net.savagedev.namemcrewards.common.storage.implementation.file.FileStorage;
import net.savagedev.namemcrewards.common.storage.implementation.file.loader.YamlLoader;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NameMCRewardsBungee extends Plugin implements NameMCRewardsPlugin {
    private ApiPollTask apiPollThread;
    private Configuration config;
    private Storage storage;

    @Override
    public void onEnable() {
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
    public void dispatchCommand(String command) {
        this.getProxy().getPluginManager().dispatchCommand(this.getProxy().getConsole(), command);
    }

    @Override
    public Set<Sender<?>> getOnlineSenders() {
        return this.getProxy().getPlayers().stream().map(BungeeSender::new).collect(Collectors.toSet());
    }

    @Override
    public Optional<Sender<?>> getSender(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void onDisable() {
        this.apiPollThread.shutdown();
        this.storage.shutdown();
    }

    private void initConfig() {
        this.config = new Configuration(this.getDataFolder().toPath().resolve("config.yml"));
    }

    private void startPollThread() {
        this.apiPollThread = new ApiPollTask(this);
        this.apiPollThread.start();
    }

    private void initStorage() {
        this.storage = new Storage(new FileStorage(new YamlLoader(), this.getDataFolder().toPath().resolve(Paths.get("storage", "data.yml"))));
        this.storage.init();
    }

    private void initCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new BungeeCommandExecutor(new NameMcCommand(this), "namemc", null, "nmc"));
    }

    private void initListeners() {
        this.getProxy().getPluginManager().registerListener(this, new ConnectionListener(this));
        NameMcApi.subscribe(new NameMcListener());
    }

    @Override
    public UUID getUniqueId(String username) {
        return null;
    }

    @Override
    public String getUsername(UUID uuid) {
        return null;
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
        return this.getLogger();
    }
}
