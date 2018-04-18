package com.djrapitops.plugin.command.sponge;

import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SenderType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.source.RconSource;
import org.spongepowered.api.command.source.SignSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextStyles;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * ISender implementation for Sponge CommandSource.
 *
 * @author Rsl1122
 */
public class SpongeCMDSender implements ISender {

    private final CommandSource commandSource;

    public SpongeCMDSender(CommandSource commandSource) {
        this.commandSource = commandSource;
    }

    @Override
    public void sendMessage(String string) {
        commandSource.sendMessage(Text.of(string));
    }

    @Override
    public void sendMessage(String[] strings) {
        for (String string : strings) {
            sendMessage(string);
        }
    }

    @Override
    public void sendLink(String pretext, String message, String url) {
        try {
            commandSource.sendMessage(
                    Text.join(
                            Text.of(pretext),
                            Text.builder(message).style(TextStyles.UNDERLINE).onClick(TextActions.openUrl(new URL(url))).build()
                    ));
        } catch (MalformedURLException e) {
            sendLink(message, url, url);
        }
    }

    @Override
    public void sendLink(String message, String url) {
        try {
            commandSource.sendMessage(
                    Text.builder(message).onClick(TextActions.openUrl(new URL(url))).build()
            );
        } catch (MalformedURLException e) {
            sendLink(message, url, url);
        }
    }

    @Override
    public String getName() {
        return commandSource.getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSource.hasPermission(permission);
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public SenderType getSenderType() {
        if (commandSource instanceof CommandBlockSource || commandSource instanceof SignSource) {
            return SenderType.CMD_BLOCK;
        } else if (commandSource instanceof ConsoleSource) {
            return SenderType.CONSOLE;
        } else if (commandSource instanceof Player) {
            return SenderType.PLAYER;
        } else if (commandSource instanceof RconSource) {
            return SenderType.PROXY_PLAYER;
        }
        return SenderType.CONSOLE;
    }

    @Override
    public CommandSource getSender() {
        return commandSource;
    }
}