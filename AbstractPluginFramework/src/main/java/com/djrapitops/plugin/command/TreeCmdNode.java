package com.djrapitops.plugin.command;

import com.djrapitops.plugin.command.defaultcmds.HelpCommand;
import com.djrapitops.plugin.settings.ColorScheme;

import java.util.Arrays;

/**
 * Represents a command with sub-commands.
 *
 * @author Rsl1122
 */
public class TreeCmdNode extends CommandNode {

    private final HelpCommand helpCommand;
    private final CommandNode parent;
    private ColorScheme colorScheme;

    private CommandNode[][] nodeGroups;

    public TreeCmdNode(String name, String permission, CommandType commandType, CommandNode parent) {
        super(name, permission, commandType);
        helpCommand = new HelpCommand(this);
        this.parent = parent;
    }

    public String getCommandString() {
        StringBuilder builder = new StringBuilder();
        builder.insert(0, getName());
        CommandNode cmdNode = parent;
        while (cmdNode != null) {
            builder.insert(0, cmdNode.getName() + " ");
            if (cmdNode instanceof TreeCmdNode) {
                cmdNode = ((TreeCmdNode) cmdNode).parent;
            } else {
                cmdNode = null;
            }
        }
        return builder.insert(0, "/").toString();
    }

    public CommandNode[][] getNodeGroups() {
        return nodeGroups;
    }

    public void setNodeGroups(CommandNode[]... nodeGroups) {
        this.nodeGroups = nodeGroups;
    }

    public ColorScheme getColorScheme() {
        return colorScheme != null ? colorScheme : new ColorScheme("§f", "§f", "§f");
    }

    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    @Override
    public void onCommand(ISender sender, String commandLabel, String[] args) {
        try {
            CommandNode command = getCommand(args);

            boolean console = !CommandUtils.isPlayer(sender);
            String permission = command.getPermission();
            if (!"".equals(permission) && !sender.hasPermission(permission)) {
                throw new IllegalStateException("You do not have the required permission.");
            }

            CommandType cType = command.getCommandType();
            if ((cType == CommandType.ALL_WITH_ARGS && args.length < 2)
                    || console && args.length < 2 && cType == CommandType.PLAYER_OR_ARGS) {
                throw new IllegalStateException("Too few arguments! " + Arrays.toString(getArguments()));
            }

            if (console && cType == CommandType.PLAYER) {
                throw new IllegalStateException("Command can only be used as a player.");
            }
            if (args[args.length - 1].equals("?")) {
                sender.sendMessage(command.getInDepthHelp());
                sender.sendMessage("Aliases: " + Arrays.toString(getAliases()));
            }

            String[] realArgs = new String[args.length - 1];
            System.arraycopy(args, 1, realArgs, 0, args.length - 1);

            command.onCommand(sender, commandLabel, realArgs);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cNumber Required: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cBad Argument: " + e.getMessage());
        } catch (IllegalStateException e) {
            sender.sendMessage("§c" + e.getMessage());
        }
    }

    private CommandNode getCommand(String[] args) {
        if (args.length < 1) {
            return helpCommand;
        }

        String name = args[0];
        CommandNode cmd = getMatchingCommand(name);

        return cmd != null ? cmd : helpCommand;
    }

    private CommandNode getMatchingCommand(String name) {
        for (CommandNode[] nodeGroup : nodeGroups) {
            for (CommandNode cmd : nodeGroup) {
                String[] aliases = cmd.getAliases();

                for (String alias : aliases) {
                    if (alias.trim().equalsIgnoreCase(name)) {
                        return cmd;
                    }
                }
            }
        }
        return null;
    }
}