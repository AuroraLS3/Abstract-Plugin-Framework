package com.djrapitops.plugin.command.velocity;

import com.djrapitops.plugin.command.CommandNode;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;

/**
 * {@link CommandNode} wrapper for Velocity compatibility.
 *
 * @author MicleBrick
 */
public class VelocityCommand implements Command {

    private final CommandNode commandNode;

    public VelocityCommand(CommandNode commandNode) {
        this.commandNode = commandNode;
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        VelocityCMDSender cmdSender = new VelocityCMDSender(sender);
        commandNode.onCommand(cmdSender, "", args);
    }
}