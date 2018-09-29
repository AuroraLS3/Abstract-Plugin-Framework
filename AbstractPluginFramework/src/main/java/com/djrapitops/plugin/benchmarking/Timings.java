package com.djrapitops.plugin.benchmarking;

import com.djrapitops.plugin.logging.debug.DebugLogger;

import java.util.*;

/**
 * Class that manages benchmarks and their results.
 *
 * @author Rsl1122
 */
public class Timings {

    private final Map<String, List<Benchmark>> results;
    private final Map<String, RunningBenchmark> running;

    private final DebugLogger debugLogger;

    public Timings(DebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        results = new HashMap<>();
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

        List<Benchmark> benchmarks = results.getOrDefault(name, new ArrayList<>());
        benchmarks.add(result);
        results.put(name, benchmarks);
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
        results.clear();
    }

    /**
     * Get a list of each named {@link Benchmark} object averaged from all the results.
     *
     * @return List that contains one {@link Benchmark} for each name.
     */
    public List<Benchmark> getAverageResults() {
        List<Benchmark> averageResults = new ArrayList<>();
        for (List<Benchmark> benchmarks : results.values()) {
            averageResults.add(new BenchmarkMutator(benchmarks).average());
        }
        Collections.sort(averageResults);
        return averageResults;
    }
}