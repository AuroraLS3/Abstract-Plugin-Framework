package com.djrapitops.plugin.api;

import com.djrapitops.plugin.utilities.Format;
import com.djrapitops.plugin.utilities.FormattingUtils;
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

    public static void start(String source) {
        Class plugin = StackUtils.getCallingPlugin();
        Map<String, Long> pluginBenchStarts = STARTS.getOrDefault(plugin, new HashMap<>());
        pluginBenchStarts.put(source, getTime());
        STARTS.put(plugin, pluginBenchStarts);
    }

    public static String stopAndFormat(String source) {
        return FormattingUtils.formatBench(source, stop(source));
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

    public Timings getAverages() {
        return TIMINGS.getOrDefault(StackUtils.getCallingPlugin(), new Timings());
    }

    public static void pluginDisabled(Class c) {
        STARTS.remove(c);
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }
}
