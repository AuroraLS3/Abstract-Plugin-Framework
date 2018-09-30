package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.logging.console.PluginLogger;

import java.io.File;

/**
 * {@link ErrorHandler} implementation that is used by default in APF plugins.
 * <p>
 * To replace ErrorHandlers in use call {@link IPlugin#getErrorHandler()#setErrorHandlers(ErrorHandler...)}.
 *
 * @author Rsl1122
 */
public class DefaultErrorHandler extends CombineErrorHandler {

    /**
     * Create a new DefaultErrorHandler.
     *
     * @param plugin     Plugin to use in {@link CriticalErrorHandler}.
     * @param logger     PluginLogger to use in {@link ConsoleErrorLogger}.
     * @param logsFolder Folder to use in {@link FolderTimeStampErrorFileLogger}.
     */
    public DefaultErrorHandler(IPlugin plugin, PluginLogger logger, File logsFolder) {
        this(new ConsoleErrorLogger(logger), new CriticalErrorHandler(plugin), logsFolder);
    }

    private DefaultErrorHandler(
            ConsoleErrorLogger consoleErrorLogger,
            CriticalErrorHandler criticalErrorHandler,
            File logsFolder) {
        super(
                consoleErrorLogger,
                new FolderTimeStampErrorFileLogger(logsFolder, consoleErrorLogger),
                criticalErrorHandler
        );
    }

}
