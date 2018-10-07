/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
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