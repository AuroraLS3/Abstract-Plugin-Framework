package com.djrapitops.plugin.api;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
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