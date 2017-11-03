/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.Verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Object that represents Debug information about a specific process.
 *
 * @author Rsl1122
 * @since 2.0.2
 */
public class DebugInfo {

    private final Class sourcePlugin;
    private final String task;
    private final List<String> msg;
    private final long firstCallTime;

    public DebugInfo(Class sourcePlugin, long time, String task) {
        Verify.nullCheck(sourcePlugin, task);
        this.sourcePlugin = sourcePlugin;
        this.task = task;
        this.msg = Collections.synchronizedList(new ArrayList<String>());
        this.firstCallTime = time;
        addHeader();
    }

    private String timeStamp(long time) {
        return FormatUtils.formatTimeStampSecond(time);
    }

    public DebugInfo addLine(String line) {
        addLine(line, Benchmark.getTime());
        return this;
    }

    private void addHeader() {
        if (msg.isEmpty()) {
            this.msg.add("|                  | " + task + " (" + timeStamp(firstCallTime) + ") ------------");
        }
    }

    public DebugInfo addLine(String line, long time) {
        addHeader();
        msg.add("| " + timeStamp(time) + " | " + line);
        return this;
    }

    public DebugInfo addLines(Collection<String> lines) {
        addHeader();
        for (String line : lines) {
            msg.add("|                  | " + line);
        }
        return this;
    }

    public DebugInfo addEmptyLine() {
        addHeader();
        msg.add("|");
        return this;
    }

    public void toLog() {
        toLog(null);
    }

    public void toLog(Long time) {
        if (msg.size() > 1) {
            msg.add(getFooter(time));
            Log.debug(msg);
        }
        DebugLog.clearDebug(task);
    }

    private String getFooter(Long time) {
        int footerMaxLength = msg.get(0).length();
        StringBuilder footer = new StringBuilder(footerMaxLength);
        footer.append("| ---------------- | ");
        if (time != null) {
            footer.append(task).append(" ");
            footer.append(time).append(" ms ");
        }
        while (footer.length() < footerMaxLength) {
            footer.append("-");
        }
        return footer.toString();
    }

    public String getLastLine() {
        if (msg.isEmpty()) {
            return "No information.";
        }
        return msg.get(msg.size() - 1);
    }
}