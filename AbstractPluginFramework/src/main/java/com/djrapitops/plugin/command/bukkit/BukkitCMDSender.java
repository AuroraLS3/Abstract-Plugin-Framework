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
 * @author Rsl1122
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
        return (cs instanceof Player) ? SenderType.PLAYER : ((cs instanceof CommandBlock) ? SenderType.CMD_BLOCK : SenderType.CONSOLE);
    }

    @Override
    public CommandSender getSender() {
        return cs;
    }
}
