package com.djrapitops.plugin.utilities.player;

import com.djrapitops.plugin.command.ISender;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 *
 * @author Rsl1122
 */
public interface IPlayer extends IOfflinePlayer, ISender {

    String getDisplayName();

    String getPlayerListName();

    InetSocketAddress getAddress();

    void sendRawMessage(String string);

    void chat(String string);

    boolean performCommand(String string);

    boolean isSneaking();

    boolean isSprinting();

    boolean isSleepingIgnored();

    void giveExp(int i);

    void giveExpLevels(int i);

    float getExp();

    int getLevel();

    int getTotalExperience();

    boolean getAllowFlight();

    void setAllowFlight(boolean bln);

    boolean isFlying();

    void setFlying(boolean bln);

    String getName();

    UUID getUuid();

    boolean isBanned();

    boolean isWhitelisted();

    boolean isOnline();

    long getLastPlayed();

    boolean isOp();

    long getFirstPlayed();

    Object getWrappedPlayerClass();

    long getRegistered();

    Gamemode getGamemode();

    @Deprecated
    default Gamemode getGameMode() {
        return getGamemode();
    }

    @Deprecated
    default UUID getUniqueId() {
        return getUuid();
    }

    void sendPluginMessage(IPlugin plugin, String channel, byte[] bytes);
}
