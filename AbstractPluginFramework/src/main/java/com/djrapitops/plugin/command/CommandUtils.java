package com.djrapitops.plugin.command;

/**
 * Utility class for checking various conditions related to {@link Sender}.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class CommandUtils {

    /**
     * Check if the sender is a player.
     *
     * @param sender entity that executed a command.
     * @return true/false
     */
    public static boolean isPlayer(Sender sender) {
        return !isConsole(sender) && !isCommandBlock(sender);
    }

    /**
     * Check if the sender is a command block.
     *
     * @param sender entity that executed a command.
     * @return true/false
     */
    public static boolean isCommandBlock(Sender sender) {
        return sender.getSenderType() == SenderType.CMD_BLOCK;
    }

    /**
     * Check if the sender is a console.
     *
     * @param sender entity that executed a command.
     * @return true/false
     */
    public static boolean isConsole(Sender sender) {
        return sender.getSenderType() == SenderType.CONSOLE;
    }

    /**
     * Check if the sender has in game representation on a server.
     *
     * @param sender entity that executed a command.
     * @return true/false
     */
    public static boolean senderHasEntity(Sender sender) {
        return isPlayer(sender) && sender.getSenderType() != SenderType.PROXY_PLAYER;
    }
}
