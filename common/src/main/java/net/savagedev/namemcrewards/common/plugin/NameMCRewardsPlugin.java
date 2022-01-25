package net.savagedev.namemcrewards.common.plugin;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.storage.Storage;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public interface NameMCRewardsPlugin {
    void onReload();

    void dispatchCommand(String command);

    Set<Sender<?>> getOnlineSenders();

    Optional<Sender<?>> getSender(UUID uuid);

    UUID getUniqueId(String username);

    String getUsername(UUID uuid);

    Configuration getConfiguration();

    Storage getStorage();

    Logger getPluginLogger();
}
