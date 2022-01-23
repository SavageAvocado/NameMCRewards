package net.savagedev.namemcrewards.common.storage.implementation.file.loader;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.nio.file.Path;


public interface ConfigLoader {
    ConfigurationLoader<? extends ConfigurationNode> load(Path path);
}
