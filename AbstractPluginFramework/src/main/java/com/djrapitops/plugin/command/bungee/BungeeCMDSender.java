package com.djrapitops.plugin.command.bungee;

import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SenderType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ConnectedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Class that wraps bungee's CommandSender into an ISender.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class BungeeCMDSender implements ISender {

    private final CommandSender cs;

    public BungeeCMDSender(CommandSender cs) {
        this.cs = cs;
    }

    @Override
    public String getName() {
        return cs.getName();
    }

    @Override
    public void sendMessage(String string) {
        ComponentBuilder c = new ComponentBuilder(string);
        cs.sendMessage(c.create());
    }

    @Override
    public void sendLink(String pretext, String linkMsg, String url) {
        TextComponent message = new TextComponent(pretext);
        TextComponent link = new TextComponent(linkMsg);
        link.setUnderlined(true);
        message.addExtra(link);
        cs.sendMessage(message);
    }

    @Override
    public void sendLink(String linkMsg, String url) {
        sendLink("", linkMsg, url);
    }

    @Override
    public boolean hasPermission(String string) {
        return cs.hasPermission(string);
    }

    @Override
    public void sendMessage(String[] strings) {
        for (int i = 1; i < strings.length; i++) {
            sendMessage(strings[i]);
        }
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public SenderType getSenderType() {
        return cs instanceof ConnectedPlayer ? SenderType.PLAYER : (cs instanceof ProxiedPlayer ? SenderType.PROXY_PLAYER : SenderType.CONSOLE);
    }

    @Override
    public CommandSender getSender() {
        return cs;
    }

}
