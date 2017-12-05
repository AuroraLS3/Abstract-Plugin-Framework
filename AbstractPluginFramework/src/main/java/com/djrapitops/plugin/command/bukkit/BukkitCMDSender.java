package com.djrapitops.plugin.command.bukkit;

import com.djrapitops.plugin.command.CommandUtils;
import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SenderType;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that wraps bukkit's CommandSender to an ISender.
 * <p>
 * Represents a command sender of Bukkit server.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class BukkitCMDSender implements ISender {

    private final CommandSender cs;

    public BukkitCMDSender(CommandSender cs) {
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
    public void sendLink(String pretext, String linkMsg, String url) {
        if (CommandUtils.isPlayer(this)) {
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
        } else {
            cs.sendMessage(url);
        }
    }

    @Override
    public void sendLink(String message, String url) {
        sendLink("", message, url);
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
        return (cs instanceof Player) ? SenderType.PLAYER : ((cs instanceof CommandBlock) ? SenderType.CMD_BLOCK : SenderType.CONSOLE);
    }

    @Override
    public CommandSender getSender() {
        return cs;
    }
}
