package com.djrapitops.plugin.utilities.status;

import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.status.obj.BenchmarkObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class responsible for calculating average execution time of benchmarks.
 *
 * @author Rsl1122
 */
@Deprecated
public class Timings {

    private final Map<String, BenchmarkObj> avgTimings;

    public Timings() {
        avgTimings = new HashMap<>();
    }

    public void markExecution(String benchmark, long time) {
        avgTimings.computeIfAbsent(benchmark, computedBench -> new BenchmarkObj())
                .addBenchmark(time);
    }

    public String[] asStringArray() {
        String[] states = new String[avgTimings.size()];
        int i = 0;
        List<String> msgs = avgTimings.keySet().stream()
                .sorted()
                .map(bench -> {
                    try {
                        return FormatUtils.formatBench(bench, avgTimings.get(bench).getAverage());
                    } catch (NullPointerException e) {
                        return FormatUtils.formatBench(bench, -1);
                    }
                }).collect(Collectors.toList());
        for (String msg : msgs) {
            states[i] = msg;
            i++;
        }
        return states;
    }
}
