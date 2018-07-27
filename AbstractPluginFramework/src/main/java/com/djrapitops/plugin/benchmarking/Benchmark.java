package com.djrapitops.plugin.benchmarking;

import com.djrapitops.plugin.api.TimeAmount;

/**
 * Object that represents a single benchmark.
 *
 * @author Rsl1122
 */
public class Benchmark implements Comparable<Benchmark> {

    private final long ns;
    private String name;

    public Benchmark(long ns) {
        this.ns = ns;
    }

    public Benchmark(String name, long ns) {
        this.name = name;
        this.ns = ns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNs() {
        return ns;
    }

    @Override
    public int compareTo(Benchmark o) {
        return this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }

    public String toDurationString() {
        long millisecond = TimeAmount.MILLISECOND.ns();
        if (this.ns < millisecond) {
            return ns + " ns";
        } else {
            return (ns / millisecond) + " ms";
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(toDurationString());
        while (b.length() < 10) {
            b.append(" ");
        }
        b.append(name);
        return b.toString();
    }
}