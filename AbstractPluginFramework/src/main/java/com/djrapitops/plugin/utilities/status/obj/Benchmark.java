/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities.status.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Benchmark times given by BenchUtil.
 *
 * @author Rsl1122
 */
public class Benchmark {

    private final List<Long> benchmarks;

    public Benchmark() {
        benchmarks = new ArrayList<>();
    }

    public void addBenchmark(long time) {
        benchmarks.add(time);
    }

    public long getAverage() {
        return benchmarks.stream().mapToLong(i -> i).sum() / benchmarks.size();
    }
}
