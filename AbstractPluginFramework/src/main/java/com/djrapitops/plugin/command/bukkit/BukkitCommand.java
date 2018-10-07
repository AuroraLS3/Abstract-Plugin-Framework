package com.djrapitops.plugin.command.bukkit;

import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.Sender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * {@link CommandNode} wrapper for Bukkit implementation.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class BukkitCommand implements CommandExecutor {

    private final CommandNode commandNode;

    public BukkitCommand(CommandNode commandNode) {
        this.commandNode = commandNode;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Sender iSender = new BukkitCMDSender(sender);
        commandNode.onCommand(iSender, label, args);
        return true;
    }
}
