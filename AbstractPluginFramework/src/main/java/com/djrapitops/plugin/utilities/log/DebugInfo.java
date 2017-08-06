package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.FormattingUtils;
import com.djrapitops.plugin.utilities.Verify;

import java.util.ArrayList;
import java.util.List;

public class DebugInfo {

    private final PluginLog log;
    private final String task;
    private final List<String> msg;

    public DebugInfo(PluginLog log, long time, String task) {
        Verify.nullCheck(log, task);
        this.log = log;
        this.task = task;
        this.msg = new ArrayList<>();
        this.msg.add("| Task: " + task + " (" + timeStamp(time) + ") ------------");
    }

    private String timeStamp(long time) {
        return FormattingUtils.formatTimeStampSecond(time);
    }

    public DebugInfo addLine(String line) {
        addLine(line, BenchUtil.getTime());
        return this;
    }

    public DebugInfo addLine(String line, long time) {
        msg.add("| " + timeStamp(time) + " | " + line);
        if (log.logConsole()) {
            log.info("[Debug | "+task+"]: "+line);
        }
        return this;
    }

    public DebugInfo addEmptyLine() {
        msg.add("|");
        return this;
    }

    public DebugInfo toLog() {
        return toLog(null);
    }

    public DebugInfo toLog(Long time) {
        msg.add(getFooter(time));
        log.toLog(msg, log.getDebugFilename());
        return this;
    }

    private String getFooter(Long time) {
        int footerMaxLength = msg.get(0).length();
        StringBuilder footer = new StringBuilder(footerMaxLength);
        footer.append("| "+task+": ");
        if (time != null) {
            footer.append(time).append(" ms");
        }
        while (footer.length() < footerMaxLength) {
            footer.append("-");
        }
        return footer.toString();
    }
}
