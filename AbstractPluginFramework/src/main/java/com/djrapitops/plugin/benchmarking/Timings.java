package com.djrapitops.plugin.benchmarking;

import java.util.*;

/**
 * Class that manages benchmarks and their results.
 *
 * @author Rsl1122
 */
public class Timings {

    private final Map<String, List<Benchmark>> results;

    private final Map<String, Long> running;

    public Timings() {
        results = new HashMap<>();
        running = new HashMap<>();
    }

    public void start(String name) {
        running.put(name, System.nanoTime());
    }

    public Optional<Benchmark> end(String name) {
        Long start = running.get(name);
        if (start == null) {
            return Optional.empty();
        }
        long end = System.nanoTime();
        long diff = start - end;

        List<Benchmark> benchmarks = results.getOrDefault(name, new ArrayList<>());
        Benchmark result = new Benchmark(name, diff);
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