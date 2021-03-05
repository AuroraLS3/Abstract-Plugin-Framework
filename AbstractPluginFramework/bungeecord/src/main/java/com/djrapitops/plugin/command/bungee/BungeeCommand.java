/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
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
package com.djrapitops.plugin.command.bungee;

import com.djrapitops.plugin.command.CommandNode;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Objects;

/**
 * {@link CommandNode} wrapper for Bungee implementation.
 *
 * @author AuroraLS3
 * @since 2.0.0
 */
public class BungeeCommand extends Command {

    private final CommandNode commandNode;

    public BungeeCommand(String name, CommandNode commandNode) {
        super(name);
        this.commandNode = commandNode;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BungeeCMDSender iSender = new BungeeCMDSender(sender);
        commandNode.onCommand(iSender, "", args);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BungeeCommand)) return false;
        if (!super.equals(o)) return false;
        BungeeCommand that = (BungeeCommand) o;
        return Objects.equals(commandNode, that.commandNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), commandNode);
    }
}
