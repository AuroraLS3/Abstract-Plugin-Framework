package com.djrapitops.plugin.command.defaultcmds;

import com.djrapitops.plugin.command.*;

/**
 * CommandNode that displays Help for the command.
 *
 * Lists each sub {@link CommandNode} of a {@link TreeCmdNode} that the {@link Sender} has permission for.
 *
 * If the {@link Sender} does not have permission, the list is empty.
 *
 * @author Rsl1122
 */
public class HelpCommand extends CommandNode {

    private final TreeCmdNode treeCmdNode;

    public HelpCommand(TreeCmdNode treeCmdNode) {
        super("help|?", "", CommandType.ALL);
        this.treeCmdNode = treeCmdNode;
    }

    @Override
    public void onCommand(Sender sender, String commandLabel, String[] args) {
        ColorScheme colorScheme = treeCmdNode.getColorScheme();
        String cMain = colorScheme.getMainColor();
        String cSec = colorScheme.getSecondaryColor();
        String cTer = colorScheme.getTertiaryColor();
        sender.sendMessage(cTer + "> SubCommands " + cMain + treeCmdNode.getCommandString());
        sender.sendMessage("  ");

        for (CommandNode[] commandNodes : treeCmdNode.getNodeGroups()) {
            for (CommandNode node : commandNodes) {
                if (node == null) {
                    continue;
                }
                sender.sendMessage(cMain + "  " + getNameAndArgs(node) + " " + cTer + node.getShortHelp());
            }
            sender.sendMessage("  ");
        }

        sender.sendMessage("  " + cSec + "Add ? to the end of the command for more help");
        sender.sendMessage(cTer + ">");
    }

    private String getNameAndArgs(CommandNode node) {
        StringBuilder builder = new StringBuilder(node.getName());

        for (String arg : node.getArguments()) {
            builder.append(" ").append(arg);
        }

        return builder.toString();
    }
}