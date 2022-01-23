package net.savagedev.namemcrewards.nukkit.command.sender;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.savagedev.namemcrewards.common.command.sender.Sender;

import java.util.List;

public class NukkitSender implements Sender<CommandSender> {
    private final CommandSender sender;

    public NukkitSender(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void message(List<String> messages) {
        for (String message : messages) {
            this.sender.sendMessage(TextFormat.colorize('&', message));
        }
    }

    @Override
    public void message(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        this.sender.sendMessage(TextFormat.colorize('&', message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.sender.hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    @Override
    public CommandSender getBase() {
        return this.sender;
    }
}
