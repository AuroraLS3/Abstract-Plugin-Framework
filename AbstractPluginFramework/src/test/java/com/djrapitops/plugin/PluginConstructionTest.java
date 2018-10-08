package com.djrapitops.plugin;

import com.velocitypowered.api.proxy.ProxyServer;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class PluginConstructionTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void bukkitPluginConstructionSucceeds() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("JavaPlugin requires org.bukkit.plugin.java.PluginClassLoader");

        new BukkitPlugin() {
            @Override
            public String getVersion() {
                return "0";
            }
        };
    }

    @Test
    public void bungeePluginConstructionSucceeds() {
        new BungeePlugin() {
            @Override
            public String getVersion() {
                return "0";
            }
        };
    }

    @Test
    public void spongePluginConstructionSucceeds() {
        new SpongePlugin() {
            @Override
            public Logger getLogger() {
                return null;
            }

            @Override
            public void onEnable() {
            }

            @Override
            public File getDataFolder() {
                try {
                    return temporaryFolder.newFolder();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        };
    }

    @Test
    public void velocityPluginConstructionSucceeds() {
        new VelocityPlugin() {
            @Override
            protected ProxyServer getProxy() {
                return Mockito.mock(ProxyServer.class);
            }

            @Override
            protected Logger getLogger() {
                return null;
            }

            @Override
            public void onEnable() {

            }

            @Override
            public File getDataFolder() {
                try {
                    return temporaryFolder.newFolder();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        };
    }
}