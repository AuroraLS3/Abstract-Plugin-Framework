package com.djrapitops.plugin.api.utility;

import com.djrapitops.plugin.utilities.StackUtils;
import com.google.common.base.Objects;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Utility class for checking newest version availability from different sources.
 *
 * @author Rsl1122
 */
public class Version implements Comparable<Version> {

    private final String versionString;

    public Version(String versionString) {
        this.versionString = versionString;
    }

    public static Version getGitVersion(String url) throws IOException {
        URL githubUrl = new URL(url);
        String lineWithVersion = "";
        try (Scanner websiteScanner = new Scanner(githubUrl.openStream())) {
            while (websiteScanner.hasNextLine()) {
                String line = websiteScanner.nextLine();
                if (line.toLowerCase().contains("version")) {
                    lineWithVersion = line;
                    break;
                }
            }
        }
        return new Version(lineWithVersion.split(": ")[1]);
    }

    public static boolean isNewVersionAvailable(Version currentVersion, Version newVersion) {
        return newVersion.compareTo(currentVersion) > 0;
    }

    public static boolean checkVersion(String version, String versionStringUrl) throws IOException {
        Version currentVersion = new Version(version);
        boolean gitHub = versionStringUrl.contains("raw.githubusercontent.com");
        boolean spigot = versionStringUrl.contains("spigotmc.org");
        try {
            if (gitHub) {
                return isNewVersionAvailable(currentVersion, getGitVersion(versionStringUrl));
            } else if (spigot) {
                return isNewVersionAvailable(currentVersion, getSpigotVersion(versionStringUrl));
            }
        } catch (NumberFormatException e) {
            throw new IOException("Version fetch error, address: " + versionStringUrl, e);
        }
        throw new IOException("Version can not be fetched from this address: " + versionStringUrl);
    }

    public static Version getSpigotVersion(String versionStringUrl) throws IOException {
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
                            return new Version(element.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                        } else if (element.isJsonObject()) {
                            return new Version(element.getAsJsonObject().get("name").getAsString());
                        }
                        return new Version("0");
                    }
            }
        }
    }

    @Override
    public int compareTo(Version o) {
        return Long.compare(
                this.getVersionLong(),
                o.getVersionLong()
        );
    }

    /**
     * Get a parsed long of the version string.
     *
     * @return Comparable version number.
     */
    public long getVersionLong() {
        String replaced = versionString.replaceAll("[^0-9]", ".");
        String[] split = replaced.split("\\.");
        List<String> version = Arrays.stream(split).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        long versionNumber = 0;
        for (int i = 0; i < version.size(); i++) {
            int num = Integer.parseInt(version.get(i));
            long multiplier = (long) Math.pow(100, 8.0 - i);
            versionNumber += num * multiplier;
        }
        return versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return Objects.equal(versionString, version.versionString);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(versionString);
    }

    public String getVersionString() {
        return versionString;
    }

    @Override
    public String toString() {
        return versionString;
    }
}
