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
package com.djrapitops.plugin.logging.debug;

import com.djrapitops.plugin.utilities.EjectingQueue;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@link DebugLogger} implementation that logs messages to be kept in memory.
 *
 * @author AuroraLS3
 */
public class MemoryDebugLogger implements DebugLogger {

    private final Map<String, EjectingQueue<String>> channels;

    /**
     * Create a new MemoryDebugLogger.
     */
    public MemoryDebugLogger() {
        channels = new HashMap<>();
    }

    @Override
    public void logOn(String channel, String... lines) {
        String timeStamp = getTimeStamp();
        EjectingQueue<String> messages = channels.getOrDefault(channel, new EjectingQueue<>(100));
        for (String line : lines) {
            messages.add(timeStamp + " | " + line);
        }
        channels.put(channel, messages);
    }

    /**
     * Creates a new timestamp string with the current millisecond.
     *
     * @return Timestamp of format MM-dd HH:mm:ss
     */
    public String getTimeStamp() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    /**
     * Retrieve the map of channels and messages in memory.
     * <p>
     * Reference to an instance to this map should not be stored in a class variable.
     *
     * @return Map: channel name - immutable list of messages.
     */
    public Map<String, List<String>> getChannels() {
        return channels.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getElements()));
    }
}
