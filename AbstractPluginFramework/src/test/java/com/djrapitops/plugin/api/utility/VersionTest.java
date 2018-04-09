package com.djrapitops.plugin.api.utility;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class VersionTest {
    @Test
    public void checkVersionGitHub() throws Exception {
        assertTrue(Version.checkVersion("1", "https://raw.githubusercontent.com/Rsl1122/Plan-PlayerAnalytics/master/Plan/src/main/resources/plugin.yml"));
    }

    @Test
    public void checkVersionSpigot() throws Exception {
        assertTrue(Version.checkVersion("1", "https://www.spigotmc.org/resources/plan-player-analytics.32536/"));
    }
    @Test
    public void checkVersionSpigot2() throws Exception {
        assertFalse(Version.checkVersion("400", "https://www.spigotmc.org/resources/plan-player-analytics.32536/"));
    }

    @Test
    public void isNewVersionAvailableComparesCorrectly() {
        assertTrue(Version.isNewVersionAvailable(new Version("1"), new Version("2")));
        assertFalse(Version.isNewVersionAvailable(new Version("1"), new Version("1")));
        assertFalse(Version.isNewVersionAvailable(new Version("1"), new Version("0")));
    }

}