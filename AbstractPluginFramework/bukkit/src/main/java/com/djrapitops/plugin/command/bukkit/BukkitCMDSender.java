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
package com.djrapitops.plugin.command.bukkit;

import com.djrapitops.plugin.command.CommandUtils;
import com.djrapitops.plugin.command.Sender;
import com.djrapitops.plugin.command.SenderType;
import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class for wrapping Bukkit CommandSender into a {@link Sender}.
 *
 * @author AuroraLS3
 * @since 2.0.0
 */
class BukkitCMDSender implements Sender {

    private final CommandSender cs;

    BukkitCMDSender(CommandSender cs) {
        this.cs = cs;
    }

    @Override
    public void sendMessage(String string) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(string);
            return;
        }
        final int length = string.length();
        if (length > 60) {
            int i = 59;
            while (i < length && string.charAt(i) != ' ') {
                i++;
            }
            String shortened = string.substring(0, i);
            String lastCols = ChatColor.getLastColors(string);
            cs.sendMessage(shortened);
            String leftover = lastCols + string.substring(i);
            sendMessage(leftover);
        } else {
            if (ChatColor.stripColor(string).isEmpty()) {
                return;
            }
            cs.sendMessage(string);
        }
    }

    @Override
    public void sendMessage(String[] strings) {
        cs.sendMessage(strings);
    }

    @Override
    public void sendLink(String pretext, String linkText, String url) {
        try {
            if (CommandUtils.isPlayer(this)) {
                LinkSender.send(cs, pretext, linkText, url);
                return;
            }
        } catch (NoClassDefFoundError ignore) {
            /* Using CraftBukkit */
        }
        cs.sendMessage(url);
    }

    @Override
    public void sendLink(String linkText, String url) {
        sendLink("", linkText, url);
    }

    @Override
    public String getName() {
        return cs.getName();
    }

    @Override
    public boolean hasPermission(String string) {
        return cs.hasPermission(string);
    }

    @Override
    public boolean isOp() {
        return cs.isOp();
    }

    @Override
    public SenderType getSenderType() {
        if (cs instanceof Player) {
            return SenderType.PLAYER;
        } else if (cs instanceof CommandBlock) {
            return SenderType.CMD_BLOCK;
        } else {
            return SenderType.CONSOLE;
        }
    }

    @Override
    public CommandSender getSender() {
        return cs;
    }
}
