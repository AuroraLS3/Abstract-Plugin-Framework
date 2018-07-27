package com.djrapitops.plugin.benchmarking;

import java.util.List;

/**
 * Mutator object for a List of {@link Benchmark}s.
 *
 * @author Rsl1122
 */
public class BenchmarkMutator {

    private final List<Benchmark> benchmarks;

    public BenchmarkMutator(List<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public Benchmark average() {
        if (benchmarks.isEmpty()) {
            return new Benchmark(0);
        }
        long totalNs = 0;
        int size = benchmarks.size();
        for (Benchmark benchmark : benchmarks) {
            totalNs += benchmark.getNs();
        }
        return new Benchmark(benchmarks.get(0).getName(), totalNs / size);
    }
}