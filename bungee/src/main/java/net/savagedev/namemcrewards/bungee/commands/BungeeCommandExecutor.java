package net.savagedev.namemcrewards.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.savagedev.namemcrewards.bungee.commands.sender.BungeeSender;
import net.savagedev.namemcrewards.common.command.Command;

public class BungeeCommandExecutor extends net.md_5.bungee.api.plugin.Command {
    private final Command command;

    public BungeeCommandExecutor(Command command, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.command = command;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.command.execute(new BungeeSender(sender), args);
    }
}
