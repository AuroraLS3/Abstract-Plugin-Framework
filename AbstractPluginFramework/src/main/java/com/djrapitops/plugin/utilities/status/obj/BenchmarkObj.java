/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities.status.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a BenchmarkObj times given by BenchUtil.
 *
 * @author Rsl1122
 */
@Deprecated
public class BenchmarkObj {

    private final List<Long> benchmarks;

    public BenchmarkObj() {
        benchmarks = new ArrayList<>();
    }

    public void addBenchmark(long time) {
        benchmarks.add(time);
    }

    public long getAverage() {
        return benchmarks.stream().filter(Objects::nonNull).mapToLong(i -> i).sum() / benchmarks.size();
    }
}
