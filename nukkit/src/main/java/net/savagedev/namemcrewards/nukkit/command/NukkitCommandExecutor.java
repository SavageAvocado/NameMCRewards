package net.savagedev.namemcrewards.nukkit.command;

import cn.nukkit.command.CommandSender;
import net.savagedev.namemcrewards.nukkit.command.sender.NukkitSender;
import net.savagedev.namemcrewards.common.command.Command;

public class NukkitCommandExecutor extends cn.nukkit.command.Command {
    private final Command command;

    public NukkitCommandExecutor(Command command) {
        super("namemc", "Get a link to the server's NameMC page.", "/namemc <reload|likes [player]>", new String[]{"nmc"});
        this.command = command;
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        this.command.execute(new NukkitSender(sender), args);
        return true;
    }
}
