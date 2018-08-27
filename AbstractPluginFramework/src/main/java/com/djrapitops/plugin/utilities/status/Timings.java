package com.djrapitops.plugin.utilities.status;

import com.djrapitops.plugin.utilities.status.obj.BenchmarkObj;

import java.util.HashMap;
import java.util.Map;

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

}
