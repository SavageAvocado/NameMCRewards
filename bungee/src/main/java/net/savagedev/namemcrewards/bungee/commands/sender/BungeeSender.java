package net.savagedev.namemcrewards.bungee.commands.sender;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.savagedev.namemcrewards.common.command.sender.Sender;

import java.util.List;

public class BungeeSender implements Sender<CommandSender> {
    private final CommandSender sender;

    public BungeeSender(final CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void message(List<String> messages) {
        for (String message : messages) {
            this.sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
        }
    }

    @Override
    public void message(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        this.sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.sender.hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return this.sender instanceof ProxiedPlayer;
    }

    @Override
    public CommandSender getBase() {
        return this.sender;
    }
}
