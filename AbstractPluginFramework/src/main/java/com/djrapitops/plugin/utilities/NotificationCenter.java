package com.djrapitops.plugin.utilities;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.api.Priority;
import com.djrapitops.plugin.utilities.player.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages automatic notifications for Staff when they join.
 * <p>
 * Requires notification sending with a separate Login listener.
 *
 * @author Rsl1122
 * @since 2.0.1
 */
public class NotificationCenter<T extends IPlugin> {

    private final T instance;
    private final Map<Priority, List<String>> notifications;

    public NotificationCenter(T instance) {
        this.instance = instance;
        notifications = new HashMap<>();
    }

    public void addNotification(Priority priority, String message) {
        notifications.computeIfAbsent(priority, p -> new ArrayList<>())
                .add(message);
    }

    public void checkNotifications(IPlayer player) {
        if (player.isOp() || player.hasPermission("apf.notify")) {
            for (String msg : getNotifications()) {
                player.sendMessage(msg);
            }
        }
    }

    public List<String> getNotifications() {
        String prefix = instance.getColorScheme().getMainColor() + instance.getPrefix();
        List<String> messages = new ArrayList<>();
        for (Priority p : new Priority[]{Priority.HIGH, Priority.MEDIUM, Priority.LOW}) {
            String priority = Format.create(p.name()).capitalize().toString();
            List<String> msgs = notifications.get(p);
            if (msgs != null) {
                messages.addAll(msgs.stream()
                        .map(notification -> prefix + " " + p.getColor() + priority + "§r " + notification)
                        .collect(Collectors.toList()));
            }
        }
        return messages;
    }
}

