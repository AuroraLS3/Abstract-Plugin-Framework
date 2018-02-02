package com.djrapitops.plugin.api.systems;

import com.djrapitops.plugin.api.utility.log.Log;
import com.djrapitops.plugin.task.IRunnable;
import com.djrapitops.plugin.task.ITask;
import com.djrapitops.plugin.utilities.StackUtils;
import com.djrapitops.plugin.utilities.status.obj.TaskInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages information about different Runnables and Tasks related to them.
 *
 * @author Rsl1122
 */
public class TaskCenter {

    private static final Map<Class, List<TaskInfo>> taskInfo = new HashMap<>();
    private static final Map<Class, List<IRunnable>> tasks = new HashMap<>();

    public static void taskStarted(Class plugin, ITask task, String name, IRunnable run) {
        TaskInfo info = new TaskInfo(name, task.isSync(), "Started", task.getTaskId());

        List<TaskInfo> taskInfoList = taskInfo.getOrDefault(plugin, new ArrayList<>());
        taskInfoList.add(info);
        taskInfo.put(plugin, taskInfoList);

        List<IRunnable> taskList = tasks.getOrDefault(plugin, new ArrayList<>());
        taskList.add(run);
        tasks.put(plugin, taskList);
        Log.debug(plugin, "Started task " + info);
    }

    public static void cancelAllKnownTasks() {
        cancelAllKnownTasks(StackUtils.getCallingPlugin());
    }

    public static void cancelAllKnownTasks(Class plugin) {
        List<IRunnable> taskList = tasks.getOrDefault(plugin, new ArrayList<>());
        for (IRunnable iRunnable : taskList) {
            try {
                iRunnable.cancel();
                taskCancelled(plugin, iRunnable.getTaskName(), iRunnable.getTaskId());
            } catch (Exception ignored) {
            }
        }
        tasks.remove(plugin);
        taskInfo.remove(plugin);
    }

    public static void taskCancelled(Class plugin, String name, int id) {
        List<TaskInfo> task = taskInfo.get(plugin);
        Optional<TaskInfo> first = task.stream().filter(t -> t.getName().equals(name) && t.getId() == id).findFirst();
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
        List<String> infos = new ArrayList<>(taskInfo.get(plugin))
                .stream()
                .map(i -> "Task " + i)
                .sorted()
                .collect(Collectors.toList());

        int length = infos.size();
        String[] states = new String[length];

        for (int i = 0; i < length; i++) {
            states[i] = infos.get(i);
        }

        return states;
    }

    public static int getTaskCount(Class plugin) {
        return taskInfo.get(plugin).size();
    }
}
