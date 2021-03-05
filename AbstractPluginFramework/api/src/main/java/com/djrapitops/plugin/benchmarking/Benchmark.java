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

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Object that represents a single benchmark.
 * <p>
 * To obtain Benchmark objects, use {@link Timings}.
 *
 * @author AuroraLS3
 */
public class Benchmark implements Comparable<Benchmark> {

    private final long ns;
    private final long estimatedMemoryUse;

    private String name;

    Benchmark(long ns, long estimatedMemoryUse) {
        this.ns = ns;
        this.estimatedMemoryUse = estimatedMemoryUse;
    }

    Benchmark(String name, long ns, long estimatedMemoryUse) {
        this.name = name;
        this.ns = ns;
        this.estimatedMemoryUse = estimatedMemoryUse;
    }

    /**
     * Get name of the Benchmark.
     *
     * @return Name defined in {@link Timings} that create the object.
     */
    public String getName() {
        return name;
    }

    /**
     * Get how many nanoseconds the benchmark lasted.
     *
     * @return Difference in nanos between Timings#start and Timings#end.
     * @see Timings
     */
    public long getNs() {
        return ns;
    }

    @Override
    public int compareTo(Benchmark o) {
        return this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }

    /**
     * Get a text representation of the duration.
     *
     * @return "x ns" or "x ms" depending on duration.
     */
    public String toDurationString() {
        long millisecondNs = TimeUnit.MILLISECONDS.toNanos(1);
        if (this.ns < millisecondNs) {
            return ns + " ns";
        } else {
            return (ns / millisecondNs) + " ms";
        }
    }

    /**
     * Get a text representation of the change in memory usage.
     *
     * @return " 0 B" "+/-x B", "+/-x KB" or "+/-x MB".
     */
    public String toMemoryString() {
        long usedKilobytes = estimatedMemoryUse / 1000L;
        if (estimatedMemoryUse == 0) {
            return " 0 B";
        }
        if (estimatedMemoryUse > 0) {
            if (estimatedMemoryUse < 1000) {
                return "+" + estimatedMemoryUse + " B";
            }
            if (usedKilobytes > 1000) {
                return "+" + (usedKilobytes / 1000L) + " MB";
            }
            return "+" + usedKilobytes + " KB";
        } else {
            if (estimatedMemoryUse > -1000) {
                return estimatedMemoryUse + " B";
            }
            if (usedKilobytes < -1000) {
                return (usedKilobytes / 1000L) + " MB";
            }
            return usedKilobytes + " KB";
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(toDurationString());
        while (b.length() < 11) {
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
     * @return bytes (Difference)
     */
    public long getUsedMemory() {
        return estimatedMemoryUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Benchmark)) return false;
        Benchmark benchmark = (Benchmark) o;
        return ns == benchmark.ns &&
                estimatedMemoryUse == benchmark.estimatedMemoryUse &&
                Objects.equals(name, benchmark.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ns, estimatedMemoryUse, name);
    }
}