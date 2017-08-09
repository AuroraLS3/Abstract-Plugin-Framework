package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.FormattingUtils;
import com.djrapitops.plugin.utilities.Verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Object that represents Debug information about a specific process.
 *
 * @author Rsl1122
 * @since 2.0.2
 */
public class DebugInfo {

    private final PluginLog log;
    private final String task;
    private final List<String> msg;
    private boolean logged = false;

    public DebugInfo(PluginLog log, long time, String task) {
        Verify.nullCheck(log, task);
        this.log = log;
        this.task = task;
        this.msg = new ArrayList<>();
        this.msg.add("|                  | " + task + " (" + timeStamp(time) + ") ------------");
    }

    private String timeStamp(long time) {
        return FormattingUtils.formatTimeStampSecond(time);
    }

    public DebugInfo addLine(String line) {
        clean();
        addLine(line, BenchUtil.getTime());
        return this;
    }

    private void clean() {
        if (logged) {
            msg.clear();
            msg.add("| ---------------- | " + task + " (" + timeStamp(BenchUtil.getTime()) + ") ------------");
            logged = false;
        }
    }

    public DebugInfo addLine(String line, long time) {
        clean();
        msg.add("| " + timeStamp(time) + " | " + line);
        return this;
    }

    public DebugInfo addLines(Collection<String> lines) {
        clean();
        for (String line : lines) {
            msg.add("| " + line);
        }
        return this;
    }

    public DebugInfo addEmptyLine() {
        clean();
        msg.add("|");
        return this;
    }

    public DebugInfo toLog() {
        return toLog(null);
    }

    public DebugInfo toLog(Long time) {
        msg.add(getFooter(time));
        log.toLog(msg, log.getDebugFilename());
        logged = true;
        return this;
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
}
