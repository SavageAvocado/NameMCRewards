package net.savagedev.namemcrewards.bungee.listeners;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.savagedev.namemcrewards.bungee.NameMCRewardsBungee;

public class ConnectionListener implements Listener {
    private final NameMCRewardsBungee context;

    public ConnectionListener(final NameMCRewardsBungee context) {
        this.context = context;
    }

    @EventHandler
    public void on(LoginEvent e) {
        // TODO: If the player was offline at the time they liked the server, handle like event rewards silently.
        //  (Dispatch reward commands without sending messages)
    }
}
