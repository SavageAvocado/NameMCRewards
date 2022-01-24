package net.savagedev.namemcrewards.sponge.commands.sender;


import net.savagedev.namemcrewards.common.command.sender.Sender;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;

public class SpongeSender implements Sender<CommandSource> {
    private final CommandSource source;

    public SpongeSender(final CommandSource source) {
        this.source = source;
    }

    @Override
    public void message(List<String> messages) {
        for (String message : messages) {
            this.source.sendMessage(TextSerializers.formattingCode('&').deserialize(message));
        }
    }

    @Override
    public void message(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        this.source.sendMessage(TextSerializers.formattingCode('&').deserialize(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.source.hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return this.source instanceof Player;
    }

    @Override
    public CommandSource getBase() {
        return this.source;
    }
}
