package net.savagedev.namemcrewards.common.commands.subcommands;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;

public class LikesCommand implements SubCommand {
    private final NameMCRewardsPlugin context;

    public LikesCommand(NameMCRewardsPlugin context) {
        this.context = context;
    }

    @Override
    public void execute(Sender<?> sender, String[] args) {
        this.context.getStorage().getLikes().thenAccept(likes -> {
            if (args.length == 1) {
                sender.message("&aThis server has " + likes.size() + " likes on NameMC.");
                return;
            }

            final String player = args[1];

            sender.message(likes.contains(this.context.getUniqueId(player)) ? "&a" + player + " likes the server on NameMC." : "&c" + player + " does not like the server on NameMC.");
        });
    }

    @Override
    public String getPermission() {
        return null;
    }
}
