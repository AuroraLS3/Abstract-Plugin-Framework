package com.djrapitops.plugin.utilities.player.bungee;

import com.djrapitops.plugin.command.SenderType;
import com.djrapitops.plugin.command.bungee.BungeeCMDSender;
import com.djrapitops.plugin.utilities.player.Gamemode;
import com.djrapitops.plugin.utilities.player.IPlayer;

import java.net.InetSocketAddress;
import java.util.UUID;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ConnectedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Rsl1122
 */
public class BungeeConnectedPlayer extends BungeeProxiedPlayer implements IPlayer {

    public static IPlayer wrap(ConnectedPlayer p) {
        return new BungeeConnectedPlayer(p);
    }

    private final ConnectedPlayer p;

    public BungeeConnectedPlayer(ConnectedPlayer p) {
        super(p);
        this.p = p;
    }

    @Override
    public String getDisplayName() {
        return p.getDisplayName();
    }

    @Override
    public String getPlayerListName() {
        return getDisplayName();
    }

    @Override
    public InetSocketAddress getAddress() {
        return p.getAddress();
    }

    @Override
    public void sendRawMessage(String string) {
        p.sendMessage(new TextComponent(string));
    }

    @Override
    public void chat(String string) {
        p.chat(string);
    }

    @Override
    public boolean performCommand(String string) {
        chat('/' + string);
        return true;
    }

    @Override
    public boolean isSneaking() {
        return false;
    }

    @Override
    public boolean isSprinting() {
        return false;
    }

    @Override
    public boolean isSleepingIgnored() {
        return false;
    }

    @Override
    public void giveExp(int i) {
    }

    @Override
    public void giveExpLevels(int i) {
    }

    @Override
    public float getExp() {
        return -1;
    }

    @Override
    public int getLevel() {
        return -1;
    }

    @Override
    public int getTotalExperience() {
        return -1;
    }

    @Override
    public boolean getAllowFlight() {
        return false;
    }

    @Override
    public void setAllowFlight(boolean bln) {
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public void setFlying(boolean bln) {
    }

    @Override
    public Gamemode getGamemode() {
        return Gamemode.SURVIVAL;
    }

    @Override
    public void sendPluginMessage(IPlugin plugin, String channel, byte[] bytes) {
        // TODO
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
    public void sendMessage(String string) {
        ComponentBuilder c = new ComponentBuilder(string);
        p.sendMessage(c.create());
    }

    @Override
    public boolean hasPermission(String string) {
        return p.hasPermission(string);
    }

    @Override
    public void sendMessage(String[] strings) {
        for (int i = 1; i < strings.length; i++) {
            sendMessage(strings[i]);
        }
    }

    @Override
    public void sendLink(String pretext, String linkMsg, String url) {
        new BungeeCMDSender((ConnectedPlayer) this.getWrappedPlayerClass()).sendLink(pretext, linkMsg, url);
    }

    @Override
    public void sendLink(String message, String url) {
        sendLink("", message, url);
    }

    @Override
    public SenderType getSenderType() {
        return p instanceof ConnectedPlayer ? SenderType.PLAYER : (p instanceof ProxiedPlayer ? SenderType.PROXY_PLAYER : SenderType.CONSOLE);
    }

    @Override
    public Object getSender() {
        return getWrappedPlayerClass();
    }
}
