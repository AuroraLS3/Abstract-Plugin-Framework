package com.djrapitops.plugin.api.utility;

import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void parseVersionNumber() {
        assertEquals(10101000000000000L, new Version("1.1.1").getVersionLong());
        assertEquals(10101010000000000L, new Version("1.1.1-Dev1").getVersionLong());
        assertEquals(10201010000000000L, new Version("1.2.1-Dev1").getVersionLong());
        assertEquals(30201010000000000L, new Version("3.2.1-Dev1").getVersionLong());
        assertEquals(30250010000000000L, new Version("3.2.50-Dev1").getVersionLong());
        assertEquals(424643430000000000L, new Version("42.46.43.43").getVersionLong());
        assertTrue(new Version("3.2.50-Dev1").compareTo(new Version("42.46.43.43")) < 0);
        assertTrue(new Version("1.1.1").compareTo(new Version("1.1.1-Dev1")) < 0);
        assertTrue(new Version("1.1.1-Dev1").compareTo(new Version("1.2.1-Dev1")) < 0);
        assertTrue(new Version("3.2.1-Dev1").compareTo(new Version("3.2.50-Dev1")) < 0);
    }

}