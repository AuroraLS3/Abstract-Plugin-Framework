package com.djrapitops.plugin.api;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckTest {

    @Test
    public void testTrue() {
        assertTrue(Check.isBukkitAvailable());
        assertTrue(Check.isBungeeAvailable());
        assertTrue(Check.isSpigotAvailable());
        assertTrue(Check.isSpongeAvailable());
        assertTrue(Check.isPaperAvailable());
    }

}