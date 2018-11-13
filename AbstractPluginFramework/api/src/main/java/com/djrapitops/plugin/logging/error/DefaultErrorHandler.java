/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
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

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.logging.console.PluginLogger;

import java.io.File;
import java.util.function.Supplier;

/**
 * {@link ErrorHandler} implementation that is used by default in APF plugins.
 * <p>
 * To replace ErrorHandlers in use call {@link CombineErrorHandler#setErrorHandlers(ErrorHandler...)}.
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

    /**
     * Create a new DefaultErrorHandler.
     *
     * @param plugin     Plugin to use in {@link CriticalErrorHandler}.
     * @param logger     PluginLogger to use in {@link ConsoleErrorLogger}.
     * @param logsFolder Supplier for the Folder to use in {@link FolderTimeStampErrorFileLogger}.
     */
    public DefaultErrorHandler(IPlugin plugin, PluginLogger logger, Supplier<File> logsFolder) {
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

    private DefaultErrorHandler(
            ConsoleErrorLogger consoleErrorLogger,
            CriticalErrorHandler criticalErrorHandler,
            Supplier<File> logsFolder) {
        super(
                consoleErrorLogger,
                new FolderTimeStampErrorFileLogger(logsFolder, consoleErrorLogger),
                criticalErrorHandler
        );
    }

}
