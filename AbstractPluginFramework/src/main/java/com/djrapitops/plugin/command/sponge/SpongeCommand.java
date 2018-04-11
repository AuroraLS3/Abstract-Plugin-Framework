package com.djrapitops.plugin.command.sponge;

import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.utilities.FormatUtils;
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
 * CommandNode Wrapper for Sponge compatibility.
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
        String[] args = arguments.split(" ");
        command.onCommand(new SpongeCMDSender(source), "", args);
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
            return Optional.of(Text.of(FormatUtils.collectionToStringNoBrackets(
                    Arrays.asList(inDepthHelp)
            )));
        }
        return Optional.empty();
    }

    @Override
    public Text getUsage(CommandSource source) {
        return Text.of(FormatUtils.collectionToStringNoBrackets(Arrays.asList(command.getArguments())));
    }
}