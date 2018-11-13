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
package com.djrapitops.plugin.command.sponge;

import com.djrapitops.plugin.command.Sender;
import com.djrapitops.plugin.command.SenderType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.source.RconSource;
import org.spongepowered.api.command.source.SignSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextStyles;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class for wrapping Sponge CommandSource into a {@link Sender}.
 *
 * @author Rsl1122
 */
class SpongeCMDSender implements Sender {

    private final CommandSource commandSource;

    SpongeCMDSender(CommandSource commandSource) {
        this.commandSource = commandSource;
    }

    @Override
    public void sendMessage(String string) {
        commandSource.sendMessage(Text.of(string));
    }

    @Override
    public void sendMessage(String[] strings) {
        for (String string : strings) {
            sendMessage(string);
        }
    }

    @Override
    public void sendLink(String pretext, String linkText, String url) {
        try {
            commandSource.sendMessage(
                    Text.join(
                            Text.of(pretext),
                            Text.builder(linkText).style(TextStyles.UNDERLINE).onClick(TextActions.openUrl(new URL(url))).build()
                    ));
        } catch (MalformedURLException e) {
            sendLink(linkText, url, url);
        }
    }

    @Override
    public void sendLink(String linkText, String url) {
        try {
            commandSource.sendMessage(
                    Text.builder(linkText).onClick(TextActions.openUrl(new URL(url))).build()
            );
        } catch (MalformedURLException e) {
            sendLink(linkText, url, url);
        }
    }

    @Override
    public String getName() {
        return commandSource.getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSource.hasPermission(permission);
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public SenderType getSenderType() {
        if (commandSource instanceof CommandBlockSource || commandSource instanceof SignSource) {
            return SenderType.CMD_BLOCK;
        } else if (commandSource instanceof ConsoleSource) {
            return SenderType.CONSOLE;
        } else if (commandSource instanceof Player) {
            return SenderType.PLAYER;
        } else if (commandSource instanceof RconSource) {
            return SenderType.PROXY_PLAYER;
        }
        return SenderType.CONSOLE;
    }

    @Override
    public CommandSource getSender() {
        return commandSource;
    }
}