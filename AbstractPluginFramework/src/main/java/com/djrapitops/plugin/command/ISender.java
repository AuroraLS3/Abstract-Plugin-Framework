package com.djrapitops.plugin.command;

/**
 * Interface that represents a CommandSender for any command.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public interface ISender {

    public void sendMessage(String string);

    public void sendMessage(String[] strings);

    public String getName();

    public boolean hasPermission(String string);

    public boolean isOp();

    public SenderType getSenderType();

    public Object getSender();
}
