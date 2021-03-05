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
 * @author AuroraLS3
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