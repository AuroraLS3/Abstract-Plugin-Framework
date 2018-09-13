package com.djrapitops.plugin.api;

import java.util.concurrent.TimeUnit;

/**
 * Enum containing milli and nano second durations for several times.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public enum TimeAmount {

    @Deprecated
    MILLISECOND(TimeUnit.MILLISECONDS.toMillis(1L)),
    @Deprecated
    SECOND(TimeUnit.SECONDS.toMillis(1L)),
    @Deprecated
    MINUTE(TimeUnit.MINUTES.toMillis(1L)),
    @Deprecated
    HOUR(TimeUnit.HOURS.toMillis(1L)),
    @Deprecated
    DAY(TimeUnit.DAYS.toMillis(1L)),
    WEEK(TimeUnit.DAYS.toMillis(7L)),
    MONTH(TimeUnit.DAYS.toMillis(30L)),
    YEAR(TimeUnit.DAYS.toMillis(365L));

    private final long ms;

    TimeAmount(long ms) {
        this.ms = ms;
    }

    public static long toTicks(long amount, TimeUnit unit) {
        return unit.toMillis(amount) / 50L;
    }

    public static long ticksToMillis(long ticks) {
        return ticks * 50L;
    }

    /**
     * @deprecated use {@code System.currentTimeMillis()} instead.
     */
    @Deprecated
    public static long currentMs() {
        return System.currentTimeMillis();
    }

    /**
     * @deprecated use {@code toTicks(long, TimeUnit)} instead.
     */
    @Deprecated
    public long ticks() {
        return toTicks(ms, TimeUnit.MILLISECONDS);
    }

    /**
     * @deprecated use {@code TimeUnit#toMillis(long)} instead.
     */
    @Deprecated
    public long ms() {
        return ms;
    }

    /**
     * @deprecated use {@code TimeUnit#toNanos(long)} instead.
     */
    @Deprecated
    public long ns() {
        return TimeUnit.MILLISECONDS.toNanos(ms);
    }
}
