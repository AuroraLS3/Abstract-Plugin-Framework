package com.djrapitops.plugin;

import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.command.bungee.BungeeCommand;
import com.djrapitops.plugin.config.BungeeConfig;
import com.djrapitops.plugin.config.IConfig;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.settings.Version;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.NotificationCenter;
import com.djrapitops.plugin.utilities.log.BungeeLog;
import com.djrapitops.plugin.utilities.log.PluginLog;
import com.djrapitops.plugin.utilities.player.Fetch;
import com.djrapitops.plugin.utilities.status.TaskStatus;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @param <T>
 * @author Rsl1122
 */
public abstract class BungeePlugin<T extends BungeePlugin> extends Plugin implements IPlugin {

    private String updateCheckUrl = "";
    private String updateUrl = "";
    private String logPrefix = "[DefaultPrefix]";
    private String debugMode = "false";
    private ColorScheme colorScheme = new ColorScheme(ChatColor.WHITE, ChatColor.GRAY, ChatColor.DARK_GRAY);

    private final TaskStatus<T> taskStat;
    private final BenchUtil benchmark;
    private PluginLog log;
    private final RunnableFactory factory;
    private final Fetch<T> playerFetcher;
    private final NotificationCenter<T> notificationCenter;

    public BungeePlugin() {
        try {
            log = new BungeeLog(this, debugMode, logPrefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        taskStat = new TaskStatus(this);
        benchmark = new BenchUtil(this);
        factory = new RunnableFactory(this);
        playerFetcher = new Fetch(this);
        notificationCenter = new NotificationCenter(this);
    }

    @Override
    public abstract void onEnable();

    @Override
    public abstract void onDisable();

    @Override
    public void onEnableDefaultTasks() {
        log.setDebugMode(debugMode);
        log.setPrefix(logPrefix);
        logDebugHeader();
        if (!updateCheckUrl.isEmpty()) {
            getPluginLogger().info(Version.checkVersion(this));
        }
    }

    private void logDebugHeader() {
        log.debug("-------------------------------------");
        log.debug("Debug log: " + this.getClass().getSimpleName() + " v." + getVersion());
        log.debug("Implements RslPlugin v." + getAPFVersion());
        log.debug("Bungee Version: " + getProxy().getVersion());
        log.debug("-------------------------------------");
    }

    public final void setInstance(BungeePlugin instance) {
        setInstance(instance.getClass(), instance);
    }

    public final void setInstance(Class c, BungeePlugin instance) {
        StaticHolder.setInstance(c, instance);
    }

    public static <T extends BungeePlugin> T getInstance(Class<T> c) {
        return (T) getPluginInstance(c);
    }

    public final static <T extends BungeePlugin> BungeePlugin getPluginInstance(Class<T> c) {
        BungeePlugin INSTANCE = StaticHolder.getInstance(c);
        if (INSTANCE == null) {
            throw new IllegalStateException("Plugin not enabled properly, Singleton instance is null: " + c.getSimpleName());
        }
        return INSTANCE;
    }

    @Override
    public String getUpdateCheckUrl() {
        return updateCheckUrl;
    }

    public void setUpdateCheckUrl(String updateCheckUrl) {
        if (updateCheckUrl.contains("raw.githubusercontent.com")) {
            this.updateCheckUrl = updateCheckUrl;
        }
    }

    @Override
    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    @Override
    public PluginLog getPluginLogger() {
        return log;
    }

    @Override
    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    @Override
    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public String getPrefix() {
        return logPrefix;
    }

    @Override
    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    @Override
    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    @Override
    public String getAPFVersion() {
        return StaticHolder.getAPFVersion();
    }

    public void registerListener(Listener l) {
        getProxy().getPluginManager().registerListener(this, l);
    }

    @Override
    public void setLog(PluginLog log) {
        this.log = log;
    }

    @Override
    public TaskStatus<T> taskStatus() {
        return taskStat;
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void registerCommand(SubCommand subCmd) {
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand(subCmd));
    }

    @Override
    public BenchUtil benchmark() {
        return benchmark;
    }

    @Override
    public RunnableFactory getRunnableFactory() {
        return factory;
    }

    @Override
    public void disablePlugin() {
        onDisable();
    }

    @Override
    public Fetch fetch() {
        return playerFetcher;
    }

    @Override
    public void copyDefaultConfig(String header) {
        final File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        File file = new File(dataFolder, "config.yml");
        if (!file.exists()) {
            InputStream in = getResourceAsStream("bungeeconfig.yml");
            if (in == null) {
                in = getResourceAsStream("config.yml");
            }
            try {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                log.toLog(this.getClass().getName(), e);
            }
        }
    }

    public IConfig getIConfig() throws IOException {
        BungeeConfig bungeeConfig = new BungeeConfig(getDataFolder(), "config.yml");
        return bungeeConfig;
    }

    public void saveConfig(Configuration config) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
    }

    @Override
    public NotificationCenter getNotificationCenter() {
        return notificationCenter;
    }
}
