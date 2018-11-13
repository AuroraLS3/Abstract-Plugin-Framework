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
 * @author Rsl1122
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