package com.djrapitops.plugin.command.bukkit;

import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.utilities.player.Fetch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that is used to wrap a SubCommand implementation into executable
 * command by Bukkit.
 *
 * @author Rsl1122
 * @since 2.0.0
 * @see SubCommand
 */
public class BukkitCommand implements CommandExecutor {

    private final SubCommand subCmd;

    public BukkitCommand(SubCommand subCmd) {
        this.subCmd = subCmd;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ISender iSender;
        if (sender instanceof Player) {
            iSender = Fetch.wrapBukkit((Player) sender);
        } else {
            iSender = new BukkitCMDSender(sender);
        }
        return subCmd.onCommand(iSender, label, args);
    }
}
