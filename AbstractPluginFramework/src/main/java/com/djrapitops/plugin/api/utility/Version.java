package com.djrapitops.plugin.api.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.api.Priority;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.StackUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Utility class for checking newest version availability from different sources.
 *
 * @author Rsl1122
 */
public class Version {

    private static <T extends IPlugin> String getGitVersion(String url) throws IOException {
        URL githubUrl = new URL(url);
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

    private static boolean isNewVersionAvailable(String currentVersion, String newVersion) {
        long newestVersionNumber = FormatUtils.parseVersionNumber(newVersion);
        long currentVersionNumber = FormatUtils.parseVersionNumber(currentVersion);
        return newestVersionNumber > currentVersionNumber;
    }

    public static boolean checkVersion(String version, String versionStringUrl) throws IOException {
        boolean gitHub = versionStringUrl.contains("raw.githubusercontent.com");
        boolean spigot = versionStringUrl.contains("spigotmc.org");
        try {
            if (gitHub) {
                return isNewVersionAvailable(version, getGitVersion(versionStringUrl));
            } else if (spigot) {
                return isNewVersionAvailable(version, getSpigotVersion(versionStringUrl));
            }
        } catch (NumberFormatException e) {
            throw new IOException("Version fetch error, address: " + versionStringUrl, e);
        }
        throw new IOException("Version can not be fetched from this address: " + versionStringUrl);
    }

    private static String getSpigotVersion(String versionStringUrl) throws IOException {
        String[] split = versionStringUrl.split("\\.");
        String resourceID = split[split.length - 1].replace("/", "");
        String requestUrl = "https://api.spiget.org/v2/resources/" + resourceID + "/versions?size=1&sort=-name";
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "AbstractPluginFramework: " + StackUtils.getCallingPlugin().getSimpleName());

        int responseCode = connection.getResponseCode();
        try (InputStream inputStream = connection.getInputStream()) {

            switch (responseCode) {
                case 500:
                case 403:
                case 404:
                case 400:
                    throw new IOException("Spiget API returned response code: " + responseCode);
                default:
                    try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                        JsonElement element = new JsonParser().parse(reader);
                        if (element.isJsonArray()) {
                            return element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                        } else if (element.isJsonObject()) {
                            return element.getAsJsonObject().get("name").getAsString();
                        }
                        return "0";
                    }
            }
        }
    }
}
