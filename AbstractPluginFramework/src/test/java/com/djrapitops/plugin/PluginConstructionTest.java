package com.djrapitops.plugin;

import com.djrapitops.plugin.logging.L;
import com.velocitypowered.api.proxy.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;
import org.junit.After;
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
import java.lang.reflect.Field;
import java.nio.file.Path;

import static org.mockito.Mockito.when;

public class PluginConstructionTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private IPlugin underTest;

    @After
    public void tearDown() {
        underTest = null;
    }

    @Test
    public void bukkitPluginConstructionSucceeds() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("JavaPlugin requires org.bukkit.plugin.java.PluginClassLoader");

        underTest = new BukkitPlugin() {
            @Override
            public String getVersion() {
                return "0";
            }
        };
    }

    @Test
    public void bungeePluginConstructionSucceeds() throws NoSuchFieldException, IOException, IllegalAccessException {
        this.underTest = new TestBungeePlugin();
    }

    @Test
    public void spongePluginConstructionSucceeds() {
        underTest = new SpongePlugin() {
            @Override
            public Logger getLogger() {
                return Mockito.mock(org.slf4j.Logger.class);
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
    public void velocityPluginConstructionSucceeds() throws IOException {
        ProxyServer proxy = Mockito.mock(ProxyServer.class);
        Logger slf4jLogger = Mockito.mock(Logger.class);
        Path dataFolderPath = temporaryFolder.newFolder().toPath();
        underTest = new VelocityPlugin(proxy, slf4jLogger, dataFolderPath) {
            @Override
            public void onEnable() {

            }
        };
    }

    @Test
    public void bungeePluginAPIFunctions() throws IllegalAccessException, NoSuchFieldException, IOException {
        bungeePluginConstructionSucceeds();
        pluginAPIFunctions();
    }

    @Test
    public void spongePluginAPIFunctions() {
        spongePluginConstructionSucceeds();
        pluginAPIFunctions();
    }

    @Test
    public void velocityPluginAPIFunctions() throws IOException {
        velocityPluginConstructionSucceeds();
        pluginAPIFunctions();
    }

    private void pluginAPIFunctions() {
        underTest.getPluginLogger().log(L.INFO, "Test Message");
        underTest.getErrorHandler().log(L.INFO, this.getClass(), new Exception("Test Exception"));
    }

    private class TestBungeePlugin extends BungeePlugin {
        java.util.logging.Logger testLogger;

        TestBungeePlugin() throws NoSuchFieldException, IOException, IllegalAccessException {
            super();
            this.testLogger = Mockito.mock(java.util.logging.Logger.class);

            // Emulate Bungee PluginManager functionality by setting variables after construction is complete.
            Field dataFolder = Plugin.class.getDeclaredField("file");
            dataFolder.setAccessible(true);
            dataFolder.set(this, temporaryFolder.newFolder());

            Field description = Plugin.class.getDeclaredField("description");
            description.setAccessible(true);
            PluginDescription mockDescription = Mockito.mock(PluginDescription.class);
            when(mockDescription.getName()).thenReturn(getClass().getName());
            description.set(this, mockDescription);

            Field proxy = Plugin.class.getDeclaredField("proxy");
            proxy.setAccessible(true);
            net.md_5.bungee.api.ProxyServer server = Mockito.mock(net.md_5.bungee.api.ProxyServer.class);
            when(server.getPluginsFolder()).thenReturn(temporaryFolder.newFolder());
            proxy.set(this, server);
        }

        @Override
        public String getVersion() {
            return "0";
        }

        @Override
        public java.util.logging.Logger getLogger() {
            return testLogger;
        }
    }
}