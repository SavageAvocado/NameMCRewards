package net.savagedev.namemcrewards.common.commands.subcommands;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;

public class ReloadCommand implements SubCommand {
    private final NameMCRewardsPlugin context;

    public ReloadCommand(NameMCRewardsPlugin context) {
        this.context = context;
    }

    @Override
    public void execute(Sender<?> sender, String[] args) {
        this.context.onReload();
        sender.message("&aPlugin reloaded.");
    }

    @Override
    public String getPermission() {
        return null;
    }
}
