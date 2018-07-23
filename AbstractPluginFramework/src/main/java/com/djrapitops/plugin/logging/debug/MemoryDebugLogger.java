package com.djrapitops.plugin.logging.debug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MemoryDebugLogger implements DebugLogger {

    private final List<String> messages;

    public MemoryDebugLogger() {
        messages = new ArrayList<>();
    }

    @Override
    public void log(String... message) {
        String timeStamp = getTimeStamp();
        for (String line : message) {
            messages.add(timeStamp + " | " + line);
        }
    }

    public String getTimeStamp() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    public List<String> getMessages() {
        return messages;
    }
}
