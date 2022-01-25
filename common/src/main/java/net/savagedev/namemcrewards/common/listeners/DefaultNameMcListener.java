package net.savagedev.namemcrewards.common.listeners;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import net.savagedev.namemcrewards.common.namemc.event.NameMcApiListener;
import net.savagedev.namemcrewards.common.plugin.NameMCRewardsPlugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DefaultNameMcListener implements NameMcApiListener {
    private final NameMCRewardsPlugin context;

    public DefaultNameMcListener(NameMCRewardsPlugin context) {
        this.context = context;
    }

    @Override
    public void onDislike(UUID uuid) {
        final String username = this.context.getUsername(uuid);
        for (Sender<?> sender : this.context.getOnlineSenders()) {
            sender.message(this.context.getConfiguration().getString("unlike.broadcast").replace("%player%", username));
        }
        final Optional<Sender<?>> sender = this.context.getSender(uuid);
        if (sender.isPresent()) {
            sender.get().message(this.context.getConfiguration().getString("unlike.message").replace("%player%", username));
            this.dispatchCommands(this.context.getConfiguration().getStringList("unlike.commands"), username);
        }
    }

    @Override
    public void onLike(UUID uuid) {
        final String username = this.context.getUsername(uuid);
        for (Sender<?> sender : this.context.getOnlineSenders()) {
            sender.message(this.context.getConfiguration().getString("rewards.broadcast").replace("%player%", username));
        }
        final Optional<Sender<?>> sender = this.context.getSender(uuid);
        if (sender.isPresent()) {
            sender.get().message(this.context.getConfiguration().getString("rewards.message").replace("%player%", username));
            this.dispatchCommands(this.context.getConfiguration().getStringList("rewards.commands"), username);
        }
    }

    private void dispatchCommands(List<String> commands, String username) {
        for (String command : commands) {
            this.context.dispatchCommand(command.replace("%player%", username));
        }
    }
}
