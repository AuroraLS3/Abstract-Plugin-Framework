package com.djrapitops.plugin.command;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.settings.DefaultMessages;
import com.djrapitops.plugin.utilities.FormatUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

/**
 * Abstract class for any command that has multiple subcommands.
 *
 * @param <T> IPlugin implementation type.
 * @author Rsl1122
 * @since 2.0.0
 */
public abstract class TreeCommand<T extends IPlugin> extends SubCommand {

    protected final T plugin;

    protected final List<SubCommand> commands;
    private String defaultCommand = "help";
    private String helpPrefix = "";
    private ColorScheme cs = new ColorScheme("§7", "§f", "§8");

    /**
     * Class Constructor.
     *
     * @param plugin     Current instance
     * @param values
     * @param helpPrefix
     */
    public TreeCommand(T plugin, SubCommand values, String helpPrefix) {
        this(plugin, values.getName(), values.getCommandType(), values.getPermission(), values.getUsage(), helpPrefix);
    }

    public TreeCommand(T plugin, String name, CommandType type, String permission, String usage, String helpPrefix) {
        super(name, type, permission, usage, "");
        this.plugin = plugin;
        this.helpPrefix = helpPrefix;
        commands = new ArrayList<>();
        add(new HelpCommand(plugin, this));
        addCommands();
    }

    public void setDefaultCommand(String defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    public void add(SubCommand... commands) {
        for (SubCommand command : commands) {
            this.commands.add(command);
            StaticHolder.saveInstance(command.getClass(), plugin.getClass());
        }
    }

    public abstract void addCommands();

    /**
     * Used to get the list of all subcommands.
     *
     * @return Initialized SubCommands
     */
    public List<SubCommand> getCommands() {
        return this.commands;
    }

    /**
     * Checks SubCommands for matching aliases.
     *
     * @param name SubCommand in text form that might match alias.
     * @return SubCommand, null if no match.
     */
    public SubCommand getCommand(String name) {
        for (SubCommand command : commands) {
            String[] aliases = command.getName().split(",");

            for (String alias : aliases) {
                if (alias.trim().equalsIgnoreCase(name)) {
                    return command;
                }
            }
        }
        return null;
    }

    private void sendDefaultCommand(ISender sender, String commandLabel, String[] args) {
        String command = defaultCommand;
        if (args.length < 1) {
            command = "help";
        }
        onCommand(sender, commandLabel, FormatUtils.mergeArrays(new String[]{command}, args));
    }

    /**
     * Checks if Sender has rights to run the command and executes matching
     * subcommand.
     *
     * @param sender       source of the command.
     * @param commandLabel label.
     * @param args         arguments of the command
     * @return true
     */
    @Override
    public boolean onCommand(ISender sender, String commandLabel, String[] args) {
        if (args.length < 1) {
            sendDefaultCommand(sender, commandLabel, args);
            return true;
        }

        SubCommand command = getCommand(args[0]);

        if (command == null) {
            sendDefaultCommand(sender, commandLabel, args);
            return true;
        }

        boolean console = !CommandUtils.isPlayer(sender);

        if (!command.hasPermission(sender)) {
            sender.sendMessage(ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "] " + DefaultMessages.COMMAND_NO_PERMISSION);
            return true;
        }

        CommandType cType = command.getCommandType();
        if (cType == CommandType.ALL_WITH_ARGS || console && args.length < 2 && cType == CommandType.PLAYER_OR_ARGS) {
            sender.sendMessage(ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "] " + DefaultMessages.COMMAND_REQUIRES_ARGUMENTS.parse("1") + " " + command.getArguments());
            return true;
        }

        if (console && cType == CommandType.PLAYER) {
            sender.sendMessage(ChatColor.RED + "[" + plugin.getClass().getSimpleName() + "] " + DefaultMessages.COMMAND_SENDER_NOT_PLAYER);

            return true;
        }

        if (args[args.length - 1].equals("?")) {
            sender.sendMessage(command.getInDepthHelp());
            return true;
        }

        String[] realArgs = new String[args.length - 1];
        System.arraycopy(args, 1, realArgs, 0, args.length - 1);

        command.onCommand(sender, commandLabel, realArgs);
        return true;
    }

    public String getHelpCmd() {
        return helpPrefix;
    }

    public ColorScheme getColorScheme() {
        return cs;
    }

    public TreeCommand<T> setColorScheme(ColorScheme cs) {
        this.cs = cs;
        return this;
    }
}

class HelpCommand<T extends IPlugin> extends SubCommand {

    private final TreeCommand<T> command;

    /**
     * Subcommand Constructor.
     *
     * @param plugin  Current instance of Plan
     * @param command Current instance of PlanCommand
     */
    public HelpCommand(T plugin, TreeCommand<T> command) {
        super("help,?", CommandType.ALL, command.getPermission(), "Show help for the command.");
        this.command = command;
    }

    @Override
    public boolean onCommand(ISender sender, String commandLabel, String[] args) {
        boolean isConsole = !CommandUtils.isPlayer(sender);
        ColorScheme cs = command.getColorScheme();
        String oColor = cs.getMainColor();
        String sColor = cs.getSecondaryColor();
        String tColor = cs.getTertiaryColor();

        sender.sendMessage(tColor + DefaultMessages.ARROWS_RIGHT.parse() + oColor + " " + StringUtils.capitalize(command.getFirstName()) + " Help");
        List<SubCommand> commands = this.command.getCommands();

        commands.stream()
                .filter(cmd -> !cmd.getName().equalsIgnoreCase(getName()))
                .filter(cmd -> cmd.hasPermission(sender))
                .filter(cmd -> !(isConsole && cmd.getCommandType() == CommandType.PLAYER))
                .map(cmd -> tColor + " " + DefaultMessages.BALL.toString() + oColor + " /" + command.getHelpCmd() + " " + cmd.getFirstName() + " " + cmd.getArguments() + tColor + " - " + cmd.getUsage())
                .forEach(sender::sendMessage);
        sender.sendMessage(sColor + " Add ? to the end of the command for more help");
        sender.sendMessage(tColor + DefaultMessages.ARROWS_RIGHT.parse());
        return true;
    }
}
