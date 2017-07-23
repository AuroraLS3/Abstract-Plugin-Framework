package com.djrapitops.plugin.utilities.player;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.command.ISender;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 *
 * @author Rsl1122
 */
public interface IPlayer extends IOfflinePlayer, ISender {

    public String getDisplayName();

    public String getPlayerListName();

    public InetSocketAddress getAddress();

    public void sendRawMessage(String string);

    public void chat(String string);

    public boolean performCommand(String string);

    public boolean isSneaking();

    public boolean isSprinting();

    public boolean isSleepingIgnored();

    public void giveExp(int i);

    public void giveExpLevels(int i);

    public float getExp();

    public int getLevel();

    public int getTotalExperience();

    public boolean getAllowFlight();

    public void setAllowFlight(boolean bln);

    public boolean isFlying();

    public void setFlying(boolean bln);

    public String getName();

    public UUID getUuid();

    public boolean isBanned();

    public boolean isWhitelisted();

    public boolean isOnline();

    public long getLastPlayed();

    public boolean isOp();

    public long getFirstPlayed();

    public abstract Object getWrappedPlayerClass();

    public long getRegistered();

    public Gamemode getGamemode();

    @Deprecated
    public default Gamemode getGameMode() {
        return getGamemode();
    }

    @Deprecated
    public default UUID getUniqueId() {
        return getUuid();
    }

    public void sendPluginMessage(IPlugin plugin, String channel, byte[] bytes);
}
