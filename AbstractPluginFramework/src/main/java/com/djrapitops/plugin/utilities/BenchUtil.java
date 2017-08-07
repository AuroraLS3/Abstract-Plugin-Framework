package com.djrapitops.plugin.utilities;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.utilities.status.Timings;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rsl1122
 */
public class BenchUtil {

    private final Map<String, Long> starts;
    private final Timings timings;
    private IPlugin plugin;

    public BenchUtil(IPlugin plugin) {
        this.plugin = plugin;
        starts = new HashMap<>();
        timings = new Timings();
    }

    /**
     *
     * @param source
     */
    public void start(String source) {
        starts.put(source, System.nanoTime());
    }

    /**
     *
     * @param source
     * @return
     */
    public long stop(String source) {
        Long s = starts.get(source);
        if (s != null) {
            long ms = (System.nanoTime() - s) / 1000000;
            starts.remove(source);
            timings.markExecution(source, ms);
            return ms;
        }
        return -1;
    }

    public long stop(String task, String source) {
        Long s = starts.get(source);
        if (s != null) {
            long ms = (System.nanoTime() - s) / 1000000;
            starts.remove(source);
            timings.markExecution(source, ms);
            plugin.getPluginLogger().getDebug(task).addLine(source+" took: "+ms+" ms");
            return ms;
        }
        return -1;
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public Timings getTimings() {
        return timings;
    }
}
