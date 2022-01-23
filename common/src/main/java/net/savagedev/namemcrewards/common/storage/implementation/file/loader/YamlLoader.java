package net.savagedev.namemcrewards.common.storage.implementation.file.loader;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlLoader implements ConfigLoader {
    @Override
    public ConfigurationLoader<? extends ConfigurationNode> load(Path path) {
        return YamlConfigurationLoader.builder()
                .nodeStyle(NodeStyle.BLOCK)
                .indent(2)
                .source(() -> Files.newBufferedReader(path, StandardCharsets.UTF_8))
                .sink(() -> Files.newBufferedWriter(path, StandardCharsets.UTF_8))
                .build();
    }
}
