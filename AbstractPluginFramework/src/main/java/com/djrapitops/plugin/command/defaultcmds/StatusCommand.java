package com.djrapitops.plugin.command.defaultcmds;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.command.CommandType;
import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.settings.DefaultMessages;
import com.djrapitops.plugin.utilities.NotificationCenter;
import com.djrapitops.plugin.utilities.status.ProcessStatus;
import com.djrapitops.plugin.utilities.status.TaskStatus;

import java.util.Arrays;
import java.util.List;

/**
 * A Default Command for displaying plugin's task, process and benchmark status.
 *
 * @param <T>
 * @author Rsl1122
 * @since 2.0.0
 */
public class StatusCommand<T extends IPlugin> extends SubCommand {

    private final T plugin;

    public StatusCommand(T plugin, String permission) {
        super("status", CommandType.CONSOLE, permission, "Check the status of plugin's processes.", "[timings]");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(ISender sender, String commandLabel, String[] args) {
        ColorScheme cs = plugin.getColorScheme();
        String oColor = cs.getMainColor();
        String sColor = cs.getSecondaryColor();
        String tColor = cs.getTertiaryColor();

        sender.sendMessage(tColor + DefaultMessages.ARROWS_RIGHT.parse() + oColor + " " + plugin.getClass().getSimpleName() + " Status");

        if (args.length >= 1 && "timings".equals(args[0].toLowerCase())) {
            sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Benchmark Averages: ");
            Arrays.stream(plugin.benchmark().getTimings().getTimings())
                    .map(benchmark -> tColor + "   " + benchmark)
                    .forEach(msg -> sender.sendMessage(msg));
            return true;
        }

        NotificationCenter notificationCenter = plugin.getNotificationCenter();

        List<String> notifications = notificationCenter.getNotifications();
        if (!notifications.isEmpty()) {
            sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Notifications: ");
            for (String notification : notifications) {
                sender.sendMessage("   " + notification.replace(plugin.getPrefix() + " ", ""));
            }
        }


        TaskStatus taskStatus = plugin.taskStatus();
        ProcessStatus processStatus = plugin.processStatus();
        sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Tasks running: " + sColor + taskStatus.getTaskCount());
        sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Processes: ");
        Arrays.stream(processStatus.getProcesses())
                .map(process -> tColor + "   " + process)
                .forEach(msg -> sender.sendMessage(msg));
        sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Tasks: ");
        Arrays.stream(taskStatus.getTasks())
                .map(process -> tColor + "   " + process)
                .forEach(msg -> sender.sendMessage(msg));
        sender.sendMessage(tColor + DefaultMessages.ARROWS_RIGHT.parse());
        return true;
    }

}
