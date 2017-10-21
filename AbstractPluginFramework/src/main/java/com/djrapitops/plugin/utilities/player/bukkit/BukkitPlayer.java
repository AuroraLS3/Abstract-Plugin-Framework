package com.djrapitops.plugin.utilities.player.bukkit;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.command.SenderType;
import com.djrapitops.plugin.command.bukkit.BukkitCMDSender;
import com.djrapitops.plugin.utilities.player.Gamemode;
import com.djrapitops.plugin.utilities.player.IPlayer;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * @author Rsl1122
 */
public class BukkitPlayer extends BukkitOfflinePlayer implements IPlayer {

    private final Player p;

    public BukkitPlayer(Player p) {
        super(p);
        this.p = p;
    }

    @Override
    public String getDisplayName() {
        return p.getDisplayName();
    }

    @Override
    public String getPlayerListName() {
        return p.getPlayerListName();
    }

    @Override
    public InetSocketAddress getAddress() {
        return p.getAddress();
    }

    @Override
    public void sendRawMessage(String string) {
        p.sendRawMessage(string);
    }

    @Override
    public void chat(String string) {
        p.chat(string);
    }

    @Override
    public boolean performCommand(String string) {
        return p.performCommand(string);
    }

    @Override
    public boolean isSneaking() {
        return p.isSneaking();
    }

    @Override
    public boolean isSprinting() {
        return p.isSprinting();
    }

    @Override
    public boolean isSleepingIgnored() {
        return p.isSleepingIgnored();
    }

    @Override
    public void giveExp(int i) {
        p.giveExp(i);
    }

    @Override
    public void giveExpLevels(int i) {
        p.giveExpLevels(i);
    }

    @Override
    public float getExp() {
        return p.getExp();
    }

    @Override
    public int getLevel() {
        return p.getLevel();
    }

    @Override
    public int getTotalExperience() {
        return p.getTotalExperience();
    }

    @Override
    public boolean getAllowFlight() {
        return p.getAllowFlight();
    }

    @Override
    public void setAllowFlight(boolean bln) {
        p.setAllowFlight(bln);
    }

    @Override
    public boolean isFlying() {
        return p.isFlying();
    }

    @Override
    public void setFlying(boolean bln) {
        p.setFlying(bln);
    }

    @Override
    public Object getWrappedPlayerClass() {
        return p;
    }

    public static BukkitPlayer wrap(Player p) {
        return new BukkitPlayer(p);
    }

    @Override
    public Gamemode getGamemode() {
        return Gamemode.wrap(p.getGameMode());
    }

    @Override
    public UUID getUuid() {
        return getUniqueId();
    }

    @Override
    public long getRegistered() {
        return getFirstPlayed();
    }

    @Override
    public void sendPluginMessage(IPlugin plugin, String channel, byte[] bytes) {
        try {
            p.sendPluginMessage((BukkitPlugin) plugin, channel, bytes);
        } catch (Throwable e) {
            plugin.getPluginLogger().toLog(this.getClass().getName(), e);
        }
    }

    @Override
    public void sendLink(String pretext, String message, String url) {
        new BukkitCMDSender((Player) this.getWrappedPlayerClass()).sendLink(pretext, message, url);
    }

    @Override
    public void sendMessage(String string) {
        new BukkitCMDSender((Player) this.getWrappedPlayerClass()).sendMessage(string);
    }

    @Override
    public void sendMessage(String[] strings) {
        new BukkitCMDSender((Player) this.getWrappedPlayerClass()).sendMessage(strings);
    }

    @Override
    public boolean hasPermission(String string) {
        return p.hasPermission(string);
    }

    @Override
    public SenderType getSenderType() {
        return SenderType.PLAYER;
    }

    @Override
    public Object getSender() {
        return getWrappedPlayerClass();
    }
}
