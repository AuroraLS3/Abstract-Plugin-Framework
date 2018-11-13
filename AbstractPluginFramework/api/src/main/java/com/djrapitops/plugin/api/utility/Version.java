package com.djrapitops.plugin.api.utility;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Utility class for checking newest version availability from different sources.
 *
 * @author Rsl1122
 */
public class Version implements Comparable<Version> {

    private final String versionString;

    /**
     * Create a new Version object.
     *
     * @param versionString For example "2017.132", "1.1.0", "v-43"
     *                      All non-number characters will be omitted during comparison.
     */
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

    /**
     * Compare two versions.
     *
     * @param currentVersion Current version object.
     * @param newVersion     New version object.
     * @return if new version object has higher version than current version.
     */
    public static boolean isNewVersionAvailable(Version currentVersion, Version newVersion) {
        return newVersion.compareTo(currentVersion) > 0;
    }

    public static boolean checkVersion(String version, String versionStringUrl) throws IOException {
        Version currentVersion = new Version(version);
        boolean gitHub = versionStringUrl.contains("raw.githubusercontent.com");
        try {
            if (gitHub) {
                return isNewVersionAvailable(currentVersion, getGitVersion(versionStringUrl));
            }
        } catch (NumberFormatException e) {
            throw new IOException("Version fetch error, address: " + versionStringUrl, e);
        }
        throw new IOException("Version can not be fetched from this address: " + versionStringUrl);
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
        return Objects.equals(versionString, version.versionString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionString);
    }

    public String getVersionString() {
        return versionString;
    }

    @Override
    public String toString() {
        return versionString;
    }
}
