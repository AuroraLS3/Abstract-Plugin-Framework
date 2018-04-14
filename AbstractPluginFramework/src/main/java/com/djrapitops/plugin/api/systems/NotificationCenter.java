package com.djrapitops.plugin.api.systems;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.api.Priority;
import com.djrapitops.plugin.utilities.Format;
import com.djrapitops.plugin.utilities.StackUtils;

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
public class NotificationCenter {

    private static final Map<Class, Map<Priority, List<String>>> notifications = new HashMap<>();

    public static void addNotification(Priority priority, String message) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        Map<Priority, List<String>> notificationMap = notifications.computeIfAbsent(callingPlugin, p -> new HashMap<>());
        notificationMap.computeIfAbsent(priority, p -> new ArrayList<>())
                .add(message);
    }

    public static void checkNotifications(org.bukkit.entity.Player player) {
        if (player.isOp() || player.hasPermission("apf.notify")) {
            for (String msg : getNotifications()) {
                player.sendMessage(msg);
            }
        }
    }

    public static void checkNotifications(org.spongepowered.api.entity.living.player.Player player) {
        if (player.hasPermission("apf.notify")) {
            for (String msg : getNotifications()) {
                SpongePlugin.sendPlayerMsg(player, msg);
            }
        }
    }

    public static List<String> getNotifications() {
        return getNotifications(StackUtils.getCallingPlugin());
    }

    public static List<String> getNotifications(Class callingPlugin) {
        String prefix = "[" + callingPlugin.getSimpleName() + "]";

        List<String> messages = new ArrayList<>();

        Map<Priority, List<String>> notificationMap = notifications.getOrDefault(callingPlugin, new HashMap<>());
        for (Priority p : new Priority[]{Priority.HIGH, Priority.MEDIUM, Priority.LOW}) {
            String priority = Format.create(p.name()).capitalize().toString();
            List<String> msgs = notificationMap.get(p);
            if (msgs != null) {
                messages.addAll(msgs.stream()
                        .map(notification -> p.getColor() + prefix + " " + priority + "§r " + notification)
                        .collect(Collectors.toList()));
            }
        }
        return messages;
    }
}

