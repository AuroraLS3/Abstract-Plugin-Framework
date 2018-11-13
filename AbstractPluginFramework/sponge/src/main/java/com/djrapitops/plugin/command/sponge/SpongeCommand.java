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
package com.djrapitops.plugin.command.sponge;

import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.utilities.Verify;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * {@link CommandNode} wrapper for Sponge compatibility.
 *
 * @author Rsl1122
 */
public class SpongeCommand implements CommandCallable {

    private final CommandNode command;

    public SpongeCommand(CommandNode command) {
        this.command = command;
    }

    @Override
    public CommandResult process(CommandSource source, String arguments) {
        if (arguments.isEmpty()) {
            command.onCommand(new SpongeCMDSender(source), "", new String[]{});
        } else {
            String[] args = arguments.split(" ");
            command.onCommand(new SpongeCMDSender(source), "", args);
        }
        return CommandResult.success();
    }

    @Override
    public List<String> getSuggestions(CommandSource source, String arguments, @Nullable Location<World> targetPosition) {
        return new ArrayList<>();
    }

    @Override
    public boolean testPermission(CommandSource source) {
        String permission = command.getPermission();
        return Verify.isEmpty(permission) || source.hasPermission(permission);
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        String shortHelp = command.getShortHelp();
        if (shortHelp != null) {
            return Optional.of(Text.of(shortHelp));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Text> getHelp(CommandSource source) {
        String[] inDepthHelp = command.getInDepthHelp();
        if (inDepthHelp != null) {
            String helpString = Arrays.toString(inDepthHelp);
            return Optional.of(Text.of(helpString.substring(1, helpString.length() - 1)));
        }
        return Optional.empty();
    }

    @Override
    public Text getUsage(CommandSource source) {
        String usageString = Arrays.toString(command.getArguments());
        return Text.of(usageString.substring(1, usageString.length() - 1));
    }
}