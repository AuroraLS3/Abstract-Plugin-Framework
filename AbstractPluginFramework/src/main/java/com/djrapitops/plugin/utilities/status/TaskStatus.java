package com.djrapitops.plugin.utilities.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.task.IRunnable;
import com.djrapitops.plugin.task.ITask;
import com.djrapitops.plugin.utilities.Verify;
import com.djrapitops.plugin.utilities.status.obj.TaskInfo;

/**
 *
 * @author Rsl1122
 * @param <T>
 */
public class TaskStatus<T extends IPlugin> {

    private final T plugin;
    private final Map<String, List<TaskInfo>> taskInfo;
    private final Map<String, List<IRunnable>> tasks;

    public TaskStatus(T plugin) {
        this.plugin = plugin;
        this.taskInfo = new HashMap<>();
        tasks = new HashMap<>();
    }

    public void taskStarted(ITask task, String name, IRunnable run) {
        TaskInfo info = new TaskInfo(name, task.isSync(), "Started", task.getTaskId());
        if (!taskInfo.containsKey(name)) {
            taskInfo.put(name, new ArrayList<>());
        }
        taskInfo.get(name).add(info);
        if (!tasks.containsKey(name)) {
            tasks.put(name, new ArrayList<>());
        }
        tasks.get(name).add(run);
        plugin.getPluginLogger().debug("Started task " + info);
    }

    public void cancelAllKnownTasks() {
        tasks.keySet().forEach((name) -> {
            tasks.get(name).forEach((task) -> {
                task.cancel();
            });
        });
    }

    public void taskCancelled(String name, int id) {
        List<TaskInfo> task = taskInfo.get(name);
        if (Verify.isEmpty(task)) {
            return;
        }
        TaskInfo info = getMatchingTask(name, id);
        if (info != null) {
            plugin.getPluginLogger().debug("Ended task " + info);
            task.remove(info);
        }
    }

    public TaskInfo getMatchingTask(String name, int id) {
        List<TaskInfo> task = new ArrayList<>(taskInfo.get(name));
        for (TaskInfo i : task) {
            if (i.getId() == id && name.equals(i.getName())) {
                return i;
            }
        }
        return null;
    }

    public String getTaskName(int id) {
        Set<TaskInfo> info = new HashSet<>();
        for (List<TaskInfo> tInfo : new HashSet<>(taskInfo.values())) {
            info.addAll(tInfo);
        }
        Optional<TaskInfo> task = info.stream().filter(i -> i.getId() == id).findFirst();
        if (task.isPresent()) {
            return task.get().getName();
        }
        return "Unknown";
    }

    public String[] getTasks() {
        List<TaskInfo> info = new ArrayList<>();
        for (List<TaskInfo> tInfo : new HashSet<>(taskInfo.values())) {
            info.addAll(tInfo);
        }
        List<String> infos = info.stream().map(i -> "Task " + i).collect(Collectors.toList());
        Collections.sort(infos);
        String[] states = new String[info.size()];
        for (int i = 0; i < info.size(); i++) {
            states[i] = infos.get(i);
        }
        return states;
    }

    public int getTaskCount() {
        return new HashSet<>(taskInfo.values()).stream().map(l -> l.size()).mapToInt(i -> i).sum();
    }
}
