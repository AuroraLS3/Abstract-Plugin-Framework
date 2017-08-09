package com.djrapitops.plugin.command;

/**
 * Abstract class for any command that implements a functionality.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public abstract class SubCommand {

    final private CommandType commandType;
    final private String name;
    final private String permission;
    final private String usage;
    final private String arguments;
    private String[] inDepthHelp;

    public SubCommand(String name, CommandType type, String permission, String usage, String arguments) {
        commandType = type;
        this.name = name;
        this.permission = permission;
        this.usage = usage;
        this.arguments = arguments;
        inDepthHelp = addHelp();
    }

    public SubCommand(String name, CommandType type) {
        this(name, type, "");
    }

    public SubCommand(String name, CommandType type, String permission) {
        this(name, type, permission, "");
    }

    public SubCommand(String name, CommandType type, String permission, String usage) {
        this(name, type, permission, usage, "");
    }

    public abstract boolean onCommand(ISender sender, String commandLabel, String[] args);

    public CommandType getCommandType() {
        return commandType;
    }

    public boolean hasPermission(ISender sender) {
        return permission.isEmpty()
                || !CommandUtils.isPlayer(sender)
                || sender.hasPermission(permission);
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        if (name.contains(",")) {
            return name.split(",")[0];
        }
        return name.trim();
    }

    public String getPermission() {
        return permission;
    }

    public String getUsage() {
        return usage;
    }

    public String getArguments() {
        return arguments;
    }

    public String[] getInDepthHelp() {
        if (inDepthHelp != null) {
            return inDepthHelp;
        } else {
            return new String[]{usage};
        }
    }

    /**
     * Override this method to automatically set the In depth help
     *
     * @return Lines of In Depth help.
     */
    public String[] addHelp() {
        return null;
    }

    public void setInDepthHelp(String[] inDepthHelp) {
        this.inDepthHelp = inDepthHelp;
    }
}
