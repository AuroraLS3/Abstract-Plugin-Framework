package com.djrapitops.plugin.api;

import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.StackUtils;
import com.djrapitops.plugin.utilities.status.Timings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rsl1122
 */
public class Benchmark {

    private static final Map<Class, Map<String, Long>> STARTS = new HashMap<>();
    private static final Map<Class, Timings> TIMINGS = new HashMap<>();

    /**
     * Start a new benchmark.
     *
     * @param source Task/Source/Name
     * @return Bench start Epoch ms.
     */
    public static long start(String source) {
        Class plugin = StackUtils.getCallingPlugin();
        Map<String, Long> pluginBenchStarts = STARTS.getOrDefault(plugin, new HashMap<>());
        long time = getTime();
        pluginBenchStarts.put(source, time);
        STARTS.put(plugin, pluginBenchStarts);
        return time;
    }

    public static String startAndFormat(String source) {
        return source + ": Started " + FormatUtils.formatTimeStampSecond(start(source));
    }

    public static String stopAndFormat(String source) {
        return FormatUtils.formatBench(source, stop(source));
    }

    public static String stop(String logDebug, String source) {
        String message = stopAndFormat(source);
        DebugLog.logDebug(logDebug, message);
        return message;
    }

    public static long stop(String source) {
        Class plugin = StackUtils.getCallingPlugin();
        long stop = getTime();

        Map<String, Long> pluginBenchStarts = STARTS.getOrDefault(plugin, new HashMap<>());
        Long start = pluginBenchStarts.get(source);

        if (start == null) {
            return -1;
        }

        pluginBenchStarts.remove(source);

        long bench = stop - start;

        Timings timings = TIMINGS.getOrDefault(plugin, new Timings());
        timings.markExecution(source, bench);
        TIMINGS.put(plugin, timings);

        return bench;
    }

    public static Timings getAverages() {
        return TIMINGS.getOrDefault(StackUtils.getCallingPlugin(), new Timings());
    }

    public static void pluginDisabled(Class c) {
        STARTS.remove(c);
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }
}
