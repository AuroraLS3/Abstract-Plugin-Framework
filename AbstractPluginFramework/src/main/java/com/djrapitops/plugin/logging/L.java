package com.djrapitops.plugin.logging;

/**
 * L - Level, determines the logging type of
 * {@link com.djrapitops.plugin.logging.console.PluginLogger} and {@link com.djrapitops.plugin.logging.error.ErrorHandler}.
 * <p>
 * INFO: INFO level logging, any and all messages.
 * INFO_COLOR: INFO level logging with colored messages.
 * DEBUG: messages going into the {@link com.djrapitops.plugin.logging.debug.DebugLogger} default channel.
 * DEBUG_INFO: INFO level message logging of DEBUG channel messages if {@link com.djrapitops.plugin.logging.debug.DebugConsoleLogger} is used.
 * WARN: WARN level messages, warnings to the user.
 * ERROR: ERROR level messages, warnings to the user.
 * CRITICAL: ERROR level messages, implementations should disable the plugin if logging an exception.
 *
 * @author Rsl1122
 */
public enum L {

    INFO,
    INFO_COLOR,
    DEBUG,
    DEBUG_INFO,
    WARN,
    ERROR,
    CRITICAL

}
