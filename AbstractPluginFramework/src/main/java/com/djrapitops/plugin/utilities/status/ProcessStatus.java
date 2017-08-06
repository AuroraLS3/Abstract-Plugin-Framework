package com.djrapitops.plugin.utilities.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.FormattingUtils;

/**
 *
 * @author Rsl1122
 * @param <T>
 */
@Deprecated
public class ProcessStatus<T extends IPlugin> {

    private final T plugin;
    private final Map<String, String> status;

    @Deprecated
    public ProcessStatus(T plugin) {
        status = new HashMap<>();
        this.plugin = plugin;
    }

    @Deprecated
    public String getStatus(String process) {
        final String state = status.get(process);
        if (state == null) {
            return "Process not running.";
        }
        return state;
    }

    @Deprecated
    public void setStatus(String process, String state) {
        status.put(process, state);
        plugin.getPluginLogger().debug(process + ": " + state);
    }

    @Deprecated
    public void startExecution(String process) {
        BenchUtil benchmark = plugin.benchmark();
        benchmark.start(process);
        setStatus(process, "Started: " + FormattingUtils.formatTimeStampSecond(BenchUtil.getTime()));
    }

    @Deprecated
    public long finishExecution(String process) {
        if (!status.containsKey(process)) {
            return -1;
        }
        BenchUtil benchmark = plugin.benchmark();
        long ms = benchmark.stop(process);
        setStatus(process, "Finished (" + FormattingUtils.formatTimeStampSecond(BenchUtil.getTime()) + "), took: " + ms + "ms");
        return ms;
    }

    @Deprecated
    public String[] getProcesses() {
        String[] states = new String[status.size()];
        List<String> processes = new ArrayList<>(status.keySet());
        for (int i = 0; i < status.size(); i++) {
            String process = processes.get(i);
            states[i] = process + ": " + status.get(process);
        }
        return states;
    }
}
