package com.djrapitops.plugin.command;

/**
 * Interface that represents a CommandSender for any command.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public interface ISender {

    void sendMessage(String string);

    void sendMessage(String[] strings);

    void sendLink(String pretext, String message, String url);

    default void sendLink(String message, String url) {
        sendLink("", message, url);
    }

    String getName();

    boolean hasPermission(String string);

    boolean isOp();

    SenderType getSenderType();

    Object getSender();
}
