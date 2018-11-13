package com.djrapitops.plugin;

/**
 * Interface layer to hide implementation specific methods from {@link IPlugin} interface.
 *
 * @author Rsl1122
 * @see IPlugin for proper plugin interface methods.
 */
public interface APFPlugin extends IPlugin {

    /**
     * Internal method for setting the plugin as reloading.
     *
     * @param value is the plugin reloading.
     */
    void setReloading(boolean value);

}
