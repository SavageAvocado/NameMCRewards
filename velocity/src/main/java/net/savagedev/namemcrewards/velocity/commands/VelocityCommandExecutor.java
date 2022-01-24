package net.savagedev.namemcrewards.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import net.savagedev.namemcrewards.common.command.Command;
import net.savagedev.namemcrewards.velocity.commands.sender.VelocitySender;
import org.checkerframework.checker.nullness.qual.NonNull;

public class VelocityCommandExecutor implements com.velocitypowered.api.command.Command {
    private final Command command;

    public VelocityCommandExecutor(Command command) {
        this.command = command;
    }

    @Override
    public void execute(CommandSource source, String @NonNull [] args) {
        this.command.execute(new VelocitySender(source), args);
    }
}
