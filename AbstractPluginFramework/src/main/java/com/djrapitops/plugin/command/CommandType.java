package com.djrapitops.plugin.command;

/**
 * Enum that defines {@link CommandNode} {@link SenderType} requirements.
 * <p>
 * ALL can be used by any sender.
 * ALL_WITH_ARGS can be used by any sender, but requires arguments
 * PLAYER can only be used as a player
 * PLAYER_OR_ARGS can be used as a player, but requires arguments on console.
 * CONSOLE can be used only on console.
 *
 * @author Rsl1122
 */
public enum CommandType {
    ALL,
    ALL_WITH_ARGS,
    PLAYER_OR_ARGS,
    CONSOLE,
    PLAYER
}
