package com.djrapitops.plugin;

import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.config.IConfig;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.NotificationCenter;
import com.djrapitops.plugin.utilities.log.PluginLog;
import com.djrapitops.plugin.utilities.player.Fetch;
import com.djrapitops.plugin.utilities.status.TaskStatus;

import java.io.File;
import java.io.IOException;

/**
 * Interface all APF Plugin classes implement.
 * @author Rsl1122
 */
public interface IPlugin {

    void onEnable();

    void onDisable();

    void onEnableDefaultTasks();

    TaskStatus taskStatus();

    String getVersion();

    String getUpdateUrl();

    String getUpdateCheckUrl();

    PluginLog getPluginLogger();

    void setLogPrefix(String logPrefix);

    void setDebugMode(String debugMode);

    String getPrefix();

    void setLog(PluginLog log);

    ColorScheme getColorScheme();

    void setColorScheme(ColorScheme colorScheme);

    String getAPFVersion();

    void registerCommand(SubCommand subCmd);

    BenchUtil benchmark();

    RunnableFactory getRunnableFactory();
    
    void disablePlugin();
    
    Fetch fetch();
    
    void copyDefaultConfig(String header);

    NotificationCenter getNotificationCenter();

    File getDataFolder();

    IConfig getIConfig() throws IOException;
}
