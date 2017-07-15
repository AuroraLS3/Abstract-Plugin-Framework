package com.djrapitops.plugin;

import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.log.PluginLog;
import com.djrapitops.plugin.utilities.player.Fetch;
import com.djrapitops.plugin.utilities.status.ProcessStatus;
import com.djrapitops.plugin.utilities.status.TaskStatus;

/**
 * Interface all APF Plugin classes implement.
 * @author Rsl1122
 */
public interface IPlugin {

    public void onEnable();

    public void onDisable();

    public void onEnableDefaultTasks();

    public ProcessStatus processStatus();

    public TaskStatus taskStatus();

    public String getVersion();

    public String getUpdateUrl();

    public String getUpdateCheckUrl();

    public PluginLog getPluginLogger();

    public void setLogPrefix(String logPrefix);

    public void setDebugMode(String debugMode);

    public String getPrefix();

    public void setLog(PluginLog log);

    public ColorScheme getColorScheme();

    public void setColorScheme(ColorScheme colorScheme);

    public String getAPFVersion();

    public void registerCommand(SubCommand subCmd);

    public BenchUtil benchmark();

    public RunnableFactory getRunnableFactory();
    
    public void disablePlugin();
    
    public Fetch fetch();
    
    public void copyDefaultConfig(String header);
}
