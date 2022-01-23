package net.savagedev.namemcrewards.spigot.commands.sender;

import net.savagedev.namemcrewards.common.command.sender.Sender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SpigotSender implements Sender<CommandSender> {
    private final CommandSender sender;

    public SpigotSender(final CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void message(List<String> messages) {
        for (String message : messages) {
            this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    @Override
    public void message(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
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
