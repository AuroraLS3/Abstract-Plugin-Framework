package com.djrapitops.plugin.command;

import com.djrapitops.plugin.command.defaultcmds.HelpCommand;
import com.djrapitops.plugin.utilities.ArrayUtil;
import com.djrapitops.plugin.utilities.Verify;

import java.util.Arrays;

/**
 * {@link CommandNode} with sub-commands.
 * <p>
 * Sub commands are defined with {@link TreeCmdNode#setNodeGroups}.
 * Each TreeCmdNode is initialized with a {@link HelpCommand} that provides listing of sub-commands.
 * It is recommended to use {@link TreeCmdNode#setColorScheme(ColorScheme)} to apply chat colors to the help listings.
 * <p>
 * It is possible to set a command to be executed with unknown arguments by using {@link TreeCmdNode#setDefaultCommand(String)}.
 * If this is not set 'help' command will be executed when first argument is not a known sub-command.
 *
 * @author Rsl1122
 * @see ColorScheme for Color instructions.
 */
public class TreeCmdNode extends CommandNode {

    private final HelpCommand helpCommand;
    private final CommandNode parent;
    private ColorScheme colorScheme;

    private String defaultCommand = "help";
    private CommandNode[][] nodeGroups = new CommandNode[][]{};

    /**
     * Create a new TreeCmdNode.
     *
     * @param name        Name of the {@link CommandNode}.
     * @param permission  Permission of the {@link CommandNode}.
     * @param commandType {@link CommandType} of the {@link CommandNode}.
     * @param parent      Parent node of this command, {@code null} if this command is a root {@link CommandNode}.
     */
    public TreeCmdNode(String name, String permission, CommandType commandType, CommandNode parent) {
        super(name, permission, commandType);
        helpCommand = new HelpCommand(this);
        this.parent = parent;
    }

    /**
     * Get the in game representation for calling this command.
     * <p>
     * If a parent is set, its name is appended before this node.
     * Traversal is performed up the command tree.
     *
     * @return For example /command treecmdnode or /treecmdnode
     */
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

    /**
     * Retrieve groupings of {@link CommandNode} sub-commands of this command.
     *
     * @return Array that consists of grouped {@link CommandNode} arrays.
     */
    public CommandNode[][] getNodeGroups() {
        return nodeGroups;
    }

    /**
     * Set {@link CommandNode} sub-commands of this command.
     * <p>
     * Each array is regarded as a sub-command group, that is displayed in its own block on {@link HelpCommand}.
     *
     * @param nodeGroups {@link CommandNode}s grouped by their use, for easier reading of Help.
     */
    public void setNodeGroups(CommandNode[]... nodeGroups) {
        Verify.nullCheck(nodeGroups);
        this.nodeGroups = nodeGroups;
    }

    /**
     * Get the {@link ColorScheme} appropriate for this command.
     *
     * @return a defined color scheme or all white if not defined.
     */
    public ColorScheme getColorScheme() {
        return colorScheme != null ? colorScheme : new ColorScheme("§f", "§f", "§f");
    }

    /**
     * Define the {@link ColorScheme} appropriate for this command.
     *
     * @param colorScheme a color scheme.
     * @see ColorScheme for Color instructions.
     */
    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    /**
     * Set the command to execute when unknown sub-command is attempted to be used.
     * <p>
     * This command will be executed when first given argument is not a known sub-command.
     * If this is not set 'help' will be executed.
     *
     * @param defaultCommand Command to execute, eg. 'find', 'search'
     */
    public void setDefaultCommand(String defaultCommand) {
        Verify.nullCheck(defaultCommand);
        this.defaultCommand = defaultCommand;
    }

    @Override
    public void onCommand(Sender sender, String commandLabel, String[] args) {
        try {
            if (args.length == 0) {
                helpCommand.onCommand(sender, commandLabel, args);
                return;
            }
            CommandNode command = getCommand(args);
            CommandType cmdType = command.getCommandType();

            boolean console = !CommandUtils.isPlayer(sender);
            consoleTypeCheck(cmdType, console);
            checkPermission(sender, this);
            checkPermission(sender, command);

            boolean isDefaultCommandWithoutCommandArg = checkArguments(args, command, console, cmdType);

            // In Depth Help
            if (args[args.length - 1].equals("?")) {
                sendInDepthHelp(sender, args, command);
                return;
            }

            // Argument parsing for non-help default command
            String[] realArgs = args;
            if (!isDefaultCommandWithoutCommandArg) {
                realArgs = ArrayUtil.removeFirstArgument(args);
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

    private void sendInDepthHelp(Sender sender, String[] args, CommandNode command) {
        if (args.length == 1) {
            sender.sendMessage(getInDepthHelp());
            sender.sendMessage("Aliases: " + Arrays.toString(getAliases()));
        } else {
            sender.sendMessage(command.getInDepthHelp());
            sender.sendMessage("Aliases: " + Arrays.toString(command.getAliases()));
        }
    }

    private void consoleTypeCheck(CommandType cmdType, boolean console) throws IllegalAccessException {
        if (console && cmdType == CommandType.PLAYER) {
            throw new IllegalAccessException("Command can only be used as a player.");
        }
    }

    private void checkPermission(Sender sender, CommandNode command) throws IllegalAccessException {
        String permission = command.getPermission();
        if (!Verify.isEmpty(permission) && !sender.hasPermission(permission)) {
            throw new IllegalAccessException("You do not have the required permission.");
        }
    }

    private boolean checkArguments(String[] args, CommandNode command, boolean console, CommandType cmdType) throws IllegalAccessException {
        String commandName = command.getName();
        // Check if default command was parsed without the command argument
        boolean isDefaultCommandWithoutCommandArg = commandName.equals(defaultCommand)
                && args.length <= 1 && !args[0].equals(commandName);
        // Check if there are enough arguments
        if (!isDefaultCommandWithoutCommandArg
                && ((cmdType == CommandType.ALL_WITH_ARGS && args.length < 2)
                || console && args.length < 2 && cmdType == CommandType.PLAYER_OR_ARGS)) {
            throw new IllegalAccessException("Too few arguments! " + Arrays.toString(getArguments()));
        }
        return isDefaultCommandWithoutCommandArg;
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
            return getCommand(ArrayUtil.merge(new String[]{defaultCommand}, args));
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

    public HelpCommand getHelpCommand() {
        return helpCommand;
    }
}