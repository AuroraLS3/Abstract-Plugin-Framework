package com.djrapitops.plugin.settings;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.api.Priority;
import com.djrapitops.plugin.utilities.FormattingUtils;

/**
 * Utility method class containing various static methods.
 *
 * @author Rsl1122
 */
public class Version {

    /**
     * Checks the version and returns response String.
     *
     * @param <T>
     * @param instance
     * @return String informing about status of plugins version.
     */
    public static <T extends IPlugin> String checkVersion(T instance) {
        try {
            String currentVersion = instance.getVersion();
            String gitVersion = getGitVersion(instance);
            if (isNewVersionAvailable(currentVersion, gitVersion)) {
                String message = "New Version (" + gitVersion + ") is available at" + instance.getUpdateUrl();
                instance.getNotificationCenter().addNotification(Priority.HIGH, message);
                return message;
            } else {
                return "You're running the latest version";
            }
        } catch (NumberFormatException e) {
            instance.getPluginLogger().error("Failed to compare versions.");
        } catch (IOException | NullPointerException e) {
            instance.getPluginLogger().error("Failed to get newest version number.");
        }
        return "";
    }

    private static <T extends IPlugin> String getGitVersion(T instance) throws IOException, NullPointerException {
        URL githubUrl = new URL(instance.getUpdateCheckUrl());
        String lineWithVersion = "";
        Scanner websiteScanner = new Scanner(githubUrl.openStream());
        while (websiteScanner.hasNextLine()) {
            String line = websiteScanner.nextLine();
            if (line.toLowerCase().contains("version")) {
                lineWithVersion = line;
                break;
            }
        }
        return lineWithVersion.split(": ")[1];
    }

    /**
     *
     * @param <T>
     * @param instance
     * @return
     * @throws java.io.IOException
     * @throws NumberFormatException
     */
    public static <T extends IPlugin> boolean isUpToDate(T instance) throws IOException {
        String currentVersion = instance.getVersion();
        String gitVersion = getGitVersion(instance);
        return !isNewVersionAvailable(currentVersion, gitVersion);
    }

    private static boolean isNewVersionAvailable(String currentVersion, String gitVersion) throws NumberFormatException {
        int newestVersionNumber = FormattingUtils.parseVersionNumber(gitVersion);
        int currentVersionNumber = FormattingUtils.parseVersionNumber(currentVersion);
        return newestVersionNumber > currentVersionNumber;
    }
}
