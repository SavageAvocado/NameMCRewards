package net.savagedev.namemcrewards.spigot.listeners;

import net.savagedev.namemcrewards.common.listeners.AbstractConnectionListener;
import net.savagedev.namemcrewards.spigot.NameMCRewardsSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener extends AbstractConnectionListener implements Listener {
    private final NameMCRewardsSpigot context;

    public ConnectionListener(final NameMCRewardsSpigot context) {
        this.context = context;
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        // TODO: If the player was offline at the time they liked the server, handle like event rewards silently.
        //  (Dispatch reward commands without sending messages)
    }
}
