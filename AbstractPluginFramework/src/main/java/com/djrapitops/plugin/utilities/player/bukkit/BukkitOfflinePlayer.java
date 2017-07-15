package com.djrapitops.plugin.utilities.player.bukkit;

import com.djrapitops.plugin.utilities.player.IOfflinePlayer;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

/**
 *
 * @author Rsl1122
 */
public class BukkitOfflinePlayer implements IOfflinePlayer {

    private final OfflinePlayer p;

    public BukkitOfflinePlayer(OfflinePlayer p) {
        this.p = p;
    }

    @Override
    public boolean isBanned() {
        return p.isBanned();
    }

    @Override
    public boolean isWhitelisted() {
        return p.isWhitelisted();
    }

    @Override
    public boolean isOnline() {
        return p.isOnline();
    }

    @Override
    public long getLastPlayed() {
        return p.getLastPlayed();
    }

    @Override
    public boolean isOp() {
        return p.isOp();
    }

    @Override
    public Object getWrappedPlayerClass() {
        return p;
    }

    public static BukkitOfflinePlayer wrap(OfflinePlayer p) {
        return new BukkitOfflinePlayer(p);
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
    public long getFirstPlayed() {
        return p.getFirstPlayed();
    }
    
    @Override
    public boolean hasPlayedBefore() {
        return p.hasPlayedBefore();
    }
}
