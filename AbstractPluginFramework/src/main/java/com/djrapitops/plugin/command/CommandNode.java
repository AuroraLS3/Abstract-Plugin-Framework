package com.djrapitops.plugin.command;

/**
 * Represents a command, or sub-command.
 *
 * @author Rsl1122
 */
public abstract class CommandNode {

    private final String aliases;
    private final String permission;
    private final CommandType commandType;

    private String[] arguments;
    private String[] tabComplete;
    private String onHover;
    private String shortHelp = "";
    private String[] inDepthHelp;

    public CommandNode(String aliases, String permission, CommandType commandType) {
        this.aliases = aliases;
        this.permission = permission;
        this.commandType = commandType;
    }

    public abstract void onCommand(ISender sender, String commandLabel, String[] args);

    public String getName() {
        return getAliases()[0];
    }

    public String getPermission() {
        return permission;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getAliases() {
        return aliases.split("\\|");
    }

    public String[] getArguments() {
        return arguments;
    }

    protected CommandNode setArguments(String... arguments) {
        this.arguments = arguments;
        return this;
    }

    public String[] getTabComplete() {
        return tabComplete;
    }

    protected CommandNode setTabComplete(String... tabComplete) {
        this.tabComplete = tabComplete;
        return this;
    }

    public String getOnHover() {
        return onHover;
    }

    protected CommandNode setOnHover(String onHover) {
        this.onHover = onHover;
        return this;
    }

    public String getShortHelp() {
        return shortHelp;
    }

    protected CommandNode setShortHelp(String help) {
        this.shortHelp = help;
        return this;
    }

    public String[] getInDepthHelp() {
        if (inDepthHelp != null) {
            return inDepthHelp;
        } else {
            return new String[]{shortHelp};
        }
    }

    protected CommandNode setInDepthHelp(String... inDepthHelp) {
        this.inDepthHelp = inDepthHelp;
        return this;
    }
}