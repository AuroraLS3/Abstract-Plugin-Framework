package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;

/**
 * Interface for a class that handles errors in an appropriate way.
 *
 * @author Rsl1122
 */
public interface ErrorHandler {

    /**
     * Handle a caught Throwable.
     *
     * @param level     Level of severity.
     * @param caughtBy  Class the throwable was caught by.
     * @param throwable Throwable that was caught.
     */
    void log(L level, Class caughtBy, Throwable throwable);
}
