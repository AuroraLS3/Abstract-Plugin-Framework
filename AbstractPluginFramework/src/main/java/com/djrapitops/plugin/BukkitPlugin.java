package com.djrapitops.plugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.command.bukkit.BukkitCommand;
import com.djrapitops.plugin.pluginchannel.MessageSubChannel;
import com.djrapitops.plugin.settings.ColorScheme;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.settings.Version;
import com.djrapitops.plugin.utilities.log.BukkitLog;
import com.djrapitops.plugin.utilities.log.PluginLog;
import com.djrapitops.plugin.utilities.player.Fetch;
import com.djrapitops.plugin.utilities.status.ProcessStatus;
import com.djrapitops.plugin.utilities.status.TaskStatus;
import org.bukkit.ChatColor;
import org.bukkit.plugin.messaging.Messenger;

/**
 * Abstract BukkitPlugin instance that extends JavaPlugin.
 *
 * {@code setInstance(this);} should be called on the first line of onEnable.
 * {@code setInstance(this.getClass(), null);} should be called on the last line
 * of onDisable.
 *
 * Required settings: logPrefix.
 *
 * Optional: updateCheckUrl, updateUrl, debugMode, colorScheme
 *
 * Call {@code super.onEnableDefaultTasks();} before using utility classes or
 * methods. Set settings before using the method.
 *
 * @author Rsl1122
 * @param <T> The Type which is being extended, ie.
 * {@code Example extends BukkitPlugin<Example>}
 * @since 2.0.0
 */
public abstract class BukkitPlugin<T extends BukkitPlugin> extends JavaPlugin implements IPlugin {

    final private String version = "2.0.0";

    private String updateCheckUrl = "";
    private String updateUrl = "";
    private String logPrefix = "[DefaultPrefix]";
    private String debugMode = "false";
    private ColorScheme colorScheme = new ColorScheme(ChatColor.WHITE, ChatColor.GRAY, ChatColor.DARK_GRAY);

    private final ProcessStatus<T> progressStat;
    private final TaskStatus<T> taskStat;
    private final BenchUtil benchmark;
    private PluginLog log;
    private final RunnableFactory factory;
    private final Fetch<T> playerFetcher;

    public BukkitPlugin() {
        getDataFolder().mkdirs();
        log = new BukkitLog(this, debugMode, logPrefix);
        progressStat = new ProcessStatus(this);
        taskStat = new TaskStatus(this);
        benchmark = new BenchUtil();
        factory = new RunnableFactory(this);
        playerFetcher = new Fetch(this);
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
        log.debug("Implements BukkitPlugin v." + getAPFVersion());
        log.debug("Bukkit Version: " + getServer().getBukkitVersion());
        log.debug("Version: " + getServer().getVersion());
        log.debug("-------------------------------------");
    }

    public final void setInstance(BukkitPlugin instance) {
        setInstance(instance.getClass(), instance);
    }

    public final void setInstance(Class c, BukkitPlugin instance) {
        StaticHolder.setInstance(c, instance);
    }

    public static <T extends BukkitPlugin> T getInstance(Class<T> c) {
        return (T) getPluginInstance(c);
    }

    public final static <T extends BukkitPlugin> BukkitPlugin getPluginInstance(Class<T> c) {
        BukkitPlugin INSTANCE = StaticHolder.getInstance(c);
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
        return version;
    }

    public void registerListener(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    @Override
    public void setLog(PluginLog log) {
        this.log = log;
    }

    @Override
    public ProcessStatus<T> processStatus() {
        return progressStat;
    }

    @Override
    public TaskStatus<T> taskStatus() {
        return taskStat;
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public void registerCommand(SubCommand subCmd) {
        try {
            getCommand(subCmd.getFirstName()).setExecutor(new BukkitCommand(subCmd));
        } catch (Exception e) {
            log.toLog(this.getClass().getName(), e);
        }
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
        getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public Fetch fetch() {
        return playerFetcher;
    }

    @Override
    public void copyDefaultConfig(String header) {
        getConfig().options().copyDefaults(true);
        getConfig().options().header(header);
        saveConfig();
    }

    public void registerPluginMessageSubChannel(MessageSubChannel channel) {
        final Messenger m = getServer().getMessenger();
        m.registerIncomingPluginChannel(this, "BungeeCord", channel);
        m.registerOutgoingPluginChannel(this, "BungeeCord");
    }
}
