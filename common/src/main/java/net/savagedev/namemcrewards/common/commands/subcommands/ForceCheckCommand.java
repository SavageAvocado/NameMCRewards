package net.savagedev.namemcrewards.common.commands.subcommands;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.namemc.NameMcApi;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;

public class ForceCheckCommand implements SubCommand {
    private final NameMCRewardsPlugin context;

    public ForceCheckCommand(NameMCRewardsPlugin context) {
        this.context = context;
    }

    @Override
    public void execute(Sender<?> sender, String[] args) {
        sender.message("&aChecking for likes...");
        NameMcApi.forceCheck(this.context)
                .thenAccept(v -> sender.message("&aDone."));
    }

    @Override
    public String getPermission() {
        return null;
    }
}
