package net.savagedev.namemcrewards.sponge.commands;

import net.savagedev.namemcrewards.common.command.Command;
import net.savagedev.namemcrewards.sponge.commands.sender.SpongeSender;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class SpongeCommandExecutor implements CommandCallable {
    private final Command command;

    public SpongeCommandExecutor(Command command) {
        this.command = command;
    }

    @Override
    public CommandResult process(CommandSource source, String arguments) throws CommandException {
        this.command.execute(new SpongeSender(source), arguments.split(" "));
        return null;
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments, @Nullable Location<World> targetPosition) throws CommandException {
        return null;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        return true;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.empty();
    }

    @Override
    public Optional<Text> getHelp(CommandSource source) {
        return Optional.empty();
    }

    @Override
    public Text getUsage(CommandSource source) {
        return null;
    }
}
