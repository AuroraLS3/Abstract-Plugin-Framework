package com.djrapitops.plugin.api;

import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.utilities.StackUtils;

import java.util.concurrent.TimeUnit;

@Deprecated
public class Benchmark {

    public static long start(String source) {
        Class plugin = StackUtils.getCallingPlugin();
        StaticHolder.getInstance(plugin).getTimings().start(source);
        return System.currentTimeMillis();
    }

    @Deprecated
    public static void start(String logDebug, String source) {
        start(source);
    }

    @Deprecated
    public static String startAndFormat(String source) {
        start(source);
        return "started   " + source;
    }

    @Deprecated
    public static String stopAndFormat(String source) {
        StringBuilder b = new StringBuilder();
        b.append(stop(source)).append(" ms");
        while (b.length() < 10) {
            b.append(" ");
        }
        b.append(source);
        return b.toString();
    }

    @Deprecated
    public static long stop(String logDebug, String source) {
        Class plugin = StackUtils.getCallingPlugin();
        return TimeUnit.NANOSECONDS.toMillis(
                StaticHolder.getInstance(plugin).getTimings().end(logDebug, source)
                        .map(com.djrapitops.plugin.benchmarking.Benchmark::getNs)
                        .orElse(0L)
        );
    }

    public static long stop(String source) {
        Class plugin = StackUtils.getCallingPlugin();
        return TimeUnit.NANOSECONDS.toMillis(
                StaticHolder.getInstance(plugin).getTimings().end(source)
                        .map(com.djrapitops.plugin.benchmarking.Benchmark::getNs)
                        .orElse(0L)
        );
    }

    @Deprecated
    public static void pluginDisabled(Class c) {
    }

    @Deprecated
    public static long getTime() {
        return System.currentTimeMillis();
    }

    private Benchmark() {
        /* Hide constructor */
    }
}
