/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.config.fileconfig;

import java.util.List;
import java.util.Set;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public interface IConfigSection {

    void set(String path, Object object);

    boolean getBoolean(String path);

    Integer getInt(String path);

    Double getDouble(String path);

    Long getLong(String path);

    String getString(String path);

    List<String> getStringList(String path);

    List<Integer> getIntegerList(String path);

    IConfigSection getConfigSection(String path);

    Set<String> getKeys();
}