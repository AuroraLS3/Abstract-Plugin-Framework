/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.djrapitops.plugin.command;

/**
 * Utility class for checking various conditions related to {@link Sender}.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class CommandUtils {

    private CommandUtils() {
        /* static method class */
    }

    /**
     * Check if the sender is a player.
     *
     * @param sender entity that executed a command.
     * @return true/false
     */
    public static boolean isPlayer(Sender sender) {
        SenderType type = sender.getSenderType();
        return type == SenderType.PLAYER || type == SenderType.PROXY_PLAYER;
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
        return sender.getSenderType() == SenderType.PLAYER;
    }
}
