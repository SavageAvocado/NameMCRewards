package net.savagedev.namemcrewards.nukkit;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.commands.NameMcCommand;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.namemc.ApiPollTask;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.storage.Storage;
import net.savagedev.namemcrewards.common.storage.implementation.file.FileStorage;
import net.savagedev.namemcrewards.common.storage.implementation.file.loader.YamlLoader;
import net.savagedev.namemcrewards.nukkit.command.NukkitCommandExecutor;
import net.savagedev.namemcrewards.nukkit.command.sender.NukkitSender;
import net.savagedev.namemcrewards.nukkit.listeners.ConnectionListener;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NameMCRewardsNukkit extends PluginBase implements NameMCRewardsPlugin {
    private final Logger logger = Logger.getLogger("NameMCRewards");

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
    }

    @Override
    public Set<Sender<?>> getOnlineSenders() {
        return this.getServer().getOnlinePlayers().values().stream().map(NukkitSender::new).collect(Collectors.toSet());
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
        this.getServer().getCommandMap().register("NameMCRewards", new NukkitCommandExecutor(new NameMcCommand(this)));
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
    }

    @Override
    public UUID getUniqueId(String username) {
        final Player player = this.getServer().getPlayer(username);
        if (player == null) {
            return this.getServer().getOfflinePlayer(username).getUniqueId();
        }
        return player.getUniqueId();
    }

    @Override
    public String getUsername(UUID uuid) {
        final Optional<Player> player = this.getServer().getPlayer(uuid);
        if (player.isEmpty()) {
            return this.getServer().getOfflinePlayer(uuid).getName();
        }
        return player.get().getName();
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
