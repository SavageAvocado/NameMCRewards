package net.savagedev.namemcrewards.spigot.listeners;

import net.savagedev.namemcrewards.common.namemc.NameMcApi;
import net.savagedev.namemcrewards.common.utils.io.Constants;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Locale;

public class MessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals(Constants.MESSAGING_CHANNEL)) {
            return;
        }

        try (final ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes)) {
            try (final DataInputStream dataStream = new DataInputStream(byteStream)) {
                final String action = dataStream.readUTF();

                NameMcApi.forceEvent(NameMcApi.EventType.valueOf(action.toUpperCase(Locale.ROOT)), player.getUniqueId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
