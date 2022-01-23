package net.savagedev.namemcrewards.common.command;

import net.savagedev.namemcrewards.common.command.sender.Sender;

public interface Command {
    void execute(Sender<?> sender, String[] args);
}
