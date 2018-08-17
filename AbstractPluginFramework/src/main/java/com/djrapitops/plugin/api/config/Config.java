package com.djrapitops.plugin.api.config;

import java.io.File;
import java.util.List;

/**
 * @author Rsl1122
 * @deprecated Use new location instead.
 */
@Deprecated
public class Config extends com.djrapitops.plugin.config.Config {

    public Config(File file) {
        super(file);
    }

    public Config(File file, List<String> defaults) {
        super(file, defaults);
    }
}