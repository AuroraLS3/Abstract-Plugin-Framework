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

import com.djrapitops.plugin.logging.console.TestPluginLogger;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link CombineDebugLogger}.
 *
 * @author AuroraLS3
 */
public class CombineDebugLoggerTest {

    @Test
    public void existingSubDebugLoggerIsFound() {
        CombineDebugLogger underTest = new CombineDebugLogger(
                new MemoryDebugLogger(),
                new ConsoleDebugLogger(new TestPluginLogger())
        );

        assertTrue(underTest.getDebugLogger(MemoryDebugLogger.class).isPresent());
        assertTrue(underTest.getDebugLogger(ConsoleDebugLogger.class).isPresent());
    }

    @Test
    public void nonExistentSubDebugLoggerIsNotFound() {
        CombineDebugLogger underTest = new CombineDebugLogger(
                new MemoryDebugLogger(),
                new ConsoleDebugLogger(new TestPluginLogger())
        );

        assertFalse(underTest.getDebugLogger(FolderTimeStampFileDebugLogger.class).isPresent());
    }

    @Test
    public void subLoggersAreCalledOnLog() {
        DebugLogger mockOne = Mockito.mock(DebugLogger.class);
        DebugLogger mockTwo = Mockito.mock(DebugLogger.class);

        DebugLogger underTest = new CombineDebugLogger(mockOne, mockTwo);

        underTest.log("Test");

        verify(mockOne).logOn("", "Test");
        verify(mockTwo).logOn("", "Test");
    }

    @Test
    public void subLoggersAreCalledOnLogOn() {
        DebugLogger mockOne = Mockito.mock(DebugLogger.class);
        DebugLogger mockTwo = Mockito.mock(DebugLogger.class);

        DebugLogger underTest = new CombineDebugLogger(mockOne, mockTwo);

        underTest.logOn("Channel", "Test");

        verify(mockOne).logOn("Channel", "Test");
        verify(mockTwo).logOn("Channel", "Test");
    }
}