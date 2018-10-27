package com.djrapitops.plugin.command.defaultcmds;

import com.djrapitops.plugin.command.*;
import com.djrapitops.plugin.utilities.Verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CommandNode that displays Help for the command.
 * <p>
 * Lists each sub {@link CommandNode} of a {@link TreeCmdNode} that the {@link Sender} has permission for.
 * <p>
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
        getHelpMessages(sender).forEach(sender::sendMessage);
    }

    public List<String> getHelpMessages(Sender sender) {
        String helpPermission = getPermission();
        if (!Verify.isEmpty(helpPermission) && !sender.hasPermission(helpPermission)) {
            return Collections.singletonList("§cYou do not have the required permission.");
        }

        ColorScheme colorScheme = treeCmdNode.getColorScheme();
        String cMain = colorScheme.getMainColor();
        String cSec = colorScheme.getSecondaryColor();
        String cTer = colorScheme.getTertiaryColor();

        List<String> messages = new ArrayList<>();
        messages.add(cTer + "> SubCommands " + cMain + treeCmdNode.getCommandString());
        messages.add("  ");

        for (CommandNode[] commandNodes : treeCmdNode.getNodeGroups()) {
            // Used to track if player has any permissions in the group, to separate the groups
            boolean addedCmdInfos = false;
            for (CommandNode node : commandNodes) {
                if (node == null) {
                    continue;
                }
                String permission = node.getPermission();
                if (Verify.isEmpty(permission) || sender.hasPermission(permission)) {
                    messages.add(cMain + "  " + getNameAndArgs(node) + " " + cTer + node.getShortHelp());
                    addedCmdInfos = true;
                }
            }
            if (addedCmdInfos) {
                messages.add("  ");
            }
        }

        messages.add("  " + cSec + "Add ? to the end of the command for more help");
        messages.add(cTer + ">");
        return messages;
    }

    private String getNameAndArgs(CommandNode node) {
        StringBuilder builder = new StringBuilder(node.getName());

        for (String arg : node.getArguments()) {
            builder.append(" ").append(arg);
        }

        return builder.toString();
    }
}