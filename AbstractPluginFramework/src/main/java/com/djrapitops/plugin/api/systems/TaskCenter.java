package com.djrapitops.plugin.api.systems;

import com.djrapitops.plugin.api.utility.log.Log;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import com.djrapitops.plugin.utilities.StackUtils;
import com.djrapitops.plugin.utilities.status.obj.TaskInfo;

import java.util.*;

/**
 * Manages information about different Runnables and Tasks related to them.
 *
 * @author Rsl1122
 */
public class TaskCenter {

    private static final Map<Class, List<TaskInfo>> taskInfo = new HashMap<>();
    private static final Map<Class, List<PluginRunnable>> tasks = new HashMap<>();

    public static void taskStarted(Class plugin, PluginTask task, String name, PluginRunnable run) {
        TaskInfo info = new TaskInfo(name, task.isSync(), "Started", task.getTaskId());

        List<TaskInfo> taskInfoList = taskInfo.getOrDefault(plugin, new ArrayList<>());
        taskInfoList.add(info);
        taskInfo.put(plugin, taskInfoList);

        List<PluginRunnable> taskList = tasks.getOrDefault(plugin, new ArrayList<>());
        taskList.add(run);
        tasks.put(plugin, taskList);
        Log.debug(plugin, "Started task " + info);
    }

    public static void cancelAllKnownTasks() {
        cancelAllKnownTasks(StackUtils.getCallingPlugin());
    }

    public static void cancelAllKnownTasks(Class plugin) {
        List<PluginRunnable> taskList = tasks.getOrDefault(plugin, new ArrayList<>());
        // taskList is copied to new ArrayList to avoid Concurrent Modification
        for (PluginRunnable pluginRunnable : new ArrayList<>(taskList)) {
            try {
                pluginRunnable.cancel();
                taskCancelled(plugin, pluginRunnable.getTaskName(), pluginRunnable.getTaskId());
            } catch (Exception ignored) {
            }
        }
        tasks.remove(plugin);
        taskInfo.remove(plugin);
    }

    public static void taskCancelled(Class plugin, String name, int id) {
        List<TaskInfo> task = taskInfo.getOrDefault(plugin, new ArrayList<>());
        Optional<TaskInfo> first = new ArrayList<>(task).stream()
                .filter(Objects::nonNull)
                .filter(t -> (name != null && name.equals(t.getName())) || t.getId() == id)
                .findFirst();
        first.ifPresent(info -> {
            Log.debug(plugin, "Ended task " + info);
            task.remove(info);
        });
    }

    public static TaskInfo getMatchingTask(String name, int id) {
        for (List<TaskInfo> tasks : taskInfo.values()) {
            for (TaskInfo task : tasks) {
                if (task.getId() == id && name.equals(task.getName())) {
                    return task;
                }
            }
        }
        return null;
    }

    public static String getTaskName(int id) {
        return taskInfo.values().stream()
                .flatMap(Collection::stream)
                .filter(i -> i.getId() == id)
                .findFirst()
                .map(TaskInfo::getName)
                .orElse("Unknown");
    }

    public static String[] getTasks(Class plugin) {
        return new ArrayList<>(taskInfo.get(plugin))
                .stream()
                .map(TaskInfo::toString)
                .sorted().toArray(String[]::new);
    }

    public static int getTaskCount(Class plugin) {
        return taskInfo.get(plugin).size();
    }
}
