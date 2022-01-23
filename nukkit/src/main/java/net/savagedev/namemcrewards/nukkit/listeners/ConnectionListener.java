package net.savagedev.namemcrewards.nukkit.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void on(PlayerJoinEvent event) {
        // TODO: If the player was offline at the time they liked the server, handle like event rewards silently.
        //  (Dispatch reward commands without sending messages)
    }
}
