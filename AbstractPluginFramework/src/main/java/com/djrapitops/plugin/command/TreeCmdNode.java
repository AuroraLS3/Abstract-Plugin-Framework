package com.djrapitops.plugin.command;

import com.djrapitops.plugin.command.defaultcmds.HelpCommand;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.Verify;

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

    private String defaultCommand = "help";
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
        Verify.nullCheck(nodeGroups);
        this.nodeGroups = nodeGroups;
    }

    public ColorScheme getColorScheme() {
        return colorScheme != null ? colorScheme : new ColorScheme("§f", "§f", "§f");
    }

    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    public void setDefaultCommand(String defaultCommand) {
        Verify.nullCheck(defaultCommand);
        this.defaultCommand = defaultCommand;
    }

    @Override
    public void onCommand(ISender sender, String commandLabel, String[] args) {
        try {
            if (args.length == 0) {
                helpCommand.onCommand(sender, commandLabel, args);
                return;
            }
            CommandNode command = getCommand(args);

            boolean console = !CommandUtils.isPlayer(sender);
            String permission = command.getPermission();
            if (!"".equals(permission) && !sender.hasPermission(permission)) {
                throw new IllegalAccessException("You do not have the required permission.");
            }

            boolean isDefaultCommandWithArgs = command.getName().equals(defaultCommand) && args.length <= 1;

            CommandType cType = command.getCommandType();
            if (!isDefaultCommandWithArgs
                    && ((cType == CommandType.ALL_WITH_ARGS && args.length < 2)
                    || console && args.length < 2 && cType == CommandType.PLAYER_OR_ARGS)) {
                throw new IllegalAccessException("Too few arguments! " + Arrays.toString(getArguments()));
            }

            if (console && cType == CommandType.PLAYER) {
                throw new IllegalAccessException("Command can only be used as a player.");
            }
            if (args[args.length - 1].equals("?")) {
                if (args.length == 1) {
                    sender.sendMessage(getInDepthHelp());
                    sender.sendMessage("Aliases: " + Arrays.toString(getAliases()));
                } else {
                    sender.sendMessage(command.getInDepthHelp());
                    sender.sendMessage("Aliases: " + Arrays.toString(command.getAliases()));
                }
                return;
            }

            String[] realArgs = args;
            if (!isDefaultCommandWithArgs) {
                realArgs = new String[args.length - 1];
                System.arraycopy(args, 1, realArgs, 0, args.length - 1);
            }

            command.onCommand(sender, commandLabel, realArgs);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cNumber Required: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cBad Argument: " + e.getMessage());
        } catch (IllegalAccessException e) {
            sender.sendMessage("§c" + e.getMessage());
        }
    }

    private CommandNode getCommand(String[] args) {
        if (args.length < 1) {
            return helpCommand;
        }

        String name = args[0];
        CommandNode cmd = getMatchingCommand(name);
        if (cmd != null) {
            return cmd;
        }
        if (!defaultCommand.equals(args[0])) {
            return getCommand(FormatUtils.mergeArrays(new String[]{defaultCommand}, args));
        }
        return helpCommand;
    }

    private CommandNode getMatchingCommand(String name) {
        for (CommandNode[] nodeGroup : nodeGroups) {
            for (CommandNode node : nodeGroup) {
                if (node == null) {
                    continue;
                }
                String[] aliases = node.getAliases();

                for (String alias : aliases) {
                    if (alias.trim().equalsIgnoreCase(name)) {
                        return node;
                    }
                }
            }
        }
        return null;
    }
}