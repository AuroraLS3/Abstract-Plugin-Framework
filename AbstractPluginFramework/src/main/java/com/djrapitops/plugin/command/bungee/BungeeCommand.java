package com.djrapitops.plugin.command.bungee;

import com.djrapitops.plugin.command.CommandNode;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Objects;

/**
 * {@link CommandNode} wrapper for Bungee implementation.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BungeeCommand)) return false;
        if (!super.equals(o)) return false;
        BungeeCommand that = (BungeeCommand) o;
        return Objects.equals(commandNode, that.commandNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), commandNode);
    }
}
