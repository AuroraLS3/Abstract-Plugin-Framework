package com.djrapitops.plugin.utilities.player.bungee;

import com.djrapitops.plugin.utilities.player.IOfflinePlayer;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 *
 * @author Rsl1122
 */
public class BungeeProxiedPlayer implements IOfflinePlayer {

    public static IOfflinePlayer wrap(ProxiedPlayer p) {
        return new BungeeProxiedPlayer(p);
    }

    private final ProxiedPlayer p;

    public BungeeProxiedPlayer(ProxiedPlayer p) {
        this.p = p;
    }

    @Override
    public String getName() {
        return p.getName();
    }

    @Override
    public UUID getUniqueId() {
        return p.getUniqueId();
    }

    @Override
    public boolean isBanned() {
        return false;
    }

    @Override
    public boolean isWhitelisted() {
        return true;
    }

    @Override
    public boolean isOnline() {
        return p.isConnected();
    }

    @Override
    public long getLastPlayed() {
        if (isOnline()) {
            return System.currentTimeMillis();
        } else {
            return -1;
        }
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public long getFirstPlayed() {
        return getLastPlayed();
    }

    @Override
    public Object getWrappedPlayerClass() {
        return p;
    }

    @Override
    public boolean hasPlayedBefore() {
        return p.isConnected();
    }

}
