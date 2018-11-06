package com.djrapitops.plugin.api;

import java.util.concurrent.TimeUnit;

/**
 * Utility for converting time amounts to ticks and milliseconds.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public enum TimeAmount {

    WEEK(TimeUnit.DAYS.toMillis(7L)),
    MONTH(TimeUnit.DAYS.toMillis(30L)),
    YEAR(TimeUnit.DAYS.toMillis(365L));

    private final long ms;

    TimeAmount(long ms) {
        this.ms = ms;
    }

    /**
     * Convert a time into ticks.
     *
     * @param amount How many of the time unit?
     * @param unit   Unit of time
     * @return How many ticks that time span is.
     */
    public static long toTicks(long amount, TimeUnit unit) {
        return unit.toMillis(amount) / 50L;
    }

    /**
     * Convert a tick count into milliseconds.
     *
     * @param ticks How many ticks?
     * @return How many milliseconds pass during those ticks.
     */
    public static long ticksToMillis(long ticks) {
        return ticks * 50L;
    }

    /**
     * Find how many milliseconds a TimeAmount is.
     * <p>
     * This method is provided as extension for TimeUnit that does not provide WEEK, MONTH or YEAR.
     *
     * @param amount Amount of the time unit.
     * @return Milliseconds.
     */
    public long toMillis(long amount) {
        return ms * amount;
    }
}
