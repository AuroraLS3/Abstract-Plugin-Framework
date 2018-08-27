package com.djrapitops.plugin.api;

import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.StackUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rsl1122
 */
@Deprecated
public class Benchmark {

    private static final Map<Class, Map<String, Long>> STARTS = new HashMap<>();

    /**
     * Start a new benchmark.
     *
     * @param source Task/Source/Name
     * @return Bench start Epoch ms.
     */
    public static long start(String source) {
        Class plugin = StackUtils.getCallingPlugin();
        Map<String, Long> pluginBenchStarts = STARTS.getOrDefault(plugin, new HashMap<>());
        long time = TimeAmount.currentMs();
        pluginBenchStarts.put(source, time);
        STARTS.put(plugin, pluginBenchStarts);
        return time;
    }

    public static void start(String logDebug, String source) {
        DebugLog.logDebug(logDebug, startAndFormat(source));
    }

    public static String startAndFormat(String source) {
        start(source);
        return "started   " + source;
    }

    public static String stopAndFormat(String source) {
        return FormatUtils.formatBench(source, stop(source));
    }

    public static long stop(String logDebug, String source) {
        long time = stop(source);
        DebugLog.logDebug(logDebug, FormatUtils.formatBench(source, time));
        return time;
    }

    public static long stop(String source) {
        Class plugin = StackUtils.getCallingPlugin();
        long stop = TimeAmount.currentMs();

        Map<String, Long> pluginBenchStarts = STARTS.getOrDefault(plugin, new HashMap<>());
        Long start = pluginBenchStarts.get(source);

        if (start == null) {
            return -1;
        }

        pluginBenchStarts.remove(source);

        return stop - start;
    }

    public static void pluginDisabled(Class c) {
        STARTS.remove(c);
    }

    /**
     * @deprecated Use TimeAmount.currentMs instead.
     */
    @Deprecated
    public static long getTime() {
        return System.currentTimeMillis();
    }

    private Benchmark() {
        /* Hide constructor */
    }
}
