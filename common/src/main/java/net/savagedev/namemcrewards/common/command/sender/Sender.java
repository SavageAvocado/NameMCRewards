package net.savagedev.namemcrewards.common.command.sender;

import java.util.List;

public interface Sender<T> {
    void message(List<String> messages);

    void message(String message);

    boolean hasPermission(String permission);

    boolean isPlayer();

    T getBase();
}
