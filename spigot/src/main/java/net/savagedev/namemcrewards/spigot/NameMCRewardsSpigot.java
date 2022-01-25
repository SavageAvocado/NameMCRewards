package net.savagedev.namemcrewards.spigot;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.commands.NameMcCommand;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.namemc.ApiPollTask;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.storage.Storage;
import net.savagedev.namemcrewards.common.storage.implementation.file.FileStorage;
import net.savagedev.namemcrewards.common.storage.implementation.file.loader.YamlLoader;
import net.savagedev.namemcrewards.common.utils.io.Constants;
import net.savagedev.namemcrewards.spigot.commands.SpigotCommandExecutor;
import net.savagedev.namemcrewards.spigot.commands.sender.SpigotSender;
import net.savagedev.namemcrewards.spigot.listeners.ConnectionListener;
import net.savagedev.namemcrewards.spigot.listeners.MessageListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NameMCRewardsSpigot extends JavaPlugin implements NameMCRewardsPlugin {
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
    public void onDisable() {
        this.apiPollThread.shutdown();
        this.storage.shutdown();
    }

    private void initConfig() {
        this.config = new Configuration(this.getDataFolder().toPath().resolve("config.yml"));
    }

    private void initStorage() {
        this.storage = new Storage(new FileStorage(new YamlLoader(), this.getDataFolder().toPath().resolve(Paths.get("storage", "data.yml"))));
        this.storage.init();
    }

    private void startPollThread() {
        if (this.getConfiguration().getBoolean("bungeecord")) {
            this.getServer().getMessenger().registerIncomingPluginChannel(this, Constants.MESSAGING_CHANNEL, new MessageListener());
        } else {
            this.apiPollThread = new ApiPollTask(this);
            this.apiPollThread.start();
        }
    }

    private void initCommands() {
        final PluginCommand command = this.getCommand("namemc");
        if (command != null) {
            command.setExecutor(new SpigotCommandExecutor(new NameMcCommand(this)));
        }
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
    }

    @Override
    public void dispatchCommand(String command) {
        this.getServer().dispatchCommand(this.getServer().getConsoleSender(), command);
    }

    @Override
    public Set<Sender<?>> getOnlineSenders() {
        return this.getServer().getOnlinePlayers().stream().map(SpigotSender::new).collect(Collectors.toSet());
    }

    @Override
    public Optional<Sender<?>> getSender(UUID uuid) {
        final Player player = this.getServer().getPlayer(uuid);
        if (player == null) {
            return Optional.empty();
        }
        return Optional.of(new SpigotSender(player));
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
        final Player player = this.getServer().getPlayer(uuid);
        if (player == null) {
            return this.getServer().getOfflinePlayer(uuid).getName();
        }
        return player.getName();
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
