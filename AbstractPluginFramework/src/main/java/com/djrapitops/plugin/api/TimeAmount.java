package com.djrapitops.plugin.api;

/**
 * Enum containing milli and nano second durations for several times.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public enum TimeAmount {

    MILLISECOND(1L),
    SECOND(1000L),
    MINUTE(60L * SECOND.ms()),
    HOUR(60L * MINUTE.ms()),
    DAY(24L * HOUR.ms()),
    WEEK(7L * DAY.ms()),
    MONTH(30L * DAY.ms()),
    YEAR(365L * DAY.ms());

    private final long ms;

    TimeAmount(long ms) {
        this.ms = ms;
    }

    public long ticks() {
        return (ms / 1000L) * 20L;
    }

    public long ms() {
        return ms;
    }

    public long ns() {
        return ms * 1000000L;
    }

    public static long ticksToMillis(long ticks) {
        return ticks * 1000 / 20;
    }

    public static long currentMs() {
        return System.currentTimeMillis();
    }
}
