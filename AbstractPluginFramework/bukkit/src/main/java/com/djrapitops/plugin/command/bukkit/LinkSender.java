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
package com.djrapitops.plugin.command.bukkit;

import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class used to separate ChatComponent classes from BukkitCMDSender.
 *
 * @author Rsl1122
 */
class LinkSender {

    private LinkSender() {
        /* static method class */
    }

    static void send(CommandSender cs, String pretext, String linkMsg, String url) {
        BaseComponent message = new TextComponent(TextComponent.fromLegacyText(pretext));

        BaseComponent[] link =
                new ComponentBuilder(linkMsg)
                        .underlined(true)
                        .event(new ClickEvent(ClickEvent.Action.OPEN_URL, url.replace(" ", "%20")))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(url).create()))
                        .create();

        for (BaseComponent baseComponent : link) {
            message.addExtra(baseComponent);
        }

        ((Player) cs).spigot().sendMessage(message);
    }

}