package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.console.PluginLogger;

import java.io.File;

public class DefaultErrorHandler extends CombineErrorHandler {

    public DefaultErrorHandler(PluginLogger logger, File logsFolder) {
        this(new ConsoleErrorLogger(logger), logsFolder);
    }

    private DefaultErrorHandler(ConsoleErrorLogger consoleErrorLogger, File logsFolder) {
        super(
                consoleErrorLogger,
                new FolderTimeStampErrorFileLogger(logsFolder, consoleErrorLogger)
        );
    }


}
