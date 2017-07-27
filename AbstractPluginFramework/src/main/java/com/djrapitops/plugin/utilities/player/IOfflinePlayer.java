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

    String getName();

    default UUID getUuid() {
        return getUniqueId();
    }

    UUID getUniqueId();

    boolean isBanned();

    boolean isWhitelisted();

    boolean isOnline();

    long getLastPlayed();

    boolean isOp();

    long getFirstPlayed();

    Object getWrappedPlayerClass();

    default long getRegistered() {
        return getFirstPlayed();
    }
    
    boolean hasPlayedBefore();
}
