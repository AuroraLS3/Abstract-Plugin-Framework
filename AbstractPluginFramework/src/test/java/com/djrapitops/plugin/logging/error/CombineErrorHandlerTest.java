package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.TestPluginLogger;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link CombineErrorHandler}.
 *
 * @author Rsl1122
 */
public class CombineErrorHandlerTest {

    @Test
    public void existingSubErrorHandlerIsFound() {
        CombineErrorHandler underTest = new CombineErrorHandler(
                new ConsoleErrorLogger(new TestPluginLogger()),
                new CriticalErrorHandler(null)
        );

        assertTrue(underTest.getErrorHandler(ConsoleErrorLogger.class).isPresent());
        assertTrue(underTest.getErrorHandler(CriticalErrorHandler.class).isPresent());
    }

    @Test
    public void nonExistentSubErrorHandlerIsNotFound() {
        CombineErrorHandler underTest = new CombineErrorHandler(
                new ConsoleErrorLogger(new TestPluginLogger()),
                new CriticalErrorHandler(null)
        );

        assertFalse(underTest.getErrorHandler(FolderTimeStampErrorFileLogger.class).isPresent());
    }

    @Test
    public void subErrorHandlersAreCalledOnLog() {
        ErrorHandler mockOne = Mockito.mock(ErrorHandler.class);
        ErrorHandler mockTwo = Mockito.mock(ErrorHandler.class);

        ErrorHandler underTest = new CombineErrorHandler(mockOne, mockTwo);

        Exception testException = new Exception("Test Exception");
        underTest.log(L.WARN, ErrorHandler.class, testException);

        verify(mockOne).log(L.WARN, ErrorHandler.class, testException);
        verify(mockOne).log(L.WARN, ErrorHandler.class, testException);
    }

}