/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
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
package com.djrapitops.plugin.command.velocity;

import com.djrapitops.plugin.command.Sender;
import com.djrapitops.plugin.command.SenderType;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.format.TextDecoration;

/**
 * Class for wrapping Velocity CommandSource into a {@link Sender}.
 *
 * @author MicleBrick
 */
class VelocityCMDSender implements Sender {

    private final CommandSource cs;

    VelocityCMDSender(CommandSource cs) {
        this.cs = cs;
    }

    @Override
    public String getName() {
        if (cs instanceof Player) {
            return ((Player) cs).getUsername();
        }
        return "Unknown";
    }

    @Override
    public void sendMessage(String string) {
        cs.sendMessage(TextComponent.of(string));
    }

    @Override
    public void sendLink(String pretext, String linkMsg, String url) {
        TextComponent message = TextComponent.of(pretext)
                .append(TextComponent.of(linkMsg)
                        .decoration(TextDecoration.UNDERLINE, TextDecoration.State.TRUE))
                .clickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
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
        return cs instanceof Player ? SenderType.PLAYER : SenderType.CONSOLE;
    }

    @Override
    public CommandSource getSender() {
        return cs;
    }

}