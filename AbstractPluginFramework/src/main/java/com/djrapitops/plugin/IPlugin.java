/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin;

import com.djrapitops.plugin.command.CommandNode;

import java.io.File;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public interface IPlugin {

    void onEnable();

    void onDisable();

    void onReload();

    void reloadPlugin(boolean full);

    boolean isReloading();

    void log(String level, String s);

    File getDataFolder();

    String getVersion();

    void registerCommand(String name, CommandNode command);

    void setReloading(boolean value);
}
