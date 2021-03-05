/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.djrapitops.plugin.command.bungee;

import com.djrapitops.plugin.command.Sender;
import com.djrapitops.plugin.command.SenderType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ConnectedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Class for wrapping Bungee CommandSender into a {@link Sender}.
 *
 * @author AuroraLS3
 * @since 2.0.0
 */
class BungeeCMDSender implements Sender {

    private final CommandSender cs;

    BungeeCMDSender(CommandSender cs) {
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
    public void sendLink(String pretext, String linkText, String url) {
        TextComponent message = new TextComponent(pretext);
        TextComponent link = new TextComponent(linkText);
        link.setUnderlined(true);
        message.addExtra(link);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        cs.sendMessage(message);
    }

    @Override
    public void sendLink(String linkText, String url) {
        sendLink("", linkText, url);
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
        if (cs instanceof ConnectedPlayer) {
            return SenderType.PLAYER;
        } else if (cs instanceof ProxiedPlayer) {
            return SenderType.PROXY_PLAYER;
        } else {
            return SenderType.CONSOLE;
        }
    }

    @Override
    public CommandSender getSender() {
        return cs;
    }

}
