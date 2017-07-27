package com.djrapitops.plugin.command.bukkit;

import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SenderType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that wraps bukkit's CommandSender to an ISender.
 *
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
            cs.sendMessage(string);
        }
    }

    @Override
    public void sendMessage(String[] strings) {
        cs.sendMessage(strings);
    }

    @Override
    public void sendLink(String pretext, String linkMsg, String url) {
        TextComponent message = new TextComponent(pretext);
        TextComponent link = new TextComponent(linkMsg);
        link.setUnderlined(true);
        message.addExtra(link);
        cs.spigot().sendMessage(message);
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
