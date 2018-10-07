package com.djrapitops.plugin.command;

/**
 * Interface that represents the entity that executed the request for a command.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public interface Sender {

    /**
     * Send a message to the sender.
     *
     * @param string text to send.
     */
    void sendMessage(String string);

    /**
     * Send multiple messages to the sender.
     *
     * @param strings Array of text to send, each element is sent as a separate message.
     */
    void sendMessage(String[] strings);

    /**
     * Send a link to the sender.
     *
     * @param pretext  Text to display before the actual link. Not displayed on console.
     * @param linkText Text to display as the link, underlined. Not displayed on console.
     * @param url      URL to point to.
     */
    void sendLink(String pretext, String linkText, String url);

    /**
     * Send a link to the sender without a pretext.
     *
     * @param linkText Text to display as the link, underlined. Not displayed on console.
     * @param url      URL to point to.
     */
    default void sendLink(String linkText, String url) {
        sendLink("", linkText, url);
    }

    /**
     * Get the name of the sender, defined by delegated sender object.
     *
     * @return Name returned by sender defined by the server platform.
     */
    String getName();

    /**
     * Does the sender have a permission, defined by delegated sender object.
     *
     * @param string Permission node.
     * @return true/false returned by sender defined by the server platform.
     */
    boolean hasPermission(String string);

    /**
     * Check if the sender has OP status.
     *
     * @return true/false returned by sender defined by the server platform. Some platforms do not have concept of OP.
     */
    boolean isOp();

    /**
     * Get what kind of a sender this object is.
     *
     * @return One of {@link SenderType} enum values.
     */
    SenderType getSenderType();

    /**
     * Get the delegated sender object.
     *
     * @return sender defined by the server platform.
     */
    Object getSender();
}
