package com.djrapitops.plugin.command.defaultcmds;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.log.DebugInfo;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.command.CommandType;
import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.settings.DefaultMessages;
import com.djrapitops.plugin.api.systems.NotificationCenter;
import com.djrapitops.plugin.utilities.status.TaskCenter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A Default Command for displaying plugin's task, process and benchmark status.
 *
 * @param <T>
 * @author Rsl1122
 * @since 2.0.0
 */
public class StatusCommand<T extends IPlugin> extends SubCommand {

    private final T plugin;
    private final ColorScheme cs;

    public StatusCommand(T plugin, String permission, ColorScheme cs) {
        super("status", CommandType.CONSOLE, permission, "Check the status of plugin's processes.", "[timings]");
        this.plugin = plugin;
        this.cs = cs;
    }

    @Override
    public boolean onCommand(ISender sender, String commandLabel, String[] args) {
        String oColor = cs.getMainColor();
        String sColor = cs.getSecondaryColor();
        String tColor = cs.getTertiaryColor();

        Class<? extends IPlugin> pluginClass = plugin.getClass();
        sender.sendMessage(tColor + DefaultMessages.ARROWS_RIGHT.parse() + oColor + " " + pluginClass.getSimpleName() + " Status");

        if (args.length >= 1 && "timings".equals(args[0].toLowerCase())) {
            sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " BenchmarkObj Averages: ");
            Arrays.stream(Benchmark.getAverages().asStringArray())
                    .map(benchmark -> tColor + "   " + benchmark)
                    .forEach(sender::sendMessage);
            return true;
        }

        List<String> notifications = NotificationCenter.getNotifications(pluginClass);
        if (!notifications.isEmpty()) {
            sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Notifications: ");
            for (String notification : notifications) {
                sender.sendMessage("   " + notification.replace(pluginClass.getSimpleName() + " ", ""));
            }
        }
        sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Tasks running: " + sColor + TaskCenter.getTaskCount(pluginClass));
        sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Processes: ");
        Map<String, DebugInfo> debugs = DebugLog.getAllDebugInfo(pluginClass);
        debugs.forEach((key, value) -> sender.sendMessage(tColor + "   " + key + ": " + value.getLastLine()));
        sender.sendMessage(sColor + " " + DefaultMessages.BALL.toString() + oColor + " Tasks: ");
        Arrays.stream(TaskCenter.getTasks(pluginClass))
                .map(task -> tColor + "   " + task)
                .forEach(sender::sendMessage);
        sender.sendMessage(tColor + DefaultMessages.ARROWS_RIGHT.parse());
        return true;
    }

}
