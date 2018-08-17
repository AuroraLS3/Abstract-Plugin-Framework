package com.djrapitops.plugin.benchmarking;

import com.djrapitops.plugin.api.TimeAmount;

/**
 * Object that represents a single benchmark.
 *
 * @author Rsl1122
 */
public class Benchmark implements Comparable<Benchmark> {

    private final long ns;
    private final long estimatedMemoryUse;

    private String name;

    public Benchmark(long ns, long estimatedMemoryUse) {
        this.ns = ns;
        this.estimatedMemoryUse = estimatedMemoryUse;
    }

    public Benchmark(String name, long ns, long estimatedMemoryUse) {
        this.name = name;
        this.ns = ns;
        this.estimatedMemoryUse = estimatedMemoryUse;
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

    private String toMemoryString() {
        long usedMegabytes = estimatedMemoryUse / 1000000L;
        return "~" + usedMegabytes + " MB";
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(toDurationString());
        while (b.length() < 10) {
            b.append(" ");
        }
        b.append(toMemoryString());
        while ((b.length() < 19)) {
            b.append(" ");
        }
        b.append(name);
        return b.toString();
    }

    /**
     * Estimate of memory difference between start and end in bytes.
     *
     * @return bytes of memory difference.
     */
    public long getUsedMemory() {
        return estimatedMemoryUse;
    }
}