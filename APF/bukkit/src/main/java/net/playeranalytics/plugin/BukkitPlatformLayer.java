package net.playeranalytics.plugin;

import net.playeranalytics.plugin.dependencies.DependencyLoader;
import net.playeranalytics.plugin.scheduling.BukkitRunnableFactory;
import net.playeranalytics.plugin.scheduling.RunnableFactory;
import net.playeranalytics.plugin.server.BukkitListeners;
import net.playeranalytics.plugin.server.JavaUtilLoggerShim;
import net.playeranalytics.plugin.server.Listeners;
import net.playeranalytics.plugin.server.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URLClassLoader;

public class BukkitPlatformLayer implements PlatformAbstractionLayer {

    private final JavaPlugin plugin;

    private PluginLogger pluginLogger;
    private Listeners listeners;
    private RunnableFactory runnableFactory;
    private PluginInformation pluginInformation;
    private DependencyLoader dependencyLoader;

    private BukkitPlatformLayer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    PlatformAbstractionLayer create(JavaPlugin plugin) {
        return new BukkitPlatformLayer(plugin);
    }

    @Override
    public PluginLogger getPluginLogger() {
        if (pluginLogger == null) pluginLogger = new JavaUtilLoggerShim(plugin.getLogger());
        return pluginLogger;
    }

    @Override
    public Listeners getListeners() {
        if (listeners == null) listeners = new BukkitListeners(plugin);
        return listeners;
    }

    @Override
    public RunnableFactory getRunnableFactory() {
        if (runnableFactory == null) runnableFactory = new BukkitRunnableFactory(plugin);
        return runnableFactory;
    }

    @Override
    public PluginInformation getPluginInformation() {
        return plugin::getDataFolder;
    }

    @Override
    public DependencyLoader getDependencyLoader() {
        if (dependencyLoader == null) {
            dependencyLoader = new DependencyLoader(
                    (URLClassLoader) getClass().getClassLoader(),
                    getPluginLogger(),
                    getPluginInformation()
            );
        }
        return dependencyLoader;
    }
}
