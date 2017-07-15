package com.djrapitops.plugin.command;

/**
 * Enum containing different ISender types.
 *
 * PLAYER: Bukkit's Player, Bungee's ConnectedPlayer.
 * PROXY_PLAYER: Player connected to Bungee but not in lobby.
 * CONSOLE: Not a player or a command block.
 * CMD_BLOCK: Bukkit's CommandBlock.
 * 
 * @author Rsl1122
 * @since 2.0.0
 */
public enum SenderType {
    PLAYER, PROXY_PLAYER, CONSOLE, CMD_BLOCK;
}
