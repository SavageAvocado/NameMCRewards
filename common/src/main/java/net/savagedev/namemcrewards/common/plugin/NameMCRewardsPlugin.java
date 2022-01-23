package net.savagedev.namemcrewards.common.plugin;

import net.savagedev.namemcrewards.common.config.Configuration;
import net.savagedev.namemcrewards.common.storage.Storage;

import java.util.UUID;
import java.util.logging.Logger;

public interface NameMCRewardsPlugin {
    void onReload();

    UUID getUniqueId(String username);

    Configuration getConfiguration();

    Storage getStorage();

    Logger getPluginLogger();
}
