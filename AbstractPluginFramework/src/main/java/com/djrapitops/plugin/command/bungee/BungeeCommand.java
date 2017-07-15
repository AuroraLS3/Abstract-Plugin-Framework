package com.djrapitops.plugin.command.bungee;

import com.djrapitops.plugin.command.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Class that is used to wrap a SubCommand implementation into executable
 * command by Bungee.
 *
 * @author Rsl1122
 * @since 2.0.0
 * @see SubCommand
 */
public class BungeeCommand extends Command {

    private final SubCommand subCmd;

    public BungeeCommand(SubCommand subCmd) {
        super(subCmd.getFirstName());
        this.subCmd = subCmd;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        subCmd.onCommand(new BungeeCMDSender(sender), "", args);
    }

}
