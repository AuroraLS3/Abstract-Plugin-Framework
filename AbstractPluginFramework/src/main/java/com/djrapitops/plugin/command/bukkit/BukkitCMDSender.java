package com.djrapitops.plugin.command.bukkit;

import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SenderType;
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
        cs.sendMessage(string);
    }

    @Override
    public void sendMessage(String[] strings) {
        cs.sendMessage(strings);
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
