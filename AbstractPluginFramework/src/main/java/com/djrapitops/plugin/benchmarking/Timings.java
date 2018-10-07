package com.djrapitops.plugin.benchmarking;

import com.djrapitops.plugin.logging.debug.DebugLogger;

import java.util.*;

/**
 * Class that manages benchmarks and their results.
 *
 * @author Rsl1122
 */
public class Timings {

    private final Map<String, Benchmark> rollingAverage;
    private final Map<String, Long> timesRun;
    private final Map<String, RunningBenchmark> running;

    private final DebugLogger debugLogger;

    public Timings(DebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        rollingAverage = new HashMap<>();
        timesRun = new HashMap<>();
        running = new HashMap<>();
    }

    /**
     * Start a new {@link RunningBenchmark} with the given name.
     *
     * @param name Name of the benchmark.
     */
    public void start(String name) {
        running.put(name, new RunningBenchmark(name));
    }

    /**
     * End a {@link RunningBenchmark} with the given name.
     *
     * @param name Name of the benchmark.
     * @return Result of the {@link Benchmark}.
     */
    public Optional<Benchmark> end(String name) {
        RunningBenchmark bench = running.get(name);
        if (bench == null) {
            return Optional.empty();
        }
        Benchmark result = bench.end();

        Long times = timesRun.getOrDefault(name, 0L);
        Benchmark currentAverage = rollingAverage.get(name);

        long weighedAvgNs = currentAverage != null
                ? (result.getNs() + currentAverage.getNs() * times) / (times + 1)
                : result.getNs();
        long weighedAvgMem = currentAverage != null
                ? (result.getUsedMemory() + currentAverage.getUsedMemory() * times) / (times + 1)
                : result.getUsedMemory();

        rollingAverage.put(name, new Benchmark(name, weighedAvgNs, weighedAvgMem));
        timesRun.put(name, times + 1L);

        return Optional.of(result);
    }

    /**
     * End a {@link RunningBenchmark} with the given name and log the result in the given debug channel.
     *
     * @param debugChannel Channel to log the result in.
     * @param name         Name of the benchmark.
     * @return Result of the {@link Benchmark}.
     */
    public Optional<Benchmark> end(String debugChannel, String name) {
        Optional<Benchmark> benchmark = end(name);
        benchmark.ifPresent(bench -> debugLogger.logOn(debugChannel, bench.toString()));
        return benchmark;
    }

    /**
     * Clear references to {@link RunningBenchmark}s and result {@link Benchmark}s from memory.
     */
    public void reset() {
        running.clear();
        rollingAverage.clear();
    }

    /**
     * Get a list of each named {@link Benchmark} object that represents the rolling average.
     *
     * @return List that contains one {@link Benchmark} for each name.
     */
    public List<Benchmark> getAverageResults() {
        List<Benchmark> averageResults = new ArrayList<>(rollingAverage.values());
        Collections.sort(averageResults);
        return averageResults;
    }
}