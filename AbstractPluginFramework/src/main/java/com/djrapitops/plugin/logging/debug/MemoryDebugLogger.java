package com.djrapitops.plugin.logging.debug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryDebugLogger implements DebugLogger {

    private final Map<String, List<String>> channels;

    public MemoryDebugLogger() {
        channels = new HashMap<>();
    }

    @Override
    public void logOn(String channel, String... message) {
        String timeStamp = getTimeStamp();
        List<String> messages = channels.getOrDefault(channel, new ArrayList<>());
        for (String line : message) {
            messages.add(timeStamp + " | " + line);
        }
        channels.put(channel, messages);
    }

    public String getTimeStamp() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    public Map<String, List<String>> getChannels() {
        return channels;
    }
}
