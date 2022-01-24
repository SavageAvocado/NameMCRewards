package net.savagedev.namemcrewards.sponge;

import com.google.inject.Inject;
import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.commands.NameMcCommand;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.namemc.ApiPollTask;
import net.savagedev.namemcrewards.common.namemc.NameMCAPI;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.storage.Storage;
import net.savagedev.namemcrewards.common.storage.implementation.file.FileStorage;
import net.savagedev.namemcrewards.common.storage.implementation.file.loader.YamlLoader;
import net.savagedev.namemcrewards.sponge.commands.SpongeCommandExecutor;
import net.savagedev.namemcrewards.sponge.listeners.ConnectionListener;
import net.savagedev.namemcrewards.sponge.listeners.namemc.NameMcListener;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigRoot;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

@Plugin(id = "namemcrewards", name = "NameMC Rewards", version = "${version}", description = "Reward your players for liking your server on NameMC.", authors = {"SavageAvocado"})
public class NameMCRewardsSponge implements NameMCRewardsPlugin {
    @Inject
    private Game game;

    @Inject
    private ConfigRoot configRoot;

    private ApiPollTask apiPollThread;
    private Configuration config;
    private Storage storage;

    @Listener
    public void on(GameStartedServerEvent ignored) {
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

    public void on(GameStoppingServerEvent ignored) {
        this.apiPollThread.shutdown();
        this.storage.shutdown();
    }

    private void initConfig() {
        this.config = new Configuration(this.configRoot.getConfigPath().resolve("config.yml"));
    }

    private void initStorage() {
        this.storage = new Storage(new FileStorage(new YamlLoader(), this.configRoot.getConfigPath().resolve(Paths.get("storage", "data.yml"))));
        this.storage.init();
    }

    private void startPollThread() {
        /* if (this.getConfiguration().getBoolean("bungeecord")) {
            this.getServer().getMessenger().registerIncomingPluginChannel(this, Constants.MESSAGING_CHANNEL, new MessageListener());
        } else { */
        this.apiPollThread = new ApiPollTask(this);
        this.apiPollThread.start();
        // }
    }

    private void initCommands() {
        this.game.getCommandManager().register(this, new SpongeCommandExecutor(new NameMcCommand(this)), "namemc", "nmc");

    }

    private void initListeners() {
        this.game.getEventManager().registerListeners(this, new ConnectionListener());
        NameMCAPI.subscribe(new NameMcListener());
    }

    @Override
    public UUID getUniqueId(String username) {
        return this.game.getServer().getPlayer(username)
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
        return null;
    }
}
