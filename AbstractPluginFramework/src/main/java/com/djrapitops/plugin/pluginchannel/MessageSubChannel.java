package com.djrapitops.plugin.pluginchannel;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.utilities.player.Fetch;
import com.djrapitops.plugin.utilities.player.IPlayer;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Register this class to IPlugin to listen for subchannel messages for the
 * BungeeCord PluginMessage channel.
 *
 * @author Rsl1122
 * @param <T>
 * @since 2.0.0
 */
public abstract class MessageSubChannel<T extends BukkitPlugin> implements PluginMessageListener {

    private final String channelName;
    private final T plugin;

    public MessageSubChannel(T plugin, String channelName) {
        this.channelName = channelName;
        this.plugin = plugin;
    }

    public abstract void onPluginMessageReceived(IPlayer player, byte[] message);

    protected void sendPluginMessage(String targetArgument, byte[] message) {
        sendPluginMessage(targetArgument, null, message);
    }

    protected void sendPluginMessage(String targetArgument, IPlayer player, byte[] message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channelName);
        out.writeUTF("Forward");
        out.writeUTF(targetArgument);
        out.write(message);
        if (player == null) {
            final Player somePlayer = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            if (somePlayer == null) {
                throw new IllegalStateException("No players online to send message with.");
            }
            player = Fetch.wrap(somePlayer);
        }
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subchannel = in.readUTF();
        if (subchannel.equals(channelName)) {
            onPluginMessageReceived(Fetch.wrap(player), bytes);
        }
    }

    public void register() {
        plugin.registerPluginMessageSubChannel(this);
    }
}
