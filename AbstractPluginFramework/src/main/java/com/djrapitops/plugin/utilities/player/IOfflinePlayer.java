/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities.player;

import java.util.UUID;

/**
 *
 * @author Rsl1122
 */
public interface IOfflinePlayer {

    public String getName();

    public default UUID getUuid() {
        return getUniqueId();
    }

    public UUID getUniqueId();

    public abstract boolean isBanned();

    public abstract boolean isWhitelisted();

    public abstract boolean isOnline();

    public abstract long getLastPlayed();

    public abstract boolean isOp();

    public long getFirstPlayed();

    public abstract Object getWrappedPlayerClass();

    public default long getRegistered() {
        return getFirstPlayed();
    }
    
    public boolean hasPlayedBefore();
}
