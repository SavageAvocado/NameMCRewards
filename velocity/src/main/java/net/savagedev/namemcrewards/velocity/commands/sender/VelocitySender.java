package net.savagedev.namemcrewards.velocity.commands.sender;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import net.savagedev.namemcrewards.common.command.sender.Sender;

import java.util.List;

public class VelocitySender implements Sender<CommandSource> {
    private final CommandSource source;

    public VelocitySender(CommandSource source) {
        this.source = source;
    }

    @Override
    public void message(List<String> messages) {
        for (String message : messages) {
            this.source.sendMessage(LegacyComponentSerializer.legacy().deserialize(message, '&'));
        }
    }

    @Override
    public void message(String message) {
        this.source.sendMessage(LegacyComponentSerializer.legacy().deserialize(message, '&'));
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
