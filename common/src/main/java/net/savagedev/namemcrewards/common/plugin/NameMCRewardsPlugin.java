package net.savagedev.namemcrewards.common.plugin;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.storage.Storage;

import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public interface NameMCRewardsPlugin {
    void onReload();

    Set<Sender<?>> getOnlineSenders();

    UUID getUniqueId(String username);

    Configuration getConfiguration();

    Storage getStorage();

    Logger getPluginLogger();
}
