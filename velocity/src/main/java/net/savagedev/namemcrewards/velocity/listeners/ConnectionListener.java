package net.savagedev.namemcrewards.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import net.savagedev.namemcrewards.common.listeners.AbstractConnectionListener;

public class ConnectionListener extends AbstractConnectionListener {
    @Subscribe
    public void on(LoginEvent event) {
    }
}
