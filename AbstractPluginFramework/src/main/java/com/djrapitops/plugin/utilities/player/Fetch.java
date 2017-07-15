package com.djrapitops.plugin.utilities.player;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.BungeePlugin;
import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.utilities.Compatibility;
import com.djrapitops.plugin.utilities.Verify;
import com.djrapitops.plugin.utilities.player.bukkit.*;
import com.djrapitops.plugin.utilities.player.bungee.BungeeConnectedPlayer;
import com.djrapitops.plugin.utilities.player.bungee.BungeeProxiedPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Class for fetching abstracted player classes.
 *
 * @author Rsl1122
 * @param <T>
 */
public class Fetch<T extends IPlugin> {

    private final IPlugin plugin;

    /**
     * Constructor for the Fetch utility.
     *
     * @param plugin a BukkitPlugin or BungeePlugin instance.
     */
    public Fetch(IPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Check if a player is online.
     *
     * Bukkit: Checks if player is on the server.
     *
     * Bungee: Checks if player is connected to the network.
     *
     * @param uuid UUID of the player.
     * @return true / false
     * @throws NullPointerException If given uuid is null
     */
    public boolean isOnline(UUID uuid) throws NullPointerException {
        Verify.nullCheck(uuid);
        return getPlayer(uuid).isPresent();
    }

    /**
     * Check if the player has played before.
     *
     * Bukkit: Checks the player files for the player.
     *
     * Bungee: Checks if the player is connected to the network. Bungee
     * functionality is limited because there are no files saved for offline
     * players.
     *
     * @param uuid UUID of the player.
     * @return true / false
     */
    public boolean hasPlayedBefore(UUID uuid) {
        if (Compatibility.isBukkitAvailable()) {
            return ((BukkitPlugin) plugin).getServer().getOfflinePlayer(uuid).hasPlayedBefore();
        } else if (Compatibility.isBungeeAvailable()) {
            return isOnline(uuid);
        } else {
            return false;
        }
    }

    /**
     * Used to get the wrapped IPlayer object for a player.
     *
     * Bukkit: Wrapped player object if the player is online or null.
     *
     * Bungee: player that is connected directly to the proxy server.
     *
     * @param uuid UUID of the player.
     * @return IPlayer object or null if the player is not online.
     * @throws IllegalStateException If Bukkit or Bungee classes are not
     * available.
     */
    public Optional<IPlayer> getPlayer(UUID uuid) throws NullPointerException, IllegalStateException {
        IPlayer p = null;
        if (Compatibility.isBukkitAvailable()) {
            p = wrap(((BukkitPlugin) plugin).getServer().getPlayer(uuid));
        } else if (Compatibility.isBungeeAvailable()) {
            try {
                wrap((net.md_5.bungee.api.connection.ConnectedPlayer) ((BungeePlugin) plugin).getProxy().getPlayer(uuid));
            } catch (Throwable e) {
            }
        } else {
            throw new IllegalStateException("Can not get Player objecst without Bukkit or Bungee.");
        }
        return Optional.of(p);
    }

    /**
     * Used to get the wrapped IOfflinePlayer object for a player.
     *
     * Bukkit: Wrapped player object
     *
     * Bungee: Proxied player if the server is online anywhere or null if not
     * online.
     *
     * @param uuid UUID of the player.
     * @return IPlayer object or null if the player is not online.
     * @throws IllegalStateException If Bukkit or Bungee classes are not
     * available.
     */
    public IOfflinePlayer getOfflinePlayer(UUID uuid) throws IllegalStateException {
        if (Compatibility.isBukkitAvailable()) {
            return wrap(((BukkitPlugin) plugin).getServer().getOfflinePlayer(uuid));
        } else if (Compatibility.isBungeeAvailable()) {
            try {
                return wrap(((BungeePlugin) plugin).getProxy().getPlayer(uuid));
            } catch (Throwable e) {
                return null;
            }
        } else {
            throw new IllegalStateException("Can not get Player objecst without Bukkit or Bungee.");
        }
    }

    /**
     * Used to get a List of IPlayer objects for every player online in the
     * server.
     *
     * Bukkit: List of Wrapped player objects.
     *
     * Bungee: List of all players connected directly to the proxy server.
     *
     * @return list of wrapped player objects.
     * @throws IllegalStateException If Bukkit or Bungee classes are not
     * available.
     */
    public List<IPlayer> getOnlinePlayers() {
        if (Compatibility.isBukkitAvailable()) {
            return ((BukkitPlugin) plugin).getServer().getOnlinePlayers().stream().map(p -> wrap(p)).collect(Collectors.toList());
        } else if (Compatibility.isBungeeAvailable()) {
            return ((BungeePlugin) plugin).getProxy().getPlayers().stream()
                    .filter(p -> p != null && p.isConnected())
                    .map(p -> wrap((net.md_5.bungee.api.connection.ConnectedPlayer) p))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalStateException("Can not get Player objecst without Bukkit or Bungee.");
        }
    }

    /**
     * Used to get a List of IOfflinePlayer objects for every player that have
     * played on the server.
     *
     * Bukkit: List of Wrapped player objects.
     *
     * Bungee: List of all Proxied players.
     *
     * @return list of wrapped player objects.
     * @throws IllegalStateException If Bukkit or Bungee classes are not
     * available.
     */
    public List<IOfflinePlayer> getOfflinePlayers() {
        if (Compatibility.isBukkitAvailable()) {
            return Arrays.stream(((BukkitPlugin) plugin).getServer().getOfflinePlayers()).map(p -> new BukkitOfflinePlayer(p)).collect(Collectors.toList());
        } else if (Compatibility.isBungeeAvailable()) {
            return ((BungeePlugin) plugin).getProxy().getPlayers().stream()
                    .filter(p -> p != null && p.isConnected())
                    .map(p -> wrap(p))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalStateException("Can not get Player objecst without Bukkit or Bungee.");
        }
    }

    /**
     * Used to get the IPlayer object for an online player.
     *
     * @param uuid UUID of the player.
     * @return IPlayer object or null if not online.
     */
    public static Optional<IPlayer> getIPlayer(UUID uuid) {
        return getPlayer(uuid, getProviderClass());
    }

    /**
     * Used to get the IOfflinePlayer object for a player.
     *
     * @param uuid UUID of the player.
     * @return IOfflinePlayer object or null if not online.
     */
    public static IOfflinePlayer getIOfflinePlayer(UUID uuid) {
        return getOfflinePlayer(uuid, getProviderClass());
    }

    /**
     * Used to get a List of IPlayer objects for every player online in the
     * server.
     *
     * Bukkit: List of Wrapped player objects.
     *
     * Bungee: Empty list (Unimplemented)
     *
     * @return list of wrapped player objects.
     * @throws IllegalStateException If Bukkit or Bungee classes are not
     * available.
     */
    public static List<IPlayer> getIOnlinePlayers() {
        return getOnlinePlayers(getProviderClass());
    }

    /**
     * Used to get a List of IOfflinePlayer objects for every player that have
     * played on the server.
     *
     * Bukkit: List of Wrapped player objects.
     *
     * Bungee: Empty list (Unimplemented)
     *
     * @return list of wrapped player objects.
     * @throws IllegalStateException If Bukkit or Bungee classes are not
     * available.
     */
    public static List<IOfflinePlayer> getIOfflinePlayers() {
        return getOfflinePlayers(getProviderClass());
    }

    /**
     *
     * @param <T>
     * @param uuid
     * @param c
     * @return
     * @deprecated Class is not required, use the static method without Class
     * parameter.
     */
    @Deprecated
    public static <T extends IPlugin> Optional<IPlayer> getPlayer(UUID uuid, Class<T> c) {
        return getPlayerFetchUtility(c).getPlayer(uuid);
    }

    /**
     * Get the IOfflinePlayer for a player.
     *
     * @param <T> The type of the class
     * @param uuid UUID of the player
     * @param c Class that implements BukkitPlugin or BungeePlugin
     * @return IOfflinePlayer.
     * @deprecated Class is not required, use the static method without Class
     * parameter.
     */
    @Deprecated
    public static <T extends IPlugin> IOfflinePlayer getOfflinePlayer(UUID uuid, Class<T> c) {
        return getPlayerFetchUtility(c).getOfflinePlayer(uuid);
    }

    /**
     *
     * @param <T>
     * @param c
     * @return
     * @deprecated Class is not required, use the static method without Class
     * parameter.
     */
    @Deprecated
    public static <T extends IPlugin> List<IPlayer> getOnlinePlayers(Class<T> c) {
        return getPlayerFetchUtility(c).getOnlinePlayers();
    }

    /**
     *
     * @param <T>
     * @param c
     * @return
     * @deprecated Class is not required, use the static method without Class
     * parameter.
     */
    @Deprecated
    public static <T extends IPlugin> List<IOfflinePlayer> getOfflinePlayers(Class<T> c) {
        return getPlayerFetchUtility(c).getOfflinePlayers();
    }

    private static Class getProviderClass() {
        return Compatibility.getUtilityProviderClass();
    }

    private static <T extends IPlugin> Fetch getPlayerFetchUtility(Class<T> c) {
        if (Compatibility.isBukkitAvailable() || Compatibility.isBungeeAvailable()) {
            return StaticHolder.getInstance(c).fetch();
        } else {
            throw new IllegalStateException("Can not get Player objecst without Bukkit or Bungee.");
        }
    }

    /**
     * Wraps a OfflinePlayer into a IOfflinePlayer object.
     *
     * @param p OfflinePlayer object.
     * @return IOfflinePlayer. null if given parameter is null.
     */
    public static IOfflinePlayer wrap(org.bukkit.OfflinePlayer p) {
        if (p == null) {
            return null;
        }
        return BukkitOfflinePlayer.wrap(p);
    }

    /**
     * Wraps a Player into a IPlayer object.
     *
     * @param p Player object.
     * @return IPlayer. null if given parameter is null.
     */
    public static IPlayer wrap(org.bukkit.entity.Player p) {
        if (p == null) {
            return null;
        }
        return BukkitPlayer.wrap(p);
    }

    public static IPlayer wrap(net.md_5.bungee.api.connection.ConnectedPlayer p) {
        if (p == null) {
            return null;
        }
        return BungeeConnectedPlayer.wrap(p);
    }

    public static IOfflinePlayer wrap(net.md_5.bungee.api.connection.ProxiedPlayer p) {
        if (p == null) {
            return null;
        }
        return BungeeProxiedPlayer.wrap(p);
    }
}
