package net.savagedev.namemcrewards.spigot.commands;

import net.savagedev.namemcrewards.common.command.Command;
import net.savagedev.namemcrewards.spigot.commands.sender.SpigotSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class SpigotCommandExecutor implements CommandExecutor {
    private final Command command;

    public SpigotCommandExecutor(final Command command) {
        this.command = command;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull org.bukkit.command.Command cmd, @Nonnull String d, @Nonnull String[] args) {
        this.command.execute(new SpigotSender(sender), args);
        return true;
    }
}
