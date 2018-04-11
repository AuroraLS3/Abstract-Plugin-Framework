package com.djrapitops.plugin.command.bungee;

import com.djrapitops.plugin.command.CommandNode;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Class that is used to wrap a CommandNode implementation into executable
 * command by Bungee.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class BungeeCommand extends Command {

    private final CommandNode commandNode;

    public BungeeCommand(String name, CommandNode commandNode) {
        super(name);
        this.commandNode = commandNode;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BungeeCMDSender iSender = new BungeeCMDSender(sender);
        commandNode.onCommand(iSender, "", args);
    }
}
