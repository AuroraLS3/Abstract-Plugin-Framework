/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.djrapitops.plugin.benchmarking;

import java.util.List;

/**
 * Mutator object for a List of {@link Benchmark}s.
 *
 * @author AuroraLS3
 */
class BenchmarkMutator {

    private final List<Benchmark> benchmarks;

    BenchmarkMutator(List<Benchmark> benchmarks) {
        this.benchmarks = benchmarks;
    }

    Benchmark average() {
        if (benchmarks.isEmpty()) {
            return new Benchmark(0, 0);
        }
        long totalNs = 0;
        long totalMem = 0;
        int size = benchmarks.size();
        for (Benchmark benchmark : benchmarks) {
            totalNs += benchmark.getNs();
            totalMem += benchmark.getUsedMemory();
        }
        return new Benchmark(benchmarks.get(0).getName(), totalNs / size, totalMem / size);
    }
}