package com.djrapitops.plugin.benchmarking;

import java.util.*;

/**
 * Class that manages benchmarks and their results.
 *
 * @author Rsl1122
 */
public class Timings {

    private final Map<String, List<Benchmark>> results;

    private final Map<String, RunningBenchmark> running;

    public Timings() {
        results = new HashMap<>();
        running = new HashMap<>();
    }

    public void start(String name) {
        running.put(name, new RunningBenchmark(name));
    }

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

    public void reset() {
        running.clear();
        results.clear();
    }

    public List<Benchmark> getAverageResults() {
        List<Benchmark> averageResults = new ArrayList<>();
        for (List<Benchmark> benchmarks : results.values()) {
            averageResults.add(new BenchmarkMutator(benchmarks).average());
        }
        Collections.sort(averageResults);
        return averageResults;
    }
}