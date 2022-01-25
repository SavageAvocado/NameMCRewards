package net.savagedev.namemcrewards.common.commands;

import net.savagedev.namemcrewards.common.command.Command;
import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.commands.subcommands.ForceCheckCommand;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;
import net.savagedev.namemcrewards.common.commands.subcommands.LikesCommand;
import net.savagedev.namemcrewards.common.commands.subcommands.ReloadCommand;
import net.savagedev.namemcrewards.common.commands.subcommands.SubCommand;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NameMcCommand implements Command {
    private final Map<String, SubCommand> commandMap = new HashMap<>();

    private final NameMCRewardsPlugin context;

    public NameMcCommand(final NameMCRewardsPlugin context) {
        this.context = context;

        this.commandMap.put("forcecheck", new ForceCheckCommand(context));
        this.commandMap.put("likes", new LikesCommand(context));
        this.commandMap.put("reload", new ReloadCommand(context));
    }

    @Override
    public void execute(Sender<?> sender, String[] args) {
        if (args.length == 0 || !sender.hasPermission("namemcrewards.admin")) {
            sender.message(this.context.getConfiguration().getStringList("namemc-info"));
            return;
        }

        final String commandName = args[0].toLowerCase(Locale.ROOT);

        if (!this.commandMap.containsKey(commandName)) {
            sender.message("&cInvalid arguments! Try: /namemc <reload|likes [player]>");
            return;
        }

        final SubCommand command = this.commandMap.get(commandName);
        if (command.getPermission() == null || sender.hasPermission(command.getPermission())) {
            command.execute(sender, args);
        } else {
            sender.message("&cYou do not have permission to execute this command!");
        }
    }
}
