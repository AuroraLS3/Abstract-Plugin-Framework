package com.djrapitops.plugin.command;

/**
 * Utility class for checking various conditions.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class CommandUtils {

    public static boolean isPlayer(ISender sender) {
        return !isConsole(sender) && !isCommandBlock(sender);
    }

    public static boolean isCommandBlock(ISender sender) {
        return sender.getSenderType() == SenderType.CMD_BLOCK;
    }

    public static boolean isConsole(ISender sender) {
        return sender.getSenderType() == SenderType.CONSOLE;
    }

    public static boolean senderHasEntity(ISender sender) {
        return isPlayer(sender);
    }
}
