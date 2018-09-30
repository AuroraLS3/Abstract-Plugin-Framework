package com.djrapitops.plugin.command;

/**
 * Represents a command, or sub-command.
 * <p>
 * Various setters should be called in the constructor to allow full use of {@link com.djrapitops.plugin.command.defaultcmds.HelpCommand}.
 *
 * @author Rsl1122
 */
public abstract class CommandNode {

    private final String aliases;
    private final String permission;
    private final CommandType commandType;

    private String[] arguments = new String[0];
    private String[] tabComplete = new String[0];
    private String onHover;
    private String shortHelp = "";
    private String[] inDepthHelp;

    /**
     * Create a new CommandNode.
     * <p>
     * It is recommended to call following methods during construction:
     * {@link CommandNode#setShortHelp(String)}
     * {@link CommandNode#setInDepthHelp(String...)}
     * {@link CommandNode#setArguments(String...)}
     *
     * @param aliases     Aliases of this command, separated with a '|' - eg. one|two|three,
     *                    First alias will be regarded as the name of this command.
     * @param permission  Permission required to use this node.
     * @param commandType {@link CommandType} sender requirements for this command.
     */
    public CommandNode(String aliases, String permission, CommandType commandType) {
        this.aliases = aliases;
        this.permission = permission;
        this.commandType = commandType;
    }

    @Deprecated
    public CommandNode(String name, CommandType type, String permission, String usage, String arguments) {
        this(name, permission, type);
        this.shortHelp = usage;
        this.arguments = arguments.split(" ");
    }

    @Deprecated
    public CommandNode(String name, CommandType type) {
        this(name, "", type);
    }

    @Deprecated
    public CommandNode(String name, CommandType type, String permission) {
        this(name, permission, type);
    }

    @Deprecated
    public CommandNode(String name, CommandType type, String permission, String shortHelp) {
        this(name, permission, type);
        setShortHelp(shortHelp);
    }

    /**
     * Implement this method for the command do do something.
     *
     * @param sender       entity that sent the command.
     * @param commandLabel Label defined by Bukkit or an empty string.
     * @param args         Arguments given to the command, not including the command name.
     */
    public abstract void onCommand(Sender sender, String commandLabel, String[] args);

    /**
     * Retrieve the name of this command.
     *
     * @return First alias of the command.
     */
    public String getName() {
        return getAliases()[0];
    }

    /**
     * Retrieve the permission required to execute this command.
     *
     * @return Permission node.
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Retrieve the CommandType that defines restrictions on executing this command.
     *
     * @return a {@link CommandType}.
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * Retrieve all aliases of this command.
     *
     * @return an array of the aliases.
     */
    public String[] getAliases() {
        return aliases.split("\\|");
    }

    /**
     * Get list of argument help that should be given to this command.
     *
     * @return For example: {@code [optional] <required>}
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * Set list of argument helpers.
     *
     * @param arguments For example: {@code [optional] <required>}
     * @return The current node, for builder like calls.
     */
    protected CommandNode setArguments(String... arguments) {
        this.arguments = arguments;
        return this;
    }

    /**
     * Retrieve the text that should be displayed when hovering over the command in help.
     *
     * @return null or text.
     */
    public String getOnHover() {
        return onHover;
    }

    /**
     * Set the text that should be displayed when hovering over the command in help.
     *
     * @param onHover text or null.
     * @return The current node, for builder like calls.
     */
    protected CommandNode setOnHover(String onHover) {
        this.onHover = onHover;
        return this;
    }

    /**
     * Retrieve short instructions on what the command is used for.
     *
     * @return For example: "Find information about something"
     */
    public String getShortHelp() {
        return shortHelp;
    }

    /**
     * Set short instructions on what the command is used for.
     *
     * @param help For example: "Find information about something"
     * @return The current node, for builder like calls.
     */
    protected CommandNode setShortHelp(String help) {
        this.shortHelp = help;
        return this;
    }

    /**
     * Retrieve in depth instructions on what the command is used for.
     * <p>
     * If in depth help is not defined, short help will be displayed instead.
     *
     * @return For example: "This is used for finding players", "Use with caution"
     */
    public String[] getInDepthHelp() {
        if (inDepthHelp != null) {
            return inDepthHelp;
        } else {
            return new String[]{shortHelp};
        }
    }

    /**
     * Set in depth instructions on what the command is used for.
     *
     * Each String is it's own row when in depth help is displayed.
     *
     * @param inDepthHelp "This is used for finding players", "Use with caution"
     * @return The current node, for builder like calls.
     */
    protected CommandNode setInDepthHelp(String... inDepthHelp) {
        if (inDepthHelp == null || inDepthHelp.length == 0) {
            return this;
        }
        this.inDepthHelp = inDepthHelp;
        return this;
    }
}