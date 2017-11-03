package com.djrapitops.plugin.command;

/**
 * Enum that contains command requirement types.
 * <p>
 * ALL can be used always, PLAYER can only be used as a player,
 * CONSOLE_WITH_ARGUMENTS can be used always, except with arguments on console.
 *
 * @author Rsl1122
 */
public enum CommandType {
    ALL,
    ALL_WITH_ARGS,
    PLAYER_OR_ARGS,
    CONSOLE,
    PLAYER;
}
