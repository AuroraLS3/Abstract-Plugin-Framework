/**
 * Things related to task abstraction layer.
 * <p>
 * This package attempts to abstract away task scheduling on different platforms with {@link com.djrapitops.plugin.task.RunnableFactory}.
 * A RunnableFactory can be obtained via {@link com.djrapitops.plugin.IPlugin#getRunnableFactory()}.
 * <p>
 * Remember to call scheduling method of {@link com.djrapitops.plugin.task.PluginRunnable} after creating an instance.
 */
package com.djrapitops.plugin.task;