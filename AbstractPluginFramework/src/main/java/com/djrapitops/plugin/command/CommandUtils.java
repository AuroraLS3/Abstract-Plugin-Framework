package com.djrapitops.plugin.command;

/**
 * Utility class for checking various conditions.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class CommandUtils {

    public static boolean isPlayer(Sender sender) {
        return !isConsole(sender) && !isCommandBlock(sender);
    }

    public static boolean isCommandBlock(Sender sender) {
        return sender.getSenderType() == SenderType.CMD_BLOCK;
    }

    public static boolean isConsole(Sender sender) {
        return sender.getSenderType() == SenderType.CONSOLE;
    }

    public static boolean senderHasEntity(Sender sender) {
        return isPlayer(sender);
    }
}
