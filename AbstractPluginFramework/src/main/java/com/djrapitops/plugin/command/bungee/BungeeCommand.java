package com.djrapitops.plugin.command.bungee;

import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Class that is used to wrap a SubCommand implementation into executable
 * command by Bungee.
 *
 * @author Rsl1122
 * @see SubCommand
 * @since 2.0.0
 */
public class BungeeCommand extends Command {

    private SubCommand subCmd;
    private CommandNode commandNode;

    @Deprecated
    public BungeeCommand(String name, SubCommand subCmd) {
        super(name);
        this.subCmd = subCmd;
    }

    public BungeeCommand(String name, CommandNode commandNode) {
        super(name);
        this.commandNode = commandNode;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BungeeCMDSender iSender = new BungeeCMDSender(sender);
        if (commandNode != null) {
            commandNode.onCommand(iSender, "", args);
        }
        subCmd.onCommand(iSender, "", args);
    }

}
